package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntitySILEX;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineSILEX extends BlockDummyable {

	public MachineSILEX(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= 12)
			return new TileEntitySILEX();
		if(meta >= 6)
			return new TileEntityProxyCombo(true, false, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
			
		} else if(!player.isSneaking()) {
			int[] pos = this.findCore(world, x, y, z);

			if(pos == null)
				return false;

			FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return false;
		}
	}
	
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);
		
		if(dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
			this.makeExtra(world, x + dir.offsetX * o + 1, y + 1, z + dir.offsetZ * o);
			this.makeExtra(world, x + dir.offsetX * o - 1, y + 1, z + dir.offsetZ * o);
		}
		
		if(dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
			this.makeExtra(world, x + dir.offsetX * o, y + 1, z + dir.offsetZ * o + 1);
			this.makeExtra(world, x + dir.offsetX * o, y + 1, z + dir.offsetZ * o - 1);
		}
	}
}
