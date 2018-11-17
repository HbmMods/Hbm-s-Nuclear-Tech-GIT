package com.hbm.packet;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.saveddata.RadEntitySavedData;
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

	float rad;

	public RadSurveyPacket()
	{
		
	}

	public RadSurveyPacket(float rad)
	{
		this.rad = rad;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		
		rad = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		
		buf.writeFloat(rad);
	}

	public static class Handler implements IMessageHandler<RadSurveyPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(RadSurveyPacket m, MessageContext ctx) {
			try {
				
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				RadEntitySavedData data = RadEntitySavedData.getData(player.worldObj);
				
				data.setRadForEntity(player, m.rad);
				
			} catch (Exception x) { }
			return null;
		}
	}
}
