package com.hbm.entity.mob;

import com.hbm.entity.item.EntityItemBuoyant;
import com.hbm.items.ModItems;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Copy-pasted shit from the squid class
 * Mojang-certified
 * 
 * @author hbm
 */
public class EntityPlasticBag extends EntityWaterMob {

	public float rotation;
	public float prevRotation;
	private float randomMotionSpeed;
	private float rotationVelocity;
	private float randomMotionVecX;
	private float randomMotionVecY;
	private float randomMotionVecZ;

	public EntityPlasticBag(World world) {
		super(world);
		this.setSize(0.45F, 0.45F);
		this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(!worldObj.isRemote) {
			this.setDead();
			this.dropItem(ModItems.plastic_bag, 1);
		}
		
		return true;
	}
	
	@Override
	public EntityItem entityDropItem(ItemStack stack, float offset) {
		if(stack.stackSize != 0 && stack.getItem() != null) {
			EntityItemBuoyant entityitem = new EntityItemBuoyant(this.worldObj, this.posX, this.posY + (double) offset, this.posZ, stack);
			entityitem.delayBeforeCanPickup = 10;
			if(captureDrops) {
				capturedDrops.add(entityitem);
			} else {
				this.worldObj.spawnEntityInWorld(entityitem);
			}
			return entityitem;
		} else {
			return null;
		}
	}

	@Override
	protected String getLivingSound() {
		return null;
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean isInWater() {
		return this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.6D, 0.0D), Material.water, this);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		this.prevRotation = this.rotation;
		this.rotation += this.rotationVelocity;

		if(this.rotation > ((float) Math.PI * 2F)) {
			this.rotation -= ((float) Math.PI * 2F);

			if(this.rand.nextInt(10) == 0) {
				this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
			}
		}

		if(this.isInWater()) {
			float f;

			if(this.rotation < (float) Math.PI) {
				f = this.rotation / (float) Math.PI;

				if((double) f > 0.75D) {
					this.randomMotionSpeed = 0.1F;
				}
			} else {
				this.randomMotionSpeed *= 0.999F;
			}

			if(!this.worldObj.isRemote) {
				this.motionX = (double) (this.randomMotionVecX * this.randomMotionSpeed);
				this.motionY = (double) (this.randomMotionVecY * this.randomMotionSpeed);
				this.motionZ = (double) (this.randomMotionVecZ * this.randomMotionSpeed);
			}

			f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.renderYawOffset += (-((float) Math.atan2(this.motionX, this.motionZ)) * 180.0F / (float) Math.PI - this.renderYawOffset) * 0.1F;
			this.rotationYaw = this.renderYawOffset;
			this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
		} else {
			if(!this.worldObj.isRemote) {
				this.motionX = 0.0D;
				this.motionY -= 0.08D;
				this.motionY *= 0.98D;
				this.motionZ = 0.0D;
			}
		}
	}

	@Override
	public void moveEntityWithHeading(float forward, float strafe) {
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
	}

	@Override
	protected void updateEntityActionState() {
		++this.entityAge;

		if(this.entityAge > 100) {
			this.randomMotionVecX = this.randomMotionVecY = this.randomMotionVecZ = 0.0F;
		} else if(this.rand.nextInt(50) == 0 || !this.inWater || this.randomMotionVecX == 0.0F && this.randomMotionVecY == 0.0F && this.randomMotionVecZ == 0.0F) {
			float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
			this.randomMotionVecX = MathHelper.cos(f) * 0.2F;
			this.randomMotionVecY = -0.1F + this.rand.nextFloat() * 0.2F;
			this.randomMotionVecZ = MathHelper.sin(f) * 0.2F;
		}

		this.despawnEntity();
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.posY > 45.0D && this.posY < 63.0D && this.getRNG().nextInt(10) == 0 && super.getCanSpawnHere();
	}
}
