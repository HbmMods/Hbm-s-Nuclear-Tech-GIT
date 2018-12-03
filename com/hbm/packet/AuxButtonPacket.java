package com.hbm.packet;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.saveddata.SatelliteSaveStructure;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.tileentity.bomb.TileEntityTurretCIWS;
import com.hbm.tileentity.bomb.TileEntityTurretCheapo;
import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;
import com.hbm.tileentity.machine.TileEntityForceField;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;
import com.hbm.tileentity.machine.TileEntityRadioRec;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

public class AuxButtonPacket implements IMessage {

	int x;
	int y;
	int z;
	int value;
	int id;

	public AuxButtonPacket()
	{
		
	}

	public AuxButtonPacket(int x, int y, int z, int value, int id)
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

	public static class Handler implements IMessageHandler<AuxButtonPacket, IMessage> {

		@Override
		public IMessage onMessage(AuxButtonPacket m, MessageContext ctx) {
			
			EntityPlayer p = ctx.getServerHandler().playerEntity;
			
			//try {
				TileEntity te = p.worldObj.getTileEntity(m.x, m.y, m.z);
				
				if (te instanceof TileEntityMachineReactorSmall) {
					TileEntityMachineReactorSmall reactor = (TileEntityMachineReactorSmall)te;
					
					if(m.id == 0)
						reactor.retracting = m.value == 1;
					
					if(m.id == 1) {
						FluidType type = FluidType.STEAM;
						int fill = reactor.tanks[2].getFill();
						
						switch(m.value) {
						case 0: type = FluidType.HOTSTEAM; fill = (int)Math.floor(fill / 10D); break;
						case 1: type = FluidType.SUPERHOTSTEAM; fill = (int)Math.floor(fill / 10D); break;
						case 2: type = FluidType.STEAM; fill = (int)Math.floor(fill * 100); break;
						}
						
						if(fill > reactor.tanks[2].getMaxFill())
							fill = reactor.tanks[2].getMaxFill();
						
						reactor.tanks[2].setTankType(type);
						reactor.tanks[2].setFill(fill);
					}
				}
				
				if (te instanceof TileEntityRadioRec) {
					TileEntityRadioRec radio = (TileEntityRadioRec)te;
					
					if(m.id == 0) {
						radio.isOn = (m.value == 1);
						System.out.println("Radio is now " + radio.isOn);
					}
					
					if(m.id == 1) {
						radio.freq = ((double)m.value) / 10D;
						System.out.println("Radio is now " + radio.freq);
					}
				}
				
				if (te instanceof TileEntityForceField) {
					TileEntityForceField field = (TileEntityForceField)te;
					
					field.isOn = !field.isOn;
				}
				
			//} catch (Exception x) { }
			
			return null;
		}
	}
}
