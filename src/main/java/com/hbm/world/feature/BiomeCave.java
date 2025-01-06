package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.BlockEnums.EnumBiomeType;
import com.hbm.blocks.ModBlocks;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.TempCategory;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;

public class BiomeCave {
	
	private NoiseGeneratorPerlin noise;
	/** The number that is being deducted flat from the result of the perlin noise before all other processing. Increase this to make strata rarer. */
	private double threshold = 2D;
	/** The mulitplier for the remaining bit after the threshold has been deducted. Increase to make strata wavier. */
	private int rangeMult = 3;
	/** The maximum range after multiplying - anything above this will be subtracted from (maxRange * 2) to yield the proper range. Increase this to make strata thicker. */
	private int maxRange = 4;
	/** The y-level around which the stratum is centered. */
	private int yLevel = 30;
	
	public BiomeCave() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public BiomeCave setThreshold(double threshold) {
		this.threshold = threshold;
		return this;
	}
	
	public BiomeCave setRangeMult(int rangeMult) {
		this.rangeMult = rangeMult;
		return this;
	}
	
	public BiomeCave setMaxRange(int maxRange) {
		this.maxRange = maxRange;
		return this;
	}
	
	public BiomeCave setYLevel(int yLevel) {
		this.yLevel = yLevel;
		return this;
	}

	@SubscribeEvent
	public void onDecorate(DecorateBiomeEvent.Pre event) {
		
		World world = event.world;
		
		if(world.provider == null || world.provider.dimensionId != 0) return;
		
		if(this.noise == null) {
			this.noise = new NoiseGeneratorPerlin(new Random(event.world.getSeed() - 1916169 + yLevel), 2);
		}
		
		int cX = event.chunkX;
		int cZ = event.chunkZ;
		
		double scale = 0.01D;
		
		for(int x = cX + 8; x < cX + 24; x++) {
			for(int z = cZ + 8; z < cZ + 24; z++) {
				
				BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
				EnumBiomeType type = getTypeFromBiome(biome);
				
				double n = noise.func_151601_a(x * scale, z * scale);
				
				if(type != null && n > threshold) {
					int range = (int)((n - threshold) * rangeMult);
					
					if(range > maxRange)
						range = (maxRange * 2) - range;
					
					if(range < 0)
						continue;
					
					for(int y = yLevel - range; y <= yLevel + range; y++) {
						handleBiome(world, x, y, z, type);
					}
				}
			}
		}
	}
	
	private static void handleBiome(World world, int x, int y, int z, EnumBiomeType type) {
		Block target = world.getBlock(x, y, z);
		
		if(target.isNormalCube() && target.isReplaceableOreGen(world, x, y, z, Blocks.stone)) {
			
			boolean shouldGen = false;
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).isAir(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
					shouldGen = true; break;
				}
				if(world.getBlock(x + dir.offsetX * 2, y + dir.offsetY * 2, z + dir.offsetZ * 2).isAir(world, x + dir.offsetX * 2, y + dir.offsetY * 2, z + dir.offsetZ * 2)) {
					shouldGen = true; break;
				}
			}
			
			if(shouldGen) {
				world.setBlock(x, y, z, ModBlocks.stone_biome, type.ordinal(), 2);
			}
		}
	}
	
	private static EnumBiomeType getTypeFromBiome(BiomeGenBase biome) {

		if(biome.temperature >= 1 && biome.rainfall < 0.25) return EnumBiomeType.DESERT;
		if(biome.temperature >= 0.5 && biome.rainfall > 0.25 && biome.getTempCategory() != TempCategory.OCEAN) return EnumBiomeType.WOODLAND;
		
		return null;
	}
}
