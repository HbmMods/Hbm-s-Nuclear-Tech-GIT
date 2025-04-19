package com.hbm.world.biome;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.mob.EntityUndeadSoldier;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;

public class BiomeGenNoMansLand extends BiomeGenBase {

	public static BiomeGenNoMansLand instance;

	public static void initDictionary() {
		BiomeDictionary.registerBiomeType(instance,	DEAD,	PLAINS,	WASTELAND);
		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(instance, 5));
	}

	public BiomeGenNoMansLand(int id) {
		super(id);
		instance = this;
		this.theBiomeDecorator = new BiomeDecoratorNoMansLand();
		this.waterColorMultiplier = 0x6F6F12;
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.setHeight(height_LowPlains);
		this.theBiomeDecorator.treesPerChunk = -999;
		this.theBiomeDecorator.flowersPerChunk = 0;
		this.theBiomeDecorator.grassPerChunk = 0;
		this.flowers.clear();

		this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityUndeadSoldier.class, 1, 4, 6));
	}

	@Override
	public List getSpawnableList(EnumCreatureType type) {
		return super.getSpawnableList(type);
	}

	@Override @SideOnly(Side.CLIENT)
	public int getBiomeGrassColor(int x, int y, int z) { return 0x696F59; }

	@Override @SideOnly(Side.CLIENT)
	public int getBiomeFoliageColor(int x, int y, int z) { return 0x767C67; }

	@Override @SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float temp) { return 0x949494; }
}
