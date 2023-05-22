package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderDieselGen extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata()) {
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.dieselgen_tex);
		
		TileEntityMachineDiesel engine = (TileEntityMachineDiesel) tile;
		ResourceManager.dieselgen.renderPart("Generator");
		
		if(engine.hasAcceptableFuel() && engine.tank.getFill() > 0) {
			double swingSide = Math.sin(System.currentTimeMillis() / 50D) * 0.005;
			double swingFront = Math.sin(System.currentTimeMillis() / 25D) * 0.005;
			GL11.glTranslated(swingFront, 0, swingSide);
		}
		
		ResourceManager.dieselgen.renderPart("Engine");
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_diesel);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				double scale = 5;
				GL11.glScaled(scale, scale, scale);
			}
			public void renderCommon() {
				GL11.glScaled(2, 2, 2);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.dieselgen_tex);
				ResourceManager.dieselgen.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
