package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.mob.EntityDoner;
import com.hbm.entity.mob.EntityGhost;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelDoner;
import com.hbm.render.model.ModelFBI;
import com.hbm.render.model.ModelSiegeZombie;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderDoner extends RenderBiped {

    private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":" + "textures/entity/entitydoner.png");
	
    public RenderDoner() {
        super(new ModelDoner(), 0.5F, 1.0F);
    }
    
	@Override
	protected ResourceLocation getEntityTexture(EntityLiving entity) {
		return this.getEntityTexture((EntityGhost) entity);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return this.getEntityTexture((EntityDoner) entity);
	}

	protected ResourceLocation getEntityTexture(EntityDoner entity) {
		return texture;
	}
	
    
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
    	
    	super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
