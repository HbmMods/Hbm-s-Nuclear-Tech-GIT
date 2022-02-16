package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyGenerator;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineGasFlare extends TileEntity implements ISidedInventory, IEnergyGenerator, IFluidContainer, IFluidAcceptor {

	private ItemStack slots[];
	
	public long power;
	public static final long maxPower = 100000;
	public FluidTank tank;
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2, 0};
	private static final int[] slots_side = new int[] {0};
	Random rand = new Random();
	
	private String customName;
	
	public TileEntityMachineGasFlare() {
		slots = new ItemStack[3];
		tank = new FluidTank(Fluids.GAS, 64000, 0);
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
		return this.hasCustomInventoryName() ? this.customName : "container.gasFlare";
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
		tank.readFromNBT(nbt, "gas");
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
		nbt.setLong("powerTime", power);
		tank.writeToNBT(nbt, "gas");
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
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {

			this.sendPower(worldObj, xCoord + 2, yCoord, zCoord, Library.POS_X);
			this.sendPower(worldObj, xCoord - 2, yCoord, zCoord, Library.NEG_X);
			this.sendPower(worldObj, xCoord, yCoord, zCoord + 2, Library.POS_Z);
			this.sendPower(worldObj, xCoord, yCoord, zCoord - 2, Library.NEG_Z);
			
			tank.loadTank(1, 2, slots);
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			if(tank.getFill() >= 10) {
				tank.setFill(tank.getFill() - 10);
				power += 50;
				
				if(power > maxPower)
					power = maxPower;

	    		worldObj.spawnEntityInWorld(new EntityGasFlameFX(worldObj, this.xCoord + 0.5F, this.yCoord + 11F, this.zCoord + 0.5F, 0.0, 0.0, 0.0));
				ExplosionThermo.setEntitiesOnFire(worldObj, this.xCoord, this.yCoord + 11, zCoord, 5);
	    		
	    		if(worldObj.getTotalWorldTime() % 5 == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord + 11, this.zCoord, "hbm:weapon.flamethrowerShoot", 1.5F, 1F);
			}
			
			power = Library.chargeItemsFromTE(slots, 0, power, maxPower);

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
		
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
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		tank.setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		tank.setTankType(type);
	}

	@Override
	public int getMaxFillForReceive(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getMaxFill() : 0;
	}

	@Override
	public int getFluidFill(FluidType type) {
		return type.name().equals(this.tank.getTankType().name()) ? tank.getFill() : 0;
	}

	@Override
	public void setFillForTransfer(int i, FluidType type) {
		if(type.name().equals(tank.getTankType().name()))
			tank.setFill(i);
	}
}
