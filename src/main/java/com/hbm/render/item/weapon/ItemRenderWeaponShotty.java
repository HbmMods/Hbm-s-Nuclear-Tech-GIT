package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.config.WeaponConfig;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.Animation;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponShotty implements IItemRenderer {
	
	public ItemRenderWeaponShotty() { }

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case ENTITY:
			return true;
		case INVENTORY:
			return false;
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
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.shotty_tex);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:

			String animSuffix = WeaponConfig.linearAnimations ? "Lame" : "";
			
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-95F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-2.0F, 0.25F, -2.5F);

			HbmAnimations.applyRelevantTransformation("Body" + animSuffix);
			ResourceManager.shotty.renderPart("Body");

			HbmAnimations.applyRelevantTransformation("Barrel" + animSuffix);
			ResourceManager.shotty.renderPart("Barrel");

			// If we've run out of ammo, stop drawing the shells after ejection has completed
			Animation anim = HbmAnimations.getRelevantAnim();
			int millis = anim != null ? (int)(System.currentTimeMillis() - anim.startMillis) : 0;

			if(ItemGunBase.getBeltSize(player, ItemGunBase.getBeltType(player, item, true)) > 0 || millis < 1000) {
				GL11.glPushMatrix();
				HbmAnimations.applyRelevantTransformation("ShellL" + animSuffix);
				ResourceManager.shotty.renderPart("ShellL");
				GL11.glPopMatrix();
				
				GL11.glPushMatrix();
				HbmAnimations.applyRelevantTransformation("ShellR" + animSuffix);
				ResourceManager.shotty.renderPart("ShellR");
				GL11.glPopMatrix();
			}
			
			break;
			
		case EQUIPPED:

			GL11.glRotatef(-170F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-10F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-10F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(5F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(-0.4F, 0.0F, -0.5F);
			GL11.glScaled(0.35, 0.35, 0.35);
			ResourceManager.shotty.renderPart("Body");
			ResourceManager.shotty.renderPart("Barrel");
			
			break;
			
		case ENTITY:

			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glTranslatef(0.0F, 0.2F, 0.0F);
			ResourceManager.shotty.renderPart("Body");
			ResourceManager.shotty.renderPart("Barrel");
			break;
			
		default: break;
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
