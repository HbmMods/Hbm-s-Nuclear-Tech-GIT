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

        bindTexture(ResourceManager.assembler_body_tex);
        
        ResourceManager.assembler_body.renderAll();

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
		

        bindTexture(ResourceManager.assembler_slider_tex);
        
        int offset = (int) (System.currentTimeMillis() % 5000) / 5;
        
        if(offset > 500)
        	offset = 500 - (offset - 500);
        
        TileEntityMachineAssembler assembler = (TileEntityMachineAssembler) tileEntity;
        
        if(assembler.isProgressing)
        	GL11.glTranslated(offset * 0.003 - 0.75, 0, 0);
		
        ResourceManager.assembler_slider.renderAll();

        bindTexture(ResourceManager.assembler_arm_tex);
        
        double sway = (System.currentTimeMillis() % 2000) / 2;

        sway = Math.sin(sway / Math.PI / 50);

        if(assembler.isProgressing)
        	GL11.glTranslated(0, 0, sway * 0.3);
        ResourceManager.assembler_arm.renderAll();

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
		

        bindTexture(ResourceManager.assembler_cog_tex);

        int rotation = (int) (System.currentTimeMillis() % (360 * 5)) / 5;
        
        TileEntityMachineAssembler assembler = (TileEntityMachineAssembler) tileEntity;

        if(!assembler.isProgressing)
        	rotation = 0;
        
        GL11.glPushMatrix();
		GL11.glTranslated(-0.6, 0.75, 1.0625);
		GL11.glRotatef(-rotation, 0F, 0F, 1F);
		ResourceManager.assembler_cog.renderAll();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		GL11.glTranslated(0.6, 0.75, 1.0625);
		GL11.glRotatef(rotation, 0F, 0F, 1F);
		ResourceManager.assembler_cog.renderAll();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		GL11.glTranslated(-0.6, 0.75, -1.0625);
		GL11.glRotatef(-rotation, 0F, 0F, 1F);
		ResourceManager.assembler_cog.renderAll();
        GL11.glPopMatrix();
        
        GL11.glPushMatrix();
		GL11.glTranslated(0.6, 0.75, -1.0625);
		GL11.glRotatef(rotation, 0F, 0F, 1F);
		ResourceManager.assembler_cog.renderAll();
        GL11.glPopMatrix();

        GL11.glPopMatrix();
	}
}
