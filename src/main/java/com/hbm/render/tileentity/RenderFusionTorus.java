package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.FusionRecipe;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.fusion.TileEntityFusionTorus;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Clock;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderFusionTorus extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		TileEntityFusionTorus torus = (TileEntityFusionTorus) tile;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.fusion_torus_tex);
		ResourceManager.fusion_torus.renderPart("Torus");
		
		GL11.glPushMatrix();
		float rot = torus.prevMagnet + (torus.magnet - torus.prevMagnet) * interp;
		GL11.glRotatef(rot, 0, 1, 0);
		ResourceManager.fusion_torus.renderPart("Magnet");
		GL11.glPopMatrix();

		if(torus.connections[0]) ResourceManager.fusion_torus.renderPart("Bolts2");
		if(torus.connections[1]) ResourceManager.fusion_torus.renderPart("Bolts4");
		if(torus.connections[2]) ResourceManager.fusion_torus.renderPart("Bolts3");
		if(torus.connections[3]) ResourceManager.fusion_torus.renderPart("Bolts1");
		
		FusionRecipe recipe = (FusionRecipe) torus.fusionModule.getRecipe();
		
		if(torus.plasmaEnergy > 0 && recipe != null) {
			
			long time = Clock.get_ms() + torus.timeOffset;
			
			float alpha = 0.35F + (float) (Math.sin(time / 1000D) * 0.25F);
			
			float r = recipe.r;
			float g = recipe.g;
			float b = recipe.b;
			
			double mainOsc = BobMathUtil.sps(time / 1000D) % 1D;
			double glowOsc = Math.sin(time / 2000D) % 1D;
			double glowExtra = time / 10000D % 1D;
			double sparkleSpin = time / 500D * -1 % 1D;
			double sparkleOsc = Math.sin(time / 1000D) * 0.5D % 1D;

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			GL11.glDisable(GL11.GL_CULL_FACE);

			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDepthMask(false);

			GL11.glColor4f(r, g, b, alpha);
			
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			bindTexture(ResourceManager.fusion_plasma_tex);
			GL11.glTranslated(0, mainOsc, 0);
			ResourceManager.fusion_torus.renderPart("Plasma");
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			
			// cost-cutting measure, don't render extra layers from more than 100m away
			if(MainRegistry.proxy.me().getDistanceSq(torus.xCoord + 0.5, torus.yCoord + 2.5, torus.zCoord + 0.5) < 100 * 100) {
	
				GL11.glColor4f(r * 2, g * 2, b * 2, alpha * 2);
				
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				bindTexture(ResourceManager.fusion_plasma_glow_tex);
				GL11.glTranslated(0, glowOsc + glowExtra, 0);
				ResourceManager.fusion_torus.renderPart("Plasma");
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
	
				GL11.glColor4f(r * 2, g * 2, b * 2, 0.75F);
				
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				bindTexture(ResourceManager.fusion_plasma_sparkle_tex);
				GL11.glTranslated(sparkleSpin, sparkleOsc, 0);
				ResourceManager.fusion_torus.renderPart("Plasma");
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
			}

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthMask(true);
			GL11.glPopAttrib();
			
			GL11.glEnable(GL11.GL_CULL_FACE);
		}
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.fusion_torus);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glScaled(2, 2, 2);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.fusion_torus_tex);
				ResourceManager.fusion_torus.renderPart("Torus");
				GL11.glPushMatrix();
				double rot = (System.currentTimeMillis() / 5 % 360);
				GL11.glRotated(rot, 0, 1, 0);
				ResourceManager.fusion_torus.renderPart("Magnet");
				GL11.glPopMatrix();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
