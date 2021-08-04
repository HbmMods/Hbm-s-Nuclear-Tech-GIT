package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelPylon;
import com.hbm.tileentity.conductor.TileEntityPylonRedWire;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderPylon extends TileEntitySpecialRenderer {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelPylon.png");
	
	private ModelPylon pylon;
	
	public RenderPylon() {
		this.pylon = new ModelPylon();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		TileEntityPylonRedWire pyl = (TileEntityPylonRedWire)tileentity;
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F - ((1F / 16F) * 14F), (float) z + 0.5F);
			GL11.glRotatef(180, 0F, 0F, 1F);
			bindTexture(texture);
			this.pylon.renderAll(0.0625F);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		
		for(int i = 0; i < pyl.connected.size(); i++) {

			int[] wire = pyl.connected.get(i);
			
			float wX = (wire[0] - pyl.xCoord) / 2F;
			float wY = (wire[1] - pyl.yCoord) / 2F;
			float wZ = (wire[2] - pyl.zCoord) / 2F;
			
			float count = 10;
			for(float j = 0; j < count; j++) {
				
				float k = j + 1;
				
				drawPowerLine(
						x + 0.5 + (wX * j / count),
						y + 5.4 + (wY * j / count) - Math.sin(j / count * Math.PI * 0.5),
						z + 0.5 + (wZ * j / count),
						x + 0.5 + (wX * k / count),
						y + 5.4 + (wY * k / count) - Math.sin(k / count * Math.PI * 0.5),
						z + 0.5 + (wZ * k / count));
			}
		}
		GL11.glPopMatrix();
	}
	
	public void drawPowerLine(double x, double y, double z, double a, double b, double c) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(5);
        tessellator.setColorOpaque_I(0xBB3311);
        //tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
        tessellator.addVertex(x, y + 0.05F, z);
        tessellator.addVertex(x, y - 0.05F, z);
        tessellator.addVertex(a, b + 0.05F, c);
        tessellator.addVertex(a, b - 0.05F, c);
        tessellator.draw();
        tessellator.startDrawing(5);
        tessellator.setColorOpaque_I(0xBB3311);
        //tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
        tessellator.addVertex(x + 0.05F, y, z);
        tessellator.addVertex(x - 0.05F, y, z);
        tessellator.addVertex(a + 0.05F, b, c);
        tessellator.addVertex(a - 0.05F, b, c);
        tessellator.draw();
        tessellator.startDrawing(5);
        tessellator.setColorOpaque_I(0xBB3311);
        //tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
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
