package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.render.util.RenderItemStack;
import com.hbm.render.util.RenderMiscEffects;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderTransformer implements IItemRenderer {

	double[] rtp;
	double[] ttp;
	double[] stp;
	double[] rfp;
	double[] tfp;
	double[] sfp;
	double[] rir;
	double[] tir;
	double[] sir;
	
	public ItemRenderTransformer(double[] rtp, double[] ttp, double[] stp, double[] rfp, double[] tfp, double[] sfp, double[] rir, double[] tir, double[] sir) {
		this.rtp = rtp;
		this.ttp = ttp;
		this.stp = stp;
		this.rfp = rfp;
		this.tfp = tfp;
		this.sfp = sfp;
		this.rir = rir;
		this.tir = tir;
		this.sir = sir;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type != ItemRenderType.ENTITY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			GL11.glRotated(rfp[1], 0, 1, 0);
			GL11.glRotated(rfp[2], 0, 0, 1);
			GL11.glRotated(rfp[0], 1, 0, 0);
			GL11.glTranslated(0.5, 0.5, 0);
			GL11.glTranslated(-0.5, -0.5, 0);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(-90, 0, 0, 1);
			GL11.glTranslated(0.5, 0.5, 0);
			GL11.glScaled(sfp[0] * 2, sfp[1] * 2, sfp[2] * 2);
			GL11.glTranslated(-0.5, -0.5, 0.25);
			break;
		case EQUIPPED:
			GL11.glRotated(rtp[1], 0, 1, 0);
			GL11.glRotated(rtp[2], 0, 0, 1);
			GL11.glRotated(rtp[0], 1, 0, 0);
			GL11.glTranslated(ttp[0], ttp[1], ttp[2]);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(-45, 0, 0, 1);
			GL11.glScaled(stp[0], stp[1], stp[2]);
			break;
		case INVENTORY:
			GL11.glRotated(rir[0], 1, 0, 0);
			GL11.glRotated(rir[1], 0, 1, 0);
			GL11.glRotated(rir[2], 0, 0, 1);
			GL11.glTranslated(tir[0] * 0.0625, tir[1] * 0.0625, tir[2] * 0.0625);
			GL11.glTranslated(8, 8, 0);
			GL11.glScaled(sir[0], sir[1], sir[2]);
			GL11.glTranslated(-8, -8, 0);
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
			if(item.hasEffect()) renderGlint3D(tessellator, 0.0625F);
		} else {
			RenderItemStack.renderItemStackNoEffect(0, 0, 0, item);
			if(item.hasEffect()) renderGlintFlat();
		}
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

			float in = 0.76F;
			GL11.glColor4f(0.5F * in, 0.25F * in, 0.8F * in, 1.0F);

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

		float in = 0.76F;
		GL11.glColor4f(0.5F * in, 0.25F * in, 0.8F * in, 1.0F);
		
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
