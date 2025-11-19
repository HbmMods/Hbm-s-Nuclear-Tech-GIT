package com.hbm.tileentity.machine.fusion;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.container.ContainerFusionBreeder;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIFusionBreeder;
import com.hbm.inventory.recipes.OutgasserRecipes;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.uninos.GenNode;
import com.hbm.uninos.UniNodespace;
import com.hbm.uninos.networkproviders.PlasmaNetworkProvider;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
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

public class TileEntityFusionBreeder extends TileEntityMachineBase implements IFluidStandardTransceiverMK2, IFusionPowerReceiver, IGUIProvider {

	protected GenNode plasmaNode;
	
	public FluidTank[] tanks;

	public double neutronEnergy;
	public double neutronEnergySync;
	public double progress;
	public static final double capacity = 10_000D;

	public TileEntityFusionBreeder() {
		super(3);
		
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.NONE, 16_000);
		tanks[1] = new FluidTank(Fluids.NONE, 16_000);
	}

	@Override
	public String getName() {
		return "container.fusionBreeder";
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			if(!canProcessSolid()) {
				this.progress = 0;
			}
			
			// because tile updates may happen in any order and the value that needs
			// to be synced needs to persist until the next tick due to the batched packets
			this.neutronEnergySync = this.neutronEnergy;
			
			for(DirPos pos : getConPos()) {
				if(tanks[0].getTankType() != Fluids.NONE) this.trySubscribe(tanks[0].getTankType(), worldObj, pos);
				if(tanks[1].getFill() > 0) this.tryProvide(tanks[1], worldObj, pos);
			}
			
			if(plasmaNode == null || plasmaNode.expired) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10).getOpposite();
				plasmaNode = UniNodespace.getNode(worldObj, xCoord + dir.offsetX * 2, yCoord + 2, zCoord + dir.offsetZ * 2, PlasmaNetworkProvider.THE_PROVIDER);
				
				if(plasmaNode == null) {
					plasmaNode = new GenNode(PlasmaNetworkProvider.THE_PROVIDER,
							new BlockPos(xCoord + dir.offsetX * 2, yCoord + 2, zCoord + dir.offsetZ * 2))
							.setConnections(new DirPos(xCoord + dir.offsetX * 3, yCoord + 2, zCoord + dir.offsetZ * 3, dir));
					
					UniNodespace.createNode(worldObj, plasmaNode);
				}
			}
			
			if(plasmaNode != null && plasmaNode.hasValidNet()) plasmaNode.net.addReceiver(this);
			
			this.networkPackNT(25);
			
			this.neutronEnergy = 0;
		}
	}

	public boolean canProcessSolid() {
		if(slots[1] == null) return false;

		Pair<ItemStack, FluidStack> output = OutgasserRecipes.getOutput(slots[1]);
		if(output == null) return false;

		FluidStack fluid = output.getValue();

		if(fluid != null) {
			if(tanks[1].getTankType() != fluid.type && tanks[1].getFill() > 0) return false;
			tanks[1].setTankType(fluid.type);
			if(tanks[1].getFill() + fluid.fill > tanks[1].getMaxFill()) return false;
		}

		ItemStack out = output.getKey();
		if(slots[2] == null || out == null) return true;

		return slots[2].getItem() == out.getItem() && slots[2].getItemDamage() == out.getItemDamage() && slots[2].stackSize + out.stackSize <= slots[2].getMaxStackSize();
	}

	private void processSolid() {

		Pair<ItemStack, FluidStack> output = OutgasserRecipes.getOutput(slots[1]);
		this.decrStackSize(1, 1);
		this.progress = 0;

		if(output.getValue() != null) {
			tanks[1].setFill(tanks[1].getFill() + output.getValue().fill);
		}

		ItemStack out = output.getKey();

		if(out != null) {
			if(slots[2] == null) {
				slots[2] = out.copy();
			} else {
				slots[2].stackSize += out.stackSize;
			}
		}
	}
	
	public void doProgress() {

		if(canProcessSolid()) {
			this.progress += this.neutronEnergy;
			if(progress > capacity) {
				processSolid();
				progress = 0;
				this.markDirty();
			}
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot == 0) return stack.getItem() instanceof IItemFluidIdentifier; // fluid ID
		if(slot == 1) return OutgasserRecipes.getOutput(stack) != null; // input
		return false;
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 3, yCoord + 2, zCoord + dir.offsetZ * 3, dir),
				new DirPos(xCoord + rot.offsetX * 3, yCoord, zCoord + rot.offsetZ * 3, rot),
				new DirPos(xCoord - rot.offsetX * 3, yCoord, zCoord - rot.offsetZ * 3, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX + rot.offsetX * 3, yCoord, zCoord + dir.offsetX + rot.offsetZ * 3, rot),
				new DirPos(xCoord - dir.offsetX - rot.offsetX * 3, yCoord, zCoord - dir.offsetX - rot.offsetZ * 3, rot.getOpposite())
		};
	}

	@Override
	public void invalidate() {
		super.invalidate();

		if(!worldObj.isRemote) {
			if(this.plasmaNode != null) UniNodespace.destroyNode(worldObj, plasmaNode);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeDouble(neutronEnergySync);
		buf.writeDouble(progress);
		
		this.tanks[0].serialize(buf);
		this.tanks[1].serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.neutronEnergy = buf.readDouble();
		this.progress = buf.readDouble();
		
		this.tanks[0].deserialize(buf);
		this.tanks[1].deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.progress = nbt.getDouble("progress");
		this.tanks[0].readFromNBT(nbt, "t0");
		this.tanks[1].readFromNBT(nbt, "t1");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("progress", this.progress);
		this.tanks[0].writeToNBT(nbt, "t0");
		this.tanks[1].writeToNBT(nbt, "t1");
	}

	@Override public boolean receivesFusionPower() { return false; }
	@Override public void receiveFusionPower(long fusionPower, double neutronPower) { this.neutronEnergy = neutronPower; doProgress(); }

	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tanks[0]}; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {tanks[1]}; }
	@Override public FluidTank[] getAllTanks() { return tanks; }

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFusionBreeder(player.inventory, this);
	}

	@Override
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFusionBreeder(player.inventory, this);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 2,
					yCoord,
					zCoord - 2,
					xCoord + 3,
					yCoord + 4,
					zCoord + 3
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
