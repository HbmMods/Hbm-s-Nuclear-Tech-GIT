package com.hbm.blocks.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiblock;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MachineAssembler extends BlockContainer implements IMultiblock {

	public MachineAssembler(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityMachineAssembler();

	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(ModBlocks.machine_assembler);
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
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.assemblerDimensionEast)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.assemblerDimensionEast, ModBlocks.dummy_block_assembler);

				//
				DummyBlockAssembler.safeBreak = true;
				world.setBlock(x - 1, y, z, ModBlocks.dummy_port_assembler);
				TileEntity te = world.getTileEntity(x - 1, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z + 1, ModBlocks.dummy_port_assembler);
				TileEntity te2 = world.getTileEntity(x - 1, y, z + 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 2, y, z, ModBlocks.dummy_port_assembler);
				TileEntity te3 = world.getTileEntity(x + 2, y, z);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 2, y, z + 1, ModBlocks.dummy_port_assembler);
				TileEntity te4 = world.getTileEntity(x + 2, y, z + 1);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockAssembler.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.assemblerDimensionSouth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.assemblerDimensionSouth, ModBlocks.dummy_block_assembler);

				//
				DummyBlockAssembler.safeBreak = true;
				world.setBlock(x, y, z - 1, ModBlocks.dummy_port_assembler);
				TileEntity te = world.getTileEntity(x, y, z - 1);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z - 1, ModBlocks.dummy_port_assembler);
				TileEntity te2 = world.getTileEntity(x - 1, y, z - 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x, y, z + 2, ModBlocks.dummy_port_assembler);
				TileEntity te3 = world.getTileEntity(x, y, z + 2);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 1, y, z + 2, ModBlocks.dummy_port_assembler);
				TileEntity te4 = world.getTileEntity(x - 1, y, z + 2);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockAssembler.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.assemblerDimensionWest)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.assemblerDimensionWest, ModBlocks.dummy_block_assembler);

				//
				DummyBlockAssembler.safeBreak = true;
				world.setBlock(x + 1, y, z, ModBlocks.dummy_port_assembler);
				TileEntity te = world.getTileEntity(x + 1, y, z);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z - 1, ModBlocks.dummy_port_assembler);
				TileEntity te2 = world.getTileEntity(x + 1, y, z - 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 2, y, z, ModBlocks.dummy_port_assembler);
				TileEntity te3 = world.getTileEntity(x - 2, y, z);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x - 2, y, z - 1, ModBlocks.dummy_port_assembler);
				TileEntity te4 = world.getTileEntity(x - 2, y, z - 1);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockAssembler.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
		if (i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			if(MultiblockHandler.checkSpace(world, x, y, z, MultiblockHandler.assemblerDimensionNorth)) {
				MultiblockHandler.fillUp(world, x, y, z, MultiblockHandler.assemblerDimensionNorth, ModBlocks.dummy_block_assembler);

				//
				DummyBlockAssembler.safeBreak = true;
				world.setBlock(x, y, z + 1, ModBlocks.dummy_port_assembler);
				TileEntity te = world.getTileEntity(x, y, z + 1);
				if(te instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z + 1, ModBlocks.dummy_port_assembler);
				TileEntity te2 = world.getTileEntity(x + 1, y, z + 1);
				if(te2 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te2;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x, y, z - 2, ModBlocks.dummy_port_assembler);
				TileEntity te3 = world.getTileEntity(x, y, z - 2);
				if(te3 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te3;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				world.setBlock(x + 1, y, z - 2, ModBlocks.dummy_port_assembler);
				TileEntity te4 = world.getTileEntity(x + 1, y, z - 2);
				if(te4 instanceof TileEntityDummy) {
					TileEntityDummy dummy = (TileEntityDummy)te4;
					dummy.targetX = x;
					dummy.targetY = y;
					dummy.targetZ = z;
				}
				DummyBlockAssembler.safeBreak = false;
				//
				
			} else
				world.func_147480_a(x, y, z, true);
		}
	}
}
