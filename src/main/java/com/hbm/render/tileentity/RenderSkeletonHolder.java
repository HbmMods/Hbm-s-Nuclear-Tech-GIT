package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockSkeletonHolder.TileEntitySkeletonHolder;
import com.hbm.main.ResourceManager;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class RenderSkeletonHolder extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float interp) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		
		switch(te.getBlockMetadata()) {
		case 2: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(90, 0F, 1F, 0F); break;
		}
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		RenderHelper.enableStandardItemLighting();

		bindTexture(ResourceManager.skeleton_holder_tex);
		ResourceManager.skeleton_holder.renderPart("Holder1");
		
		TileEntitySkeletonHolder pedestal = (TileEntitySkeletonHolder) te;
		
		if(pedestal.item != null) {

			ItemStack stack = pedestal.item.copy();
			
			GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);

			if(!(stack.getItemSpriteNumber() == 0 && stack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(stack.getItem()).getRenderType()))) {
				GL11.glScaled(1.5, 1.5, 1.5);
			}
			
			GL11.glTranslated(0, 0.125, 0);

			EntityItem dummy = new EntityItem(te.getWorldObj(), 0, 0, 0, stack);
			dummy.hoverStart = 0.0F;

			RenderItem.renderInFrame = true;
			RenderManager.instance.renderEntityWithPosYaw(dummy, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
			RenderItem.renderInFrame = false;
		}
		
		GL11.glPopMatrix();
	}
}
