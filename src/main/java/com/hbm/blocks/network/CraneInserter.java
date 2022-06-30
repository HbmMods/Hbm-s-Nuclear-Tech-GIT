package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityCraneInserter;

import api.hbm.conveyor.IConveyorItem;
import api.hbm.conveyor.IEnterableBlock;
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
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CraneInserter extends BlockCraneBase implements IEnterableBlock {

	public CraneInserter() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCraneInserter();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconDirectional = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_top");
		this.iconDirectionalUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_up");
		this.iconDirectionalDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_in_side_down");
	}

	@Override
	public boolean canEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity) {
		ForgeDirection orientation = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
		return orientation == dir;
	}

	@Override
	public void onEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity) {
		TileEntity te = world.getTileEntity(x - dir.offsetX, y - dir.offsetY, z - dir.offsetZ);
		
		if(entity == null || entity.getItemStack() == null || entity.getItemStack().stackSize <= 0) {
			return;
		}
		
		ItemStack toAdd = entity.getItemStack().copy();
		
		int[] access = null;
		
		if(te instanceof ISidedInventory) {
			ISidedInventory sided = (ISidedInventory) te;
			//access = sided.getAccessibleSlotsFromSide(dir.ordinal());
			access = masquerade(sided, dir.ordinal());
		}
		
		if(te instanceof IInventory) {
			IInventory inv = (IInventory) te;
			
			addToInventory(inv, access, toAdd, dir.ordinal());
			
			/*int limit = inv.getInventoryStackLimit();
			
			int size = access == null ? inv.getSizeInventory() : access.length;
			
			for(int i = 0; i < size; i++) {
				int index = access == null ? i : access[i];
				ItemStack stack = inv.getStackInSlot(index);
				
				if(stack != null && toAdd.isItemEqual(stack) && ItemStack.areItemStackTagsEqual(toAdd, stack) && stack.stackSize < Math.min(stack.getMaxStackSize(), limit)) {
					
					int stackLimit = Math.min(stack.getMaxStackSize(), limit);
					int amount = Math.min(toAdd.stackSize, stackLimit - stack.stackSize);
					
					stack.stackSize += amount;
					toAdd.stackSize -= amount;
					
					if(toAdd.stackSize == 0) {
						return;
					}
				}
			}
			
			for(int i = 0; i < size; i++) {
				int index = access == null ? i : access[i];
				ItemStack stack = inv.getStackInSlot(index);
				
				if(stack == null && inv.isItemValidForSlot(index, stack)) {
					
					int amount = Math.min(toAdd.stackSize, limit);
					
					ItemStack newStack = toAdd.copy();
					newStack.stackSize = amount;
					inv.setInventorySlotContents(index, newStack);
					toAdd.stackSize -= amount;
					
					if(toAdd.stackSize == 0) {
						return;
					}
				}
			}*/
		}
		
		if(toAdd != null && toAdd.stackSize > 0) {
			addToInventory((TileEntityCraneInserter) world.getTileEntity(x, y, z), null, toAdd, dir.ordinal());
		}
		if(toAdd != null && toAdd.stackSize > 0) {
			EntityItem drop = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, toAdd.copy());
			world.spawnEntityInWorld(drop);
		}
	}
	
	public static int[] masquerade(ISidedInventory sided, int side) {
		
		if(sided instanceof TileEntityFurnace) {
			return new int[] {1, 0};
		}
		
		return sided.getAccessibleSlotsFromSide(side);
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
	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta > 1 && side == 1) {
			if(meta == 2) return 3;
			if(meta == 3) return 0;
			if(meta == 4) return 1;
			if(meta == 5) return 2;
		}
		
		return 0;
	}
	
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
