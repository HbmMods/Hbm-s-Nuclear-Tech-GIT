package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;


public class ItemRenderCCPlasmaCannon implements IItemRenderer {

	public ItemRenderCCPlasmaCannon() { }
	
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
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if(item.getItem() == ModItems.gun_cc_plasma)
	
		GL11.glTranslated(0.5, 0.5, 0.5);
		GL11.glScaled(0.5, 0.4, 0.5);
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.cc_plasma_tex);
		GL11.glEnable(GL11.GL_CULL_FACE);
		double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
		switch(type){
		case EQUIPPED_FIRST_PERSON:
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(140+recoil[1]*2F, 0, 0, 1);
			GL11.glTranslated(4, 1, 1);
			GL11.glRotated(7, 0, 0, 1);
			GL11.glRotated(180, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);

			
			GL11.glTranslated(-recoil[2]*0.15F, 0, 0);
			break;
		case EQUIPPED:
			GL11.glTranslated(0, -0.1, -0.8);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glScaled(0.5, 0.5, 0.5);
			break;
		case INVENTORY:
			GL11.glTranslated(-0.175, 0.2, 0);
			GL11.glScaled(1.0, 1.1, 1.1);
			GL11.glRotated(-180, 0, 1, 0);
			GL11.glRotated(-45, 0, 0, 1);
			GL11.glRotated(-180, 0, 1, 0);
			break;
		default:break;
		}
		GL11.glShadeModel(GL11.GL_SMOOTH);
		boolean prevBlend = GL11.glGetBoolean(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		ResourceManager.cc_plasma.renderAll();
		if(!prevBlend)
		GL11.glDisable(GL11.GL_BLEND);
	}	
}