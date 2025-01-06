package com.hbm.packet.toclient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hbm.explosion.vanillant.standard.ExplosionEffectStandard;
import com.hbm.interfaces.NotableComments;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.ChunkPosition;

/**
 * Can you tell I'm fucking done with packets? Well, can you?
 * @author hbm
 *
 */
@NotableComments
public class ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket implements IMessage {

	private double posX;
	private double posY;
	private double posZ;
	private float size;
	private List affectedBlocks;

	public ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket() { }

	public ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket(double x, double y, double z, float size, List blocks) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		this.size = size;
		this.affectedBlocks = new ArrayList(blocks);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.posX = (double) buf.readFloat();
		this.posY = (double) buf.readFloat();
		this.posZ = (double) buf.readFloat();
		this.size = buf.readFloat();
		int i = buf.readInt();
		this.affectedBlocks = new ArrayList(i);
		int j = (int) this.posX;
		int k = (int) this.posY;
		int l = (int) this.posZ;

		for(int i1 = 0; i1 < i; ++i1) {
			int j1 = buf.readByte() + j;
			int k1 = buf.readByte() + k;
			int l1 = buf.readByte() + l;
			this.affectedBlocks.add(new ChunkPosition(j1, k1, l1));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat((float) this.posX);
		buf.writeFloat((float) this.posY);
		buf.writeFloat((float) this.posZ);
		buf.writeFloat(this.size);
		buf.writeInt(this.affectedBlocks.size());
		int i = (int) this.posX;
		int j = (int) this.posY;
		int k = (int) this.posZ;
		Iterator iterator = this.affectedBlocks.iterator();

		while(iterator.hasNext()) {
			ChunkPosition chunkposition = (ChunkPosition) iterator.next();
			int l = chunkposition.chunkPosX - i;
			int i1 = chunkposition.chunkPosY - j;
			int j1 = chunkposition.chunkPosZ - k;
			buf.writeByte(l);
			buf.writeByte(i1);
			buf.writeByte(j1);
		}
	}

	public static class Handler implements IMessageHandler<ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket m, MessageContext ctx) {
			
			ExplosionEffectStandard.performClient(Minecraft.getMinecraft().theWorld, m.posX, m.posY, m.posZ, m.size, m.affectedBlocks);
			return null;
		}
	}
}
