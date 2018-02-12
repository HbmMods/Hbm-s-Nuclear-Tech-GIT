package com.hbm.items.weapon;

import java.util.List;

import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WeaponizedCell extends Item {

    public boolean onEntityItemUpdate(EntityItem item)
    {
    	World world = item.worldObj;
    	
    	if(item.ticksExisted > 60 * 20 || item.isBurning()) {
			
	    	if(!world.isRemote) {
	    		world.playSoundEffect(item.posX, item.posY, item.posZ,
						"random.explode", 100.0f, world.rand.nextFloat() * 0.1F + 0.9F);

				EntityNukeExplosionMK3 exp = new EntityNukeExplosionMK3(world);
				exp.posX = item.posX;
				exp.posY = item.posY;
				exp.posZ = item.posZ;
				exp.destructionRange = 100;
				exp.speed = 25;
				exp.coefficient = 1.0F;
				exp.waste = false;

				world.spawnEntityInWorld(exp);
	    		
	    		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(world, 100);
	    		cloud.posX = item.posX;
	    		cloud.posY = item.posY;
	    		cloud.posZ = item.posZ;
	    		world.spawnEntityInWorld(cloud);
	    	}
    	}
    	
    	int randy = (60 * 20) - item.ticksExisted;
    	
    	if(randy < 1)
    		randy = 1;
    	
    	if(item.worldObj.rand.nextInt(60 * 20) >= randy)
    		world.spawnParticle("reddust", item.posX + item.worldObj.rand.nextGaussian() * item.width / 2, item.posY + item.worldObj.rand.nextGaussian() * item.height, item.posZ + item.worldObj.rand.nextGaussian() * item.width / 2, 0.0, 0.0, 0.0);
    	else
    		world.spawnParticle("smoke", item.posX + item.worldObj.rand.nextGaussian() * item.width / 2, item.posY + item.worldObj.rand.nextGaussian() * item.height, item.posZ + item.worldObj.rand.nextGaussian() * item.width / 2, 0.0, 0.0, 0.0);
    	
    	if(randy < 100)
    		world.spawnParticle("lava", item.posX + item.worldObj.rand.nextGaussian() * item.width / 2, item.posY + item.worldObj.rand.nextGaussian() * item.height, item.posZ + item.worldObj.rand.nextGaussian() * item.width / 2, 0.0, 0.0, 0.0);
    	
        return false;
    }

	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool) {

		list.add("A charged energy cell, rigged to explode");
		list.add("when left on the floor for too long.");
	}
}
