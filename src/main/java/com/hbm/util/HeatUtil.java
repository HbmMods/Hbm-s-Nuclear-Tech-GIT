package com.hbm.util;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;

public class HeatUtil {

	/**
	 * Returns the amount of mB (with decimals!) of the supplied fluid that can be saturated with the given amount of heat
	 * @param toHeat The type of fluid that should be saturated
	 * @param heat The amount of heat used
	 * @return The amount of fluid that can be fully heated
	 */
	public static double getHeatableAmount(FluidType toHeat, double heat) {
		return heat / (toHeat.heatCap * toHeat.temperature);
	}
	
	/**
	 * @param fluid
	 * @param amount
	 * @return The total heat energy stored in the given fluid (with a delta from the fluid's temp to 0°C)
	 */
	public static double getHeatEnergy(FluidType fluid, int amount) {
		return fluid.heatCap * fluid.temperature * amount;
	}
	
	public static double getAmountAtStandardPressure(FluidType type, int pressurizedAmount) {
		return pressurizedAmount * type.compression;
	}
	
	public static double getAmountPressurized(FluidType type, int depressurizedAmount) {
		return depressurizedAmount / type.compression;
	}
	
	//brain mush, will do math later
	/*public static double boilTo(FluidTank cold, FluidTank hot, double heat) {
		int pressurizedFluid = cold.getFill();
		int pressurizedSpace = hot.getMaxFill() - hot.getFill();
		
		//how much heat energy our input tank has
		double initialHeat = getHeatEnergy(cold.getTankType(), pressurizedFluid);
	}*/
}
