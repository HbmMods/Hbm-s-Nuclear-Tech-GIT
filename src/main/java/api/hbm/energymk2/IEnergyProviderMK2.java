package api.hbm.energymk2;

/** If it sends energy, use this */
public interface IEnergyProviderMK2 extends IEnergyHandlerMK2 {

	/** Uses up available power, default implementation has no sanity checking, make sure that the requested power is lequal to the current power */
	public default void usePower(long power) {
		this.setPower(this.getPower() - power);
	}

	public default long getProviderSpeed() {
		return this.getMaxPower();
	}
}
