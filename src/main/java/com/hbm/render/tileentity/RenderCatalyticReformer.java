package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.HorsePronter;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderCatalyticReformer extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	private static ResourceLocation extra = new ResourceLocation(RefStrings.MODID, "textures/models/horse/dyx.png");
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);

		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.catalytic_reformer_tex);
		ResourceManager.catalytic_reformer.renderAll();
		GL11.glShadeModel(GL11.GL_FLAT);

		if(MainRegistry.polaroidID == 11) {
			/// rapidly spinning dicks ///
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glTranslated(-1.125, 1.375, 1);
			double s = 0.125D;
			GL11.glScaled(s, s, s);
			GL11.glRotated(System.currentTimeMillis() / 5D % 360D, 0, -1, 0);
			GL11.glTranslated(0, 0.1, -0.5);
	
			this.bindTexture(extra);
			HorsePronter.reset();
			double r = 60;
			HorsePronter.pose(HorsePronter.id_body, 0, -r, 0);
			HorsePronter.pose(HorsePronter.id_tail, 0, 45, 90);
			HorsePronter.pose(HorsePronter.id_lbl, 0, -90 + r, 35);
			HorsePronter.pose(HorsePronter.id_rbl, 0, -90 + r, -35);
			HorsePronter.pose(HorsePronter.id_lfl, 0, r - 10, 5);
			HorsePronter.pose(HorsePronter.id_rfl, 0, r - 10, -5);
			HorsePronter.pose(HorsePronter.id_head, 0, r, 0);
			HorsePronter.enableHorn();
			HorsePronter.enableWings();
			HorsePronter.pront();
			
			ItemStack stack = new ItemStack(ModItems.cigarette);
			double scale = 0.25;
			GL11.glTranslated(0.02, 1.13, -0.42);
			GL11.glScaled(scale, scale, scale);
			GL11.glRotated(90, 0, -1, 0);
			GL11.glRotated(60, 0, 0, -1);
			bindTexture(TextureMap.locationItemsTexture);
			IIcon icon = stack.getIconIndex();
			ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		}

		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_catalytic_reformer);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(3.5, 3.5, 3.5);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.catalytic_reformer_tex);
				ResourceManager.catalytic_reformer.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
