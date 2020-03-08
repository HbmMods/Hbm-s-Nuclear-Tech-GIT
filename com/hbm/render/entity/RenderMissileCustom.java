package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.missile.EntityMissileAntiBallistic;
import com.hbm.entity.missile.EntityMissileBunkerBuster;
import com.hbm.entity.missile.EntityMissileCluster;
import com.hbm.entity.missile.EntityMissileGeneric;
import com.hbm.entity.missile.EntityMissileIncendiary;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.MissileMultipart;
import com.hbm.render.util.MissilePart;
import com.hbm.render.util.MissilePronter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class RenderMissileCustom extends Render {

	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {

		GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(p_76986_1_.prevRotationYaw + (p_76986_1_.rotationYaw - p_76986_1_.prevRotationYaw) * p_76986_9_ - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_, 0.0F, 0.0F, 1.0F);

        int w = p_76986_1_.getDataWatcher().getWatchableObjectInt(9);
        int f = p_76986_1_.getDataWatcher().getWatchableObjectInt(10);
        int s = p_76986_1_.getDataWatcher().getWatchableObjectInt(11);
        int t = p_76986_1_.getDataWatcher().getWatchableObjectInt(12);
        
        MissileMultipart missile = new MissileMultipart();
        missile.warhead = MissilePart.getPart(Item.getItemById(w));
        missile.fuselage = MissilePart.getPart(Item.getItemById(f));
        missile.fins = MissilePart.getPart(Item.getItemById(s));
        missile.thruster = MissilePart.getPart(Item.getItemById(t));
        
        MissilePronter.prontMissile(missile, Minecraft.getMinecraft().getTextureManager());
        
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return ResourceManager.universal;
	}

}
