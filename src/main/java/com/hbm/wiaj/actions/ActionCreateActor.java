package com.hbm.wiaj.actions;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;
import com.hbm.wiaj.actors.ISpecialActor;

public class ActionCreateActor implements IJarAction {
	
	int id;
	ISpecialActor actor;
	
	public ActionCreateActor(int id, ISpecialActor actor) {
		this.id = id;
		this.actor = actor;
	}

	@Override
	public int getDuration() {
		return 0;
	}

	@Override
	public void act(WorldInAJar world, JarScene scene) {
		scene.script.actors.put(id, actor);
	}
}
