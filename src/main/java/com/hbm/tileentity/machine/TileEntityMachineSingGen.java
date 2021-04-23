package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.SingGenRecipes;
import com.hbm.inventory.SingGenRecipes.SingGenRecipe;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import scala.actors.threadpool.Arrays;

@Spaghetti("Preemptive")
public class TileEntityMachineSingGen extends TileEntityMachineBase	implements IFluidAcceptor, IConsumer
{
	public long power;
	public static final long maxPower = 100000000L;
	private List<IConsumer> consume = new ArrayList();
	public FluidTank tank;
	
	public boolean isOn;
	private int progress;
	private int[] ringSlots = new int[] {5, 9, 6, 11, 12, 7, 10, 8};
	
	private List<ComparableStack> ringList = new ArrayList<ComparableStack>();
	private AStack[] ringArray;
	private SingGenRecipe currentRecipe = null;
	
	public TileEntityMachineSingGen()
	{
		super(13);
		tank = new FluidTank(FluidType.ASCHRAB, 64000, 0);
	}
	
	public boolean isProcessing()
	{
		if ((progress > 0 || canProcess()) && isOn)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public SingGenRecipe validateRecipe(AStack[] ringIn, ItemStack centerIn, FluidType fluidIn, int amountIn)
	{
		for (SingGenRecipe recipe : SingGenRecipes.recipes)
		{
			System.out.println(String.format("Checking recipe ring input: %s, Recipe center input: %s", recipe.inputRing.toString(), recipe.inputCenter.toString()));
			System.out.println(String.format("Checking machine ring input: %s, Machine center input: %s", ringArray.toString(), centerIn.toString()));
			boolean passedRing = false;
			if (recipe.inputRing.equals(ringIn) && recipe.shaped) // If shaped
			{
				passedRing = true;
			}
			else if (!recipe.shaped) // If shapeless
			{
				for (AStack checkingItem : recipe.inputRing)
				{
					if (!Arrays.asList(ringIn).contains(checkingItem))
					{
						System.out.println("No valid shapeless recipe found");
						return null;
					}
				}
			}
			if (centerIn != null && passedRing)
			{
				if (recipe.inputCenter.equals(centerIn.getItem()))
				{
					if (recipe.fluid.equals(fluidIn) && !recipe.fluid.equals(FluidType.NONE))
					{
						if (recipe.fluidAmount <= amountIn)
						{
							System.out.println(String.format("Recipe outputs: %s, is shaped", recipe.getOutput().getDisplayName()));
							return recipe;
						}
					}
					else if (recipe.fluid.equals(FluidType.NONE))
					{
						System.out.println(String.format("Recipe outputs: %s, is not shaped", recipe.getOutput().getDisplayName()));
						return recipe;
					}
				}
			}
		}
		System.out.println("No valid recipe found");
		return null;
	}
	/* -- Input Slots --
	 * 5  9  6
	 * 11 4  12
	 * 7  10 8
	 */
	public boolean canProcess()
	{
		currentRecipe = validateRecipe(ringArray, slots[4], tank.getTankType(), tank.getFill());
		if (currentRecipe != null)
		{
			if ((tank.getTankType().equals(currentRecipe.fluid) && tank.getFill() >= currentRecipe.fluidAmount) || currentRecipe.fluid == FluidType.NONE)
			{
				if (this.getStackInSlot(1) == null || this.getStackInSlot(1).getMaxStackSize() >= this.getStackInSlot(1).stackSize + currentRecipe.output.stackSize)
				{
					System.out.println("Can process");
					return true;
				}
			}
		}
		System.out.println("Can't process");
		return false;
	}
	private void updateRing()
	{
		System.out.println("Updating machine ring input...");
		//ringList.clear();
		int index = 0;
		for (int slot : ringSlots)
		{
			if (this.getStackInSlot(slot) != null)
			{
				System.out.println(String.format("Item in slot #%s is not null, is: %s", slot, this.getStackInSlot(slot).getItem()));
				//ringList.add(new ComparableStack(this.getStackInSlot(slot).getItem()));
				ringArray[index] = new ComparableStack(this.getStackInSlot(slot).getItem());
			}
			else
			{
				System.out.println(String.format("Item in slot #%s is null", slot));
				//ringList.add(null);
				ringArray[index] = null;
			}
			index++;
		}
		//System.out.println(String.format("Ring input: %s", ring));
	}
	@Override
	public void updateEntity()
	{
		//System.out.println(String.format("Center input: %s", this.getStackInSlot(4)));
		if (!worldObj.isRemote)
		{
			this.power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			tank.loadTank(0, 1, slots);
			tank.unloadTank(2, 3, slots);
			markDirty();
			
			if (isOn)
			{
			updateRing();
				if (canProcess())
				{
					// TODO Add power consumption
					progress++;
					if (progress == 200)
					{
						process();
						progress = 0;
					}
				}
				else
				{
					progress = 0;
				}
			}
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", progress);
			data.setBoolean("isOn", isOn);
			this.networkPack(data, 25);
		}
	}
	public void networkUnpack(NBTTagCompound data)
	{
		this.isOn = data.getBoolean("isOn");
		this.power = data.getLong("power");
		this.progress = data.getInteger("progress");
	}
	@Override
	public void handleButtonPacket(int value, int meta)
	{
		switch(meta)
		{
		case 0:
			this.isOn = !this.isOn;
			break;
		case 1:
			if (this.tank.getTankType().equals(FluidType.ASCHRAB))
			{
				this.tank.setTankType(FluidType.LAVA);
			}
			else
			{
				this.tank.setTankType(FluidType.ASCHRAB);
			}
			break;
		}
	}
	public void process()
	{
		System.out.println("Processing...");
		if (!currentRecipe.keepRing)
		{
			for (int slot : ringSlots)
			{
				if (this.getStackInSlot(slot) != null)
				{
					this.decrStackSize(slot, 1);
				}
			}
		}
		this.decrStackSize(4, 1);
		if (currentRecipe.fluid != FluidType.NONE)
		{
			tank.setFill(tank.getFill() - currentRecipe.fluidAmount);
		}
		if (this.getStackInSlot(1).getItem() == currentRecipe.output.getItem())
		{
			this.getStackInSlot(1).stackSize = this.getStackInSlot(1).stackSize + currentRecipe.output.stackSize;
		}
		else if (this.getStackInSlot(1) == null)
		{
			this.setInventorySlotContents(1, currentRecipe.getOutput());
		}
		markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		power = nbt.getLong("power");
		isOn = nbt.getBoolean("isOn");
		progress = nbt.getInteger("progress");
		tank.readFromNBT(nbt, "tank");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setLong("power", power);
		nbt.setBoolean("isOn", isOn);
		nbt.setInteger("progress", progress);
		tank.writeToNBT(nbt, "tank");
	}
	
	@Override
	public void setPower(long i)
	{
		this.power = i;
	}

	@Override
	public long getPower()
	{
		return this.power;
	}

	@Override
	public long getMaxPower()
	{
		return this.maxPower;
	}

	public int getPowerScaled(long i)
	{
		return (int) ((power * i) / maxPower);
	}
	
	@Override
	public int getMaxFluidFill(FluidType type)
	{
		return tank.getMaxFill();
	}

	@Override
	public void setFillstate(int fill, int index)
	{
		tank.setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type)
	{
		if (type.name().equals(tank.getTankType().name()))
		{
			tank.setFill(fill);
		}
	}

	@Override
	public void setType(FluidType type, int index)
	{
		tank.setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks()
	{
		List<FluidTank> list = new ArrayList();
		list.add(tank);
		return list;
	}

	@Override
	public int getFluidFill(FluidType type)
	{
		return tank.getFill();
	}

	@Override
	public String getName()
	{
		return "container.machineSingGen";
	}
}
