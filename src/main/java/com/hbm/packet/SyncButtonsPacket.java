package com.hbm.packet;

import com.hbm.items.ISyncButtons;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class SyncButtonsPacket implements IMessage {

	boolean state;
	int button;

	public SyncButtonsPacket() { }

	public SyncButtonsPacket(boolean s, int b) {
		state = s;
		button = b;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		state = buf.readBoolean();
		button = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(state);
		buf.writeInt(button);
	}

	public static class Handler implements IMessageHandler<SyncButtonsPacket, IMessage> {

		@Override
		public IMessage onMessage(SyncButtonsPacket m, MessageContext ctx) {
			
			if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
				return null;
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			if(p.getHeldItem() != null && p.getHeldItem().getItem() instanceof ISyncButtons) {
				
				ISyncButtons item = (ISyncButtons)p.getHeldItem().getItem();
				item.receiveMouse(p, p.getHeldItem(), m.button, m.state);
			}
			
			return null;
		}
	}
}
