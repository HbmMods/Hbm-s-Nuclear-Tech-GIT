package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemWand extends Item {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		list.add("Creative-only item");
		list.add("\"Destruction brings creation\"");
		list.add("(Set positions with right click,");
		list.add("set block with shift-right click!)");

		if(stack.stackTagCompound != null && !(stack.stackTagCompound.getInteger("x") == 0 && stack.stackTagCompound.getInteger("y") == 0 && stack.stackTagCompound.getInteger("z") == 0)) {
			list.add("Pos: " + stack.stackTagCompound.getInteger("x") + ", " + stack.stackTagCompound.getInteger("y") + ", " + stack.stackTagCompound.getInteger("z"));
		} else {
			list.add("Positions not set!");
		}
		if(stack.stackTagCompound != null)
			list.add("Block saved: " + Block.getBlockById(stack.stackTagCompound.getInteger("block")).getUnlocalizedName());
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float fx, float fy, float fz) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}

		if(player.isSneaking()) {
			stack.stackTagCompound.setInteger("block", Block.getIdFromBlock(world.getBlock(x, y, z)));
			stack.stackTagCompound.setInteger("meta", world.getBlockMetadata(x, y, z));
			if(world.isRemote)
				player.addChatMessage(new ChatComponentText("Set block " + Block.getBlockById(stack.stackTagCompound.getInteger("block")).getUnlocalizedName()));
		} else {
			if(stack.stackTagCompound.getInteger("x") == 0 && stack.stackTagCompound.getInteger("y") == 0 && stack.stackTagCompound.getInteger("z") == 0) {
				stack.stackTagCompound.setInteger("x", x);
				stack.stackTagCompound.setInteger("y", y);
				stack.stackTagCompound.setInteger("z", z);
				if(world.isRemote)
					player.addChatMessage(new ChatComponentText("Position set!"));
			} else {

				int ox = stack.stackTagCompound.getInteger("x");
				int oy = stack.stackTagCompound.getInteger("y");
				int oz = stack.stackTagCompound.getInteger("z");

				stack.stackTagCompound.setInteger("x", 0);
				stack.stackTagCompound.setInteger("y", 0);
				stack.stackTagCompound.setInteger("z", 0);

				if(!world.isRemote) {
					Block block = Block.getBlockById(stack.stackTagCompound.getInteger("block"));
					int meta = stack.stackTagCompound.getInteger("meta");
					boolean replaceAir = block == ModBlocks.wand_air;

					for(int i = Math.min(ox, x); i <= Math.max(ox, x); i++) {
						for(int j = Math.min(oy, y); j <= Math.max(oy, y); j++) {
							for(int k = Math.min(oz, z); k <= Math.max(oz, z); k++) {
								if(replaceAir && world.getBlock(i, j, k) != Blocks.air) continue;
								world.setBlock(i, j, k, block, meta, 3);
							}
						}
					}
				}
				if(world.isRemote)
					player.addChatMessage(new ChatComponentText("Selection filled!"));
			}
		}

		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		if(player.isSneaking()) {
			stack.stackTagCompound.setInteger("block", 0);
			stack.stackTagCompound.setInteger("meta", 0);
			if(world.isRemote)
				player.addChatMessage(new ChatComponentText("Set block " + Block.getBlockById(stack.stackTagCompound.getInteger("block")).getUnlocalizedName()));
		}

		return stack;
	}

}