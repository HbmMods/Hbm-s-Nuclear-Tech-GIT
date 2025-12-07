package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderAnnihilator extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		switch(tileEntity.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		bindTexture(ResourceManager.annihilator_tex);
		ResourceManager.annihilator.renderPart("Annihilator");
		
		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.75, 0);
		GL11.glRotated(System.currentTimeMillis() * 0.15 % 360, 0, 0, -1);
		GL11.glTranslated(0, -1.75, 0);
		ResourceManager.annihilator.renderPart("Roller");
		GL11.glPopMatrix();

		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		bindTexture(ResourceManager.annihilator_belt_tex);
		GL11.glTranslated(-System.currentTimeMillis() / 3000D % 1D, 0, 0);
		ResourceManager.annihilator.renderPart("Belt");
		GL11.glMatrixMode(GL11.GL_TEXTURE);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_annihilator);
	}

	@Override
	public IItemRenderer getRenderer() {
		
		return new ItemRenderBase() {
			
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(2.75, 2.75, 2.75);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.annihilator_tex);
				ResourceManager.annihilator.renderPart("Annihilator");
				ResourceManager.annihilator.renderPart("Roller");
				bindTexture(ResourceManager.annihilator_belt_tex);
				ResourceManager.annihilator.renderPart("Belt");
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
