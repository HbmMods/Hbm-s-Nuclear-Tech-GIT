package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.util.ObjUtil;
import com.hbm.tileentity.deco.TileEntityNTMSteelBeam;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class RenderNTMSteelBeam extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		// Get rotation from metadata
		int metadata = tileEntity.getBlockMetadata();
		float rotation = 0.0F;

		switch(metadata) {
		case 2: // North
			rotation = 0.0F;
			break;
		case 3: // South
			rotation = 180.0F;
			break;
		case 4: // West
			rotation = 90.0F;
			break;
		case 5: // East
			rotation = 270.0F;
			break;
		}

		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);

		// Bind texture
		bindTexture(new ResourceLocation("hbm", "textures/models/ntm_steel_beam.png"));

		// Render the OBJ model if available
		if(ResourceManager.ntm_steel_beam != null) {
			((WavefrontObject) ResourceManager.ntm_steel_beam).renderAll();
		}

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
}
