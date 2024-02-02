package com.hbm.render.entity.rocket;

import org.lwjgl.opengl.GL11;

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
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * interp, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * interp - 90.0F, 0.0F, -1.0F, 0.0F);

		int w = entity.getDataWatcher().getWatchableObjectInt(9);
		int f = entity.getDataWatcher().getWatchableObjectInt(10);
		int s = entity.getDataWatcher().getWatchableObjectInt(11);
		int t = entity.getDataWatcher().getWatchableObjectInt(12);
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
