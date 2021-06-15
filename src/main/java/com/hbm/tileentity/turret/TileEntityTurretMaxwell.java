package com.hbm.tileentity.turret;

import java.util.List;

import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.EntityDamageUtil;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
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
		return 128D;
	}

	@Override
	public long getMaxPower() {
		return 10000000;
	}

	@Override
	public long getConsumption() {
		return 5000;
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
		}
		
		super.updateEntity();
	}

	@Override
	public void updateFiringTick() {
		
		long demand = this.getConsumption() * 10;
		
		if(this.target != null && this.getPower() >= demand) {

			EntityDamageUtil.attackEntityFromIgnoreIFrame(this.target, ModDamageSource.shrapnel, 1F);
			
			for(int i = 1; i <= 10; i *= 10) {
				
				if(EntityDamageUtil.getLastDamage(this.target) < i * 0.5F)
					EntityDamageUtil.attackEntityFromIgnoreIFrame(this.target, ModDamageSource.shrapnel, i * 10F);
				else
					break;
			}
			
			if(!this.target.isEntityAlive()) {
				float health = this.target instanceof EntityLivingBase ? ((EntityLivingBase)this.target).getMaxHealth() : 20F;
				int count = Math.min((int)Math.ceil(health / 3D), 250);
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaburst");
				data.setInteger("count", count * 4);
				data.setDouble("motion", 0.1D);
				data.setString("mode", "blockdust");
				data.setInteger("block", Block.getIdFromBlock(Blocks.redstone_block));
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, this.target.posX, this.target.posY + this.target.height * 0.5, this.target.posZ), new TargetPoint(this.target.dimension, this.target.posX, this.target.posY + this.target.height * 0.5, this.target.posZ, 50));

				NBTTagCompound vdat = new NBTTagCompound();
				vdat.setString("type", "vanish");
				vdat.setInteger("ent", this.target.getEntityId());
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(vdat, this.target.posX, this.target.posY + this.target.height * 0.5, this.target.posZ), new TargetPoint(this.target.dimension, this.target.posX, this.target.posY + this.target.height * 0.5, this.target.posZ, 50));
				
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
