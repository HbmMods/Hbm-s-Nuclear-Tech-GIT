package com.hbm.render.block;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.generic.BlockScaffoldDynamic;
import com.hbm.blocks.generic.BlockScaffoldDynamic.TileEntityScaffoldDynamic;
import com.hbm.render.util.RenderBlocksNT;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class RenderScaffoldDynamic implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

		Tessellator tessellator = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		tessellator.startDrawingQuads();

		
		renderer.setRenderBounds(0D, 0D, 0.875D, 0.75D, 1D, 1D);
		tessellator.setNormal(0F, 1F, 0F);	renderer.renderFaceYPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, -1F, 0F);	renderer.renderFaceYNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, 1F);	renderer.renderFaceZPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, 0, 0, 0, block.getIcon(0, 0));

		
		renderer.setRenderBounds(0.75D, 0D, 0.75D, 1D, 1D, 1D);
		tessellator.setNormal(0F, 1F, 0F);	renderer.renderFaceYPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, -1F, 0F);	renderer.renderFaceYNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(1F, 0F, 0F);	renderer.renderFaceXPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, 1F);	renderer.renderFaceZPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, 0, 0, 0, block.getIcon(0, 0));
		
		renderer.setRenderBounds(0.875D, 0D, 0D, 1D, 1D, 0.75D);
		tessellator.setNormal(0F, 1F, 0F);	renderer.renderFaceYPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, -1F, 0F);	renderer.renderFaceYNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(1F, 0F, 0F);	renderer.renderFaceXPos(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(-1F, 0F, 0F);	renderer.renderFaceXNeg(block, 0, 0, 0, block.getIcon(0, 0));
		tessellator.setNormal(0F, 0F, -1F);	renderer.renderFaceZNeg(block, 0, 0, 0, block.getIcon(0, 0));
		
		tessellator.draw();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
		
		renderer = RenderBlocksNT.INSTANCE.setWorld(world);
		
		TileEntity tile = world.getTileEntity(x, y, z);
		if(!(tile instanceof TileEntityScaffoldDynamic)) return false;
		TileEntityScaffoldDynamic scaffold = (TileEntityScaffoldDynamic) tile;
		
		boolean lpx = scaffold.has(scaffold.BAR_LOWER_POS_X);
		boolean lnx = scaffold.has(scaffold.BAR_LOWER_NEG_X);
		boolean upx = scaffold.has(scaffold.BAR_UPPER_POS_X);
		boolean unx = scaffold.has(scaffold.BAR_UPPER_NEG_X);
		boolean lpz = scaffold.has(scaffold.BAR_LOWER_POS_Z);
		boolean lnz = scaffold.has(scaffold.BAR_LOWER_NEG_Z);
		boolean upz = scaffold.has(scaffold.BAR_UPPER_POS_Z);
		boolean unz = scaffold.has(scaffold.BAR_UPPER_NEG_Z);

		boolean p_nx_nz = scaffold.has(scaffold.POLE_NX_NZ);
		boolean p_px_nz = scaffold.has(scaffold.POLE_PX_NZ);
		boolean p_px_pz = scaffold.has(scaffold.POLE_PX_PZ);
		boolean p_nx_pz = scaffold.has(scaffold.POLE_NX_PZ);
		
		BlockScaffoldDynamic.renderMode = 0;
		if(p_nx_nz) { renderer.setRenderBounds(0D, 0D, 0D, 0.25D, 1D, 0.25D); renderer.renderStandardBlock(block, x, y, z); }
		if(p_px_nz) { renderer.setRenderBounds(0.75D, 0D, 0D, 1D, 1D, 0.25D); renderer.renderStandardBlock(block, x, y, z); }
		if(p_px_pz) { renderer.setRenderBounds(0.75D, 0D, 0.75D, 1D, 1D, 1D); renderer.renderStandardBlock(block, x, y, z); }
		if(p_nx_pz) { renderer.setRenderBounds(0D, 0D, 0.75D, 0.25D, 1D, 1D); renderer.renderStandardBlock(block, x, y, z); }
		
		BlockScaffoldDynamic.renderMode = 1;
		if(scaffold.has(scaffold.GRATE_LOWER)) { renderer.setRenderBounds(0.000, 0.000, 0.000, 1.000, 0.125, 1.000); renderer.renderStandardBlock(block, x, y, z); }
		if(scaffold.has(scaffold.GRATE_UPPER)) { renderer.setRenderBounds(0.000, 0.875, 0.000, 1.000, 1.000, 1.000); renderer.renderStandardBlock(block, x, y, z); }
		
		BlockScaffoldDynamic.renderMode = 2;

		if(lpx) { renderer.setRenderBounds(0.75, 0.00, p_px_nz ? 0.25 : 0.00, 1.00, 0.25, p_px_pz ? 0.75 : 1.00); renderer.renderStandardBlock(block, x, y, z); }
		if(upx) { renderer.setRenderBounds(0.75, 0.75, p_px_nz ? 0.25 : 0.00, 1.00, 1.00, p_px_pz ? 0.75 : 1.00); renderer.renderStandardBlock(block, x, y, z); }
		if(lnx) { renderer.setRenderBounds(0.00, 0.00, p_nx_nz ? 0.25 : 0.00, 0.25, 0.25, p_nx_pz ? 0.75 : 1.00); renderer.renderStandardBlock(block, x, y, z); }
		if(unx) { renderer.setRenderBounds(0.00, 0.75, p_nx_nz ? 0.25 : 0.00, 0.25, 1.00, p_nx_pz ? 0.75 : 1.00); renderer.renderStandardBlock(block, x, y, z); }
		if(lpz) { renderer.setRenderBounds(p_nx_pz || lnx ? 0.25 : 0.00, 0.00, 0.75, p_px_pz || lpx ? 0.75 : 1.00, 0.25, 1.00); renderer.renderStandardBlock(block, x, y, z); }
		if(upz) { renderer.setRenderBounds(p_nx_pz || unx ? 0.25 : 0.00, 0.75, 0.75, p_px_pz || upx ? 0.75 : 1.00, 1.00, 1.00); renderer.renderStandardBlock(block, x, y, z); }
		if(lnz) { renderer.setRenderBounds(p_nx_nz || lnx ? 0.25 : 0.00, 0.00, 0.00, p_px_nz || lpx ? 0.75 : 1.00, 0.25, 0.25); renderer.renderStandardBlock(block, x, y, z); }
		if(unz) { renderer.setRenderBounds(p_nx_nz || unx ? 0.25 : 0.00, 0.75, 0.00, p_px_nz || upx ? 0.75 : 1.00, 1.00, 0.25); renderer.renderStandardBlock(block, x, y, z); }
		
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return BlockScaffoldDynamic.renderIDScaffold;
	}
}
