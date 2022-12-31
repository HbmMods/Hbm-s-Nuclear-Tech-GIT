package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.item.ItemStack;

public interface IFillableItem {

	/** Whether this stack can be filled with this type. Not particularly useful for normal operations */
	public boolean acceptsFluid(FluidType type, ItemStack stack);
	/** Tries to fill the stack, returns the remainder that couldn't be added */
	public int tryFill(FluidType type, int amount, ItemStack stack);
	/** Whether this stack can fill tiles with this type. Not particularly useful for normal operations */
	public boolean providesFluid(FluidType type, ItemStack stack);
	/** Provides fluid with the maximum being the requested amount */
	public int tryEmpty(FluidType type, int amount, ItemStack stack);
}
