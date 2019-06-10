package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IMultiblock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class LaunchTable extends BlockContainer implements IMultiblock, IBomb {

	public LaunchTable(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityLaunchTable();
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
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityLaunchTable entity = (TileEntityLaunchTable) world.getTileEntity(x, y, z);
			if(entity != null)
			{
				FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_launch_table, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onBlockPlacedBy(World worldObj, int xCoord, int yCoord, int zCoord, EntityLivingBase player, ItemStack itemStack) {
		int d = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		for(int k = -4; k <= 4; k++)
			for(int l = -4; l <= 4; l++)
				if(l != 0 && k != 0)
					if(!worldObj.getBlock(xCoord + k, yCoord, zCoord + l).isReplaceable(worldObj, xCoord + k, yCoord, zCoord + l)) {
						worldObj.func_147480_a(xCoord, yCoord, zCoord, true);
						return;
					}

		if (d == 0) {
			
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
			for(int i = 1; i < 12; i++)
				worldObj.setBlock(xCoord + 3, yCoord + i, zCoord, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(worldObj, xCoord + i, yCoord, zCoord, xCoord, yCoord, zCoord, ModBlocks.dummy_port_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(worldObj, xCoord, yCoord, zCoord + i, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_launch_table);
		}
		if (d == 1) {
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 2);
			for(int i = 1; i < 12; i++)
				worldObj.setBlock(xCoord, yCoord + i, zCoord + 3, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(worldObj, xCoord + i, yCoord, zCoord, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(worldObj, xCoord, yCoord, zCoord + i, xCoord, yCoord, zCoord, ModBlocks.dummy_port_launch_table);
		}
		if (d == 2) {
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
			for(int i = 1; i < 12; i++)
				worldObj.setBlock(xCoord - 3, yCoord + i, zCoord, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(worldObj, xCoord + i, yCoord, zCoord, xCoord, yCoord, zCoord, ModBlocks.dummy_port_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(worldObj, xCoord, yCoord, zCoord + i, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_launch_table);
		}
		if (d == 3) {
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 3, 2);
			for(int i = 1; i < 12; i++)
				worldObj.setBlock(xCoord, yCoord + i, zCoord - 3, Blocks.air);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(worldObj, xCoord + i, yCoord, zCoord, xCoord, yCoord, zCoord, ModBlocks.dummy_plate_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(worldObj, xCoord, yCoord, zCoord + i, xCoord, yCoord, zCoord, ModBlocks.dummy_port_launch_table);
		}

		for(int i = -4; i <= 4; i++)
			for(int j = -4; j <= 4; j++)
				if(i != 0 && j != 0)
					placeDummy(worldObj, xCoord + i, yCoord, zCoord + j, xCoord, yCoord, zCoord, ModBlocks.dummy_port_launch_table);
	}
	
	private void placeDummy(World world, int x, int y, int z, int xCoord, int yCoord, int zCoord, Block block) {
		
		world.setBlock(x, y, z, block);
		
		TileEntity te = world.getTileEntity(x, y, z);
		
		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.targetX = xCoord;
			dummy.targetY = yCoord;
			dummy.targetZ = zCoord;
		}
	}

	@Override
	public void explode(World world, int x, int y, int z) {
		TileEntityLaunchTable entity = (TileEntityLaunchTable) world.getTileEntity(x, y, z);
		
		if(entity.canLaunch())
			entity.launch();
	}

}
