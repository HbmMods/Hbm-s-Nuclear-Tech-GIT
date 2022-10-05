package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineChemfac;
import com.hbm.tileentity.machine.TileEntityMachineTurbineGas;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTurbineGas extends TileEntitySpecialRenderer {
	
	public RenderTurbineGas() { }

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D); //AAAAAAAAAAAAAA
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        
        TileEntityMachineTurbineGas turbinegas = (TileEntityMachineTurbineGas) tileEntity;
        
		switch(turbinegas.getBlockMetadata() - BlockDummyable.offset)
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

        bindTexture(ResourceManager.universal);
        ResourceManager.turbinegas.renderAll();

        GL11.glPopMatrix();
    }

}
