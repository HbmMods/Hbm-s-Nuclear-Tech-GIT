package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMissileBaseNT;
import com.hbm.entity.missile.EntityMissileTier4.*;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMissileNuclear extends Render {
	
	public RenderMissileNuclear() { }

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

		if(entity instanceof EntityMissileNuclear) bindTexture(ResourceManager.missileNuclear_tex);
		if(entity instanceof EntityMissileMirv) bindTexture(ResourceManager.missileThermo_tex);
		if(entity instanceof EntityMissileDoomsday) bindTexture(ResourceManager.missileDoomsday_tex);
		if(entity instanceof EntityMissileDoomsdayRusted) bindTexture(ResourceManager.missileDoomsdayRusted_tex);
		if(entity instanceof EntityMissileVolcano) bindTexture(ResourceManager.missileVolcano_tex);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.missileNuclear.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.missileNuclear_tex;
	}

}
