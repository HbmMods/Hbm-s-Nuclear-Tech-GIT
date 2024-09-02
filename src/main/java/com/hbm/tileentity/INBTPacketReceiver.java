package com.hbm.tileentity;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.NBTPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public interface INBTPacketReceiver {
	
	public void networkUnpack(NBTTagCompound nbt);
	
	public static void networkPack(TileEntity that, NBTTagCompound data, int range) {
		PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(data, that.xCoord, that.yCoord, that.zCoord), new TargetPoint(that.getWorldObj().provider.dimensionId, that.xCoord, that.yCoord, that.zCoord, range));
	}
}
