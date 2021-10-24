package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IMultiblock;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityZirnoxDestroyed;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ZirnoxDestroyed extends BlockDummyable implements IMultiblock {
	
	public ZirnoxDestroyed(Material mat) {
		super(mat);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntityZirnoxDestroyed();
		if(meta >= 6)
			return new TileEntityProxyCombo(false, true, true);
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
			return false;
	}
	
	@Override
	public int[] getDimensions() {
		return new int[] {4, 0, 2, 2, 2, 2,}; 
	}
	
	@Override
	public int getOffset() {
		return 2;
	}
	
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		this.makeExtra(world, x + dir.offsetX * o + rot.offsetX * 2, y + 1, z + dir.offsetZ * o + rot.offsetZ * 2);
		this.makeExtra(world, x + dir.offsetX * o + rot.offsetX * -2, y + 1, z + dir.offsetZ * o + rot.offsetZ * -2);
	}
	
}
