package com.hbm.world.biome;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.feature.WorldGenSurfaceSpot;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeDecoratorNoMansLand extends BiomeDecorator {

	public WorldGenerator sellafiteGen;
	public WorldGenerator gravelGen;
	public int sellafitePerChunk;
	public int gravelPerChunk;
	
	public BiomeDecoratorNoMansLand() {
		super();

		//TODO: instead of multiple localized shitty generators, make one that covers everything
		this.sellafiteGen = new WorldGenSurfaceSpot(ModBlocks.sellafield_slaked, 6, 0.15F);
		this.gravelGen = new WorldGenSurfaceSpot(ModBlocks.sellafield_slaked, 6, 0.15F);
		this.sellafitePerChunk = 2;
		this.gravelPerChunk = 2;
	}

	protected void genDecorations(BiomeGenBase biome) {
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
		this.generateOres();
		
		int x;
		int y;
		int z;

		/// SAND IN WATER ///
		boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, SAND);
		for(int i = 0; doGen && i < this.sandPerChunk2; ++i) {
			x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			this.sandGen.generate(this.currentWorld, this.randomGenerator, x, this.currentWorld.getTopSolidOrLiquidBlock(x, z), z);
		}

		/// GRAVEL IN WATER ///
		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, SAND_PASS2);
		for(int i = 0; doGen && i < this.sandPerChunk; ++i) {
			x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, x, this.currentWorld.getTopSolidOrLiquidBlock(x, z), z);
		}
		
		/// SELLAFITE ///
		for(int i = 0; i < this.sellafitePerChunk; ++i) {
			x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			this.sellafiteGen.generate(this.currentWorld, this.randomGenerator, x, this.currentWorld.getTopSolidOrLiquidBlock(x, z), z);
		}
		
		/// GRAVEL ///
		for(int i = 0; i < this.gravelPerChunk; ++i) {
			x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			this.gravelGen.generate(this.currentWorld, this.randomGenerator, x, this.currentWorld.getTopSolidOrLiquidBlock(x, z), z);
		}

		int trees = this.treesPerChunk;

		if(this.randomGenerator.nextInt(10) == 0) trees++;

		/// TREES ///
		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, TREE);
		for(int i = 0; doGen && i < trees; ++i) {
			x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			y = this.currentWorld.getHeightValue(x, z);
			WorldGenAbstractTree worldgenabstracttree = biome.func_150567_a(this.randomGenerator);
			worldgenabstracttree.setScale(1.0D, 1.0D, 1.0D);

			if(worldgenabstracttree.generate(this.currentWorld, this.randomGenerator, x, y, z)) {
				worldgenabstracttree.func_150524_b(this.currentWorld, this.randomGenerator, x, y, z);
			}
		}

		/// TALL GRASS ///
		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, GRASS);
		for(int i = 0; doGen && i < this.grassPerChunk; ++i) {
			x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			y = nextInt(this.currentWorld.getHeightValue(x, z) * 2);
			WorldGenerator worldgenerator = biome.getRandomWorldGenForGrass(this.randomGenerator);
			worldgenerator.generate(this.currentWorld, this.randomGenerator, x, y, z);
		}

		/// SHRUBBERY ///
		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, DEAD_BUSH);
		for(int i = 0; doGen && i < this.deadBushPerChunk; ++i) {
			x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
			z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
			y = nextInt(this.currentWorld.getHeightValue(x, z) * 2);
			(new WorldGenDeadBush(Blocks.deadbush)).generate(this.currentWorld, this.randomGenerator, x, y, z);
		}

		/// LAKES ///
		doGen = TerrainGen.decorate(currentWorld, randomGenerator, chunk_X, chunk_Z, LAKE);
		if(doGen && this.generateLakes) {
			for(int i = 0; i < 50; ++i) {
				x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
				y = this.randomGenerator.nextInt(this.randomGenerator.nextInt(248) + 8);
				z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
				(new WorldGenLiquids(Blocks.flowing_water)).generate(this.currentWorld, this.randomGenerator, x, y, z);
			}
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
	}

	private int nextInt(int i) {
		if(i <= 1)
			return 0;
		return this.randomGenerator.nextInt(i);
	}
}
