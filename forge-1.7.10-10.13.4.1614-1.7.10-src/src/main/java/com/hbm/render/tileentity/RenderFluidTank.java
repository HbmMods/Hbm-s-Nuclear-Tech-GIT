package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderFluidTank extends TileEntitySpecialRenderer {

	private static final ResourceLocation body = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/fluidtank_main.obj");
	private static final ResourceLocation rotor = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/fluidtank_label.obj");
	private IModelCustom genModel;
	private IModelCustom rotModel;
    private ResourceLocation genTexture;
    private ResourceLocation rotTexture;
	
	public RenderFluidTank()
    {
		genModel = AdvancedModelLoader.loadModel(body);
		rotModel = AdvancedModelLoader.loadModel(rotor);
		//gadgetTexture = new ResourceLocation(RefStrings.MODID, "textures/models/TheGadget3_.png");
		genTexture = new ResourceLocation(RefStrings.MODID, "textures/models/tank.png");
		rotTexture = new ResourceLocation(RefStrings.MODID, "textures/models/tank_none.png");
    }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(90, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		}

        bindTexture(genTexture);
        
        genModel.renderAll();

        GL11.glPopMatrix();
        
        renderTileEntityAt2(tileEntity, x, y, z, f);
    }
    
	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(90, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		}

		String s = "NONE";
		if(tileEntity instanceof TileEntityMachineFluidTank)
			s = ((TileEntityMachineFluidTank)tileEntity).tank.getTankType().name();
		rotTexture = new ResourceLocation(RefStrings.MODID, "textures/models/tank_" + s + ".png");
        bindTexture(rotTexture);
        rotModel.renderAll();

        GL11.glPopMatrix();
    }
}
