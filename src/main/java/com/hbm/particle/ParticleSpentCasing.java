package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.particle.SpentCasingConfig.CasingType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleSpentCasing extends EntityFX
{
	private static final ResourceLocation TEXTURE_BRASS = new ResourceLocation(RefStrings.MODID, "textures/particle/casing_brass.png"),
										  TEXTURE_SHOTGUN = new ResourceLocation(RefStrings.MODID, "textures/particle/casing_shotgun.png"),
										  TEXTURE_AR2 = new ResourceLocation(RefStrings.MODID, "textures/particle/casing_ar2.png");
	
	private final TextureManager textureManager;
	
	private final float momentumPitch, momentumYaw;
	private final CasingType casingType;
	public ParticleSpentCasing(TextureManager textureManager, World world, double x, double y, double z, double mx, double my, double mz, CasingType casingType, float momentumPitch, float momentumYaw)
	{
		super(world, x, y, z, mx, my, mz);
		particleMaxAge = 120;
		this.textureManager = textureManager;
		this.casingType = casingType;
		this.momentumPitch = momentumPitch;
		this.momentumYaw = momentumYaw;
	}

	@Override
	public int getFXLayer()
	{
		return 3;
	}
	
	@Override
	public void onUpdate()
	{
		// TODO Auto-generated method stub
		super.onUpdate();
		
		prevRotationPitch = rotationPitch;
		prevRotationYaw = rotationYaw;
		
		if (!onGround)
		{
			rotationPitch += momentumPitch;
			rotationYaw += momentumYaw;
		}
	}
	
	@Override
	public void renderParticle(
			Tessellator tessellator, float interp, float x, float y, float z,
			float tx, float tz
	)
	{
		// TODO Auto-generated method stub
		super.renderParticle(tessellator, interp, x, y, z, tx, tz);
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		final ResourceLocation texture;
		switch (casingType)
		{
			case AR2: texture = TEXTURE_AR2; break;
			case BRASS: texture = TEXTURE_BRASS; break;
			case SHOTGUN: texture = TEXTURE_SHOTGUN; break;
			default: throw new IllegalStateException("CasingType [" + casingType + "] is not recognized, cannot render spent casing!");
		}
		textureManager.bindTexture(texture);
		
		final float scale = particleScale * 0.1f,
					xInterp = (float) (prevPosX + (posX - prevPosX) * interp - interpPosX),
					yInterp = (float) (prevPosY + (posY - prevPosY) * interp - interpPosY),
					zInterp = (float) (prevPosZ + (posZ - prevPosZ) * interp - interpPosZ);
		
		tessellator.startDrawingQuads();
		tessellator.setNormal(0, 1, 0);
		tessellator.setBrightness(240);
		tessellator.setColorRGBA_F(particleRed, particleGreen, particleBlue, particleAlpha);
		tessellator.addVertexWithUV(xInterp - x * scale - tx * scale, yInterp - y, zInterp - z * scale - tz * scale, 0, 0);
		tessellator.addVertexWithUV(xInterp - x * scale + tx * scale, yInterp + y, zInterp - z * scale + tz * scale, 0, 1);
		tessellator.addVertexWithUV(xInterp + x * scale + tx * scale, yInterp + y, zInterp + z * scale + tz * scale, 1, 1);
		tessellator.addVertexWithUV(xInterp + x * scale - tx * scale, yInterp - y, zInterp + z * scale - tz * scale, 1, 0);
		tessellator.draw();
		
		GL11.glPopMatrix();
	}
}
