package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleDebug extends EntityFX {

	private static final ResourceLocation power = new ResourceLocation(RefStrings.MODID + ":textures/particle/debug_power.png");
	private static final ResourceLocation fluid = new ResourceLocation(RefStrings.MODID + ":textures/particle/debug_fluid.png");
	private TextureManager theRenderEngine;
	private int type;

	public ParticleDebug(TextureManager texman, World world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z);
		this.theRenderEngine = texman;
		this.particleMaxAge = 10;
		this.type = 0;
		this.motionX = motionX;
		this.motionY = motionY;
		this.motionZ = motionZ;
		this.noClip = true;
	}

	public ParticleDebug(TextureManager texman, World world, double x, double y, double z, double motionX, double motionY, double motionZ, int color) {
		this(texman, world, x, y, z, motionX, motionY, motionZ);
		this.type = 1;
		this.particleRed = ((color & 0xff0000) >> 16) / 255F;
		this.particleGreen = ((color & 0x00ff00) >> 8) / 255F;
		this.particleBlue = (color & 0x0000ff) / 255F;
	}

	public int getFXLayer() {
		return 3;
	}

	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

		this.theRenderEngine.bindTexture(type == 0 ? power : fluid);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.disableStandardItemLighting();

		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setBrightness(240);
		
		float scale = 0.05F;
		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);

		float pX = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - interpPosX);
		float pY = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - interpPosY);
		float pZ = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - interpPosZ);

		tess.addVertexWithUV((double) (pX - x * scale - tx * scale), (double) (pY - y * scale), (double) (pZ - z * scale - tz * scale), 1, 1);
		tess.addVertexWithUV((double) (pX - x * scale + tx * scale), (double) (pY + y * scale), (double) (pZ - z * scale + tz * scale), 1, 0);
		tess.addVertexWithUV((double) (pX + x * scale + tx * scale), (double) (pY + y * scale), (double) (pZ + z * scale + tz * scale), 0, 0);
		tess.addVertexWithUV((double) (pX + x * scale - tx * scale), (double) (pY - y * scale), (double) (pZ + z * scale - tz * scale), 0, 1);
		tess.draw();

		GL11.glPolygonOffset(0.0F, 0.0F);
	}
}
