package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineReactor;
import com.hbm.config.MobConfig;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidContainer;
import com.hbm.interfaces.IFluidSource;
import com.hbm.inventory.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFuelRod;
import com.hbm.lib.Library;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.RadiationSavedData;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

public class TileEntityMachineReactorSmall extends TileEntity implements ISidedInventory, IFluidContainer, IFluidAcceptor, IFluidSource {

	private ItemStack slots[];

	public int hullHeat;
	public final int maxHullHeat = 100000;
	public int coreHeat;
	public final int maxCoreHeat = 50000;
	public int rods;
	public final int rodsMax = 100;
	public boolean retracting = true;
	public int age = 0;
	public List<IFluidAcceptor> list = new ArrayList();
	public FluidTank[] tanks;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15 };
	private static final int[] slots_side = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14 };

	private String customName;

	public TileEntityMachineReactorSmall() {
		slots = new ItemStack[16];
		tanks = new FluidTank[3];
		tanks[0] = new FluidTank(FluidType.WATER, 32000, 0);
		tanks[1] = new FluidTank(FluidType.COOLANT, 16000, 1);
		tanks[2] = new FluidTank(FluidType.STEAM, 8000, 2);
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
		if(slots[i] != null) {
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
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
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
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
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
		if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 10 || i == 11)
			if(itemStack.getItem() instanceof ItemFuelRod)
				return true;
		if(i == 12)
			if(itemStack.getItem() == ModItems.rod_water || itemStack.getItem() == ModItems.rod_dual_water || itemStack.getItem() == ModItems.rod_quad_water || itemStack.getItem() == Items.water_bucket)
				return true;
		if(i == 14)
			if(itemStack.getItem() == ModItems.rod_coolant || itemStack.getItem() == ModItems.rod_dual_coolant || itemStack.getItem() == ModItems.rod_quad_coolant)
				return true;
		return false;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(slots[i] != null) {
			if(slots[i].stackSize <= j) {
				ItemStack itemStack = slots[i];
				slots[i] = null;
				return itemStack;
			}
			ItemStack itemStack1 = slots[i].splitStack(j);
			if(slots[i].stackSize == 0) {
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
		retracting = nbt.getBoolean("ret");
		slots = new ItemStack[getSizeInventory()];
		tanks[0].readFromNBT(nbt, "water");
		tanks[1].readFromNBT(nbt, "coolant");
		tanks[2].readFromNBT(nbt, "steam");

		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if(b0 >= 0 && b0 < slots.length) {
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
		nbt.setBoolean("ret", retracting);
		NBTTagList list = new NBTTagList();
		tanks[0].writeToNBT(nbt, "water");
		tanks[1].writeToNBT(nbt, "coolant");
		tanks[2].writeToNBT(nbt, "steam");

		for(int i = 0; i < slots.length; i++) {
			if(slots[i] != null) {
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
		if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 10 || i == 11)
			if(itemStack.getItem() == ModItems.rod_uranium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_dual_uranium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_quad_uranium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_thorium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_dual_thorium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_quad_thorium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_plutonium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_dual_plutonium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_quad_plutonium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_mox_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_dual_mox_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_quad_mox_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_schrabidium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_dual_schrabidium_fuel_depleted ||
			itemStack.getItem() == ModItems.rod_quad_schrabidium_fuel_depleted)
				return true;
		if(i == 13 || i == 15)
			if(itemStack.getItem() == Items.bucket || itemStack.getItem() == ModItems.rod_empty || itemStack.getItem() == ModItems.rod_dual_empty || itemStack.getItem() == ModItems.rod_quad_empty || itemStack.getItem() == ModItems.fluid_tank_empty || itemStack.getItem() == ModItems.fluid_barrel_empty)
				return true;

		return false;

	}

	public int getCoreHeatScaled(int i) {
		return (coreHeat * i) / maxCoreHeat;
	}

	public int getHullHeatScaled(int i) {
		return (hullHeat * i) / maxHullHeat;
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

	private int[] getNeighbouringSlots(int id) {

		switch(id) {
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

	public int getFuelPercent() {

		if(getRodCount() == 0)
			return 0;

		int rodMax = 0;
		int rod = 0;

		for(int i = 0; i < 12; i++) {

			if(slots[i] != null && slots[i].getItem() instanceof ItemFuelRod) {
				rodMax += ((ItemFuelRod) slots[i].getItem()).lifeTime;
				rod += ((ItemFuelRod) slots[i].getItem()).lifeTime - ItemFuelRod.getLifeTime(slots[i]);
			}
		}

		if(rodMax == 0)
			return 0;

		return rod * 100 / rodMax;
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {

			age++;
			if(age >= 20) {
				age = 0;
			}

			if(age == 9 || age == 19)
				fillFluidInit(tanks[2].getTankType());

			tanks[0].loadTank(12, 13, slots);
			tanks[1].loadTank(14, 15, slots);

			if(retracting && rods > 0) {

				if(rods == rodsMax)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStart", 1.0F, 0.75F);

				rods--;

				if(rods == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStop", 1.0F, 1.0F);
			}
			if(!retracting && rods < rodsMax) {

				if(rods == 0)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStart", 1.0F, 0.75F);

				rods++;

				if(rods == rodsMax)
					this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "hbm:block.reactorStop", 1.0F, 1.0F);
			}

			if(rods >= rodsMax)
				
				for(int i = 0; i < 12; i++) {
					
					if(slots[i] != null) {
						if(slots[i].getItem() instanceof ItemFuelRod)
							decay(i);
						else if(slots[i].getItem() == ModItems.meteorite_sword_bred)
							slots[i] = new ItemStack(ModItems.meteorite_sword_irradiated);
					}
				}

			coreHeatMod = 1.0;
			hullHeatMod = 1.0;
			conversionMod = 1.0;
			decayMod = 1.0;

			getInteractions();

			if(this.coreHeat > 0 && this.tanks[1].getFill() > 0 && this.hullHeat < this.maxHullHeat) {
				this.hullHeat += this.coreHeat * 0.175 * hullHeatMod;
				this.coreHeat -= this.coreHeat * 0.1;

				this.tanks[1].setFill(this.tanks[1].getFill() - 10);

				if(this.tanks[1].getFill() < 0)
					this.tanks[1].setFill(0);
			}

			if(this.hullHeat > maxHullHeat) {
				this.hullHeat = maxHullHeat;
			}

			if(this.hullHeat > 0 && this.tanks[0].getFill() > 0) {
				generateSteam();
				this.hullHeat -= this.hullHeat * 0.085;
			}

			if(this.coreHeat > maxCoreHeat) {
				this.explode();
			}

			if(rods > 0 && coreHeat > 0 && !(blocksRad(xCoord + 1, yCoord + 1, zCoord) && blocksRad(xCoord - 1, yCoord + 1, zCoord) && blocksRad(xCoord, yCoord + 1, zCoord + 1) && blocksRad(xCoord, yCoord + 1, zCoord - 1))) {

				/*
				 * List<Entity> list = (List<Entity>)
				 * worldObj.getEntitiesWithinAABBExcludingEntity(null,
				 * AxisAlignedBB.getBoundingBox(xCoord + 0.5 - 5, yCoord + 1.5 -
				 * 5, zCoord + 0.5 - 5, xCoord + 0.5 + 5, yCoord + 1.5 + 5,
				 * zCoord + 0.5 + 5));
				 * 
				 * for (Entity e : list) { if (e instanceof EntityLivingBase)
				 * Library.applyRadiation((EntityLivingBase)e, 80, 24, 60, 19);
				 * }
				 */

				float rad = (float) coreHeat / (float) maxCoreHeat * 50F;
				RadiationSavedData data = RadiationSavedData.getData(worldObj);
				data.incrementRad(worldObj, xCoord, zCoord, rad, rad * 4);
			}

			for(int i = 0; i < 3; i++)
				tanks[i].updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);

			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, rods, 0), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, retracting ? 1 : 0, 1), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, coreHeat, 2), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(xCoord, yCoord, zCoord, hullHeat, 3), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 50));
		}
	}

	@SuppressWarnings("incomplete-switch")
	private void generateSteam() {

		// function of SHS produced per tick
		// maxes out at heat% * tank capacity / 20
		double steam = (((double) hullHeat / (double) maxHullHeat) * ((double) tanks[2].getMaxFill() / 50D)) * conversionMod;

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

		tanks[0].setFill(tanks[0].getFill() - (int) Math.ceil(water));
		tanks[2].setFill(tanks[2].getFill() + (int) Math.floor(steam));

		if(tanks[0].getFill() < 0)
			tanks[0].setFill(0);

		if(tanks[2].getFill() > tanks[2].getMaxFill())
			tanks[2].setFill(tanks[2].getMaxFill());

	}

	private void getInteractions() {

		getInteractionForBlock(xCoord + 1, yCoord + 1, zCoord);
		getInteractionForBlock(xCoord - 1, yCoord + 1, zCoord);
		getInteractionForBlock(xCoord, yCoord + 1, zCoord + 1);
		getInteractionForBlock(xCoord, yCoord + 1, zCoord - 1);

		TileEntity te1 = worldObj.getTileEntity(xCoord + 2, yCoord, zCoord);
		TileEntity te2 = worldObj.getTileEntity(xCoord - 2, yCoord, zCoord);
		TileEntity te3 = worldObj.getTileEntity(xCoord, yCoord, zCoord + 2);
		TileEntity te4 = worldObj.getTileEntity(xCoord, yCoord, zCoord - 2);

		boolean b1 = blocksRad(xCoord + 1, yCoord + 1, zCoord);
		boolean b2 = blocksRad(xCoord - 1, yCoord + 1, zCoord);
		boolean b3 = blocksRad(xCoord, yCoord + 1, zCoord + 1);
		boolean b4 = blocksRad(xCoord, yCoord + 1, zCoord - 1);

		TileEntityMachineReactorSmall[] reactors = new TileEntityMachineReactorSmall[4];

		reactors[0] = ((te1 instanceof TileEntityMachineReactorSmall && !b1) ? (TileEntityMachineReactorSmall) te1 : null);
		reactors[1] = ((te2 instanceof TileEntityMachineReactorSmall && !b2) ? (TileEntityMachineReactorSmall) te2 : null);
		reactors[2] = ((te3 instanceof TileEntityMachineReactorSmall && !b3) ? (TileEntityMachineReactorSmall) te3 : null);
		reactors[3] = ((te4 instanceof TileEntityMachineReactorSmall && !b4) ? (TileEntityMachineReactorSmall) te4 : null);

		for(int i = 0; i < 4; i++) {

			if(reactors[i] != null && reactors[i].rods >= rodsMax && reactors[i].getRodCount() > 0) {
				decayMod += reactors[i].getRodCount() / 2D;
			}
		}
	}

	private double decayMod = 1.0D;
	private double coreHeatMod = 1.0D;
	private double hullHeatMod = 1.0D;
	private double conversionMod = 1.0D;

	private void getInteractionForBlock(int x, int y, int z) {

		Block b = worldObj.getBlock(x, y, z);
		TileEntity te = worldObj.getTileEntity(x, y, z);

		if(b == Blocks.lava || b == Blocks.flowing_lava) {
			hullHeatMod *= 3;
			conversionMod *= 0.5;

		} else if(b == Blocks.redstone_block) {
			conversionMod *= 1.15;

		} else if(b == ModBlocks.block_lead) {
			decayMod += 1;

		} else if(b == Blocks.water || b == Blocks.flowing_water) {
			tanks[0].setFill(tanks[0].getFill() + 25);

			if(tanks[0].getFill() > tanks[0].getMaxFill())
				tanks[0].setFill(tanks[0].getMaxFill());

		} else if(b == ModBlocks.block_niter || b == ModBlocks.block_niter_reinforced) {
			if(tanks[0].getFill() >= 50 && tanks[1].getFill() + 5 <= tanks[1].getMaxFill()) {
				tanks[0].setFill(tanks[0].getFill() - 50);
				tanks[1].setFill(tanks[1].getFill() + 5);
			}

		} else if(b == ModBlocks.machine_reactor) {

			int[] pos = ((MachineReactor) ModBlocks.machine_reactor).findCore(worldObj, x, y, z);

			if(pos != null) {

				TileEntity tile = worldObj.getTileEntity(pos[0], pos[1], pos[2]);

				if(tile instanceof TileEntityMachineReactor) {

					TileEntityMachineReactor reactor = (TileEntityMachineReactor) tile;

					if(reactor.charge <= 1 && this.hullHeat > 0) {
						reactor.charge = 1;
						reactor.heat = (int) Math.floor(hullHeat * 4 / maxHullHeat) + 1;
					}
				}
			}

		} else if(te instanceof TileEntityNukeFurnace) {
			TileEntityNukeFurnace reactor = (TileEntityNukeFurnace) te;
			if(reactor.dualPower < 1 && this.coreHeat > 0)
				reactor.dualPower = 1;

		} else if(b == ModBlocks.block_uranium) {
			coreHeatMod *= 1.05;

		} else if(b == Blocks.coal_block) {
			hullHeatMod *= 1.1;

		} else if(b == ModBlocks.block_beryllium) {
			hullHeatMod *= 0.95;
			conversionMod *= 1.05;

		} else if(b == ModBlocks.block_schrabidium) {
			decayMod += 1;
			conversionMod *= 1.25;
			hullHeatMod *= 1.1;

		} else if(b == ModBlocks.block_waste) {
			decayMod += 3;

		}
	}

	private boolean blocksRad(int x, int y, int z) {

		Block b = worldObj.getBlock(x, y, z);

		if(b == ModBlocks.block_lead || b == ModBlocks.block_desh || b == ModBlocks.brick_concrete)
			return true;

		if(b.getExplosionResistance(null) >= 100)
			return true;

		return false;
	}

	public int getRodCount() {

		int count = 0;

		for(int i = 0; i < 12; i++) {

			if(slots[i] != null && slots[i].getItem() instanceof ItemFuelRod)
				count++;
		}

		return count;
	}

	private boolean hasFuelRod(int id) {
		if(id > 11)
			return false;

		if(slots[id] != null)
			return slots[id].getItem() instanceof ItemFuelRod;

		return false;
	}

	private int getNeightbourCount(int id) {

		int[] neighbours = this.getNeighbouringSlots(id);

		if(neighbours == null)
			return 0;

		int count = 0;

		for(int i = 0; i < neighbours.length; i++)
			if(hasFuelRod(neighbours[i]))
				count++;

		return count;

	}

	// itemstack in slots[id] has to contain ItemFuelRod item
	private void decay(int id) {
		if(id > 11)
			return;

		int decay = getNeightbourCount(id) + 1;

		decay *= decayMod;

		for(int i = 0; i < decay; i++) {
			ItemFuelRod rod = ((ItemFuelRod) slots[id].getItem());
			this.coreHeat += rod.heat * coreHeatMod;
			ItemFuelRod.setLifeTime(slots[id], ItemFuelRod.getLifeTime(slots[id]) + 1);

			if(ItemFuelRod.getLifeTime(slots[id]) > ((ItemFuelRod) slots[id].getItem()).lifeTime) {
				onRunOut(id);
				return;
			}
		}
	}

	// itemstack in slots[id] has to contain ItemFuelRod item
	private void onRunOut(int id) {

		// System.out.println("aaa");

		Item item = slots[id].getItem();

		if(item == ModItems.rod_uranium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_uranium_fuel_depleted);

		} else if(item == ModItems.rod_thorium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_thorium_fuel_depleted);

		} else if(item == ModItems.rod_plutonium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_plutonium_fuel_depleted);

		} else if(item == ModItems.rod_mox_fuel) {
			slots[id] = new ItemStack(ModItems.rod_mox_fuel_depleted);

		} else if(item == ModItems.rod_schrabidium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_schrabidium_fuel_depleted);

		} else if(item == ModItems.rod_dual_uranium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_dual_uranium_fuel_depleted);

		} else if(item == ModItems.rod_dual_thorium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_dual_thorium_fuel_depleted);

		} else if(item == ModItems.rod_dual_plutonium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_dual_plutonium_fuel_depleted);

		} else if(item == ModItems.rod_dual_mox_fuel) {
			slots[id] = new ItemStack(ModItems.rod_dual_mox_fuel_depleted);

		} else if(item == ModItems.rod_dual_schrabidium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_dual_schrabidium_fuel_depleted);

		} else if(item == ModItems.rod_quad_uranium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_quad_uranium_fuel_depleted);

		} else if(item == ModItems.rod_quad_thorium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_quad_thorium_fuel_depleted);

		} else if(item == ModItems.rod_quad_plutonium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_quad_plutonium_fuel_depleted);

		} else if(item == ModItems.rod_quad_mox_fuel) {
			slots[id] = new ItemStack(ModItems.rod_quad_mox_fuel_depleted);

		} else if(item == ModItems.rod_quad_schrabidium_fuel) {
			slots[id] = new ItemStack(ModItems.rod_quad_schrabidium_fuel_depleted);
		}
	}

	private void explode() {
		
		for(int i = 0; i < slots.length; i++) {
			this.slots[i] = null;
		}

		worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
		worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, 18.0F, true);
		ExplosionNukeGeneric.waste(worldObj, this.xCoord, this.yCoord, this.zCoord, 35);
		worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, ModBlocks.toxic_block);

		RadiationSavedData data = RadiationSavedData.getData(worldObj);
		data.incrementRad(worldObj, xCoord, zCoord, 1000F, 2000F);
		
		if(MobConfig.enableElementals) {
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(100, 100, 100));
			
			for(EntityPlayer player : players) {
				player.getEntityData().getCompoundTag(player.PERSISTED_NBT_TAG).setBoolean("radMark", true);
			}
		}
	}

	@Override
	public void fillFluid(int x, int y, int z, boolean newTact, FluidType type) {
		Library.transmitFluid(x, y, z, newTact, this, worldObj, type);
	}

	@Override
	public void fillFluidInit(FluidType type) {
		fillFluid(this.xCoord - 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord, this.zCoord + 1, getTact(), type);

		fillFluid(this.xCoord - 1, this.yCoord + 2, this.zCoord, getTact(), type);
		fillFluid(this.xCoord + 1, this.yCoord + 2, this.zCoord, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 2, this.zCoord - 1, getTact(), type);
		fillFluid(this.xCoord, this.yCoord + 2, this.zCoord + 1, getTact(), type);
	}

	@Override
	public boolean getTact() {
		if(age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public int getMaxFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getMaxFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getMaxFill();
		else
			return 0;
	}

	@Override
	public void setFluidFill(int i, FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			tanks[0].setFill(i);
		else if(type.name().equals(tanks[1].getTankType().name()))
			tanks[1].setFill(i);
		else if(type.name().equals(tanks[2].getTankType().name()))
			tanks[2].setFill(i);
	}

	@Override
	public int getFluidFill(FluidType type) {
		if(type.name().equals(tanks[0].getTankType().name()))
			return tanks[0].getFill();
		else if(type.name().equals(tanks[1].getTankType().name()))
			return tanks[1].getFill();
		else if(type.name().equals(tanks[2].getTankType().name()))
			return tanks[2].getFill();
		else
			return 0;
	}

	@Override
	public void setFillstate(int fill, int index) {
		if(index < 3 && tanks[index] != null)
			tanks[index].setFill(fill);
	}

	@Override
	public void setType(FluidType type, int index) {
		if(index < 3 && tanks[index] != null)
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

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	public boolean isSubmerged() {
		
		return worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord).getMaterial() == Material.water &&
				worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1).getMaterial() == Material.water &&
				worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord).getMaterial() == Material.water &&
				worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1).getMaterial() == Material.water;
	}
}