package com.hbm.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSchnitzelVegan extends ItemFood {

	public ItemSchnitzelVegan(int hunger, boolean isDogFood) {
		super(hunger, isDogFood);
	}

	@Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
        	player.addPotionEffect(new PotionEffect(Potion.blindness.id, 10 * 20, 0));
        	player.addPotionEffect(new PotionEffect(Potion.confusion.id, 30 * 20, 0));
        	player.addPotionEffect(new PotionEffect(Potion.hunger.id, 3 * 60 * 20, 4));
        	player.addPotionEffect(new PotionEffect(Potion.wither.id, 3 * 20, 0));
        	
        	player.setFire(5 * 20);
        	player.motionY = 2;
        }
    }

}
