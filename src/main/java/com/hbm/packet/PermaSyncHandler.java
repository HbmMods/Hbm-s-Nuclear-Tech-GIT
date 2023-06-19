package com.hbm.packet;

import com.hbm.handler.ImpactWorldHandler;
import com.hbm.handler.RogueWorldHandler;
import com.hbm.saveddata.RogueWorldSaveData;
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

	public static void writePacket(ByteBuf buf, World world, EntityPlayerMP player) {
		
		/// TOM IMPACT DATA ///
		TomSaveData impact = TomSaveData.forWorld(world);
		buf.writeFloat(impact.fire);
		buf.writeFloat(impact.dust);
		buf.writeBoolean(impact.impact);
		buf.writeLong(impact.time);
		/// TOM IMPACT DATA ///
		
		/// ROGUE PLANET DATA ///
		RogueWorldSaveData rogue = RogueWorldSaveData.forWorld(world);
		buf.writeFloat(rogue.distance);
		buf.writeFloat(rogue.distance); //dont ask me how, or why, it just works okay :(
		buf.writeFloat(rogue.distance); //and it for some REASON IT SPECIFICALLY **NEEDS** 3!??!?? like it can work with more but 3 is the minimum
		buf.writeFloat(rogue.atmosphere);
		buf.writeBoolean(rogue.star);
		buf.writeBoolean(rogue.rogue);
		/// ROGUE PLANET DATA ///
	}
	
	public static void readPacket(ByteBuf buf, World world, EntityPlayer player) {

		/// TOM IMPACT DATA ///
		ImpactWorldHandler.lastSyncWorld = player.worldObj;
		ImpactWorldHandler.fire = buf.readFloat();
		ImpactWorldHandler.dust = buf.readFloat();
		ImpactWorldHandler.impact = buf.readBoolean();
		/// TOM IMPACT DATA ///
		
		/// ROGUE PLANET DATA ///
		RogueWorldHandler.lastSyncWorld = player.worldObj;
		RogueWorldHandler.distance = buf.readFloat();// SOMEONE PLEASE explain to me why my code is acting like a 5 year old
		RogueWorldHandler.distance = buf.readFloat();//mommy i want three! and only three! otherwise im not gonna work at all waahhh!!!!
		RogueWorldHandler.distance = buf.readFloat();
		RogueWorldHandler.atmosphere = buf.readFloat();
		RogueWorldHandler.star = buf.readBoolean();
		RogueWorldHandler.rogue = buf.readBoolean();
		/// ROGUE PLANET DATA ///
	}
}
