package com.hbm.packet;

import com.hbm.sound.SoundLoopAssembler;
import com.hbm.sound.SoundLoopChemplant;
import com.hbm.sound.SoundLoopMiner;
import com.hbm.tileentity.TileEntityMachineAssembler;
import com.hbm.tileentity.TileEntityMachineChemplant;
import com.hbm.tileentity.TileEntityMachineMiningDrill;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
		//Tamaized, I love you!
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(LoopedSoundPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityMachineMiningDrill) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopMiner.list.size(); i++)  {
					if(SoundLoopMiner.list.get(i).getTE() == te && !SoundLoopMiner.list.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag && te.getWorldObj().isRemote && ((TileEntityMachineMiningDrill)te).torque > 0.2F)
					Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopMiner(new ResourceLocation("hbm:block.minerOperate"), te));
			}
			
			if (te != null && te instanceof TileEntityMachineChemplant) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopChemplant.list.size(); i++)  {
					if(SoundLoopChemplant.list.get(i).getTE() == te && !SoundLoopChemplant.list.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag && te.getWorldObj().isRemote && ((TileEntityMachineChemplant)te).isProgressing)
					Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopChemplant(new ResourceLocation("hbm:block.chemplantOperate"), te));
			}
			
			if (te != null && te instanceof TileEntityMachineAssembler) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopAssembler.list.size(); i++)  {
					if(SoundLoopAssembler.list.get(i).getTE() == te && !SoundLoopAssembler.list.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag && te.getWorldObj().isRemote && ((TileEntityMachineAssembler)te).isProgressing)
					Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopAssembler(new ResourceLocation("hbm:block.assemblerOperate"), te));
			}
			return null;
		}
	}
}
