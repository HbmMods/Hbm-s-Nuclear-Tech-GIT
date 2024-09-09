package com.hbm.blocks.network;

import api.hbm.conveyor.IConveyorItem;
import api.hbm.conveyor.IConveyorPackage;
import api.hbm.conveyor.IEnterableBlock;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityCraneBase;
import com.hbm.tileentity.network.TileEntityCraneInserter;
import com.hbm.util.InventoryUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CraneInserter extends BlockCraneBase implements IEnterableBlock {

	public CraneInserter() {
		super(Material.iron);
	}

	@Override
	public TileEntityCraneBase createNewTileEntity(World world, int meta) {
		return new TileEntityCraneInserter();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconDirectional = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_top");
		this.iconDirectionalUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_up");
		this.iconDirectionalDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_down");
		this.iconDirectionalTurnLeft = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_top_left");
		this.iconDirectionalTurnRight = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_top_right");
		this.iconDirectionalSideLeftTurnUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_left_turn_up");
		this.iconDirectionalSideRightTurnUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_right_turn_up");
		this.iconDirectionalSideLeftTurnDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_left_turn_down");
		this.iconDirectionalSideRightTurnDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_right_turn_down");
		this.iconDirectionalSideUpTurnLeft = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_up_turn_left");
		this.iconDirectionalSideUpTurnRight = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_up_turn_right");
		this.iconDirectionalSideDownTurnLeft = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_down_turn_left");
		this.iconDirectionalSideDownTurnRight = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_down_turn_right");
	}

	@Override
	public boolean canItemEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity) {
		ForgeDirection orientation = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
		return orientation == dir;
	}

	@Override
	public void onItemEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity) {
		ForgeDirection outputDirection = getOutputSide(world, x, y, z);
		TileEntity te = world.getTileEntity(x + outputDirection.offsetX, y + outputDirection.offsetY, z + outputDirection.offsetZ);
		
		if(entity == null || entity.getItemStack() == null || entity.getItemStack().stackSize <= 0) {
			return;
		}
		
		ItemStack toAdd = entity.getItemStack().copy();
		
		int[] access = null;
		
		if(te instanceof ISidedInventory) {
			ISidedInventory sided = (ISidedInventory) te;
			access = InventoryUtil.masquerade(sided, outputDirection.getOpposite().ordinal());
		}
		
		if(te instanceof IInventory) {
			IInventory inv = (IInventory) te;
			
			addToInventory(inv, access, toAdd, outputDirection.getOpposite().ordinal());
		}
		
		TileEntityCraneInserter inserter = null;
		
		if(toAdd.stackSize > 0) {
			inserter = (TileEntityCraneInserter) world.getTileEntity(x, y, z);
			addToInventory(inserter, null, toAdd, outputDirection.getOpposite().ordinal());
		}
		if(toAdd.stackSize > 0 && inserter != null && !inserter.destroyer) {
			EntityItem drop = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, toAdd.copy());
			world.spawnEntityInWorld(drop);
		}
	}
	
	public static ItemStack addToInventory(IInventory inv, int[] access, ItemStack toAdd, int side) {
		
		ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;
		int limit = inv.getInventoryStackLimit();
		
		int size = access == null ? inv.getSizeInventory() : access.length;
		
		for(int i = 0; i < size; i++) {
			int index = access == null ? i : access[i];
			ItemStack stack = inv.getStackInSlot(index);
			
			if(stack != null && toAdd.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(toAdd, stack) && stack.stackSize < Math.min(stack.getMaxStackSize(), limit)
					 && ((sided == null || sided.canInsertItem(index, toAdd, side)) && inv.isItemValidForSlot(index, toAdd))) {
				
				int stackLimit = Math.min(stack.getMaxStackSize(), limit);
				int amount = Math.min(toAdd.stackSize, stackLimit - stack.stackSize);
				
				stack.stackSize += amount;
				toAdd.stackSize -= amount;
				inv.markDirty();
				
				if(toAdd.stackSize == 0) {
					return null;
				}
			}
		}
		
		for(int i = 0; i < size; i++) {
			int index = access == null ? i : access[i];
			ItemStack stack = inv.getStackInSlot(index);
			
			if(stack == null && ((sided == null || sided.canInsertItem(index, toAdd, side)) && inv.isItemValidForSlot(index, toAdd))) {
				
				int amount = Math.min(toAdd.stackSize, limit);
				
				ItemStack newStack = toAdd.copy();
				newStack.stackSize = amount;
				inv.setInventorySlotContents(index, newStack);
				toAdd.stackSize -= amount;
				inv.markDirty();
				
				if(toAdd.stackSize == 0) {
					return null;
				}
			}
		}
		
		return toAdd;
	}

	@Override
	public boolean canPackageEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorPackage entity) {
		return false;
	}

	@Override
	public void onPackageEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorPackage entity) { }

    @Override
	public boolean hasComparatorInputOverride() {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		return Container.calcRedstoneFromInventory((TileEntityCraneInserter)world.getTileEntity(x, y, z));
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		this.dropContents(world, x, y, z, block, meta, 0, 21);
		super.breakBlock(world, x, y, z, block, meta);
	}
}
