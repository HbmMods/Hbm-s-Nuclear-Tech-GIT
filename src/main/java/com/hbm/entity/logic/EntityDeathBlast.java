package com.hbm.entity.logic;

import com.hbm.entity.projectile.EntityBulletBaseNT;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityDeathBlast extends Entity {
	
	public static final int maxAge = 60;

	public EntityDeathBlast(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) { }
	
	@Override
	public void onUpdate() {
		
		if(this.ticksExisted >= maxAge && !worldObj.isRemote) {
			this.setDead();
			
			worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFacNoRad(worldObj, 40, posX, posY, posZ));
			
			int count = 100;
			for(int i = 0; i < count; i++) {
				
				Vec3 vec = Vec3.createVectorHelper(0.2, 0, 0);
				vec.rotateAroundY((float)(2 * Math.PI * i / (float)count));
				
				EntityBulletBaseNT laser = new EntityBulletBaseNT(worldObj, BulletConfigSyncingUtil.MASKMAN_BOLT);
				laser.setPosition(posX, posY + 2, posZ);
				laser.motionX = vec.xCoord;
				laser.motionZ = vec.zCoord;
				laser.motionY = -0.01;
				worldObj.spawnEntityInWorld(laser);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "muke");
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 250));
			worldObj.playSoundEffect(posX, posY, posZ, "hbm:weapon.mukeExplosion", 25.0F, 0.9F);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 25000;
	}
}
