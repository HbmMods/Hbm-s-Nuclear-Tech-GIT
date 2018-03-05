package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiblock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;
import com.hbm.tileentity.machine.TileEntityMachineRadGen;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineRadGen extends BlockContainer implements IMultiblock {

	public MachineRadGen(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineRadGen();

	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.machine_radgen);
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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.radGenDimensionEast)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.radGenDimensionEast, ModBlocks.dummy_block_radgen);
				
				//
				DummyBlockRadGen.safeBreak = true;
				world.setBlock(x, y, z + 4, ModBlocks.dummy_port_radgen);
				TileEntity te = world.getTileEntity(x, y, z + 4);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockRadGen.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.radGenDimensionSouth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.radGenDimensionSouth, ModBlocks.dummy_block_radgen);
				
				//
				DummyBlockRadGen.safeBreak = true;
				world.setBlock(x - 4, y, z, ModBlocks.dummy_port_radgen);
				TileEntity te = world.getTileEntity(x - 4, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockRadGen.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.radGenDimensionWest)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.radGenDimensionWest, ModBlocks.dummy_block_radgen);
				
				//
				DummyBlockRadGen.safeBreak = true;
				world.setBlock(x, y, z - 4, ModBlocks.dummy_port_radgen);
				TileEntity te = world.getTileEntity(x, y, z - 4);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockRadGen.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.radGenDimensionNorth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.radGenDimensionNorth, ModBlocks.dummy_block_radgen);
				
				//
				DummyBlockRadGen.safeBreak = true;
				world.setBlock(x + 4, y, z, ModBlocks.dummy_port_radgen);
				TileEntity te = world.getTileEntity(x + 4, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockRadGen.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityMachineRadGen entity = (TileEntityMachineRadGen) world.getTileEntity(x, y, z);
    		if(entity != null)
    		{
    			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_radgen, world, x, y, z);
    		}
			return true;
		} else {
			return false;
		}
	}
}
