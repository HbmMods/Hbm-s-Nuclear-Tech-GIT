package com.hbm.entity.logic;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.projectile.EntityBombletZeta;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.deco.TileEntityBomber;
import com.hbm.tileentity.machine.TileEntityMachineRadar;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBomber extends Entity {
	
	int timer = 200;
	int bombStart = 75;
	int bombStop = 125;
	int bombRate = 3;
	int type = 0;

	public int health = 50;
	
    public EntityBomber(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
    	this.setSize(8.0F, 4.0F);
	}
	
    public boolean canBeCollidedWith()
    {
        return this.health > 0;
    }
    
    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_)
    {
    	if(p_70097_1_ == ModDamageSource.nuclearBlast)
    		return false;
    	
        if (this.isEntityInvulnerable())
        {
            return false;
        }
        else
        {
            if (!this.isDead && !this.worldObj.isRemote && this.health > 0)
            {
            	health -= p_70097_2_;
            	
                if (this.health <= 0)
                {
                    this.killBomber();
                }
            }

            return true;
        }
    }
    
    private void killBomber() {
        ExplosionLarge.explode(worldObj, posX, posY, posZ, 5, true, false, true);
    }
	
	@Override
	public void onUpdate() {
		
		//super.onUpdate();

		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;

		this.setPosition(posX + motionX, posY + motionY, posZ + motionZ);
		
		this.rotation();
		
		if(this.health <= 0) {
			motionY -= 0.025;
			
        	for(int i = 0; i < 10; i++)
        		this.worldObj.spawnEntityInWorld(new EntityGasFlameFX(this.worldObj, this.posX + rand.nextGaussian() * 0.5 - motionX * 2, this.posY + rand.nextGaussian() * 0.5 - motionY * 2, this.posZ + rand.nextGaussian() * 0.5 - motionZ * 2, 0.0, 0.1, 0.0));
			
			if(worldObj.getBlock((int)posX, (int)posY, (int)posZ).isNormalCube() && !worldObj.isRemote) {
				this.setDead();
				
				/*worldObj.setBlock((int)posX, (int)posY, (int)posZ, ModBlocks.bomber);
				TileEntityBomber te = (TileEntityBomber)worldObj.getTileEntity((int)posX, (int)posY, (int)posZ);

				if(te != null) {
					te.yaw = (int)(this.rotationYaw);
					te.pitch = (int)(this.rotationPitch);
					
					te.type = this.getDataWatcher().getWatchableObjectByte(16);
				}*/
				
				ExplosionLarge.explodeFire(worldObj, posX, posY, posZ, 25, true, false, true);
				
				return;
			}
		}
		
		//if(this.ticksExisted > timer)
		//	this.setDead();
		
		if(!worldObj.isRemote && this.health > 0 && this.ticksExisted > bombStart && this.ticksExisted < bombStop && this.ticksExisted % bombRate == 0) {
			
			if(type == 3) {

	        	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "random.fizz", 5.0F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
				ExplosionChaos.spawnChlorine(worldObj, this.posX, this.posY - 1F, this.posZ, 10, 0.5, 3);
				
			} else {
				EntityBombletZeta zeta = new EntityBombletZeta(worldObj);
				zeta.rotationYaw = this.rotationYaw;
				zeta.rotationPitch = this.rotationPitch;
				
				zeta.type = type;
				
				zeta.posX = posX + rand.nextDouble() - 0.5;
				zeta.posY = posY - rand.nextDouble();
				zeta.posZ = posZ + rand.nextDouble() - 0.5;
				
				zeta.motionX = motionX;
				zeta.motionZ = motionZ;
				
				worldObj.spawnEntityInWorld(zeta);
			}
		}
		
	}
    
    public void fac(World world, double x, double y, double z) {
    	
    	Vec3 vector = Vec3.createVectorHelper(world.rand.nextDouble() - 0.5, 0, world.rand.nextDouble() - 0.5);
    	vector = vector.normalize();
    	vector.xCoord *= 2;
    	vector.zCoord *= 2;

    	this.posX = x - vector.xCoord * 100;
    	this.posY = y + 50;
    	this.posZ = z - vector.zCoord * 100;
    	
    	this.motionX = vector.xCoord;
    	this.motionZ = vector.zCoord;
    	this.motionY = 0.0D;
    	
    	this.rotation();
    	
    	int i = 1;
    	
    	int rand = world.rand.nextInt(101);
    	
    	if(rand < 50)
    		i = 1;
    	else if(rand > 50)
    		i = 2;
    	else
    		i = 0;
    	
    	this.getDataWatcher().updateObject(16, (byte)i);
    	this.setSize(8.0F, 4.0F);
    }
    
    public static EntityBomber statFacCarpet(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 50;
    	bomber.bombStop = 100;
    	bomber.bombRate = 3;

    	bomber.fac(world, x, y, z);
    	
    	bomber.type = 0;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacNapalm(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 50;
    	bomber.bombStop = 100;
    	bomber.bombRate = 5;

    	bomber.fac(world, x, y, z);
    	
    	bomber.type = 1;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacChlorine(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 50;
    	bomber.bombStop = 100;
    	bomber.bombRate = 4;

    	bomber.fac(world, x, y, z);
    	
    	bomber.type = 2;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacOrange(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 75;
    	bomber.bombStop = 125;
    	bomber.bombRate = 1;

    	bomber.fac(world, x, y, z);
    	
    	bomber.type = 3;
    	
    	return bomber;
    }
    
    public static EntityBomber statFacABomb(World world, double x, double y, double z) {
    	
    	EntityBomber bomber = new EntityBomber(world);
    	
    	bomber.timer = 200;
    	bomber.bombStart = 70;
    	bomber.bombStop = 80;
    	bomber.bombRate = 75;

    	bomber.fac(world, x, y, z);
    	
    	bomber.type = 4;
    	
    	return bomber;
    }

    @Override
	public void entityInit() {
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) { }
	
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
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }

}
