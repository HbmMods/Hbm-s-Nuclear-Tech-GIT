package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.machine.MachineGenerator;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.ISource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.items.special.ItemFuelRod;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityMachineReactorSmall extends TileEntity
		implements ISidedInventory, ISource, IFluidContainer, IFluidAcceptor {

	private ItemStack slots[];

	public int hullHeat;
	public final int maxHullHeat = 100000;
	public int coreHeat;
	public final int maxCoreHeat = 50000;
	public long power;
	public final long powerMax = 250000;
	public int rods;
	public final int rodsMax = 100;
	public boolean retracting = true;
	public int age = 0;
	public List<IConsumer> list = new ArrayList();
	public FluidTank[] tanks;

	private static final int[] slots_top = new int[] { 16 };
	private static final int[] slots_bottom = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 16 };
	private static final int[] slots_side = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16 };

	private String customName;

	public TileEntityMachineReactorSmall() {
		slots = new ItemStack[17];
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(FluidType.WATER, 32000, 0);
		tanks[1] = new FluidTank(FluidType.COOLANT, 16000, 1);
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
		return this.hasCustomInventoryName() ? this.customName : "container.reactorSmall";
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
		if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 10
				|| i == 11)
			if (itemStack.getItem() instanceof ItemFuelRod)
				return true;
		if (i == 12)
			if (itemStack.getItem() == ModItems.rod_water || itemStack.getItem() == ModItems.rod_dual_water
					|| itemStack.getItem() == ModItems.rod_quad_water || itemStack.getItem() == Items.water_bucket)
				return true;
		if (i == 14)
			if (itemStack.getItem() == ModItems.rod_coolant || itemStack.getItem() == ModItems.rod_dual_coolant
					|| itemStack.getItem() == ModItems.rod_quad_coolant)
				return true;
		if (i == 16)
			if (itemStack.getItem() instanceof ItemBattery)
				return true;
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

		power = nbt.getLong("power");
		coreHeat = nbt.getInteger("heat");
		hullHeat = nbt.getInteger("hullHeat");
		slots = new ItemStack[getSizeInventory()];
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "coolant");

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
		nbt.setInteger("heat", coreHeat);
		nbt.setInteger("hullHeat", hullHeat);
		NBTTagList list = new NBTTagList();
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "coolant");

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
		if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 10
				|| i == 11)
			if (itemStack.getItem() == ModItems.rod_uranium_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_dual_uranium_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_quad_uranium_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_plutonium_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_dual_plutonium_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_quad_plutonium_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_mox_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_dual_mox_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_quad_mox_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_schrabidium_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_dual_schrabidium_fuel_depleted
					|| itemStack.getItem() == ModItems.rod_quad_schrabidium_fuel_depleted)
				return true;
		if (i == 13 || i == 15)
			if (itemStack.getItem() == Items.bucket || itemStack.getItem() == ModItems.rod_empty
					|| itemStack.getItem() == ModItems.rod_dual_empty || itemStack.getItem() == ModItems.rod_quad_empty
					|| itemStack.getItem() == ModItems.fluid_tank_empty
					|| itemStack.getItem() == ModItems.fluid_barrel_empty)
				return true;
		if (i == 16)
			if (itemStack.getItem() instanceof ItemBattery
					&& ItemBattery.getCharge(itemStack) == ItemBattery.getMaxChargeStatic(itemStack))
				return true;

		return false;
	}

	public long getPowerScaled(long i) {
		return (power * i) / powerMax;
	}

	public int getCoreHeatScaled(int i) {
		return (coreHeat * i) / maxCoreHeat;
	}

	public int getHullHeatScaled(int i) {
		return (hullHeat * i) / maxHullHeat;
	}

	public boolean hasPower() {
		return power > 0;
	}

	public boolean hasCoreHeat() {
		return coreHeat > 0;
	}

	public boolean hasHullHeat() {
		return hullHeat > 0;
	}

	private int[] getNeighbouringSlots(int id) {

		switch (id) {
		case 0:
			return new int[] { 1, 5 };
		case 1:
			return new int[] { 0, 6 };
		case 2:
			return new int[] { 3, 7 };
		case 3:
			return new int[] { 2, 4, 8 };
		case 4:
			return new int[] { 3, 9 };
		case 5:
			return new int[] { 0, 6, 0xA };
		case 6:
			return new int[] { 1, 5, 0xB };
		case 7:
			return new int[] { 2, 8 };
		case 8:
			return new int[] { 3, 7, 9 };
		case 9:
			return new int[] { 4, 8 };
		case 10:
			return new int[] { 5, 0xB };
		case 11:
			return new int[] { 6, 0xA };
		}

		return null;
	}

	@Override
	public void updateEntity() {

		if (!worldObj.isRemote) {

			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19)
				ffgeuaInit();

			if (tanks[0].getFill() < tanks[0].getMaxFill()) {

				if (worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord) == Blocks.water
						|| worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord) == Blocks.flowing_water)
					tanks[0].setFill(tanks[0].getFill() + 25);

				if (worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord) == Blocks.water
						|| worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord) == Blocks.flowing_water)
					tanks[0].setFill(tanks[0].getFill() + 25);

				if (worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1) == Blocks.water
						|| worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1) == Blocks.flowing_water)
					tanks[0].setFill(tanks[0].getFill() + 25);

				if (worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1) == Blocks.water
						|| worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1) == Blocks.flowing_water)
					tanks[0].setFill(tanks[0].getFill() + 25);

				if (tanks[0].getFill() > tanks[0].getMaxFill())
					tanks[0].setFill(tanks[0].getMaxFill());
			}

			tanks[0].loadTank(12, 13, slots);
			tanks[1].loadTank(14, 15, slots);

			if (retracting && rods > 0) {

				if (rods == rodsMax)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStart", 1.0F,
							0.75F);

				rods--;

				if (rods == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStop", 1.0F,
							1.0F);
			}
			if (!retracting && rods < rodsMax) {

				if (rods == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStart", 1.0F,
							0.75F);

				rods++;

				if (rods == rodsMax)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStop", 1.0F,
							1.0F);
			}

			for (int i = 0; i < 2; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord);

			// Batteries
			power = Library.chargeItemsFromTE(slots, 11, power, powerMax);

			if (rods >= rodsMax)
				for (int i = 0; i < 12; i++) {
					if (slots[i] != null && slots[i].getItem() instanceof ItemFuelRod)
						decay(i);
				}

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
				this.power += this.hullHeat * 0.1;
				this.hullHeat -= this.hullHeat * 0.085;

				this.tanks[0].setFill(this.tanks[0].getFill() - 100);

				if (this.tanks[0].getFill() < 0)
					this.tanks[0].setFill(0);
			}

			if (this.power > powerMax) {
				this.power = powerMax;
			}

			if (this.coreHeat > maxCoreHeat) {
				this.explode();
			}

			if (rods > 0 && coreHeat > 0
					&& !(worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord).isNormalCube()
							&& worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord).isNormalCube()
							&& worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1).isNormalCube()
							&& worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1).isNormalCube()
							&& worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord) != Blocks.air
							&& worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord) != Blocks.air
							&& worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1) != Blocks.air
							&& worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1) != Blocks.air)) {

				List<Entity> list = (List<Entity>) worldObj.getEntitiesWithinAABBExcludingEntity(null,
						AxisAlignedBB.getBoundingBox(xCoord + 0.5 - 5, yCoord + 1.5 - 5, zCoord + 0.5 - 5,
								xCoord + 0.5 + 5, yCoord + 1.5 + 5, zCoord + 0.5 + 5));

				for (Entity e : list) {
					if (e instanceof EntityLivingBase)
                		Library.applyRadiation((EntityLivingBase)e, 80, 24, 60, 19);
				}
			}

			PacketDispatcher.wrapper.sendToAll(new AuxElectricityPacket(xCoord, yCoord, zCoord, power));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, rods, 0));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, retracting ? 1 : 0, 1));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, coreHeat, 2));
			PacketDispatcher.wrapper.sendToAll(new AuxGaugePacket(xCoord, yCoord, zCoord, hullHeat, 3));
		}
	}

	private boolean hasFuelRod(int id) {
		if (id > 11)
			return false;

		if (slots[id] != null)
			return slots[id].getItem() instanceof ItemFuelRod;

		return false;
	}

	private int getNeightbourCount(int id) {

		int[] neighbours = this.getNeighbouringSlots(id);

		if (neighbours == null)
			return 0;

		int count = 0;

		for (int i = 0; i < neighbours.length; i++)
			if (hasFuelRod(neighbours[i]))
				count++;

		return count;

	}

	// itemstack in slots[id] has to contain ItemFuelRod item
	private void decay(int id) {
		if (id > 11)
			return;

		int decay = getNeightbourCount(id) + 1;

		for (int i = 0; i < decay; i++) {
			ItemFuelRod rod = ((ItemFuelRod) slots[id].getItem());
			this.coreHeat += rod.heat;
			ItemFuelRod.setLifeTime(slots[id], ItemFuelRod.getLifeTime(slots[id]) + 1);
			ItemFuelRod.updateDamage(slots[id]);

			if (ItemFuelRod.getLifeTime(slots[id]) > ((ItemFuelRod) slots[id].getItem()).lifeTime) {
				onRunOut(id);
				return;
			}
		}
	}

	// itemstack in slots[id] has to contain ItemFuelRod item
	private void onRunOut(int id) {

		System.out.println("aaa");

		Item item = slots[id].getItem();

		if (item == ModItems.rod_uranium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_uranium_fuel_depleted);

		} else if (item == ModItems.rod_plutonium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_plutonium_fuel_depleted);

		} else if (item == ModItems.rod_mox_fuel) {
			slots[id] = new ItemStack(ModItems.rod_mox_fuel_depleted);

		} else if (item == ModItems.rod_schrabidium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_schrabidium_fuel_depleted);

		} else if (item == ModItems.rod_dual_uranium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_dual_uranium_fuel_depleted);

		} else if (item == ModItems.rod_dual_plutonium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_dual_plutonium_fuel_depleted);

		} else if (item == ModItems.rod_dual_mox_fuel) {
			slots[id] = new ItemStack(ModItems.rod_dual_mox_fuel_depleted);

		} else if (item == ModItems.rod_dual_schrabidium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_dual_schrabidium_fuel_depleted);

		} else if (item == ModItems.rod_quad_uranium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_quad_uranium_fuel_depleted);

		} else if (item == ModItems.rod_quad_plutonium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_quad_plutonium_fuel_depleted);

		} else if (item == ModItems.rod_quad_mox_fuel) {
			slots[id] = new ItemStack(ModItems.rod_quad_mox_fuel_depleted);

		} else if (item == ModItems.rod_quad_schrabidium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_quad_schrabidium_fuel_depleted);
		}
	}

	private void explode() {
		for (int i = 0; i < slots.length; i++) {
			this.slots[i] = null;
		}

		worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 18.0F, true);
		ExplosionNukeGeneric.waste(worldObj, this.xCoord, this.yCoord, this.zCoord, 35);
		worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.flowing_lava);
	}

	@Override
	public void ffgeua(int x, int y, int z, boolean newTact) {

		Library.ffgeua(x, y, z, newTact, this, worldObj);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(this.xCoord - 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord + 1, this.yCoord, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord - 1, getTact());
		ffgeua(this.xCoord, this.yCoord, this.zCoord + 1, getTact());

		ffgeua(this.xCoord - 1, this.yCoord + 2, this.zCoord, getTact());
		ffgeua(this.xCoord + 1, this.yCoord + 2, this.zCoord, getTact());
		ffgeua(this.xCoord, this.yCoord + 2, this.zCoord - 1, getTact());
		ffgeua(this.xCoord, this.yCoord + 2, this.zCoord + 1, getTact());
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
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
	public void setFillstate(int fill, int index) {
		if (index < 2 && tanks[index] != null)
			tanks[index].setFill(fill);
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
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}