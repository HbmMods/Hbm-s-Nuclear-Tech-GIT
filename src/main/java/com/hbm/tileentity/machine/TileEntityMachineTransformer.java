package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import api.hbm.energy.IEnergyConsumer;
import api.hbm.energy.IEnergySource;
import com.hbm.lib.Library;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMachineTransformer extends TileEntity implements IEnergySource, IEnergyConsumer {

	public long power;
	public long maxPower = 10000;
	public int delay = 1;
	public List<IEnergyConsumer> list = new ArrayList();
	boolean tact;
	int age;
	
	public TileEntityMachineTransformer() { }
	
	public TileEntityMachineTransformer(long buffer, int d) {
		maxPower = buffer;
		delay = d;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("powerTime");
		this.maxPower = nbt.getLong("maxPower");
		this.delay = nbt.getInteger("delay");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", power);
		nbt.setLong("maxPower", maxPower);
		nbt.setInteger("delay", delay);
	}


	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			age++;
			
			if(age == delay) {
				
				maxPower /= (20D / delay);
				long saved = 0;
				
				if(power > maxPower) {
					saved = power - maxPower;
					power = maxPower;
				}
				
				tact = true;
				ffgeuaInit();
				tact = false;
				ffgeuaInit();
				
				age = 0;
				
				maxPower *= (20D / delay);
				
				power += saved;
			}
		}
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord + 1, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord - 1, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IEnergyConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}

	@Override
	public boolean getTact() {
		return this.tact;
	}

	@Override
	public void setPower(long i) {
		power = i;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
}
