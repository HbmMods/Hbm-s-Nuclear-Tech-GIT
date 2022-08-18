package com.hbm.entity.logic;

import org.apache.logging.log4j.Level;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionFleija;
import com.hbm.explosion.ExplosionHurtUtil;
import com.hbm.explosion.ExplosionNukeAdvanced;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionSolinium;
import com.hbm.interfaces.Spaghetti;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

@Spaghetti("why???")
public class EntityNukeExplosionMK3 extends Entity implements IChunkLoader {
	
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
	private Ticket loaderTicket;
	//Extended Type
	public int extType = 0;

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
		
		if(BombConfig.limitExplosionLifespan > 0 && System.currentTimeMillis() - time > BombConfig.limitExplosionLifespan * 1000)
			this.setDead();
		
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
        	for(Object player : this.worldObj.playerEntities)
    			((EntityPlayer)player).triggerAchievement(MainRegistry.achManhattan);
        	
    		if(GeneralConfig.enableExtendedLogging && !worldObj.isRemote)
    			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized mk3 explosion at " + posX + " / " + posY + " / " + posZ + " with strength " + destructionRange + "!");
    		
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
        	
        	this.did = true;
        }
        
        speed += 1;	//increase speed to keep up with expansion
        
        boolean flag = false;
        boolean flag3 = false;
        
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
        	
        if(!flag)
        {
        	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
        	
        	if(waste || extType != 1) {
        		ExplosionNukeGeneric.dealDamage(this.worldObj, this.posX, this.posY, this.posZ, this.destructionRange * 2);
        	} else {
        		ExplosionHurtUtil.doRadiation(worldObj, posX, posY, posZ, 15000, 250000, this.destructionRange);
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
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
	}
	
	public static EntityNukeExplosionMK3 statFacFleija(World world, double x, double y, double z, int range) {
		
		EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
		entity.posX = x;
		entity.posY = y;
		entity.posZ = z;
		entity.destructionRange = range;
		entity.speed = BombConfig.blastSpeed;
		entity.coefficient = 1.0F;
		entity.waste = false;
		
		if(range > 50) {
			
			for(int i = -1; i <= 1; i++) {
				for(int j = -1; j <= 1; j++) {
					for(int k = (int)y + 15; k > 5; k--) {
						
						if(world.getBlock((int)x + i * 15, k, (int)z + j * 15) == ModBlocks.stone_porous) {
							entity.destructionRange = 50;
							return entity;
						}
					}
				}
			}
		}
		
		return entity;
	}
	
	public EntityNukeExplosionMK3 makeSol() {
		this.extType = 1;
		return this;
	}
	public void init(Ticket ticket) {
		if(!worldObj.isRemote) {
			
            if(ticket != null) {
            	
                if(loaderTicket == null) {
                	
                	loaderTicket = ticket;
                	loaderTicket.bindEntity(this);
                	loaderTicket.getModData();
                }

                ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
            }
        }
	}
}
