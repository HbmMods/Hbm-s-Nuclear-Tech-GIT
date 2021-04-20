package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControl;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderRBMKControlRod extends TileEntitySpecialRenderer {
	
	private ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/blocks/rbmk/rbmk_control.png");
	
	public RenderRBMKControlRod(String texture) {
		this.texture = new ResourceLocation(texture);
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float i) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 3, z + 0.5);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		TileEntityRBMKControl control = (TileEntityRBMKControl)te;
		
		bindTexture(texture);
		
		double level = control.lastLevel + (control.level - control.lastLevel) * i;
		
		GL11.glTranslated(0, level, 0);
		ResourceManager.rbmk_rods.renderPart("Lid");

		GL11.glPopMatrix();
	}
}
