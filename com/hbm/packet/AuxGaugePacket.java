package com.hbm.packet;

import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.tileentity.bomb.TileEntityTurretCIWS;
import com.hbm.tileentity.bomb.TileEntityTurretCheapo;
import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class AuxGaugePacket implements IMessage {

	int x;
	int y;
	int z;
	int value;
	int id;

	public AuxGaugePacket()
	{
		
	}

	public AuxGaugePacket(int x, int y, int z, int value, int id)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.value = value;
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		value = buf.readInt();
		id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(value);
		buf.writeInt(id);
	}

	public static class Handler implements IMessageHandler<AuxGaugePacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(AuxGaugePacket m, MessageContext ctx) {
			try {
				TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

				if (te instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter limiter = (TileEntityAMSLimiter)te;
					if(m.id == 0)
						limiter.locked = m.value == 1;
					else if(m.id == 1)
						limiter.efficiency = m.value;
				}
				if (te instanceof TileEntityAMSEmitter) {
					TileEntityAMSEmitter emitter = (TileEntityAMSEmitter)te;
					if(m.id == 0)
						emitter.locked = m.value == 1;
					else if(m.id == 1)
						emitter.efficiency = m.value;
				}
				if (te instanceof TileEntityAMSBase) {
					TileEntityAMSBase base = (TileEntityAMSBase)te;
					
					if(m.id == 0)
						base.locked = m.value == 1;
					else if(m.id == 1)
						base.color = m.value;
					else if(m.id == 2)
						base.efficiency = m.value;
					else if(m.id == 3)
						base.field = m.value;
				}
				if (te instanceof TileEntityTurretCIWS) {
					TileEntityTurretCIWS cwis = (TileEntityTurretCIWS)te;
					
					cwis.rotation = m.value;
				}
				if (te instanceof TileEntityTurretCheapo) {
					TileEntityTurretCheapo cwis = (TileEntityTurretCheapo)te;
					
					cwis.rotation = m.value;
				}
				if (te instanceof TileEntityMachineSeleniumEngine) {
					TileEntityMachineSeleniumEngine selenium = (TileEntityMachineSeleniumEngine)te;
					
					selenium.pistonCount = m.value;
				}
				
			} catch (Exception x) { }
			return null;
		}
	}
}
