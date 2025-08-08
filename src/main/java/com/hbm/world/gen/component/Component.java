package com.hbm.world.gen.component;

import java.util.Random;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.config.StructureConfig;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.machine.TileEntityLockableBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.util.ForgeDirection;

abstract public class Component extends StructureComponent {

	/** Average height (Presumably stands for height position) */
	protected int hpos = -1;

	protected Component() {
		super(0);
	}

	protected Component(int componentType) {
		super(componentType);
	}

	protected Component(Random rand, int minX, int minY, int minZ, int maxX, int maxY, int maxZ ) {
		super(0);
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
		nbt.setInteger("HPos", this.hpos);
	}

	/** Get from NBT */
	protected void func_143011_b(NBTTagCompound nbt) {
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

	protected static int getAverageHeight(World world, StructureBoundingBox area, StructureBoundingBox box, int y) {

		int total = 0;
		int iterations = 0;

		for(int z = area.minZ; z <= area.maxZ; z++) {
			for(int x = area.minX; x <= area.maxX; x++) {
				if(box.isVecInside(x, y, z)) {
					total += Math.max(world.getTopSolidOrLiquidBlock(x, z), world.provider.getAverageGroundLevel());
					iterations++;
				}
			}
		}

		if(iterations == 0)
			return -1;

		return total / iterations;
	}

	public int getCoordMode() {
		return this.coordBaseMode;
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
	 * should work for hoppers, just flip dir for N/S and W/E
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
	 * Get orientation-offset metadata for BlockDecoModel; also suitable for trapdoors
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
		//genuinely like. why did i do that
		return metadata << 2; //To accommodate for BlockDecoModel's shift in the rotation bits; otherwise, simply bit-shift right and or any non-rotation meta after
	}

	//works for crts, toasters, and anything that follows mc's cardinal dirs. S: 0, W: 1, N: 2, E: 3
	protected int getCRTMeta(int meta) {
		return (meta + this.coordBaseMode) % 4;
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

	/*
	 * Assuming door is on opposite side of block from direction: East: 0, South: 1, West: 2, North: 3<br>
	 * Doors cleverly take advantage of the use of two blocks to get around the 16 value limit on metadata, with the top and bottom blocks essentially relying on eachother for everything.<br>
	 * <li>The 4th bit (0b1000 or 8) indicates whether it is the top block: on for yes, off for no.
	 * <li>When the 4th bit is on, the 1st bit indicates whether the door opens to the right or not: on (0b1001) for yes, off (0b1000) for no.
	 * <li>The bits 1 & 2 (0b0011 or 3) indicate the direction the door is facing.
	 * <li>When the 4th bit is off, the 3rd bit (0b0100 or 4) indicates whether the door is open or not: on for yes, off for no. Used for doors' interactions with redstone power.
	 * </li>
	 */
	protected void placeDoor(World world, StructureBoundingBox box, Block door, int dirMeta, boolean opensRight, boolean isOpen, int featureX, int featureY, int featureZ) { //isOpen for randomly opened doors
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);

		if(!box.isVecInside(posX, posY, posZ)) return;

		switch(this.coordBaseMode) {
		default: //South
			break;
		case 1: //West
			dirMeta = (dirMeta + 1) % 4; break;
		case 2: //North
			dirMeta ^= 2; break; //Flip second bit
		case 3: //East
			dirMeta = (dirMeta + 3) % 4; break; //fuck you modulo
		}

		//hee hoo
		int metaTop = opensRight ? 0b1001 : 0b1000;
		int metaBottom = dirMeta | (isOpen ? 0b100 : 0);

		if(world.doesBlockHaveSolidTopSurface(world, posX, posY - 1, posZ)) {
			world.setBlock(posX, posY, posZ, door, metaBottom, 2);
			world.setBlock(posX, posY + 1, posZ, door, metaTop, 2);
		}
	}
	/** 1 for west face, 2 for east face, 3 for north, 4 for south*/
	protected void placeLever(World world, StructureBoundingBox box, int dirMeta, boolean on, int featureX, int featureY, int featureZ) {
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);

		if(!box.isVecInside(posX, posY, posZ)) return;

		if(dirMeta <= 0 || dirMeta >= 7) { //levers suck ass
			switch(this.coordBaseMode) {
			case 1: case 3: //west / east
				dirMeta ^= 0b111;
			}
		} else if(dirMeta >= 5) {
			switch(this.coordBaseMode) {
			case 1: case 3: //west / east
				dirMeta = (dirMeta + 1) % 2 + 5;
			}
		} else {
			dirMeta = getButtonMeta(dirMeta);
		}

		world.setBlock(posX, posY, posZ, Blocks.lever, on ? dirMeta | 8 : dirMeta, 2);
	}

	/** pain. works for side-facing levers as well */
	protected int getButtonMeta(int dirMeta) {
		switch(this.coordBaseMode) { //are you ready for the pain?
		case 1: //West
			if(dirMeta <= 2) return dirMeta + 2;
			else if(dirMeta < 4) return dirMeta - 1;
			else return dirMeta - 3;// this shit sucks ass
		case 2: //North
			return dirMeta + (dirMeta % 2 == 0 ? -1 : 1);
		case 3: //East
			if(dirMeta <= 1) return dirMeta + 3;
			else if(dirMeta <= 2) return dirMeta + 1;
			else return dirMeta - 2;
		default: //South
			return dirMeta;
		}
	}

	/**N:0 W:1 S:2 E:3 */
	protected void placeBed(World world, StructureBoundingBox box, int meta, int featureX, int featureY, int featureZ) {
		int xOffset = 0;
		int zOffset = 0;

		switch(meta & 3) {
		default:
			zOffset = 1; break;
		case 1:
			xOffset = -1; break;
		case 2:
			zOffset = -1; break;
		case 3:
			xOffset = 1; break;
		}

		switch(this.coordBaseMode) {
		default: //S
			break;
		case 1: //W
			meta = (meta + 1) % 4; break;
		case 2: //N
			meta ^= 2; break;
		case 3: //E
			meta = (meta - 1) % 4; break;
		}

		placeBlockAtCurrentPosition(world, Blocks.bed, meta, featureX, featureY, featureZ, box);
		placeBlockAtCurrentPosition(world, Blocks.bed, meta + 8, featureX + xOffset, featureY, featureZ + zOffset, box);
	}

	/**Tripwire Hook: S:0 W:1 N:2 E:3 */
	protected int getTripwireMeta(int metadata) {
		switch(this.coordBaseMode) {
		default:
			return metadata;
		case 1:
			return (metadata + 1) % 4;
		case 2:
			return metadata ^ 2;
		case 3:
			return (metadata - 1) % 4;
		}
	}


	/** Loot Methods **/

	/**
	 * it feels disgusting to make a method with this many parameters but fuck it, it's easier
	 * @return TE implementing IInventory with randomized contents
	 */
	protected boolean generateInvContents(World world, StructureBoundingBox box, Random rand, Block block, int featureX, int featureY, int featureZ, WeightedRandomChestContent[] content, int amount) {
		return generateInvContents(world, box, rand, block, 0, featureX, featureY, featureZ, content, amount);
	}

	//TODO: explore min / max item generations: e.g., between 3 and 5 separate items are generated
	protected boolean generateInvContents(World world, StructureBoundingBox box, Random rand, Block block, int meta, int featureX, int featureY, int featureZ, WeightedRandomChestContent[] content, int amount) {
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);

		if(!box.isVecInside(posX, posY, posZ) || world.getBlock(posX, posY, posZ) == block) //replacement for hasPlacedLoot checks
			return true;

		this.placeBlockAtCurrentPosition(world, block, meta, featureX, featureY, featureZ, box);
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
		return generateLockableContents(world, box, rand, block, 0, featureX, featureY, featureZ, content, amount, mod);
	}

	protected boolean generateLockableContents(World world, StructureBoundingBox box, Random rand, Block block, int meta, int featureX, int featureY, int featureZ,
			WeightedRandomChestContent[] content, int amount, double mod) {
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);

		if(!box.isVecInside(posX, posY, posZ) || world.getBlock(posX, posY, posZ) == block) //replacement for hasPlacedLoot checks
			return false;

		this.placeBlockAtCurrentPosition(world, block, meta, featureX, featureY, featureZ, box);
		TileEntity tile = world.getTileEntity(posX, posY, posZ);
		TileEntityLockableBase lock = (TileEntityLockableBase) tile;
		IInventory inventory = (IInventory) tile;

		if(inventory != null && lock != null) {
			amount = (int)Math.floor(amount * StructureConfig.lootAmountFactor);
			WeightedRandomChestContent.generateChestContents(rand, content, inventory, amount < 1 ? 1 : amount);

			lock.setPins(rand.nextInt(999) + 1);
			lock.setMod(mod);
			lock.lock();
			return true;
		}

		return false;
	}

	protected void generateLoreBook(World world, StructureBoundingBox box, int featureX, int featureY, int featureZ, int slot, ItemStack stack) { //kept for compat
		int posX = this.getXWithOffset(featureX, featureZ);
		int posY = this.getYWithOffset(featureY);
		int posZ = this.getZWithOffset(featureX, featureZ);

		if(!box.isVecInside(posX, posY, posZ)) return;

		IInventory inventory = (IInventory) world.getTileEntity(posX, posY, posZ);

		if(inventory != null) {
			inventory.setInventorySlotContents(slot, stack);
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
					int brake = 0;

					while ((world.isAirBlock(posX, posY, posZ) ||
							!block.getMaterial().isSolid() ||
							(block.isFoliage(world, posX, posY, posZ) || block.getMaterial() == Material.leaves)) &&
							posY > 1 && brake <= 15) {
						world.setBlock(posX, posY, posZ, placeBlock, meta, 2);
						block = world.getBlock(posX, --posY, posZ);

						brake++;
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
	public int getXWithOffset(int x, int z) {
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
	public int getZWithOffset(int x, int z) {
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
			return z;
		}
	}

	/** Methods that are actually optimized, including ones that cut out replaceBlock and onlyReplace functionality when it's redundant. */
	protected void fillWithAir(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) { //TODO these could technically be optimized a bit more. probably won't do anything but worth

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);

						world.setBlock(posX, posY, posZ, Blocks.air, 0, 2);
					}
				}
			}
		}
	}

	@Override
	protected void fillWithBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block, Block replaceBlock, boolean onlyReplace) {

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) {

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);

						if(!onlyReplace || world.getBlock(posX, posY, posZ).getMaterial() != Material.air) {
							if(x != minX && x != maxX && y != minY && y != maxY && z != minZ && z != maxZ)
								world.setBlock(posX, posY, posZ, replaceBlock, 0, 2);
							else
								world.setBlock(posX, posY, posZ, block, 0, 2);
						}
					}
				}
			}
		}
	}

	protected void fillWithBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block) {

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) {

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);

						world.setBlock(posX, posY, posZ, block, 0, 2);
					}
				}
			}
		}
	}

	@Override
	protected void fillWithMetadataBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block, int meta, Block replaceBlock, int replaceMeta, boolean onlyReplace) {

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) {

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);

						if(!onlyReplace || world.getBlock(posX, posY, posZ).getMaterial() != Material.air) {
							if(x != minX && x != maxX && y != minY && y != maxY && z != minZ && z != maxZ)
								world.setBlock(posX, posY, posZ, replaceBlock, replaceMeta, 2);
							else
								world.setBlock(posX, posY, posZ, block, meta, 2);
						}
					}
				}
			}
		}
	}

	protected void fillWithMetadataBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block, int meta) {

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) {

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);

						world.setBlock(posX, posY, posZ, block, meta, 2);
					}
				}
			}
		}
	}

	@Override
	protected void fillWithRandomizedBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean onlyReplace, Random rand, BlockSelector selector) {

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) {

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);

						if(!onlyReplace || world.getBlock(posX, posY, posZ).getMaterial() != Material.air) {
							selector.selectBlocks(rand, posX, posY, posZ, x == minX || x == maxX || y == minY || y == maxY || z == minZ || z == maxZ);
							world.setBlock(posX, posY, posZ, selector.func_151561_a(), selector.getSelectedBlockMetaData(), 2);
						}
					}
				}
			}
		}
	}
	//TODO replace the shitty block selector with something else. probably a lambda that returns a metablock for convenience
	protected void fillWithRandomizedBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Random rand, BlockSelector selector) { //so i don't have to replace shit

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) {

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);
						//keep this functionality in mind!
						selector.selectBlocks(rand, posX, posY, posZ, false); //for most structures it's redundant since nothing is just hollow cubes, but vanilla structures rely on this. use the method above in that case.
						world.setBlock(posX, posY, posZ, selector.func_151561_a(), selector.getSelectedBlockMetaData(), 2);
					}
				}
			}
		}
	}

	//stairs and shit
	protected void fillWithRandomizedBlocksMeta(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Random rand, BlockSelector selector, int meta) {

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) {

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);
						//keep this functionality in mind!
						selector.selectBlocks(rand, posX, posY, posZ, false); //for most structures it's redundant since nothing is just hollow cubes, but vanilla structures rely on this. use the method above in that case.
						world.setBlock(posX, posY, posZ, selector.func_151561_a(), meta | selector.getSelectedBlockMetaData(), 2); //Make sure the metadata is initialized in the ctor!
					}
				}
			}
		}
	}

	@Override
	protected void randomlyFillWithBlocks(World world, StructureBoundingBox box, Random rand, float randLimit, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block, Block replaceBlock, boolean onlyReplace) {

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) {

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);

						if(rand.nextFloat() <= randLimit && (!onlyReplace || world.getBlock(posX, posY, posZ).getMaterial() != Material.air)) {
							if(x != minX && x != maxX && y != minY && y != maxY && z != minZ && z != maxZ)
								world.setBlock(posX, posY, posZ, replaceBlock, 0, 2);
							else
								world.setBlock(posX, posY, posZ, block, 0, 2);
						}
					}
				}
			}
		}
	}

	protected void randomlyFillWithBlocks(World world, StructureBoundingBox box, Random rand, float randLimit, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block) {

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) {

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);

						if(rand.nextFloat() <= randLimit)
							world.setBlock(posX, posY, posZ, block, 0, 2);
					}
				}
			}
		}
	}

	protected void randomlyFillWithBlocks(World world, StructureBoundingBox box, Random rand, float randLimit, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, Block block, int meta) {

		if(getYWithOffset(minY) < box.minY || getYWithOffset(maxY) > box.maxY)
			return;

		for(int x = minX; x <= maxX; x++) {

			for(int z = minZ; z <= maxZ; z++) {
				int posX = getXWithOffset(x, z);
				int posZ = getZWithOffset(x, z);

				if(posX >= box.minX && posX <= box.maxX && posZ >= box.minZ && posZ <= box.maxZ) {
					for(int y = minY; y <= maxY; y++) {
						int posY = getYWithOffset(y);

						if(rand.nextFloat() <= randLimit)
							world.setBlock(posX, posY, posZ, block, meta, 2);
					}
				}
			}
		}
	}

	protected ForgeDirection getDirection(ForgeDirection dir) {
		switch(coordBaseMode) {
		default: //South
			return dir;
		case 1: //West
			return dir.getRotation(ForgeDirection.UP);
		case 2: //North
			return dir.getOpposite();
		case 3: //East
			return dir.getRotation(ForgeDirection.DOWN);
		}
	}

	/** Sets the core block for a BlockDummyable multiblock. WARNING: Does not take {@link com.hbm.blocks.BlockDummyable#getDirModified(ForgeDirection)} or {@link com.hbm.blocks.BlockDummyable#getMetaForCore(World, int, int, int, EntityPlayer, int)}
	 * into account yet! This will be changed as it comes up!<br>
	 * For BlockDummyables, 'dir' <b>always</b> faces the player, being the opposite of the player's direction. This is already taken into account. */
	protected void placeCore(World world, StructureBoundingBox box, Block block, ForgeDirection dir, int x, int y, int z) {
		int posX = getXWithOffset(x, z);
		int posZ = getZWithOffset(x, z);
		int posY = getYWithOffset(y);

		if(!box.isVecInside(posX, posY, posZ)) return;

		if(dir == null)
			dir = ForgeDirection.NORTH;

		dir = getDirection(dir.getOpposite());
		world.setBlock(posX, posY, posZ, block, dir.ordinal() + BlockDummyable.offset, 2);
	}

	//always set the core block first
	/** StructureComponent-friendly method for {@link com.hbm.handler.MultiblockHandlerXR#fillSpace(World, int, int, int, int[], Block, ForgeDirection)}. Prevents runoff outside of the provided bounding box.<br>
	 * For BlockDummyables, 'dir' <b>always</b> faces the player, being the opposite of the player's direction. This is already taken into account. */
	protected void fillSpace(World world, StructureBoundingBox box, int x, int y, int z, int[] dim, Block block, ForgeDirection dir) {

		if(getYWithOffset(y - dim[1]) < box.minY || getYWithOffset(y + dim[0]) > box.maxY) //the BlockDummyable will be fucked regardless if it goes beyond either limit
			return;

		if(dir == null)
			dir = ForgeDirection.NORTH;

		dir = getDirection(dir.getOpposite());

		int count = 0;

		int[] rot = MultiblockHandlerXR.rotate(dim, dir);

		int posX = getXWithOffset(x, z);
		int posZ = getZWithOffset(x, z); //MY SILLY ASS OPERATING WITH ALREADY FUCKING MODIFIED VARIABLES CLOWNKOEN
		int posY = getYWithOffset(y);

		BlockDummyable.safeRem = true;

		for(int a = posX - rot[4]; a <= posX + rot[5]; a++) {
			for(int c = posZ - rot[2]; c <= posZ + rot[3]; c++) {

				if(a >= box.minX && a <= box.maxX && c >= box.minZ && c <= box.maxZ) {
					for(int b = posY - rot[1]; b <= posY + rot[0]; b++) {

						int meta = 0;

						if(b < posY) {
							meta = ForgeDirection.DOWN.ordinal();
						} else if(b > posY) {
							meta = ForgeDirection.UP.ordinal();
						} else if(a < posX) {
							meta = ForgeDirection.WEST.ordinal();
						} else if(a > posX) {
							meta = ForgeDirection.EAST.ordinal();
						} else if(c < posZ) {
							meta = ForgeDirection.NORTH.ordinal();
						} else if(c > posZ) {
							meta = ForgeDirection.SOUTH.ordinal();
						} else {
							continue;
						}

						world.setBlock(a, b, c, block, meta, 2);

						count++;

						if(count > 2000) {
							System.out.println("component's fillspace: ded " + a + " " + b + " " + c + " " + x + " " + y + " " + z);

							BlockDummyable.safeRem = false;
							return;
						}
					}
				}
			}
		}

		BlockDummyable.safeRem = false;
	}

	/** StructureComponent-friendly method for {@link com.hbm.blocks.BlockDummyable#makeExtra(World, int, int, int)}. Prevents runoff outside of the provided bounding box. */
		public void makeExtra(World world, StructureBoundingBox box, Block block, int x, int y, int z) {
			int posX = getXWithOffset(x, z);
			int posZ = getZWithOffset(x, z);
			int posY = getYWithOffset(y);

			if(!box.isVecInside(posX, posY, posZ))
				return;

			if(world.getBlock(posX, posY, posZ) != block)
				return;

			int meta = world.getBlockMetadata(posX, posY, posZ);

			if(meta > 5)
				return;

			BlockDummyable.safeRem = true;
			world.setBlock(posX, posY, posZ, block, meta + BlockDummyable.extra, 3);
			BlockDummyable.safeRem = false;
		}

	/** Block Selectors **/

	static class Sandstone extends StructureComponent.BlockSelector {

		Sandstone() { }

		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
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
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			float chance = rand.nextFloat();

			if(chance < 0.4F) {
				this.field_151562_a = ModBlocks.brick_concrete;
			} else if (chance < 0.7F) {
				this.field_151562_a = ModBlocks.brick_concrete_mossy;
			} else if (chance < 0.9F) {
				this.field_151562_a = ModBlocks.brick_concrete_cracked;
			} else {
				this.field_151562_a = ModBlocks.brick_concrete_broken;
			}
		}
	}

	static class ConcreteBricksStairs extends StructureComponent.BlockSelector {

		ConcreteBricksStairs() {
			this.selectedBlockMetaData = 0;
		}

		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			float chance = rand.nextFloat();

			if(chance < 0.4F) {
				this.field_151562_a = ModBlocks.brick_concrete_stairs;
			} else if (chance < 0.7F) {
				this.field_151562_a = ModBlocks.brick_concrete_mossy_stairs;
			} else if (chance < 0.9F) {
				this.field_151562_a = ModBlocks.brick_concrete_cracked_stairs;
			} else {
				this.field_151562_a = ModBlocks.brick_concrete_broken_stairs;
			}
		}
	}

	static class ConcreteBricksSlabs extends StructureComponent.BlockSelector {

		ConcreteBricksSlabs() {
			this.field_151562_a = ModBlocks.concrete_brick_slab;
			this.selectedBlockMetaData = 0;
		}

		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			float chance = rand.nextFloat();

			if (chance >= 0.4F && chance < 0.7F) {
				this.selectedBlockMetaData |= 1;
			} else if (chance < 0.9F) {
				this.selectedBlockMetaData |= 2;
			} else {
				this.selectedBlockMetaData |= 3;
			}
		}
	}

	//ag
	static class LabTiles extends StructureComponent.BlockSelector {

		LabTiles() { }

		/** Selects blocks */
		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
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
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			this.selectedBlockMetaData = rand.nextInt(6) + 10;
		}
	}

	public static class MeteorBricks extends BlockSelector {

		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			float chance = rand.nextFloat();

			if(chance < 0.4F) {
				this.field_151562_a = ModBlocks.meteor_brick;
			} else if (chance < 0.7F) {
				this.field_151562_a = ModBlocks.meteor_brick_mossy;
			} else {
				this.field_151562_a = ModBlocks.meteor_brick_cracked;
			}
		}

	}

	public static class SupplyCrates extends BlockSelector {

		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			float chance = rand.nextFloat();

			if(chance < 0.6F) {
				this.field_151562_a = Blocks.air;
			} else if(chance < 0.8F) {
				this.field_151562_a = ModBlocks.crate_ammo;
			} else if(chance < 0.9F) {
				this.field_151562_a = ModBlocks.crate_can;
			} else {
				this.field_151562_a = ModBlocks.crate;
			}
		}

	}

	public static class CrabSpawners extends BlockSelector {

		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			float chance = rand.nextFloat();

			if(chance < 0.8F) {
				this.field_151562_a = ModBlocks.meteor_brick;
			} else {
				this.field_151562_a = ModBlocks.meteor_spawner;
			}
		}

	}

	public static class GreenOoze extends BlockSelector {

		@Override
		public void selectBlocks(Random rand, int posX, int posY, int posZ, boolean notInterior) {
			float chance = rand.nextFloat();

			if(chance < 0.8F) {
				this.field_151562_a = ModBlocks.toxic_block;
			} else {
				this.field_151562_a = ModBlocks.meteor_polished;
			}
		}

	}

}
