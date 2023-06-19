package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.PistonInserter.TileEntityPistonInserter;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.RenderDecoItem;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderPistonInserter extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_CULL_FACE);
			
			switch(tile.getBlockMetadata()) {
			case 0: GL11.glRotatef(180, 1F, 0F, 0F); break;
			case 1: break;
			case 2: GL11.glRotatef(-90, 1F, 0F, 0F);
					GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(90, 0F, 0F, 1F);
					GL11.glRotatef(-90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(90, 1F, 0F, 0F); break;
			case 5: GL11.glRotatef(-90, 0F, 0F, 1F);
					GL11.glRotatef(90, 0F, 1F, 0F); break;
			}
			
			GL11.glTranslated(0D, -0.5, 0D); 
			
			bindTexture(ResourceManager.piston_inserter_tex);
			ResourceManager.piston_inserter.renderPart("Frame");
			
			TileEntityPistonInserter piston = (TileEntityPistonInserter)tile;
			double e = (piston.lastExtend + (piston.renderExtend - piston.lastExtend) * interp) / (double) piston.maxExtend;
			GL11.glTranslated(0, e * 0.9375D, 0);
			ResourceManager.piston_inserter.renderPart("Piston");
			
			RenderItem itemRenderer = new RenderDecoItem(this);
			itemRenderer.setRenderManager(RenderManager.instance);
			
			if(piston.slot != null) {
				ItemStack stack = piston.slot.copy();
				
				EntityItem item = new EntityItem(null, 0.0D, 0.0D, 0.0D, stack);
				item.getEntityItem().stackSize = 1;
				item.hoverStart = 0.0F;
				
				if(stack.getItem() instanceof ItemBlock) {
					GL11.glTranslated(0.0D, 1.125D, 0.0D);
				} else {
					GL11.glTranslated(0.0D, 1.0625D, 0.1D);
					if(!RenderManager.instance.options.fancyGraphics)
						GL11.glTranslated(0.0D, 0.01D, 0.0D);
					
					GL11.glRotated(90, -1, 0, 0);
				}
				
				RenderItem.renderInFrame = true;
				itemRenderer.doRender(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
				RenderItem.renderInFrame = false;
			}

		
		GL11.glPopMatrix();
	}
	
	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.piston_inserter);
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
				bindTexture(ResourceManager.piston_inserter_tex);
				ResourceManager.piston_inserter.renderAll();
			}};
	}
}
