package com.hbm.saveddata.satellites;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemSatellite.EnumSatType;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.network.RTTYSystem;

import api.hbm.redstoneoverradio.IRORInteractive;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class SatelliteRelay extends SatelliteBase {

	public static final String CMD_RELAY = "relay";
	
	/*
	 * Originally, the relay had to be set up, with the id and channel being configured using commands.
	 * While this made it feel more technical and was in line with how the other satellites work,
	 * it did mean that for channel changes, an extra tick had to be wasted since the channel can't be
	 * changed and a signal sent in the same tick. So for convenience and reliability purposes, the
	 * relay only has a single command that does all the configuring and sending at once.
	 */
	
	public SatelliteRelay() { }

	@Override public String getType() { return "DIMENSIONAL_RELAY"; }
	
	@Override
	public IChatComponent[] getInfo(World world) {
		return new IChatComponent[] {
				new ChatComponentTranslation(ModItems.satellite.getUnlocalizedName(new ItemStack(ModItems.satellite, 1, EnumSatType.RELAY.ordinal())) + ".name")
		};
	}
	
	@Override
	public void onOrbit(World world, double x, double y, double z) {
		super.onOrbit(world, x, y, z);

		for(Object p : world.playerEntities)
			((EntityPlayer) p).triggerAchievement(MainRegistry.achFOEQ);
	}

	@Override
	public void onCommandImpl(World world, String... cmd) {
		if(cmd.length <= 0) return;
		
		if(cmd[0].equals(CMD_RELAY) && cmd.length > 3) {
			
			int dim = IRORInteractive.parseInt(cmd[1]);
			String chan = cmd[2];

			World targetWorld = DimensionManager.getWorld(dim);
			
			if(targetWorld != null) {
				
				StringBuilder signal = new StringBuilder();
				for(int i = 1; i < cmd.length; i++) {
					if(i > 1) signal.append(" ");
					signal.append(cmd[i]);
				}
				
				RTTYSystem.broadcast(targetWorld, chan, signal.toString());
			}
		}
	}
}
