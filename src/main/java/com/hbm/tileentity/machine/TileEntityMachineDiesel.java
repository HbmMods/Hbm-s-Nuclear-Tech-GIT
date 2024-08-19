package com.hbm.tileentity.machine;

import java.io.IOException;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.container.ContainerMachineDiesel;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.fluid.trait.FT_Combustible;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.inventory.fluid.trait.FluidTrait.FluidReleaseType;
import com.hbm.inventory.gui.GUIMachineDiesel;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConfigurableMachine;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachinePolluting;
import com.hbm.util.CompatEnergyControl;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.energymk2.IEnergyProviderMK2;
import api.hbm.fluid.IFluidStandardTransceiver;
import api.hbm.tile.IInfoProviderEC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineDiesel extends TileEntityMachinePolluting implements IEnergyProviderMK2, IFluidStandardTransceiver, IConfigurableMachine, IGUIProvider, IInfoProviderEC {

	public long power;
	public int soundCycle = 0;
	public long powerCap = maxPower;
	public FluidTank tank;
	
	/* CONFIGURABLE CONSTANTS */
	public static long maxPower = 50000;
	public static int fluidCap = 16000;
	public static HashMap<FuelGrade, Double> fuelEfficiency = new HashMap();
	static {
		fuelEfficiency.put(FuelGrade.MEDIUM,	0.5D);
		fuelEfficiency.put(FuelGrade.HIGH,		0.75D);
		fuelEfficiency.put(FuelGrade.AERO,		0.1D);
	}
	public static boolean shutUp = false;

	private static final int[] slots_top = new int[] { 0 };
	private static final int[] slots_bottom = new int[] { 1, 2 };
	private static final int[] slots_side = new int[] { 2 };

	public TileEntityMachineDiesel() {
		super(5, 100);
		tank = new FluidTank(Fluids.DIESEL, 4_000);
	}

	@Override
	public String getName() {
		return "container.machineDiesel";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if (i == 0)
			if (FluidContainerRegistry.getFluidContent(stack, tank.getTankType()) > 0)
				return true;
		if (i == 2)
			if (stack.getItem() instanceof IBatteryItem)
				return true;

		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("powerTime");
		this.powerCap = nbt.getLong("powerCap");
		tank.readFromNBT(nbt, "fuel");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setLong("powerTime", power);
		nbt.setLong("powerCap", powerCap);
		tank.writeToNBT(nbt, "fuel");
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_side);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		if(i == 1) {
			if(stack.getItem() == ModItems.canister_empty || stack.getItem() == ModItems.tank_steel) {
				return true;
			}
		}
		if(i == 2) {
			if(stack.getItem() instanceof IBatteryItem && ((IBatteryItem) stack.getItem()).getCharge(stack) == ((IBatteryItem) stack.getItem()).getMaxCharge()) {
				return true;
			}
		}

		return false;
	}

	public long getPowerScaled(long i) {
		return (power * i) / powerCap;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				this.tryProvide(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
				this.sendSmoke(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ, dir);
			}

			//Tank Management
			FluidType last = tank.getTankType();
			if(tank.setType(3, 4, slots)) this.unsubscribeToAllAround(last, this);
			tank.loadTank(0, 1, slots);
			
			this.subscribeToAllAround(tank.getTankType(), this);

			FluidType type = tank.getTankType();
			if(type == Fluids.NITAN)
				powerCap = maxPower * 10;
			else
				powerCap = maxPower;
			
			// Battery Item
			power = Library.chargeItemsFromTE(slots, 2, power, powerCap);

			generate();

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("power", (int) power);
			data.setInteger("powerCap", (int) powerCap);
			tank.writeToNBT(data, "t");
			this.networkPack(data, 50);
		}
	}
	
	public void networkUnpack(NBTTagCompound data) {
		super.networkUnpack(data);

		power = data.getInteger("power");
		powerCap = data.getInteger("powerCap");
		tank.readFromNBT(data, "t");
	}
	
	public boolean hasAcceptableFuel() {
		return getHEFromFuel() > 0;
	}
	
	public long getHEFromFuel() {
		return getHEFromFuel(tank.getTankType());
	}
	
	public static long getHEFromFuel(FluidType type) {
		
		if(type.hasTrait(FT_Combustible.class)) {
			FT_Combustible fuel = type.getTrait(FT_Combustible.class);
			FuelGrade grade = fuel.getGrade();
			double efficiency = fuelEfficiency.containsKey(grade) ? fuelEfficiency.get(grade) : 0;
			
			if(fuel.getGrade() != FuelGrade.LOW) {
				return (long) (fuel.getCombustionEnergy() / 1000L * efficiency);
			}
		}
		
		return 0;
	}

	public void generate() {
		
		if(hasAcceptableFuel()) {
			if (tank.getFill() > 0) {
				
				if(!shutUp) {
					if (soundCycle == 0) {
						this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "fireworks.blast", this.getVolume(0.75F), 0.5F);
					}
					soundCycle++;
				}

				if(soundCycle >= 3)
					soundCycle = 0;

				tank.setFill(tank.getFill() - 1);
				if(tank.getFill() < 0)
					tank.setFill(0);
				
				if(worldObj.getTotalWorldTime() % 5 == 0) {
					super.pollute(tank.getTankType(), FluidReleaseType.BURN, 5F);
				}

				if(power + getHEFromFuel() <= powerCap) {
					power += getHEFromFuel();
				} else {
					power = powerCap;
				}
			}
		}
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
	public FluidTank[] getAllTanks() {
		return new FluidTank[] { tank };
	}

	@Override
	public String getConfigName() {
		return "dieselgen";
	}

	@Override
	public void readIfPresent(JsonObject obj) {
		maxPower = IConfigurableMachine.grab(obj, "L:powerCap", maxPower);
		fluidCap = IConfigurableMachine.grab(obj, "I:fuelCap", fluidCap);
		
		if(obj.has("D[:efficiency")) {
			JsonArray array = obj.get("D[:efficiency").getAsJsonArray();
			for(FuelGrade grade : FuelGrade.values()) {
				fuelEfficiency.put(grade, array.get(grade.ordinal()).getAsDouble());
			}
		}
		shutUp = IConfigurableMachine.grab(obj, "B:shutUp", shutUp);
	}

	@Override
	public void writeConfig(JsonWriter writer) throws IOException {
		writer.name("L:powerCap").value(maxPower);
		writer.name("I:fuelCap").value(fluidCap);
		
		String info = "Fuel grades in order: ";
		for(FuelGrade grade : FuelGrade.values()) info += grade.name() + " ";
		info = info.trim();
		writer.name("INFO").value(info);
		
		writer.name("D[:efficiency").beginArray().setIndent("");
		for(FuelGrade grade : FuelGrade.values()) {
			double d = fuelEfficiency.containsKey(grade) ? fuelEfficiency.get(grade) : 0.0D;
			writer.value(d);
		}
		writer.endArray().setIndent("  ");
		writer.name("B:shutUp").value(shutUp);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineDiesel(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineDiesel(player.inventory, this);
	}

	@Override
	public FluidTank[] getSendingTanks() {
		return this.getSmokeTanks();
	}

	@Override
	public void provideExtraInfo(NBTTagCompound data) {
		long he = getHEFromFuel(tank.getTankType());
		boolean active = tank.getFill() > 0 && he > 0;
		data.setBoolean(CompatEnergyControl.B_ACTIVE, active);
		data.setDouble(CompatEnergyControl.D_CONSUMPTION_MB, active ? 1D : 0D);
		data.setDouble(CompatEnergyControl.D_OUTPUT_HE, he);
	}
}
