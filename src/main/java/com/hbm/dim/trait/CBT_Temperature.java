package com.hbm.dim.trait;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class CBT_Temperature extends CelestialBodyTrait {
	
	public float gregrees;

	public CBT_Temperature() {}

	public CBT_Temperature(float gregrees) {
		this.gregrees = gregrees;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("gregrees", gregrees);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		gregrees = nbt.getFloat("gregrees");
	}

	@Override
	public void writeToBytes(ByteBuf buf) {
		buf.writeFloat(gregrees);
	}

	@Override
	public void readFromBytes(ByteBuf buf) {
		gregrees = buf.readFloat();
	}

}
