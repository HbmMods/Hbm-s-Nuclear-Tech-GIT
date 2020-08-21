package com.hbm.entity.mob.botprime;

import com.hbm.entity.mob.EntityAINearestAttackableTargetNT;
import com.hbm.entity.particle.EntityCloudFX;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBOTPrimeHead extends EntityBOTPrimeBase implements IBossDisplayData {
	
	/*   ___   _   _   _   ___           ___           _____ ___  ___ _  _       ___ ___  _ _   _ ___
	 *  | _ ) /_\ | | | | / __|   ___   |   |   ___   |_   _| _ )|   | \| |     | _ \ _ )| | \ / | __|
	 *  | _ \/ _ \| |_| |_\__ \  |___|  | | |  |___|    | | |   \| | |    |     |  _/   \| |  V  | _|
	 *  |___/_/ \_\___|___|___/         |___|           |_| |_|\_\___|_|\_|     |_| |_|\_\_|_|V|_|___|
	 */

	private final WormMovementHeadNT movement = new WormMovementHeadNT(this);
	
	public EntityBOTPrimeHead(World world) {
		super(world);
		/*this.experienceValue = 1000;
		this.wasNearGround = false;
		this.attackRange = 150.0D;
		this.setSize(3.0F, 3.0F);
		this.maxSpeed = 1.0D;
		this.fallSpeed = 0.006D;
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTargetNT(this, EntityPlayer.class, 0, false, false, null, 128.0D));*/
		//this.targetTasks.addTask(3, new EntityAINearestAttackableTargetNT(this, Entity.class, 0, false, false, this.selector, 50.0D));
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
    	/*setHeadID(this.getEntityId());
    	
    	int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.posY);
        int z = MathHelper.floor_double(this.posZ);
        
        for (int i = 0; i < 49; i++) {
        	
          EntityBOTPrimeBody bodyPart = new EntityBOTPrimeBody(this.worldObj);
          bodyPart.setPartNumber(i);
          bodyPart.setPosition(x, y, z);
          bodyPart.setHeadID(getEntityId());
          this.worldObj.spawnEntityInWorld(bodyPart);
        }
        
        setPosition(x, y, z);
        this.spawnPoint.set(x, y, z);
        
        this.aggroCooldown = 60;*/
        
        return super.onSpawnWithEgg(data);
    }
    
	@Override
	protected void updateAITasks() {
		
		super.updateAITasks();
		
	    /*this.movement.updateMovement();
	    
	    if(worldObj.isRemote) {
	    	System.out.println(posX + " " + posY + " " + posZ);
	    }
	    
	    if ((getHealth() < getMaxHealth()) && (this.ticksExisted % 6 == 0)) {
	      if (this.targetedEntity != null) {
	        heal(1.0F);
	      } else if (this.recentlyHit == 0) {
	        heal(4.0F);
	      }
	    }
	    if ((this.targetedEntity != null) && (this.targetedEntity.getDistanceSqToEntity(this) < this.attackRange * this.attackRange))
	    {
	      if (canEntityBeSeenThroughNonSolids(this.targetedEntity))
	      {
	        this.attackCounter += 1;
	        if (this.attackCounter == 10)
	        {
	          //useLaser(this.targetedEntity, true);
	          
	          this.attackCounter = -20;
	        }
	      }
	      else if (this.attackCounter > 0)
	      {
	        this.attackCounter -= 1;
	      }
	    }
	    else if (this.attackCounter > 0) {
	      this.attackCounter -= 1;
	    }*/
	}

	@Override
	public float getAttackStrength(Entity target) {
		
		if(target instanceof EntityLivingBase) {
			return ((EntityLivingBase) target).getHealth() * 0.75F;
		}
		
		return 100;
	}

	@Override
	public IChatComponent func_145748_c_() {
        return super.func_145748_c_();
	}
	
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	  {
	    super.writeEntityToNBT(par1NBTTagCompound);
	    par1NBTTagCompound.setInteger("AggroCD", this.aggroCooldown);
	    par1NBTTagCompound.setInteger("CenterX", this.spawnPoint.posX);
	    par1NBTTagCompound.setInteger("CenterY", this.spawnPoint.posY);
	    par1NBTTagCompound.setInteger("CenterZ", this.spawnPoint.posZ);
	  }
	  
	  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	  {
	    super.readEntityFromNBT(par1NBTTagCompound);
	    this.aggroCooldown = par1NBTTagCompound.getInteger("AggroCD");
	    this.spawnPoint.set(par1NBTTagCompound.getInteger("CenterX"), par1NBTTagCompound.getInteger("CenterY"), par1NBTTagCompound.getInteger("CenterZ"));
	  }

}
