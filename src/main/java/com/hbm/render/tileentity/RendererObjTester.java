package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RendererObjTester extends TileEntitySpecialRenderer {
	
	private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/TestObj.obj");
	//private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Prototype.obj");
	private IModelCustom objTesterModel;
    private ResourceLocation objTesterTexture;
	
	public RendererObjTester()
    {
		objTesterModel = AdvancedModelLoader.loadModel(objTesterModelRL);
		objTesterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/TestObj.png");
		//objTesterTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Prototype.png");
    }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 3, z + 0.5D);
        GL11.glRotatef(180, 0F, 0F, 1F);
		switch(tileEntity.getBlockMetadata())
		{
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        bindTexture(objTesterTexture);
        objTesterModel.renderAll();

        GL11.glPopMatrix();
    }

}