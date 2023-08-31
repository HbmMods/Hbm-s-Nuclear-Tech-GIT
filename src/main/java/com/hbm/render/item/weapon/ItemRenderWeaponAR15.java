package com.hbm.render.item.weapon;

import com.hbm.render.anim.HbmAnimations;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderWeaponAR15 implements IItemRenderer {
	
	public ItemRenderWeaponAR15() { }

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

		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.ar15_tex);

		IModelCustom model = ResourceManager.ar15;

		switch (type) {

			case EQUIPPED_FIRST_PERSON:

				double s0 = 0.25D;
				GL11.glRotated(25, 0, 0, 1);
				GL11.glTranslated(1.25, 0, -0.25);
				GL11.glRotated(-100, 0, 1, 0);
				GL11.glScaled(s0, s0, s0);

				double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL");
				GL11.glRotated(recoil[0] * 2.5, 1, 0, 0);
				GL11.glTranslated(0, 0, recoil[0]);


				double[] tilt = HbmAnimations.getRelevantTransformation("TILT");
				GL11.glTranslated(0, tilt[0], 3);
				GL11.glRotated(tilt[0] * -35, 0, 0, 1);
				GL11.glTranslated(0, 0, -3);

				model.renderPart("main");

				double[] mag = HbmAnimations.getRelevantTransformation("MAG");
				GL11.glPushMatrix();
				GL11.glTranslated(0, 0, 5);
				GL11.glRotated(mag[0] * 60 * (mag[2] == 1 ? 2.5 : 1), -1, 0, 0);
				GL11.glTranslated(0, 0, -5);
				model.renderPart("mag");
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

				double s = 1.45D;

				GL11.glTranslated(6, 9, 0);
				GL11.glRotated(90, 0, 1, 0);
				GL11.glRotated(135, 1, 0, 0);
				GL11.glScaled(s, s, -s);

				break;

			default:
				break;
		}

		if (type != ItemRenderType.EQUIPPED_FIRST_PERSON) {
			model.renderAll();
		}

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}
}
