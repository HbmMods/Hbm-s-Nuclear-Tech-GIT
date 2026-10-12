package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.sedna.factory.LegoClient;
import com.hbm.main.ResourceManager;
import com.hbm.util.Vec3NT;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderLambdaRocket extends Render {
	
	protected static Vec3NT vec = new Vec3NT(0, 0, 0);

	@Override
	public void doRender(Entity entity, double x, double y, double z, float i, float j) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		this.bindTexture(ResourceManager.lambda_rocket_tex);
		ResourceManager.lambda_rocket.renderAll();
		
		// move the flare one block towards the player so it doesn't clip into the model
		vec.setComponents(x, y, z).normalizeSelf().multiply(-1);
		GL11.glTranslated(vec.xCoord, vec.yCoord, vec.zCoord);
		
		GL11.glScaled(2, 2, 2);
		LegoClient.renderFlare(entity, j, 1F, 0.75F, 0.5F);
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.lambda_rocket_tex;
	}
}
