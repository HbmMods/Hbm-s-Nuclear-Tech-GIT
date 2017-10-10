package com.hbm.items.food;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemAppleSchrabidium extends ItemFood {

	public ItemAppleSchrabidium(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_) {
		super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
        this.setHasSubtypes(true);
        this.setAlwaysEdible();
	}

    @Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack p_77636_1_)
    {
        return p_77636_1_.getItemDamage() == 2;
    }

    @Override
	protected void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_)
    {
        if (!p_77849_2_.isRemote)
        {
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
        }

        if (p_77849_1_.getItemDamage() == 1)
        {
            if (!p_77849_2_.isRemote)
            {
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1200, 4));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 1200, 4));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 1200, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1200, 4));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 1200, 2));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1200, 2));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.jump.id, 1200, 4));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 1200, 9));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 1200, 4));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 1200, 9));
            }
        }

        if (p_77849_1_.getItemDamage() == 2)
        {
            if (!p_77849_2_.isRemote)
            {
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.regeneration.id, 2147483647, 4));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 2147483647, 1));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 2147483647, 0));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2147483647, 9));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 2147483647, 4));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 2147483647, 3));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.jump.id, 2147483647, 4));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 2147483647, 24));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 2147483647, 14));
                p_77849_3_.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 2147483647, 99));
            }
        }
        else
        {
            super.onFoodEaten(p_77849_1_, p_77849_2_, p_77849_3_);
        }
    }
    
    @Override
	public EnumRarity getRarity(ItemStack p_77613_1_)
    {
    	if(p_77613_1_.getItemDamage() == 0)
    	{
    		return EnumRarity.uncommon;
    	}
    	
    	if(p_77613_1_.getItemDamage() == 1)
    	{
    		return EnumRarity.rare;
    	}
    	
    	if(p_77613_1_.getItemDamage() == 2)
    	{
    		return EnumRarity.epic;
    	}
    	
		return EnumRarity.common;
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 0));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 1));
        p_150895_3_.add(new ItemStack(p_150895_1_, 1, 2));
    }

}
