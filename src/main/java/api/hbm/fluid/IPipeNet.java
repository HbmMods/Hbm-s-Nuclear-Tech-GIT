package api.hbm.fluid;

import java.util.List;

import com.hbm.inventory.fluid.FluidType;

public interface IPipeNet {
	
	public void joinNetworks(IPipeNet network);

	public List<IFluidConductor> getLinks();
	public List<IFluidConnector> getSubscribers();

	public IPipeNet joinLink(IFluidConductor conductor);
	public void leaveLink(IFluidConductor conductor);

	public void subscribe(IFluidConnector connector);
	public void unsubscribe(IFluidConnector connector);
	public boolean isSubscribed(IFluidConnector connector);

	public void destroy();
	
	public boolean isValid();
	
	public long transferFluid(long power);
	public FluidType getType();
}
