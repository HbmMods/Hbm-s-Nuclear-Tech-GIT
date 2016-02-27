package com.hbm.blocks;

import com.hbm.interfaces.IReactor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityFusionMultiblock extends TileEntity implements ISidedInventory, IReactor {

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int p_70301_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStructureValid(World world) {
		if(world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 8, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_center &&
				world.getBlock(this.xCoord + 0, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord + 0) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 2, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 8, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_center &&
				world.getBlock(this.xCoord + 0, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 1) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord + 0) == ModBlocks.fusion_motor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 2, this.zCoord - 1) == ModBlocks.fusion_motor &&
				
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord - 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord - 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 6) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 6) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord + 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord + 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord + 5) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord - 5) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord - 4) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				
				world.getBlock(this.xCoord + 8, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord + 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord + 1, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 7) == ModBlocks.fusion_heater &&
				
				world.getBlock(this.xCoord + 8, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 1, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 1, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord - 1, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord - 1, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 7) == ModBlocks.fusion_heater &&

				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord) == ModBlocks.fusion_center &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord) == ModBlocks.fusion_center &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 6, this.yCoord, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord - 3) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 6, this.yCoord, this.zCoord + 3) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord - 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord, this.zCoord - 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 4, this.yCoord, this.zCoord + 5) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord + 4) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 4, this.yCoord, this.zCoord + 5) == ModBlocks.fusion_conductor &&

				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord + 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 2) == ModBlocks.fusion_conductor &&
				
				world.getBlock(this.xCoord + 8, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 8, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_hatch &&
				world.getBlock(this.xCoord + 8, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 7, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 8, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_hatch &&
				world.getBlock(this.xCoord - 8, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord, this.zCoord - 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord, this.zCoord + 0) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 7, this.yCoord, this.zCoord + 1) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord + 8) == ModBlocks.fusion_hatch &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord - 8) == ModBlocks.fusion_hatch &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 8) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord - 7) == ModBlocks.fusion_heater &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 7) == ModBlocks.fusion_heater &&

				world.getBlock(this.xCoord, this.yCoord, this.zCoord) == ModBlocks.fusion_core)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean isCoatingValid(World world) {
		if(world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord - 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord - 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord - 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord - 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord + 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord + 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord + 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord + 1, this.zCoord - 3) == ModBlocks.block_tungsten &&
				
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord + 0) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 5, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord - 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord - 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord - 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord + 0) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord + 1) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord + 2) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 5, this.yCoord, this.zCoord + 3) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord + 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 3, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 2, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 1, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 0, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 1, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 2, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 3, this.yCoord, this.zCoord - 5) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord + 4, this.yCoord, this.zCoord - 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord, this.zCoord + 4) == ModBlocks.block_tungsten &&
				world.getBlock(this.xCoord - 4, this.yCoord, this.zCoord - 4) == ModBlocks.block_tungsten)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public boolean hasFuse() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getWaterScaled(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCoolantScaled(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPowerScaled(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeatScaled(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateEntity() {
		if(isCoatingValid(worldObj))
		{
			worldObj.setBlock(xCoord, yCoord + 3, zCoord, Blocks.dirt);
		}
		if(isStructureValid(worldObj))
		{
			worldObj.setBlock(xCoord, yCoord + 4, zCoord, Blocks.coal_block);
		}
	}

}
