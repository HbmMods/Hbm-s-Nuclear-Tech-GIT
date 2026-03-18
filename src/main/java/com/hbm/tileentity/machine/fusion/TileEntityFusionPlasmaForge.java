package com.hbm.tileentity.machine.fusion;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerMachinePlasmaForge;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachinePlasmaForge;
import com.hbm.inventory.recipes.PlasmaForgeRecipes;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.module.machine.ModuleMachinePlasma;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardReceiverMK2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityFusionPlasmaForge extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardReceiverMK2, IControlReceiver, IGUIProvider {

	public FluidTank inputTank;
	
	public long power;
	public long maxPower = 10_000_000;
	public boolean didProcess;

	public ModuleMachinePlasma plasmaModule;
	
	public TileEntityFusionPlasmaForge() {
		super(16);
		this.inputTank = new FluidTank(Fluids.NONE, 16_000);
		
		this.plasmaModule = new ModuleMachinePlasma(0, this, slots)
				.itemInput(3).itemOutput(15).fluidInput(inputTank);
	}

	@Override
	public String getName() {
		return "container.machinePlasmaForge";
	}

	@Override
	public void updateEntity() {
		
		if(maxPower <= 0) this.maxPower = 1_000_000;
		
		if(!worldObj.isRemote) {
			
			GenericRecipe recipe = PlasmaForgeRecipes.INSTANCE.recipeNameMap.get(plasmaModule.recipe);
			if(recipe != null) this.maxPower = recipe.power * 100;
			
			this.maxPower = BobMathUtil.max(this.power, this.maxPower, 100_000);
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(worldObj, pos);
				if(inputTank.getTankType() != Fluids.NONE) this.trySubscribe(inputTank.getTankType(), worldObj, pos);
			}

			double speed = 1D;
			double pow = 1D;
			
			this.plasmaModule.update(speed, pow, true, slots[1]);
			this.didProcess = this.plasmaModule.didProcess;
			if(this.plasmaModule.markDirty) this.markDirty();
			
			this.networkPackNT(100);
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 6 - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 6 - rot.offsetZ * 2, dir),
				new DirPos(xCoord + dir.offsetX * 6 - rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 6 - rot.offsetZ * 1, dir),
				new DirPos(xCoord + dir.offsetX * 6 + rot.offsetX * 0, yCoord, zCoord + dir.offsetZ * 6 + rot.offsetZ * 0, dir),
				new DirPos(xCoord + dir.offsetX * 6 + rot.offsetX * 1, yCoord, zCoord + dir.offsetZ * 6 + rot.offsetZ * 1, dir),
				new DirPos(xCoord + dir.offsetX * 6 + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 6 + rot.offsetZ * 2, dir),
				new DirPos(xCoord - dir.offsetX * 6 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 6 - rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 6 - rot.offsetX * 1, yCoord, zCoord - dir.offsetZ * 6 - rot.offsetZ * 1, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 6 + rot.offsetX * 0, yCoord, zCoord - dir.offsetZ * 6 + rot.offsetZ * 0, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 6 + rot.offsetX * 1, yCoord, zCoord - dir.offsetZ * 6 + rot.offsetZ * 1, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 6 + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 6 + rot.offsetZ * 2, dir.getOpposite()),
		};
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		this.inputTank.serialize(buf);
		buf.writeLong(power);
		buf.writeLong(maxPower);
		buf.writeBoolean(didProcess);
		this.plasmaModule.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		boolean wasProcessing = this.didProcess;
		this.inputTank.deserialize(buf);
		this.power = buf.readLong();
		this.maxPower = buf.readLong();
		this.didProcess = buf.readBoolean();
		this.plasmaModule.deserialize(buf);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.inputTank.readFromNBT(nbt, "i");
		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.plasmaModule.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		this.inputTank.writeToNBT(nbt, "i");
		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		this.plasmaModule.writeToNBT(nbt);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return true; // battery
		if(slot == 1 && stack.getItem() == ModItems.blueprints) return true;
		// TODO booster material
		if(this.plasmaModule.isItemValid(slot, stack)) return true; // recipe input crap
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 15 || this.plasmaModule.isSlotClogged(i);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
	}

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }

	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {inputTank}; }
	@Override public FluidTank[] getAllTanks() { return new FluidTank[] {inputTank}; }
	
	@Override public boolean hasPermission(EntityPlayer player) { return this.isUseableByPlayer(player); }

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("index") && data.hasKey("selection")) {
			int index = data.getInteger("index");
			String selection = data.getString("selection");
			if(index == 0) {
				this.plasmaModule.recipe = selection;
				this.markChanged();
			}
		}
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) bb = AxisAlignedBB.getBoundingBox(xCoord - 5, yCoord, zCoord - 5, xCoord + 5, yCoord + 6, zCoord + 6);
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachinePlasmaForge(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachinePlasmaForge(player.inventory, this); }
}
