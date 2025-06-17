package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyDyn;
import com.hbm.tileentity.machine.TileEntityMachineChemicalFactory;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineChemicalFactory extends BlockDummyable {

	public MachineChemicalFactory(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineChemicalFactory();
		if(meta >= 6) return new TileEntityProxyDyn().inventory().power().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override public int[] getDimensions() { return new int[] {2, 0, 2, 2, 2, 2}; }
	@Override public int getOffset() { return 2; }

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x -= dir.offsetX * 2;
		z -= dir.offsetZ * 2;
		
		for(int i = -2; i <= 2; i++) for(int j = -2; j <= 2; j++) {
			if(Math.abs(i) == 2 || Math.abs(j) == 2) this.makeExtra(world, x + i, y, z + j);
		}
	}
}
