package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponBolter implements IItemRenderer {
	
	public ItemRenderWeaponBolter() { }

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
		
		return type == ItemRenderType.ENTITY && (helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		if(item.getItem() == ModItems.gun_bolter)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bolter_tex);
		if(item.getItem() == ModItems.gun_bolter_digamma)
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.bolter_digamma_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.25D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(1.25, -0.25, -0.25);
			GL11.glRotated(-100, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
			GL11.glRotated(recoil[0] * 5, 1, 0, 0);
			GL11.glTranslated(0, 0, recoil[0]);

			double[] tilt = HbmAnimations.getRelevantTransformation("TILT");
			GL11.glTranslated(0, tilt[0], 3);
			GL11.glRotated(tilt[0] * 35, 1, 0, 0);
			GL11.glTranslated(0, 0, -3);
			
			ResourceManager.bolter.renderPart("Body");

			double[] mag = HbmAnimations.getRelevantTransformation("MAG");
			GL11.glPushMatrix();
			GL11.glTranslated(0, 0, 5);
			GL11.glRotated(mag[0] * 60 * (mag[2] == 1 ? 2.5 : 1), -1, 0, 0);
			GL11.glTranslated(0, 0, -5);
			ResourceManager.bolter.renderPart("Mag");
			if(mag[2] != 1) ResourceManager.bolter.renderPart("Bullet");
			GL11.glPopMatrix();

			GL11.glPushMatrix();
			double[] casing = HbmAnimations.getRelevantTransformation("EJECT");
			GL11.glTranslated(casing[2] * 5, casing[2] * 2, 0);
			GL11.glRotated(casing[2] * 60, 1, 0, 0);
			ResourceManager.bolter.renderPart("Casing");
			GL11.glPopMatrix();
			
			break;
			
		case EQUIPPED:

			double scale = 0.25D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-170, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-15F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(-2F, -0.9F, -0.75F);
			
			break;
			
		case ENTITY:

			double s1 = 0.2D;
			GL11.glScaled(s1, s1, s1);
			GL11.glTranslated(0, 1, 0);
			GL11.glRotatef(90, 0, 1, 0);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 2.5D;
			GL11.glTranslated(6, 10, 0);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(135, 1, 0, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}
		
		if(type != ItemRenderType.EQUIPPED_FIRST_PERSON) {
			ResourceManager.bolter.renderAll();
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		String s = ((ItemGunBase) item.getItem()).getMag(item) + "";
		float f3 = 0.04F;
		GL11.glTranslatef(0.025F - (font.getStringWidth(s) / 2) * 0.04F, 2.11F, 2.91F);
		GL11.glScalef(f3, -f3, f3);
		GL11.glRotatef(45, 1, 0, 0);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
		font.drawString(s, 0, 0, 0xff0000);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}
}
