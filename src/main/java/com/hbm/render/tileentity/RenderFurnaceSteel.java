package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityFurnaceSteel;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderFurnaceSteel extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		GL11.glRotatef(-90, 0F, 1F, 0F);
		
		bindTexture(ResourceManager.furnace_steel_tex);
		ResourceManager.furnace_steel.renderAll();
		
		TileEntityFurnaceSteel furnace = (TileEntityFurnaceSteel) tileEntity;
		
		if(furnace.wasOn) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			float col = (float )Math.sin(System.currentTimeMillis() * 0.001);
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			tess.setColorRGBA_F(0.875F + col * 0.125F, 0.625F + col * 0.375F, 0F, 0.5F);
			//tess.setColorRGBA_I(Color.HSBtoRGB(furnace.getWorldObj().getTotalWorldTime() / 50.0F, 0.5F, 0.1F) & 16777215, 255);
			tess.setBrightness(240);
			for(int i = 0; i < 4; i++) {
				tess.setNormal(1F, 0F, 0F);
				tess.addVertex(1 + i * 0.0625, 1, -1);
				tess.addVertex(1 + i * 0.0625, 1, 1);
				tess.addVertex(1 + i * 0.0625, 0.5, 1);
				tess.addVertex(1 + i * 0.0625, 0.5, -1);
			}
			tess.draw();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.furnace_steel);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -1.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.furnace_steel_tex);
				ResourceManager.furnace_steel.renderAll();
			}};
	}

}
