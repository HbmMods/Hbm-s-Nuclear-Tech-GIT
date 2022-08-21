package com.hbm.tileentity.machine.oil;

import java.util.ArrayList;
import java.util.List;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.FluidTank;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.RefineryRecipes;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Quintet;
import com.hbm.util.fauxpointtwelve.DirPos;

import api.hbm.energy.IEnergyUser;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class TileEntityMachineRefinery extends TileEntityMachineBase implements IEnergyUser, IFluidContainer, IFluidAcceptor, IFluidSource, IControlReceiver, IFluidStandardTransceiver {

	public long power = 0;
	public int sulfur = 0;
	public static final int maxSulfur = 100;
	public static final long maxPower = 1000;
	public FluidTank[] tanks;
	public List<IFluidAcceptor> list1 = new ArrayList();
	public List<IFluidAcceptor> list2 = new ArrayList();
	public List<IFluidAcceptor> list3 = new ArrayList();
	public List<IFluidAcceptor> list4 = new ArrayList();

	private static final int[] slot_access = new int[] {11};
	
	public TileEntityMachineRefinery() {
		super(12);
		tanks = new FluidTank[5];
		tanks[0] = new FluidTank(Fluids.HOTOIL, 64000, 0);
		tanks[1] = new FluidTank(Fluids.HEAVYOIL, 16000, 1);
		tanks[2] = new FluidTank(Fluids.NAPHTHA, 16000, 2);
		tanks[3] = new FluidTank(Fluids.LIGHTOIL, 16000, 3);
		tanks[4] = new FluidTank(Fluids.PETROLEUM, 16000, 4);
	}

	@Override
	public String getName() {
		return "container.machineRefinery";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		power = nbt.getLong("power");
		tanks[0].readFromNBT(nbt, "input");
		tanks[1].readFromNBT(nbt, "heavy");
		tanks[2].readFromNBT(nbt, "naphtha");
		tanks[3].readFromNBT(nbt, "light");
		tanks[4].readFromNBT(nbt, "petroleum");
		sulfur = nbt.getInteger("sulfur");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("power", power);
		tanks[0].writeToNBT(nbt, "input");
		tanks[1].writeToNBT(nbt, "heavy");
		tanks[2].writeToNBT(nbt, "naphtha");
		tanks[3].writeToNBT(nbt, "light");
		tanks[4].writeToNBT(nbt, "petroleum");
		nbt.setInteger("sulfur", sulfur);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slot_access;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 11;
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			this.updateConnections();
			
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
				fillFluidInit(tanks[1].getTankType());
				fillFluidInit(tanks[2].getTankType());
				fillFluidInit(tanks[3].getTankType());
				fillFluidInit(tanks[4].getTankType());
			}
			
			tanks[0].loadTank(1, 2, slots);
			
			refine();

			tanks[1].unloadTank(3, 4, slots);
			tanks[2].unloadTank(5, 6, slots);
			tanks[3].unloadTank(7, 8, slots);
			tanks[4].unloadTank(9, 10, slots);
			
			for(int i = 0; i < 5; i++) {
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", this.power);
			this.networkPack(data, 50);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
	}
	
	private void refine() {
		Quintet<FluidStack, FluidStack, FluidStack, FluidStack, ItemStack> refinery = RefineryRecipes.getRefinery(tanks[0].getTankType());
		
		if(refinery == null) //usually not possible
			return;
		
		FluidStack[] stacks = new FluidStack[] {refinery.getV(), refinery.getW(), refinery.getX(), refinery.getY()};
		
		for(int i = 0; i < stacks.length; i++)
			tanks[i + 1].setTankType(stacks[i].type);
		
		if(power < 5 || tanks[0].getFill() < 100)
			return;

		for(int i = 0; i < stacks.length; i++) {
			if(tanks[i + 1].getFill() + stacks[i].fill > tanks[i + 1].getMaxFill()) {
				return;
			}
		}
		
		tanks[0].setFill(tanks[0].getFill() - 100);

		for(int i = 0; i < stacks.length; i++)
			tanks[i + 1].setFill(tanks[i + 1].getFill() + stacks[i].fill);
		
		this.sulfur++;
		
		if(this.sulfur >= maxSulfur) {
			this.sulfur -= maxSulfur;
			
			ItemStack out = refinery.getZ();
			
			if(out != null) {
				
				if(slots[11] == null) {
					slots[11] = out.copy();
				} else {
					
					if(out.getItem() == slots[11].getItem() && out.getItemDamage() == slots[11].getItemDamage() && slots[11].stackSize + out.stackSize <= slots[11].getMaxStackSize()) {
						slots[11].stackSize += out.stackSize;
					}
				}
			}
			
			this.markDirty();
		}
		
		this.power -= 5;
	}
	
	private void updateConnections() {
		for(DirPos pos : getConPos()) {
			this.trySubscribe(worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
			this.trySubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ(), pos.getDir());
		}
	}
	
	public DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(xCoord + 2, yCoord, zCoord + 1, Library.POS_X),
				new DirPos(xCoord + 2, yCoord, zCoord - 1, Library.POS_X),
				new DirPos(xCoord - 2, yCoord, zCoord + 1, Library.NEG_X),
				new DirPos(xCoord - 2, yCoord, zCoord - 1, Library.NEG_X),
				new DirPos(xCoord + 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord - 1, yCoord, zCoord + 2, Library.POS_Z),
				new DirPos(xCoord + 1, yCoord, zCoord - 2, Library.NEG_Z),
				new DirPos(xCoord - 1, yCoord, zCoord - 2, Library.NEG_Z)
		};
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
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
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord + 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord - 2, getTact(), type);
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord + 2, getTact(), type);
		
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 1, getTact(), type);
		fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 1, getTact(), type);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public boolean getTact() {
		return worldObj.getTotalWorldTime() % 20 < 10;
	}

	@Override
	public int getFluidFill(FluidType type) {
		
		for(int i = 0; i < 5; i++) {
			if(type == tanks[i].getTankType()) {
				return tanks[i].getFill();
			}
		}
		
		return 0;
	}

	@Override
	public void setFluidFill(int fill, FluidType type) {
		
		for(int i = 0; i < 5; i++) {
			if(type == tanks[i].getTankType()) {
				tanks[i].setFill(fill);
			}
		}
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type == tanks[1].getTankType()) return list1;
		if(type == tanks[2].getTankType()) return list2;
		if(type == tanks[3].getTankType()) return list3;
		if(type == tanks[4].getTankType()) return list4;
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type == tanks[1].getTankType()) list1.clear();
		if(type == tanks[2].getTankType()) list2.clear();
		if(type == tanks[3].getTankType()) list3.clear();
		if(type == tanks[4].getTankType()) list4.clear();
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type == tanks[0].getTankType())
			return tanks[0].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFillForSync(int fill, int index) {
		if(index < 5 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setTypeForSync(FluidType type, int index) {
		if(index < 5 && tanks[index] != null)
			tanks[index].setTankType(type);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 25;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("toggle")) {
			
			for(DirPos pos : getConPos()) {
				this.tryUnsubscribe(tanks[0].getTankType(), worldObj, pos.getX(), pos.getY(), pos.getZ());
			}
			
			if(tanks[0].getTankType() == Fluids.HOTOIL) {
				tanks[0].setTankType(Fluids.HOTCRACKOIL);
			} else {
				tanks[0].setTankType(Fluids.HOTOIL);
			}
		}
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return new FluidTank[] { tanks[1], tanks[2], tanks[3], tanks[4] };
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] { tanks[0] };
	}
}
