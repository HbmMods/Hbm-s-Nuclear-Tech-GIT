package com.hbm.items.gear;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Multimap;
import com.hbm.items.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class WeaponSpecial extends ItemSword {
	
	Random rand = new Random();

	public WeaponSpecial(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
	}
    
    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
    {
		if(this == ModItems.schrabidium_hammer) {
			return EnumRarity.rare;
		}
		if(this == ModItems.ullapool_caber) {
			return EnumRarity.uncommon;
		}
		
		return EnumRarity.common;
    }
    
    public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase entityPlayer)
    {
    	World world = entity.worldObj;

		if(this == ModItems.schrabidium_hammer) {
			if (!world.isRemote)
        	{
        		entity.setHealth(0.0F);
        	}
        	world.playSoundAtEntity(entity, "random.anvil_land", 3.0F, 0.1F);
		}

		if(this == ModItems.bottle_opener) {
			if (!world.isRemote)
        	{
				int i = rand.nextInt(7);
				if(i == 0)
					entity.addPotionEffect(new PotionEffect(Potion.blindness.id, 5 * 60 * 20, 0));
				if(i == 1)
					entity.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 5 * 60 * 20, 2));
				if(i == 2)
					entity.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 5 * 60 * 20, 2));
				if(i == 3)
					entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 1 * 60 * 20, 0));
        	}
        	world.playSoundAtEntity(entity, "random.anvil_land", 3.0F, 1.F);
		}
    	
		if(this == ModItems.ullapool_caber) {
			if (!world.isRemote)
        	{
				world.createExplosion(null, entity.posX, entity.posY, entity.posZ, 7.5F, true);
        	}
			
			stack.damageItem(505, entityPlayer);
		}
		
		return false;
    }
    
    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
		if(this == ModItems.schrabidium_hammer) {
			multimap.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)-0.5, 1));
		}
        return multimap;
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.schrabidium_hammer) {
			list.add("Even though it says \"+1000000000");
			list.add("damage\", it's actually \"onehit anything\"");
		}
		if(this == ModItems.ullapool_caber) {
			list.add("High-yield Scottish face removal.");
			list.add("A sober person would throw it...");
		}
		if(this == ModItems.bottle_opener) {
			list.add("My very own bottle opener.");
			list.add("Use with caution!");
		}
	}

}
