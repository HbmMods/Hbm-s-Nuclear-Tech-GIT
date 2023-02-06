package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.items.ModItems;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityTurretRichard extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList();
	
	static {
		configs.add(BulletConfigSyncingUtil.ROCKET_NORMAL);
		configs.add(BulletConfigSyncingUtil.ROCKET_HE);
		configs.add(BulletConfigSyncingUtil.ROCKET_INCENDIARY);
		configs.add(BulletConfigSyncingUtil.ROCKET_SHRAPNEL);
		configs.add(BulletConfigSyncingUtil.ROCKET_EMP);
		configs.add(BulletConfigSyncingUtil.ROCKET_GLARE);
		configs.add(BulletConfigSyncingUtil.ROCKET_SLEEK);
		configs.add(BulletConfigSyncingUtil.ROCKET_NUKE);
		configs.add(BulletConfigSyncingUtil.ROCKET_CHAINSAW);
		configs.add(BulletConfigSyncingUtil.ROCKET_TOXIC);
		configs.add(BulletConfigSyncingUtil.ROCKET_PHOSPHORUS);
		configs.add(BulletConfigSyncingUtil.ROCKET_CANISTER);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return configs;
	}

	@Override
	public String getName() {
		return "container.turretRichard";
	}

	@Override
	public double getTurretDepression() {
		return 25D;
	}

	@Override
	public double getTurretElevation() {
		return 25D;
	}

	@Override
	public double getBarrelLength() {
		return 1.25D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}
	
	@Override
	public double getDecetorGrace() {
		return 8D;
	}

	@Override
	public double getDecetorRange() {
		return 64D;
	}
	
	int timer;
	public int loaded;
	int reload;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			if(reload > 0) {
				reload--;
				
				if(reload == 0)
					this.loaded = 17;
			}
			
			if(loaded <= 0 && reload <= 0 && this.getFirstConfigLoaded() != null) {
				reload = 100;
			}
			
			if(this.getFirstConfigLoaded() == null) {
				this.loaded = 0;
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("loaded", this.loaded);
			this.networkPack(data, 250);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		if(nbt.hasKey("loaded"))
			this.loaded = nbt.getInteger("loaded");
		else
			super.networkUnpack(nbt);
	}

	@Override
	public void updateFiringTick() {
		
		if(reload > 0)
			return;
		
		timer++;
		
		if(timer > 0 && timer % 10 == 0) {
			
			BulletConfiguration conf = this.getFirstConfigLoaded();
			
			if(conf != null) {
				this.spawnBullet(conf);
				this.conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.richard_fire", 2.0F, 1.0F);
				this.loaded--;
				
				if(conf.ammo == ModItems.ammo_rocket_nuclear)
					timer = -50;
				
			} else {
				this.loaded = 0;
			}
		}
	}

	@Override
	public void spawnBullet(BulletConfiguration bullet) {
		
		Vec3 pos = this.getTurretPos();
		Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
		vec.rotateAroundZ((float) -this.rotationPitch);
		vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
		
		EntityBulletBase proj = new EntityBulletBase(worldObj, BulletConfigSyncingUtil.getKey(bullet));
		proj.setPositionAndRotation(pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord, 0.0F, 0.0F);
		
		proj.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, bullet.velocity * 0.75F, bullet.spread);
		worldObj.spawnEntityInWorld(proj);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.loaded = nbt.getInteger("loaded");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("loaded", this.loaded);
	}
}
