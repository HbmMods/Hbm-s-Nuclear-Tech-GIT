package com.hbm.render.world;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.lwjgl.opengl.GL11;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.ImpactWorldHandler;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.saveddata.TomSaveData;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;
import scala.languageFeature.implicitConversions;

public class RenderNTMSkyboxChainloader extends IRenderHandler { //why an abstract class uses the I-prefix is beyond me but ok, alright, whatever
	
	/*
	 * To get the terrain render order right, making a sky rendering handler is absolutely necessary. Turns out MC can only handle one of these, so what do we do?
	 * We make out own renderer, grab any existing renderers that are already occupying the slot, doing what is effectively chainloading while adding our own garbage.
	 * If somebody does the exact same thing as we do we might be screwed due to increasingly long recursive loops but we can fix that too, no worries.
	 */
	private IRenderHandler parent;

	private static final ResourceLocation digammaStar = new ResourceLocation("hbm:textures/misc/star_digamma.png");
	private static final ResourceLocation bobmazonSat = new ResourceLocation("hbm:textures/misc/sat_bobmazon.png");
	private static final ResourceLocation nova = new ResourceLocation("hbm:textures/misc/sunSpikes.png");
	private static final ResourceLocation nova2 = new ResourceLocation("hbm:textures/misc/sunSpikes2.png");
	private static final ResourceLocation flash = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");
	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/shockwave.png");

	/*
	 * If the skybox was rendered successfully in the last tick (even from other mods' skyboxes chainloading this one) then we don't need to add it again
	 */
	public static boolean didLastRender = false;
	
	public RenderNTMSkyboxChainloader(IRenderHandler parent) {
		this.parent = parent;
	}
	
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		boolean test = ImpactWorldHandler.getDivinityForClient(world);
		float flash = ImpactWorldHandler.getFlashForClient(world);

		if(parent != null) {
			
			//basically a recursion-brake to prevent endless rendering loops from other mods' chainloaders.
			//do other mods' skyboxes even employ chainloading?
			if(!didLastRender) {
				didLastRender = true;
				parent.render(partialTicks, world, mc);
				didLastRender = false;
			}
			
		} else{
			RenderGlobal rg = Minecraft.getMinecraft().renderGlobal;
			world.provider.setSkyRenderer(null);
			rg.renderSky(partialTicks);
			world.provider.setSkyRenderer(this);
		}
		
		GL11.glPushMatrix();
		GL11.glDepthMask(false);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_FOG);
		OpenGlHelper.glBlendFunc(770, 1, 1, 0);
		
		float brightness = (float) Math.sin(world.getCelestialAngle(partialTicks) * Math.PI);
		brightness *= brightness;
		
		GL11.glColor4f(brightness, brightness, brightness, 1.0F);
		
		GL11.glPushMatrix();
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-40.0F, 0.0F, 0.0F, 1.0F);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(digammaStar);
		
		float digamma = HbmLivingProps.getDigamma(Minecraft.getMinecraft().thePlayer);
		float var12 = 1F * (1 + digamma * 0.25F);
		double dist = 100D - digamma * 2.5;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-var12, dist, -var12, 0.0D, 0.0D);
		tessellator.addVertexWithUV(var12, dist, -var12, 0.0D, 1.0D);
		tessellator.addVertexWithUV(var12, dist, var12, 1.0D, 1.0D);
		tessellator.addVertexWithUV(-var12, dist, var12, 1.0D, 0.0D);
		tessellator.draw();
		GL11.glPopMatrix();
			if(test) {
			brightness *= brightness;
			
			GL11.glColor4f(brightness, brightness, brightness, 1.0F);
			
			GL11.glPushMatrix();
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-39.7F, 0.0F, 0.0F, 1.0F);
			
			mc.renderEngine.bindTexture(nova);
			float var13 = flash / 2;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-var13, 100.0D, -var13, 0.0D, 0.0D);
			tessellator.addVertexWithUV(var13, 100.0D, -var13, 1.0D, 0.0D);
			tessellator.addVertexWithUV(var13, 100.0D, var13, 1.0D, 1.0D);
			tessellator.addVertexWithUV(-var13, 100.0D, var13, 0.0D, 1.0D);
			tessellator.draw();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glPopMatrix();

			{
				brightness *= brightness;
				float var14 = flash;
				float alpha = 1.0F - Math.min(1.0F, var14 / 100);
				GL11.glColor4f(brightness, brightness, brightness, alpha);
				
				GL11.glPushMatrix();
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-39.7F, 0.0F, 0.0F, 1.0F);
				
				mc.renderEngine.bindTexture(texture);
				// flashMaxValue is the maximum value flash can reach

				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-var14, 100.0D, -var14, 0.0D, 0.0D);
				tessellator.addVertexWithUV(var14, 100.0D, -var14, 1.0D, 0.0D);
				tessellator.addVertexWithUV(var14, 100.0D, var14, 1.0D, 1.0D);
				tessellator.addVertexWithUV(-var14, 100.0D, var14, 0.0D, 1.0D);
				tessellator.draw();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
			{
				float var14 = flash;
				float var15 = Math.min(70, flash * 2 );
				float alpha = 1.0F - Math.min(1.0F, var14 / 100);
				GL11.glColor4f(brightness, brightness, brightness, alpha);
				
				GL11.glPushMatrix();
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-39.7F, 0.0F, 0.0F, 1.0F);
				
				mc.renderEngine.bindTexture(RenderNTMSkyboxChainloader.flash);
				// flashMaxValue is the maximum value flash can reach

				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-var15, 100.0D, -var15, 0.0D, 0.0D);
				tessellator.addVertexWithUV(var15, 100.0D, -var15, 1.0D, 0.0D);
				tessellator.addVertexWithUV(var15, 100.0D, var15, 1.0D, 1.0D);
				tessellator.addVertexWithUV(-var15, 100.0D, var15, 0.0D, 1.0D);
				tessellator.draw();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
			{
				float var19 = flash;
				float var16 = Math.max(0, flash);
				float alpha = 1.0F - Math.min(1.0F, var19 / 100);
				GL11.glColor4f(brightness, brightness, brightness, alpha);
				
				GL11.glPushMatrix();
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-39.7F, 0.0F, 0.0F, 1.0F);
				
				mc.renderEngine.bindTexture(nova2);
				tessellator.startDrawingQuads();
				tessellator.addVertexWithUV(-var16, 100.0D, -var16, 0.0D, 0.0D);
				tessellator.addVertexWithUV(var16, 100.0D, -var16, 1.0D, 0.0D);
				tessellator.addVertexWithUV(var16, 100.0D, var16, 1.0D, 1.0D);
				tessellator.addVertexWithUV(-var16, 100.0D, var16, 0.0D, 1.0D);
				tessellator.draw();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
			}
		GL11.glPushMatrix();
		GL11.glRotatef(-40.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef((System.currentTimeMillis() % (360 * 1000) / 1000F), 0.0F, 1.0F, 0.0F);
		GL11.glRotatef((System.currentTimeMillis() % (360 * 100) / 100F), 1.0F, 0.0F, 0.0F);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(bobmazonSat);
		
		var12 = 0.5F;
		dist = 100D;
		
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-var12, dist, -var12, 0.0D, 0.0D);
		tessellator.addVertexWithUV(var12, dist, -var12, 0.0D, 1.0D);
		tessellator.addVertexWithUV(var12, dist, var12, 1.0D, 1.0D);
		tessellator.addVertexWithUV(-var12, dist, var12, 1.0D, 0.0D);
		tessellator.draw();
		GL11.glPopMatrix();
		
		GL11.glDepthMask(true);

		GL11.glEnable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		GL11.glPopMatrix();
	}

}