package com.hbm.packet;

import com.hbm.inventory.SlotLayer;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;

public class GuiLayerPacket implements IMessage {

	private int layer;

	public GuiLayerPacket() {}

	public GuiLayerPacket(int layer) {
		this.layer = layer;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		layer = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeByte(layer);
	}

	public static class Handler implements IMessageHandler<GuiLayerPacket, IMessage> {

		@Override
		public IMessage onMessage(GuiLayerPacket message, MessageContext ctx) {
			if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
				return null;

			EntityPlayerMP player = ctx.getServerHandler().playerEntity;
			if(player.openContainer != null) {
				for(int i = 0; i < player.openContainer.inventorySlots.size(); i++) {
					Slot slot = (Slot) player.openContainer.inventorySlots.get(i);
					if(slot instanceof SlotLayer) {
						((SlotLayer) slot).setLayer(message.layer);
					}
				}
			}

			return null;
		}

	}
	
}
