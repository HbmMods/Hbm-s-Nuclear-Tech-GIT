package com.hbm.render.world;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.IRenderHandler;

public class RenderNTMSkybox extends IRenderHandler { //why an abstract class uses the I-prefix is beyond me but ok, alright, whatever
	
	/*
	 * To get the terrain render order right, making a sky rendering handler is absolutely necessary. Turns out MC can only handle one of these, so what do we do?
	 * We make out own renderer, grab any existing renderers that are already occupying the slot, doing what is effectively chainloading while adding our own garbage.
	 * If somebody does the exact same thing as we do we might be screwed due to increasingly long recursive loops but we can fix that too, no worries.
	 */
	private IRenderHandler parent;
	
	private static final ResourceLocation digammaStar = new ResourceLocation("hbm:textures/misc/star_digamma.png");
	
	/*
	 * If the skybox was rendered successfully in the last tick (even from other mods' skyboxes chainloading this one) then we don't need to add it again
	 */
	public static boolean didLastRender = false;
	
	public RenderNTMSkybox(IRenderHandler parent) {
		this.parent = parent;
	}
	
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		
		GL11.glPushMatrix();
		GL11.glDepthMask(false);

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		OpenGlHelper.glBlendFunc(770, 1, 1, 0);
		
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(140.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-40.0F, 0.0F, 0.0F, 1.0F);
		
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(digammaStar);
		
		float var12 = 2.5F;
		double dist = 150D;
		
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-var12, dist, -var12, 0.0D, 0.0D);
		tessellator.addVertexWithUV(var12, dist, -var12, 0.0D, 1.0D);
		tessellator.addVertexWithUV(var12, dist, var12, 1.0D, 1.0D);
		tessellator.addVertexWithUV(-var12, dist, var12, 1.0D, 0.0D);
		tessellator.draw();
		
		GL11.glDepthMask(true);
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		
		GL11.glPopMatrix();
		
		didLastRender = true;
		
		if(parent != null) {
			parent.render(partialTicks, world, mc);
		} else{
			RenderGlobal rg = Minecraft.getMinecraft().renderGlobal;
			world.provider.setSkyRenderer(null);
			//rg.renderSky(partialTicks);
			world.provider.setSkyRenderer(this);
		}
	}

}
