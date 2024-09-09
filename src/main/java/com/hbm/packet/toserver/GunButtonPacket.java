package com.hbm.packet.toserver;

import com.hbm.items.weapon.ItemGunBase;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class GunButtonPacket implements IMessage {

	//true or false, whether or not the key is pressed
	boolean state;
	//0: [M1]
	//1: [M2]
	//2: [R]
	byte button;

	public GunButtonPacket() { }

	public GunButtonPacket(boolean m1, byte b) {
		state = m1;
		button = b;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		state = buf.readBoolean();
		button = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(state);
		buf.writeByte(button);
	}

	public static class Handler implements IMessageHandler<GunButtonPacket, IMessage> {

		@Override
		public IMessage onMessage(GunButtonPacket m, MessageContext ctx) {
			
			if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
				return null;
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			if(p.getHeldItem() != null && p.getHeldItem().getItem() instanceof ItemGunBase) {
				
				ItemGunBase item = (ItemGunBase)p.getHeldItem().getItem();
				
				switch(m.button) {
				case 0: ItemGunBase.setIsMouseDown(p.getHeldItem(), m.state);
						if(m.state)
							item.startAction(p.getHeldItem(), p.worldObj, p, true);
						else
							item.endAction(p.getHeldItem(), p.worldObj, p, true);
						break;
						
				case 1: ItemGunBase.setIsAltDown(p.getHeldItem(), m.state);
						if(m.state)
							item.startAction(p.getHeldItem(), p.worldObj, p, false);
						else
							item.endAction(p.getHeldItem(), p.worldObj, p, false);
						break;
						
				case 2: 
					if(item.canReload(p.getHeldItem(), p.worldObj, p)) {
						item.startReloadAction(p.getHeldItem(), p.worldObj, p);
					}
						break;
				}
			}
			
			return null;
		}
	}
}
