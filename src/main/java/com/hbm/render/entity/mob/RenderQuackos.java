package com.hbm.render.entity.mob;

import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;

public class RenderQuackos extends RenderChicken {

	public static final ResourceLocation ducc = new ResourceLocation(RefStrings.MODID, "textures/entity/duck.png");
	
	public RenderQuackos(ModelBase p_i1252_1_, float p_i1252_2_) {
		super(p_i1252_1_, p_i1252_2_);
	}
	
    protected ResourceLocation getEntityTexture(EntityChicken p_110775_1_) {
        return ducc;
    }
}
