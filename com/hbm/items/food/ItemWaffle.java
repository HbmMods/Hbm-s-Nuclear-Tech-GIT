package com.hbm.items.food;

import com.hbm.entity.logic.EntityNukeExplosionAdvanced;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.main.MainRegistry;

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
		world.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(world, (int)(MainRegistry.fatmanRadius * 1.5), player.posX, player.posY, player.posZ));
    	
    	ExplosionParticle.spawnMush(world, (int)player.posX, (int)player.posY - 3, (int)player.posZ);
    }

}
