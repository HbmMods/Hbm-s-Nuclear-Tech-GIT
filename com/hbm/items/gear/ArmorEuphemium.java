package com.hbm.items.gear;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;

public class ArmorEuphemium extends ItemArmor implements ISpecialArmor {
	private String [] armourTypes = new String [] {"euphemium_helmet", "euphemium_chest", "euphemium_legs", "euphemium_boots"};
	
	public ArmorEuphemium(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
		super(armorMaterial, renderIndex, armorType);
		this.setCreativeTab(null);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem().equals(ModItems.euphemium_helmet) || stack.getItem().equals(ModItems.euphemium_plate) || stack.getItem().equals(ModItems.euphemium_boots)) {
			return (RefStrings.MODID + ":textures/armor/euphemium_1.png");
		}
		if(stack.getItem().equals(ModItems.euphemium_legs)) {
			return (RefStrings.MODID + ":textures/armor/euphemium_2.png");
		}
		
		else return null;
	}
	
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if(player instanceof EntityPlayer && Library.checkArmor((EntityPlayer)player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))
		{
			return new ArmorProperties(1, 1, MathHelper.floor_double(999999999));
		}
		return new ArmorProperties(0, 0, 0);
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
			stack.damageItem(damage * 0, entity);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		if(player instanceof EntityPlayer && Library.checkArmor((EntityPlayer)player, ModItems.euphemium_helmet, ModItems.euphemium_plate, ModItems.euphemium_legs, ModItems.euphemium_boots))
		{
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 5, 127, true));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 5, 127, true));
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 5, 127, true));
			player.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 5, 127, true));
		 
			if(player.motionY < -0.25D)
			{
				player.motionY = -0.25D;
			}
		}
	}

    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
		return EnumRarity.epic;
    }

}
