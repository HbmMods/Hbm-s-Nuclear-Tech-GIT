package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

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
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.universal);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:

			double[] recoil = HbmAnimations.getRelevantTransformation("SHOTTY_RECOIL");
			double[] eject = HbmAnimations.getRelevantTransformation("SHOTTY_BREAK");
			double[] ejectShell = HbmAnimations.getRelevantTransformation("SHOTTY_EJECT");
			double[] insertShell = HbmAnimations.getRelevantTransformation("SHOTTY_INSERT");
			
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-10F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(1.75F, -0.2F, -0.3F);
			
			if(player.isSneaking()) {
				GL11.glTranslatef(0F, 1.0F, -1.8F);
				GL11.glRotatef(3.5F, 0.0F, 1.0F, 0.0F);
			} else {
				
				GL11.glRotated(-eject[2] * 0.25, 0, 0, 1);
			}

			GL11.glTranslated(-recoil[0] * 2, 0, 0);
			GL11.glRotated(recoil[0] * 5, 0, 0, 1);
			
			GL11.glPushMatrix();
			GL11.glRotated(-eject[2] * 0.8, 0, 0, 1);
			ResourceManager.shotty.renderPart("Barrel");

			GL11.glPushMatrix();
			GL11.glRotated(ejectShell[0] * 90, 0, 0, 1);
			GL11.glTranslated(-ejectShell[0] * 10, 0, 0);
			ResourceManager.shotty.renderPart("Shells");
			GL11.glPopMatrix();
			
			GL11.glPushMatrix();
			GL11.glTranslated(-insertShell[0], insertShell[2] * -2, insertShell[2] * -1);
			ResourceManager.shotty.renderPart("Shells");
			GL11.glPopMatrix();
			
			GL11.glPopMatrix();
			
			ResourceManager.shotty.renderPart("Handle");
			
			break;
			
		case EQUIPPED:

			GL11.glRotatef(-80F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-10F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(10F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(5F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.5F, 0.0F, -0.4F);
			GL11.glScaled(0.35, 0.35, 0.35);
			ResourceManager.shotty.renderPart("Handle");
			ResourceManager.shotty.renderPart("Barrel");
			
			break;
			
		case ENTITY:

			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glTranslatef(-1.0F, 0.2F, 0.0F);
			ResourceManager.shotty.renderPart("Handle");
			ResourceManager.shotty.renderPart("Barrel");
			break;
			
		default: break;
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
