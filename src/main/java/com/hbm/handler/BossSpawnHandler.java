package com.hbm.handler;

import com.hbm.entity.mob.EntityMaskMan;
import com.hbm.util.ContaminationUtil;

import cpw.mods.fml.common.eventhandler.Event.Result;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class BossSpawnHandler {
	
	public static void rollTheDice(World world) {
		
		int delay = 60 * 60 * 60;	//every hour
		
		if(world.getTotalWorldTime() % delay == 0) {
			
			if(world.rand.nextInt(3) == 0 && !world.playerEntities.isEmpty() && world.provider.isSurfaceWorld()) {	//33% chance only if there is a player online
				
				EntityPlayer player = (EntityPlayer) world.playerEntities.get(world.rand.nextInt(world.playerEntities.size()));	//choose a random player
				
				if(ContaminationUtil.getRads(player) >= 50 && world.getHeightValue((int)player.posX, (int)player.posZ) > player.posY + 3) {	//if the player has more than 50 RAD and is underground
					
					double spawnX = player.posX + world.rand.nextGaussian() * 20;
					double spawnZ = player.posZ + world.rand.nextGaussian() * 20;
					double spawnY = world.getHeightValue((int)player.posX, (int)player.posZ);
					
					EntityMaskMan maskman = new EntityMaskMan(world);
					maskman.setLocationAndAngles(spawnX, spawnY, spawnZ, world.rand.nextFloat() * 360.0F, 0.0F);
					Result canSpawn = ForgeEventFactory.canEntitySpawn(maskman, world, (float)spawnX, (float)spawnY, (float)spawnZ);
					
					if (canSpawn == Result.ALLOW || canSpawn == Result.DEFAULT) {
						
						world.spawnEntityInWorld(maskman);
						ForgeEventFactory.doSpecialSpawn(maskman, world, (float)spawnX, (float)spawnY, (float)spawnZ);
					}
				}
			}
		}
	}

}
