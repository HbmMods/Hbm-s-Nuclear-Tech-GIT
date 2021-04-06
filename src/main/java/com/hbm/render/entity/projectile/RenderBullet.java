package com.hbm.render.entity.projectile;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.BulletConfiguration;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelBaleflare;
import com.hbm.render.model.ModelBullet;
import com.hbm.render.util.RenderSparks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RenderBullet extends Render {

	private ModelBullet bullet;
	private ModelBaleflare bf;

	public RenderBullet() {
		bullet = new ModelBullet();
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

		int style = bullet.getDataWatcher().getWatchableObjectByte(16);
		int trail = bullet.getDataWatcher().getWatchableObjectByte(17);
		
		if(style != BulletConfiguration.STYLE_BLADE)
			GL11.glRotatef(new Random(bullet.getEntityId()).nextInt(90) - 45, 1.0F, 0.0F, 0.0F);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(style) {
			case BulletConfiguration.STYLE_NONE: break;
			case BulletConfiguration.STYLE_NORMAL: renderBullet(trail); break;
			case BulletConfiguration.STYLE_PISTOL: renderPistol(trail); break;
			case BulletConfiguration.STYLE_BOLT: renderDart(trail, bullet.getEntityId()); break;
			case BulletConfiguration.STYLE_FLECHETTE: renderFlechette(); break;
			case BulletConfiguration.STYLE_FOLLY: renderBullet(trail); break;
			case BulletConfiguration.STYLE_PELLET: renderBuckshot(); break;
			case BulletConfiguration.STYLE_ROCKET: renderRocket(trail); break;
			case BulletConfiguration.STYLE_GRENADE: renderGrenade(trail); break;
			case BulletConfiguration.STYLE_NUKE: renderNuke(0); break;
			case BulletConfiguration.STYLE_MIRV: renderNuke(1); break;
			case BulletConfiguration.STYLE_BF: renderNuke(2); break;
			case BulletConfiguration.STYLE_ORB: renderOrb(trail); break;
			case BulletConfiguration.STYLE_METEOR: renderMeteor(trail); break;
			case BulletConfiguration.STYLE_APDS: renderAPDS(); break;
			case BulletConfiguration.STYLE_BLADE: renderBlade(); break;
			default: renderBullet(trail); break;
		}
		
		
		GL11.glPopMatrix();
	}

	private void renderBullet(int type) {

		if (type == 2) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/emplacer.png"));
			bullet.renderAll(0.0625F);
		} else if (type == 1) {
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/models/tau.png"));
			bullet.renderAll(0.0625F);
		} else if (type == 0) {
			
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glRotated(90, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);

			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.bullet_rifle_tex);
			ResourceManager.projectiles.renderPart("BulletRifle");
			GL11.glShadeModel(GL11.GL_FLAT);
		}
		
	}
	
	private void renderPistol(int type) {
		
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glRotated(90, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.bullet_pistol_tex);
		ResourceManager.projectiles.renderPart("BulletPistol");
		GL11.glShadeModel(GL11.GL_FLAT);
		
	}
	
	private void renderBuckshot() {
		
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glRotated(90, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.buckshot_tex);
		ResourceManager.projectiles.renderPart("Buckshot");
		GL11.glShadeModel(GL11.GL_FLAT);
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
		case 10:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketCanister.png")); break;
		}
		
		if(type == 8) {
			bindTexture(ResourceManager.rpc_tex);
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			GL11.glRotatef(180, 1, 0, 0);
			ResourceManager.rpc.renderAll();
			return;
		} else {
			
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glRotated(90, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);

			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.rocket_tex);
			ResourceManager.projectiles.renderPart("Rocket");
			GL11.glShadeModel(GL11.GL_FLAT);
		}
	}
	
	private void renderGrenade(int type) {

		GL11.glScalef(0.25F, 0.25F, 0.25F);
		GL11.glRotated(90, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		
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

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.grenade_tex);
		ResourceManager.projectiles.renderPart("Grenade");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	private void renderNuke(int type) {
		
		switch(type) {
		case 0:
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glRotated(90, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.mini_nuke_tex);
			ResourceManager.projectiles.renderPart("MiniNuke");
			GL11.glShadeModel(GL11.GL_FLAT);
			break;
		case 1:
			GL11.glScaled(0.5, 0.5, 0.5);
			GL11.glRotated(90, 0, 0, 1);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			bindTexture(ResourceManager.mini_mirv_tex);
			ResourceManager.projectiles.renderPart("MiniMIRV");
			GL11.glShadeModel(GL11.GL_FLAT);
			break;
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
			
		case 1:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor4f(0.5F, 0.0F, 0.0F, 0.5F);
			ResourceManager.sphere_uv.renderAll();
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			ResourceManager.sphere_uv.renderAll();
			GL11.glScalef(1F/0.75F, 1F/0.75F, 1F/0.75F);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			for(int i = 0; i < 3; i++)
				RenderSparks.renderSpark((int) (System.currentTimeMillis() / 100 + 100 * i), 0, 0, 0, 1F, 2, 3, 0xFF0000, 0xFF8080);
			break;
		}
		
		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);

	}
	
	private void renderMeteor(int type) {

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);

		switch(type) {
		case 0:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/blocks/block_meteor_molten.png")); break;
		case 1:
			bindTexture(new ResourceLocation("textures/blocks/obsidian.png")); break;
		}
		
		ResourceManager.meteor.renderAll();
		
        GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	private void renderFlechette() {
		
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glRotated(90, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.flechette_tex);
		ResourceManager.projectiles.renderPart("Flechette");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	private void renderAPDS() {
		
		GL11.glScaled(2, 2, 2);
		GL11.glRotated(90, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.flechette_tex);
		ResourceManager.projectiles.renderPart("Flechette");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	private void renderDart(int style, int eID) {
		
		float red = 1F;
		float green = 1F;
		float blue = 1F;
		
		switch(style) {
		case BulletConfiguration.BOLT_LASER: red = 1F; green = 0F; blue = 0F; break;
		case BulletConfiguration.BOLT_NIGHTMARE: red = 1F; green = 1F; blue = 0F; break;
		case BulletConfiguration.BOLT_LACUNAE: red = 0.25F; green = 0F; blue = 0.75F; break;
		case BulletConfiguration.BOLT_WORM: red = 0F; green = 1F; blue = 0F; break;
		
		case BulletConfiguration.BOLT_ZOMG:
			Random rand = new Random(eID * eID);
			red = rand.nextInt(2) * 0.6F;
			green = rand.nextInt(2) * 0.6F;
			blue = rand.nextInt(2) * 0.6F;
			break;
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
	
	private void renderBlade() {
		GL11.glPushMatrix();
		
		EntityItem dummy = new EntityItem(Minecraft.getMinecraft().theWorld, 0, 0, 0, new ItemStack(ModItems.blade_titanium));
		dummy.getEntityItem().stackSize = 1;
		dummy.hoverStart = 0.0F;

		GL11.glRotated(90, 0, 0, 1);
		GL11.glTranslated(0, 0.5, 0);
		GL11.glRotated(System.currentTimeMillis() % 360, 1, 0, 0);
		GL11.glTranslated(0, -0.5, 0);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glScaled(1, 2, 1);
		RenderItem.renderInFrame = true;
		RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		RenderItem.renderInFrame = false;
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/bullet.png");
	}

}
