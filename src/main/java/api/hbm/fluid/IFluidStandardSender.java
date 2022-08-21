package api.hbm.fluid;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTank;

/**
 * Uses default implementation to make the underlying interfaces easier to use for the most common fluid users.
 * Only handles a single output tank of the same type.
 * Uses standard FluidTanks which use int32.
 * Don't use this as part of the API!
 * @author hbm
 *
 */
public interface IFluidStandardSender extends IFluidUser {

	public FluidTank[] getSendingTanks();
	
	@Override
	public default long getTotalFluidForSend(FluidType type) {
		
		for(FluidTank tank : getSendingTanks()) {
			if(tank.getTankType() == type) {
				return tank.getFill();
			}
		}
		
		return 0;
	}
	
	@Override
	public default void removeFluidForTransfer(FluidType type, long amount) {
		
		for(FluidTank tank : getSendingTanks()) {
			if(tank.getTankType() == type) {
				tank.setFill(tank.getFill() - (int) amount);
				return;
			}
		}
	}

	@Override
	public default long transferFluid(FluidType type, long fluid) {
		return fluid;
	}

	@Override
	public default long getDemand(FluidType type) {
		return 0;
	}
}
