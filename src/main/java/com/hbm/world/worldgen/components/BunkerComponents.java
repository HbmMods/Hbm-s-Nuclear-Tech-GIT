package com.hbm.world.worldgen.components;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.HbmChestContents;
import com.hbm.tileentity.network.TileEntityPylonBase;
import com.hbm.world.worldgen.components.ProceduralComponents.ProceduralComponent;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.util.ForgeDirection;

public class BunkerComponents extends ProceduralComponents {
	
	public BunkerComponents() {
		
		//Pass each respective method as a method reference (::) to the weight constructors, in order to boost clarity
		weightArray = new Weight[] {
			new Weight(30, -1, Corridor::findValidPlacement),  //Corridor and Wide version
			new Weight(10, -1, Intersection::findValidPlacement), //Intersection and wide version
			new Weight(5, 5, CenterCrossing::findValidPlacement),
			new Weight(3, 5, UtilityCloset::findValidPlacement) {
				public boolean canSpawnStructure(int componentAmount, int coordMode, ProceduralComponent component) {
					return (this.instanceLimit < 0 || this.instancesSpawned < this.instanceLimit) && componentAmount > 6; //prevent the gimping of necessary corridors
				}
			},
			new Weight(8, 4, SupplyRoom::findValidPlacement) {
				public boolean canSpawnStructure(int componentAmount, int coordMode, ProceduralComponent component) {
					return (this.instanceLimit < 0 || this.instancesSpawned < this.instanceLimit) && componentAmount > 6; //prevent the gimping of necessary corridors
				}
			},
			new Weight(3, 3, WasteDisposal::findValidPlacement) {
				public boolean canSpawnStructure(int componentAmount, int coordMode, ProceduralComponent component) {
					return (this.instanceLimit < 0 || this.instancesSpawned < this.instanceLimit) && componentAmount > 6; //prevent the gimping of necessary corridors
				}
			},
			new Weight(10, 15, Bedroom::findValidPlacement) {
				public boolean canSpawnStructure(int componentAmount, int coordMode, ProceduralComponent component) {
					return (this.instanceLimit < 0 || this.instancesSpawned < this.instanceLimit) && componentAmount > 6; //prevent the gimping of necessary corridors
				}
			},
			new Weight(20, 1, Reactor::findValidPlacement) {
				public boolean canSpawnStructure(int componentAmount, int coordMode, ProceduralComponent component) {
					return (this.instanceLimit < 0 || this.instancesSpawned < this.instanceLimit) && componentAmount > 4; //prevent the gimping of necessary corridors
				}
			},
			new Weight(20, 1, RTG::findValidPlacement) {
				public boolean canSpawnStructure(int componentAmount, int coordMode, ProceduralComponent component) {
					return (this.instanceLimit < 0 || this.instancesSpawned < this.instanceLimit) && componentAmount > 6; //prevent the gimping of necessary corridors
				}
			},
		};
		
		sizeLimit = 100;
		distanceLimit = 100;
	}
	
	public static abstract class Bunker extends ProceduralComponent {
		
		boolean underwater = false;
		
		public Bunker() { }
		
		public Bunker(int componentType) {
			super(componentType);
		}
		
		public void buildComponent(ProceduralComponents instance, ControlComponent original, List components, Random rand) {
			checkModifiers(original);
		}
		
		protected void checkModifiers(ControlComponent original) {
			if(original instanceof Atrium)
				this.underwater = ((Atrium) original).underwater;
		}
		
		protected void placeLamp(World world, StructureBoundingBox box, Random rand, int featureX, int featureY, int featureZ) {
			if(rand.nextInt(underwater ? 5 : 3) == 0) {
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_on, 0, featureX, featureY, featureZ, box);
				placeBlockAtCurrentPosition(world, Blocks.redstone_block, 0, featureX, featureY + 1, featureZ, box);
			} else
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_lamp_off, 0, featureX, featureY, featureZ, box);
		}
		
		protected void fillWithWater(World world, StructureBoundingBox box, Random rand, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int waterLevel) {
			
			if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
				return;
			
			waterLevel += getYWithOffset(minY) - 1;
			
			for(int x = minX; x <= maxX; x++) {
				
				for(int z = minZ; z <= maxZ; z++) {
					int posX = getXWithOffset(x, z);
					int posZ = getZWithOffset(x, z);
					
					if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
						for(int y = minY; y <= maxY; y++) {
							int posY = getYWithOffset(y);
							Block genTarget = world.getBlock(posX, posY, posZ);
							
							if(!genTarget.isAir(world, posX, posY, posZ))
								continue;
							
							if(posY <= waterLevel && world.getBlock(posX, posY - 1, posZ).getMaterial() != Material.air) {
								world.setBlock(posX, posY, posZ, Blocks.water, 0, 2);
								continue;
							}
							
							boolean canGenFluid = true;
							boolean canGenPlant = false;
							int canGenVine = -1;
							
							for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
								Block neighbor = world.getBlock(posX + dir.offsetX, posY + dir.offsetY, posZ + dir.offsetZ);
								boolean isSolid = neighbor.isNormalCube();
								
								switch(dir) {
								case DOWN: if(neighbor == Blocks.water) canGenPlant = true;
									if(!isSolid && neighbor != Blocks.water) canGenFluid = false;
									break;
								case UP: if(neighbor.getMaterial() != Material.air) canGenFluid = false; 
									if(isSolid) canGenVine = 0;
									break;
								case NORTH: if(isSolid) canGenVine |= 4;
									if(!isSolid && neighbor != Blocks.water) canGenFluid = false;
									break;
								case SOUTH: if(isSolid) canGenVine |= 1;
									if(!isSolid && neighbor != Blocks.water) canGenFluid = false;
									break;
								case EAST: if(isSolid) canGenVine |= 8;
									if(!isSolid && neighbor != Blocks.water) canGenFluid = false;
									break;
								case WEST: if(isSolid) canGenVine |= 2;
									if(!isSolid && neighbor != Blocks.water) canGenFluid = false;
									break;
								default: //shut the fuck up!
									if(!isSolid && neighbor != Blocks.water) canGenFluid = false;
									break;
								}
							}
							
							if(canGenFluid)
								world.setBlock(posX, posY, posZ, Blocks.water);
							else {
								 if(canGenVine >= 0) {
									if(rand.nextInt(3) == 0)
										canGenVine |= 1 << rand.nextInt(4);
									
									world.setBlock(posX, posY, posZ, Blocks.vine, canGenVine, 2);
									
									if(canGenVine > 0) {
										int i = posY;
										while(world.getBlock(posX, --i, posZ).getMaterial() == Material.air)
											world.setBlock(posX, i, posZ, Blocks.vine, canGenVine, 2);
									}
								 }
								 
								 if(canGenPlant) {
										int value = rand.nextInt(2);
										
										if(value <= 0) {
											int rY = posY + rand.nextInt(10) - rand.nextInt(10);
											if(rY == posY)
												world.setBlock(posX, posY, posZ, Blocks.waterlily, 0, 2);
										} else if(value <= 1) {
											int rY = posY + rand.nextInt(10) - rand.nextInt(10);
											if(rY == posY)
												world.setBlock(posX, posY, posZ, ModBlocks.reeds, 0, 2);
										}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static class UtilityCloset extends Bunker {
		
		boolean energy = false; //if false, this is a water closet. if true, this is an energy closet
		boolean hasLoot = false;
		
		public UtilityCloset() { }
		
		public UtilityCloset(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			
			energy = rand.nextBoolean();
			hasLoot = rand.nextInt(3) == 0;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				
				fillWithAir(world, box, 1, 1, 1, 3, 3, 2);
				//Floor
				placeBlockAtCurrentPosition(world, ModBlocks.vinyl_tile, 0, 2, 0, 0, box);
				fillWithBlocks(world, box, 1, 0, 1, 3, 0, 2, ModBlocks.vinyl_tile);
				//Wall
				fillWithBlocks(world, box, 0, 1, 1, 0, 1, 2, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 0, 2, 1, 0, 2, 2, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 0, 3, 1, 0, 3, 2, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 1, 1, 0, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 1, 2, 0, box);
				fillWithBlocks(world, box, 1, 3, 0, 3, 3, 0, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 3, 1, 0, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 3, 2, 0, box);
				fillWithBlocks(world, box, 4, 1, 1, 4, 1, 2, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 4, 2, 1, 4, 2, 2, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 4, 3, 1, 4, 3, 2, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 1, 3, 3, 1, 3, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 2, 3, 3, 2, 3, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 1, 3, 3, 3, 3, 3, ModBlocks.reinforced_brick);
				//Ceiling
				fillWithBlocks(world, box, 1, 4, 1, 3, 4, 2, ModBlocks.reinforced_brick);
				
				int decoMetaS = getDecoMeta(2);
				int decoMetaN = getDecoMeta(3);
				int decoMetaW = getDecoMeta(4);
				
				if(energy) {
					placeBlockAtCurrentPosition(world, ModBlocks.red_wire_coated, 0, 0, 3, 2, box);
					placeBlockAtCurrentPosition(world, ModBlocks.machine_transformer, 0, 1, 3, 2, box);
					placeBlockAtCurrentPosition(world, ModBlocks.red_connector, getDecoMeta(5), 2, 3, 2, box);
					fillWithMetadataBlocks(world, box, 2, 1, 2, 2, 2, 2, ModBlocks.red_connector, decoMetaW);
					
					makeConnection(world, 2, 3, 2, 2, 2, 2);
					makeConnection(world, 2, 2, 2, 2, 1, 2);
					
					fillWithMetadataBlocks(world, box,3, 1, 1, 3, 2, 1, ModBlocks.steel_wall, decoMetaS);
					placeBlockAtCurrentPosition(world, ModBlocks.steel_roof, 0, 3, 3, 2, box);
					placeBlockAtCurrentPosition(world, ModBlocks.cable_diode, decoMetaS, 3, 1, 2, box);
					placeBlockAtCurrentPosition(world, ModBlocks.cable_diode, decoMetaW, 3, 2, 2, box);
					placeBlockAtCurrentPosition(world, ModBlocks.deco_red_copper, 0, 3, 1, 3, box);
					placeBlockAtCurrentPosition(world, ModBlocks.deco_red_copper, 0, 4, 2, 2, box);
					
					int cabinetMeta = getDecoModelMeta(0);
					if(hasLoot)
						generateInvContents(world, box, rand, ModBlocks.filing_cabinet, cabinetMeta, 1, 1, 2, HbmChestContents.machineParts, 4);
				} else {
					fillWithMetadataBlocks(world, box, 1, 1, 2, 2, 1, 2, ModBlocks.deco_pipe_quad_green_rusted, getPillarMeta(4));
					placeBlockAtCurrentPosition(world, ModBlocks.machine_boiler_off, decoMetaN, 3, 1, 2, box);
					fillWithBlocks(world, box, 3, 2, 2, 3, 3, 2, ModBlocks.deco_pipe_rusted);
					
					int cabinetMeta = getDecoModelMeta(3);
					if(hasLoot)
						generateInvContents(world, box, rand, ModBlocks.filing_cabinet, cabinetMeta, 1, 1, 1, HbmChestContents.machineParts, 4);
				}
				
				//Door
				placeDoor(world, box, ModBlocks.door_bunker, 1, 2, 1, 0);
				
				fillWithCobwebs(world, box, rand, 1, 1, 1, 3, 3, 2);
				
				return true;
			}
		}
		
		protected void makeConnection(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
			int posX1 = getXWithOffset(x1, z1);
			int posY1 = getYWithOffset(y1);
			int posZ1 = getZWithOffset(x1, z1);
			
			int posX2 = getXWithOffset(x2, z2);
			int posY2 = getYWithOffset(y2);
			int posZ2 = getZWithOffset(x2, z2);
			
			TileEntity tile1 = world.getTileEntity(posX1, posY1, posZ1);
			TileEntity tile2 = world.getTileEntity(posX2, posY2, posZ2);
			if(tile1 instanceof TileEntityPylonBase && tile2 instanceof TileEntityPylonBase) {
				TileEntityPylonBase pylon1 = (TileEntityPylonBase)tile1;
				pylon1.addConnection(posX2, posY2, posZ2);
				TileEntityPylonBase pylon2 = (TileEntityPylonBase)tile2;
				pylon2.addConnection(posX1, posY1, posZ1);
			}
		}
		
		/** Finds valid placement, using input information. Should be passed as a method reference to its respective Weight. */
		public static ProceduralComponent findValidPlacement(List components, Random rand, int x, int y, int z, int mode, int type) {
			StructureBoundingBox box = getComponentToAddBoundingBox(x, y, z, -1, -1, 0, 5, 5, 4, mode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new UtilityCloset(type, rand, box, mode) : null;
		}
	}
	
	public static class SupplyRoom extends Bunker {
		
		BlockSelector crateSelector = new BlockSelector() {
			public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
				float chance = rand.nextFloat();
				
				if(chance < 0.001)
					this.field_151562_a = ModBlocks.crate_red;
				else if(chance < 0.05)
					this.field_151562_a = ModBlocks.crate_ammo;
				else if(chance < 0.10)
					this.field_151562_a = ModBlocks.crate_metal;
				else if(chance < 0.20)
					this.field_151562_a = ModBlocks.crate_weapon;
				else if(chance < 0.35)
					this.field_151562_a = ModBlocks.crate;
				else if(chance < 0.50)
					this.field_151562_a = ModBlocks.crate_can;
				else
					this.field_151562_a = Blocks.air;
			}
		};
		
		public SupplyRoom() { }
		
		public SupplyRoom(int componentType, Random rand, StructureBoundingBox box, int coordModeBase) {
			super(componentType);
			this.coordBaseMode = coordModeBase;
			this.boundingBox = box;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				fillWithAir(world, box, 5, 1, 0, 7, 3, 0);
				fillWithAir(world, box, 1, 1, 1, 11, 3, 11);
				
				//Floor
				fillWithMetadataBlocks(world, box, 1, 0, 2, 1, 0, 10, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 2, 0, 1, 3, 0, 11, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 4, 0, 1, 4, 0, 11, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 5, 0, 0, 7, 0, 10, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 5, 0, 11, 7, 0, 11, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 8, 0, 1, 8, 0, 11, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 9, 0, 1, 10, 0, 11, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 11, 0, 2, 11, 0, 10, ModBlocks.vinyl_tile, 1);
				//Walls
				fillWithBlocks(world, box, 2, 1, 0, 4, 1, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 2, 2, 0, 4, 2, 0, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 2, 3, 0, 4, 3, 0, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 1, 1, 1, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 1, 2, 1, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 1, 3, 1, box);
				fillWithBlocks(world, box, 0, 1, 2, 0, 1, 10, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 0, 2, 2, 0, 2, 10, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 0, 3, 2, 0, 3, 10, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 1, 1, 11, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 1, 2, 11, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 1, 3, 11, box);
				fillWithBlocks(world, box, 2, 1, 12, 10, 1, 12, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 2, 2, 12, 10, 2, 12, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 2, 3, 12, 10, 3, 12, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 8, 1, 0, 10, 1, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 8, 2, 0, 10, 2, 0, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 8, 3, 0, 10, 3, 0, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 11, 1, 1, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 11, 2, 1, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 11, 3, 1, box);
				fillWithBlocks(world, box, 12, 1, 2, 12, 1, 10, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 12, 2, 2, 12, 2, 10, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 12, 3, 2, 12, 3, 10, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 11, 1, 11, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 11, 2, 11, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 11, 3, 11, box);
				//Ceiling
				int pillarMetaNS = getPillarMeta(8);
				int pillarMetaWE = getPillarMeta(4);
				
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 5, 4, 0, box);
				fillWithBlocks(world, box, 2, 4, 1, 5, 4, 5, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 4, 2, 1, 4, 5, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 1, 4, 6, 5, 4, 6, ModBlocks.concrete_pillar, pillarMetaWE);
				fillWithBlocks(world, box, 1, 4, 7, 1, 4, 10, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 2, 4, 7, 5, 4, 11, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 7, 4, 0, box);
				fillWithBlocks(world, box, 7, 4, 1, 10, 4, 5, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 11, 4, 2, 11, 4, 5, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 7, 4, 6, 11, 4, 6, ModBlocks.concrete_pillar, pillarMetaWE);
				fillWithBlocks(world, box, 11, 4, 7, 11, 4, 10, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 7, 4, 7, 10, 4, 11, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 7, 4, 0, box);
				fillWithMetadataBlocks(world, box, 6, 4, 0, 6, 4, 2, ModBlocks.concrete_pillar, pillarMetaNS);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 5, 4, 0, box);
				for(int i = 3; i <= 9; i += 3) {
					placeLamp(world, box, rand, 6, 4, i);
					fillWithMetadataBlocks(world, box, 6, 4, i + 1, 6, 4, i + 2, ModBlocks.concrete_pillar, pillarMetaNS);
				}
				//Shelves Right
				for(int i = 1; i <= 11; i += i == 4 ? 4 : 3) {
					if(i % 2 == 0) {
						for(int j = 1; j <= 11; j += 10) {
							placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, i, 1, j, box);
							placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, i, 2, j, box);
							placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, i, 3, j, box);
						}
					}
					
					fillWithMetadataBlocks(world, box, i, 2, 2, i, 2, 4, ModBlocks.brick_slab, 8); //b100 = dense stone, top position
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, i, 1, 5, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, i, 2, 5, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, i, 3, 5, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, i, 1, 7, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, i, 2, 7, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, i, 3, 7, box);
					fillWithMetadataBlocks(world, box, i, 2, 8, i, 2, 10, ModBlocks.brick_slab, 8);
					//Crates
					fillWithRandomizedBlocks(world, box, i, 1, 2, i, 1, 4, rand, crateSelector);
					fillWithRandomizedBlocks(world, box, i, 3, 2, i, 3, 4, rand, crateSelector);
					if(i % 2 != 0) {
						placeBlockAtCurrentPosition(world, ModBlocks.brick_slab, 8, i, 2, 6, box); //middle shelf part
						fillWithRandomizedBlocks(world, box, i, 1, 6, i, 1, 6, rand, crateSelector);
						fillWithRandomizedBlocks(world, box, i, 3, 6, i, 3, 6, rand, crateSelector);
					}
					fillWithRandomizedBlocks(world, box, i, 1, 8, i, 1, 10, rand, crateSelector);
					fillWithRandomizedBlocks(world, box, i, 3, 8, i, 3, 10, rand, crateSelector);
				}
				
				fillWithMetadataBlocks(world, box, 5, 2, 11, 7, 2, 11, ModBlocks.brick_slab, 8);
				generateInvContents(world, box, rand, ModBlocks.crate_iron, 6, 3, 11, HbmChestContents.machineParts, 10);
				generateInvContents(world, box, rand, ModBlocks.crate_iron, 7, 1, 11, HbmChestContents.vault1, 8);
				
				if(underwater) {
					fillWithWater(world, box, rand, 1, 1, 1, 11, 3, 11, 1);
					fillWithWater(world, box, rand, 5, 1, 0, 7, 3, 0, 1);
				} else
					fillWithCobwebs(world, box, rand, 1, 1, 0, 11, 3, 11);
				
				return true;
			}
		}
		
		/** Finds valid placement, using input information. Should be passed as a method reference to its respective Weight. */
		public static ProceduralComponent findValidPlacement(List components, Random rand, int x, int y, int z, int mode, int type) {
			StructureBoundingBox box = getComponentToAddBoundingBox(x, y, z, -5, -1, 0, 13, 6, 13, mode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new SupplyRoom(type, rand, box, mode) : null;
		}
	}
	//what 'waste'?
	public static class WasteDisposal extends Bunker {
		
		public WasteDisposal() { }
		
		public WasteDisposal(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				//Floor
				placeBlockAtCurrentPosition(world, ModBlocks.vinyl_tile, 1, 4, 0, 0, box);
				fillWithBlocks(world, box, 1, 0, 1, 1, 0, 2, ModBlocks.concrete_brick_slab);
				fillWithBlocks(world, box, 1, 0, 3, 1, 0, 7, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 2, 0, 1, 2, 0, 7, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 3, 0, 1, 5, 0, 3, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 3, 0, 4, 5, 0, 4, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 3, 0, 5, 5, 0, 7, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 6, 0, 1, 6, 0, 7, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 7, 0, 1, 7, 0, 2, ModBlocks.concrete_brick_slab);
				fillWithBlocks(world, box, 7, 0, 3, 7, 0, 7, ModBlocks.vinyl_tile);
				//Wall
				fillWithBlocks(world, box, 1, 0, 0, 1, 1, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 2, 1, 0, 3, 1, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 2, 0, 3, 2, 0, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 7, 0, 0, 7, 1, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 5, 1, 0, 6, 1, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 5, 2, 0, 7, 2, 0, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 1, 3, 0, 7, 3, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 0, 0, 1, 0, 1, 2, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 0, 1, 3, 0, 1, 7, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 0, 2, 1, 0, 2, 7, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 0, 3, 1, 0, 3, 7, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 1, 8, 7, 1, 8, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 2, 8, 7, 2, 8, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 1, 3, 8, 7, 3, 8, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 8, 1, 3, 8, 1, 7, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 8, 0, 1, 8, 1, 2, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 8, 2, 1, 8, 2, 7, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 8, 3, 1, 8, 3, 7, ModBlocks.reinforced_brick);
				//Ceiling
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 4, 1, box);
				fillWithBlocks(world, box, 1, 4, 1, 3, 4, 7, ModBlocks.reinforced_brick);
				placeLamp(world, box, rand, 4, 4, 2);
				fillWithBlocks(world, box, 4, 4, 3, 4, 4, 7, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 5, 4, 1, 7, 4, 7, ModBlocks.reinforced_brick);
				//Decorations
				fillWithMetadataBlocks(world, box, 1, 1, 1, 1, 1, 2, ModBlocks.steel_grate, 7);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, getDecoMeta(3), 1, 1, 3, box);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, getDecoMeta(2), 1, 1, 5, box);
				fillWithMetadataBlocks(world, box, 1, 1, 6, 1, 1, 7, ModBlocks.steel_grate, 7);
				generateInvContents(world, box, rand, ModBlocks.crate_iron, 1, 2, 7, HbmChestContents.filingCabinet, 10);
				fillWithMetadataBlocks(world, box, 7, 1, 1, 7, 1, 2, ModBlocks.steel_grate, 7);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, getDecoMeta(3), 7, 1, 3, box);
				placeRandomBobble(world, box, rand, 7, 2, 1);
				//Cremator
				int pillarMetaWE = getPillarMeta(4);
				
				//fillWithMetadataBlocks(world, box, 3, 1, 5, 5, 1, 7, ModBlocks.heater_firebox, 4);
				//placeholder, but how the hell am i to guarantee that this multiblock shit won't spill into unloaded chunks?
				fillWithBlocks(world, box, 3, 1, 5, 3, 1, 7, ModBlocks.brick_fire);
				fillWithMetadataBlocks(world, box, 4, 1, 5, 4, 1, 6, ModBlocks.brick_slab, 6);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_fire, 0, 4, 1, 7, box);
				fillWithBlocks(world, box, 5, 1, 5, 5, 1, 7, ModBlocks.brick_fire);
				//i genuinely cannot be bothered to go to the effort of block selectors for the stairs n shit
				fillWithMetadataBlocks(world, box, 3, 2, 5, 3, 2, 6, ModBlocks.brick_concrete_stairs, getStairMeta(1));
				placeBlockAtCurrentPosition(world, ModBlocks.brick_concrete_stairs, getStairMeta(2), 3, 2, 7, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_brick_slab, 0, 4, 2, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_concrete_stairs, getStairMeta(2), 4, 2, 7, box);
				fillWithMetadataBlocks(world, box, 5, 2, 5, 5, 2, 6, ModBlocks.brick_concrete_stairs, getStairMeta(0));
				placeBlockAtCurrentPosition(world, ModBlocks.brick_concrete_stairs, getStairMeta(2), 5, 2, 7, box);
				fillWithBlocks(world, box, 3, 3, 5, 3, 3, 7, ModBlocks.brick_concrete);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_brick_slab, 8, 4, 3, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.brick_concrete, 0, 4, 3, 7, box);
				fillWithBlocks(world, box, 5, 3, 5, 5, 3, 7, ModBlocks.brick_concrete);
				fillWithMetadataBlocks(world, box, 1, 3, 6, 2, 3, 6, ModBlocks.deco_pipe_quad_rusted, pillarMetaWE);
				fillWithMetadataBlocks(world, box, 6, 3, 6, 7, 3, 6, ModBlocks.deco_pipe_quad_rusted, pillarMetaWE);
				placeDoor(world, box, ModBlocks.door_bunker, 1, 4, 1, 0);
				
				if(underwater) { //against all odds, building remains unflooded
					fillWithWater(world, box, rand, 1, 1, 1, 7, 3, 7, 0);
				} else {
					fillWithCobwebs(world, box, rand, 1, 1, 1, 7, 3, 7);
				}
				
				return true;
			}
		}
		
		/** Finds valid placement, using input information. Should be passed as a method reference to its respective Weight. */
		public static ProceduralComponent findValidPlacement(List components, Random rand, int x, int y, int z, int mode, int type) {
			StructureBoundingBox box = getComponentToAddBoundingBox(x, y, z, -3, -1, 0, 9, 6, 9, mode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new WasteDisposal(type, rand, box, mode) : null;
		}
	}
	
	public static class Bedroom extends Bunker {
		
		public Bedroom() { }
		
		public Bedroom(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				//Floor
				placeBlockAtCurrentPosition(world, ModBlocks.vinyl_tile, 1, 2, 0, 0, box);
				fillWithMetadataBlocks(world, box, 1, 0, 1, 6, 0, 8, ModBlocks.vinyl_tile, 1);
				//Wall
				fillWithBlocks(world, box, 3, 1, 0, 6, 1, 0, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 1, 1, 0, box);
				fillWithBlocks(world, box, 3, 2, 0, 6, 3, 0, ModBlocks.concrete_colored);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored, 0, 2, 3, 0, box);
				fillWithBlocks(world, box, 1, 2, 0, 1, 3, 0, ModBlocks.concrete_colored);
				fillWithBlocks(world, box, 0, 1, 1, 0, 1, 8, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 0, 2, 1, 0, 3, 8, ModBlocks.concrete_colored);
				fillWithBlocks(world, box, 1, 1, 9, 6, 1, 9, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 2, 9, 3, 3, 9, ModBlocks.concrete_colored);
				fillWithBlocks(world, box, 4, 2, 9, 6, 2, 9, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 4, 3, 9, 6, 3, 9, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 7, 1, 1, 7, 1, 8, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 7, 2, 1, 7, 3, 4, ModBlocks.concrete_colored);
				fillWithBlocks(world, box, 7, 2, 5, 7, 2, 8, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 7, 3, 5, 7, 3, 8, ModBlocks.reinforced_brick);
				//Interior Wall
				fillWithBlocks(world, box, 5, 1, 5, 6, 1, 5, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 5, 2, 5, 6, 3, 5, ModBlocks.concrete_colored);
				fillWithBlocks(world, box, 4, 1, 6, 4, 1, 7, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 4, 2, 6, 4, 3, 7, ModBlocks.concrete_colored);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_colored, 0, 4, 3, 8, box);
				//Ceiling
				fillWithBlocks(world, box, 1, 4, 1, 6, 4, 1, ModBlocks.concrete);
				for(int i = 1; i <= 4; i += 3) {
					fillWithBlocks(world, box, i, 4, 2, i, 4, 3, ModBlocks.concrete);
					placeLamp(world, box, rand, i + 1, 4, 2);
					placeLamp(world, box, rand, i + 1, 4, 3);
					fillWithBlocks(world, box, i + 2, 4, 2, i + 2, 4, 3, ModBlocks.concrete);
				}
				fillWithBlocks(world, box, 1, 4, 4, 1, 4, 8, ModBlocks.concrete);
				fillWithBlocks(world, box, 2, 4, 4, 2, 4, 6, ModBlocks.concrete);
				placeLamp(world, box, rand, 2, 4, 7);
				fillWithBlocks(world, box, 2, 4, 8, 4, 4, 8, ModBlocks.concrete);
				fillWithBlocks(world, box, 3, 4, 4, 4, 4, 7, ModBlocks.concrete);
				fillWithBlocks(world, box, 5, 4, 4, 6, 4, 5, ModBlocks.concrete);
				fillWithBlocks(world, box, 5, 4, 6, 5, 4, 8, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 6, 4, 6, box);
				placeLamp(world, box, rand, 6, 4, 7);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 6, 4, 8, box);
				
				//Decorations
				//Bathroom
				placeDoor(world, box, ModBlocks.door_metal, 4, 4, 1, 8);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 9, 5, 1, 6, box);
				placeBlockAtCurrentPosition(world, Blocks.cauldron, 0, 6, 1, 6, box);
				placeBlockAtCurrentPosition(world, Blocks.tripwire_hook, getTripwireMeta(0), 6, 2, 6, box);
				placeBlockAtCurrentPosition(world, Blocks.hopper, getDecoMeta(3), 6, 1, 8, box);
				placeBlockAtCurrentPosition(world, Blocks.trapdoor, getDecoModelMeta(0), 6, 2, 8, box);
				//Furnishing
				placeBed(world, box, 2, 4, 1, 2);
				placeBed(world, box, 2, 5, 1, 2);
				fillWithMetadataBlocks(world, box, 6, 1, 1, 6, 1, 4, ModBlocks.concrete_slab, 9);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(1), 5, 1, 4, box);
				placeBlockAtCurrentPosition(world, ModBlocks.radiorec, getDecoMeta(5), 6, 2, 1, box);
				placeRandomBobble(world, box, rand, 6, 2, 3);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(2), 6, 2, 4, box);
				generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(0), 4, 1, 5, HbmChestContents.officeTrash, 4); //TODO: create more contents
				placeBlockAtCurrentPosition(world, ModBlocks.filing_cabinet, getDecoModelMeta(0), 4, 1, 5, box);
				//
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_stairs, getStairMeta(7), 1, 1, 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_stairs, getStairMeta(6), 1, 1, 3, box);
				placeBlockAtCurrentPosition(world, ModBlocks.machine_electric_furnace_off, 5, 1, 1, 4, box);
				fillWithMetadataBlocks(world, box, 1, 3, 2, 1, 3, 4, ModBlocks.reinforced_brick_stairs, getStairMeta(1));
				//
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(3), 1, 1, 6, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_stairs, getStairMeta(4), 2, 1, 7, box);
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_slab, 9, 1, 1, 7, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(2), 1, 1, 8, box);
				
				placeDoor(world, box, ModBlocks.door_metal, 1, 2, 1, 0);
				
				if(underwater) { //against all odds, building remains unflooded
					fillWithWater(world, box, rand, 1, 1, 1, 6, 3, 8, 0);
				} else {
					fillWithCobwebs(world, box, rand, 1, 1, 1, 6, 3, 8);
				}
				
				return true;
			}
		}
		
		/** Finds valid placement, using input information. Should be passed as a method reference to its respective Weight. */
		public static ProceduralComponent findValidPlacement(List components, Random rand, int x, int y, int z, int mode, int type) {
			StructureBoundingBox box = getComponentToAddBoundingBox(x, y, z, -1, -1, 0, 8, 6, 10, mode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new Bedroom(type, rand, box, mode) : null;
		}
	}
	
	public static class CenterCrossing extends Bunker {
		
		BlockSelector plantSelector = new BlockSelector() {
			public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
				int chance = rand.nextInt(10);
				
				switch(chance) {
				case 0:
					this.field_151562_a = Blocks.yellow_flower;
					break;
				case 1:
				case 2:
				case 3:
				case 4:
					this.field_151562_a = Blocks.red_flower;
					this.selectedBlockMetaData = rand.nextInt(9);
					break;
				case 5:
				case 6:
					this.field_151562_a = Blocks.tallgrass;
					this.selectedBlockMetaData = rand.nextInt(2) + 1;
					break;
				default:
					this.field_151562_a = Blocks.air;
				}
			}
		};
		
		boolean expandsNX;
		boolean expandsPX;
		
		int decorationType = 0;
		
		public CenterCrossing() { }
		
		public CenterCrossing(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			this.decorationType = rand.nextInt(2);
		}
		
		protected void func_143012_a(NBTTagCompound data) {
			super.func_143012_a(data);
			data.setBoolean("expandsNX", expandsNX);
			data.setBoolean("expandsPX", expandsPX);
			data.setInteger("decoration", decorationType);
		}
		
		protected void func_143011_b(NBTTagCompound data) {
			super.func_143011_b(data);
			expandsNX = data.getBoolean("expandsNX");
			expandsPX = data.getBoolean("expandsPX");
			decorationType = data.getInteger("decoration");
		}
		
		@Override
		public void buildComponent(ProceduralComponents instance, ControlComponent original, List components, Random rand) {
			checkModifiers(original);
			
			StructureComponent componentN = getNextComponentNX(instance, original, components, rand, 3, 1);
			expandsNX = componentN != null;
			
			StructureComponent componentP = getNextComponentPX(instance, original, components, rand, 7, 1);
			expandsPX = componentP != null;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				fillWithAir(world, box, 6, 1, 0, 8, 3, 0);
				fillWithAir(world, box, 1, 1, 1, 13, 3, 11);
				fillWithAir(world, box, 2, 4, 2, 12, 5, 10);
				
				//Floor
				fillWithMetadataBlocks(world, box, 6, 0, 0, 8, 0, 0, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 1, 0, 1, 4, 0, 1, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 1, 0, 2, 5, 0, 2, ModBlocks.vinyl_tile, 1);
				placeBlockAtCurrentPosition(world, ModBlocks.vinyl_tile, 1, 5, 0, 1, box);
				fillWithBlocks(world, box, 6, 0, 1, 8, 0, 2, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 9, 0, 2, 13, 0, 2, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 10, 0, 1, 13, 0, 1, ModBlocks.vinyl_tile);
				placeBlockAtCurrentPosition(world, ModBlocks.vinyl_tile, 1, 9, 0, 1, box);
				fillWithBlocks(world, box, 1, 0, 3, 13, 0, 5, ModBlocks.vinyl_tile);
				fillWithBlocks(world, box, 1, 0, 7, 3, 0, 11, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 1, 0, 6, 13, 0, 6, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 4, 0, 7, 10, 0, 11, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 11, 0, 7, 13, 0, 11, ModBlocks.vinyl_tile);
				//Wall
				fillWithBlocks(world, box, 1, 1, 0, 5, 1, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 2, 0, 5, 2, 0, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 1, 3, 0, 5, 3, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 4, 0, 13, 4, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 9, 1, 0, 13, 1, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 9, 2, 0, 13, 2, 0, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 9, 3, 0, 13, 3, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 1, 12, 13, 1, 12, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 2, 12, 13, 2, 12, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 1, 3, 12, 13, 4, 12, ModBlocks.reinforced_brick);
				//Upper lining
				for(int i = 1; i <= 11; i += 10) {
					fillWithMetadataBlocks(world, box, 1, 4, i, 13, 4, i, ModBlocks.brick_slab, 8);
					fillWithBlocks(world, box, 8, 5, i, 13, 5, i, ModBlocks.reinforced_brick);
					placeLamp(world, box, rand, 7, 5, i);
					fillWithBlocks(world, box, 1, 5, i, 6, 5, i, ModBlocks.reinforced_brick);
				}
				
				for(int i = 1; i <= 13; i += 12) {
					fillWithMetadataBlocks(world, box, i, 4, 2, i, 4, 10, ModBlocks.brick_slab, 8);
					fillWithBlocks(world, box, i, 5, 2, i, 5, 5, ModBlocks.reinforced_brick);
					placeLamp(world, box, rand, i, 5, 6);
					fillWithBlocks(world, box, i, 5, 7, i, 5, 10, ModBlocks.reinforced_brick);
				}
				//Ceiling
				int pillarMeta = getPillarMeta(4);
				
				fillWithBlocks(world, box, 2, 6, 2, 12, 6, 5, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 2, 6, 7, 12, 6, 10, ModBlocks.reinforced_brick);
				for(int i = 2; i <= 10; i += 2) {
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMeta, i, 6, 6, box);
					placeLamp(world, box, rand, i + 1, 6, 6);
				}
				placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMeta, 12, 6, 6, box);
				
				if(expandsNX) {
					fillWithMetadataBlocks(world, box, 0, 0, 3, 0, 0, 5, ModBlocks.vinyl_tile, 1); //Floor
					fillWithBlocks(world, box, 0, 1, 1, 0, 1, 2, ModBlocks.reinforced_brick); //Wall
					fillWithBlocks(world, box, 0, 2, 1, 0, 2, 2, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 1, 0, 3, 2, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 4, 1, 0, 4, 11, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 1, 6, 0, 1, 11, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, 6, 0, 2, 11, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 6, 0, 3, 11, ModBlocks.reinforced_brick);
					fillWithAir(world, box, 0, 1, 3, 0, 3, 5);
				} else {
					fillWithBlocks(world, box, 0, 1, 1, 0, 1, 11, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, 1, 0, 2, 11, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 1, 0, 4, 11, ModBlocks.reinforced_brick);
				}
				
				if(expandsPX) {
					fillWithMetadataBlocks(world, box, 14, 0, 3, 14, 0, 5, ModBlocks.vinyl_tile, 1); //Floor
					fillWithBlocks(world, box, 14, 1, 1, 14, 1, 2, ModBlocks.reinforced_brick); //Wall
					fillWithBlocks(world, box, 14, 2, 1, 14, 2, 2, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 14, 3, 1, 14, 3, 2, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 14, 4, 1, 14, 4, 11, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 14, 1, 6, 14, 1, 11, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 14, 2, 6, 14, 2, 11, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 14, 3, 6, 14, 3, 11, ModBlocks.reinforced_brick);
					fillWithAir(world, box, 14, 1, 3, 14, 3, 5);
				} else {
					fillWithBlocks(world, box, 14, 1, 1, 14, 1, 11, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 14, 2, 1, 14, 2, 11, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 14, 3, 1, 14, 4, 11, ModBlocks.reinforced_brick);
				}
				
				//Decorations
				switch(decorationType) {
				case 0:
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(3), 1, 1, 8, box); //Bench 1
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(1), 1, 1, 9, box);
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(2), 1, 1, 10, box);
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(3), 4, 1, 8, box); //Bench 2
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(0), 4, 1, 9, box);
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(2), 4, 1, 10, box);
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(3), 10, 1, 8, box); //Bench 3
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(1), 10, 1, 9, box);
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(2), 10, 1, 10, box);
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(3), 13, 1, 8, box); //Bench 4
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(0), 13, 1, 9, box);
					placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(2), 13, 1, 10, box);
					//Fountain
					fillWithBlocks(world, box, 5, 1, 8, 5, 1, 11, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 1, 7, 8, 1, 7, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 9, 1, 8, 9, 1, 11, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 7, 1, 9, 7, 2, 9, ModBlocks.concrete_pillar);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_light, 0, 7, 3, 9, box);
					placeBlockAtCurrentPosition(world, Blocks.flowing_water, 0, 7, 4, 9, box); //meh! regular stronghold does it too
					fillWithMetadataBlocks(world, box, 5, 2, 9, 5, 2, 10, ModBlocks.brick_slab, 1);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick_stairs, getStairMeta(2), 5, 2, 11, box);
					fillWithMetadataBlocks(world, box, 9, 2, 9, 9, 2, 10, ModBlocks.brick_slab, 1);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick_stairs, getStairMeta(2), 9, 2, 11, box);
					break;
				case 1:
					int stairMetaW = getStairMeta(0);
					int stairMetaE = getStairMeta(1);
					int stairMetaN = getStairMeta(2);
					int stairMetaS = getStairMeta(3);
					
					//Right Planter
					fillWithBlocks(world, box, 1, 1, 1, 4, 1, 1, Blocks.grass);
					fillWithMetadataBlocks(world, box, 1, 1, 2, 3, 1, 2, ModBlocks.reinforced_brick_stairs, stairMetaS);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 1, 2, box);
					fillWithMetadataBlocks(world, box, 5, 1, 1, 5, 1, 2, ModBlocks.reinforced_brick_stairs, stairMetaE);
					//Left Planter
					fillWithBlocks(world, box, 10, 1, 1, 13, 1, 1, Blocks.grass);
					fillWithMetadataBlocks(world, box, 11, 1, 2, 13, 1, 2, ModBlocks.reinforced_brick_stairs, stairMetaS);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 10, 1, 2, box);
					fillWithMetadataBlocks(world, box, 9, 1, 1, 9, 1, 2, ModBlocks.reinforced_brick_stairs, stairMetaW);
					//Main planter with conversation pits
					fillWithBlocks(world, box, 1, 1, 7, 2, 1, 11, Blocks.grass); //Planter
					fillWithBlocks(world, box, 7, 1, 8, 7, 1, 10, Blocks.grass);
					fillWithBlocks(world, box, 12, 1, 7, 13, 1, 10, Blocks.grass);
					fillWithBlocks(world, box, 3, 1, 11, 13, 1, 11, Blocks.grass);
					fillWithBlocks(world, box, 1, 1, 6, 2, 1, 6, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 3, 1, 6, 3, 1, 7, ModBlocks.reinforced_brick);
					fillWithMetadataBlocks(world, box, 6, 1, 7, 8, 1, 7, ModBlocks.reinforced_brick_stairs, stairMetaN);
					fillWithBlocks(world, box, 11, 1, 6, 11, 1, 7, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 12, 1, 6, 13, 1, 6, ModBlocks.reinforced_brick);
					for(int i = 3; i <= 8; i += 5) { //Conversation pits
						fillWithMetadataBlocks(world, box, i, 1, 10, i + 3, 1, 10, Blocks.spruce_stairs, stairMetaN);
						placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaE, i, 1, 9, box);
						placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaS, i, 1, 8, box);
						placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaW, i + 3, 1, 9, box);
						placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, stairMetaS, i + 3, 1, 8, box);
					}
					//Hanging lights
					for(int i = 4; i <= 10; i += 3) {
						fillWithBlocks(world, box, i, 4, 9, i, 5, 9, ModBlocks.chain);
						placeBlockAtCurrentPosition(world, ModBlocks.reinforced_light, 0, i, 3, 9, box);
					}
					for(int i = 3; i <= 11; i += 8) {
						fillWithBlocks(world, box, i, 4, 2, i, 5, 2, ModBlocks.chain);
						placeBlockAtCurrentPosition(world, ModBlocks.reinforced_light, 0, i, 3, 2, box);
					}
					//Plant life
					fillWithRandomizedBlocks(world, box, 1, 2, 1, 4, 2, 1, rand, plantSelector);
					fillWithRandomizedBlocks(world, box, 10, 2, 1, 13, 2, 1, rand, plantSelector);
					fillWithRandomizedBlocks(world, box, 1, 2, 7, 2, 2, 11, rand, plantSelector);
					fillWithRandomizedBlocks(world, box, 3, 2, 11, 11, 2, 11, rand, plantSelector);
					fillWithRandomizedBlocks(world, box, 12, 2, 7, 13, 2, 11, rand, plantSelector);
					fillWithRandomizedBlocks(world, box, 7, 2, 8, 7, 2, 10, rand, plantSelector);
					break;
				}
				
				if(!underwater)
					fillWithCobwebs(world, box, rand, 0, 1, 0, 14, 5, 12);
				else {
					fillWithWater(world, box, rand, 6, 1, 0, 8, 3, 0, 1);
					fillWithWater(world, box, rand, 0, 1, 3, 0, 3, 5, 1);
					fillWithWater(world, box, rand, 14, 1, 3, 14, 3, 5, 1);
					fillWithWater(world, box, rand, 1, 1, 1, 13, 5, 11, 1);
				}
				
				return true;
			}
		}
		
		/** Finds valid placement, using input information. Should be passed as a method reference to its respective Weight. */
		public static ProceduralComponent findValidPlacement(List components, Random rand, int x, int y, int z, int mode, int type) {
			StructureBoundingBox box = getComponentToAddBoundingBox(x, y, z, -6, -1, 0, 15, 8, 13, mode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new CenterCrossing(type, rand, box, mode) : null;
		}
	}
	
	//This one will be a doozy
	public static class Reactor extends Bunker {
		
		boolean destroyed = false;
		
		static BlockSelector coriumSelector = new BlockSelector() {
			public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
				float chance = rand.nextFloat();
				
				if(chance < 0.10)
					this.field_151562_a = ModBlocks.block_corium;
				else if(chance < 0.70)
					this.field_151562_a = ModBlocks.block_corium_cobble;
				else
					this.field_151562_a = Blocks.gravel;
			}
		};
		
		private static ConcreteBricks conBrick = new ConcreteBricks();
		private static ConcreteBricksStairs conBrickStairs = new ConcreteBricksStairs();
		
		public Reactor() { }
		
		public Reactor(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
		}
		
		protected void func_143012_a(NBTTagCompound data) {
			super.func_143012_a(data);
			data.setBoolean("destroyed", destroyed);
		}
		
		protected void func_143011_b(NBTTagCompound data) {
			super.func_143011_b(data);
			destroyed = data.getBoolean("destroyed");
		}
		
		@Override
		public void buildComponent(ProceduralComponents instance, ControlComponent original, List components, Random rand) {
			checkModifiers(original);
			
			destroyed = underwater ? rand.nextInt(5) == 0 : false;
			//destroyed = true;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				fillWithAir(world, box, 5, 2, 0, 7, 4, 0);
				fillWithAir(world, box, 3, 1, 3, 9, 1, 9);
				fillWithAir(world, box, 1, 2, 1, 11, 7, 11);
				fillWithAir(world, box, 12, 2, 3, 14, 4, 7);
				//Floor
				fillWithBlocks(world, box, 3, 0, 3, 9, 0, 9, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 5, 1, 0, 7, 1, 0, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 1, 1, 1, 11, 1, 1, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 3, 1, 2, 10, 1, 2, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 1, 1, 2, 1, 1, 10, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 2, 1, 2, 2, 1, 10, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 1, 1, 11, 11, 1, 11, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 3, 1, 10, 10, 1, 10, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 11, 1, 2, 11, 1, 10, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 10, 1, 3, 10, 1, 9, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 13, 1, 3, 14, 1, 7, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 12, 1, 5, 12, 1, 7, ModBlocks.vinyl_tile, 1);
				//Walls
				fillWithBlocks(world, box, 1, 2, 0, 4, 2, 0, ModBlocks.reinforced_brick); //jesus christ
				fillWithBlocks(world, box, 1, 3, 0, 4, 3, 0, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 1, 4, 0, 4, 4, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 8, 2, 0, 11, 2, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 8, 3, 0, 11, 3, 0, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 8, 4, 0, 11, 4, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 5, 0, 11, 7, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 0, 2, 1, 0, 2, 11, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 0, 3, 1, 0, 3, 11, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 0, 4, 1, 0, 7, 11, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 2, 12, 11, 2, 12, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 3, 12, 11, 3, 12, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 1, 4, 12, 11, 7, 12, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 12, 2, 8, 12, 2, 11, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 12, 3, 8, 12, 3, 11, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 12, 2, 1, 12, 2, 4, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 12, 3, 1, 12, 3, 4, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 12, 4, 1, 12, 7, 11, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 13, 2, 8, 14, 2, 8, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 13, 3, 8, 14, 3, 8, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 13, 4, 8, 14, 4, 8, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 15, 2, 3, 15, 2, 7, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 15, 3, 3, 15, 3, 7, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 15, 4, 3, 15, 4, 7, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 14, 2, 2, 14, 4, 2, ModBlocks.red_wire_coated);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 13, 2, 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 13, 3, 2, box);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 13, 4, 2, box);
				//Ceiling
				fillWithBlocks(world, box, 1, 8, 1, 11, 8, 4, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 8, 5, 4, 8, 7, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 8, 8, 11, 8, 11, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 8, 8, 5, 11, 8, 7, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 13, 5, 3, 14, 5, 7, ModBlocks.reinforced_brick);
				for(int i = 5; i <= 7; i++) {
					for(int j = 5; j <= 7; j++) {
						if(i != 6 && j != 6 || i == 6 && j == 6) placeLamp(world, box, rand, i, 8, j);
						else placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, i, 8, j, box);
					}
				}
				
				//Reactor Casing
				int decoMetaS = getDecoMeta(2);
				int decoMetaN = getDecoMeta(3);
				int decoMetaE = getDecoMeta(4);
				int decoMetaW = getDecoMeta(5);
				for(int i = 4; i <= 8; i += 4) {
					for(int j = 4; j <= 8; j += 4) {
						placeBlockAtCurrentPosition(world, ModBlocks.steel_scaffold, decoMetaN, i, 1, j, box);
						fillWithMetadataBlocks(world, box, i, 5, j, i, 7, j, ModBlocks.steel_scaffold, decoMetaN);
					}
				}
				int stairMetaW = getStairMeta(0);
				int stairMetaE = getStairMeta(1);
				int stairMetaN = getStairMeta(2);
				int stairMetaS = getStairMeta(3);
				fillWithBlocks(world, box, 6, 1, 5, 6, 1, 7, ModBlocks.deco_pipe_quad_rusted);
				fillWithBlocks(world, box, 6, 2, 5, 6, 2, 7, ModBlocks.reactor_conductor);
				fillWithRandomizedBlocks(world, box, 4, 2, 5, 5, 2, 7, rand, conBrick);
				fillWithRandomizedBlocks(world, box, 5, 2, 4, 7, 2, 4, rand, conBrick);
				fillWithRandomizedBlocks(world, box, 5, 2, 8, 7, 2, 8, rand, conBrick);
				fillWithRandomizedBlocks(world, box, 7, 2, 5, 8, 2, 7, rand, conBrick);
				fillWithRandomizedBlocksMeta(world, box, 5, 2, 3, 7, 2, 3, rand, conBrickStairs, stairMetaN | 4);
				fillWithRandomizedBlocks(world, box, 5, 3, 3, 7, 4, 3, rand, conBrick);
				fillWithRandomizedBlocks(world, box, 4, 2, 4, 4, 4, 4, rand, conBrick);
				fillWithRandomizedBlocksMeta(world, box, 3, 2, 5, 3, 2, 7, rand, conBrickStairs, stairMetaW | 4);
				fillWithRandomizedBlocks(world, box, 3, 3, 5, 3, 4, 7, rand, conBrick);
				fillWithRandomizedBlocks(world, box, 4, 2, 8, 4, 4, 8, rand, conBrick);
				fillWithRandomizedBlocksMeta(world, box, 5, 2, 9, 7, 2, 9, rand, conBrickStairs, stairMetaS | 4);
				fillWithRandomizedBlocks(world, box, 5, 3, 9, 7, 4, 9, rand, conBrick);
				fillWithRandomizedBlocks(world, box, 8, 2, 8, 8, 4, 8, rand, conBrick);
				fillWithRandomizedBlocksMeta(world, box, 9, 2, 5, 9, 2, 7, rand, conBrickStairs, stairMetaE | 4);
				fillWithRandomizedBlocks(world, box, 9, 3, 5, 9, 4, 7, rand, conBrick);
				fillWithRandomizedBlocks(world, box, 8, 2, 4, 8, 4, 4, rand, conBrick);
				//Reactor Core
				for(int i = 4; i <= 8; i += 1) {
					for(int j = 4; j <= 8; j += 1) {
						if((i == 4 || i == 8) && (j == 4 || j == 8)) continue;
						
						int check = i % 2 == 0 ? 0 : 1;
						if(j % 2 == check)
							placeBlockAtCurrentPosition(world, ModBlocks.reactor_control, 0, i, 3, j, box);
						else {
							Block choice = i < 5 || i > 7 || j < 5 || j > 7 ? ModBlocks.reactor_element : ModBlocks.machine_generator;
							placeBlockAtCurrentPosition(world, choice, 0, i, 3, j, box);
						}
					}
				}
				
				if(destroyed) {
					randomlyFillWithBlocks(world, box, rand, 0.20F, 4, 4, 4, 8, 4, 8, Blocks.gravel);
					fillWithRandomizedBlocks(world, box, 5, 1, 4, 7, 3, 8, rand, coriumSelector);
					fillWithRandomizedBlocks(world, box, 4, 1, 5, 4, 3, 7, rand, coriumSelector);
					fillWithRandomizedBlocks(world, box, 8, 1, 5, 8, 3, 7, rand, coriumSelector);
				} else {
					//Reactor Shield/Top
					fillWithBlocks(world, box, 5, 4, 4, 7, 4, 8, ModBlocks.glass_lead);
					fillWithBlocks(world, box, 4, 4, 5, 4, 4, 7, ModBlocks.glass_lead);
					fillWithBlocks(world, box, 8, 4, 5, 8, 4, 7, ModBlocks.glass_lead);
					fillWithBlocks(world, box, 5, 5, 4, 7, 5, 8, ModBlocks.steel_roof);
					fillWithBlocks(world, box, 4, 5, 5, 4, 5, 7, ModBlocks.steel_roof);
					fillWithBlocks(world, box, 8, 5, 5, 8, 5, 7, ModBlocks.steel_roof);
				}
				
				//Scaffolding
				//Grates
				for(int i = 4; i <= 9; i++) { //Steps
					int meta = i % 2 == 0 ? 3 : 7;
					placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, meta, 1, i / 2, i, box);
				}
				fillWithMetadataBlocks(world, box, 2, 4, 8, 3, 4, 9, ModBlocks.steel_grate, 7);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 7, 4, 4, 9, box);
				fillWithMetadataBlocks(world, box, 3, 4, 1, 9, 4, 2, ModBlocks.steel_grate, 7);
				fillWithMetadataBlocks(world, box, 3, 4, 3, 4, 4, 3, ModBlocks.steel_grate, 7);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 7, 3, 4, 4, box);
				fillWithMetadataBlocks(world, box, 8, 4, 3, 9, 4, 3, ModBlocks.steel_grate, 7);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_grate, 7, 9, 4, 4, box);
				//Barriers
				fillWithMetadataBlocks(world, box, 3, 5, 1, 3, 5, 7, ModBlocks.steel_wall, decoMetaW);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, decoMetaN, 2, 5, 8, box);
				fillWithMetadataBlocks(world, box, 1, 5, 9, 7, 5, 9, ModBlocks.steel_wall, decoMetaS);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, decoMetaW, 8, 5, 9, box);
				fillWithMetadataBlocks(world, box, 9, 5, 1, 9, 5, 7, ModBlocks.steel_wall, decoMetaE);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, decoMetaN, 9, 5, 8, box);
				//Decoration
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick_stairs, stairMetaE | 4, 4, 5, 1, box);
				fillWithMetadataBlocks(world, box, 5, 5, 1, 7, 5, 1, ModBlocks.brick_slab, 9);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick_stairs, stairMetaW | 4, 8, 5, 1, box);
				fillWithMetadataBlocks(world, box, 4, 6, 1, 5, 6, 1, ModBlocks.tape_recorder, decoMetaN);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoModelMeta(1), 7, 6, 1, box);
				placeBlockAtCurrentPosition(world, ModBlocks.geiger, decoMetaS, 6, 6, 1, box);
				placeBlockAtCurrentPosition(world, ModBlocks.machine_storage_drum, 0, 4, 5, 3, box);
				placeRandomBobble(world, box, rand, 4, 6, 3);
				placeBlockAtCurrentPosition(world, ModBlocks.machine_storage_drum, 0, 1, 2, 11, box);
				placeBlockAtCurrentPosition(world, ModBlocks.machine_storage_drum, 0, 9, 2, 10, box);
				placeBlockAtCurrentPosition(world, ModBlocks.machine_storage_drum, 0, 11, 2, 9, box);
				placeBlockAtCurrentPosition(world, ModBlocks.machine_storage_drum, 0, 11, 2, 11, box);
				//Turbines/Transformers
				placeBlockAtCurrentPosition(world, ModBlocks.machine_turbine, 0, 14, 2, 6, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_quad_rusted, getPillarMeta(8), 14, 2, 7, box);
				placeBlockAtCurrentPosition(world, ModBlocks.machine_turbine, 0, 14, 4, 6, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_quad_rusted, getPillarMeta(8), 14, 4, 7, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_pipe_rim_rusted, 0, 14, 3, 6, box);
				placeBlockAtCurrentPosition(world, ModBlocks.red_connector, decoMetaE, 13, 2, 6, box);
				placeBlockAtCurrentPosition(world, ModBlocks.red_connector, decoMetaE, 13, 4, 6, box);
				placeBlockAtCurrentPosition(world, ModBlocks.red_connector, decoMetaN, 13, 3, 4, box);
				makeConnection(world, 13, 2, 6, 13, 3, 4);
				makeConnection(world, 13, 4, 6, 13, 3, 4);
				fillWithMetadataBlocks(world, box, 13, 2, 3, 13, 3, 3, ModBlocks.machine_battery, decoMetaN);
				fillWithMetadataBlocks(world, box, 14, 2, 3, 14, 3, 3, ModBlocks.cable_diode, decoMetaN);
				
				generateInvContents(world, box, rand, ModBlocks.crate_iron, 8, 5, 3, HbmChestContents.nuclear, 10);
				generateInvContents(world, box, rand, ModBlocks.crate_iron, 10, 2, 1, HbmChestContents.nuclearFuel, 12);
				
				if(underwater) {
					fillWithWater(world, box, rand, 5, 2, 0, 7, 4, 0, 1);
					fillWithWater(world, box, rand, 1, 1, 1, 11, 7, 11, 2);
					fillWithWater(world, box, rand, 12, 2, 3, 14, 4, 7, 1);
				} else {
					fillWithCobwebs(world, box, rand, 1, 1, 0, 11, 7, 11);
					fillWithCobwebs(world, box, rand, 12, 2, 3, 14, 4, 7);
				}
				
				return true;
			}
		}
		
		protected void makeConnection(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
			int posX1 = getXWithOffset(x1, z1);
			int posY1 = getYWithOffset(y1);
			int posZ1 = getZWithOffset(x1, z1);
			
			int posX2 = getXWithOffset(x2, z2);
			int posY2 = getYWithOffset(y2);
			int posZ2 = getZWithOffset(x2, z2);
			
			TileEntity tile1 = world.getTileEntity(posX1, posY1, posZ1);
			TileEntity tile2 = world.getTileEntity(posX2, posY2, posZ2);
			if(tile1 instanceof TileEntityPylonBase && tile2 instanceof TileEntityPylonBase) {
				TileEntityPylonBase pylon1 = (TileEntityPylonBase)tile1;
				pylon1.addConnection(posX2, posY2, posZ2);
				TileEntityPylonBase pylon2 = (TileEntityPylonBase)tile2;
				pylon2.addConnection(posX1, posY1, posZ1);
			}
		}
		
		/** Finds valid placement, using input information. Should be passed as a method reference to its respective Weight. */
		public static ProceduralComponent findValidPlacement(List components, Random rand, int x, int y, int z, int mode, int type) {
			StructureBoundingBox box = getComponentToAddBoundingBox(x, y, z, -5, -2, 0, 16, 10, 13, mode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new Reactor(type, rand, box, mode) : null;
		}
	}
	
	public static class RTG extends Bunker {
		
		public RTG() { }
		
		public RTG(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				fillWithAir(world, box, 4, 1, 0, 6, 3, 0);
				fillWithAir(world, box, 1, 1, 1, 9, 3, 9);
				
				//Floor
				fillWithMetadataBlocks(world, box, 4, 0, 0, 6, 0, 0, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 1, 0, 1, 9, 0, 1, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 1, 0, 2, 1, 0, 8, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 1, 0, 9, 9, 0, 9, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 9, 0, 2, 9, 0, 8, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 2, 0, 2, 8, 0, 8, ModBlocks.vinyl_tile);
				//Walls
				fillWithBlocks(world, box, 1, 1, 0, 3, 1, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 2, 0, 3, 2, 0, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 1, 3, 0, 3, 3, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 0, 1, 1, 0, 1, 9, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 0, 2, 1, 0, 2, 9, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 0, 3, 1, 0, 3, 9, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 1, 10, 9, 1, 10, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 1, 2, 10, 9, 2, 10, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 1, 3, 10, 9, 3, 10, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 10, 1, 6, 10, 1, 9, ModBlocks.reinforced_brick);
				placeBlockAtCurrentPosition(world, ModBlocks.red_wire_coated, 0, 10, 1, 5, box);
				fillWithBlocks(world, box, 10, 1, 1, 10, 1, 4, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 10, 2, 1, 10, 2, 9, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 10, 3, 1, 10, 3, 9, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 7, 1, 0, 9, 1, 0, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 7, 2, 0, 9, 2, 0, ModBlocks.reinforced_stone);
				fillWithBlocks(world, box, 7, 3, 0, 9, 3, 0, ModBlocks.reinforced_brick);
				//Ceiling
				int pillarMetaWE = getPillarMeta(4);
				fillWithBlocks(world, box, 4, 4, 0, 6, 4, 0, ModBlocks.reinforced_brick);
				for(int i = 1; i <= 9; i += 3) {
					fillWithBlocks(world, box, 2, 4, i, 8, 4, i, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 1, 4, i, 1, 4, i + 2, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 2, 4, i + 2, 8, 4, i + 2, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 9, 4, i, 9, 4, i + 2, ModBlocks.reinforced_brick);
					placeLamp(world, box, rand, 2, 4, i + 1);
					fillWithMetadataBlocks(world, box, 3, 4, i + 1, 4, 4, i + 1, ModBlocks.concrete_pillar, pillarMetaWE);
					placeLamp(world, box, rand, 5, 4, i + 1);
					fillWithMetadataBlocks(world, box, 6, 4, i + 1, 7, 4, i + 1, ModBlocks.concrete_pillar, pillarMetaWE);
					placeLamp(world, box, rand, 8, 4, i + 1);
				}
				
				//Decoration
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick_stairs, getStairMeta(7), 1, 1, 3, box); //Desk
				fillWithMetadataBlocks(world, box, 1, 1, 4, 1, 1, 6, ModBlocks.brick_slab, 9);
				placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick_stairs, getStairMeta(6), 1, 1, 7, box);
				placeBlockAtCurrentPosition(world, Blocks.spruce_stairs, getStairMeta(0), 2, 1, 4, box);
				placeBlockAtCurrentPosition(world, ModBlocks.deco_computer, getDecoMeta(3), 1, 2, 4, box);
				
				fillWithBlocks(world, box, 1, 1, 9, 2, 1, 9, ModBlocks.crate_lead);
				placeBlockAtCurrentPosition(world, ModBlocks.barrel_corroded, 0, 1, 2, 9, box);
				fillWithMetadataBlocks(world, box, 4, 1, 9, 4, 2, 9, ModBlocks.tape_recorder, getDecoMeta(2));
				placeBlockAtCurrentPosition(world, ModBlocks.barrel_corroded, 0, 7, 1, 9, box);
				placeBlockAtCurrentPosition(world, ModBlocks.barrel_corroded, 0, 9, 1, 8, box);
				
				//The boy
				placeBlockAtCurrentPosition(world, ModBlocks.machine_radiolysis, getDirection(ForgeDirection.EAST).ordinal() + BlockDummyable.offset, 5, 1, 5, box);
				fillSpace(world, box, 5, 1, 5, new int[] {2, 0, 1, 1, 1, 1}, ModBlocks.machine_radiolysis, ForgeDirection.EAST);
				makeExtra(world, box, ModBlocks.machine_radiolysis, 5 + 1, 1, 5);
				makeExtra(world, box, ModBlocks.machine_radiolysis, 5 - 1, 1, 5);
				makeExtra(world, box, ModBlocks.machine_radiolysis, 5, 1, 5 + 1);
				makeExtra(world, box, ModBlocks.machine_radiolysis, 5, 1, 5 - 1);
				
				int decoMetaS = getDecoMeta(2);
				int decoMetaN = getDecoMeta(3);
				int decoMetaE = getDecoMeta(4);
				int decoMetaW = getDecoMeta(5);
				fillWithMetadataBlocks(world, box, 4, 1, 3, 6, 3, 3, ModBlocks.steel_wall, decoMetaS);
				fillWithMetadataBlocks(world, box, 3, 1, 4, 3, 3, 6, ModBlocks.steel_wall, decoMetaE);
				fillWithMetadataBlocks(world, box, 4, 1, 7, 6, 3, 7, ModBlocks.steel_wall, decoMetaN);
				fillWithMetadataBlocks(world, box, 7, 1, 4, 7, 3, 4, ModBlocks.steel_wall, decoMetaW);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, decoMetaW, 7, 3, 5, box);
				fillWithMetadataBlocks(world, box, 7, 1, 6, 7, 3, 6, ModBlocks.steel_wall, decoMetaW);
				
				//Cable
				placeBlockAtCurrentPosition(world, ModBlocks.red_cable, 0, 7, 1, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.red_wire_coated, 0, 8, 1, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.red_cable, 0, 9, 1, 5, box);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, decoMetaN, 8, 1, 6, box);
				placeBlockAtCurrentPosition(world, ModBlocks.steel_wall, decoMetaS, 8, 1, 4, box);
				fillWithBlocks(world, box, 7, 2, 5, 9, 2, 5, ModBlocks.steel_roof);
				
				generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(3), 1, 1, 2, HbmChestContents.filingCabinet, 4);
				generateInvContents(world, box, rand, ModBlocks.filing_cabinet, getDecoModelMeta(3), 1, 2, 2, HbmChestContents.filingCabinet, 4);
				
				if(underwater) {
					fillWithWater(world, box, rand, 4, 1, 0, 6, 3, 0, 1);
					fillWithWater(world, box, rand, 1, 1, 1, 9, 3, 9, 1);
				} else
					fillWithCobwebs(world, box, rand, 1, 1, 0, 9, 3, 9);
				
				return true;
			}
		}
		

		/** Finds valid placement, using input information. Should be passed as a method reference to its respective Weight. */
		public static ProceduralComponent findValidPlacement(List components, Random rand, int x, int y, int z, int mode, int type) {
			StructureBoundingBox box = getComponentToAddBoundingBox(x, y, z, -4, -1, 0, 11, 6, 11, mode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new RTG(type, rand, box, mode) : null;
		}
	}
	
	public static class Atrium extends ControlComponent {
		
		public boolean underwater = false;
		
		public Atrium() { }
		
		public Atrium(int componentType, Random rand, int posX, int posZ) { //TODO: change basically everything about this component
			super(componentType);
			this.coordBaseMode = rand.nextInt(4);
			this.boundingBox = new StructureBoundingBox(posX, 64, posZ, posX + 12, 68, posZ + 12);
		}
		
		@Override
		public void buildComponent(ProceduralComponents instance, ControlComponent original, List components, Random rand) {
			
			StructureComponent component = getNextComponentNormal(instance, original, components, rand, 3, 1);
			System.out.println("ComponentPZ:" + component);
			
			StructureComponent componentAN = getNextComponentAntiNormal(instance, original, components, rand, 3, 1);
			System.out.println("ComponentNZ:" + componentAN);
			
			StructureComponent componentN = getNextComponentNX(instance, original, components, rand, 3, 1);
			System.out.println("ComponentNX:" + componentN);
			
			StructureComponent componentP = getNextComponentPX(instance, original, components, rand, 3, 1);
			System.out.println("ComponentPX:" + componentP);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			fillWithBlocks(world, box, 0, 0, 0, 12, 4, 12, ModBlocks.reinforced_brick, Blocks.air, false);
			
			return true;
		}
	}
	
	public static class Corridor extends Bunker {
		
		boolean expandsNX = false;
		boolean expandsPX = false;
		boolean extendsPZ = true;
		
		public Corridor() { }
		
		public Corridor(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			
		}
		
		protected void func_143012_a(NBTTagCompound data) {
			super.func_143012_a(data);
			data.setBoolean("expandsNX", expandsNX);
			data.setBoolean("expandsPX", expandsPX);
			data.setBoolean("extendsPZ", extendsPZ);
		}
		
		protected void func_143011_b(NBTTagCompound data) {
			super.func_143011_b(data);
			expandsNX = data.getBoolean("expandsNX");
			expandsPX = data.getBoolean("expandsPX");
			extendsPZ = data.getBoolean("extendsPZ");
		}
		
		@Override
		public void buildComponent(ProceduralComponents instance, ControlComponent original, List components, Random rand) {
			checkModifiers(original);
			
			StructureComponent component = getNextComponentNormal(instance, original, components, rand, 1, 1);
			extendsPZ = component != null;
			
			if(rand.nextInt(2) == 0) {
				StructureComponent componentN = getNextComponentNX(instance, original, components, rand, 6, 1);
				expandsNX = componentN != null;
			}
			
			if(rand.nextInt(2) == 0) {
				StructureComponent componentP = getNextComponentPX(instance, original, components, rand, 6, 1);
				expandsPX = componentP != null;
			}
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				int end = extendsPZ ? 14 : 13;
				
				fillWithAir(world, box, 1, 1, 0, 3, 3, end);				
				fillWithBlocks(world, box, 1, 0, 0, 3, 0, end, ModBlocks.vinyl_tile);
				
				//Walls
				for(int x = 0; x <= 4; x += 4) {
					fillWithBlocks(world, box, x, 1, 0, x, 1, 4, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, x, 1, 10, x, 1, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, x, 2, 0, x, 2, 4, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, x, 2, 10, x, 2, end, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, x, 3, 10, x, 3, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, x, 3, 0, x, 3, 4, ModBlocks.reinforced_brick);
				}
				
				if(!extendsPZ) {
					fillWithBlocks(world, box, 1, 1, 14, 3, 1, 14, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 1, 2, 14, 3, 2, 14, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 1, 3, 14, 3, 3, 14, ModBlocks.reinforced_brick);
				}
				
				//ExpandsNX
				if(expandsNX) {
					fillWithBlocks(world, box, 0, 0, 6, 0, 0, 8, ModBlocks.vinyl_tile); //Floor
					fillWithBlocks(world, box, 0, 1, 5, 0, 3, 5, ModBlocks.concrete_pillar); //Walls
					fillWithBlocks(world, box, 0, 1, 9, 0, 3, 9, ModBlocks.concrete_pillar);
					fillWithAir(world, box, 0, 1, 6, 0, 3, 8);
					fillWithBlocks(world, box, 0, 4, 6, 0, 4, 8, ModBlocks.reinforced_brick); //Ceiling
				} else {
					fillWithBlocks(world, box, 0, 1, 5, 0, 1, 9, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, 5, 0, 2, 9, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 5, 0, 3, 9, ModBlocks.reinforced_brick);
				}
				
				//ExpandsPX
				if(expandsPX) {
					fillWithBlocks(world, box, 4, 0, 6, 4, 0, 8, ModBlocks.vinyl_tile);
					fillWithBlocks(world, box, 4, 1, 5, 4, 3, 5, ModBlocks.concrete_pillar);
					fillWithBlocks(world, box, 4, 1, 9, 4, 3, 9, ModBlocks.concrete_pillar);
					fillWithAir(world, box, 4, 1, 6, 4, 3, 8);
					fillWithBlocks(world, box, 4, 4, 6, 4, 4, 8, ModBlocks.reinforced_brick);
				} else {
					fillWithBlocks(world, box, 4, 1, 5, 4, 1, 9, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 4, 2, 5, 4, 2, 9, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 4, 3, 5, 4, 3, 9, ModBlocks.reinforced_brick);
				}
				
				//Ceiling
				fillWithBlocks(world, box, 1, 4, 0, 1, 4, end, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 3, 4, 0, 3, 4, end, ModBlocks.reinforced_brick);
				int pillarMeta = getPillarMeta(8);
				for(int i = 0; i <= 12; i += 3) {
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMeta, 2, 4, i, box);
					placeLamp(world, box, rand, 2, 4, i + 1);
					
					if(extendsPZ || i < 12)
						placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMeta, 2, 4, i + 2, box);
				}
				
				if(underwater) {
					fillWithWater(world, box, rand, 1, 1, 0, 3, 3, end, 1);
					if(expandsNX) fillWithWater(world, box, rand, 0, 1, 6, 0, 3, 8, 1);
					if(expandsPX) fillWithWater(world, box, rand, 4, 1, 6, 4, 3, 8, 1);
				} else
					fillWithCobwebs(world, box, rand, expandsNX ? 0 : 1, 1, 0, expandsPX ? 4 : 3, 3, end);
				
				return true;
			}
		}
		
		/** Finds valid placement, using input information. Should be passed as a method reference to its respective Weight. */
		public static ProceduralComponent findValidPlacement(List components, Random rand, int x, int y, int z, int mode, int type) {
			StructureBoundingBox box = getComponentToAddBoundingBox(x, y, z, -3, -1, 0, 9, 6, 15, mode); //Corridor and Wide version
			if(box.minY > 10 && StructureComponent.findIntersecting(components, box) == null) return new WideCorridor(type, rand, box, mode);
			
			box = getComponentToAddBoundingBox(x, y, z, -1, -1, 0, 5, 6, 15, mode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new Corridor(type, rand, box, mode) : null;
		}
	}
	
	private interface Wide { } //now you may ask yourself - where is that beautiful house? you may ask yourself - where does that highway go to?
	//you may ask yourself - am i right, am i wrong? you may say to yourself - my god, no multiple inheritance to be done!
	private interface Bulkhead { public void setBulkheadNZ(boolean bool);
		public default void flipConstituentBulkhead(StructureComponent component, Random rand) {
			if(component instanceof Bulkhead) {
				Bulkhead head = (Bulkhead) component;
				head.setBulkheadNZ(rand.nextInt(4) == 0);
			}
		}
	}
	
	public static class WideCorridor extends Corridor implements Wide, Bulkhead {
		
		boolean bulkheadNZ = true;
		public void setBulkheadNZ(boolean bool) { bulkheadNZ = bool; }
		
		boolean bulkheadPZ = true;
		
		public WideCorridor() { }
		
		public WideCorridor(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType, rand, box, coordBaseMode);
		}
		
		protected void func_143012_a(NBTTagCompound data) {
			super.func_143012_a(data);
			data.setBoolean("bulkheadNZ", bulkheadNZ);
			data.setBoolean("bulkheadPZ", bulkheadPZ);
		}
		
		protected void func_143011_b(NBTTagCompound data) {
			super.func_143011_b(data);
			bulkheadNZ = data.getBoolean("bulkheadNZ");
			bulkheadPZ = data.getBoolean("bulkheadPZ");
		}
		
		@Override
		public void buildComponent(ProceduralComponents instance, ControlComponent original, List components, Random rand) {
			checkModifiers(original);
			
			StructureComponent component = getNextComponentNormal(instance, original, components, rand, 3, 1);
			extendsPZ = component != null;
			
			if(component instanceof Wide) {
				bulkheadPZ = false;
				flipConstituentBulkhead(component, rand);
			}
			
			if(rand.nextInt(2) == 0) {
				StructureComponent componentN = getNextComponentNX(instance, original, components, rand, 6, 1);
				expandsNX = componentN != null;
			}
			
			if(rand.nextInt(2) == 0) {
				StructureComponent componentP = getNextComponentPX(instance, original, components, rand, 6, 1);
				expandsPX = componentP != null;
			}
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				int begin = bulkheadNZ ? 1 : 0;
				int end =  bulkheadPZ ? 13 : 14; //for the bulkhead
				int endExtend = !extendsPZ ? 13 : 14; //for parts that would be cut off if it doesn't extend further
				
				fillWithAir(world, box, 1, 1, begin, 7, 3, end);
				
				//Floor
				fillWithBlocks(world, box, 1, 0, begin, 1, 0, end, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 2, 0, begin, 2, 0, end, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 3, 0, 0, 5, 0, endExtend, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 6, 0, begin, 6, 0, end, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 7, 0, begin, 7, 0, end, ModBlocks.vinyl_tile);
				
				int pillarMeta = getPillarMeta(8);
				//Walls
				if(expandsNX) {
					fillWithBlocks(world, box, 0, 1, begin, 0, 1, 4, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, begin, 0, 2, 4, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, begin, 0, 3, 4, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 1, 10, 0, 1, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, 10, 0, 2, end, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 10, 0, 3, end, ModBlocks.reinforced_brick);
					
					fillWithBlocks(world, box, 0, 0, 6, 0, 0, 8, ModBlocks.vinyl_tile);
					fillWithBlocks(world, box, 0, 1, 5, 0, 3, 5, ModBlocks.concrete_pillar);
					fillWithBlocks(world, box, 0, 1, 9, 0, 3, 9, ModBlocks.concrete_pillar);
					fillWithMetadataBlocks(world, box, 0, 4, 6, 0, 4, 8, ModBlocks.concrete_pillar, pillarMeta);
					fillWithAir(world, box, 0, 1, 6, 0, 3, 8);
					
				} else {
					fillWithBlocks(world, box, 0, 1, begin, 0, 1, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 0, 2, begin, 0, 2, end, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, begin, 0, 3, end, ModBlocks.reinforced_brick);
				}
				
				if(expandsPX) {
					fillWithBlocks(world, box, 8, 1, begin, 8, 1, 4, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 8, 2, begin, 8, 2, 4, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 8, 3, begin, 8, 3, 4, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 8, 1, 10, 8, 1, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 8, 2, 10, 8, 2, end, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 8, 3, 10, 8, 3, end, ModBlocks.reinforced_brick);
					
					fillWithBlocks(world, box, 8, 0, 6, 8, 0, 8, ModBlocks.vinyl_tile);
					fillWithBlocks(world, box, 8, 1, 5, 8, 3, 5, ModBlocks.concrete_pillar);
					fillWithBlocks(world, box, 8, 1, 9, 8, 3, 9, ModBlocks.concrete_pillar);
					fillWithMetadataBlocks(world, box, 8, 4, 6, 8, 4, 8, ModBlocks.concrete_pillar, pillarMeta);
					fillWithAir(world, box, 8, 1, 6, 8, 3, 8);
					
				} else {
					fillWithBlocks(world, box, 8, 1, begin, 8, 1, end, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 8, 2, begin, 8, 2, end, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 8, 3, begin, 8, 3, end, ModBlocks.reinforced_brick);
				}
				
				if(bulkheadNZ) {
					fillWithBlocks(world, box, 1, 1, 0, 2, 1, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 1, 2, 0, 2, 2, 0, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 1, 3, 0, 2, 3, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 1, 0, 7, 1, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 2, 0, 7, 2, 0, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 6, 3, 0, 7, 3, 0, ModBlocks.reinforced_brick);
					fillWithAir(world, box, 3, 1, 0, 5, 3, 0);
				}
				
				if(bulkheadPZ) {
					fillWithBlocks(world, box, 1, 1, 14, 2, 1, 14, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 1, 2, 14, 2, 2, 14, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 1, 3, 14, 2, 3, 14, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 1, 14, 7, 1, 14, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 2, 14, 7, 2, 14, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 6, 3, 14, 7, 3, 14, ModBlocks.reinforced_brick);
					
					if(!extendsPZ) {
						fillWithBlocks(world, box, 3, 1, 14, 5, 1, 14, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 3, 2, 14, 5, 2, 14, ModBlocks.reinforced_stone);
						fillWithBlocks(world, box, 3, 3, 14, 5, 3, 14, ModBlocks.reinforced_brick);
					} else
						fillWithAir(world, box, 3, 1, 14, 5, 3, 14);
				}
				
				//Ceiling
				fillWithBlocks(world, box, 1, 4, begin, 1, 4, end, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 2, 4, begin, 2, 4, end, ModBlocks.concrete_pillar, pillarMeta);
				fillWithBlocks(world, box, 3, 4, 0, 3, 4, endExtend, ModBlocks.reinforced_brick);
				fillWithBlocks(world, box, 5, 4, 0, 5, 4, endExtend, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 6, 4, begin, 6, 4, end, ModBlocks.concrete_pillar, pillarMeta);
				fillWithBlocks(world, box, 7, 4, begin, 7, 4, end, ModBlocks.reinforced_brick);
				
				for(int i = 0; i <= 12; i += 3) {
					placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMeta, 4, 4, i, box);
					placeLamp(world, box, rand, 4, 4, i + 1);
					
					if(extendsPZ || i < 12)
						placeBlockAtCurrentPosition(world, ModBlocks.concrete_pillar, pillarMeta, 4, 4, i + 2, box);
				}
				
				if(underwater) {
					fillWithWater(world, box, rand, 1, 1, 0, 7, 3, endExtend, 1);
					if(expandsNX) fillWithWater(world, box, rand, 0, 1, 6, 0, 3, 8, 1);
					if(expandsPX) fillWithWater(world, box, rand, 8, 1, 6, 8, 3, 8, 1);
				} else
					fillWithCobwebs(world, box, rand, expandsNX ? 0 : 1, 1, 0, expandsPX ? 8 : 7, 3, endExtend);
								
				return true;
			}
		}
	}
	
	public static class Turn extends ProceduralComponent {
		
		public Turn() { }
		
		public Turn(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			return true;
		}
	}
	
	public static class WideTurn extends Turn implements Wide {
		
		public WideTurn() { }
		
		public WideTurn(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType, rand, box, coordBaseMode);
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			return true;
		}
	}
	
	public static class Intersection extends Bunker {
		
		boolean opensNX = false;
		boolean opensPX = false;
		boolean opensPZ = false;
		
		public Intersection() { }
		
		public Intersection(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType);
			this.coordBaseMode = coordBaseMode;
			this.boundingBox = box;
			
		}
		
		@Override
		public void buildComponent(ProceduralComponents instance, ControlComponent original, List components, Random rand) {
			checkModifiers(original);
			
			StructureComponent component = getNextComponentNormal(instance, original, components, rand, 1, 1);
			opensPZ = component != null;
			
			StructureComponent componentN = getNextComponentNX(instance, original, components, rand, 1, 1);
			opensNX = componentN != null;
			
			StructureComponent componentP = getNextComponentPX(instance, original, components, rand, 1, 1);
			opensPX = componentP != null;
		}
		
		protected void func_143012_a(NBTTagCompound data) {
			super.func_143012_a(data);
			data.setBoolean("opensNX", opensNX);
			data.setBoolean("opensPX", opensPX);
			data.setBoolean("opensPZ", opensPZ);
		}
		
		protected void func_143011_b(NBTTagCompound data) {
			super.func_143011_b(data);
			opensNX = data.getBoolean("opensNX");
			opensPX = data.getBoolean("opensPX");
			opensPZ = data.getBoolean("opensPZ");
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				
				fillWithAir(world, box, 1, 1, 0, 3, 3, 3);
				//Floor
				fillWithBlocks(world, box, 1, 0, 0, 3, 0, 3, ModBlocks.vinyl_tile);
				//Ceiling
				int pillarMetaWE = getPillarMeta(4);
				int pillarMetaNS = getPillarMeta(8);
				
				fillWithBlocks(world, box, 3, 4, 0, 3, 4, 1, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 2, 4, 0, 2, 4, 1, ModBlocks.concrete_pillar, pillarMetaNS);
				fillWithBlocks(world, box, 1, 4, 0, 1, 4, 1, ModBlocks.reinforced_brick);
				
				placeLamp(world, box, rand, 2, 4, 2);
				
				if(opensPZ) {
					fillWithBlocks(world, box, 1, 0, 4, 3, 0, 4, ModBlocks.vinyl_tile); //Floor
					fillWithBlocks(world, box, 1, 4, 3, 1, 4, 4, ModBlocks.reinforced_brick); //Ceiling
					fillWithMetadataBlocks(world, box, 2, 4, 3, 2, 4, 4, ModBlocks.concrete_pillar, pillarMetaNS);
					fillWithBlocks(world, box, 3, 4, 3, 3, 4, 4, ModBlocks.reinforced_brick);
					fillWithAir(world, box, 1, 1, 4, 3, 3, 4); //Opening
				} else {
					fillWithBlocks(world, box, 1, 4, 3, 3, 4, 3, ModBlocks.reinforced_brick); //Ceiling
					fillWithBlocks(world, box, 1, 1, 4, 3, 1, 4, ModBlocks.reinforced_brick); //Wall
					fillWithBlocks(world, box, 1, 2, 4, 3, 2, 4, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 1, 3, 4, 3, 3, 4, ModBlocks.reinforced_brick);
				}
				
				if(opensNX) {
					fillWithBlocks(world, box, 0, 0, 1, 0, 0, 3, ModBlocks.vinyl_tile); //Floor
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 4, 1, box); //Ceiling
					fillWithMetadataBlocks(world, box, 0, 4, 2, 1, 4, 2, ModBlocks.concrete_pillar, pillarMetaWE);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 4, 3, box);
					fillWithAir(world, box, 0, 1, 1, 0, 3, 3); //Opening
				} else {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 1, 4, 2, box); //Ceiling
					fillWithBlocks(world, box, 0, 1, 1, 0, 1, 3, ModBlocks.reinforced_brick); //Wall
					fillWithBlocks(world, box, 0, 2, 1, 0, 2, 3, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 1, 0, 3, 3, ModBlocks.reinforced_brick);
				}
				
				if(opensPX) {
					fillWithBlocks(world, box, 4, 0, 1, 4, 0, 3, ModBlocks.vinyl_tile); //Floor
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 4, 1, box); //Ceiling
					fillWithMetadataBlocks(world, box, 3, 4, 2, 4, 4, 2, ModBlocks.concrete_pillar, pillarMetaWE);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 4, 3, box);
					fillWithAir(world, box, 4, 1, 1, 4, 3, 3); //Opening
				} else {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 3, 4, 2, box);
					fillWithBlocks(world, box, 4, 1, 1, 4, 1, 3, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 4, 2, 1, 4, 2, 3, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 4, 3, 1, 4, 3, 3, ModBlocks.reinforced_brick);
				}
				
				//Pillars
				if(opensNX)
					fillWithBlocks(world, box, 0, 1, 0, 0, 3, 0, ModBlocks.concrete_pillar);
				else {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 1, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 0, 2, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 3, 0, box);
				}
				
				if(opensPX)
					fillWithBlocks(world, box, 4, 1, 0, 4, 3, 0, ModBlocks.concrete_pillar);
				else {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 1, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 4, 2, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 3, 0, box);
				}
				
				if(opensNX && opensPZ)
					fillWithBlocks(world, box, 0, 1, 4, 0, 3, 4, ModBlocks.concrete_pillar);
				else if(opensNX || opensPZ) {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 1, 4, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 0, 2, 4, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 3, 4, box);
				}
				
				if(opensPX && opensPZ)
					fillWithBlocks(world, box, 4, 1, 4, 4, 3, 4, ModBlocks.concrete_pillar);
				else if(opensPX || opensPZ) {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 1, 4, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 4, 2, 4, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 3, 4, box);
				}
				
				if(underwater) {
					fillWithWater(world, box, rand, 1, 1, 0, 3, 3, opensPZ ? 4 : 3, 1);
					if(opensNX) fillWithWater(world, box, rand, 0, 1, 1, 0, 3, 3, 1);
					if(opensPX) fillWithWater(world, box, rand, 4, 1, 1, 4, 3, 3, 1);
				} else
					fillWithCobwebs(world, box, rand, opensNX ? 0 : 1, 1, 0, opensPX ? 4 : 3, 3, opensPZ ? 4 : 3);
				
				return true;
			}
		}
		
		/** Finds valid placement, using input information. Should be passed as a method reference to its respective Weight. */
		public static ProceduralComponent findValidPlacement(List components, Random rand, int x, int y, int z, int mode, int type) {
			StructureBoundingBox box = getComponentToAddBoundingBox(x, y, z, -3, -1, 0, 9, 6, 9, mode);
			if(box.minY > 10 && StructureComponent.findIntersecting(components, box) == null) return new WideIntersection(type, rand, box, mode);
			
			box = getComponentToAddBoundingBox(x, y, z, -1, -1, 0, 5, 6, 5, mode);
			return box.minY > 10 && StructureComponent.findIntersecting(components, box) == null ? new Intersection(type, rand, box, mode) : null;
		}
	}
	
	public static class WideIntersection extends Intersection implements Wide, Bulkhead {
		
		boolean bulkheadNZ = true;
		public void setBulkheadNZ(boolean bool) { bulkheadNZ = bool; }
		
		boolean bulkheadPZ = true;
		boolean bulkheadNX = true;
		boolean bulkheadPX = true;
		
		public WideIntersection() { }
		
		public WideIntersection(int componentType, Random rand, StructureBoundingBox box, int coordBaseMode) {
			super(componentType, rand, box, coordBaseMode);
		}
		
		protected void func_143012_a(NBTTagCompound data) {
			super.func_143012_a(data);
			data.setBoolean("bulkheadNZ", bulkheadNZ);
			data.setBoolean("bulkheadPZ", bulkheadPZ);
			data.setBoolean("bulkheadNX", bulkheadNX);
			data.setBoolean("bulkheadPX", bulkheadPX);
		}
		
		protected void func_143011_b(NBTTagCompound data) {
			super.func_143011_b(data);
			bulkheadNZ = data.getBoolean("bulkheadNZ");
			bulkheadPZ = data.getBoolean("bulkheadPZ");
			bulkheadNX = data.getBoolean("bulkheadNX");
			bulkheadPX = data.getBoolean("bulkheadPX");
		}
		
		@Override
		public void buildComponent(ProceduralComponents instance, ControlComponent original, List components, Random rand) {
			checkModifiers(original);
			
			StructureComponent component = getNextComponentNormal(instance, original, components, rand, 3, 1);
			opensPZ = component != null;
			
			if(component instanceof Wide) {
				bulkheadPZ = false;
				flipConstituentBulkhead(component, rand);
			}
			
			StructureComponent componentN = getNextComponentNX(instance, original, components, rand, 3, 1);
			opensNX = componentN != null;
			
			if(componentN instanceof Wide) {
				bulkheadNX = false;
				flipConstituentBulkhead(component, rand);
			}
			
			StructureComponent componentP = getNextComponentPX(instance, original, components, rand, 3, 1);
			opensPX = componentP != null;
			
			if(componentP instanceof Wide) {
				bulkheadPX = false;
				flipConstituentBulkhead(component, rand);
			}
		}
		
		@Override
		public boolean addComponentParts(World world, Random rand, StructureBoundingBox box) {
			if(!underwater && isLiquidInStructureBoundingBox(world, boundingBox)) {
				return false;
			} else {
				
				int start = bulkheadNZ ? 1 : 0;
				int end = bulkheadPZ ? 7 : 8;
				int right = bulkheadNX ? 1 : 0;
				int left = bulkheadPX ? 7 : 8;
				
				int pillarMetaNS = getPillarMeta(8);
				int pillarMetaWE = getPillarMeta(4);
				
				fillWithAir(world, box, 1, 1, 0, 7, 3, end);
				//Floor
				fillWithBlocks(world, box, 3, 0, 0, 5, 0, 1, ModBlocks.vinyl_tile);
				fillWithMetadataBlocks(world, box, 2, 0, start, 2, 0, 6, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 3, 0, 2, 5, 0, 2, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 3, 0, 6, 5, 0, 6, ModBlocks.vinyl_tile, 1);
				fillWithMetadataBlocks(world, box, 6, 0, start, 6, 0, 6, ModBlocks.vinyl_tile, 1);
				fillWithBlocks(world, box, 3, 0, 3, 5, 0, 5, ModBlocks.vinyl_tile);
				//Wall
				if(!bulkheadNZ || (opensNX && !bulkheadNX)) {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 1, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 0, 2, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 3, 0, box);
				}
				
				if(!bulkheadNZ || (opensPX && !bulkheadPX)) {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 8, 1, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 8, 2, 0, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 8, 3, 0, box);
				}
				//Ceiling
				fillWithBlocks(world, box, 1, 4, start, 1, 4, 1, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 2, 4, start, 2, 4, 1, ModBlocks.concrete_pillar, pillarMetaNS);
				fillWithBlocks(world, box, 3, 4, 0, 3, 4, 3, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 4, 4, 0, 4, 4, 3, ModBlocks.concrete_pillar, pillarMetaNS);
				fillWithBlocks(world, box, 5, 4, 0, 5, 4, 3, ModBlocks.reinforced_brick);
				fillWithMetadataBlocks(world, box, 6, 4, start, 6, 4, 1, ModBlocks.concrete_pillar, pillarMetaNS);
				fillWithBlocks(world, box, 7, 4, start, 7, 4, 1, ModBlocks.reinforced_brick);
				placeLamp(world, box, rand, 2, 4, 2);
				placeLamp(world, box, rand, 2, 4, 6);
				placeLamp(world, box, rand, 4, 4, 4);
				placeLamp(world, box, rand, 6, 4, 2);
				placeLamp(world, box, rand, 6, 4, 6);
				
				if(bulkheadNZ) {
					fillWithBlocks(world, box, 1, 1, 0, 2, 1, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 1, 2, 0, 2, 2, 0, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 1, 3, 0, 2, 3, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 1, 0, 7, 1, 0, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 2, 0, 7, 2, 0, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 6, 3, 0, 7, 3, 0, ModBlocks.reinforced_brick);
					fillWithAir(world, box, 3, 1, 0, 5, 3, 0);
				} else
					fillWithAir(world, box, 1, 1, 0, 7, 3, 0);
				
				if(opensPZ) {
					fillWithBlocks(world, box, 1, 0, 7, 1, 0, end, ModBlocks.vinyl_tile); //Floor
					fillWithMetadataBlocks(world, box, 2, 0, 7, 2, 0, end, ModBlocks.vinyl_tile, 1);
					fillWithBlocks(world, box, 3, 0, 7, 5, 0, 8, ModBlocks.vinyl_tile);
					fillWithMetadataBlocks(world, box, 6, 0, 7, 6, 0, end, ModBlocks.vinyl_tile, 1);
					fillWithBlocks(world, box, 7, 0, 7, 7, 0, end, ModBlocks.vinyl_tile);
					fillWithBlocks(world, box, 1, 4, 7, 1, 4, end, ModBlocks.reinforced_brick); //Ceiling
					fillWithMetadataBlocks(world, box, 2, 4, 7, 2, 4, end, ModBlocks.concrete_pillar, pillarMetaNS);
					fillWithBlocks(world, box, 3, 4, 5, 3, 4, 8, ModBlocks.reinforced_brick);
					fillWithMetadataBlocks(world, box, 4, 4, 5, 4, 4, 8, ModBlocks.concrete_pillar, pillarMetaNS);
					fillWithBlocks(world, box, 5, 4, 5, 5, 4, 8, ModBlocks.reinforced_brick);
					fillWithMetadataBlocks(world, box, 6, 4, 7, 6, 4, end, ModBlocks.concrete_pillar, pillarMetaNS);
					fillWithBlocks(world, box, 7, 4, 7, 7, 4, end, ModBlocks.reinforced_brick);
					
					if(bulkheadPZ) {
						fillWithBlocks(world, box, 1, 1, 8, 2, 1, 8, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 1, 2, 8, 2, 2, 8, ModBlocks.reinforced_stone);
						fillWithBlocks(world, box, 1, 3, 8, 2, 3, 8, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 6, 1, 8, 7, 1, 8, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 6, 2, 8, 7, 2, 8, ModBlocks.reinforced_stone);
						fillWithBlocks(world, box, 6, 3, 8, 7, 3, 8, ModBlocks.reinforced_brick);
						fillWithAir(world, box, 3, 1, 8, 5, 3, 8);
					} else
						fillWithAir(world, box, 1, 1, 8, 7, 3, 8);
				} else {
					fillWithBlocks(world, box, 1, 0, 7, 7, 0, 7, ModBlocks.vinyl_tile); //Floor
					fillWithBlocks(world, box, 1, 1, 8, 7, 1, 8, ModBlocks.reinforced_brick); //Wall
					fillWithBlocks(world, box, 1, 2, 8, 7, 2, 8, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 1, 3, 8, 7, 3, 8, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 3, 4, 5, 5, 4, 6, ModBlocks.reinforced_brick); //Ceiling
					fillWithBlocks(world, box, 1, 4, 7, 7, 4, 7, ModBlocks.reinforced_brick);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 4, 4, 5, box);
				}
				
				if(opensNX) {
					fillWithBlocks(world, box, 1, 0, start, 1, 0, 1, ModBlocks.vinyl_tile); //Floor
					
					fillWithMetadataBlocks(world, box, right, 0, 2, 1, 0, 2, ModBlocks.vinyl_tile, 1);
					fillWithBlocks(world, box, 0, 0, 3, 1, 0, 5, ModBlocks.vinyl_tile);
					fillWithMetadataBlocks(world, box, right, 0, 6, 1, 0, 6, ModBlocks.vinyl_tile, 1);
					
					 //Ceiling
					fillWithMetadataBlocks(world, box, right, 4, 2, 1, 4, 2, ModBlocks.concrete_pillar, pillarMetaWE);
					fillWithBlocks(world, box, 0, 4, 3, 2, 4, 3, ModBlocks.reinforced_brick);
					fillWithMetadataBlocks(world, box, 0, 4, 4, 3, 4, 4, ModBlocks.concrete_pillar, pillarMetaWE);
					fillWithBlocks(world, box, 0, 4, 5, 2, 4, 5, ModBlocks.reinforced_brick);
					fillWithMetadataBlocks(world, box, right, 4, 6, 1, 4, 6, ModBlocks.concrete_pillar, pillarMetaWE);
					
					if(bulkheadNX) {
						fillWithBlocks(world, box, 0, 1, 1, 0, 1, 2, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 0, 2, 1, 0, 2, 2, ModBlocks.reinforced_stone);
						fillWithBlocks(world, box, 0, 3, 1, 0, 3, 2, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 0, 1, 6, 0, 1, 7, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 0, 2, 6, 0, 2, 7, ModBlocks.reinforced_stone);
						fillWithBlocks(world, box, 0, 3, 6, 0, 3, 7, ModBlocks.reinforced_brick);
						fillWithAir(world, box, 0, 1, 3, 0, 3, 5);
					} else {
						fillWithAir(world, box, 0, 1, 1, 0, 3, 7);
						placeBlockAtCurrentPosition(world, ModBlocks.vinyl_tile, 0, 0, 0, 1, box); //outlier single-block placing operations
						placeBlockAtCurrentPosition(world, ModBlocks.vinyl_tile, 0, 0, 0, 7, box);
						placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 4, 1, box);
						placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 4, 7, box);
					}
				} else {
					fillWithBlocks(world, box, 1, 0, start, 1, 0, 6, ModBlocks.vinyl_tile); //Floor
					fillWithBlocks(world, box, 0, 1, 1, 0, 1, 7, ModBlocks.reinforced_brick); //Wall
					fillWithBlocks(world, box, 0, 2, 1, 0, 2, 7, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 0, 3, 1, 0, 3, 7, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 1, 4, 2, 1, 4, 6, ModBlocks.reinforced_brick); //Ceiling
					fillWithBlocks(world, box, 2, 4, 3, 2, 4, 5, ModBlocks.reinforced_brick);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 3, 4, 4, box);
				}
				
				if(opensPX) {
					 //Floor
					fillWithBlocks(world, box, 7, 0, start, 7, 0, 1, ModBlocks.vinyl_tile);
					fillWithMetadataBlocks(world, box, 7, 0, 2, left, 0, 2, ModBlocks.vinyl_tile, 1);
					fillWithBlocks(world, box, 7, 0, 3, 8, 0, 5, ModBlocks.vinyl_tile);
					fillWithMetadataBlocks(world, box, 7, 0, 6, left, 0, 6, ModBlocks.vinyl_tile, 1);
					 //Ceiling
					fillWithMetadataBlocks(world, box, 7, 4, 2, left, 4, 2, ModBlocks.concrete_pillar, pillarMetaWE);
					fillWithBlocks(world, box, 6, 4, 3, 8, 4, 3, ModBlocks.reinforced_brick);
					fillWithMetadataBlocks(world, box, 5, 4, 4, 8, 4, 4, ModBlocks.concrete_pillar, pillarMetaWE);
					fillWithBlocks(world, box, 6, 4, 5, 8, 4, 5, ModBlocks.reinforced_brick);
					fillWithMetadataBlocks(world, box, 7, 4, 6, left, 4, 6, ModBlocks.concrete_pillar, pillarMetaWE);
					
					if(bulkheadPX) {
						fillWithBlocks(world, box, 8, 1, 1, 8, 1, 2, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 8, 2, 1, 8, 2, 2, ModBlocks.reinforced_stone);
						fillWithBlocks(world, box, 8, 3, 1, 8, 3, 2, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 8, 1, 6, 8, 1, 7, ModBlocks.reinforced_brick);
						fillWithBlocks(world, box, 8, 2, 6, 8, 2, 7, ModBlocks.reinforced_stone);
						fillWithBlocks(world, box, 8, 3, 6, 8, 3, 7, ModBlocks.reinforced_brick);
						fillWithAir(world, box, 8, 1, 3, 8, 3, 5);
					} else {
						fillWithAir(world, box, 8, 1, 1, 8, 3, 7);
						placeBlockAtCurrentPosition(world, ModBlocks.vinyl_tile, 0, 8, 0, 1, box);
						placeBlockAtCurrentPosition(world, ModBlocks.vinyl_tile, 0, 8, 0, 7, box);
						placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 8, 4, 1, box);
						placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 8, 4, 7, box);
					}
				} else {
					fillWithBlocks(world, box, 7, 0, start, 7, 0, 6, ModBlocks.vinyl_tile); //Floor
					fillWithBlocks(world, box, 8, 1, 1, 8, 1, 7, ModBlocks.reinforced_brick); //Wall
					fillWithBlocks(world, box, 8, 2, 1, 8, 2, 7, ModBlocks.reinforced_stone);
					fillWithBlocks(world, box, 8, 3, 1, 8, 3, 7, ModBlocks.reinforced_brick);
					fillWithBlocks(world, box, 6, 4, 3, 6, 4, 5, ModBlocks.reinforced_brick); //Ceiling
					fillWithBlocks(world, box, 7, 4, 2, 7, 4, 6, ModBlocks.reinforced_brick);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 5, 4, 4, box);
				}
				//Wall corners
				if(opensNX || opensPZ) {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 1, 8, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 0, 2, 8, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 0, 3, 8, box);
				}
				
				if(opensPX || opensPZ) {
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 8, 1, 8, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_stone, 0, 8, 2, 8, box);
					placeBlockAtCurrentPosition(world, ModBlocks.reinforced_brick, 0, 8, 3, 8, box);
				}
				
				if(underwater) {
					fillWithWater(world, box, rand, 1, 1, 0, 7, 3, opensPZ ? 8 : 7, 1);
					if(opensNX) fillWithWater(world, box, rand, 0, 1, bulkheadNX ? 3 : 1, 0, 3, bulkheadNX ? 5 : 7, 1);
					if(opensPX) fillWithWater(world, box, rand, 8, 1, bulkheadPX ? 3 : 1, 8, 3, bulkheadPX ? 5 : 7, 1);
				} else
					fillWithCobwebs(world, box, rand, opensNX ? 0 : 1, 1, 0, opensPX ? 8 : 7, 3, opensPZ ? 8 : 7);
				
				return true;
			}
		}
	}
}