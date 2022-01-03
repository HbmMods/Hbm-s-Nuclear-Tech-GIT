package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.ReactorResearch;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.items.ModItems;
import com.hbm.packet.NBTControlPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlAuto.RBMKFunction;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlManual.RBMKColor;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneComparator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class TileEntityReactorControl extends TileEntityMachineBase implements IControlReceiver {

	private static final int[] slots_io = new int[] {0};
	
	public TileEntityReactorControl() {
		super(1);
	}
	
	@Override
	public String getName() {
		return "container.reactorControl";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("items", 10);

		isLinked = nbt.getBoolean("isLinked");
		levelLower = nbt.getDouble("levelLower");
		levelUpper = nbt.getDouble("levelUpper");
		heatLower = nbt.getDouble("heatLower");
		heatUpper = nbt.getDouble("heatUpper");
		function = RodFunction.values()[nbt.getInteger("function")];
		
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
		
		nbt.setBoolean("isLinked", isLinked);
		nbt.setDouble("levelLower", levelLower);
		nbt.setDouble("levelUpper", levelUpper);
		nbt.setDouble("heatLower", heatLower);
		nbt.setDouble("heatUpper", heatUpper);
		nbt.setInteger("function", function.ordinal());

		
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
	
	public TileEntityReactorResearch reactor;
	
	public boolean isLinked;
	public int heat;
	
	public double levelLower;
	public double levelUpper;
	public double heatLower;
	public double heatUpper;
	public RodFunction function = RodFunction.LINEAR;
	
	//TODO: Remove all large reactor functionality for this
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			isLinked = establishLink();
			
			if(isLinked) { 
				this.heat = reactor.heat;
				
				double fauxLevel = 0;

				double lowerBound = Math.min(this.heatLower, this.heatUpper);
				double upperBound = Math.max(this.heatLower, this.heatUpper);
				
				if(this.heat < lowerBound) {
					fauxLevel = this.levelLower;
					
				} else if(this.heat > upperBound) {
					fauxLevel = this.levelUpper;
					
				} else {
		
					switch(this.function) {
					case LINEAR:
						fauxLevel = (this.heat - this.heatLower) * ((this.levelUpper - this.levelLower) / (this.heatUpper - this.heatLower)) + this.levelLower;
						break;
					case QUAD:
						fauxLevel = Math.pow((this.heat - this.heatLower) / (this.heatUpper - this.heatLower), 2) * (this.levelUpper - this.levelLower) + this.levelLower;
						break;
					case LOG:
						fauxLevel = Math.pow((this.heat - this.heatUpper) / (this.heatLower - this.heatUpper), 2) * (this.levelLower - this.levelUpper) + this.levelUpper;
						break;
					default:
						break;
					}
				}
				
				double level = MathHelper.clamp_double((fauxLevel * 0.01D), 0D, 1D);
				
				NBTTagCompound control = new NBTTagCompound();
				control.setDouble("level", level);
				
				PacketDispatcher.wrapper.sendToServer(new NBTControlPacket(control, reactor.xCoord, reactor.yCoord, reactor.zCoord));
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("heat", heat);
			data.setBoolean("isLinked", isLinked);
			data.setDouble("levelLower", levelLower);
			data.setDouble("levelUpper", levelUpper);
			data.setDouble("heatLower", heatLower);
			data.setDouble("heatUpper", heatUpper);
			data.setInteger("function", function.ordinal());
			this.networkPack(data, 150);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		this.heat = data.getInteger("heat");
		isLinked = data.getBoolean("isLinked");
		levelLower = data.getDouble("levelLower");
		levelUpper = data.getDouble("levelUpper");
		heatLower = data.getDouble("heatLower");
		heatUpper = data.getDouble("heatUpper");
		function = RodFunction.values()[data.getInteger("function")];
	}
	
	private boolean establishLink() {
		if(slots[0] != null && slots[0].getItem() == ModItems.reactor_sensor && slots[0].stackTagCompound != null) {
			int xCoord = slots[0].stackTagCompound.getInteger("x");
    		int yCoord = slots[0].stackTagCompound.getInteger("y");
    		int zCoord = slots[0].stackTagCompound.getInteger("z");
    		
    		Block b = worldObj.getBlock(xCoord, yCoord, zCoord);
    		
    		if(b == ModBlocks.reactor_research) {
    			
    			int[] pos = ((ReactorResearch) ModBlocks.reactor_research).findCore(worldObj, xCoord, yCoord, zCoord);
    			
    			if(pos != null) {

					TileEntity tile = worldObj.getTileEntity(pos[0], pos[1], pos[2]);

					if(tile instanceof TileEntityReactorResearch) {
						reactor = (TileEntityReactorResearch) tile;
						
						return true;
					}
				}
    		}
		}
		
		return false;
	}
	
	public int[] getDisplayData() {
		if(reactor != null) {
			int[] data = new int[3];
			data[0] = (int) reactor.level * 100;
			data[1] = reactor.totalFlux;
			data[2] = (int) Math.round((this.heat) * 0.00002 * 980 + 20);
			return data;
		} else {
			return new int[] { 0, 0, 0 };
		}
	}
	
	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("function")) {
			this.function = RodFunction.values()[data.getInteger("function")];
		} else {
			this.levelLower = data.getDouble("levelLower");
			this.levelUpper = data.getDouble("levelUpper");
			this.heatLower = data.getDouble("heatLower");
			this.heatUpper = data.getDouble("heatUpper");
		}
		
		this.markDirty();
	}
	
	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(xCoord - player.posX, yCoord - player.posY, zCoord - player.posZ).lengthVector() < 20;
	}
	
	public enum RodFunction {
		LINEAR,
		QUAD,
		LOG
	}
	
	/*public int hullHeat;
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
	public boolean isLinkd;
	public boolean redstoned;
	private int lastRods = 100;*/
	
	/*@Override
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
        		fuel = reactor.fuel * 100 / Math.max(1, reactor.maxFuel);
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
        	
        	PacketDispatcher.wrapper.sendToAllAround(new TEControlPacket(xCoord, yCoord, zCoord, hullHeat, coreHeat, fuel, water, cool, steam, maxWater, maxCool, maxSteam, compression, rods, maxRods, isOn, auto, isLinked), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 30));
		}
	}*/
}
