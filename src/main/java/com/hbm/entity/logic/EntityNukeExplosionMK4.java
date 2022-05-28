package com.hbm.entity.logic;

import org.apache.logging.log4j.Level;

import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeRay;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;

public class EntityNukeExplosionMK4 extends Entity implements IChunkLoader {
	
	//Strength of the blast
	public int strength;
	//How many rays should be created
	public int count;
	//How many rays are calculated per tick
	public int speed;
	public int length;
	private Ticket loaderTicket;
	public boolean mute = false;
	
	public boolean fallout = true;
	private int falloutAdd = 0;
	
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
		
		for(Object player : this.worldObj.playerEntities)
			((EntityPlayer)player).triggerAchievement(MainRegistry.achManhattan);
		
		if(!worldObj.isRemote && fallout && explosion != null) {

			//float radMax = (float) (length / 2F * Math.pow(length, 2) / 35F);
			float radMax = Math.min((float) (length / 2F * Math.pow(length, 1.5) / 35F), 15000);
			//System.out.println(radMax);
			float rad = radMax / 4F;
			ChunkRadiationManager.proxy.incrementRad(worldObj, (int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ), rad);
		}
		
		if(!mute) {
	    	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
	    	if(rand.nextInt(5) == 0)
	        	this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
		}
		
		ExplosionNukeGeneric.dealDamage(this.worldObj, this.posX, this.posY, this.posZ, this.length * 2);
		
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
				explosion.processTip(BombConfig.mk4);
		} else if(fallout) {

			//MainRegistry.logger.info("STOP: " + System.currentTimeMillis());
			
			EntityFalloutRain fallout = new EntityFalloutRain(this.worldObj);
			fallout.posX = this.posX;
			fallout.posY = this.posY;
			fallout.posZ = this.posZ;
			fallout.setScale((int)(this.length * 1.8 + falloutAdd) * BombConfig.falloutRange / 100);

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
	protected void readEntityFromNBT(NBTTagCompound nbt) {

		/*strength = nbt.getInteger("strength");
		count = nbt.getInteger("count");
		speed = nbt.getInteger("speed");
		length = nbt.getInteger("length");
		fallout = nbt.getBoolean("fallout");
		falloutAdd = nbt.getInteger("falloutAdd");*/
		
		//TODO: implement NBT functionality for MK4 explosion logic
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) { }
	
	public static EntityNukeExplosionMK4 statFac(World world, int r, double x, double y, double z) {
		
		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
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
		
		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
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
		
		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
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
	
	public EntityNukeExplosionMK4 moreFallout(int fallout) {
		falloutAdd = fallout;
		return this;
	}
	
	public EntityNukeExplosionMK4 mute() {
		this.mute = true;
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
