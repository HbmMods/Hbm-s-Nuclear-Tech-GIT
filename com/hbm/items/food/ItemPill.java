package com.hbm.items.food;

import java.util.List;
import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class ItemPill extends ItemFood {

	public ItemPill(int hunger) {
		super(hunger, false);
        this.setAlwaysEdible();
	}
	
	Random rand = new Random();

	@Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
        	if(this == ModItems.pill_iodine) {
        		player.removePotionEffect(Potion.blindness.id);
        		player.removePotionEffect(Potion.confusion.id);
        		player.removePotionEffect(Potion.digSlowdown.id);
        		player.removePotionEffect(Potion.hunger.id);
        		player.removePotionEffect(Potion.moveSlowdown.id);
        		player.removePotionEffect(Potion.poison.id);
        		player.removePotionEffect(Potion.weakness.id);
        		player.removePotionEffect(Potion.wither.id);
        		player.removePotionEffect(HbmPotion.radiation.id);
        	}

        	if(this == ModItems.plan_c) {
        		for(int i = 0; i < 10; i++)
        			player.attackEntityFrom(rand.nextBoolean() ? ModDamageSource.euthanizedSelf : ModDamageSource.euthanizedSelf2, 1000);
        	}
        }
    }
    
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		if(this == ModItems.pill_iodine) {
			list.add("Removes negative effects");
		}
		if(this == ModItems.plan_c) {
			list.add("Deadly");
		}
	}

}
