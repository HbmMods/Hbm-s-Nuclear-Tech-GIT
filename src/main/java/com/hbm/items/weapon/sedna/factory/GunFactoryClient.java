package com.hbm.items.weapon.sedna.factory;

import static com.hbm.items.weapon.sedna.factory.GunFactory.*;
import static com.hbm.items.weapon.sedna.factory.XFactory357.*;
import static com.hbm.items.weapon.sedna.factory.XFactory44.*;
import static com.hbm.items.weapon.sedna.factory.XFactory9mm.*;

import java.util.function.BiConsumer;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.render.item.weapon.sedna.ItemRenderAtlas;
import com.hbm.render.item.weapon.sedna.ItemRenderDebug;
import com.hbm.render.item.weapon.sedna.ItemRenderGreasegun;
import com.hbm.render.item.weapon.sedna.ItemRenderHenry;
import com.hbm.render.item.weapon.sedna.ItemRenderPepperbox;

import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.client.MinecraftForgeClient;

public class GunFactoryClient {

	public static void init() {
		//GUNS
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_debug, new ItemRenderDebug());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_pepperbox, new ItemRenderPepperbox());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_atlas, new ItemRenderAtlas());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_henry, new ItemRenderHenry());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_greasegun, new ItemRenderGreasegun());
		//PROJECTILES
		ammo_debug.setRenderer(RENDER_STANDARD_BULLET);
		ammo_debug_buckshot.setRenderer(RENDER_STANDARD_BULLET);
		m357_sp.setRenderer(RENDER_STANDARD_BULLET);
		m357_fmj.setRenderer(RENDER_STANDARD_BULLET);
		m357_jhp.setRenderer(RENDER_STANDARD_BULLET);
		m357_ap.setRenderer(RENDER_STANDARD_BULLET);
		m357_express.setRenderer(RENDER_EXPRESS_BULLET);
		m44_sp.setRenderer(RENDER_STANDARD_BULLET);
		m44_fmj.setRenderer(RENDER_STANDARD_BULLET);
		m44_jhp.setRenderer(RENDER_STANDARD_BULLET);
		m44_ap.setRenderer(RENDER_STANDARD_BULLET);
		m44_express.setRenderer(RENDER_EXPRESS_BULLET);
		p9_sp.setRenderer(RENDER_STANDARD_BULLET);
		p9_fmj.setRenderer(RENDER_STANDARD_BULLET);
		p9_jhp.setRenderer(RENDER_STANDARD_BULLET);
		p9_ap.setRenderer(RENDER_STANDARD_BULLET);
		//HUDS
		((ItemGunBaseNT) ModItems.gun_debug).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_pepperbox).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_atlas).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_henry).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_greasegun).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
	}
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_STANDARD_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0xFFBF00, 0xFFFFFF, length, false);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_EXPRESS_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0x9E082E, 0xFF8A79, length, false);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_TRACER_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0x9E082E, 0xFF8A79, length, true);
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
