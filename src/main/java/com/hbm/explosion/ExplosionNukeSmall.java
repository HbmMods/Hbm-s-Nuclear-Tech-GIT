package com.hbm.explosion;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.RadiationSavedData;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ExplosionNukeSmall {

	public static final int safe = 0;
	public static final int tots = 1;
	public static final int low = 2;
	public static final int medium = 3;
	public static final int high = 4;

	public static void explode(World world, double posX, double posY, double posZ, int size) {

		
		//all sizes have the same animation except tiny tots
		NBTTagCompound data = new NBTTagCompound();
		if(size == tots)
			data.setString("type", "tinytot");
		else
			data.setString("type", "muke");
		if(MainRegistry.polaroidID == 11 || world.rand.nextInt(100) == 0)
			data.setBoolean("balefire", true);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 250));
		world.playSoundEffect(posX, posY, posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
		
		//no shrapnels for large mukes and tinty tots
		if(size != high && size != tots)
			ExplosionLarge.spawnShrapnels(world, posX, posY, posZ, 25);
		
		if(size == safe) {
			ExplosionNukeGeneric.dealDamage(world, posX, posY, posZ, 45);
		
		} else if(size > safe && size < high) {
			
			switch(size) {
			case 1: new ExplosionNT(world, null, posX, posY, posZ, 10F).addAllAttrib(ExplosionNT.nukeAttribs).overrideResolution(32).explode();
				ExplosionNukeGeneric.dealDamage(world, posX, posY, posZ, 30); break;
			
			case 2: new ExplosionNT(world, null, posX, posY, posZ, 15F).addAllAttrib(ExplosionNT.nukeAttribs).overrideResolution(64).explode();
				ExplosionNukeGeneric.dealDamage(world, posX, posY, posZ, 45); break;
			
			case 3: new ExplosionNT(world, null, posX, posY, posZ, 20F).addAllAttrib(ExplosionNT.nukeAttribs).overrideResolution(64).explode();
				ExplosionNukeGeneric.dealDamage(world, posX, posY, posZ, 55); break;
			}
			
		} else if(size == high) {
			world.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(world, BombConfig.fatmanRadius, posX, posY, posZ).mute());
		}
		
		//radiation is 50 RAD/s in the epicenter, times the radMod
		float radMod = size * 0.33F;

		//radMod for safe nukes is the same as for low yield
		if(size == safe)
			radMod = 0.66F;
		
		for(int i = -2; i <= 2; i++)
			for(int j = -2; j <= 2; j++)
				if(i + j < 4)
					RadiationSavedData.incrementRad(world, (int)posX + i * 16, (int)posZ + j * 16, 50 / (Math.abs(i) + Math.abs(j) + 1) * radMod, 1000);
	}
}
