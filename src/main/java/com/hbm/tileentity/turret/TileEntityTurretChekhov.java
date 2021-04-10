package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityTurretChekhov extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList();
	
	//because cramming it into the ArrayList's constructor with nested curly brackets and all that turned out to be not as pretty
	//also having a floaty `static` like this looks fun
	//idk if it's just me though
	static {
		configs.add(BulletConfigSyncingUtil.BMG50_NORMAL);
		configs.add(BulletConfigSyncingUtil.BMG50_INCENDIARY);
		configs.add(BulletConfigSyncingUtil.BMG50_EXPLOSIVE);
		configs.add(BulletConfigSyncingUtil.BMG50_AP);
		configs.add(BulletConfigSyncingUtil.BMG50_DU);
		configs.add(BulletConfigSyncingUtil.BMG50_STAR);
		configs.add(BulletConfigSyncingUtil.BMG50_PHOSPHORUS);
		configs.add(BulletConfigSyncingUtil.BMG50_SLEEK);
		configs.add(BulletConfigSyncingUtil.CHL_BMG50);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return configs;
	}

	@Override
	public String getName() {
		return "container.turretChekhov";
	}

	@Override
	public double getTurretElevation() {
		return 45D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}

	@Override
	public double getBarrelLength() {
		return 3.5D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 15;
	}
	
	int timer;

	@Override
	public void updateFiringTick() {
		
		timer++;
		
		if(timer > 20 && timer % getDelay() == 0) {
			
			BulletConfiguration conf = this.getFirstConfigLoaded();
			
			if(conf != null) {
				this.spawnBullet(conf);
				this.conusmeAmmo(conf.ammo);
				this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.chekhov_fire", 2.0F, 1.0F);
				
				Vec3 pos = this.getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 1.5F);
				data.setByte("count", (byte)1);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			}
		}
	}
	
	public int getDelay() {
		return 2;
	}
	
	public float spin;
	public float lastSpin;
	private float accel;
	private boolean manual;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(worldObj.isRemote) {
			
			if(this.tPos != null || manual) {
				this.accel = Math.min(45F, this.accel += 2);
			} else {
				this.accel = Math.max(0F, this.accel -= 2);
			}
			
			manual = false;
			
			this.lastSpin = this.spin;
			this.spin += this.accel;
			
			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}
		} else {
			
			if(this.tPos == null && !manual) {
				
				this.timer--;
				
				if(timer > 20)
					timer = 20;
				
				if(timer < 0)
					timer = 0;
			}
		}
	}
	
	@Override
	public void manualSetup() {
		
		manual = true;
	}
}
