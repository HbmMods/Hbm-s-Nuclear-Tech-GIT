package com.hbm.tileentity.network;

import com.hbm.entity.item.EntityMovingItem;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.conveyor.IConveyorBelt;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCraneExtractor extends TileEntityMachineBase {

	public TileEntityCraneExtractor() {
		super(20);
	}

	@Override
	public String getName() {
		return "container.craneExtractor";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote && worldObj.getTotalWorldTime() % 20 == 0) {
			
			int amount = 16;

			ForgeDirection dir = ForgeDirection.getOrientation(this.blockMetadata);
			TileEntity te = worldObj.getTileEntity(xCoord - dir.offsetX, yCoord - dir.offsetY, zCoord - dir.offsetZ);
			Block b = worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
			
			int[] access = null;
			ISidedInventory sided = null;
			
			if(te instanceof ISidedInventory) {
				sided = (ISidedInventory) te;
				access = sided.getAccessibleSlotsFromSide(dir.ordinal());
			}
			
			if(b instanceof IConveyorBelt && te instanceof IInventory) {
				IInventory inv = (IInventory) te;
				int size = access == null ? inv.getSizeInventory() : access.length;
				
				for(int i = 0; i < size; i++) {
					int index = access == null ? i : access[i];
					ItemStack stack = inv.getStackInSlot(index);
					
					if(stack != null && (sided == null || sided.canExtractItem(index, stack, dir.ordinal()))){
						stack = stack.copy();
						int toSend = Math.min(amount, stack.stackSize);
						inv.decrStackSize(index, toSend);
						stack.stackSize = toSend;
						
						EntityMovingItem moving = new EntityMovingItem(worldObj);
						Vec3 pos = Vec3.createVectorHelper(xCoord + 0.5 + dir.offsetX * 0.55, yCoord + 0.5 + dir.offsetY * 0.55, zCoord + 0.5 + dir.offsetZ * 0.55);
						Vec3 snap = ((IConveyorBelt) b).getClosestSnappingPosition(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, pos);
						moving.setPosition(snap.xCoord, snap.yCoord, snap.zCoord);
						moving.setItemStack(stack);
						worldObj.spawnEntityInWorld(moving);
						break;
					}
				}
			}
		}
	}
}
