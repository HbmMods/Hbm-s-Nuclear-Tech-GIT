package com.hbm.inventory.fluid.tank;

import java.util.Random;

import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.machine.ItemInfiniteFluid;

import net.minecraft.item.ItemStack;

public class FluidLoaderInfinite extends FluidLoadingHandler {
	
	private static Random rand = new Random();

	@Override
	public boolean fillItem(ItemStack[] slots, int in, int out, FluidTank tank) {
		
		if(slots[in] == null || !(slots[in].getItem() instanceof ItemInfiniteFluid)) return false;
		
		ItemInfiniteFluid item = (ItemInfiniteFluid) slots[in].getItem();
		
		if(!item.allowPressure(tank.pressure)) return false;
		if(item.getType() != null && tank.type != item.getType()) return false;
		if(!item.canOperate(slots[in])) return false;
		
		if(item.getChance() <= 1 || rand.nextInt(item.getChance()) == 0) {
			tank.setFill(Math.max(tank.getFill() - item.getAmount(), 0));
		}
		
		return true;
	}

	@Override
	public boolean emptyItem(ItemStack[] slots, int in, int out, FluidTank tank) {
		
		if(slots[in] == null || !(slots[in].getItem() instanceof ItemInfiniteFluid) || tank.getTankType() == Fluids.NONE) return false;
		
		ItemInfiniteFluid item = (ItemInfiniteFluid) slots[in].getItem();
		
		if(item.getType() != null && tank.type != item.getType()) return false;
		if(!item.canOperate(slots[in])) return false;
		
		if(item.getChance() <= 1 || rand.nextInt(item.getChance()) == 0) {
			tank.setFill(Math.min(tank.getFill() + item.getAmount(), tank.getMaxFill()));
		}
		
		return true;
	}
}
