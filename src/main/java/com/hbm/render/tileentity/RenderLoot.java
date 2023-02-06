package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockLoot.TileEntityLoot;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelFatman;
import com.hbm.render.model.ModelLeverAction;
import com.hbm.util.Tuple.Quartet;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class RenderLoot extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glEnable(GL11.GL_LIGHTING);
		RenderHelper.enableStandardItemLighting();
		
		TileEntityLoot loot = (TileEntityLoot) te;
		
		for(Quartet<ItemStack, Double, Double, Double> item : loot.items) {
			
			ItemStack stack = item.getW();

			GL11.glPushMatrix();
			GL11.glTranslated(item.getX(), item.getY(), item.getZ());
			
			if(stack.getItem() == ModItems.ammo_nuke) {
				renderNuke();
				
			} else if(stack.getItem() == ModItems.gun_fatman || stack.getItem() == ModItems.gun_proto || stack.getItem() == ModItems.gun_mirv) {
				renderLauncher();
				
			} else if(stack.getItem() == ModItems.gun_lever_action) {
				renderShotgun();
				
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

	protected ModelFatman launcher;
	private void renderLauncher() {
		
		if(launcher == null)
			launcher = new ModelFatman();
		
		GL11.glRotated(180, 1, 0, 0);
		GL11.glRotated(3, 0, 0, 1);
		GL11.glTranslated(0.5, -0.3751, -0.625);
		
		bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/FatmanLauncher.png"));
		launcher.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F, new ItemStack(ModItems.gun_fatman));
	}

	protected ModelLeverAction shotgun;
	private void renderShotgun() {
		
		if(shotgun == null)
			shotgun = new ModelLeverAction();

		GL11.glScaled(0.25, 0.25, 0.25);
		GL11.glTranslated(3, 0.0625, 2);
		GL11.glRotated(-25, 0, 1, 0);
		GL11.glRotated(90, 1, 0, 0);
		bindTexture(new ResourceLocation(RefStrings.MODID +":textures/models/ModelLeverAction.png"));
		shotgun.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F);
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
