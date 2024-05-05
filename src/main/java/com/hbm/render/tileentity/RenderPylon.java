package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelPylon;
import com.hbm.tileentity.network.TileEntityPylon;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderPylon extends RenderPylonBase {

	private static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelPylon.png");
	
	private ModelPylon pylon;
	
	public RenderPylon() {
		this.pylon = new ModelPylon();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		TileEntityPylon pyl = (TileEntityPylon)te;
		
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F - ((1F / 16F) * 14F), (float) z + 0.5F);
			GL11.glRotatef(180, 0F, 0F, 1F);
			bindTexture(texture);
			this.pylon.renderAll(0.0625F);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		this.renderLinesGeneric(pyl, x, y, z);
		GL11.glPopMatrix();
	}
}
