package com.hbm.entity.mob.botprime;

import java.util.List;

import com.hbm.entity.mob.EntityAINearestAttackableTargetNT;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBOTPrimeHead extends EntityBOTPrimeBase implements IBossDisplayData {
	
	/*   ___   _   _   _   ___           ___           _____ ___  ___ _  _       ___ ___  _ _   _ ___
	 *  | _ ) /_\ | | | | / __|   ___   |   |   ___   |_   _| _ )|   | \| |     | _ \ _ )| | \ / | __|
	 *  | _ \/ _ \| |_| |_\__ \  |___|  | | |  |___|    | | |   \| | |    |     |  _/   \| |  V  | _|
	 *  |___/_/ \_\___|___|___/         |___|           |_| |_|\_\___|_|\_|     |_| |_|\_\_|_|V|_|___|
	 */

	//TODO: clean-room implementation of the movement behavior classes (again)
	
	private final WormMovementHeadNT movement = new WormMovementHeadNT(this);
	
	public EntityBOTPrimeHead(World world) {
		super(world);
		this.experienceValue = 1000;
		this.wasNearGround = false;
		this.attackRange = 150.0D;
		this.setSize(3.0F, 3.0F);
		this.maxSpeed = 1.0D;
		this.fallSpeed = 0.006D;
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTargetNT(this, EntityPlayer.class, 0, false, false, null, 128.0D));
		this.ignoreFrustumCheck = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15D);
	}

	@Override
	public boolean getIsHead() {
		return true;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		
		if(super.attackEntityFrom(par1DamageSource, par2)) {
			this.dmgCooldown = 4;
			return true;
		}

		return false;
	}

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
    	
    	//TODO: check if this is even needed
    	setHeadID(this.getEntityId());
    	
    	int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.posY);
        int z = MathHelper.floor_double(this.posZ);
        
        for (int i = 0; i < 74; i++) {
        	
          EntityBOTPrimeBody bodyPart = new EntityBOTPrimeBody(this.worldObj);
          bodyPart.setPartNumber(i);
          bodyPart.setPosition(x, y, z);
          bodyPart.setHeadID(getEntityId());
          this.worldObj.spawnEntityInWorld(bodyPart);
        }
        
        setPosition(x, y, z);
        this.spawnPoint.set(x, y, z);
        
        return super.onSpawnWithEgg(data);
    }
    
	@Override
	protected void updateAITasks() {

		this.updateEntityActionState();
		super.updateAITasks();

		this.movement.updateMovement();

		if((getHealth() < getMaxHealth()) && (this.ticksExisted % 6 == 0)) {
			if(this.targetedEntity != null) {
				heal(1.0F);
			} else if(this.recentlyHit == 0) {
				heal(4.0F);
			}
		}
		if((this.targetedEntity != null) && (this.targetedEntity.getDistanceSqToEntity(this) < this.attackRange * this.attackRange)) {
			if(canEntityBeSeenThroughNonSolids(this.targetedEntity)) {
				
				this.attackCounter ++;
				
				if(this.attackCounter == 30) {
					
					laserAttack(this.targetedEntity, true);
					this.attackCounter = 0;
				}
			} else {
				this.attackCounter = 0;
			}
		} else {
			this.attackCounter = 0;
		}
	}
	
	@Override
	public void onUpdate() {
		
		super.onUpdate();

		double dx = motionX;
		double dy = motionY;
		double dz = motionZ;
		float f3 = MathHelper.sqrt_double(dx * dx + dz * dz);
		this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(dx, dz) * 180.0D / Math.PI);
		this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(dy, f3) * 180.0D / Math.PI);
	}
    
    @Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);
		
		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(200, 200, 200));

		for(EntityPlayer player : players) {
			player.triggerAchievement(MainRegistry.bossMaskman);
			player.inventory.addItemStackToInventory(new ItemStack(ModItems.coin_worm));
		}
	}

	@Override
	public float getAttackStrength(Entity target) {
		return 1000;
	}
	
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("spawnX", this.spawnPoint.posX);
		nbt.setInteger("spawnY", this.spawnPoint.posY);
		nbt.setInteger("spawnZ", this.spawnPoint.posZ);
	}

	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.spawnPoint.set(nbt.getInteger("spawnX"), nbt.getInteger("spawnY"), nbt.getInteger("spawnZ"));
	}

}
