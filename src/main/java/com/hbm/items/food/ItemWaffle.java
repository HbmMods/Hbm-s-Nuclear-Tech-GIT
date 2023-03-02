package com.hbm.items.food;

import com.hbm.explosion.ExplosionNukeSmall;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWaffle extends ItemFood {

	public ItemWaffle(int heal, boolean canWolfEat) {
		super(heal, canWolfEat);
	}
	
	@Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		
		if(!world.isRemote)
			ExplosionNukeSmall.explode(world, player.posX, player.posY + 0.5, player.posZ, ExplosionNukeSmall.PARAMS_MEDIUM);
    }

}
