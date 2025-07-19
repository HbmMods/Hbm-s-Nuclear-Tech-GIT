package com.hbm.particle.helper;

import java.util.Random;

import com.hbm.main.MainRegistry;
import com.hbm.particle.ParticleFlamethrower;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class FlameCreator implements IParticleCreator {

	public static int META_FIRE = 0;
	public static int META_BALEFIRE = 1;
	public static int META_DIGAMMA = 2;

	public static void composeEffect(World world, double x, double y, double z, int meta) {
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "flamethrower");
		data.setInteger("meta", meta);
		IParticleCreator.sendPacket(world, x, y, z, 50, data);
	}

	public static void composeEffectClient(World world, double x, double y, double z, int meta) {
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "flamethrower");
		data.setInteger("meta", meta);
		data.setDouble("posX", x);
		data.setDouble("posY", y);
		data.setDouble("posZ", z);
		MainRegistry.proxy.effectNT(data);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void makeParticle(World world, EntityPlayer player, TextureManager texman, Random rand, double x, double y, double z, NBTTagCompound data) {
		ParticleFlamethrower particle = new ParticleFlamethrower(world, x, y, z, data.getInteger("meta"));
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
}
