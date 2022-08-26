package com.hbm.wiaj.actors;

import com.hbm.wiaj.JarScene;

import net.minecraft.nbt.NBTTagCompound;

public class ActorFancyPanel implements ISpecialActor {
	
	public ActorFancyPanel() {
		
	}

	@Override
	public void drawForegroundComponent(int w, int h, int ticks, float interp) { }

	@Override
	public void drawBackgroundComponent(int ticks, float interp) { }

	@Override
	public void updateActor(JarScene scene) { }

	@Override
	public void setActorData(NBTTagCompound data) { }

	@Override
	public void setDataPoint(String tag, Object o) { }
	
	/** where the arrow should be or if the box should be centered around the home position */
	public static enum Orientation {
		TOP,
		BOTTOM,
		LEFT,
		RIGHT,
		CENTER
	}
}
