package api.hbm.tile;

import com.hbm.inventory.fluid.tank.FluidTank;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

/**
 * Info providers for ENERGY CONTROL
 * 
 * For EC's implementation, refer to:
 * https://github.com/Zuxelus/Energy-Control/blob/1.7.10/src/main/java/com/zuxelus/energycontrol/crossmod/CrossHBM.java
 * https://github.com/Zuxelus/Energy-Control/blob/1.7.10/src/main/java/com/zuxelus/energycontrol/items/cards/ItemCardHBM.java
 * https://github.com/Zuxelus/Energy-Control/blob/1.7.10/src/main/java/com/zuxelus/energycontrol/utils/DataHelper.java
 * 
 * (keys are from DataHelper.java and CrossHBM.java)
 * 
 *  */
public interface IInfoProviderEC {

	/** The meat of the interface and the only method that should be called from externally, returns
	 * an NBTTagCompound with all relevant data in EC's accepted format, the implementor takes care of
	 * collecting and adding the data. */
	public NBTTagCompound provideInfo();
	
	
	
	
	/*
	 * INTERNAL USE ONLY - HELPER METHODS BELOW
	 */
	
	/** Instantiates the NBTTagCompound and adds common identifiers needed for NTM machines (e.g. HE as the energy type) */
	public default NBTTagCompound setup() {
		NBTTagCompound data = new NBTTagCompound();
		data.setString(KEY_EUTYPE, "HE");
		return data;
	}
	
	/** Adds the tank to the NBTTagCompound using the supplied String as the key. */
	public default void addTank(String name, NBTTagCompound tag, FluidTank tank) {
		if(tank.getFill() == 0) {
			tag.setString(name, "N/A");
		} else {
			tag.setString(name, String.format("%s: %s mB", StatCollector.translateToLocal(tank.getTankType().getConditionalName()), tank.getFill()));
		}
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
	public static final String L_PRESSURE_BAR =				"bar";				// ZIRNOX pressure
	public static final String I_FUEL =						"fuel";				// RTG Blast Furnace heat
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
