package com.hbm.inventory.fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.inventory.fluid.FluidType.FluidTrait;
import com.hbm.render.util.EnumSymbol;

public class Fluids {

	public static FluidType NONE;
	public static FluidType WATER;
	public static FluidType STEAM;
	public static FluidType HOTSTEAM;
	public static FluidType SUPERHOTSTEAM;
	public static FluidType ULTRAHOTSTEAM;
	public static FluidType COOLANT;
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
	public static FluidType PETROIL;
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
	public static FluidType GASOLINE;			//gasoline, leaded
	public static FluidType COALGAS;			//coal-based gasoline
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
		
		NONE =				new FluidType("NONE",0x888888, 0, 0, 0, EnumSymbol.NONE, "hbmfluid.none");
		WATER =				new FluidType("WATER",0x3333FF, 0, 0, 0, EnumSymbol.NONE, "hbmfluid.water");
		STEAM =				new FluidType("STEAM",0xe5e5e5, 3, 0, 0, EnumSymbol.NONE, "hbmfluid.steam", 100);
		HOTSTEAM =			new FluidType("HOTSTEAM",0xE7D6D6,  4, 0, 0, EnumSymbol.NONE, "hbmfluid.hotsteam", 300);
		SUPERHOTSTEAM =		new FluidType("SUPERHOTSTEAM",0xE7B7B7, 4, 0, 0, EnumSymbol.NONE, "hbmfluid.superhotsteam", 450);
		ULTRAHOTSTEAM =		new FluidType("ULTRAHOTSTEAM",0xE39393, 4, 0, 0, EnumSymbol.NONE, "hbmfluid.ultrahotsteam", 600);
		COOLANT =			new FluidType("COOLANT",0xd8fcff, 1, 0, 0, EnumSymbol.NONE, "hbmfluid.coolant");
		LAVA =				new FluidType("LAVA",0xFF3300, 4, 0, 0, EnumSymbol.NOWATER, "hbmfluid.lava", 1200);
		DEUTERIUM =			new FluidTypeCombustible("DEUTERIUM",0x0000FF, 3, 4, 0, EnumSymbol.NONE, "hbmfluid.deuterium");
		TRITIUM =			new FluidTypeCombustible("TRITIUM",0x000099, 3, 4, 0, EnumSymbol.RADIATION, "hbmfluid.tritium");
		OIL =				new FluidTypeFlammable("OIL",0x020202, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.oil");
		HOTOIL =			new FluidTypeFlammable("HOTOIL",0x300900, 2, 3, 0, EnumSymbol.NONE, "hbmfluid.hotoil", 350);
		HEAVYOIL =			new FluidTypeFlammable("HEAVYOIL",0x141312, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.heavyoil");
		BITUMEN =			new FluidType("BITUMEN",0x1f2426, 2, 0, 0, EnumSymbol.NONE, "hbmfluid.bitumen");
		SMEAR =				new FluidTypeFlammable("SMEAR",0x190f01, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.smear");
		HEATINGOIL =		new FluidTypeCombustible("HEATINGOIL",0x211806, 2, 2, 0, EnumSymbol.NONE, "hbmfluid.heatingoil");
		RECLAIMED =			new FluidTypeCombustible("RECLAIMED",0x332b22, 2, 2, 0, EnumSymbol.NONE, "hbmfluid.reclaimed");
		PETROIL =			new FluidTypeCombustible("PETROIL",0x44413d, 1, 3, 0, EnumSymbol.NONE, "hbmfluid.petroil");
		LUBRICANT =			new FluidType("LUBRICANT",0x606060, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.lubricant");
		NAPHTHA =			new FluidTypeFlammable("NAPHTHA",0x595744, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.naphtha");
		DIESEL =			new FluidTypeCombustible("DIESEL",0xf2eed5, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.diesel");
		LIGHTOIL =			new FluidTypeCombustible("LIGHTOIL",0x8c7451, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.lightoil");
		KEROSENE =			new FluidTypeCombustible("KEROSENE",0xffa5d2, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.kerosene");
		GAS =				new FluidTypeFlammable("GAS",0xfffeed, 1, 4, 1, EnumSymbol.NONE, "hbmfluid.gas");
		PETROLEUM = 		new FluidTypeFlammable("PETROLEUM",0x7cb7c9, 1, 4, 1, EnumSymbol.NONE, "hbmfluid.petroleum");
		LPG =				new FluidTypeCombustible("LPG",0x4747EA, 1, 3, 1, EnumSymbol.NONE, "hbmfluid.lpg");
		BIOGAS =			new FluidTypeFlammable("BIOGAS",0xbfd37c, 1, 4, 1, EnumSymbol.NONE, "hbmfluid.biogas");
		BIOFUEL =			new FluidTypeCombustible("BIOFUEL",0xeef274, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.biofuel");
		NITAN =				new FluidTypeCombustible("NITAN",0x8018ad, 2, 4, 1, EnumSymbol.NONE, "hbmfluid.nitan");
		UF6 =				new FluidType("UF6",0xD1CEBE, 4, 0, 2, EnumSymbol.RADIATION, "hbmfluid.uf6", FluidTrait.CORROSIVE);
		PUF6 =				new FluidType("PUF6",0x4C4C4C, 4, 0, 4, EnumSymbol.RADIATION, "hbmfluid.puf6", FluidTrait.CORROSIVE);
		SAS3 =				new FluidType("SAS3",0x4ffffc,  5, 0, 4, EnumSymbol.RADIATION, "hbmfluid.sas3", FluidTrait.CORROSIVE);
		SCHRABIDIC =		new FluidType("SCHRABIDIC",0x006B6B, 5, 0, 5, EnumSymbol.ACID, "hbmfluid.schrabidic", FluidTrait.CORROSIVE_2);
		AMAT =				new FluidType("AMAT",0x010101, 5, 0, 5, EnumSymbol.ANTIMATTER, "hbmfluid.amat", FluidTrait.AMAT);
		ASCHRAB =			new FluidType("ASCHRAB",0xb50000, 5, 0, 5, EnumSymbol.ANTIMATTER, "hbmfluid.aschrab", FluidTrait.AMAT);
		ACID =				new FluidType("ACID",0xfff7aa, 3, 0, 3, EnumSymbol.OXIDIZER, "hbmfluid.acid", FluidTrait.CORROSIVE);
		WATZ =				new FluidType("WATZ",0x86653E, 4, 0, 3, EnumSymbol.ACID, "hbmfluid.watz", FluidTrait.CORROSIVE_2);
		CRYOGEL =			new FluidType("CRYOGEL",0x32ffff, 2, 0, 0, EnumSymbol.CROYGENIC, "hbmfluid.cryogel", -170);
		HYDROGEN =			new FluidTypeCombustible("HYDROGEN",0x4286f4, 3, 4, 0, EnumSymbol.CROYGENIC, "hbmfluid.hydrogen");
		OXYGEN =			new FluidType("OXYGEN",0x98bdf9, 3, 0, 0, EnumSymbol.CROYGENIC, "hbmfluid.oxygen");
		XENON =				new FluidType("XENON",0xba45e8, 0, 0, 0, EnumSymbol.ASPHYXIANT, "hbmfluid.xenon");
		BALEFIRE =			new FluidType("BALEFIRE",0x28e02e, 4, 4, 3, EnumSymbol.RADIATION, "hbmfluid.balefire", 1500, FluidTrait.CORROSIVE);
		MERCURY =			new FluidType("MERCURY",0x808080, 2, 0, 0, EnumSymbol.NONE, "hbmfluid.mercury");
		PAIN =				new FluidType("PAIN",0x938541, 2, 0, 1, EnumSymbol.ACID, "hbmfluid.pain", 300, FluidTrait.CORROSIVE);
		WASTEFLUID =		new FluidType("WASTEFLUID",0x544400, 2, 0, 1, EnumSymbol.RADIATION, "hbmfluid.wastefluid", FluidTrait.NO_CONTAINER);
		WASTEGAS =			new FluidType("WASTEGAS",0xB8B8B8, 2, 0, 1, EnumSymbol.RADIATION, "hbmfluid.wastegas", FluidTrait.NO_CONTAINER);
		GASOLINE =			new FluidTypeCombustible("GASOLINE",0x445772, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.gasoline");
		COALGAS =			new FluidTypeCombustible("COALGAS",0x445772, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.coalgas");
		SPENTSTEAM =		new FluidType("SPENTSTEAM",0x445772, 2, 0, 0, EnumSymbol.NONE, "hbmfluid.spentsteam", FluidTrait.NO_CONTAINER);
		FRACKSOL =			new FluidType("FRACKSOL",0x798A6B, 1, 3, 3, EnumSymbol.ACID, "hbmfluid.fracksol", FluidTrait.CORROSIVE);
		PLASMA_DT =			new FluidType("PLASMA_DT",0xF7AFDE, 0, 4, 0, EnumSymbol.RADIATION, "hbmfluid.plasma_dt", 3250, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_HD =			new FluidType("PLASMA_HD",0xF0ADF4, 0, 4, 0, EnumSymbol.RADIATION, "hbmfluid.plasma_hd", 2500, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_HT =			new FluidType("PLASMA_HT",0xD1ABF2, 0, 4, 0, EnumSymbol.RADIATION, "hbmfluid.plasma_ht", 3000, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_XM =			new FluidType("PLASMA_XM",0xC6A5FF, 0, 4, 1, EnumSymbol.RADIATION, "hbmfluid.plasma_xm", 4250, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_BF =			new FluidType("PLASMA_BF",0xA7F1A3, 4, 5, 4, EnumSymbol.ANTIMATTER, "hbmfluid.plasma_bf", 8500, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		CARBONDIOXIDE =		new FluidType("CARBONDIOXIDE",0x404040, 3, 0, 0, EnumSymbol.ASPHYXIANT, "hbmfluid.carbondioxide");
		PLASMA_DH3 =		new FluidType("PLASMA_DH3",0xFF83AA, 0, 4, 0, EnumSymbol.RADIATION, "hbmfluid.plasma_dh3", 3480, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		HELIUM3 =			new FluidType("HELIUM3",0xFCF0C4, 3, 4, 0, EnumSymbol.ASPHYXIANT, "hbmfluid.helium3");
		DEATH =				new FluidType("DEATH",0x717A88, 2, 0, 1, EnumSymbol.ACID, "hbmfluid.death", 300, FluidTrait.CORROSIVE_2, FluidTrait.LEAD_CONTAINER);
		ETHANOL =			new FluidTypeCombustible("ETHANOL",0xe0ffff, 2, 3, 0, EnumSymbol.NONE, "hbmfluid.ethanol");
		HEAVYWATER =		new FluidType("HEAVYWATER",0x00a0b0, 1, 0, 0, EnumSymbol.NONE, "hbmfluid.heavywater");
		CRACKOIL =			new FluidTypeFlammable("CRACKOIL",0x020202, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.crackoil");
		COALOIL =			new FluidTypeFlammable("COALOIL",0x020202, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.coaloil");
		HOTCRACKOIL =		new FluidTypeFlammable("HOTCRACKOIL",0x300900, 2, 3, 0, EnumSymbol.NONE, "hbmfluid.hotcrackoil", 350);
		NAPHTHA_CRACK =		new FluidTypeFlammable("NAPHTHA_CRACK",0x595744, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.naphtha_crack");
		LIGHTOIL_CRACK =	new FluidTypeFlammable("LIGHTOIL_CRACK",0x8c7451, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.lightoil_crack");
		DIESEL_CRACK =		new FluidTypeCombustible("DIESEL_CRACK",0xf2eed5, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.diesel_crack");
		AROMATICS =			new FluidTypeFlammable("AROMATICS",0xfffeed, 1, 4, 1, EnumSymbol.NONE, "hbmfluid.aromatics");
		UNSATURATEDS =		new FluidTypeFlammable("UNSATURATEDS",0xfffeed, 1, 4, 1, EnumSymbol.NONE, "hbmfluid.unsaturateds");
		
		
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
		metaOrder.add(CRYOGEL);
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
		//metaOrder.add(HOTCOALOIL);
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
		metaOrder.add(GASOLINE);
		metaOrder.add(COALGAS);
		metaOrder.add(BIOGAS);
		metaOrder.add(BIOFUEL);
		metaOrder.add(ETHANOL);
		metaOrder.add(NITAN);
		metaOrder.add(BALEFIRE);
		//processing fluids
		metaOrder.add(ACID);
		metaOrder.add(UF6);
		metaOrder.add(PUF6);
		metaOrder.add(SAS3);
		metaOrder.add(SCHRABIDIC);
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