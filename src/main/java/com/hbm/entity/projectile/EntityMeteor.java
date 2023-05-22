package com.hbm.entity.projectile;

import com.hbm.config.WorldConfig;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;
import com.hbm.world.feature.Meteorite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMeteor extends Entity {
	
	public boolean safe = false;

	public EntityMeteor(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.setSize(4F, 4F);
	}

	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote && !WorldConfig.enableMeteorStrikes) {
			this.setDead();
			return;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.motionY -= 0.03;
		if(motionY < -2.5)
			motionY = -2.5;
		
		this.moveEntity(motionX, motionY, motionZ);

		if(!this.worldObj.isRemote && this.onGround && this.posY < 260) {
			
			worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 5 + rand.nextFloat(), !safe);
			if(WorldConfig.enableMeteorTails) {
				ExplosionLarge.spawnParticles(worldObj, posX, posY + 5, posZ, 75);
				ExplosionLarge.spawnParticles(worldObj, posX + 5, posY, posZ, 75);
				ExplosionLarge.spawnParticles(worldObj, posX - 5, posY, posZ, 75);
				ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ + 5, 75);
				ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ - 5, 75);
			}

			(new Meteorite()).generate(worldObj, rand, (int) Math.round(this.posX - 0.5D), (int) Math.round(this.posY - 0.5D), (int) Math.round(this.posZ - 0.5D), safe, true, true);
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:entity.oldExplosion", 10000.0F, 0.5F + this.rand.nextFloat() * 0.1F);
			this.setDead();
		}

		if(WorldConfig.enableMeteorTails && worldObj.isRemote) {

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "exhaust");
			data.setString("mode", "meteor");
			data.setInteger("count", 10);
			data.setDouble("width", 1);
			data.setDouble("posX", posX - motionX);
			data.setDouble("posY", posY - motionY);
			data.setDouble("posZ", posZ - motionZ);

			MainRegistry.proxy.effectNT(data);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.safe = nbt.getBoolean("safe");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("safe", safe);
	}
}
