package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.turret.TurretBase;
import com.hbm.entity.logic.EntityBomber;
import com.hbm.entity.missile.EntityMIRV;
import com.hbm.entity.missile.EntityMissileBaseAdvanced;
import com.hbm.entity.missile.EntityMissileCustom;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TETurretPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public abstract class TileEntityTurretBase extends TileEntity {
	
	public double rotationYaw;
	public double rotationPitch;
	public boolean isAI = false;
	public List<String> players = new ArrayList();
	public int use;
	public int ammo = 0;
	
	@Override
	public void updateEntity() {
		
		if(isAI) {
			
			Object[] iter = worldObj.loadedEntityList.toArray();
			double radius = 1000;
			if(this instanceof TileEntityTurretFlamer)
				radius /= 2;
			if(this instanceof TileEntityTurretSpitfire)
				radius *= 3;
			if(this instanceof TileEntityTurretCIWS)
				radius *= 250;
			Entity target = null;
			for (int i = 0; i < iter.length; i++)
			{
				Entity e = (Entity) iter[i];
				if (isInSight(e))
				{
					double distance = e.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
					if (distance < radius)
					{
						radius = distance;
						target = e;
					}
				}
			}

			if(target != null) {

				Vec3 turret = Vec3.createVectorHelper(target.posX - (xCoord + 0.5), target.posY + target.getEyeHeight() - (yCoord + 1), target.posZ - (zCoord + 0.5));
				
				if(this instanceof TileEntityTurretCIWS || this instanceof TileEntityTurretSpitfire || this instanceof TileEntityTurretCheapo) {
					turret = Vec3.createVectorHelper(target.posX - (xCoord + 0.5), target.posY + target.getEyeHeight() - (yCoord + 1.5), target.posZ - (zCoord + 0.5));
				}
				
				rotationPitch = -Math.asin(turret.yCoord/turret.lengthVector()) * 180 / Math.PI;
				rotationYaw = -Math.atan2(turret.xCoord, turret.zCoord) * 180 / Math.PI;
				
				if(rotationPitch < -60)
					rotationPitch = -60;
				if(rotationPitch > 30)
					rotationPitch = 30;
				
				if(this instanceof TileEntityTurretCheapo) {
					
					if(rotationPitch < -30)
						rotationPitch = -30;
					if(rotationPitch > 15)
						rotationPitch = 15;
				}
				
				if(worldObj.getBlock(xCoord, yCoord, zCoord) instanceof TurretBase && ammo > 0) {
					if(((TurretBase)worldObj.getBlock(xCoord, yCoord, zCoord)).executeHoldAction(worldObj, use, rotationYaw, rotationPitch, xCoord, yCoord, zCoord))
						ammo--;
				}
				
				use++;
				
			} else {
				use = 0;
			}
		}
		
		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new TETurretPacket(xCoord, yCoord, zCoord, rotationYaw, rotationPitch), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
	}
	
	private boolean isInSight(Entity e) {
		if(!(e instanceof EntityLivingBase) && !(e instanceof EntityMissileBaseAdvanced) && !(e instanceof EntityBomber) && !(e instanceof EntityMissileCustom)&& !(e instanceof EntityMIRV))
			return false;
		
		if(this instanceof TileEntityTurretCIWS && !(e instanceof EntityMissileBaseAdvanced) && !(e instanceof EntityBomber) && !(e instanceof EntityMissileCustom)&& !(e instanceof EntityMIRV))
			return false;
		
		if(e instanceof EntityPlayer && players.contains((((EntityPlayer)e).getDisplayName())))
			return false;
		
		Vec3 turret;
		if(this instanceof TileEntityTurretSpitfire || this instanceof TileEntityTurretCIWS || this instanceof TileEntityTurretCheapo)
			turret = Vec3.createVectorHelper(xCoord + 0.5, yCoord + 1.5, zCoord + 0.5);
		else
			turret = Vec3.createVectorHelper(xCoord + 0.5, yCoord + 1, zCoord + 0.5);
		
		Vec3 entity = Vec3.createVectorHelper(e.posX, e.posY + e.getEyeHeight(), e.posZ);
		Vec3 side = Vec3.createVectorHelper(entity.xCoord - turret.xCoord, entity.yCoord - turret.yCoord, entity.zCoord - turret.zCoord);
		side = side.normalize();
		
		turret.xCoord += side.xCoord;
		turret.yCoord += side.yCoord;
		turret.zCoord += side.zCoord;
		
		if(this instanceof TileEntityTurretTau)
			return true;
		
		return !Library.isObstructed(worldObj, turret.xCoord, turret.yCoord, turret.zCoord, entity.xCoord, entity.yCoord, entity.zCoord);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		rotationYaw = nbt.getDouble("yaw");
		rotationPitch = nbt.getDouble("pitch");
		isAI = nbt.getBoolean("AI");
		ammo = nbt.getInteger("ammo");
		
		int playercount = nbt.getInteger("playercount");
		
		for(int i = 0; i < playercount; i++) {
			players.add(nbt.getString("player_" + i));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("yaw", rotationYaw);
		nbt.setDouble("pitch", rotationPitch);
		nbt.setBoolean("AI", isAI);
		nbt.setInteger("ammo", ammo);
		
		nbt.setInteger("playercount", players.size());
		
		for(int i = 0; i < players.size(); i++) {
			nbt.setString("player_" + i, players.get(i));
		}
	}

}
