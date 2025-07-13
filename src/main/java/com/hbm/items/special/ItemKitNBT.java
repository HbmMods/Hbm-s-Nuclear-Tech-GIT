package com.hbm.items.special;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.ItemStackUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemKitNBT extends Item {

	public ItemKitNBT() {
		this.setMaxStackSize(1);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		ItemStack[] stacks = ItemStackUtil.readStacksFromNBT(stack);

		if(stacks != null) {

			for(ItemStack item : stacks) {
				if(item != null) {
					player.inventory.addItemStackToInventory(item.copy());
				}
			}
		}

		ItemStack container = stack.getItem().getContainerItem(stack);

		stack.stackSize--;

		if(container != null) {

			if(stack.stackSize > 0) {
				player.inventory.addItemStackToInventory(container.copy());
			} else {
				stack = container.copy();
			}
		}

		world.playSoundAtEntity(player, "hbm:item.unpack", 1.0F, 1.0F);

		return stack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean ext) {

		ItemStack[] stacks = ItemStackUtil.readStacksFromNBT(stack);

		if(stacks != null) {

			list.add("Contains:");

			for(ItemStack item : stacks) {
				list.add("-" + item.getDisplayName() + (item.stackSize > 1 ? (" x" + item.stackSize) : ""));
			}
		}
	}

	public static ItemStack create(ItemStack... contents) {
		ItemStack stack = new ItemStack(ModItems.legacy_toolbox);
		stack.stackTagCompound = new NBTTagCompound();
		ItemStackUtil.addStacksToNBT(stack, contents);

		return stack;
	}
}
