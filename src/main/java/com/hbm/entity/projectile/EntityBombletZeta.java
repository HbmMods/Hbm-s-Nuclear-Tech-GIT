package com.hbm.entity.projectile;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBombletZeta extends EntityThrowable {
	
	public int type = 0;

	public EntityBombletZeta(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
	}
	
	@Override
	public void onUpdate() {


		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);
		
		this.motionX *= 0.99;
		this.motionZ *= 0.99;
		this.motionY -= 0.05D;
        
        this.rotation();
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air)
        {
    		if(!this.worldObj.isRemote)
    		{
    			if(type == 0) {
    				ExplosionLarge.explode(worldObj, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 5.0F, true, false, false, this);
    	        	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "hbm:entity.bombDet", 25.0F, 0.8F + rand.nextFloat() * 0.4F);
    			}
    			if(type == 1) {
    				ExplosionLarge.explode(worldObj, this.posX + 0.5F, this.posY + 0.5F, this.posZ + 0.5F, 2.5F, false, false, false, this);
    				ExplosionChaos.burn(worldObj, (int)posX, (int)posY, (int)posZ, 9);
    				ExplosionChaos.flameDeath(worldObj, (int)posX, (int)posY, (int)posZ, 14);
    	        	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "hbm:entity.bombDet", 25.0F, 1.0F);
    	        	
    	        	for(int i = 0; i < 5; i++)
    	        		ExplosionLarge.spawnBurst(worldObj, this.posX + 0.5F, this.posY + 1.0F, this.posZ + 0.5F, rand.nextInt(10) + 15, rand.nextFloat() * 2 + 2);
    			}
    			if(type == 2) {
    	        	worldObj.playSoundEffect((double)(posX + 0.5F), (double)(posY + 0.5F), (double)(posZ + 0.5F), "random.fizz", 5.0F, 2.6F + (rand.nextFloat() - rand.nextFloat()) * 0.8F);
    				ExplosionChaos.spawnChlorine(worldObj, this.posX + 0.5F - motionX, this.posY + 0.5F - motionY, this.posZ + 0.5F - motionZ, 75, 2, 0);
    			}
    			if(type == 4) {
    				worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(worldObj, (int) (BombConfig.fatmanRadius * 1.5), posX, posY, posZ).mute());
    				
    				NBTTagCompound data = new NBTTagCompound();
    				data.setString("type", "muke");
    				if(rand.nextInt(100) == 0) data.setBoolean("balefire", true);
    				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(dimension, posX, posY, posZ, 250));
    				worldObj.playSoundEffect(posX, posY, posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
    			}
    		}
    		this.setDead();
        }
	}
	
	public void rotation() {
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
	protected void onImpact(MovingObjectPosition p_70184_1_) {
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 25000;
    }
}
