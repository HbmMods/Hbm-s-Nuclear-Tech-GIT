package com.hbm.saveddata.satellites;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionData;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SatelliteMapper extends SatelliteBase {

	public static final String CMD_TARGET_LOADED = "targetloaded";
	public static final String CMD_GETSMOG = "getsmog";
	public static final String CMD_SPOT_PLAYER = "spotplayers";
	
	public static final int SPOT_PLAYER_MAX_RANGE = 250;
	
	public SatelliteMapper() { }

	@Override public String getType() { return "NOT_A_SPY_SATELLITE_:)"; }
	
	@Override
	public void onCommandImpl(World world, String... cmd) {
		if(cmd.length <= 0) return;
		
		if(cmd[0].equals(CMD_TARGET_LOADED)) {
			this.tx = "" + world.getChunkProvider().chunkExists(targetX >> 4, targetZ >> 4);
			this.tx = this.tx.toUpperCase(Locale.US);
			return;
		}
		
		if(cmd[0].equals(CMD_GETSMOG)) {

			PollutionData data = PollutionHandler.getPollutionData(world, this.targetX, 255, this.targetZ);
			if(data != null) {
				float soot = data.pollution[PollutionType.SOOT.ordinal()];
				this.tx = "" + (int) Math.ceil(soot);
			}
			return;
		}
		
		if(cmd[0].equals(CMD_SPOT_PLAYER)) {
			
			List<String> names = new ArrayList();
			
			for(Object o : world.playerEntities) {
				EntityPlayer player = (EntityPlayer) o;
				
				int x = (int) Math.floor(player.posX);
				int z = (int) Math.floor(player.posZ);
				
				double dX = x - targetX;
				double dZ = z - targetZ;
				
				if(dX * dX + dZ * dZ <= SPOT_PLAYER_MAX_RANGE * SPOT_PLAYER_MAX_RANGE) {
					int height = world.getHeightValue(x, z);
					if(height < player.posY + 2) names.add(player.getCommandSenderName());
				}
			}
			
			if(names.isEmpty()) {
				this.tx = "NONE";
				return;
			}
			
			this.tx = String.join(";", names);
			return;
		}
	}
}
