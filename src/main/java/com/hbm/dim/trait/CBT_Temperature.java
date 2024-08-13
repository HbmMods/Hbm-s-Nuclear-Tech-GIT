package com.hbm.dim.trait;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class CBT_Temperature extends CelestialBodyTrait {
	
	public float degrees; // In SI units (Celsius)
	
	public CBT_Temperature() {
		this.degrees = 20;
	}

	public CBT_Temperature(float degrees) {
		this.degrees = degrees;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("c", degrees);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		degrees = nbt.getFloat("c");
	}

	@Override
	public void writeToBytes(ByteBuf buf) {
		buf.writeFloat(degrees);
	}

	@Override
	public void readFromBytes(ByteBuf buf) {
		degrees = buf.readFloat();
	}

}
