package com.hbm.items.gear;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

public class ArmorAsbestos extends ItemArmor implements ISpecialArmor {
	private String [] armourTypes = new String [] {"asbestos_helmet", "asbestos_plate", "asbestos_legs", "asbestos_boots"};
	
	public ArmorAsbestos(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
		super(armorMaterial, renderIndex, armorType);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem().equals(ModItems.asbestos_helmet) || stack.getItem().equals(ModItems.asbestos_plate) || stack.getItem().equals(ModItems.asbestos_boots)) {
			return (RefStrings.MODID + ":textures/armor/asbestos_1.png");
		}
		if(stack.getItem().equals(ModItems.asbestos_legs)) {
			return (RefStrings.MODID + ":textures/armor/asbestos_2.png");
		}
		
		else return null;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if(source.isFireDamage())
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
		stack.damageItem(damage * 1, entity);
		
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack armor) {
		 
		 player.extinguish();
	}

}
