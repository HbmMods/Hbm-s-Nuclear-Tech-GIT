package com.hbm.explosion.vanillant.standard;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.interfaces.IExplosionSFX;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ExplosionEffectTiny implements IExplosionSFX {

	@Override
	public void doEffect(ExplosionVNT explosion, World world, double x, double y, double z, float size) {
		if(world.isRemote) return;

		world.playSoundEffect(x, y, z, "hbm:weapon.explosionTiny", 15.0F, 1.0F);
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "vanillaExt");
		data.setString("mode", "largeexplode");
		data.setFloat("size", 1.5F);
		data.setByte("count", (byte)1);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(world.provider.dimensionId, x, y, z, 100));
	}
}
