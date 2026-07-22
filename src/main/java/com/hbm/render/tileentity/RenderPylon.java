package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelPylon;
import com.hbm.tileentity.network.TileEntityPylon;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderPylon extends RenderPylonBase implements IItemRendererProvider {

	private ModelPylon pylon;
	private ModelPylon pylonitem;
	
	public RenderPylon() {
		this.pylon = new ModelPylon();
		this.pylonitem = new ModelPylon();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f) {
		TileEntityPylon pyl = (TileEntityPylon)te;
		
		GL11.glEnable(GL11.GL_LIGHTING);
		
		GL11.glPushMatrix();
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F - ((1F / 16F) * 14F), (float) z + 0.5F);
			GL11.glRotatef(180, 0F, 0F, 1F);
			if(te.getBlockType() == ModBlocks.red_pylon_steel)
				bindTexture(ResourceManager.pylon_red_steel_tex);
			else
				bindTexture(ResourceManager.pylon_red_wood_tex);
			this.pylon.renderAll(0.0625F);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		this.renderLinesGeneric(pyl, x, y, z);
		GL11.glPopMatrix();
	}

	public Item[] getItemsForRenderer() {
		return new Item[] {
				Item.getItemFromBlock(ModBlocks.red_pylon),
				Item.getItemFromBlock(ModBlocks.red_pylon_steel)
		};
	}
	
	@Override
	public Item getItemForRenderer() { return Item.getItemFromBlock(ModBlocks.red_pylon); }
	
	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(1, -5, 0);
				GL11.glScaled(4.5, 4.5, 4.5);
			}
			public void renderCommonWithStack(ItemStack stack) {
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glScaled(1.0, -1.0, -1.0);
				GL11.glTranslated(0.25, -0.45, 0);
				
				if(stack.getItem() == Item.getItemFromBlock(ModBlocks.red_pylon_steel))
					bindTexture(ResourceManager.pylon_red_steel_tex);
				else
					bindTexture(ResourceManager.pylon_red_wood_tex);
				
				pylonitem.renderAll(0.0400F);
				
			}
		};
	}
}
