package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.hbm.entity.projectile.EntityBullet;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelBullet;

@SideOnly(Side.CLIENT)
public class RenderRocket extends Render {

	private ModelBullet miniNuke;

	public RenderRocket() {
		miniNuke = new ModelBullet();
	}

	@Override
	public void doRender(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
		GL11.glRotatef(rocket.prevRotationYaw + (rocket.rotationYaw - rocket.prevRotationYaw) * p_76986_9_ - 90.0F,
				0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rocket.prevRotationPitch + (rocket.rotationPitch - rocket.prevRotationPitch) * p_76986_9_ + 180,
				0.0F, 0.0F, 1.0F);
		GL11.glScalef(1.5F, 1.5F, 1.5F);
		

		GL11.glRotatef(new Random(rocket.getEntityId()).nextInt(360),
				1.0F, 0.0F, 0.0F);

		if (rocket instanceof EntityBullet && ((EntityBullet) rocket).getIsChopper()) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/emplacer.png"));
		} else if (rocket instanceof EntityBullet && ((EntityBullet) rocket).getIsCritical()) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/tau.png"));
		} else if (rocket instanceof EntityBullet) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/bullet.png"));
		}
		miniNuke.renderAll(0.0625F);
		
		//renderFlechette();
		//renderDart();
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		if (p_110775_1_ instanceof EntityBullet && ((EntityBullet) p_110775_1_).getIsChopper()) {
			return new ResourceLocation(RefStrings.MODID + ":textures/models/emplacer.png");
		} else if (p_110775_1_ instanceof EntityBullet && ((EntityBullet) p_110775_1_).getIsCritical()) {
			return new ResourceLocation(RefStrings.MODID + ":textures/models/tau.png");
		} else if (p_110775_1_ instanceof EntityBullet) {
			return new ResourceLocation(RefStrings.MODID + ":textures/models/bullet.png");
		}

		return null;
	}
	
	private void renderFlechette() {
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		
        GL11.glScalef(1F/16F, 1F/16F, 1F/16F);
        GL11.glScalef(-1, 1, 1);
        
		Tessellator tess = Tessellator.instance;
		
		//back
		GL11.glColor3f(0.15F, 0.15F, 0.15F);
		tess.startDrawingQuads();
		tess.addVertex(0, 1, -1);
		tess.addVertex(0, -1, -1);
		tess.addVertex(0, -1, 1);
		tess.addVertex(0, 1, 1);
		tess.draw();
		
		//base
		tess.startDrawingQuads();
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(0, -1, -1);
		tess.addVertex(0, 1, -1);
		tess.addVertex(1, 0.5, -0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertex(0, -1, 1);
		tess.addVertex(1, -0.5, 0.5);
		tess.addVertex(1, 0.5, 0.5);
		tess.addVertex(0, 1, 1);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertex(0, -1, -1);
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(1, -0.5, 0.5);
		tess.addVertex(0, -1, 1);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(0, 1, -1);
		tess.addVertex(0, 1, 1);
		tess.addVertex(1, 0.5, 0.5);
		tess.draw();

		//pin
		tess.startDrawing(4);
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(6, 0, 0);
		tess.draw();

		tess.startDrawing(4);
		tess.addVertex(1, -0.5, 0.5);
		tess.addVertex(6, 0, 0);
		tess.addVertex(1, 0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(6, 0, 0);
		tess.addVertex(1, -0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(1, 0.5, 0.5);
		tess.addVertex(6, 0, 0);
		tess.draw();
		

        GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPopMatrix();
	}
	
	private void renderDart() {
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(false);

        GL11.glScalef(1F/4F, 1F/8F, 1F/8F);
        GL11.glScalef(-1, 1, 1);
        
		Tessellator tess = Tessellator.instance;
		
		float red = 0.25F;
		float green = 0.0F;
		float blue = 1.0F;
		
		//front
		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, -1);
		tess.addVertex(3, 1, -1);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, 1);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, 1, 1);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, -1);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, 1);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, 1, -1);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, 1, 1);
		tess.draw();
		
		//mid
		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, 0.5, -0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, 0.5);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, 0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, -0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, 0.5, -0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.draw();
		
		//tail
		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, 0.5, -0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, 0.5, 0.5);
		tess.addVertex(0, 0.5, -0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, -0.5, 0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, -0.5, 0.5);
		tess.addVertex(0, -0.5, -0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, 0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, 0.5, 0.5);
		tess.addVertex(0, -0.5, 0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, 0.5, -0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, 0.5, -0.5);
		tess.addVertex(0, -0.5, -0.5);
		tess.draw();
		
        GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);
		
		GL11.glPopMatrix();
	}

}