package com.hbm.tileentity.machine;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.blocks.machine.MachineCoal;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.pollution.PollutionHandler.PollutionType;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.container.ContainerMachineCoal;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineCoal;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;

import api.hbm.energy.IBatteryItem;
import api.hbm.energy.IEnergyGenerator;
import api.hbm.fluid.IFluidStandardTransceiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineCoal extends TileEntityMachinePolluting implements ISidedInventory, IEnergyGenerator, IFluidStandardTransceiver, IConfigurableMachine, IGUIProvider {

	public long power;
	public int burnTime;
	public static final long maxPower = 100000;
	public FluidTank tank;
	
	private static final int[] slots_top = new int[] {1};
	private static final int[] slots_bottom = new int[] {0, 2};
	private static final int[] slots_side = new int[] {0, 2};
	
	/* CONFIGURABLE CONSTANTS */
	public static int waterCap = 5000;
	public static int genRate = 25;
	public static double fuelMod = 0.5D;
	
	public TileEntityMachineCoal() {
		super(4, 50);
		tank = new FluidTank(Fluids.WATER, waterCap, 0);
	}

	@Override
	public String getName() {
		return "container.machineCoal";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 0)
			if(FluidContainerRegistry.getFluidContent(stack, Fluids.WATER) > 0)
				return true;
		if(i == 2)
			if(stack.getItem() instanceof IBatteryItem)
				return true;
		if(i == 1)
			if(TileEntityFurnace.getItemBurnTime(stack) > 0)
				return true;
		
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.power = nbt.getLong("powerTime");
		tank.readFromNBT(nbt, "water");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setLong("powerTime", power);
		tank.writeToNBT(nbt, "water");
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
		if(i == 0)
			if(itemStack.getItem() == Items.bucket || itemStack.getItem() == ModItems.rod_empty || itemStack.getItem() == ModItems.rod_dual_empty || itemStack.getItem() == ModItems.rod_quad_empty)
				return true;
		if(i == 2)
			if (itemStack.getItem() instanceof IBatteryItem && ((IBatteryItem)itemStack.getItem()).getCharge(itemStack) == ((IBatteryItem)itemStack.getItem()).getMaxCharge())
				return true;
		
		return false;
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void updateEntity() {

		if(!worldObj.isRemote) {
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				this.sendPower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
				this.sendSmoke(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			}
			
			this.subscribeToAllAround(Fluids.WATER, this);
		
			//Water
			tank.loadTank(0, 3, slots);
			
			tank.updateTank(xCoord, yCoord, zCoord, worldObj.provider.dimensionId);

			//Battery Item
			power = Library.chargeItemsFromTE(slots, 2, power, maxPower);
			
			boolean trigger = true;
			
			if(isItemValid() && this.burnTime == 0) {
				trigger = false;
			}

			if(trigger) {
				MachineCoal.updateBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
			
			generate();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("power", power);
			data.setInteger("burnTime", burnTime);
			tank.writeToNBT(data, "tank");
			this.networkPack(data, 15);
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.power = nbt.getLong("power");
		this.burnTime = nbt.getInteger("burnTime");
		this.tank.readFromNBT(nbt, "tank");
	}
	
	public void generate() {
		
		if(slots[1] != null && TileEntityFurnace.getItemBurnTime(slots[1]) > 0 && burnTime <= 0)
		{
			burnTime = (int) (TileEntityFurnace.getItemBurnTime(slots[1]) * fuelMod);
			slots[1].stackSize -= 1;
			if(slots[1].stackSize == 0)
			{
				if(slots[1].getItem().getContainerItem() != null)
					slots[1] = new ItemStack(slots[1].getItem().getContainerItem());
				else
					slots[1] = null;
			}
		}
		
		if(burnTime > 0) {
			burnTime--;

			if(worldObj.getTotalWorldTime() % 20 == 0) this.pollute(PollutionType.SOOT, PollutionHandler.SOOT_PER_SECOND);

			if(tank.getFill() > 0) {
				tank.setFill(tank.getFill() - 1);

				power += genRate;

				if(power > maxPower)
					power = maxPower;
			}
		}
	}
	
	public boolean isItemValid() {

		if(slots[1] != null && TileEntityFurnace.getItemBurnTime(slots[1]) > 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return new FluidTank[] {tank};
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return this.getSmokeTanks();
	}

	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public String getConfigName() {
		return "combustiongen";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		waterCap = IConfigurableMachine.grab(obj, "I:waterCapacity", waterCap);
		genRate = IConfigurableMachine.grab(obj, "I:powerGen", genRate);
		fuelMod = IConfigurableMachine.grab(obj, "D:burnTimeMod", fuelMod);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("I:waterCapacity").value(waterCap);
		writer.name("I:powerGen").value(genRate);
		writer.name("D:burnTimeMod").value(fuelMod);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineCoal(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineCoal(player.inventory, this);
	}
}
