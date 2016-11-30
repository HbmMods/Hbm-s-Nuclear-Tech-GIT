package com.hbm.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemCottonCandy extends ItemFood {

	public ItemCottonCandy(int p_i45340_1_, boolean p_i45340_2_) {
		super(p_i45340_1_, p_i45340_2_);
        this.setAlwaysEdible();
	}

    @Override
	protected void onFoodEaten(ItemStack p_77849_1_, World p_77849_2_, EntityPlayer p_77849_3_)
    {
        if (!p_77849_2_.isRemote)
        {
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.poison.id, 15 * 20, 0));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.wither.id, 5 * 20, 0));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.weakness.id, 25 * 20, 2));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 25 * 20, 2));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.resistance.id, 30 * 20, 4));
        }
    }

}
