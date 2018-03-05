package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiblock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineIGenerator extends BlockContainer implements IMultiblock {

	public MachineIGenerator(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineIGenerator();

	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.machine_industrial_generator);
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
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.iGenDimensionEast)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.iGenDimensionEast, ModBlocks.dummy_block_igenerator);
				
				//
				DummyBlockIGenerator.safeBreak = true;
				world.setBlock(x + 2, y, z, ModBlocks.dummy_port_igenerator);
				TileEntity te = world.getTileEntity(x + 2, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 3, y, z, ModBlocks.dummy_port_igenerator);
				TileEntity te2 = world.getTileEntity(x - 3, y, z);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockIGenerator.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.iGenDimensionSouth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.iGenDimensionSouth, ModBlocks.dummy_block_igenerator);
				
				//
				DummyBlockIGenerator.safeBreak = true;
				world.setBlock(x, y, z + 2, ModBlocks.dummy_port_igenerator);
				TileEntity te = world.getTileEntity(x, y, z + 2);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x, y, z - 3, ModBlocks.dummy_port_igenerator);
				TileEntity te2 = world.getTileEntity(x, y, z - 3);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockIGenerator.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.iGenDimensionWest)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.iGenDimensionWest, ModBlocks.dummy_block_igenerator);
				
				//
				DummyBlockIGenerator.safeBreak = true;
				world.setBlock(x + 3, y, z, ModBlocks.dummy_port_igenerator);
				TileEntity te = world.getTileEntity(x + 3, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 2, y, z, ModBlocks.dummy_port_igenerator);
				TileEntity te2 = world.getTileEntity(x - 2, y, z);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockIGenerator.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.iGenDimensionNorth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.iGenDimensionNorth, ModBlocks.dummy_block_igenerator);
				
				//
				DummyBlockIGenerator.safeBreak = true;
				world.setBlock(x, y, z + 3, ModBlocks.dummy_port_igenerator);
				TileEntity te = world.getTileEntity(x, y, z + 3);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x, y, z - 2, ModBlocks.dummy_port_igenerator);
				TileEntity te2 = world.getTileEntity(x, y, z - 2);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockIGenerator.safeBreak = false;
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
			TileEntityMachineIGenerator entity = (TileEntityMachineIGenerator) world.getTileEntity(x, y, z);
    		if(entity != null)
    		{
    			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_machine_industrial_generator, world, x, y, z);
    		}
			return true;
		} else {
			return false;
		}
	}
}
