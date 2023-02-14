package com.hbm.handler;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ModEventHandlerRogue;
import com.hbm.saveddata.RogueWorldSaveData;
//import com.hbm.saveddata.TomSaveData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockVine;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

public class RogueWorldHandler {

	public static void frostEffects(World world) {

		if(!(world instanceof WorldServer))
			return;

		if(world.provider.dimensionId != 0) {
			return;
		}

		WorldServer serv = (WorldServer) world;

		List<Chunk> list = serv.theChunkProviderServer.loadedChunks;
		int listSize = list.size();
		
		if(listSize > 0) {
			for(int i = 0; i < 3; i++) {
				
				Chunk chunk = list.get(serv.rand.nextInt(listSize));
				ChunkCoordIntPair coord = chunk.getChunkCoordIntPair();
				
				for(int x = 0; x < 16; x++) {
					for(int z = 0; z < 16; z++) {
						
						int X = coord.getCenterXPos() - 8 + x;
						int Z = coord.getCenterZPosition() - 8 + z;
						int Y = world.getHeightValue(X, Z) - world.rand.nextInt(Math.max(1, world.getHeightValue(X, Z)));

						RogueWorldSaveData data = RogueWorldSaveData.forWorld(world);
						float temp = ModEventHandlerRogue.getTemperatureAtDepth(Y, world);
						//if(temp < 0) {
							freeze(world, X, Y, Z, temp);
						//}
					}
				}
			}
		}
	}

	public static void freeze(World world, int x, int y, int z, float temp) {
		Block b = world.getBlock(x, y, z);
		if(temp<0)
		{
			if(b == Blocks.water) {
				world.setBlock(x, y, z, ModBlocks.cold_ice);
			}
		}
		if(temp<-20)
		{
			if(b == Blocks.grass) {
				world.setBlock(x, y, z, ModBlocks.waste_earth);
			} else if(b instanceof BlockBush) {
				world.setBlock(x, y, z, ModBlocks.plant_dead);
			}
		}
		if(temp<-60)
		{
			if(b == Blocks.grass) {
				world.setBlock(x, y, z, ModBlocks.frozen_grass);
			} else if(b instanceof BlockBush) {
				world.setBlock(x, y, z, Blocks.air);
			} else if(b instanceof BlockLog) {
				int meta = world.getBlockMetadata(x, y, z);
				world.setBlock(x, y, z, ModBlocks.frozen_log, meta, 2);
			} else if(b instanceof BlockLeaves) {
				world.setBlock(x, y, z, ModBlocks.frozen_leaves);
			} else if(b == Blocks.water) {
				world.setBlock(x, y, z, ModBlocks.cold_ice);
			} else if(b == Blocks.ice) {
				world.setBlock(x, y, z, ModBlocks.cold_ice);
			} else if(b == Blocks.dirt) {
				world.setBlock(x, y, z, ModBlocks.frozen_dirt);
			} else if(b == Blocks.gravel) {
				world.setBlock(x, y, z, ModBlocks.frozen_gravel);
			} else if(b == Blocks.farmland) {
				world.setBlock(x, y, z, ModBlocks.frozen_farmland);
			} else if(b == Blocks.planks) {
				world.setBlock(x, y, z, ModBlocks.frozen_planks);
			}	
		}
	}	


	public static World lastSyncWorld = null;
	public static float distance = 1F;
	public static float atmosphere = 1F;
	//public static float dust = 0F;
	public static boolean star = false;
	public static boolean rogue = false;

	@SideOnly(Side.CLIENT)
	public static float getDistanceForClient(World world) {
		if(world != lastSyncWorld) return 1.0F;
		return distance;
	}

	@SideOnly(Side.CLIENT)
	public static boolean getStarForClient(World world) {
		if(world != lastSyncWorld) return false;
		return star;
	}

	@SideOnly(Side.CLIENT)
	public static boolean getRogueWorldForClient(World world) {
		if(world != lastSyncWorld) return false;
		return rogue;
	}
	
	@SideOnly(Side.CLIENT)
	public static float getAtmosphereForClient(World world) {
		if(world != lastSyncWorld) return 1.0F;
		return atmosphere;
	}
}