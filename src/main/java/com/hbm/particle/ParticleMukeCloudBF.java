package com.hbm.particle;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleMukeCloudBF extends ParticleMukeCloud {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/explosion_bf.png");

	public ParticleMukeCloudBF(TextureManager texman, World world, double x, double y, double z, double mx, double my, double mz) {
		super(texman, world, x, y, z, mx, my, mz);
	}
	
	protected ResourceLocation getTexture() {
		return texture;
	}
}
