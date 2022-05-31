package com.hbm.util;

import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

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
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(world.provider.dimensionId, x, y, z, 150));
	}
}
