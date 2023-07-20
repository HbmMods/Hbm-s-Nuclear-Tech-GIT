package com.hbm.render.entity.effect;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.effect.EntityNukeCloudSmall.Cloudlet;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.loader.HFRWavefrontObject;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class RenderSmallNukeMK4 extends Render {

	public static final IModelCustom mush = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/effect/mush.obj"));
	public static final IModelCustom shockwave = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/effect/ring_roller.obj"));
	public static final IModelCustom thinring = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/effect/ring_thin.obj"));
	private static final ResourceLocation cloudlet = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");

	/*
	 *     //      //  //////  //////  //    //
	 *    ////  ////  //  //    //    ////  //
	 *   //  //  //  //////    //    //  ////
	 *  //      //  //  //    //    //    //
	 * //      //  //  //  //////  //    //
	 */
	
	/**
	 * Look how nice and clean this is!
	 */
	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float interp) {
		
		GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        
        EntityNukeCloudSmall cloud = (EntityNukeCloudSmall)entity;
        
        mushWrapper(cloud, interp);
        cloudletWrapper(cloud, interp);
        flashWrapper(cloud, interp);
        
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
	
	/*
	 *     //      //  //////  //////  //////  //////  //////  //////  //////
	 *    //      //  //  //  //  //  //  //  //  //  //      //  //  //
	 *   //  //  //  ////    //////  //////  //////  ////    ////    //////
	 *  ////  ////  //  //  //  //  //      //      //      //  //      //
	 * //      //  //  //  //  //  //      //      //////  //  //  //////
	 */
	
	/**
	 * Wrapper for the initial flash
	 * Caps the rendering at 60 ticks and sets the alpha function
	 * @param cloud
	 * @param interp
	 */
	private void flashWrapper(EntityNukeCloudSmall cloud, float interp) {
        
        if(cloud.age < 60) {

    		GL11.glPushMatrix();
    		//Function [0, 1] that determines the scale and intensity (inverse!) of the flash
        	double scale = (cloud.ticksExisted + interp) / 60D;
        	GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.0F);
        	
        	//Euler function to slow down the scale as it progresses
        	//Makes it start fast and the fade-out is nice and smooth
        	scale = scale * Math.pow(Math.E, -scale) * 2.717391304D;
        	
        	renderFlash(scale);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
    		GL11.glPopMatrix();
        }
	}
	
	/**
	 * Wrapper for the entire mush (head + stem)
	 * Renders the entire thing twice to allow for smooth color gradients
	 * @param cloud
	 * @param interp
	 */
	private void mushWrapper(EntityNukeCloudSmall cloud, float interp) {
		
    	float size = cloud.getDataWatcher().getWatchableObjectFloat(18) * 5;
		
		GL11.glPushMatrix();
		
		GL11.glScalef(size, size, size);
		
		boolean balefire = cloud.getDataWatcher().getWatchableObjectByte(19) == 1;
		boolean antimatter = cloud.getDataWatcher().getWatchableObjectByte(19) == 2;
		
		if(balefire)
			bindTexture(ResourceManager.balefire);
		else if(antimatter)
			bindTexture(ResourceManager.antimatter);
		else
			bindTexture(ResourceManager.fireball);

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		//Float [0, 1] for the initial solid-colored layer fade-in
		float func = MathHelper.clamp_float((cloud.ticksExisted + interp) * 0.0075F, 0, 1);
		//Function that determines how high the cloud has risen. The values are the results of trial and error and i forgot what they mean
		double height = Math.max(20 - 30 * 20 / ((((cloud.ticksExisted + interp) * 0.5) - 60 * 0.1) + 1), 0);
		
		if(balefire)
			GL11.glColor4f(1.0F - (1.0F - 0.64F) * func, 1.0F, 1.0F - (1.0F - 0.5F) * func, 1F);
		else if(antimatter)
			GL11.glColor4f(1.0F * func,1.0F* func, 1.0F* func, 1F * func);
		else
			GL11.glColor4f(1.0F, 1.0F - (1.0F - 0.7F) * func, 1.0F - (1.0F - 0.48F) * func, 1F);
		
        renderMushHead(cloud.ticksExisted + interp, height);
        renderMushStem(cloud.ticksExisted + interp, height);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		//Float [0.75, 0] That determines the occupancy of the texture layer
		float texAlpha = func * 0.875F;
		
		GL11.glColor4f(1F, 1F, 1F, texAlpha);
		//Sets blend to "how you'd expect it" mode
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glEnable(GL11.GL_BLEND);
		
		//And now we fuck with texture transformations
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glLoadIdentity();
        
        GL11.glTranslated(0, -(cloud.ticksExisted + interp) * 0.035, 0);
        
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
        GL11.glPushMatrix();
        	//It's the thing that makes glow-in-the-dark work
	        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
	        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
	        renderMushHead(cloud.ticksExisted + interp, height);
	        renderMushStem(cloud.ticksExisted + interp, height);
	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glPopAttrib();
        GL11.glPopMatrix();

        //Clean this up otherwise the game becomes one-dimensional
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glLoadIdentity();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_CULL_FACE);
        
		GL11.glPopMatrix();
	}
	
	/**
	 * Adds all cloudlets to the tessellator and then draws them
	 * @param cloud
	 * @param interp
	 */
	private void cloudletWrapper(EntityNukeCloudSmall cloud, float interp) {

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		//To prevent particles cutting off before fully fading out
    	GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.01F);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
    	
    	bindTexture(cloudlet);

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		
    	for(Cloudlet cloudlet : cloud.cloudlets) {
    		float scale = cloud.age + interp - cloudlet.age;
    		tessellateCloudlet(tess, cloudlet.posX, cloudlet.posY - cloud.posY + 2, cloudlet.posZ, scale, cloud.getDataWatcher().getWatchableObjectByte(19));
    	}
    	
    	/*Random rand = new Random(cloud.getEntityId());
    	float size = cloud.getDataWatcher().getWatchableObjectFloat(18);
    	
    	for(int i = 0; i < 300 * size; i++) {
    		
    		float scale = size * 10;
    		Vec3 vec = Vec3.createVectorHelper(rand.nextGaussian() * scale, 0, rand.nextGaussian() * scale);
    		
    		tessellateCloudlet(tess, vec.xCoord, (scale - vec.lengthVector()) * rand.nextDouble() * 0.5, vec.zCoord - 10, (float)(cloud.age * cloud.cloudletLife) / cloud.maxAge, cloud.getDataWatcher().getWatchableObjectByte(19));
    	}*/
    	
		tess.draw();

		GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderHelper.enableStandardItemLighting();
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	/*
	 *     //////  //////  //    //  ////    //////  //////  //////  //////  //////
	 *    //  //  //      ////  //  //  //  //      //  //  //      //  //  //
	 *   ////    ////    //  ////  //  //  ////    ////    ////    ////    //////
	 *  //  //  //      //    //  //  //  //      //  //  //      //  //      //
	 * //  //  //////  //    //  ////    //////  //  //  //////  //  //  //////
	 */

	/**
	 * Once again the recycled ender dragon death animation
	 * It worked so well the last 14 times, let's go for 15
	 * @param intensity Double [0, 1] that determines scale and alpha
	 */
	private void renderFlash(double intensity) {
    	
    	GL11.glScalef(0.2F, 0.2F, 0.2F);
    	
    	double inverse = 1.0D - intensity;
    	
        Tessellator tessellator = Tessellator.instance;
		RenderHelper.disableStandardItemLighting();

        Random random = new Random(432L);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        
        GL11.glPushMatrix();
        
        float scale = 100;
        
        for(int i = 0; i < 300; i++) {
        	
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            
            float vert1 = (random.nextFloat() * 20.0F + 5.0F + 1 * 10.0F) * (float)(intensity * scale);
            float vert2 = (random.nextFloat() * 2.0F + 1.0F + 1 * 2.0F) * (float)(intensity * scale);
            
            tessellator.startDrawing(6);
            
            tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, (float) inverse);
            tessellator.addVertex(0.0D, 0.0D, 0.0D);
            tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 0.0F);
            tessellator.addVertex(-0.866D * vert2, vert1, -0.5F * vert2);
            tessellator.addVertex(0.866D * vert2, vert1, -0.5F * vert2);
            tessellator.addVertex(0.0D, vert1, 1.0F * vert2);
            tessellator.addVertex(-0.866D * vert2, vert1, -0.5F * vert2);
            tessellator.draw();
        }

        GL11.glPopMatrix();
        
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        RenderHelper.enableStandardItemLighting();
	}
	
	/**
	 * Render call for the mush head model
	 * Includes offset and smoothing
	 * Also scales the fireball along XZ
	 * @param progress Lifetime + interpolation number
	 * @param height The current animation offset
	 */
	private void renderMushHead(float progress, double height) {
		
		GL11.glPushMatrix();
		
		double expansion = 100;
		double width = Math.min(progress, expansion) / expansion * 0.3 + 0.7;
		
		GL11.glTranslated(0, -26 + height, 0);
		GL11.glScaled(width, 1, width);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
		mush.renderPart("Ball");
        GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
	
	/**
	 * Render call for the mush stem model
	 * Includes offset and smoothing
	 * @param progress Lifetime + interpolation number
	 * @param height The current animation offset
	 */
	private void renderMushStem(float progress, double height) {
		
		GL11.glPushMatrix();

		GL11.glTranslated(0, -26 + height, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
		mush.renderPart("Stem");
        GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}
	
	/**
	 * Adds one cloudlet (one face) to the tessellator.
	 * Rotation is done using ActiveRenderInfo, which I'd assume runs on magic
	 * But hey, if it works for particles, why not here too?
	 * @param tess
	 * @param posX
	 * @param posY
	 * @param posZ
	 * @param age The mush' age when the cloudlet was created
	 * @param type DataWatcher byte #19 which differentiates between different mush types
	 */
	private void tessellateCloudlet(Tessellator tess, double posX, double posY, double posZ, float age, int type) {
		
		float alpha = 1F - Math.max(age / (float)(EntityNukeCloudSmall.cloudletLife), 0F);
		float alphaorig = alpha;
		
		float scale = 5F * (alpha * 0.5F + 0.5F);
		
		if(age < 3)
			alpha = age * 0.333F;
		
        float f1 = ActiveRenderInfo.rotationX;
        float f2 = ActiveRenderInfo.rotationZ;
        float f3 = ActiveRenderInfo.rotationYZ;
        float f4 = ActiveRenderInfo.rotationXY;
        float f5 = ActiveRenderInfo.rotationXZ;
        
        Random rand = new Random((long) ((posX * 5 + posY * 25 + posZ * 125) * 1000D));
        
        float brightness = rand.nextFloat() * 0.25F + 0.25F;

        if(type == 1) {
        	tess.setColorRGBA_F(0.25F * alphaorig, alphaorig - brightness * 0.5F, 0.25F * alphaorig, alpha);
        } else if(type == 2) {
        	tess.setColorRGBA_F(alphaorig - brightness * 0.5F, 0.25F *alphaorig, alphaorig - brightness * 0.5F, alpha);
        } else {
    		
        	tess.setColorRGBA_F(brightness, brightness, brightness, alpha);
        }
        
		tess.addVertexWithUV((double)(posX - f1 * scale - f3 * scale), (double)(posY - f5 * scale), (double)(posZ - f2 * scale - f4 * scale), 1, 1);
		tess.addVertexWithUV((double)(posX - f1 * scale + f3 * scale), (double)(posY + f5 * scale), (double)(posZ - f2 * scale + f4 * scale), 1, 0);
		tess.addVertexWithUV((double)(posX + f1 * scale + f3 * scale), (double)(posY + f5 * scale), (double)(posZ + f2 * scale + f4 * scale), 0, 0);
		tess.addVertexWithUV((double)(posX + f1 * scale - f3 * scale), (double)(posY - f5 * scale), (double)(posZ + f2 * scale - f4 * scale), 0, 1);
		
	}
}
