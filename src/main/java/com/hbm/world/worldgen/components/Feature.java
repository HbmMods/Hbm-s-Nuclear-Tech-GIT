package com.hbm.world.worldgen.components;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.config.StructureConfig;
import com.hbm.lib.HbmChestContents;
import com.hbm.tileentity.machine.TileEntityLockableBase;
import com.hbm.tileentity.machine.storage.TileEntityCrateIron;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

abstract public class Feature extends StructureComponent {
	/** The size of the bounding box for this feature in the X axis */
	protected int sizeX;
	/** The size of the bounding box for this feature in the Y axis */
	protected int sizeY;
	/** The size of the bounding box for this feature in the Z axis */
	protected int sizeZ;
	/** Average height (Presumably stands for height position) */
	protected int hpos = -1;
	
	protected Feature() {
		super(0);
	}
	
	protected Feature(Random rand, int minX, int minY, int minZ, int maxX, int maxY, int maxZ ) {
		super(0);
		this.sizeX = maxX;
		this.sizeY = maxY;
		this.sizeZ = maxZ;
		this.coordBaseMode = rand.nextInt(4);
		
		switch(this.coordBaseMode) {
		case 0:
			this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxX, minY + maxY, minZ + maxZ);
			break;
		case 1:
			this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxZ, minY + maxY, minZ + maxX);
			break;
		case 2:
			this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxX, minY + maxY, minZ + maxZ);
			break;
		case 3:
			this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxZ, minY + maxY, minZ + maxX);
			break;
		default:
			this.boundingBox = new StructureBoundingBox(minX, minY, minZ, minX + maxX, minY + maxY, minZ + maxZ);
			
		}
	}
	
	/** Set to NBT */
	protected void func_143012_a(NBTTagCompound nbt) {
		nbt.setInteger("Width", this.sizeX);
		nbt.setInteger("Height", this.sizeY);
		nbt.setInteger("Depth", this.sizeZ);
		nbt.setInteger("HPos", this.hpos);
	}
	
	/** Get from NBT */
	protected void func_143011_b(NBTTagCompound nbt) {
		this.sizeX = nbt.getInteger("Width");
		this.sizeY = nbt.getInteger("Height");
		this.sizeZ = nbt.getInteger("Depth");
		this.hpos = nbt.getInteger("HPos");
	}
	
	protected boolean setAverageHeight(World world, StructureBoundingBox box, int y) {
		
		int total = 0;
		int iterations = 0;
		
		for(int z = this.boundingBox.minZ; z <= this.boundingBox.maxZ; z++) {
			for(int x = this.boundingBox.minX; x <= this.boundingBox.maxX; x++) {
				if(box.isVecInside(x, y, z)) {
					total += Math.max(world.getTopSolidOrLiquidBlock(x, z), world.provider.getAverageGroundLevel());
					iterations++;
				}
			}
		}
		
		if(iterations == 0)
			return false;
		
		this.hpos = total / iterations; //finds mean of every block in bounding box
		this.boundingBox.offset(0, this.hpos - this.boundingBox.minY, 0);
		return true;
	}
	
	/** Metadata for Decoration Methods **/
	
	/**
	 * Gets metadata for rotatable pillars.
	 * @param metadata (First two digits are equal to block metadata, other two are equal to orientation
	 * @return metadata adjusted for random orientation
	 */
	protected int getPillarMeta(int metadata) {
		if(this.coordBaseMode % 2 != 0 && this.coordBaseMode != -1)
			metadata = metadata ^ 12;
		
		return metadata;
	}
	
	/**
	 * Gets metadata for rotatable DecoBlock
	 * honestly i don't remember how i did this and i'm scared to optimize it because i fail to see any reasonable patterns like the pillar
	 * seriously, 3 fucking bits for 4 orientations when you can do it easily with 2?
	 * @param metadata (2 for facing South, 3 for facing North, 4 for facing East, 5 for facing West
	 */
	protected int getDecoMeta(int metadata) {
		switch(this.coordBaseMode) {
		case 0: //South
			switch(metadata) {
			case 2: return 2;
			case 3: return 3;
			case 4: return 4;
			case 5: return 5;
			}
		case 1: //West
			switch(metadata) {
			case 2: return 5;
			case 3: return 4;
			case 4: return 2;
			case 5: return 3;
			}
		case 2: //North
			switch(metadata) {
			case 2: return 3;
			case 3: return 2;
			case 4: return 5;
			case 5: return 4;
			}
		case 3: //East
			switch(metadata) {
			case 2: return 4;
			case 3: return 5;
			case 4: return 3;
			case 5: return 2;
			}
		}
		return 0;
	}
	
	/**
	 * Get orientation-offset metadata for BlockDecoModel
	 * @param metadata (0 for facing North, 1 for facing South, 2 for facing West, 3 for facing East)
	 */
	protected int getDecoModelMeta(int metadata) {
		//N: 0b00, S: 0b01, W: 0b10, E: 0b11
		
		switch(this.coordBaseMode) {
		default: //South
			break;
		case 1: //West
			if((metadata & 3) < 2) //N & S can just have bits toggled
				metadata = metadata ^ 3;
			else //W & E can just have first bit set to 0
				metadata = metadata ^ 2;
			break;
		case 2: //North
			metadata = metadata ^ 1; //N, W, E & S can just have first bit toggled
			break;
		case 3: //East
			if((metadata & 3) < 2)//N & S can just have second bit set to 1
				metadata = metadata ^ 2;
			else //W & E can just have bits toggled
				metadata = metadata ^ 3;
			break;
		}
		
		return metadata;
	}
	
	/**
	 * Gets orientation-adjusted meta for stairs.
	 * 0 = West, 1 = East, 2 = North, 3 = South
	 */
	protected int getStairMeta(int metadata) {
		switch(this.coordBaseMode) {
		default: //South
			break;
		case 1: //West
			if((metadata & 3) < 2) //Flip second bit for E/W
				metadata = metadata ^ 2;
			else
				metadata = metadata ^ 3; //Flip both bits for N/S
			break;
		case 2: //North
			metadata = metadata ^ 1; //Flip first bit
			break;
		case 3: //East
			if((metadata & 3) < 2) //Flip both bits for E/W
				metadata = metadata ^ 3;
			else //Flip second bit for N/S
				metadata = metadata ^ 2;
			break;
		}
		
		return metadata;
	}
	
	/* For Later:
	* 0/S: S->S; W->W; N->N; E->E
	* 1/W: S->W; W->N; N->E; E->S
	* 2/N: S->N; W->E; N->S; E->W
	* 3/E: S->E; W->S; N->W; E->N
	* 0/b00/W, 1/b01/N, 2/b10/E, 3/b11/S
	*/
	/**
	 * Places door at specified location with orientation-adjusted meta
	 * 0 = West, 1 = North, 2 = East, 3 = South
	 */
	protected void placeDoor(World world, StructureBoundingBox box, Block door, int meta, int featureX, int featureY, int featureZ) {
		switch(this.coordBaseMode) {
		default:
			break;
		case 1:
			meta = (meta + 1) % 4; break;
		case 2:
			meta = meta ^ 2; break; //Flip second bit
		case 3:
			meta = (meta - 1) % 4; break;
		}
		
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);
		
		this.placeBlockAtCurrentPosition(world, door, meta, featureX, featureY, featureZ, box);
		ItemDoor.placeDoorBlock(world, posX, posY, posZ, meta, door);
	}
	
	/** Loot Methods **/
	
	/**
	 * it feels disgusting to make a method with this many parameters but fuck it, it's easier
	 * @return TE implementing IInventory with randomized contents
	 */
	protected boolean generateInvContents(World world, StructureBoundingBox box, Random rand, Block block, int featureX, int featureY, int featureZ, WeightedRandomChestContent[] content, int amount) {
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);
		
		this.placeBlockAtCurrentPosition(world, block, 0, featureX, featureY, featureZ, box);
		IInventory inventory = (IInventory)world.getTileEntity(posX, posY, posZ);
		
		if(inventory != null) {
			amount = (int)Math.floor(amount * StructureConfig.lootAmountFactor);
			WeightedRandomChestContent.generateChestContents(rand, content, inventory, amount < 1 ? 1 : amount);
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Block TE MUST extend TileEntityLockableBase, otherwise this will not work and crash!
	 * @return TE implementing IInventory and extending TileEntityLockableBase with randomized contents + lock
	 */
	protected boolean generateLockableContents(World world, StructureBoundingBox box, Random rand, Block block, int featureX, int featureY, int featureZ,
			WeightedRandomChestContent[] content, int amount, double mod) {
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);
		
		this.placeBlockAtCurrentPosition(world, block, 0, featureX, featureY, featureZ, box);
		TileEntity tile = world.getTileEntity(posX, posY, posZ);
		TileEntityLockableBase lock = (TileEntityLockableBase) tile;
		IInventory inventory = (IInventory) tile;
		
		if(inventory != null && lock != null) {
			lock.setPins(rand.nextInt(999) + 1);
			lock.setMod(mod);
			lock.lock();
			
			amount = (int)Math.floor(amount * StructureConfig.lootAmountFactor);
			WeightedRandomChestContent.generateChestContents(rand, content, inventory, amount < 1 ? 1 : amount);
			return true;
		}
		
		return false;
	}
	
	protected void generateLoreBook(World world, StructureBoundingBox box, int featureX, int featureY, int featureZ, int slot, String key) {
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);
		
		IInventory inventory = (IInventory) world.getTileEntity(posX, posY, posZ);
		
		if(inventory != null) {
			ItemStack book = HbmChestContents.genetateBook(key);
			
			inventory.setInventorySlotContents(slot, book);
		}
	}
	
	/**
	 * Places random bobblehead with a randomized orientation at specified location
	 */
	protected void placeRandomBobble(World world, StructureBoundingBox box, Random rand, int featureX, int featureY, int featureZ) {
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);
		
		placeBlockAtCurrentPosition(world, ModBlocks.bobblehead, rand.nextInt(16), featureX, featureY, featureZ, box);
		TileEntityBobble bobble = (TileEntityBobble) world.getTileEntity(posX, posY, posZ);
		
		if(bobble != null) {
			bobble.type = BobbleType.values()[rand.nextInt(BobbleType.values().length - 1) + 1];
			bobble.markDirty();
		}
	}
	
	/** Block Placement Utility Methods **/
	
	/**
	 * Places blocks underneath location until reaching a solid block; good for foundations
	 */
	protected void placeFoundationUnderneath(World world, Block placeBlock, int meta, int minX, int minZ, int maxX, int maxZ, int featureY, StructureBoundingBox box) {
		
		for(int featureX = minX; featureX <= maxX; featureX++) {
			for(int featureZ = minZ; featureZ <= maxZ; featureZ++) {
				int posX = this.getXWithOffset(featureX, featureZ);
				int posY = this.getYWithOffset(featureY);
				int posZ = this.getZWithOffset(featureX, featureZ);
				
				if(box.isVecInside(posX, posY, posZ)) {
					Block block = world.getBlock(posX, posY, posZ);
					
					while ((world.isAirBlock(posX, posY, posZ) || !block.getMaterial().isSolid() || (block.isFoliage(world, posX, posY, posZ) || block.getMaterial() == Material.leaves)) && posY > 1) {
						world.setBlock(posX, posY, posZ, placeBlock, meta, 2);
						block = world.getBlock(posX, --posY, posZ);
					}
				}
			}
		}
	}
	
	/**
	 * Places specified blocks on top of pre-existing blocks in a given area, up to a certain height. Does NOT place blocks on top of liquids.
	 * Useful for stuff like fences and walls most likely.
	 */
	protected void placeBlocksOnTop(World world, StructureBoundingBox box, Block block, int minX, int minZ, int maxX, int maxZ, int height) {
		
		for(int x = minX; x <= maxX; x++) {
			for(int z = minZ; z <= maxZ; z++) {
				int posX = this.getXWithOffset(x, z);
				int posZ = this.getZWithOffset(x, z);
				int topHeight = world.getTopSolidOrLiquidBlock(posX, posZ);
				
				if(!world.getBlock(posX, topHeight, posZ).getMaterial().isLiquid()) {
					
					for(int i = 0; i < height; i++) {
						int posY = topHeight + i;
						
						world.setBlock(posX, posY, posZ, block, 0, 2);
					}
				}
			}
		}
	}
	
	/** getXWithOffset & getZWithOffset Methods that are actually fixed **/
	//Turns out, this entire time every single minecraft structure is mirrored instead of rotated when facing East and North
	//Also turns out, it's a scarily easy fix that they somehow didn't see *entirely*
	@Override
	protected int getXWithOffset(int x, int z) {
		switch(this.coordBaseMode) {
		case 0:
			return this.boundingBox.minX + x;
		case 1:
			return this.boundingBox.maxX - z;
		case 2:
			return this.boundingBox.maxX - x;
		case 3:
			return this.boundingBox.minX + z;
		default:
			return x;
		}
	}
	
	@Override
	protected int getZWithOffset(int x, int z) {
		switch(this.coordBaseMode) {
		case 0:
			return this.boundingBox.minZ + z;
		case 1:
			return this.boundingBox.minZ + x;
		case 2:
			return this.boundingBox.maxZ - z;
		case 3:
			return this.boundingBox.maxZ - x;
		default:
			return x;
		}
	}
	
	/** Methods that remove the replaceBlock and alwaysReplace, and other parameters: as they are useless and only serve as a time sink normally. */
	
	protected void fillWithBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block) {
		this.fillWithBlocks(world, box, minX, minY, minZ, maxX, maxY, maxZ, block, block, false);
	}
	
	protected void fillWithMetadataBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block, int meta) {
		this.fillWithMetadataBlocks(world, box, minX, minY, minZ, maxX, maxY, maxZ, block, meta, block, meta, false);
	}
	
	protected void randomlyFillWithBlocks(World world, StructureBoundingBox box, Random rand, float randLimit, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block) {
		this.randomlyFillWithBlocks(world, box, rand, randLimit, minX, minY, minZ, maxX, maxY, maxZ, block, block, false);
	}
	
	protected void fillWithRandomizedBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Random rand, BlockSelector selector) {
		this.fillWithRandomizedBlocks(world, box, minX, minY, minZ, maxX, maxY, maxZ, false, rand, selector);
	}
	
	/** Block Selectors **/
	
	static class Sandstone extends StructureComponent.BlockSelector {
		
		Sandstone() { }
		
		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean p_75062_5_) {
			float chance = rand.nextFloat();
			
			if(chance > 0.6F) {
				this.field_151562_a = Blocks.sandstone;
			} else if (chance < 0.5F ) {
				this.field_151562_a = ModBlocks.reinforced_sand;
			} else {
				this.field_151562_a = Blocks.sand;
			}
		}
	}
	
	static class ConcreteBricks extends StructureComponent.BlockSelector {
		
		ConcreteBricks() { }
		
		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean p_75062_5_) {
			float chance = rand.nextFloat();
			
			if(chance < 0.2F) {
				this.field_151562_a = ModBlocks.brick_concrete;
			} else if (chance < 0.55F) {
				this.field_151562_a = ModBlocks.brick_concrete_mossy;
			} else if (chance < 0.75F) {
				this.field_151562_a = ModBlocks.brick_concrete_cracked;
			} else {
				this.field_151562_a = ModBlocks.brick_concrete_broken;
			}
		}
	}
	
	//ag
	static class LabTiles extends StructureComponent.BlockSelector {
		
		LabTiles() { }
		
		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean p_75062_5_) {
			float chance = rand.nextFloat();
			
			if(chance < 0.5F) {
				this.field_151562_a = ModBlocks.tile_lab;
			} else if (chance < 0.9F) {
				this.field_151562_a = ModBlocks.tile_lab_cracked;
			} else {
				this.field_151562_a = ModBlocks.tile_lab_broken;
			}
		}
	}
	
	static class SuperConcrete extends StructureComponent.BlockSelector {
		
		SuperConcrete() {
			this.field_151562_a = ModBlocks.concrete_super;
		}
		
		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean p_75062_5_) {
			this.selectedBlockMetaData = rand.nextInt(6) + 10;
		}
	}
	
}
