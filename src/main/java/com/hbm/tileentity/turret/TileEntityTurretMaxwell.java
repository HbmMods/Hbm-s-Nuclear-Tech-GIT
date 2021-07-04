package com.hbm.tileentity.turret;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.EntityDamageUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;

public class TileEntityTurretMaxwell extends TileEntityTurretBaseNT {

	@Override
	public String getName() {
		return "container.turretMaxwell";
	}

	@Override
	protected List<Integer> getAmmoList() {
		return null;
	}
	
	@Override
	public double getAcceptableInaccuracy() {
		return 2;
	}

	@Override
	public double getDecetorGrace() {
		return 5D;
	}

	@Override
	public double getTurretYawSpeed() {
		return 9D;
	}

	@Override
	public double getTurretPitchSpeed() {
		return 6D;
	}

	@Override
	public double getTurretElevation() {
		return 40D;
	}

	@Override
	public double getTurretDepression() {
		return 35D;
	}

	@Override
	public double getDecetorRange() {
		return 64D + this.greenLevel * 3;
	}

	@Override
	public long getMaxPower() {
		return 10000000;
	}

	@Override
	public long getConsumption() {
		return 10000 - this.blueLevel * 300;
	}

	@Override
	public double getBarrelLength() {
		return 2.125D;
	}

	@Override
	public double getHeightOffset() {
		return 2D;
	}
	
	public int beam;
	public double lastDist;
	
	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) {
			
			if(this.tPos != null) {
				Vec3 pos = this.getTurretPos();
				double length = Vec3.createVectorHelper(tPos.xCoord - pos.xCoord, tPos.yCoord - pos.yCoord, tPos.zCoord - pos.zCoord).lengthVector();
				this.lastDist = length;
			}
			
			if(beam > 0)
				beam--;
		} else {
			
			if(checkDelay <= 0) {
				checkDelay = 20;
				
				this.redLevel = 0;
				this.greenLevel = 0;
				this.blueLevel = 0;
				this.blackLevel = 0;
				this.pinkLevel = 0;
				
				for(int i = 1; i < 10; i++) {
					if(slots[i] != null) {
						Item item = slots[i].getItem();
						
						if(item == ModItems.upgrade_speed_1) redLevel += 1;
						if(item == ModItems.upgrade_speed_2) redLevel += 2;
						if(item == ModItems.upgrade_speed_3) redLevel += 3;
						if(item == ModItems.upgrade_effect_1) greenLevel += 1;
						if(item == ModItems.upgrade_effect_2) greenLevel += 2;
						if(item == ModItems.upgrade_effect_3) greenLevel += 3;
						if(item == ModItems.upgrade_power_1) blueLevel += 1;
						if(item == ModItems.upgrade_power_2) blueLevel += 2;
						if(item == ModItems.upgrade_power_3) blueLevel += 3;
						if(item == ModItems.upgrade_afterburn_1) pinkLevel += 1;
						if(item == ModItems.upgrade_afterburn_2) pinkLevel += 2;
						if(item == ModItems.upgrade_afterburn_3) pinkLevel += 3;
						if(item == ModItems.upgrade_overdrive_1) blackLevel += 1;
						if(item == ModItems.upgrade_overdrive_2) blackLevel += 2;
						if(item == ModItems.upgrade_overdrive_3) blackLevel += 3;
					}
				}
			}
			
			checkDelay--;
		}
		
		super.updateEntity();
	}
	
	int redLevel;
	int greenLevel;
	int blueLevel;
	int blackLevel;
	int pinkLevel;
	
	int checkDelay;

	@Override
	public void updateFiringTick() {
		
		long demand = this.getConsumption() * 10;
		
		if(this.target != null && this.getPower() >= demand) {

			EntityDamageUtil.attackEntityFromIgnoreIFrame(this.target, ModDamageSource.shrapnel, (this.blackLevel * 10 + this.redLevel + 1F) * 0.25F);
			
			if(pinkLevel > 0)
				this.target.setFire(this.pinkLevel * 3);
			
			/*for(int i = 1; i <= 10; i *= 10) {
				
				if(EntityDamageUtil.getLastDamage(this.target) < i * 0.5F)
					EntityDamageUtil.attackEntityFromIgnoreIFrame(this.target, ModDamageSource.shrapnel, i * 10F);
				else
					break;
			}*/
			
			if(!this.target.isEntityAlive()) {
				NBTTagCompound vdat = new NBTTagCompound();
				vdat.setString("type", "giblets");
				vdat.setInteger("ent", this.target.getEntityId());
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(vdat, this.target.posX, this.target.posY + this.target.height * 0.5, this.target.posZ), new TargetPoint(this.target.dimension, this.target.posX, this.target.posY + this.target.height * 0.5, this.target.posZ, 150));
				
				worldObj.playSoundEffect(this.target.posX, this.target.posY, this.target.posZ, "mob.zombie.woodbreak", 2.0F, 0.95F + worldObj.rand.nextFloat() * 0.2F);
			}
			
			this.power -= demand;
			
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("shot", true);
			this.networkPack(data, 250);
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		if(nbt.hasKey("shot"))
			beam = 5;
		else
			super.networkUnpack(nbt);
	}
}
