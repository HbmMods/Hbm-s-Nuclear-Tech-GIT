package com.hbm.packet.toclient;

import com.hbm.interfaces.IAnimatedDoor;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEDoorAnimationPacket implements IMessage {

	public int x, y, z;
	public byte state;
	public byte skinIndex;
	public byte texture;
	
	public TEDoorAnimationPacket() {
	}
	
	public TEDoorAnimationPacket(int x, int y, int z, byte state) {
		this(x, y, z, state, (byte) 0, (byte) -1);
	}
	
	public TEDoorAnimationPacket(int x, int y, int z, byte state, byte skinIndex, byte tex) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.state = state;
		this.skinIndex = skinIndex;
		this.texture = tex;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		state = buf.readByte();
		skinIndex = buf.readByte();
		if(buf.readableBytes() == 1){
			texture = buf.readByte();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeByte(state);
		buf.writeByte(skinIndex);
		if(texture != -1){
			buf.writeByte(texture);
		}
	}
	
	public static class Handler implements IMessageHandler<TEDoorAnimationPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TEDoorAnimationPacket m, MessageContext ctx) {
			
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
			if(te instanceof IAnimatedDoor){
				((IAnimatedDoor) te).handleNewState(m.state);
				((IAnimatedDoor) te).setSkinIndex(m.skinIndex);
				((IAnimatedDoor) te).setTextureState(m.texture);
			}
			
			return null;
		}
	}
}
