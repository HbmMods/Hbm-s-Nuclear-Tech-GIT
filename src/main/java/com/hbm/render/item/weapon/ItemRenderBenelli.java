package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderBenelli implements IItemRenderer
{
	public ItemRenderBenelli() {}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		GL11.glPushMatrix();

		GL11.glShadeModel(GL11.GL_SMOOTH);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.benelli_tex);
		final float scale1 = 0.2F;
		final double scale2 = 0.065D;
		final double scale3 = 0.52D;
		
		switch (type) {
		case EQUIPPED_FIRST_PERSON:// In hand from POV
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			if (player.isSneaking()) {
				GL11.glRotatef(25.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-5F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(-1.007F, 0F, -2.5F);
			}
			else {
				GL11.glRotatef(-10F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(0.0F, -0.5F, -2.5F);
			}

			GL11.glScalef(scale1, scale1, scale1);

			HbmAnimations.applyRelevantTransformation("Body");
			ResourceManager.benelli.renderPart("Body");

			GL11.glPushMatrix();

			HbmAnimations.applyRelevantTransformation("Bolt");
			ResourceManager.benelli.renderPart("Bolt");

			GL11.glPopMatrix();

			GL11.glPushMatrix();

			HbmAnimations.applyRelevantTransformation("Drum");
			ResourceManager.benelli.renderPart("Drum");

			GL11.glPopMatrix();

			GL11.glPushMatrix();

			HbmAnimations.applyRelevantTransformation("Shell");
			ResourceManager.benelli.renderPart("Shell");

			GL11.glPopMatrix();

			break;
		case EQUIPPED:// In hand from other's POV
			GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-170, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-15F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(-0.4F, 0.05F, -0.5F);
			GL11.glScaled(scale2 - scale2 * 2, scale2, scale2);

			GL11.glPushMatrix();
			GL11.glPopMatrix();
			break;
		case ENTITY:// Dropped entity
			GL11.glScaled(0.0625D, 0.0625D, 0.0625D);
			break;
		case INVENTORY:// Inventory icon
			GL11.glScaled(scale3, scale3, -scale3);
			GL11.glTranslatef(14.4F, 15.0F, 0.0F);
			GL11.glRotatef(270.0F, 10.0F, 0.0F, 0.0F);
			GL11.glRotatef(52.5F, 0.0F, 10.0F, 0.0F);
			GL11.glRotatef(270.0F, 0.0F, 0.0F, 10.0F);
		default:
			break;
		}
		
		if(type != ItemRenderType.EQUIPPED_FIRST_PERSON)
			ResourceManager.benelli.renderAll();
		GL11.glPopMatrix();

	}

}