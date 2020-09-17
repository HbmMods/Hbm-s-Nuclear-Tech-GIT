package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponVortex implements IItemRenderer {
	
	public ItemRenderWeaponVortex() { }

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

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.vortex_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.05D;
			GL11.glScaled(s0, s0, s0);
			GL11.glRotated(25, 0, 0, 1);
			GL11.glRotated(-5, 0, 1, 0);
			GL11.glTranslated(17, -5, -5);
			
			break;
			
		case EQUIPPED:

			double scale = 0.075D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(-75, 0, 1, 0);
			GL11.glRotated(-10, 0, 0, 1);
			GL11.glRotated(15, 1, 0, 0);
			GL11.glTranslated(7, -4, -6);
			
			break;
			
		case ENTITY:

			double s1 = 0.05D;
			GL11.glScaled(s1, s1, s1);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 0.3D;
			GL11.glTranslated(7, 11, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}

		ResourceManager.vortex.renderAll();
		
		GL11.glPopMatrix();
	}
}
