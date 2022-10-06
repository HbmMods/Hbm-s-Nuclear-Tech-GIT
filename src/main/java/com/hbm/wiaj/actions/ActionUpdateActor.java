package com.hbm.wiaj.actions;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

public class ActionUpdateActor implements IJarAction {
	
	int id;
	String key;
	Object data;
	
	public ActionUpdateActor(int id, String key, Object data) {
		this.id = id;
		this.key = key;
		this.data = data;
	}

	@Override
	public int getDuration() {
		return 0;
	}

	@Override
	public void act(WorldInAJar world, JarScene scene) {
		scene.script.actors.get(id).setDataPoint(key, data);
	}
}
