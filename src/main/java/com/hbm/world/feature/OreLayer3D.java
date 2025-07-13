package com.hbm.world.feature;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

public class OreLayer3D {
	
	public static int counter = 0;
	public int id;

	NoiseGeneratorPerlin noiseX;
	NoiseGeneratorPerlin noiseY;
	NoiseGeneratorPerlin noiseZ;
	
	double scaleH;
	double scaleV;
	double threshold;
	
	Block block;
	int meta;
	int dim = 0;
	
	public OreLayer3D(Block block, int meta) {
		this.block = block;
		this.meta = meta;
		MinecraftForge.EVENT_BUS.register(this);
		this.id = counter;
		counter++;
	}
	
	public OreLayer3D setDimension(int dim) {
		this.dim = dim;
		return this;
	}
	
	public OreLayer3D setScaleH(double scale) {
		this.scaleH = scale;
		return this;
	}
	
	public OreLayer3D setScaleV(double scale) {
		this.scaleV = scale;
		return this;
	}
	
	public OreLayer3D setThreshold(double threshold) {
		this.threshold = threshold;
		return this;
	}

	@SubscribeEvent
	public void onDecorate(DecorateBiomeEvent.Pre event) {

		World world = event.world;
		
		if(world.provider == null || world.provider.dimensionId != this.dim) return;

		if(this.noiseX == null) this.noiseX = new NoiseGeneratorPerlin(new Random(event.world.getSeed() + 101 + id), 4);
		if(this.noiseY == null) this.noiseY = new NoiseGeneratorPerlin(new Random(event.world.getSeed() + 102 + id), 4);
		if(this.noiseZ == null) this.noiseZ = new NoiseGeneratorPerlin(new Random(event.world.getSeed() + 103 + id), 4);
		
		int cX = event.chunkX;
		int cZ = event.chunkZ;
		
		for(int x = cX + 8; x < cX + 24; x++) {
			for(int z = cZ + 8; z < cZ + 24; z++) {
				for(int y = 64; y > 5; y--) {
					double nX = this.noiseX.func_151601_a(y * scaleV, z * scaleH);
					double nY = this.noiseY.func_151601_a(x * scaleH, z * scaleH);
					double nZ = this.noiseZ.func_151601_a(x * scaleH, y * scaleV);
					
					if(nX * nY * nZ > threshold) {
						Block target = world.getBlock(x, y, z);
						
						if(target.isNormalCube() && target.getMaterial() == Material.rock && target.isReplaceableOreGen(world, x, y, z, Blocks.stone)) {
							world.setBlock(x, y, z, block, meta, 2);
						}
					}
				}
			}
		}
	}
}
