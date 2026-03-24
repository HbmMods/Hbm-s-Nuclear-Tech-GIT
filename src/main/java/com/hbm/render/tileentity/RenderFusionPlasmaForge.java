package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.fusion.TileEntityFusionPlasmaForge;
import com.hbm.util.BobMathUtil;
import com.hbm.util.Clock;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderFusionPlasmaForge extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		GL11.glRotatef(90, 0F, 1F, 0F);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
		TileEntityFusionPlasmaForge forge = (TileEntityFusionPlasmaForge) tile;
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.fusion_plasma_forge_tex);
		ResourceManager.fusion_plasma_forge.renderPart("Body");
		
		double[] striker = forge.armStriker.getPositions(interp);
		double[] jet = forge.armJet.getPositions(interp);
		double rotor = forge.prevRing + (forge.ring - forge.prevRing) * interp;
		
		GL11.glPushMatrix();
		GL11.glRotated(rotor, 0, 1, 0);
		GL11.glPushMatrix();
		ResourceManager.fusion_plasma_forge.renderPart("SliderStriker");
		GL11.glTranslated(-2.75, 2.5, 0);
		GL11.glRotated(-striker[0], 0, 0, 1);
		GL11.glTranslated(2.75, -2.5, 0);
		ResourceManager.fusion_plasma_forge.renderPart("ArmLowerStriker");
		GL11.glTranslated(-2.75, 3.75, 0);
		GL11.glRotated(-striker[1], 0, 0, 1);
		GL11.glTranslated(2.75, -3.75, 0);
		ResourceManager.fusion_plasma_forge.renderPart("ArmUpperStriker");
		GL11.glTranslated(-1.5, 3.75, 0);
		GL11.glRotated(-striker[2], 0, 0, 1);
		GL11.glTranslated(1.5, -3.75, 0);
		ResourceManager.fusion_plasma_forge.renderPart("StrikerMount");
		GL11.glPushMatrix();
		GL11.glTranslated(0, 3.375, 0.5);
		GL11.glRotated(striker[3], 1, 0, 0);
		GL11.glTranslated(0, -3.375, -0.5);
		ResourceManager.fusion_plasma_forge.renderPart("StrikerRight");
		GL11.glTranslated(0, -striker[4], 0);
		ResourceManager.fusion_plasma_forge.renderPart("PistonRight");
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(0, 3.375, -0.5);
		GL11.glRotated(-striker[3], 1, 0, 0);
		GL11.glTranslated(0, -3.375, 0.5);
		ResourceManager.fusion_plasma_forge.renderPart("StrikerLeft");
		GL11.glTranslated(0, -striker[5], 0);
		ResourceManager.fusion_plasma_forge.renderPart("PistonLeft");
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		ResourceManager.fusion_plasma_forge.renderPart("SliderJet");
		GL11.glTranslated(2.75, 2.5, 0);
		GL11.glRotated(jet[0], 0, 0, 1);
		GL11.glTranslated(-2.75, -2.5, 0);
		ResourceManager.fusion_plasma_forge.renderPart("ArmLowerJet");
		GL11.glTranslated(2.75, 3.75, 0);
		GL11.glRotated(jet[1], 0, 0, 1);
		GL11.glTranslated(-2.75, -3.75, 0);
		ResourceManager.fusion_plasma_forge.renderPart("ArmUpperJet");
		GL11.glTranslated(1.5, 3.75, 0);
		GL11.glRotated(jet[2], 0, 0, 1);
		GL11.glTranslated(-1.5, -3.75, 0);
		ResourceManager.fusion_plasma_forge.renderPart("Jet");
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		
		if(forge.plasmaEnergySync <= 0) {
			GL11.glColor3f(0F, 0F, 0F);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			ResourceManager.fusion_plasma_forge.renderPart("Plasma");
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(1F, 1F, 1F);
		} else {
			RenderArcFurnace.fullbright(true);
			long time = Clock.get_ms() + forge.timeOffset;
			float alpha = 0.5F + (float) (Math.sin(time / 500D) * 0.25F);
			double mainOsc = BobMathUtil.sps(time / 750D) % 1D;
			double glowOsc = Math.sin(time / 1000D) % 1D;
			double glowExtra = time / 10000D % 1D;
			
			GL11.glColor3f(forge.plasmaRed * alpha, forge.plasmaGreen * alpha, forge.plasmaBlue * alpha);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			bindTexture(ResourceManager.fusion_plasma_tex);
			GL11.glTranslated(0, mainOsc, 0);
			ResourceManager.fusion_plasma_forge.renderPart("Plasma");
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
			GL11.glDepthMask(false);

			GL11.glColor3f(forge.plasmaRed * 2, forge.plasmaGreen * 2, forge.plasmaBlue * 2);
			
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			bindTexture(ResourceManager.fusion_plasma_glow_tex);
			GL11.glTranslated(0, glowOsc + glowExtra, 0);
			ResourceManager.fusion_plasma_forge.renderPart("Plasma");
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			glowOsc = Math.sin(time / 600D + 2) % 1D;
			glowExtra = time / 5000D % 1D;
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			bindTexture(ResourceManager.fusion_plasma_glow_tex);
			GL11.glTranslated(0, glowOsc + glowExtra, 0);
			ResourceManager.fusion_plasma_forge.renderPart("Plasma");
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glDepthMask(true);
			
			RenderArcFurnace.fullbright(false);
		}
		GL11.glColor3f(1F, 1F, 1F);
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.fusion_plasma_forge);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			public void renderInventory() {
				GL11.glTranslated(0, -1, 0);
				GL11.glScaled(2.75, 2.75, 2.75);
				GL11.glRotated(90, 0, 1, 0);
			}
			public void renderCommon() {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotatef(90, 0F, 1F, 0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.fusion_plasma_forge_tex);
				ResourceManager.fusion_plasma_forge.renderAllExcept("Plasma");
				
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glColor3f(0F, 0F, 0F);
				ResourceManager.fusion_plasma_forge.renderPart("Plasma");
				GL11.glColor3f(1F, 1F, 1F);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
