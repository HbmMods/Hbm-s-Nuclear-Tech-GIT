package com.hbm.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.hbm.entity.EntityRocket;
import com.hbm.lib.RefStrings;

@SideOnly(Side.CLIENT)
public class RenderRocket extends Render {
	

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelRocket.png");
	ModelRocket md;

	public RenderRocket()
	{
		this.md = new ModelRocket();
	}
	  
	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
	}
	
	public void render(Entity rocket, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
	    GL11.glEnable(2896);
	    GL11.glPushMatrix();
	    GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(rocket.prevRotationYaw + (rocket.rotationYaw - rocket.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(rocket.prevRotationPitch + (rocket.rotationPitch - rocket.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);
	    Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	    this.md.render(rocket, p_76986_9_, p_76986_9_, p_76986_9_, p_76986_9_, p_76986_9_, p_76986_9_);
	    GL11.glPopMatrix();
	}
	
	protected ResourceLocation getEntityTexture(EntityRocket p_110775_1_) {
		return texture;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return this.getEntityTexture((EntityRocket) p_110775_1_);
	}
	
}