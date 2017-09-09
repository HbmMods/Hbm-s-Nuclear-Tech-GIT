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
		for(TileEntityPylonRedWire wire : pyl.connected)
			drawPowerLine(x + 0.5, y + 5.3, z + 0.5, x + (wire.xCoord - pyl.xCoord) + 0.5, y + (wire.yCoord - pyl.yCoord) + 5.3, z + (wire.zCoord - pyl.zCoord) + 0.5);
		GL11.glPopMatrix();
	}
	
	public void drawPowerLine(double x, double y, double z, double a, double b, double c) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(5);
        tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
        tessellator.addVertex(x, y + 0.05F, z);
        tessellator.addVertex(x, y - 0.05F, z);
        tessellator.addVertex(a, b + 0.05F, c);
        tessellator.addVertex(a, b - 0.05F, c);
        tessellator.draw();
        tessellator.startDrawing(5);
        tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
        tessellator.addVertex(x + 0.05F, y, z);
        tessellator.addVertex(x - 0.05F, y, z);
        tessellator.addVertex(a + 0.05F, b, c);
        tessellator.addVertex(a - 0.05F, b, c);
        tessellator.draw();
        tessellator.startDrawing(5);
        tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
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
