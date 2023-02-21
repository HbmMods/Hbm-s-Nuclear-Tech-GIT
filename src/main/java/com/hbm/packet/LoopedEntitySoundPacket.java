package com.hbm.packet;

import com.hbm.entity.logic.EntityBomber;
import com.hbm.sound.MovingSoundBomber;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
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

				int n = 1;
		        int x = e.getDataWatcher().getWatchableObjectByte(16);

		        switch(x) {
		        case 0:
		        case 1:
		        case 2:
		        case 3:
		        case 4: n = 2; break;
		        case 5:
		        case 6:
		        case 7:
		        case 8: n = 1; break;
		        case 9: n = 3; break;
		        default: n = 2; break;
		        }
		        
				boolean flag = true;
				for(int i = 0; i < MovingSoundBomber.globalSoundList.size(); i++)  {
					if(MovingSoundBomber.globalSoundList.get(i).bomber == e && !MovingSoundBomber.globalSoundList.get(i).isDonePlaying())
						flag = false;
				}
				
				if(flag) {
					if(n == 2)
						Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundBomber(new ResourceLocation("hbm:entity.bomberSmallLoop"), (EntityBomber)e));
					if(n == 1)
						Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundBomber(new ResourceLocation("hbm:entity.bomberLoop"), (EntityBomber)e));
					if(n == 3)
						Minecraft.getMinecraft().getSoundHandler().playSound(new MovingSoundBomber(new ResourceLocation("hbm:entity.civFly"), (EntityBomber)e));
				}
			}
			
			return null;
		}
	}
}
