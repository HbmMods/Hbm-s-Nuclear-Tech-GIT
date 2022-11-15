package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import api.hbm.fluid.IFluidStandardTransceiver;
import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Coolable;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineLargeTurbine extends TileEntityMachineBase implements IFluidContainer, IFluidAcceptor, IFluidSource, IEnergyGenerator, IFluidStandardTransceiver {

	public long power;
	public static final long maxPower = 100000000;
	public int age = 0;
	public List<IFluidAcceptor> list2 = new ArrayList();
	public FluidTank[] tanks;
	
	private boolean shouldTurn;
	public float rotor;
	public float lastRotor;
	public float fanAcceleration = 0F;
	
	public TileEntityMachineLargeTurbine() {
		super(7);
		
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.STEAM, 512000, 0);
		tanks[1] = new FluidTank(Fluids.SPENTSTEAM, 10240000, 1);
	}

	@Override
	public String getName() {
		return "container.machineLargeTurbine";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			age++;
			if(age >= 2)
			{
				age = 0;
			}
			
			fillFluidInit(tanks[1].getTankType());
			
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
			this.sendPower(worldObj, xCoord + dir.offsetX * -4, yCoord, zCoord + dir.offsetZ * -4, dir.getOpposite());
			for(DirPos pos : getConPos()) this.sendFluid(tanks[1].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());

			tanks[0].setType(0, 1, slots);
			tanks[0].loadTank(2, 3, slots);
			power = Library.chargeItemsFromTE(slots, 4, power, maxPower);
			
			boolean operational = false;
			
			FluidType in = tanks[0].getTankType();
			boolean valid = false;
			if(in.hasTrait(FT_Coolable.class)) {
				FT_Coolable trait = in.getTrait(FT_Coolable.class);
				double eff = trait.getEfficiency(CoolingType.TURBINE); //100% efficiency
				if(eff > 0) {
					tanks[1].setTankType(trait.coolsTo);
					int inputOps = (int) Math.floor(tanks[0].getFill() / trait.amountReq); //amount of cycles possible with the entire input buffer
					int outputOps = (tanks[1].getMaxFill() - tanks[1].getFill()) / trait.amountProduced; //amount of cycles possible with the output buffer's remaining space
					int cap = (int) Math.ceil(tanks[0].getFill() / trait.amountReq / 5F); //amount of cycles by the "at least 20%" rule
					int ops = Math.min(inputOps, Math.min(outputOps, cap)); //defacto amount of cycles
					tanks[0].setFill(tanks[0].getFill() - ops * trait.amountReq);
					tanks[1].setFill(tanks[1].getFill() + ops * trait.amountProduced);
					this.power += (ops * trait.heatEnergy * eff);
					valid = true;
					operational = ops > 0;
				}
			}
			if(!valid) tanks[1].setTankType(Fluids.NONE);
			if(power > maxPower) power = maxPower;
			
			tanks[1].unloadTank(5, 6, slots);
			
			for(int i = 0; i < 2; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setBoolean("operational", operational);
			this.networkPack(data, 50);
		} else {
			this.lastRotor = this.rotor;
			this.rotor += this.fanAcceleration;
				
			if(this.rotor >= 360) {
				this.rotor -= 360;
				this.lastRotor -= 360;
			}
			
			if(shouldTurn) {

				this.fanAcceleration = Math.max(0F, Math.min(15F, this.fanAcceleration += 0.1F));
			}
			if(!shouldTurn) {
				this.fanAcceleration = Math.max(0F, Math.min(15F, this.fanAcceleration -= 0.1F));
			}
		}
	}
	
	protected DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + rot.offsetX * 2, yCoord, zCoord + rot.offsetZ * 2, rot),
				new DirPos(xCoord - rot.offsetX * 2, yCoord, zCoord - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2, dir)
		};
	}
	
	public void networkUnpack(NBTTagCompound data) {
		this.power = data.getLong("power");
		this.shouldTurn = data.getBoolean("operational");
	}
	
	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
		power = nbt.getLong("power");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "steam");
		nbt.setLong("power", power);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		dir = dir.getRotation(ForgeDirection.UP);

		fillFluid(xCoord + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2, getTact(), type);
		fillFluid(xCoord + dir.offsetX * -2, yCoord, zCoord + dir.offsetZ * -2, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		if(age == 0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list2;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		list2.clear();
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public FluidTank[] getAllTanks() {
		return tanks;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] {tanks[1]};
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tanks[0]};
	}
}
