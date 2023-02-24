package com.hbm.blocks.bomb;

import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.interfaces.IBomb;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class NukeBalefire extends BlockMachineBase implements IBomb {

	public NukeBalefire(Material mat) {
		super(mat, 0);
		rotatable = true;
	}


	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityNukeBalefire();
	}
	
	@Override
	public int getRenderType(){
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {

		if (world.isBlockIndirectlyGettingPowered(x, y, z)) {
			explode(world, x, y, z);
		}
	}

	@Override
	public BombReturnCode explode(World world, int x, int y, int z) {
		
		if(!world.isRemote) {
			TileEntityNukeBalefire bomb = (TileEntityNukeBalefire) world.getTileEntity(x, y, z);
				
			if(bomb.isLoaded()) {
				bomb.explode();
				return BombReturnCode.DETONATED;
			}
			
			return BombReturnCode.ERROR_MISSING_COMPONENT;
		}
		
		return BombReturnCode.UNDEFINED;
	}

}
