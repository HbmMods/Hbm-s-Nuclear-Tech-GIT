package com.hbm.packet.toclient;

import com.hbm.interfaces.Spaghetti;
import com.hbm.items.weapon.ItemCustomMissilePart.PartSize;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnace;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

@Spaghetti("Changing all machines to use TileEntityMachineBase will reduce the total chaos in this class")
@Deprecated //use the NBT packet instead
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
				if (te instanceof TileEntityMachineArcFurnace) {
					TileEntityMachineArcFurnace furn = (TileEntityMachineArcFurnace)te;
					
					if(m.id == 0)
						furn.dualCookTime = m.value;
				}
				if (te instanceof TileEntityCompactLauncher) {
					TileEntityCompactLauncher launcher = (TileEntityCompactLauncher)te;
					
					launcher.solid = m.value;
				}
				if (te instanceof TileEntityLaunchTable) {
					TileEntityLaunchTable launcher = (TileEntityLaunchTable)te;
					
					if(m.id == 0)
						launcher.solid = m.value;
					if(m.id == 1)
						launcher.padSize = PartSize.values()[m.value];
				}
				
				if(te instanceof TileEntityMachineBase) {
					((TileEntityMachineBase)te).processGauge(m.value, m.id);
				}
				
			} catch (Exception x) {}
			return null;
		}
	}
}
