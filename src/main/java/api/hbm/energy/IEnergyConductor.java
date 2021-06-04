package api.hbm.energy;

/**
 * For compatible cables with no buffer, using the IPowertNet. You can make your own cables with IEnergyConnector as well, but they won't join their power network.
 * @author hbm
 */
public interface IEnergyConductor extends IEnergyConnector {

	public IPowerNet getPowerNet();
	
	public void setPowerNet(IPowerNet network);
}
