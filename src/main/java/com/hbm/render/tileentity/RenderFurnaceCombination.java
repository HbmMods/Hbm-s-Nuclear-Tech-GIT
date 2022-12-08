package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityFurnaceCombination;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class RenderFurnaceCombination extends TileEntitySpecialRenderer implements IItemRendererProvider {

	public static final ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/particle/rbmk_fire.png");

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		bindTexture(ResourceManager.combination_oven_tex);
		ResourceManager.combination_oven.renderAll();
		
		TileEntityFurnaceCombination furnace = (TileEntityFurnaceCombination) tileEntity;
		
		if(furnace.wasOn) {

			bindTexture(texture);

			GL11.glPushMatrix();
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GEQUAL, 0);
			GL11.glDepthMask(false);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			RenderHelper.disableStandardItemLighting();
			
			int texIndex = (int) (furnace.getWorldObj().getTotalWorldTime() / 2 % 14);
			float f0 = 1F / 14F;

			float uMin = texIndex % 5 * f0;
			float uMax = uMin + f0;
			float vMin = 0;
			float vMax = 1;
			
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			
			tess.setNormal(0.0F, 1.0F, 0.0F);
			tess.setBrightness(240);
			tess.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
			
			GL11.glTranslated(0, 1.75, 0);
			GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);

			double scaleH = 1;
			double scaleV = 3;

			tess.addVertexWithUV(-scaleH, 0, 0, uMax, vMax);
			tess.addVertexWithUV(-scaleH, scaleV, 0, uMax, vMin);
			tess.addVertexWithUV(scaleH, scaleV, 0, uMin, vMin);
			tess.addVertexWithUV(scaleH, 0, 0, uMin, vMax);
			
			tess.draw();

			//GL11.glPolygonOffset(0.0F, 0.0F);
			GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glDepthMask(true);
			GL11.glPopMatrix();
			RenderHelper.enableStandardItemLighting();
		}
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.furnace_combination);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -1.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommon() {
				bindTexture(ResourceManager.combination_oven_tex);
				ResourceManager.combination_oven.renderAll();
			}};
	}
}
