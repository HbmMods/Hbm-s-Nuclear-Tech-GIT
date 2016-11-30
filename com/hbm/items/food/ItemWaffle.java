package com.hbm.items.food;

import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.explosion.ExplosionParticle;

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
    	
    	ExplosionParticle.spawnMush(world, (int)player.posX, (int)player.posY - 3, (int)player.posZ);
    }

}
