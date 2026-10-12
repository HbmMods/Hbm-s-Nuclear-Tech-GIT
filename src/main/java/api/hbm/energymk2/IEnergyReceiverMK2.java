package api.hbm.energymk2;

/** If it receives energy, use this */
public interface IEnergyReceiverMK2 extends IEnergyHandlerMK2 {

	public default long transferPower(long power) {
		if(power + this.getPower() <= this.getMaxPower()) {
			this.setPower(power + this.getPower());
			return 0;
		}
		long capacity = this.getMaxPower() - this.getPower();
		long overshoot = power - capacity;
		this.setPower(this.getMaxPower());
		return overshoot;
	}

	public default long getReceiverSpeed() {
		return this.getMaxPower();
	}

	public enum ConnectionPriority {
		LOWEST,
		LOW,
		NORMAL,
		HIGH,
		HIGHEST
	}
	
	public default ConnectionPriority getPriority() {
		return ConnectionPriority.NORMAL;
	}
}
