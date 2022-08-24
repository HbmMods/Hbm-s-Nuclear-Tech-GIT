package com.hbm.wiaj.actors;

import com.hbm.wiaj.JarScene;

import net.minecraft.nbt.NBTTagCompound;

public class ActorTileEntity implements ISpecialActor {
	
	ITileActorRenderer renderer;
	NBTTagCompound data = new NBTTagCompound();
	
	public ActorTileEntity(ITileActorRenderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public void draw(int ticks, float interp) {
		renderer.renderActor(ticks, interp, data);
	}

	@Override
	public void updateActor(JarScene scene) {
		renderer.updateActor(scene.script.ticksElapsed, data);
	}

	@Override
	public void setActorData(NBTTagCompound data) {
		this.data = data;
	}

	@Override
	public void setDataPoint(String tag, Object o) {
		if(o instanceof String) this.data.setString(tag, (String) o);
		if(o instanceof Integer) this.data.setInteger(tag, (Integer) o);
		if(o instanceof Float) this.data.setFloat(tag, (Float) o);
		if(o instanceof Double) this.data.setDouble(tag, (Double) o);
		if(o instanceof Boolean) this.data.setBoolean(tag, (Boolean) o);
	}
}
