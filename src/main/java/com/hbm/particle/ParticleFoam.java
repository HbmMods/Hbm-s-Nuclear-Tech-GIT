package com.hbm.particle;

import com.hbm.main.ModEventHandlerClient;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SideOnly(Side.CLIENT)
public class ParticleFoam extends EntityFX {

	private int age;
	public int maxAge;
	private float baseScale = 1.0F;
	private float maxScale = 1.5F;

	// Parameters for the trail effect
	private List<TrailPoint> trail = new ArrayList<TrailPoint>();
	private int trailLength = 15;
	private float initialVelocity;
	private float buoyancy = 0.05F;
	private float jitter = 0.15F;
	private float drag = 0.96F;
	private int explosionPhase; // 0=burst up, 1=peak, 2=settle

	private static class TrailPoint {
		double x, y, z;

		public TrailPoint(double x, double y, double z, float alpha) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}

	public ParticleFoam(TextureManager p_i1213_1_, World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		particleIcon = ModEventHandlerClient.particleBase;

		maxAge = 60 + rand.nextInt(60);
		particleGravity = 0.005F + rand.nextFloat() * 0.015F;

		initialVelocity = 2.0F + rand.nextFloat() * 3.0F;
		motionY = initialVelocity;

		double angle = rand.nextDouble() * Math.PI * 2;
		double strength = rand.nextDouble() * 0.5;
		motionX = Math.cos(angle) * strength;
		motionZ = Math.sin(angle) * strength;

		explosionPhase = 0; // Start in burst phase

		particleScale = 0.3F + rand.nextFloat() * 0.7F;
	}

	public void setBaseScale(float f) { this.baseScale = f; }
	public void setMaxScale(float f) { this.maxScale = f; }
	public void setTrailLength(int length) { this.trailLength = length; }
	public void setBuoyancy(float buoyancy) { this.buoyancy = buoyancy; }

	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		trail.add(0, new TrailPoint(posX, posY, posZ, particleAlpha));

		while (trail.size() > trailLength) {
			trail.remove(trail.size() - 1);
		}

		// Update age and phase
		++this.age;

		if (this.age == this.maxAge) {
			this.setDead();
		}

		float phaseRatio = (float) age / (float) maxAge;
		if (phaseRatio < 0.3F) {
			explosionPhase = 0;

			if (phaseRatio < 0.15F) {
				motionY += buoyancy * 6.0F;
			} else {
				motionY += buoyancy * (1.0F - (phaseRatio / 0.3F)) * 2.0F;
			}

			particleScale = baseScale + (maxScale - baseScale) * (phaseRatio / 0.3F);
		} else if (phaseRatio < 0.6F) {
			explosionPhase = 1;
			motionY *= 0.98F;

			particleScale = maxScale;
		} else {
			explosionPhase = 2;
			motionY -= particleGravity;

			particleScale = maxScale * (1.0F - ((phaseRatio - 0.6F) / 0.4F) * 0.7F);
		}

		particleAlpha = 0.8F * (1.0F - phaseRatio * phaseRatio);

		motionX += (rand.nextFloat() - 0.5F) * jitter;
		motionZ += (rand.nextFloat() - 0.5F) * jitter;

		// drag like ninja drags the low taper fade
		motionX *= drag;
		motionY *= drag;
		motionZ *= drag;

		this.moveEntity(this.motionX, motionY, this.motionZ);

		// Kill particle if it hits ground
		if (this.onGround || this.isInWeb) {
			this.setDead();
		}
	}

	public int getFXLayer() {
		return 1;
	}

	// ty kercig cuz id kms lol

	public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
		renderFoamBubbles(p_70539_1_, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_,
			posX, posY, posZ, particleScale, particleAlpha);

		for (int i = 1; i < trail.size(); i++) {
			TrailPoint point = trail.get(i);
			float trailScale = particleScale * (1.0F - (float)i / trailLength);
			float trailAlpha = particleAlpha * (1.0F - (float)i / trailLength) * 0.7F;

			renderFoamBubbles(p_70539_1_, p_70539_2_, p_70539_3_, p_70539_4_, p_70539_5_, p_70539_6_, p_70539_7_,
				point.x, point.y, point.z, trailScale, trailAlpha);
		}
	}

	private void renderFoamBubbles(Tessellator tessellator, float p_70539_2_, float p_70539_3_, float p_70539_4_,
								   float p_70539_5_, float p_70539_6_, float p_70539_7_, double x, double y, double z, float scale, float alpha) {

		Random urandom = new Random(this.getEntityId() + (long)(x * 100) + (long)(y * 10) + (long)z);

		int bubbleCount = explosionPhase == 0 ? 8 : (explosionPhase == 1 ? 6 : 4);

		for (int i = 0; i < bubbleCount; i++) {
			float whiteness = 0.9F + urandom.nextFloat() * 0.1F;
			tessellator.setColorRGBA_F(whiteness, whiteness, whiteness, alpha);
			tessellator.setNormal(0.0F, 1.0F, 0.0F);

			float bubbleScale = scale * (urandom.nextFloat() * 0.5F + 0.75F);
			float offset = explosionPhase == 0 ? 0.4F : (explosionPhase == 1 ? 0.6F : 0.9F);

			float pX = (float) ((x - interpPosX) + (urandom.nextGaussian()) * offset);
			float pY = (float) ((y - interpPosY) + (urandom.nextGaussian()) * offset * 0.7F);
			float pZ = (float) ((z - interpPosZ) + (urandom.nextGaussian()) * offset);

			tessellator.addVertexWithUV(
				(double)(pX - p_70539_3_ * bubbleScale - p_70539_6_ * bubbleScale),
				(double)(pY - p_70539_4_ * bubbleScale),
				(double)(pZ - p_70539_5_ * bubbleScale - p_70539_7_ * bubbleScale),
				particleIcon.getMaxU(), particleIcon.getMaxV());
			tessellator.addVertexWithUV(
				(double)(pX - p_70539_3_ * bubbleScale + p_70539_6_ * bubbleScale),
				(double)(pY + p_70539_4_ * bubbleScale),
				(double)(pZ - p_70539_5_ * bubbleScale + p_70539_7_ * bubbleScale),
				particleIcon.getMaxU(), particleIcon.getMinV());
			tessellator.addVertexWithUV(
				(double)(pX + p_70539_3_ * bubbleScale + p_70539_6_ * bubbleScale),
				(double)(pY + p_70539_4_ * bubbleScale),
				(double)(pZ + p_70539_5_ * bubbleScale + p_70539_7_ * bubbleScale),
				particleIcon.getMinU(), particleIcon.getMinV());
			tessellator.addVertexWithUV(
				(double)(pX + p_70539_3_ * bubbleScale - p_70539_6_ * bubbleScale),
				(double)(pY - p_70539_4_ * bubbleScale),
				(double)(pZ + p_70539_5_ * bubbleScale - p_70539_7_ * bubbleScale),
				particleIcon.getMinU(), particleIcon.getMaxV());
		}
	}
}
