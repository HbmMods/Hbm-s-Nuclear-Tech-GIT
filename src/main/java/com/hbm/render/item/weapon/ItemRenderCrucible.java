package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

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
		
		boolean isOn = item.getItemDamage() < item.getMaxDamage();
		
		switch(type) {
		case EQUIPPED_FIRST_PERSON:

			GL11.glTranslated(1.5, -0.3, 0);
			
			if(player.isBlocking()) {
				GL11.glTranslated(-0.125, -0.25, 0);
			}
			
			player.isSwingInProgress = false;

			float prevEq = ReflectionHelper.getPrivateValue(ItemRenderer.class, Minecraft.getMinecraft().entityRenderer.itemRenderer, "prevEquippedProgress", "field_78451_d");
			float eq = ReflectionHelper.getPrivateValue(ItemRenderer.class, Minecraft.getMinecraft().entityRenderer.itemRenderer, "equippedProgress", "field_78454_c");
			
			if(eq < prevEq) {
				ReflectionHelper.setPrivateValue(ItemRenderer.class, Minecraft.getMinecraft().entityRenderer.itemRenderer, 0.0F, "prevEquippedProgress", "field_78451_d");
				ReflectionHelper.setPrivateValue(ItemRenderer.class, Minecraft.getMinecraft().entityRenderer.itemRenderer, 0.0F, "equippedProgress", "field_78454_c");
			} else if(eq > prevEq) {
				ReflectionHelper.setPrivateValue(ItemRenderer.class, Minecraft.getMinecraft().entityRenderer.itemRenderer, 1.0F, "prevEquippedProgress", "field_78451_d");
				ReflectionHelper.setPrivateValue(ItemRenderer.class, Minecraft.getMinecraft().entityRenderer.itemRenderer, 1.0F, "equippedProgress", "field_78454_c");
			}
			
			GL11.glScaled(0.3, 0.3, 0.3);
			
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			
			boolean isSwing = false;

			if(!player.isBlocking()) {
				double[] sRot = HbmAnimations.getRelevantTransformation("SWING_ROT");
				double[] sTrans = HbmAnimations.getRelevantTransformation("SWING_TRANS");
				GL11.glTranslated(sTrans[0], sTrans[1], sTrans[2]);
				GL11.glRotated(sRot[0], 1, 0, 0);
				GL11.glRotated(sRot[2], 0, 0, 1);
				GL11.glRotated(sRot[1], 0, 1, 0);
				
				if(sRot[0] != 0)
					isSwing = true;
			}
			
			double[] rot = HbmAnimations.getRelevantTransformation("GUARD_ROT");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_hilt);
			ResourceManager.crucible.renderPart("Hilt");

			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_guard);
			double rotGuard = rot[0];
			
			if(!isSwing && !isOn)
				rotGuard = 90;
			
			GL11.glPushMatrix();
				GL11.glTranslated(0, 3, 0.5);
				GL11.glRotated(rotGuard, -1, 0, 0);
				GL11.glTranslated(0, -3, -0.5);
			ResourceManager.crucible.renderPart("GuardLeft");
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
				GL11.glTranslated(0, 3, -0.5);
				GL11.glRotated(rotGuard, 1, 0, 0);
				GL11.glTranslated(0, -3, 0.5);
			ResourceManager.crucible.renderPart("GuardRight");
			GL11.glPopMatrix();
			
			if(eq == 1.0F && prevEq == 1.0F && rot[2] == 0 && (isSwing || isOn)) {
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
			GL11.glPushMatrix();
			GL11.glTranslated(0, 3, 0.5);
			GL11.glRotated(isOn ? 0 : 90, -1, 0, 0);
			GL11.glTranslated(0, -3, -0.5);
			ResourceManager.crucible.renderPart("GuardLeft");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(0, 3, -0.5);
			GL11.glRotated(isOn ? 0 : 90, 1, 0, 0);
			GL11.glTranslated(0, -3, 0.5);
			ResourceManager.crucible.renderPart("GuardRight");
			GL11.glPopMatrix();
			
			if(isOn) {
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
		case INVENTORY:

			GL11.glTranslated(2, 14, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			double scale = 1.5D;
			GL11.glScaled(scale, scale, scale);

			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_hilt);
			ResourceManager.crucible.renderPart("Hilt");
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_guard);
			GL11.glPushMatrix();
			GL11.glTranslated(0, 3, 0.5);
			GL11.glRotated(isOn ? 0 : 90, -1, 0, 0);
			GL11.glTranslated(0, -3, -0.5);
			ResourceManager.crucible.renderPart("GuardLeft");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			GL11.glTranslated(0, 3, -0.5);
			GL11.glRotated(isOn ? 0 : 90, 1, 0, 0);
			GL11.glTranslated(0, -3, 0.5);
			ResourceManager.crucible.renderPart("GuardRight");
			GL11.glPopMatrix();
			GL11.glTranslated(0.005, 0, 0);
			if(isOn) {
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.crucible_blade);
				ResourceManager.crucible.renderPart("Blade");
			}
			break;

		default: break;
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
