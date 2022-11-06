package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.storage.TileEntityFileCabinet;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderFileCabinet extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata() >> 2) { //rotation
		case 0:
			GL11.glRotatef(180, 0F, 1F, 0F);
			break;
		case 1:
			GL11.glRotatef(0, 0F, 1F, 0F);
			break;
		case 2:
			GL11.glRotatef(270, 0F, 1F, 0F);
			break;
		case 3:
			GL11.glRotatef(90, 0F, 1F, 0F);
			break;
		}
		
		switch(tile.getBlockMetadata() & 3) {
		default:
			bindTexture(ResourceManager.file_cabinet_tex); break;
		case 1:
			bindTexture(ResourceManager.file_cabinet_steel_tex); //sadge
		}
		
		TileEntityFileCabinet cabinet = (TileEntityFileCabinet) tile;
		
		ResourceManager.file_cabinet.renderPart("Cabinet");
		
		GL11.glPushMatrix();
		float lower = cabinet.prevLowerExtent + (cabinet.lowerExtent - cabinet.prevLowerExtent) * interp;
		GL11.glTranslated(0F, 0F, 0.6875F * lower);
		ResourceManager.file_cabinet.renderPart("LowerDrawer");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		float upper = cabinet.prevUpperExtent + (cabinet.upperExtent - cabinet.prevUpperExtent) * interp;
		GL11.glTranslated(0F, 0F, 0.6875F * upper);
		ResourceManager.file_cabinet.renderPart("UpperDrawer");
		GL11.glPopMatrix();
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.filing_cabinet);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(-1D, 0.5D, -1D);
				GL11.glRotatef(180F, 0, 1F, 0);
				GL11.glScalef(4F, 4F, 4F);
			}
			public void renderCommonWithStack(ItemStack stack) {
				GL11.glTranslated(0, -1.25D, 0);
				GL11.glScaled(2.75D, 2.75D, 2.75D);
				
				switch(stack.getItemDamage()) {
				default:
					bindTexture(ResourceManager.file_cabinet_tex); break;
				case 1:
					bindTexture(ResourceManager.file_cabinet_steel_tex);
				}
				
				ResourceManager.file_cabinet.renderAll();
			}};
	}
}
