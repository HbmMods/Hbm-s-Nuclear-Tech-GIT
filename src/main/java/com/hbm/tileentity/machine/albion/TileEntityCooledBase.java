package com.hbm.tileentity.machine.albion;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileEntityCooledBase extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiver {
	
	public FluidTank[] tanks;
	
	public long power;
	
	public static final float KELVIN = 273F;
	public float temperature = KELVIN + 20;
	public static final float temperature_target = KELVIN - 150F;
	public static final float temp_change_per_mb = 0.5F;
	public static final float temp_passive_heating = 2.5F;
	public static final float temp_change_max = 5F + temp_passive_heating;

	public TileEntityCooledBase(int slotCount) {
		super(slotCount);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.PERFLUOROMETHYL_COLD, 4_000);
		tanks[1] = new FluidTank(Fluids.PERFLUOROMETHYL, 4_000);
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			for(DirPos pos : this.getConPos()) {
				this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
				this.sendFluid(tanks[1], worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			}
			
			this.temperature += this.temp_passive_heating;
			if(this.temperature > KELVIN + 20) this.temperature = KELVIN + 20;
			
			if(this.temperature > this.temperature_target) {
				int cyclesTemp = (int) Math.ceil((Math.min(this.temperature - temperature_target, temp_change_max)) / temp_change_per_mb);
				int cyclesCool = tanks[0].getFill();
				int cyclesHot = tanks[1].getMaxFill() - tanks[1].getFill();
				int cycles = BobMathUtil.min(cyclesTemp, cyclesCool, cyclesHot);

				tanks[0].setFill(tanks[0].getFill() - cycles);
				tanks[1].setFill(tanks[1].getFill() + cycles);
				this.temperature -= this.temp_change_per_mb * cycles;
			}
			
			this.networkPackNT(50);
		}
	}
	
	public boolean isCool() {
		return this.temperature <= this.temperature_target;
	}
	
	public abstract DirPos[] getConPos();

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		tanks[0].serialize(buf);
		tanks[1].serialize(buf);
		buf.writeFloat(temperature);
		buf.writeLong(power);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		tanks[0].deserialize(buf);
		tanks[1].deserialize(buf);
		this.temperature = buf.readFloat();
		this.power = buf.readLong();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		tanks[0].readFromNBT(nbt, "t0");
		tanks[1].readFromNBT(nbt, "t1");
		this.temperature = nbt.getFloat("temperature");
		this.power = nbt.getLong("power");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		tanks[0].writeToNBT(nbt, "t0");
		tanks[1].writeToNBT(nbt, "t1");
		nbt.setFloat("temperature", temperature);
		nbt.setLong("power", power);
	}
	
	@Override public long getPower() { return this.power; }
	@Override public void setPower(long power) { this.power = power; }

	@Override public FluidTank[] getSendingTanks() { return new FluidTank[] {tanks[1]}; }
	@Override public FluidTank[] getReceivingTanks() { return new FluidTank[] {tanks[0]}; }
	@Override public FluidTank[] getAllTanks() { return tanks; }
}
