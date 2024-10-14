package com.hbm.particle.helper;

import java.util.Random;

import com.hbm.particle.ParticleFlamethrower;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class FlameCreator implements IParticleCreator {

	public static void composeEffect(World world, double x, double y, double z) {
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "flamethrower");
		IParticleCreator.sendPacket(world, x, y, z, 50, data);
	}

	@Override
	public void makeParticle(World world, EntityPlayer player, TextureManager texman, Random rand, double x, double y, double z, NBTTagCompound data) {
		ParticleFlamethrower particle = new ParticleFlamethrower(world, x, y, z);
		Minecraft.getMinecraft().effectRenderer.addEffect(particle);
	}
}
