package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockLoot.TileEntityLoot;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.render.util.RenderItemStack;
import com.hbm.util.Tuple.Quartet;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

public class RenderLoot extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		TileEntityLoot loot = (TileEntityLoot) te;
		
		for(Quartet<ItemStack, Double, Double, Double> item : loot.items) {
			
			ItemStack stack = item.getW();

			GL11.glPushMatrix();
			GL11.glTranslated(item.getX(), item.getY(), item.getZ());
			
			if(stack.getItem() == ModItems.ammo_nuke || stack.getItem() == ModItems.ammo_nuke_low || stack.getItem() == ModItems.ammo_nuke_high || stack.getItem() == ModItems.ammo_nuke_safe) {
				renderNuke();
			} else {
				renderStandardItem(item.getW());
			}
			
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
	
	private void renderNuke() {
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glTranslated(1, 0.5, 1);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.mini_nuke_tex);
		ResourceManager.projectiles.renderPart("MiniNuke");
		GL11.glShadeModel(GL11.GL_FLAT);
	}
	
	private void renderStandardItem(ItemStack stack) {
		GL11.glTranslated(0.25, 0, 0.25);
		GL11.glScaled(0.5, 0.5, 0.5);
		GL11.glRotated(90, 1, 0, 0);
		
		bindTexture(TextureMap.locationItemsTexture);
		IIcon icon = stack.getIconIndex();
		float f14 = icon.getMinU();
		float f15 = icon.getMaxU();
		float f4 = icon.getMinV();
		float f5 = icon.getMaxV();
		ItemRenderer.renderItemIn2D(Tessellator.instance, f15, f4, f14, f5, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
	}
}
