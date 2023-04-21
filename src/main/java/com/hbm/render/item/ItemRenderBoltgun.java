package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderBoltgun implements IItemRenderer {

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
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.boltgun_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.15D;
			GL11.glTranslated(0.5, 0.35, -0.25F);
			GL11.glRotated(15, 0, 0, 1);
			GL11.glRotated(80, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			
			GL11.glPushMatrix();
			double[] anim = HbmAnimations.getRelevantTransformation("RECOIL");
			GL11.glTranslated(0, 0, -anim[0]);
			if(anim[0] != 0) player.isSwingInProgress = false;
			ResourceManager.boltgun.renderPart("Barrel");
			GL11.glPopMatrix();
			
			break;
			
		case EQUIPPED:

			double scale = 0.25D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(10, 0, 1, 0);
			GL11.glRotated(10, 0, 0, 1);
			GL11.glRotated(10, 1, 0, 0);
			GL11.glTranslated(1.5, -0.25, 1);
			
			break;
			
		case ENTITY:

			double s1 = 0.1D;
			GL11.glScaled(s1, s1, s1);
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 1.75D;
			GL11.glTranslated(7, 10, 0);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-135, 1, 0, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}

		ResourceManager.boltgun.renderPart("Gun");
		if(type != type.EQUIPPED_FIRST_PERSON) {
			ResourceManager.boltgun.renderPart("Barrel");
		}
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
