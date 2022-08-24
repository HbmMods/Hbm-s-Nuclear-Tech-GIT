package com.hbm.wiaj.actors;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileActorRenderer {

	public void renderActor(int ticks, float interp, NBTTagCompound data);
	public void updateActor(int ticks, NBTTagCompound data);
}
