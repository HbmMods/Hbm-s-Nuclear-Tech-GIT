package com.hbm.tileentity.machine;

import com.google.common.annotations.Beta;
import com.hbm.calc.EasyLocation;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.RecipesCommon;
import com.hbm.inventory.recipes.ChemPlantRecipesNT;
import com.hbm.inventory.recipes.ChemPlantRecipesNT.ChemPlantRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemistryTemplate.EnumChemistryTemplate;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEChemplantPacket;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import scala.actors.threadpool.Arrays;
/**
 * Very experimental ChemPlant "reworking"
 * @author UFFR
 *
 */
@Beta
public class TileEntityChemPlantNT extends TileEntityMachineChemplant
{
	private EnumChemistryTemplate currTemplate;
	private ChemPlantRecipe currRecipe = ChemPlantRecipe.NONE;
	
	private void updateUpgrades()
	{
		for (int i = 1; i < 4; i++)
		{
			ItemStack stack = slots[i];
			if (stack == null || !(stack.getItem() instanceof ItemMachineUpgrade))
				continue;
			else
			{
				short[] mod = {0, 0};
				if (stack.getItem() == ModItems.upgrade_speed_1)
					mod = new short[] {-25, 300};
				if (stack.getItem() == ModItems.upgrade_speed_2)
					mod = new short[] {-50, 600};
				if (stack.getItem() == ModItems.upgrade_speed_3)
					mod = new short[] {-75, 900};
				if (stack.getItem() == ModItems.upgrade_power_1)
					mod = new short[] {5, -30};
				if (stack.getItem() == ModItems.upgrade_power_2)
					mod = new short[] {10, -60};
				if (stack.getItem() == ModItems.upgrade_power_3)
					mod = new short[] {15, -90};
				
				speed += mod[0];
				consumption += mod[1];
				if (speed < 25)
					speed = 25;
				if (consumption < 10)
					consumption = 10;
			}
		}
	}
	
	@Override
	public void updateEntity()
	{
		consumption = 100;
		speed = 100;
		
		updateUpgrades();
		
		if (!worldObj.isRemote)
		{
			isProgressing = false;
			age++;
			if (age >= 20)
				age = 0;
			
			if(age == 9 || age == 19)
			{
				fillFluidInit(tanks[2].getTankType());
				fillFluidInit(tanks[3].getTankType());
			}

			setContainers();
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			tanks[0].loadTank(17, 19, slots);
			tanks[1].loadTank(18, 20, slots);
			
			if(slots[17] != null && !(slots[17].getItem() == ModItems.fluid_barrel_infinite || slots[17].getItem() == ModItems.inf_water))
				tanks[0].unloadTank(17, 19, slots);
			if(slots[18] != null && !(slots[18].getItem() == ModItems.fluid_barrel_infinite || slots[18].getItem() == ModItems.inf_water))
				tanks[1].unloadTank(18, 20, slots);

			tanks[2].unloadTank(9, 11, slots);
			tanks[3].unloadTank(10, 12, slots);

			for (int i = 0; i < 4; i++)
				tanks[i].updateTank(this);
			
			currTemplate = EnumChemistryTemplate.getEnum(slots[4].getItemDamage());
			currRecipe = ChemPlantRecipesNT.recipes.get(currTemplate.toString());
			FluidStack[] fInputs = currRecipe.fIn;
			FluidStack[] fOutputs = currRecipe.fOut;
			ItemStack[] iInputs = RecipesCommon.objectToStackArray(currRecipe.inputs);
			ItemStack[] iOutputs = currRecipe.outputs;
			
			if((!Library.isArrayEmpty(iInputs) || !Library.isArrayEmpty(fInputs)) && 
					(!Library.isArrayEmpty(iOutputs) || !Library.isArrayEmpty(fOutputs)))
			{
				maxProgress = (currRecipe.time * speed) / 100;
				
				if(power >= consumption && removeItems(Arrays.asList(iInputs), cloneItemStackProper(slots)) && hasFluidsStored(fInputs))
				{
					if(hasSpaceForItems(iOutputs) && hasSpaceForFluids(fOutputs))
					{
						progress++;
						isProgressing = true;
						
						if (progress >= maxProgress)
						{
							progress = 0;
							
							addItems(iOutputs);
							addFluids(fOutputs);
							
							removeItems(Arrays.asList(iInputs), slots);
							removeFluids(fInputs);
							
							if (slots[0] != null && slots[0].getItem() == ModItems.meteorite_sword_machined)
								slots[0] = new ItemStack(ModItems.meteorite_sword_treated);
						}
						power -= consumption;
					}
					else
						progress = 0;
				}
				else
					progress = 0;
				
				int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
				TileEntity te1 = null;
				TileEntity te2 = null;
				
				
				if(meta == 2)
				{
					te1 = worldObj.getTileEntity(xCoord - 2, yCoord, zCoord);
					te2 = worldObj.getTileEntity(xCoord + 3, yCoord, zCoord - 1);
				}
				if(meta == 3)
				{
					te1 = worldObj.getTileEntity(xCoord + 2, yCoord, zCoord);
					te2 = worldObj.getTileEntity(xCoord - 3, yCoord, zCoord + 1);
				}
				if(meta == 4)
				{
					te1 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 2);
					te2 = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord - 3);
				}
				if(meta == 5)
				{
					te1 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 2);
					te2 = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord + 3);
				}

				tryExchangeTemplates(te1, te2);
				
				if (te1 instanceof TileEntityChest)
				{
					TileEntityChest chest = (TileEntityChest)te1;

					for (int i = 5; i < 9; i++)
						tryFillContainer(chest, i);
				}
				
				if (te1 instanceof TileEntityHopper)
				{
					TileEntityHopper hopper = (TileEntityHopper)te1;

					for (int i = 5; i < 9; i++)
						tryFillContainer(hopper, i);
				}
				
				if (te1 instanceof TileEntityCrateIron)
				{
					TileEntityCrateIron crate = (TileEntityCrateIron)te1;

					for (int i = 5; i < 9; i++)
						tryFillContainer(crate, i);
				}
				
				if (te1 instanceof TileEntityCrateSteel)
				{
					TileEntityCrateSteel crate = (TileEntityCrateSteel)te1;

					for (int i = 5; i < 9; i++)
						tryFillContainer(crate, i);
				}
				
				//INPUT
				if (te2 instanceof TileEntityChest)
				{
					TileEntityChest chest = (TileEntityChest)te2;
					
					for (int i = 0; i < chest.getSizeInventory(); i++)
						if (tryFillAssembler(chest, i))
							break;
				}
				
				if (te2 instanceof TileEntityHopper)
				{
					TileEntityHopper hopper = (TileEntityHopper)te2;

					for (int i = 0; i < hopper.getSizeInventory(); i++)
						if (tryFillAssembler(hopper, i))
							break;
				}
				
				if (te2 instanceof TileEntityCrateIron)
				{
					TileEntityCrateIron hopper = (TileEntityCrateIron)te2;

					for (int i = 0; i < hopper.getSizeInventory(); i++)
						if (tryFillAssembler(hopper, i))
							break;
				}
				
				if(te2 instanceof TileEntityCrateSteel)
				{
					TileEntityCrateSteel hopper = (TileEntityCrateSteel)te2;

					for (int i = 0; i < hopper.getSizeInventory(); i++)
						if (tryFillAssembler(hopper, i))
							break;
				}

				if (isProgressing)
				{
					EasyLocation toMod = new EasyLocation(this);
					boolean sendPacket;
					switch (meta)
					{
					case 2:
					case 3:
					case 4:
					case 5:
						sendPacket = true;
						break;
					default:
						sendPacket = false;
						break;
					}
					switch (meta)
					{
					case 2:
						toMod.modifyCoord(0.375, 3, -0.625);
						break;
					case 3:
						toMod.modifyCoord(0.375, 3, 1.625);
						break;
					case 4:
						toMod.modifyCoord(0.375, 3, 0.625);
						break;
					case 5:
						toMod.modifyCoord(1.625, 3, 0.375);
						break;
					default:
						break;
					}
					if (sendPacket)
						PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(toMod, 1), Library.easyTargetPoint(toMod, 50));
				}
				
				PacketDispatcher.wrapper.sendToAllAround(new TEChemplantPacket(this), Library.easyTargetPoint(this, 150));
				PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(this), Library.easyTargetPoint(this, 50));
				PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(this, power), Library.easyTargetPoint(this, 50));
			}

		}
	}
	
	@Override
	public boolean tryFillAssembler(IInventory inventory, int slot)
	{
		FluidStack[] fInput = currRecipe.fIn;
		FluidStack[] fOutput = currRecipe.fOut;
		
		if(!((!Library.isArrayEmpty(currRecipe.inputs) || !Library.isArrayEmpty(fInput)) && 
				(!Library.isArrayEmpty(currRecipe.outputs) || !Library.isArrayEmpty(fOutput))))
			return false;
		else
		{
			ItemStack[] inputCopy = RecipesCommon.objectToStackArray(currRecipe.inputs.clone());
			if (Library.isArrayEmpty(inputCopy))
				return false;
			
			for (int i = 0; i < inputCopy.length; i++)
				inputCopy[i].stackSize = 1;
			
			if (inventory.getStackInSlot(slot) == null)
				return false;
			
			ItemStack stackCopy = inventory.getStackInSlot(slot).copy();
			stackCopy.stackSize = 1;
			
			boolean flag = false;
			
			for (int i = 0; i < inputCopy.length; i++)
				if (ItemStack.areItemStacksEqual(inputCopy[i], stackCopy) && ItemStack.areItemStackTagsEqual(inputCopy[i], stackCopy))
					flag = true;
			
			if (!flag)
				return false;
		}
		
		for(int i = 13; i < 17; i++)
		{
			if(slots[i] != null)
			{
			
				ItemStack stack1 = inventory.getStackInSlot(slot).copy();
				ItemStack stack2 = slots[i].copy();
				if(stack1 != null && stack2 != null)
				{
					stack1.stackSize = 1;
					stack2.stackSize = 1;
			
					if(isItemAcceptible(stack1, stack2) && slots[i].stackSize < slots[i].getMaxStackSize())
					{
						ItemStack stack3 = inventory.getStackInSlot(slot).copy();
						stack3.stackSize--;
						if(stack3.stackSize <= 0)
							stack3 = null;
						inventory.setInventorySlotContents(slot, stack3);
				
						slots[i].stackSize++;
						return true;
					}
				}
			}
		}
		for(int i = 13; i < 17; i++)
		{

			ItemStack stack2 = inventory.getStackInSlot(slot).copy();
			if(slots[i] == null && stack2 != null)
			{
				stack2.stackSize = 1;
				slots[i] = stack2.copy();
				
				ItemStack stack3 = inventory.getStackInSlot(slot).copy();
				stack3.stackSize--;
				if(stack3.stackSize <= 0)
					stack3 = null;
				inventory.setInventorySlotContents(slot, stack3);
				
				return true;
			}
		}

		return false;
	}
}
