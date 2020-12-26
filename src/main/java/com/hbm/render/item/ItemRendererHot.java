package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.special.ItemHot;
import com.hbm.render.util.RenderItemStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererHot implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
		return type == ItemRenderType.INVENTORY;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		RenderHelper.enableGUIStandardItemLighting();
		
		Minecraft mc = Minecraft.getMinecraft();
		RenderItemStack.renderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, item, 0, 0);
		
		double h = ItemHot.getHeat(item);
		if(h > 0) {

            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glColor4f(1F, 1F, 1F, (float) h);
            RenderItem.getInstance().renderIcon(0, 0, ((ItemHot)item.getItem()).hotIcon, 16, 16);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
		}
		
		GL11.glPopMatrix();
	}
}
