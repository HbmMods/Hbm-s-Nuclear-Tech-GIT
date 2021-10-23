package com.hbm.entity.mob;

import com.hbm.entity.particle.EntityBSmokeFX;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * BOW
 */
public class EntityQuackos extends EntityDuck implements IBossDisplayData
{

	/**
	 * BOW
	 */
	public EntityQuackos(World world) {
		super(world);
		this.setSize(0.3F * 25, 0.7F * 25);
	}

	/**
	 * BOW
	 */
	@Override
	protected String getLivingSound() {
		return "hbm:entity.megaquacc";
	}

	/**
	 * BOW
	 */
	@Override
	protected String getHurtSound() {
		return "hbm:entity.megaquacc";
	}

	/**
	 * BOW
	 */
	@Override
	protected String getDeathSound() {
		return "hbm:entity.megaquacc";
	}

	/**
	 * BOW
	 */
	@Override
	public EntityQuackos createChild(EntityAgeable entity) {
		return new EntityQuackos(this.worldObj);
	}

	/**
	 * BOW
	 */
	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}

	/**
	 * BOW
	 */
	@Override
	public void setDead() {
		if(worldObj.isRemote)
			super.setDead();
	} //prank'd

	/**
	 * BOW
	 */
	@Override
	public void setHealth(float f) {
		super.setHealth(this.getMaxHealth());
	} //prank'd

	/**
	 * BOW
	 */
	@Override
	public boolean interact(EntityPlayer player) {

		if(super.interact(player)) {
			return true;

		} else {

			if(!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == player)) {
				player.mountEntity(this);
				return true;
			}

			return false;
		}
	}
	
	/**
	 * BOW
	 */
	public void despawn() {
		
		if(!worldObj.isRemote) {
			for(int i = 0; i < 150; i++) {
				
				EntityBSmokeFX fx = new EntityBSmokeFX(worldObj);
				fx.setPositionAndRotation(posX + rand.nextDouble() * 20 - 10, posY + rand.nextDouble() * 25, posZ + rand.nextDouble() * 20 - 10, 0, 0);
				worldObj.spawnEntityInWorld(fx);
			}
		}
		this.isDead = true;
	}

	/**
	 * BOW
	 */
	@Override
	public void updateRiderPosition() {

		super.updateRiderPosition();
		float f = MathHelper.sin(this.renderYawOffset * (float) Math.PI / 180.0F);
		float f1 = MathHelper.cos(this.renderYawOffset * (float) Math.PI / 180.0F);
		float f2 = 0.1F;
		float f3 = 0.0F;
		this.riddenByEntity.setPosition(this.posX + f2 * f, this.posY + (this.height - 0.125F) + this.riddenByEntity.getYOffset() + f3, this.posZ - f2 * f1);

		if(this.riddenByEntity instanceof EntityLivingBase) {
			((EntityLivingBase) this.riddenByEntity).renderYawOffset = this.renderYawOffset;
		}
	}

	/**
	 * BOW
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 7.5F;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if(!worldObj.isRemote && this.posY < -30) {
			this.setPosition(this.posX + rand.nextGaussian() * 30, 256, this.posZ + rand.nextGaussian() * 30);
		}
	}
}