package com.hbm.hrist;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.BlockDummyable;
import com.hbm.render.block.ISBRHUniversal;
import com.hbm.render.util.RenderBlocksNT;
import com.hbm.util.fauxpointtwelve.BlockPos;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

//you can think of it like a pipeline
public abstract class BlockConduitBase extends BlockDummyable implements ISBRHUniversal {

	public BlockConduitBase() {
		super(Material.ground);
	}

	@Override public TileEntity createNewTileEntity(World world, int meta) { return null; }
	@Override public int getRenderType() { return ISBRHUniversal.renderID; }

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta >= 12) {
			ConduitPiece piece = this.getPiece(world, x, y, z, meta);
			ConduitSpace.pushPiece(world, piece, new BlockPos(x, y, z));
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int meta) {
		super.breakBlock(world, x, y, z, b, meta);
		
		if(meta >= 12) {
			ConduitSpace.popPiece(world, new BlockPos(x, y, z));
		}
	}
	
	public abstract ConduitPiece getPiece(World world, int x, int y, int z, int meta);
	
	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, Object renderBlocks) {

		GL11.glPushMatrix();
		RenderBlocks renderer = (RenderBlocks) renderBlocks;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1); RenderBlocksNT.renderStandardInventoryBlock(block, meta, renderer);
		GL11.glPopMatrix();
	}
}
