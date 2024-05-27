package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityICF;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineICF extends BlockDummyable {

	public MachineICF() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityICF();
		if(meta >= 6) return new TileEntityProxyCombo().inventory().fluid();
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {5, 0, 1, 1, 8, 8};
	}

	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + 3, z + dir.offsetZ * o, new int[] {1, 1, -1, 2, 8, 8}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + 3, z + dir.offsetZ * o, new int[] {1, 1, 2, -1, 8, 8}, this, dir);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;
		
		this.makeExtra(world, x , y + 5, z);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		this.makeExtra(world, x + dir.offsetX * 2 + rot.offsetX * 6 , y + 3, z + dir.offsetZ * 2 + rot.offsetZ * 6);
		this.makeExtra(world, x + dir.offsetX * 2 - rot.offsetX * 6 , y + 3, z + dir.offsetZ * 2 - rot.offsetZ * 6);
		this.makeExtra(world, x - dir.offsetX * 2 + rot.offsetX * 6 , y + 3, z - dir.offsetZ * 2 + rot.offsetZ * 6);
		this.makeExtra(world, x - dir.offsetX * 2 - rot.offsetX * 6 , y + 3, z - dir.offsetZ * 2 - rot.offsetZ * 6);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		if(!super.checkRequirement(world, x, y, z, dir, o)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + 3, z + dir.offsetZ * o, new int[] {1, 1, -1, 2, 8, 8}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + 3, z + dir.offsetZ * o, new int[] {1, 1, 2, -1, 8, 8}, x, y, z, dir)) return false;
		
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return super.standardOpenBehavior(world, x, y, z, player, 0);
	}
}
