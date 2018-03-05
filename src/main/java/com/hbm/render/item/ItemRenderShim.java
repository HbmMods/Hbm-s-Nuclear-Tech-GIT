package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelSword;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemRenderShim implements IItemRenderer {
	
	public ItemRenderShim() { }

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
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.shimmer_sledge_tex);
				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
				GL11.glScalef(1.5F, 1.5F, 1.5F);
				GL11.glTranslatef(0.45F, -0.3F, 0.0F);
				ResourceManager.shimmer_sledge.renderAll();
			GL11.glPopMatrix();
		default: break;
		}
	}
}
