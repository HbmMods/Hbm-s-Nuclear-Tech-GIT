package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderVortex extends Render {

	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Sphere.obj");
	private IModelCustom blastModel;
    private ResourceLocation blastTexture;

    
    public RenderVortex() {
    	blastModel = AdvancedModelLoader.loadModel(objTesterModelRL);
    	blastTexture = new ResourceLocation(RefStrings.MODID, "textures/models/BlackHole.png");

    }

	@Override
	public void doRender(Entity entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		
		if(entity instanceof EntityBlackHole) {
			GL11.glPushMatrix();
        	GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
    		GL11.glRotatef((entity.ticksExisted % 360) * 10, 1, 1, 1);
        	GL11.glDisable(GL11.GL_LIGHTING);
        	GL11.glDisable(GL11.GL_CULL_FACE);
        	
        	float size = entity.getDataWatcher().getWatchableObjectFloat(16);
        	
        	GL11.glScalef(size, size, size);
        
        	bindTexture(blastTexture);
        	blastModel.renderAll();
        	

        	GL11.glScalef(0.2F, 0.2F, 0.2F);
        	
        	//FLARE START
            Tessellator tessellator = Tessellator.instance;
    		RenderHelper.disableStandardItemLighting();
    		int j = 75;//entity.ticksExisted > 250 ? 250 : entity.ticksExisted;
            float f1 = (j + 2.0F) / 200.0F;
            float f2 = 0.0F;
            int count = 250;
            
            /*if(entity.ticksExisted < 250)
            {
            	count = entity.ticksExisted * 3;
            }*/
            
            count = j;

            if (f1 > 0.8F)
            {
                f2 = (f1 - 0.8F) / 0.2F;
            }

            Random random = new Random(432L);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDepthMask(false);
            GL11.glPushMatrix();
            
            //for (int i = 0; (float)i < (f1 + f1 * f1) / 2.0F * 60.0F; ++i)
            for(int i = 0; i < count; i++)
            {
                GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(random.nextFloat() * 360.0F + f1 * 90.0F, 0.0F, 0.0F, 1.0F);
                tessellator.startDrawing(6);
                float f3 = random.nextFloat() * 20.0F + 5.0F + f2 * 10.0F;
                float f4 = random.nextFloat() * 2.0F + 1.0F + f2 * 2.0F;
                //tessellator.setColorRGBA_I(16777215, (int)(255.0F * (1.0F - f2)));
                tessellator.setColorRGBA_I(0x3898b3, (int)(255.0F * (1.0F/* - f2*/)));
                tessellator.addVertex(0.0D, 0.0D, 0.0D);
                //tessellator.setColorRGBA_I(16711935, 0);
                tessellator.setColorRGBA_I(0x3898b3, 0);
                tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
                tessellator.addVertex(0.866D * f4, f3, -0.5F * f4);
                tessellator.addVertex(0.0D, f3, 1.0F * f4);
                tessellator.addVertex(-0.866D * f4, f3, -0.5F * f4);
                tessellator.draw();
            }

            GL11.glPopMatrix();
        	GL11.glPopMatrix();
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            RenderHelper.enableStandardItemLighting();
            //FLARE END
            
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
