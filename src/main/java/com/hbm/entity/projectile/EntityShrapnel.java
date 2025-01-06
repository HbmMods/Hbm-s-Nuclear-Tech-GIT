package com.hbm.entity.projectile;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.lib.ModDamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityShrapnel extends EntityThrowable {

	public EntityShrapnel(World p_i1773_1_) {
		super(p_i1773_1_);
		this.isImmuneToFire = true;
	}

	public EntityShrapnel(World p_i1774_1_, EntityLivingBase p_i1774_2_) {
		super(p_i1774_1_, p_i1774_2_);
	}

	@Override
	public void entityInit() {
		this.dataWatcher.addObject(16, Byte.valueOf((byte) 0));
	}

	public EntityShrapnel(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_) {
		super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(worldObj.isRemote && this.dataWatcher.getWatchableObjectByte(16) == 1)
			worldObj.spawnParticle("flame", posX, posY, posZ, 0.0, 0.0, 0.0);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if(mop.entityHit != null) {
			byte b0 = 15;

			mop.entityHit.attackEntityFrom(ModDamageSource.shrapnel, b0);
		}

		if(this.ticksExisted > 5) {
			
			if(!worldObj.isRemote)
				this.setDead();

			int b = this.dataWatcher.getWatchableObjectByte(16);
			if(b == 2 || b == 4) {
				
				if(!worldObj.isRemote) {
					if(motionY < -0.2D) {
						
						if(worldObj.getBlock(mop.blockX, mop.blockY + 1, mop.blockZ).isReplaceable(worldObj, mop.blockX, mop.blockY + 1, mop.blockZ))
							worldObj.setBlock(mop.blockX, mop.blockY + 1, mop.blockZ, b == 2 ? ModBlocks.volcanic_lava_block : ModBlocks.rad_lava_block);
						
						for(int x = mop.blockX - 1; x <= mop.blockX + 1; x++) {
							for(int y = mop.blockY; y <= mop.blockY + 2; y++) {
								for(int z = mop.blockZ - 1; z <= mop.blockZ + 1; z++) {
									if(worldObj.getBlock(x, y, z) == Blocks.air)
										worldObj.setBlock(x, y, z, ModBlocks.gas_monoxide);
								}
							}
						}
					}
					
					if(motionY > 0) {
						ExplosionNT explosion = new ExplosionNT(worldObj, null, mop.blockX + 0.5, mop.blockY + 0.5, mop.blockZ + 0.5, 7);
						explosion.addAttrib(ExAttrib.NODROP);
						explosion.addAttrib(b == 2 ? ExAttrib.LAVA_V : ExAttrib.LAVA_R);
						explosion.addAttrib(ExAttrib.NOSOUND);
						explosion.addAttrib(ExAttrib.ALLMOD);
						explosion.addAttrib(ExAttrib.NOHURT);
						explosion.explode();
					}
				}
				
			} else if(this.dataWatcher.getWatchableObjectByte(16) == 3) {
				
				if(worldObj.getBlock(mop.blockX, mop.blockY + 1, mop.blockZ).isReplaceable(worldObj, mop.blockX, mop.blockY + 1, mop.blockZ)) {
					worldObj.setBlock(mop.blockX, mop.blockY + 1, mop.blockZ, ModBlocks.mud_block);
				}
				
			} else {
				
				for(int i = 0; i < 5; i++) worldObj.spawnParticle("lava", posX, posY, posZ, 0.0, 0.0, 0.0);
			}

			worldObj.playSoundEffect(posX, posY, posZ, "random.fizz", 1.0F, 1.0F);
		}
	}

	public void setTrail(boolean b) {
		this.dataWatcher.updateObject(16, (byte) (b ? 1 : 0));
	}

	public void setVolcano(boolean b) {
		this.dataWatcher.updateObject(16, (byte) (b ? 2 : 0));
	}

	public void setWatz(boolean b) {
		this.dataWatcher.updateObject(16, (byte) (b ? 3 : 0));
	}

	public void setRadVolcano(boolean b) {
		this.dataWatcher.updateObject(16, (byte) (b ? 4 : 0));
	}

	@Override
	public boolean writeToNBTOptional(NBTTagCompound nbt) {
		return false;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setDead();
	}
}
