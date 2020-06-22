package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineCyclotron extends TileEntity implements ISidedInventory, ISource {

	private ItemStack slots[];

	public long power;
	public int progress;
	public int soundCycle = 0;
	public static final long maxPower = 10000000;
	public static final int processTime = 690;
	public boolean isOn = false;
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	Random rand = new Random();

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0, 0 };
	private static final int[] slots_side = new int[] { 0 };

	private String customName;

	public TileEntityMachineCyclotron() {
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
		return this.hasCustomInventoryName() ? this.customName : "container.cyclotron";
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

		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		this.isOn = nbt.getBoolean("isOn");
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
		nbt.setLong("power", power);
		nbt.setInteger("progress", progress);
		nbt.setBoolean("isOn", isOn);
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

			if(!isOn && hasFuse() && getHeatLevel() != 4 && hasEnergy() && (isPart(slots[0]) || isPart(slots[1]) || isPart(slots[2]))) {
				isOn = true;
				slots[6] = null;
			}
			
			if(isOn && (!hasFuse() || (!isPart(slots[0]) && !isPart(slots[1]) && !isPart(slots[2])))) {
				isOn = false;
			}
			
			if(isOn) {

				this.power += getPower(slots[0]);
				this.power += getPower(slots[1]);
				this.power += getPower(slots[2]);
				if(this.power > maxPower)
					power = maxPower;
				
				if(progress < processTime) {
					progress++;
				} else {
					progress = 0;
					process();
				}
				
				if(slots[7] != null) {
					slots[7].setItemDamage(slots[7].getItemDamage() + 1);
					if(slots[7].getItemDamage() >= slots[7].getMaxDamage())
						slots[7] = null;
				}
				
				if(getCoolantTicksLeft() == 100) {
			        this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.shutdown", 10.0F, 1.0F);
				}
				
				if(getHeatLevel() == 1) {
					ExplosionChaos.flameDeath(worldObj, this.xCoord, this.yCoord, zCoord, 15);
				}
				
				if(getHeatLevel() == 2) {
					ExplosionChaos.flameDeath(worldObj, this.xCoord, this.yCoord, zCoord, 25);
					ExplosionChaos.burn(worldObj, this.xCoord, this.yCoord, zCoord, 7);
					ExplosionThermo.setEntitiesOnFire(worldObj, this.xCoord, this.yCoord, zCoord, 7);
				}
				
				if(getHeatLevel() == 3) {
					ExplosionChaos.flameDeath(worldObj, this.xCoord, this.yCoord, zCoord, 35);
					ExplosionChaos.burn(worldObj, this.xCoord, this.yCoord, zCoord, 15);
					ExplosionThermo.setEntitiesOnFire(worldObj, this.xCoord, this.yCoord, zCoord, 25);
					ExplosionThermo.scorchLight(worldObj, this.xCoord, this.yCoord, zCoord, 5);
					if(rand.nextInt(50) == 0)
						ExplosionLarge.spawnTracers(worldObj, xCoord + 0.5, yCoord + 5, zCoord + 0.5, 3);
				}
				
				if(getHeatLevel() == 4) {
					int i = rand.nextInt(4);

					worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
					
					if(i == 0) {
						ExplosionLarge.explodeFire(worldObj, xCoord, yCoord, zCoord, 35 + rand.nextInt(21), true, true, true);
					}
					if(i == 1) {
						worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(worldObj, (int)(MainRegistry.fatmanRadius * 1.5), xCoord, yCoord, zCoord));
						ExplosionParticleB.spawnMush(worldObj, xCoord, yCoord - 3, zCoord);
					}
					if(i == 2) {
					
						EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(worldObj);
						entity.posX = this.xCoord;
						entity.posY = this.yCoord;
						entity.posZ = this.zCoord;
						int j = 15 + rand.nextInt(21);
						entity.destructionRange = j;
						entity.speed = 25;
						entity.coefficient = 1.0F;
						entity.waste = false;
		    	
						worldObj.spawnEntityInWorld(entity);
		    		
						EntityCloudFleija cloud = new EntityCloudFleija(worldObj, j);
						cloud.posX = xCoord;
						cloud.posY = yCoord;
						cloud.posZ = zCoord;
						worldObj.spawnEntityInWorld(cloud);
					}
					if(i == 3) {
						EntityBlackHole bl = new EntityBlackHole(worldObj, 1.5F + rand.nextFloat());
						bl.posX = xCoord + 0.5F;
						bl.posY = yCoord + 3.5F;
						bl.posZ = zCoord + 0.5F;
						worldObj.spawnEntityInWorld(bl);
					}
				}
				
			} else {
				progress = 0;
			}
			
			power = Library.chargeItemsFromTE(slots, 9, power, maxPower);
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(xCoord, yCoord, zCoord, power), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}
	
	public void process() {
		ItemStack stack1 = MachineRecipes.getCyclotronOutput(slots[0], slots[3]);
		ItemStack stack2 = MachineRecipes.getCyclotronOutput(slots[1], slots[4]);
		ItemStack stack3 = MachineRecipes.getCyclotronOutput(slots[2], slots[5]);
		
		if(stack1 != null && hasSpaceForItem(stack1.getItem())) {
			addItemPlox(stack1.getItem());
			slots[0].stackSize--;
			slots[3].stackSize--;
			if(slots[0].stackSize <= 0)
				slots[0] = null;
			if(slots[3].stackSize <= 0)
				slots[3] = null;
		}
		if(stack2 != null && hasSpaceForItem(stack2.getItem())) {
			addItemPlox(stack2.getItem());
			slots[1].stackSize--;
			slots[4].stackSize--;
			if(slots[1].stackSize <= 0)
				slots[1] = null;
			if(slots[4].stackSize <= 0)
				slots[4] = null;
		}
		if(stack3 != null && hasSpaceForItem(stack3.getItem())) {
			addItemPlox(stack3.getItem());
			slots[2].stackSize--;
			slots[5].stackSize--;
			if(slots[2].stackSize <= 0)
				slots[2] = null;
			if(slots[5].stackSize <= 0)
				slots[5] = null;
		}
		
		if(slots[0] != null && stack1 == null) {
			if(rand.nextInt(100) < getAmatChance(slots[0]))
				if(hasSpaceForItem(ModItems.cell_antimatter) && useCell())
					addItemPlox(ModItems.cell_antimatter);

			slots[0].stackSize--;
			if(slots[0].stackSize <= 0)
				slots[0] = null;
					
		}
		
		if(slots[1] != null && stack1 == null) {
			if(rand.nextInt(100) < getAmatChance(slots[1]))
				if(hasSpaceForItem(ModItems.cell_antimatter) && useCell())
					addItemPlox(ModItems.cell_antimatter);
			
			slots[1].stackSize--;
			if(slots[1].stackSize <= 0)
				slots[1] = null;
					
		}
		
		if(slots[2] != null && stack1 == null) {
			if(rand.nextInt(100) < getAmatChance(slots[2]))
				if(hasSpaceForItem(ModItems.cell_antimatter) && useCell())
					addItemPlox(ModItems.cell_antimatter);

			slots[2].stackSize--;
			if(slots[2].stackSize <= 0)
				slots[2] = null;
					
		}
	}
	
	public boolean hasSpaceForItem(Item item) {
		
		if(slots[11] == null || slots[12] == null || slots[13] == null || slots[14] == null || slots[15] == null)
			return true;

		if(slots[11] != null && slots[11].getItem() == item && slots[11].stackSize < slots[11].getMaxStackSize())
			return true;
		if(slots[12] != null && slots[12].getItem() == item && slots[12].stackSize < slots[12].getMaxStackSize())
			return true;
		if(slots[13] != null && slots[13].getItem() == item && slots[13].stackSize < slots[13].getMaxStackSize())
			return true;
		if(slots[14] != null && slots[14].getItem() == item && slots[14].stackSize < slots[14].getMaxStackSize())
			return true;
		if(slots[15] != null && slots[15].getItem() == item && slots[15].stackSize < slots[15].getMaxStackSize())
			return true;
		
		return false;
	}
	
	public boolean useCell() {
		if(slots[10] != null && slots[10].getItem() == ModItems.cell_empty) {
			slots[10].stackSize--;
			if(slots[10].stackSize <= 0)
				slots[10] = null;
			return true;
		}
		return false;
	}
	
	public void addItemPlox(Item item) {
		if(slots[11] != null && slots[11].getItem() == item && slots[11].stackSize < slots[11].getMaxStackSize()) {
			slots[11].stackSize++;
			return;
		}
		if(slots[12] != null && slots[12].getItem() == item && slots[12].stackSize < slots[12].getMaxStackSize()) {
			slots[12].stackSize++;
			return;
		}
		if(slots[13] != null && slots[13].getItem() == item && slots[13].stackSize < slots[13].getMaxStackSize()) {
			slots[13].stackSize++;
			return;
		}
		if(slots[14] != null && slots[14].getItem() == item && slots[14].stackSize < slots[14].getMaxStackSize()) {
			slots[14].stackSize++;
			return;
		}
		if(slots[15] != null && slots[15].getItem() == item && slots[15].stackSize < slots[15].getMaxStackSize()) {
			slots[15].stackSize++;
			return;
		}
		if(slots[11] == null) {
			slots[11] = new ItemStack(item, 1);
			return;
		}
		if(slots[12] == null) {
			slots[12] = new ItemStack(item, 1);
			return;
		}
		if(slots[13] == null) {
			slots[13] = new ItemStack(item, 1);
			return;
		}
		if(slots[14] == null) {
			slots[14] = new ItemStack(item, 1);
			return;
		}
		if(slots[15] == null) {
			slots[15] = new ItemStack(item, 1);
			return;
		}
	}
	
	public boolean hasFuse() {
		return slots[8] != null && (slots[8].getItem() == ModItems.fuse || slots[8].getItem() == ModItems.screwdriver);
	}
	
	public boolean hasEnergy() {
		return slots[6] != null && slots[6].getItem() == ModItems.crystal_energy;
	}
	
	public int getHeatLevel() {
		if(slots[7] != null && slots[7].getItem() == ModItems.pellet_coolant) {
			int i = (slots[7].getItemDamage() * 100) / slots[7].getMaxDamage();
			if(i < 75)
				return 0;
			if(i < 85)
				return 1;
			if(i < 95)
				return 2;
			return 3;
		}
		
		return 4;
	}
	
	public int getCoolantTicksLeft() {
		if(slots[7] != null && slots[7].getItem() == ModItems.pellet_coolant) {
			int i = slots[7].getMaxDamage() - slots[7].getItemDamage();
			return i;
		}
		
		return 0;
	}
	
	public boolean isPart(ItemStack stack) {
		if(stack != null) {
			if(stack.getItem() == ModItems.part_lithium)
				return true;
			if(stack.getItem() == ModItems.part_beryllium)
				return true;
			if(stack.getItem() == ModItems.part_carbon)
				return true;
			if(stack.getItem() == ModItems.part_copper)
				return true;
			if(stack.getItem() == ModItems.part_plutonium)
				return true;
		}
		return false;
	}
	
	public int getPower(ItemStack stack) {
		if(stack != null) {
			if(stack.getItem() == ModItems.part_lithium)
				return 250;
			if(stack.getItem() == ModItems.part_beryllium)
				return 350;
			if(stack.getItem() == ModItems.part_carbon)
				return 600;
			if(stack.getItem() == ModItems.part_copper)
				return 750;
			if(stack.getItem() == ModItems.part_plutonium)
				return 1000;
		}
		return 0;
	}
	
	public int getAmatChance(ItemStack stack) {
		if(stack != null) {
			if(stack.getItem() == ModItems.part_lithium)
				return 2;
			if(stack.getItem() == ModItems.part_beryllium)
				return 3;
			if(stack.getItem() == ModItems.part_carbon)
				return 6;
			if(stack.getItem() == ModItems.part_copper)
				return 29;
			if(stack.getItem() == ModItems.part_plutonium)
				return 94;
		}
		return 0;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getProgressScaled(int i) {
		return (progress * i) / processTime;
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {
		
		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord + 2, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord - 2, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 2, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 2, getTact());
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
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
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
