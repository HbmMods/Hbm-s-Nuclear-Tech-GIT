package api.hbm.internet;

import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.Untested;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.tileentity.TileEntity;
@Beta
public class Internet implements IInternet
{
	private boolean valid = false;
	private ArrayList<IDataConductor> links = new ArrayList<>();
	private ArrayList<IDataConnector> subscribers = new ArrayList<>();
	
	@Override
	public void joinNetwork(IInternet network)
	{
		if (network == this)
			return;
		
		for (IDataConductor conductor : network.getInternetLinks())
		{
			conductor.setDataNetwork(this);
			getInternetLinks().add(conductor);
		}
		
		network.getInternetLinks().clear();
		
		for (IDataConnector connector : network.getInternetSubscribers())
			internetSubscribe(connector);
		
		network.destroyConnection();
	}

	@Override
	public IInternet joinDataNet(IDataConductor conductor)
	{
		if (conductor.getDataNetwork() != null)
			conductor.getDataNetwork().leaveDataNet(conductor);
		
		conductor.setDataNetwork(this);
		getInternetLinks().add(conductor);
		return this;
	}

	@Override
	public void leaveDataNet(IDataConductor conductor)
	{
		conductor.setDataNetwork(null);
		getInternetLinks().remove(conductor);
	}

	@Override
	public void internetSubscribe(IDataConnector connector)
	{
		subscribers.add(connector);
	}

	@Override
	public void internetUnsubscribe(IDataConnector connector)
	{
		subscribers.remove(connector);
	}

	@Override
	public boolean isSubscribed(IDataConnector connector)
	{
		return subscribers.contains(connector);
	}

	@Override
	public void destroyConnection()
	{
		valid = false;
		subscribers.clear();
		for (IDataConductor conductor : links)
			conductor.setDataNetwork(null);
		
		links.clear();
	}

	@Override
	public boolean isConnectionValid()
	{
		return valid;
	}

	@Override
	public List<IDataConductor> getInternetLinks()
	{
		return links;
	}

	@Override
	public List<IDataConnector> getInternetSubscribers()
	{
		return subscribers;
	}
	
	@Untested
	@Override
	public long transferData(long data)
	{
		subscribers.removeIf(x -> x == null || !(x instanceof TileEntity) || ((TileEntity)x).isInvalid());
		
		if (subscribers.isEmpty())
			return data;
		
		ArrayList<Long> weight = new ArrayList<>();
		long totalReq = 0;
		
		for (IDataConnector connector : subscribers)
		{
			long req = Math.max(connector.getMaxStorage() - connector.getDataAmount(), 0);
			weight.add(req);
			totalReq += req;
		}
		
		long totalGiven = 0;
		
		for (int i = 0; i < subscribers.size(); i++)
		{
			IDataConnector connector = subscribers.get(i);
			long req = weight.get(i);
			double frac = (double) req / (double) totalReq;
			long given = (long) Math.floor(frac * data);
			totalGiven += given - connector.transferData(data);
		}
		
		return data - totalGiven;
	}
	
}
