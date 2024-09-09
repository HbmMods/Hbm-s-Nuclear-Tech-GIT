package com.hbm.tileentity;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.BufPacket;
import com.hbm.packet.toclient.NBTPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidTank;

public abstract class TileEntityTickingBase extends TileEntityLoadedBase implements INBTPacketReceiver, IBufPacketReceiver {
	
	public TileEntityTickingBase() { }
	
	public abstract String getInventoryName();
	
	public int getGaugeScaled(int i, FluidTank tank) {
		return tank.getFluidAmount() * i / tank.getCapacity();
	}

	//abstracting this method forces child classes to implement it
	//so i don't have to remember the fucking method name
	//was it update? onUpdate? updateTile? did it have any args?
	//shit i don't know man
	@Override
	public abstract void updateEntity();
	
	@Deprecated public void networkPack(NBTTagCompound nbt, int range) {

		if(!worldObj.isRemote)
			PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(nbt, xCoord, yCoord, zCoord), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}
	
	@Deprecated public void networkUnpack(NBTTagCompound nbt) { }
	
	@Deprecated
	public void handleButtonPacket(int value, int meta) { }
	
	public void networkPackNT(int range) {
		if(!worldObj.isRemote) PacketDispatcher.wrapper.sendToAllAround(new BufPacket(xCoord, yCoord, zCoord, this), new TargetPoint(this.worldObj.provider.dimensionId, xCoord, yCoord, zCoord, range));
	}

	@Override public void serialize(ByteBuf buf) {
		buf.writeBoolean(muffled);
	}
	
	@Override public void deserialize(ByteBuf buf) {
		this.muffled = buf.readBoolean();
	}
}
