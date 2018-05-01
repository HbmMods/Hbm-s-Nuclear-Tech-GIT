package com.hbm.entity.missile;

import com.hbm.entity.particle.EntityDSmokeFX;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityCarrier extends EntityThrowable {

	double acceleration = 0.00D;

	public EntityCarrier(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
        this.setSize(3.0F, 26.0F);
	}
	
	@Override
	public void onUpdate() {
		
		//this.setDead();
		
		if(motionY < 3.0D) {
			acceleration += 0.0005D;
			motionY += acceleration;
		}
		
		this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);
		
		if(!worldObj.isRemote) {
			for(int i = 0; i < 10; i++) {
				EntityGasFlameFX fx = new EntityGasFlameFX(worldObj);
				fx.posY = posY - 0.25D;
				fx.posX = posX + rand.nextGaussian() * 0.75D;
				fx.posZ = posZ + rand.nextGaussian() * 0.75D;
				fx.motionY = -0.2D;
				
				worldObj.spawnEntityInWorld(fx);
			}
			
			if(this.dataWatcher.getWatchableObjectInt(8) == 1)
				for(int i = 0; i < 2; i++) {
					EntityGasFlameFX fx1 = new EntityGasFlameFX(worldObj);
					fx1.posY = posY - 0.25D;
					fx1.posX = posX + rand.nextGaussian() * 0.15D + 2.5D;
					fx1.posZ = posZ + rand.nextGaussian() * 0.15D;
					fx1.motionY = -0.2D;
					
					worldObj.spawnEntityInWorld(fx1);
	
					EntityGasFlameFX fx2 = new EntityGasFlameFX(worldObj);
					fx2.posY = posY - 0.25D;
					fx2.posX = posX + rand.nextGaussian() * 0.15D - 2.5D;
					fx2.posZ = posZ + rand.nextGaussian() * 0.15D;
					fx2.motionY = -0.2D;
					
					worldObj.spawnEntityInWorld(fx2);
	
					EntityGasFlameFX fx3 = new EntityGasFlameFX(worldObj);
					fx3.posY = posY - 0.25D;
					fx3.posX = posX + rand.nextGaussian() * 0.15D;
					fx3.posZ = posZ + rand.nextGaussian() * 0.15D + 2.5D;
					fx3.motionY = -0.2D;
					
					worldObj.spawnEntityInWorld(fx3);
	
					EntityGasFlameFX fx4 = new EntityGasFlameFX(worldObj);
					fx4.posY = posY - 0.25D;
					fx4.posX = posX + rand.nextGaussian() * 0.15D;
					fx4.posZ = posZ + rand.nextGaussian() * 0.15D - 2.5D;
					fx4.motionY = -0.2D;
					
					worldObj.spawnEntityInWorld(fx4);
				}
			
			
			if(this.ticksExisted < 20) {
				ExplosionLarge.spawnShock(worldObj, posX, posY, posZ, 13 + rand.nextInt(3), 4 + rand.nextGaussian() * 2);
			}
		}
		
		if(this.posY > 300 && this.dataWatcher.getWatchableObjectInt(8) == 1)
			this.disengageBoosters();
			//this.setDead();
		
		if(this.posY > 600) {
			this.setDead();
		}
	}

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(8, 1);
	}
	
	private void disengageBoosters() {
		this.dataWatcher.updateObject(8, 0);
		
		if(!worldObj.isRemote) {
			EntityBooster boost1 = new EntityBooster(worldObj);
			boost1.posX = posX + 1.5D;
			boost1.posY = posY;
			boost1.posZ = posZ;
			boost1.motionX = 0.45D + rand.nextDouble() * 0.2D;
			boost1.motionY = motionY;
			boost1.motionZ = rand.nextGaussian() * 0.1D;
			worldObj.spawnEntityInWorld(boost1);
			
			EntityBooster boost2 = new EntityBooster(worldObj);
			boost2.posX = posX - 1.5D;
			boost2.posY = posY;
			boost2.posZ = posZ;
			boost2.motionX = -0.45D - rand.nextDouble() * 0.2D;
			boost2.motionY = motionY;
			boost2.motionZ = rand.nextGaussian() * 0.1D;
			worldObj.spawnEntityInWorld(boost2);
			
			EntityBooster boost3 = new EntityBooster(worldObj);
			boost3.posX = posX;
			boost3.posY = posY;
			boost3.posZ = posZ + 1.5D;
			boost3.motionZ = 0.45D + rand.nextDouble() * 0.2D;
			boost3.motionY = motionY;
			boost3.motionX = rand.nextGaussian() * 0.1D;
			worldObj.spawnEntityInWorld(boost3);
			
			EntityBooster boost4 = new EntityBooster(worldObj);
			boost4.posX = posX;
			boost4.posY = posY;
			boost4.posZ = posZ - 1.5D;
			boost4.motionZ = -0.45D - rand.nextDouble() * 0.2D;
			boost4.motionY = motionY;
			boost4.motionX = rand.nextGaussian() * 0.1D;
			worldObj.spawnEntityInWorld(boost4);
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }
}
