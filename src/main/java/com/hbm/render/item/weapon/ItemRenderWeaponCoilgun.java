package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponCoilgun implements IItemRenderer {
	
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

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.coilgun_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.25D;
			GL11.glRotated(20, 0, 0, 1);
			GL11.glTranslated(0.75, 0.3, -0.5);
			GL11.glRotated(-10, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);

			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
			GL11.glTranslated(-1.5 - recoil[0] * 0.5, 0, 0);
			GL11.glRotated(recoil[0] * 45, 0, 0, 1);
			GL11.glTranslated(1.5, 0, 0);

			double[] reload = HbmAnimations.getRelevantTransformation("RELOAD");
			GL11.glTranslated(-2.5, 0, 0);
			GL11.glRotated(reload[0] * -45, 0, 0, 1);
			GL11.glTranslated(2.5, 0, 0);
			
			break;
			
		case EQUIPPED:

			double scale = 0.25D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-80, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-15F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(1.25F, 0.5F, -1.75F);
			
			break;
			
		case ENTITY:

			double s1 = 0.2D;
			GL11.glScaled(s1, s1, s1);
			
			break;
			
		case INVENTORY:
			
			double s = 3.5D;
			GL11.glTranslated(6, 8, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.coilgun.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
