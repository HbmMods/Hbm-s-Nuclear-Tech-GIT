package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderCyclotron extends TileEntitySpecialRenderer {

    @Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
    	
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);

        bindTexture(ResourceManager.cyclotron_tex);
        ResourceManager.cyclotron.renderPart("Body");
        
        GL11.glShadeModel(GL11.GL_FLAT);
        
        TileEntityMachineCyclotron cyc = (TileEntityMachineCyclotron)tileEntity;
        
        boolean plugged = true;
        
        if(cyc.getPlug(0)) {
        	bindTexture(ResourceManager.cyclotron_ashes_filled);
        } else {
        	bindTexture(ResourceManager.cyclotron_ashes);
        	plugged = false;
        }
        ResourceManager.cyclotron.renderPart("B1");
        
        if(cyc.getPlug(1)) {
        	bindTexture(ResourceManager.cyclotron_book_filled);
        } else {
        	bindTexture(ResourceManager.cyclotron_book);
        	plugged = false;
        }
        ResourceManager.cyclotron.renderPart("B2");
        
        if(cyc.getPlug(2)) {
        	bindTexture(ResourceManager.cyclotron_gavel_filled);
        } else {
        	bindTexture(ResourceManager.cyclotron_gavel);
        	plugged = false;
        }
        ResourceManager.cyclotron.renderPart("B3");
        
        if(cyc.getPlug(3)) {
        	bindTexture(ResourceManager.cyclotron_coin_filled);
        } else {
        	bindTexture(ResourceManager.cyclotron_coin);
        	plugged = false;
        }
        ResourceManager.cyclotron.renderPart("B4");
        
        if(plugged) {
			
        	GL11.glPushMatrix();
            RenderHelper.enableStandardItemLighting();
			GL11.glRotated(System.currentTimeMillis() * 0.025 % 360, 0, 1, 0);
			
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	        GL11.glDisable(GL11.GL_ALPHA_TEST);
			
			String msg = "plures necat crapula quam gladius";

			GL11.glTranslated(0, 2, 0);
			GL11.glRotated(180, 1, 0, 0);
			
			float rot = 0F;
			
			//looks dumb but we'll use this technology for the cyclotron
			for(char c : msg.toCharArray()) {

				GL11.glPushMatrix();
				
				GL11.glRotatef(rot, 0, 1, 0);
				
				rot -= Minecraft.getMinecraft().fontRenderer.getCharWidth(c) * 2F;
				
				GL11.glTranslated(2.75, 0, 0);
				
				GL11.glRotatef(-90, 0, 1, 0);
				
				float scale = 0.1F;
				GL11.glScalef(scale, scale, scale);
				GL11.glDisable(GL11.GL_CULL_FACE);
				Minecraft.getMinecraft().standardGalacticFontRenderer.drawString(String.valueOf(c), 0, 0, 0x600060);
				GL11.glEnable(GL11.GL_CULL_FACE);
	    		GL11.glPopMatrix();
			}
	
	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glDisable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);

	        GL11.glPopMatrix();

			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderHelper.enableStandardItemLighting();
        }
        
        GL11.glEnable(GL11.GL_CULL_FACE);

        GL11.glPopMatrix();
    }
}
