package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.loader.HFRWavefrontObject;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;

public class RenderCoin extends Render {
	
	public static final IModelCustom coin = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/trinkets/chip.obj")).asVBO();
	public static final ResourceLocation coin_tex = new ResourceLocation(RefStrings.MODID, "textures/models/trinkets/chip_gold.png");

	@Override
	public void doRender(Entity coin, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(coin.prevRotationYaw + (coin.rotationYaw - coin.prevRotationYaw) * f1 - 90.0F, 0.0F, -1.0F, 0.0F);
		GL11.glRotated((coin.ticksExisted + f1) * 45, 0, 0, 1);
		
		double scale = 0.125D;
		GL11.glScaled(scale, scale, scale);
		
		this.bindEntityTexture(coin);
		this.coin.renderAll();
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return coin_tex;
	}
}
