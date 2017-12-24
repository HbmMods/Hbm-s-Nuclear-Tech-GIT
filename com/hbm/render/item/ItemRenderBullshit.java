package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelBaleflare;
import com.hbm.render.model.ModelDash;
import com.hbm.render.model.ModelDefabricator;
import com.hbm.render.model.ModelEuthanasia;
import com.hbm.render.model.ModelHP;
import com.hbm.render.model.ModelJack;
import com.hbm.render.model.ModelMP40;
import com.hbm.render.model.ModelPip;
import com.hbm.render.model.ModelSpark;
import com.hbm.render.model.ModelTwiGun;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemRenderBullshit implements IItemRenderer {

	protected ModelSpark sparkPlug;
	protected ModelPip pip;
	protected ModelMP40 mp40;
	protected ModelBaleflare bomb;
	
	public ItemRenderBullshit() {
		sparkPlug = new ModelSpark();
		pip = new ModelPip();
		mp40 = new ModelMP40();
		bomb = new ModelBaleflare();
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
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_CULL_FACE);
				
				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.5F, 0.0F, -0.2F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glTranslatef(-0.2F, -0.1F, -0.1F);
				
				renderWhatever(type, item, data);
				
			GL11.glPopMatrix();
			break;
		case EQUIPPED:
		case ENTITY:
			GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_CULL_FACE);
				
				GL11.glRotatef(-200.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(75.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.2F, -0.5F);
				GL11.glRotatef(-5.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(0.5F, -0.2F, 0.0F);
				//GL11.glScalef(0.75F, 0.75F, 0.75F);
				GL11.glTranslatef(-1.4F, 0.0F, 0.0F);
				
				renderWhatever(type, item, data);
				
			GL11.glPopMatrix();
		default: break;
		}
	}
	
	private void renderWhatever(ItemRenderType type, ItemStack item, Object... data) {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelMP40.png"));
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		mp40.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelSpark.png"));
		GL11.glScalef(4/3F, 4/3F, 4/3F);
		GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
		sparkPlug.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelPip.png"));
		GL11.glTranslatef(0.0F, 0.2F, 0.0F);
		GL11.glTranslatef(0.5F, 0.0F, 0.0F);
		GL11.glScalef(0.75F, 0.75F, 0.75F);
		pip.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/BaleFlare.png"));
		GL11.glScalef(4/3F, 4/3F, 4/3F);
		GL11.glTranslatef(-1.5F, 0.0F, 0.0F);
		bomb.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
	}
}
