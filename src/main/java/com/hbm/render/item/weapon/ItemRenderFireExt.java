package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.ItemGunBase;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderFireExt implements IItemRenderer {
	
	public ItemRenderFireExt() { }

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
		
		int magType = ItemGunBase.getMagType(item);
		int config = ((ItemGunBase)ModItems.gun_fireext).mainConfig.config.get(magType);
		int ammo = BulletConfigSyncingUtil.pullConfig(config).ammo.meta;
		ResourceLocation tex;
		switch (ammo) {
			case 0: tex = ResourceManager.fireext_foam_tex; break;
			case 1: tex = ResourceManager.fireext_sand_tex; break;
			default: tex = ResourceManager.fireext_tex; break;
		}
		Minecraft.getMinecraft().renderEngine.bindTexture(tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.35D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(0.5, -0.5, -0.5F);
			GL11.glRotated(80, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			
			break;
			
		case EQUIPPED:

			double scale = 0.5D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotatef(20F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-5, 0.0F, 1.0F, 1.0F);
			GL11.glRotatef(10, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(15F, 1.0F, 0.0F, 0.0F);
			GL11.glTranslatef(0.75F, -2.75F, 0.5F);
			
			break;
			
		case ENTITY:

			double s1 = 0.3D;
			GL11.glScaled(s1, s1, s1);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 4.5D;
			GL11.glTranslated(2, 14, 0);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(-135, 1, 0, 0);
			GL11.glRotated(System.currentTimeMillis() / 10 % 360, 0, 1, 0);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.fireext.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
