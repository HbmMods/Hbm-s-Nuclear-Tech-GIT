package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.render.util.DiamondPronter;
import com.hbm.render.util.EnumSymbol;
import com.hbm.lib.Library;
import com.hbm.main.ResourceManager;
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

			TileEntityBarrel barrel = (TileEntityBarrel) te;
			FluidType type = barrel.tank.getTankType();

			if(type != Fluids.NONE) {

				GL11.glPushMatrix();

				int poison = type.poison;
				int flammability = type.flammability;
				int reactivity = type.reactivity;
				EnumSymbol symbol = type.symbol;

				int cx = te.xCoord;
				int cy = te.yCoord;
				int cz = te.zCoord;

				if(te.getBlockType() == ModBlocks.barrel_plastic) bindTexture(ResourceManager.barrel_plastic_tex);
				else if(te.getBlockType() == ModBlocks.barrel_steel) bindTexture(ResourceManager.barrel_steel_tex);
				else if(te.getBlockType() == ModBlocks.barrel_tcalloy) bindTexture(ResourceManager.barrel_tcalloy_tex);
				else if(te.getBlockType() == ModBlocks.barrel_antimatter) bindTexture(ResourceManager.barrel_antimatter_tex);

				if(Library.canConnectFluid(te.getWorldObj(), cx + 1, cy, cz, Library.POS_X, type)) {
					GL11.glTranslatef(0.0F, -0.5F, 0.0F);
					GL11.glRotatef(0F, 0F, 0F, 0F);
					ResourceManager.barrel.renderPart("Connector");
					GL11.glTranslatef(0.0F, 0.5F, 0.0F);
				}

				if(Library.canConnectFluid(te.getWorldObj(), cx - 1, cy, cz, Library.NEG_X, type)) {
					GL11.glRotatef(180, 0F, 1F, 0F);
					GL11.glTranslatef(0.0F, -0.5F, 0.0F);
					ResourceManager.barrel.renderPart("Connector");
					GL11.glTranslatef(0.0F, 0.5F, 0.0F);
					GL11.glRotatef(-180, 0F, 1F, 0F);
				}

				if(Library.canConnectFluid(te.getWorldObj(), cx, cy, cz - 1, Library.NEG_Z, type)) {
					GL11.glRotatef(90, 0F, 1F, 0F);
					GL11.glTranslatef(0.0F, -0.5F, 0.0F);
					ResourceManager.barrel.renderPart("Connector");
					GL11.glTranslatef(0.0F, 0.5F, 0.0F);
					GL11.glRotatef(-90, 0F, 1F, 0F);
				}

				if(Library.canConnectFluid(te.getWorldObj(), cx, cy, cz + 1, Library.POS_Z, type)) {
					GL11.glRotatef(-90, 0F, 1F, 0F);
					GL11.glTranslatef(0.0F, -0.5F, 0.0F);
					ResourceManager.barrel.renderPart("Connector");
					GL11.glTranslatef(0.0F, 0.5F, 0.0F);
					GL11.glRotatef(90, 0F, 1F, 0F);
				}

				RenderHelper.disableStandardItemLighting();

				for(int j = 0; j < 4; j++) {

					GL11.glPushMatrix();
					GL11.glTranslated(0.4, 0.30, -0.24);
					GL11.glScalef(1.0F, 0.25F, 0.25F);
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
