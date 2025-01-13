package com.hbm.entity.logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.hbm.handler.threading.PacketThreading;
import org.apache.logging.log4j.Level;

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
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

@Spaghetti("why???")
public class EntityNukeExplosionMK3 extends EntityExplosionChunkloading {

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

		if(BombConfig.limitExplosionLifespan > 0 && System.currentTimeMillis() - time > BombConfig.limitExplosionLifespan * 1000) {
			this.clearChunkLoader();
			this.setDead();
		}

		if(this.waste) {
			exp = new ExplosionNukeAdvanced((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, this.destructionRange, this.coefficient, 0);
			exp.readFromNbt(nbt, "exp_");
			wst = new ExplosionNukeAdvanced((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, (int) (this.destructionRange * 1.8), this.coefficient, 2);
			wst.readFromNbt(nbt, "wst_");
			vap = new ExplosionNukeAdvanced((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, (int) (this.destructionRange * 2.5), this.coefficient, 1);
			vap.readFromNbt(nbt, "vap_");
		} else {

			if(extType == 0) {
				expl = new ExplosionFleija((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, this.destructionRange, this.coefficient, this.coefficient2);
				expl.readFromNbt(nbt, "expl_");
			}
			if(extType == 1) {
				sol = new ExplosionSolinium((int) this.posX, (int) this.posY, (int) this.posZ, this.worldObj, this.destructionRange, this.coefficient, this.coefficient2);
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

		if(!worldObj.isRemote) loadChunk((int) Math.floor(posX / 16D), (int) Math.floor(posZ / 16D));

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

		for(int i = 0; i < this.speed; i++) {
			if(waste) {
				flag = exp.update();
				wst.update();
				flag3 = vap.update();

				if(flag3) {
					this.clearChunkLoader();
					this.setDead();
				}
			} else {
				if(extType == 0) {
					if(expl.update()) {
						this.clearChunkLoader();
						this.setDead();
					}
				}
				if(extType == 1) {
					if(sol.update()) {
						this.clearChunkLoader();
						this.setDead();
					}
				}
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

	public static HashMap<ATEntry, Long> at = new HashMap();

	public static EntityNukeExplosionMK3 statFacFleija(World world, double x, double y, double z, int range) {

		EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(world);
		entity.posX = x;
		entity.posY = y;
		entity.posZ = z;
		entity.destructionRange = range;
		entity.speed = BombConfig.blastSpeed;
		entity.coefficient = 1.0F;
		entity.waste = false;

		Iterator<Entry<ATEntry, Long>> it = at.entrySet().iterator();

		while(it.hasNext()) {

			Entry<ATEntry, Long> next = it.next();
			if(next.getValue() < world.getTotalWorldTime()) {
				it.remove();
				continue;
			}

			ATEntry entry = next.getKey();
			if(entry.dim != world.provider.dimensionId)  continue;

			Vec3 vec = Vec3.createVectorHelper(x - entry.x, y - entry.y, z - entry.z);

			if(vec.lengthVector() < 300) {
				entity.setDead();

				/* just to make sure */
				if(!world.isRemote) {

					for(int i = 0; i < 2; i++) {
						double ix = i == 0 ? x : (entry.x + 0.5);
						double iy = i == 0 ? y : (entry.y + 0.5);
						double iz = i == 0 ? z : (entry.z + 0.5);

						world.playSoundEffect(ix, iy, iz, "hbm:entity.ufoBlast", 15.0F, 0.7F + world.rand.nextFloat() * 0.2F);

						NBTTagCompound data = new NBTTagCompound();
						data.setString("type", "plasmablast");
						data.setFloat("r", 0.0F);
						data.setFloat("g", 0.75F);
						data.setFloat("b", 1.0F);
						data.setFloat("scale", 7.5F);
						PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, ix, iy, iz), new TargetPoint(entry.dim, ix, iy, iz, 150));
					}
				}

				break;
			}
		}

		return entity;
	}

	public EntityNukeExplosionMK3 makeSol() {
		this.extType = 1;
		return this;
	}

	public static class ATEntry {
		public int dim;
		public int x;
		public int y;
		public int z;

		public ATEntry(int dim, int x, int y, int z) {
			this.dim = dim;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public int hashCode() {
			final int prime = 27644437;
			int result = 1;
			result = prime * result + dim;
			result = prime * result + x;
			result = prime * result + y;
			result = prime * result + z;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if(this == obj)
				return true;
			if(obj == null)
				return false;
			if(getClass() != obj.getClass())
				return false;
			ATEntry other = (ATEntry) obj;
			if(dim != other.dim)
				return false;
			if(x != other.x)
				return false;
			if(y != other.y)
				return false;
			if(z != other.z)
				return false;
			return true;
		}
	}
}
