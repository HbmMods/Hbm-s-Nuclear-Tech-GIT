package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineBlastFurnace;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineBlastFurnace extends BlockDummyable {

	public MachineBlastFurnace() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineBlastFurnace();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().fluid();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override public int[] getDimensions() { return new int[] {6, 0, 1, 1, 1, 1}; }
	@Override public int getOffset() { return 1; }

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		x -= dir.offsetX;
		z -= dir.offsetZ;
		
		this.makeExtra(world, x + 1, y, z);
		this.makeExtra(world, x - 1, y, z);
		this.makeExtra(world, x, y, z + 1);
		this.makeExtra(world, x, y, z - 1);

		this.makeExtra(world, x + dir.offsetX, y + 3, z + dir.offsetZ);
		this.makeExtra(world, x + dir.offsetX, y + 5, z + dir.offsetZ);
		
		this.makeExtra(world, x, y + 6, z);
	}
}
