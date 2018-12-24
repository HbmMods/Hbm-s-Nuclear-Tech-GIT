package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemFuelRod;
import com.hbm.lib.Library;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.RadiationSavedData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
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

public class TileEntityMachineReactorLarge extends TileEntity
	implements ISidedInventory, IFluidContainer, IFluidAcceptor, IFluidSource {

	private ItemStack slots[];

	public int hullHeat;
	public final int maxHullHeat = 100000;
	public int coreHeat;
	public final int maxCoreHeat = 50000;
	public int rods;
	public final int rodsMax = 100;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	public FluidTank[] tanks;
	public ReactorFuelType type;
	public int fuel;
	public int maxFuel = 240 * fuelMult;
	public int waste;
	public int maxWaste = 240 * fuelMult;

	public static int fuelMult = 1000;
	public static int cycleDuration = 24000;
	private static int fuelBase = 240 * fuelMult;
	private static int waterBase = 128 * 1000;
	private static int coolantBase = 64 * 1000;
	private static int steamBase = 32 * 1000;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 16 };
	private static final int[] slots_side = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16 };

	private String customName;

	public TileEntityMachineReactorLarge() {
		slots = new ItemStack[8];
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(FluidType.WATER, 128000, 0);
		tanks[1] = new FluidTank(FluidType.COOLANT, 64000, 1);
		tanks[2] = new FluidTank(FluidType.STEAM, 32000, 2);
		type = ReactorFuelType.URANIUM;
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
		return this.hasCustomInventoryName() ? this.customName : "container.reactorLarge";
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
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
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

		coreHeat = nbt.getInteger("heat");
		hullHeat = nbt.getInteger("hullHeat");
		rods = nbt.getInteger("rods");
		slots = new ItemStack[getSizeInventory()];
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "coolant");
		type = ReactorFuelType.getEnum(nbt.getInteger("type"));

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
		nbt.setInteger("heat", coreHeat);
		nbt.setInteger("hullHeat", hullHeat);
		nbt.setInteger("rods", rods);
		NBTTagList list = new NBTTagList();
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "coolant");
		nbt.setInteger("type", type.getID());

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
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	
	}

	public int getCoreHeatScaled(int i) {
		return (coreHeat * i) / maxCoreHeat;
	}

	public int getHullHeatScaled(int i) {
		return (hullHeat * i) / maxHullHeat;
	}

	public int getFuelScaled(int i) {
		return (fuel * i) / maxFuel;
	}

	public int getWasteScaled(int i) {
		return (waste * i) / maxWaste;
	}

	public int getSteamScaled(int i) {
		return (tanks[2].getFill() * i) / tanks[2].getMaxFill();
	}

	public boolean hasCoreHeat() {
		return coreHeat > 0;
	}

	public boolean hasHullHeat() {
		return hullHeat > 0;
	}
	
	public boolean checkBody() {
		
		return worldObj.getBlock(xCoord + 1, yCoord, zCoord + 1) == ModBlocks.reactor_element &&
				worldObj.getBlock(xCoord - 1, yCoord, zCoord + 1) == ModBlocks.reactor_element &&
				worldObj.getBlock(xCoord - 1, yCoord, zCoord - 1) == ModBlocks.reactor_element &&
				worldObj.getBlock(xCoord + 1, yCoord, zCoord - 1) == ModBlocks.reactor_element &&
				worldObj.getBlock(xCoord + 1, yCoord, zCoord) == ModBlocks.reactor_control &&
				worldObj.getBlock(xCoord - 1, yCoord, zCoord) == ModBlocks.reactor_control &&
				worldObj.getBlock(xCoord, yCoord, zCoord + 1) == ModBlocks.reactor_control &&
				worldObj.getBlock(xCoord, yCoord, zCoord - 1) == ModBlocks.reactor_control;
	}
	
	public boolean checkSegment(int offset) {
		
		return worldObj.getBlock(xCoord + 1, yCoord + offset, zCoord + 1) == ModBlocks.reactor_element &&
				worldObj.getBlock(xCoord - 1, yCoord + offset, zCoord + 1) == ModBlocks.reactor_element &&
				worldObj.getBlock(xCoord - 1, yCoord + offset, zCoord - 1) == ModBlocks.reactor_element &&
				worldObj.getBlock(xCoord + 1, yCoord + offset, zCoord - 1) == ModBlocks.reactor_element &&
				worldObj.getBlock(xCoord + 1, yCoord + offset, zCoord) == ModBlocks.reactor_control &&
				worldObj.getBlock(xCoord - 1, yCoord + offset, zCoord) == ModBlocks.reactor_control &&
				worldObj.getBlock(xCoord, yCoord + offset, zCoord + 1) == ModBlocks.reactor_control &&
				worldObj.getBlock(xCoord, yCoord + offset, zCoord - 1) == ModBlocks.reactor_control &&
				worldObj.getBlock(xCoord, yCoord + offset, zCoord) == ModBlocks.reactor_conductor;
	}
	
	int height;
	int depth;
	public int size;
	
	private void caluclateSize() {
		
		height = 0;
		depth = 0;
		
		for(int i = 0; i < 7; i++) {
			
			if(checkSegment(i + 1))
				height++;
			else
				break;
		}
		
		for(int i = 0; i < 7; i++) {
			
			if(checkSegment(-i - 1))
				depth++;
			else
				break;
		}
		
		size = height + depth + 1;
	}
	
	private int getSize() {
		return size;
	}
	
	private void generate() {
		
		int consumption = (maxFuel / cycleDuration) * rods / 100;
		
		if(consumption > fuel)
			consumption = fuel;
		
		if(consumption + waste > maxWaste)
			consumption = maxWaste - waste;
		
		fuel -= consumption;
		waste += consumption;
		
		int heat = (consumption / size) * type.heat / fuelMult;
		
		this.coreHeat += heat;
		
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote && checkBody()) {

			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19)
				fillFluidInit(tanks[2].getTankType());
			
			caluclateSize();

			tanks[0].changeTankSize(waterBase * getSize());
			tanks[1].changeTankSize(coolantBase * getSize());
			tanks[2].changeTankSize(steamBase * getSize());
		}
		
		maxWaste = maxFuel = fuelBase * getSize();
			
		if(!worldObj.isRemote) {
			
			if(waste > maxWaste)
				waste = maxWaste;
			
			if(fuel > maxFuel)
				fuel = maxFuel;
			
			tanks[0].loadTank(0, 1, slots);
			tanks[1].loadTank(2, 3, slots);
			
			//Change fuel type if empty
			if(fuel == 0) {
				
				if(slots[4] != null && !getFuelType(slots[4].getItem()).toString().equals(ReactorFuelType.UNKNOWN.toString())) {
					
					this.type = getFuelType(slots[4].getItem());
					
				}
			}
			
			//Load fuel
			if(slots[4] != null && getFuelContent(slots[4].getItem(), type) > 0) {
				
				int cont = getFuelContent(slots[4].getItem(), type) * fuelMult;
				
				System.out.println(type.toString());
				
				if(fuel + cont <= maxFuel) {
					
					if(!slots[4].getItem().hasContainerItem()) {
						
						slots[4].stackSize--;
						fuel += cont;
						
					} else if(slots[5] == null) {
						
						slots[5] = new ItemStack(slots[4].getItem().getContainerItem());
						slots[4].stackSize--;
						fuel += cont;
						
					} else if(slots[4].getItem().getContainerItem() == slots[5].getItem() && slots[5].stackSize < slots[5].getMaxStackSize()) {
						
						slots[4].stackSize--;
						slots[5].stackSize++;
						fuel += cont;
						
					}
					
					if(slots[4].stackSize == 0)
						slots[4] = null;
				}
			}
			
			//Unload waste
			if(slots[6] != null && getWasteAbsorbed(slots[6].getItem(), type) > 0) {
				
				int absorbed = getWasteAbsorbed(slots[6].getItem(), type) * fuelMult;
				
				if(absorbed <= waste) {
					
					if(slots[7] == null) {

						waste -= absorbed;
						slots[7] = new ItemStack(getWaste(slots[6].getItem(), type));
						slots[6].stackSize--;
						
					} else if(slots[7] != null && slots[7].getItem() == getWaste(slots[6].getItem(), type) && slots[7].stackSize < slots[7].getMaxStackSize()) {

						waste -= absorbed;
						slots[7].stackSize++;
						slots[6].stackSize--;
					}
					
					if(slots[6].stackSize == 0)
						slots[6] = null;
				}
				
			}
			
			if(rods > 0)
				generate();

			if (this.coreHeat > 0 && this.tanks[1].getFill() > 0 && this.hullHeat < this.maxHullHeat) {
				this.hullHeat += this.coreHeat * 0.175;
				this.coreHeat -= this.coreHeat * 0.1;

				this.tanks[1].setFill(this.tanks[1].getFill() - 10);

				if (this.tanks[1].getFill() < 0)
					this.tanks[1].setFill(0);
			}

			if (this.hullHeat > maxHullHeat) {
				this.hullHeat = maxHullHeat;
			}

			if (this.hullHeat > 0 && this.tanks[0].getFill() > 0) {
				generateSteam();
				this.hullHeat -= this.hullHeat * 0.085;
			}

			if (this.coreHeat > maxCoreHeat) {
				this.explode();
			}

			if (rods > 0 && coreHeat > 0 /*rad block*/) {

				float rad = (float)coreHeat / (float)maxCoreHeat * 50F;
				RadiationSavedData data = RadiationSavedData.getData(worldObj);
				data.incrementRad(worldObj, xCoord, zCoord, rad, rad * 4);
			}

			for (int i = 0; i < 3; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord);

			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, rods, 0));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, coreHeat, 1));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, hullHeat, 2));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, size, 3));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, fuel, 4));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, waste, 5));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, type.getID(), 6));
		}
	}
	
	@SuppressWarnings("incomplete-switch")
	private void generateSteam() {

		//function of SHS produced per tick
		//maxes out at heat% * tank capacity / 20
		
		double statSteMaFiFiLe = 8000;
		
		double steam = (((double)hullHeat / (double)maxHullHeat) * (/*(double)tanks[2].getMaxFill()*/statSteMaFiFiLe / 50D)) * size;
		
		double water = steam;
		
		switch(tanks[2].getTankType()) {
		case STEAM:
			water /= 100D;
			break;
		case HOTSTEAM:
			water /= 10;
			break;
		case SUPERHOTSTEAM:
			break;
		}
		
		tanks[0].setFill(tanks[0].getFill() - (int)Math.ceil(water));
		tanks[2].setFill(tanks[2].getFill() + (int)Math.floor(steam));
		
		if(tanks[0].getFill() < 0)
			tanks[0].setFill(0);
		
		if(tanks[2].getFill() > tanks[2].getMaxFill())
			tanks[2].setFill(tanks[2].getMaxFill());
		
	}

	private void explode() {
		for (int i = 0; i < slots.length; i++) {
			this.slots[i] = null;
		}

		worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 7.5F, true);
		ExplosionNukeGeneric.waste(worldObj, this.xCoord, this.yCoord, this.zCoord, 35);
		worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.toxic_block);
		
		for(int i = yCoord - depth; i <= yCoord + height; i++) {

			if(worldObj.rand.nextInt(4) == 0) {
				worldObj.setBlock(this.xCoord + 1, i, this.zCoord + 1, ModBlocks.toxic_block);
			}
			if(worldObj.rand.nextInt(4) == 0) {
				worldObj.setBlock(this.xCoord + 1, i, this.zCoord - 1, ModBlocks.toxic_block);
			}
			if(worldObj.rand.nextInt(4) == 0) {
				worldObj.setBlock(this.xCoord - 1, i, this.zCoord - 1, ModBlocks.toxic_block);
			}
			if(worldObj.rand.nextInt(4) == 0) {
				worldObj.setBlock(this.xCoord - 1, i, this.zCoord + 1, ModBlocks.toxic_block);
			}
			
			if(worldObj.rand.nextInt(10) == 0) {
				worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 5.0F, true);
			}
		}

		RadiationSavedData data = RadiationSavedData.getData(worldObj);
		data.incrementRad(worldObj, xCoord, zCoord, 1000F, 2000F);
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord - 3, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 3, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 3, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 3, getTact(), type);
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
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
	public void setFluidFill(int i, FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if (type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
		else if (type.name().equals(tanks[2].getTankType().name()))
			tanks[2].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if (type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if (type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if (type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getFill();
		else
			return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if (index < 3 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if (index < 3 && tanks[index] != null)
			tanks[index].setTankType(type);
	}

	@Override
	public List<FluidTank> getTanks() {
		List<FluidTank> list = new ArrayList();
		list.add(tanks[0]);
		list.add(tanks[1]);
		list.add(tanks[2]);
		
		return list;
	}

	@Override
	public List<IFluidAcceptor> getFluidList(FluidType type) {
		return list;
	}

	@Override
	public void clearFluidList(FluidType type) {
		list.clear();
	}
	
	public enum ReactorFuelType {
		
		URANIUM(250000),
		PLUTONIUM(312500),
		MOX(250000),
		SCHRABIDIUM(20850000),
		UNKNOWN(1);
		
		private ReactorFuelType(int i) {
			heat = i;
		}
		
		//Heat per nugget burned
		private int heat;
		
		public int getHeat() {
			return heat;
		}
		
		public int getID() {
			return Arrays.asList(ReactorFuelType.values()).indexOf(this);
		}
		
		public static ReactorFuelType getEnum(int i) {
			if(i < ReactorFuelType.values().length)
				return ReactorFuelType.values()[i];
			else
				return ReactorFuelType.URANIUM;
		}
	}
	
	static class ReactorFuelEntry {
		
		int value;
		ReactorFuelType type;
		Item item;
		
		public ReactorFuelEntry(int value, ReactorFuelType type, Item item) {
			this.value = value;
			this.type = type;
			this.item = item;
		}
	}
	
	static class ReactorWasteEntry {
		
		int value;
		ReactorFuelType type;
		Item in;
		Item out;
		
		public ReactorWasteEntry(int value, ReactorFuelType type, Item in, Item out) {
			this.value = value;
			this.type = type;
			this.in = in;
			this.out = out;
		}
	}

	static List<ReactorFuelEntry> fuels = new ArrayList();
	static List<ReactorWasteEntry> wastes = new ArrayList();
	
	public static void registerFuelEntry(int nuggets, ReactorFuelType type, Item fuel) {
		
		fuels.add(new ReactorFuelEntry(nuggets, type, fuel));
	}
	
	public static void registerWasteEntry(int nuggets, ReactorFuelType type, Item in, Item out) {
		
		wastes.add(new ReactorWasteEntry(nuggets, type, in, out));
	}
	
	public static int getFuelContent(Item item, ReactorFuelType type) {
		
		for(ReactorFuelEntry ent : fuels) {
			if(ent.item == item && type.toString().equals(ent.type.toString()))
				return ent.value;
		}
			
		return 0;
	}
	
	public static ReactorFuelType getFuelType(Item item) {
		
		for(ReactorFuelEntry ent : fuels) {
			if(ent.item == item)
				return ent.type;
		}
			
		return ReactorFuelType.UNKNOWN;
	}
	
	public static Item getWaste(Item item, ReactorFuelType type) {
		
		for(ReactorWasteEntry ent : wastes) {
			if(ent.in == item && type.toString().equals(ent.type.toString()))
				return ent.out;
		}
			
		return null;
	}
	
	public static int getWasteAbsorbed(Item item, ReactorFuelType type) {
		
		for(ReactorWasteEntry ent : wastes) {
			if(ent.in == item && type.toString().equals(ent.type.toString()))
				return ent.value;
		}
			
		return 0;
	}
}
