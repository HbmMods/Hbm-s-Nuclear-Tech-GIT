package api.hbm.fluid;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.hbm.inventory.fluid.FluidType;

import net.minecraft.tileentity.TileEntity;

public class PipeNet implements IPipeNet {

	private boolean valid = true;
	private FluidType type;
	private List<IFluidConductor> links = new ArrayList();
	private HashSet<IFluidConnector> subscribers = new HashSet();

	public static List<PipeNet> trackingInstances = null;
	protected BigInteger totalTransfer = BigInteger.ZERO;
	public List<String> debug = new ArrayList();

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
	public HashSet<IFluidConnector> getSubscribers() {
		return subscribers;
	}

	@Override
	public IPipeNet joinLink(IFluidConductor conductor) {

		if(conductor.getPipeNet(type) != null)
			conductor.getPipeNet(type).leaveLink(conductor);

		conductor.setPipeNet(type, this);
		this.links.add(conductor);
		return this;
	}

	@Override
	public void leaveLink(IFluidConductor conductor) {
		conductor.setPipeNet(type, null);
		this.links.remove(conductor);
	}

	@Override
	public void subscribe(IFluidConnector connector) {
		this.subscribers.add(connector);
	}

	@Override
	public void unsubscribe(IFluidConnector connector) {
		this.subscribers.remove(connector);
	}

	@Override
	public boolean isSubscribed(IFluidConnector connector) {
		return this.subscribers.contains(connector);
	}

	@Override
	public long transferFluid(long fill, int pressure) {

		subscribers.removeIf(x ->
			x == null || !(x instanceof TileEntity) || ((TileEntity)x).isInvalid() || !x.isLoaded()
		);

		if(this.subscribers.isEmpty())
			return fill;

		trackingInstances = new ArrayList();
		trackingInstances.add(this);
		List<IFluidConnector> subList = new ArrayList(subscribers);
		return fairTransfer(subList, type, pressure, fill);
	}

	public static long fairTransfer(List<IFluidConnector> subList, FluidType type, int pressure, long fill) {

		if(fill <= 0) return 0;

		List<Long> weight = new ArrayList();
		long totalReq = 0;

		for(IFluidConnector con : subList) {
			long req = con.getDemand(type, pressure);
			weight.add(req);
			totalReq += req;
		}

		if(totalReq == 0)
			return fill;

		long totalGiven = 0;

		for(int i = 0; i < subList.size(); i++) {
			IFluidConnector con = subList.get(i);
			long req = weight.get(i);
			double fraction = (double)req / (double)totalReq;

			long given = (long) Math.floor(fraction * fill);

			if(given > 0) {

				totalGiven += (given - con.transferFluid(type, pressure, given));

				if(con instanceof TileEntity) {
					TileEntity tile = (TileEntity) con;
					tile.getWorldObj().markTileEntityChunkModified(tile.xCoord, tile.yCoord, tile.zCoord, tile);
				}

				/* debug code
				if(trackingInstances != null) {
					for(int j = 0; j < trackingInstances.size(); j++) {
						PipeNet net = trackingInstances.get(j);
						SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
						sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
						log(net, sdf.format(new Date(System.currentTimeMillis())) + " Sending " + given + "mB to " + conToString(con));
					}
				}
				*/
			}
		}

		if(trackingInstances != null) {

			for(int i = 0; i < trackingInstances.size(); i++) {
				PipeNet net = trackingInstances.get(i);
				net.totalTransfer = net.totalTransfer.add(BigInteger.valueOf(totalGiven));
			}
		}

		return fill - totalGiven;
	}

	@Override
	public FluidType getType() {
		return type;
	}

	@Override
	public void destroy() {
		this.valid = false;
		this.subscribers.clear();

		for(IFluidConductor con : this.links)
			con.setPipeNet(type, null);

		this.links.clear();
	}

	@Override
	public boolean isValid() {
		return this.valid;
	}

	@Override
	public BigInteger getTotalTransfer() {
		return this.totalTransfer;
	}

	public static void log(PipeNet net, String msg) {
		net.debug.add(msg);

		while(net.debug.size() > 50) {
			net.debug.remove(0);
		}
	}

	public static String conToString(IFluidConnector con) {

		if(con instanceof TileEntity) {
			TileEntity tile = (TileEntity) con;
			return tile.getClass().getSimpleName() + " @ " + tile.xCoord + "/" + tile.yCoord + "/" + tile.zCoord;
		}

		return "" + con;
	}
}
