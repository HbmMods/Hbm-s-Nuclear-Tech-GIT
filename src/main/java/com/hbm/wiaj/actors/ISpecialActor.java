package com.hbm.wiaj.actors;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Actors, anything that can receive ticks (for rendering movement for example) and renders on screen
 * Can be tile entity models, faux entities or tooltips
 * @author hbm
 */
public interface ISpecialActor {

	/** Draws things in the foreground like text boxes */
	public void drawForegroundComponent(int w, int h, int ticks, float interp);
	/** Draws things in the background, fitted to the world renderer like TESRs */
	public void drawBackgroundComponent(WorldInAJar world, int ticks, float interp);
	/** Update ticks to emulate serverside ticking */
	public void updateActor(JarScene scene);
	/** Sets the data object to the passed NBT */
	public void setActorData(NBTTagCompound data);
	/** Auto-detects the passed object's type and sets the specified NBT tag */
	public void setDataPoint(String tag, Object o);
}
