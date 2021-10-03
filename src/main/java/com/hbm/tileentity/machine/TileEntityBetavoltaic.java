package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.annotations.Beta;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IRTGUser;
import com.hbm.interfaces.IRadioisotopeFuel;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBetavoltaic;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
@Beta
public class TileEntityBetavoltaic extends TileEntityMachineBase implements IRTGUser, ISource
{
	public static final long maxPower = 5000000;
	public static final short maxHeat = 32000;
	private static final HashMap<Item, IRadioisotopeFuel> fuelMap = new HashMap<>();
	private ArrayList<IConsumer> cList = new ArrayList<IConsumer>();
	private short heat = 0;
	private long power = 0;
	private byte age = 0;
	
	private static final int thaLife = 48000 * 40;
	private static final byte thaPower = 75;
	private static final int sr90Life = 48000 * 100 * 10;
	private static final byte sr90Power = 50;
	private static final byte sa327Power = 30;
	// Interfaces my beloved
	static
	{
		fuelMap.put(ModItems.ingot_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.ingot_thorium_fuel);}
		});
		fuelMap.put(ModItems.nugget_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower / 10;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.nugget_thorium_fuel);}
		});
		fuelMap.put(ModItems.billet_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower / 2;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.billet_thorium_fuel);}
		});
		fuelMap.put(Item.getItemFromBlock(ModBlocks.block_tha), new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower * 10;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModBlocks.block_thorium_fuel);}
		});
		fuelMap.put(ModItems.rod_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower / 2;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.rod_thorium_fuel);}
		});
		fuelMap.put(ModItems.rod_dual_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.rod_dual_thorium_fuel);}
		});
		fuelMap.put(ModItems.rod_quad_tha, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return thaPower + thaPower / 2;}
			@Override public long getMaxLifespan(){return thaLife;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.rod_quad_thorium_fuel);}
		});
		fuelMap.put(ModItems.ingot_sr90, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return sr90Power;}
			@Override public long getMaxLifespan(){return sr90Life;}
			@Override public boolean getDoesDecay(){return true;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.powder_zirconium);}
		});
		fuelMap.put(ModItems.cell_tritium, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return 1;}
			@Override public long getMaxLifespan(){return 48000 * 100 * 5;}
			@Override public boolean getDoesDecay(){return false;}
			@Override public ItemStack getDecayItem(){return new ItemStack(ModItems.cell_empty);}
		});
		fuelMap.put(ModItems.ingot_solinium, new IRadioisotopeFuel()
		{
			@Override public IRadioisotopeFuel setDecays(ItemStack stack, long lifespan){return this;}
			@Override public short getPower(){return sa327Power;}
			@Override public long getMaxLifespan(){return 0;}
			@Override public boolean getDoesDecay(){return false;}
			@Override public ItemStack getDecayItem(){return null;}
		});
	}
	public TileEntityBetavoltaic()
	{
		super(24);
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
	public int getHeat()
	{
		return heat;
	}

	@Override
	public Class getDesiredClass()
	{
		return ItemBetavoltaic.class;
	}

	@Override
	public String getName()
	{
		return "container.betavoltaic";
	}

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			age++;
			if (age >= 20)
				age = 20;
			if (age == 9 || age == 19)
				ffgeuaInit();
			
			heat = (short) updateRTGs(slots, fuelMap);
			
			if (heat > maxHeat)
				heat = maxHeat;
			power += heat;
			if (power > maxPower)
				power = maxPower;
		}
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setShort("heat", heat);
		networkPack(data, 25);
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		power = nbt.getLong("power");
		heat = nbt.getShort("heat");
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
	public boolean isItemValid(Item itemIn)
	{
		return IRTGUser.super.isItemValid(itemIn) || fuelMap.containsKey(itemIn);
	}
}
