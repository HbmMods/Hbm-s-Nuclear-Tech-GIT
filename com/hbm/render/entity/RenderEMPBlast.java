package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.effect.EntityNukeCloudNoShroom;
import com.hbm.entity.particle.EntityBSmokeFX;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderEMPBlast extends Render {

	private static final ResourceLocation ringModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Ring.obj");
	private IModelCustom ringModel;
    private ResourceLocation ringTexture;
    
    public RenderEMPBlast() {
    	ringModel = AdvancedModelLoader.loadModel(ringModelRL);
    	ringTexture = new ResourceLocation(RefStrings.MODID, "textures/models/EMPBlast.png");
    }

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
		this.renderRing((EntityEMPBlast)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
	
	public void renderRing(EntityEMPBlast p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glScalef(p_76986_1_.scale, 1F, p_76986_1_.scale);
        
        bindTexture(ringTexture);
        ringModel.renderAll();
        GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
