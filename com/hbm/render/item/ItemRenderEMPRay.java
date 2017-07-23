package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelEMPRay;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderEMPRay implements IItemRenderer {
	
	protected ModelEMPRay swordModel;
	
	public ItemRenderEMPRay() {
		swordModel = new ModelEMPRay();
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
		float f = 0;

		if((Entity)data[1] instanceof EntityPlayer)
			//f = ((EntityPlayer)data[1]).getItemInUseCount() > 0 && ((Entity)data[1]).isSneaking() ? 0.05F : 0;
			f = (((EntityPlayer)data[1]).getItemInUse() != null &&((EntityPlayer)data[1]).getItemInUse().getItemUseAction() == EnumAction.bow) ? 0.15F : 0;
		switch(type) {
		case EQUIPPED_FIRST_PERSON:
			GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_CULL_FACE);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelEMPRay.png"));
				GL11.glRotatef(-135.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(-0.5F, 0.0F, -0.2F);
				//GL11.glScalef(2.0F, 2.0F, 2.0F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				//GL11.glTranslatef(-0.4F, -0.1F, 0.1F);
				GL11.glTranslatef(-0.8F, -0.2F, 0.0F);
				swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, f);
			GL11.glPopMatrix();
			break;
		case EQUIPPED:
		case ENTITY:
			GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_CULL_FACE);
				Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelEMPRay.png"));
				GL11.glRotatef(-200.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(75.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.2F, -0.5F);
				GL11.glRotatef(-5.0F, 0.0F, 0.0F, 1.0F);
				GL11.glTranslatef(0.5F, -0.2F, 0.0F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				GL11.glTranslatef(-1.8F, -0.2F, 0.2F);
				swordModel.render((Entity)data[1], 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, f);
			GL11.glPopMatrix();
		default: break;
		}
	}
}
