package com.hbm.render.item.block;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelSatelliteReceiver;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderSatelliteReceiver implements IItemRenderer {
	
	protected ModelSatelliteReceiver swordModel;
	
	public ItemRenderSatelliteReceiver() {
		swordModel = new ModelSatelliteReceiver();
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
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type) {
		case ENTITY:
			GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/PoleSatelliteReceiver.png"));
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -1.0F, 0.0F);
				swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
		case EQUIPPED:
			GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/PoleSatelliteReceiver.png"));
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glTranslatef(0.8F, -0.3F, 0.2F);
				GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
				swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			GL11.glPopMatrix();
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/PoleSatelliteReceiver.png"));
				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.6F, -0.6F, -0.1F);
				GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
			swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
			break;
		default: break;
		}
	}

}
