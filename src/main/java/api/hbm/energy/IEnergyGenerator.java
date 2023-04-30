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

	/* should stop making non-receivers from interfering by applying their weight which doesn't even matter */
	@Override
	public default long getTransferWeight() {
		return 0;
	}
}
