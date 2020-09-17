package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ItemRenderNukeGadget implements IItemRenderer {

	private IModelCustom gadgetModel;
    private ResourceLocation gadgetTexture;
    float f = -1;
	
	public ItemRenderNukeGadget() {
		gadgetModel = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/TheGadget3.obj"));
		gadgetTexture = new ResourceLocation(RefStrings.MODID, "textures/models/TheGadget3_.png");
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
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			GL11.glPushMatrix();
				Minecraft.getMinecraft().renderEngine.bindTexture(gadgetTexture);
				//GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(15.0F, 0.0F, 0.0F, -1.0F);
				GL11.glTranslatef(0.8F, 0.2F, 0.5F);
				GL11.glScalef(0.25F, 0.25F, 0.25F);
		        gadgetModel.renderAll();
			GL11.glPopMatrix();
		default: break;
		}
	}

}
