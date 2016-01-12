package com.hbm.items;

import com.hbm.entity.EntityNukeExplosionAdvanced;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWaffle extends ItemFood {

	public ItemWaffle(int heal, boolean canWolfEat) {
		super(heal, canWolfEat);
	}
	
	@Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
    	EntityNukeExplosionAdvanced explosion = new EntityNukeExplosionAdvanced(world);
    	explosion.speed = 25;
    	explosion.coefficient = 5.0F;
    	explosion.destructionRange = 25;
    	explosion.posX = player.posX;
    	explosion.posY = player.posY;
    	explosion.posZ = player.posZ;
    	world.spawnEntityInWorld(explosion);
    }

}
