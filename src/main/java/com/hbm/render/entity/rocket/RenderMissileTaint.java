package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMissileBaseNT;
import com.hbm.entity.missile.EntityMissileTier0.*;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMissileTaint extends Render {
	
	public RenderMissileTaint() {
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * interp, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90.0F, 0.0F, -1.0F, 0.0F);

		if(entity instanceof EntityMissileBaseNT) switch(entity.getDataWatcher().getWatchableObjectByte(3)) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		GL11.glEnable(GL11.GL_CULL_FACE);
		bindTexture(getEntityTexture(entity));
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.missileMicro.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		if(p_110775_1_ instanceof EntityMissileTaint)
			return ResourceManager.missileMicroTaint_tex;
		if(p_110775_1_ instanceof EntityMissileBHole)
			return ResourceManager.missileMicroBHole_tex;
		if(p_110775_1_ instanceof EntityMissileSchrabidium)
			return ResourceManager.missileMicroSchrab_tex;
		if(p_110775_1_ instanceof EntityMissileEMP)
			return ResourceManager.missileMicroEMP_tex;
		if(p_110775_1_ instanceof EntityMissileTest)
			return ResourceManager.missileMicroTest_tex;
		
		return ResourceManager.missileMicro_tex;
	}
}
