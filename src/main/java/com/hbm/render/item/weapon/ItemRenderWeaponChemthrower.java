package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemGunChemthrower;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponChemthrower implements IItemRenderer {
	
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

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.chemthrower_tex);
		
		switch(type) {
		
		case EQUIPPED_FIRST_PERSON:
			
			double s0 = 0.25D;
			GL11.glRotated(25, 0, 0, 1);
			GL11.glTranslated(1.0, 0.0, 0.0);
			GL11.glRotated(170, 0, 1, 0);
			GL11.glScaled(s0, s0, s0);
			
			break;
			
		case EQUIPPED:

			double scale = 0.25D;
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(100, 0, 1, 0);
			GL11.glRotated(-10, 1, 0, 0);
			GL11.glRotated(10, 0, 0, 1);
			GL11.glTranslatef(-0.25F, -2.5F, 1.75F);
			
			break;
			
		case ENTITY:

			double s1 = 0.25D;
			GL11.glScaled(s1, s1, s1);
			
			break;
			
		case INVENTORY:

			GL11.glEnable(GL11.GL_LIGHTING);
			
			double s = 1.5D;
			GL11.glTranslated(9, 9, 0);
			GL11.glRotated(180, 1, 0, 0);
			GL11.glRotated(45, 0, 0, -1);
			GL11.glScaled(s, s, -s);
			
			break;
			
		default: break;
		}
		
		ItemGunChemthrower chem = (ItemGunChemthrower) item.getItem();

		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		ResourceManager.chemthrower.renderPart("Gun");
		ResourceManager.chemthrower.renderPart("Hose");
		ResourceManager.chemthrower.renderPart("Nozzle");
		
		GL11.glTranslated(0, 0.875, 1.75);
		double d = (double) chem.getMag(item) / (double) chem.mainConfig.ammoCap;
		GL11.glRotated(135 - d * 270, 1, 0, 0);
		GL11.glTranslated(0, -0.875, -1.75);
		
		ResourceManager.chemthrower.renderPart("Gauge");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
}
