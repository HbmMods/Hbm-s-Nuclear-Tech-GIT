package com.hbm.tileentity.machine.fusion;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerFusionTorus;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIFusionTorus;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.module.machine.ModuleMachineFusion;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.machine.albion.TileEntityCooledBase;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.INetworkProvider;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.KlystronNetworkProvider;
import com.hbm.uninos.networkproviders.PlasmaNetworkProvider;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

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

public class TileEntityFusionTorus extends TileEntityCooledBase implements IGUIProvider, IControlReceiver {

	public boolean didProcess = false;
	
	public FluidTank[] tanks;
	public ModuleMachineFusion fusionModule;

	protected GenNode[] klystronNodes;
	protected GenNode[] plasmaNodes;
	
	public TileEntityFusionTorus() {
		super(3);

		klystronNodes = new GenNode[4];
		plasmaNodes = new GenNode[4];
		
		this.tanks = new FluidTank[4];

		this.tanks[0] = new FluidTank(Fluids.NONE, 4_000);
		this.tanks[1] = new FluidTank(Fluids.NONE, 4_000);
		this.tanks[2] = new FluidTank(Fluids.NONE, 4_000);
		this.tanks[3] = new FluidTank(Fluids.NONE, 4_000);
		
		this.fusionModule = new ModuleMachineFusion(0, this, slots)
				.fluidInput(tanks[0], tanks[1], tanks[2])
				.fluidOutput(tanks[3])
				.itemOutput(2);
	}

	@Override
	public String getName() {
		return "container.fusionTorus";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {

			for(int i = 0; i < 4; i++) {
				if(klystronNodes[i] == null || klystronNodes[i].expired) klystronNodes[i] = createNode(KlystronNetworkProvider.THE_PROVIDER, ForgeDirection.getOrientation(i + 2));
				if(plasmaNodes[i] == null || plasmaNodes[i].expired) plasmaNodes[i] = createNode(PlasmaNetworkProvider.THE_PROVIDER, ForgeDirection.getOrientation(i + 2));

				if(klystronNodes[i].net != null) klystronNodes[i].net.addReceiver(this);
				if(plasmaNodes[i].net != null) plasmaNodes[i].net.addProvider(this);
			}

			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
			
			this.fusionModule.update(1D, 1D, true, slots[1]);
			this.didProcess = this.fusionModule.didProcess;
			if(this.fusionModule.markDirty) this.markDirty();
			
			this.networkPackNT(150);
		}
	}
	
	public GenNode createNode(INetworkProvider provider, ForgeDirection dir) {
		GenNode node = UniNodespace.getNode(worldObj, xCoord + dir.offsetX * 7, yCoord + 2, zCoord + dir.offsetZ * 7, provider);
		if(node != null) return node;
		
		node = new GenNode(provider,
				new BlockPos(xCoord + dir.offsetX * 7, yCoord + 2, zCoord + dir.offsetZ * 7))
				.setConnections(new DirPos(xCoord + dir.offsetX * 8, yCoord + 2, zCoord + dir.offsetZ * 8, dir));
		
		UniNodespace.createNode(worldObj, node);
		
		return node;
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(this.didProcess);
		this.fusionModule.serialize(buf);
		for(int i = 0; i < 4; i++) this.tanks[i].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.didProcess = buf.readBoolean();
		this.fusionModule.deserialize(buf);
		for(int i = 0; i < 4; i++) this.tanks[i].deserialize(buf);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		for(int i = 0; i < 4; i++) this.tanks[i].readFromNBT(nbt, "t" + i);

		this.power = nbt.getLong("power");
		this.fusionModule.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		for(int i = 0; i < 4; i++) this.tanks[i].writeToNBT(nbt, "t" + i);

		nbt.setLong("power", power);
		this.fusionModule.writeToNBT(nbt);
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}
	
	/** Linearly scales up from 0% to 100% from 0 to 0.5, then stays at 100% */
	public static double getSpeedScaled(double max, double level) {
		if(level >= max * 0.5) return 1D;
		return level / max * 2D;
	}

	@Override
	public DirPos[] getConPos() {
		return new DirPos[0]; // TBI
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return true; // battery
		if(slot == 1 && stack.getItem() == ModItems.blueprints) return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 2;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] {2};
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 8,
					yCoord,
					zCoord - 8,
					xCoord + 9,
					yCoord + 5,
					zCoord + 9
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFusionTorus(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFusionTorus(player.inventory, this);
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) return false;
		return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 32 * 32;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("index") && data.hasKey("selection")) {
			int index = data.getInteger("index");
			String selection = data.getString("selection");
			if(index == 0) {
				this.fusionModule.recipe = selection;
				this.markChanged();
			}
		}
	}
}
