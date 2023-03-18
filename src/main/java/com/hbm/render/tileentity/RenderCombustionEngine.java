package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids.CD_Canister;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineCombustionEngine;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderCombustionEngine extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(-0.5, 0, 3);
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.combustion_engine_tex);
		ResourceManager.combustion_engine.renderPart("Engine");
		
		TileEntityMachineCombustionEngine engine = (TileEntityMachineCombustionEngine) tile;
		CD_Canister canister = engine.tank.getTankType().getContainer(CD_Canister.class);
		
		if(canister != null) {
			int color = canister.color;
			float r = ((color & 0xff0000) >> 16) / 256F;
			float g = ((color & 0x00ff00) >> 8) / 256F;
			float b = ((color & 0x0000ff) >> 0) / 256F;
			GL11.glColor4f(r, g, b, 1F);
		}
		ResourceManager.combustion_engine.renderPart("Canister");
		GL11.glColor4f(1F, 1F, 1F, 1F);

		GL11.glTranslated(1, 0, -2.6875);
		GL11.glRotated(engine.prevDoorAngle + (engine.doorAngle - engine.prevDoorAngle) * interp, 0, -1, 0);
		GL11.glTranslated(-1, 0, 2.6875);
		ResourceManager.combustion_engine.renderPart("Hatch");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_combustion_engine);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2.75, 2.75, 2.75);
			}
			public void renderCommon() {
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glTranslated(0, 0, 2.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.combustion_engine_tex);
				ResourceManager.combustion_engine.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
