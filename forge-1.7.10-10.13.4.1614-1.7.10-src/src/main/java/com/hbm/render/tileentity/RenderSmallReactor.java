package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSmallReactor extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		
		TileEntityMachineReactorSmall reactor = (TileEntityMachineReactorSmall)tileEntity;

        bindTexture(ResourceManager.reactor_small_base_tex);
        ResourceManager.reactor_small_base.renderAll();
        
        GL11.glTranslated(0.0D, reactor.rods / 100D, 0.0D);

        bindTexture(ResourceManager.reactor_small_rods_tex);
        ResourceManager.reactor_small_rods.renderAll();

        GL11.glPopMatrix();
    }
}
