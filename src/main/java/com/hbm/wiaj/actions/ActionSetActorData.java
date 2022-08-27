package com.hbm.wiaj.actions;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

import net.minecraft.nbt.NBTTagCompound;

public class ActionSetActorData implements IJarAction {
	
	int id;
	NBTTagCompound data;
	
	public ActionSetActorData(int id, NBTTagCompound data) {
		this.id = id;
		this.data = data;
	}

	@Override
	public int getDuration() {
		return 0;
	}

	@Override
	public void act(WorldInAJar world, JarScene scene) {
		scene.script.actors.get(id).setActorData((NBTTagCompound) data.copy());
	}
}
