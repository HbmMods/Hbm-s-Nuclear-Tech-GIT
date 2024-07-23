package com.hbm.dim;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeDecoratorCelestial extends BiomeDecorator {

	// Same as BiomeDecorator, but skips any vanilla plant life and supports different stone types

	public int lavaCount = 20;

	public int waterPlantsPerChunk = 0;
	public WorldGenerator genPlants;
	
	private final Block stoneBlock;

	public BiomeDecoratorCelestial(Block stoneBlock) {
		this.stoneBlock = stoneBlock;
		this.genPlants = new WorldGenWaterPlant();
	}

	@Override
	protected void genDecorations(BiomeGenBase biome) {
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
		this.generateOres();

		boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, LAKE);
		if(doGen && this.generateLakes) {
			for (int i = 0; i < lavaCount; ++i) {
				int x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				int y = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
				int z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				(new WorldGenLiquidsCelestial(Blocks.flowing_lava, stoneBlock)).generate(this.currentWorld, this.randomGenerator, x, y, z);
			}
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, REED);
		if(doGen && this.waterPlantsPerChunk > 0) {
			for (int i = 0; i < waterPlantsPerChunk; ++i) {
				int x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				int z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				int y = this.randomGenerator.nextInt(64);
				genPlants.generate(currentWorld, randomGenerator, x, y, z);
			}
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
	}

}
