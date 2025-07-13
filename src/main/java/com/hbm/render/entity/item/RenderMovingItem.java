package com.hbm.render.entity.item;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.item.EntityMovingItem;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderMovingItem extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f1, float f2) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		Random rand = new Random(entity.getEntityId());
		GL11.glTranslated(0, rand.nextDouble() * 0.0625, 0);

		EntityMovingItem item = (EntityMovingItem) entity;
		ItemStack stack = item.getItemStack();

		if(!(stack.getItemSpriteNumber() == 0 && stack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType()))) {
			GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslated(0.0, -0.1875, 0.0);
			
			if(!this.renderManager.options.fancyGraphics) {
				GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
			}
		}

		if(item.cacheForRender != null) {
			item.cacheForRender.hoverStart = 0.0F;

			RenderItem.renderInFrame = true;
			RenderManager.instance.renderEntityWithPosYaw(item.cacheForRender, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			RenderItem.renderInFrame = false;
		}

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
