package com.hbm.dim.trait;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class CBT_Bees extends CelestialBodyTrait {
	
	//amount of bees in a dimension (1 == x100 bees)
	public float bees;

	public CBT_Bees() {}

	public CBT_Bees(float bees) {
		this.bees = bees;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setFloat("bees", bees);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		bees = nbt.getFloat("bees");
	}

	@Override
	public void writeToBytes(ByteBuf buf) {
		buf.writeFloat(bees);
	}

	@Override
	public void readFromBytes(ByteBuf buf) {
		bees = buf.readFloat();
	}

}
