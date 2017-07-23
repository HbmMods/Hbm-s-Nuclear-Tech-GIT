package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IGasAcceptor;
import com.hbm.interfaces.IGasSource;
import com.hbm.interfaces.IOilAcceptor;
import com.hbm.interfaces.IOilSource;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.lib.Library;

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

public class TileEntityMachineOilWell extends TileEntity implements ISidedInventory, IConsumer, IOilSource, IGasSource {

	private ItemStack slots[];

	public int oil;
	public int gas;
	public int power;
	public int warning;
	public int warning2;
	public static final int maxPower = 100000;
	public static final int maxOil = 640;
	public static final int maxGas = 640;
	public int age = 0;
	public int age2 = 0;
	public List<IOilAcceptor> aclist = new ArrayList();
	public List<IGasAcceptor> gaslist = new ArrayList();
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {2, 0};
	private static final int[] slots_side = new int[] {0};
	Random rand = new Random();
	
	private String customName;
	
	public TileEntityMachineOilWell() {
		slots = new ItemStack[6];
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
		return this.hasCustomInventoryName() ? this.customName : "container.oilWell";
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
			if(itemStack.getItem() instanceof ItemBattery)
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
		
		this.power = nbt.getInteger("powerTime");
		this.oil = nbt.getInteger("oil");
		this.gas = nbt.getInteger("gas");
		this.age = nbt.getInteger("age");
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
		nbt.setInteger("powerTime", power);
		nbt.setInteger("oil", oil);
		nbt.setInteger("gas", gas);
		nbt.setInteger("age", age);
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
	
	public int getOilScaled(int i) {
		return (oil * i) / maxOil;
	}
	
	public int getGasScaled(int i) {
		return (gas * i) / maxGas;
	}
	
	public int getPowerScaled(int i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void updateEntity() {
		
		int timer = 50;
		
		age++;
		age2++;
		if(age >= timer)
			age -= timer;
		if(age2 >= 20)
			age2 -= 20;
		if(age2 == 9 || age2 == 19) {
			fillInit();
			fillGasInit();
		}
		
		if(!worldObj.isRemote) {
			
			if(slots[1] != null && slots[1].getItem() == ModItems.canister_empty && oil >= 10) {
				if(slots[2] == null) {
					oil -= 10;
					slots[2] = new ItemStack(ModItems.canister_oil);
					slots[1].stackSize--;
					if(slots[1].stackSize <= 0)
						slots[1] = null;
				} else  if(slots[2] != null && slots[2].getItem() == ModItems.canister_oil && slots[2].stackSize < slots[2].getMaxStackSize()) {
					oil -= 10;
					slots[2].stackSize++;
					slots[1].stackSize--;
					if(slots[1].stackSize <= 0)
						slots[1] = null;
				}
			}
			
			if(slots[3] != null && slots[3].getItem() == ModItems.gas_empty && gas >= 10) {
				if(slots[4] == null) {
					gas -= 10;
					slots[4] = new ItemStack(ModItems.gas_full);
					slots[3].stackSize--;
					if(slots[3].stackSize <= 0)
						slots[3] = null;
				} else  if(slots[4] != null && slots[4].getItem() == ModItems.gas_full && slots[4].stackSize < slots[4].getMaxStackSize()) {
					gas -= 10;
					slots[4].stackSize++;
					slots[3].stackSize--;
					if(slots[3].stackSize <= 0)
						slots[3] = null;
				}
			}

			power = Library.chargeTEFromItems(slots, 0, power, maxPower);
			
			if(power >= 100) {
				
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
						
						if(b == Blocks.air || b == Blocks.grass || b == Blocks.dirt || 
								b == Blocks.stone || b == Blocks.sand || b == Blocks.sandstone || 
								b == Blocks.clay || b == Blocks.hardened_clay || b == Blocks.stained_hardened_clay || 
								b == Blocks.gravel || isOre(b, worldObj.getBlockMetadata(xCoord, i, zCoord)) ||
								b.isReplaceable(worldObj, xCoord, i, zCoord)) {
							worldObj.setBlock(xCoord, i, zCoord, ModBlocks.oil_pipe);
						
							//Code 2: The drilling ended
							if(i == this.yCoord - 100)
								warning = 2;
							break;
							
						} else if((b == ModBlocks.ore_oil || b == ModBlocks.ore_oil_empty) && this.oil < TileEntityMachineOilWell.maxOil && this.gas < TileEntityMachineOilWell.maxGas) {
							if(succ(this.xCoord, i, this.zCoord)) {
								oil += 5;
								if(oil > maxOil)
									oil = maxOil;
								
								gas += rand.nextInt(26) + 25;
								if(gas > maxGas)
									gas = maxGas;
								
								ExplosionLarge.spawnOilSpills(worldObj, xCoord + 0.5F, yCoord + 5.5F, zCoord + 0.5F, 3);
								worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "game.neutral.swim.splash", 3.0F, 0.5F);
								
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
				
				power -= 100;
			} else {
				warning = 1;
			}

			warning2 = 0;
			if(gas > 0) {
				if(slots[5] != null && (slots[5].getItem() == ModItems.fuse || slots[5].getItem() == ModItems.screwdriver)) {
					warning2 = 2;
					gas -= 1;
					if(gas <= 0)
						gas = 0;
		    		worldObj.spawnEntityInWorld(new EntityGasFX(worldObj, this.xCoord + 0.5F, this.yCoord + 6.5F, this.zCoord + 0.5F, 0.0, 0.0, 0.0));
				} else {
					warning2 = 1;
				}
			}
		}
		
	}
	
	public boolean isOre(Block b, int meta) {
		
		int[] ids = OreDictionary.getOreIDs(new ItemStack(b, 1, meta));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 3 && s.substring(0, 3).equals("ore"))
				return true;
		}
		
		return false;
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
	public void setPower(int i) {
		power = i;
		
	}

	@Override
	public int getPower() {
		return power;
		
	}

	@Override
	public int getMaxPower() {
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
	public void fillInit() {
		fill(this.xCoord + 2, this.yCoord, this.zCoord, getTact());
		fill(this.xCoord - 2, this.yCoord, this.zCoord, getTact());
		fill(this.xCoord, this.yCoord, this.zCoord + 2, getTact());
		fill(this.xCoord, this.yCoord, this.zCoord - 2, getTact());
	}

	@Override
	public void fill(int x, int y, int z, boolean newTact) {
		Library.transmitOil(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void fillGasInit() {
		fillGas(this.xCoord + 2, this.yCoord, this.zCoord, getTact());
		fillGas(this.xCoord - 2, this.yCoord, this.zCoord, getTact());
		fillGas(this.xCoord, this.yCoord, this.zCoord + 2, getTact());
		fillGas(this.xCoord, this.yCoord, this.zCoord - 2, getTact());
	}

	@Override
	public void fillGas(int x, int y, int z, boolean newTact) {
		Library.transmitGas(x, y, z, newTact, this, worldObj);
	}

	@Override
	public boolean getTact() {
		if (age2 >= 0 && age2 < 10) {
			return true;
		}

		return false;
	}

	@Override
	public int getSFill() {
		return this.oil;
	}

	@Override
	public void setSFill(int i) {
		this.oil = i;
	}

	@Override
	public List<IOilAcceptor> getList() {
		return aclist;
	}

	@Override
	public void clearList() {
		this.aclist.clear();
	}

	@Override
	public int getGasFill() {
		return this.gas;
	}

	@Override
	public void setGasFill(int i) {
		this.gas = i;
	}

	@Override
	public List<IGasAcceptor> getGasList() {
		return this.gaslist;
	}

	@Override
	public void clearGasList() {
		this.gaslist.clear();
	}

}
