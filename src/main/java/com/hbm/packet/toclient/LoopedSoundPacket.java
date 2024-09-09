package com.hbm.packet.toclient;

import com.hbm.interfaces.Spaghetti;
import com.hbm.sound.*;
import com.hbm.tileentity.machine.*;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@Spaghetti("this class should be destroyed")
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
		//Tamaized, I love you!
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(LoopedSoundPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);
			
			if (te != null && te instanceof TileEntityMachineAssembler) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopAssembler.list.size(); i++)  {
					if(SoundLoopAssembler.list.get(i).getTE() == te && !SoundLoopAssembler.list.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag && te.getWorldObj().isRemote && ((TileEntityMachineAssembler)te).isProgressing)
					Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopAssembler(new ResourceLocation("hbm:block.assemblerOperate"), te));
			}
			
			if (te != null && te instanceof TileEntityMachineTurbofan) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopTurbofan.list.size(); i++)  {
					if(SoundLoopTurbofan.list.get(i).getTE() == te && !SoundLoopTurbofan.list.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag && te.getWorldObj().isRemote && ((TileEntityMachineTurbofan)te).wasOn)
					Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopTurbofan(new ResourceLocation("hbm:block.turbofanOperate"), te));
			}
			
			if (te != null && te instanceof TileEntityBroadcaster) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopBroadcaster.list.size(); i++)  {
					if(SoundLoopBroadcaster.list.get(i).getTE() == te && !SoundLoopBroadcaster.list.get(i).isDonePlaying())
						flag = false;
				}
				
				int j = te.xCoord + te.zCoord + te.yCoord;
				
				if(flag && te.getWorldObj().isRemote)
					Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopBroadcaster(new ResourceLocation("hbm:block.broadcast" + (Math.abs(j) % 3 + 1)), te));
			}
			
			if (te != null && te instanceof TileEntityMachineCentrifuge) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopCentrifuge.list.size(); i++)  {
					if(SoundLoopCentrifuge.list.get(i).getTE() == te && !SoundLoopCentrifuge.list.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag && te.getWorldObj().isRemote && ((TileEntityMachineCentrifuge)te).isProgressing)
					Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopCentrifuge(new ResourceLocation("hbm:block.centrifugeOperate"), te));
			}
			
			if (te != null && te instanceof TileEntityMachineGasCent) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopCentrifuge.list.size(); i++)  {
					if(SoundLoopCentrifuge.list.get(i).getTE() == te && !SoundLoopCentrifuge.list.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag && te.getWorldObj().isRemote && ((TileEntityMachineGasCent)te).isProgressing)
					Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopCentrifuge(new ResourceLocation("hbm:block.centrifugeOperate"), te));
			}
			return null;
		}
	}
}
