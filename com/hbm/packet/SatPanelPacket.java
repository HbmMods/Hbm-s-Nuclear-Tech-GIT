package com.hbm.packet;

import com.hbm.items.tool.ItemSatInterface;
import com.hbm.saveddata.SatelliteSaveStructure;
import com.hbm.saveddata.SatelliteSaveStructure.SatelliteType;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.tileentity.machine.TileEntityMachineRadar;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class SatPanelPacket implements IMessage {

	int id;
	int dim;
	SatelliteType type;
	long lastOp;

	public SatPanelPacket() {

	}

	public SatPanelPacket(SatelliteSaveStructure sat) {
		id = sat.satelliteID;
		dim = sat.satDim;
		type = sat.satelliteType;
		lastOp = sat.lastOp;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		id = buf.readInt();
		dim = buf.readInt();
		type = SatelliteType.getEnum(buf.readInt());
		lastOp = buf.readLong();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(id);
		buf.writeInt(dim);
		buf.writeInt(type.getID());
		buf.writeLong(lastOp);
	}

	public static class Handler implements IMessageHandler<SatPanelPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(SatPanelPacket m, MessageContext ctx) {
			
			EntityPlayer p = Minecraft.getMinecraft().thePlayer;

			try {
				
				if(ItemSatInterface.satData == null) {
					ItemSatInterface.satData = new SatelliteSavedData(p.worldObj);
				}
				
				SatelliteSaveStructure sat = new SatelliteSaveStructure(m.id, m.type, m.dim);
				sat.lastOp = m.lastOp;
				ItemSatInterface.satData.satellites.add(sat);
				
				ItemSatInterface.satData.satCount = ItemSatInterface.satData.satellites.size();
				
			} catch (Exception x) {
			}
			return null;
		}
	}
}
