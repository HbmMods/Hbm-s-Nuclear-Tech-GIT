package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponCryoCannon implements IItemRenderer {
	
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
		GL11.glEnable(GL11.GL_LIGHTING);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.cryocannon_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.25D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(1, 0, -0.3);
			GL11.glRotated(80, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			
			break;
			
		case EQUIPPED:

			double scale = 0.5D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(10F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.75F, -2.5F, 3.5F);
			
			break;
			
		case ENTITY:

			double s1 = 0.25D;
			GL11.glScaled(s1, s1, s1);
			GL11.glTranslated(0, 0, 3);
			
			break;
			
		case INVENTORY:
			
			double s = 2.5D;
			GL11.glTranslated(1, 6, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.cryocannon.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
