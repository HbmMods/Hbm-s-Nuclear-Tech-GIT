package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelMP;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderMP implements IItemRenderer {
	
	protected ModelMP swordModel;
	
	public ItemRenderMP() {
		swordModel = new ModelMP();
	}

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
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelMP.png"));
		
		switch(type) {
		case EQUIPPED_FIRST_PERSON:
			GL11.glTranslatef(1.0F, 0.75F, 0.0F);
			GL11.glRotatef(150F, 0.0F, 0.0F, -1.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
			
		case EQUIPPED:
			GL11.glTranslatef(0.35F, 0.5F, 0.0F);
			GL11.glRotatef(100F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(170F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(15F, 0.0F, 0.0F, -1.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
			
		case ENTITY:
			GL11.glTranslatef(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
			
		default: break;
		}
		
		GL11.glPopMatrix();
	}
}
