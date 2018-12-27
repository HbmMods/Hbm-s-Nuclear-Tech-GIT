package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemKeyPin;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEControlPacket;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileEntityReactorControl extends TileEntity implements ISidedInventory {

	private ItemStack slots[];

	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {0};
	private static final int[] slots_side = new int[] {0};
	
	private String customName;
	
	public TileEntityReactorControl() {
		slots = new ItemStack[1];
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
		return this.hasCustomInventoryName() ? this.customName : "container.reactorControl";
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
	
	@Override
	public void openInventory() {}
	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
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
		
		redstoned = nbt.getBoolean("red");
		lastRods = nbt.getInteger("lastRods");
		
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
		NBTTagList list = new NBTTagList();
		
		nbt.setBoolean("red", redstoned);
		nbt.setInteger("lastRods", lastRods);
		
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
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return false;
	}
	
	public int linkX;
	public int linkY;
	public int linkZ;
	
	public int hullHeat;
	public int coreHeat;
	public int fuel;
	public int water;
	public int cool;
	public int steam;
	public int maxWater;
	public int maxCool;
	public int maxSteam;
	public int compression;
	public int rods;
	public int maxRods;
	public boolean isOn;
	public boolean auto;
	public boolean isLinked;
	public boolean redstoned;
	private int lastRods = 100;
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote)
		{
        	if(slots[0] != null && slots[0].getItem() == ModItems.reactor_sensor && 
        			slots[0].stackTagCompound != null)
        	{
        		int xCoord = slots[0].stackTagCompound.getInteger("x");
        		int yCoord = slots[0].stackTagCompound.getInteger("y");
        		int zCoord = slots[0].stackTagCompound.getInteger("z");
        		
        		Block b = worldObj.getBlock(xCoord, yCoord, zCoord);
        		
        		if(b == ModBlocks.machine_reactor_small || b == ModBlocks.reactor_computer) {
        			linkX = xCoord;
        			linkY = yCoord;
        			linkZ = zCoord;
        		} else if(b == ModBlocks.dummy_block_reactor_small || b == ModBlocks.dummy_port_reactor_small) {
        			linkX = ((TileEntityDummy)worldObj.getTileEntity(xCoord, yCoord, zCoord)).targetX;
        			linkY = ((TileEntityDummy)worldObj.getTileEntity(xCoord, yCoord, zCoord)).targetY;
        			linkZ = ((TileEntityDummy)worldObj.getTileEntity(xCoord, yCoord, zCoord)).targetZ;
        		} else {
        			linkY = -1;
            	}
        	} else {
    			linkY = -1;
        	}
        	
        	if(linkY >= 0 && worldObj.getTileEntity(linkX, linkY, linkZ) instanceof TileEntityMachineReactorSmall) {
        		TileEntityMachineReactorSmall reactor = (TileEntityMachineReactorSmall)worldObj.getTileEntity(linkX, linkY, linkZ);
        		
        		hullHeat = reactor.hullHeat;
        		coreHeat = reactor.coreHeat;
        		fuel = reactor.getFuelPercent();
        		water = reactor.tanks[0].getFill();
        		cool = reactor.tanks[1].getFill();
        		steam = reactor.tanks[2].getFill();
        		maxWater = reactor.tanks[0].getMaxFill();
        		maxCool = reactor.tanks[1].getMaxFill();
        		maxSteam = reactor.tanks[2].getMaxFill();
        		rods = reactor.rods;
        		maxRods = reactor.rodsMax;
        		isOn = !reactor.retracting;
        		isLinked = true;
        		
        		switch(reactor.tanks[2].getTankType()) {
        		case HOTSTEAM: 
            		compression = 1; break;
        		case SUPERHOTSTEAM: 
            		compression = 2; break;
            	default:
            		compression = 0; break;
        		}
        		
        		if(!redstoned) {
        			if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
        				redstoned = true;
        				reactor.retracting = !reactor.retracting;
        			}
        		} else {
        			if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
        				redstoned = false;
        			}
        		}
        		
        		if(auto && (water < 100 || cool < 100 || coreHeat > (50000 * 0.95)) && fuel > 0) {
        			reactor.retracting = true;
        		}
        		
        	} else if(linkY >= 0 && worldObj.getTileEntity(linkX, linkY, linkZ) instanceof TileEntityMachineReactorLarge && ((TileEntityMachineReactorLarge)worldObj.getTileEntity(linkX, linkY, linkZ)).checkBody()) {
        		TileEntityMachineReactorLarge reactor = (TileEntityMachineReactorLarge)worldObj.getTileEntity(linkX, linkY, linkZ);
        		
        		hullHeat = reactor.hullHeat;
        		coreHeat = reactor.coreHeat;
        		fuel = reactor.fuel * 100 / reactor.maxFuel;
        		water = reactor.tanks[0].getFill();
        		cool = reactor.tanks[1].getFill();
        		steam = reactor.tanks[2].getFill();
        		maxWater = reactor.tanks[0].getMaxFill();
        		maxCool = reactor.tanks[1].getMaxFill();
        		maxSteam = reactor.tanks[2].getMaxFill();
        		rods = reactor.rods;
        		maxRods = reactor.rodsMax;
        		isOn = reactor.rods > 0;
        		isLinked = true;
        		
        		switch(reactor.tanks[2].getTankType()) {
        		case HOTSTEAM: 
            		compression = 1; break;
        		case SUPERHOTSTEAM: 
            		compression = 2; break;
            	default:
            		compression = 0; break;
        		}
        		
        		if(rods != 0)
        			lastRods = rods;
        		
        		if(!redstoned) {
        			if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
        				redstoned = true;
        				
        				if(rods == 0)
        					rods = lastRods;
        				else
        					rods = 0;
        			}
        		} else {
        			if(!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
        				redstoned = false;
        			}
        		}
        		
        		if(auto && (water < 100 || cool < 100 || coreHeat > (50000 * 0.95)) && fuel > 0) {
        			reactor.rods = 0;
        		}
        		
        	} else {
        		hullHeat = 0;
        		coreHeat = 0;
        		fuel = 0;
        		water = 0;
        		cool = 0;
        		steam = 0;
        		maxWater = 0;
        		maxCool = 0;
        		maxSteam = 0;
        		rods = 0;
        		maxRods = 0;
        		isOn = false;
        		compression = 0;
        		isLinked = false;
        	}

        	if(worldObj.getBlock(xCoord, yCoord, zCoord + 1) instanceof BlockRedstoneComparator) {
        		worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord + 1, worldObj.getBlock(xCoord, yCoord, zCoord + 1), 1);
        	}
        	if(worldObj.getBlock(xCoord, yCoord, zCoord - 1) instanceof BlockRedstoneComparator) {
        		worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord - 1, worldObj.getBlock(xCoord, yCoord, zCoord - 1), 1);
        	}
        	if(worldObj.getBlock(xCoord + 1, yCoord, zCoord) instanceof BlockRedstoneComparator) {
        		worldObj.scheduleBlockUpdate(xCoord + 1, yCoord, zCoord, worldObj.getBlock(xCoord + 1, yCoord, zCoord), 1);
        	}
        	if(worldObj.getBlock(xCoord - 1, yCoord, zCoord) instanceof BlockRedstoneComparator) {
        		worldObj.scheduleBlockUpdate(xCoord - 1, yCoord, zCoord, worldObj.getBlock(xCoord - 1, yCoord, zCoord), 1);
        	}
        	
        	PacketDispatcher.wrapper.sendToAll(new TEControlPacket(xCoord, yCoord, zCoord, hullHeat, coreHeat, fuel, water, cool, steam, maxWater, maxCool, maxSteam, compression, rods, maxRods, isOn, auto, isLinked));
		}
	}
}
