package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponSauer implements IItemRenderer {
	
	public ItemRenderWeaponSauer() { }

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

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.sauergun_tex);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:

			double[] recoil = HbmAnimations.getRelevantTransformation("SAUER_RECOIL");
			double[] tilt = HbmAnimations.getRelevantTransformation("SAUER_TILT");
			double[] cock = HbmAnimations.getRelevantTransformation("SAUER_COCK");
			double[] eject = HbmAnimations.getRelevantTransformation("SAUER_SHELL_EJECT");
			
			double s0 = 0.5D;
			GL11.glScaled(s0, s0, s0);
			
			GL11.glTranslatef(0.0F, -0.5F, 0.0F);
			GL11.glRotatef(-100F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(20F, 1.0F, 0.0F, 0.0F);

			if(recoil != null)
				GL11.glTranslated(0, 0, recoil[0]);

			if(player.isSneaking()) {
				GL11.glRotatef(-3F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(2F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(3F, 1.0F, 0.0F, 0.0F);
				GL11.glTranslatef(-2.0F, 0.5F, 0.3F);
			}

			if(tilt != null) {
				GL11.glTranslated(0, -5, 0);
				GL11.glRotated(tilt[2] * -0.5, 1, 0, 0);
				GL11.glTranslated(0, 5, 0);
				GL11.glRotated(tilt[0], 0, 0, 1);

				GL11.glTranslated(0, 0, cock[0] * 2);
				ResourceManager.sauergun.renderPart("Lever");
				GL11.glTranslated(0, 0, -cock[0] * 2);

				GL11.glTranslated(eject[2] * 10, -eject[2], 0);
				GL11.glRotated(eject[2] * 90, -1, 0, 0);
				ResourceManager.sauergun.renderPart("Shell");
				GL11.glRotated(eject[2] * 90, 1, 0, 0);
				GL11.glTranslated(-eject[2] * 10, eject[2], 0);
				
			} else {

				ResourceManager.sauergun.renderPart("Lever");
			}
			
			break;
			
		case EQUIPPED:

			double scale = 0.5D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-170, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-15F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(-1F, -0.3F, 0.0F);
			ResourceManager.sauergun.renderPart("Lever");
			break;
			
		case ENTITY:

			double s1 = 0.25D;
			GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.0F, 0.0F, 0.5F);
			GL11.glScaled(s1, s1, s1);
			ResourceManager.sauergun.renderPart("Lever");
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 2.25D;
			GL11.glScaled(s, s, -s);
			GL11.glTranslatef(4.0F, 4.5F, 0.0F);
			GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(45F, 1.0F, 0.0F, 0.0F);
			ResourceManager.sauergun.renderPart("Lever");
			
			break;
			
		default: break;
		}

		ResourceManager.sauergun.renderPart("Gun");

		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
