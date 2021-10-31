package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityDeuteriumTower;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class DeuteriumTower extends BlockDummyable {

	public DeuteriumTower(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		
		if(meta >= 12)
			return new TileEntityDeuteriumTower();
		
		if(meta >= 8)
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] {9, 0, 1, 0, 0, 1};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;
		
        ForgeDirection dr2 = dir.getRotation(ForgeDirection.UP);
        
        this.makeExtra(world, x - dir.offsetX - dr2.offsetX, y, z - dir.offsetZ - dr2.offsetZ);
        this.makeExtra(world, x, y, z - dir.offsetZ - dr2.offsetZ);
        this.makeExtra(world, x - dir.offsetX - dr2.offsetX, y, z);
	}
}
