package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiblock;
import com.hbm.tileentity.TileEntityDummy;
import com.hbm.tileentity.TileEntityMachineChemplant;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineChemplant extends BlockContainer implements IMultiblock {

	public MachineChemplant(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineChemplant();

	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.machine_chemplant);
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
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.chemplantDimensionEast)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.chemplantDimensionEast, ModBlocks.dummy_block_chemplant);

				//
				DummyBlockChemplant.safeBreak = true;
				world.setBlock(x - 1, y, z, ModBlocks.dummy_port_chemplant);
				TileEntity te = world.getTileEntity(x - 1, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z + 1, ModBlocks.dummy_port_chemplant);
				TileEntity te2 = world.getTileEntity(x - 1, y, z + 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 2, y, z, ModBlocks.dummy_port_chemplant);
				TileEntity te3 = world.getTileEntity(x + 2, y, z);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 2, y, z + 1, ModBlocks.dummy_port_chemplant);
				TileEntity te4 = world.getTileEntity(x + 2, y, z + 1);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockChemplant.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.chemplantDimensionSouth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.chemplantDimensionSouth, ModBlocks.dummy_block_chemplant);

				//
				DummyBlockChemplant.safeBreak = true;
				world.setBlock(x, y, z - 1, ModBlocks.dummy_port_chemplant);
				TileEntity te = world.getTileEntity(x, y, z - 1);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z - 1, ModBlocks.dummy_port_chemplant);
				TileEntity te2 = world.getTileEntity(x - 1, y, z - 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x, y, z + 2, ModBlocks.dummy_port_chemplant);
				TileEntity te3 = world.getTileEntity(x, y, z + 2);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z + 2, ModBlocks.dummy_port_chemplant);
				TileEntity te4 = world.getTileEntity(x - 1, y, z + 2);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockChemplant.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.chemplantDimensionWest)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.chemplantDimensionWest, ModBlocks.dummy_block_chemplant);

				//
				DummyBlockChemplant.safeBreak = true;
				world.setBlock(x + 1, y, z, ModBlocks.dummy_port_chemplant);
				TileEntity te = world.getTileEntity(x + 1, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z - 1, ModBlocks.dummy_port_chemplant);
				TileEntity te2 = world.getTileEntity(x + 1, y, z - 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 2, y, z, ModBlocks.dummy_port_chemplant);
				TileEntity te3 = world.getTileEntity(x - 2, y, z);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 2, y, z - 1, ModBlocks.dummy_port_chemplant);
				TileEntity te4 = world.getTileEntity(x - 2, y, z - 1);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockChemplant.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.chemplantDimensionNorth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.chemplantDimensionNorth, ModBlocks.dummy_block_chemplant);

				//
				DummyBlockChemplant.safeBreak = true;
				world.setBlock(x, y, z + 1, ModBlocks.dummy_port_chemplant);
				TileEntity te = world.getTileEntity(x, y, z + 1);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z + 1, ModBlocks.dummy_port_chemplant);
				TileEntity te2 = world.getTileEntity(x + 1, y, z + 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x, y, z - 2, ModBlocks.dummy_port_chemplant);
				TileEntity te3 = world.getTileEntity(x, y, z - 2);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z - 2, ModBlocks.dummy_port_chemplant);
				TileEntity te4 = world.getTileEntity(x + 1, y, z - 2);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockChemplant.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
	}
}
