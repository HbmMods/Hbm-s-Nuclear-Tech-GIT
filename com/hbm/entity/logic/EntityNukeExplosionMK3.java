package com.hbm.entity.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionFleija;
import com.hbm.explosion.ExplosionNukeAdvanced;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionSolinium;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityNukeExplosionMK3 extends Entity {
	
	public int age = 0;
	public int destructionRange = 0;
	public ExplosionNukeAdvanced exp;
	public ExplosionNukeAdvanced wst;
	public ExplosionNukeAdvanced vap;
	public ExplosionFleija expl;
	public ExplosionSolinium sol;
	public int speed = 1;
	public float coefficient = 1;
	public float coefficient2 = 1;
	public boolean did = false;
	public boolean did2 = false;
	public boolean waste = true;
	public boolean destructive = true;
	public boolean affectedByWalls = true;
	//Extended Type
	public int extType = 0;
	// Effects applied
	public List<PotionEffect> effects;

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		age = nbt.getInteger("age");
		destructionRange = nbt.getInteger("destructionRange");
		speed = nbt.getInteger("speed");
		coefficient = nbt.getFloat("coefficient");
		coefficient2 = nbt.getFloat("coefficient2");
		did = nbt.getBoolean("did");
		did2 = nbt.getBoolean("did2");
		waste = nbt.getBoolean("waste");
		extType = nbt.getInteger("extType");
		
		long time = nbt.getLong("milliTime");
		
		if(MainRegistry.limitExplosionLifespan > 0 && System.currentTimeMillis() - time > MainRegistry.limitExplosionLifespan * 1000)
			this.setDead();
		
		destructive = nbt.getBoolean("destructive");
		affectedByWalls = nbt.getBoolean("affectedByWalls");
		if (this.destructive) { 
			if(this.waste)
			{
				exp = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient, 0);
				exp.readFromNbt(nbt, "exp_");
				wst = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, (int)(this.destructionRange * 1.8), this.coefficient, 2);
				wst.readFromNbt(nbt, "wst_");
				vap = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, (int)(this.destructionRange * 2.5), this.coefficient, 1);
				vap.readFromNbt(nbt, "vap_");
			} else {
				if(extType == 0) {
					expl = new ExplosionFleija((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient, this.coefficient2);
					expl.readFromNbt(nbt, "expl_");
				}
				if(extType == 1) {
					sol = new ExplosionSolinium((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient, this.coefficient2);
					sol.readFromNbt(nbt, "sol_");
				}
			}
		}
    	
    	this.did = true;
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("age", age);
		nbt.setInteger("destructionRange", destructionRange);
		nbt.setInteger("speed", speed);
		nbt.setFloat("coefficient", coefficient);
		nbt.setFloat("coefficient2", coefficient2);
		nbt.setBoolean("did", did);
		nbt.setBoolean("did2", did2);
		nbt.setBoolean("waste", waste);
		nbt.setInteger("extType", extType);
		
		nbt.setLong("milliTime", System.currentTimeMillis());
		
		nbt.setBoolean("destructive", destructive);
		nbt.setBoolean("affectedByWalls", affectedByWalls);
    	
		if(exp != null)
			exp.saveToNbt(nbt, "exp_");
		if(wst != null)
			wst.saveToNbt(nbt, "wst_");
		if(vap != null)
			vap.saveToNbt(nbt, "vap_");
		if(expl != null)
			expl.saveToNbt(nbt, "expl_");
		if(sol != null)
			sol.saveToNbt(nbt, "sol_");
		
	}

	public EntityNukeExplosionMK3(World p_i1582_1_) {
		super(p_i1582_1_);
	}

    @Override
	public void onUpdate() {
        super.onUpdate();
        	
        if(!this.did)
        {
    		if(MainRegistry.enableExtendedLogging && !worldObj.isRemote)
    			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized mk3 explosion at " + posX + " / " + posY + " / " + posZ + " with strength " + destructionRange + "!");
    		
    		if (this.destructive) { 
    			if(this.waste)
    			{
    				exp = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient, 0);
    				wst = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, (int)(this.destructionRange * 1.8), this.coefficient, 2);
    				vap = new ExplosionNukeAdvanced((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, (int)(this.destructionRange * 2.5), this.coefficient, 1);
    			} else {
    				if(extType == 0)
    					expl = new ExplosionFleija((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient, this.coefficient2);
    				if(extType == 1)
    					sol = new ExplosionSolinium((int)this.posX, (int)this.posY, (int)this.posZ, this.worldObj, this.destructionRange, this.coefficient, this.coefficient2);
    			}
    		}
        	
        	this.did = true;
        }
        
        speed += 1;	//increase speed to keep up with expansion
        
        boolean flag = false;
        boolean flag3 = false;
        
        if (this.destructive) { 
        	for(int i = 0; i < this.speed; i++)
        	{
        		if(waste) {
        			flag = exp.update();
        			wst.update();
        			flag3 = vap.update();
        		
        			if(flag3) {
        				this.setDead();
        			}
        		} else {
        			if(extType == 0)
        				if(expl.update())
        					this.setDead();
        			if(extType == 1)
        				if(sol.update())
        					this.setDead();
        		}
        	}
        } else {
        	if (this.age > this.destructionRange) {
        		this.setDead();
        	}
        }
        	
        if(!flag)
        {
        	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
        	if (this.destructive) {
        		ExplosionNukeGeneric.dealDamage(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.destructionRange * 2);
        	}
        	if (effects != null && effects.size() != 0) {
        		ExplosionNukeGeneric.applyPotionEffects(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.destructionRange * 2, effects, affectedByWalls);
        	}
        } else {
			if (!did2 && waste) {
				EntityFalloutRain fallout = new EntityFalloutRain(this.worldObj, (int)(this.destructionRange * 1.8) * 10);
				fallout.posX = this.posX;
				fallout.posY = this.posY;
				fallout.posZ = this.posZ;
				fallout.setScale((int)(this.destructionRange * 1.8));

				this.worldObj.spawnEntityInWorld(fallout);
				//this.worldObj.getWorldInfo().setRaining(true);
				
				did2 = true;
        	}
        }
        
        age++;
    }

	@Override
	protected void entityInit() { }
}
