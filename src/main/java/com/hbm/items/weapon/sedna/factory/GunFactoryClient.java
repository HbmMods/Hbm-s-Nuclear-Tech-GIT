package com.hbm.items.weapon.sedna.factory;

import static com.hbm.items.weapon.sedna.factory.GunFactory.*;
import static com.hbm.items.weapon.sedna.factory.XFactory12ga.*;
import static com.hbm.items.weapon.sedna.factory.XFactory357.*;
import static com.hbm.items.weapon.sedna.factory.XFactory40mm.*;
import static com.hbm.items.weapon.sedna.factory.XFactory44.*;
import static com.hbm.items.weapon.sedna.factory.XFactory762mm.*;
import static com.hbm.items.weapon.sedna.factory.XFactory9mm.*;

import java.util.function.BiConsumer;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.weapon.sedna.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public class GunFactoryClient {

	public static void init() {
		//GUNS
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_debug, new ItemRenderDebug());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_pepperbox, new ItemRenderPepperbox());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_atlas, new ItemRenderAtlas());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_henry, new ItemRenderHenry());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_greasegun, new ItemRenderGreasegun());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_maresleg, new ItemRenderMaresleg());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_flaregun, new ItemRenderFlaregun());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_heavy_revolver, new ItemRenderHeavyRevolver());
		MinecraftForgeClient.registerItemRenderer(ModItems.gun_carbine, new ItemRenderCarbine());
		//PROJECTILES
		ammo_debug.setRenderer(RENDER_STANDARD_BULLET);
		ammo_debug_buckshot.setRenderer(RENDER_STANDARD_BULLET);
		m357_sp.setRenderer(RENDER_STANDARD_BULLET);
		m357_fmj.setRenderer(RENDER_STANDARD_BULLET);
		m357_jhp.setRenderer(RENDER_STANDARD_BULLET);
		m357_ap.setRenderer(RENDER_AP_BULLET);
		m357_express.setRenderer(RENDER_EXPRESS_BULLET);
		m44_sp.setRenderer(RENDER_STANDARD_BULLET);
		m44_fmj.setRenderer(RENDER_STANDARD_BULLET);
		m44_jhp.setRenderer(RENDER_STANDARD_BULLET);
		m44_ap.setRenderer(RENDER_AP_BULLET);
		m44_express.setRenderer(RENDER_EXPRESS_BULLET);
		p9_sp.setRenderer(RENDER_STANDARD_BULLET);
		p9_fmj.setRenderer(RENDER_STANDARD_BULLET);
		p9_jhp.setRenderer(RENDER_STANDARD_BULLET);
		p9_ap.setRenderer(RENDER_AP_BULLET);
		g12_bp.setRenderer(RENDER_STANDARD_BULLET);
		g12_bp_magnum.setRenderer(RENDER_STANDARD_BULLET);
		g12_bp_slug.setRenderer(RENDER_STANDARD_BULLET);
		g12.setRenderer(RENDER_STANDARD_BULLET);
		g40_flare.setRenderer(RENDER_FLARE);
		r762_sp.setRenderer(RENDER_STANDARD_BULLET);
		r762_fmj.setRenderer(RENDER_STANDARD_BULLET);
		r762_jhp.setRenderer(RENDER_STANDARD_BULLET);
		r762_ap.setRenderer(RENDER_AP_BULLET);
		r762_du.setRenderer(RENDER_DU_BULLET);
		//HUDS
		((ItemGunBaseNT) ModItems.gun_debug).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_pepperbox).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_atlas).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_henry).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_greasegun).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_maresleg).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_flaregun).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_heavy_revolver).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
		((ItemGunBaseNT) ModItems.gun_carbine).config_DNA.hud(LegoClient.HUD_COMPONENT_DURABILITY, LegoClient.HUD_COMPONENT_AMMO);
	}
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_STANDARD_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0xFFBF00, 0xFFFFFF, length, false);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_AP_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0xFF6A00, 0xFFE28D, length, false);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_EXPRESS_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0x9E082E, 0xFF8A79, length, false);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_DU_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0x5CCD41, 0xE9FF8D, length, false);
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

	private static final ResourceLocation flare = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_FLARE = (bullet, interp) -> {
		
		if(bullet.ticksExisted < 2) return;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		RenderHelper.disableStandardItemLighting();

		Minecraft.getMinecraft().getTextureManager().bindTexture(flare);

		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();

		float f1 = ActiveRenderInfo.rotationX;
		float f2 = ActiveRenderInfo.rotationZ;
		float f3 = ActiveRenderInfo.rotationYZ;
		float f4 = ActiveRenderInfo.rotationXY;
		float f5 = ActiveRenderInfo.rotationXZ;

		double posX = 0;
		double posY = 0;
		double posZ = 0;
		double scale = Math.min(5, (bullet.ticksExisted + interp - 2) * 0.5) * (0.8 + bullet.worldObj.rand.nextDouble() * 0.4);

		tess.setColorRGBA_F(1F, 0.5F, 0.5F, 0.5F);
		tess.addVertexWithUV((double) (posX - f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ - f2 * scale - f4 * scale), 1, 1);
		tess.addVertexWithUV((double) (posX - f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ - f2 * scale + f4 * scale), 1, 0);
		tess.addVertexWithUV((double) (posX + f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ + f2 * scale + f4 * scale), 0, 0);
		tess.addVertexWithUV((double) (posX + f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ + f2 * scale - f4 * scale), 0, 1);

		scale *= 0.5D;
		
		tess.setColorRGBA_F(1F, 1F, 1F, 0.75F);
		tess.addVertexWithUV((double) (posX - f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ - f2 * scale - f4 * scale), 1, 1);
		tess.addVertexWithUV((double) (posX - f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ - f2 * scale + f4 * scale), 1, 0);
		tess.addVertexWithUV((double) (posX + f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ + f2 * scale + f4 * scale), 0, 0);
		tess.addVertexWithUV((double) (posX + f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ + f2 * scale - f4 * scale), 0, 1);

		tess.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		RenderHelper.enableStandardItemLighting();
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	};
}
