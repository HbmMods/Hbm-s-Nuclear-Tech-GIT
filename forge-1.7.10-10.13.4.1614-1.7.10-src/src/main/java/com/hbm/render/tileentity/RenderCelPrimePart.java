package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.bomb.TileEntityCelPrimeBattery;
import com.hbm.tileentity.bomb.TileEntityCelPrimePort;
import com.hbm.tileentity.bomb.TileEntityCelPrimeTanks;
import com.hbm.tileentity.bomb.TileEntityCelPrimeTerminal;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCelPrimePart extends TileEntitySpecialRenderer {
	
	public RenderCelPrimePart() { }

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        
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

		if(tileEntity instanceof TileEntityCelPrimeTerminal) {
			bindTexture(ResourceManager.universal);
        	ResourceManager.cp_terminal.renderAll();
		}
		if(tileEntity instanceof TileEntityCelPrimeBattery) {
			bindTexture(ResourceManager.universal);
        	ResourceManager.cp_battery.renderAll();
		}
		if(tileEntity instanceof TileEntityCelPrimePort) {
			bindTexture(ResourceManager.universal);
        	ResourceManager.cp_port.renderAll();
		}
		if(tileEntity instanceof TileEntityCelPrimeTanks) {
			bindTexture(ResourceManager.universal);
        	ResourceManager.cp_tanks.renderAll();
		}
        
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }
}
