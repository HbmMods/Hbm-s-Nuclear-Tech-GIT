package com.hbm.entity.mob.botprime;

import com.hbm.entity.mob.ai.EntityAINearestAttackableTargetNT;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBOTPrimeBody extends EntityBOTPrimeBase {

	//private WormMovementBodyNT movement = new WormMovementBodyNT(this);

	public EntityBOTPrimeBody(World world) {
		super(world);
		this.rangeForParts = 70.0D;
		this.segmentDistance = 3.5D;
		this.maxBodySpeed = 1.4D;
		this.targetTasks.addTask(1, new EntityAINearestAttackableTargetNT(this, EntityPlayer.class, 0, false, false, this.selector, 128.0D));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(17, (byte) 0);
	}

	@Override
	public float getAttackStrength(Entity target) {

		if(target instanceof EntityLivingBase) {
			return ((EntityLivingBase) target).getHealth() * 0.75F;
		}

		return 100;
	}

	@Override
	public boolean isPotionApplicable(PotionEffect potion) {
		return false;
	}

	@Override
	protected void updateAITasks() {
		this.updateEntityActionState();
		this.targetTasks.onUpdateTasks();
		
		updateMovement();

		if(this.didCheck) {
			if(this.targetedEntity == null || !this.targetedEntity.isEntityAlive()) {
				setHealth(getHealth() - 1999.0F);
			}
			if(((this.followed == null) || (!this.followed.isEntityAlive())) && (this.rand.nextInt(60) == 0)) {
				this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.0F, false);
			}
		}

		if(this.followed != null && this.followed.isEntityAlive() && getAttackTarget() != null) {
			if(canEntityBeSeenThroughNonSolids(getAttackTarget())) {
				this.attackCounter += 1;
				if(this.attackCounter == 10) {
					laserAttack(this.getAttackTarget(), false);

					this.attackCounter = -20;
				}
			} else if(this.attackCounter > 0) {
				this.attackCounter -= 1;
			}
		} else if(this.attackCounter > 0) {
			this.attackCounter -= 1;
		}

		if(this.targetedEntity != null) {
			double dx = targetedEntity.posX - posX;
			double dy = targetedEntity.posY - posY;
			double dz = targetedEntity.posZ - posZ;
			float f3 = MathHelper.sqrt_double(dx * dx + dz * dz);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(dx, dz) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(dy, f3) * 180.0D / Math.PI);
		}
	}

	@Override
	public void onUpdate() {

		super.onUpdate();

		if(this.targetedEntity != null) {
			double dx = targetedEntity.posX - posX;
			double dy = targetedEntity.posY - posY;
			double dz = targetedEntity.posZ - posZ;
			float f3 = MathHelper.sqrt_double(dx * dx + dz * dz);
			this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(dx, dz) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(dy, f3) * 180.0D / Math.PI);
		}
	}

	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("partID", this.getPartNumber());
	}

	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		setPartNumber(nbt.getInteger("partID"));
	}
}
