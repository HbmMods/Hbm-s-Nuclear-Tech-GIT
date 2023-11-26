package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

@Deprecated
public class TileEntityMachineCoal extends TileEntityMachineBase implements ISidedInventory {
	
	public TileEntityMachineCoal() {
		super(4);
	}

	@Override
	public String getName() {
		return "container.machineCoal";
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			EntityItem drop = new EntityItem(worldObj);
			drop.setEntityItemStack(new ItemStack(ModBlocks.machine_wood_burner));
			drop.setPosition(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5);
			worldObj.spawnEntityInWorld(drop);
		}
	}
}
