package com.hbm.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSyringe extends Item {

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
		if(this == ModItems.syringe_antidote)
		{
            if (!world.isRemote)
            {
            	player.clearActivePotions();
            
            	stack.stackSize--;

            	if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.syringe_empty);
            	}

            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty)))
            	{
            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
            	}
            }
		}
		
		if(this == ModItems.syringe_awesome)
		{
            if (!world.isRemote)
            {
            	player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 50 * 20, 9));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 50 * 20, 9));
                player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 50 * 20, 0));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 50 * 20, 24));
                player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 50 * 20, 9));
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 50 * 20, 6));
                player.addPotionEffect(new PotionEffect(Potion.jump.id, 50 * 20, 9));
                player.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 50 * 20, 9));
                player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 50 * 20, 4));
                player.addPotionEffect(new PotionEffect(Potion.confusion.id, 5 * 20, 4));
                
                stack.stackSize--;

                if (stack.stackSize <= 0)
                {
                    return new ItemStack(ModItems.syringe_empty);
                }

                if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_empty)))
                {
                	player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_empty, 1, 0), false);
                }
            }
		}
		
		if(this == ModItems.syringe_metal_stimpak)
		{
            if (!world.isRemote)
            {
            	player.heal(5);
            
            	stack.stackSize--;

            	if (stack.stackSize <= 0)
            	{
                	return new ItemStack(ModItems.syringe_metal_empty);
            	}

            	if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.syringe_metal_empty)))
            	{
            		player.dropPlayerItemWithRandomChoice(new ItemStack(ModItems.syringe_metal_empty, 1, 0), false);
            	}
            }
		}
		
		return stack;
	}

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_)
    {
    	if(this == ModItems.syringe_awesome)
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    public EnumRarity getRarity(ItemStack p_77613_1_)
    {
    	if(this == ModItems.syringe_awesome)
    	{
    		return EnumRarity.uncommon;
    	}
    	
		return EnumRarity.common;
    }
}
