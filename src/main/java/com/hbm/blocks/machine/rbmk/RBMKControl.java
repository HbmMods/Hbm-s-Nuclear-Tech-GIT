package com.hbm.blocks.machine.rbmk;

import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlManual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class RBMKControl extends RBMKBase {
	
	public boolean moderated = false;
	
	public RBMKControl(boolean moderated) {
		super();
		this.moderated = moderated;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= this.offset)
			return new TileEntityRBMKControlManual();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return openInv(world, x, y, z, player);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}
	
	@Override
	public int getRenderType(){
		return this.renderIDControl;
	}
}
