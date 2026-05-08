package com.hbm.items.weapon.grenade;

import com.hbm.items.IAnimatedItem;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.render.anim.BusAnimation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Kyiv grenade - a single grenade type (not part of the universal grenade system)
 * STUB IMPLEMENTATION for compilation purposes
 */
public class ItemGrenadeKyiv extends Item implements IEquipReceiver, IAnimatedItem {

	public ItemGrenadeKyiv(int damage) {
		super();
		this.setMaxDamage(damage);
		this.setMaxStackSize(1);
	}

	@Override
	public void onEquip(EntityPlayer player, ItemStack stack) {
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return stack;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isHeld) {
	}

	@Override
	public BusAnimation getAnimation(NBTTagCompound tag, ItemStack stack) {
		return null;
	}

	@Override
	public boolean shouldPlayerModelAim(ItemStack stack) {
		return false;
	}

	public Class getEnum() {
		return null;
	}
}
