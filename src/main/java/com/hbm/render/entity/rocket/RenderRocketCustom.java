package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityRideableRocket;
import com.hbm.handler.RocketStruct;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.MissilePronter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderRocketCustom extends Render {

    @Override
    public void doRender(Entity entity, double x, double y, double z, float f, float interp) {
        if(!(entity instanceof EntityRideableRocket)) return;
        EntityRideableRocket rocketEntity = (EntityRideableRocket) entity;
        RocketStruct rocket = rocketEntity.getRocket();

        GL11.glPushMatrix();
        {

            GL11.glTranslated(x, y, z);
            GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * interp, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90.0F, 0.0F, -1.0F, 0.0F);

            MissilePronter.prontRocket(rocket, rocketEntity, Minecraft.getMinecraft().getTextureManager(), true, interp);

        }
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
		return ResourceManager.universal;
    }
    
}
