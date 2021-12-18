package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponFFMG42 implements IItemRenderer {
	
	public ItemRenderWeaponFFMG42() { }

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

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.mg42_tex);
		boolean renderMag = true;
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.65D;
			GL11.glRotated(170, 0, 1, 0);
			GL11.glRotated(-25, 0, 0, 1);
			GL11.glTranslated(-0.125, 0, 0);
			GL11.glScaled(s0, s0, s0);
			
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
			GL11.glTranslated(recoil[0] * 0.25, 0, 0);
			
			double[] reload = HbmAnimations.getRelevantTransformation("MAG");
			GL11.glRotated(reload[1] * 15, 1, 0, 0);
			
			GL11.glPushMatrix();
			
			GL11.glRotated(reload[1] * -45, 1, 0, 0);
			GL11.glTranslated(0, reload[1] * 0.25, -reload[1]);
			ResourceManager.mg42.renderPart("Mag");
			GL11.glPopMatrix();
			
			renderMag = false;
			
			break;
			
		case EQUIPPED:

			double scale = 0.35D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(100, 0, 1, 0);
			GL11.glRotated(-10, 1, 0, 0);
			GL11.glRotated(10, 0, 0, 1);
			GL11.glTranslated(-0.5, 0.75, 1.25);
			
			break;
			
		case ENTITY:

			double s1 = 0.3D;
			GL11.glScaled(s1, s1, s1);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 1.75D;
			GL11.glTranslated(10, 9.5, 0);
			GL11.glRotated(180, 1, 0, 0);
			GL11.glRotated(-45, 0, 0, 1);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}

		ResourceManager.mg42.renderPart("Gun");
		if(renderMag) ResourceManager.mg42.renderPart("Mag");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
