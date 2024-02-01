package com.hbm.render.item.weapon;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponSpas12 implements IItemRenderer {
	
	public ItemRenderWeaponSpas12() { }

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

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.spas_12_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.5D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(0, -0.8, 0.1);
			GL11.glRotated(-100, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);

			HbmAnimations.applyRelevantTransformation("MainBody");
			ResourceManager.spas_12.renderPart("MainBody");
			
			GL11.glPushMatrix();
			HbmAnimations.applyRelevantTransformation("PumpGrip");
			ResourceManager.spas_12.renderPart("PumpGrip");
			GL11.glPopMatrix();


            GL11.glPushMatrix();
            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.casings_tex);

            HbmAnimations.applyRelevantTransformation("Shell");

            ItemGunBase gun = (ItemGunBase)item.getItem();
            BulletConfiguration bullet = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(ItemGunBase.getMagType(item)));
            int[] colors = bullet.spentCasing.getColors();

            Color shellColor = new Color(colors[1]);
            GL11.glColor3f(shellColor.getRed() / 255F, shellColor.getGreen() / 255F, shellColor.getBlue() / 255F);
            ResourceManager.spas_12.renderPart("Shell");
            
            Color shellForeColor = new Color(colors[0]);
            GL11.glColor3f(shellForeColor.getRed() / 255F, shellForeColor.getGreen() / 255F, shellForeColor.getBlue() / 255F);
            ResourceManager.spas_12.renderPart("ShellFore");

            GL11.glColor3f(1F, 1F, 1F);
            GL11.glPopMatrix();
			
			break;
			
		case EQUIPPED:

			double scale = 0.25D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(190, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-15F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(-2F, -1F, 2F);
			
			ResourceManager.spas_12.renderAll();
			
			break;
			
		case ENTITY:

			double s1 = 0.25D;
			GL11.glScaled(s1, s1, s1);
			GL11.glTranslated(0, 0, 4);
			
			ResourceManager.spas_12.renderAll();
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 1.6D;
			GL11.glTranslated(12, 13.5, 0);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-135, 1, 0, 0);
			GL11.glScaled(s, s, s);
			
			ResourceManager.spas_12.renderAll();
			
			break;
			
		default: break;
		}
		
		GL11.glPopMatrix();
	}
}
