package api.hbm.fluidmk2;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;

/**
 * IFluidReceiverMK2 with standard implementation for transfer and demand getter.
 * @author hbm
 */
public interface IFluidStandardReceiverMK2 extends IFluidReceiverMK2 {
	
	public FluidTank[] getReceivingTanks();

	@Override
	public default long getDemand(FluidType type, int pressure) {
		long amount = 0;
		for(FluidTank tank : getReceivingTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) amount += (tank.getMaxFill() - tank.getFill());
		}
		return amount;
	}

	@Override
	public default long transferFluid(FluidType type, int pressure, long amount) {
		int tanks = 0;
		for(FluidTank tank : getReceivingTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) tanks++;
		}
		if(tanks > 1) {
			int firstRound = (int) Math.floor((double) amount / (double) tanks);
			for(FluidTank tank : getReceivingTanks()) {
				if(tank.getTankType() == type && tank.getPressure() == pressure) {
					int toAdd = Math.min(firstRound, tank.getMaxFill() - tank.getFill());
					tank.setFill(tank.getFill() + toAdd);
					amount -= toAdd;
				}
			}
		}
		if(amount > 0) for(FluidTank tank : getReceivingTanks()) {
			if(tank.getTankType() == type && tank.getPressure() == pressure) {
				int toAdd = (int) Math.min(amount, tank.getMaxFill() - tank.getFill());
				tank.setFill(tank.getFill() + toAdd);
				amount -= toAdd;
			}
		}
		return amount;
	}

	@Override
	public default int[] getReceivingPressureRange(FluidType type) {
		int lowest = HIGHEST_VALID_PRESSURE;
		int highest = 0;
		
		for(FluidTank tank : getReceivingTanks()) {
			if(tank.getTankType() == type) {
				if(tank.getPressure() < lowest) lowest = tank.getPressure();
				if(tank.getPressure() > highest) highest = tank.getPressure();
			}
		}
		
		return lowest <= highest ? new int[] {lowest, highest} : DEFAULT_PRESSURE_RANGE;
	}

	@Override
	public default long getReceiverSpeed(FluidType type, int pressure) {
		return 1_000_000_000;
	}
}
