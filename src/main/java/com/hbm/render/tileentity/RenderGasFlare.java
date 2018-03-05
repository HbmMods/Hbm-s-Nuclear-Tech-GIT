package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderGasFlare extends TileEntitySpecialRenderer {

	private static final ResourceLocation body = new ResourceLocation(RefStrings.MODID, "models/oilFlare.obj");
	private IModelCustom genModel;
    private ResourceLocation genTexture;
	
	public RenderGasFlare()
    {
		genModel = AdvancedModelLoader.loadModel(body);
		genTexture = new ResourceLocation(RefStrings.MODID, "textures/models/oilFlareTexture.png");
    }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(genTexture);
        
        genModel.renderAll();

        GL11.glPopMatrix();
    }
}
