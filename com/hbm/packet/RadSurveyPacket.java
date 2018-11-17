package com.hbm.packet;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.chunk.Chunk;

public class RadSurveyPacket implements IMessage {

	float[] rad;

	public RadSurveyPacket()
	{
		
	}

	public RadSurveyPacket(float[] rad)
	{
		this.rad = rad;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		rad = new float[9];
		
		for(int i = 0; i < 9; i++)
			rad[i] = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		for(int i = 0; i < 9; i++)
			buf.writeFloat(rad[i]);
	}

	public static class Handler implements IMessageHandler<RadSurveyPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(RadSurveyPacket m, MessageContext ctx) {
			try {
				
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				RadiationSavedData data = RadiationSavedData.getData(player.worldObj);
				data.jettisonData();

				Chunk[] chunks = new Chunk[9];

				chunks[0] = player.worldObj.getChunkFromBlockCoords((int)player.posX + 16, (int)player.posZ + 16);
				chunks[1] = player.worldObj.getChunkFromBlockCoords((int)player.posX, (int)player.posZ + 16);
				chunks[2] = player.worldObj.getChunkFromBlockCoords((int)player.posX - 16, (int)player.posZ + 16);
				chunks[3] = player.worldObj.getChunkFromBlockCoords((int)player.posX - 16, (int)player.posZ);
				chunks[4] = player.worldObj.getChunkFromBlockCoords((int)player.posX - 16, (int)player.posZ - 16);
				chunks[5] = player.worldObj.getChunkFromBlockCoords((int)player.posX, (int)player.posZ - 16);
				chunks[6] = player.worldObj.getChunkFromBlockCoords((int)player.posX + 16, (int)player.posZ - 16);
				chunks[7] = player.worldObj.getChunkFromBlockCoords((int)player.posX + 16, (int)player.posZ);
				chunks[8] = player.worldObj.getChunkFromBlockCoords((int)player.posX, (int)player.posZ);

				for(int i = 0; i < 9; i++)
					data.createEntry(chunks[i].xPosition, chunks[i].zPosition, m.rad[i]);
				
			} catch (Exception x) { }
			return null;
		}
	}
}
