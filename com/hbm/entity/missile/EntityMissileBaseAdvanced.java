package com.hbm.entity.missile;

import com.hbm.entity.particle.EntitySmokeFX;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityMissileBaseAdvanced extends Entity {
	
	int startX;
	int startZ;
	int targetX;
	int targetZ;
	double decelY;
	double accelXZ;
	boolean isCluster = false;
	float health = 50;

	public EntityMissileBaseAdvanced(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		startX = (int) posX;
		startZ = (int) posZ;
		targetX = (int) posX;
		targetZ = (int) posZ;
	}
	
    public boolean attackEntityFrom(DamageSource p_70097_1_, float f)
    {
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            this.setBeenAttacked();
            health -= f;
            
            if(health <= 0) {
            	if(!worldObj.isRemote)
            		worldObj.createExplosion(this, posX, posY, posZ, 15, true);
            	
            	this.setDead();
            }
            
            return true;
        }
    }

	public EntityMissileBaseAdvanced(World world, float x, float y, float z, int a, int b) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		startX = (int) x;
		startZ = (int) z;
		targetX = a;
		targetZ = b;
		this.motionY = 1.5;
		
        Vec3 vector = Vec3.createVectorHelper(targetX - startX, 0, targetZ - startZ);
		accelXZ = decelY = 1/vector.lengthVector();
		decelY *= 1.5;
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		motionX = nbt.getDouble("moX");
		motionY = nbt.getDouble("moY");
		motionZ = nbt.getDouble("moZ");
		posX = nbt.getDouble("poX");
		posY = nbt.getDouble("poY");
		posZ = nbt.getDouble("poZ");
		decelY = nbt.getDouble("decel");
		accelXZ = nbt.getDouble("accel");
		targetX = nbt.getInteger("tX");
		targetZ = nbt.getInteger("tZ");
		startX = nbt.getInteger("sX");
		startZ = nbt.getInteger("sZ");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("moX", motionX);
		nbt.setDouble("moY", motionY);
		nbt.setDouble("moZ", motionZ);
		nbt.setDouble("poX", posX);
		nbt.setDouble("poY", posY);
		nbt.setDouble("poZ", posZ);
		nbt.setDouble("decel", decelY);
		nbt.setDouble("accel", accelXZ);
		nbt.setInteger("tX", targetX);
		nbt.setInteger("tZ", targetZ);
		nbt.setInteger("sX", startX);
		nbt.setInteger("sZ", startZ);
	}
	
	protected void rotation() {
        float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
	}
	
	@Override
    public void onUpdate()
    {
		super.onUpdate();
        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        
        this.rotation();
        
        this.motionY -= decelY;
        
        Vec3 vector = Vec3.createVectorHelper(targetX - startX, 0, targetZ - startZ);
        vector = vector.normalize();
        vector.xCoord *= accelXZ;
        vector.zCoord *= accelXZ;
        
        if(motionY > 0) {
        	motionX += vector.xCoord;
        	motionZ += vector.zCoord;
        }
        
        if(motionY < 0) {
        	motionX -= vector.xCoord;
        	motionZ -= vector.zCoord;
        }

		if(!this.worldObj.isRemote)
			this.worldObj.spawnEntityInWorld(new EntitySmokeFX(this.worldObj, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0));
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air && 
        		this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.water && 
        		this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.flowing_water) {
        	
    		if(!this.worldObj.isRemote)
    		{
    			onImpact();
    		}
    		this.setDead();
        }
        
        if(motionY < -1 && this.isCluster && !worldObj.isRemote) {
        	cluster();
    		this.setDead();
        }
    }
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }

	public abstract void onImpact();
	
	public void cluster() { }

}
