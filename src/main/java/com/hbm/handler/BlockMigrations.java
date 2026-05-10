package com.hbm.handler;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkDataEvent;

public class BlockMigrations {
	
	private static final String NBT_KEY_BUILD_NUMBER = "hfr_migrations_version";
	private static int buildNumber = -1;
	
	public static int buildNumber() {
		if(buildNumber != -1) return buildNumber;
		String versionString = RefStrings.VERSION.substring(RefStrings.VERSION.indexOf('(') + 1, RefStrings.VERSION.indexOf(')'));
		try {
			buildNumber = Integer.parseInt(versionString);
		} catch(Exception ex) { }
		return buildNumber;
	}

	@SubscribeEvent
	public void onChunkLoad(ChunkDataEvent.Load event) {
		// if no build number for migrations has been found, we assume this is a freshly generated chunk, so we skip the migrations.
		// side effect is that migrations will not work when the starting version is one from before the current migrations system.
		// we are in this for the long game anyway, so i'd wager this isn't that big of an issue.
		if(!event.getData().hasKey(NBT_KEY_BUILD_NUMBER)) return;
		int prevBuildNo = event.getData().getInteger(NBT_KEY_BUILD_NUMBER);
		
		if(prevBuildNo != buildNumber) try { doMigraion(event.getChunk()); } catch(Exception ex) { }
	}
	
	@SubscribeEvent
	public void onChunkSave(ChunkDataEvent.Save event) {
		event.getData().setInteger(NBT_KEY_BUILD_NUMBER, buildNumber);
	}
	
	public static void doMigraion(Chunk chunk) {
		
		for(int x = 0; x < 16; x++) for(int z = 0; z < 16; z++) {
			// save ourselves a ton of iterations by optimizing all this air away
			for(int y = chunk.getHeightValue(x, z); y >= 0; y++) {
				
			}
		}
	}
}
