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
import net.minecraft.client.renderer.Tessellator;
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

	public static final ResourceLocation bobble_vaultboy = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/vaultboy.png");
	public static final ResourceLocation bobble_hbm = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/hbm.png");
	public static final ResourceLocation bobble_pu238 = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/pellet.png");
	public static final ResourceLocation bobble_frizzle = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/frizzle.png");
	public static final ResourceLocation bobble_vt = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/vt.png");
	public static final ResourceLocation bobble_cirno = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/cirno.png");

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
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		
		TextureManager texman = Minecraft.getMinecraft().getTextureManager();
		
		texman.bindTexture(socket);
		bobble.renderPart("Socket");
		
		switch(type) {
		case STRENGTH:
		case PERCEPTION:
		case ENDURANCE:
		case CHARISMA:
		case INTELLIGENCE:
		case AGILITY:
		case LUCK:		texman.bindTexture(bobble_vaultboy); break;
			
		case BOB:		texman.bindTexture(bobble_hbm); break;
		case PU238:		texman.bindTexture(bobble_pu238); break;
		case FRIZZLE:	texman.bindTexture(bobble_frizzle); break;
		case VT:		texman.bindTexture(bobble_vt); break;
		case CIRNO:		texman.bindTexture(bobble_cirno); break;
		default:		texman.bindTexture(ResourceManager.universal);
		}
		
		switch(type) {
		case PU238:	renderPellet(type); break;
		default: renderGuy(type);
		}
		
		renderSocket(type);
	}
	
	/*
	 * RENDER STANDARD PLAYER MODEL
	 */

	public static double[] rotLeftArm = {0, 0, 0};
	public static double[] rotRightArm = {0, 0, 0};
	public static double[] rotLeftLeg = {0, 0, 0};
	public static double[] rotRightLeg = {0, 0, 0};
	public static double rotBody = 0;
	public static double[] rotHead = {0, 0, 0};
	
	public void resetFigurineRotation() {
		rotLeftArm = new double[]{0, 0, 0};
		rotRightArm = new double[]{0, 0, 0};
		rotLeftLeg = new double[]{0, 0, 0};
		rotRightLeg = new double[]{0, 0, 0};
		rotBody = 0;
		rotHead = new double[]{0, 0, 0};
	}
	
	@SuppressWarnings("incomplete-switch") // shut up
	public void setupFigurineRotation(BobbleType type) {
		switch(type) {
		case STRENGTH:
			rotLeftArm = new double[]{0, 25, 135};
			rotRightArm = new double[]{0, -45, 135};
			rotLeftLeg = new double[]{0, 0, -5};
			rotRightLeg = new double[]{0, 0, 5};
			rotHead = new double[]{15, 0, 0};
			break;
		case PERCEPTION:
			rotLeftArm = new double[]{0, -15, 135};
			rotRightArm = new double[]{-5, 0, 0};
			break;
		case ENDURANCE:
			rotBody = 45;
			rotLeftArm = new double[]{0, -25, 30};
			rotRightArm = new double[]{0, 45, 30};
			rotHead = new double[]{0, -45, 0};
			break;
		case CHARISMA:
			rotBody = 45;
			rotRightArm = new double[]{0, -45, 90};
			rotLeftLeg = new double[]{0, 0, -5};
			rotRightLeg = new double[]{0, 0, 5};
			rotHead = new double[]{-5, -45, 0};
			break;
		case INTELLIGENCE:
			rotHead = new double[]{0, 30, 0};
			rotLeftArm = new double[]{5, 0, 0};
			rotRightArm = new double[]{15, 0, 170};
			break;
		case AGILITY:
			rotLeftArm = new double[]{0, 0, 60};
			rotRightArm = new double[]{0, 0, -45};
			rotLeftLeg = new double[]{0, 0, -15};
			rotRightLeg = new double[]{0, 0, 45};
			break;
		case LUCK:
			rotLeftArm = new double[]{135, 45, 0};
			rotRightArm = new double[]{-135, -45, 0};
			rotRightLeg = new double[]{-5, 0, 0};
			break;
		case BOB: break;
		case PU238: break;
		case VT:
			rotLeftArm = new double[]{0, -45, 60};
			rotRightArm = new double[]{0, 0, 45};
			rotLeftLeg = new double[]{2, 0, 0};
			rotRightLeg = new double[]{-2, 0, 0};
			break;
		case CIRNO: break;
		}
	}
	
	public void renderGuy(BobbleType type) {

		resetFigurineRotation();
		setupFigurineRotation(type);

		GL11.glPushMatrix();
		GL11.glRotated(rotBody, 0, 1, 0);
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		String suffix = type.skinLayers ? "" : "17";
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		//LEFT LEG//
		GL11.glPushMatrix();
			GL11.glTranslated(0, 1, -0.125);
			GL11.glRotated(rotLeftLeg[0], 1, 0, 0);
			GL11.glRotated(rotLeftLeg[1], 0, 1, 0);
			GL11.glRotated(rotLeftLeg[2], 0, 0, 1);
			GL11.glTranslated(0, -1, 0.125);
			bobble.renderPart("LL" + suffix);
		GL11.glPopMatrix();

		//RIGHT LEG//
		GL11.glPushMatrix();
			GL11.glTranslated(0, 1, 0.125);
			GL11.glRotated(rotRightLeg[0], 1, 0, 0);
			GL11.glRotated(rotRightLeg[1], 0, 1, 0);
			GL11.glRotated(rotRightLeg[2], 0, 0, 1);
			GL11.glTranslated(0, -1, -0.125);
			bobble.renderPart("RL" + suffix);
		GL11.glPopMatrix();
		
		//renderOrigin();

		//LEFT ARM//
		GL11.glPushMatrix();
			GL11.glTranslated(0, 1.625, -0.25);
			GL11.glRotated(rotLeftArm[0], 1, 0, 0);
			GL11.glRotated(rotLeftArm[1], 0, 1, 0);
			GL11.glRotated(rotLeftArm[2], 0, 0, 1);
			GL11.glTranslated(0, -1.625, 0.25);
			bobble.renderPart("LA" + suffix);
		GL11.glPopMatrix();

		//RIGHT ARM//
		GL11.glPushMatrix();
			GL11.glTranslated(0, 1.625, 0.25);
			GL11.glRotated(rotRightArm[0], 1, 0, 0);
			GL11.glRotated(rotRightArm[1], 0, 1, 0);
			GL11.glRotated(rotRightArm[2], 0, 0, 1);
			GL11.glTranslated(0, -1.625, -0.25);
			bobble.renderPart("RA" + suffix);
		GL11.glPopMatrix();

		//BODY//
		bobble.renderPart("Body" + suffix);

		//HEAD//
		double speed = 0.005;
		double amplitude = 1;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.75, 0);
		GL11.glRotated(Math.sin(System.currentTimeMillis() * speed) * amplitude, 1, 0, 0);
		GL11.glRotated(Math.sin(System.currentTimeMillis() * speed + (Math.PI * 0.5)) * amplitude, 0, 0, 1);
		
		GL11.glRotated(rotHead[0], 1, 0, 0);
		GL11.glRotated(rotHead[1], 0, 1, 0);
		GL11.glRotated(rotHead[2], 0, 0, 1);
		
		GL11.glTranslated(0, -1.75, 0);
		bobble.renderPart("Head" + suffix);
		
		if(type == BobbleType.VT)
			bobble.renderPart("Horn");
		
		GL11.glPopMatrix();
		
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}
	
	public void renderPellet(BobbleType type) {

		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		GL11.glDisable(GL11.GL_LIGHTING);
		bobble.renderPart("Pellet");
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glColor4f(1.0F, 1.0F, 0.0F, 0.1F + (float)Math.sin(System.currentTimeMillis() * 0.001D) * 0.05F);
		bobble.renderPart("PelletShine");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	
	public void renderOrigin() {

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tess = Tessellator.instance;
		tess.setColorOpaque_F(1F, 0F, 0F);
		tess.startDrawing(GL11.GL_TRIANGLES);
		
		double d = 0.125D;
		tess.addVertex(0, d, 0);
		tess.addVertex(d, 0, 0);
		tess.addVertex(0, 0, d);
		
		tess.addVertex(0, d, 0);
		tess.addVertex(-d, 0, 0);
		tess.addVertex(0, 0, d);
		
		tess.addVertex(0, d, 0);
		tess.addVertex(d, 0, 0);
		tess.addVertex(0, 0, -d);
		
		tess.addVertex(0, d, 0);
		tess.addVertex(-d, 0, 0);
		tess.addVertex(0, 0, -d);
		
		tess.addVertex(0, -d, 0);
		tess.addVertex(d, 0, 0);
		tess.addVertex(0, 0, d);
		
		tess.addVertex(0, -d, 0);
		tess.addVertex(d, 0, 0);
		tess.addVertex(0, 0, -d);
		
		tess.addVertex(0, -d, 0);
		tess.addVertex(-d, 0, 0);
		tess.addVertex(0, 0, d);
		
		tess.addVertex(0, -d, 0);
		tess.addVertex(-d, 0, 0);
		tess.addVertex(0, 0, -d);
		
		tess.draw();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	/*
	 * RENDER BASE PEDESTAL
	 */
	public void renderSocket(BobbleType type) {

		GL11.glDisable(GL11.GL_LIGHTING);
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		float f3 = 0.01F;
		GL11.glTranslated(0.63, 0.175F, 0.0);
		GL11.glScalef(f3, -f3, f3);
		GL11.glTranslated(0, 0, font.getStringWidth(type.label) * 0.5D);
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glDepthMask(false);
		GL11.glTranslatef(0, 1, 0);
		font.drawString(type.label, 0, 0, type == BobbleType.VT ? 0xff0000 : 0xffffff);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

}
