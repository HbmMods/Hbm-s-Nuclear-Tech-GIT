package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class ItemRenderCrucible implements IItemRenderer {
	
	public ItemRenderCrucible() { }

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
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		switch(type) {
		case EQUIPPED_FIRST_PERSON:

			GL11.glTranslated(1.5, -0.3, 0);
			
			if(player.isBlocking()) {
				GL11.glTranslated(-0.125, -0.25, 0);
			}
			
			player.isSwingInProgress = false;

			
			GL11.glScaled(0.3, 0.3, 0.3);
			
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);

			if(!player.isBlocking()) {
				double[] sRot = HbmAnimations.getRelevantTransformation("SWING_ROT");
				double[] sTrans = HbmAnimations.getRelevantTransformation("SWING_TRANS");
				GL11.glTranslated(sTrans[0], sTrans[1], sTrans[2]);
				GL11.glRotated(sRot[0], 1, 0, 0);
				GL11.glRotated(sRot[2], 0, 0, 1);
				GL11.glRotated(sRot[1], 0, 1, 0);
			}
			
			double[] rot = HbmAnimations.getRelevantTransformation("GUARD_ROT");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_hilt);
			ResourceManager.crucible.renderPart("Hilt");

			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_guard);
			GL11.glPushMatrix();
			if(rot[2] == 1) {
				GL11.glTranslated(0, 3, 0.5);
				GL11.glRotated(rot[0], -1, 0, 0);
				GL11.glTranslated(0, -3, -0.5);
			}
			ResourceManager.crucible.renderPart("GuardLeft");
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			if(rot[2] == 1) {
				GL11.glTranslated(0, 3, -0.5);
				GL11.glRotated(rot[0], 1, 0, 0);
				GL11.glTranslated(0, -3, 0.5);
			}
			ResourceManager.crucible.renderPart("GuardRight");
			GL11.glPopMatrix();

			float equippedProgress = ReflectionHelper.getPrivateValue(ItemRenderer.class, Minecraft.getMinecraft().entityRenderer.itemRenderer, "equippedProgress", "field_78454_c");
			
			if(equippedProgress == 1.0F && rot[2] == 0) {
				GL11.glPushMatrix();
				GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_CULL_FACE);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
				GL11.glTranslated(0.005, 0, 0);
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_blade);
				ResourceManager.crucible.renderPart("Blade");
				GL11.glEnable(GL11.GL_LIGHTING);

				GL11.glPopAttrib();
				GL11.glPopMatrix();
			}
			
			break;

		case ENTITY:
			GL11.glTranslated(-0.75, 0.6, 0);
			GL11.glRotated(-45, 0, 0, 1);
		case EQUIPPED:

			GL11.glRotated(45, 0, 0, 1);
			GL11.glTranslated(0.75, -0.4, 0);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(0.15, 0.15, 0.15);

			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_hilt);
			ResourceManager.crucible.renderPart("Hilt");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_guard);
			ResourceManager.crucible.renderPart("GuardLeft");
			ResourceManager.crucible.renderPart("GuardRight");
			
			GL11.glPushMatrix();
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glTranslated(0.005, 0, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_blade);
			ResourceManager.crucible.renderPart("Blade");
			GL11.glEnable(GL11.GL_LIGHTING);

			GL11.glPopAttrib();
			GL11.glPopMatrix();
			
			break;
		case INVENTORY:

			GL11.glTranslated(2, 14, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			double scale = 1.5D;
			GL11.glScaled(scale, scale, scale);

			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_hilt);
			ResourceManager.crucible.renderPart("Hilt");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_guard);
			ResourceManager.crucible.renderPart("GuardLeft");
			ResourceManager.crucible.renderPart("GuardRight");
			GL11.glTranslated(0.005, 0, 0);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_blade);
			ResourceManager.crucible.renderPart("Blade");
			break;

		default: break;
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
