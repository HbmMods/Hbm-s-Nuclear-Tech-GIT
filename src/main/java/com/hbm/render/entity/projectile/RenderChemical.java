package com.hbm.render.entity.projectile;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityChemical;
import com.hbm.entity.projectile.EntityChemical.ChemicalStyle;
import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderChemical extends Render {
	
	private static ResourceLocation gas = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		EntityChemical chem = (EntityChemical) entity;
		ChemicalStyle style = chem.getStyle();
		
		if(style == ChemicalStyle.AMAT || style == ChemicalStyle.LIGHTNING)
			renderAmatBeam(chem, f1);
		
		if(style == ChemicalStyle.GAS) {
			this.bindEntityTexture(chem);
			renderGasCloud(chem, f1);
		}
		
		if(style == ChemicalStyle.GASFLAME) {
			this.bindEntityTexture(chem);
			renderGasFire(chem, f1);
		}

		GL11.glPopMatrix();
	}
	
	private void renderGasFire(EntityChemical chem, float interp) {

		float exp = (float) (chem.ticksExisted + interp) / (float) chem.getMaxAge();
		double size = 0.0 + exp * 2;
		Color color = Color.getHSBColor(Math.max((60 - exp * 100) / 360F, 0.0F), 1 - exp * 0.25F, 1 - exp * 0.5F);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		
		Tessellator tess = Tessellator.instance;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setColorRGBA_I(color.getRGB(), (int) Math.max(255 * (1 - exp), 0));
		tess.addVertexWithUV(-size, -size, 0.0D, 1, 1);
		tess.addVertexWithUV(size, -size, 0.0D, 0, 1);
		tess.addVertexWithUV(size, size, 0.0D, 0, 0);
		tess.addVertexWithUV(-size, size, 0.0D, 1, 0);
		tess.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
	}
	
	private void renderGasCloud(EntityChemical chem, float interp) {

		double exp = (double) (chem.ticksExisted + interp) / (double) chem.getMaxAge();
		double size = 0.0 + exp * 10;
		int color = chem.getType().getColor();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDepthMask(false);
		
		Tessellator tess = Tessellator.instance;
		GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		
		Random rand = new Random(chem.getEntityId());
		int i = rand.nextInt(2);
		int j = rand.nextInt(2);
		
		tess.startDrawingQuads();
		tess.setNormal(0.0F, 1.0F, 0.0F);
		tess.setColorRGBA_I(color, (int) Math.max(127 * (1 - exp), 0));
		tess.addVertexWithUV(-size, -size, 0.0D, 1 - i, 1 - j);
		tess.addVertexWithUV(size, -size, 0.0D, i, 1 - j);
		tess.addVertexWithUV(size, size, 0.0D, i, j);
		tess.addVertexWithUV(-size, size, 0.0D, 1 - i, j);
		tess.draw();

		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glAlphaFunc(GL11.GL_GEQUAL, 0.1F);
	}
	
	private void renderAmatBeam(EntityChemical chem, float interp) {

		float yaw = chem.prevRotationYaw + (chem.rotationYaw - chem.prevRotationYaw) * interp;
		float pitch = chem.prevRotationPitch + (chem.rotationPitch - chem.prevRotationPitch) * interp;
		GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-pitch - 90, 1.0F, 0.0F, 0.0F);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glDepthMask(false);
		
		/*double x0 = chem.prevPosX + (chem.posX - chem.prevPosX) * interp;
		double y0 = chem.prevPosX + (chem.posY - chem.prevPosY) * interp;
		double z0 = chem.prevPosX + (chem.posZ - chem.prevPosZ) * interp;*/
		
		double length = Vec3.createVectorHelper(chem.motionX, chem.motionY, chem.motionZ).lengthVector() * (chem.ticksExisted + interp) * 0.75;
		double size = 0.0625;
		float o = 0.2F;
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		
		tess.setColorRGBA_F(1F, 1F, 1F, o);
		tess.addVertex(-size, 0, -size);
		tess.addVertex(size, 0, -size);
		tess.setColorRGBA_F(1F, 1F, 1F, 0.0F);
		tess.addVertex(size, length, -size);
		tess.addVertex(-size, length, -size);

		tess.setColorRGBA_F(1F, 1F, 1F, o);
		tess.addVertex(-size, 0, size);
		tess.addVertex(size, 0, size);
		tess.setColorRGBA_F(1F, 1F, 1F, 0.0F);
		tess.addVertex(size, length, size);
		tess.addVertex(-size, length, size);

		tess.setColorRGBA_F(1F, 1F, 1F, o);
		tess.addVertex(-size, 0, -size);
		tess.addVertex(-size, 0, size);
		tess.setColorRGBA_F(1F, 1F, 1F, 0.0F);
		tess.addVertex(-size, length, size);
		tess.addVertex(-size, length, -size);

		tess.setColorRGBA_F(1F, 1F, 1F, o);
		tess.addVertex(size, 0, -size);
		tess.addVertex(size, 0, size);
		tess.setColorRGBA_F(1F, 1F, 1F, 0.0F);
		tess.addVertex(size, length, size);
		tess.addVertex(size, length, -size);
		
		tess.draw();

		GL11.glDepthMask(true);
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return gas;
	}
}
