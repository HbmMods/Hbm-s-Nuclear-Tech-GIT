package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineAutosaw;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderAutosaw extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);

		TileEntityMachineAutosaw saw = (TileEntityMachineAutosaw) tile;

		double turn = saw.prevRotationYaw + (saw.rotationYaw - saw.prevRotationYaw) * interp;
		double angle = 80 - (saw.prevRotationPitch + (saw.rotationPitch - saw.prevRotationPitch) * interp);
		float spin = saw.lastSpin + (saw.spin - saw.lastSpin) * interp;
		double engine = saw.isOn ? Math.sin(saw.getWorldObj().getTotalWorldTime() * 2 % (Math.PI * 2) + interp) : 0;
		renderCommon(turn, angle, spin, engine);
		
		GL11.glPopMatrix();
	}
	
	private void renderCommon(double turn, double angle, double spin, double engine) {
		
		bindTexture(ResourceManager.autosaw_tex);
		ResourceManager.autosaw.renderPart("Base");

		GL11.glRotated(turn, 0, -1, 0);
		ResourceManager.autosaw.renderPart("Main");
		GL11.glPushMatrix();
		GL11.glTranslated(0, engine * 0.01, 0);
		ResourceManager.autosaw.renderPart("Engine");
		GL11.glPopMatrix();

		GL11.glTranslated(0, 1.75, 0);
		GL11.glRotated(angle, 1, 0, 0);
		GL11.glTranslated(0, -1.75, 0);
		ResourceManager.autosaw.renderPart("ArmUpper");

		GL11.glTranslated(0, 1.75, -4);
		GL11.glRotated(angle * -2, 1, 0, 0);
		GL11.glTranslated(0, -1.75, 4);
		GL11.glTranslated(-0.01, 0, 0);
		ResourceManager.autosaw.renderPart("ArmLower");
		GL11.glTranslated(0.01, 0, 0);

		GL11.glTranslated(0, 1.75, -8);
		GL11.glRotated(angle, 1, 0, 0);
		GL11.glTranslated(0, -1.75, 8);
		ResourceManager.autosaw.renderPart("ArmTip");

		GL11.glTranslated(0, 1.75, -10);
		GL11.glRotated(spin, 0, -1, 0);
		GL11.glTranslated(0, -1.75, 10);
		ResourceManager.autosaw.renderPart("Sawblade");
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_autosaw);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -3.5, -3);
				GL11.glScaled(5, 5, 5);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotatef(-90, 0F, 1F, 0F);
				RenderAutosaw.this.renderCommon(0D, 80D, System.currentTimeMillis() % 3600 * 0.1D, 0);
			}};
	}
}
