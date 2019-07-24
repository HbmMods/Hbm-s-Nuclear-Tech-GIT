package com.hbm.packet;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.saveddata.SatelliteSaveStructure;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.tileentity.bomb.TileEntityTurretCIWS;
import com.hbm.tileentity.bomb.TileEntityTurretCheapo;
import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;
import com.hbm.tileentity.machine.TileEntityForceField;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;
import com.hbm.tileentity.machine.TileEntityRadioRec;
import com.hbm.tileentity.machine.TileEntityReactorControl;

import cpw.mods.fml.common.FMLCommonHandler;
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
			
			//System.out.println(m.button + ": " + m.state);
			
			return null;
		}
	}
}
