package api.hbm.internet;

import java.util.List;

import com.google.common.annotations.Beta;
import com.hbm.calc.EasyLocation;
import com.hbm.interfaces.Untested;
import com.hbm.packet.NBTInternetDataPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

@Untested
@Beta
public interface IInternet
{
	public void joinNetwork(IInternet network);
	
	public IInternet joinDataNet(IDataConductor conductor);
	public void leaveDataNet(IDataConductor conductor);
	
	public void internetSubscribe(IDataConnector connector);
	public void internetUnsubscribe(IDataConnector connector);
	public boolean isSubscribed(IDataConnector connector);
	
	public void destroyConnection();
	
	public boolean isConnectionValid();
	
	public List<IDataConductor> getInternetLinks();
	public List<IDataConnector> getInternetSubscribers();
	
	@Untested
	public long transferData(long data);
	
	public default boolean sendData(NBTTagCompound data, EasyLocation loc, World worldIn)
	{
		TileEntity te = loc.getTEAtCoord(worldIn);
		if (te == null || te.isInvalid())
			return false;
		else if (!(te instanceof IDataConnector))
			return false;
		else
		{
			IDataConnector reciever = (IDataConnector) te;
			if (!this.getInternetSubscribers().contains(reciever))
				return false;
			else
				PacketDispatcher.wrapper.sendToServer(new NBTInternetDataPacket(data, loc));
			return true;
		}
	}
	
	public static NBTTagCompound constructBaseNBT(long amount, boolean keepData)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setLong("dataAmount", amount);
		nbt.setBoolean("keepData", keepData);
		return nbt;
	}
}
