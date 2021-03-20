package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public abstract class ItemRenderBase implements IItemRenderer {

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
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		if(type == ItemRenderType.INVENTORY) {
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glTranslated(8, 10, 0);
			GL11.glRotated(-30, 1, 0, 0);
			GL11.glRotated(45, 0, 1, 0);
			GL11.glScaled(-1, -1, -1);
			renderInventory();
		} else {
			
			if(type != ItemRenderType.ENTITY)
				GL11.glTranslated(0.5, 0.25, 0);
			else
				GL11.glScaled(1.5, 1.5, 1.5);
			
			GL11.glScaled(0.25, 0.25, 0.25);
			GL11.glRotated(90, 0, 1, 0);
			renderNonInv();
		}
		renderCommon();
		GL11.glPopMatrix();
	}

	public void renderNonInv() { }
	public void renderInventory() { }
	public void renderCommon() { }
}
