package com.hbm.dim;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.*;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeDecoratorDead extends BiomeDecorator {

	// Same as BiomeDecorator, but skips any plant life and supports different stone types
	public boolean generateSand = false;
	public boolean generateClay = false;
	public boolean generateGravel = false;

	public int lavaCount = 20;
	
	private final Block stoneBlock;

	public BiomeDecoratorDead(Block stoneBlock) {
		this.stoneBlock = stoneBlock;
	}

	@Override
	protected void genDecorations(BiomeGenBase biome) {
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
		this.generateOres();
		int i;
		int j;
		int k;
		int l;

		boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, SAND);
		if(doGen && generateSand) {
			for(i = 0; i < this.sandPerChunk2; ++i) {
				j = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				k = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				this.sandGen.generate(this.currentWorld, this.randomGenerator, j, this.currentWorld.getTopSolidOrLiquidBlock(j, k), k);
			}
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, CLAY);
		if(doGen && generateClay) {
			for(i = 0; i < this.clayPerChunk; ++i) {
				j = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				k = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				this.clayGen.generate(this.currentWorld, this.randomGenerator, j, this.currentWorld.getTopSolidOrLiquidBlock(j, k), k);
			}
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, SAND_PASS2);
		if(doGen && generateGravel) {
			for(i = 0; i < this.sandPerChunk; ++i) {
				j = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				k = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, j, this.currentWorld.getTopSolidOrLiquidBlock(j, k), k);
			}
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, LAKE);
		if(doGen && this.generateLakes) {
			for (i = 0; i < lavaCount; ++i) {
				j = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				k = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
				l = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				(new WorldGenLiquidsCelestial(Blocks.flowing_lava, stoneBlock)).generate(this.currentWorld, this.randomGenerator, j, k, l);
			}
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
	}

}
