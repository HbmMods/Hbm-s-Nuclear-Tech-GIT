package com.hbm.items;

import com.hbm.blocks.ModBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemClip extends Item {

    public ItemClip()
    {
        this.setMaxDamage(1);
    }
    
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		stack.stackSize--;
		if(stack.stackSize <= 0)
			stack.damageItem(5, player);
		
		if(this == ModItems.clip_revolver_iron)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_revolver_iron_ammo, 20)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_revolver_iron_ammo, 20), false);
        	}
		}
		
		if(this == ModItems.clip_revolver)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_revolver_ammo, 12)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_revolver_ammo, 12), false);
        	}
		}
		
		if(this == ModItems.clip_revolver_gold)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_revolver_gold_ammo, 4)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_revolver_gold_ammo, 4), false);
        	}
		}
		
		if(this == ModItems.clip_revolver_schrabidium)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_revolver_schrabidium_ammo, 2)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_revolver_schrabidium_ammo, 2), false);
        	}
		}
		
		if(this == ModItems.clip_rpg)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_rpg_ammo, 3)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_rpg_ammo, 3), false);
        	}
		}
		
		if(this == ModItems.clip_osipr)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_osipr_ammo, 30)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_osipr_ammo, 30), false);
        	}
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_osipr_ammo2, 1)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_osipr_ammo2, 1), false);
        	}
		}
		
		if(this == ModItems.clip_xvl1456)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_xvl1456_ammo, 60)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_xvl1456_ammo, 60), false);
        	}
		}
		
		if(this == ModItems.clip_revolver_lead)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_revolver_lead_ammo, 12)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_revolver_lead_ammo, 12), false);
        	}
		}
		
		if(this == ModItems.clip_revolver_cursed)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_revolver_cursed_ammo, 17)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_revolver_cursed_ammo, 17), false);
        	}
		}
		
		if(this == ModItems.clip_fatman)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_fatman_ammo, 6)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_fatman_ammo, 6), false);
        	}
		}
		
		if(this == ModItems.clip_mp)
		{
			if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.gun_mp_ammo, 30)))
        	{
        		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.gun_mp_ammo, 30), false);
        	}
		}
		
		return stack;
		
	}
}
