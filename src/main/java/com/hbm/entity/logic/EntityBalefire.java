package com.hbm.entity.logic;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.explosion.ExplosionBalefire;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.main.MainRegistry;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.hbm.util.ParticleUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBalefire extends EntityExplosionChunkloading  {
	
	public int age = 0;
	public int destructionRange = 0;
	public ExplosionBalefire exp;
	public int speed = 1;
	public boolean did = false;
	public boolean mute = false;
	public boolean antimatter = false;

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		age = nbt.getInteger("age");
		destructionRange = nbt.getInteger("destructionRange");
		speed = nbt.getInteger("speed");
		did = nbt.getBoolean("did");
		mute = nbt.getBoolean("mute");
		antimatter = nbt.getBoolean("antimatter");
		
    	
		exp = new ExplosionBalefire((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.antimatter);
		exp.readFromNbt(nbt, "exp_");
    	
    	this.did = true;
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", age);
		nbt.setInteger("destructionRange", destructionRange);
		nbt.setInteger("speed", speed);
		nbt.setBoolean("did", did);
		nbt.setBoolean("mute", mute);
		nbt.setBoolean("antimatter", antimatter);
    	
		if(exp != null)
			exp.saveToNbt(nbt, "exp_");
		
	}

	public EntityBalefire(World p_i1582_1_) {
		super(p_i1582_1_);
	}

    @Override
	public void onUpdate() {
        super.onUpdate();
        	
        if(!this.did)
        {
    		if(GeneralConfig.enableExtendedLogging && !worldObj.isRemote)
    			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized BF explosion at " + posX + " / " + posY + " / " + posZ + " with strength " + destructionRange + "!");
    		
        	exp = new ExplosionBalefire((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.antimatter);
        	
        	this.did = true;
        }
        
        speed += 1;	//increase speed to keep up with expansion
        
        boolean flag = false;
        for(int i = 0; i < this.speed; i++)
        {
        	flag = exp.update();
        	
        	if(flag) {
				clearChunkLoader();
        		this.setDead();
        	}
        }
		if(!worldObj.isRemote && antimatter && this.ticksExisted < 10) {
			radiate(40000*this.destructionRange, this.destructionRange * 2);
		}
    	if(!mute && rand.nextInt(5) == 0)
        	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
        	
        if(!flag) {
        	
        	if(!mute)
        		this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
        	
        	ExplosionNukeGeneric.dealDamage(this.worldObj, this.posX, this.posY, this.posZ, this.destructionRange * 2);
        }
        
        age++;
    }

	private void radiate(float rads, double range) {
		
		List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX, posY, posZ).expand(range, range, range));
		
		for(EntityLivingBase e : entities) {
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - posX, (e.posY + e.getEyeHeight()) - posY, e.posZ - posZ);
			double len = vec.lengthVector();
			vec = vec.normalize();
			
			float res = 0;
			
			for(int i = 1; i < len; i++) {

				int ix = (int)Math.floor(posX + vec.xCoord * i);
				int iy = (int)Math.floor(posY + vec.yCoord * i);
				int iz = (int)Math.floor(posZ + vec.zCoord * i);
				
				res += worldObj.getBlock(ix, iy, iz).getExplosionResistance(null);
			}
			
			if(res < 1)
				res = 1;
			
			float eRads = rads;
			eRads /= (float)res;
			eRads /= (float)(len * len);
			
			ContaminationUtil.contaminate(e, HazardType.RADIATION, ContaminationType.CREATIVE, eRads);
			if(eRads>=500)
			{
				if(!(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode))
				{
					e.setHealth(0);
					//this.worldObj.createExplosion(this, e.posX, e.posY, e.posZ, 2.0F, true);
					//ExplosionChaos.plasma(this.worldObj, (int) e.posX, (int) e.posY, (int) e.posZ, 4);
					ExplosionLarge.spawnShock(worldObj, e.posX, e.posY, e.posZ, 10, 10);
					e.setDead();
				}
			}
		}

		age++;
	}
	public EntityBalefire mute() {
		this.mute = true;
		return this;
	}
	
	public EntityBalefire antimatter() {
		this.antimatter = true;
		return this;
	}
}
