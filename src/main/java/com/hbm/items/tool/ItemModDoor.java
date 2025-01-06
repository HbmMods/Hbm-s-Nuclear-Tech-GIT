package com.hbm.items.tool;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemModDoor extends Item {
	
	public ItemModDoor() {
		this.maxStackSize = 1;
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(side != 1) {
			return false;
		} else {
			++y;
			
			Block block = Blocks.air;

			if(this == ModItems.door_metal) block = ModBlocks.door_metal;
			if(this == ModItems.door_office) block = ModBlocks.door_office;
			if(this == ModItems.door_bunker) block = ModBlocks.door_bunker;
			if(this == ModItems.door_red) block = ModBlocks.door_red;

			if(player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack)) {
				if(!block.canPlaceBlockAt(world, x, y, z)) {
					return false;
				} else {
					int i1 = MathHelper.floor_double((double) ((player.rotationYaw + 180.0F) * 4.0F / 360.0F) - 0.5D) & 3;
					placeDoorBlock(world, x, y, z, i1, block);
					--stack.stackSize;
					return true;
				}
			} else {
				return false;
			}
		}
	}

	public static void placeDoorBlock(World world, int x, int y, int z, int meta, Block door) {
		byte offsetX = 0;
		byte offsetZ = 0;

		if(meta == 0) {
			offsetZ = 1;
		}

		if(meta == 1) {
			offsetX = -1;
		}

		if(meta == 2) {
			offsetZ = -1;
		}

		if(meta == 3) {
			offsetX = 1;
		}

		int i1 = (world.getBlock(x - offsetX, y, z - offsetZ).isNormalCube() ? 1 : 0) + (world.getBlock(x - offsetX, y + 1, z - offsetZ).isNormalCube() ? 1 : 0);
		int j1 = (world.getBlock(x + offsetX, y, z + offsetZ).isNormalCube() ? 1 : 0) + (world.getBlock(x + offsetX, y + 1, z + offsetZ).isNormalCube() ? 1 : 0);
		boolean flag = world.getBlock(x - offsetX, y, z - offsetZ) == door || world.getBlock(x - offsetX, y + 1, z - offsetZ) == door;
		boolean flag1 = world.getBlock(x + offsetX, y, z + offsetZ) == door || world.getBlock(x + offsetX, y + 1, z + offsetZ) == door;
		boolean flag2 = false;

		if(flag && !flag1) {
			flag2 = true;
		} else if(j1 > i1) {
			flag2 = true;
		}

		world.setBlock(x, y, z, door, meta, 2);
		world.setBlock(x, y + 1, z, door, 8 | (flag2 ? 1 : 0), 2);
		world.notifyBlocksOfNeighborChange(x, y, z, door);
		world.notifyBlocksOfNeighborChange(x, y + 1, z, door);
	}
}
