package com.hbm.render.entity.item;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBomber extends Render {

	public RenderBomber() {
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(90, 0F, 0F, 1F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * interp, 0.0F, 0.0F, 1.0F);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		int i = entity.getDataWatcher().getWatchableObjectByte(16);

		switch(i) {
		case 0: bindTexture(ResourceManager.dornier_1_tex); break;
		case 1: bindTexture(ResourceManager.dornier_1_tex); break;
		case 2: bindTexture(ResourceManager.dornier_2_tex); break;
		case 3: bindTexture(ResourceManager.dornier_1_tex); break;
		case 4: bindTexture(ResourceManager.dornier_4_tex); break;
		case 5: bindTexture(ResourceManager.b29_0_tex); break;
		case 6: bindTexture(ResourceManager.b29_1_tex); break;
		case 7: bindTexture(ResourceManager.b29_2_tex); break;
		case 8: bindTexture(ResourceManager.b29_3_tex); break;
		default: bindTexture(ResourceManager.dornier_1_tex); break;
		}

		GL11.glRotatef((float) Math.sin((entity.ticksExisted + interp) * 0.05) * 10, 1F, 0F, 0F);

		switch(i) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			GL11.glScalef(5F, 5F, 5F);
			GL11.glRotatef(-90, 0F, 1F, 0F);
			ResourceManager.dornier.renderAll();
			break;
		case 5:
		case 6:
		case 7:
		case 8:
			GL11.glScalef(30F / 3.1F, 30F / 3.1F, 30F / 3.1F);
			GL11.glRotatef(180, 0F, 1F, 0F);
			ResourceManager.b29.renderAll();
			break;
		default:
			ResourceManager.dornier.renderAll();
			break;
		}

		GL11.glEnable(GL11.GL_CULL_FACE);

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.dornier_1_tex;
	}
}
