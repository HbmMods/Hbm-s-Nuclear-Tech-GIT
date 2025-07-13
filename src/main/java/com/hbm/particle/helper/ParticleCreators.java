package com.hbm.particle.helper;

import java.util.HashMap;

public class ParticleCreators {
	
	public static HashMap<String, IParticleCreator> particleCreators = new HashMap();
	
	static {
		particleCreators.put("explosionLarge", new ExplosionCreator());
		particleCreators.put("casingNT", new CasingCreator());
		particleCreators.put("flamethrower", new FlameCreator());
		particleCreators.put("explosionSmall", new ExplosionSmallCreator());
		particleCreators.put("blackPowder", new BlackPowderCreator());
		particleCreators.put("ashes", new AshesCreator());
		particleCreators.put("skeleton", new SkeletonCreator());
	}
}
