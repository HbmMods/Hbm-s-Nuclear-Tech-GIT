package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityCargoElevator;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderCargoElevator extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		bindTexture(ResourceManager.cargo_elevator_tex);
		TileEntityCargoElevator elevator = (TileEntityCargoElevator) tile;
		
		if(elevator.renderPlatform) {
			double extension = elevator.prevExtension + (elevator.extension - elevator.prevExtension) * interp;
			ResourceManager.cargo_elevator.renderPart("Base");
			
			GL11.glPushMatrix(); {
				GL11.glTranslated(0, extension, 0);
				ResourceManager.cargo_elevator.renderPart("Platform");
				for(int i = 0; i < extension + 1; i++) {
					ResourceManager.cargo_elevator.renderPart("Piston");
					GL11.glTranslated(0, -1, 0);
				}
			} GL11.glPopMatrix();
		}

		GL11.glPushMatrix(); {
			for(int i = 0; i <= elevator.height; i++) {
				ResourceManager.cargo_elevator.renderPart("Guides");
				GL11.glTranslated(0, 1, 0);
			}
		} GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.cargo_elevator);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2.75, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommon() {
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.cargo_elevator_tex);
				ResourceManager.cargo_elevator.renderPart("Base");
				ResourceManager.cargo_elevator.renderPart("Piston");
				ResourceManager.cargo_elevator.renderPart("Guides");
				GL11.glTranslated(0, 1, 0);
				ResourceManager.cargo_elevator.renderPart("Piston");
				ResourceManager.cargo_elevator.renderPart("Guides");
				ResourceManager.cargo_elevator.renderPart("Platform");
				GL11.glTranslated(0, 1, 0);
				ResourceManager.cargo_elevator.renderPart("Guides");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
