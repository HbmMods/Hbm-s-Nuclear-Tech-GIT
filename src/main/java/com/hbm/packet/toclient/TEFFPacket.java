package com.hbm.packet.toclient;

import com.hbm.tileentity.machine.TileEntityForceField;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class TEFFPacket implements IMessage {

	int x;
	int y;
	int z;
	float rad;
	int health;
	int maxHealth;
	int power;
	boolean isOn;
	int color;
	int cooldown;

	public TEFFPacket() {

	}

	public TEFFPacket(int x, int y, int z, float rad, int health, int maxHealth, int power, boolean isOn, int color, int cooldown) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.rad = rad;
		this.health = health;
		this.maxHealth = maxHealth;
		this.power = power;
		this.isOn = isOn;
		this.color = color;
		this.cooldown = cooldown;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		rad = buf.readFloat();
		health = buf.readInt();
		maxHealth = buf.readInt();
		power = buf.readInt();
		isOn = buf.readBoolean();
		color = buf.readInt();
		cooldown = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeFloat(rad);
		buf.writeInt(health);
		buf.writeInt(maxHealth);
		buf.writeInt(power);
		buf.writeBoolean(isOn);
		buf.writeInt(color);
		buf.writeInt(cooldown);
	}

	public static class Handler implements IMessageHandler<TEFFPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TEFFPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			try {
				
				if(te instanceof TileEntityForceField) {
					TileEntityForceField ff = (TileEntityForceField)te;

					ff.radius = m.rad;
					ff.health = m.health;
					ff.maxHealth = m.maxHealth;
					ff.power = m.power;
					ff.isOn = m.isOn;
					ff.color = m.color;
					ff.cooldown = m.cooldown;
				}
				
			} catch (Exception x) {
			}
			return null;
		}
	}
}
