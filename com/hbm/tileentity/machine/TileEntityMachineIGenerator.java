package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEIGeneratorPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineIGenerator extends TileEntity implements ISidedInventory, ISource {

	private ItemStack slots[];

	public int power;
	public int torque;
	public int heat;
	public int water;
	public int lubricant;
	public int fuel;
	public int burn;
	public int soundCycle = 0;
	public float rotation;
	public static final int maxPower = 100000;
	public static final int maxTorque = 2500;
	public static final int maxHeat = 7500;
	public static final int maxWater = 10000;
	public static final int maxLubricant = 10000;
	public static final int maxFuel = 50000;
	public int age = 0;
	public List<IConsumer> list = new ArrayList();

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0, 0 };
	private static final int[] slots_side = new int[] { 0 };

	private String customName;

	public TileEntityMachineIGenerator() {
		slots = new ItemStack[16];
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
		if (slots[i] != null) {
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
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.iGenerator";
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
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64;
		}
	}

	// You scrubs aren't needed for anything (right now)
	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if (slots[i] != null) {
			if (slots[i].stackSize <= j) {
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if (slots[i].stackSize == 0) {
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

		this.power = nbt.getInteger("power");
		this.torque = nbt.getInteger("torque");
		this.heat = nbt.getInteger("heat");
		this.water = nbt.getInteger("water");
		this.lubricant = nbt.getInteger("lubricant");
		this.fuel = nbt.getInteger("fuel");
		this.burn = nbt.getInteger("burn");
		slots = new ItemStack[getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < slots.length) {
				slots[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("power", power);
		nbt.setInteger("torque", torque);
		nbt.setInteger("heat", heat);
		nbt.setInteger("water", water);
		nbt.setInteger("lubricant", lubricant);
		nbt.setInteger("fuel", fuel);
		nbt.setInteger("burn", burn);
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < slots.length; i++) {
			if (slots[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				slots[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
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

	@Override
	public void updateEntity() {
		
		if (!worldObj.isRemote) {
			
			age++;
			if(age >= 20)
			{
				age = 0;
			}
			
			if(age == 9 || age == 19)
				ffgeuaInit();
			
			rotation += (this.torque / 100F);

			if(burn > 0) {
				burn--;
				
				if(heat + 10 <= maxHeat)
					heat += 10;
			}
			
			if(water > 0) {
				
				if(heat >= 8) {
				
					heat -= 8;
					torque += 10;
					water--;
				}
			} else {

				if(heat >= 4) {

					heat -= 4;
					torque += 5;
				}
			}

			heat += (5 * this.canLocateRTG());
			
			heat += (3 * this.canLocateWeakRTG());
			
			for(int i = 0; i < this.canLocateThermalElement(); i++) {
				if(heat >= 10) {
					heat -= 10;
					
					if(power + 10 <= maxPower) {
						power += 10;
					}
				}
			}
			
			this.power += this.torque;
			
			if(power > maxPower)
				power = maxPower;
			
			if(torque > 0) {
				if(lubricant > 0 ) {
					torque--;
					lubricant--;
				} else {
					torque -= 5;
				}
			}
			
			if(torque < 0)
				torque = 0;
			
			if(torque > maxTorque && this.hasLimiter())
				torque = maxTorque;
			
			if(torque > maxTorque) {
				worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.air);
			}

			if(this.getHeatScaled(100) < 90) {
				
				if(fuel > 0) {
					fuel --;
					if(heat + 10 <= maxHeat)
						heat += 10;
				}
				
				doSolidFuelTask();
			}
			doFuelTask();
			doLubeTask();
			doWaterTask();
			doBatteryTask();
		}
		
		if(!worldObj.isRemote) {
			PacketDispatcher.wrapper.sendToAll(new TEIGeneratorPacket(xCoord, yCoord, zCoord, rotation, torque));
			PacketDispatcher.wrapper.sendToAll(new LoopedSoundPacket(xCoord, yCoord, zCoord));
			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, power));
		}
	}
	
	public void doFuelTask() {

		if (slots[13] != null && slots[13].getItem() == ModItems.canister_fuel && fuel + 625 <= maxFuel) {
			if (slots[14] == null || slots[14] != null && slots[14].getItem() == slots[13].getItem().getContainerItem()
					&& slots[14].stackSize < slots[14].getMaxStackSize()) {
				if (slots[14] == null)
					slots[14] = new ItemStack(slots[13].getItem().getContainerItem());
				else
					slots[14].stackSize++;

				slots[13].stackSize--;
				if (slots[13].stackSize <= 0)
					slots[13] = null;

				fuel += 625;
			}
		}
		if (slots[13] != null && slots[13].getItem() == ModItems.canister_smear && fuel + 200 <= maxFuel) {
			if (slots[14] == null || slots[14] != null && slots[14].getItem() == slots[13].getItem().getContainerItem()
					&& slots[14].stackSize < slots[14].getMaxStackSize()) {
				if (slots[14] == null)
					slots[14] = new ItemStack(slots[13].getItem().getContainerItem());
				else
					slots[14].stackSize++;

				slots[13].stackSize--;
				if (slots[13].stackSize <= 0)
					slots[13] = null;

				fuel += 200;
			}
		}
		if (slots[13] != null && slots[13].getItem() == ModItems.canister_reoil && fuel + 350 <= maxFuel) {
			if (slots[14] == null || slots[14] != null && slots[14].getItem() == slots[13].getItem().getContainerItem()
					&& slots[14].stackSize < slots[14].getMaxStackSize()) {
				if (slots[14] == null)
					slots[14] = new ItemStack(slots[13].getItem().getContainerItem());
				else
					slots[14].stackSize++;

				slots[13].stackSize--;
				if (slots[13].stackSize <= 0)
					slots[13] = null;

				fuel += 350;
			}
		}
		if (slots[13] != null && slots[13].getItem() == ModItems.canister_petroil && fuel + 500 <= maxFuel) {
			if (slots[14] == null || slots[14] != null && slots[14].getItem() == slots[13].getItem().getContainerItem()
					&& slots[14].stackSize < slots[14].getMaxStackSize()) {
				if (slots[14] == null)
					slots[14] = new ItemStack(slots[13].getItem().getContainerItem());
				else
					slots[14].stackSize++;

				slots[13].stackSize--;
				if (slots[13].stackSize <= 0)
					slots[13] = null;

				fuel += 500;
			}
		}
		if (slots[13] != null && slots[13].getItem() == Item.getItemFromBlock(ModBlocks.red_barrel) && fuel + 5000 <= maxFuel) {
			if (slots[14] == null || slots[14] != null && slots[14].getItem() == ModItems.tank_steel
					&& slots[14].stackSize < slots[14].getMaxStackSize()) {
				if (slots[14] == null)
					slots[14] = new ItemStack(ModItems.tank_steel);
				else
					slots[14].stackSize++;

				slots[13].stackSize--;
				if (slots[13].stackSize <= 0)
					slots[13] = null;

				fuel += 5000;
			}
		}
		
		if (slots[13] != null && slots[13].getItem() == ModItems.inf_diesel)
			this.fuel = TileEntityMachineIGenerator.maxFuel;
	}
	
	public void doLubeTask() {
		
		if (slots[13] != null && slots[13].getItem() == ModItems.canister_canola && lubricant + 625 <= maxLubricant) {
			if (slots[14] == null || slots[14] != null && slots[14].getItem() == slots[13].getItem().getContainerItem()
					&& slots[14].stackSize < slots[14].getMaxStackSize()) {
				if (slots[14] == null)
					slots[14] = new ItemStack(slots[13].getItem().getContainerItem());
				else
					slots[14].stackSize++;

				slots[13].stackSize--;
				if (slots[13].stackSize <= 0)
					slots[13] = null;

				lubricant += 625;
			}
		}
	}
	
	public void doWaterTask() {

		if (slots[13] != null && slots[13].getItem() == Items.water_bucket && water + 625 <= maxWater) {
			if (slots[14] == null || slots[14] != null && slots[14].getItem() == slots[13].getItem().getContainerItem()
					&& slots[14].stackSize < slots[14].getMaxStackSize()) {
				if (slots[14] == null)
					slots[14] = new ItemStack(slots[13].getItem().getContainerItem());
				else
					slots[14].stackSize++;

				slots[13].stackSize--;
				if (slots[13].stackSize <= 0)
					slots[13] = null;

				water += 625;
			}
		}
		if (slots[13] != null && slots[13].getItem() == ModItems.rod_water && water + 625 <= maxWater) {
			if (slots[14] == null || slots[14] != null && slots[14].getItem() == slots[13].getItem().getContainerItem()
					&& slots[14].stackSize < slots[14].getMaxStackSize()) {
				if (slots[14] == null)
					slots[14] = new ItemStack(slots[13].getItem().getContainerItem());
				else
					slots[14].stackSize++;

				slots[13].stackSize--;
				if (slots[13].stackSize <= 0)
					slots[13] = null;

				water += 625;
			}
		}
		if (slots[13] != null && slots[13].getItem() == ModItems.rod_dual_water && water + 1250 <= maxWater) {
			if (slots[14] == null || slots[14] != null && slots[14].getItem() == slots[13].getItem().getContainerItem()
					&& slots[14].stackSize < slots[14].getMaxStackSize()) {
				if (slots[14] == null)
					slots[14] = new ItemStack(slots[13].getItem().getContainerItem());
				else
					slots[14].stackSize++;

				slots[13].stackSize--;
				if (slots[13].stackSize <= 0)
					slots[13] = null;

				water += 1250;
			}
		}
		if (slots[13] != null && slots[13].getItem() == ModItems.rod_quad_water && water + 2500 <= maxWater) {
			if (slots[14] == null || slots[14] != null && slots[14].getItem() == slots[13].getItem().getContainerItem()
					&& slots[14].stackSize < slots[14].getMaxStackSize()) {
				if (slots[14] == null)
					slots[14] = new ItemStack(slots[13].getItem().getContainerItem());
				else
					slots[14].stackSize++;

				slots[13].stackSize--;
				if (slots[13].stackSize <= 0)
					slots[13] = null;

				water += 2500;
			}
		}
		
		if (slots[13] != null && slots[13].getItem() == ModItems.inf_water)
			this.water = TileEntityMachineIGenerator.maxWater;
	}
	
	public void doSolidFuelTask() {
		if(slots[12] != null && slots[12].getItem() == Items.coal && burn <= 0)
		{
			slots[12].stackSize -= 1;
			burn = 200;
			if(slots[12].stackSize == 0)
			{
				slots[12] = null;
			}
		}
		if(slots[12] != null && slots[12].getItem() == ModItems.powder_coal && burn <= 0)
		{
			slots[12].stackSize -= 1;
			burn = 200;
			if(slots[12].stackSize == 0)
			{
				slots[12] = null;
			}
		}
		if(slots[12] != null && slots[12].getItem() == Item.getItemFromBlock(Blocks.coal_block) && burn <= 0)
		{
			slots[12].stackSize -= 1;
			burn = 2000;
			if(slots[12].stackSize == 0)
			{
				slots[12] = null;
			}
		}
	}
	
	public void doBatteryTask() {
		power = Library.chargeItemsFromTE(slots, 15, power, maxPower);
	}

	public int canLocateThermalElement() {
		
		int thermo = 0;
		
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null && slots[i].getItem() == ModItems.thermo_element)
				thermo ++;
		}
		
		return thermo;
	}

	public int canLocateRTG() {
		
		int rtg = 0;
		
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null && slots[i].getItem() == ModItems.pellet_rtg)
				rtg ++;
		}
		
		return rtg;
	}

	public int canLocateWeakRTG() {
		
		int rtg = 0;
		
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null && slots[i].getItem() == ModItems.pellet_rtg_weak)
				rtg ++;
		}
		
		return rtg;
	}

	public boolean hasLimiter() {
		
		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null && slots[i].getItem() == ModItems.limiter)
				return true;
		}
		
		return false;
	}
	
	public int getFuelScaled(int i) {
		return (fuel * i) / maxFuel;
	}
	
	public int getLubeScaled(int i) {
		return (lubricant * i) / maxLubricant;
	}
	
	public int getWaterScaled(int i) {
		return (water * i) / maxWater;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / maxHeat;
	}
	
	public int getTorqueScaled(int i) {
		return (torque * i) / maxTorque;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		int i = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		if(i == 5) {
			ffgeua(this.xCoord + 3, this.yCoord, this.zCoord, getTact());
			ffgeua(this.xCoord - 4, this.yCoord, this.zCoord, getTact());
		}
		if(i == 3) {
			ffgeua(this.xCoord, this.yCoord, this.zCoord + 3, getTact());
			ffgeua(this.xCoord, this.yCoord, this.zCoord - 4, getTact());
		}
		if(i == 4) {
			ffgeua(this.xCoord + 4, this.yCoord, this.zCoord, getTact());
			ffgeua(this.xCoord - 3, this.yCoord, this.zCoord, getTact());
		}
		if(i == 2) {
			ffgeua(this.xCoord, this.yCoord, this.zCoord + 4, getTact());
			ffgeua(this.xCoord, this.yCoord, this.zCoord - 3, getTact());
		}
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
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
	public int getSPower() {
		return power;
	}

	@Override
	public void setSPower(int i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
	}

}
