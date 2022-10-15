package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityArtilleryRocket;
import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.items.weapon.ItemAmmoHIMARS.HIMARSRocket;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderArtilleryRocket extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f1 - 90, 0.0F, 0.0F, 1.0F);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glRotated(90, 1, 0, 0);
		
		this.bindEntityTexture(entity);
		
		boolean fog = GL11.glIsEnabled(GL11.GL_FOG);
		
		if(fog) GL11.glDisable(GL11.GL_FOG);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		EntityArtilleryRocket rocket = (EntityArtilleryRocket) entity;
		HIMARSRocket type = rocket.getType();
		if(type.modelType == 0) ResourceManager.turret_himars.renderPart("RocketStandard");
		if(type.modelType == 1) ResourceManager.turret_himars.renderPart("RocketSingle");
		GL11.glShadeModel(GL11.GL_FLAT);
		if(fog) GL11.glEnable(GL11.GL_FOG);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		EntityArtilleryRocket rocket = (EntityArtilleryRocket) entity;
		return ItemAmmoHIMARS.itemTypes[rocket.getDataWatcher().getWatchableObjectInt(10)].texture;
	}
}
