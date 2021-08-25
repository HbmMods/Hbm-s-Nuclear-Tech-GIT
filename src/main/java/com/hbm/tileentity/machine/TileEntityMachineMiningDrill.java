package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.UpgradeManager;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEDrillPacket;
import com.hbm.sound.SoundLoopMachine;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.block.IDrillInteraction;
import api.hbm.block.IMiningDrill;
import api.hbm.energy.IBatteryItem;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;

@Spaghetti("if i had a time machine i'd go back to the year 2017 and uppercut myself")
public class TileEntityMachineMiningDrill extends TileEntityMachineBase implements IConsumer, IMiningDrill {

	public long power;
	public int warning;
	public static final long maxPower = 100000;
	int age = 0;
	int timer = 50;
	int radius = 100;
	int consumption = 100;
	int fortune = 0;
	boolean flag = true;
	public float torque;
	public float rotation;
	SoundLoopMachine sound;
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2, 0};
	private static final int[] slots_side = new int[] {0};
	Random rand = new Random();
	
	public TileEntityMachineMiningDrill() {
		super(13);
	}

	@Override
	public String getName() {
		return "container.miningDrill";
	}

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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("powerTime");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("powerTime", power);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_)
    {
        return p_94128_1_ == 0 ? slots_bottom : (p_94128_1_ == 1 ? slots_top : slots_side);
    }
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void updateEntity() {
		
		this.consumption = 100;
		this.timer = 50;
		this.radius = 1;
		this.fortune = 0;
		
		UpgradeManager.eval(slots, 10, 13);
		this.radius += Math.min(UpgradeManager.getLevel(UpgradeType.EFFECT), 3);
		this.consumption += Math.min(UpgradeManager.getLevel(UpgradeType.EFFECT), 3) * 80;
		
		this.timer -= Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3) * 15;
		this.consumption += Math.min(UpgradeManager.getLevel(UpgradeType.SPEED), 3) * 300;
		
		this.consumption -= Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3) * 30;
		this.timer += Math.min(UpgradeManager.getLevel(UpgradeType.POWER), 3) * 5;
		
		this.fortune += Math.min(UpgradeManager.getLevel(UpgradeType.FORTUNE), 3);
		this.timer += Math.min(UpgradeManager.getLevel(UpgradeType.FORTUNE), 3) * 15;
		
		age++;
		if(age >= timer)
			age -= timer;
		
		if(!worldObj.isRemote) {
			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			if(power >= consumption) {
				
				//operation start
				
				if(age == timer - 1) {
					warning = 0;
					
					//warning 0, green: drill is operational
					//warning 1, red: drill is full, has no power or the drill is jammed
					//warning 2, yellow: drill has reached max depth
					
					for(int i = this.yCoord - 1; i > this.yCoord - 1 - 100; i--) {
						
						if(i <= 5) {
							//Code 2: The drilling ended
							warning = 2;
							break;
						}

						Block b = worldObj.getBlock(this.xCoord, i, this.zCoord);
						Block b1 = worldObj.getBlock(this.xCoord, i - 1, this.zCoord);
						int meta = worldObj.getBlockMetadata(this.xCoord, i, this.zCoord);
						int meta1 = worldObj.getBlockMetadata(this.xCoord, i - 1, this.zCoord);
						ItemStack stack = new ItemStack(b.getItemDropped(meta, rand, fortune), b.quantityDropped(meta, fortune, rand), b.damageDropped(meta));
						ItemStack stack1 = new ItemStack(b1.getItemDropped(meta1, rand, fortune), b1.quantityDropped(meta1, fortune, rand), b1.damageDropped(meta1));
						
						if(i == this.yCoord - 1 && worldObj.getBlock(this.xCoord, i, this.zCoord) != ModBlocks.drill_pipe) {
							if(this.isOreo(this.xCoord, i, this.zCoord) && this.hasSpace(stack)) {
								//if(stack != null)
									//this.addItemToInventory(stack);
								worldObj.setBlock(this.xCoord, i, this.zCoord, ModBlocks.drill_pipe);
								break;
							} else {
								//Code 2: Drill jammed
								warning = 1;
								break;
							}
						}
						
						if(b1 == ModBlocks.drill_pipe) {
							continue;
						} else {
							
							flag = i != this.yCoord - 1;
							
							if(!this.drill(this.xCoord, i, this.zCoord, radius)) {
								if(this.isOreo(this.xCoord, i - 1, this.zCoord) && this.hasSpace(stack1)) {
										worldObj.setBlock(this.xCoord, i - 1, this.zCoord, ModBlocks.drill_pipe);
								} else {
									//Code 2: Drill jammed
									warning = 1;
								}
							
							}
							
							break;
						}
					}
				}
				
				//operation end
				
				power -= consumption;
			} else {
				warning = 1;
			}
			
			int meta = worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			TileEntity te = null;
			if(meta == 2) {
				te = worldObj.getTileEntity(xCoord - 2, yCoord, zCoord);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 3) {
				te = worldObj.getTileEntity(xCoord + 2, yCoord, zCoord);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 4) {
				te = worldObj.getTileEntity(xCoord, yCoord, zCoord + 2);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 5) {
				te = worldObj.getTileEntity(xCoord, yCoord, zCoord - 2);
				//worldObj.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			
			if(te != null && te instanceof TileEntityChest) {
				TileEntityChest chest = (TileEntityChest)te;
				
				for(int i = 1; i < 10; i++)
					if(tryFillContainer(chest, i))
						break;
			}
			
			if(te != null && te instanceof TileEntityHopper) {
				TileEntityHopper hopper = (TileEntityHopper)te;
				
				for(int i = 1; i < 10; i++)
					if(tryFillContainer(hopper, i))
						break;
			}
			
			if(te != null && te instanceof TileEntityCrateIron) {
				TileEntityCrateIron hopper = (TileEntityCrateIron)te;
				
				for(int i = 1; i < 10; i++)
					if(tryFillContainer(hopper, i))
						break;
			}
			
			if(te != null && te instanceof TileEntityCrateSteel) {
				TileEntityCrateSteel hopper = (TileEntityCrateSteel)te;
				
				for(int i = 1; i < 10; i++)
					if(tryFillContainer(hopper, i))
						break;
			}
			
			if(warning == 0) {
				torque += 0.1;
				if(torque > (100/timer))
					torque = (100/timer);
			} else {
				torque -= 0.1F;
				if(torque < -(100/timer))
					torque = -(100/timer);
			}
			
			if(torque < 0) {
				torque = 0;
			}
			rotation += torque;
			if(rotation >= 360)
				rotation -= 360;

			PacketDispatcher.wrapper.sendToAllAround(new TEDrillPacket(xCoord, yCoord, zCoord, rotation, torque), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(xCoord, yCoord, zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}
	
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
	
	//Method: isOre
	//"make it oreo!"
	//"ok"
	public boolean isOreo(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x, y, z);
		float hardness = b.getBlockHardness(worldObj, x, y, z);
		
		return hardness < 70 && hardness >= 0;
	}
	
	public boolean isMinableOreo(int x, int y, int z) {
		
		Block b = worldObj.getBlock(x, y, z);
		float hardness = b.getBlockHardness(worldObj, x, y, z);
		
		return hardness < 70 && hardness >= 0;
	}
	
	/**
	 * returns true if there has been a successful mining operation
	 * returns false if no block could be mined and the drill is ready to extend
	 * */
	public boolean drill(int x, int y, int z, int rad) {
		
		if(!flag)
			return false;
		
		for(int ix = x - rad; ix <= x + rad; ix++) {
			for(int iz = z - rad; iz <= z + rad; iz++) {
				
				if(ix != x || iz != z)
					if(tryDrill(ix, y, iz))
						return true;
			}
		}
					
		return false;
	}

	/**
	 * returns true if there has been a successful mining operation
	 * returns false if no block could be mined, as it is either air or unmineable
	 * */
	public boolean tryDrill(int x, int y, int z) {

		if(worldObj.getBlock(x, y, z) == Blocks.air || !isMinableOreo(x, y, z))
			return false;
		if(worldObj.getBlock(x, y, z).getMaterial().isLiquid()) {
			worldObj.func_147480_a(x, y, z, false);
			return false;
		}
		
		Block b = worldObj.getBlock(x, y, z);
		int meta = worldObj.getBlockMetadata(x, y, z);
		
		if(b instanceof IDrillInteraction) {
			IDrillInteraction in = (IDrillInteraction) b;
			
			ItemStack sta = in.extractResource(worldObj, x, y, z, meta, this);

			if(hasSpace(sta)) {
				this.addItemToInventory(sta);
			}
			
			if(!in.canBreak(worldObj, x, y, z, meta, this))
				return true; //true because the block is still there and mining should continue
		}
		
		ItemStack stack = new ItemStack(b.getItemDropped(meta, rand, fortune), b.quantityDropped(meta, fortune, rand), b.damageDropped(meta));

		//yup that worked
		if(stack != null && stack.getItem() == null) {
			worldObj.func_147480_a(x, y, z, false);
			return true;
		}
		
		if(hasSpace(stack)) {
			this.addItemToInventory(stack);
			worldObj.func_147480_a(x, y, z, false);
			return true;
		}
		
		return true;
	}
	
	public boolean hasSpace(ItemStack stack) {

		ItemStack st = stack.copy();
		
		if(st == null)
			return true;
		
		for(int i = 1; i < 10; i++) {
			if(slots[i] == null)
				return true;
		}
		
		st.stackSize = 1;
		
		ItemStack[] fakeArray = slots.clone();
		boolean flag = true;
		for(int i = 0; i < stack.stackSize; i++) {
			if(!canAddItemToArray(st, fakeArray))
				flag = false;
		}
		
		return flag;
	}
	
	public void addItemToInventory(ItemStack stack) {

		ItemStack st = stack.copy();
		
		if(st == null)
			return;
		
		int size = st.stackSize;
		st.stackSize = 1;
		
		for(int i = 0; i < size; i++)
			canAddItemToArray(st, this.slots);
		
	}
	
	public boolean canAddItemToArray(ItemStack stack, ItemStack[] array) {

		ItemStack st = stack.copy();
		
		if(stack == null || st == null)
			return true;
		
		for(int i = 1; i < 10; i++) {
			
			if(array[i] != null) {
				ItemStack sta = array[i].copy();
				
				if(stack == null || st == null)
					return true;
				
				if(sta != null && sta.getItem() == st.getItem() && sta.stackSize < st.getMaxStackSize()) {
					array[i].stackSize++;
					return true;
				}
			}
		}
		
		for(int i = 1; i < 10; i++) {
			if(array[i] == null) {
				array[i] = stack.copy();
				return true;
			}
		}
		
		return false;
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
	public DrillType getDrillTier() {
		return DrillType.INDUSTRIAL;
	}

	@Override
	public int getDrillRating() {
		return 50;
	}
}
