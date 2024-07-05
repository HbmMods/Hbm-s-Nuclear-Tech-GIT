package com.hbm.blocks.machine;

import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineRocketAssembly;
import com.hbm.util.ItemStackUtil;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineRocketAssembly extends BlockContainer {

	public MachineRocketAssembly(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityMachineRocketAssembly();
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		ItemStackUtil.spillItems(world, x, y, z, block, world.rand);
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			TileEntityMachineRocketAssembly entity = (TileEntityMachineRocketAssembly) world.getTileEntity(x, y, z);
			if(entity != null) {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
}
