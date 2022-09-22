package com.hbm.tileentity.machine;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats.MaterialStack;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityFoundryBasin extends TileEntityFoundryBase {

	@Override
	public int getCapacity() {
		return MaterialShapes.BLOCK.q(1);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		//TODO: cool off
	}

	//TODO: move to block
	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		
		if(side != ForgeDirection.UP) return false; //reject from any direction other than the top
		if(this.type != null && this.type != stack.material) return false; //reject if there's already a different material
		
		for(String name : stack.material.names) {
			String od = "block" + name;
			
			if(!OreDictionary.getOres(od).isEmpty()) {
				return true; //at least one block for this material? return TRUE
			}
		}
		
		return false; //no OD match -> no pouring
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		
		if(stack.amount + this.amount <= this.getCapacity()) {
			this.amount += stack.amount;
			return null;
		}
		
		int required = this.getCapacity() - this.amount;
		this.amount = this.getCapacity();
		
		stack.amount -= required;
		
		return stack;
	}

	@Override public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) { return stack; }
}
