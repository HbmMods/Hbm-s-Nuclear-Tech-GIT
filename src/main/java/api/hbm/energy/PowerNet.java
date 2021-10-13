package api.hbm.energy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;

/**
 * Basic IPowerNet implementation. The behavior of this demo might change inbetween releases, but the API remains the same.
 * For more consistency please implement your own IPowerNet.
 * @author hbm
 */
public class PowerNet implements IPowerNet {
	
	private boolean valid = true;
	private List<IEnergyConductor> links = new ArrayList();
	private List<IEnergyConnector> subscribers = new ArrayList();

	@Override
	public void joinNetworks(IPowerNet network) {
		
		if(network == this)
			return; //wtf?!

		for(IEnergyConductor conductor : network.getLinks()) {
			conductor.setPowerNet(this);
			this.getLinks().add(conductor);
		}
		network.getLinks().clear();
		
		for(IEnergyConnector connector : network.getSubscribers()) {
			this.subscribe(connector);
		}
		
		network.destroy();
	}

	@Override
	public IPowerNet joinLink(IEnergyConductor conductor) {
		
		if(conductor.getPowerNet() != null)
			conductor.getPowerNet().leaveLink(conductor);
		
		conductor.setPowerNet(this);
		this.getLinks().add(conductor);
		return this;
	}

	@Override
	public void leaveLink(IEnergyConductor conductor) {
		conductor.setPowerNet(null);
		this.getLinks().remove(conductor);
	}

	@Override
	public void subscribe(IEnergyConnector connector) {
		this.subscribers.add(connector);
	}

	@Override
	public void unsubscribe(IEnergyConnector connector) {
		this.subscribers.remove(connector);
	}

	@Override
	public boolean isSubscribed(IEnergyConnector connector) {
		return this.subscribers.contains(connector);
	}

	@Override
	public List<IEnergyConductor> getLinks() {
		return this.links;
	}

	@Override
	public List<IEnergyConnector> getSubscribers() {
		return this.subscribers;
	}
	
	@Override
	public void destroy() {
		this.valid = false;
		
		this.subscribers.clear();
		
		for(IEnergyConductor link : this.links) {
			link.setPowerNet(null);
		}
		
		this.links.clear();
	}
	
	@Override
	public boolean isValid() {
		return this.valid;
	}
	
	@Override
	public long transferPower(long power) {
		
		this.subscribers.removeIf(x -> 
			x == null || !(x instanceof TileEntity) || ((TileEntity)x).isInvalid()
		);
		
		if(this.subscribers.isEmpty())
			return power;
		
		List<Long> weight = new ArrayList();
		long totalReq = 0;
		
		for(IEnergyConnector con : this.subscribers) {
			long req = Math.max(con.getMaxPower() - con.getPower(), 0);
			weight.add(req);
			totalReq += req;
		}
		
		long totalGiven = 0;
		
		for(int i = 0; i < this.subscribers.size(); i++) {
			IEnergyConnector con = this.subscribers.get(i);
			long req = weight.get(i);
			double fraction = (double)req / (double)totalReq;
			
			long given = (int) Math.floor(fraction * power);
			
			totalGiven += (given - con.transferPower(given));
		}
		
		return power - totalGiven;
	}
}
