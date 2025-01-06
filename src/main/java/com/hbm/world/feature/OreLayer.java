package com.hbm.world.feature;

import java.util.Random;

import com.hbm.inventory.RecipesCommon.MetaBlock;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

public class OreLayer {

	private NoiseGeneratorPerlin noise;
	private MetaBlock ore;
	private Block target;
	private float density;
	/** The number that is being deducted flat from the result of the perlin noise before all other processing. Increase this to make strata rarer. */
	private int threshold = 5;
	/** The mulitplier for the remaining bit after the threshold has been deducted. Increase to make strata wavier. */
	private int rangeMult = 3;
	/** The maximum range after multiplying - anything above this will be subtracted from (maxRange * 2) to yield the proper range. Increase this to make strata thicker. */
	private int maxRange = 4;
	/** The y-level around which the stratum is centered. */
	private int yLevel = 30;
	int dim = 0;
	
	public OreLayer(Block ore, float density) {
		this(ore, 0, Blocks.stone, density);
	}
	
	public OreLayer(Block ore, int meta, Block target, float density) {
		this.ore = new MetaBlock(ore, meta);
		this.target = target;
		this.density = density;
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public OreLayer setThreshold(int threshold) {
		this.threshold = threshold;
		return this;
	}
	
	public OreLayer setRangeMult(int rangeMult) {
		this.rangeMult = rangeMult;
		return this;
	}
	
	public OreLayer setMaxRange(int maxRange) {
		this.maxRange = maxRange;
		return this;
	}
	
	public OreLayer setYLevel(int yLevel) {
		this.yLevel = yLevel;
		return this;
	}
	
	public OreLayer setDimension(int dim) {
		this.dim = dim;
		return this;
	}

	@SubscribeEvent
	public void onDecorate(DecorateBiomeEvent.Pre event) {
		
		World world = event.world;
		
		if(world.provider == null || world.provider.dimensionId != this.dim) return;
		
		if(this.noise == null) {
			this.noise = new NoiseGeneratorPerlin(new Random(event.world.getSeed() + (ore.getID() * 31) + yLevel), 4);
		}
		
		int cX = event.chunkX;
		int cZ = event.chunkZ;
		
		double scale = 0.01D;
		
		for(int x = cX; x < cX + 16; x++) {
			for(int z = cZ; z < cZ + 16; z++) {
				
				double n = noise.func_151601_a(x * scale, z * scale);
				
				if(n > threshold) {
					int range = (int)((n - threshold) * rangeMult);
					
					if(range > maxRange)
						range = (maxRange * 2) - range;
					
					if(range < 0)
						continue;
					
					for(int y = yLevel - range; y <= yLevel + range; y++) {
						
						if(event.rand.nextFloat() < density) {
							Block genTarget = world.getBlock(x, y, z);
							
							if(genTarget.isReplaceableOreGen(world, x, y, z, target) && genTarget.isReplaceableOreGen(world, x, y, z, Blocks.stone)) {
								world.setBlock(x, y, z, ore.block, ore.meta, 2);
							}
						}
					}
				}
			}
		}
	}
}
