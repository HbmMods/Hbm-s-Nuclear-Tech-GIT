package api.hbm.fluidmk2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.uninos.NodeNet;
import com.hbm.util.Tuple.Pair;

import api.hbm.energymk2.IEnergyReceiverMK2.ConnectionPriority;

public class FluidNetMK2 extends NodeNet<IFluidReceiverMK2, IFluidProviderMK2, FluidNode> {

	public long fluidTracker = 0L;
	
	protected static int timeout = 3_000;
	protected static long currentTime = 0;
	protected FluidType type;
	
	public FluidNetMK2(FluidType type) {
		this.type = type;
		for(int i = 0; i < IFluidUserMK2.HIGHEST_VALID_PRESSURE + 1; i++) providers[i] = new ArrayList();
		for(int i = 0; i < IFluidUserMK2.HIGHEST_VALID_PRESSURE + 1; i++) for(int j = 0; j < ConnectionPriority.values().length; j++) receivers[i][j] = new ArrayList();
	}
	
	@Override public void resetTrackers() { this.fluidTracker = 0; }
	
	@Override
	public void update() {
		
		if(providerEntries.isEmpty()) return;
		if(receiverEntries.isEmpty()) return;
		currentTime = System.currentTimeMillis();

		setupFluidProviders();
		setupFluidReceivers();
		
		cleanUp();
	}
	
	//this sucks ass, but it makes the code just a smidge more structured
	public long[] fluidAvailable = new long[IFluidUserMK2.HIGHEST_VALID_PRESSURE + 1];
	public List<Pair<IFluidProviderMK2, Long>>[] providers = new ArrayList[IFluidUserMK2.HIGHEST_VALID_PRESSURE + 1];
	public long[][] fluidDemand = new long[IFluidUserMK2.HIGHEST_VALID_PRESSURE + 1][ConnectionPriority.values().length];
	public List<Pair<IFluidReceiverMK2, Long>>[][] receivers = new ArrayList[IFluidUserMK2.HIGHEST_VALID_PRESSURE + 1][ConnectionPriority.values().length];
	
	public void setupFluidProviders() {
		Iterator<Entry<IFluidProviderMK2, Long>> iterator = providerEntries.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Entry<IFluidProviderMK2, Long> entry = iterator.next();
			if(currentTime - entry.getValue() > timeout) { iterator.remove(); continue; }
			IFluidProviderMK2 provider = entry.getKey();
			int[] pressureRange = provider.getProvidingPressureRange(type);
			for(int p = pressureRange[0]; p <= pressureRange[1]; p++) {
				long available = Math.min(provider.getFluidAvailable(type, p), provider.getProviderSpeed(type, p));
				providers[p].add(new Pair(provider, available));
				fluidAvailable[p] += available;
			}
		}
	}
	
	public void setupFluidReceivers() {
		Iterator<Entry<IFluidReceiverMK2, Long>> iterator = receiverEntries.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Entry<IFluidReceiverMK2, Long> entry = iterator.next();
			if(currentTime - entry.getValue() > timeout) { iterator.remove(); continue; }
			IFluidReceiverMK2 receiver = entry.getKey();
			int[] pressureRange = receiver.getReceivingPressureRange(type);
			for(int p = pressureRange[0]; p <= pressureRange[1]; p++) {
				long required = Math.min(receiver.getDemand(type, p), receiver.getReceiverSpeed(type, p));
				int priority = receiver.getFluidPriority().ordinal();
				receivers[p][priority].add(new Pair(receiver, required));
				fluidDemand[p][priority] += required;
			}
		}
	}
	
	public void cleanUp() {
		for(int i = 0; i < IFluidUserMK2.HIGHEST_VALID_PRESSURE + 1; i++) {
			fluidAvailable[i] = 0;
			providers[i].clear();
			
			for(int j = 0; j < ConnectionPriority.values().length; j++) {
				fluidDemand[i][j] = 0;
				receivers[i][j].clear();
			}
		}
	}
}
