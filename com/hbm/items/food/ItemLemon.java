package com.hbm.items.food;

import java.util.List;

import com.hbm.items.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemLemon extends ItemFood {

	public ItemLemon(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.lemon) {
			list.add("Eh, good enough.");
		}
		
		if(this == ModItems.definitelyfood) {
			list.add("A'right, I got sick and tired of");
			list.add("having to go out, kill things just");
			list.add("to get food and not die, so here is ");
			list.add("my absolutely genius solution:");
			list.add("");
			list.add("Have some edible dirt.");
		}
		
		if(this == ModItems.med_ipecac) {
			list.add("Bitter juice that will cause your stomach");
			list.add("to forcefully eject it's contents.");
		}
		
		if(this == ModItems.med_ptsd) {
			list.add("This isn't even PTSD mediaction, it's just");
			list.add("Ipecac in a different bottle!");
		}
		
		if(this == ModItems.med_schizophrenia) {
			list.add("Makes the voices go away. Just for a while.");
			list.add("");
			list.add("...");
			list.add("Better not take it.");
		}
		
		if(this == ModItems.med_schizophrenia) {
			list.add("Makes the voices go away. Just for a while.");
			list.add("");
			list.add("...");
			list.add("Better not take it.");
		}
		
		if(this == ModItems.loops) {
			list.add("Brøther, may I have some lööps?");
		}
		
		if(this == ModItems.loop_stew) {
			list.add("A very, very healthy breakfast.");
		}
	}


    @Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
		if(this == ModItems.med_ipecac || this == ModItems.med_ptsd) {
			player.addPotionEffect(new PotionEffect(Potion.hunger.id, 50, 49));
		}
		
		if(this == ModItems.med_schizophrenia) {
			int o = 0;
			
			int z = 5 / o;
		}
		
		if(this == ModItems.loop_stew) {
			player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 20 * 20, 1));
			player.addPotionEffect(new PotionEffect(Potion.resistance.id, 60 * 20, 2));
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 60 * 20, 1));
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 20 * 20, 2));
		}
    }

    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        ItemStack sta = super.onEaten(p_77654_1_, p_77654_2_, p_77654_3_);
        
        if(this == ModItems.loop_stew)
        	return new ItemStack(Items.bowl);
        
        return sta;
        
    }

}
