package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.pile.BlockPileDevice;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.pile.TileEntityPileLoader;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderPileLoader extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		switch(te.getBlockMetadata() % 4) {
		case 0: GL11.glRotated(90, 0, 1, 0); break;
		case 1: GL11.glRotated(270, 0, 1, 0); break;
		case 2: GL11.glRotated(180, 0, 1, 0); break;
		case 3: GL11.glRotated(0, 0, 1, 0); break;
		}
		
		TileEntityPileLoader loader = (TileEntityPileLoader) te;
		double position = loader.lastLevel + (loader.level - loader.lastLevel) * interp;
		
		bindTexture(ResourceManager.pile_loader_tex);
		ResourceManager.pile_loader.renderPart("Loader");
		GL11.glPushMatrix(); {
			GL11.glTranslated(-0.1875, 0.5, 0);
			GL11.glRotated(position * 90, 0, 0, 1);
			GL11.glTranslated(0.1875, -0.5, 0);
			ResourceManager.pile_loader.renderPart("Lever");
		} GL11.glPopMatrix();
		
		GL11.glTranslated(position * -0.5, 0, 0);
		ResourceManager.pile_loader.renderPart("Slider");
		if(loader.syncStack != null) ResourceManager.pile_loader.renderPart("Rod");
		
		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.pile_device);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, 0);
				double scale = 5;
				GL11.glScaled(scale, scale, scale);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(2, 2, 2);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				if(item.getItemDamage() == BlockPileDevice.ITEM_META_LOADER) {
					bindTexture(ResourceManager.pile_loader_tex);
					ResourceManager.pile_loader.renderAll();
				}
				if(item.getItemDamage() == BlockPileDevice.ITEM_META_VENT) {
					bindTexture(ResourceManager.pile_vent_tex);
					ResourceManager.pile_vent.renderAll();
				}
				if(item.getItemDamage() == BlockPileDevice.ITEM_META_CONTROL) {
					bindTexture(ResourceManager.pile_control_tex);
					ResourceManager.pile_control.renderAll();
				}
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
