package com.hbm.items.special;

import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.lib.ModDamageSource;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemUnstable extends Item {
	
	int radius;
	int timer;
	
	public ItemUnstable(int radius, int timer) {
		this.radius = radius;
		this.timer = timer;
	}
	
    public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	this.setTimer(stack, this.getTimer(stack) + 1);
    	
    	if(this.getTimer(stack) == timer && !world.isRemote) {
    		world.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(world, radius, entity.posX, entity.posY, entity.posZ));
    		world.playSoundAtEntity(entity, "hbm:weapon.immolatorIgnite", 1.0F, 1.0F);
    		entity.attackEntityFrom(ModDamageSource.nuclearBlast, 10000);
    	}
    }
    
    private void setTimer(ItemStack stack, int time) {
    	if(!stack.hasTagCompound())
    		stack.stackTagCompound = new NBTTagCompound();
    	
    	stack.stackTagCompound.setInteger("timer", time);
    }
    
    private int getTimer(ItemStack stack) {
    	if(!stack.hasTagCompound())
    		stack.stackTagCompound = new NBTTagCompound();
    	
    	return stack.stackTagCompound.getInteger("timer");
    }

}
