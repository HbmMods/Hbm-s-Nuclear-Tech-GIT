package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.items.ModItems;
import com.hbm.main.ChunkLoaderManager;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityTransporterBase;
import com.hbm.tileentity.machine.TileEntityTransporterRocket;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTransporterRocket extends BlockDummyable {

	public BlockTransporterRocket(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityTransporterRocket();
		return new TileEntityProxyCombo().inventory().fluid();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
			return player.getHeldItem() == null || player.getHeldItem().getItem() != ModItems.transporter_linker;
		
		if(player.getHeldItem() == null || player.getHeldItem().getItem() != ModItems.transporter_linker) {
			return standardOpenBehavior(world, x, y, z, player, 0);
		}
		
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		super.onBlockPlacedBy(world, x, y, z, player, itemStack);
		if(world.isRemote) return;

		// If we don't have a position, we failed to place, and should skip chunkloading
		int[] pos = findCore(world, x, y, z);
		if(pos != null) {
			ChunkLoaderManager.forceChunk(world, pos[0], pos[1], pos[2]);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int meta) {
		if(meta >= 12) {
			ChunkLoaderManager.unforceChunk(world, x, y, z);

			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityTransporterBase) {
				((TileEntityTransporterBase) te).unlinkTransporter();
			}
		}

		super.breakBlock(world, x, y, z, b, meta);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
	
}
