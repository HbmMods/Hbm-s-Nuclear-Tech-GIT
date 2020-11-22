package com.hbm.render.entity.mob;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.ResourceLocation;

/**
 *  BOW
 */
public class RenderQuacc extends RenderChicken {
    
    /**
     *  BOW
     */
	public static final ResourceLocation ducc = new ResourceLocation(RefStrings.MODID, "textures/entity/duck.png");
    
    /**
     *  BOW
     */
	public RenderQuacc(ModelBase model, float f0) {
		super(model, f0);
	}
    
    /**
     *  BOW
     */
    protected ResourceLocation getEntityTexture(EntityChicken DUCC) {
        return ducc;
    }
    
    /**
     *  BOW
     */
    protected void preRenderCallback(EntityLivingBase DUCC, float f0) {
    	
        BossStatus.setBossStatus((IBossDisplayData) DUCC, false);
        
    	GL11.glScaled(25, 25, 25);
    }
}
