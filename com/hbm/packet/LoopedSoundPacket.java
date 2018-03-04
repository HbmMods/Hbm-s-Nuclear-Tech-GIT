package com.hbm.packet;

import com.hbm.sound.SoundLoopAssembler;
import com.hbm.sound.SoundLoopBroadcaster;
import com.hbm.sound.SoundLoopChemplant;
import com.hbm.sound.SoundLoopIGen;
import com.hbm.sound.SoundLoopMiner;
import com.hbm.sound.SoundLoopTurbofan;
import com.hbm.tileentity.machine.TileEntityBroadcaster;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;
import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

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
			
			if (te != null && te instanceof TileEntityMachineIGenerator) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopIGen.list.size(); i++)  {
					if(SoundLoopIGen.list.get(i).getTE() == te && !SoundLoopIGen.list.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag && te.getWorldObj().isRemote && ((TileEntityMachineIGenerator)te).torque > 0)
					Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopIGen(new ResourceLocation("hbm:block.igeneratorOperate"), te));
			}
			
			if (te != null && te instanceof TileEntityMachineTurbofan) {
				
				boolean flag = true;
				for(int i = 0; i < SoundLoopTurbofan.list.size(); i++)  {
					if(SoundLoopTurbofan.list.get(i).getTE() == te && !SoundLoopTurbofan.list.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag && te.getWorldObj().isRemote && ((TileEntityMachineTurbofan)te).isRunning)
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
					Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopBroadcaster(new ResourceLocation("hbm:block.broadcast" + (j % 3 + 1)), te));
			}
			return null;
		}
	}
}
