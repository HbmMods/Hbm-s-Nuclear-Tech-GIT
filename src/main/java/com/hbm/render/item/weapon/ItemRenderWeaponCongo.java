package com.hbm.render.item.weapon;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.ResourceManager;
import com.hbm.particle.SpentCasing;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponCongo implements IItemRenderer {
	
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
		
		GL11.glShadeModel(GL11.GL_SMOOTH);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.congolake_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.25D;
			GL11.glRotated(20, 0, 0, 1);
			GL11.glTranslated(0.5, 0.0, -0.5);
			GL11.glRotated(-10, 0, 1, 0);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);

			HbmAnimations.applyRelevantTransformation("Gun");
			ResourceManager.congolake.renderPart("Gun");


			GL11.glPushMatrix();
			{
				HbmAnimations.applyRelevantTransformation("Pump");
				ResourceManager.congolake.renderPart("Pump");
			}
			GL11.glPopMatrix();


			GL11.glPushMatrix();
			{
				HbmAnimations.applyRelevantTransformation("Sight");
				ResourceManager.congolake.renderPart("Sight");
			}
			GL11.glPopMatrix();


			GL11.glPushMatrix();
			{
				HbmAnimations.applyRelevantTransformation("Loop");
				ResourceManager.congolake.renderPart("Loop");
			}
			GL11.glPopMatrix();


			GL11.glPushMatrix();
			{
				HbmAnimations.applyRelevantTransformation("GuardOuter");
				ResourceManager.congolake.renderPart("GuardOuter");

				GL11.glPushMatrix();
				{
					HbmAnimations.applyRelevantTransformation("GuardInner");
					ResourceManager.congolake.renderPart("GuardInner");
				}
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();


			GL11.glPushMatrix();
			{
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.casings_tex);

				HbmAnimations.applyRelevantTransformation("Shell");

				ItemGunBase gun = (ItemGunBase)item.getItem();
				BulletConfiguration bullet = BulletConfigSyncingUtil.pullConfig(gun.mainConfig.config.get(ItemGunBase.getMagType(item)));
				int[] colors = bullet.spentCasing != null ? bullet.spentCasing.getColors() : new int[] { SpentCasing.COLOR_CASE_40MM };

				Color shellColor = new Color(colors[0]);
				GL11.glColor3f(shellColor.getRed() / 255F, shellColor.getGreen() / 255F, shellColor.getBlue() / 255F);
				ResourceManager.congolake.renderPart("Shell");
				
				Color shellForeColor = new Color(colors.length > 1 ? colors[1] : colors[0]);
				GL11.glColor3f(shellForeColor.getRed() / 255F, shellForeColor.getGreen() / 255F, shellForeColor.getBlue() / 255F);
				ResourceManager.congolake.renderPart("ShellFore");

				GL11.glColor3f(1F, 1F, 1F);
			}
			GL11.glPopMatrix();
			
			break;
			
		case EQUIPPED:

			double scale = 0.35D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(15F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(15, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(1.25F, -1F, 1.5F);
			
			break;
			
		case ENTITY:

			double s1 = 0.2D;
			GL11.glScaled(s1, s1, s1);
			
			break;
			
		case INVENTORY:
			
			double s = 2D;
			GL11.glTranslated(6, 9, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}

		if(type != ItemRenderType.EQUIPPED_FIRST_PERSON) {
			ResourceManager.congolake.renderAll();
		}

		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}
}
