package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.TileEntityTurretBase;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderPumpjack extends TileEntitySpecialRenderer {
	
	public RenderPumpjack() { }
	
    private ResourceLocation gadgetTexture = new ResourceLocation(RefStrings.MODID, "textures/models/TheGadget3_.png");
    
    int i = 0;

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f)
    {
    	i += 2;
    	if(i >= 360)
    		i-= 360;
    	
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
        
		this.bindTexture(gadgetTexture);
        ResourceManager.pumpjack_base.renderAll();

        GL11.glPopMatrix();
        
        renderTileEntityAt2(tileEntity, x, y, z, f);
    }
    
	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GL11.glTranslated(0, 1.5, 5.5);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		this.bindTexture(gadgetTexture);
		GL11.glRotated(i - 90, 1F, 0F, 0F);
        ResourceManager.pumpjack_rotor.renderAll();

        GL11.glPopMatrix();
        
        renderTileEntityAt3(tileEntity, x, y, z, f);
    }
    
	public void renderTileEntityAt3(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 1, z + 0.5D);
        GL11.glTranslated(0, 2.5, 2.5);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		this.bindTexture(gadgetTexture);
		float t = (float) Math.sin((i / (180 / Math.PI)))/2 * 25;
		GL11.glRotatef(t, 1F, 0F, 0F);
        ResourceManager.pumpjack_head.renderAll();

        GL11.glPopMatrix();

        renderTileEntityAt4(tileEntity, x, y, z, f);
    }
    
	public void renderTileEntityAt4(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y + 1, z + 0.5D);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);

		float t = (float) Math.sin((i / (180 / Math.PI)));
		float u = (float) Math.sin(((i + 90) / (180 / Math.PI)));
		drawConnection(0.55, 0.5 + t, -5.5 - u, 0.55, 2.5, -6);
		drawConnection(-0.55, 0.5 + t, -5.5 - u, -0.55, 2.5, -6);

        GL11.glPopMatrix();
    }
	
	public void drawConnection(double x, double y, double z, double a, double b, double c) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(5);
        tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
        tessellator.addVertex(x + 0.05F, y, z);
        tessellator.addVertex(x - 0.05F, y, z);
        tessellator.addVertex(a + 0.05F, b, c);
        tessellator.addVertex(a - 0.05F, b, c);
        tessellator.draw();
        tessellator.startDrawing(5);
        tessellator.setColorRGBA_F(0.683F, 0.089F, 0.0F, 1.0F);
        tessellator.addVertex(x, y, z + 0.05F);
        tessellator.addVertex(x, y, z - 0.05F);
        tessellator.addVertex(a, b, c + 0.05F);
        tessellator.addVertex(a, b, c - 0.05F);
        tessellator.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_CULL_FACE);
	}

}
