package com.hbm.saveddata.satellites;

import java.util.Locale;

import com.hbm.entity.projectile.EntityTom;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.world.WorldUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class SatelliteHorizons extends SatelliteBase {
	
	public static final String CMD_FIRE = "fire";
	public static final String CMD_CANFIRE = "settarget";
	
	boolean used = false;
	
	public SatelliteHorizons() { }

	@Override public String getType() { return "PAYLOAD_UNKNOWN"; }
	
	@Override
	public IChatComponent[] getInfo(World world) {
		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.sat_gerald.getUnlocalizedName() + ".name"),
				used ? new ChatComponentTranslation("satellite.spent") : new ChatComponentTranslation("satellite.ready")
		};
	}

	@Override
	public void onOrbit(World world, double x, double y, double z) {
		super.onOrbit(world, x, y, z);

		for(Object p : world.playerEntities)
			((EntityPlayer)p).triggerAchievement(MainRegistry.horizonsStart);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("used", used);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		used = nbt.getBoolean("used");
	}

	@Override
	public void onCommandImpl(World world, String... cmd) {
		if(cmd.length <= 0) return;
		
		if(cmd[0].equals(CMD_FIRE)) {
			theHorizons(world, targetX, targetZ);
			return;
		}
		
		if(cmd[0].equals(CMD_CANFIRE)) {
			this.tx = (!used) + "";
			this.tx = this.tx.toUpperCase(Locale.US);
			return;
		}
	}

	@Override
	public void onCoordAction(World world, EntityPlayer player, int x, int y, int z) {
		this.setTarget(x, z);
		this.theHorizons(world, x, z);
	}
	
	public void theHorizons(World world, int x, int z) {
		if(used) return;
		
		used = true;
		SatelliteSavedData.getData(world).markDirty();
		
		EntityTom tom = new EntityTom(world);
		tom.setPosition(x + 0.5, 600, z + 0.5);
		
		IChunkProvider provider = world.getChunkProvider();
		provider.loadChunk(x >> 4, z >> 4);
		
		WorldUtil.loadAndSpawnEntityInWorld(tom);

		for(Object p : world.playerEntities)
			((EntityPlayer)p).triggerAchievement(MainRegistry.horizonsEnd);
		
		//not necessary but JUST to make sure
		if(!world.isRemote) {
			
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(EnumChatFormatting.RED + "Horizons has been activated."));
		}
	}
}
