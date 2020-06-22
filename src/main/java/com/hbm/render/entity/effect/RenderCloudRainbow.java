package com.hbm.render.entity.effect;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderCloudRainbow extends Render {

	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Sphere.obj");
	private IModelCustom blastModel;
    public float scale = 0;
    public float ring = 0;
    
    public RenderCloudRainbow() {
    	blastModel = AdvancedModelLoader.loadModel(objTesterModelRL);
    	scale = 0;
    }

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		render((EntityCloudFleijaRainbow)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	public void render(EntityCloudFleijaRainbow cloud, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
        
        GL11.glScalef(cloud.age, cloud.age, cloud.age);

		GL11.glColor3ub((byte)cloud.worldObj.rand.nextInt(0x100), (byte)cloud.worldObj.rand.nextInt(0x100), (byte)cloud.worldObj.rand.nextInt(0x100));

        GL11.glScalef(0.5F, 0.5F, 0.5F);
        blastModel.renderAll();
        GL11.glScalef(1/0.5F, 1/0.5F, 1/0.5F);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		
        for(float i = 0.6F; i <= 1F; i += 0.1F) {

    		GL11.glColor3ub((byte)cloud.worldObj.rand.nextInt(0x100), (byte)cloud.worldObj.rand.nextInt(0x100), (byte)cloud.worldObj.rand.nextInt(0x100));
    		
            GL11.glScalef(i, i, i);
            blastModel.renderAll();
            GL11.glScalef(1/i, 1/i, 1/i);
        }
        
		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
