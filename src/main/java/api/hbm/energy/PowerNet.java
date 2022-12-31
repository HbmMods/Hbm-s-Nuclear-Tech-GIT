package api.hbm.energy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.config.GeneralConfig;

import api.hbm.energy.IEnergyConnector.ConnectionPriority;
import net.minecraft.tileentity.TileEntity;

/**
 * Basic IPowerNet implementation. The behavior of this demo might change inbetween releases, but the API remains the same.
 * For more consistency please implement your own IPowerNet.
 * @author hbm
 */
public class PowerNet implements IPowerNet {
	
	private boolean valid = true;
	private HashMap<Integer, IEnergyConductor> links = new HashMap();
	private HashMap<Integer, Integer> proxies = new HashMap();
	private List<IEnergyConnector> subscribers = new ArrayList();

	@Override
	public void joinNetworks(IPowerNet network) {
		
		if(network == this)
			return; //wtf?!

		for(IEnergyConductor conductor : network.getLinks()) {
			joinLink(conductor);
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
		int identity = conductor.getIdentity();
		this.links.put(identity, conductor);
		
		if(conductor.hasProxies()) {
			for(Integer i : conductor.getProxies()) {
				this.proxies.put(i, identity);
			}
		}
		
		return this;
	}

	@Override
	public void leaveLink(IEnergyConductor conductor) {
		conductor.setPowerNet(null);
		int identity = conductor.getIdentity();
		this.links.remove(identity);
		
		if(conductor.hasProxies()) {
			for(Integer i : conductor.getProxies()) {
				this.proxies.remove(i);
			}
		}
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
		List<IEnergyConductor> linkList = new ArrayList();
		linkList.addAll(this.links.values());
		return linkList;
	}

	public HashMap<Integer, Integer> getProxies() {
		HashMap<Integer, Integer> proxyCopy = new HashMap(proxies);
		return proxyCopy;
	}

	@Override
	public List<IEnergyConnector> getSubscribers() {
		return this.subscribers;
	}
	
	@Override
	public void destroy() {
		this.valid = false;
		this.subscribers.clear();
		
		for(IEnergyConductor link : this.links.values()) {
			link.setPowerNet(null);
		}
		
		this.links.clear();
	}
	
	@Override
	public boolean isValid() {
		return this.valid;
	}
	
	public long lastCleanup = System.currentTimeMillis();
	
	@Override
	public long transferPower(long power) {
		
		if(lastCleanup + 45 < System.currentTimeMillis()) {
			cleanup(this.subscribers);
			lastCleanup = System.currentTimeMillis();
		}
		
		return fairTransfer(this.subscribers, power);
	}
	
	public static void cleanup(List<IEnergyConnector> subscribers) {

		subscribers.removeIf(x -> 
			x == null || !(x instanceof TileEntity) || ((TileEntity)x).isInvalid() || !x.isLoaded()
		);
	}
	
	public static long fairTransfer(List<IEnergyConnector> subscribers, long power) {
		
		if(subscribers.isEmpty())
			return power;
		
		ConnectionPriority[] priorities = new ConnectionPriority[] {ConnectionPriority.HIGH, ConnectionPriority.NORMAL, ConnectionPriority.LOW};
		
		for(ConnectionPriority p : priorities) {
			
			List<IEnergyConnector> subList = new ArrayList();
			subscribers.forEach(x -> {
				if(x.getPriority() == p) {
					subList.add(x);
				}
			});
			
			if(subList.isEmpty())
				continue;
			
			List<Long> weight = new ArrayList();
			long totalReq = 0;
			
			for(IEnergyConnector con : subList) {
				long req = con.getTransferWeight();
				weight.add(req);
				totalReq += req;
			}
			
			if(totalReq == 0)
				continue;
			
			long totalGiven = 0;
			
			for(int i = 0; i < subList.size(); i++) {
				IEnergyConnector con = subList.get(i);
				long req = weight.get(i);
				double fraction = (double)req / (double)totalReq;
				
				long given = (long) Math.floor(fraction * power);
				
				totalGiven += (given - con.transferPower(given));
			}
			
			power -= totalGiven;
		}
		
		return power;
	}

	@Override
	public void reevaluate() {
		
		if(!GeneralConfig.enableReEval) {
			this.destroy();
			return;
		}

		HashMap<Integer, IEnergyConductor> copy = new HashMap(links);
		HashMap<Integer, Integer> proxyCopy = new HashMap(proxies);
		
		for(IEnergyConductor link : copy.values()) {
			this.leaveLink(link);
		}
		
		for(IEnergyConductor link : copy.values()) {
			
			link.setPowerNet(null);
			link.reevaluate(copy, proxyCopy);
			
			if(link.getPowerNet() == null) {
				link.setPowerNet(new PowerNet().joinLink(link));
			}
		}
	}
}
