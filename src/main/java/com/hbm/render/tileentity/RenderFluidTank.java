package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.storage.TileEntityMachineFluidTank;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class RenderFluidTank extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(90, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

        bindTexture(ResourceManager.tank_tex);
		ResourceManager.fluidtank.renderPart("Tank");

		GL11.glShadeModel(GL11.GL_FLAT);
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
		
        bindTexture(new ResourceLocation(RefStrings.MODID, "textures/models/tank_" + s + ".png"));
        ResourceManager.fluidtank.renderPart("Label");

        GL11.glPopMatrix();
    }
}
