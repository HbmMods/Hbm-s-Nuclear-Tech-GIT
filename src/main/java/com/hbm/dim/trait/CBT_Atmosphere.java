package com.hbm.dim.trait;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class CBT_Atmosphere extends CelestialBodyTrait {
	
	public FluidType fluid;
	public float pressure;

	//just like this class name, it has indeed tortured something
	public CBT_Atmosphere() {}
	
	public CBT_Atmosphere(FluidType fluid, float pressure) {
		this.fluid = fluid;
		this.pressure = pressure;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("type", fluid.getID());
		nbt.setFloat("pressure", pressure);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		fluid = Fluids.fromID(nbt.getInteger("type"));
		pressure = nbt.getFloat("pressure");
	}

	@Override
	public void writeToBytes(ByteBuf buf) {
		buf.writeInt(fluid.getID());
		buf.writeFloat(pressure);
	}

	@Override
	public void readFromBytes(ByteBuf buf) {
		fluid = Fluids.fromID(buf.readInt());
		pressure = buf.readFloat();
	}

}
