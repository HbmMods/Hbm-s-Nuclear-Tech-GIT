package api.hbm.energy;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic IPowerNet implementation. The behavior of this demo might change inbetween releases, but the API remains the same.
 * For more consistency please implement your own IPowerNet.
 * @author hbm
 */
public class PowerNet implements IPowerNet {
	
	private boolean valid = true;
	private List<IEnergyConductor> subscribers = new ArrayList();

	@Override
	public void join(IPowerNet network) { }

	@Override
	public IPowerNet subscribe(IEnergyConductor conductor) {
		
		if(conductor.getPowerNet() != null)
			conductor.getPowerNet().unsubscribe(conductor);
		
		conductor.setPowerNet(this);
		this.getSubscribers().add(conductor);
		return this;
	}

	@Override
	public void unsubscribe(IEnergyConductor conductor) {
		conductor.setPowerNet(null);
		this.getSubscribers().remove(conductor);
	}

	@Override
	public List<IEnergyConductor> getSubscribers() {
		return null;
	}
	
	@Override
	public void destroy() {
		this.valid = false;
	}
	
	@Override
	public boolean isValid() {
		return this.valid;
	}
}
