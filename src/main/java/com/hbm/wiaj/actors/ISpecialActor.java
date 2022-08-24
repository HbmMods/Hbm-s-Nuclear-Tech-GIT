package com.hbm.wiaj.actors;

import com.hbm.wiaj.JarScene;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Actors, anything that can receive ticks (for rendering movement for example) and renders on screen
 * Can be tile entity models, faux entities or tooltips
 * @author hbm
 */
public interface ISpecialActor {

	public void draw(int ticks, float interp);
	public void updateActor(JarScene scene);
	public void setActorData(NBTTagCompound data);
	public void setDataPoint(String tag, Object o);
}
