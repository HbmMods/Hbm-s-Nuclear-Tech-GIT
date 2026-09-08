package com.hbm.render.tileentity;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.main.ResourceManager;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RendererObjTester extends TileEntitySpecialRenderer {

	//private static ResourceLocation extra = new ResourceLocation(RefStrings.MODID, "textures/models/horse/sunburst.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);

		bindTexture(ResourceManager.dropship_tex);
		ResourceManager.dropship.renderPart("Pod");

		for(int i = 0; i < 4; i++) {
			GL11.glPushMatrix();
			GL11.glRotated(45 + 90 * i, 0, 1, 0);
			GL11.glTranslated(0.5, 1.75, 0);
			GL11.glRotated(150, 0, 0, 1);
			GL11.glTranslated(-0.5, -1.75, 0);
			ResourceManager.dropship.renderPart("Leg");
			GL11.glPopMatrix();
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
	

	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GL11.glEnable(GL11.GL_LIGHTING);
		/*switch(tileEntity.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}*/

		//GL11.glScaled(5, 5, 5);
		
        /*GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		bindTexture(ResourceManager.sat_foeq_burning_tex);
		ResourceManager.sat_foeq_burning.renderAll();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		
		Random rand = new Random(System.currentTimeMillis() / 50);

        GL11.glScaled(1.15, 0.75, 1.15);
        GL11.glTranslated(0, -0.5, 0.3);
        GL11.glDisable(GL11.GL_CULL_FACE);
		for(int i = 0; i < 10; i++) {
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glColor3d(1, 0.75, 0.25);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
	        GL11.glTranslated(0, 2, 0);
			GL11.glColor3d(1, 0.5, 0);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
	        GL11.glTranslated(0, 2, 0);
			GL11.glColor3d(1, 0.25, 0);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
	        GL11.glTranslated(0, 2, 0);
			GL11.glColor3d(1, 0.0, 0);
	        GL11.glRotatef(rand.nextInt(360), 0F, 1F, 0F);
			ResourceManager.sat_foeq_fire.renderAll();
			
	        GL11.glTranslated(0, -3.8, 0);
	        
	        GL11.glScaled(0.95, 1.2, 0.95);
		}
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);*/

        //GL11.glTranslatef(0.0F, -0.25F, 0.0F);
        //GL11.glRotatef(-25, 0, 1, 0);
        //GL11.glRotatef(15, 0, 0, 1);
        
        /*GL11.glRotatef(-90, 0, 1, 0);
        GL11.glTranslated(0, 3, 0);
        bindTexture(ResourceManager.nikonium_tex);
        ResourceManager.nikonium.renderAll();
        GL11.glTranslated(0, -3, 0);
        GL11.glRotatef(90, 0, 1, 0);

        GL11.glShadeModel(GL11.GL_SMOOTH);
        bindTexture(ResourceManager.fstbmb_tex);
        ResourceManager.fstbmb.renderPart("Body");
        ResourceManager.fstbmb.renderPart("Balefire");
        
        bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/misc/glintBF.png"));
        RenderMiscEffects.renderClassicGlint(tileEntity.getWorldObj(), f, ResourceManager.fstbmb, "Balefire", 0.0F, 0.8F, 0.15F, 5, 2F);
        
        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        float f3 = 0.04F;
        GL11.glTranslatef(0.815F, 0.9275F, 0.5F);
        GL11.glScalef(f3, -f3, f3);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glDepthMask(false);
        GL11.glTranslatef(0, 1, 0);
        font.drawString("00:15", 0, 0, 0xff0000);
        GL11.glDepthMask(true);
        
        GL11.glShadeModel(GL11.GL_FLAT);*/
        
        GL11.glTranslated(0D, 4D, 0D);
        GL11.glRotated(System.currentTimeMillis() % 3600 / 10D, 0, 0, 1);
        GL11.glTranslated(0D, -4D, 0D);
        GL11.glRotated(System.currentTimeMillis() % 3600 / 10D, 0, 1, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		//GL11.glDisable(GL11.GL_TEXTURE_2D);
		RenderHelper.disableStandardItemLighting();
		RenderHelper.enableStandardItemLighting();
		GL11.glColor4d(1, 1, 1, 1);
		GL11.glClearColor(0, 0, 0, 0);

		float amb = 2F;
		float dif = 2F;
		float spe = 2F;
		float shi = 1F;
		FloatBuffer iamb = (FloatBuffer) BufferUtils.createFloatBuffer(8).put(new float[] { amb, amb, amb, 1F }).flip();
		FloatBuffer idif = (FloatBuffer) BufferUtils.createFloatBuffer(8).put(new float[] { dif, dif, dif, 1F }).flip();
		FloatBuffer ispe = (FloatBuffer) BufferUtils.createFloatBuffer(8).put(new float[] { spe, spe, spe, 1F }).flip();
		FloatBuffer mamb = (FloatBuffer) BufferUtils.createFloatBuffer(8).put(new float[] { amb, amb, amb, 1F }).flip();
		FloatBuffer mdif = (FloatBuffer) BufferUtils.createFloatBuffer(8).put(new float[] { dif, dif, dif, 1F }).flip();
		FloatBuffer mspe = (FloatBuffer) BufferUtils.createFloatBuffer(8).put(new float[] { spe, spe, spe, 1F }).flip();
		float msh = 128F * shi;
		FloatBuffer mem = (FloatBuffer) BufferUtils.createFloatBuffer(8).put(new float[] { 1F, 1F, 1F, 1F }).flip();
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, iamb);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, idif);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, ispe);
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, iamb);
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, idif);
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR, ispe);
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT, mamb);
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_DIFFUSE, mdif);
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, mspe);
		GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, msh);
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_EMISSION, mem); 
		GL11.glLightModeli(GL12.GL_LIGHT_MODEL_COLOR_CONTROL, GL12.GL_SEPARATE_SPECULAR_COLOR);
		
		
		bindTexture(ResourceManager.soyuz_module_dome_tex);
		ResourceManager.soyuz_module.renderPart("Dome");
		bindTexture(ResourceManager.soyuz_module_lander_tex);
		ResourceManager.soyuz_module.renderPart("Capsule");
		bindTexture(ResourceManager.soyuz_module_propulsion_tex);
		ResourceManager.soyuz_module.renderPart("Propulsion");
		bindTexture(ResourceManager.soyuz_module_solar_tex);
		ResourceManager.soyuz_module.renderPart("Solar");

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(1F, 1F, 1F);

		GL11.glPopMatrix();
		RenderHelper.enableStandardItemLighting();
	}

}