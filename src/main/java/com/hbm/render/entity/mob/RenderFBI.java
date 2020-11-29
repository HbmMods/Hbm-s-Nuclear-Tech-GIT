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
}
