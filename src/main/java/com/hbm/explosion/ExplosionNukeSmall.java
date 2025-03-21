package com.hbm.explosion;

import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.handler.threading.PacketThreading;
import com.hbm.main.MainRegistry;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

@Deprecated public class ExplosionNukeSmall {

	public static void explode(World world, double posX, double posY, double posZ, MukeParams params) {

		// spawn particles, if present
		if(params.particle != null) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", params.particle);
			// if the FX type is "muke", apply random BF effect
			if(params.particle.equals("muke") && (MainRegistry.polaroidID == 11 || world.rand.nextInt(100) == 0)) {
				data.setBoolean("balefire", true);
			}
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(world.provider.dimensionId, posX, posY, posZ, 250));
		}

		// play the sound in any case
		world.playSoundEffect(posX, posY, posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);

		if(params.shrapnelCount > 0) ExplosionLarge.spawnShrapnels(world, posX, posY, posZ, params.shrapnelCount);
		if(params.miniNuke && !params.safe) new ExplosionNT(world, null, posX, posY, posZ, params.blastRadius).addAllAttrib(params.explosionAttribs).overrideResolution(params.resolution).explode();
		if(params.killRadius > 0) ExplosionNukeGeneric.dealDamage(world, posX, posY, posZ, params.killRadius);
		if(!params.miniNuke) world.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(world, (int) params.blastRadius, posX, posY, posZ));

		if(params.miniNuke) {
			float radMod = params.radiationLevel / 3F;

			for(int i = -2; i <= 2; i++) {
				for(int j = -2; j <= 2; j++) {
					if(Math.abs(i) + Math.abs(j) < 4) {
						ChunkRadiationManager.proxy.incrementRad(world, (int) Math.floor(posX + i * 16), (int) Math.floor(posY), (int) Math.floor(posZ + j * 16), 50 / (Math.abs(i) + Math.abs(j) + 1) * radMod);
					}
				}
			}
		}
	}

	public static MukeParams PARAMS_SAFE = new MukeParams() {{ safe = true; killRadius = 45F; radiationLevel = 2F; }};
	public static MukeParams PARAMS_TOTS = new MukeParams() {{ blastRadius = 10F; killRadius = 30F; particle = "tinytot"; shrapnelCount = 0; resolution = 32; radiationLevel = 1; }};
	public static MukeParams PARAMS_LOW = new MukeParams() {{ blastRadius = 15F; killRadius = 45F; radiationLevel = 2; }};
	public static MukeParams PARAMS_MEDIUM = new MukeParams() {{ blastRadius = 20F; killRadius = 55F; radiationLevel = 3; }};
	public static MukeParams PARAMS_HIGH = new MukeParams() {{ miniNuke = false; blastRadius = BombConfig.fatmanRadius; shrapnelCount = 0; }};

	/* more sensible approach with more customization options, idea shamelessly stolen from Martin */
	public static class MukeParams {
		public boolean miniNuke = true;
		public boolean safe = false;
		public float blastRadius;
		public float killRadius;
		public float radiationLevel = 1F;
		public String particle = "muke";
		public int shrapnelCount = 25;
		public int resolution = 64;
		public ExAttrib[] explosionAttribs = new ExAttrib[] { ExAttrib.FIRE, ExAttrib.NOPARTICLE, ExAttrib.NOSOUND, ExAttrib.NODROP, ExAttrib.NOHURT }; //TODO: replace with VNT
	}
}
