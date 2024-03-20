package api.hbm.energymk2;

public interface IEnergyConductorMK2 extends IEnergyConnectorMK2 {

	public PowerNetMK2 getPowerNet();
	
	public void setPowerNet(PowerNetMK2 network);
}
