package com.hbm.render.entity.mob;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelFBI;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderFBI extends RenderBiped {
	
    private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":" + "textures/entity/fbi.png");
	
    public RenderFBI() {
        super(new ModelFBI(), 0.5F, 1.0F);
    }
    
    protected ResourceLocation getEntityTexture(EntityLiving entity) {
        return texture;
    }
    
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
    	
    	modelBipedMain.aimedBow = field_82423_g.aimedBow = field_82425_h.aimedBow = true;
    	super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    	modelBipedMain.aimedBow = field_82423_g.aimedBow = field_82425_h.aimedBow = true;
    }
}
