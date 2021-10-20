package com.hbm.handler;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ModEventHandler;
import com.hbm.saveddata.TomSaveData;
import com.hbm.world.WorldProviderNTM;

import net.minecraft.block.Block;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.util.ForgeDirection;

public class ImpactWorldHandler {
	
	public static void impactEffects(World world) {
		
		if(!(world instanceof WorldServer))
			return;
		
		if(!(world.provider.dimensionId == 0))
		{
			return;
		}
		
		WorldServer serv = (WorldServer)world;
		
		ChunkProviderServer provider = (ChunkProviderServer) serv.getChunkProvider();		
		Random rand = new Random();
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
						int Y = Math.max(1,world.getHeightValue(X, Z) - world.rand.nextInt(world.getHeightValue(X, Z)));
						int Y2 = world.getHeightValue(X, Z) - world.rand.nextInt(2);

						die(world, X, Y, Z);
						if(TomSaveData.fire>0 || ModEventHandler.fire >0)
						{
							burn(world, X, Y, Z);
						}
					}
				}
			}
		}
	}
	///Plants die without sufficient light.
	public static void die(World world, int x, int y, int z) {
		int light = Math.max(world.getSavedLightValue(EnumSkyBlock.Block, x, y+1, z),(int)(world.getBlockLightValue(x, y + 1, z)*(1-ModEventHandler.dust)));
		if(world.getBlock(x, y, z) == Blocks.grass) {
			if(light < 4)
			world.setBlock(x, y, z, Blocks.dirt);
		} else if(world.getBlock(x, y, z) instanceof BlockBush) {
			if(light < 4)
			world.setBlock(x, y, z, Blocks.air);
		} else if(world.getBlock(x, y, z) instanceof BlockLeaves) {
			if(light < 4)
			world.setBlock(x, y, z, Blocks.air);
		} else if(world.getBlock(x, y, z) instanceof BlockVine) {
			if(light < 4)
			world.setBlock(x, y, z, Blocks.air);
		}
	}	
	///Burn the world.
	public static void burn(World world, int x, int y, int z) {
		Block b = world.getBlock(x, y, z);
		if(b.isFlammable(world, x, y, z, ForgeDirection.UP) && world.getBlock(x, y + 1, z) == Blocks.air && world.getSavedLightValue(EnumSkyBlock.Sky, x, y+1, z)>=7 ) {
			if(b instanceof BlockLeaves || b instanceof BlockBush)
			{
				world.setBlockToAir(x, y, z);
			}
			world.setBlock(x, y+1, z, Blocks.fire);
		}
		else if((b == Blocks.grass || b == Blocks.mycelium || b == ModBlocks.waste_earth || b == ModBlocks.frozen_grass || b == ModBlocks.waste_mycelium) && !world.canLightningStrikeAt(x, y, z) && world.getSavedLightValue(EnumSkyBlock.Sky, x, y+1, z)>=7) {
			world.setBlock(x, y, z, ModBlocks.burning_earth);
		}
		else if(b == ModBlocks.frozen_dirt && world.getSavedLightValue(EnumSkyBlock.Sky, x, y+1, z)>=7)
		{
			world.setBlock(x, y, z, Blocks.dirt);
		}
	}		
}
