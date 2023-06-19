package com.hbm.util;

import java.util.Random;

import com.hbm.main.MainRegistry;
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
		
		if(world.isRemote) {
			data.setDouble("posX", x);
			data.setDouble("posY", y);
			data.setDouble("posZ", z);
			MainRegistry.proxy.effectNT(data);
		} else {
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(world.provider.dimensionId, x, y, z, 150));
		}
	}
	public static void spawnJesusFlame(World world, double x, double y, double z) {
		Random rand = new Random();

		if(rand.nextInt(12) == 0) {
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "duodec");
		if(world.isRemote) {
			data.setDouble("posX", x);
			data.setDouble("posY", y);
			data.setDouble("posZ", z);
			MainRegistry.proxy.effectNT(data);
		} else {
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(world.provider.dimensionId, x, y, z, 150));
		}
	}
	}
	public static void spawnTuneFlame(World world, double x, double y, double z) {
		Random rand = new Random();
		if(rand.nextInt(12) == 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "duoewe");
			if(world.isRemote) {
				data.setDouble("posX", x);
				data.setDouble("posY", y);
				data.setDouble("posZ", z);
				MainRegistry.proxy.effectNT(data);
			} else {
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(world.provider.dimensionId, x, y, z, 150));
			}
		}	
		}
}
