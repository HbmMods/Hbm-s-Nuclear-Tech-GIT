package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.fusion.TileEntityFusionMHDT;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderFusionMHDT extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.fusion_mhdt_tex);
		ResourceManager.fusion_mhdt.renderPart("Turbine");
		
		TileEntityFusionMHDT turbine = (TileEntityFusionMHDT) tile;
		
		GL11.glPushMatrix();
		float rot = (turbine.prevRotor + (turbine.rotor - turbine.prevRotor) * interp) % 15;
		GL11.glTranslated(0, 1.5, 0);
		GL11.glRotated(rot, 1, 0, 0);
		GL11.glTranslated(0, -1.5, 0);
		ResourceManager.fusion_mhdt.renderPart("Coils");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.fusion_mhdt);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(2.5, 2.5, 2.5);
				GL11.glRotated(90, 0, 1, 0);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.fusion_mhdt_tex);
				ResourceManager.fusion_mhdt.renderPart("Turbine");
				double rot = (System.currentTimeMillis() / 5) % 30D;
				rot -= 15;
				GL11.glTranslated(0, 1.5, 0);
				GL11.glRotated(rot, 1, 0, 0);
				GL11.glTranslated(0, -1.5, 0);
				ResourceManager.fusion_mhdt.renderPart("Coils");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
