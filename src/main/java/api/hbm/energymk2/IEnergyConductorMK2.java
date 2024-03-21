package api.hbm.energymk2;

public interface IEnergyConductorMK2 extends IEnergyConnectorMK2 {

	// ??? could be redundant because of nodespace, we'll see how that works out
	public PowerNetMK2 getPowerNet();
	
	public void setPowerNet(PowerNetMK2 network);
}
