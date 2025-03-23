package com.hbm.tileentity.machine.storage;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;
import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.CompatHandler;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.container.ContainerBarrel;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Corrosive;
import com.hbm.inventory.fluid.trait.FT_Polluting;
import com.hbm.inventory.fluid.trait.FluidTrait.FluidReleaseType;
import com.hbm.inventory.gui.GUIBarrel;
import com.hbm.lib.Library;
import com.hbm.saveddata.TomSaveData;
import com.hbm.tileentity.IFluidCopiable;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "opencomputers")})
public class TileEntityBarrel extends TileEntityMachineBase implements SimpleComponent, IFluidStandardTransceiverMK2, IPersistentNBT, IGUIProvider, CompatHandler.OCComponent, IFluidCopiable {

	public FluidTank tank;
	public short mode = 0;
	public static final short modes = 4;
	public int age = 0;
	public byte lastRedstone = 0;

	public TileEntityBarrel() {
		super(6);
		tank = new FluidTank(Fluids.NONE, 0);
	}

	public TileEntityBarrel(int capacity) {
		super(6);
		tank = new FluidTank(Fluids.NONE, capacity);
	}

	@Override
	public String getName() {
		return "container.barrel";
	}

	public byte getComparatorPower() {
		if(tank.getFill() == 0) return 0;
		double frac = (double) tank.getFill() / (double) tank.getMaxFill() * 15D;
		return (byte) (MathHelper.clamp_int((int) frac + 1, 0, 15));
	}

	@Override
	public long getDemand(FluidType type, int pressure) {
		if(this.mode == 2 || this.mode == 3) return 0;
		if(tank.getPressure() != pressure) return 0;
		return type == tank.getTankType() ? tank.getMaxFill() - tank.getFill() : 0;
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			byte comp = this.getComparatorPower(); //do comparator shenanigans
			if(comp != this.lastRedstone) {
				this.markDirty();
				for(DirPos pos : getConPos()) this.updateRedstoneConnection(pos);
			}
			this.lastRedstone = comp;

			tank.setType(0, 1, slots);
			tank.loadTank(2, 3, slots);
			tank.unloadTank(4, 5, slots);
			
			for(DirPos pos : getConPos()) {
				if(mode == 0 || mode == 1) this.trySubscribe(tank.getTankType(), worldObj, pos);
				if(mode == 1 || mode == 2) this.tryProvide(tank, worldObj, pos);
			}

			if(tank.getFill() > 0) {
				checkFluidInteraction();
			}

			this.networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeShort(mode);
		tank.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		mode = buf.readShort();
		tank.deserialize(buf);
	}

	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 1, yCoord, zCoord, Library.POS_X),
				new DirPos(xCoord - 1, yCoord, zCoord, Library.NEG_X),
				new DirPos(xCoord, yCoord + 1, zCoord, Library.POS_Y),
				new DirPos(xCoord, yCoord - 1, zCoord, Library.NEG_Y),
				new DirPos(xCoord, yCoord, zCoord + 1, Library.POS_Z),
				new DirPos(xCoord, yCoord, zCoord - 1, Library.NEG_Z)
		};
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		ItemStack full = FluidContainerRegistry.getFullContainer(itemStack, tank.getTankType());
		//if fillable and the fill being possible for this tank size
		if(i == 4 && full != null && FluidContainerRegistry.getFluidContent(full, tank.getTankType()) <= tank.getMaxFill())
			return true;
		int content = FluidContainerRegistry.getFluidContent(itemStack, tank.getTankType());
		//if content is above 0 but still within capacity
		if(i == 2 && content > 0 && content <= tank.getMaxFill())
			return true;

		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 3 || i == 5;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] {2, 3, 4, 5};
	}

	public void checkFluidInteraction() {

		Block b = this.getBlockType();

		//for when you fill antimatter into a matter tank
		if(b != ModBlocks.barrel_antimatter && tank.getTankType().isAntimatter()) {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
			worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true, true);
		}

		//for when you fill hot or corrosive liquids into a plastic tank
		if(b == ModBlocks.barrel_plastic && (tank.getTankType().isCorrosive() || tank.getTankType().isHot())) {
			worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.fizz", 1.0F, 1.0F);
		}

		//for when you fill corrosive liquid into an iron tank
		if((b == ModBlocks.barrel_iron && tank.getTankType().isCorrosive()) ||
				(b == ModBlocks.barrel_steel && tank.getTankType().hasTrait(FT_Corrosive.class) && tank.getTankType().getTrait(FT_Corrosive.class).getRating() > 50)) {
			ItemStack[] copy = this.slots.clone();
			this.slots = new ItemStack[6];
			worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.barrel_corroded);
			TileEntityBarrel barrel = (TileEntityBarrel)worldObj.getTileEntity(xCoord, yCoord, zCoord);

			if(barrel != null) {
				barrel.tank.setTankType(tank.getTankType());
				barrel.tank.setFill(Math.min(barrel.tank.getMaxFill(), tank.getFill()));
				barrel.slots = copy;
			}

			worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, "random.fizz", 1.0F, 1.0F);
		}

		if(b == ModBlocks.barrel_corroded ) {
			if(worldObj.rand.nextInt(3) == 0) {
				tank.setFill(tank.getFill() - 1);
				FT_Polluting.pollute(worldObj, xCoord, yCoord, zCoord, tank.getTankType(), FluidReleaseType.SPILL, 1F);
			}
			if(worldObj.rand.nextInt(3 * 60 * 20) == 0) worldObj.func_147480_a(xCoord, yCoord, zCoord, false);
		}

		//For when Tom's firestorm hits a barrel full of water
		if(tank.getTankType() == Fluids.WATER && TomSaveData.forWorld(worldObj).fire > 1e-5) {
			int light = this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord);

			if(light > 7) {
				worldObj.newExplosion(null, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, 5, true, true);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		mode = nbt.getShort("mode");
		tank.readFromNBT(nbt, "tank");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setShort("mode", mode);
		tank.writeToNBT(nbt, "tank");
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return (mode == 1 || mode == 2) ? new FluidTank[] {tank} : new FluidTank[0];
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return (mode == 0 || mode == 1) ? new FluidTank[] {tank} : new FluidTank[0];
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public ConnectionPriority getFluidPriority() {
		return mode == 1 ? ConnectionPriority.LOW : ConnectionPriority.NORMAL;
	}

	@Override
	public int[] getFluidIDToCopy() {
		return new int[] {tank.getTankType().getID()};
	}

	@Override
	public FluidTank getTankToPaste() {
		return tank;
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(tank.getFill() == 0) return;
		NBTTagCompound data = new NBTTagCompound();
		this.tank.writeToNBT(data, "tank");
		data.setShort("mode", mode);
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		this.tank.readFromNBT(data, "tank");
		this.mode = data.getShort("nbt");
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerBarrel(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIBarrel(player.inventory, this);
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String getComponentName() {
		return "ntm_fluid_tank";
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getFluidStored(Context context, Arguments args) {
		return new Object[] {tank.getFill()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getMaxStored(Context context, Arguments args) {
		return new Object[] {tank.getMaxFill()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getTypeStored(Context context, Arguments args) {
		return new Object[] {tank.getTankType().getName()};
	}

	@Callback(direct = true)
	@Optional.Method(modid = "OpenComputers")
	public Object[] getInfo(Context context, Arguments args) {
		return new Object[]{tank.getFill(), tank.getMaxFill(), tank.getTankType().getName()};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public String[] methods() {
		return new String[] {
				"getFluidStored",
				"getMaxStored",
				"getTypeStored",
				"getInfo"
		};
	}

	@Override
	@Optional.Method(modid = "OpenComputers")
	public Object[] invoke(String method, Context context, Arguments args) throws Exception {
		switch (method) {
			case "getFluidStored":
				return getFluidStored(context, args);
			case "getMaxStored":
				return getMaxStored(context, args);
			case "getTypeStored":
				return getTypeStored(context, args);
			case "getInfo":
				return getInfo(context, args);
		}
		throw new NoSuchMethodException();
	}
}
