package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.render.util.RenderItemStack;
import com.hbm.render.util.RenderMiscEffects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererMeteorSword implements IItemRenderer {

	float r;
	float g;
	float b;

	public ItemRendererMeteorSword(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
		return type != ItemRenderType.ENTITY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(-90, 0, 0, 1);
			GL11.glTranslated(0.5, 0.5, 0);
			GL11.glTranslated(-0.5, -0.5, 0);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(-90, 0, 0, 1);
			GL11.glTranslated(0.5, 0.5, 0);
			GL11.glScaled(1.36 * 2, 1.36 * 2, 0.68 * 2);
			GL11.glTranslated(-0.5, -0.5, 0.25);
			break;
		case EQUIPPED:
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(-90, 0, 0, 1);
			GL11.glTranslated(0.2, 0.55, 0);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(-45, 0, 0, 1);
			GL11.glScaled(1.7, 1.7, 0.85);
			break;
		case INVENTORY:
			break;
			
		default: break;
		}

		if(data.length > 1 && data[1] instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) data[1];
			IIcon iicon = entity.getItemIcon(item, 0);

			if(iicon == null) {
				return;
			}

			Minecraft.getMinecraft().getTextureManager().bindTexture(Minecraft.getMinecraft().getTextureManager().getResourceLocation(item.getItemSpriteNumber()));
			TextureUtil.func_152777_a(false, false, 1.0F);
			Tessellator tessellator = Tessellator.instance;
			ItemRenderer.renderItemIn2D(tessellator, iicon.getMaxU(), iicon.getMinV(), iicon.getMinU(), iicon.getMaxV(), iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
			renderGlint3D(tessellator, 0.0625F);
		} else {
			RenderItemStack.renderItemStackNoEffect(0, 0, 0, item);
			renderGlintFlat();
		}

		GL11.glPopMatrix();
	}
	
	public void renderGlintFlat() {

		Minecraft mc = Minecraft.getMinecraft();
		mc.renderEngine.bindTexture(RenderMiscEffects.glint);

		GL11.glDepthFunc(GL11.GL_EQUAL);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);

		for(int j1 = 0; j1 < 2; ++j1) {
			OpenGlHelper.glBlendFunc(772, 1, 0, 0);
			float scaleU = 0.00390625F;
			float scaleV = 0.00390625F;
			float anim = (float) (Minecraft.getSystemTime() % (long) (3000 + j1 * 1873)) / (3000.0F + (float) (j1 * 1873)) * 256.0F;
			float offsetV = 0.0F;
			Tessellator tessellator = Tessellator.instance;
			float sizeMultU = 4.0F;

			if(j1 == 1) {
				sizeMultU = -1.0F;
			}

			float in = 0.36F;
			GL11.glColor4f(r * in, g * in, b * in, 1.0F);

			int x = 0;
			int sizeX = 16;
			int y = 0;
			int sizeY = 16;
			int zLevel = 0;

			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(x + 0,		y + sizeY,	zLevel,	(anim + sizeY * sizeMultU) * scaleU,			(offsetV + sizeY) * scaleV);
			tessellator.addVertexWithUV(x + sizeX,	y + sizeY,	zLevel,	(anim + sizeX + sizeY * sizeMultU) * scaleU,	(offsetV + sizeY) * scaleV);
			tessellator.addVertexWithUV(x + sizeX,	y + 0,		zLevel,	(anim + sizeX) * scaleU,				(offsetV + 0.0F) * scaleV);
			tessellator.addVertexWithUV(x + 0,		y + 0,		zLevel,	(anim + 0.0F) * scaleU,					(offsetV + 0.0F) * scaleV);
			tessellator.draw();
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
	}
	
	public void renderGlint3D(Tessellator tessellator, float depth) {

		Minecraft mc = Minecraft.getMinecraft();
		mc.renderEngine.bindTexture(RenderMiscEffects.glint);
		
		GL11.glDepthFunc(GL11.GL_EQUAL);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);

		float in = 0.36F;
		GL11.glColor4f(r * in, g * in, b * in, 1.0F);
		
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		
		GL11.glPushMatrix();
		float scale = 0.125F;
		GL11.glScalef(scale, scale, scale);
		float offset = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
		GL11.glTranslatef(offset, 0.0F, 0.0F);
		GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
		ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, depth);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(scale, scale, scale);
		offset = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
		GL11.glTranslatef(-offset, 0.0F, 0.0F);
		GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
		ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, depth);
		GL11.glPopMatrix();
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
	}
}
