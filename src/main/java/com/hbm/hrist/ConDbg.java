package com.hbm.hrist;

import java.util.Map.Entry;

import com.hbm.hrist.ConduitPiece.ConnectionDefinition;
import com.hbm.hrist.ConduitSpace.ConduitWorld;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.world.World;

/** Test and debugging tools */
public class ConDbg {
	
	public static void debugUpdateWorld(World world) {
		
		ConduitWorld cWorld = ConduitSpace.worlds.get(world);
		if(cWorld == null) return;
		
		for(Entry<BlockPos, ConduitPiece> entry : cWorld.pieces.entrySet()) {
			
			for(ConnectionDefinition def : entry.getValue().definitions) {
				ConduitLine line = def.getLine();
				if(line == null) continue;
				
				BlockPos pos = def.connectors[0];
				BlockPos nex = def.connectors[1];
	
				double x = pos.getX();
				double y = pos.getY() + 0.5;
				double z = pos.getZ();
				double dx = nex.getX() - pos.getX();
				double dy = nex.getY() - pos.getY();
				double dz = nex.getZ() - pos.getZ();
				
				int color = line.hashCode() & 0xFFFFFF;
				
				ParticleUtil.spawnDroneLine(world, x, y, z, dx, dy, dz, !line.valid ? 0xff0000 : color, true);
			}
		}
	}
}
