package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityElectrolyser;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineElectrolyser extends BlockDummyable {

	public MachineElectrolyser() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityElectrolyser();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 4, 4, 2, 2};
	}

	@Override
	public int getOffset() {
		return 4;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return this.standardOpenBehavior(world, x, y, z, player, 0);
	}
	
	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {1, 0, 4, 4, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -1, 4, 4, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, 1, -1, -2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , 3 + y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, 1, -1, -1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, 3, -3, -2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , 3 + y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, 3, -3, -1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, -1, 1, -2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , 3 + y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, -1, 1, -1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, -3, 3, -2, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x + dir.offsetX * o , 3 + y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, -3, 3, -1, 1}, this, dir);

	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, getDimensions(), x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {1, 0, 4, 4, 1, 1}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, -1, 4, 4, 0, 0}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, 1, -1, -2, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , 3 +  y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, 1, -1, -1, 1}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, 3, -3, -2, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , 3 +  y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, 3, -3, -1, 1}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, -1, 1, -2, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , 3 +  y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, -1, 1, -1, 1}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {3, 0, -3, 3, -2, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * o , 3 + y + dir.offsetY * o, z + dir.offsetZ * o, new int[] {0, 0, -3, 3, -1, 1}, x, y, z, dir)) return false;
		
		return true;
	}

}
