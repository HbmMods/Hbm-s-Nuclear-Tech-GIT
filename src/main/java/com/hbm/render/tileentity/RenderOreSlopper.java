package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.render.util.RenderDecoItem;
import com.hbm.tileentity.machine.TileEntityMachineOreSlopper;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;

public class RenderOreSlopper extends TileEntitySpecialRenderer implements IItemRendererProvider {
	
	private RenderItem itemRenderer = new RenderDecoItem(this);

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
		
		TileEntityMachineOreSlopper slopper = (TileEntityMachineOreSlopper) tile;

		GL11.glShadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.ore_slopper_tex);
		ResourceManager.ore_slopper.renderPart("Base");
		
		GL11.glPushMatrix();
		
		double slide = slopper.prevSlider + (slopper.slider - slopper.prevSlider) * interp;
		GL11.glTranslated(0, 0, slide * -3);
		ResourceManager.ore_slopper.renderPart("Slider");

		GL11.glPushMatrix();
		double extend = (slopper.prevBucket + (slopper.bucket - slopper.prevBucket) * interp) * 1.5;
		GL11.glTranslated(0, -MathHelper.clamp_double(extend - 0.25, 0, 1.25), 0);
		ResourceManager.ore_slopper.renderPart("Hydraulics");
		GL11.glTranslated(0, -MathHelper.clamp_double(extend, 0, 1.25), 0);
		ResourceManager.ore_slopper.renderPart("Bucket");
		
		if(slopper.animation == slopper.animation.LIFTING) {
			GL11.glTranslated(0.0625D, 4.3125D, 2D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glRotatef(90, 0F, 1F, 0F);
			GL11.glRotatef(-90, 1F, 0F, 0F);
			ItemStack stack = new ItemStack(ModItems.bedrock_ore, 1, 0);
			EntityItem item = new EntityItem(null, 0.0D, 0.0D, 0.0D, stack);
			item.getEntityItem().stackSize = 1;
			item.hoverStart = 0.0F;
			RenderItem.renderInFrame = true;
			GL11.glScaled(1.75, 1.75, 1.75);
			itemRenderer.doRender(item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			RenderItem.renderInFrame = false;
			bindTexture(ResourceManager.ore_slopper_tex);
		}
		
		GL11.glPopMatrix();
		GL11.glPopMatrix();
		
		double blades = slopper.prevBlades + (slopper.blades - slopper.prevBlades) * interp;
		
		GL11.glPushMatrix();
		GL11.glTranslated(0.375, 2.75, 0);
		GL11.glRotated(blades, 0, 0, 1);
		GL11.glTranslated(-0.375, -2.75, 0);
		ResourceManager.ore_slopper.renderPart("BladesLeft");
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslated(-0.375, 2.75, 0);
		GL11.glRotated(-blades, 0, 0, 1);
		GL11.glTranslated(0.375, -2.75, 0);
		ResourceManager.ore_slopper.renderPart("BladesRight");
		GL11.glPopMatrix();
		
		double fan = slopper.prevFan + (slopper.fan - slopper.prevFan) * interp;

		GL11.glPushMatrix();
		GL11.glTranslated(0, 1.875, -1);
		GL11.glRotated(-fan, 1, 0, 0);
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
