package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityMass;
import com.hbm.entity.mob.EntityMass.MassFragment;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMass extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y + 1.5D, z);
		
		this.bindTexture(getEntityTexture(entity));
		EntityMass mass = (EntityMass) entity;
		double scale = 0.75D;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		for(MassFragment frag : mass.frags) {
			GL11.glPushMatrix();

			double posX = frag.prevX + (frag.posX - frag.prevX) * interp;
			double posY = frag.prevY + (frag.posY - frag.prevY) * interp;
			double posZ = frag.prevZ + (frag.posZ - frag.prevZ) * interp;
			GL11.glTranslated(posX, posY, posZ);
			
			double yaw = frag.prevYaw + (frag.yaw - frag.prevYaw) * interp;
			double pitch = frag.prevPitch + (frag.pitch - frag.prevPitch) * interp;
			double roll = frag.prevRoll + (frag.roll - frag.prevRoll) * interp;
			
			GL11.glRotated(pitch, 0, 0, 1);
			GL11.glRotated(yaw, 0, 1, 0);
			GL11.glRotated(roll, 1, 0, 0);
			
			GL11.glScaled(scale, scale, scale);
			
			ResourceManager.mass.renderPart(frag.type.model);
			GL11.glPopMatrix();
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return new ResourceLocation(RefStrings.MODID + ":textures/entity/mass.png");
	}
}
