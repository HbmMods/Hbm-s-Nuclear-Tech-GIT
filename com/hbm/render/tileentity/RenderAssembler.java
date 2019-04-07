package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderAssembler extends TileEntitySpecialRenderer {

	private static final ResourceLocation body = new ResourceLocation(RefStrings.MODID, "models/assembler_new_body.obj");
	private static final ResourceLocation cog = new ResourceLocation(RefStrings.MODID, "models/assembler_new_cog.obj");
	private static final ResourceLocation slider = new ResourceLocation(RefStrings.MODID, "models/assembler_new_slider.obj");
	private static final ResourceLocation arm = new ResourceLocation(RefStrings.MODID, "models/assembler_new_arm.obj");

	private static final IModelCustom bodyModel = AdvancedModelLoader.loadModel(body);
	private static final IModelCustom cogModel = AdvancedModelLoader.loadModel(cog);
	private static final IModelCustom sliderModel = AdvancedModelLoader.loadModel(slider);
	private static final IModelCustom armModel = AdvancedModelLoader.loadModel(arm);

    private static final ResourceLocation bodyTexture = new ResourceLocation(RefStrings.MODID, "textures/models/assembler_base_new.png");
    private static final ResourceLocation cogTexture = new ResourceLocation(RefStrings.MODID, "textures/models/assembler_cog_new.png");
    private static final ResourceLocation sliderTexture = new ResourceLocation(RefStrings.MODID, "textures/models/assembler_slider_new.png");
    private static final ResourceLocation armTexture = new ResourceLocation(RefStrings.MODID, "textures/models/assembler_arm_new.png");
	
	public RenderAssembler() { }

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
        
        renderSlider(tileEntity, x, y, z, f);
    }
    
	public void renderSlider(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
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
		

        bindTexture(sliderTexture);
        
        int offset = (int) (System.currentTimeMillis() % 5000) / 5;
        
        if(offset > 500)
        	offset = 500 - (offset - 500);
        
		GL11.glTranslated(offset * 0.003 - 0.75, 0, 0);
		
        sliderModel.renderAll();

        bindTexture(armTexture);
        
        double sway = (System.currentTimeMillis() % 2000) / 2;

        sway = Math.sin(sway / Math.PI / 50);
        
		GL11.glTranslated(0, 0, sway * 0.3);
        armModel.renderAll();

        GL11.glPopMatrix();
        
        renderCogs(tileEntity, x, y, z, f);
    }
	
	public void renderCogs(TileEntity tileEntity, double x, double y, double z, float f) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
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
		

        bindTexture(cogTexture);

        int rotation = (int) (System.currentTimeMillis() % (360 * 5)) / 5;

        GL11.glPushMatrix();
		GL11.glTranslated(-0.6, 0.75, 1.0625);
		GL11.glRotatef(-rotation, 0F, 0F, 1F);
		cogModel.renderAll();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		GL11.glTranslated(0.6, 0.75, 1.0625);
		GL11.glRotatef(rotation, 0F, 0F, 1F);
		cogModel.renderAll();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		GL11.glTranslated(-0.6, 0.75, -1.0625);
		GL11.glRotatef(-rotation, 0F, 0F, 1F);
		cogModel.renderAll();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		GL11.glTranslated(0.6, 0.75, -1.0625);
		GL11.glRotatef(rotation, 0F, 0F, 1F);
		cogModel.renderAll();
        GL11.glPopMatrix();

        GL11.glPopMatrix();
	}
}
