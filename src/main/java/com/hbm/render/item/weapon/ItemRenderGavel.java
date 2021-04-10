package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderGavel implements IItemRenderer {
	
	public ItemRenderGavel() { }

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			return true;
		default: return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if(item.getItem() == ModItems.wood_gavel)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.gavel_wood);
		if(item.getItem() == ModItems.lead_gavel)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.gavel_lead);
		if(item.getItem() == ModItems.diamond_gavel)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.gavel_diamond);
		if(item.getItem() == ModItems.mese_gavel)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.gavel_mese);
		
		switch(type) {
		case EQUIPPED_FIRST_PERSON:

			GL11.glTranslated(1, 0.5, 0);
			
			if(player.isBlocking()) {
				GL11.glTranslated(-0.5, 0, 0);
			}
			
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			
			if(item.getItem() == ModItems.mese_gavel)
				GL11.glScaled(2, 2, 2);
			
			break;

		case ENTITY:
			GL11.glTranslated(-0.5, 0, 0);
		case EQUIPPED:

			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glTranslated(1.375, 0, 0);
			GL11.glRotated(90, 0, 1, 0);
			
			if(item.getItem() == ModItems.mese_gavel) {
				GL11.glScaled(2, 2, 2);
				GL11.glTranslated(0, 0.25, 0);
			}
			
			break;

		default: break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.gavel.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
