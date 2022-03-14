package com.hbm.render.item;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderDetonatorLaser implements IItemRenderer {
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
		case INVENTORY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.detonator_laser_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.25D;
			GL11.glScaled(s0, s0, s0);
			
			GL11.glRotatef(80F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-20F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(1.0F, 0.5F, 3.0F);
			
			break;
			
		case EQUIPPED:

			double scale = 0.125D;
			GL11.glScaled(-scale, -scale, -scale);
			GL11.glRotatef(85F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(145F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -1.0F, 6.5F);
			break;
			
		case ENTITY:

			double s1 = 0.25D;
			GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
			GL11.glScaled(s1, s1, s1);
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 3.5D;
			GL11.glScaled(s, s, -s);
			GL11.glTranslatef(1.5F, 2.75F, 0.0F);
			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-45F, 1.0F, 0.0F, 0.0F);
			
			break;
			
		default: break;
		}

		ResourceManager.detonator_laser.renderPart("Main");
		
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(1F, 0F, 0F);
		ResourceManager.detonator_laser.renderPart("Lights");
		GL11.glColor3f(1F, 1F, 1F);
		
		GL11.glPushMatrix();
		
		float px = 0.0625F;
		GL11.glTranslatef(0.5626F, px * 18, -px * 14);
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_QUADS);
		
		int sub = 32;
		double width = px * 8;
		double len = width / sub;
		double time = System.currentTimeMillis() / -100D;
		double amplitude = 0.075;
		
		tess.setColorOpaque_I(0xffff00);
		
		for(int i = 0; i < sub; i++) {
			double h0 = Math.sin(i * 0.5 + time) * amplitude;
			double h1 = Math.sin((i + 1) * 0.5 + time) * amplitude;
			tess.addVertex(0, -px * 0.25 + h1, len * (i + 1));
			tess.addVertex(0, px * 0.25 + h1, len * (i + 1));
			tess.addVertex(0, px * 0.25 + h0, len * i);
			tess.addVertex(0, -px * 0.25 + h0, len * i);
		}
		tess.setColorOpaque_F(1F, 1F, 1F);
		
		tess.draw();
		
		GL11.glPopMatrix();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glPushMatrix();
		String s;
		Random rand = new Random(System.currentTimeMillis() / 500);
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		float f3 = 0.01F;
		GL11.glTranslatef(0.5625F, 1.3125F, 0.875F);
		GL11.glScalef(f3, -f3, f3);
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);

		GL11.glTranslatef(3F, -2F, 0.2F);
		
		for(int i = 0; i < 3; i++) {
			s = (rand.nextInt(900000) + 100000) + "";
			font.drawString(s, 0, 0, 0xff0000);
			GL11.glTranslatef(0F, 12.5F, 0F);
		}
		GL11.glPopMatrix();
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
