package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMissileTier0.*;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMissileTaint extends Render {
	
	public RenderMissileTaint() {
	}

	@Override
	public void doRender(Entity missile, double x, double y, double z, float f1, float f2) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)x, (float)y, (float)z);
        GL11.glRotatef(missile.prevRotationYaw + (missile.rotationYaw - missile.prevRotationYaw) * f2 - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(missile.prevRotationPitch + (missile.rotationPitch - missile.prevRotationPitch) * f2, 0.0F, 0.0F, 1.0F);
        GL11.glScalef(2F, 2F, 2F);

        GL11.glDisable(GL11.GL_CULL_FACE);
        bindTexture(getEntityTexture(missile));
        ResourceManager.missileTaint.renderAll();
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		if(p_110775_1_ instanceof EntityMissileTaint)
			return ResourceManager.missileTaint_tex;
		if(p_110775_1_ instanceof EntityMissileBHole)
			return ResourceManager.missileMicroBHole_tex;
		if(p_110775_1_ instanceof EntityMissileSchrabidium)
			return ResourceManager.missileMicroSchrab_tex;
		if(p_110775_1_ instanceof EntityMissileEMP)
			return ResourceManager.missileMicroEMP_tex;
		
		return ResourceManager.missileMicro_tex;
	}
}
