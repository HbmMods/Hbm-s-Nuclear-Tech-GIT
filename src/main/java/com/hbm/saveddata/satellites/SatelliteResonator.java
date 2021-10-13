package com.hbm.saveddata.satellites;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public class SatelliteResonator extends Satellite {
	
	public SatelliteResonator() {
		this.coordAcs.add(CoordActions.HAS_Y);
		this.satIface = Interfaces.SAT_COORD;
	}
	
	public void onCoordAction(World world, EntityPlayer player, int x, int y, int z) {

		if(!(player instanceof EntityPlayerMP))
			return;

		world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
		player.mountEntity(null);
		((EntityPlayerMP)player).playerNetServerHandler.setPlayerLocation(x + 0.5D, y, z + 0.5D, player.rotationYaw, player.rotationPitch);
		world.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F);
	}
}
