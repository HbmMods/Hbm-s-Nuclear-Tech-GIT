package com.hbm.render.entity.projectile;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.BulletConfiguration;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelBaleflare;
import com.hbm.render.model.ModelBuckshot;
import com.hbm.render.model.ModelBullet;
import com.hbm.render.model.ModelGrenade;
import com.hbm.render.model.ModelMIRV;
import com.hbm.render.model.ModelMiniNuke;
import com.hbm.render.model.ModelRocket;
import com.hbm.render.util.RenderSparks;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBullet extends Render {

	private ModelBullet bullet;
	private ModelBuckshot buckshot;
	private ModelRocket rocket;
	private ModelGrenade grenade;
	private ModelMiniNuke nuke;
	private ModelMIRV mirv;
	private ModelBaleflare bf;

	public RenderBullet() {
		bullet = new ModelBullet();
		buckshot = new ModelBuckshot();
		rocket = new ModelRocket();
		grenade = new ModelGrenade();
		nuke = new ModelMiniNuke();
		mirv = new ModelMIRV();
		bf = new ModelBaleflare();
	}

	@Override
	public void doRender(Entity bullet, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(bullet.prevRotationYaw + (bullet.rotationYaw - bullet.prevRotationYaw) * f1 - 90.0F,
				0.0F, 1.0F, 0.0F);
		GL11.glRotatef(bullet.prevRotationPitch + (bullet.rotationPitch - bullet.prevRotationPitch) * f1 + 180,
				0.0F, 0.0F, 1.0F);
		GL11.glScalef(1.5F, 1.5F, 1.5F);
		
		GL11.glRotatef(new Random(bullet.getEntityId()).nextInt(90) - 45, 1.0F, 0.0F, 0.0F);

		int style = bullet.getDataWatcher().getWatchableObjectByte(16);
		int trail = bullet.getDataWatcher().getWatchableObjectByte(17);
		
		switch(style) {
			case BulletConfiguration.STYLE_NONE: break;
			case BulletConfiguration.STYLE_NORMAL: renderBullet(trail); break;
			case BulletConfiguration.STYLE_BOLT: renderDart(trail); break;
			case BulletConfiguration.STYLE_FLECHETTE: renderFlechette(); break;
			case BulletConfiguration.STYLE_FOLLY: renderBullet(trail); break;
			case BulletConfiguration.STYLE_PELLET: renderBuckshot(); break;
			case BulletConfiguration.STYLE_ROCKET: renderRocket(trail); break;
			case BulletConfiguration.STYLE_GRENADE: renderGrenade(trail); break;
			case BulletConfiguration.STYLE_NUKE: renderNuke(0); break;
			case BulletConfiguration.STYLE_MIRV: renderNuke(1); break;
			case BulletConfiguration.STYLE_BF: renderNuke(2); break;
			case BulletConfiguration.STYLE_ORB: renderOrb(trail); break;
			default: renderBullet(trail); break;
		}
		
		
		GL11.glPopMatrix();
	}
	
	private void renderBullet(int type) {

		if (type == 2) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/emplacer.png"));
		} else if (type == 1) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/tau.png"));
		} else if (type == 0) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/bullet.png"));
		}
		
		bullet.renderAll(0.0625F);
	}
	
	private void renderBuckshot() {

		bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/buckshot.png"));
		
		buckshot.renderAll(0.0625F);
	}
	
	private void renderRocket(int type) {
		
		switch(type) {
		case 0:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocket.png")); break;
		case 1:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketHE.png")); break;
		case 2:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketIncendiary.png")); break;
		case 3:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketShrapnel.png")); break;
		case 4:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketEMP.png")); break;
		case 5:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketGlare.png")); break;
		case 6:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketSleek.png")); break;
		case 7:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketNuclear.png")); break;
		case 9:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketPhosphorus.png")); break;
		}
		
		if(type == 8) {
			bindTexture(ResourceManager.rpc_tex);
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			GL11.glRotatef(180, 1, 0, 0);
			ResourceManager.rpc.renderAll();
			return;
		}
		
		rocket.renderAll(0.0625F);
	}
	
	private void renderGrenade(int type) {

		GL11.glScalef(0.25F, 0.25F, 0.25F);
		
		switch(type) {
		case 0:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenade.png")); break;
		case 1:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenadeHE.png")); break;
		case 2:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenadeIncendiary.png")); break;
		case 3:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenadeToxic.png")); break;
		case 4:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenadeSleek.png")); break;
		case 5:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenadeTraining.png")); break;
		}
		
		grenade.renderAll(0.0625F);
	}
	
	private void renderNuke(int type) {

        GL11.glScalef(1.5F, 1.5F, 1.5F);
		
		switch(type) {
		case 0:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/MiniNuke.png"));
			nuke.renderAll(0.0625F); break;
		case 1:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/Mirv.png"));
			mirv.renderAll(0.0625F); break;
		case 2:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/BaleFlare.png"));
			bf.renderAll(0.0625F); break;
		}

	}
	
	private void renderOrb(int type) {

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDepthMask(false);
		
		switch(type) {
		case 0:
			bindTexture(ResourceManager.tom_flame_tex);
			ResourceManager.sphere_uv_anim.renderAll();
			GL11.glScalef(0.3F, 0.3F, 0.3F);
			ResourceManager.sphere_uv_anim.renderAll();
			GL11.glScalef(1F/0.3F, 1F/0.3F, 1F/0.3F);
			for(int i = 0; i < 5; i++)
				RenderSparks.renderSpark((int) (System.currentTimeMillis() / 100 + 100 * i), 0, 0, 0, 0.5F, 2, 2, 0x8080FF, 0xFFFFFF);
			break;
		}
		
		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);

	}
	
	private void renderFlechette() {
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		
        GL11.glScalef(1F/16F, 1F/16F, 1F/16F);
        GL11.glScalef(-1, 1, 1);
        
		Tessellator tess = Tessellator.instance;
		
		//back
		GL11.glColor3f(0.15F, 0.15F, 0.15F);
		tess.startDrawingQuads();
		tess.addVertex(0, -1, -1);
		tess.addVertex(0, 1, -1);
		tess.addVertex(0, 1, 1);
		tess.addVertex(0, -1, 1);
		tess.draw();
		
		//base
		tess.startDrawingQuads();
		tess.addVertex(0, -1, -1);
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(0, 1, -1);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertex(1, -0.5, 0.5);
		tess.addVertex(0, -1, 1);
		tess.addVertex(0, 1, 1);
		tess.addVertex(1, 0.5, 0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(0, -1, -1);
		tess.addVertex(0, -1, 1);
		tess.addVertex(1, -0.5, 0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.addVertex(0, 1, -1);
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(1, 0.5, 0.5);
		tess.addVertex(0, 1, 1);
		tess.draw();

		//pin
		tess.startDrawing(4);
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(6, 0, 0);
		tess.draw();

		tess.startDrawing(4);
		tess.addVertex(6, 0, 0);
		tess.addVertex(1, -0.5, 0.5);
		tess.addVertex(1, 0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.addVertex(6, 0, 0);
		tess.addVertex(1, -0.5, -0.5);
		tess.addVertex(1, -0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.addVertex(1, 0.5, 0.5);
		tess.addVertex(1, 0.5, -0.5);
		tess.addVertex(6, 0, 0);
		tess.draw();
		

        GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPopMatrix();
	}
	
	private void renderDart(int style) {
		
		float red = 1F;
		float green = 1F;
		float blue = 1F;
		
		switch(style) {
		case BulletConfiguration.BOLT_LASER: red = 1F; green = 0F; blue = 0F; break;
		case BulletConfiguration.BOLT_NIGHTMARE: red = 1F; green = 1F; blue = 0F; break;
		case BulletConfiguration.BOLT_LACUNAE: red = 0.25F; green = 0F; blue = 0.75F; break;
		}
		
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDepthMask(false);

        GL11.glScalef(1F/4F, 1F/8F, 1F/8F);
        GL11.glScalef(-1, 1, 1);

        GL11.glScalef(2, 2, 2);
        
		Tessellator tess = Tessellator.instance;
		
		//front
		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, -1);
		tess.addVertex(3, 1, -1);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, 1);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, 1, 1);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, -1);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, -1, 1);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, 1, -1);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(3, 1, 1);
		tess.draw();
		
		//mid
		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, 0.5, -0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, 0.5);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, 0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, -0.5, 0.5);
		tess.draw();

		tess.startDrawing(4);
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(6, 0, 0);
		tess.addVertex(4, 0.5, -0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.draw();
		
		//tail
		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, 0.5, -0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, 0.5, 0.5);
		tess.addVertex(0, 0.5, -0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, -0.5, 0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, -0.5, 0.5);
		tess.addVertex(0, -0.5, -0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, 0.5);
		tess.addVertex(4, 0.5, 0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, 0.5, 0.5);
		tess.addVertex(0, -0.5, 0.5);
		tess.draw();

		tess.startDrawingQuads();
		tess.setColorRGBA_F(red, green, blue, 1);
		tess.addVertex(4, -0.5, -0.5);
		tess.addVertex(4, 0.5, -0.5);
		tess.setColorRGBA_F(red, green, blue, 0);
		tess.addVertex(0, 0.5, -0.5);
		tess.addVertex(0, -0.5, -0.5);
		tess.draw();
		
        GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/bullet.png");
	}

}
