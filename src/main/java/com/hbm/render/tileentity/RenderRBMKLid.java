package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBoiler;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKHeater;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderRBMKLid extends TileEntitySpecialRenderer {

	private ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_blank.png");
	private ResourceLocation texture_glass = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_blank_glass.png");
	private static final ResourceLocation texture_rods = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_element.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float i) {
		
		TileEntityRBMKBase control = (TileEntityRBMKBase)te;
		boolean hasRod = false;
		boolean cherenkov = false;
		
		if(te instanceof TileEntityRBMKRod) {
			
			TileEntityRBMKRod rod = (TileEntityRBMKRod) te;
			
			if(rod.hasRod)
				hasRod = true;
			
			if(rod.lastFluxQuantity > 5)
				cherenkov = true;
		}

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		int offset = 1;
		
		for(int o = 1; o < 16; o++) {
			
			if(te.getWorldObj().getBlock(te.xCoord, te.yCoord + o, te.zCoord) == te.getBlockType()) {
				offset = o;
				
				int meta = te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord + o, te.zCoord);
				
				if(meta > 5 && meta < 12)
					break;
				
			} else {
				break;
			}
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		if(control.hasLid()) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, offset, 0);
			
			int meta = control.getBlockMetadata() - RBMKBase.offset;
			
			if(meta == RBMKBase.DIR_GLASS_LID.ordinal()) {
				bindTexture(texture_glass);
			} else {
				if(control.getBlockType() instanceof RBMKBase) {
					bindTexture(((RBMKBase)control.getBlockType()).coverTexture);
				} else {
					bindTexture(texture);
				}

				cherenkov = false;
			}
			
			if((control instanceof TileEntityRBMKBoiler || control instanceof TileEntityRBMKHeater) && meta != RBMKBase.DIR_GLASS_LID.ordinal()) {
				ResourceManager.rbmk_rods_vbo.renderPart("Lid");
			} else {
				ResourceManager.rbmk_element_vbo.renderPart("Lid");
			}

			GL11.glPopMatrix();
		}
		
		if(hasRod) {
			GL11.glPushMatrix();
			bindTexture(texture_rods);
			
			for(int j = 0; j <= offset; j++) {
				ResourceManager.rbmk_element_vbo.renderPart("Rods");
				GL11.glTranslated(0, 1, 0);
			}

			GL11.glPopMatrix();
		}
		
		if(cherenkov) {
			GL11.glTranslated(0, 0.75, 0);

			GL11.glDisable(GL11.GL_CULL_FACE);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			tess.setColorRGBA_F(0.4F, 0.9F, 1.0F, 0.1F);
			
			for(double j = 0; j <= offset; j += 0.25) {
				tess.addVertex(-0.5, j, -0.5);
				tess.addVertex(-0.5, j, 0.5);
				tess.addVertex(0.5, j, 0.5);
				tess.addVertex(0.5, j, -0.5);
			}
			tess.draw();

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);
		}

		GL11.glPopMatrix();
	}
}
