package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.MachineRecipes;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityLoadedBase;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineTurbine extends TileEntityLoadedBase implements ISidedInventory, IFluidContainer, IFluidAcceptor, IFluidSource, IEnergyGenerator, IFluidStandardTransceiver {

	private ItemStack slots[];

	public long power;
	public static final long maxPower = 1000000;
	public int age = 0;
	public List<IFluidAcceptor> list2 = new ArrayList();
	public FluidTank[] tanks;
	
	private static final int[] slots_top = new int[] {4};
	private static final int[] slots_bottom = new int[] {6};
	private static final int[] slots_side = new int[] {4};
	
	private String customName;
	
	public TileEntityMachineTurbine() {
		slots = new ItemStack[7];
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(Fluids.STEAM, 64000, 0);
		tanks[1] = new FluidTank(Fluids.SPENTSTEAM, 128000, 1);
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
		return this.hasCustomInventoryName() ? this.customName : "container.machineTurbine";
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
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <=64;
		}
	}
	
	//You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		
		if(i == 4)
			if(stack != null && stack.getItem() instanceof IBatteryItem)
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

		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "steam");
		power = nbt.getLong("power");
		
		slots = new ItemStack[getSizeInventory()];
		
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
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "steam");
		nbt.setLong("power", power);
		
		NBTTagList list = new NBTTagList();
		
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
        return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
    }

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	public long getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote)
		{
			age++;
			if(age >= 2)
			{
				age = 0;
			}
			
			this.subscribeToAllAround(tanks[0].getTankType(), this);
			fillFluidInit(tanks[1].getTankType());
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
				this.sendPower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);

			tanks[0].setType(0, 1, slots);
			tanks[0].loadTank(2, 3, slots);
			power = Library.chargeItemsFromTE(slots, 4, power, maxPower);
			
			Object[] outs = MachineRecipes.getTurbineOutput(tanks[0].getTankType());
			
			if(outs == null) {
				tanks[1].setTankType(Fluids.NONE);
			} else {
				tanks[1].setTankType((FluidType) outs[0]);
				
				int processMax = 1200;																//the maximum amount of cycles based on the 1.2k cycle cap (subject to change)
				int processSteam = tanks[0].getFill() / (Integer)outs[2];							//the maximum amount of cycles depending on steam
				int processWater = (tanks[1].getMaxFill() - tanks[1].getFill()) / (Integer)outs[1];	//the maximum amount of cycles depending on water
				
				int cycles = Math.min(processMax, Math.min(processSteam, processWater));
				
				tanks[0].setFill(tanks[0].getFill() - (Integer)outs[2] * cycles);
				tanks[1].setFill(tanks[1].getFill() + (Integer)outs[1] * cycles);
				
				power += (Integer)outs[3] * cycles;
				
				if(power > maxPower)
					power = maxPower;
			}
			
			this.sendFluidToAll(tanks[1].getTankType(), this);
			
			tanks[1].unloadTank(5, 6, slots);
			
			for(int i = 0; i < 2; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord - 1, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}
	
	@Override
	public boolean getTact() {
		if(age == 0)
		{
			return true;
		}
		
		return false;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		
		return 0;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		
		return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}
	
	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list2;
	}
	
	@Override
	public void clearFluidList(FluidType type) {
		list2.clear();
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
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks[1] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tanks[0] };
	}
}
