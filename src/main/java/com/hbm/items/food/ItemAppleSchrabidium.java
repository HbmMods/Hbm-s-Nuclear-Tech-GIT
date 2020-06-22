package com.hbm.items.food;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

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
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
    	
		if (!world.isRemote) {
			
			if(stack.getItem() == ModItems.apple_schrabidium) {
				if (stack.getItemDamage() == 0) {
					player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
					player.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
					player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
				}
	
				if (stack.getItemDamage() == 1) {
					
					player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 1200, 4));
					player.addPotionEffect(new PotionEffect(Potion.resistance.id, 1200, 4));
					player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 1200, 0));
					player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1200, 4));
					player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 1200, 2));
					player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 1200, 2));
					player.addPotionEffect(new PotionEffect(Potion.jump.id, 1200, 4));
					player.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 1200, 9));
					player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 1200, 4));
					player.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 1200, 9));
				}
	
				if (stack.getItemDamage() == 2) {
					
					player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 2147483647, 4));
					player.addPotionEffect(new PotionEffect(Potion.resistance.id, 2147483647, 1));
					player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 2147483647, 0));
					player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2147483647, 9));
					player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 2147483647, 4));
					player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 2147483647, 3));
					player.addPotionEffect(new PotionEffect(Potion.jump.id, 2147483647, 4));
					player.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 2147483647, 24));
					player.addPotionEffect(new PotionEffect(Potion.field_76444_x.id, 2147483647, 14));
					player.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 2147483647, 99));
				}
			}
			
			if(stack.getItem() == ModItems.apple_lead) {
				
				if (stack.getItemDamage() == 0) {
					player.addPotionEffect(new PotionEffect(HbmPotion.lead.id, 15 * 20, 2));
				}
	
				if (stack.getItemDamage() == 1) {

					player.addPotionEffect(new PotionEffect(HbmPotion.lead.id, 60 * 20, 4));
				}
	
				if (stack.getItemDamage() == 2) {
					
					player.attackEntityFrom(ModDamageSource.lead, 500F);
				}
			}
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
