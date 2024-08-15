package com.hbm.tileentity.machine.oil;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.AlkylationRecipes;
import com.hbm.tileentity.IPersistentNBT;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Triplet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineAlkylation extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiver, IPersistentNBT {
	
	public long power;
	public static final long maxPower = 1_000_000;

	public FluidTank[] tanks;

	public TileEntityMachineAlkylation() {
		super(11);
		
		this.tanks = new FluidTank[4];
		this.tanks[0] = new FluidTank(Fluids.CHLOROMETHANE, 8_000);
		this.tanks[1] = new FluidTank(Fluids.NONE, 4_000);
		this.tanks[2] = new FluidTank(Fluids.UNSATURATEDS, 8_000);
		this.tanks[3] = new FluidTank(Fluids.CHLORINE, 8_000);
	}

	@Override
	public String getName() {
		return "container.alkylation";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			if(this.worldObj.getTotalWorldTime() % 10 == 0) this.updateConnections();
			
			if(worldObj.getTotalWorldTime() % 2 == 0) alkylate();
			
			this.networkPackNT(25);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeLong(power);
		for(int i = 0; i < tanks.length; i++) tanks[i].serialize(buf);
	}
	
	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.power = buf.readLong();
		for(int i = 0; i < tanks.length; i++) tanks[i].deserialize(buf);
	}
	
	private void alkylate() {
		
		Triplet<FluidStack, FluidStack, FluidStack> out = AlkylationRecipes.getOutput(tanks[0].getTankType());
		if(out == null) {
			tanks[2].setTankType(Fluids.NONE);
			tanks[3].setTankType(Fluids.NONE);
			return;
		}

		tanks[1].setTankType(out.getX().type);
		tanks[2].setTankType(out.getY().type);
		tanks[3].setTankType(out.getZ().type);
		
		if(power < 4_000) return; // 40 kHE/s
		if(tanks[0].getFill() < 100) return;
		if(tanks[1].getFill() < out.getX().fill) return;

		if(tanks[2].getFill() + out.getY().fill > tanks[2].getMaxFill()) return;
		if(tanks[3].getFill() + out.getZ().fill > tanks[3].getMaxFill()) return;

		tanks[0].setFill(tanks[0].getFill() - 100);
		tanks[1].setFill(tanks[1].getFill() - out.getX().fill);
		tanks[2].setFill(tanks[2].getFill() + out.getY().fill);
		tanks[3].setFill(tanks[3].getFill() + out.getZ().fill);
		
		power -= 4_000;
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			if(tanks[2].getFill() > 0) this.sendFluid(tanks[2], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			if(tanks[3].getFill() > 0) this.sendFluid(tanks[3], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		
		return new DirPos[] {
			new DirPos(xCoord + rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2, dir),
			new DirPos(xCoord + rot.offsetX * 2 + dir.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2 + dir.offsetZ * 2, dir),
			new DirPos(xCoord + rot.offsetX * 1 + dir.offsetX * 3, yCoord, zCoord + rot.offsetZ * 1 + dir.offsetZ * 3, dir),
			new DirPos(xCoord + rot.offsetX * 2 - dir.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2 - dir.offsetZ * 2, dir),
			new DirPos(xCoord + rot.offsetX * 1 - dir.offsetX * 3, yCoord, zCoord + rot.offsetZ * 1 - dir.offsetZ * 3, dir),

			new DirPos(xCoord - rot.offsetX * 2, yCoord, zCoord - rot.offsetZ * 2, dir),
			new DirPos(xCoord - rot.offsetX * 2 + dir.offsetX * 2, yCoord, zCoord - rot.offsetZ * 2 + dir.offsetZ * 2, dir),
			new DirPos(xCoord - rot.offsetX * 1 + dir.offsetX * 3, yCoord, zCoord - rot.offsetZ * 1 + dir.offsetZ * 3, dir),
			new DirPos(xCoord - rot.offsetX * 2 - dir.offsetX * 2, yCoord, zCoord - rot.offsetZ * 2 - dir.offsetZ * 2, dir),
			new DirPos(xCoord - rot.offsetX * 1 - dir.offsetX * 3, yCoord, zCoord - rot.offsetZ * 1 - dir.offsetZ * 3, dir),
		};
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		for(int i = 0; i < tanks.length; i++) tanks[i].readFromNBT(nbt, "t" + i);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		for(int i = 0; i < tanks.length; i++) tanks[i].writeToNBT(nbt, "t" + i);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
				xCoord - 3,
				yCoord,
				zCoord - 3,
				xCoord + 3,
				yCoord + 3,
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

	@Override public long getPower() { return power; }
	@Override public void setPower(long power) { this.power = power; }
	@Override public long getMaxPower() { return maxPower; }
	@Override public FluidTank[] getAllTanks() { return tanks; }
	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {tanks[2], tanks[3]}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tanks[0], tanks[1]}; }
	@Override public boolean canConnect(ForgeDirection dir) { return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN; }
	@Override public boolean canConnect(FluidType type, ForgeDirection dir) { return dir != ForgeDirection.UNKNOWN && dir != ForgeDirection.DOWN; }

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		if(tanks[0].getFill() == 0 && tanks[1].getFill() == 0 && tanks[2].getFill() == 0 && tanks[3].getFill() == 0) return;
		NBTTagCompound data = new NBTTagCompound();
		for(int i = 0; i < tanks.length; i++) this.tanks[i].writeToNBT(data, "t" + i);
		nbt.setTag(NBT_PERSISTENT_KEY, data);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound data = nbt.getCompoundTag(NBT_PERSISTENT_KEY);
		for(int i = 0; i < tanks.length; i++) this.tanks[i].readFromNBT(data, "t" + i);
	}
}
