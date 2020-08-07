package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEPumpjackPacket;

import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMachinePumpjack extends TileEntity implements ISidedInventory, IConsumer, IFluidContainer, IFluidSource {

	private ItemStack slots[];

	public long power;
	public int warning;
	public int warning2;
	public static final long maxPower = 100000;
	public int age = 0;
	public int age2 = 0;
	public List<IFluidAcceptor> list1 = new ArrayList();
	public List<IFluidAcceptor> list2 = new ArrayList();
	public FluidTank[] tanks;
	public boolean isProgressing;
	public int rotation;
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2, 0};
	private static final int[] slots_side = new int[] {0};
	Random rand = new Random();
	
	private String customName;
	
	public TileEntityMachinePumpjack() {
		slots = new ItemStack[6];
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.OIL, 128000, 0);
		tanks[1] = new FluidTank(FluidType.GAS, 128000, 1);
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
		return this.hasCustomInventoryName() ? this.customName : "container.pumpjack";
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
		this.age = nbt.getInteger("age");
		this.rotation = nbt.getInteger("rotation");

		this.tanks[0].readFromNBT(nbt, "oil");
		this.tanks[1].readFromNBT(nbt, "gas");
		
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
		nbt.setInteger("age", age);
		nbt.setInteger("rotation", rotation);

		this.tanks[0].writeToNBT(nbt, "oil");
		this.tanks[1].writeToNBT(nbt, "gas");
		
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
		
		int timer = 20;
		
		age++;
		age2++;
		if(age >= timer)
			age -= timer;
		if(age2 >= 20)
			age2 -= 20;
		if(age2 == 9 || age2 == 19) {
			fillFluidInit(tanks[0].getTankType());
			fillFluidInit(tanks[1].getTankType());
		}
		
		if(!worldObj.isRemote) {
			this.tanks[0].unloadTank(1, 2, slots);
			this.tanks[1].unloadTank(3, 4, slots);
			
			for(int i = 0; i < 2; i++) {
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);
			}

			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			if(power >= 200) {
				
				//operation start
				
				if(age == timer - 1) {
					warning = 0;
					
					//warning 0, green: derrick is operational
					//warning 1, red: derrick is full, has no power or the drill is jammed
					//warning 2, yellow: drill has reached max depth
					
					for(int i = this.yCoord - 1; i > this.yCoord - 1 - 100; i--) {
						
						if(i <= 5) {
							//Code 2: The drilling ended
							warning = 2;
							break;
						}
						
						Block b = worldObj.getBlock(this.xCoord, i, this.zCoord);
						if(b == ModBlocks.oil_pipe)
							continue;
						
						if(b.isReplaceable(worldObj, xCoord, i, zCoord) || b.getExplosionResistance(null) < 100) {
							worldObj.setBlock(xCoord, i, zCoord, ModBlocks.oil_pipe);
						
							//Code 2: The drilling ended
							if(i == this.yCoord - 100)
								warning = 2;
							break;
							
						} else if((b == ModBlocks.ore_oil || b == ModBlocks.ore_oil_empty) && this.tanks[0].getFill() < this.tanks[0].getMaxFill() && this.tanks[1].getFill() < this.tanks[1].getMaxFill()) {
							if(succ(this.xCoord, i, this.zCoord)) {
								
								this.tanks[0].setFill(this.tanks[0].getFill() + 650);
								if(this.tanks[0].getFill() > this.tanks[0].getMaxFill())
									this.tanks[0].setFill(tanks[0].getMaxFill());
								

								this.tanks[1].setFill(this.tanks[1].getFill() + (100 + rand.nextInt(301)));
								if(this.tanks[1].getFill() > this.tanks[1].getMaxFill())
									this.tanks[1].setFill(tanks[1].getMaxFill());
								
								break;
							} else {
								worldObj.setBlock(xCoord, i, zCoord, ModBlocks.oil_pipe);
								break;
							}
							
						} else {
							//Code 1: Drill jammed
							warning = 1;
							break;
						}
					}
				}
				
				//operation end
				
				power -= 200;
			} else {
				warning = 1;
			}

			warning2 = 0;
			if(tanks[1].getFill() > 0) {
				if(slots[5] != null && (slots[5].getItem() == ModItems.fuse || slots[5].getItem() == ModItems.screwdriver)) {
					warning2 = 2;
					tanks[1].setFill(tanks[1].getFill() - 50);
					if(tanks[1].getFill() <= 0)
						tanks[1].setFill(0);
		    		worldObj.spawnEntityInWorld(new EntityGasFX(worldObj, this.xCoord + 0.5F, this.yCoord + 0.5F, this.zCoord + 0.5F, 0.0, 0.0, 0.0));
				} else {
					warning2 = 1;
				}
			}
			
			isProgressing = warning == 0;
			rotation += (warning == 0 ? 5 : 0);
			rotation = rotation % 360;

			PacketDispatcher.wrapper.sendToAllAround(new TEPumpjackPacket(xCoord, yCoord, zCoord, rotation, isProgressing), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
		
	}
	
	public boolean succ(int x, int y, int z) {
		
		list.clear();
		
		succ1(x, y, z);
		succ2(x, y, z);
		
		if(!list.isEmpty()) {
			
			int i = rand.nextInt(list.size());
			int a = list.get(i)[0];
			int b = list.get(i)[1];
			int c = list.get(i)[2];
			
			if(worldObj.getBlock(a, b, c) == ModBlocks.ore_oil) {
				
				worldObj.setBlock(a, b, c, ModBlocks.ore_oil_empty);
				return true;
			}
		}
		
		return false;
	}
	
	public void succInit1(int x, int y, int z) {
		succ1(x + 1, y, z);
		succ1(x - 1, y, z);
		succ1(x, y + 1, z);
		succ1(x, y - 1, z);
		succ1(x, y, z + 1);
		succ1(x, y, z - 1);
	}
	
	public void succInit2(int x, int y, int z) {
		succ2(x + 1, y, z);
		succ2(x - 1, y, z);
		succ2(x, y + 1, z);
		succ2(x, y - 1, z);
		succ2(x, y, z + 1);
		succ2(x, y, z - 1);
	}
	
	List<int[]> list = new ArrayList<int[]>();
	
	public void succ1(int x, int y, int z) {
		if(worldObj.getBlock(x, y, z) == ModBlocks.ore_oil_empty && 
				worldObj.getBlockMetadata(x, y, z) == 0) {
			worldObj.setBlockMetadataWithNotify(x, y, z, 1, 2);
			succInit1(x, y, z);
		}
	}
	
	public void succ2(int x, int y, int z) {
		if(worldObj.getBlock(x, y, z) == ModBlocks.ore_oil_empty && 
				worldObj.getBlockMetadata(x, y, z) == 1) {
			worldObj.setBlockMetadataWithNotify(x, y, z, 0, 2);
			succInit2(x, y, z);
		} else if(worldObj.getBlock(x, y, z) == ModBlocks.ore_oil) {
			list.add(new int[] { x, y, z });
		}
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
	public boolean getTact() {
		if (age2 >= 0 && age2 < 10) {
			return true;
		}

		return false;
	}

	@Override
	public void fillFluidInit(FluidType type) {
		
		int i = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

		if(i == 5) {
			fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 2, getTact(), type);
			fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 2, getTact(), type);
			fillFluid(this.xCoord - 3, this.yCoord, this.zCoord + 2, getTact(), type);
			fillFluid(this.xCoord - 3, this.yCoord, this.zCoord - 2, getTact(), type);
		}
		if(i == 3) {
			fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 2, getTact(), type);
			fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 2, getTact(), type);
			fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 3, getTact(), type);
			fillFluid(this.xCoord - 2, this.yCoord, this.zCoord - 3, getTact(), type);
		}
		if(i == 4) {
			fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 2, getTact(), type);
			fillFluid(this.xCoord + 2, this.yCoord, this.zCoord - 2, getTact(), type);
			fillFluid(this.xCoord + 3, this.yCoord, this.zCoord + 2, getTact(), type);
			fillFluid(this.xCoord + 3, this.yCoord, this.zCoord - 2, getTact(), type);
		}
		if(i == 2) {
			fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 2, getTact(), type);
			fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 2, getTact(), type);
			fillFluid(this.xCoord + 2, this.yCoord, this.zCoord + 3, getTact(), type);
			fillFluid(this.xCoord - 2, this.yCoord, this.zCoord + 3, getTact(), type);
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
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
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return this.list1;
		if(type.name().equals(tanks[1].getTankType().name()))
			return this.list2;
		return new ArrayList<IFluidAcceptor>();
	}

	@Override
	public void clearFluidList(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			list1.clear();
		if(type.name().equals(tanks[1].getTankType().name()))
			list2.clear();
	}

	@Override
	public void setFillstate(int fill, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if(index < 2 && tanks[index] != null)
			tanks[index].setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tanks[0]);
		list.add(tanks[1]);
		
		return list;
	}
}
