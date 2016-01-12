package com.hbm.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

import com.hbm.lib.RefStrings;

public class ArmorSchrabidium extends ItemArmor implements ISpecialArmor {
	private String [] armourTypes = new String [] {"schrabidium_helmet", "schrabidium_plate", "schrabidium_legs", "schrabidium_boots"};
	
	public ArmorSchrabidium(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
		super(armorMaterial, renderIndex, armorType);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem().equals(ModItems.schrabidium_helmet) || stack.getItem().equals(ModItems.schrabidium_plate) || stack.getItem().equals(ModItems.schrabidium_boots)) {
			return (RefStrings.MODID + ":textures/armor/schrabidium_1.png");
		}
		if(stack.getItem().equals(ModItems.schrabidium_legs)) {
			return (RefStrings.MODID + ":textures/armor/schrabidium_2.png");
		}
		
		else return null;
	}

    public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
		return EnumRarity.rare;
    }

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if(damage >= 20)
		{
			player.setHealth(player.getHealth() - 1F);
			return new ArmorProperties(1, 1, 2000);
		}
		return new ArmorProperties(1, 1, 2000);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if(slot == 0)
		{
			return 3;
		}
		if(slot == 1)
		{
			return 8;
		}
		if(slot == 2)
		{
			return 6;
		}
		if(slot == 3)
		{
			return 3;
		}
		return 0;
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		stack.damageItem(damage * 1, entity);
		
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		 
		 if(armor.getItem() == ModItems.schrabidium_helmet)
		 {
			 player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 5, 0));
			 player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 5, 9));
		 }
		 
		 if(armor.getItem() == ModItems.schrabidium_plate)
		 {
			 player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 5, 0));
			 player.addPotionEffect(new PotionEffect(Potion.resistance.id, 5, 0));
			 player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 5, 0));
		 }
		 
		 if(armor.getItem() == ModItems.schrabidium_legs)
		 {
			 player.addPotionEffect(new PotionEffect(Potion.jump.id, 5, 4));
		 }
		 
		 if(armor.getItem() == ModItems.schrabidium_boots)
		 {
			 player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5, 4));
		 }
	}

}
