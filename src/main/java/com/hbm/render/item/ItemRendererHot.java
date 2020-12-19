package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.special.ItemHot;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererHot implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
		
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		
		float d = (float)ItemHot.getHeat(item);
		
		//GL11.glColor3f(1F, 1F - d * 0.25F, d);
		
		/*if(currentItem != null)
			RenderItemStack.renderItemStack(0, 0, 1.0F, currentItem);
		else
			RenderItemStack.renderItemStack(0, 0, 1.0F, item);*/
		
		GL11.glPopMatrix();
	}

}
