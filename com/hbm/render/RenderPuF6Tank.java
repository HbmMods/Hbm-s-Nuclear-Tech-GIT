package com.hbm.render;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderPuF6Tank extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation tankModel = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/tank.obj");
	private IModelCustom tankModelC;
    private ResourceLocation tankTexture;
	
	public RenderPuF6Tank()
    {
		tankModelC = AdvancedModelLoader.loadModel(tankModel);
		tankTexture = new ResourceLocation(RefStrings.MODID, "textures/models/PUF6Tank.png");
    }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
		switch(tileEntity.getBlockMetadata())
		{
		case 4:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        bindTexture(tankTexture);
        tankModelC.renderAll();

        GL11.glPopMatrix();
    }
}
