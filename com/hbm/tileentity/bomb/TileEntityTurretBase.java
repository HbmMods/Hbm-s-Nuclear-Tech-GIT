package com.hbm.tileentity.bomb;

import com.hbm.blocks.bomb.TurretBase;
import com.hbm.entity.missile.EntityMissileBaseAdvanced;
import com.hbm.lib.Library;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TETurretPacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public abstract class TileEntityTurretBase extends TileEntity {
	
	public double rotationYaw;
	public double rotationPitch;
	public boolean isAI = false;
	public String uuid = "none";
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
				rotationPitch = -Math.asin(turret.yCoord/turret.lengthVector()) * 180 / Math.PI;
				rotationYaw = -Math.atan2(turret.xCoord, turret.zCoord) * 180 / Math.PI;
				
				if(rotationPitch < -60)
					rotationPitch = -60;
				if(rotationPitch > 30)
					rotationPitch = 30;
				
				use++;

				if(worldObj.getBlock(xCoord, yCoord, zCoord) instanceof TurretBase && ammo > 0) {
					if(((TurretBase)worldObj.getBlock(xCoord, yCoord, zCoord)).executeHoldAction(worldObj, use, rotationYaw, rotationPitch, xCoord, yCoord, zCoord))
						ammo--;
				}
				
			} else {
				use = 0;
			}
		}
		
		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAll(new TETurretPacket(xCoord, yCoord, zCoord, rotationYaw, rotationPitch));
	}
	
	private boolean isInSight(Entity e) {
		if(!(e instanceof EntityLivingBase) && !(e instanceof EntityMissileBaseAdvanced))
			return false;
		
		if(e instanceof EntityPlayer && ((EntityPlayer)e).getUniqueID().toString().equals(uuid))
			return false;
		
		Vec3 turret;
		if(this instanceof TileEntityTurretSpitfire)
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
		uuid = nbt.getString("player");
		ammo = nbt.getInteger("ammo");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setDouble("yaw", rotationYaw);
		nbt.setDouble("pitch", rotationPitch);
		nbt.setBoolean("AI", isAI);
		nbt.setString("player", uuid);
		nbt.setInteger("ammo", ammo);
	}

}
