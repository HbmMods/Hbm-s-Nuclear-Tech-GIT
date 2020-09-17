package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyEnergy;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class MachineMiningLaser extends BlockDummyable {

	public MachineMiningLaser(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= 12)
			return new TileEntityMachineMiningLaser();
		
		if(meta == 7)
			return new TileEntityProxyEnergy();

		if(meta >= 6)
			return new TileEntityProxyInventory();
		
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 1, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public int getHeightOffset() {
		return -1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			int[] pos = this.findCore(world, x, y, z);
			
			if(pos == null)
				return false;
			
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_mining_laser, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}
	
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {

		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		this.makeExtra(world, x + 1, y, z);
		this.makeExtra(world, x - 1, y, z);
		this.makeExtra(world, x, y, z + 1);
		this.makeExtra(world, x, y, z - 1);
		
		this.makeExtra(world, x, y + 1, z);
	}
}
