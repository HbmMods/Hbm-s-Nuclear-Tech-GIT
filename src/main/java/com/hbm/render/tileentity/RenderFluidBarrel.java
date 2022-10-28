package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.render.util.DiamondPronter;
import com.hbm.render.util.EnumSymbol;
import com.hbm.tileentity.machine.storage.TileEntityBarrel;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderFluidBarrel extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float i) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		if(te instanceof TileEntityBarrel) {
			
			TileEntityBarrel barrel = (TileEntityBarrel)te;
			FluidType type = barrel.tank.getTankType();
			
			if(type != Fluids.NONE) {
				
				RenderHelper.disableStandardItemLighting();
				GL11.glPushMatrix();
				
				int poison = type.poison;
				int flammability = type.flammability;
				int reactivity = type.reactivity;
				EnumSymbol symbol = type.symbol;
				
				for(int j = 0; j < 4; j++) {
					
					GL11.glPushMatrix();
					GL11.glTranslated(0.4, 0.25, -0.15);
					GL11.glScalef(1.0F, 0.35F, 0.35F);
					DiamondPronter.pront(poison, flammability, reactivity, symbol);
					GL11.glPopMatrix();
					GL11.glRotatef(90, 0, 1, 0);
				}
				
				GL11.glPopMatrix();
				RenderHelper.enableStandardItemLighting();
			}
			
		}
		
		GL11.glPopMatrix();
	}

}
