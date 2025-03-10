package api.hbm.fluidmk2;

import com.hbm.uninos.NodeNet;

public class FluidNetMK2 extends NodeNet<IFluidReceiverMK2, IFluidProviderMK2, FluidNode> {

	public long fluidTracker = 0L;
	
	@Override
	public void update() {
		
	}
}
