package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.generic.BlockBobble.BobbleType;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderBobble extends TileEntitySpecialRenderer {
	
	public static RenderBobble instance = new RenderBobble();
	
	public static final IModelCustom bobble = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/trinkets/bobble.obj"));
	public static final ResourceLocation socket = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/socket.png");

	public static final ResourceLocation bobble_hbm = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/hbm.png");
	public static final ResourceLocation bobble_cirno = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/cirno.png");

	@SuppressWarnings("incomplete-switch") //shut up
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float intero) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		double scale = 0.25D;
		GL11.glScaled(scale, scale, scale);
		
		TileEntityBobble te = (TileEntityBobble) tile;
		BobbleType type = te.type;
		
		GL11.glRotated(22.5D * tile.getBlockMetadata() + 90, 0, -1, 0);
		
		renderBobble(type);
		
		GL11.glPopMatrix();
	}
	
	public void renderBobble(BobbleType type) {
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		TextureManager texman = Minecraft.getMinecraft().getTextureManager();
		
		texman.bindTexture(socket);
		bobble.renderPart("Socket");
		
		switch(type) {
		case BOB:	texman.bindTexture(bobble_hbm); break;
		case CIRNO:	texman.bindTexture(bobble_cirno); break;
		default: texman.bindTexture(ResourceManager.universal);
		}
		
		String suffix = type.skinLayers ? "" : "17";
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		bobble.renderPart("LL" + suffix);
		bobble.renderPart("RL" + suffix);
		bobble.renderPart("LA" + suffix);
		bobble.renderPart("RA" + suffix);
		bobble.renderPart("Body" + suffix);
		
		double speed = 0.005;
		double amplitude = 1;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.75, 0);
		GL11.glRotated(Math.sin(System.currentTimeMillis() * speed) * amplitude, 1, 0, 0);
		GL11.glRotated(Math.sin(System.currentTimeMillis() * speed + (Math.PI * 0.5)) * amplitude, 0, 0, 1);
		GL11.glTranslated(0, -1.75, 0);
		bobble.renderPart("Head" + suffix);
		GL11.glPopMatrix();
		
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		float f3 = 0.01F;
		GL11.glTranslated(0.63, 0.175F, 0.0);
		GL11.glScalef(f3, -f3, f3);
		GL11.glTranslated(0, 0, font.getStringWidth(type.label) * 0.5D);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glDepthMask(false);
		GL11.glTranslatef(0, 1, 0);
		font.drawString(type.label, 0, 0, 0xffffff);
		GL11.glDepthMask(true);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

}
