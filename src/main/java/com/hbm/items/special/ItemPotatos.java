package com.hbm.items.special;

import com.hbm.items.machine.ItemBattery;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemPotatos extends ItemBattery {

	public ItemPotatos(long dura, long chargeRate, long dischargeRate) {
		super(dura, chargeRate, dischargeRate);
	}

    @Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b) {
    	
    	if(getCharge(stack) == 0)
    		return;
    	
    	if(getTimer(stack) > 0) {
    		setTimer(stack, getTimer(stack) - 1);
    	} else {
    		if(entity instanceof EntityPlayer) {
    			EntityPlayer p = (EntityPlayer) entity;
    			
    			if(p.getHeldItem() == stack) {
    				
    		    	float pitch = (float)getCharge(stack) / (float)this.getMaxCharge(stack) * 0.5F + 0.5F;
    		    	
    				world.playSoundAtEntity(p, "hbm:potatos.random", 1.0F, pitch);
    				setTimer(stack, 200 + itemRand.nextInt(100));
    			}
    		}
    	}
    }
	
	private static int getTimer(ItemStack stack) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
			return 0;
		}
		
		return stack.stackTagCompound.getInteger("timer");
		
	}
	
	private static void setTimer(ItemStack stack, int i) {
		if(stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		
		stack.stackTagCompound.setInteger("timer", i);
		
	}

}
