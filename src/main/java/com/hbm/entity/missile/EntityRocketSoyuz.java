package com.hbm.entity.missile;

import java.util.List;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ISatChip;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.satellites.XSatelliteRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class EntityRocketSoyuz extends EntityRocketBase {
	
	public int mode;
	public int targetX;
	public int targetZ;
	boolean memed = false;

	public EntityRocketSoyuz(World world) {
		super(world, 18);
		this.setSize(5.0F, 50.0F);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!worldObj.isRemote) {
			
			List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(posX - 5, posY - 15, posZ - 5, posX + 5, posY, posZ + 5));
			
			for(Entity e : list) {
				e.setFire(15);
				e.attackEntityFrom(ModDamageSource.exhaust, 100.0F);
				
				if(e instanceof EntityPlayer) {
					if(!memed) {
						memed = true;
						worldObj.playSoundEffect(posX, posY, posZ, "hbm:alarm.soyuzed", 100, 1.0F);
					}
					
					((EntityPlayer)e).triggerAchievement(MainRegistry.achSoyuz);
				}
			}
		}
		
		if(worldObj.isRemote) {
			spawnExhaust(posX, posY, posZ);
			spawnExhaust(posX + 2.75, posY, posZ);
			spawnExhaust(posX - 2.75, posY, posZ);
			spawnExhaust(posX, posY, posZ + 2.75);
			spawnExhaust(posX, posY, posZ - 2.75);
		}
	}
	
	private void spawnExhaust(double x, double y, double z) {

		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "exhaust");
		data.setString("mode", "soyuz");
		data.setInteger("count", 1);
		data.setDouble("width", worldObj.rand.nextDouble() * 0.25 - 0.5);
		data.setDouble("posX", x);
		data.setDouble("posY", y);
		data.setDouble("posZ", z);
		
		MainRegistry.proxy.effectNT(data);
	}
	
	@Override
	protected void deployPayload() {

		if(mode == 0 && payload != null) {

			if(payload[0] != null) {
				
				ItemStack load = payload[0];
				
				if(load.getItem() == ModItems.flame_pony) {
					ExplosionLarge.spawnTracers(worldObj, posX, posY, posZ, 25);
					for(Object p : worldObj.playerEntities)
						((EntityPlayer)p).triggerAchievement(MainRegistry.achSpace);
				}
				
				if(load.getItem() == ModItems.sat_foeq) {
					for(Object p : worldObj.playerEntities)
						((EntityPlayer)p).triggerAchievement(MainRegistry.achFOEQ);
				}
				
				if(load.getItem() instanceof ISatChip) {
					int freq = ISatChip.getFreqS(load);
					XSatelliteRegistry.orbit(worldObj, load, freq, posX, posY, posZ);
				}
			}
		}
		
		if(mode == 1) {
			
			EntitySoyuzCapsule capsule = new EntitySoyuzCapsule(worldObj);
			capsule.payload = this.payload;
			capsule.soyuz = this.getSkin();
			capsule.setPosition(targetX + 0.5, 600, targetZ + 0.5);
			
			IChunkProvider provider = worldObj.getChunkProvider();
			provider.loadChunk(targetX >> 4, targetZ >> 4);
			
			worldObj.spawnEntityInWorld(capsule);
		}
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(8, 0);
	}
	
	public void setSkin(int i) {
		this.dataWatcher.updateObject(8, i);
	}
	
	public int getSkin() {
		return this.dataWatcher.getWatchableObjectInt(8);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		this.setSkin(nbt.getInteger("skin"));
		targetX = nbt.getInteger("targetX");
		targetZ = nbt.getInteger("targetZ");
		mode = nbt.getInteger("mode");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);

		nbt.setInteger("skin", this.getSkin());
		nbt.setInteger("targetX", targetX);
		nbt.setInteger("targetZ", targetZ);
		nbt.setInteger("mode", mode);
	}
}
