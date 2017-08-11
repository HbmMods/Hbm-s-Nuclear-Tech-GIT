package com.hbm.tileentity;

import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TETurretPacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public abstract class TileEntityTurretBase extends TileEntity {
	
	public double rotationYaw;
	public double rotationPitch;
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAll(new TETurretPacket(xCoord, yCoord, zCoord, rotationYaw, rotationPitch));
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

}
