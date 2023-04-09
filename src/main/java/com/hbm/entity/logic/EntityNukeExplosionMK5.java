package com.hbm.entity.logic;

import java.util.List;

import org.apache.logging.log4j.Level;

import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeRayBatched;
import com.hbm.main.MainRegistry;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityNukeExplosionMK5 extends Entity {
	
	//Strength of the blast
	public int strength;
	//How many rays are calculated per tick
	public int speed;
	public int length;
	
	public boolean mute = false;
	
	public boolean fallout = true;
	public boolean salted = false;
	private int falloutAdd = 0;
	
	ExplosionNukeRayBatched explosion;

	public EntityNukeExplosionMK5(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	public EntityNukeExplosionMK5(World world, int strength, int speed, int length) {
		super(world);
		this.strength = strength;
		this.speed = speed;
		this.length = length;
	}
	
	@Override
	public void onUpdate() {
		
		if(strength == 0) {
			this.setDead();
			return;
		}
		
		for(Object player : this.worldObj.playerEntities) {
			((EntityPlayer)player).triggerAchievement(MainRegistry.achManhattan);
		}
		
		if(!worldObj.isRemote && fallout && explosion != null && this.ticksExisted < 10) {
			radiate(500_000, this.length * 2);
		}
		
		if(!mute) {
			this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
			if(rand.nextInt(5) == 0)
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
		}
		
		ExplosionNukeGeneric.dealDamage(this.worldObj, this.posX, this.posY, this.posZ, this.length * 2);
		
		if(explosion == null) {
			explosion = new ExplosionNukeRayBatched(worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, this.strength, this.speed, this.length);
		}
		
		if(!explosion.isAusf3Complete) {
			explosion.collectTip(speed * 10);
		} else if(explosion.perChunk.size() > 0) {
			long start = System.currentTimeMillis();
			
			while(explosion.perChunk.size() > 0 && System.currentTimeMillis() < start + BombConfig.mk5) explosion.processChunk();
			
		} else if(fallout) {

			EntityFalloutRain fallout = new EntityFalloutRain(this.worldObj);
			fallout.posX = this.posX;
			fallout.posY = this.posY;
			fallout.posZ = this.posZ;
			fallout.setScale((int)(this.length * 5 + falloutAdd) * BombConfig.falloutRange / 100);
			if(salted)
			{
				fallout.setSalted(true);
			}
			this.worldObj.spawnEntityInWorld(fallout);
			
			this.setDead();
		} else {
			this.setDead();
		}
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
			ContaminationUtil.contaminate(e, HazardType.NEUTRON, ContaminationType.CREATIVE, eRads);
			if(e instanceof EntityPlayer && !RadiationConfig.disableNeutron) {
				//Random rand = target.getRNG();
				EntityPlayer player = (EntityPlayer) e;
				for(int i2 = 0; i2 < player.inventory.mainInventory.length; i2++)
				{
					ItemStack stack2 = player.inventory.getStackInSlot(i2);
					
					if(stack2 != null) {
							if(!stack2.hasTagCompound())
								stack2.stackTagCompound = new NBTTagCompound();
							float activation = stack2.stackTagCompound.getFloat("ntmNeutron");
							stack2.stackTagCompound.setFloat("ntmNeutron", activation+(eRads/stack2.stackSize));
							
						//}
					}
				}
				for(int i2 = 0; i2 < player.inventory.armorInventory.length; i2++)
				{
					ItemStack stack2 = player.inventory.armorItemInSlot(i2);
					
					//only affect unstackables (e.g. tools and armor) so that the NBT tag's stack restrictions isn't noticeable
					if(stack2 != null) {					
							if(!stack2.hasTagCompound())
								stack2.stackTagCompound = new NBTTagCompound();
							float activation = stack2.stackTagCompound.getFloat("ntmNeutron");
							stack2.stackTagCompound.setFloat("ntmNeutron", activation+(eRads/stack2.stackSize));
					}
				}	
			}
		}
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.ticksExisted = nbt.getInteger("ticksExisted");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("ticksExisted", this.ticksExisted);
	}
	
	public static EntityNukeExplosionMK5 statFac(World world, int r, double x, double y, double z) {
		
		if(GeneralConfig.enableExtendedLogging && !world.isRemote)
			MainRegistry.logger.log(Level.INFO, "[NUKE] Initialized explosion at " + x + " / " + y + " / " + z + " with strength " + r + "!");
		
		if(r == 0)
			r = 25;
		
		r *= 2;
		
		EntityNukeExplosionMK5 mk5 = new EntityNukeExplosionMK5(world);
		mk5.strength = (int)(r);
		mk5.speed = (int)Math.ceil(100000 / mk5.strength);
		mk5.setPosition(x, y, z);
		mk5.length = mk5.strength / 2;
		return mk5;
	}
	
	public static EntityNukeExplosionMK5 statFacNoRad(World world, int r, double x, double y, double z) {
		
		EntityNukeExplosionMK5 mk5 = statFac(world, r, x, y ,z);
		mk5.fallout = false;
		return mk5;
	}
	
	public static EntityNukeExplosionMK5 statFacSalted(World world, int r, double x, double y, double z) {
		
		EntityNukeExplosionMK5 mk5 = statFac(world, r, x, y ,z);
		mk5.salted = true;
		return mk5;
	}
	
	public EntityNukeExplosionMK5 moreFallout(int fallout) {
		falloutAdd = fallout;
		return this;
	}
	
	public EntityNukeExplosionMK5 mute() {
		this.mute = true;
		return this;
	}
}
