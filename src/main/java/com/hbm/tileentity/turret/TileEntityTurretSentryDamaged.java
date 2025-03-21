package com.hbm.tileentity.turret;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.factory.XFactory9mm;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityTurretSentryDamaged extends TileEntityTurretSentry {

	@Override
	public boolean hasPower() { //does not need power
		return true;
	}

	@Override
	public boolean isOn() { //is always on
		return true;
	}

	@Override
	public double getTurretYawSpeed() {
		return 3D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 2D;
	}

	@Override
	public boolean hasThermalVision() {
		return false;
	}

	@Override
	public boolean entityAcceptableTarget(Entity e) { //will fire at any living entity

		if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
			return false;

		return e instanceof EntityLivingBase;
	}

	@Override
	public void updateFiringTick() {

		timer++;

		if(timer % 10 == 0) {

			BulletConfig conf = XFactory9mm.p9_fmj;

			if(conf != null) {

				Vec3 pos = this.getTurretPos();
				Vec3 vec = Vec3.createVectorHelper(0, 0, 0);
				Vec3 side = Vec3.createVectorHelper(0, 0, 0);

				this.cachedCasingConfig = conf.casing;

				if(shotSide) {
					this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.sentry_fire", 2.0F, 1.0F);
					this.spawnBullet(conf, 5F);

					vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
					vec.rotateAroundZ((float) -this.rotationPitch);
					vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));

					side = Vec3.createVectorHelper(0.125 * (shotSide ? 1 : -1), 0, 0);
					side.rotateAroundY((float) -(this.rotationYaw));

				} else {
					this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "hbm:turret.sentry_fire", 2.0F, 0.75F);
					if(usesCasings()) {
						if(this.casingDelay() == 0) {
							spawnCasing();
						} else {
							casingDelay = this.casingDelay();
						}
					}
				}

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 1F);
				data.setByte("count", (byte) 1);
				PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord + side.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord + side.zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));

				if(shotSide) {
					this.didJustShootLeft = true;
				} else {
					this.didJustShootRight = true;
				}
				shotSide = !shotSide;
			}
		}
	}
}
