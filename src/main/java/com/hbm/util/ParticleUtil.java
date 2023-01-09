package com.hbm.util;

import com.hbm.interfaces.ILocationProvider;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.particle.SpentCasingConfig;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ParticleUtil {

	public static void spawnGasFlame(World world, double x, double y, double z, double mX, double mY, double mZ) {

		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "gasfire");
		data.setDouble("mX", mX);
		data.setDouble("mY", mY);
		data.setDouble("mZ", mZ);
		
		if(world.isRemote) {
			data.setDouble("posX", x);
			data.setDouble("posY", y);
			data.setDouble("posZ", z);
			MainRegistry.proxy.effectNT(data);
		} else {
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(world.provider.dimensionId, x, y, z, 150));
		}
	}
	
	/**
	 * Spawn a spent shell casing.
	 * @param location Location to spawn from.
	 * @param config The shell casing configuration to use.
	 * @param pitch Pitch rotation in radians.
	 * @param yaw Yaw rotation in radians.
	 * @param heightAdjustment Height adjustment.
	 * @param sneaking Assume from a sneaking/crouched entity.
	 */
	public static void spawnCasing(ILocationProvider location, SpentCasingConfig config, float pitch, float yaw, float heightAdjustment, boolean sneaking)
	{
		final NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "casing");
		data.setDouble("posX", location.posX());
		data.setDouble("posY", location.posY() + heightAdjustment);
		data.setDouble("posZ", location.posZ());
		data.setFloat("pitch", pitch);
		data.setFloat("yaw", yaw);
		data.setBoolean("crouched", sneaking);
		data.setString("name", config.getRegistryName());
		MainRegistry.proxy.effectNT(data);
	}
}
