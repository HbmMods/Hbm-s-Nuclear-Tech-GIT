package com.hbm.explosion.vanillant.standard;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IExplosionSFX;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ExplosionEffectAmat implements IExplosionSFX {

	@Override
	public void doEffect(ExplosionVNT explosion, World world, double x, double y, double z, float size) {
		
		if(size < 15)
			world.playSoundEffect(x, y, z, "random.explode", 4.0F, (1.4F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);
		else
			world.playSoundEffect(x, y, z, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "amat");
		data.setFloat("scale", size);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(world.provider.dimensionId, x, y, z, 200));
	}
}
