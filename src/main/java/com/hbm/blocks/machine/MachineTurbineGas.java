package com.hbm.blocks.machine;

import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineTurbineGas;

import com.hbm.blocks.BlockDummyable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineTurbineGas extends BlockDummyable {
	
	public MachineTurbineGas(Material mat) {
		super(mat);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12) 
			return new TileEntityMachineTurbineGas();
		if(meta >= 6) 
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] { 2, 0, 1, 1, 4, 5 };
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return standardOpenBehavior(world, x, y, z, player, 0);
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		this.makeExtra(world, x - dir.offsetX * 1 - dir.offsetZ * 4, y + 1, z + dir.offsetX * 4 - dir.offsetZ * 1); //power
		this.makeExtra(world, x - dir.offsetZ * 1, y, z + dir.offsetX * 1); //gas in
		this.makeExtra(world, x - dir.offsetX * 2 - dir.offsetZ * 1, y, z + dir.offsetX * 1 - dir.offsetZ * 2); //gas in
		this.makeExtra(world, x + dir.offsetZ * 4, y, z - dir.offsetX * 4); //wa'er in
		this.makeExtra(world, x + dir.offsetZ * 4 - dir.offsetX * 2, y, z - dir.offsetX * 4 - dir.offsetZ * 2); //wa'er in
		this.makeExtra(world, x + dir.offsetZ * 5 - dir.offsetX * 1, y + 1, z - dir.offsetX * 5 - dir.offsetZ * 1); //steam out
	}
}