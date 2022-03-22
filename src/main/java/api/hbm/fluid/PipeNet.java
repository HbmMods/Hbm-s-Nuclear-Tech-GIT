package api.hbm.fluid;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.fluid.FluidType;

public class PipeNet implements IPipeNet {
	
	private FluidType type;
	private List<IFluidConductor> links = new ArrayList();
	private List<IFluidConnector> subscribers = new ArrayList();
	
	public PipeNet(FluidType type) {
		this.type = type;
	}

	@Override
	public void joinNetworks(IPipeNet network) {
		
		if(network == this)
			return;

		for(IFluidConductor conductor : network.getLinks()) {
			conductor.setPipeNet(type, this);
			this.getLinks().add(conductor);
		}
		network.getLinks().clear();
		
		for(IFluidConnector connector : network.getSubscribers()) {
			this.subscribe(connector);
		}
		
		network.destroy();
	}

	@Override
	public List<IFluidConductor> getLinks() {
		return links;
	}

	@Override
	public List<IFluidConnector> getSubscribers() {
		return subscribers;
	}

	@Override
	public IPipeNet joinLink(IFluidConductor conductor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void leaveLink(IFluidConductor conductor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void subscribe(IFluidConnector connector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unsubscribe(IFluidConnector connector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSubscribed(IFluidConnector connector) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long transferFluid(long power) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public FluidType getType() {
		return type;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
}
