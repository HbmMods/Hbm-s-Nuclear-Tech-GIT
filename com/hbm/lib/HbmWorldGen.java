package com.hbm.lib;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntitySafe;
import com.hbm.world.Antenna;
import com.hbm.world.Barrel;
import com.hbm.world.Bunker;
import com.hbm.world.CrashedVertibird;
import com.hbm.world.DesertAtom001;
import com.hbm.world.Dud;
import com.hbm.world.Factory;
import com.hbm.world.Geyser;
import com.hbm.world.GeyserLarge;
import com.hbm.world.LibraryDungeon;
import com.hbm.world.OilBubble;
import com.hbm.world.OilSandBubble;
import com.hbm.world.Radio01;
import com.hbm.world.Relay;
import com.hbm.world.Satellite;
import com.hbm.world.Sellafield;
import com.hbm.world.Silo;
import com.hbm.world.Spaceship;
import com.hbm.world.Vertibird;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
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
			if(MainRegistry.enableMDOres)
				generateSurface(world, rand, chunkX * 16, chunkZ * 16); break;
		}

	}

	private void generateSurface(World world, Random rand, int i, int j) {
		// Ore stains per chunk
		for (int k = 0; k < MainRegistry.uraniumSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			// Max height of generation
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			// Ore, amount of ore in one stain
			(new WorldGenMinable(ModBlocks.ore_uranium, 5)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.thoriumSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			// Max height of generation
			int randPosY = rand.nextInt(30);
			int randPosZ = j + rand.nextInt(16);

			// Ore, amount of ore in one stain
			(new WorldGenMinable(ModBlocks.ore_thorium, 5)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.titaniumSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_titanium, 6)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.sulfurSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_sulfur, 8)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.aluminiumSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(45);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_aluminium, 6)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.copperSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(50);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_copper, 6)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.fluoriteSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(40);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_fluorite, 4)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.niterSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_niter, 6)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.tungstenSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_tungsten, 8)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.leadSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_lead, 9)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.berylliumSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(35);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_beryllium, 4)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

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

		for (int k = 0; k < MainRegistry.niterSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_rare, 5)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		for (int k = 0; k < MainRegistry.ligniteSpawn; k++) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25) + 35;
			int randPosZ = j + rand.nextInt(16);

			(new WorldGenMinable(ModBlocks.ore_lignite, 24)).generate(world, rand, randPosX, randPosY, randPosZ);
		}

		/*if (MainRegistry.enableBarrels && rand.nextInt(5) == 0) {
			for (int k = 0; k < 1; k++) {
				int randPosX = i + rand.nextInt(16);
				int randPosY = rand.nextInt(25);
				int randPosZ = j + rand.nextInt(16);

				(new WorldGenMinable(ModBlocks.yellow_barrel, 10)).generate(world, rand, randPosX, randPosY, randPosZ);
			}
		}*/

		if (MainRegistry.enableDungeons) {

			BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(i, j);

			if (biome == BiomeGenBase.plains || biome == BiomeGenBase.desert) {
				if (rand.nextInt(MainRegistry.radioStructure) == 0) {
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
				if (rand.nextInt(MainRegistry.antennaStructure) == 0) {
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
				if (rand.nextInt(MainRegistry.atomStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new DesertAtom001().generate(world, rand, x, y, z);
					}
				}
			}

			if (biome == BiomeGenBase.desert) {
				if (rand.nextInt(MainRegistry.vertibirdStructure) == 0) {
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

			if (rand.nextInt(MainRegistry.dungeonStructure) == 0) {
				int x = i + rand.nextInt(16);
				int y = rand.nextInt(256);
				int z = j + rand.nextInt(16);
				new LibraryDungeon().generate(world, rand, x, y, z);
			}

			if (biome == BiomeGenBase.plains || biome == BiomeGenBase.desert) {
				if (rand.nextInt(MainRegistry.relayStructure) == 0) {
					for (int a = 0; a < 1; a++) {
						int x = i + rand.nextInt(16);
						int z = j + rand.nextInt(16);
						int y = world.getHeightValue(x, z);

						new Relay().generate(world, rand, x, y, z);
					}
				}
			}
			if (biome == BiomeGenBase.plains || biome == BiomeGenBase.desert) {
				if (rand.nextInt(MainRegistry.satelliteStructure) == 0) {
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

			if (rand.nextInt(MainRegistry.bunkerStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Bunker().generate(world, rand, x, y, z);
			}

			if (rand.nextInt(MainRegistry.siloStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Silo().generate(world, rand, x, y, z);
			}

			if (rand.nextInt(MainRegistry.factoryStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Factory().generate(world, rand, x, y, z);
			}

			if (rand.nextInt(MainRegistry.dudStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Dud().generate(world, rand, x, y, z);
			}

			if (rand.nextInt(MainRegistry.spaceshipStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Spaceship().generate(world, rand, x, y, z);
			}
			
			if (biome == BiomeGenBase.desert && rand.nextInt(MainRegistry.barrelStructure) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				new Barrel().generate(world, rand, x, y, z);
			}

			if (rand.nextInt(MainRegistry.broadcaster) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y, z).canPlaceTorchOnTop(world, x, y, z))
					world.setBlock(x, y + 1, z, ModBlocks.broadcaster_pc, rand.nextInt(4) + 2, 2);
				
				if(MainRegistry.enableDebugMode)
					MainRegistry.logger.info("[Debug] Successfully spawned corrupted broadcaster at " + x + " " + (y + 1) +" " + z);
			}

			if (MainRegistry.enableMines && rand.nextInt(MainRegistry.minefreq) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y, z).canPlaceTorchOnTop(world, x, y, z)) {
					world.setBlock(x, y + 1, z, ModBlocks.mine_ap);
				
					if(MainRegistry.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned landmine at " + x + " " + (y + 1) +" " + z);
				}
			}
			
			if (MainRegistry.enableRad && rand.nextInt(MainRegistry.radfreq) == 0 && biome == BiomeGenBase.desert) {
				
				for (int a = 0; a < 1; a++) {
					int x = i + rand.nextInt(16);
					int z = j + rand.nextInt(16);
					
					double r = rand.nextInt(15) + 10;
					
					if(rand.nextInt(50) == 0)
						r = 50;

					new Sellafield().generate(world, x, z, r, r * 0.35D);

					if(MainRegistry.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned raditation hotspot at " + x + " " + z);
				}
			}

			if (biome == BiomeGenBase.plains && rand.nextInt(20) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);
				
				if(world.getBlock(x, y - 1, z) == Blocks.grass)
					new Geyser().generate(world, rand, x, y, z);
			}

			if (biome == BiomeGenBase.desert && rand.nextInt(20) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y - 1, z) == Blocks.sand)
					new GeyserLarge().generate(world, rand, x, y, z);
			}

			if (rand.nextInt(20) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y - 1, z) == Blocks.stone)
					world.setBlock(x, y - 1, z, ModBlocks.geysir_vapor);
			}

			if (MainRegistry.enableVaults && rand.nextInt(MainRegistry.vaultfreq) == 0) {
				int x = i + rand.nextInt(16);
				int z = j + rand.nextInt(16);
				int y = world.getHeightValue(x, z);

				if(world.getBlock(x, y, z).canPlaceTorchOnTop(world, x, y, z)) {
					world.setBlock(x, y + 1, z, ModBlocks.safe, rand.nextInt(4) + 2, 2);
					
					switch(rand.nextInt(10)) {
					case 0:
					case 1:
					case 2:
					case 3:
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).setMod(1);
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(10), (TileEntitySafe)world.getTileEntity(x, y + 1, z), rand.nextInt(4) + 3);
						break;
					case 4:
					case 5:
					case 6:
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).setMod(0.1);
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(11), (TileEntitySafe)world.getTileEntity(x, y + 1, z), rand.nextInt(3) + 2);
						break;
					case 7:
					case 8:
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).setMod(0.02);
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(12), (TileEntitySafe)world.getTileEntity(x, y + 1, z), rand.nextInt(3) + 1);
						break;
					case 9:
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).setPins(rand.nextInt(999) + 1);
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).setMod(0.0);
						((TileEntitySafe)world.getTileEntity(x, y + 1, z)).lock();
						WeightedRandomChestContent.generateChestContents(rand, HbmChestContents.getLoot(13), (TileEntitySafe)world.getTileEntity(x, y + 1, z), rand.nextInt(2) + 1);
						break;
					}
					
					if(MainRegistry.enableDebugMode)
						MainRegistry.logger.info("[Debug] Successfully spawned safe at " + x + " " + (y + 1) +" " + z);
				}
				
			}
		}

		if (rand.nextInt(25) == 0) {
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(25);
			int randPosZ = j + rand.nextInt(16);

			OilBubble.spawnOil(world, randPosX, randPosY, randPosZ, 7 + rand.nextInt(9));
		}

		if (MainRegistry.enableNITAN) {

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

		for (int k = 0; k < 8; k++)
		{
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(127);
			int randPosZ = j + rand.nextInt(16);
			
			(new WorldGenMinable(ModBlocks.ore_nether_uranium, 6, Blocks.netherrack)).generate(world, rand, randPosX, randPosY, randPosZ);
		}
		if(MainRegistry.enablePlutoniumOre)
		{
			for (int k = 0; k < 6; k++)
			{
				int randPosX = i + rand.nextInt(16);
				int randPosY = rand.nextInt(127);
				int randPosZ = j + rand.nextInt(16);
			
				(new WorldGenMinable(ModBlocks.ore_nether_plutonium, 4, Blocks.netherrack)).generate(world, rand, randPosX, randPosY, randPosZ);
			}
		}
		for (int k = 0; k < 10; k++)
		{
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(127);
			int randPosZ = j + rand.nextInt(16);
			
			(new WorldGenMinable(ModBlocks.ore_nether_tungsten, 10, Blocks.netherrack)).generate(world, rand, randPosX, randPosY, randPosZ);
		}
		for (int k = 0; k < 26; k++)
		{
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(127);
			int randPosZ = j + rand.nextInt(16);
			
			(new WorldGenMinable(ModBlocks.ore_nether_sulfur, 12, Blocks.netherrack)).generate(world, rand, randPosX, randPosY, randPosZ);
		}
		for (int k = 0; k < 24; k++)
		{
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(127);
			int randPosZ = j + rand.nextInt(16);
			
			(new WorldGenMinable(ModBlocks.ore_nether_fire, 3, Blocks.netherrack)).generate(world, rand, randPosX, randPosY, randPosZ);
		}
	}

	private void generateEnd(World world, Random rand, int i, int j) {

		for (int k = 0; k < 8; k++)
		{
			int randPosX = i + rand.nextInt(16);
			int randPosY = rand.nextInt(127);
			int randPosZ = j + rand.nextInt(16);
			
			(new WorldGenMinable(ModBlocks.ore_tikite, 6, Blocks.end_stone)).generate(world, rand, randPosX, randPosY, randPosZ);
		}
	}

}
