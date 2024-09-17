package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineDiFurnace;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.container.ContainerDiFurnace;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIDiFurnace;
import com.hbm.inventory.recipes.BlastFurnaceRecipes;
import com.hbm.items.ModItems;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.CompatEnergyControl;

import api.hbm.fluid.IFluidStandardSender;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDiFurnace extends TileEntityMachinePolluting implements IFluidStandardSender, IGUIProvider, IInfoProviderEC {

	public int progress;
	public int fuel;
	public static final int maxFuel = 12800;
	public static final int processingSpeed = 400;

	private static final int[] slots_io = new int[] { 0, 1, 2, 3 };
	public byte sideFuel = 1;
	public byte sideUpper = 1;
	public byte sideLower = 1;

	public TileEntityDiFurnace() {
		super(4, 50);
	}

	@Override
	public String getName() {
		return "container.diFurnace";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return i != 3;
	}

	public boolean hasItemPower(ItemStack stack) {
		return getItemPower(stack) > 0;
	}

	//TODO: replace this terribleness
	private static int getItemPower(ItemStack stack) {
		if(stack == null) {
			return 0;
		} else {
			Item item = stack.getItem();

			if(item == Items.coal) return 200;
			if(item == Item.getItemFromBlock(Blocks.coal_block)) return 2000;
			if(item == Item.getItemFromBlock(ModBlocks.block_coke)) return 4000;
			if(item == Items.lava_bucket) return 12800;
			if(item == Items.blaze_rod) return 1000;
			if(item == Items.blaze_powder) return 300;
			if(item == ModItems.lignite) return 150;
			if(item == ModItems.powder_lignite) return 150;
			if(item == ModItems.powder_coal) return 200;
			if(item == ModItems.briquette) return 200;
			if(item == ModItems.coke) return 400;
			if(item == ModItems.solid_fuel) return 400;

			return 0;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.fuel = nbt.getInteger("powerTime");
		this.progress = nbt.getShort("cookTime");
		
		byte[] modes = nbt.getByteArray("modes");
		this.sideFuel = modes[0];
		this.sideUpper = modes[1];
		this.sideLower = modes[2];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("powerTime", fuel);
		nbt.setShort("cookTime", (short) progress);
		nbt.setByteArray("modes", new byte[] {(byte) sideFuel, (byte) sideUpper, (byte) sideLower});
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots_io;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemStack, int j) {

		if(i == 0 && this.sideUpper != j) return false;
		if(i == 1 && this.sideLower != j) return false;
		if(i == 2 && this.sideFuel != j) return false;
		if(i == 3) return false;
		
		return this.isItemValidForSlot(i, itemStack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 3;
	}

	public int getDiFurnaceProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}

	public int getPowerRemainingScaled(int i) {
		return (fuel * i) / maxFuel;
	}

	public boolean canProcess() {
		if(slots[0] == null || slots[1] == null) return false;
		if(!this.hasPower()) return false;
		
		ItemStack output = BlastFurnaceRecipes.getOutput(slots[0], slots[1]);
		if(output == null) return false;
		if(slots[3] == null) return true;
		if(!slots[3].isItemEqual(output)) return false;

		if(slots[3].stackSize + output.stackSize <= slots[3].getMaxStackSize()) {
			return true;
		}
		
		return false;
	}

	private void processItem() {
		ItemStack itemStack = BlastFurnaceRecipes.getOutput(slots[0], slots[1]);

		if(slots[3] == null) {
			slots[3] = itemStack.copy();
		} else if(slots[3].isItemEqual(itemStack)) {
			slots[3].stackSize += itemStack.stackSize;
		}

		for(int i = 0; i < 2; i++) {
			this.decrStackSize(i, 1);
		}
	}

	public boolean hasPower() {
		return fuel > 0;
	}

	public boolean isProcessing() {
		return this.progress > 0;
	}

	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			boolean extension = worldObj.getBlock(xCoord, yCoord + 1, zCoord) == ModBlocks.machine_difurnace_extension;
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				this.sendSmoke(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			}
			
			if(extension) this.sendSmoke(xCoord, yCoord + 2, zCoord, ForgeDirection.UP);

			boolean markDirty = false;
			
			if(this.hasItemPower(this.slots[2]) && this.fuel <= (TileEntityDiFurnace.maxFuel - TileEntityDiFurnace.getItemPower(this.slots[2]))) {
				this.fuel += getItemPower(this.slots[2]);
				if(this.slots[2] != null) {
					markDirty = true;
					this.slots[2].stackSize--;
					if(this.slots[2].stackSize == 0) {
						this.slots[2] = this.slots[2].getItem().getContainerItem(this.slots[2]);
					}
				}
			}

			if(canProcess()) {

				//fuel -= extension ? 2 : 1;
				fuel -= 1; //switch it up on me, fuel efficiency, on fumes i'm running - running - running - running
				progress += extension ? 3 : 1;

				if(this.progress >= TileEntityDiFurnace.processingSpeed) {
					this.progress = 0;
					this.processItem();
					markDirty = true;
				}
				
				if(fuel < 0) {
					fuel = 0;
				}

				if(worldObj.getTotalWorldTime() % 20 == 0) this.pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND * (extension ? 3 : 1));
				
			} else {
				progress = 0;
			}

			boolean trigger = true;

			if(canProcess() && this.progress == 0) {
				trigger = false;
			}

			if(trigger) {
				markDirty = true;
				MachineDiFurnace.updateBlockState(this.progress > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}

			NBTTagCompound data = new NBTTagCompound();
			data.setShort("time", (short) this.progress);
			data.setShort("fuel", (short) this.fuel);
			data.setByteArray("modes", new byte[] { (byte) sideFuel, (byte) sideUpper, (byte) sideLower });
			INBTPacketReceiver.networkPack(this, data, 15);

			if(markDirty) {
				this.markDirty();
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.progress = nbt.getShort("time");
		this.fuel = nbt.getShort("fuel");
		byte[] modes = nbt.getByteArray("modes");
		this.sideFuel = modes[0];
		this.sideUpper = modes[1];
		this.sideLower = modes[2];
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerDiFurnace(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIDiFurnace(player.inventory, this);
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[0];
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return this.getSmokeTanks();
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		data.setLong(CompatEnergyControl.L_ENERGY_, this.fuel);
		data.setLong(CompatEnergyControl.L_CAPACITY_, this.maxFuel);
		data.setInteger(CompatEnergyControl.I_PROGRESS, this.progress);
	}
}
