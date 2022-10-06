package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntitySawmill;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class RenderSawmill extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		TileEntitySawmill sawmill = (TileEntitySawmill) tile;
		
		float rot = sawmill.lastSpin + (sawmill.spin - sawmill.lastSpin) * interp;
		renderCommon(rot, sawmill.hasBlade);
		
		GL11.glPopMatrix();
	}
	
	private void renderCommon(float rot, boolean hasBlade) {

		bindTexture(ResourceManager.sawmill_tex);
		ResourceManager.sawmill.renderPart("Main");

		if(hasBlade) {
			GL11.glPushMatrix();
			GL11.glTranslated(0, 1.375, 0);
			GL11.glRotatef(-rot * 2, 0, 0, 1);
			GL11.glTranslated(0, -1.375, 0);
			ResourceManager.sawmill.renderPart("Blade");
			GL11.glPopMatrix();
		}
		
		GL11.glPushMatrix();
		GL11.glTranslated(0.5625, 1.375, 0);
		GL11.glRotatef(rot, 0, 0, 1);
		GL11.glTranslated(-0.5625, -1.375, 0);
		ResourceManager.sawmill.renderPart("GearLeft");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(-0.5625, 1.375, 0);
		GL11.glRotatef(-rot, 0, 0, 1);
		GL11.glTranslated(0.5625, -1.375, 0);
		ResourceManager.sawmill.renderPart("GearRight");
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_sawmill);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -1.5, 0);
				GL11.glScaled(3.25, 3.25, 3.25);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glRotatef(90, 0F, 1F, 0F);
				boolean cog = item.getItemDamage() != 1;
				RenderSawmill.this.renderCommon(cog ? System.currentTimeMillis() % 3600 * 0.1F : 0, cog);
			}};
	}

}
