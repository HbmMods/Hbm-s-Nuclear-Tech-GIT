package com.hbm.lib;

import java.util.Random;

import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMotherOfAllOres;
import com.hbm.blocks.generic.BlockNTMFlower.EnumFlowerType;
import com.hbm.config.GeneralConfig;
import com.hbm.config.WorldConfig;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBedrockOre.EnumBedrockOre;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.TomSaveData;
import com.hbm.tileentity.machine.storage.TileEntitySafe;
import com.hbm.tileentity.machine.storage.TileEntitySoyuzCapsule;
import com.hbm.world.dungeon.AncientTomb;
import com.hbm.world.dungeon.Antenna;
import com.hbm.world.dungeon.ArcticVault;
import com.hbm.world.dungeon.Barrel;
import com.hbm.world.dungeon.Bunker;
import com.hbm.world.dungeon.CrashedVertibird;
import com.hbm.world.dungeon.DesertAtom001;
import com.hbm.world.dungeon.Factory;
import com.hbm.world.dungeon.LibraryDungeon;
import com.hbm.world.dungeon.Radio01;
import com.hbm.world.dungeon.Relay;
import com.hbm.world.dungeon.Satellite;
import com.hbm.world.dungeon.Silo;
import com.hbm.world.dungeon.Spaceship;
import com.hbm.world.dungeon.Vertibird;
import com.hbm.world.feature.BedrockOre;
import com.hbm.world.feature.DepthDeposit;
import com.hbm.world.feature.Dud;
import com.hbm.world.feature.Geyser;
import com.hbm.world.feature.GeyserLarge;
import com.hbm.world.feature.Meteorite;
import com.hbm.world.feature.OilBubble;
import com.hbm.world.feature.OilSandBubble;
import com.hbm.world.feature.OilSpot;
import com.hbm.world.feature.Sellafield;
import com.hbm.world.generator.CellularDungeonFactory;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBeach;
import net.minecraft.world.biome.BiomeGenForest;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenRiver;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class HbmWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case -1:
			generateNether(world, rand, chunkX * 16, chunkZ * 16); break;
		case 0:
			generateSurface(world, rand, chunkX * 16, chunkZ * 16); break;
		case 1:
			generateEnd(world, rand, chunkX * 16, chunkZ * 16); break;
		default:
			if(GeneralConfig.enableMDOres)
				generateSurface(world, rand, chunkX * 16, chunkZ * 16); break;
		}
	}
	
	private void generateSurface(World world, Random rand, int i, int j) {

		BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, j);

		if(!TomSaveData.forWorld(world).impact) {
			
			if(biome instanceof BiomeGenForest && rand.nextInt(16) == 0) {
				DungeonToolbox.generateFlowers(world, rand, i, j, ModBlocks.plant_flower, EnumFlowerType.FOXGLOVE.ordinal());
			}
			if(biome == BiomeGenBase.roofedForest && rand.nextInt(8) == 0) {
				DungeonToolbox.generateFlowers(world, rand, i, j, ModBlocks.plant_flower, EnumFlowerType.NIGHTSHADE.ordinal());
			}
			if(biome instanceof BiomeGenJungle && rand.nextInt(8) == 0) {
				DungeonToolbox.generateFlowers(world, rand, i, j, ModBlocks.plant_flower, EnumFlowerType.TOBACCO.ordinal());
			}
			if(rand.nextInt(64) == 0) {
				DungeonToolbox.generateFlowers(world, rand, i, j, ModBlocks.plant_flower, EnumFlowerType.WEED.ordinal());
			}
			if(biome instanceof BiomeGenRiver && rand.nextInt(4) == 0) {
				DungeonToolbox.generateFlowers(world, rand, i, j, ModBlocks.reeds, 0);
			}
			if(biome instanceof BiomeGenBeach && rand.nextInt(8) == 0) {
				DungeonToolbox.generateFlowers(world, rand, i, j, ModBlocks.reeds, 0);
			}
		}
		
		if(WorldConfig.oilcoalSpawn > 0 && rand.nextInt(WorldConfig.oilcoalSpawn) == 0)
			DungeonToolbox.generateOre(world, rand, i, j, 1, 64, 32, 32, ModBlocks.ore_coal_oil);

		if(WorldConfig.gasbubbleSpawn > 0 && rand.nextInt(WorldConfig.gasbubbleSpawn) == 0)
			DungeonToolbox.generateOre(world, rand, i, j, 1, 32, 30, 10, ModBlocks.gas_flammable, 1);

		if(WorldConfig.explosivebubbleSpawn > 0 && rand.nextInt(WorldConfig.explosivebubbleSpawn) == 0)
			DungeonToolbox.generateOre(world, rand, i, j, 1, 32, 30, 10, ModBlocks.gas_explosive, 1);

		if(WorldConfig.alexandriteSpawn > 0 && rand.nextInt(WorldConfig.alexandriteSpawn) == 0)
			DungeonToolbox.generateOre(world, rand, i, j, 1, 3, 10, 5, ModBlocks.ore_alexandrite);

		DepthDeposit.generateConditionOverworld(world, i, 0, 3, j, 5, 0.6D, ModBlocks.cluster_depth_iron, rand, 24);
		DepthDeposit.generateConditionOverworld(world, i, 0, 3, j, 5, 0.6D, ModBlocks.cluster_depth_titanium, rand, 32);
		DepthDeposit.generateConditionOverworld(world, i, 0, 3, j, 5, 0.6D, ModBlocks.cluster_depth_tungsten, rand, 32);
		DepthDeposit.generateConditionOverworld(world, i, 0, 3, j, 5, 0.8D, ModBlocks.ore_depth_cinnebar, rand, 16);
		DepthDeposit.generateConditionOverworld(world, i, 0, 3, j, 5, 0.8D, ModBlocks.ore_depth_zirconium, rand, 16);
		DepthDeposit.generateConditionOverworld(world, i, 0, 3, j, 5, 0.8D, ModBlocks.ore_depth_borax, rand, 16);

		if(WorldConfig.overworldOre) {
			DungeonToolbox.generateOre(world, rand, i, j, 25, 6, 30, 10, ModBlocks.ore_gneiss_iron, ModBlocks.stone_gneiss);
			DungeonToolbox.generateOre(world, rand, i, j, 10, 6, 30, 10, ModBlocks.ore_gneiss_gold, ModBlocks.stone_gneiss);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.uraniumSpawn * 3, 6, 30, 10, ModBlocks.ore_gneiss_uranium, ModBlocks.stone_gneiss);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.copperSpawn * 3, 6, 30, 10, ModBlocks.ore_gneiss_copper, ModBlocks.stone_gneiss);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.asbestosSpawn * 3, 6, 30, 10, ModBlocks.ore_gneiss_asbestos, ModBlocks.stone_gneiss);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.lithiumSpawn, 6, 30, 10, ModBlocks.ore_gneiss_lithium, ModBlocks.stone_gneiss);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.rareSpawn, 6, 30, 10, ModBlocks.ore_gneiss_rare, ModBlocks.stone_gneiss);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.gassshaleSpawn * 3, 10, 30, 10, ModBlocks.ore_gneiss_gas, ModBlocks.stone_gneiss);
	
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.uraniumSpawn, 5, 5, 20, ModBlocks.ore_uranium);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.thoriumSpawn, 5, 5, 25, ModBlocks.ore_thorium);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumSpawn, 6, 5, 30, ModBlocks.ore_titanium);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.sulfurSpawn, 8, 5, 30, ModBlocks.ore_sulfur);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.aluminiumSpawn, 6, 5, 40, ModBlocks.ore_aluminium);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.copperSpawn, 6, 5, 45, ModBlocks.ore_copper);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.fluoriteSpawn, 4, 5, 45, ModBlocks.ore_fluorite);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.niterSpawn, 6, 5, 30, ModBlocks.ore_niter);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.tungstenSpawn, 8, 5, 30, ModBlocks.ore_tungsten);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.leadSpawn, 9, 5, 30, ModBlocks.ore_lead);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.berylliumSpawn, 4, 5, 30, ModBlocks.ore_beryllium);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.rareSpawn, 5, 5, 20, ModBlocks.ore_rare);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.ligniteSpawn, 24, 35, 25, ModBlocks.ore_lignite);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.asbestosSpawn, 4, 16, 16, ModBlocks.ore_asbestos);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.cinnebarSpawn, 4, 8, 16, ModBlocks.ore_cinnebar);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.cobaltSpawn, 4, 4, 8, ModBlocks.ore_cobalt);
	
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.ironClusterSpawn, 6, 15, 45, ModBlocks.cluster_iron);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.titaniumClusterSpawn, 6, 15, 30, ModBlocks.cluster_titanium);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.aluminiumClusterSpawn, 6, 15, 35, ModBlocks.cluster_aluminium);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.copperClusterSpawn, 6, 15, 20, ModBlocks.cluster_copper);

			//DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.hematiteSpawn, 10, 4, 80, ModBlocks.stone_resource, EnumStoneType.HEMATITE.ordinal());
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.malachiteSpawn, 10, 6, 40, ModBlocks.stone_resource, EnumStoneType.MALACHITE.ordinal());
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.limestoneSpawn, 12, 25, 30, ModBlocks.stone_resource, EnumStoneType.LIMESTONE.ordinal());
			
			DungeonToolbox.generateBedrockOreWithChance(world, rand, i, j, EnumBedrockOre.IRON,													1, WorldConfig.bedrockIronSpawn);
			DungeonToolbox.generateBedrockOreWithChance(world, rand, i, j, EnumBedrockOre.COPPER,												1, WorldConfig.bedrockCopperSpawn);
			DungeonToolbox.generateBedrockOreWithChance(world, rand, i, j, EnumBedrockOre.BORAX,	new FluidStack(Fluids.SULFURIC_ACID, 500),	3, WorldConfig.bedrockBoraxSpawn);
			DungeonToolbox.generateBedrockOreWithChance(world, rand, i, j, EnumBedrockOre.ASBESTOS,												2, WorldConfig.bedrockAsbestosSpawn);
			DungeonToolbox.generateBedrockOreWithChance(world, rand, i, j, EnumBedrockOre.NIOBIUM,	new FluidStack(Fluids.ACID, 1_000),			2, WorldConfig.bedrockNiobiumSpawn);
			DungeonToolbox.generateBedrockOreWithChance(world, rand, i, j, EnumBedrockOre.TITANIUM,	new FluidStack(Fluids.SULFURIC_ACID, 500),	2, WorldConfig.bedrockTitaniumSpawn);
			DungeonToolbox.generateBedrockOreWithChance(world, rand, i, j, EnumBedrockOre.TUNGSTEN,	new FluidStack(Fluids.ACID, 1_000),			2, WorldConfig.bedrockTungstenSpawn);
			DungeonToolbox.generateBedrockOreWithChance(world, rand, i, j, EnumBedrockOre.GOLD,													1, WorldConfig.bedrockGoldSpawn);

			for(int k = 0; k < WorldConfig.randomSpawn; k++) {
				BlockMotherOfAllOres.shuffleOverride(rand);
				DungeonToolbox.generateOre(world, rand, i, j, 1, 10, 4, 30, ModBlocks.ore_random);
			}
			BlockMotherOfAllOres.resetOverride();
			
			if(GeneralConfig.enable528ColtanSpawn) {
				DungeonToolbox.generateOre(world, rand, i, j, GeneralConfig.coltanRate, 4, 15, 40, ModBlocks.ore_coltan);
			}

			Random colRand = new Random(world.getSeed() + 5);
			int colX = (int) (colRand.nextGaussian() * 1500);
			int colZ = (int) (colRand.nextGaussian() * 1500);
			int colRange = 750;
			
			if((GeneralConfig.enable528BedrockSpawn || GeneralConfig.enable528BedrockDeposit) && rand.nextInt(GeneralConfig.bedrockRate) != 0) {
				int x = i + rand.nextInt(16) + 8;
				int z = j + rand.nextInt(16) + 8;
				
				if(GeneralConfig.enable528BedrockSpawn || (GeneralConfig.enable528BedrockDeposit && x <= colX + colRange && x >= colX - colRange && z <= colZ + colRange && z >= colZ - colRange)) {
					BedrockOre.generate(world, x, z, new ItemStack(ModItems.fragment_coltan), null, 0xA78D7A, 1);
				}
			}
			
			if(GeneralConfig.enable528ColtanDeposit) {
				for(int k = 0; k < 2; k++) {

					for(int r = 1; r <= 5; r++) {
						int randPosX = i + rand.nextInt(16);
						int randPosY = rand.nextInt(25) + 15;
						int randPosZ = j + rand.nextInt(16);

						int range = colRange / r;

						if(randPosX <= colX + range && randPosX >= colX - range && randPosZ <= colZ + range && randPosZ >= colZ - range) {
							(new WorldGenMinable(ModBlocks.ore_coltan, 4)).generate(world, rand, randPosX, randPosY, randPosZ);
						}
					}
				}
			}

			for(int k = 0; k < rand.nextInt(4); k++) {
				int randPosX = i + rand.nextInt(16);
				int randPosY = rand.nextInt(15) + 15;
				int randPosZ = j + rand.nextInt(16);

				if(randPosX <= -350 && randPosX >= -450 && randPosZ <= -350 && randPosZ >= -450)
					(new WorldGenMinable(ModBlocks.ore_australium, 50)).generate(world, rand, randPosX, randPosY, randPosZ);
			}
		}

		if(GeneralConfig.enableDungeons && world.provider.isSurfaceWorld()) {

			if(biome == BiomeGenBase.plains || biome == BiomeGenBase.desert) {
				if(WorldConfig.radioStructure > 0 && rand.nextInt(WorldConfig.radioStructure) == 0) {
					for(int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new Radio01().generate(world, rand, x, y, z);
					}
				}
			}

			if(biome.temperature >= 0.4F && biome.rainfall <= 0.6F) {
				if(WorldConfig.antennaStructure > 0 && rand.nextInt(WorldConfig.antennaStructure) == 0) {
					for(int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new Antenna().generate(world, rand, x, y, z);
					}
				}
			}

			if(!biome.canSpawnLightningBolt() && biome.temperature >= 1.5F) {
				if(WorldConfig.atomStructure > 0 && rand.nextInt(WorldConfig.atomStructure) == 0) {
					for(int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new DesertAtom001().generate(world, rand, x, y, z);
					}
				}
			}

			if(!biome.canSpawnLightningBolt() && biome.temperature >= 2F) {
				if(WorldConfig.vertibirdStructure > 0 && rand.nextInt(WorldConfig.vertibirdStructure) == 0) {
					for(int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						if(rand.nextInt(2) == 0) {
							new Vertibird().generate(world, rand, x, y, z);
						} else {
							new CrashedVertibird().generate(world, rand, x, y, z);
						}

					}
				}
			}

			if(WorldConfig.dungeonStructure > 0 && rand.nextInt(WorldConfig.dungeonStructure) == 0) {
				int x = i + rand.nextInt(16);
				int y = rand.nextInt(256);
				int z = j + rand.nextInt(16);
				new LibraryDungeon().generate(world, rand, x, y, z);
			}

			if(biome.temperature == 0.5F || biome.temperature == 2.0F) {
				if(WorldConfig.relayStructure > 0 && rand.nextInt(WorldConfig.relayStructure) == 0) {
					for(int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new Relay().generate(world, rand, x, y, z);
					}
				}
			}

			if(biome.temperature == 0.5F || biome.temperature == 2.0F) {
				if(WorldConfig.satelliteStructure > 0 && rand.nextInt(WorldConfig.satelliteStructure) == 0) {
					for(int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new Satellite().generate(world, rand, x, y, z);
					}
				}
			}

			if(!biome.canSpawnLightningBolt() && biome.temperature >= 1.5F) {
				if(rand.nextInt(200) == 0) {
					for(int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						OilSandBubble.spawnOil(world, x, y, z, 15 + rand.nextInt(31));
					}
				}
			}

			if(WorldConfig.bunkerStructure > 0 && rand.nextInt(WorldConfig.bunkerStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Bunker().generate(world, rand, x, y, z);
			}

			if(WorldConfig.siloStructure > 0 && rand.nextInt(WorldConfig.siloStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Silo().generate(world, rand, x, y, z);
			}

			if(WorldConfig.factoryStructure > 0 && rand.nextInt(WorldConfig.factoryStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Factory().generate(world, rand, x, y, z);
			}

			if(WorldConfig.dudStructure > 0 && rand.nextInt(WorldConfig.dudStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Dud().generate(world, rand, x, y, z);
			}

			if(WorldConfig.spaceshipStructure > 0 && rand.nextInt(WorldConfig.spaceshipStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Spaceship().generate(world, rand, x, y, z);
			}

			if(WorldConfig.barrelStructure > 0 && biome.temperature >= 1.5F && !biome.canSpawnLightningBolt() && rand.nextInt(WorldConfig.barrelStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Barrel().generate(world, rand, x, y, z);
			}

			if(WorldConfig.broadcaster > 0 && rand.nextInt(WorldConfig.broadcaster) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y - 1, z).canPlaceTorchOnTop(world, x, y - 1, z)) {
					world.setBlock(x, y, z, ModBlocks.broadcaster_pc, rand.nextInt(4) + 2, 2);
					
					if(GeneralConfig.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned corrupted broadcaster at " + x + " " + (y) +" " + z);
				}
			}

			if(WorldConfig.minefreq > 0 && GeneralConfig.enableMines && rand.nextInt(WorldConfig.minefreq) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y - 1, z).canPlaceTorchOnTop(world, x, y - 1, z)) {
					world.setBlock(x, y, z, ModBlocks.mine_ap);

					if(GeneralConfig.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned landmine at " + x + " " + (y) + " " + z);
				}
			}

			if(GeneralConfig.enable528 && GeneralConfig.enable528BosniaSimulator && rand.nextInt(16) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);
				if(world.getBlock(x, y - 1, z).canPlaceTorchOnTop(world, x, y - 1, z)) {
					world.setBlock(x, y, z, ModBlocks.mine_he);
				}
			}

			if(WorldConfig.radfreq > 0 && GeneralConfig.enableRad && rand.nextInt(WorldConfig.radfreq) == 0 && biome == BiomeGenBase.desert) {
				
				for (int a = 0; a < 1; a++) {
					int x = i + rand.nextInt(16);
					int z = j + rand.nextInt(16);
					
					double r = rand.nextInt(15) + 10;
					
					if(rand.nextInt(50) == 0)
						r = 50;

					new Sellafield().generate(world, x, z, r, r * 0.35D);

					if(GeneralConfig.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned raditation hotspot at " + x + " " + z);
				}
			}

			if (WorldConfig.geyserChlorine > 0 && biome == BiomeGenBase.plains && rand.nextInt(WorldConfig.geyserWater) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);
				
				if(world.getBlock(x, y - 1, z) == Blocks.grass)
					new Geyser().generate(world, rand, x, y, z);
			}

			if (WorldConfig.geyserWater > 0 && biome == BiomeGenBase.desert && rand.nextInt(WorldConfig.geyserChlorine) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y - 1, z) == Blocks.sand)
					new GeyserLarge().generate(world, rand, x, y, z);
			}

			if (WorldConfig.capsuleStructure > 0 && biome == BiomeGenBase.beach && rand.nextInt(WorldConfig.capsuleStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z) - 4;
				
				if(world.getBlock(x, y + 1, z).canPlaceTorchOnTop(world, x, y + 1, z)) {
					
					world.setBlock(x, y, z, ModBlocks.soyuz_capsule, 3, 2);
					
					TileEntitySoyuzCapsule cap = (TileEntitySoyuzCapsule)world.getTileEntity(x, y, z);
					
					if(cap != null) {
						cap.setInventorySlotContents(rand.nextInt(cap.getSizeInventory()), new ItemStack(ModItems.record_glass));
					}
	
					if(GeneralConfig.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned capsule at " + x + " " + z);
				}
			}

			if (WorldConfig.geyserVapor > 0 && rand.nextInt(WorldConfig.geyserVapor) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y, z) == Blocks.stone)
						world.setBlock(x, y, z, ModBlocks.geysir_vapor);
				else if(world.getBlock(x, y - 1, z) == Blocks.stone)
					world.setBlock(x, y - 1, z, ModBlocks.geysir_vapor);
			}

			if (rand.nextInt(1000) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				
				boolean done = false;
				
				for(int k = 0; k < 256; k++) {
					if(world.getBlock(x, k, z) == Blocks.log && world.getBlockMetadata(x, k, z) == 0) {
						world.setBlock(x, k, z, ModBlocks.pink_log);
						done = true;
					}
				}

				if(GeneralConfig.enableDebugMode && done)
					MainRegistry.logger.info("[Debug] Successfully spawned pink tree at " + x + " " + z);
			}

			if (WorldConfig.vaultfreq > 0 && GeneralConfig.enableVaults && rand.nextInt(WorldConfig.vaultfreq) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y - 1, z).canPlaceTorchOnTop(world, x, y - 1, z)) {
					world.setBlock(x, y, z, ModBlocks.safe, rand.nextInt(4) + 2, 2);
					
					switch(rand.nextInt(10)) {
					case 0:
					case 1:
					case 2:
					case 3:
						((TileEntitySafe)world.getTileEntity(x, y, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y, z)).setMod(1);
						((TileEntitySafe)world.getTileEntity(x, y, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.vault1, (TileEntitySafe)world.getTileEntity(x, y, z), rand.nextInt(4) + 3);
						break;
					case 4:
					case 5:
					case 6:
						((TileEntitySafe)world.getTileEntity(x, y, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y, z)).setMod(0.1);
						((TileEntitySafe)world.getTileEntity(x, y, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.vault2, (TileEntitySafe)world.getTileEntity(x, y, z), rand.nextInt(3) + 2);
						break;
					case 7:
					case 8:
						((TileEntitySafe)world.getTileEntity(x, y, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y, z)).setMod(0.02);
						((TileEntitySafe)world.getTileEntity(x, y, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.vault3, (TileEntitySafe)world.getTileEntity(x, y, z), rand.nextInt(3) + 1);
						break;
					case 9:
						((TileEntitySafe)world.getTileEntity(x, y, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y, z)).setMod(0.0);
						((TileEntitySafe)world.getTileEntity(x, y, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.vault4, (TileEntitySafe)world.getTileEntity(x, y, z), rand.nextInt(2) + 1);
						break;
					}
					
					if(GeneralConfig.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned safe at " + x + " " + (y + 1) +" " + z);
				}
				
			}

			if (WorldConfig.meteorStructure > 0 && rand.nextInt(WorldConfig.meteorStructure) == 0 && biome != BiomeGenBase.ocean && biome != BiomeGenBase.deepOcean) {
				int x = i + rand.nextInt(16) + 8;
				int z = j + rand.nextInt(16) + 8;
				
				CellularDungeonFactory.meteor.generate(world, x, 10, z, rand);
				
				if(GeneralConfig.enableDebugMode)
					MainRegistry.logger.info("[Debug] Successfully spawned meteor dungeon at " + x + " 10 " + z);
				
				int y = world.getHeightValue(x, z);
				
				for(int f = 0; f < 3; f++)
					world.setBlock(x, y + f, z, ModBlocks.meteor_pillar);
				world.setBlock(x, y + 3, z, ModBlocks.meteor_brick_chiseled);
				
				for(int f = 0; f < 10; f++) {

					x = i + rand.nextInt(65) - 32;
					z = j + rand.nextInt(65) - 32;
					y = world.getHeightValue(x, z);
					
					if(world.getBlock(x, y - 1, z).canPlaceTorchOnTop(world, x, y - 1, z)) {
						world.setBlock(x, y, z, Blocks.skull, 1, 2);
						TileEntitySkull skull = (TileEntitySkull)world.getTileEntity(x, y, z);
						
						if(skull != null)
							skull.func_145903_a(rand.nextInt(16));
					}
				}
			}

			if((biome == BiomeGenBase.jungle || biome == BiomeGenBase.jungleEdge || biome == BiomeGenBase.jungleHills) &&
					WorldConfig.jungleStructure > 0 && rand.nextInt(WorldConfig.jungleStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				
				CellularDungeonFactory.jungle.generate(world, x, 20, z, world.rand);
				CellularDungeonFactory.jungle.generate(world, x, 24, z, world.rand);
				CellularDungeonFactory.jungle.generate(world, x, 28, z, world.rand);
				
				if(GeneralConfig.enableDebugMode)
					MainRegistry.logger.info("[Debug] Successfully spawned jungle dungeon at " + x + " 10 " + z);
				
				int y = world.getHeightValue(x, z);
				
				for(int f = 0; f < 3; f++)
					world.setBlock(x, y + f, z, ModBlocks.deco_titanium);
				world.setBlock(x, y + 3, z, Blocks.redstone_block);
			}

			if (WorldConfig.arcticStructure > 0 && rand.nextInt(WorldConfig.arcticStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = 16 + rand.nextInt(32);
				new ArcticVault().trySpawn(world, x, y, z);
			}
			
			if (WorldConfig.pyramidStructure > 0 && biome.temperature >= 2.0F && !biome.canSpawnLightningBolt() && rand.nextInt(WorldConfig.pyramidStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);
				
				new AncientTomb().build(world, rand, x, y, z);
			}
		}

		if(WorldConfig.oilSpawn > 0 && rand.nextInt(WorldConfig.oilSpawn) == 0) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			OilBubble.spawnOil(world, randPosX, randPosY, randPosZ, 10 + rand.nextInt(7));
		}

		if(WorldConfig.bedrockOilSpawn > 0 && rand.nextInt(WorldConfig.bedrockOilSpawn) == 0) {
			int randPosX = i + rand.nextInt(16);
			int randPosZ = j + rand.nextInt(16);
			
			for(int x = -4; x <= 4; x++) {
				for(int y = 0; y <= 4; y++) {
					for(int z = -4; z <= 4; z++) {
						
						if(Math.abs(x) + Math.abs(y) + Math.abs(z) <= 6) {
							Block b = world.getBlock(randPosX + x, y, randPosZ + z);
							if(b.isReplaceableOreGen(world, randPosX + x, y, randPosZ + z, Blocks.stone) || b.isReplaceableOreGen(world, randPosX + x, y, randPosZ + z, Blocks.bedrock)) {
								world.setBlock(randPosX + x, y, randPosZ + z, ModBlocks.ore_bedrock_oil);
							}
						}
					}
				}
			}
			
			DungeonToolbox.generateOre(world, rand, i, j, 16, 8, 10, 50, ModBlocks.stone_porous);
			OilSpot.generateOilSpot(world, randPosX, randPosZ, 5, 50, true);
		}

		if(WorldConfig.meteoriteSpawn > 0 && rand.nextInt(WorldConfig.meteoriteSpawn) == 0) {
			int x = i + rand.nextInt(16);
			int z = j + rand.nextInt(16);
			int y = world.getHeightValue(x, z) - rand.nextInt(10);
			(new Meteorite()).generate(world, rand, x, y, z, false, true, false);
		}

		if (GeneralConfig.enableNITAN) {

			if (i <= 10000 && i + 16 >= 10000 && j <= 10000 && j + 16 >= 10000) {
				if (world.getBlock(10000, 250, 10000) == Blocks.air) {
					world.setBlock(10000, 250, 10000, Blocks.chest);
					if (world.getBlock(10000, 250, 10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.powder,
								(TileEntityChest) world.getTileEntity(10000, 250, 10000), 29);
					}
				}
			}
			if (i <= 0 && i + 16 >= 0 && j <= 10000 && j + 16 >= 10000) {
				if (world.getBlock(0, 250, 10000) == Blocks.air) {
					world.setBlock(0, 250, 10000, Blocks.chest);
					if (world.getBlock(0, 250, 10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.powder,
								(TileEntityChest) world.getTileEntity(0, 250, 10000), 29);
					}
				}
			}
			if (i <= -10000 && i + 16 >= -10000 && j <= 10000 && j + 16 >= 10000) {
				if (world.getBlock(-10000, 250, 10000) == Blocks.air) {
					world.setBlock(-10000, 250, 10000, Blocks.chest);
					if (world.getBlock(-10000, 250, 10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.powder,
								(TileEntityChest) world.getTileEntity(-10000, 250, 10000), 29);
					}
				}
			}
			if (i <= 10000 && i + 16 >= 10000 && j <= 0 && j + 16 >= 0) {
				if (world.getBlock(10000, 250, 0) == Blocks.air) {
					world.setBlock(10000, 250, 0, Blocks.chest);
					if (world.getBlock(10000, 250, 0) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.powder,
								(TileEntityChest) world.getTileEntity(10000, 250, 0), 29);
					}
				}
			}
			if (i <= -10000 && i + 16 >= -10000 && j <= 0 && j + 16 >= 0) {
				if (world.getBlock(-10000, 250, 0) == Blocks.air) {
					world.setBlock(-10000, 250, 0, Blocks.chest);
					if (world.getBlock(-10000, 250, 0) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.powder,
								(TileEntityChest) world.getTileEntity(-10000, 250, 0), 29);
					}
				}
			}
			if (i <= 10000 && i + 16 >= 10000 && j <= -10000 && j + 16 >= -10000) {
				if (world.getBlock(10000, 250, -10000) == Blocks.air) {
					world.setBlock(10000, 250, -10000, Blocks.chest);
					if (world.getBlock(10000, 250, -10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.powder,
								(TileEntityChest) world.getTileEntity(10000, 250, -10000), 29);
					}
				}
			}
			if (i <= 0 && i + 16 >= 0 && j <= -10000 && j + 16 >= -10000) {
				if (world.getBlock(0, 250, -10000) == Blocks.air) {
					world.setBlock(0, 250, -10000, Blocks.chest);
					if (world.getBlock(0, 250, -10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.powder,
								(TileEntityChest) world.getTileEntity(0, 250, -10000), 29);
					}
				}
			}
			if (i <= -10000 && i + 16 >= -10000 && j <= -10000 && j + 16 >= -10000) {
				if (world.getBlock(-10000, 250, -10000) == Blocks.air) {
					world.setBlock(-10000, 250, -10000, Blocks.chest);
					if (world.getBlock(-10000, 250, -10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.powder,
								(TileEntityChest) world.getTileEntity(-10000, 250, -10000), 29);
					}
				}
			}
		}

	}

	private void generateNether(World world, Random rand, int i, int j) {

		if(WorldConfig.netherOre) {
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherUraniumuSpawn, 6, 0, 127, ModBlocks.ore_nether_uranium, Blocks.netherrack);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherTungstenSpawn, 10, 0, 127, ModBlocks.ore_nether_tungsten, Blocks.netherrack);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherSulfurSpawn, 12, 0, 127, ModBlocks.ore_nether_sulfur, Blocks.netherrack);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherPhosphorusSpawn, 6, 0, 127, ModBlocks.ore_nether_fire, Blocks.netherrack);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherCoalSpawn, 32, 16, 96, ModBlocks.ore_nether_coal, Blocks.netherrack);
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherCobaltSpawn, 6, 100, 26, ModBlocks.ore_nether_cobalt, Blocks.netherrack);
			
			if(GeneralConfig.enablePlutoniumOre)
				DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherPlutoniumSpawn, 4, 0, 127, ModBlocks.ore_nether_plutonium, Blocks.netherrack);
		}

		DepthDeposit.generateConditionNether(world, i, 0, 3, j, 7, 0.6D, ModBlocks.ore_depth_nether_neodymium, rand, 16);
		DepthDeposit.generateConditionNether(world, i, 125, 3, j, 7, 0.6D, ModBlocks.ore_depth_nether_neodymium, rand, 16);

		for(int k = 0; k < 30; k++){
			int x = i + rand.nextInt(16);
			int z = j + rand.nextInt(16);
			int d = 16 + rand.nextInt(96);

			for(int y = d - 5; y <= d; y++)
			if(world.getBlock(x, y + 1, z) == Blocks.air && world.getBlock(x, y, z) == Blocks.netherrack)
				world.setBlock(x, y, z, ModBlocks.ore_nether_smoldering);
		}

		for(int k = 0; k < 1; k++){
			int x = i + rand.nextInt(16);
			int z = j + rand.nextInt(16);
			int d = 16 + rand.nextInt(96);

			for(int y = d - 5; y <= d; y++)
			if(world.getBlock(x, y + 1, z) == Blocks.air && world.getBlock(x, y, z) == Blocks.netherrack)
				world.setBlock(x, y, z, ModBlocks.geysir_nether);
		}
	}

	private void generateEnd(World world, Random rand, int i, int j) {
		
		if(WorldConfig.endOre) {
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.endTikiteSpawn, 6, 0, 127, ModBlocks.ore_tikite, Blocks.end_stone);
	
			/*for(int k = 0; k < 50; k++){
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int d = 5 + rand.nextInt(60);
	
				for(int y = d - 5; y <= d; y++)
					if(world.getBlock(x, y, z) == Blocks.air && world.getBlock(x, y + 1, z).isSideSolid(world, x, y, z, ForgeDirection.DOWN))
						world.setBlock(x, y, z, ModBlocks.crystal_trixite);
			}*/
		}
	}

}
