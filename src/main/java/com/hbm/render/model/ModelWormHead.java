package com.hbm.render.model;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelWormHead extends ModelBase {
	
	public static final IModelCustom head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/mobs/bot_prime_head.obj"));

	@Override
	public void render(Entity entity, float x, float y, float z, float f3, float f4, float f5) {
		super.render(entity, x, y, z, f3, f4, f5);
		
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f5 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f5 - 90, 0.0F, 0.0F, 1.0F);
		
		head.renderAll();
	}

}
