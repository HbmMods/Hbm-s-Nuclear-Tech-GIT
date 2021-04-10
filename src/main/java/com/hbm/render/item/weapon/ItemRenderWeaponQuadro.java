package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponQuadro implements IItemRenderer {
	
	public ItemRenderWeaponQuadro() { }

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
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:

			if(item.getItem() == ModItems.gun_quadro) {
				GL11.glTranslatef(0.75F, 0.0F, -0.15F);
				GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-25F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-10F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(0.5, 0.5, 0.5);
				
				if(player.isSneaking()) {
					GL11.glRotatef(5F, 0.0F, 1.0F, 0.0F);
					GL11.glTranslatef(1.0F, 0.5F, 0.3F);
				}

				double[] recoil = HbmAnimations.getRelevantTransformation("QUADRO_RECOIL");
				GL11.glTranslated(0, 0, recoil[2]);

				double[] reload = HbmAnimations.getRelevantTransformation("QUADRO_RELOAD_ROTATE");
				GL11.glRotated(reload[2], 1, 0, 0);
			}
			
			break;
			
		case EQUIPPED:

			if(item.getItem() == ModItems.gun_quadro) {
				GL11.glRotatef(20F, 1.0F, 0.0F, 1.0F);
				GL11.glRotatef(10F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.4F, -0.35F, -0.4F);
				GL11.glScaled(0.75, 0.75, 0.75);
			}
			
			break;
			
		case ENTITY:
			
			if(item.getItem() == ModItems.gun_quadro) {
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glTranslatef(0.0F, 0.0F, 0.0F);
				GL11.glScaled(0.75, 0.75, 0.75);
			}
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			if(item.getItem() == ModItems.gun_quadro) {
				GL11.glScaled(4.5, 4.5, -4.5);
				GL11.glTranslatef(1.0F, 2.5F, 0.0F);
				GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-45F, 1.0F, 0.0F, 0.0F);
			}
			
			break;
			
		default: break;
		}

		if(item.getItem() == ModItems.gun_quadro) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.quadro_tex);
			ResourceManager.quadro.renderPart("Launcher");
			
			if(ItemGunBase.getMag(item) > 0 || ItemGunBase.getIsReloading(item) && type != ItemRenderType.INVENTORY) {
				GL11.glPushMatrix();
				
				GL11.glTranslated(0, -1, 0);
	
				double[] push = HbmAnimations.getRelevantTransformation("QUADRO_RELOAD_PUSH");
				GL11.glTranslated(0, 3, 0);
				GL11.glRotated(push[1] * 30, 1, 0, 0);
				GL11.glTranslated(0, -3, 0);
				GL11.glTranslated(0, 0, push[0] * 3);
				
				Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.quadro_rocket_tex);
				ResourceManager.quadro.renderPart("Rockets");
				GL11.glPopMatrix();
			}
			
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
