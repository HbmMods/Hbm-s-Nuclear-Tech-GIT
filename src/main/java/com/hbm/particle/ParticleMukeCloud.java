package com.hbm.particle;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleMukeCloud extends EntityFX {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/explosion.png");

	public ParticleMukeCloud(TextureManager texman, World world, double x, double y, double z, double mx, double my, double mz) {
		super(world, x, y, z, mx, my, mz);
	}

	public int getFXLayer() {
		return 3;
	}
}
