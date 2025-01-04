package com.hbm.blocks.network;

import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.network.TileEntityPylonBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class PylonBase extends BlockContainer implements ITooltipProvider {

	protected PylonBase(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block b, int m) {

		TileEntity te = world.getTileEntity(x, y, z);

		if(te instanceof TileEntityPylonBase) {
			((TileEntityPylonBase)te).disconnectAll();
		}

		super.breakBlock(world, x, y, z, b, m);
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityPylonBase te = (TileEntityPylonBase) world.getTileEntity(x, y, z);
			return te.setColor(player.getHeldItem());
		} else {
			return false;
		}
	}
}
