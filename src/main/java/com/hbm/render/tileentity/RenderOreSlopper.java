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
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;

public class RenderOreSlopper extends TileEntitySpecialRenderer implements IItemRendererProvider {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		switch(tile.getBlockMetadata() - BlockDummyable.offset) {
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.ore_slopper_tex);
		ResourceManager.ore_slopper.renderPart("Base");
		ResourceManager.ore_slopper.renderPart("Slider");

		GL11.glPushMatrix();
		double extend = Math.sin(((tile.getWorldObj().getTotalWorldTime() + interp) * 0.1 % (Math.PI * 2))) * 0.625+ 0.625;
		GL11.glTranslated(0, -MathHelper.clamp_double(extend - 0.25, 0, 1.25), 0);
		ResourceManager.ore_slopper.renderPart("Hydraulics");
		GL11.glTranslated(0, -MathHelper.clamp_double(extend, 0, 1.25), 0);
		ResourceManager.ore_slopper.renderPart("Bucket");
		GL11.glPopMatrix();
		
		double speed = 10;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0.375, 2.75, 0);
		GL11.glRotated((tile.getWorldObj().getTotalWorldTime() % 360 + interp) * speed, 0, 0, 1);
		GL11.glTranslated(-0.375, -2.75, 0);
		ResourceManager.ore_slopper.renderPart("BladesLeft");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(-0.375, 2.75, 0);
		GL11.glRotated((tile.getWorldObj().getTotalWorldTime() % 360 + interp) * -speed, 0, 0, 1);
		GL11.glTranslated(0.375, -2.75, 0);
		ResourceManager.ore_slopper.renderPart("BladesRight");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.875, -1);
		GL11.glRotated((tile.getWorldObj().getTotalWorldTime() % 360 + interp) * -25, 1, 0, 0);
		GL11.glTranslated(0, -1.875, 1);
		ResourceManager.ore_slopper.renderPart("Fan");
		GL11.glPopMatrix();
		
		GL11.glShadeModel(GL11.GL_FLAT);
		
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_ore_slopper);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase( ) {
			public void renderInventory() {
				GL11.glTranslated(0, -3, 0);
				GL11.glScaled(3.75, 3.75, 3.75);
			}
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(0.5, 0.5, 0.5);
				GL11.glRotatef(-90, 0F, 1F, 0F);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.ore_slopper_tex);
				ResourceManager.ore_slopper.renderAll();
				GL11.glShadeModel(GL11.GL_FLAT);
			}};
	}
}
