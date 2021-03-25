package com.hbm.render.util;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class RenderItemStack {
	
	public static RenderItem renderItem = new RenderItem();
	
	public static void renderItemStack(int x, int y, float f0, ItemStack stack) {
		
		Minecraft mc = Minecraft.getMinecraft();
		
		if (stack != null) {
			
			float f1 = stack.animationsToGo - f0;
			
			if (f1 > 0.0F) {
				GL11.glPushMatrix();
				float f2 = 1.0F + f1 / 5.0F;
				GL11.glTranslatef(x + 8, y + 12, 0.0F);
				GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef(-(x + 8), -(y + 12), 0.0F);
			}
			renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
			
			if (f1 > 0.0F)
				GL11.glPopMatrix();
			
			renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
		}
	}
	
	public static void renderItemStackDry(int x, int y, float f0, ItemStack stack) {
		
		Minecraft mc = Minecraft.getMinecraft();
		
		if (stack != null) {
			
			float f1 = stack.animationsToGo - f0;
			
			if (f1 > 0.0F) {
				GL11.glPushMatrix();
				float f2 = 1.0F + f1 / 5.0F;
				GL11.glTranslatef(x + 8, y + 12, 0.0F);
				GL11.glScalef(1.0F / f2, (f2 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef(-(x + 8), -(y + 12), 0.0F);
			}
			renderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
			
			if (f1 > 0.0F)
				GL11.glPopMatrix();
			
			renderItem.renderItemIntoGUI(mc.fontRenderer, mc.renderEngine, stack, x, y);
		}
	}
}
