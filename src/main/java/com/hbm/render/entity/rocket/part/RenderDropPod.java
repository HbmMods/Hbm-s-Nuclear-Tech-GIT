package com.hbm.render.entity.rocket.part;

import org.lwjgl.opengl.GL11;

import com.hbm.dim.CelestialBody;
import com.hbm.entity.missile.EntityRideableRocket;
import com.hbm.entity.missile.EntityRideableRocket.RocketState;
import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.MathHelper;

public class RenderDropPod extends RenderRocketPart {

	private boolean brakes;
	private long brakeStart;

	@Override
	public void render(TextureManager tex, EntityRideableRocket rocket, float interp) {
		tex.bindTexture(ResourceManager.drop_pod_tex);

		if(CelestialBody.inOrbit(rocket.worldObj)) {
			ResourceManager.drop_pod.renderAll();
			return;
		}

		RocketState state = rocket.getState();
		int timer = rocket.getStateTimer();
		float lerpTimer = (float)timer + interp;
		
		ResourceManager.drop_pod.renderPart("DropPod");

		// Render door
		GL11.glPushMatrix();
		{

			float doorRotation = 0;
			if(state == RocketState.LANDED) {
				doorRotation = MathHelper.clamp_float(lerpTimer * 2, 0, 90);
			} else if(state == RocketState.AWAITING) {
				doorRotation = MathHelper.clamp_float(90 - lerpTimer * 2, 0, 90);
			}

			GL11.glTranslatef(0.69291F, 2.8333F, 0);
			GL11.glRotatef(doorRotation, 0, 0, 1);
			GL11.glTranslatef(-0.69291F, -2.8333F, 0);
			ResourceManager.drop_pod.renderPart("Door");

		}
		GL11.glPopMatrix();

		// Render airbrakes
		GL11.glPushMatrix();
		{

			float brakeRotation = 0;
			if(state == RocketState.LANDING) {
				if(rocket.motionY > -0.4F) {
					if(brakes) {
						brakes = false;
						brakeStart = System.currentTimeMillis();
					}
				} else {
					brakes = true;
				}

				if(!brakes) {
					float t = MathHelper.clamp_float((float)(System.currentTimeMillis() - brakeStart) / 1000, 0, 1);
					brakeRotation = (1 - t) * 65;
				} else {
					brakeRotation = 65;
				}
			}

			for(int i = 0; i < 4; i++) {
				GL11.glPushMatrix();
				{

					GL11.glRotatef(i * 90 - 45, 0, 1, 0);
					GL11.glTranslated(0.46194, 3.5, 0);
					GL11.glRotatef(brakeRotation, 0, 0, 1);
					GL11.glTranslated(-0.46194, -3.5, 0);
					GL11.glRotatef(45, 0, 1, 0);
					ResourceManager.drop_pod.renderPart("Airbrake0");

				}
				GL11.glPopMatrix();
			}

		}
		GL11.glPopMatrix();

		// Render legs
		GL11.glPushMatrix();
		{

			double legExtension = 1;
			if(state == RocketState.LAUNCHING) legExtension = 0;

			GL11.glTranslated(0, -legExtension * 0.5, 0);
			ResourceManager.drop_pod.renderPart("Legs");

		}
		GL11.glPopMatrix();
	}

}
