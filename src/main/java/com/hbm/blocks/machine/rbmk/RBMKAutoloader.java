package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKAutoloader;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class RBMKAutoloader extends BlockDummyable {

	public RBMKAutoloader() {
		super(Material.iron);
		
		this.bounding.add(AxisAlignedBB.getBoundingBox(-0.125, 0, -0.125, 0.125, 4, 0.125));
		this.bounding.add(AxisAlignedBB.getBoundingBox(-0.5, 4, -0.5, 0.5, 9, 0.5));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityRBMKAutoloader();
		return new TileEntityProxyCombo().inventory();
	}

	@Override
	public int[] getDimensions() {
		return new int[] {8, 0, 0, 0, 0, 0};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}
}
