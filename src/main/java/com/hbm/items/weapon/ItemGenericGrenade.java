package com.hbm.items.weapon;

import com.hbm.entity.grenade.EntityGrenadeBouncyGeneric;
import com.hbm.entity.grenade.EntityGrenadeImpactGeneric;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGenericGrenade extends ItemGrenade {

	public ItemGenericGrenade(int fuse) {
		super(fuse);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if(!player.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		
		if(!world.isRemote) {
			
			if(fuse == -1)
				world.spawnEntityInWorld(new EntityGrenadeImpactGeneric(world, player).setType(this));
			else
				world.spawnEntityInWorld(new EntityGrenadeBouncyGeneric(world, player).setType(this));
		}

		return stack;
	}

	public void explode(Entity grenade, EntityLivingBase thrower, World world, double x, double y, double z) { }

	public int getMaxTimer() {
		return this.fuse * 20;
	}

	public double getBounceMod() {
		return 0.5D;
	}
}
