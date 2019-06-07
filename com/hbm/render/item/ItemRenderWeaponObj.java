package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelGustav;
import com.hbm.render.model.ModelPanzerschreck;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemRenderWeaponObj implements IItemRenderer {
	
	public ItemRenderWeaponObj() { }

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
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);

		if(item.getItem() == ModItems.gun_hk69)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.hk69_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			GL11.glTranslatef(1.0F, 0.5F, 0.0F);
			GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-25F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-10F, 0.0F, 1.0F, 0.0F);
			GL11.glScaled(0.75, 0.75, 0.75);
			
			if(Minecraft.getMinecraft().thePlayer.isSneaking()) {
				GL11.glTranslatef(1.16F, 0.35F, -0.8F);
				GL11.glRotatef(5.5F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(4F, 1.0F, 0.0F, 0.0F);
			}
			break;
			
		case EQUIPPED:
			GL11.glRotatef(20F, 1.0F, 0.0F, 1.0F);
			GL11.glRotatef(10F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.4F, 0.0F, 0.55F);
			GL11.glScaled(0.75, 0.75, 0.75);
			break;
			
		case ENTITY:
			GL11.glTranslatef(0.0F, 0.2F, 0.0F);
			GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
			GL11.glScaled(0.75, 0.75, 0.75);
			break;
			
		default: break;
		}

		if(item.getItem() == ModItems.gun_hk69)
			ResourceManager.hk69.renderAll();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
