package com.hbm.lib;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.config.WorldConfig;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntitySafe;
import com.hbm.tileentity.machine.TileEntitySoyuzCapsule;
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
import com.hbm.world.feature.Dud;
import com.hbm.world.feature.Geyser;
import com.hbm.world.feature.GeyserLarge;
import com.hbm.world.feature.OilBubble;
import com.hbm.world.feature.OilSandBubble;
import com.hbm.world.feature.Sellafield;
import com.hbm.world.generator.CellularDungeonFactory;
import com.hbm.world.generator.DungeonToolbox;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class HbmWorldGen implements IWorldGenerator {

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
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
	
	NoiseGeneratorOctaves octaves = new NoiseGeneratorOctaves(new Random(0x706f6e6379756dL), 1);
	
	/**
	 * Fake noise generator "unruh" ("unrest", the motion of a clockwork), using a bunch of layered, scaaled and offset
	 * sine functions to simulate a simple noise generator that runs somewhat efficiently
	 * @param long the random function seed used for this operation
	 * @param x the exact x-coord of the height you want
	 * @param z the exact z-coord of the height you want
	 * @param scale how much the x/z coords should be amplified
	 * @param depth the resolution of the operation, higher numbers call more sine functions
	 * @return the height value
	 */
	private double generateUnruh(long seed, int x, int z, double scale, int depth) {
		
		scale = 1/scale;
		
		double result = 1;
		
		Random rand = new Random(seed);
		
		for(int i = 0; i < depth; i++) {

			double offsetX = rand.nextDouble() * Math.PI * 2;
			double offsetZ = rand.nextDouble() * Math.PI * 2;
			
			result += Math.sin(x / Math.pow(2, depth) * scale + offsetX) * Math.sin(z / Math.pow(2, depth) * scale + offsetZ);
		}
		
		return result / depth;
	}

	private void generateSurface(World world, Random rand, int i, int j) {
		
		for(int x = 0; x < 16; x++) {
			
			for(int z = 0; z < 16; z++) {
				
				double unruh = Math.abs(generateUnruh(world.getSeed(), i + x, j + z, 4, 4)) * 1.5;
				double thresh = 0.8D;
				
				if(unruh >= thresh) {
					
					int span = (int)(Math.floor((unruh - thresh) * 7));
					
					for(int s = -span; s <= span; s++) {
						
						int y = 35 + s;
						
						Block b = world.getBlock(x, y, z);
						
						if(b.getMaterial() == Material.rock || b == Blocks.dirt)
							world.setBlock(i + x, (int) (y), j + z, ModBlocks.stone_gneiss, 0, 2);
					}
				}
			}
		}

		DungeonToolbox.generateOre(world, rand, i, j, 25, 6, 30, 10, ModBlocks.ore_gneiss_iron, ModBlocks.stone_gneiss);
		DungeonToolbox.generateOre(world, rand, i, j, 10, 6, 30, 10, ModBlocks.ore_gneiss_gold, ModBlocks.stone_gneiss);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.uraniumSpawn * 3, 6, 30, 10, ModBlocks.ore_gneiss_uranium, ModBlocks.stone_gneiss);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.copperSpawn * 3, 6, 30, 10, ModBlocks.ore_gneiss_copper, ModBlocks.stone_gneiss);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.asbestosSpawn * 3, 6, 30, 10, ModBlocks.ore_gneiss_asbestos, ModBlocks.stone_gneiss);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.lithiumSpawn, 6, 30, 10, ModBlocks.ore_gneiss_lithium, ModBlocks.stone_gneiss);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.rareSpawn, 6, 30, 10, ModBlocks.ore_gneiss_asbestos, ModBlocks.stone_gneiss);
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
		
		if(WorldConfig.oilcoalSpawn > 0 && rand.nextInt(WorldConfig.oilcoalSpawn) == 0)
			DungeonToolbox.generateOre(world, rand, i, j, 1, 64, 32, 32, ModBlocks.ore_coal_oil);

		if(WorldConfig.gasbubbleSpawn > 0 && rand.nextInt(WorldConfig.gasbubbleSpawn) == 0)
			DungeonToolbox.generateOre(world, rand, i, j, 1, 32, 30, 10, ModBlocks.gas_flammable);

		for (int k = 0; k < 6; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			if(randPosX <= 50 && randPosX >= -50 && randPosZ <= 50 && randPosZ >= -50)
				(new WorldGenMinable(ModBlocks.ore_reiium, 12)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < 80; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(128);
			int randPosZ = j + rand.nextInt(16);

			if(randPosX <= 250 && randPosX >= 150 && randPosZ <= 250 && randPosZ >= 150)
				(new WorldGenMinable(ModBlocks.ore_unobtainium, 4)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < rand.nextInt(4); k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(15) + 15;
			int randPosZ = j + rand.nextInt(16);

			if(randPosX <= -350 && randPosX >= -450 && randPosZ <= -350 && randPosZ >= -450)
				(new WorldGenMinable(ModBlocks.ore_australium, 50)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < 12; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			if(randPosX <= 50 && randPosX >= -50 && randPosZ <= 350 && randPosZ >= 250)
				(new WorldGenMinable(ModBlocks.ore_weidanium, 6)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < 24; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(10);
			int randPosZ = j + rand.nextInt(16);

			if(randPosX <= 450 && randPosX >= 350 && randPosZ <= -150 && randPosZ >= -250)
				(new WorldGenMinable(ModBlocks.ore_daffergon, 16)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < 12; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25) + 25;
			int randPosZ = j + rand.nextInt(16);

			if(randPosX <= -250 && randPosX >= -350 && randPosZ <= 250 && randPosZ >= 150)
				(new WorldGenMinable(ModBlocks.ore_verticium, 16)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		if (GeneralConfig.enableDungeons) {

			BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, j);

			if (biome == BiomeGenBase.plains || biome == BiomeGenBase.desert) {
				if (WorldConfig.radioStructure > 0 && rand.nextInt(WorldConfig.radioStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new Radio01().generate(world, rand, x, y, z);
					}
				}
			}

			if (biome == BiomeGenBase.plains || biome == BiomeGenBase.forest || biome == BiomeGenBase.desert
					|| biome == BiomeGenBase.swampland || biome == BiomeGenBase.extremeHills) {
				if (WorldConfig.antennaStructure > 0 && rand.nextInt(WorldConfig.antennaStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new Antenna().generate(world, rand, x, y, z);
					}
				}
			}

			if (biome == BiomeGenBase.desert || biome == BiomeGenBase.beach || biome == BiomeGenBase.mesa
					|| biome == BiomeGenBase.mesaPlateau) {
				if (WorldConfig.atomStructure > 0 && rand.nextInt(WorldConfig.atomStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new DesertAtom001().generate(world, rand, x, y, z);
					}
				}
			}

			if (biome == BiomeGenBase.desert) {
				if (WorldConfig.vertibirdStructure > 0 && rand.nextInt(WorldConfig.vertibirdStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						if (rand.nextInt(2) == 0) {
							new Vertibird().generate(world, rand, x, y, z);
						} else {
							new CrashedVertibird().generate(world, rand, x, y, z);
						}

					}
				}
			}

			if (WorldConfig.dungeonStructure > 0 && rand.nextInt(WorldConfig.dungeonStructure) == 0) {
				int x = i + rand.nextInt(16);
				int y = rand.nextInt(256);
				int z = j + rand.nextInt(16);
				new LibraryDungeon().generate(world, rand, x, y, z);
			}

			if (biome == BiomeGenBase.plains || biome == BiomeGenBase.desert) {
				if (WorldConfig.relayStructure > 0 && rand.nextInt(WorldConfig.relayStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new Relay().generate(world, rand, x, y, z);
					}
				}
			}
			if (biome == BiomeGenBase.plains || biome == BiomeGenBase.desert) {
				if (WorldConfig.satelliteStructure > 0 && rand.nextInt(WorldConfig.satelliteStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new Satellite().generate(world, rand, x, y, z);
					}
				}
			}
			if (biome == BiomeGenBase.desert) {
				if (rand.nextInt(200) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						OilSandBubble.spawnOil(world, x, y, z, 15 + rand.nextInt(31));
					}
				}
			}

			if (WorldConfig.bunkerStructure > 0 && rand.nextInt(WorldConfig.bunkerStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Bunker().generate(world, rand, x, y, z);
			}

			if (WorldConfig.siloStructure > 0 && rand.nextInt(WorldConfig.siloStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Silo().generate(world, rand, x, y, z);
			}

			if (WorldConfig.factoryStructure > 0 && rand.nextInt(WorldConfig.factoryStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Factory().generate(world, rand, x, y, z);
			}

			if (WorldConfig.dudStructure > 0 && rand.nextInt(WorldConfig.dudStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Dud().generate(world, rand, x, y, z);
			}

			if (WorldConfig.spaceshipStructure > 0 && rand.nextInt(WorldConfig.spaceshipStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Spaceship().generate(world, rand, x, y, z);
			}
			
			if (WorldConfig.barrelStructure > 0 && biome == BiomeGenBase.desert && rand.nextInt(WorldConfig.barrelStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Barrel().generate(world, rand, x, y, z);
			}

			if (WorldConfig.broadcaster > 0 && rand.nextInt(WorldConfig.broadcaster) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y - 1, z).canPlaceTorchOnTop(world, x, y - 1, z)) {
					world.setBlock(x, y, z, ModBlocks.broadcaster_pc, rand.nextInt(4) + 2, 2);
					
					if(GeneralConfig.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned corrupted broadcaster at " + x + " " + (y) +" " + z);
				}
			}

			if (WorldConfig.minefreq > 0 && GeneralConfig.enableMines && rand.nextInt(WorldConfig.minefreq) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y - 1, z).canPlaceTorchOnTop(world, x, y - 1, z)) {
					world.setBlock(x, y, z, ModBlocks.mine_ap);
				
					if(GeneralConfig.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned landmine at " + x + " " + (y) +" " + z);
				}
			}
			
			if (WorldConfig.radfreq > 0 && GeneralConfig.enableRad && rand.nextInt(WorldConfig.radfreq) == 0 && biome == BiomeGenBase.desert) {
				
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

			if (WorldConfig.geyserWater > 0 && biome == BiomeGenBase.plains && rand.nextInt(WorldConfig.geyserWater) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);
				
				if(world.getBlock(x, y - 1, z) == Blocks.grass)
					new Geyser().generate(world, rand, x, y, z);
			}

			if (WorldConfig.geyserChlorine > 0 && biome == BiomeGenBase.desert && rand.nextInt(WorldConfig.geyserChlorine) == 0) {
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

				if(world.getBlock(x, y - 1, z) == Blocks.stone)
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
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(10), (TileEntitySafe)world.getTileEntity(x, y, z), rand.nextInt(4) + 3);
						break;
					case 4:
					case 5:
					case 6:
						((TileEntitySafe)world.getTileEntity(x, y, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y, z)).setMod(0.1);
						((TileEntitySafe)world.getTileEntity(x, y, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(11), (TileEntitySafe)world.getTileEntity(x, y, z), rand.nextInt(3) + 2);
						break;
					case 7:
					case 8:
						((TileEntitySafe)world.getTileEntity(x, y, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y, z)).setMod(0.02);
						((TileEntitySafe)world.getTileEntity(x, y, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(12), (TileEntitySafe)world.getTileEntity(x, y, z), rand.nextInt(3) + 1);
						break;
					case 9:
						((TileEntitySafe)world.getTileEntity(x, y, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y, z)).setMod(0.0);
						((TileEntitySafe)world.getTileEntity(x, y, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(13), (TileEntitySafe)world.getTileEntity(x, y, z), rand.nextInt(2) + 1);
						break;
					}
					
					if(GeneralConfig.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned safe at " + x + " " + (y + 1) +" " + z);
				}
				
			}

			if (WorldConfig.meteorStructure > 0 && rand.nextInt(WorldConfig.meteorStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				
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
			
			if (WorldConfig.pyramidStructure > 0 && biome == BiomeGenBase.desert && rand.nextInt(WorldConfig.pyramidStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);
				
				new AncientTomb().build(world, rand, x, y, z);
			}
		}

		if (rand.nextInt(25) == 0) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			OilBubble.spawnOil(world, randPosX, randPosY, randPosZ, 7 + rand.nextInt(9));
		}

		if (GeneralConfig.enableNITAN) {

			if (i <= 10000 && i + 16 >= 10000 && j <= 10000 && j + 16 >= 10000) {
				if (world.getBlock(10000, 250, 10000) == Blocks.air) {
					world.setBlock(10000, 250, 10000, Blocks.chest);
					if (world.getBlock(10000, 250, 10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(10000, 250, 10000), 29);
					}
				}
			}
			if (i <= 0 && i + 16 >= 0 && j <= 10000 && j + 16 >= 10000) {
				if (world.getBlock(0, 250, 10000) == Blocks.air) {
					world.setBlock(0, 250, 10000, Blocks.chest);
					if (world.getBlock(0, 250, 10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(0, 250, 10000), 29);
					}
				}
			}
			if (i <= -10000 && i + 16 >= -10000 && j <= 10000 && j + 16 >= 10000) {
				if (world.getBlock(-10000, 250, 10000) == Blocks.air) {
					world.setBlock(-10000, 250, 10000, Blocks.chest);
					if (world.getBlock(-10000, 250, 10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(-10000, 250, 10000), 29);
					}
				}
			}
			if (i <= 10000 && i + 16 >= 10000 && j <= 0 && j + 16 >= 0) {
				if (world.getBlock(10000, 250, 0) == Blocks.air) {
					world.setBlock(10000, 250, 0, Blocks.chest);
					if (world.getBlock(10000, 250, 0) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(10000, 250, 0), 29);
					}
				}
			}
			if (i <= -10000 && i + 16 >= -10000 && j <= 0 && j + 16 >= 0) {
				if (world.getBlock(-10000, 250, 0) == Blocks.air) {
					world.setBlock(-10000, 250, 0, Blocks.chest);
					if (world.getBlock(-10000, 250, 0) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(-10000, 250, 0), 29);
					}
				}
			}
			if (i <= 10000 && i + 16 >= 10000 && j <= -10000 && j + 16 >= -10000) {
				if (world.getBlock(10000, 250, -10000) == Blocks.air) {
					world.setBlock(10000, 250, -10000, Blocks.chest);
					if (world.getBlock(10000, 250, -10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(10000, 250, -10000), 29);
					}
				}
			}
			if (i <= 0 && i + 16 >= 0 && j <= -10000 && j + 16 >= -10000) {
				if (world.getBlock(0, 250, -10000) == Blocks.air) {
					world.setBlock(0, 250, -10000, Blocks.chest);
					if (world.getBlock(0, 250, -10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(0, 250, -10000), 29);
					}
				}
			}
			if (i <= -10000 && i + 16 >= -10000 && j <= -10000 && j + 16 >= -10000) {
				if (world.getBlock(-10000, 250, -10000) == Blocks.air) {
					world.setBlock(-10000, 250, -10000, Blocks.chest);
					if (world.getBlock(-10000, 250, -10000) == Blocks.chest) {
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(9),
								(TileEntityChest) world.getTileEntity(-10000, 250, -10000), 29);
					}
				}
			}
		}

	}

	private void generateNether(World world, Random rand, int i, int j) {

		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherUraniumuSpawn, 6, 0, 127, ModBlocks.ore_nether_uranium, Blocks.netherrack);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherTungstenSpawn, 10, 0, 127, ModBlocks.ore_nether_tungsten, Blocks.netherrack);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherSulfurSpawn, 12, 0, 127, ModBlocks.ore_nether_sulfur, Blocks.netherrack);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherPhosphorusSpawn, 6, 0, 127, ModBlocks.ore_nether_fire, Blocks.netherrack);
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherCoalSpawn, 32, 16, 96, ModBlocks.ore_nether_coal, Blocks.netherrack);
		
		if(GeneralConfig.enablePlutoniumOre)
			DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.netherPlutoniumSpawn, 4, 0, 127, ModBlocks.ore_nether_plutonium, Blocks.netherrack);

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
		DungeonToolbox.generateOre(world, rand, i, j, WorldConfig.endTikiteSpawn, 6, 0, 127, ModBlocks.ore_tikite, Blocks.end_stone);
	}

}
