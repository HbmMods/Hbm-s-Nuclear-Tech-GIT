package com.hbm.packet;

import java.util.ArrayList;
import java.util.List;

import com.hbm.sound.SoundLoopMiner;
import com.hbm.tileentity.TileEntityMachineAssembler;
import com.hbm.tileentity.TileEntityMachineIGenerator;
import com.hbm.tileentity.TileEntityMachineMiningDrill;
import com.hbm.tileentity.TileEntityPylonRedWire;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class LoopedSoundPacket implements IMessage {

	int x;
	int y;
	int z;

	public LoopedSoundPacket()
	{
		
	}

	public LoopedSoundPacket(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	public static class Handler implements IMessageHandler<LoopedSoundPacket, IMessage> {
		
		@Override
		public IMessage onMessage(LoopedSoundPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			try {
			if (te != null && te instanceof TileEntityMachineMiningDrill) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopMiner.list.size(); i++)  {
					if(SoundLoopMiner.list.get(i).getTE() == te && !SoundLoopMiner.list.get(i).isDonePlaying())
						flag = false;
				}
				
				//if(flag && te.getWorldObj().isRemote)
				//	Minecraft.getMinecraft().
			}
			} catch(Exception ex) {
				System.out.println("Sorry folks, not today.");
			}
			return null;
		}
	}
}
