package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemAmmoHIMARS;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderArtilleryRocket extends Render {

	@Override
	public void doRender(Entity shell, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(shell.prevRotationYaw + (shell.rotationYaw - shell.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(shell.prevRotationPitch + (shell.rotationPitch - shell.prevRotationPitch) * f1 - 90, 0.0F, 0.0F, 1.0F);
		
		this.bindEntityTexture(shell);
		
		boolean fog = GL11.glIsEnabled(GL11.GL_FOG);
		
		if(fog) GL11.glDisable(GL11.GL_FOG);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ResourceManager.turret_himars.renderPart("RocketStandard");
		GL11.glShadeModel(GL11.GL_FLAT);
		if(fog) GL11.glEnable(GL11.GL_FOG);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ItemAmmoHIMARS.itemTypes[0].texture;
	}
}
