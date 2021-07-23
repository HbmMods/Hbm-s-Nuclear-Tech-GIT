package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.config.BombConfig;
import com.hbm.config.MachineConfig;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityVortex;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.SingGenRecipes;
import com.hbm.inventory.SingGenRecipes.SingGenRecipe;
import com.hbm.inventory.container.ContainerMachineSingGen;
import com.hbm.items.machine.ItemFluidTank;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IBatteryItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import scala.actors.threadpool.Arrays;

@Spaghetti("Preemptive")
public class TileEntityMachineSingGen extends TileEntityMachineBase	implements IFluidAcceptor, IConsumer
{
	private Random rand = new Random();
	public long power;
	public static final long maxPower = 50000000000L;
	private static long consumptionRate = (maxPower / 200) / 4;// TODO Placeholder(?)
	private List<IConsumer> consume = new ArrayList();
	public FluidTank tank;
	
	public boolean isOn;
	private int progress;
	private static final int[] ringSlots = new int[] {5, 9, 6, 11, 12, 7, 10, 8};
	
	private ItemStack[] ringArray = new ItemStack[8];
	public SingGenRecipe currentRecipe = null;
	
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
	public int getProgressPercent()
	{
		return (progress * 100) / 200;
	}
	private SingGenRecipe validateRecipe(ItemStack[] ringIn, ItemStack centerIn, FluidType fluidIn, int amountIn)
	{
		for (SingGenRecipe recipe : SingGenRecipes.recipes)
		{
			//System.out.println(String.format("Checking recipe ring input: %s, Recipe center input: %s", recipe.inputRing, recipe.inputCenter.getUnlocalizedName()));
			//System.out.println("Is shaped? " + recipe.shaped);
			//System.out.println("Recipe center input: " + recipe.inputCenter.getUnlocalizedName());
			if (centerIn != null && SingGenRecipes.doRingsMatch(ringIn, recipe.aInputRing, recipe.shaped))
			{
				if (SingGenRecipes.isItemValid(centerIn, recipe.inputCenter))
				{
					if (recipe.fluid.equals(fluidIn) && !recipe.fluid.equals(FluidType.NONE))
					{
						if (recipe.fluidAmount <= amountIn)
						{
							//System.out.println(String.format("Recipe outputs: %s, uses fluids", recipe.getOutput().getDisplayName()));
							return recipe;
						}
					}
					else if (recipe.fluid.equals(FluidType.NONE))
					{
						//System.out.println(String.format("Recipe outputs: %s, does not use fluids", recipe.getOutput().getDisplayName()));
						return recipe;
					}
				}
			}
		}
		//System.out.println("No valid recipe found");
		return null;
	}
	/* -- Input Slots --
	 * 5  9  6
	 * 11 4  12
	 * 7  10 8
	 */
	public boolean canProcess()
	{
		updateRing();
		currentRecipe = validateRecipe(ringArray, slots[4], tank.getTankType(), tank.getFill());
		if (currentRecipe != null)
		{
			if ((tank.getTankType().equals(currentRecipe.fluid) && tank.getFill() >= currentRecipe.fluidAmount) || currentRecipe.fluid == FluidType.NONE)
			{
				if (this.getStackInSlot(1) == null || this.getStackInSlot(1).getMaxStackSize() >= this.getStackInSlot(1).stackSize + currentRecipe.getOutput().stackSize)
				{
					if (power >= consumptionRate)
					{
						return true;
					}
				}
			}
		}
		//System.out.println("Can't process");
		return false;
	}
	private void updateRing()
	{
		//System.out.println("Updating machine ring input...");
		int index = 0;
		for (int slot : ringSlots)
		{
			if (this.getStackInSlot(slot) != null)
			{
				//System.out.println(String.format("Item in slot #%s is not null, is: %s", slot, this.getStackInSlot(slot).getItem().getUnlocalizedName()));
				ringArray[index] = this.getStackInSlot(slot);
			}
			else
			{
				//System.out.println(String.format("Item in slot #%s is null", slot));
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
			tank.loadTank(2, 3, slots);
			//tank.unloadTank(2, 3, slots);
			markDirty();
			
			if (isOn)
			{
				if (canProcess())
				{
					progress++;
					power -= consumptionRate;
					if (progress == 200)
					{
						process();
						progress = 0;
					}
				}
				else
				{
					progress = 0;
					if (consumptionRate > power)
						attemptFail();
				}
			}
			else
			{
				progress = 0;
			}
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("progress", progress);
			data.setBoolean("isOn", isOn);
			this.networkPack(data, 25);
		}
	}
	/** Called when the machine is on but has no power,
	 * if the config allows it, may explode with a
	 * Fölkvangr field with a 3/4 chance (yield also
	 * determined by config) or collapse into a black hole
	 * with a 1/4 chance
	 */
	private void attemptFail()
	{
		if ((power < consumptionRate && tank.getFill() > 0 && tank.getTankType().equals(FluidType.ASCHRAB) && (currentRecipe != null && currentRecipe.fluid.equals(FluidType.ASCHRAB)) && !MachineConfig.singGenFailType.equals(MachineConfig.EnumSingGenFail.NONE)))
		{
			if (rand.nextInt(4) == 0)
			{
				EntityVortex bhole = new EntityVortex(getWorldObj(), (tank.getFill() / 8000));
				bhole.posX = xCoord;
				bhole.posY = yCoord;
				bhole.posZ = zCoord;
				worldObj.spawnEntityInWorld(bhole);
			}
			else
			{
				int yield = (int)BombConfig.aSchrabRadius * (tank.getFill() / 1000);
				if (yield > MachineConfig.singGenFailRadius && MachineConfig.singGenFailType.equals(MachineConfig.EnumSingGenFail.CAP_CUSTOM))
					yield = MachineConfig.singGenFailRadius;
				EntityNukeExplosionMK3 field = new EntityNukeExplosionMK3(getWorldObj());
				field.posX = xCoord;
				field.posY = yCoord;
				field.posZ = zCoord;
				field.destructionRange = yield;
				field.speed = 25;
				field.coefficient = 1.0F;
				field.waste = false;
				worldObj.spawnEntityInWorld(field);
				EntityCloudFleija effect = new EntityCloudFleija(getWorldObj(), yield);
				effect.posX = xCoord;
				effect.posY = yCoord;
				effect.posZ = zCoord;
				worldObj.spawnEntityInWorld(effect);
			}
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
				this.tank.setTankType(FluidType.LAVA);
			else
				this.tank.setTankType(FluidType.ASCHRAB);
			break;
		}
	}
	private void process()
	{
		//System.out.println("Processing...");
		if (!currentRecipe.keepRing)
		{
			for (int slot : ringSlots)
				if (this.getStackInSlot(slot) != null)
					this.decrStackSize(slot, 1);
		}
		this.decrStackSize(4, currentRecipe.inputCenter.stacksize);
		if (currentRecipe.fluid != FluidType.NONE)
			tank.setFill(tank.getFill() - currentRecipe.fluidAmount);
		
		if (this.getStackInSlot(1) == null)
			this.setInventorySlotContents(1, currentRecipe.getOutput());
		else if (this.getStackInSlot(1) != null && slots[1].getItem() == currentRecipe.getOutput().getItem())
			this.getStackInSlot(1).stackSize += currentRecipe.getOutput().stackSize;

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
	public boolean isItemValidForSlot(int i, ItemStack itemStack)
	{
		switch (i)
		{
		case 0:
			if (itemStack.getItem() instanceof IBatteryItem)
				return true;
			break;
		case 1:
			return false;
		case 2:
			if (itemStack.getItem() instanceof ItemFluidTank)
				return true;
			break;
		case 3:
			return false;
		case 4:
			if (Arrays.asList(ContainerMachineSingGen.centerItems).contains(itemStack.getItem()))
				return true;
			break;
		case 5:
		case 6:
		case 7:
		case 8:
			if (Arrays.asList(ContainerMachineSingGen.cornerItems).contains(itemStack.getItem()))
				return true;
			break;
		case 9:
		case 10:
		case 11:
		case 12:
			if (Arrays.asList(ContainerMachineSingGen.sideItems).contains(itemStack.getItem()))
				return true;
			break;
		default:
			return true;
		}
		return false;
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