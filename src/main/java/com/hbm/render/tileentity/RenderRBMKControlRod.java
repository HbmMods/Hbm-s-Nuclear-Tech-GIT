package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.machine.rbmk.RBMKBase;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControl;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderRBMKControlRod extends TileEntitySpecialRenderer {
	
	private ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control.png");

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float i) {

		GL11.glPushMatrix();
		
		TileEntityRBMKControl control = (TileEntityRBMKControl)te;
		
		int offset = 1;
		
		for(int o = 1; o < 16; o++) {
			
			if(te.getWorldObj().getBlock(te.xCoord, te.yCoord + o, te.zCoord) == te.getBlockType()) {
				offset = o;
			} else {
				break;
			}
		}
		
		GL11.glTranslated(x + 0.5, y + offset, z + 0.5);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		
		if(control.getBlockType() instanceof RBMKBase) {
			bindTexture(((RBMKBase)control.getBlockType()).coverTexture);
		} else {
			bindTexture(texture);
		}
		
		double level = control.lastLevel + (control.level - control.lastLevel) * i;
		
		GL11.glTranslated(0, level, 0);
		ResourceManager.rbmk_rods_vbo.renderPart("Lid");

		GL11.glPopMatrix();
	}
}
