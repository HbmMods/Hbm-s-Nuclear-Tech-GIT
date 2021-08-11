package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import scala.actors.threadpool.Arrays;

public class TileEntityReactorAmat extends TileEntityMachineBase implements IFluidAcceptor, ISidedInventory, IConsumer, IFluidSource
{
	public FluidTank[] tanks = new FluidTank[3];
	private byte age = 0;
	private List<IFluidAcceptor> aList = new ArrayList<IFluidAcceptor>();
	private boolean isOn = false;
	private short boosterLife = 0;
	public static final short boosterMax = 1200;
	private short catalystLife = 0;
	public static final short catalystMax = 20000;
	private long coreLife = 0;
	private int fuelRate = 0;
	private int fuelRateBase = 1;
	private int plasmaRate = 0;
	private int plasmaRateBase = 0;
	private static final int powerRate = 200000;
	public static final long maxPower = 1000000000L;
	public long power = 0;
	private ReactorCatalyst cat;
	private ComparableStack currCat;
	private ReactorBooster boost;
	private ComparableStack currBoost;
	private ReactorCore core;
	private ComparableStack currCore;
	private int heat = 0;
	public static final int critHeat = 1750;
	public static final int maxHeat = 2500;
	private static HashMap<ComparableStack,ReactorBooster> boosterMap = new HashMap<ComparableStack, ReactorBooster>();
	private static HashMap<ComparableStack,ReactorCatalyst> catalystMap = new HashMap<ComparableStack, ReactorCatalyst>();
	private static HashMap<ComparableStack,ReactorCore> coreMap = new HashMap<ComparableStack, ReactorCore>();
	static
	{
		boosterMap.put(new ComparableStack(Items.diamond), new ReactorBooster(50, 1, 1, 1, 1));
		boosterMap.put(new ComparableStack(ModItems.powder_australium), new ReactorBooster(250, 1, 5, 0, 10));
		boosterMap.put(new ComparableStack(ModItems.powder_power), new ReactorBooster(500, 5, 10, 10, 15));
		boosterMap.put(new ComparableStack(ModItems.crystal_energy), new ReactorBooster(5000, 5, 100, 15, 15));
		boosterMap.put(new ComparableStack(ModItems.powder_nitan_mix), new ReactorBooster(1000, 1, 5, -1, 5));
		boosterMap.put(new ComparableStack(ModItems.powder_spark_mix), new ReactorBooster(boosterMax, 1, 1, -5, 50));
		catalystMap.put(new ComparableStack(ModItems.catalyst_clay), new ReactorCatalyst(500, 10));
		catalystMap.put(new ComparableStack(ModItems.catalyst_rare), new ReactorCatalyst(2500, 15));
		catalystMap.put(new ComparableStack(ModItems.catalyst_ten), new ReactorCatalyst(7500, 20));
		catalystMap.put(new ComparableStack(ModItems.powder_combine_steel), new ReactorCatalyst(12500, 50));
		catalystMap.put(new ComparableStack(ModItems.powder_schrabidate), new ReactorCatalyst(catalystMax, 100));
		coreMap.put(new ComparableStack(ModItems.singularity_micro), new ReactorCore((byte)2, (byte)5, 1000000));
		coreMap.put(new ComparableStack(ModItems.singularity), new ReactorCore((byte)2, (byte)5, 10000000));
		coreMap.put(new ComparableStack(ModItems.singularity_super_heated), new ReactorCore((byte)1, (byte)15, 10000000));
		coreMap.put(new ComparableStack(ModItems.black_hole), new ReactorCore((byte)2, (byte)5));
	}

	public TileEntityReactorAmat()
	{
		super(8);
		tanks[0] = new FluidTank(FluidType.DEUTERIUM, 32000, 0);
		tanks[1] = new FluidTank(FluidType.AMAT, 32000, 1);
		tanks[2] = new FluidTank(FluidType.PLASMA_WARP, 128000, 2);
	}

	public FluidTank getTank(int index)
	{
		return tanks[index];
	}

	private void decreaseFluidFill(FluidType type, int i)
	{
		tanks[getIndexFromFType(type)].setFill(tanks[getIndexFromFType(type)].getFill() - i);
	}
	private void increaseFluidFill(FluidType type, int i)
	{
		tanks[getIndexFromFType(type)].setFill(tanks[getIndexFromFType(type)].getFill() + i);
	}
	
	@Override
	public String getName()
	{
		return "container.reactorAmat";
	}

	@Override
	public void updateEntity()
	{
		// TODO Auto-generated method stub
		if (!worldObj.isRemote)
		{
			age++;
			if (age >= 20)
				age = 0;
			if (age == 9 || age == 19)
				fillFluidInit(FluidType.PLASMA_WARP);
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tanks[0].loadTank(2, 3, slots);
			tanks[1].loadTank(4, 5, slots);
			markDirty();
			for (int i = 0; i < 3; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			updateInputs();
			if (isOn && hasFuel(false) && isCoreValid() && hasPower(false) && tanks[2].getFill() + plasmaRate <= getMaxFluidFill(FluidType.PLASMA_WARP))
			{
				power -= powerRate;
				if (catalystLife > 0)
					catalystLife--;
				if (boosterLife > 0)
					boosterLife--;
				if (coreLife > 0)
					coreLife--;
			 	decreaseFluidFill(FluidType.DEUTERIUM, fuelRate);
			 	decreaseFluidFill(FluidType.AMAT, fuelRate);
			 	increaseFluidFill(FluidType.PLASMA_WARP, plasmaRate);
			}
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setBoolean("isOn", isOn);
			data.setShort("boosterLife", boosterLife);
			data.setShort("catalystLife", catalystLife);
			data.setLong("coreLife", coreLife);
			data.setInteger("plasmaRate", plasmaRate);
			data.setInteger("plasmaRateBase", plasmaRateBase);
			data.setInteger("fuelRate", fuelRate);
			data.setInteger("fuelRateBase", fuelRateBase);
			if (currBoost != null)
				data.setIntArray("currBoost", new int[] {Item.getIdFromItem(currBoost.item), currBoost.meta});
			if (currCat != null)
				data.setIntArray("currCat", new int[] {Item.getIdFromItem(currCat.item), currCat.meta});
			if (currCore != null)
				data.setIntArray("currCore", new int[] {Item.getIdFromItem(currCore.item), currCore.meta});
			networkPack(data, 50);
		}
	}
	/**
	 * Check if the reactor has fuel
	 * @param low - Set if it should determine if it's low on fuel instead
	 * @return If the reactor has (sufficient) fuel
	 */
	public boolean hasFuel(boolean low)
	{
		return (low ? tanks[0].getFill() > 15000 && tanks[1].getFill() > 15000 : tanks[0].getFill() >= fuelRate && tanks[1].getFill() >= fuelRate);
	}
	public boolean hasPower(boolean low)
	{
		return (low ? power < maxPower * 0.75F : power >= powerRate);
	}
	public void handleButtonPacket(int value, int meta)
	{
		switch (value)
		{
		case 0:
			isOn = !isOn;
			break;
		default:
			break;
		}
	}
	/** Consumes inputs and updates rates **/
	private void updateInputs()
	{
		boolean validCat = slots[7] != null && catalystMap.containsKey(new ComparableStack(getStackInSlot(7)).makeSingular());
		ReactorCatalyst testCat;
		boolean validBoost = slots[6] != null && boosterMap.containsKey(new ComparableStack(getStackInSlot(6)).makeSingular());
		ReactorBooster testBoost;
		boolean validCore = slots[1] != null && coreMap.containsKey(new ComparableStack(getStackInSlot(1)).makeSingular());
		ReactorCore testCore;
		if (slots[6] != null)
			boost = boosterMap.get(new ComparableStack(getStackInSlot(6)).makeSingular());
		if (isCoreValid())
			core = coreMap.get(new ComparableStack(getStackInSlot(1)).makeSingular());
		if (coreLife == 0 && isCoreValid())
		{
			testCore = coreMap.get(new ComparableStack(getStackInSlot(1)).makeSingular());
			if (core != null && core.equals(testCore) && coreLife == 0)
			{
				core = testCore;
				plasmaRate = core.getPlasmaProduction();
				plasmaRateBase = core.getPlasmaProduction();
				fuelRate = core.getFuelConsumption();
				fuelRateBase = core.getFuelConsumption();
				if (core.getDoesDecay())
				{
					coreLife = core.getMaxLifespan();
					decrStackSize(1, 1);
				}
			}
		}
		if (validCat)
		{
			testCat = catalystMap.get(new ComparableStack(getStackInSlot(7)).makeSingular());
			if (cat != null && cat.equals(testCat) && catalystMax >= testCat.getBuffer() + catalystLife)
			{
				decrStackSize(7, 1);
				cat = testCat;
				catalystLife += cat.getBuffer();
				plasmaRate += cat.getPlasmaProduction();
			}
			else if (cat == null || catalystLife == 0)
			{
				cat = testCat;
				decrStackSize(7, 1);
				catalystLife = cat.getBuffer();
				plasmaRate += cat.getPlasmaProduction();
			}
		}
		if (validBoost)
		{
			testBoost = boosterMap.get(new ComparableStack(getStackInSlot(6)).makeSingular());
			if (catalystLife >= boost.getCatalystConsumption() && boost != null && boost.equals(testBoost) && boosterMax >= testBoost.toBuffer + boosterLife)
			{
				decrStackSize(6, 1);
				boost = testBoost;
			 	boosterLife += boost.getBuffer();
				catalystLife -= boost.getCatalystConsumption();
				fuelRate += boost.getFuelConsumption();
				plasmaRate += boost.getPlasmaProduction();
			}
		}
	 	if (catalystLife == 0 && cat != null)
	 	{
	 		fuelRate = fuelRateBase;
	 		plasmaRate = plasmaRateBase;
	 	}
	 	else if (boosterLife == 0 && boost != null)
	 	{
	 		fuelRate -= boost.getFuelConsumption();
	 		plasmaRate -= boost.getPlasmaProduction();
	 	}
	}
	
	public boolean isCoreValid()
	{
		return getStackInSlot(1) != null ? coreMap.containsKey(new ComparableStack(getStackInSlot(1)).makeSingular()) : coreLife > 0;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack)
	{
		Item itemIn = itemStack != null ? itemStack.getItem() : null;
		ComparableStack cStack = new ComparableStack(itemStack).makeSingular();
		switch (i)
		{
		case 0:
			return itemIn instanceof IBatteryItem;
		case 1:
			return coreMap.containsKey(cStack);
		case 2:
		case 4:
			return itemIn instanceof ItemFluidTank;
		case 6:
			return boosterMap.containsKey(cStack);
		case 7:
			return catalystMap.containsKey(cStack);
		default:
			return false;
		}
	}
	/**
	 * @return If the reactor is turned on
	 */
	public boolean isOn()
	{
		return isOn;
	}
	/**
	 * @return If the reactor is on and/or contains plasma
	 */
	public boolean isOperational()
	{
		return isOn || tanks[2].getFill() > 1;
	}
	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		isOn = nbt.getBoolean("isOn");
		power = nbt.getLong("power");
		boosterLife = nbt.getByte("boosterLife");
		catalystLife = nbt.getShort("catalystLife");
		coreLife = nbt.getInteger("coreLife");
		fuelRate = nbt.getInteger("fuelRate");
		fuelRateBase = nbt.getInteger("fuelRateBase");
		plasmaRate = nbt.getInteger("plasmaRate");
		plasmaRateBase = nbt.getInteger("plasmaRateBase");
		if (nbt.hasKey("currBoost"))
		{
			int[] b = nbt.getIntArray("currBoost");
			currBoost = new ComparableStack(Item.getItemById(b[0]), 1, b[1]);
		}
		if (nbt.hasKey("currCore"))
		{
			int [] c = nbt.getIntArray("currCore");
			currCore = new ComparableStack(Item.getItemById(c[0]), 1, c[1]);
		}
//		tanks[0].readFromNBT(nbt, "deutTank");
//		tanks[1].readFromNBT(nbt, "amatTank");
//		tanks[2].readFromNBT(nbt, "plasmaTank");
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		isOn = nbt.getBoolean("isOn");
		power = nbt.getLong("power");
		boosterLife = nbt.getByte("boosterLife");
		catalystLife = nbt.getShort("catalystLife");
		tanks[0].readFromNBT(nbt, "deutTank");
		tanks[1].readFromNBT(nbt, "amatTank");
		tanks[2].readFromNBT(nbt, "plasmaTank");
//		int[] sBoost = nbt.getIntArray("currBoost");
//		int[] sCat = nbt.getIntArray("currCat");
//		currBoost = new ComparableStack(Item.getItemById(sBoost[0]), 1, sBoost[1]);
//		currCat = new ComparableStack(Item.getItemById(sCat[0]), 1, sCat[1]);
		if (nbt.hasKey("currCore"))
		{
			int[] sCore = nbt.getIntArray("currCore");
			currCore = new ComparableStack(Item.getItemById(sCore[0]), 1, sCore[1]);
		}
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setBoolean("isOn", isOn);
		nbt.setLong("power", power);
		nbt.setShort("boosterLife", boosterLife);
		nbt.setShort("catalystLife", catalystLife);
		tanks[0].writeToNBT(nbt, "deutTank");
		tanks[1].writeToNBT(nbt, "amatTank");
		tanks[2].writeToNBT(nbt, "plasmaTank");
//		nbt.setIntArray("currBoost", new int[] {Item.getIdFromItem(currBoost.item), currBoost.meta});
//		nbt.setIntArray("currCat", new int[] {Item.getIdFromItem(currCat.item), currCat.meta});
		if (currCore != null)
			nbt.setIntArray("currCore", new int[] {Item.getIdFromItem(currCore.item), currCore.meta});
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
	public short getBoosterBuffer()
	{
		return boosterLife;
	}
	public short getCatalystBuffer()
	{
		return catalystLife;
	}
	public int getPlasmaBaseProduction()
	{
		return plasmaRateBase;
	}
	public int getPlasmaProduction()
	{
		return plasmaRate;
	}
	public int getFuelBaseConsumption()
	{
		return fuelRateBase;
	}
	public int getFuelConsumption()
	{
		return fuelRate;
	}
	private static class ReactorBooster
	{
		private byte toBuffer;
		private byte cRate;
		private byte catRate;
		private byte fCons;
		private byte pProd;
		/**
		 * Add a new booster item
		 * @param toBuffer - How much is added to the buffer
		 * @param cRate - Consumption rate/tick of buffer
		 * @param catRate - Catalyst depletion per booster
		 * @param fCons - Fuel consumption in mb/tick added to base
		 * @param pProd - Plasma production in mb/tick added to base
		 */
		public ReactorBooster(int toBuffer, int cRate, int catRate, int fCons, int pProd)
		{
			this.toBuffer = (byte)toBuffer;
			this.cRate = (byte)cRate;
			this.catRate = (byte)catRate;
			this.fCons = (byte)fCons;
			this.pProd = (byte)pProd;
		}
		/** How much as added to the buffer **/
		public byte getBuffer()
		{
			return toBuffer;
		}
		/** Consumption rate/tick of buffer **/
		public byte getConsumptionRate()
		{
			return cRate;
		}
		/** Catalyst depletion per booster **/
		public byte getCatalystConsumption()
		{
			return catRate;
		}
		/** Fuel consumption in mb/tick added to base **/
		public byte getFuelConsumption()
		{
			return fCons;
		}
		/** Plasma production in mb/tick added to base **/
		public byte getPlasmaProduction()
		{
			return pProd;
		}
	}
	private static class ReactorCatalyst
	{
		private short toBuffer;
		private byte pProd;
		/**
		 * Add a new catalyst item
		 * @param toBuffer - How much as added to the buffer
		 * @param pProd - Plasma production in mb/tick added to base
		 */
		public ReactorCatalyst(int toBuffer, int pProd)
		{
			this.toBuffer = (short) toBuffer;
			this.pProd = (byte) pProd;
		}
		/** How much as added to the buffer **/
		public short getBuffer()
		{
			return toBuffer;
		}
		/** Plasma production in mb/tick added to base **/
		public byte getPlasmaProduction()
		{
			return pProd;
		}
	}
	private static class ReactorCore
	{
		private byte fCons;
		private byte pProd;
		private boolean decays = false;
		private long lifespan;
		/**
		 * Add new reactor core
		 * @param fCons - Fuel consumption in mb/tick
		 * @param pProd - Plasma production in mb/tick
		 */
		public ReactorCore(byte fCons, byte pProd)
		{
			this.fCons = fCons;
			this.pProd = pProd;
		}
		public ReactorCore(byte fCons, byte pProd, long lifespan)
		{
			this(fCons, pProd);
			this.lifespan = lifespan;
			decays = true;
		}
		/** Fuel consumption in mb/tick **/
		public byte getFuelConsumption()
		{
			return fCons;
		}
		/** Plasma production in mb/tick **/
		public byte getPlasmaProduction()
		{
			return pProd;
		}
		public boolean getDoesDecay()
		{
			return decays;
		}
		public long getMaxLifespan()
		{
			return lifespan;
		}
	}
	public long getCoreLife()
	{
		return core != null ? (core.getDoesDecay() ? coreLife : -1) : -1;
	}
	private static byte getIndexFromFType(FluidType type)
	{
		switch (type)
		{
		case AMAT:
			return 1;
		case DEUTERIUM:
			return 0;
		case PLASMA_WARP:
			return 2;
		default:
			return -1;
		}
	}
	@Override
	public void setFillstate(int fill, int index)
	{
		tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type)
	{
		tanks[getIndexFromFType(type)].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index)
	{
		tanks[index].setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks()
	{
		return Arrays.asList(tanks);
	}

	@Override
	public int getFluidFill(FluidType type)
	{
		return tanks[getIndexFromFType(type)].getFill();
	}

	@Override
	public int getMaxFluidFill(FluidType type)
	{
		return tanks[getIndexFromFType(type)].getMaxFill();
	}

	@Override
	public void fillFluidInit(FluidType type)
	{
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type)
	{
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact()
	{
		return age >= 0 && age < 10 ? true : false;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type)
	{
		return aList;
	}

	@Override
	public void clearFluidList(FluidType type)
	{
		aList.clear();
	}
}
