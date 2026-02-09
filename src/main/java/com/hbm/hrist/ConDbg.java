package com.hbm.hrist;

import java.util.Map.Entry;

import com.hbm.hrist.ConduitSpace.ConduitWorld;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.world.World;

/** Test and debugging tools */
public class ConDbg {

	public static void debugVisualsWorld(World world) {
		
		ConduitWorld cWorld = ConduitSpace.worlds.get(world);
		if(cWorld == null) return;
		
		for(Entry<BlockPos, ConduitPiece> entry : cWorld.pieces.entrySet()) {
			
			BlockPos pos = entry.getValue().definitions[0].connectors[0];
			BlockPos nex = entry.getValue().definitions[0].connectors[1];

			double x = pos.getX();
			double y = pos.getY() + 0.5;
			double z = pos.getZ();
			double dx = nex.getX() - pos.getX();
			double dy = nex.getY() - pos.getY();
			double dz = nex.getZ() - pos.getZ();
			
			ParticleUtil.spawnDroneLine(world, x, y, z, dx, dy, dz, 0xff0000, true);
		}
	}
}
