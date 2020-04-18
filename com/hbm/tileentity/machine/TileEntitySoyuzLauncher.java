package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.MissileStruct;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntitySoyuzLauncher extends TileEntityMachineBase implements ISidedInventory, IConsumer, IFluidContainer, IFluidAcceptor {

	public long power;
	public static final long maxPower = 1000000;
	public FluidTank[] tanks;
	//0: sat, 1: cargo
	public byte mode;
	public boolean starting;
	public int countdown;
	public static final int maxCount = 200;
	public byte rocketType = -1;
	
	public MissileStruct load;

	private static final int[] access = new int[] { 0 };

	private String customName;

	public TileEntitySoyuzLauncher() {
		super(27);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.KEROSENE, 128000, 0);
		tanks[1] = new FluidTank(FluidType.ACID, 128000, 1);
	}

	@Override
	public String getName() {
		return "container.soyuzLauncher";
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {

			tanks[0].loadTank(4, 5, slots);
			tanks[1].loadTank(6, 7, slots);

			for (int i = 0; i < 2; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			
			power = Library.chargeTEFromItems(slots, 8, power, maxPower);
			
			//TODO: stop countdown if launch conditions are not met
			if(!starting || !canLaunch()) {
				countdown = maxCount;
			} else if(countdown > 0) {
				countdown--;
			} else {
				starting = false;
				//TODO: liftoff!
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setByte("mode", mode);
			data.setBoolean("starting", starting);
			data.setByte("type", this.getType());
			networkPack(data, 50);
		}
		
		if(worldObj.isRemote) {
			if(!starting || !canLaunch()) {
				countdown = maxCount;
			} else if(countdown > 0) {
				countdown--;
			}
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		power = data.getLong("power");
		mode = data.getByte("mode");
		starting = data.getBoolean("starting");
		rocketType = data.getByte("type");
	}
	
	public void startCountdown() {
		
		if(canLaunch())
			starting = true;
	}
	
	public boolean canLaunch() {
		
		if(mode == 0 && slots[2] == null)
			return false;
		
		return hasRocket() && hasFuel() && hasRocket() && hasPower();
	}
	
	public boolean hasFuel() {
		
		return tanks[0].getFill() >= getFuelRequired();
	}
	
	public boolean hasOxy() {

		return tanks[1].getFill() >= getFuelRequired();
	}
	
	public int getFuelRequired() {
		return 128000;
	}
	
	public boolean hasPower() {
		
		return power >= getPowerRequired();
	}
	
	public int getPowerRequired() {
		
		return (int) (maxPower * 0.75);
	}
	
	private byte getType() {
		
		if(!hasRocket())
			return -1;
		
		return (byte) slots[0].getItemDamage();
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public boolean hasRocket() {
		return slots[0] != null && slots[0].getItem() == ModItems.missile_soyuz;
	}
	
	public int designator() {
		
		if(mode == 0)
			return 0;
		if(slots[1] != null && (slots[1].getItem() == ModItems.designator || slots[1].getItem() == ModItems.designator_range || slots[1].getItem() == ModItems.designator_manual) && slots[1].hasTagCompound())
			return 2;
		return 1;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		tanks[0].readFromNBT(nbt, "fuel");
		tanks[1].readFromNBT(nbt, "oxidizer");
		power = nbt.getLong("power");
		mode = nbt.getByte("mode");

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
		
		NBTTagList list = new NBTTagList();

		tanks[0].writeToNBT(nbt, "fuel");
		tanks[1].writeToNBT(nbt, "oxidizer");
		nbt.setLong("power", power);
		nbt.setByte("mode", mode);

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
	public int getMaxFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(fill);
		else if (type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tanks[0]);
		list.add(tanks[1]);
		
		return list;
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else
			return 0;
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
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}
}
