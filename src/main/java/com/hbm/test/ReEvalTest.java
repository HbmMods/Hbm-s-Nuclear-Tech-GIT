package com.hbm.test;

import java.util.HashMap;

import com.hbm.main.MainRegistry;

import api.hbm.energy.IEnergyConductor;

public class ReEvalTest {

	/**
	 * Runs a collision test on a relatively large scale. So large in fact that it will most certainly OOM.
	 * Not an issue, since by that point we will already have our results.
	 * @throws OutOfMemoryError
	 */
	public static void runTest() throws OutOfMemoryError {
		
		HashMap<Integer, int[]> collisions = new HashMap();
		
		int minX = -130;
		int maxX = 140;
		int minZ = 300;
		int maxZ = 520;
		
		MainRegistry.logger.info("Starting collision test...");

		for(int x = minX; x <= maxX; x++) {
			for(int y = 1; y <= 255; y++) {
				for(int z = minZ; z <= maxZ; z++) {
					int identity = IEnergyConductor.getIdentityFromPos(x, y, z);
					
					if(collisions.containsKey(identity)) {
						int[] collision = collisions.get(identity);
						MainRegistry.logger.info("Position " + x + "/" + y + "/" + z + " collides with " + collision[0] + "/" + collision[1] + "/" + collision[2] + "!");
					} else {
						collisions.put(identity, new int[] {x, y, z});
					}
				}
			}
		}
		
		MainRegistry.logger.info("Collision test complete!");
	}
}
