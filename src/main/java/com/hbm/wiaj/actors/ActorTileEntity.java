package com.hbm.wiaj.actors;

import com.hbm.wiaj.JarScene;

import net.minecraft.nbt.NBTTagCompound;

public class ActorTileEntity extends ActorBase {
	
	ITileActorRenderer renderer;
	
	public ActorTileEntity(ITileActorRenderer renderer) {
		this.renderer = renderer;
	}
	
	public ActorTileEntity(ITileActorRenderer renderer, NBTTagCompound data) {
		this(renderer);
		this.data = data;
	}

	@Override
	public void drawForegroundComponent(int w, int h, int ticks, float interp) { }

	@Override
	public void drawBackgroundComponent(int ticks, float interp) {
		renderer.renderActor(ticks, interp, data);
	}

	@Override
	public void updateActor(JarScene scene) {
		renderer.updateActor(scene.script.ticksElapsed, data);
	}
}
