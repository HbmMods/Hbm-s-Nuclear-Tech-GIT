package com.hbm.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenCrater extends BiomeGenBase {
	
	//public static final BiomeGenBase craterBiome = new BiomeGenCrater(50 /* TEMP */).setDisableRain();

	public BiomeGenCrater(int id) {
		super(id);
		this.waterColorMultiplier = 0xE0FFAE; //swamp color
		this.setBiomeName("Crater");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int x, int y, int z) {
		double noise = plantNoise.func_151601_a((double) x * 0.225D, (double) z * 0.225D);
		return noise < -0.1D ? 0x606060 : 0x505050;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int x, int y, int z) {
		return 0x6A7039;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float temp) {
		//return 0x66BBA9;
		return 0x6B9189;
	}
}
