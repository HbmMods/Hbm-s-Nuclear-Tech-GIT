package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.gui.GUIMachineRadarNT;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineRadarScreen;

import api.hbm.entity.RadarEntry;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderRadarScreen extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}

		TileEntityMachineRadarScreen screen = (TileEntityMachineRadarScreen) tileEntity;

		bindTexture(ResourceManager.radar_screen_tex);
		ResourceManager.radar_screen.renderAll();

		bindTexture(GUIMachineRadarNT.texture);
		Tessellator tess = Tessellator.instance;
		
		if(screen.linked) {
			GL11.glDepthMask(false);
			tess.startDrawingQuads();
			
			double offset = ((tileEntity.getWorldObj().getTotalWorldTime() % 56) + f) / 30D;
			tess.setColorRGBA_I(0x00ff00, 0);
			tess.addVertex(0.38, 2 - offset, 1.375);
			tess.addVertex(0.38, 2 - offset, -0.375);
			tess.setColorRGBA_I(0x00ff00, 50);
			tess.addVertex(0.38, 2 - offset - 0.125, -0.375);
			tess.addVertex(0.38, 2 - offset - 0.125, 1.375);
	
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			tess.draw();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glShadeModel(GL11.GL_FLAT);
	
			if(!screen.entries.isEmpty()) {
				tess.startDrawingQuads();
				tess.setNormal(0F, 1F, 0F);
				
				for(RadarEntry entry : screen.entries) {
	
					double sX = (entry.posX - screen.refX) / ((double) screen.range + 1) * (0.875D);
					double sZ = (entry.posZ - screen.refZ) / ((double) screen.range + 1) * (0.875D);
					double size = 0.0625D;
					tess.addVertexWithUV(0.38, 1 - sZ + size, 0.5 - sX + size, 216D / 256D, (entry.blipLevel * 8F + 8F) / 256F);
					tess.addVertexWithUV(0.38, 1 - sZ + size, 0.5 - sX - size, 224D / 256D, (entry.blipLevel * 8F + 8F) / 256F);
					tess.addVertexWithUV(0.38, 1 - sZ - size, 0.5 - sX - size, 224D / 256D, entry.blipLevel * 8F / 256F);
					tess.addVertexWithUV(0.38, 1 - sZ - size, 0.5 - sX + size, 216D / 256D, entry.blipLevel * 8F / 256F);
				}
				tess.draw();
			}
			GL11.glDepthMask(true);
		} else {
			int offset = 118 + tileEntity.getWorldObj().rand.nextInt(81);
			tess.startDrawingQuads();
			tess.setColorOpaque_I(0xffffff);
			tess.setNormal(0F, 1F, 0F);
			tess.addVertexWithUV(0.38, 1.875, 1.375, 216D / 256D, (offset + 40F) / 256F);
			tess.addVertexWithUV(0.38, 1.875, -0.375, 256D / 256D, (offset + 40F) / 256F);
			tess.addVertexWithUV(0.38, 0.125, -0.375, 256D / 256D, offset / 256F);
			tess.addVertexWithUV(0.38, 0.125, 1.375, 216D / 256D, offset / 256F);
			tess.draw();
		}

		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.radar_screen);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(5.5, 5.5, 5.5);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glTranslated(0, 0, -0.5);
				bindTexture(ResourceManager.radar_screen_tex);
				ResourceManager.radar_screen.renderAll();
			}};
	}
}
