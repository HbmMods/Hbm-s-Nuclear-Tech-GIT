package com.hbm.particle.helper;

import java.util.Random;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Does two cool things:
 * <br>- requires no more additions to ClientProxy which is already bloated, full of other stuff and cumbersome to work with
 * <br>- being a separate class, we can get as messy as we want without affecting other particles, so effects can overall have more logic behind them without turning into a big ugly clump
 * @author hbm
 *
 */
public interface IParticleCreator {

	@SideOnly(Side.CLIENT)
	public void makeParticle(World world, EntityPlayer player, TextureManager texman, Random rand, double x, double y, double z, NBTTagCompound data);

	public static void sendPacket(World world, double x, double y, double z, int range, NBTTagCompound data) {
		PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(world.provider.dimensionId, x, y, z, range));
	}
}
