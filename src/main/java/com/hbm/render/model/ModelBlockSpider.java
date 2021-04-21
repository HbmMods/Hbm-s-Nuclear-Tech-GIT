package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBlockSpider extends ModelBase {
	
	private final RenderBlocks field_147920_a = new RenderBlocks();

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		
		GL11.glPushMatrix();

		GL11.glRotatef(90, 0, -1, 0);
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glTranslatef(0, -1.5F, 0);

		float rot = -(MathHelper.cos(f * 0.6662F * 2.0F + 0.0F) * 0.4F) * f1 * 57.3F;

		int blockid = entity.getDataWatcher().getWatchableObjectInt(12);
		int meta = entity.getDataWatcher().getWatchableObjectInt(13);
		Block block = Block.getBlockById(blockid);
		
		if(block == null) {
			GL11.glPopMatrix();
			return;
		}

		GL11.glPushMatrix();
		GL11.glTranslated(0, rot * 0.005, 0);
		GL11.glRotatef(rot, 0, 1, 0);
		ResourceManager.spider.renderPart("Leg1");
		ResourceManager.spider.renderPart("Leg3");
		ResourceManager.spider.renderPart("Leg5");
		ResourceManager.spider.renderPart("Leg7");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, rot * -0.005, 0);
		GL11.glRotatef(rot, 0, -1, 0);
		ResourceManager.spider.renderPart("Leg2");
		ResourceManager.spider.renderPart("Leg4");
		ResourceManager.spider.renderPart("Leg6");
		ResourceManager.spider.renderPart("Leg8");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		GL11.glTranslated(0, 0.75, 0);
		this.field_147920_a.renderBlockAsItem(block, meta, entity.getBrightness(f5));
		GL11.glPopMatrix();
		//this.field_147920_a.setRenderBoundsFromBlock(block);
		//this.field_147920_a.renderBlockSandFalling(block, entity.worldObj, (int)Math.floor(entity.posX), (int)Math.floor(entity.posY), (int)Math.floor(entity.posZ), blockid);
		
		GL11.glPopMatrix();
	}

}
