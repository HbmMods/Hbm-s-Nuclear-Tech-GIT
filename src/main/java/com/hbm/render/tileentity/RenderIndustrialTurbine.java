package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineIndustrialTurbine;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderIndustrialTurbine extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		switch(tile.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineIndustrialTurbine turbine = (TileEntityMachineIndustrialTurbine) tile;
		
		bindTexture(ResourceManager.industrial_turbine_tex);
		ResourceManager.industrial_turbine.renderPart("Turbine");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(135 - (turbine.tanks[0].getTankType().getID() - Fluids.STEAM.getID()) * 90, 0, 0, 1);
		GL11.glTranslated(0, -1.5, 0);
		ResourceManager.industrial_turbine.renderPart("Gauge");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(turbine.lastRotor + (turbine.rotor - turbine.lastRotor) * interp, 0, 0, -1);
		GL11.glTranslated(0, -1.5, 0);
		ResourceManager.industrial_turbine.renderPart("Flywheel");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_industrial_turbine);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(1, 0, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glTranslated(0.5, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.industrial_turbine_tex);
				
				ResourceManager.industrial_turbine.renderPart("Turbine");
				
				GL11.glTranslated(0, 1.5, 0);
				GL11.glRotated(135, 0, 0, 1);
				GL11.glTranslated(0, -1.5, 0);
				ResourceManager.industrial_turbine.renderPart("Gauge");
				
				double rot = (System.currentTimeMillis() / 5) % 336D;
				GL11.glTranslated(0, 1.5, 0);
				GL11.glRotated(rot, 0, 0, -1);
				GL11.glTranslated(0, -1.5, 0);
				ResourceManager.industrial_turbine.renderPart("Flywheel");
				
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		};
	}
}
