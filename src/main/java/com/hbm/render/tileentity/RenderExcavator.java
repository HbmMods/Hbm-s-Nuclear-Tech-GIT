package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineExcavator;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderExcavator extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		GL11.glTranslated(0, -((BlockDummyable) ModBlocks.machine_excavator).getHeightOffset(), 0);
		
		TileEntityMachineExcavator drill = (TileEntityMachineExcavator) tile;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.mining_drill_tex);
		ResourceManager.mining_drill.renderPart("Main");
		ResourceManager.mining_drill.renderPart("Crusher1");
		ResourceManager.mining_drill.renderPart("Crusher2");
		
		GL11.glRotatef(drill.prevDrillRotation + (drill.drillRotation - drill.prevDrillRotation) * interp, 0F, -1F, 0F);
		float ext = drill.prevDrillExtension + (drill.drillExtension - drill.prevDrillExtension) * interp;
		GL11.glTranslatef(0.0F, -ext, 0.0F);
		ResourceManager.mining_drill.renderPart("Drillbit");
		
		while(ext >= -1.5) {
			ResourceManager.mining_drill.renderPart("Shaft");
			GL11.glTranslated(0.0D, 2.0D, 0.0D);
			ext -= 2;
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_excavator);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -2, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommon() {
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.mining_drill_tex); ResourceManager.mining_drill.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
