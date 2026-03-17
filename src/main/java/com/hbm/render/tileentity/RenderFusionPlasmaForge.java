package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

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
		
		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.fusion_plasma_forge_tex);
		ResourceManager.fusion_plasma_forge.renderPart("Body");
		
		GL11.glPushMatrix();
		GL11.glRotated(20, 0, 1, 0);
		GL11.glPushMatrix();
		ResourceManager.fusion_plasma_forge.renderPart("SliderStriker");
		GL11.glTranslated(-2.75, 2.5, 0);
		GL11.glRotated(-20, 0, 0, 1);
		GL11.glTranslated(2.75, -2.5, 0);
		ResourceManager.fusion_plasma_forge.renderPart("ArmLowerStriker");
		GL11.glTranslated(-2.75, 3.75, 0);
		GL11.glRotated(30, 0, 0, 1);
		GL11.glTranslated(2.75, -3.75, 0);
		ResourceManager.fusion_plasma_forge.renderPart("ArmUpperStriker");
		GL11.glTranslated(-1.5, 3.75, 0);
		GL11.glRotated(20, 0, 0, 1);
		GL11.glTranslated(1.5, -3.75, 0);
		ResourceManager.fusion_plasma_forge.renderPart("StrikerMount");
		GL11.glPushMatrix();
		GL11.glTranslated(0, 3.375, 0.5);
		GL11.glRotated(30, 1, 0, 0);
		GL11.glTranslated(0, -3.375, -0.5);
		ResourceManager.fusion_plasma_forge.renderPart("StrikerRight");
		GL11.glTranslated(0, -0.5, 0);
		ResourceManager.fusion_plasma_forge.renderPart("PistonRight");
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslated(0, 3.375, -0.5);
		GL11.glRotated(-30, 1, 0, 0);
		GL11.glTranslated(0, -3.375, 0.5);
		ResourceManager.fusion_plasma_forge.renderPart("StrikerLeft");
		GL11.glTranslated(0, -0.5, 0);
		ResourceManager.fusion_plasma_forge.renderPart("PistonLeft");
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		ResourceManager.fusion_plasma_forge.renderPart("SliderJet");
		GL11.glTranslated(2.75, 2.5, 0);
		GL11.glRotated(20, 0, 0, 1);
		GL11.glTranslated(-2.75, -2.5, 0);
		ResourceManager.fusion_plasma_forge.renderPart("ArmLowerJet");
		GL11.glTranslated(2.75, 3.75, 0);
		GL11.glRotated(-20, 0, 0, 1);
		GL11.glTranslated(-2.75, -3.75, 0);
		ResourceManager.fusion_plasma_forge.renderPart("ArmUpperJet");
		GL11.glTranslated(1.5, 3.75, 0);
		GL11.glRotated(-30, 0, 0, 1);
		GL11.glTranslated(-1.5, -3.75, 0);
		ResourceManager.fusion_plasma_forge.renderPart("Jet");
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0F, 0F, 0F);
		ResourceManager.fusion_plasma_forge.renderPart("Plasma");
		GL11.glColor3f(1F, 1F, 1F);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
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
