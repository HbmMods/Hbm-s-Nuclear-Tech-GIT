package com.hbm.packet;

import java.io.IOException;

import com.hbm.calc.EasyLocation;
import com.hbm.packet.NBTPacket.Handler;

import api.hbm.internet.IDataConnector;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class NBTInternetDataPacket extends NBTPacket
{
	public NBTInternetDataPacket(NBTTagCompound nbt, int x, int y, int z)
	{
		super(nbt, x, y, z);
	}
	
	public NBTInternetDataPacket(NBTTagCompound nbt, EasyLocation loc)
	{
		super(nbt, (int) loc.posX, (int) loc.posY, (int) loc.posZ);
	}
	
	public NBTInternetDataPacket(NBTTagCompound nbt, TileEntity te)
	{
		this(nbt, new EasyLocation(te));
	}

	public static class Handler implements IMessageHandler<NBTInternetDataPacket, IMessage>
	{
		@Override
		public IMessage onMessage(NBTInternetDataPacket m, MessageContext ctx)
		{
			WorldClient world = Minecraft.getMinecraft().theWorld;
			
			if (world == null)
				return null;
			TileEntity te = world.getTileEntity(m.x, m.y, m.z);
			
			try
			{
				NBTTagCompound nbt = m.buffer.readNBTTagCompoundFromBuffer();
				if (nbt != null && te != null && te instanceof IDataConnector)
					((IDataConnector)te).recieveData(nbt);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}
	}
}
