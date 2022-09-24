package com.hbm.tileentity.machine;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityFoundryMold extends TileEntityFoundryBase implements IRenderFoundry {

	@Override
	public int getCapacity() {
		return MaterialShapes.INGOT.q(1);
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote) {
			
			if(this.lastType != this.type || this.lastAmount != this.amount) {
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				this.lastType = this.type;
				this.lastAmount = this.amount;
			}
		}
		
		//TODO: cool off
	}

	@Override
	public boolean canAcceptPartialPour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		
		if(side != ForgeDirection.UP) return false; //reject from any direction other than the top
		if(this.type != null && this.type != stack.material) return false; //reject if there's already a different material
		if(this.amount >= this.getCapacity()) return false; //reject if the buffer is already full
		
		for(String name : stack.material.names) {
			String od = "ingot" + name;
			
			if(!OreDictionary.getOres(od).isEmpty()) {
				return true; //at least one block for this material? return TRUE
			}
		}
		
		return false; //no OD match -> no pouring
	}

	@Override
	public MaterialStack pour(World world, int x, int y, int z, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		
		if(this.type == null) {
			this.type = stack.material;
		}
		
		if(stack.amount + this.amount <= this.getCapacity()) {
			this.amount += stack.amount;
			return null;
		}
		
		int required = this.getCapacity() - this.amount;
		this.amount = this.getCapacity();
		
		stack.amount -= required;
		
		return stack;
	}

	@Override
	public boolean canAcceptPartialFlow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		
		if(this.type != null && this.type != stack.material) return false; //reject if there's already a different material
		if(this.amount >= this.getCapacity()) return false; //reject if the buffer is already full
		
		for(String name : stack.material.names) {
			String od = "ingot" + name;
			
			if(!OreDictionary.getOres(od).isEmpty()) {
				return true; //at least one block for this material? return TRUE
			}
		}
		
		return false; //no OD match -> no pouring
	}
	
	@Override
	public MaterialStack flow(World world, int x, int y, int z, ForgeDirection side, MaterialStack stack) {
		
		if(this.type == null) {
			this.type = stack.material;
		}
		
		if(stack.amount + this.amount <= this.getCapacity()) {
			this.amount += stack.amount;
			return null;
		}
		
		int required = this.getCapacity() - this.amount;
		this.amount = this.getCapacity();
		
		stack.amount -= required;
		
		return stack;
	}

	@Override
	public boolean shouldRender() {
		return this.type != null && this.amount > 0;
	}

	@Override
	public double getLevel() {
		return 0.125 + this.amount * 0.25D / this.getCapacity();
	}

	@Override
	public NTMMaterial getMat() {
		return this.type;
	}

	@Override public double minX() { return 0.125D; }
	@Override public double maxX() { return 0.875D; }
	@Override public double minZ() { return 0.125D; }
	@Override public double maxZ() { return 0.875D; }
}
