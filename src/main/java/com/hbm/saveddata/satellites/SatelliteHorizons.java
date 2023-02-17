package com.hbm.saveddata.satellites;

import com.hbm.entity.projectile.EntityTom;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.saveddata.TomSaveData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class SatelliteHorizons extends Satellite {
	
	boolean used = false;
	
	public SatelliteHorizons() {
		this.satIface = Interfaces.SAT_COORD;
	}

	public void onOrbit(World world, double x, double y, double z) {

		for(Object p : world.playerEntities)
			((EntityPlayer)p).triggerAchievement(MainRegistry.horizonsStart);
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("used", used);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		used = nbt.getBoolean("used");
	}
	
	public void onCoordAction(World world, EntityPlayer player, int x, int y, int z) {
		
		if(used)
			return;
		
		used = true;
		SatelliteSavedData.getData(world).markDirty();
		
		long finalDecent = (600-world.getHeightValue(x, z));
		
		TomSaveData data = TomSaveData.forWorld(world);
		data.dtime = finalDecent * 2;
		data.time = 24000;//One MC day before impact
		data.x = x;
		data.z = z;

		for(Object p : world.playerEntities)
			((EntityPlayer)p).triggerAchievement(MainRegistry.horizonsEnd);
		
		//not necessary but JUST to make sure
		if(!world.isRemote) {
			
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(EnumChatFormatting.RED + "Horizons has been activated."));
		}
	}
}
