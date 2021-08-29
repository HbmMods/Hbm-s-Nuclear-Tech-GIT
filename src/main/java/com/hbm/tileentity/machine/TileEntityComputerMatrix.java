package com.hbm.tileentity.machine;

import java.util.ArrayList;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.Untested;
import com.hbm.tileentity.TileEntityTickingBase;

import api.hbm.internet.IDataConductor;
import api.hbm.internet.IDataConductor;
import api.hbm.internet.IInternet;
import api.hbm.internet.Internet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
@Untested
@Beta
public class TileEntityComputerMatrix extends TileEntityTickingBase implements IDataConductor
{
	private int strength = 0;
	private IInternet network;
	public TileEntityComputerMatrix(int sIn)
	{
		strength = sIn;
	}

	@Override
	public String getInventoryName()
	{
		return new String();
	}

	@Override
	public void updateEntity()
	{
		// TODO Auto-generated method stub
		if (!worldObj.isRemote && canUpdate())
		{
			for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
			{
				TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
				
				if (te instanceof IDataConductor)
				{
					IDataConductor conductor = (IDataConductor) te;
					
					if (getDataNetwork() == null && conductor.getDataNetwork() == null)
						conductor.getDataNetwork().joinDataNet(this);
					
					if (getDataNetwork() != null && conductor.getDataNetwork() != null && getDataNetwork() != conductor.getDataNetwork())
						conductor.getDataNetwork().joinNetwork(getDataNetwork());
				}
			}
			
			if (getDataNetwork() == null)
				new Internet().joinDataNet(this);
		}
	}
	@Untested
	public int[] getNetStats()
	{
		int[] stats = new int[2];
		if (getDataNetwork() == null)
		{
			stats[0] = strength;
			stats[1] = 1;
		}
		else
		{
			ArrayList<TileEntityComputerMatrix> compList = new ArrayList<>();
			compList.add(this);
			for (IDataConductor test : network.getInternetLinks())
				if (test instanceof TileEntityComputerMatrix)
					compList.add((TileEntityComputerMatrix) test);
			stats[1] = compList.size();
			for (TileEntityComputerMatrix te : compList)
				stats[0] += te.getStrength();
		}
		return stats;
	}
	public int getCollectiveStrength(World worldIn)
	{
		return getNetStats()[0];
	}
	
	public int getCount(World worldIn)
	{
		return getNetStats()[1];
	}
	
	@Override
	public void invalidate()
	{
		super.invalidate();
		if (!worldObj.isRemote && network != null)
			network.destroyConnection();
	}
	
	@Override
	public boolean canUpdate()
	{
		return (network == null || network.isConnectionValid()) && !isInvalid();
	}

	@Override
	public long transferData(long data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void recieveData(NBTTagCompound data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean dataCanConnect(ForgeDirection dir)
	{
		return true;
	}

	@Override
	public long getDataAmount()
	{
		return 0;
	}

	@Override
	public long getMaxStorage()
	{
		return 0;
	}
	
	public int getStrength()
	{
		return strength;
	}

	@Override
	public IInternet getDataNetwork()
	{
		return network;
	}

	@Override
	public void setDataNetwork(IInternet network)
	{
		this.network = network;
	}

}
