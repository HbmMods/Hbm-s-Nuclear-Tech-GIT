package com.hbm.entity.logic;

import org.apache.logging.log4j.Level;

import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeRay;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityNukeExplosionMK4 extends Entity {
	
	//Strength of the blast
	public int strength;
	//How many rays should be created
	public int count;
	//How many rays are calculated per tick
	public int speed;
	public int length;
	
	public boolean fallout = true;
	
	ExplosionNukeRay explosion;

	public EntityNukeExplosionMK4(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	public EntityNukeExplosionMK4(World world, int strength, int count, int speed, int length) {
		super(world);
		this.strength = strength;
		this.count = count;
		this.speed = speed;
		this.length = length;
	}
	
	@Override
	public void onUpdate() {
		if(strength == 0) {
			this.setDead();
			return;
		}
		
		if(!worldObj.isRemote && fallout && explosion != null) {
			RadiationSavedData data = RadiationSavedData.getData(worldObj);

			//float radMax = (float) (length / 2F * Math.pow(length, 2) / 35F);
			float radMax = Math.min((float) (length / 2F * Math.pow(length, 1.5) / 35F), 15000);
			//System.out.println(radMax);
			float rad = radMax / 4F;
			data.incrementRad(worldObj, (int)this.posX, (int)this.posZ, rad, radMax);
		}
		
    	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
    	if(rand.nextInt(5) == 0)
        	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
		
    	ExplosionNukeGeneric.dealDamage(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.length * 2);
		
		if(explosion == null) {
			explosion = new ExplosionNukeRay(worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.strength, this.count, this.speed, this.length);
			
			//MainRegistry.logger.info("START: " + System.currentTimeMillis());
			
			/*if(!worldObj.isRemote)
				for(int x = (int) (posX - 1); x <= (int) (posX + 1); x++)
					for(int y = (int) (posY - 1); y <= (int) (posY + 1); y++)
						for(int z = (int) (posZ - 1); z <= (int) (posZ + 1); z++)
							worldObj.setBlock(x, y, z, Blocks.air);*/
		}
		
		//if(explosion.getStoredSize() < count / length) {
		if(!explosion.isAusf3Complete) {
			//if(!worldObj.isRemote)
			//MainRegistry.logger.info(explosion.getStoredSize() + " / " + count / length);
			//explosion.collectTip(speed * 10);
			explosion.collectTipMk4_5(speed * 10);
		} else if(explosion.getStoredSize() > 0) {
			//if(!worldObj.isRemote)
			//MainRegistry.logger.info(explosion.getProgress() + " / " + count / length);
				explosion.processTip(MainRegistry.mk4);
		} else if(fallout) {

			//MainRegistry.logger.info("STOP: " + System.currentTimeMillis());
			
			EntityFalloutRain fallout = new EntityFalloutRain(this.worldObj);
			fallout.posX = this.posX;
			fallout.posY = this.posY;
			fallout.posZ = this.posZ;
			fallout.setScale((int)(this.length * 1.8) * MainRegistry.falloutRange / 100);

			this.worldObj.spawnEntityInWorld(fallout);
			
			this.setDead();
		} else {
			this.setDead();
		}
	}

	@Override
	protected void entityInit() {
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		
	}
	
	public static EntityNukeExplosionMK4 statFac(World world, int r, double x, double y, double z) {
		
		if(MainRegistry.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized explosion at " + x + " / " + y + " / " + z + " with strength " + r + "!");
		
		if(r == 0)
			r = 25;
		
		r *= 2;
		
		EntityNukeExplosionMK4 mk4 = new EntityNukeExplosionMK4(world);
		mk4.strength = (int)(r);
		mk4.count = (int)(4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int)Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		return mk4;
	}
	
	public static EntityNukeExplosionMK4 statFacExperimental(World world, int r, double x, double y, double z) {
		
		if(MainRegistry.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized eX explosion at " + x + " / " + y + " / " + z + " with strength " + r + "!");
		
		r *= 2;
		
		EntityNukeExplosionMK4 mk4 = new EntityNukeExplosionMK4(world);
		mk4.strength = (int)(r);
		mk4.count = (int)(4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int)Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		return mk4;
	}
	
	public static EntityNukeExplosionMK4 statFacNoRad(World world, int r, double x, double y, double z) {
		
		if(MainRegistry.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized nR explosion at " + x + " / " + y + " / " + z + " with strength " + r + "!");
		
		r *= 2;
		
		EntityNukeExplosionMK4 mk4 = new EntityNukeExplosionMK4(world);
		mk4.strength = (int)(r);
		mk4.count = (int)(4 * Math.PI * Math.pow(mk4.strength, 2) * 25);
		mk4.speed = (int)Math.ceil(100000 / mk4.strength);
		mk4.setPosition(x, y, z);
		mk4.length = mk4.strength / 2;
		mk4.fallout = false;
		return mk4;
	}

}
