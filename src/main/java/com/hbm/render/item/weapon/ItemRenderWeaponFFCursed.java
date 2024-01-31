package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponFFCursed implements IItemRenderer {

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
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 1.5D;
			GL11.glTranslated(0.75, 0.5, 0);
			GL11.glScaled(s0, s0, s0);
			GL11.glRotated(100, 0, -1, 0);
			GL11.glRotated(20, 1, 0, 0);
		
			GL11.glShadeModel(GL11.GL_SMOOTH);

			HbmAnimations.applyRelevantTransformation("Body");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ff_cursed);
			ResourceManager.cursed_revolver.renderPart("Body");
			ResourceManager.cursed_revolver.renderPart("Barrel");
			ResourceManager.cursed_revolver.renderPart("Grip");
			
			GL11.glPushMatrix();
			HbmAnimations.applyRelevantTransformation("Slide");
			ResourceManager.cursed_revolver.renderPart("Slide");
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			HbmAnimations.applyRelevantTransformation("Magazine");
			ResourceManager.cursed_revolver.renderPart("Magazine");
			GL11.glPopMatrix();

			GL11.glShadeModel(GL11.GL_FLAT);
			
			GL11.glPopMatrix();
			
			return;
			
		case EQUIPPED:

			double scale = 1.5D;
			GL11.glRotated(195, 0, 1, 0);
			GL11.glRotated(-10, 0, 0, 1);
			GL11.glRotated(-10, 1, 0, 0);
			GL11.glTranslated(-0.5, 0.1, -0.25);
			GL11.glScaled(scale, scale, scale);
			
			break;
			
		case ENTITY:

			double s1 = 1D;
			GL11.glScaled(s1, s1, s1);
			GL11.glRotated(90, 0, 1, 0);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 17D;
			GL11.glTranslated(10, 11.5, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ff_cursed);
		ResourceManager.cursed_revolver.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
