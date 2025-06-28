package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBulletBaseMK4;
import com.hbm.entity.projectile.EntityBulletBeamBase;
import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.hud.HUDComponentAmmoCounter;
import com.hbm.items.weapon.sedna.hud.HUDComponentDurabilityBar;
import com.hbm.items.weapon.sedna.impl.ItemGunChargeThrower;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.weapon.sedna.ItemRenderFatMan;
import com.hbm.render.tileentity.RenderArcFurnace;
import com.hbm.render.util.BeamPronter;
import com.hbm.render.util.BeamPronter.EnumBeamType;
import com.hbm.render.util.BeamPronter.EnumWaveType;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class LegoClient {

	public static HUDComponentDurabilityBar HUD_COMPONENT_DURABILITY = new HUDComponentDurabilityBar();
	public static HUDComponentDurabilityBar HUD_COMPONENT_DURABILITY_MIRROR = new HUDComponentDurabilityBar(true);
	public static HUDComponentAmmoCounter HUD_COMPONENT_AMMO = new HUDComponentAmmoCounter(0);
	public static HUDComponentAmmoCounter HUD_COMPONENT_AMMO_MIRROR = new HUDComponentAmmoCounter(0).mirror();
	public static HUDComponentAmmoCounter HUD_COMPONENT_AMMO_NOCOUNTER = new HUDComponentAmmoCounter(0).noCounter();
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_STANDARD_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0xFFBF00, 0xFFFFFF, length, false);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_FLECHETTE_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0x8C8C8C, 0xCACACA, length, false);
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
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_HE_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0xD8CA00, 0xFFF19D, length, true);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_SM_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0x42A8DD, 0xFFFFFF, length, true);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_BLACK_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0x000000, 0x7F006E, length, true);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_TRACER_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0x9E082E, 0xFF8A79, length, true);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_LEGENDARY_BULLET = (bullet, interp) -> {
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length <= 0) return;
		renderBulletStandard(Tessellator.instance, 0x7F006E, 0xFF7FED, length, true);
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

	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_FLARE = (bullet, interp) -> { renderFlare(bullet, interp, 1F, 0.5F, 0.5F); };
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_FLARE_SUPPLY = (bullet, interp) -> { renderFlare(bullet, interp, 0.5F, 0.5F, 1F); };
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_FLARE_WEAPON = (bullet, interp) -> { renderFlare(bullet, interp, 0.5F, 1F, 0.5F); };

	private static final ResourceLocation flare = new ResourceLocation(RefStrings.MODID + ":textures/particle/flare.png");
	public static void renderFlare(Entity bullet, float interp, float r, float g, float b) {
		
		if(bullet.ticksExisted < 2) return;
		RenderArcFurnace.fullbright(true);
		
		double scale = Math.min(5, (bullet.ticksExisted + interp - 2) * 0.5) * (0.8 + bullet.worldObj.rand.nextDouble() * 0.4);
		renderFlareSprite(bullet, interp, r, g, b, scale, 0.5F, 0.75F);
		
		RenderArcFurnace.fullbright(false);
	}
	public static void renderFlareSprite(Entity bullet, float interp, float r, float g, float b, double scale, float outerAlpha, float innerAlpha) {
		
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

		tess.setColorRGBA_F(r, g, b, outerAlpha);
		tess.addVertexWithUV((double) (posX - f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ - f2 * scale - f4 * scale), 1, 1);
		tess.addVertexWithUV((double) (posX - f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ - f2 * scale + f4 * scale), 1, 0);
		tess.addVertexWithUV((double) (posX + f1 * scale + f3 * scale), (double) (posY + f5 * scale), (double) (posZ + f2 * scale + f4 * scale), 0, 0);
		tess.addVertexWithUV((double) (posX + f1 * scale - f3 * scale), (double) (posY - f5 * scale), (double) (posZ + f2 * scale - f4 * scale), 0, 1);

		scale *= 0.5D;
		
		tess.setColorRGBA_F(1F, 1F, 1F, innerAlpha);
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
	}
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_GRENADE = (bullet, interp) -> {
		GL11.glScalef(0.25F, 0.25F, 0.25F);
		GL11.glRotated(90, 0, 0, 1);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.grenade_tex);
		ResourceManager.projectiles.renderPart("Grenade");
		GL11.glShadeModel(GL11.GL_FLAT);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_BIG_NUKE = (bullet, interp) -> {
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		GL11.glRotated(90, 0, 0, 1);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.rocket_mirv_tex);
		ResourceManager.projectiles.renderPart("MissileMIRV");
		GL11.glShadeModel(GL11.GL_FLAT);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_RPZB = (bullet, interp) -> {

		GL11.glPushMatrix();
		GL11.glScalef(0.125F, 0.125F, 0.125F);
		GL11.glRotated(90, 0, -1, 0);
		GL11.glTranslatef(0, 0, 3.5F);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.panzerschreck_tex);
		ResourceManager.panzerschreck.renderPart("Rocket");
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
		
		GL11.glTranslatef(0.375F, 0, 0);
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length > 0) renderBulletStandard(Tessellator.instance, 0x808080, 0xFFF2A7, length * 2, true);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_QD = (bullet, interp) -> {

		GL11.glPushMatrix();
		GL11.glRotated(90, 0, 0, 1);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.rocket_tex);
		ResourceManager.projectiles.renderPart("Rocket");
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
		
		GL11.glTranslatef(0.375F, 0, 0);
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length > 0) renderBulletStandard(Tessellator.instance, 0x808080, 0xFFF2A7, length * 2, true);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_ML = (bullet, interp) -> {

		GL11.glPushMatrix();
		GL11.glScalef(0.25F, 0.25F, 0.25F);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glTranslatef(0, -1, -4.5F);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.missile_launcher_tex);
		ResourceManager.missile_launcher.renderPart("Missile");
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
		
		GL11.glTranslatef(0.375F, 0, 0);
		double length = bullet.prevVelocity + (bullet.velocity - bullet.prevVelocity) * interp;
		if(length > 0) renderBulletStandard(Tessellator.instance, 0x808080, 0xFFF2A7, length * 2, true);
	};
	
	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_LIGHTNING = (bullet, interp) -> {

		RenderArcFurnace.fullbright(true);
		GL11.glPushMatrix();
		GL11.glRotatef(180 - bullet.rotationYaw, 0, 1F, 0);
		GL11.glRotatef(-bullet.rotationPitch - 90, 1F, 0, 0);
		Vec3 delta = Vec3.createVectorHelper(0, bullet.beamLength, 0);
		double age = MathHelper.clamp_double(1D - ((double) bullet.ticksExisted - 2 + interp) / (double) bullet.getBulletConfig().expires, 0, 1);
		GL11.glScaled(age / 2 + 0.5, 1, age / 2 + 0.5);
		double scale = 0.075D;
		int colorInner = ((int)(0x20 * age) << 16) | ((int)(0x20 * age) << 8) | (int) (0x40 * age);
		int colorOuter = ((int)(0x40 * age) << 16) | ((int)(0x40 * age) << 8) | (int) (0x80 * age);
		BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, colorInner, colorInner, bullet.ticksExisted / 3, (int)(bullet.beamLength / 2 + 1), (float)scale * 1F, 4, 0.25F);
		BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, colorOuter, colorOuter, bullet.ticksExisted, (int)(bullet.beamLength / 2 + 1), (float)scale * 7F, 2, 0.0625F);
		BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, colorOuter, colorOuter, bullet.ticksExisted / 2, (int)(bullet.beamLength / 2 + 1), (float)scale * 7F, 2, 0.0625F);
		GL11.glPopMatrix();
		RenderArcFurnace.fullbright(false);
	};
	
	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_LIGHTNING_SUB = (bullet, interp) -> {

		RenderArcFurnace.fullbright(true);
		GL11.glPushMatrix();
		GL11.glRotatef(180 - bullet.rotationYaw, 0, 1F, 0);
		GL11.glRotatef(-bullet.rotationPitch - 90, 1F, 0, 0);
		Vec3 delta = Vec3.createVectorHelper(0, bullet.beamLength, 0);
		double age = MathHelper.clamp_double(1D - ((double) bullet.ticksExisted - 2 + interp) / (double) bullet.getBulletConfig().expires, 0, 1);
		GL11.glScaled(age / 2 + 0.15, 1, age / 2 + 0.15);
		double scale = 0.075D;
		int colorInner = ((int)(0x20 * age) << 16) | ((int)(0x20 * age) << 8) | (int) (0x40 * age);
		int colorOuter = ((int)(0x40 * age) << 16) | ((int)(0x40 * age) << 8) | (int) (0x80 * age);
		BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, colorInner, colorInner, bullet.ticksExisted / 3, (int)(bullet.beamLength / 2 + 1), (float)scale * 1F, 4, 0.25F);
		BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, colorOuter, colorOuter, bullet.ticksExisted, (int)(bullet.beamLength / 2 + 1), (float)scale * 7F, 2, 0.0625F);
		BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, colorOuter, colorOuter, bullet.ticksExisted / 2, (int)(bullet.beamLength / 2 + 1), (float)scale * 7F, 2, 0.0625F);
		GL11.glPopMatrix();
		RenderArcFurnace.fullbright(false);
	};
	
	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_TAU = (bullet, interp) -> {

		RenderArcFurnace.fullbright(true);
		double age = MathHelper.clamp_double(1D - ((double) bullet.ticksExisted - 2 + interp) / (double) bullet.getBulletConfig().expires, 0, 1);
		
		GL11.glPushMatrix();
		GL11.glRotatef(180 - bullet.rotationYaw, 0, 1F, 0);
		GL11.glRotatef(-bullet.rotationPitch - 90, 1F, 0, 0);
		
		GL11.glPushMatrix();
		Vec3 delta = Vec3.createVectorHelper(0, bullet.beamLength, 0);
		GL11.glScaled(age / 2 + 0.5, 1, age / 2 + 0.5);
		double scale = 0.075D;
		int colorInner = ((int)(0x30 * age) << 16) | ((int)(0x25 * age) << 8) | (int) (0x10 * age);
		BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, colorInner, colorInner, (bullet.ticksExisted + bullet.getEntityId()) / 2, (int)(bullet.beamLength / 2 + 1), (float)scale * 4F, 2, 0.0625F);
		GL11.glPopMatrix();

		GL11.glScaled(age * 2, 1, age * 2);
		GL11.glTranslated(0, bullet.beamLength, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		renderBulletStandard(Tessellator.instance, 0xFFBF00, 0xFFFFFF, bullet.beamLength, true);
		
		GL11.glPopMatrix();
		RenderArcFurnace.fullbright(false);
	};
	
	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_TAU_CHARGE = (bullet, interp) -> {

		RenderArcFurnace.fullbright(true);
		double age = MathHelper.clamp_double(1D - ((double) bullet.ticksExisted - 2 + interp) / (double) bullet.getBulletConfig().expires, 0, 1);
		
		GL11.glPushMatrix();
		GL11.glRotatef(180 - bullet.rotationYaw, 0, 1F, 0);
		GL11.glRotatef(-bullet.rotationPitch - 90, 1F, 0, 0);
		
		GL11.glPushMatrix();
		Vec3 delta = Vec3.createVectorHelper(0, bullet.beamLength, 0);
		GL11.glScaled(age / 2 + 0.5, 1, age / 2 + 0.5);
		double scale = 0.075D;
		int colorInner = ((int)(0x60 * age) << 16) | ((int)(0x50 * age) << 8) | (int) (0x30 * age);
		BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, colorInner, colorInner, (bullet.ticksExisted + bullet.getEntityId()) / 2, (int)(bullet.beamLength / 2 + 1), (float)scale * 4F, 2, 0.0625F);
		GL11.glPopMatrix();

		GL11.glScaled(age * 2, 1, age * 2);
		GL11.glTranslated(0, bullet.beamLength, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		renderBulletStandard(Tessellator.instance, 0xFFF0A0, 0xFFFFFF, bullet.beamLength, true);
		
		GL11.glPopMatrix();
		RenderArcFurnace.fullbright(false);
	};
	
	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_CRACKLE = (bullet, interp) -> {

		RenderArcFurnace.fullbright(true);
		double age = MathHelper.clamp_double(1D - ((double) bullet.ticksExisted - 2 + interp) / (double) bullet.getBulletConfig().expires, 0, 1);
		
		GL11.glPushMatrix();
		GL11.glRotatef(180 - bullet.rotationYaw, 0, 1F, 0);
		GL11.glRotatef(-bullet.rotationPitch - 90, 1F, 0, 0);

		double scale = 5D;
		GL11.glScaled(age * scale, 1, age * scale);
		GL11.glTranslated(0, bullet.beamLength, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		renderBulletStandard(Tessellator.instance, 0xE3D692, 0xffffff, bullet.beamLength, true);
		
		GL11.glPopMatrix();
		RenderArcFurnace.fullbright(false);
	};

	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_LASER_RED = (bullet, interp) -> {
		renderStandardLaser(bullet, interp, 0x80, 0x15, 0x15);
	};
	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_LASER_EMERALD = (bullet, interp) -> {
		renderStandardLaser(bullet, interp, 0x15, 0x80, 0x15);
	};
	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_LASER_CYAN = (bullet, interp) -> {
		renderStandardLaser(bullet, interp, 0x15, 0x15, 0x80);
	};
	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_LASER_PURPLE = (bullet, interp) -> {
		renderStandardLaser(bullet, interp, 0x60, 0x15, 0x80);
	};
	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_LASER_WHITE = (bullet, interp) -> {
		renderStandardLaser(bullet, interp, 0x15, 0x15, 0x15);
	};
	
	public static void renderStandardLaser(EntityBulletBeamBase bullet, float interp, int r, int g, int b) {

		RenderArcFurnace.fullbright(true);
		GL11.glPushMatrix();
		GL11.glRotatef(180 - bullet.rotationYaw, 0, 1F, 0);
		GL11.glRotatef(-bullet.rotationPitch - 90, 1F, 0, 0);
		Vec3 delta = Vec3.createVectorHelper(0, bullet.beamLength, 0);
		double age = MathHelper.clamp_double(1D - ((double) bullet.ticksExisted - 2 + interp) / (double) bullet.getBulletConfig().expires, 0, 1);
		GL11.glScaled(age / 2 + 0.5, 1, age / 2 + 0.5);
		int colorInner = ((int)(r * age) << 16) | ((int)(g * age) << 8) | (int) (b * age);
		BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, colorInner, colorInner, bullet.ticksExisted / 3, (int)(bullet.beamLength / 2 + 1), 0F, 8, 0.0625F);
		GL11.glPopMatrix();
		RenderArcFurnace.fullbright(false);
	}
	
	public static BiConsumer<EntityBulletBeamBase, Float> RENDER_FOLLY = (bullet, interp) -> {

		double age = MathHelper.clamp_double(1D - ((double) bullet.ticksExisted - 2 + interp) / (double) bullet.getBulletConfig().expires, 0, 1);
		RenderArcFurnace.fullbright(true);
		
		GL11.glPushMatrix();
		renderFlareSprite(bullet, interp, 1F, 1F, 1F, (1 - age) * 7.5 + 1.5, 0.5F * (float) age, 0.75F * (float) age);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		GL11.glRotatef(180 - bullet.rotationYaw, 0, 1F, 0);
		GL11.glRotatef(-bullet.rotationPitch - 90, 1F, 0, 0);
		Vec3 delta = Vec3.createVectorHelper(0, bullet.beamLength, 0);
		GL11.glScaled((1 - age) * 25 + 2.5, 1, (1 - age) * 25 + 2.5);
		int colorInner = ((int)(0x20 * age) << 16) | ((int)(0x20 * age) << 8) | (int) (0x20 * age);
		BeamPronter.prontBeam(delta, EnumWaveType.RANDOM, EnumBeamType.SOLID, colorInner, colorInner, bullet.ticksExisted / 3, (int)(bullet.beamLength / 2 + 1), 0F, 8, 0.0625F);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
		
		RenderArcFurnace.fullbright(false);
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_NUKE = (bullet, interp) -> {

		GL11.glPushMatrix();
		GL11.glScalef(0.125F, 0.125F, 0.125F);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glTranslatef(0, -1, 1F);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.fatman_mininuke_tex);
		ResourceManager.fatman.renderPart("MiniNuke");
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_NUKE_BALEFIRE = (bullet, interp) -> {

		GL11.glPushMatrix();
		GL11.glScalef(0.125F, 0.125F, 0.125F);
		GL11.glRotated(-90, 0, 1, 0);
		GL11.glTranslatef(0, -1, 1F);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		ItemRenderFatMan.renderBalefire(interp);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_HIVE = (bullet, interp) -> {

		GL11.glPushMatrix();
		GL11.glScalef(0.125F, 0.125F, 0.125F);
		GL11.glRotated(90, 0, -1, 0);
		GL11.glTranslatef(0, 0, 3.5F);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.panzerschreck_tex);
		ResourceManager.panzerschreck.renderPart("Rocket");
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_CT_HOOK = (bullet, interp) -> {

		GL11.glPushMatrix();
		
		GL11.glRotatef(bullet.prevRotationYaw + (bullet.rotationYaw - bullet.prevRotationYaw) * interp - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(bullet.prevRotationPitch + (bullet.rotationPitch - bullet.prevRotationPitch) * interp + 180, 0.0F, 0.0F, 1.0F);
		
		GL11.glScalef(0.125F, 0.125F, 0.125F);
		GL11.glRotated(90, 0, -1, 0);
		GL11.glRotated(180, 0, 0, 1);
		GL11.glTranslatef(0, 0, -6F);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_hook_tex);
		ResourceManager.charge_thrower.renderPart("Hook");
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
		
		if(bullet.getThrower() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) bullet.getThrower();
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == ModItems.gun_charge_thrower && ItemGunChargeThrower.getLastHook(player.getHeldItem()) == bullet.getEntityId()) {
				renderWire(bullet, interp);
			}
		}
	};
	
	public static void renderWire(EntityBulletBaseMK4 bullet, float interp) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.wire_greyscale_tex);

		double bx = bullet.prevPosX + (bullet.posX - bullet.prevPosX) * interp;
		double by = bullet.prevPosY + (bullet.posY - bullet.prevPosY) * interp;
		double bz = bullet.prevPosZ + (bullet.posZ - bullet.prevPosZ) * interp;
		
		Entity thrower = bullet.getThrower();
		double x = thrower.prevPosX + (thrower.posX - thrower.prevPosX) * interp;
		double y = thrower.prevPosY + (thrower.posY - thrower.prevPosY) * interp;
		double z = thrower.prevPosZ + (thrower.posZ - thrower.prevPosZ) * interp;
		double eyaw = thrower.prevRotationYaw + (thrower.rotationYaw - thrower.prevRotationYaw) * interp;
		double epitch = thrower.prevRotationPitch + (thrower.rotationPitch - thrower.prevRotationPitch) * interp;
		
		Vec3 offset = Vec3.createVectorHelper(0.125D, 0.25, -0.75);
		offset.rotateAroundX((float) -epitch / 180F * (float) Math.PI);
		offset.rotateAroundY((float) -eyaw / 180F * (float) Math.PI);
		
		Vec3 target = Vec3.createVectorHelper(x - offset.xCoord, y + thrower.getEyeHeight() - offset.yCoord, z - offset.zCoord);

		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		double deltaX = target.xCoord - bx;
		double deltaY = target.yCoord - by;
		double deltaZ = target.zCoord - bz;
		Vec3 delta = Vec3.createVectorHelper(deltaX, deltaY, deltaZ);
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		
		int count = 10;
		double hang = Math.min(delta.lengthVector() / 15D, 0.5D);

		double girth = 0.03125D;
		double hyp = Math.sqrt(delta.xCoord * delta.xCoord + delta.zCoord * delta.zCoord);
		double yaw = Math.atan2(delta.xCoord, delta.zCoord);
		double pitch = Math.atan2(delta.yCoord, hyp);
		double rotator = Math.PI * 0.5D;
		double newPitch = pitch + rotator;
		double newYaw = yaw + rotator;
		double iZ = Math.cos(yaw) * Math.cos(newPitch) * girth;
		double iX = Math.sin(yaw) * Math.cos(newPitch) * girth;
		double iY = Math.sin(newPitch) * girth;
		double jZ = Math.cos(newYaw) * girth;
		double jX = Math.sin(newYaw) * girth;
		
		for(float j = 0; j < count; j++) {

			float k = j + 1;

			double sagJ = Math.sin(j / count * Math.PI) * hang;
			double sagK = Math.sin(k / count * Math.PI) * hang;
			double sagMean = (sagJ + sagK) / 2D;

			double ja = j + 0.5D;
			double ix = bx + deltaX / (double)(count) * ja;
			double iy = by + deltaY / (double)(count) * ja - sagMean;
			double iz = bz + deltaZ / (double)(count) * ja;

			int brightness = bullet.worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(ix), MathHelper.floor_double(iy), MathHelper.floor_double(iz), 0);
			tess.setBrightness(brightness);

			tess.setColorOpaque_I(0x606060);

			drawLineSegment(tess,
					(deltaX * j / count),
					(deltaY * j / count) - sagJ,
					(deltaZ * j / count),
					(deltaX * k / count),
					(deltaY * k / count) - sagK,
					(deltaZ * k / count),
					iX, iY, iZ, jX, jZ);
		}
		
		tess.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public static void drawLineSegment(Tessellator tessellator, double x, double y, double z, double a, double b, double c, double iX, double iY, double iZ, double jX, double jZ) {

		double deltaX = a - x;
		double deltaY = b - y;
		double deltaZ = c - z;
		double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
		int wrap = (int) Math.ceil(length * 8);

		if(deltaX + deltaZ < 0) {
			wrap *= -1;
			jZ *= -1;
			jX *= -1;
		}

		tessellator.addVertexWithUV(x + iX, y + iY, z + iZ, 0, 0);
		tessellator.addVertexWithUV(x - iX, y - iY, z - iZ, 0, 1);
		tessellator.addVertexWithUV(a - iX, b - iY, c - iZ, wrap, 1);
		tessellator.addVertexWithUV(a + iX, b + iY, c + iZ, wrap, 0);
		tessellator.addVertexWithUV(x + jX, y, z + jZ, 0, 0);
		tessellator.addVertexWithUV(x - jX, y, z - jZ, 0, 1);
		tessellator.addVertexWithUV(a - jX, b, c - jZ, wrap, 1);
		tessellator.addVertexWithUV(a + jX, b, c + jZ, wrap, 0);
	}

	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_CT_MORTAR = (bullet, interp) -> {

		GL11.glPushMatrix();
		GL11.glScalef(0.125F, 0.125F, 0.125F);
		GL11.glRotated(90, 0, -1, 0);
		GL11.glRotated(180, 0, 0, 1);
		GL11.glTranslatef(0, 0, -6F);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_mortar_tex);
		ResourceManager.charge_thrower.renderPart("Mortar");
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	};
	
	public static BiConsumer<EntityBulletBaseMK4, Float> RENDER_CT_MORTAR_CHARGE = (bullet, interp) -> {

		GL11.glPushMatrix();
		GL11.glScalef(0.125F, 0.125F, 0.125F);
		GL11.glRotated(90, 0, -1, 0);
		GL11.glRotated(180, 0, 0, 1);
		GL11.glTranslatef(0, 0, -6F);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.charge_thrower_mortar_tex);
		ResourceManager.charge_thrower.renderPart("Mortar");
		ResourceManager.charge_thrower.renderPart("Oomph");
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	};
}
