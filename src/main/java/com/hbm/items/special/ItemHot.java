package com.hbm.items.special;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemHot extends Item {

	@SideOnly(Side.CLIENT)
	public IIcon hotIcon;
	protected static int heat;

	public ItemHot(int heat) {
		this.heat = heat;
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		super.registerIcons(reg);
		this.hotIcon = reg.registerIcon(this.getIconString() + "_hot");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return this.itemIcon;
	}

	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean bool) {

		if(!world.isRemote && stack.hasTagCompound()) {

			int h = stack.stackTagCompound.getInteger("heat");

			if(h > 0) {
				stack.stackTagCompound.setInteger("heat", h - 1);
			} else {
				stack.stackTagCompound = null;
			}
		}
	}

	public static ItemStack heatUp(ItemStack stack) {

		if(!(stack.getItem() instanceof ItemHot))
			return stack;

		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();

		stack.stackTagCompound.setInteger("heat", getMaxHeat(stack));
		return stack;
	}

	public static ItemStack heatUp(ItemStack stack, double d) {

		if(!(stack.getItem() instanceof ItemHot))
			return stack;

		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();

		stack.stackTagCompound.setInteger("heat", (int) (d * getMaxHeat(stack)));
		return stack;
	}

	public static double getHeat(ItemStack stack) {

		if(!(stack.getItem() instanceof ItemHot))
			return 0;

		if(!stack.hasTagCompound())
			return 0;

		int h = stack.stackTagCompound.getInteger("heat");

		return (double) h / (double) heat;
	}

	public static int getMaxHeat(ItemStack stack) {
		return heat;
	}
}
