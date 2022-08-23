package com.hbm.wiaj.actions;

import com.hbm.wiaj.JarScene;
import com.hbm.wiaj.WorldInAJar;

public interface IJarAction {

	/** Time taken by this action in ticks */
	public int getDuration();
	/** Perform this action */
	public void act(WorldInAJar world, JarScene scene);
}
