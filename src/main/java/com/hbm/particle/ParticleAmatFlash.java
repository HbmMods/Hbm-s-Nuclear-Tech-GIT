package com.hbm.particle;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleAmatFlash extends EntityFX {

	public ParticleAmatFlash(World world, double x, double y, double z, float scale) {
		super(world, x, y, z);
		this.particleMaxAge = 10;
		this.particleScale = scale;
	}

	public int getFXLayer() {
		return 3;
	}

	public void renderParticle(Tessellator tess, float interp, float x, float y, float z, float tx, float tz) {

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double dX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)interp;
		double dY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)interp;
		double dZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)interp;
		
		float pX = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double) interp - dX));
		float pY = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double) interp - dY));
		float pZ = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double) interp - dZ));


		GL11.glPushMatrix();
		GL11.glTranslatef(pX, pY, pZ);

		GL11.glScalef(0.2F * particleScale, 0.2F * particleScale, 0.2F * particleScale);

		double intensity = (double) (this.particleAge + interp) / (double) this.particleMaxAge;
		double inverse = 1.0D - intensity;

		Tessellator tessellator = Tessellator.instance;
		RenderHelper.disableStandardItemLighting();

		Random random = new Random(432L);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDepthMask(false);

		float scale = 0.5F;

		for(int i = 0; i < 100; i++) {

			GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);

			float vert1 = (random.nextFloat() * 20.0F + 5.0F + 1 * 10.0F) * (float) (intensity * scale);
			float vert2 = (random.nextFloat() * 2.0F + 1.0F + 1 * 2.0F) * (float) (intensity * scale);

			tessellator.startDrawing(6);

			tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, (float) inverse);
			tessellator.addVertex(0.0D, 0.0D, 0.0D);
			tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.0F);
			tessellator.addVertex(-0.866D * vert2, vert1, -0.5F * vert2);
			tessellator.addVertex(0.866D * vert2, vert1, -0.5F * vert2);
			tessellator.addVertex(0.0D, vert1, 1.0F * vert2);
			tessellator.addVertex(-0.866D * vert2, vert1, -0.5F * vert2);
			tessellator.draw();
		}

		GL11.glPopMatrix();

		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();
		
		/*GL11.glScalef(0.2F * particleScale, 0.2F * particleScale, 0.2F * particleScale);

		double intensity = (double) this.particleAge / (double) this.particleMaxAge;
		double inverse = 1.0D - intensity;

		Tessellator tessellator = Tessellator.instance;
		RenderHelper.disableStandardItemLighting();

		Random random = new Random(432L);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDepthMask(false);

		GL11.glPushMatrix();

		float scale = 0.002F;

		for(int i = 0; i < 300; i++) {

			GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);

			float vert1 = (random.nextFloat() * 20.0F + 5.0F + 1 * 10.0F) * (float) (intensity * scale);
			float vert2 = (random.nextFloat() * 2.0F + 1.0F + 1 * 2.0F) * (float) (intensity * scale);

			tessellator.startDrawing(6);

			tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, (float) inverse);
			tessellator.addVertex(x + 0.0D, y + 0.0D, z + 0.0D);
			tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.0F);
			tessellator.addVertex(-0.866D * vert2, vert1, -0.5F * vert2);
			tessellator.addVertex(0.866D * vert2, vert1, -0.5F * vert2);
			tessellator.addVertex(0.0D, vert1, 1.0F * vert2);
			tessellator.addVertex(-0.866D * vert2, vert1, -0.5F * vert2);
			tessellator.draw();
		}

		GL11.glPopMatrix();

		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();*/
	}
}
