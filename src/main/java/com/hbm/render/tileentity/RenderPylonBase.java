package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.tileentity.network.TileEntityPylonBase;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class RenderPylonBase extends TileEntitySpecialRenderer {
	
	//TODO: adapt this into a more generic form for multi wire pylons
	@Deprecated
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
					
					drawPowerLine(
							x + myOffset.xCoord + (wX * j / count),
							y + myOffset.yCoord + (wY * j / count) - Math.sin(j / count * Math.PI * 0.5) * hang,
							z + myOffset.zCoord + (wZ * j / count),
							x + myOffset.xCoord + (wX * k / count),
							y + myOffset.yCoord + (wY * k / count) - Math.sin(k / count * Math.PI * 0.5) * hang,
							z + myOffset.zCoord + (wZ * k / count));
				}
			}
		}
	}
	
	public void renderLinesGeneric(TileEntityPylonBase pyl, double x, double y, double z) {
		
	}
	
	public void renderLine(World world, TileEntityPylonBase pyl, double x, double y, double z, double x0, double y0, double z0, double x1, double y1, double z1) {

		GL11.glTranslated(x, y, z);
		float count = 10;
		
		for(float j = 0; j < count; j++) {
			
			float k = j + 1;
			
			double deltaX = x1 - x0;
			double deltaY = y1 - y0;
			double deltaZ = z1 - z0;
			
			double ja = j + 0.5D;
			double ix = pyl.xCoord + x0 + deltaX / (double)(count * 2) * ja;
			double iy = pyl.yCoord + y0 + deltaY / (double)(count * 2) * ja - Math.sin(j / count * Math.PI * 0.5);
			double iz = pyl.zCoord + z0 + deltaZ / (double)(count * 2) * ja;
			
			int brightness = world.getLightBrightnessForSkyBlocks(MathHelper.floor_double(ix), MathHelper.floor_double(iy), MathHelper.floor_double(iz), 0);
			int lX = brightness % 65536;
			int lY = brightness / 65536;
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)lX / 1.0F, (float)lY / 1.0F);
			
			drawPowerLine(
					x0 + (deltaX * j / count),
					y0 + (deltaY * j / count) - Math.sin(j / count * Math.PI * 0.5),
					z0 + (deltaZ * j / count),
					x0 + (deltaX * k / count),
					y0 + (deltaY * k / count) - Math.sin(k / count * Math.PI * 0.5),
					z0 + (deltaZ * k / count));
		}
	}
	
	public void drawPowerLine(double x, double y, double z, double a, double b, double c) {
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawing(5);
		tessellator.setColorOpaque_I(0xBB3311);
		tessellator.addVertex(x, y + 0.05F, z);
		tessellator.addVertex(x, y - 0.05F, z);
		tessellator.addVertex(a, b + 0.05F, c);
		tessellator.addVertex(a, b - 0.05F, c);
		tessellator.draw();
		tessellator.startDrawing(5);
		tessellator.setColorOpaque_I(0xBB3311);
		tessellator.addVertex(x + 0.05F, y, z);
		tessellator.addVertex(x - 0.05F, y, z);
		tessellator.addVertex(a + 0.05F, b, c);
		tessellator.addVertex(a - 0.05F, b, c);
		tessellator.draw();
		tessellator.startDrawing(5);
		tessellator.setColorOpaque_I(0xBB3311);
		tessellator.addVertex(x, y, z + 0.05F);
		tessellator.addVertex(x, y, z - 0.05F);
		tessellator.addVertex(a, b, c + 0.05F);
		tessellator.addVertex(a, b, c - 0.05F);
		tessellator.draw();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
}
