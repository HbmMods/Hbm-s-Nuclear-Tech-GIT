package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderDetonatorLaser implements IItemRenderer {
	
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

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.detonator_laser_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.25D;
			GL11.glScaled(s0, s0, s0);
			
			GL11.glRotatef(80F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-20F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(1.0F, 0.5F, 3.0F);
			
			break;
			
		case EQUIPPED:

			double scale = 0.125D;
			GL11.glScaled(-scale, -scale, -scale);
			GL11.glRotatef(85F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(145F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(-0.5F, -1.0F, 6.5F);
			break;
			
		case ENTITY:

			double s1 = 0.25D;
			GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
			GL11.glScaled(s1, s1, s1);
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 3.5D;
			GL11.glScaled(s, s, -s);
			GL11.glTranslatef(1.5F, 2.75F, 0.0F);
			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-45F, 1.0F, 0.0F, 0.0F);
			
			break;
			
		default: break;
		}

		ResourceManager.detonator_laser.renderPart("Main");
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(1F, 0F, 0F);
		ResourceManager.detonator_laser.renderPart("Lights");
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);

		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
