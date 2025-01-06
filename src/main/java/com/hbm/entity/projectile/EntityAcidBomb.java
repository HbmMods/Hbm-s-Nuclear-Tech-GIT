package com.hbm.entity.projectile;

import com.hbm.entity.mob.glyphid.EntityGlyphid;
import com.hbm.lib.ModDamageSource;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityAcidBomb extends EntityThrowableInterp {
	
	public float damage = 1.5F;

	public EntityAcidBomb(World world) {
		super(world);
	}

	public EntityAcidBomb(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		
		if(worldObj.isRemote) return;
		
		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
			
			if(!(mop.entityHit instanceof EntityGlyphid)) {
				mop.entityHit.attackEntityFrom(new EntityDamageSourceIndirect(ModDamageSource.s_acid, this, thrower), damage);
				this.setDead();
			}
		}
		
		if(mop.typeOfHit == mop.typeOfHit.BLOCK)
			this.setDead();
	}

	@Override
	public double getGravityVelocity() {
		return 0.04D;
	}
	
	@Override
	protected float getAirDrag() {
		return 1.0F;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setFloat("damage", damage);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.damage = nbt.getFloat("damage");
	}
}
