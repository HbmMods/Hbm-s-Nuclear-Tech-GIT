package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.config.WeaponConfig;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.EntityDamageUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;

public class TileEntityTurretHoward extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList();
	
	static {
		configs.add(BulletConfigSyncingUtil.DGK_NORMAL);
	}
	
	@Override
	protected List<Integer> getAmmoList() {
		return configs;
	}

	@Override
	public String getName() {
		return "container.turretHoward";
	}

	@Override
	public double getHeightOffset() {
		return 2.25D;
	}

	@Override
	public double getDecetorGrace() {
		return 3D;
	}

	@Override
	public double getTurretYawSpeed() {
		return 12D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 8D;
	}

	@Override
	public double getTurretElevation() {
		return 90D;
	}

	@Override
	public double getTurretDepression() {
		return 50D;
	}

	@Override
	public double getDecetorRange() {
		return 250D;
	}

	@Override
	public double getBarrelLength() {
		return 3.25D;
	}

	@Override
	public long getMaxPower() {
		return 50000;
	}

	@Override
	public long getConsumption() {
		return 500;
	}

	int loaded;
	int timer;
	public float spin;
	public float lastSpin;
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) {
			
			this.lastSpin = this.spin;
			
			if(this.tPos != null) {
				this.spin += 45;
			}
			
			if(this.spin >= 360F) {
				this.spin -= 360F;
				this.lastSpin -= 360F;
			}
		} else {
			
			if(loaded <= 0) {
				BulletConfiguration conf = this.getFirstConfigLoaded();
				
				if(conf != null) {
					this.conusmeAmmo(conf.ammo);
					this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.howard_reload", 4.0F, 1F);
					loaded = 200;
				}
			}
		}
		
		super.updateEntity();
	}

	@Override
	public void updateFiringTick() {
		
		timer++;
		
		if(loaded > 0 && this.tPos != null) {
			
			this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.howard_fire", 4.0F, 0.9F + worldObj.rand.nextFloat() * 0.3F);
			this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.howard_fire", 4.0F, 1F + worldObj.rand.nextFloat() * 0.3F);
			
			if(timer % 2 == 0) {
				loaded--;
				
				if(worldObj.rand.nextInt(100) + 1 <= WeaponConfig.ciwsHitrate)
					EntityDamageUtil.attackEntityFromIgnoreIFrame(this.target, ModDamageSource.shrapnel, 2F + worldObj.rand.nextInt(2));
					
				Vec3 pos = this.getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
				
				Vec3 hOff = Vec3.createVectorHelper(0, 0.25, 0);
				hOff.rotateAroundZ((float) -this.rotationPitch);
				hOff.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
					
				for(int i = 0; i < 2; i++) {
					
					if(i == 1) {
						hOff.xCoord *= -1;
						hOff.yCoord *= -1;
						hOff.zCoord *= -1;
					}
					
					NBTTagCompound data = new NBTTagCompound();
					data.setString("type", "vanillaExt");
					data.setString("mode", "largeexplode");
					data.setFloat("size", 1.5F);
					data.setByte("count", (byte)1);
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord + hOff.xCoord, pos.yCoord + vec.yCoord + hOff.yCoord, pos.zCoord + vec.zCoord + hOff.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
				}
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.loaded = nbt.getInteger("loaded");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("loaded", loaded);
	}
}
