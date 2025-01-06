package com.hbm.render.entity.effect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.effect.EntityNukeTorex.Cloudlet;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerClient;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderTorex extends Render {

	private static final ResourceLocation cloudlet = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");
	private static final ResourceLocation flash = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float interp) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		boolean fog = GL11.glIsEnabled(GL11.GL_FOG);
		if(fog) GL11.glDisable(GL11.GL_FOG);
		EntityNukeTorex cloud = (EntityNukeTorex)entity;
		cloudletWrapper(cloud, interp);
		if(cloud.ticksExisted < 101) flashWrapper(cloud, interp);
		if(cloud.ticksExisted < 10 && System.currentTimeMillis() - ModEventHandlerClient.flashTimestamp > 1_000) ModEventHandlerClient.flashTimestamp = System.currentTimeMillis();
		if(cloud.didPlaySound && !cloud.didShake && System.currentTimeMillis() - ModEventHandlerClient.shakeTimestamp > 1_000) {
			ModEventHandlerClient.shakeTimestamp = System.currentTimeMillis();
			cloud.didShake = true;
			EntityPlayer player = MainRegistry.proxy.me();
			player.hurtTime = 15;
			player.maxHurtTime = 15;
			player.attackedAtYaw = 0F;
		}
		if(fog) GL11.glEnable(GL11.GL_FOG);
		GL11.glPopMatrix();
	}
	
	private Comparator cloudSorter = new Comparator() {

		@Override
		public int compare(Object arg0, Object arg1) {
			Cloudlet first = (Cloudlet) arg0;
			Cloudlet second = (Cloudlet) arg1;
			EntityPlayer player = MainRegistry.proxy.me();
			double dist1 = player.getDistanceSq(first.posX, first.posY, first.posZ);
			double dist2 = player.getDistanceSq(second.posX, second.posY, second.posZ);
			
			return dist1 > dist2 ? -1 : dist1 == dist2 ? 0 : 1;
		}
	};

	private void cloudletWrapper(EntityNukeTorex cloud, float interp) {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		// To prevent particles cutting off before fully fading out
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		RenderHelper.disableStandardItemLighting();

		bindTexture(cloudlet);

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		
		ArrayList<Cloudlet> cloudlets = new ArrayList(cloud.cloudlets);
		cloudlets.sort(cloudSorter);
		
		for(Cloudlet cloudlet : cloudlets) {
			Vec3 vec = cloudlet.getInterpPos(interp);
			double x = vec.xCoord - cloud.posX;
			double y = vec.yCoord - cloud.posY;
			double z = vec.zCoord - cloud.posZ;
			tessellateCloudlet(tess, x, y, z, cloudlet, interp);
		}

		tess.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	private void flashWrapper(EntityNukeTorex cloud, float interp) {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		RenderHelper.disableStandardItemLighting();

		bindTexture(flash);

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		
		double age = Math.min(cloud.ticksExisted + interp, 100);
		float alpha = (float) ((100D - age) / 100F);
		
		Random rand = new Random(cloud.getEntityId());
		
		for(int i = 0; i < 3; i++) {
			float x = (float) (rand.nextGaussian() * 0.5F * cloud.rollerSize);
			float y = (float) (rand.nextGaussian() * 0.5F * cloud.rollerSize);
			float z = (float) (rand.nextGaussian() * 0.5F * cloud.rollerSize);
			tessellateFlash(tess, x, y + cloud.coreHeight, z, (float) (25 * cloud.rollerSize), alpha, interp);
		}

		tess.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	private void tessellateCloudlet(Tessellator tess, double posX, double posY, double posZ, Cloudlet cloud, float interp) {

		float alpha = cloud.getAlpha();
		float scale = cloud.getScale();

		float f1 = ActiveRenderInfo.rotationX;
		float f2 = ActiveRenderInfo.rotationZ;
		float f3 = ActiveRenderInfo.rotationYZ;
		float f4 = ActiveRenderInfo.rotationXY;
		float f5 = ActiveRenderInfo.rotationXZ;

		float brightness = cloud.type == cloud.type.CONDENSATION ? 0.9F : 0.75F * cloud.colorMod;
		Vec3 color = cloud.getInterpColor(interp);
		tess.setColorRGBA_F((float)color.xCoord * brightness, (float)color.yCoord * brightness, (float)color.zCoord * brightness, alpha);

		tess.addVertexWithUV((double) (posX - f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ - f2 * scale - f4 * scale), 1, 1);
		tess.addVertexWithUV((double) (posX - f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ - f2 * scale + f4 * scale), 1, 0);
		tess.addVertexWithUV((double) (posX + f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ + f2 * scale + f4 * scale), 0, 0);
		tess.addVertexWithUV((double) (posX + f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ + f2 * scale - f4 * scale), 0, 1);

	}

	private void tessellateFlash(Tessellator tess, double posX, double posY, double posZ, float scale, float alpha, float interp) {

		float f1 = ActiveRenderInfo.rotationX;
		float f2 = ActiveRenderInfo.rotationZ;
		float f3 = ActiveRenderInfo.rotationYZ;
		float f4 = ActiveRenderInfo.rotationXY;
		float f5 = ActiveRenderInfo.rotationXZ;

		tess.setColorRGBA_F(1F, 1F, 1F, alpha);

		tess.addVertexWithUV((double) (posX - f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ - f2 * scale - f4 * scale), 1, 1);
		tess.addVertexWithUV((double) (posX - f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ - f2 * scale + f4 * scale), 1, 0);
		tess.addVertexWithUV((double) (posX + f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ + f2 * scale + f4 * scale), 0, 0);
		tess.addVertexWithUV((double) (posX + f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ + f2 * scale - f4 * scale), 0, 1);

	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
