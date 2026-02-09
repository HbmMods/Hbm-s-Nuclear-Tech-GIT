package com.hbm.hrist;

import com.hbm.blocks.BlockDummyable;
import com.hbm.hrist.ConduitPiece.ConnectionDefinition;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

// you can think of it like a pipeline
public class BlockConduit extends BlockDummyable {

	public BlockConduit() {
		super(Material.ground);
	}

	@Override public TileEntity createNewTileEntity(World world, int meta) { return null; }
	@Override public int[] getDimensions() { return new int[] {0, 0, 2, 2, 1, 0}; }
	@Override public int getOffset() { return 2; }

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
	
	public ConduitPiece getPiece(World world, int x, int y, int z, int meta) {
		ForgeDirection dir = ForgeDirection.getOrientation(meta - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		DirPos d0 = new DirPos(
				x + 0.5 + rot.offsetX * 0.5 + dir.offsetX * 2.5, y,
				z + 0.5 + rot.offsetZ * 0.5 + dir.offsetZ * 2.5, dir);
		DirPos d1 = new DirPos(
				x + 0.5 + rot.offsetX * 0.5 - dir.offsetX * 2.5, y,
				z + 0.5 + rot.offsetZ * 0.5 - dir.offsetZ * 2.5, dir.getOpposite());
		return new ConduitPiece(new ConnectionDefinition(d0, d1));
	}
}
