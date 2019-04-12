package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachinePumpjack;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AuxParticlePacket implements IMessage {

	double x;
	double y;
	double z;
	int type;

	public AuxParticlePacket()
	{
		
	}

	public AuxParticlePacket(double x, double y, double z, int type)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(type);
	}

	public static class Handler implements IMessageHandler<AuxParticlePacket, IMessage> {
		
		@Override
		public IMessage onMessage(AuxParticlePacket m, MessageContext ctx) {
			
			try {
				
				World world = Minecraft.getMinecraft().theWorld;
				
				switch(m.type) {
				case 0:
					
					for(int i = 0; i < 10; i++) {
						EntityCloudFX smoke = new EntityCloudFX(world, m.x + world.rand.nextGaussian(), m.y + world.rand.nextGaussian(), m.z + world.rand.nextGaussian(), 0.0, 0.0, 0.0);
						Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
					}
					break;
					
				case 1:
					
					EntityCloudFX smoke = new EntityCloudFX(world, m.x, m.y, m.z, 0.0, 0.1, 0.0);
					Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
					break;
				}
				
			} catch(Exception x) { }
			
			return null;
		}
	}
}
