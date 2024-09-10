package com.hbm.packet.toclient;

import com.hbm.items.machine.ItemCassette.SoundType;
import com.hbm.items.machine.ItemCassette.TrackType;
import com.hbm.sound.SoundLoopSiren;
import com.hbm.tileentity.machine.TileEntityMachineSiren;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TESirenPacket implements IMessage {

	int x;
	int y;
	int z;
	int id;
	boolean active;

	public TESirenPacket()
	{
		
	}

	public TESirenPacket(int x, int y, int z, int id, boolean active)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
		this.active = active;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		id = buf.readInt();
		active = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(id);
		buf.writeBoolean(active);
	}

	public static class Handler implements IMessageHandler<TESirenPacket, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TESirenPacket m, MessageContext ctx) {
			TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(m.x, m.y, m.z);

			if (te != null && te instanceof TileEntityMachineSiren) {
				
				SoundLoopSiren sound = null;
				for(int i = 0; i < SoundLoopSiren.list.size(); i++)  {
					if(SoundLoopSiren.list.get(i).getTE() == te)
						sound = SoundLoopSiren.list.get(i);
				}
				
				if(m.active) {
					
					if(sound == null) {
						//Start sound
						if(m.id > 0) {
							boolean b = TrackType.getEnum(m.id).getType().name().equals(SoundType.LOOP.name());
							SoundLoopSiren s = new SoundLoopSiren(TrackType.getEnum(m.id).getSoundLocation(), te, TrackType.getEnum(m.id).getType());
							s.setRepeat(b);
							s.intendedVolume = TrackType.getEnum(m.id).getVolume();
							Minecraft.getMinecraft().getSoundHandler().playSound(s);
						}
					} else {
						ResourceLocation loc = TrackType.getEnum(m.id).getSoundLocation();
						
						if(loc != null) {
						String path = loc.getResourceDomain() + ":" + loc.getResourcePath();
						
							if(!sound.getPath().equals(path)) {
								//Track switched, stop and restart
								sound.endSound();
								if(m.id > 0)
									Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopSiren(TrackType.getEnum(m.id).getSoundLocation(), te, TrackType.getEnum(m.id).getType()));
							}
						}
						
						sound.intendedVolume = TrackType.getEnum(m.id).getVolume();
					}
					
				} else {
					
					if(sound != null) {
						//Stop sound
						sound.endSound();
						SoundLoopSiren.list.remove(sound);
					}
				}
			}
			return null;
		}
	}
}
