package api.hbm.energy;

import java.math.BigInteger;
import java.util.List;

/**
 * Not mandatory to use, but making your cables IPowerNet-compliant will allow them to connect to NTM cables.
 * Cables will still work without it as long as they implement IEnergyConductor (or even IEnergyConnector) + self-built network code
 * @author hbm
 */
public interface IPowerNet {
	
	public void joinNetworks(IPowerNet network);

	public IPowerNet joinLink(IEnergyConductor conductor);
	public void leaveLink(IEnergyConductor conductor);

	public void subscribe(IEnergyConnector connector);
	public void unsubscribe(IEnergyConnector connector);
	public boolean isSubscribed(IEnergyConnector connector);

	public void destroy();
	
	/**
	 * When a link is removed, instead of destroying the network, causing it to be recreated from currently loaded conductors,
	 * we re-evaluate it, creating new nets based on the previous links.
	 */
	public void reevaluate();
	
	public boolean isValid();

	public List<IEnergyConductor> getLinks();
	public List<IEnergyConnector> getSubscribers();
	
	public long transferPower(long power);
	public BigInteger getTotalTransfer();
}
