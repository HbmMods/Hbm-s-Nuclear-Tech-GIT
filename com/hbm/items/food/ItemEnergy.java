package com.hbm.items.food;

import java.util.Iterator;
import java.util.List;

import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemEnergy extends Item {

    public ItemStack onEaten(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_)
    {
        if (!p_77654_3_.capabilities.isCreativeMode)
        {
            --p_77654_1_.stackSize;
        }

        if (!p_77654_2_.isRemote)
        {
        	if(this == ModItems.can_smart)
        	{
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 0));
        	}
        	if(this == ModItems.can_creature)
        	{
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.regeneration.id, 30 * 20, 1));
        	}
        	if(this == ModItems.can_redbomb)
        	{
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 30 * 20, 2));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 1));
        	}
        	if(this == ModItems.can_mrsugar)
        	{
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 0));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 30 * 20, 1));
                p_77654_3_.addPotionEffect(new PotionEffect(Potion.jump.id, 30 * 20, 2));
        	}
        }

        if (!p_77654_3_.capabilities.isCreativeMode)
        {
            if (p_77654_1_.stackSize <= 0)
            {
                return new ItemStack(ModItems.can_empty);
            }

            p_77654_3_.inventory.addItemStackToInventory(new ItemStack(ModItems.can_empty));
        }

        return p_77654_1_;
    }
    
    public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }

    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.drink;
    }

    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
    	p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
    	return p_77659_1_;
    }
    
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List list, boolean p_77624_4_)
    {
    	if(this == ModItems.can_smart)
    	{
    		list.add("Cheap and full of bubbles");
    	}
    	if(this == ModItems.can_creature)
    	{
            list.add("Basically gasoline in a tin can");
    	}
    	if(this == ModItems.can_redbomb)
    	{
            list.add("Liquefied explosives");
    	}
    	if(this == ModItems.can_mrsugar)
    	{
            list.add("An intellectual drink, for the chosen ones!");
    	}
    }
}
