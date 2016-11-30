package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderBombMultiLarge extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation bombModel = new ResourceLocation(RefStrings.MODID, "models/BombGenericLarge.obj");
	private IModelCustom bombModelC;
    private ResourceLocation bombTexture;
	
	public RenderBombMultiLarge()
    {
		bombModelC = AdvancedModelLoader.loadModel(bombModel);
		bombTexture = new ResourceLocation(RefStrings.MODID, "textures/models/BombGenericLargeLayout.png");
    }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
		switch(tileEntity.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        bindTexture(bombTexture);
        bombModelC.renderAll();

        GL11.glPopMatrix();
    }
}
