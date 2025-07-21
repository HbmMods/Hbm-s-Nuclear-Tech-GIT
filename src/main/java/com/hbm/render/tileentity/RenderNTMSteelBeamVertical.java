package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.deco.TileEntityNTMSteelBeamVertical;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderNTMSteelBeamVertical extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.5, z);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		// Rotate 90 degrees around X-axis to make it vertical
		GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);

		// Bind texture
		bindTexture(new ResourceLocation("hbm", "textures/models/ntm_steel_beam.png"));

		// Render the OBJ model if available (reuse the same model, just rotated)
		if(ResourceManager.ntm_steel_beam != null) {
			((WavefrontObject) ResourceManager.ntm_steel_beam).renderAll();
		}

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
