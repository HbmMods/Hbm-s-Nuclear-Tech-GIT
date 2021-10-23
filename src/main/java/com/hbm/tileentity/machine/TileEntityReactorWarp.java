package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.CheckForNull;

import org.apache.logging.log4j.Level;

import com.google.common.annotations.Beta;
import com.hbm.calc.EasyLocation;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCassette.TrackType;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TESirenPacket;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.I18nUtil;

import api.hbm.energy.IBatteryItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import scala.actors.threadpool.Arrays;
@Beta
public class TileEntityReactorWarp extends TileEntityMachineBase implements IConsumer, IFluidAcceptor, IFluidSource, IControlReceiver
{
	protected FluidTank[] tanks = new FluidTank[3];
	protected byte age = 0;
	protected List<IFluidAcceptor> cList = new ArrayList<IFluidAcceptor>();
	protected static final HashMap<ComparableStack, ReactorCore> coreList = new HashMap<>();
	protected static final HashMap<ComparableStack, ReactorCatalyst> catalystList = new HashMap<>();
	protected static final HashMap<ComparableStack, ReactorBooster> boosterList = new HashMap<>();
	public static final long maxPower = 1000000000;
	public static final long powerRate = 100000;
	private long power = 0;
	public static final int maxTemp = 3500000;
	public static final int critTemp = (int) (maxTemp * 0.8);
	private int temperature = 20;
	public static final int maxBar = 200000;
	public static final int critBar = (int) (maxBar * 0.8);
	private int bar = 1;
	public static final int maxDyne = 500000;
	public static final int critDyne = (int) (maxDyne * 0.8);
	private int teradynes = 0;
	private byte[] intermix = {0, 0};
	private byte delay = 100;
	private boolean magnets = false;
	private boolean active = false;
	private final boolean[] criticalStats = {false, false, false};
	public Optional<ReactorCore> currCore = Optional.empty();
	public Optional<ReactorBooster> currBooster = Optional.empty();
	public Optional<ReactorCatalyst> currCatalyst = Optional.empty();
	static
	{
		coreList.put(new ComparableStack(ModItems.singularity_micro), new ReactorCore(1, 10, "singularity_micro").setDecays(48000 * 5));
		coreList.put(new ComparableStack(ModItems.singularity), new ReactorCore(1, 10, "singularity"));
		coreList.put(new ComparableStack(ModItems.singularity_super_heated), new ReactorCore(1, 20, "singularity_super_heated").setDecays(48000 * 50));
		catalystList.put(new ComparableStack(ModItems.catalyst_clay), new ReactorCatalyst(100, 2, "catalyst_clay"));
		catalystList.put(new ComparableStack(ModItems.catalyst_rare), new ReactorCatalyst(1000, 10, "catalyst_rare"));
		catalystList.put(new ComparableStack(ModItems.catalyst_ten), new ReactorCatalyst(10000, 30, "catalyst_ten"));
		boosterList.put(new ComparableStack(Items.diamond), new ReactorBooster(100, 1, 2, "diamond"));
		boosterList.put(new ComparableStack(ModItems.powder_power), new ReactorBooster(250, 5, 15, "powder_power"));
		boosterList.put(new ComparableStack(ModItems.powder_nitan_mix), new ReactorBooster(1000, 0, 10, "powder_nitan_mix"));
	}
	public TileEntityReactorWarp()
	{
		super(4);
		tanks[0] = new FluidTank(FluidType.DEUTERIUM, 32000, 0);
		tanks[1] = new FluidTank(FluidType.AMAT, 32000, 1);
		tanks[2] = new FluidTank(FluidType.PLASMA_WARP, 128000, 2);
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
			
			for (int i = 0; i < 3; i++)
				tanks[i].updateTank(this);
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			updateIntermixAndInputs();
			if (areMagnetsOn())
			{
				power -= powerRate;
				
				if (canOperate())
				{
					tanks[0].decrementFill(intermix[0]);
					tanks[1].decrementFill(intermix[0]);
					tanks[2].incrementFill(intermix[1]);
				}
			}
			
			final int currFluid = getFluidFill(FluidType.PLASMA_WARP);
			temperature = modStatLevel(getMaxLevel(currFluid, maxTemp), temperature);
			bar = modStatLevel(getMaxLevel(currFluid, maxBar), bar);
			teradynes = modStatLevel(getMaxLevel(currFluid, maxDyne), teradynes);
			updateCritical();
			
			for (boolean b : criticalStats)
			{
				if (b)
				{
					PacketDispatcher.wrapper.sendToAllAround(new TESirenPacket(new EasyLocation(this), TrackType.AMS_SIREN.ordinal(), true), Library.easyTargetPoint(this, 1500));
					PacketDispatcher.wrapper.sendToAllAround(new TESirenPacket(new EasyLocation(this), TrackType.AMS_SIREN.ordinal(), false), Library.easyTargetPoint(this, 1500));
					break;
				}
			}		
		}
		
		NBTTagCompound data = new NBTTagCompound();
		data.setLong("power", power);
		data.setIntArray("stats", new int[] {temperature, bar, teradynes});
		data.setByteArray("intermix", intermix);
		data.setByte("delay", delay);
		data.setBoolean("magnetsOn", magnets);
		data.setBoolean("isActive", active);
		if (currCore.isPresent())
			data.setTag("core", currCore.get().writeToNBT());
		if (currBooster.isPresent())
			data.setTag("booster", currBooster.get().writeToNBT());
		if (currCatalyst.isPresent())
			data.setTag("catalyst", currCatalyst.get().writeToNBT());
		networkPack(data, 100);
	}
	
	/** Uses an euler/natural logarithm type logarithmic function
	 * @param targLvl Target level of the stat
	 * @param currLvl Current level of the stat
	 * @return The new level of the stat
	 **/
	@Beta
	@Untested
	private static int modStatLevel(int targLvl, int currLvl)
	{
		try
		{
			return (int) Math.floor((targLvl * 1.125) / 1 + Math.pow(Math.E, Math.log(currLvl)));
		}
		catch (ArithmeticException e)
		{
			e.printStackTrace();
			MainRegistry.logger.catching(Level.WARN, e);
			return 0;
		}

	}
	/**
	 * Uses a square root function
	 * @param fLevel Current fluid level of the plasma tank
	 * @param max Maximum level of the statistic
	 * @return The target level for a stat
	 */
	@Beta
	@Untested
	private static int getMaxLevel(int fLevel, int max)
	{
		try
		{
			return (int) Math.floor(Math.sqrt(fLevel) * -(max / fLevel) + max * 1.125);
		}
		catch (ArithmeticException e)
		{
			e.printStackTrace();
			MainRegistry.logger.catching(Level.WARN, e);
			return 0;
		}
	}
	
	private void updateIntermixAndInputs()
	{
		// TODO finish
		intermix[0] = 0;
		intermix[1] = 0;
		if (currCore.isPresent())
		{
			final ReactorCore core = currCore.get();
			core.modIntermix(intermix);
			if (canOperate())
				core.decayTick();
			if (core.getLife() <= 0 || (!core.getDoesDecay() && constructComparable(1) == null))
				currCore = Optional.empty(); 
		}
		else if (isItemValidForSlot(1, getStackInSlot(1)))
		{
			currCore = Optional.of(coreList.get(constructComparable(1)));
			if (currCore.get().getDoesDecay())
				decrStackSize(1, 1);
			currCore.get().setLife(currCore.get().getMaxLife());
		}
		if (currCatalyst.isPresent())
		{
			final ReactorCatalyst catalyst = currCatalyst.get();
			catalyst.modIntermix(intermix);
			if (canOperate())
				catalyst.decayTick();
			if (catalyst.getLife() <= 0)
				currCatalyst = Optional.empty();
		}
		else if (isItemValidForSlot(2, getStackInSlot(2)))
		{
			currCatalyst = Optional.of(catalystList.get(constructComparable(2)));
			decrStackSize(2, 1);
			currCatalyst.get().setLife(currCatalyst.get().getMaxLife());
		}
		if (currBooster.isPresent())
		{
			final ReactorBooster booster = currBooster.get();
			booster.modIntermix(intermix);
			if (canOperate())
				booster.decayTick();
			if (booster.getLife() <= 0)
				currBooster = Optional.empty();
		}
		else if (isItemValidForSlot(3, getStackInSlot(3)))
		{
			currBooster = Optional.of(boosterList.get(constructComparable(3)));
			decrStackSize(3, 1);
			currBooster.get().setLife(currBooster.get().getMaxLife());
		}
	}
	
	private void updateCritical()
	{
		criticalStats[0] = temperature >= critTemp;
		criticalStats[1] = bar >= critBar;
		criticalStats[2] = teradynes >= critDyne;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack)
	{
		if (itemStack == null)
			return false;
		switch (i)
		{
		case 0:
			return itemStack.getItem() instanceof IBatteryItem;
		case 1:
			return coreList.containsKey(constructComparable(itemStack));
		case 2:
			return boosterList.containsKey(constructComparable(itemStack));
		case 3:
			return catalystList.containsKey(constructComparable(itemStack));
		default:
			return false;
		}
	}
	static final byte height = 1;
	protected void meltdown()
	{
		int yield = 0;
		yield += (getFluidFill(FluidType.AMAT) / 1000) * 5;
		yield += (getFluidFill(FluidType.PLASMA_WARP) / 10000) * 5;
		final EntityBalefire bf = new EntityBalefire(getWorldObj());
		bf.posX = xCoord;
		bf.posY = xCoord + height;
		bf.posZ = zCoord;
		bf.destructionRange = yield;
		worldObj.spawnEntityInWorld(bf);
		worldObj.spawnEntityInWorld(EntityNukeCloudSmall.statFacBale(getWorldObj(), xCoord, yCoord + height * 10, zCoord, yield * 1.5F, 1000));
	}
	
	@Override
	public void handleButtonPacket(int value, int meta)
	{
		worldObj.playSoundEffect(xCoord, yCoord, zCoord, "gui.button.press", 1.0F, 1.0F);
		switch (value)
		{
		case 0:
			magnets = !magnets;
			if (!magnets)
				active = false;
			break;
		case 1:
			if (magnets)
				active = !active;
			else
				active = false;
			break;
		default:
			break;
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt)
	{
		power = nbt.getLong("power");
		final int[] stats = nbt.getIntArray("stats");
		intermix = nbt.getByteArray("intermix");
		temperature = stats[0];
		bar = stats[1];
		teradynes = stats[2];
		delay = nbt.getByte("delay");
		magnets = nbt.getBoolean("magnetsOn");
		active = nbt.getBoolean("isActive");
		if (nbt.hasKey("core"))
			currCore = Optional.of((ReactorCore) new ReactorCore().readFromNBT(nbt.getCompoundTag("core")));
		if (nbt.hasKey("booster"))
			currBooster = Optional.of((ReactorBooster) new ReactorBooster().readFromNBT(nbt.getCompoundTag("booster")));
		if (nbt.hasKey("catalyst"))
			currCatalyst = Optional.of((ReactorCatalyst) new ReactorCatalyst().readFromNBT(nbt.getCompoundTag("catalyst")));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		for (int i = 0; i < 3; i++)
			tanks[i].writeToNBT(nbt, "tank_" + i);
		nbt.setLong("power", power);
		nbt.setIntArray("stats", new int[] {temperature, bar, teradynes});
		nbt.setByte("delay", delay);
		nbt.setBoolean("magnetsOn", magnets);
		nbt.setBoolean("isActive", active);
		if (currCore.isPresent())
			nbt.setTag("core", currCore.get().writeToNBT());
		if (currBooster.isPresent())
			nbt.setTag("booster", currBooster.get().writeToNBT());
		if (currCatalyst.isPresent())
			nbt.setTag("catalyst", currCatalyst.get().writeToNBT());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		for (int i = 0; i < 3; i++)
			tanks[i].readFromNBT(nbt, "tank_" + i);
		power = nbt.getLong("power");
		final int[] stats = nbt.getIntArray("stats");
		temperature = stats[0];
		bar = stats[1];
		teradynes = stats[2];
		delay = nbt.getByte("delay");
		magnets = nbt.getBoolean("magnetsOn");
		active = nbt.getBoolean("isActive");
		if (nbt.hasKey("core"))
			currCore = Optional.of((ReactorCore) new ReactorCore().readFromNBT(nbt.getCompoundTag("core")));
		if (nbt.hasKey("booster"))
			currBooster = Optional.of((ReactorBooster) new ReactorBooster().readFromNBT(nbt.getCompoundTag("booster")));
		if (nbt.hasKey("catalyst"))
			currCatalyst = Optional.of((ReactorCatalyst) new ReactorCatalyst().readFromNBT(nbt.getCompoundTag("catalyst")));
	}
	/**
	 * Constructs a {@link#ComparableStack} for comparisons
	 * @param slot The slot the stack will be constructed from
	 * @return The new ComparableStack or null if invalid
	 */
	@CheckForNull
	private final ComparableStack constructComparable(int slot)
	{
		return constructComparable(getStackInSlot(slot));
	}
	/**
	 * Constructs a {@link#ComparableStack} for comparisons
	 * @param stack The stack that the ComparableStack will be constructed from
	 * @return The new ComparableStack or null if invalid
	 */
	@CheckForNull
	private final ComparableStack constructComparable(ItemStack stack)
	{
		return stack == null ? null : new ComparableStack(stack).makeSingular();
	}
	
	private final boolean areMagnetsOn()
	{
		return magnets && power >= powerRate;
	}
	
	private final boolean canOperate()
	{
		return active && getFluidFill(FluidType.DEUTERIUM) >= intermix[0] && getFluidFill(FluidType.AMAT) >= intermix[0] && intermix[1] + getFluidFill(FluidType.PLASMA_WARP) <= getMaxFluidFill(FluidType.PLASMA_WARP);
	}
	
	private final byte indexFromType(FluidType type)
	{
		switch (type)
		{
		case DEUTERIUM:
			return 0;
		case AMAT:
			return 1;
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
		tanks[indexFromType(type)].setFill(fill);
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
		return tanks[indexFromType(type)].getFill();
	}
	
	@Override
	public boolean getTact()
	{
		return age >= 0 && age < 10 ? true : false;
	}
	
	@Override
	public int getMaxFluidFill(FluidType type)
	{
		return tanks[indexFromType(type)].getMaxFill();
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
		return "container.reactorAmat";
	}

	@Override
	public void fillFluidInit(FluidType type)
	{
		fillFluid(xCoord + 1, yCoord, zCoord, getTact(), type);
		fillFluid(xCoord - 1, yCoord, zCoord, getTact(), type);
		fillFluid(xCoord, yCoord + 1, zCoord, getTact(), type);
		fillFluid(xCoord, yCoord - 1, zCoord, getTact(), type);
		fillFluid(xCoord, yCoord, zCoord + 1, getTact(), type);
		fillFluid(xCoord, yCoord, zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type)
	{
		Library.transmitFluid(x, y, z, newTact, this, getWorldObj(), type);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type)
	{
		return cList;
	}

	@Override
	public void clearFluidList(FluidType type)
	{
		cList.clear();
	}
	
	private abstract static class ReactorBaseItem
	{
		protected boolean doesDecay = false;
		protected int maxLife;
		protected int life;
		protected final byte[] intermix = new byte[2];
		protected String name;
		protected ReactorBaseItem()
		{
		}
		protected ReactorBaseItem(int fCons, int pProd, String locName)
		{
			name = "item." + locName + ".name";
			intermix[0] = (byte) fCons;
			intermix[1] = (byte) pProd;
		}
		public ReactorBaseItem setDecays(int life)
		{
			maxLife = life;
			this.life = life;
			doesDecay = true;
			return this;
		}
		public int getMaxLife()
		{
			return maxLife;
		}
		public int getLife()
		{
			return life;
		}
		public boolean getDoesDecay()
		{
			return doesDecay;
		}
		public byte[] getIntermix()
		{
			return intermix.clone();
		}
		public void setLife(int i)
		{
			life = i;
		}
		public void modIntermix(byte[] intermixIn)
		{
			intermixIn[0] += intermix[0];
			intermixIn[1] += intermix[1];
		}
		public void decayTick()
		{
			if (getDoesDecay())
				life--;
			else
				return;
		}
		public abstract ReactorBaseItem getBlank();
		public NBTTagCompound writeToNBT()
		{
			NBTTagCompound data = new NBTTagCompound();
			data.setString("name", name);
			data.setByteArray("stats", getIntermix());
			data.setInteger("life", getLife());
			data.setInteger("maxLife", getMaxLife());
			data.setBoolean("decays", getDoesDecay());
			return data;
		}
		public ReactorBaseItem readFromNBT(NBTTagCompound data)
		{
			final byte[] stats = data.getByteArray("stats");
			intermix[0] = stats[0];
			intermix[1] = stats[1];
			name = data.getString("name");
			life = data.getInteger("life");
			maxLife = data.getInteger("maxLife");
			doesDecay = data.getBoolean("decays");
			return this;
		}
		@Override
		public String toString()
		{
			return I18nUtil.resolveKey(name);
		}
		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof ReactorBaseItem))
				return false;
			final ReactorBaseItem tester = (ReactorBaseItem) obj;
			return doesDecay == tester.getDoesDecay() && maxLife == tester.getMaxLife() && life == tester.getLife() && name.equals(tester.name) && getIntermix().equals(tester.getIntermix());
		}
	}
	public static final class ReactorCore extends ReactorBaseItem
	{
		public ReactorCore(){}
		public ReactorCore(int fCons, int pProd, String locName)
		{
			super(fCons, pProd, locName);
		}
		@Override
		public ReactorCore getBlank()
		{
			return new ReactorCore();
		}
		@Override
		public ReactorCore setDecays(int life)
		{
			return (ReactorCore) super.setDecays(life);
		}
	}
	public static final class ReactorCatalyst extends ReactorBaseItem
	{
		public ReactorCatalyst(){}
		public ReactorCatalyst(int life, int pBoost, String locName)
		{
			super(0, pBoost, locName);
			setDecays(life);
		}
		@Override
		public ReactorCatalyst getBlank()
		{
			return new ReactorCatalyst();
		}
	}
	public static final class ReactorBooster extends ReactorBaseItem
	{
		public ReactorBooster(){}
		public ReactorBooster(int life, int fBoost, int pBoost, String locName)
		{
			super(fBoost, pBoost, locName);
			setDecays(life);
		}

		@Override
		public ReactorBooster getBlank()
		{
			return new ReactorBooster();
		}
	}

	@Override
	public boolean hasPermission(EntityPlayer player)
	{
		return isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data)
	{
		// TODO Auto-generated method stub
		if (data.hasKey("level"))
			delay = (byte) (100 - data.getByte("level"));
	}

	public boolean[] getCriticals()
	{
		return criticalStats.clone();
	}
	public int[] assembleStats()
	{
		final int[] stats = new int[6];
		stats[0] = 100 - delay;
		stats[1] = intermix[0];
		stats[2] = intermix[1];
		stats[3] = temperature;
		stats[4] = bar;
		stats[5] = teradynes;
		return stats.clone();
	}
	public byte getDelayLevel()
	{
		return delay;
	}
	public boolean isCoreValid()
	{
		return currCore.isPresent();
	}
	public boolean magnetsActivated()
	{
		return magnets;
	}
	public boolean isRunning()
	{
		return active;
	}
	public boolean hasPower(boolean low)
	{
		return low ? power < maxPower * 0.75 : power >= powerRate;
	}
	public boolean hasPlasma(boolean low)
	{
		return low ? getFluidFill(FluidType.PLASMA_WARP) > getMaxFluidFill(FluidType.PLASMA_WARP) * 0.25 : getFluidFill(FluidType.PLASMA_WARP) > 0;
	}
}
