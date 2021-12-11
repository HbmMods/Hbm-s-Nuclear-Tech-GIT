package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderGabrielsHorn implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case INVENTORY:
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.gabriels_horn_tex);
		GL11.glPushMatrix();
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.5D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(2, 0, 1);
			GL11.glRotated(-100, 0, 1, 0);
			GL11.glRotated(90, 0, -1, -1);
			GL11.glScaled(s0, s0, s0);
			GL11.glTranslated(-2, 0, -1);
				
			break;
			
		case EQUIPPED:

			double scale = 0.5D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(20F, -2.0F, 0.0F, 1.0F);
			GL11.glRotatef(-170, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-15F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(90, 0, -1, -1.5F);
			GL11.glTranslatef(-2F, -0.7F, 0.7F);
			
			break;
			
		case ENTITY:

			double s1 = 0.5D;
			GL11.glScaled(s1, s1, s1);
			GL11.glTranslatef(0F, 1F, 0F);
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 5D;
			GL11.glTranslated(3, 3, 0);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(135, 1, 0, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;	
		}
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.gabriel_horn.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}