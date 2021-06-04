package api.hbm.energy;

public interface IEnergyConsumer {
	
	void setPower(long i);
	
	long getPower();
	
	long getMaxPower();
}
