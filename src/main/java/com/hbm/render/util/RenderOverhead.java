package com.hbm.render.util;

import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Triplet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class RenderOverhead {

	public static void renderTag(EntityLivingBase living, double x, double y, double z, RendererLivingEntity renderer, String name, boolean depthTest) {

		EntityPlayer thePlayer = Minecraft.getMinecraft().thePlayer;

		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

		if(shouldRenderTag(living)) {
			double distSq = living.getDistanceSqToEntity(thePlayer);
			float range = living.isSneaking() ? renderer.NAME_TAG_RANGE_SNEAK : renderer.NAME_TAG_RANGE;

			if(distSq < (double) (range * range)) {
				drawTagAware(living, x, y, z, name, depthTest);
			}
		}
	}

	protected static boolean shouldRenderTag(EntityLivingBase p_110813_1_) {
		return Minecraft.isGuiEnabled() && p_110813_1_ != RenderManager.instance.livingPlayer && !p_110813_1_.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) && p_110813_1_.riddenByEntity == null;
	}

	protected static void drawTagAware(EntityLivingBase entity, double x, double y, double z, String string, boolean depthTest) {
		if(entity.isPlayerSleeping()) {
			drawTag(entity, string, x, y - 1.5D, z, 64, depthTest);
		} else {
			drawTag(entity, string, x, y, z, 64, depthTest);
		}
	}

	protected static void drawTag(Entity entity, String name, double x, double y, double z, int dist, boolean depthTest) {
		
		double distsq = entity.getDistanceSqToEntity(RenderManager.instance.livingPlayer);

		if(distsq <= (double) (dist * dist)) {
			FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
			float f = 1.6F;
			float scale = 0.016666668F * f;
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.0F, (float) y + entity.height + 0.75F, (float) z);
			GL11.glNormal3f(0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(-scale, -scale, scale);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDepthMask(false);
			if(depthTest) {
				GL11.glDisable(GL11.GL_DEPTH_TEST);
			}
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			Tessellator tessellator = Tessellator.instance;
			byte heightOffset = 0;

			if(name.equals("deadmau5")) {
				heightOffset = -10;
			}

			GL11.glDisable(GL11.GL_TEXTURE_2D);
			tessellator.startDrawingQuads();
			int center = fontrenderer.getStringWidth(name) / 2;
			tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
			tessellator.addVertex((double) (-center - 1), (double) (-1 + heightOffset), 0.0D);
			tessellator.addVertex((double) (-center - 1), (double) (8 + heightOffset), 0.0D);
			tessellator.addVertex((double) (center + 1), (double) (8 + heightOffset), 0.0D);
			tessellator.addVertex((double) (center + 1), (double) (-1 + heightOffset), 0.0D);
			tessellator.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, heightOffset, 553648127);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, heightOffset, -1);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
	}
	
	public static final HashMap<Triplet<Integer, Integer, Integer>, Pair<double[], Integer>> markers = new HashMap();
	
	public static void renderMarkers(float partialTicks) {
		
		if(markers.isEmpty())
			return;
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
		double y =  player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
		double z =  player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_COLOR_MATERIAL);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_LINES);
		
		for(Entry<Triplet<Integer, Integer, Integer>, Pair<double[], Integer>> entry : markers.entrySet()) {
			Triplet<Integer, Integer, Integer> pos = entry.getKey();
			Pair<double[], Integer> pars = entry.getValue();

			int pX = pos.getX();
			int pY = pos.getY();
			int pZ = pos.getZ();
			
			int color = pars.getValue();
			double[] bounds = pars.getKey();
			double minX = bounds[0];
			double minY = bounds[1];
			double minZ = bounds[2];
			double maxX = bounds[3];
			double maxY = bounds[4];
			double maxZ = bounds[5];
			
			tess.setColorOpaque_I(color);
			tess.addVertex(pX + minX - x, pY + maxY - y, pZ + minZ - z);
			tess.addVertex(pX + minX - x, pY + minY - y, pZ + minZ - z);
			tess.addVertex(pX + minX - x, pY + maxY - y, pZ + minZ - z);
			tess.addVertex(pX + maxX - x, pY + maxY - y, pZ + minZ - z);
			tess.addVertex(pX + maxX - x, pY + maxY - y, pZ + minZ - z);
			tess.addVertex(pX + maxX - x, pY + minY - y, pZ + minZ - z);
			tess.addVertex(pX + minX - x, pY + minY - y, pZ + minZ - z);
			tess.addVertex(pX + maxX - x, pY + minY - y, pZ + minZ - z);
			tess.addVertex(pX + maxX - x, pY + minY - y, pZ + minZ - z);
			tess.addVertex(pX + maxX - x, pY + minY - y, pZ + maxZ - z);
			tess.addVertex(pX + maxX - x, pY + maxY - y, pZ + maxZ - z);
			tess.addVertex(pX + maxX - x, pY + maxY - y, pZ + minZ - z);
			tess.addVertex(pX + maxX - x, pY + maxY - y, pZ + maxZ - z);
			tess.addVertex(pX + maxX - x, pY + minY - y, pZ + maxZ - z);
			tess.addVertex(pX + minX - x, pY + maxY - y, pZ + minZ - z);
			tess.addVertex(pX + minX - x, pY + maxY - y, pZ + maxZ - z);
			tess.addVertex(pX + minX - x, pY + maxY - y, pZ + maxZ - z);
			tess.addVertex(pX + minX - x, pY + minY - y, pZ + maxZ - z);
			tess.addVertex(pX + minX - x, pY + maxY - y, pZ + maxZ - z);
			tess.addVertex(pX + maxX - x, pY + maxY - y, pZ + maxZ - z);
			tess.addVertex(pX + minX - x, pY + minY - y, pZ + maxZ - z);
			tess.addVertex(pX + maxX - x, pY + minY - y, pZ + maxZ - z);
			tess.addVertex(pX + minX - x, pY + minY - y, pZ + minZ - z);
			tess.addVertex(pX + minX - x, pY + minY - y, pZ + maxZ - z);
		}
		
		tess.draw();
		
		tess.setColorOpaque_F(1F, 1F, 1F);
		
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}
	
	public static void renderThermalSight(float partialTicks) {
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
		double y =  player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
		double z =  player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_COLOR_MATERIAL);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		Tessellator tess = Tessellator.instance;
		tess.startDrawing(GL11.GL_LINES);

		for(Object o : player.worldObj.loadedEntityList) {
			
			Entity ent = (Entity) o;
			
			if(ent == player)
				continue;
			
			if(ent.getDistanceSqToEntity(player) > 4096)
				continue;
			
			if(ent instanceof IBossDisplayData)
				tess.setColorOpaque_F(1F, 0.5F, 0F);
			else if(ent instanceof IMob)
				tess.setColorOpaque_F(1F, 0F, 0F);
			else if(ent instanceof EntityPlayer)
				tess.setColorOpaque_F(1F, 0F, 1F);
			else if(ent instanceof EntityLiving)
				tess.setColorOpaque_F(0F, 1F, 0F);
			else if(ent instanceof EntityItem)
				tess.setColorOpaque_F(1F, 1F, 0.5F);
			else if(ent instanceof EntityXPOrb) {
				if(player.ticksExisted % 10 < 5)
					tess.setColorOpaque_F(1F, 1F, 0.5F);
				else
					tess.setColorOpaque_F(0.5F, 1F, 0.5F);
			} else
				continue;
			
			AxisAlignedBB bb = ent.boundingBox;
			tess.addVertex(bb.minX - x, bb.maxY - y, bb.minZ - z);
			tess.addVertex(bb.minX - x, bb.minY - y, bb.minZ - z);
			tess.addVertex(bb.minX - x, bb.maxY - y, bb.minZ - z);
			tess.addVertex(bb.maxX - x, bb.maxY - y, bb.minZ - z);
			tess.addVertex(bb.maxX - x, bb.maxY - y, bb.minZ - z);
			tess.addVertex(bb.maxX - x, bb.minY - y, bb.minZ - z);
			tess.addVertex(bb.minX - x, bb.minY - y, bb.minZ - z);
			tess.addVertex(bb.maxX - x, bb.minY - y, bb.minZ - z);
			tess.addVertex(bb.maxX - x, bb.minY - y, bb.minZ - z);
			tess.addVertex(bb.maxX - x, bb.minY - y, bb.maxZ - z);
			tess.addVertex(bb.maxX - x, bb.maxY - y, bb.maxZ - z);
			tess.addVertex(bb.maxX - x, bb.maxY - y, bb.minZ - z);
			tess.addVertex(bb.maxX - x, bb.maxY - y, bb.maxZ - z);
			tess.addVertex(bb.maxX - x, bb.minY - y, bb.maxZ - z);
			tess.addVertex(bb.minX - x, bb.maxY - y, bb.minZ - z);
			tess.addVertex(bb.minX - x, bb.maxY - y, bb.maxZ - z);
			tess.addVertex(bb.minX - x, bb.maxY - y, bb.maxZ - z);
			tess.addVertex(bb.minX - x, bb.minY - y, bb.maxZ - z);
			tess.addVertex(bb.minX - x, bb.maxY - y, bb.maxZ - z);
			tess.addVertex(bb.maxX - x, bb.maxY - y, bb.maxZ - z);
			tess.addVertex(bb.minX - x, bb.minY - y, bb.maxZ - z);
			tess.addVertex(bb.maxX - x, bb.minY - y, bb.maxZ - z);
			tess.addVertex(bb.minX - x, bb.minY - y, bb.minZ - z);
			tess.addVertex(bb.minX - x, bb.minY - y, bb.maxZ - z);
		}
		
		tess.draw();
		
		tess.setColorOpaque_F(1F, 1F, 1F);
		
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
	}
}
