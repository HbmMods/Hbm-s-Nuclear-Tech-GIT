package com.hbm.dim;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IRenderHandler;

public class SkyProviderMoon extends IRenderHandler {

	private static final ResourceLocation earth = new ResourceLocation("hfr:textures/sky/earth.png");
	private static final ResourceLocation sun = new ResourceLocation("minecraft:textures/environment/sun.png");
	// private static final ResourceLocation sun = new
	// ResourceLocation("hfr:textures/sky/sun.png");
	//thanks xradar
	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {

		GL11.glPushMatrix();
		GL11.glDepthMask(false);

		Tessellator tessellator = Tessellator.instance;

		GL11.glEnable(3553);
		GL11.glBlendFunc(770, 1);

		GL11.glPushMatrix();

		GL11.glRotatef(60.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
		float var12 = 30.0F;
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(sun);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-var12, 150.0D, -var12, 0.0D, 0.0D);
		tessellator.addVertexWithUV(var12, 150.0D, -var12, 0.0D, 1.0D);
		tessellator.addVertexWithUV(var12, 150.0D, var12, 1.0D, 1.0D);
		tessellator.addVertexWithUV(-var12, 150.0D, var12, 1.0D, 0.0D);
		tessellator.draw();

		GL11.glPopMatrix();

		GL11.glPushMatrix();

		GL11.glDisable(3042);
		int size = 15;

		FMLClientHandler.instance().getClient().renderEngine.bindTexture(earth);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(size, -size + 15, 100, 0.0D, 1.0D);
		tessellator.addVertexWithUV(-size, -size + 15, 100, 1.0D, 1.0D);
		tessellator.addVertexWithUV(-size, size + 15, 100, 1.0D, 0.0D);
		tessellator.addVertexWithUV(size, size + 15, 100, 0.0D, 0.0D);
		tessellator.draw();

		GL11.glPopMatrix();

		GL11.glDepthMask(true);
		GL11.glPopMatrix();
	}

}