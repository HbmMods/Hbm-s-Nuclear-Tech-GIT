package com.hbm.wiaj.actors;

import com.hbm.wiaj.JarScene;

public interface ISpecialActor {

	public void draw();
	public void updateActor(JarScene scene);
	public void setActorData(Object... data);
}
