package com.hbm.packet;

import com.hbm.entity.logic.EntityBomber;
import com.hbm.sound.MovingSoundBomber;
import com.hbm.sound.SoundLoopAssembler;
import com.hbm.sound.SoundLoopBroadcaster;
import com.hbm.sound.SoundLoopCentrifuge;
import com.hbm.sound.SoundLoopChemplant;
import com.hbm.sound.SoundLoopIGen;
import com.hbm.sound.SoundLoopMiner;
import com.hbm.sound.SoundLoopTurbofan;
import com.hbm.tileentity.machine.TileEntityBroadcaster;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;
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
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class LoopedEntitySoundPacket implements IMessage {

	int entityID;

	public LoopedEntitySoundPacket()
	{
		
	}

	public LoopedEntitySoundPacket(int entityID)
	{
		this.entityID = entityID;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		entityID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(entityID);
	}

	public static class Handler implements IMessageHandler<LoopedEntitySoundPacket, IMessage> {
		
		@Override
		//Tamaized, I love you!
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(LoopedEntitySoundPacket m, MessageContext ctx) {
			
			Entity e = Minecraft.getMinecraft().theWorld.getEntityByID(m.entityID);
			
			if(e instanceof EntityBomber) {
				
				boolean flag = true;
				for(int i = 0; i < MovingSoundBomber.globalSoundList.size(); i++)  {
					if(MovingSoundBomber.globalSoundList.get(i).bomber == e && !MovingSoundBomber.globalSoundList.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag) {
					Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundBomber(new ResourceLocation("hbm:entity.bomberLoop"), (EntityBomber)e));
				}
			}
			
			return null;
		}
	}
}
