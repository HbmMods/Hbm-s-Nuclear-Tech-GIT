package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.network.TileEntityPylonBase;
import com.hbm.tileentity.network.TileEntityPylonBase.ConnectionType;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class RenderPylonBase extends TileEntitySpecialRenderer {
	
	//TODO: adapt this into a more generic form for multi wire pylons
	/*@Deprecated
	public void renderSingleLine(TileEntityPylonBase pyl, double x, double y, double z) {
		
		for(int i = 0; i < pyl.connected.size(); i++) {

			int[] wire = pyl.connected.get(i);
			TileEntity tile = pyl.getWorldObj().getTileEntity(wire[0], wire[1], wire[2]);
			
			if(tile instanceof TileEntityPylonBase) {
				TileEntityPylonBase pylon = (TileEntityPylonBase) tile;
				Vec3 myOffset = pyl.getMountPos()[0];
				Vec3 theirOffset = pylon.getMountPos()[0];

				double conX0 = pyl.xCoord + myOffset.xCoord;
				double conY0 = pyl.yCoord + myOffset.yCoord;
				double conZ0 = pyl.zCoord + myOffset.zCoord;
				double conX1 = pylon.xCoord + theirOffset.xCoord;
				double conY1 = pylon.yCoord + theirOffset.yCoord;
				double conZ1 = pylon.zCoord + theirOffset.zCoord;
				
				double wX = (conX1 - conX0) / 2D;
				double wY = (conY1 - conY0) / 2D;
				double wZ = (conZ1 - conZ0) / 2D;
				
				float count = 10;
				Vec3 delta = Vec3.createVectorHelper(conX1 - conX0, conY1 - conY0, conZ1 - conZ0);
				double hang = delta.lengthVector() / 15D;
				
				for(float j = 0; j < count; j++) {
					
					float k = j + 1;
					
					double ja = j + 0.5D;
					double ix = conX0 + delta.xCoord / (double)(count * 2) * ja;
					double iy = conY0 + delta.yCoord / (double)(count * 2) * ja - Math.sin(j / count * Math.PI * 0.5) * hang;
					double iz = conZ0 + delta.zCoord / (double)(count * 2) * ja;
					
					//pylon.getWorldObj().spawnParticle("reddust", ix, iy, iz, 0.01 + j * 0.1, 0, 0);
					
					int brightness = pyl.getWorldObj().getLightBrightnessForSkyBlocks(MathHelper.floor_double(ix), MathHelper.floor_double(iy), MathHelper.floor_double(iz), 0);
					int lX = brightness % 65536;
					int lY = brightness / 65536;
					OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lX / 1.0F, (float)lY / 1.0F);
					
					drawLineSegment(
							x + myOffset.xCoord + (wX * j / count),
							y + myOffset.yCoord + (wY * j / count) - Math.sin(j / count * Math.PI * 0.5) * hang,
							z + myOffset.zCoord + (wZ * j / count),
							x + myOffset.xCoord + (wX * k / count),
							y + myOffset.yCoord + (wY * k / count) - Math.sin(k / count * Math.PI * 0.5) * hang,
							z + myOffset.zCoord + (wZ * k / count));
				}
			}
		}
	}*/
	
	/**
	 * The closest we have to a does-all solution. It will figure out if it needs to draw multiple lines,
	 * iterate through all the mounting points, try to find the matching mounting points and then draw the lines.
	 * @param pyl
	 * @param x
	 * @param y
	 * @param z
	 */
	public void renderLinesGeneric(TileEntityPylonBase pyl, double x, double y, double z) {
		
		this.bindTexture(ResourceManager.wire_tex);
		
		for(int i = 0; i < pyl.connected.size(); i++) {

			int[] wire = pyl.connected.get(i);
			TileEntity tile = pyl.getWorldObj().getTileEntity(wire[0], wire[1], wire[2]);
			
			if(tile instanceof TileEntityPylonBase) {
				TileEntityPylonBase pylon = (TileEntityPylonBase) tile;
				
				Vec3[] m1 = pyl.getMountPos();
				Vec3[] m2 = pylon.getMountPos();
				
				int lineCount = Math.max(pyl.getConnectionType() == ConnectionType.QUAD ? 4 : 1, pylon.getConnectionType() == ConnectionType.QUAD ? 4 : 1);
				
				for(int line = 0; line < lineCount; line++) {

					Vec3 first = m1[line % m1.length];
					int secondIndex = line % m2.length;
					
					/*
					 * hacky hacky hack
					 * this will shift the mount point order by 2 to prevent wires from crossing
					 * when meta 12 and 15 pylons are connected. this isn't a great solution
					 * and there's still ways to cross the wires in an ugly way but for now
					 * it should be enough.
					 */
					if(lineCount == 4 && (
							(pyl.getBlockMetadata() - 10 == 5 && pylon.getBlockMetadata() - 10 == 2) ||
							(pyl.getBlockMetadata() - 10 == 2 && pylon.getBlockMetadata() - 10 == 5))) {
						
						secondIndex += 2;
						secondIndex %= m2.length;
					}
					
					Vec3 second = m2[secondIndex];
					
					double sX = second.xCoord + pylon.xCoord - pyl.xCoord;
					double sY = second.yCoord + pylon.yCoord - pyl.yCoord;
					double sZ = second.zCoord + pylon.zCoord - pyl.zCoord;
					
					renderLine(pyl.getWorldObj(), pyl, x, y, z,
							first.xCoord,
							first.yCoord,
							first.zCoord,
							first.xCoord + (sX - first.xCoord) * 0.5,
							first.yCoord + (sY - first.yCoord) * 0.5,
							first.zCoord + (sZ - first.zCoord) * 0.5);
				}
			}
		}
	}
	
	/**
	 * Renders half a line
	 * First coords: the relative render position
	 * Second coords: the pylon's mounting point
	 * Third coords: the midway point exactly between the mounting points. The "hang" doesn't need to be accounted for, it's calculated in here.
	 * @param world
	 * @param pyl
	 * @param x
	 * @param y
	 * @param z
	 * @param x0
	 * @param y0
	 * @param z0
	 * @param x1
	 * @param y1
	 * @param z1
	 */
	public void renderLine(World world, TileEntityPylonBase pyl, double x, double y, double z, double x0, double y0, double z0, double x1, double y1, double z1) {

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		float count = 10;
		Tessellator tess = Tessellator.instance;
		
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		tess.startDrawingQuads();
		Vec3 delta = Vec3.createVectorHelper(x0 - x1, y0 - y1, z0 - z1);
		double hang = Math.min(delta.lengthVector() / 15D, 2.5D);
		
		for(float j = 0; j < count; j++) {
			
			float k = j + 1;

			double sagJ = Math.sin(j / count * Math.PI * 0.5) * hang;
			double sagK = Math.sin(k / count * Math.PI * 0.5) * hang;
			double sagMean = (sagJ + sagK) / 2D;
			
			double deltaX = x1 - x0;
			double deltaY = y1 - y0;
			double deltaZ = z1 - z0;
			
			double ja = j + 0.5D;
			double ix = pyl.xCoord + x0 + deltaX / (double)(count) * ja;
			double iy = pyl.yCoord + y0 + deltaY / (double)(count) * ja - sagMean;
			double iz = pyl.zCoord + z0 + deltaZ / (double)(count) * ja;
			
			int brightness = world.getLightBrightnessForSkyBlocks(MathHelper.floor_double(ix), MathHelper.floor_double(iy), MathHelper.floor_double(iz), 0);
			tess.setBrightness(brightness);
			
			drawLineSegment(tess,
					x0 + (deltaX * j / count),
					y0 + (deltaY * j / count) - sagJ,
					z0 + (deltaZ * j / count),
					x0 + (deltaX * k / count),
					y0 + (deltaY * k / count) - sagK,
					z0 + (deltaZ * k / count));
		}
		tess.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glPopMatrix();
	}
	
	/**
	 * Draws a single segment from the first to the second 3D coordinate.
	 * Not fantastic but it looks good enough.
	 * Possible enhancement: remove the draw calls and put those around the drawLineSegment calls for better-er performance
	 * @param x
	 * @param y
	 * @param z
	 * @param a
	 * @param b
	 * @param c
	 */
	public void drawLineSegment(Tessellator tessellator, double x, double y, double z, double a, double b, double c) {
		
		double girth = 0.03125D;
		
		double deltaX = a - x;
		double deltaY = b - y;
		double deltaZ = c - z;
		double hyp = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
		double yaw = Math.atan2(deltaX, deltaZ);
		double pitch = Math.atan2(deltaY, hyp);
		double rotator = Math.PI * 0.5D;
		double newPitch = pitch + rotator;
		double newYaw = yaw + rotator;
		double iZ = Math.cos(yaw) * Math.cos(newPitch) * girth;
		double iX = Math.sin(yaw) * Math.cos(newPitch) * girth;
		double iY = Math.sin(newPitch) * girth;
		double jZ = Math.cos(newYaw) * Math.cos(newPitch) * girth;
		double jX = Math.sin(newYaw) * Math.cos(newPitch) * girth;
		double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
		int wrap = (int) Math.ceil(length * 8);
		
		if(deltaX + deltaZ < 0) wrap *= -1;
		
		tessellator.setColorOpaque_I(0xffffff);
		tessellator.addVertexWithUV(x + iX, y + iY, z + iZ, 0, 0);
		tessellator.addVertexWithUV(x - iX, y - iY, z - iZ, 0, 1);
		tessellator.addVertexWithUV(a - iX, b - iY, c - iZ, wrap, 1);
		tessellator.addVertexWithUV(a + iX, b + iY, c + iZ, wrap, 0);
		tessellator.addVertexWithUV(x + jX, y, z + jZ, 0, 0);
		tessellator.addVertexWithUV(x - jX, y, z - jZ, 0, 1);
		tessellator.addVertexWithUV(a - jX, b, c - jZ, wrap, 1);
		tessellator.addVertexWithUV(a + jX, b, c + jZ, wrap, 0);
	}
	
	public static final int LINE_COLOR = 0xBB3311;
}
