package com.hbm.items.special;

import com.hbm.items.ItemCustomLore;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemAntiCheat extends ItemCustomLore {
	
    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int j, boolean b) {

    	/*if(stack.getItemDamage() != 34) {
        	
        	if(entity instanceof EntityPlayer) {
        		EntityPlayer player = (EntityPlayer)entity;
        		for(ItemStack s : player.inventory.mainInventory) {
        			player.inventory.consumeInventoryItem(ModItems.ingot_euphemium);
        			player.inventory.consumeInventoryItem(ModItems.nugget_euphemium);
        		}
        	}

        	//entity.attackEntityFrom(ModDamageSource.cheater, Float.POSITIVE_INFINITY);
        	for(int i = 0; i < 100; i++)
        		entity.attackEntityFrom(ModDamageSource.cheater, 10000);
        	
        	//if(!world.isRemote)
        	//	ExplosionChaos.antiCheat(world, (int)entity.posX, (int)entity.posY, (int)entity.posZ, 20);
    	}*/
    }

}
