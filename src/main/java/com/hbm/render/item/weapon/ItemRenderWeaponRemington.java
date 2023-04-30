package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponRemington implements IItemRenderer {
	
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
		GL11.glShadeModel(GL11.GL_SMOOTH);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.remington_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double[] recoilT = HbmAnimations.getRelevantTransformation("RECOIL_TRANSLATE");
			double[] pump = HbmAnimations.getRelevantTransformation("PUMP");
			
			double s0 = 0.35D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glRotated(-10, 0, 1, 0);
			GL11.glTranslated(1.25, -1.25, -0.25);
			GL11.glScaled(s0, s0, s0);
			
			GL11.glTranslated(recoilT[2], 0, 0);
			
			ResourceManager.remington.renderPart("Gun");
			
			GL11.glTranslated(pump[2] * 0.5, 0, 0);
			
			ResourceManager.remington.renderPart("Pump");
			
			break;
			
		case EQUIPPED:

			double scale = 0.25D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-80, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-15F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(2.5F, -3.5F, -2F);
			
			ResourceManager.remington.renderAll();
			
			break;
			
		case ENTITY:

			double s1 = 0.25D;
			GL11.glScaled(s1, s1, s1);
			GL11.glTranslated(0, -3, 0);
			
			ResourceManager.remington.renderAll();
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 1.25D;
			GL11.glTranslated(4, 11, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glScaled(s, s, -s);
			
			ResourceManager.remington.renderAll();
			
			break;
			
		default: break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
