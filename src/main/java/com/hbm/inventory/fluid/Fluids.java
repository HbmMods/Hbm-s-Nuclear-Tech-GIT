package com.hbm.inventory.fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.inventory.fluid.FluidType.ExtContainer;
import com.hbm.inventory.fluid.FluidType.FluidTrait;
import com.hbm.inventory.fluid.types.*;
import com.hbm.inventory.fluid.types.FluidTypeCombustible.FuelGrade;
import com.hbm.render.util.EnumSymbol;

public class Fluids {

	public static FluidType NONE;
	public static FluidType WATER;
	public static FluidType STEAM;
	public static FluidType HOTSTEAM;
	public static FluidType SUPERHOTSTEAM;
	public static FluidType ULTRAHOTSTEAM;
	public static FluidType COOLANT;
	public static FluidType COOLANT_HOT;
	public static FluidType LAVA;
	public static FluidType DEUTERIUM;
	public static FluidType TRITIUM;
	public static FluidType OIL;
	public static FluidType CRACKOIL;
	public static FluidType COALOIL;
	public static FluidType HOTOIL;
	public static FluidType HOTCRACKOIL;
	public static FluidType HEAVYOIL;
	public static FluidType BITUMEN;
	public static FluidType SMEAR;
	public static FluidType HEATINGOIL;
	public static FluidType RECLAIMED;
	public static FluidType LUBRICANT;
	public static FluidType NAPHTHA;
	public static FluidType NAPHTHA_CRACK;
	public static FluidType DIESEL;
	public static FluidType DIESEL_CRACK;
	public static FluidType LIGHTOIL;
	public static FluidType LIGHTOIL_CRACK;
	public static FluidType KEROSENE;
	public static FluidType GAS;
	public static FluidType PETROLEUM;
	public static FluidType LPG;
	public static FluidType AROMATICS;			//anything from benzene to phenol and toluene
	public static FluidType UNSATURATEDS;		//collection of various basic unsaturated compounds like ethylene, acetylene and whatnot
	public static FluidType BIOGAS;
	public static FluidType BIOFUEL;
	public static FluidType NITAN;
	public static FluidType UF6;
	public static FluidType PUF6;
	public static FluidType SAS3;
	public static FluidType SCHRABIDIC;
	public static FluidType AMAT;
	public static FluidType ASCHRAB;
	public static FluidType ACID;
	public static FluidType WATZ;
	public static FluidType CRYOGEL;
	public static FluidType HYDROGEN;
	public static FluidType OXYGEN;
	public static FluidType XENON;
	public static FluidType BALEFIRE;
	public static FluidType MERCURY;
	public static FluidType PAIN;				//tantalite solution
	public static FluidType WASTEFLUID;
	public static FluidType WASTEGAS;
	public static FluidType PETROIL;
	public static FluidType PETROIL_LEADED;
	public static FluidType GASOLINE;
	public static FluidType GASOLINE_LEADED;
	public static FluidType COALGAS;			//coal-based gasoline
	public static FluidType COALGAS_LEADED;
	public static FluidType SPENTSTEAM;
	public static FluidType FRACKSOL;
	public static FluidType PLASMA_DT;
	public static FluidType PLASMA_HD;
	public static FluidType PLASMA_HT;
	public static FluidType PLASMA_DH3;
	public static FluidType PLASMA_XM;
	public static FluidType PLASMA_BF;
	public static FluidType CARBONDIOXIDE;
	public static FluidType HELIUM3;
	public static FluidType DEATH;				//osmiridium solution
	public static FluidType ETHANOL;
	public static FluidType HEAVYWATER;
	public static FluidType SALIENT;
	public static FluidType XPJUICE;
	public static FluidType ENDERJUICE;
	public static FluidType SULFURIC_ACID;
	public static FluidType MUG;
	public static FluidType MUG_HOT;

	private static final HashMap<Integer, FluidType> idMapping = new HashMap();
	private static final HashMap<String, FluidType> nameMapping = new HashMap();
	protected static final List<FluidType> metaOrder = new ArrayList();
	
	public static void init() {
		
		// ##### ##### ##### ##### ##  # ##### #   # ##### ##  # #####
		// #   #   #     #   #     ##  # #     #   # #   # ##  # #
		// #####   #     #   ###   # # # ##### ##### #   # # # # ###
		// #   #   #     #   #     #  ##     # #   # #   # #  ## #
		// #   #   #     #   ##### #  ## ##### #   # ##### #  ## #####
		
		/*
		 * The mapping ID is set in the CTOR, which is the static, never shifting ID that is used to save the fluid type.
		 * Therefore, ALWAYS append new fluid entries AT THE BOTTOM to avoid unnecessary ID shifting.
		 * In addition, you have to add your fluid to 'metaOrder' which is what is used to sort fluid identifiers and whatnot in the inventory.
		 * You may screw with metaOrder as much as you like, as long as you keep all fluids in the list exactly once.
		 */
		
		NONE =				new FluidType(				"NONE",				0x888888, 0, 0, 0, EnumSymbol.NONE);
		WATER =				new FluidType(				"WATER",			0x3333FF, 0, 0, 0, EnumSymbol.NONE);
		STEAM =				new Gas(					"STEAM",			0xe5e5e5, 3, 0, 0, EnumSymbol.NONE).setTemp(100).setCompression(0.01D);
		HOTSTEAM =			new Gas(					"HOTSTEAM",			0xE7D6D6, 4, 0, 0, EnumSymbol.NONE).setTemp(300).setCompression(0.1D);
		SUPERHOTSTEAM =		new Gas(					"SUPERHOTSTEAM",	0xE7B7B7, 4, 0, 0, EnumSymbol.NONE).setTemp(450).setCompression(1D);
		ULTRAHOTSTEAM =		new Gas(					"ULTRAHOTSTEAM",	0xE39393, 4, 0, 0, EnumSymbol.NONE).setTemp(600).setCompression(10D);
		COOLANT =			new FluidType(				"COOLANT",			0xd8fcff, 1, 0, 0, EnumSymbol.NONE).setHeatCap(0.25D);
		LAVA =				new FluidType(				"LAVA",				0xFF3300, 4, 0, 0, EnumSymbol.NOWATER).setTemp(1200);
		DEUTERIUM =			new CombustibleGas(			"DEUTERIUM",		0x0000FF, 3, 4, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.HIGH, 10_000).setHeatEnergy(5_000);
		TRITIUM =			new CombustibleGas(			"TRITIUM",			0x000099, 3, 4, 0, EnumSymbol.RADIATION).setCombustionEnergy(FuelGrade.HIGH, 10_000).setHeatEnergy(5_000);
		OIL =				new Oil(					"OIL",				0x020202, 2, 1, 0, EnumSymbol.NONE).addContainers(0x424242, ExtContainer.CANISTER);
		HOTOIL =			new Oil(					"HOTOIL",			0x300900, 2, 3, 0, EnumSymbol.NONE).setTemp(350);
		HEAVYOIL =			new Oil(					"HEAVYOIL",			0x141312, 2, 1, 0, EnumSymbol.NONE).addContainers(0x513F39, ExtContainer.CANISTER);
		BITUMEN =			new Petrochemical(			"BITUMEN",			0x1f2426, 2, 0, 0, EnumSymbol.NONE).addContainers(0x5A5877, ExtContainer.CANISTER);
		SMEAR =				new Oil(					"SMEAR",			0x190f01, 2, 1, 0, EnumSymbol.NONE).setHeatEnergy(50_000).addContainers(0x624F3B, ExtContainer.CANISTER);
		HEATINGOIL =		new Fuel(					"HEATINGOIL",		0x211806, 2, 2, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.LOW, 100_000).setHeatEnergy(150_000).addContainers(0x694235, ExtContainer.CANISTER);
		RECLAIMED =			new Fuel(					"RECLAIMED",		0x332b22, 2, 2, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.LOW, 200_000).setHeatEnergy(100_000).addContainers(0xF65723, ExtContainer.CANISTER);
		PETROIL =			new Fuel(					"PETROIL",			0x44413d, 1, 3, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.MEDIUM, 300_000).setHeatEnergy(125_000).addContainers(0x2369F6, ExtContainer.CANISTER);
		LUBRICANT =			new Petrochemical(			"LUBRICANT",		0x606060, 2, 1, 0, EnumSymbol.NONE).addContainers(0xF1CC05, ExtContainer.CANISTER);
		NAPHTHA =			new Oil(					"NAPHTHA",			0x595744, 2, 1, 0, EnumSymbol.NONE).addContainers(0x5F6D44, ExtContainer.CANISTER);
		DIESEL =			new Fuel(					"DIESEL",			0xf2eed5, 1, 2, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.HIGH, 500_000).setHeatEnergy(200_000).addContainers(0xFF2C2C, ExtContainer.CANISTER);
		LIGHTOIL =			new Oil(					"LIGHTOIL",			0x8c7451, 1, 2, 0, EnumSymbol.NONE).addContainers(0xB46B52, ExtContainer.CANISTER);
		KEROSENE =			new Fuel(					"KEROSENE",			0xffa5d2, 1, 2, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.AERO, 1_250_000).setHeatEnergy(300_000).addContainers(0xFF377D, ExtContainer.CANISTER);
		GAS =				new FlammableOilGas(		"GAS",				0xfffeed, 1, 4, 1, EnumSymbol.NONE).setHeatEnergy(10_000);
		PETROLEUM = 		new FlammableOilGas(		"PETROLEUM",		0x7cb7c9, 1, 4, 1, EnumSymbol.NONE).setHeatEnergy(25_000);
		LPG =				new Fuel(					"LPG",				0x4747EA, 1, 3, 1, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.HIGH, 450_000).setHeatEnergy(200_000);
		BIOGAS =			new FlammableOilGas(		"BIOGAS",			0xbfd37c, 1, 4, 1, EnumSymbol.NONE).setHeatEnergy(25_000);
		BIOFUEL =			new Fuel(					"BIOFUEL",			0xeef274, 1, 2, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.HIGH, 400_000).setHeatEnergy(150_000).addContainers(0x9EB623, ExtContainer.CANISTER);
		NITAN =				new Fuel(					"NITAN",			0x8018ad, 2, 4, 1, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.HIGH, 5_000_000).setHeatEnergy(2_000_000).addContainers(0x6B238C, ExtContainer.CANISTER);
		UF6 =				new RadioactiveGas(			"UF6",				0xD1CEBE, 4, 0, 2, EnumSymbol.RADIATION).addTraits(FluidTrait.CORROSIVE);
		PUF6 =				new RadioactiveGas(			"PUF6",				0x4C4C4C, 4, 0, 4, EnumSymbol.RADIATION).addTraits(FluidTrait.CORROSIVE);
		SAS3 =				new FluidType(				"SAS3",				0x4ffffc, 5, 0, 4, EnumSymbol.RADIATION).addTraits(FluidTrait.CORROSIVE);
		SCHRABIDIC =		new FluidType(				"SCHRABIDIC",		0x006B6B, 5, 0, 5, EnumSymbol.ACID).addTraits(FluidTrait.CORROSIVE_2);
		AMAT =				new Antimatter(				"AMAT",				0x010101, 5, 0, 5, EnumSymbol.ANTIMATTER);
		ASCHRAB =			new Antimatter(				"ASCHRAB",			0xb50000, 5, 0, 5, EnumSymbol.ANTIMATTER);
		ACID =				new FluidType(				"ACID",				0xfff7aa, 3, 0, 3, EnumSymbol.OXIDIZER).addTraits(FluidTrait.CORROSIVE);
		WATZ =				new FluidType(				"WATZ",				0x86653E, 4, 0, 3, EnumSymbol.ACID).addTraits(FluidTrait.CORROSIVE_2);
		CRYOGEL =			new FluidType(				"CRYOGEL",			0x32ffff, 2, 0, 0, EnumSymbol.CROYGENIC).setTemp(-170);
		HYDROGEN =			new FluidTypeCombustible(	"HYDROGEN",			0x4286f4, 3, 4, 0, EnumSymbol.CROYGENIC).setCombustionEnergy(FuelGrade.HIGH, 10_000).setHeatEnergy(5_000).addTraits(FluidTrait.LIQUID);
		OXYGEN =			new FluidType(				"OXYGEN",			0x98bdf9, 3, 0, 0, EnumSymbol.CROYGENIC);
		XENON =				new FluidType(				"XENON",			0xba45e8, 0, 0, 0, EnumSymbol.ASPHYXIANT);
		BALEFIRE =			new FluidType(				"BALEFIRE",			0x28e02e, 4, 4, 3, EnumSymbol.RADIATION).setTemp(1500).addTraits(FluidTrait.CORROSIVE);
		MERCURY =			new FluidType(				"MERCURY",			0x808080, 2, 0, 0, EnumSymbol.NONE);
		PAIN =				new FluidType(				"PAIN",				0x938541, 2, 0, 1, EnumSymbol.ACID).setTemp(300).addTraits(FluidTrait.CORROSIVE);
		WASTEFLUID =		new RadioactiveLiquid(		"WASTEFLUID",		0x544400, 2, 0, 1, EnumSymbol.RADIATION).setRadiation(0.5F).addTraits(FluidTrait.NO_CONTAINER);
		WASTEGAS =			new RadioactiveGas(			"WASTEGAS",			0xB8B8B8, 2, 0, 1, EnumSymbol.RADIATION).setRadiation(0.5F).addTraits(FluidTrait.NO_CONTAINER);
		GASOLINE =			new Fuel(					"GASOLINE",			0x445772, 1, 2, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.HIGH, 1_000_000).setHeatEnergy(400_000).addContainers(0x2F7747, ExtContainer.CANISTER);
		COALGAS =			new Fuel(					"COALGAS",			0x445772, 1, 2, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.MEDIUM, 150_000).setHeatEnergy(75_000).addContainers(0x2E155F, ExtContainer.CANISTER);
		SPENTSTEAM =		new Gas(					"SPENTSTEAM",		0x445772, 2, 0, 0, EnumSymbol.NONE).addTraits(FluidTrait.NO_CONTAINER).setCompression(1D);
		FRACKSOL =			new Petrochemical(			"FRACKSOL",			0x798A6B, 1, 3, 3, EnumSymbol.ACID).addTraits(FluidTrait.CORROSIVE).addContainers(0x4F887F, ExtContainer.CANISTER);
		PLASMA_DT =			new FluidType(				"PLASMA_DT",		0xF7AFDE, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3250).addTraits(FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_HD =			new FluidType(				"PLASMA_HD",		0xF0ADF4, 0, 4, 0, EnumSymbol.RADIATION).setTemp(2500).addTraits(FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_HT =			new FluidType(				"PLASMA_HT",		0xD1ABF2, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3000).addTraits(FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_XM =			new FluidType(				"PLASMA_XM",		0xC6A5FF, 0, 4, 1, EnumSymbol.RADIATION).setTemp(4250).addTraits(FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_BF =			new FluidType(				"PLASMA_BF",		0xA7F1A3, 4, 5, 4, EnumSymbol.ANTIMATTER).setTemp(8500).addTraits(FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		CARBONDIOXIDE =		new Gas(					"CARBONDIOXIDE",	0x404040, 3, 0, 0, EnumSymbol.ASPHYXIANT);
		PLASMA_DH3 =		new FluidType(				"PLASMA_DH3",		0xFF83AA, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3480).addTraits(FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		HELIUM3 =			new Gas(					"HELIUM3",			0xFCF0C4, 3, 4, 0, EnumSymbol.ASPHYXIANT);
		DEATH =				new FluidType(				"DEATH",			0x717A88, 2, 0, 1, EnumSymbol.ACID).setTemp(300).addTraits(FluidTrait.CORROSIVE_2, FluidTrait.LEAD_CONTAINER);
		ETHANOL =			new Fuel(					"ETHANOL",			0xe0ffff, 2, 3, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.HIGH, 200_000).setHeatEnergy(75_000).addContainers(0xEAFFF3, ExtContainer.CANISTER);
		HEAVYWATER =		new FluidType(				"HEAVYWATER",		0x00a0b0, 1, 0, 0, EnumSymbol.NONE);
		CRACKOIL =			new Oil(					"CRACKOIL",			0x020202, 2, 1, 0, EnumSymbol.NONE);
		COALOIL =			new Oil(					"COALOIL",			0x020202, 2, 1, 0, EnumSymbol.NONE);
		HOTCRACKOIL =		new Oil(					"HOTCRACKOIL",		0x300900, 2, 3, 0, EnumSymbol.NONE).setTemp(350);
		NAPHTHA_CRACK =		new Oil(					"NAPHTHA_CRACK",	0x595744, 2, 1, 0, EnumSymbol.NONE);
		LIGHTOIL_CRACK =	new Oil(					"LIGHTOIL_CRACK",	0x8c7451, 1, 2, 0, EnumSymbol.NONE);
		DIESEL_CRACK =		new Fuel(					"DIESEL_CRACK",		0xf2eed5, 1, 2, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.HIGH, 450_000).setHeatEnergy(200_000);
		AROMATICS =			new Oil(					"AROMATICS",		0x68A09A, 1, 4, 1, EnumSymbol.NONE);
		UNSATURATEDS =		new Oil(					"UNSATURATEDS",		0x628FAE, 1, 4, 1, EnumSymbol.NONE);
		SALIENT =			new FluidType(				"SALIENT",			0x457F2D, 0, 0, 0, EnumSymbol.NONE);
		XPJUICE =			new FluidType(				"XPJUICE",			0xBBFF09, 0, 0, 0, EnumSymbol.NONE);
		ENDERJUICE =		new FluidType(				"ENDERJUICE",		0x127766, 0, 0, 0, EnumSymbol.NONE);
		PETROIL_LEADED =	new Fuel(					"PETROIL_LEADED",	0x44413d, 1, 3, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.MEDIUM, 450_000).setHeatEnergy(((FluidTypeFlammable)PETROIL).getHeatEnergy()).addContainers(0x2331F6, ExtContainer.CANISTER);
		GASOLINE_LEADED =	new Fuel(					"GASOLINE_LEADED",	0x445772, 1, 2, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.HIGH, 1_500_000).setHeatEnergy(((FluidTypeFlammable)GASOLINE).getHeatEnergy()).addContainers(0x2F775A, ExtContainer.CANISTER);
		COALGAS_LEADED =	new Fuel(					"COALGAS_LEADED",	0x445772, 1, 2, 0, EnumSymbol.NONE).setCombustionEnergy(FuelGrade.MEDIUM, 250_000).setHeatEnergy(((FluidTypeFlammable)COALGAS).getHeatEnergy()).addContainers(0x1E155F, ExtContainer.CANISTER);
		SULFURIC_ACID =		new FluidType(				"SULFURIC_ACID",	0xB0AA64, 3, 0, 2, EnumSymbol.ACID).addTraits(FluidTrait.CORROSIVE);
		COOLANT_HOT =		new FluidType(				"COOLANT_HOT",		0x99525E, 1, 0, 0, EnumSymbol.NONE).setTemp(600).setHeatCap(COOLANT.heatCap);
		MUG =				new FluidType(				"MUG",				0x4B2D28, 0, 0, 0, EnumSymbol.NONE).setHeatCap(1D);
		MUG_HOT =			new FluidType(78,			"MUG_HOT",			0x6B2A20, 0, 0, 0, EnumSymbol.NONE).setHeatCap(MUG.heatCap).setTemp(500);
		
		
		// ^ ^ ^ ^ ^ ^ ^ ^
		//ADD NEW FLUIDS HERE
		//AND DON'T FORGET THE META DOWN HERE
		// V V V V V V V V
		
		//null
		metaOrder.add(NONE);
		//vanilla
		metaOrder.add(WATER);
		metaOrder.add(HEAVYWATER);
		metaOrder.add(LAVA);
		//steams
		metaOrder.add(STEAM);
		metaOrder.add(HOTSTEAM);
		metaOrder.add(SUPERHOTSTEAM);
		metaOrder.add(ULTRAHOTSTEAM);
		metaOrder.add(SPENTSTEAM);
		//coolants
		metaOrder.add(CARBONDIOXIDE);
		metaOrder.add(COOLANT);
		metaOrder.add(COOLANT_HOT);
		metaOrder.add(CRYOGEL);
		metaOrder.add(MUG);
		metaOrder.add(MUG_HOT);
		//pure elements, cyogenic gasses
		metaOrder.add(HYDROGEN);
		metaOrder.add(DEUTERIUM);
		metaOrder.add(TRITIUM);
		metaOrder.add(HELIUM3);
		metaOrder.add(OXYGEN);
		metaOrder.add(XENON);
		metaOrder.add(MERCURY);
		//oils, fuels
		metaOrder.add(OIL);
		metaOrder.add(CRACKOIL);
		metaOrder.add(COALOIL);
		metaOrder.add(HOTOIL);
		metaOrder.add(HOTCRACKOIL);
		metaOrder.add(HEAVYOIL);
		metaOrder.add(NAPHTHA);
		metaOrder.add(NAPHTHA_CRACK);
		metaOrder.add(LIGHTOIL);
		metaOrder.add(LIGHTOIL_CRACK);
		metaOrder.add(BITUMEN);
		metaOrder.add(SMEAR);
		metaOrder.add(HEATINGOIL);
		metaOrder.add(RECLAIMED);
		metaOrder.add(LUBRICANT);
		metaOrder.add(GAS);
		metaOrder.add(PETROLEUM);
		metaOrder.add(LPG);
		metaOrder.add(AROMATICS);
		metaOrder.add(UNSATURATEDS);
		metaOrder.add(DIESEL);
		metaOrder.add(DIESEL_CRACK);
		metaOrder.add(KEROSENE);
		metaOrder.add(PETROIL);
		metaOrder.add(PETROIL_LEADED);
		metaOrder.add(GASOLINE);
		metaOrder.add(GASOLINE_LEADED);
		metaOrder.add(COALGAS);
		metaOrder.add(COALGAS_LEADED);
		metaOrder.add(BIOGAS);
		metaOrder.add(BIOFUEL);
		metaOrder.add(ETHANOL);
		metaOrder.add(NITAN);
		metaOrder.add(BALEFIRE);
		//processing fluids
		metaOrder.add(SALIENT);
		metaOrder.add(ACID);
		metaOrder.add(SULFURIC_ACID);
		//NITRIC_ACID
		metaOrder.add(SCHRABIDIC);
		metaOrder.add(UF6);
		metaOrder.add(PUF6);
		metaOrder.add(SAS3);
		metaOrder.add(PAIN);
		metaOrder.add(DEATH);
		metaOrder.add(WATZ);
		//solutions and working fluids
		metaOrder.add(FRACKSOL);
		//antimatter
		metaOrder.add(AMAT);
		metaOrder.add(ASCHRAB);
		//nuclear waste
		metaOrder.add(WASTEFLUID);
		metaOrder.add(WASTEGAS);
		//garbage
		metaOrder.add(XPJUICE);
		metaOrder.add(ENDERJUICE);
		//plasma
		metaOrder.add(PLASMA_DT);
		metaOrder.add(PLASMA_HD);
		metaOrder.add(PLASMA_HT);
		metaOrder.add(PLASMA_DH3);
		metaOrder.add(PLASMA_XM);
		metaOrder.add(PLASMA_BF);
		
		if(idMapping.size() != metaOrder.size()) {
			throw new IllegalStateException("A severe error has occoured during NTM's fluid registering process! The MetaOrder and Mappings are inconsistent! Mapping size: " + idMapping.size()+ " / MetaOrder size: " + metaOrder.size());
		}
	}
	
	protected static int registerSelf(FluidType fluid) {
		int id = idMapping.size();
		idMapping.put(id, fluid);
		nameMapping.put(fluid.getName(), fluid);
		return id;
	}
	
	public static FluidType fromID(int id) {
		FluidType fluid = idMapping.get(id);
		
		if(fluid == null)
			fluid = Fluids.NONE;
		
		return fluid;
	}
	
	public static FluidType fromName(String name) {
		FluidType fluid = nameMapping.get(name);
		
		if(fluid == null)
			fluid = Fluids.NONE;
		
		return fluid;
	}
	
	public static FluidType[] getAll() {
		return getInOrder(false);
	}
	
	public static FluidType[] getInNiceOrder() {
		return getInOrder(true);
	}
	
	private static FluidType[] getInOrder(final boolean nice) {
		FluidType[] all = new FluidType[idMapping.size()];
		
		for(int i = 0; i < all.length; i++) {
			FluidType type = nice ? metaOrder.get(i) : idMapping.get(i);
			
			if(type == null) {
				throw new IllegalStateException("A severe error has occoured with NTM's fluid system! Fluid of the ID " + i + " has returned NULL in the registry!");
			}
			
			all[i] = type;
		}
		
		return all;
	}
}