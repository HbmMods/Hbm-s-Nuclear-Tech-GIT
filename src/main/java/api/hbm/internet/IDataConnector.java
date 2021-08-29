package api.hbm.internet;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public interface IDataConnector
{
	public long transferData(long data);
	
	public void recieveData(NBTTagCompound data);
	
	public boolean dataCanConnect(ForgeDirection dir);
	
	public long getDataAmount();
	
	public long getMaxStorage();
	
	public default Block toBlock()
	{
		return (Block) this;
	}
}
