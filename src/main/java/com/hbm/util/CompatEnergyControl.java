package com.hbm.util;

import api.hbm.energymk2.IBatteryItem;
import api.hbm.energymk2.IEnergyHandlerMK2;
import api.hbm.fluidmk2.IFluidUserMK2;
import api.hbm.tile.IInfoProviderEC;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;
import com.hbm.tileentity.machine.TileEntityMachineGasCent.PseudoFluidTank;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/** Provides data specified by EC's CrossModBase */
public class CompatEnergyControl {

	/** Returns the steel ingot to craft the sensor kit with */
	public static ItemStack getCraftingMaterial() {
		return new ItemStack(ModItems.ingot_steel); //in the event that i do end up moving all ingots to metadata
	}

	/** Returns true for stacks with electric items like batteries or powertools (i.e. implements IBatteryItem) */
	public static boolean isElectricItem(ItemStack stack) {
		return stack.getItem() instanceof IBatteryItem;
	}

	/** Standardized discharge for IBatteryItem, returns the amount that was removed */
	public static double dischargeItem(ItemStack stack, double needed) {
		IBatteryItem battery = (IBatteryItem) stack.getItem();
		long toDischarge = Math.min(battery.getDischargeRate(), Math.min(battery.getCharge(stack), (long) needed));
		battery.dischargeBattery(stack, toDischarge);
		return toDischarge;
	}

	/** Returns the power and maxPower values for IEnergyUser */
	public static void getEnergyData(TileEntity tile, NBTTagCompound data) {

		data.setString(KEY_EUTYPE, "HE");

		if(tile instanceof IEnergyHandlerMK2) {
			IEnergyHandlerMK2 user = (IEnergyHandlerMK2) tile;
			data.setDouble(L_ENERGY_HE, user.getPower());
			data.setDouble(L_CAPACITY_HE, user.getMaxPower());
		}
	}

	/** Returns the heat for RBMKs */
	public static int getHeat(TileEntity tile) {
		if(tile instanceof TileEntityRBMKBase) return (int) ((TileEntityRBMKBase) tile).heat;
		//original implementation also used the SNR and LNR for some reason, but those no longer exist. neither ZINOX nor research reactor were part of the system.
		return -1;
	}

	/** Returns a list of Object arrays, one array for each fluid tank where the array contains fluid name, fill state and capacity (STRING, INTEGER, INTEGER) */
	public static List<Object[]> getAllTanks(TileEntity tile) {

		List<Object[]> list = new ArrayList();

		if(tile instanceof IFluidUserMK2) {
			IFluidUserMK2 user = (IFluidUserMK2) tile;

			for(FluidTank tank : user.getAllTanks()) {
				if(tank.getTankType() == Fluids.SMOKE || tank.getTankType() == Fluids.SMOKE_LEADED || tank.getTankType() == Fluids.SMOKE_POISON) continue;
				list.add(toFluidInfo(tank));
			}
		}

		if(tile instanceof TileEntityMachineGasCent) {
			TileEntityMachineGasCent cent = (TileEntityMachineGasCent) tile;
			list.add(toFluidInfo(cent.inputTank));
			list.add(toFluidInfo(cent.outputTank));
		}

		if(!list.isEmpty()) return list;

		return null;
	}

	private static Object[] toFluidInfo(FluidTank tank) {
		return new Object[] {tank.getTankType().getName(), tank.getFill(), tank.getMaxFill()};
	}

	private static Object[] toFluidInfo(PseudoFluidTank tank) {
		return new Object[] {tank.getTankType().getName(), tank.getFill(), tank.getMaxFill()};
	}

	/** Returns any non-standard data like progress, unique stats and so forth. Data comes from the IInfoProviderEC implementation */
	public static void getExtraData(TileEntity tile, NBTTagCompound data) {

		if(tile instanceof IInfoProviderEC) {
			IInfoProviderEC provider = (IInfoProviderEC) tile;
			provider.provideExtraInfo(data);
		}
	}

	/** Returns the core tile entity for that position, can resolve the MK1 "IMultiblock" and MK2 "BlockDummyable" systems. */
	public static TileEntity findTileEntity(World world, int x, int y, int z) {
		return CompatExternal.getCoreFromPos(world, x, y, z); //CompatExternal you're just standing around, do something for once
	}

	/** Returns the ResourceLocation for the given fluid name */
	public static ResourceLocation getFluidTexture(String name) {
		FluidType type = Fluids.fromName(name);
		return type == null ? null : type.getTexture();
	}

	/*
	 * [DATA TYPE] _ [NAME] _ [UNIT]
	 */

	public static final String KEY_EUTYPE = "euType";

	public static final String L_ENERGY_HE =				"energy";
	public static final String L_ENERGY_TU =				"energyTU";
	public static final String L_ENERGY_ =					"energy_";			// Blast Furnace fuel

	public static final String L_CAPACITY_HE =				"capacity";
	public static final String L_CAPACITY_TU =				"capacityTU";
	public static final String L_CAPACITY_ =				"capacity_";		// Blast Furnace fuel capacity

	public static final String D_CONSUMPTION_HE =			"consumptionHE";
	public static final String D_CONSUMPTION_MB =			"consumption";
	@Deprecated public static final String S_CONSUMPTION_ =	"consumption_";		// FWatz fluid consumption rates

	public static final String D_OUTPUT_HE =				"output";
	public static final String D_OUTPUT_MB =				"outputmb";
	public static final String D_OUTPUT_TU =				"outputTU";

	public static final String L_DIFF_HE =					"diff";				// Battery diff per tick
	@Deprecated public static final String I_TEMP_K =		"temp";				// Unused?
	public static final String D_TURBINE_PERCENT =			"turbine";			// CCGT slider
	public static final String I_TURBINE_SPEED =			"speed";			// CCGT RPM
	public static final String L_COREHEAT_C =				"core";				// Research Reactor core heat
	public static final String L_HULLHEAT_C =				"hull";				// Research Reactor hull heat
	public static final String S_LEVEL_PERCENT =			"level";			// Research Reactor rods
	@Deprecated public static final String L_HEATL =		"heatL";			// AMS and old Watz heat values
	public static final String D_HEAT_C =					"heat";				// Research Reactor and RBMK column heat
	public static final String D_MAXHEAT_C =				"maxHeat";			// ZIRNOX melting temp
	public static final String L_PRESSURE_BAR =				"bar";				// ZIRNOX pressure
	public static final String L_FUEL =						"fuel";				// RTG Blast Furnace heat
	@Deprecated public static final String S_FUELTEXT =		"fuelText";			// Large Nuclear Reactor only
	@Deprecated public static final String S_DEPLETED =		"depleted";			// Large Nuclear Reactor only
	public static final String D_DEPLETION_PERCENT =		"depletion";		// RBMK Fuel depletion
	public static final String D_XENON_PERCENT =			"xenon";			// RBMK Fuel xenon poisoning
	public static final String D_SKIN_C =					"skin";				// RBMK Fuel skin heat
	public static final String D_CORE_C =					"c_heat";			// RBMK Fuel core heat
	public static final String D_MELT_C =					"melt";				// RBMK Fuel melting point
	public static final String I_PROGRESS =					"progress";
	public static final String I_FLUX =						"flux";				// Research and Breeding Reactor flux
	public static final String I_WATER =					"water";			// Research Reactor water gauge
	public static final String L_DURABILITY =				"durability";		// DFC Stabilizer Lens
	public static final String S_TANK =						"tank";
	public static final String S_TANK2 =					"tank2";
	public static final String S_TANK3 =					"tank3";
	public static final String S_TANK4 =					"tank4";
	public static final String S_TANK5 =					"tank5";
	@Deprecated public static final String I_PISTONS =		"pistons";			// Radial Performance Engine piston count
	public static final String S_CHUNKRAD =					"chunkRad";			// Geiger Counter
	public static final String B_ACTIVE =					"active";
}
