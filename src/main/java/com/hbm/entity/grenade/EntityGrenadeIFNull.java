package com.hbm.entity.grenade;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGrenade;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityGrenadeIFNull extends EntityGrenadeBouncyBase {

	public EntityGrenadeIFNull(World world) {
		super(world);
	}

	public EntityGrenadeIFNull(World world, EntityLivingBase thrower) {
		super(world, thrower);
	}

	public EntityGrenadeIFNull(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public void explode() {

		if(!this.worldObj.isRemote) {
			this.setDead();
			
			int range = 5;

			for(int a = -range; a <= range; a++)
				for(int b = -range; b <= range; b++)
					for(int c = -range; c <= range; c++)
						worldObj.setBlockToAir((int) Math.floor(posX + a), (int) Math.floor(posY + b), (int) Math.floor(posZ + c));

			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
					AxisAlignedBB.getBoundingBox((int) posX + 0.5 - 3, (int) posY + 0.5 - 3, (int) posZ + 0.5 - 3, (int) posX + 0.5 + 3, (int) posY + 0.5 + 3, (int) posZ + 0.5 + 3));

			for(Object o : list) {
				if(o instanceof EntityLivingBase) {
					EntityLivingBase e = (EntityLivingBase) o;
					e.setHealth(0);
					e.onDeath(DamageSource.outOfWorld);
				} else if(o instanceof Entity) {
					Entity e = (Entity) o;
					e.setDead();
				}
			}
		}
	}

	@Override
	protected int getMaxTimer() {
		return ItemGrenade.getFuseTicks(ModItems.grenade_if_null);
	}

	@Override
	protected double getBounceMod() {
		return 0.25D;
	}
}
