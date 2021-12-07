package api.hbm.energy;

public interface IEnergyGenerator extends IEnergyUser {

	/**
	 * Standard implementation for machines that can only send energy but never receive it.
	 * @param power
	 */
	@Override
	public default long transferPower(long power) {
		return power;
	}
}
