package com.hbm.items.armor;

import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ArmorAustralium extends ItemArmor {

	Random rand = new Random();
	
	public ArmorAustralium(ArmorMaterial armorMaterial, int armorType) {
		super(armorMaterial, 0, armorType);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		if(armor.getItemDamage() < armor.getMaxDamage()) {
			if (armor.getItem() == ModItems.australium_iii) {
				if(rand.nextInt(3) == 0) {
					armor.damageItem(1, player);
				}
				if(!player.isPotionActive(Potion.field_76444_x.id))
					player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 80, 2, true));
			}
			if (armor.getItem() == ModItems.australium_iv) {
				if(rand.nextInt(5) == 0) {
					armor.damageItem(1, player);
				}
				if(!player.isPotionActive(Potion.field_76444_x.id))
					player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 80, 4, true));
			}
			if (armor.getItem() == ModItems.australium_v) {
				if(rand.nextInt(7) == 0) {
					armor.damageItem(1, player);
				}
				if(!player.isPotionActive(Potion.field_76444_x.id))
					player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 80, 3, true));
			}
		}
	}

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		if (itemstack.getItem() == ModItems.australium_iii)
			list.add("Ouch, that hurts.");
		if (itemstack.getItem() == ModItems.australium_iv)
			list.add("Just do it.");
		if (itemstack.getItem() == ModItems.australium_v)
			list.add("Gobbles up less australium than Mark IV!");
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem().equals(ModItems.australium_iii)) {
			return (RefStrings.MODID + ":textures/armor/australium_iii.png");
		}
		if(stack.getItem().equals(ModItems.australium_iv)) {
			return (RefStrings.MODID + ":textures/armor/australium_iv.png");
		}
		if(stack.getItem().equals(ModItems.australium_v)) {
			return (RefStrings.MODID + ":textures/armor/australium_v.png");
		}
		
		else return null;
	}

}
