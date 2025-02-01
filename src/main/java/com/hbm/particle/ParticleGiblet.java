package com.hbm.particle;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleGiblet extends EntityFX {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/meat.png");
	
	private TextureManager theRenderEngine;
	
	private float momentumYaw;
	private float momentumPitch;

	public ParticleGiblet(TextureManager texman, World world, double x, double y, double z, double mX, double mY, double mZ) {
		super(world, x, y, z);
		this.motionX = mX;
		this.motionY = mY;
		this.motionZ = mZ;
		this.theRenderEngine = texman;
		this.particleMaxAge = 140 + rand.nextInt(20);
		this.particleGravity = 2F;

		this.momentumYaw = (float) rand.nextGaussian() * 15F;
		this.momentumPitch = (float) rand.nextGaussian() * 15F;
	}
	
	@Override
	public int getFXLayer() {
		return 3;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();

		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;
		
		if(!this.onGround) {
			this.rotationPitch += this.momentumPitch;
			this.rotationYaw += this.momentumYaw;
			
			EntityFX fx = new net.minecraft.client.particle.EntityBlockDustFX(worldObj, posX, posY, posZ, 0, 0, 0, Blocks.redstone_block, 0);
			ReflectionHelper.setPrivateValue(EntityFX.class, fx, 20 + rand.nextInt(20), "particleMaxAge", "field_70547_e");
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
		}
	}
	
	@Override
	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		this.theRenderEngine.bindTexture(texture);
		
		/* use this instead of EntityFX.interpPosN since interpPosN isn't set up correctly for the current tick for layer 3 particles */
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;

		float f10 = this.particleScale * 0.1F;
		float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) interp - dX);
		float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) interp - dY);
		float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - dZ);

		double pX = prevPosX + (posX - prevPosX) * interp;
		double pY = prevPosY + (posY - prevPosY) * interp;
		double pZ = prevPosZ + (posZ - prevPosZ) * interp;
		int brightness = worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(pX), MathHelper.floor_double(pY), MathHelper.floor_double(pZ), 0);
		int lX = brightness % 65536;
		int lY = brightness / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lX / 1.0F, (float)lY / 1.0F);

		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		//tess.setBrightness(240);
		tess.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		tess.addVertexWithUV((double) (f11 - x * f10 - tx * f10), (double) (f12 - y * f10), (double) (f13 - z * f10 - tz * f10), (double) 0, (double) 0);
		tess.addVertexWithUV((double) (f11 - x * f10 + tx * f10), (double) (f12 + y * f10), (double) (f13 - z * f10 + tz * f10), (double) 0, (double) 1);
		tess.addVertexWithUV((double) (f11 + x * f10 + tx * f10), (double) (f12 + y * f10), (double) (f13 + z * f10 + tz * f10), (double) 1, (double) 1);
		tess.addVertexWithUV((double)(f11 + x * f10 - tx * f10), (double)(f12 - y * f10), (double)(f13 + z * f10 - tz * f10), (double)1, (double)0);
		tess.draw();
		GL11.glPopMatrix();
	}
}
