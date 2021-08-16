package com.hbm.tileentity.turret;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ILaserable;
import com.hbm.inventory.FluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityLunarOni extends TileEntityMachineBase implements IConsumer, ILaserable
{
	private long power = 0;
	private long buffer = 0;
	public static final long powerRate = 1000000L;
	public static final long maxPower = 1000000000000L;
	private FluidTank tank;
	private float elevation = 0.0F;
	private float direction = 0.0F;
	private int range;
	public TileEntityLunarOni()
	{
		super(3);
		tank = new FluidTank(FluidType.PLASMA_WARP, 64000, 0);
	}

	@Override
	public void updateEntity()
	{
		// TODO Auto-generated method stub
		if (!worldObj.isRemote)
		{
			Library.chargeTEFromItems(slots, 0, power, maxPower);
			tank.loadTank(1, 2, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
		}
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setFloat("elevation", elevation);
		data.setFloat("direction", direction);
		networkPack(data, 100);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		power = nbt.getLong("power");
		elevation = nbt.getFloat("elevation");
		direction = nbt.getFloat("direction");
	}
	
	private void fillBuffer()
	{
		if (power >= powerRate && buffer + powerRate <= maxPower)
		{
			power -= powerRate;
			buffer += powerRate;
		}
	}
	
	private void clamp()
	{
		direction = MathHelper.clamp_float(direction, -180F, 180F);
		elevation = MathHelper.clamp_float(elevation, -50F, 50F);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "tank");
		nbt.setLong("power", power);
		nbt.setFloat("elevation", elevation);
		nbt.setFloat("direction", direction);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "tank");
		power = nbt.getLong("power");
		elevation = nbt.getFloat("elevation");
		direction = nbt.getFloat("direction");
	}
	
	@Override
	public void setPower(long i)
	{
		power = i;
	}

	@Override
	public long getPower()
	{
		return power;
	}

	@Override
	public long getMaxPower()
	{
		return maxPower;
	}

	@Override
	public String getName()
	{
		return "container.turretLunarOni";
	}

	@Override
	public void addEnergy(long energy, ForgeDirection dir)
	{
		// TODO Auto-generated method stub
		
	}
}
