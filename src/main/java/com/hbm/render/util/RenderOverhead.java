package com.hbm.render.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.wiaj.WorldInAJar;
import com.hbm.util.Clock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

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

	public static boolean shouldRenderTag(EntityLivingBase p_110813_1_) {
		return Minecraft.isGuiEnabled() && p_110813_1_ != RenderManager.instance.livingPlayer && !p_110813_1_.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer) && p_110813_1_.riddenByEntity == null;
	}

	public static void drawTagAware(EntityLivingBase entity, double x, double y, double z, String string, boolean depthTest) {
		if(entity.isPlayerSleeping()) {
			drawTag(entity, string, x, y - 1.5D, z, 64, depthTest);
		} else {
			drawTag(entity, string, x, y, z, 64, depthTest);
		}
	}

	public static void drawTag(Entity entity, String name, double x, double y, double z, int dist, boolean depthTest) {
		drawTag(entity.height + 0.75F, entity.getDistanceSqToEntity(RenderManager.instance.livingPlayer), name, x, y, z, dist, depthTest, -1, 553648127);
	}

	public static void drawTag(float offset, double distsq, String name, double x, double y, double z, int dist, boolean depthTest, int color, int shadowColor) {

		if(distsq <= (double) (dist * dist)) {
			FontRenderer fontrenderer = Minecraft.getMinecraft().fontRenderer;
			float f = 1.6F;
			float scale = 0.016666668F * f;
			GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.0F, (float) y + offset, (float) z);
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
			fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, heightOffset, shadowColor);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthMask(true);
			fontrenderer.drawString(name, -fontrenderer.getStringWidth(name) / 2, heightOffset, color);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glPopMatrix();
		}
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

	public static final HashMap<BlockPos, Marker> queuedMarkers = new HashMap();
	private static final HashMap<BlockPos, Marker> markers = new HashMap();

	public static void renderMarkers(float partialTicks) {

		markers.putAll(queuedMarkers);
		queuedMarkers.clear();

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

		Iterator<Entry<BlockPos, Marker>> it = markers.entrySet().iterator();
		List<Entry<BlockPos, Marker>> tagList = new ArrayList();
		while(it.hasNext()) {
			Entry<BlockPos, Marker> entry = it.next();
			BlockPos pos = entry.getKey();
			Marker marker = entry.getValue();

			int pX = pos.getX();
			int pY = pos.getY();
			int pZ = pos.getZ();

			double minX = marker.minX;
			double minY = marker.minY;
			double minZ = marker.minZ;
			double maxX = marker.maxX;
			double maxY = marker.maxY;
			double maxZ = marker.maxZ;

			tess.setColorOpaque_I(marker.color);
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

			tagList.add(entry);

			if(marker.expire > 0 && Clock.get_ms() > marker.expire) {
				it.remove();
			} else if(marker.maxDist > 0) {
				double aX = pX + (maxX - minX) / 2D;
				double aY = pY + (maxY - minY) / 2D;
				double aZ = pZ + (maxZ - minZ) / 2D;
				Vec3 vec = Vec3.createVectorHelper(x - aX, y - aY, z - aZ);
				if(vec.lengthVector() > marker.maxDist) {
					it.remove();
				}
			}
		}

		tess.draw();

		tess.setColorOpaque_F(1F, 1F, 1F);

		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_POINT_SMOOTH);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		for(Entry<BlockPos, Marker> entry : tagList) {

			BlockPos pos = entry.getKey();
			Marker marker = entry.getValue();

			int pX = pos.getX();
			int pY = pos.getY();
			int pZ = pos.getZ();

			double minX = marker.minX;
			double minY = marker.minY;
			double minZ = marker.minZ;
			double maxX = marker.maxX;
			double maxY = marker.maxY;
			double maxZ = marker.maxZ;

			double aX = pX + (maxX - minX) / 2D;
			double aY = pY + (maxY - minY) / 2D;
			double aZ = pZ + (maxZ - minZ) / 2D;
			Vec3 vec = Vec3.createVectorHelper(aX - x, aY - y, aZ - z);
			double len = vec.xCoord * vec.xCoord + vec.yCoord * vec.yCoord + vec.zCoord * vec.zCoord;
			double sqrt = Math.sqrt(len);
			double mult = Math.min(sqrt, 16D);
			vec.xCoord *= mult / sqrt;
			vec.yCoord *= mult / sqrt;
			vec.zCoord *= mult / sqrt;
			Vec3 look = player.getLookVec();
			Vec3 diff = vec.normalize();
			String label = marker.label;
			if(label == null) {
				label = "";
			}

			if(Math.abs(look.xCoord - diff.xCoord) + Math.abs(look.yCoord - diff.yCoord) + Math.abs(look.zCoord - diff.zCoord) < 0.15) {
				label += (!label.isEmpty() ? " " : "") + ((int) sqrt) + "m";
			}

			if(!label.isEmpty()) drawTag(1F, len, label, vec.xCoord, vec.yCoord, vec.zCoord, 100, true, marker.color, marker.color);
		}
		GL11.glPopMatrix();
	}

	public static class Marker {
		double minX = 0;
		double minY = 0;
		double minZ = 0;
		double maxX = 1;
		double maxY = 1;
		double maxZ = 1;

		int color;
		String label;

		long expire;
		double maxDist;

		public Marker(int color) {
			this.color = color;
		}

		public Marker setExpire(long expire) {
			this.expire = expire;
			return this;
		}

		public Marker setDist(double maxDist) {
			this.maxDist = maxDist;
			return this;
		}

		public Marker withLabel(String label) {
			this.label = label;
			return this;
		}
	}

	private static WorldInAJar actionPreviewWorld;
	private static int offsetX;
	private static int offsetY;
	private static int offsetZ;
	private static boolean actionPreviewSuccess;

	private static boolean clearPreview;

	public static void setActionPreview(WorldInAJar wiaj, int x, int y, int z, boolean canAction) {
		actionPreviewWorld = wiaj;
		offsetX = x;
		offsetY = y;
		offsetZ = z;
		actionPreviewSuccess = canAction;
	}

	// Prevents thread unsafe null exception
	public static void clearActionPreview() {
		clearPreview = true;
	}

	public static void renderActionPreview(float partialTicks) {
		if(clearPreview) {
			actionPreviewWorld = null;
			clearPreview = false;
		}

		if(actionPreviewWorld == null) return;

		RenderBlocks renderer = new RenderBlocks(actionPreviewWorld);

		renderer.enableAO = true;
		actionPreviewWorld.lightlevel = 15;

		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glPushMatrix();
		{

			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			double x = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
			double y =  player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
			double z =  player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;

			GL11.glTranslated(offsetX - x, offsetY - y, offsetZ - z);

			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			Tessellator.instance.startDrawingQuads();

			Tessellator.instance.disableColor();
			if(actionPreviewSuccess) {
				GL11.glColor3f(0, 1, 1);
			} else {
				GL11.glColor3f(1, 0, 0);
			}

			for(int ix = 0; ix < actionPreviewWorld.sizeX; ix++) {
				for(int iy = 0; iy < actionPreviewWorld.sizeY; iy++) {
					for(int iz = 0; iz < actionPreviewWorld.sizeZ; iz++) {
						try { renderer.renderBlockByRenderType(actionPreviewWorld.getBlock(ix, iy, iz), ix, iy, iz); } catch(Exception ex) { }
					}
				}
			}

			Tessellator.instance.draw();
			GL11.glShadeModel(GL11.GL_FLAT);

		}
		GL11.glPopMatrix();
	}
}
