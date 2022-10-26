package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityHeatBoiler;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderBoiler extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.boiler_tex);
		TileEntityHeatBoiler boiler = (TileEntityHeatBoiler) tile;
		if(!boiler.hasExploded) {
			
			if(boiler.tanks[1].getFill() > boiler.tanks[1].getMaxFill() * 0.9) {
				double sine = Math.sin(System.currentTimeMillis() / 50D % (Math.PI * 2));
				sine *= 0.01D;
				GL11.glScaled(1 - sine, 1 + sine, 1 - sine);
			}
			
			GL11.glEnable(GL11.GL_CULL_FACE);
			ResourceManager.boiler.renderAll();
		} else {
			GL11.glDisable(GL11.GL_CULL_FACE);
			ResourceManager.boiler_burst.renderAll();
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_boiler);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(3, 3, 3);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.boiler_tex);
				if(item.getItemDamage() == 1)
					ResourceManager.boiler_burst.renderAll();
				else
					ResourceManager.boiler.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
