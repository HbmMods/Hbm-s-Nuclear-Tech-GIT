package com.hbm.packet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.hbm.dim.CelestialBodyWorldSavedData;
import com.hbm.dim.trait.CelestialBodyTrait;
import com.hbm.handler.ImpactWorldHandler;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionData;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.TomSaveData;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

/**
 * Utility for permanently synchronizing values every tick with a player in the given context of a world.
 * Uses the Byte Buffer directly instead of NBT to cut back on unnecessary data.
 * @author hbm
 */
public class PermaSyncHandler {
	
	public static HashSet<Integer> boykissers = new HashSet<Integer>();
	public static float[] pollution = new float[PollutionType.values().length];

	public static void writePacket(ByteBuf buf, World world, EntityPlayerMP player) {
		
		/// TOM IMPACT DATA ///
		TomSaveData data = TomSaveData.forWorld(world);
		buf.writeFloat(data.fire);
		buf.writeFloat(data.dust);
		buf.writeBoolean(data.impact);
		buf.writeLong(data.time);
		/// TOM IMPACT DATA ///
		
		/// SHITTY MEMES ///
		List<Integer> ids = new ArrayList<Integer>();
		for(Object o : world.playerEntities) {
			EntityPlayer p = (EntityPlayer) o;
			if(p.isPotionActive(HbmPotion.death.id)) {
				ids.add(p.getEntityId());
			}
		}
		buf.writeShort((short) ids.size());
		for(Integer i : ids) buf.writeInt(i);
		/// SHITTY MEMES ///

		/// POLLUTION ///
		PollutionData pollution = PollutionHandler.getPollutionData(world, (int) Math.floor(player.posX), (int) Math.floor(player.posY), (int) Math.floor(player.posZ));
		if(pollution == null) pollution = new PollutionData();
		for(int i = 0; i < PollutionType.values().length; i++) {
			buf.writeFloat(pollution.pollution[i]);
		}
		/// POLLUTION ///

		// CBT is VARIABLE LENGTH!
		// Make sure it's always last, or you'll have issues OF THE CBT VARIETY!
		/// CBT ///
		HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits = CelestialBodyWorldSavedData.getTraits(world);
		if(traits != null) {
			buf.writeBoolean(true); // Has traits marker (since we can have an empty list)

			for(int i = 0; i < CelestialBodyTrait.traitList.size(); i++) {
				Class<? extends CelestialBodyTrait> traitClass = CelestialBodyTrait.traitList.get(i);
				CelestialBodyTrait trait = traits.get(traitClass);
	
				if(trait != null) {
					buf.writeInt(i); // ID of the trait, in order registered
					trait.writeToBytes(buf);
				}
			}
		} else {
			buf.writeBoolean(false);
		}
		/// CBT ///
	}
	
	public static void readPacket(ByteBuf buf, World world, EntityPlayer player) {

		/// TOM IMPACT DATA ///
		ImpactWorldHandler.lastSyncWorld = player.worldObj;
		ImpactWorldHandler.fire = buf.readFloat();
		ImpactWorldHandler.dust = buf.readFloat();
		ImpactWorldHandler.impact = buf.readBoolean();
		ImpactWorldHandler.time = buf.readLong();
		/// TOM IMPACT DATA ///

		/// SHITTY MEMES ///
		boykissers.clear();
		int ids = buf.readShort();
		for(int i = 0; i < ids; i++) boykissers.add(buf.readInt());
		/// SHITTY MEMES ///

		/// POLLUTION ///
		for(int i = 0; i < PollutionType.values().length; i++) {
			pollution[i] = buf.readFloat();
		}
		/// POLLUTION ///

		// I will clamp your balls if you don't heed the prior warning
		// Unless you know how to delimit byte buffers, you magician you
		/// CBT ///
		try {
			if(buf.readBoolean()) {
				HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait> traits = new HashMap<Class<? extends CelestialBodyTrait>, CelestialBodyTrait>();
	
				while(buf.isReadable()) {
					CelestialBodyTrait trait = CelestialBodyTrait.traitList.get(buf.readInt()).newInstance();
					trait.readFromBytes(buf);
	
					traits.put(trait.getClass(), trait);
				}
	
				CelestialBodyWorldSavedData.updateClientTraits(traits);
			} else {
				CelestialBodyWorldSavedData.updateClientTraits(null);
			}

		} catch (Exception ex) {
			// If any exception occurs, stop parsing any more bytes, they'll be unaligned
			// We'll unset the client trait set to prevent any issues

			MainRegistry.logger.catching(ex);
			CelestialBodyWorldSavedData.updateClientTraits(null);
		}
		/// CBT ///
	}
}
