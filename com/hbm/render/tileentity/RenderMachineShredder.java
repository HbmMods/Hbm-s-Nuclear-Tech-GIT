package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderMachineShredder extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

        bindTexture(ResourceManager.universal);
        ResourceManager.shredder_body.renderAll();

        GL11.glPushMatrix();
        GL11.glTranslated(-0.25, 2.85, 0.4375/2);

		GL11.glRotatef((System.currentTimeMillis() / 4) % 360, 0F, 0F, -1F);
        bindTexture(ResourceManager.turbofan_blades_tex);
        ResourceManager.shredder_blade.renderAll();

        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(0.25, 2.85, 0);

		GL11.glRotatef((System.currentTimeMillis() / 4) % 360, 0F, 0F, 1F);
        bindTexture(ResourceManager.turbofan_blades_tex);
        GL11.glScaled(-1, 1, 1);
        ResourceManager.shredder_blade.renderAll();

        GL11.glPopMatrix();

        GL11.glPopMatrix();
    }
}
