package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponFFNightmare implements IItemRenderer {

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
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 1.0D;
			GL11.glTranslated(0.75, 0.55, 0);
			GL11.glScaled(s0, s0, s0);
			GL11.glRotated(100, 0, -1, 0);
			GL11.glRotated(25, 1, 0, 0);
			
			break;
			
		case EQUIPPED:

			double scale = 1.0D;
			GL11.glRotated(195, 0, 1, 0);
			GL11.glRotated(-10, 0, 0, 1);
			GL11.glRotated(-10, 1, 0, 0);
			GL11.glTranslated(-0.5, 0.1, -0.25);
			GL11.glScaled(scale, scale, scale);
			
			break;
			
		case ENTITY:

			double s1 = 0.75D;
			GL11.glScaled(s1, s1, s1);
			GL11.glRotated(90, 0, 1, 0);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 9D;
			GL11.glTranslated(10, 9, 0);
			GL11.glRotated(-135, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}
		
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ff_gun_dark);
		ResourceManager.ff_nightmare.renderPart("Grip");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ff_gun_normal);
		ResourceManager.ff_nightmare.renderPart("Dark");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ff_gun_bright);
		ResourceManager.ff_nightmare.renderPart("Light");
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ff_gun_dark);
		
		int ammo = ItemGunBase.getMag(item);
		for(int i = 0; i < ammo; i++) {
			ResourceManager.ff_nightmare.renderPart("Bullet" + (i + 1));
		}
		
		GL11.glPopMatrix();
	}
}
