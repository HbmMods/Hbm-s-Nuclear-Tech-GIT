package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineCompressorCompact;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderCompressorCompact extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		switch(tileEntity.getBlockMetadata() - 10) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityMachineCompressorCompact compressor = (TileEntityMachineCompressorCompact) tileEntity;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.compressor_compact_tex);
		ResourceManager.condenser.renderPart("Condenser");

		float rot = compressor.prevFanSpin + (compressor.fanSpin - compressor.prevFanSpin) * f;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0,1.5, 0);
		GL11.glRotatef(rot, 1, 0, 0);
		GL11.glTranslated(0, -1.5, 0);
		ResourceManager.condenser.renderPart("Fan1");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(0,1.5, 0);
		GL11.glRotatef(rot, -1, 0, 0);
		GL11.glTranslated(0, -1.5, 0);
		ResourceManager.condenser.renderPart("Fan2");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_compressor_compact);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(-1, -1, 0);
				GL11.glScaled(2.75, 2.75, 2.75);
			}
			public void renderCommon() {
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glTranslated(0.5, 0, 0);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.compressor_compact_tex); ResourceManager.condenser.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		};
	}
}
