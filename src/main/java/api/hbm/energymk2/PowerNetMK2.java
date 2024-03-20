package api.hbm.energymk2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PowerNetMK2 {
	
	private boolean valid = true;
	private HashMap<Integer, IEnergyConductorMK2> links = new HashMap();
	private HashMap<Integer, Integer> proxies = new HashMap();
	private List<IEnergyConnectorMK2> subscribers = new ArrayList();

	public boolean isSubscribed(IEnergyReceiverMK2 receiver) {
		return false; //TBI
	}

	public void subscribe(IEnergyReceiverMK2 receiver) {
		//TBI
	}
}
