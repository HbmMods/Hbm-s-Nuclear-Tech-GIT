package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityTowerSmall;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineTowerSmall extends BlockDummyable {

	public MachineTowerSmall(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {
		
		if(meta >= 12)
			return new TileEntityTowerSmall();
		
		if(meta >= 8)
			return new TileEntityProxyCombo(false, false, true);
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {18, 0, 2, 2, 2, 2};
	}

	@Override
	public int getOffset() {
		return 2;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;
		
		for(int i = 2; i <= 6; i++) {
			ForgeDirection dr2 = ForgeDirection.getOrientation(i);
			this.makeExtra(world, x + dr2.offsetX * 2, y, z + dr2.offsetZ * 2);
		}
	}
}
