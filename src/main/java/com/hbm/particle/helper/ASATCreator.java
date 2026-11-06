package com.hbm.particle.helper;

import java.util.Random;

import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.render.world.RenderSkyEffects;
import com.hbm.render.world.skyfx.SkyEffect;
import com.hbm.render.world.skyfx.SkyEffectSatDebris;
import com.hbm.render.world.skyfx.SkyEffectSatExplosion;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ASATCreator implements IParticleCreator {
	
	public static void composeEffect(World world) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "asat");
		PacketDispatcher.wrapper.sendToDimension(new AuxParticlePacketNT(data, 0, 0, 0), world.provider.dimensionId);
	}

	@Override
	public void makeParticle(World world, EntityPlayer player, TextureManager texman, Random rand, double d0, double d1, double d2, NBTTagCompound data) {

		double x = world.rand.nextGaussian() * 100;
		double z = world.rand.nextGaussian() * 100;
		
		int fragCount = 5 + world.rand.nextInt(10);
		float initAngle = world.rand.nextFloat() * 360;
		
		for(int i = 0; i < fragCount; i++) {
			SkyEffect fx = new SkyEffectSatDebris(world).withPos(x, 250, z).withAngleMotion(initAngle + world.rand.nextFloat() * 180, 15 + world.rand.nextFloat() * 15, 0.1);
			RenderSkyEffects.RENDER_INSTANCE.addEffect(fx);
		}
		
		SkyEffect fx = new SkyEffectSatExplosion(world).withPos(x, 250, z);
		RenderSkyEffects.RENDER_INSTANCE.addEffect(fx);
	}
}
