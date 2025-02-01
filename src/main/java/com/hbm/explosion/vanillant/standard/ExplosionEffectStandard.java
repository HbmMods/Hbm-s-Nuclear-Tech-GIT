package com.hbm.explosion.vanillant.standard;

import java.util.List;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IExplosionSFX;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class ExplosionEffectStandard implements IExplosionSFX {

	@Override
	public void doEffect(ExplosionVNT explosion, World world, double x, double y, double z, float size) {
		
		if(world.isRemote)
			return;
		
		world.playSoundEffect(x, y, z, "random.explode", 4.0F, (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
		
		PacketDispatcher.wrapper.sendToAllAround(new ExplosionVanillaNewTechnologyCompressedAffectedBlockPositionDataForClientEffectsAndParticleHandlingPacket(x, y, z, explosion.size, explosion.compat.affectedBlockPositions),  new TargetPoint(world.provider.dimensionId, x, y, z, 250));
	}
	
	public static void performClient(World world, double x, double y, double z, float size, List affectedBlocks) {
		
		if(size >= 2.0F) {
			world.spawnParticle("hugeexplosion", x, y, z, 1.0D, 0.0D, 0.0D);
		} else {
			world.spawnParticle("largeexplode", x, y, z, 1.0D, 0.0D, 0.0D);
		}

		int count = affectedBlocks.size();

		for(int i = 0; i < count; i++) {

			ChunkPosition pos = (ChunkPosition) affectedBlocks.get(i);
			int pX = pos.chunkPosX;
			int pY = pos.chunkPosY;
			int pZ = pos.chunkPosZ;

			double oX = (double) ((float) pX + world.rand.nextFloat());
			double oY = (double) ((float) pY + world.rand.nextFloat());
			double oZ = (double) ((float) pZ + world.rand.nextFloat());
			double dX = oX - x;
			double dY = oY - y;
			double dZ = oZ - z;
			double delta = (double) MathHelper.sqrt_double(dX * dX + dY * dY + dZ * dZ) / 1D /* hehehe */;
			dX /= delta;
			dY /= delta;
			dZ /= delta;
			double mod = 0.5D / (delta / (double) size + 0.1D);
			mod *= (double) (world.rand.nextFloat() * world.rand.nextFloat() + 0.3F);
			dX *= mod;
			dY *= mod;
			dZ *= mod;
			world.spawnParticle("explode", (oX + x * 1.0D) / 2.0D, (oY + y * 1.0D) / 2.0D, (oZ + z * 1.0D) / 2.0D, dX, dY, dZ);
			world.spawnParticle("smoke", oX, oY, oZ, dX, dY, dZ);
		}
	}
}
