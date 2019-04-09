package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.HmfController;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderChemplant extends TileEntitySpecialRenderer {

	private static final ResourceLocation body = new ResourceLocation(RefStrings.MODID, "models/chemplant_new_body.obj");
	private static final ResourceLocation spinner = new ResourceLocation(RefStrings.MODID, "models/chemplant_new_spinner.obj");
	private static final ResourceLocation piston = new ResourceLocation(RefStrings.MODID, "models/chemplant_new_piston.obj");
	private static final ResourceLocation fluid = new ResourceLocation(RefStrings.MODID, "models/chemplant_new_fluid.hmf");
	private static final ResourceLocation fluidcap = new ResourceLocation(RefStrings.MODID, "models/chemplant_new_fluidcap.hmf");

	private static final IModelCustom bodyModel = AdvancedModelLoader.loadModel(body);
	private static final IModelCustom spinnerModel = AdvancedModelLoader.loadModel(spinner);
	private static final IModelCustom pistonModel = AdvancedModelLoader.loadModel(piston);
	private static final IModelCustom fluidModel = AdvancedModelLoader.loadModel(fluid);
	private static final IModelCustom fluidcapModel = AdvancedModelLoader.loadModel(fluidcap);
	
    private static final ResourceLocation bodyTexture = new ResourceLocation(RefStrings.MODID, "textures/models/chemplant_base_new.png");
    private static final ResourceLocation spinnerTexture = new ResourceLocation(RefStrings.MODID, "textures/models/chemplant_spinner_new.png");
    private static final ResourceLocation pistonTexture = new ResourceLocation(RefStrings.MODID, "textures/models/chemplant_piston_new.png");
    private static final ResourceLocation fluidTexture = new ResourceLocation(RefStrings.MODID, "textures/models/lavabase_small.png");
	
	public RenderChemplant() { }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(180, 0F, 1F, 0F);
	        GL11.glTranslated(0.5D, 0.0D, -0.5D); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F);
	        GL11.glTranslated(0.5D, 0.0D, -0.5D); break;
		case 3:
			GL11.glRotatef(0, 0F, 1F, 0F);
	        GL11.glTranslated(0.5D, 0.0D, -0.5D); break;
		case 5:
			GL11.glRotatef(90, 0F, 1F, 0F);
	        GL11.glTranslated(0.5D, 0.0D, -0.5D); break;
		}

        bindTexture(bodyTexture);
        
        bodyModel.renderAll();

        GL11.glPopMatrix();
        
        renderExtras(tileEntity, x, y, z, f);
    }
	
	public void renderExtras(TileEntity tileEntity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		TileEntityMachineChemplant chem = (TileEntityMachineChemplant)tileEntity;
		switch(chem.getBlockMetadata())
		{
		case 2:
			GL11.glTranslated(-1, 0, 0);
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3:
			GL11.glTranslated(0, 0, -1);
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5:
			GL11.glTranslated(-1, 0, -1);
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		

        bindTexture(spinnerTexture);

        int rotation = (int) (System.currentTimeMillis() % (360 * 5)) / 5;

        GL11.glPushMatrix();
		GL11.glTranslated(-0.625, 0, 0.625);
		
		if(!chem.tanks[0].getTankType().name().equals(FluidType.NONE.name()) && chem.isProgressing)
			GL11.glRotatef(-rotation, 0F, 1F, 0F);
		else
			GL11.glRotatef(-45, 0F, 1F, 0F);
		
		spinnerModel.renderAll();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		GL11.glTranslated(0.625, 0, 0.625);
		
		if(!chem.tanks[1].getTankType().name().equals(FluidType.NONE.name()) && chem.isProgressing)
			GL11.glRotatef(rotation, 0F, 1F, 0F);
		else
			GL11.glRotatef(45, 0F, 1F, 0F);
		
		spinnerModel.renderAll();
        GL11.glPopMatrix();

        double push = Math.sin((System.currentTimeMillis() % 2000) / 1000D * Math.PI) * 0.25 - 0.25;

        bindTexture(pistonTexture);
        
        GL11.glPushMatrix();
        
        if(chem.isProgressing)
        	GL11.glTranslated(0, push, 0);
		else
        	GL11.glTranslated(0, -0.25, 0);
        
		pistonModel.renderAll();
        GL11.glPopMatrix();

        bindTexture(fluidTexture);
        int color = 0;

        GL11.glDisable(GL11.GL_LIGHTING);
        if(!chem.tanks[0].getTankType().name().equals(FluidType.NONE.name())) {
            GL11.glPushMatrix();
	        
	        if(chem.isProgressing)
	        	HmfController.setMod(50000D, -250D);
	        else
	        	HmfController.setMod(50000D, -50000D);
	        
	        color = chem.tanks[0].getTankType().getColor();
			GL11.glColor3ub((byte)((color & 0xFF0000) >> 16), (byte)((color & 0x00FF00) >> 8), (byte)((color & 0x0000FF) >> 0));
			GL11.glTranslated(-0.625, 0, 0.625);
	        
			int count = chem.tanks[0].getFill() / 1000;
	        for(int i = 0; i < count; i++) {
	        	
	        	if(i < count - 1)
	        		fluidModel.renderAll();
	        	else
	        		fluidcapModel.renderAll();
				GL11.glTranslated(0, 0.125, 0);
	        }
	        GL11.glPopMatrix();
        }

        if(!chem.tanks[1].getTankType().name().equals(FluidType.NONE.name())) {
	        GL11.glPushMatrix();
	        
	        if(chem.isProgressing)
	        	HmfController.setMod(50000D, 250D);
	        else
	        	HmfController.setMod(50000D, 50000D);
	        
	        color = chem.tanks[1].getTankType().getColor();
			GL11.glColor3ub((byte)((color & 0xFF0000) >> 16), (byte)((color & 0x00FF00) >> 8), (byte)((color & 0x0000FF) >> 0));
			GL11.glTranslated(0.625, 0, 0.625);

			int count = chem.tanks[1].getFill() / 1000;
	        for(int i = 0; i < count; i++) {
	        	
	        	if(i < count - 1)
	        		fluidModel.renderAll();
	        	else
	        		fluidcapModel.renderAll();
				GL11.glTranslated(0, 0.125, 0);
	        }
	        GL11.glPopMatrix();
        }
        GL11.glEnable(GL11.GL_LIGHTING);
        
        HmfController.resetMod();

        GL11.glPopMatrix();
	}
}
