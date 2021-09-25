package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.google.common.annotations.Beta;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IRTGUser;
import com.hbm.interfaces.ISource;
import com.hbm.interfaces.Untested;
import com.hbm.items.machine.ItemRTGPellet;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.nbt.NBTTagCompound;
@Beta
@Untested
public class TileEntityBigRTG extends TileEntityMachineBase implements ISource, IRTGUser
{
	public static final long maxPower = 1000000;
	public static final short maxHeat = 3200;
	private ArrayList<IConsumer> cList = new ArrayList<IConsumer>();
	private short heat = 0;
	private long power = 0;
	private byte age = 0;
	public TileEntityBigRTG()
	{
		super(24);
	}

	@Override
	public int getHeat()
	{
		return heat;
	}

	@Override
	public void ffgeuaInit()
	{
		ffgeua(xCoord, yCoord + 1, zCoord, getTact());
		ffgeua(xCoord, yCoord - 1, zCoord, getTact());
		ffgeua(xCoord - 1,yCoord, zCoord, getTact());
		ffgeua(xCoord + 1,yCoord, zCoord, getTact());
		ffgeua(xCoord, yCoord, zCoord - 1, getTact());
		ffgeua(xCoord, yCoord, zCoord + 1, getTact());
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact)
	{
		Library.ffgeua(x, y, z, newTact, this, getWorldObj());
	}

	@Override
	public boolean getTact()
	{
		return age >= 0 && age < 10 ? true : false;
	}

	@Override
	public long getSPower()
	{
		return power;
	}

	@Override
	public void setSPower(long i)
	{
		power = i;
	}

	@Override
	public List<IConsumer> getList()
	{
		return cList;
	}

	@Override
	public void clearList()
	{
		cList.clear();
	}

	@Override
	public String getName()
	{
		return "container.bigRTG";
	}

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			age++;
			if (age >= 20)
				age = 0;
			if (age == 9 || age == 19)
				ffgeuaInit();
			
			heat = (short) updateRTGs(slots, getWorldObj());
			
			if (heat > maxHeat)
				heat = maxHeat;
			power += heat;
			if (power > maxPower)
				power = maxPower;
		}
		
		NBTTagCompound data = new NBTTagCompound();
		data.setShort("heat", heat);
		data.setLong("power", power);
		networkPack(data, 25);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		heat = nbt.getShort("heat");
		power = nbt.getLong("power");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setShort("heat", heat);
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		heat = nbt.getShort("heat");
	}

	@Override
	public Class getDesiredClass()
	{
		return ItemRTGPellet.class;
	}

}
