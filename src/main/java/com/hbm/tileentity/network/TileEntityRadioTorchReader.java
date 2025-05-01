package com.hbm.tileentity.network;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.BufferUtil;
import com.hbm.util.Compat;

import api.hbm.redstoneoverradio.IRORValueProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityRadioTorchReader extends TileEntityLoadedBase implements IControlReceiver {

	public String[] channels = new String[8];
	public String[] names = new String[8];
	public String[] prev = new String[8];
	public boolean polling = false;
	
	public TileEntityRadioTorchReader() {
		for(int i = 0; i < channels.length; i++) channels[i] = "";
		for(int i = 0; i < names.length; i++) names[i] = "";
		for(int i = 0; i < prev.length; i++) prev[i] = "";
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
			
			TileEntity tile = Compat.getTileStandard(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			
			if(tile instanceof IRORValueProvider) {
				IRORValueProvider prov = (IRORValueProvider) tile;
				
				for(int i = 0; i < 8; i++) {
					String channel = channels[i];
					String name = names[i];
					String previous = prev[i];

					if(channel == null || channel.isEmpty()) continue;
					if(name == null || name.isEmpty()) continue;
					
					String value = prov.provideRORValue(IRORValueProvider.PREFIX_VALUE + name);
					if(value == null) continue; //don't actually do this
					
					if(polling || !value.equals(previous)) {
						RTTYSystem.broadcast(worldObj, channel, value);
						this.prev[i] = value;
					}
				}
			}

			networkPackNT(50);
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		buf.writeBoolean(this.polling);
		for(int i = 0; i < channels.length; i++) BufferUtil.writeString(buf, channels[i]);
		for(int i = 0; i < names.length; i++) BufferUtil.writeString(buf, names[i]);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		this.polling = buf.readBoolean();
		for(int i = 0; i < channels.length; i++) channels[i] = BufferUtil.readString(buf);
		for(int i = 0; i < names.length; i++) names[i] = BufferUtil.readString(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.polling = nbt.getBoolean("p");
		for(int i = 0; i < channels.length; i++) channels[i] = nbt.getString("c" + i);
		for(int i = 0; i < names.length; i++) names[i] = nbt.getString("n" + i);
		for(int i = 0; i < prev.length; i++) prev[i] = nbt.getString("p" + i);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("p", polling);
		for(int i = 0; i < channels.length; i++) nbt.setString("c" + i, channels[i]);
		for(int i = 0; i < names.length; i++) nbt.setString("n" + i, names[i]);
		for(int i = 0; i < prev.length; i++) nbt.setString("p" + i, prev[i]);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("p")) this.polling = data.getBoolean("p");
		for(int i = 0; i < channels.length; i++) if(data.hasKey("c" + i)) channels[i] = data.getString("c" + i);
		for(int i = 0; i < names.length; i++) if(data.hasKey("n" + i)) names[i] = data.getString("n" + i);
		
		this.markDirty();
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return player.getDistance(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 16D;
	}
}
