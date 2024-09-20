package com.hbm.items.weapon.sedna.factory;

import static com.hbm.items.weapon.sedna.factory.GunFactory.*;

import java.util.function.BiConsumer;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBulletBaseMK4;

import net.minecraft.client.renderer.Tessellator;

public class GunFactoryClient {

	public static void init() {
		ammo_debug.setRenderer(RENDER_STANDARD_BULLET);
	}
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_STANDARD_BULLET = (bullet, interp) -> {
		Tessellator tess = Tessellator.instance;
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(tess, 0xFFBF00, 0xFFFFFF, length, false);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_TRACER_BULLET = (bullet, interp) -> {
		Tessellator tess = Tessellator.instance;
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(tess, 0x9E082E, 0xFF8A79, length, true);
	};
	
	public static void renderBulletStandard(Tessellator tess, int dark, int light, double length, boolean fullbright) { renderBulletStandard(tess, dark, light, length, 0.03125D, 0.03125D * 0.25D, fullbright); }
	
	public static void renderBulletStandard(Tessellator tess, int dark, int light, double length, double widthF, double widthB, boolean fullbright) {
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		tess.startDrawingQuads();
		if(fullbright) tess.setBrightness(240);
		tess.setNormal(0F, 1F, 0F);
		tess.setColorOpaque_I(dark);
		tess.addVertex(length, widthB, -widthB); tess.addVertex(length, widthB, widthB);
		tess.setColorOpaque_I(light);
		tess.addVertex(0, widthF, widthF); tess.addVertex(0, widthF, -widthF);
		tess.setColorOpaque_I(dark);
		tess.addVertex(length, -widthB, -widthB); tess.addVertex(length, -widthB, widthB);
		tess.setColorOpaque_I(light);
		tess.addVertex(0, -widthF, widthF); tess.addVertex(0, -widthF, -widthF);
		tess.setColorOpaque_I(dark);
		tess.addVertex(length, -widthB, widthB); tess.addVertex(length, widthB, widthB);
		tess.setColorOpaque_I(light);
		tess.addVertex(0, widthF, widthF); tess.addVertex(0, -widthF, widthF);
		tess.setColorOpaque_I(dark);
		tess.addVertex(length, -widthB, -widthB); tess.addVertex(length, widthB, -widthB);
		tess.setColorOpaque_I(light);
		tess.addVertex(0, widthF, -widthF); tess.addVertex(0, -widthF, -widthF);
		tess.setColorOpaque_I(dark);
		tess.addVertex(length, widthB, widthB); tess.addVertex(length, widthB, -widthB);
		tess.addVertex(length, -widthB, -widthB); tess.addVertex(length, -widthB, widthB);
		tess.setColorOpaque_I(light);
		tess.addVertex(0, widthF, widthF); tess.addVertex(0, widthF, -widthF);
		tess.addVertex(0, -widthF, -widthF); tess.addVertex(0, -widthF, widthF);
		tess.draw();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
}
