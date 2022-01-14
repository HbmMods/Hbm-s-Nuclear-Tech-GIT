package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.handler.FluidTypeHandler.FluidTypeTheOldOne;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEChemplantPacket;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyUser;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachineChemplant extends TileEntity implements ISidedInventory, IEnergyUser, IFluidContainer, IFluidAcceptor, IFluidSource {

	private ItemStack slots[];

	public long power;
	public static final long maxPower = 100000;
	public int progress;
	public int maxProgress = 100;
	public boolean isProgressing;
	int age = 0;
	int consumption = 100;
	int speed = 100;
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list1 = new ArrayList();
	public List<IFluidAcceptor> list2 = new ArrayList();
	
	Random rand = new Random();
	
	private String customName;
	
	public TileEntityMachineChemplant() {
		slots = new ItemStack[21];
		tanks = new FluidTank[4];
		tanks[0] = new FluidTank(FluidTypeTheOldOne.NONE, 24000, 0);
		tanks[1] = new FluidTank(FluidTypeTheOldOne.NONE, 24000, 1);
		tanks[2] = new FluidTank(FluidTypeTheOldOne.NONE, 24000, 2);
		tanks[3] = new FluidTank(FluidTypeTheOldOne.NONE, 24000, 3);
	}

	@Override
	public int getSizeInventory() {
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return slots[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(slots[i] != null)
		{
			ItemStack itemStack = slots[i];
			slots[i] = null;
			return itemStack;
		} else {
		return null;
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		slots[i] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit())
		{
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.chemplant";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=128;
		}
	}
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 0)
			if(itemStack.getItem() instanceof IBatteryItem)
				return true;
		
		if(i == 1)
			return true;
		
		return false;
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null)
		{
			if(slots[i].stackSize <= j)
			{
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0)
			{
				slots[i] = null;
			}
			
			return itemStack1;
		} else {
			return null;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);
		
		this.power = nbt.getLong("powerTime");
		slots = new ItemStack[getSizeInventory()];

		tanks[0].readFromNBT(nbt, "input1");
		tanks[1].readFromNBT(nbt, "input2");
		tanks[2].readFromNBT(nbt, "output1");
		tanks[3].readFromNBT(nbt, "output2");
		
		for(int i = 0; i < list.tagCount(); i++)
		{
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length)
			{
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", power);
		NBTTagList list = new NBTTagList();

		tanks[0].writeToNBT(nbt, "input1");
		tanks[1].writeToNBT(nbt, "input2");
		tanks[2].writeToNBT(nbt, "output1");
		tanks[3].writeToNBT(nbt, "output2");
		
		for(int i = 0; i < slots.length; i++)
		{
			if(slots[i] != null)
			{
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte)i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return new int[] { 0 };
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (progress * i) / maxProgress;
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
			this.consumption = 100;
			this.speed = 100;
			
			UpgradeManager.eval(slots, 1, 3);

			int speedLevel = Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = UpgradeManager.getLevel(UpgradeType.OVERDRIVE);
			
			speed -= speedLevel * 25;
			consumption += speedLevel * 300;
			speed += powerLevel * 5;
			consumption -= powerLevel * 30;
			speed /= (overLevel + 1);
			consumption *= (overLevel + 1);
			
			isProgressing = false;
			
			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19) {
				fillFluidInit(tanks[2].getTankType());
				fillFluidInit(tanks[3].getTankType());
			}
			
			setContainers();
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			tanks[0].loadTank(17, 19, slots);
			tanks[1].loadTank(18, 20, slots);
			
			if(slots[17] != null && !(slots[17].getItem() == ModItems.fluid_barrel_infinite || slots[17].getItem() == ModItems.inf_water || slots[17].getItem() == ModItems.inf_water_mk2))
				tanks[0].unloadTank(17, 19, slots);
			if(slots[18] != null && !(slots[18].getItem() == ModItems.fluid_barrel_infinite || slots[18].getItem() == ModItems.inf_water || slots[18].getItem() == ModItems.inf_water_mk2))
				tanks[1].unloadTank(18, 20, slots);
			
			tanks[2].unloadTank(9, 11, slots);
			tanks[3].unloadTank(10, 12, slots);
			
			for(int i = 0; i < 4; i++) {
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			}

			FluidStack[] inputs = MachineRecipes.getFluidInputFromTempate(slots[4]);
			FluidStack[] outputs = MachineRecipes.getFluidOutputFromTempate(slots[4]);
			
			if((MachineRecipes.getChemInputFromTempate(slots[4]) != null || !Library.isArrayEmpty(inputs)) && 
					(MachineRecipes.getChemOutputFromTempate(slots[4]) != null || !Library.isArrayEmpty(outputs))) {
				this.maxProgress = (ItemChemistryTemplate.getProcessTime(slots[4]) * speed) / 100;
				
				if(power >= consumption && removeItems(MachineRecipes.getChemInputFromTempate(slots[4]), cloneItemStackProper(slots)) && hasFluidsStored(inputs)) {
					
					if(hasSpaceForItems(MachineRecipes.getChemOutputFromTempate(slots[4])) && hasSpaceForFluids(outputs)) {
						progress++;
						isProgressing = true;
						
						if(progress >= maxProgress) {
							progress = 0;

							addItems(MachineRecipes.getChemOutputFromTempate(slots[4]));
							addFluids(outputs);

							removeItems(MachineRecipes.getChemInputFromTempate(slots[4]), slots);
							removeFluids(inputs);
							
							if(slots[0] != null && slots[0].getItem() == ModItems.meteorite_sword_machined)
								slots[0] = new ItemStack(ModItems.meteorite_sword_treated);
						}
						
						power -= consumption;
					}
				} else
					progress = 0;
			} else
				progress = 0;

			int meta = worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			TileEntity te1 = null;
			TileEntity te2 = null;
			
			
			if(meta == 2) {
				te1 = worldObj.getTileEntity(xCoord - 2, yCoord, zCoord);
				te2 = worldObj.getTileEntity(xCoord + 3, yCoord, zCoord - 1);
			}
			if(meta == 3) {
				te1 = worldObj.getTileEntity(xCoord + 2, yCoord, zCoord);
				te2 = worldObj.getTileEntity(xCoord - 3, yCoord, zCoord + 1);
			}
			if(meta == 4) {
				te1 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 2);
				te2 = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord - 3);
			}
			if(meta == 5) {
				te1 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 2);
				te2 = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord + 3);
			}
			
			tryExchangeTemplates(te1, te2);
			
			//OUTPUT
			if(te1 instanceof TileEntityChest) {
				TileEntityChest chest = (TileEntityChest)te1;

				for(int i = 5; i < 9; i++)
					tryFillContainer(chest, i);
			}
			
			if(te1 instanceof TileEntityHopper) {
				TileEntityHopper hopper = (TileEntityHopper)te1;

				for(int i = 5; i < 9; i++)
					tryFillContainer(hopper, i);
			}
			
			if(te1 instanceof TileEntityCrateIron) {
				TileEntityCrateIron crate = (TileEntityCrateIron)te1;

				for(int i = 5; i < 9; i++)
					tryFillContainer(crate, i);
			}
			
			if(te1 instanceof TileEntityCrateSteel) {
				TileEntityCrateSteel crate = (TileEntityCrateSteel)te1;

				for(int i = 5; i < 9; i++)
					tryFillContainer(crate, i);
			}
			
			//INPUT
			if(te2 instanceof TileEntityChest) {
				TileEntityChest chest = (TileEntityChest)te2;
				
				for(int i = 0; i < chest.getSizeInventory(); i++)
					if(tryFillAssembler(chest, i))
						break;
			}
			
			if(te2 instanceof TileEntityHopper) {
				TileEntityHopper hopper = (TileEntityHopper)te2;

				for(int i = 0; i < hopper.getSizeInventory(); i++)
					if(tryFillAssembler(hopper, i))
						break;
			}
			
			if(te2 instanceof TileEntityCrateIron) {
				TileEntityCrateIron hopper = (TileEntityCrateIron)te2;

				for(int i = 0; i < hopper.getSizeInventory(); i++)
					if(tryFillAssembler(hopper, i))
						break;
			}
			
			if(te2 instanceof TileEntityCrateSteel) {
				TileEntityCrateSteel hopper = (TileEntityCrateSteel)te2;

				for(int i = 0; i < hopper.getSizeInventory(); i++)
					if(tryFillAssembler(hopper, i))
						break;
			}
			
			if(isProgressing) {
				if(meta == 2) {
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(xCoord + 0.375, yCoord + 3, zCoord - 0.625, 1),
							new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.375, yCoord + 3, zCoord - 0.625, 50));
				}
				if(meta == 3) {
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(xCoord + 0.625, yCoord + 3, zCoord + 1.625, 1),
							new TargetPoint(worldObj.provider.dimensionId, xCoord + 0.625, yCoord + 3, zCoord + 1.625, 50));
				}
				if(meta == 4) {
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(xCoord - 0.625, yCoord + 3, zCoord + 0.625, 1),
							new TargetPoint(worldObj.provider.dimensionId, xCoord - 0.625, yCoord + 3, zCoord + 0.625, 50));
				}
				if(meta == 5) {
					PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(xCoord + 1.625, yCoord + 3, zCoord + 0.375, 1),
							new TargetPoint(worldObj.provider.dimensionId, xCoord + 1.625, yCoord + 3, zCoord + 0.375, 50));
				}
			}
			
			PacketDispatcher.wrapper.sendToAllAround(new TEChemplantPacket(xCoord, yCoord, zCoord, isProgressing), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(xCoord, yCoord, zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
		
	}
	
	private void updateConnections() {
		this.getBlockMetadata();
		
		if(this.blockMetadata == 5) {
			this.trySubscribe(worldObj, xCoord - 2, yCoord, zCoord, Library.NEG_X);
			this.trySubscribe(worldObj, xCoord - 2, yCoord, zCoord + 1, Library.NEG_X);
			this.trySubscribe(worldObj, xCoord + 3, yCoord, zCoord, Library.POS_X);
			this.trySubscribe(worldObj, xCoord + 3, yCoord, zCoord + 1, Library.POS_X);
			
		} else if(this.blockMetadata == 3) {
			this.trySubscribe(worldObj, xCoord, yCoord, zCoord - 2, Library.NEG_Z);
			this.trySubscribe(worldObj, xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z);
			this.trySubscribe(worldObj, xCoord, yCoord, zCoord + 3, Library.POS_Z);
			this.trySubscribe(worldObj, xCoord - 1, yCoord, zCoord + 3, Library.POS_Z);
			
		} else if(this.blockMetadata == 4) {
			this.trySubscribe(worldObj, xCoord + 2, yCoord, zCoord, Library.POS_X);
			this.trySubscribe(worldObj, xCoord + 2, yCoord, zCoord - 1, Library.POS_X);
			this.trySubscribe(worldObj, xCoord - 3, yCoord, zCoord, Library.NEG_X);
			this.trySubscribe(worldObj, xCoord - 3, yCoord, zCoord - 1, Library.NEG_X);
			
		} else if(this.blockMetadata == 2) {
			this.trySubscribe(worldObj, xCoord, yCoord, zCoord + 2, Library.POS_Z);
			this.trySubscribe(worldObj, xCoord + 1, yCoord, zCoord + 2, Library.POS_Z);
			this.trySubscribe(worldObj, xCoord, yCoord, zCoord - 3, Library.NEG_Z);
			this.trySubscribe(worldObj, xCoord + 1, yCoord, zCoord - 3, Library.NEG_Z);
		}
	}

	public boolean tryExchangeTemplates(TileEntity te1, TileEntity te2) {
		//validateTe sees if it's a valid inventory tile entity
		boolean te1Valid = validateTe(te1);
		boolean te2Valid = validateTe(te2);
		
		if(te1Valid && te2Valid){
			IInventory iTe1 = (IInventory)te1;
			IInventory iTe2 = (IInventory)te2;
			boolean openSlot = false;
			boolean existingTemplate = false;
			boolean filledContainer = false;
			//Check if there's an existing template and an open slot
			for(int i = 0; i < iTe1.getSizeInventory(); i++){
				if(iTe1.getStackInSlot(i) == null){
					openSlot = true;
					
				}
				
			}
			if(this.slots[4] != null){
				existingTemplate = true;
			}
			//Check if there's a template in input
			for(int i = 0; i < iTe2.getSizeInventory(); i++){
				if(iTe2.getStackInSlot(i) != null && iTe2.getStackInSlot(i).getItem() instanceof ItemChemistryTemplate){
					if(openSlot && existingTemplate){
						filledContainer = tryFillContainer(iTe1, 4);
						
					}
					if(filledContainer){
					ItemStack copy = iTe2.getStackInSlot(i).copy();
					iTe2.setInventorySlotContents(i, null);
					this.slots[4] = copy;
					}
				}
				
			}
			
		
		}
		return false;
		
	}

	private boolean validateTe(TileEntity te) {
		if(te != null && te instanceof TileEntityChest) {
			return true;
			
		}
		
		if(te != null && te instanceof TileEntityHopper) {
			return true;
		}
		
		if(te != null && te instanceof TileEntityCrateIron) {
			return true;
		}
		
		if(te != null && te instanceof TileEntityCrateSteel) {
			return true;
		}
		return false;
	}
	
	private void setContainers() {
		
		if(slots[4] == null || (slots[4] != null && !(slots[4].getItem() instanceof ItemChemistryTemplate))) {
		} else {
			FluidStack[] inputs = MachineRecipes.getFluidInputFromTempate(slots[4]);
			FluidStack[] outputs = MachineRecipes.getFluidOutputFromTempate(slots[4]);

			tanks[0].setTankType(inputs[0] == null ? FluidTypeTheOldOne.NONE : inputs[0].type);
			tanks[1].setTankType(inputs[1] == null ? FluidTypeTheOldOne.NONE : inputs[1].type);
			tanks[2].setTankType(outputs[0] == null ? FluidTypeTheOldOne.NONE : outputs[0].type);
			tanks[3].setTankType(outputs[1] == null ? FluidTypeTheOldOne.NONE : outputs[1].type);
		}
	}
	
	public boolean hasFluidsStored(FluidStack[] fluids) {
		if(Library.isArrayEmpty(fluids))
			return true;
		
		if((fluids[0] == null || fluids[0] != null && fluids[0].fill <= tanks[0].getFill()) && 
				(fluids[1] == null || fluids[1] != null && fluids[1].fill <= tanks[1].getFill()))
			return true;
		
		return false;
	}
	
	public boolean hasSpaceForFluids(FluidStack[] fluids) {
		if(Library.isArrayEmpty(fluids))
			return true;
		
		if((fluids[0] == null || fluids[0] != null && tanks[2].getFill() + fluids[0].fill <= tanks[2].getMaxFill()) && 
				(fluids[1] == null || fluids[1] != null && tanks[3].getFill() + fluids[1].fill <= tanks[3].getMaxFill()))
			return true;
		
		return false;
	}
	
	public void removeFluids(FluidStack[] fluids) {
		if(Library.isArrayEmpty(fluids))
			return;

		if(fluids[0] != null)
			tanks[0].setFill(tanks[0].getFill() - fluids[0].fill);
		if(fluids[1] != null)
			tanks[1].setFill(tanks[1].getFill() - fluids[1].fill);
	}
	
	public boolean hasSpaceForItems(ItemStack[] stacks) {
		if(stacks == null)
			return true;
		if(stacks != null && Library.isArrayEmpty(stacks))
			return true;

		ItemStack sta0 = Library.carefulCopy(slots[5]);
		if(sta0 != null)
			sta0.stackSize = 1;
		ItemStack sta1 = Library.carefulCopy(stacks[0]);
		if(sta1 != null)
			sta1.stackSize = 1;
		ItemStack sta2 = Library.carefulCopy(slots[6]);
		if(sta2 != null)
			sta2.stackSize = 1;
		ItemStack sta3 = Library.carefulCopy(stacks[1]);
		if(sta3 != null)
			sta3.stackSize = 1;
		ItemStack sta4 = Library.carefulCopy(slots[7]);
		if(sta4 != null)
			sta4.stackSize = 1;
		ItemStack sta5 = Library.carefulCopy(stacks[2]);
		if(sta5 != null)
			sta5.stackSize = 1;
		ItemStack sta6 = Library.carefulCopy(slots[8]);
		if(sta6 != null)
			sta6.stackSize = 1;
		ItemStack sta7 = Library.carefulCopy(stacks[3]);
		if(sta7 != null)
			sta7.stackSize = 1;
		
		if((slots[5] == null || stacks[0] == null || (stacks[0] != null && isItemAcceptible(sta0, sta1) && slots[5].stackSize + stacks[0].stackSize <= slots[5].getMaxStackSize())) && 
				(slots[6] == null || stacks[1] == null || (stacks[1] != null && isItemAcceptible(sta2, sta3) && slots[6].stackSize + stacks[1].stackSize <= slots[6].getMaxStackSize())) && 
				(slots[7] == null || stacks[2] == null || (stacks[2] != null && isItemAcceptible(sta4, sta5) && slots[7].stackSize + stacks[2].stackSize <= slots[7].getMaxStackSize())) && 
				(slots[8] == null || stacks[3] == null || (stacks[3] != null && isItemAcceptible(sta6, sta7) && slots[8].stackSize + stacks[3].stackSize <= slots[8].getMaxStackSize())))
			return true;
			
		return false;
	}
	
	public void addItems(ItemStack[] stacks) {
		if(slots[5] == null && stacks[0] != null)
			slots[5] = stacks[0].copy();
		else if (slots[5] != null && stacks[0] != null)
			slots[5].stackSize += stacks[0].stackSize;

		if(slots[6] == null && stacks[1] != null)
			slots[6] = stacks[1].copy();
		else if (slots[6] != null && stacks[1] != null)
			slots[6].stackSize += stacks[1].stackSize;

		if(slots[7] == null && stacks[2] != null)
			slots[7] = stacks[2].copy();
		else if (slots[7] != null && stacks[2] != null)
			slots[7].stackSize += stacks[2].stackSize;

		if(slots[8] == null && stacks[3] != null)
			slots[8] = stacks[3].copy();
		else if (slots[8] != null && stacks[3] != null)
			slots[8].stackSize += stacks[3].stackSize;
	}
	
	public void addFluids(FluidStack[] stacks) {
		if(stacks[0] != null)
			tanks[2].setFill(tanks[2].getFill() + stacks[0].fill);
		if(stacks[1] != null)
			tanks[3].setFill(tanks[3].getFill() + stacks[1].fill);
	}
	
	//I can't believe that worked.
	public ItemStack[] cloneItemStackProper(ItemStack[] array) {
		ItemStack[] stack = new ItemStack[array.length];
		
		for(int i = 0; i < array.length; i++)
			if(array[i] != null)
				stack[i] = array[i].copy();
			else
				stack[i] = null;
		
		return stack;
	}
	
	//Unloads output into chests
	public boolean tryFillContainer(IInventory inventory, int slot) {
		
		int size = inventory.getSizeInventory();

		for(int i = 0; i < size; i++) {
			if(inventory.getStackInSlot(i) != null) {
				
				if(slots[slot] == null)
					return false;
				
				ItemStack sta1 = inventory.getStackInSlot(i).copy();
				ItemStack sta2 = slots[slot].copy();
				if(sta1 != null && sta2 != null) {
					sta1.stackSize = 1;
					sta2.stackSize = 1;
				
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inventory.getStackInSlot(i).stackSize < inventory.getStackInSlot(i).getMaxStackSize()) {
						slots[slot].stackSize--;
						
						if(slots[slot].stackSize <= 0)
							slots[slot] = null;
						
						ItemStack sta3 = inventory.getStackInSlot(i).copy();
						sta3.stackSize++;
						inventory.setInventorySlotContents(i, sta3);
					
						return true;
					}
				}
			}
		}
		for(int i = 0; i < size; i++) {
			
			if(slots[slot] == null)
				return false;
			
			ItemStack sta2 = slots[slot].copy();
			if(inventory.getStackInSlot(i) == null && sta2 != null) {
				sta2.stackSize = 1;
				slots[slot].stackSize--;
				
				if(slots[slot].stackSize <= 0)
					slots[slot] = null;
				
				inventory.setInventorySlotContents(i, sta2);
					
				return true;
			}
		}
		
		return false;
	}
	
	//Loads assembler's input queue from chests
	public boolean tryFillAssembler(IInventory inventory, int slot) {

		FluidStack[] inputs = MachineRecipes.getFluidInputFromTempate(slots[4]);
		FluidStack[] outputs = MachineRecipes.getFluidOutputFromTempate(slots[4]);
		
		if(!((MachineRecipes.getChemInputFromTempate(slots[4]) != null || !Library.isArrayEmpty(inputs)) && 
				(MachineRecipes.getChemOutputFromTempate(slots[4]) != null || !Library.isArrayEmpty(outputs))))
			return false;
		else {
			List<ItemStack> list = copyItemStackList(MachineRecipes.getChemInputFromTempate(slots[4]));
			if(list == null || list.isEmpty())
				return false;
			
			for(int i = 0; i < list.size(); i++)
				list.get(i).stackSize = 1;


			if(inventory.getStackInSlot(slot) == null)
				return false;
			
			ItemStack stack = inventory.getStackInSlot(slot).copy();
			stack.stackSize = 1;
			
			boolean flag = false;
			
			for(int i = 0; i < list.size(); i++)
				if(ItemStack.areItemStacksEqual(stack, list.get(i)) && ItemStack.areItemStackTagsEqual(stack, list.get(i)))
					flag = true;
			
			if(!flag)
				return false;
			
		}
		
		for(int i = 13; i < 17; i++) {
			
			if(slots[i] != null) {
			
				ItemStack sta1 = inventory.getStackInSlot(slot).copy();
				ItemStack sta2 = slots[i].copy();
				if(sta1 != null && sta2 != null) {
					sta1.stackSize = 1;
					sta2.stackSize = 1;
			
					if(isItemAcceptible(sta1, sta2) && slots[i].stackSize < slots[i].getMaxStackSize()) {
						ItemStack sta3 = inventory.getStackInSlot(slot).copy();
						sta3.stackSize--;
						if(sta3.stackSize <= 0)
							sta3 = null;
						inventory.setInventorySlotContents(slot, sta3);
				
						slots[i].stackSize++;
						return true;
					}
				}
			}
		}
		
		for(int i = 13; i < 17; i++) {

			ItemStack sta2 = inventory.getStackInSlot(slot).copy();
			if(slots[i] == null && sta2 != null) {
				sta2.stackSize = 1;
				slots[i] = sta2.copy();
				
				ItemStack sta3 = inventory.getStackInSlot(slot).copy();
				sta3.stackSize--;
				if(sta3.stackSize <= 0)
					sta3 = null;
				inventory.setInventorySlotContents(slot, sta3);
				
				return true;
			}
		}
		
		return false;
	}
	
	//boolean true: remove items, boolean false: simulation mode
	public boolean removeItems(List<ItemStack> stack, ItemStack[] array) {
		
		if(stack == null || stack.isEmpty())
			return true;
		
		for(int i = 0; i < stack.size(); i++) {
			for(int j = 0; j < stack.get(i).stackSize; j++) {
				ItemStack sta = stack.get(i).copy();
				sta.stackSize = 1;
			
				if(!canRemoveItemFromArray(sta, array))
					return false;
			}
		}
		
		return true;
		
	}
	
	public boolean canRemoveItemFromArray(ItemStack stack, ItemStack[] array) {

		ItemStack st = stack.copy();
		
		if(st == null)
			return true;
		
		for(int i = 13; i < 17; i++) {
			
			if(array[i] != null) {
				ItemStack sta = array[i].copy();
				sta.stackSize = 1;
			
				if(sta != null && isItemAcceptible(sta, st) && array[i].stackSize > 0) {
					array[i].stackSize--;
					
					if(array[i].stackSize <= 0)
						array[i] = null;
					
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isItemAcceptible(ItemStack stack1, ItemStack stack2) {
		
		if(stack1 != null && stack2 != null) {
			if(ItemStack.areItemStacksEqual(stack1, stack2))
				return true;
		
			int[] ids1 = OreDictionary.getOreIDs(stack1);
			int[] ids2 = OreDictionary.getOreIDs(stack2);
			
			if(ids1 != null && ids2 != null && ids1.length > 0 && ids2.length > 0) {
				for(int i = 0; i < ids1.length; i++)
					for(int j = 0; j < ids2.length; j++)
						if(ids1[i] == ids2[j])
							return true;
			}
		}
		
		return false;
	}
	
	public static List<ItemStack> copyItemStackList(List<ItemStack> list){
        List<ItemStack> newList = new ArrayList<ItemStack>();
        if(list == null || list.isEmpty())
            return newList;
        for(ItemStack stack : list){
            newList.add(stack.copy());
        }
        return newList;
    }

	@Override
	public void setPower(long i) {
		power = i;
		
	}

	@Override
	public long getPower() {
		return power;
		
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if(index < 4 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if(index < 4 && tanks[index] != null)
			tanks[index].setTankType(type);
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
		else if(type.name().equals(tanks[2].getTankType().name()))
			tanks[2].setFill(i);
		else if(type.name().equals(tanks[3].getTankType().name()))
			tanks[3].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if(type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getFill();
		else if(type.name().equals(tanks[3].getTankType().name()))
			return tanks[3].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getMaxFill();
		else
			return 0;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		int meta = worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		if(meta == 5) {
			fillFluid(this.xCoord - 2, this.yCoord, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 1, getTact(), type);
			fillFluid(this.xCoord + 3, this.yCoord, this.zCoord, getTact(), type);
			fillFluid(this.xCoord + 3, this.yCoord, this.zCoord + 1, getTact(), type);
		}
		
		if(meta == 3) {
			fillFluid(this.xCoord, this.yCoord, this.zCoord - 2, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 2, getTact(), type);
			fillFluid(this.xCoord, this.yCoord, this.zCoord + 3, getTact(), type);
			fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 3, getTact(), type);
		}
		
		if(meta == 2) {
			fillFluid(this.xCoord, this.yCoord, this.zCoord + 2, getTact(), type);
			fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 2, getTact(), type);
			fillFluid(this.xCoord, this.yCoord, this.zCoord - 3, getTact(), type);
			fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 3, getTact(), type);
		}
		
		if(meta == 4) {
			fillFluid(this.xCoord + 2, this.yCoord, this.zCoord, getTact(), type);
			fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 1, getTact(), type);
			fillFluid(this.xCoord - 3, this.yCoord, this.zCoord, getTact(), type);
			fillFluid(this.xCoord - 3, this.yCoord, this.zCoord - 1, getTact(), type);
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type.name().equals(tanks[2].getTankType().name()))
			return list1;
		if(type.name().equals(tanks[3].getTankType().name()))
			return list2;
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type.name().equals(tanks[2].getTankType().name()))
			list1.clear();
		if(type.name().equals(tanks[3].getTankType().name()))
			list2.clear();
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tanks[0]);
		list.add(tanks[1]);
		list.add(tanks[2]);
		list.add(tanks[3]);
		
		return list;
	}
}
