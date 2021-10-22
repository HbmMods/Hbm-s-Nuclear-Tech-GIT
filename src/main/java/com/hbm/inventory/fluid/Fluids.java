package com.hbm.inventory.fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.HashBiMap;
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
	public static FluidType HOTOIL;
	public static FluidType HEAVYOIL;
	public static FluidType BITUMEN;
	public static FluidType SMEAR;
	public static FluidType HEATINGOIL;
	public static FluidType RECLAIMED;
	public static FluidType PETROIL;
	public static FluidType LUBRICANT;
	public static FluidType NAPHTHA;
	public static FluidType DIESEL;
	public static FluidType LIGHTOIL;
	public static FluidType KEROSENE;
	public static FluidType GAS;
	public static FluidType PETROLEUM;
	public static FluidType LPG;
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
	public static FluidType PAIN;
	public static FluidType WASTEFLUID;
	public static FluidType WASTEGAS;
	public static FluidType GASOLINE;
	public static FluidType SPENTSTEAM;
	public static FluidType FRACKSOL;
	public static FluidType PLASMA_DT;
	public static FluidType PLASMA_HD;
	public static FluidType PLASMA_HT;
	public static FluidType PLASMA_XM;
	public static FluidType PLASMA_BF;
	public static FluidType CARBONDIOXIDE;
	
	public static final HashMap<Integer, FluidType> idMapping = new HashMap();
	public static final List<FluidType> metaOrder = new ArrayList();
	
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
		
		NONE =				new FluidType(0x888888, 0, 1, 1, 0, 0, 0, EnumSymbol.NONE, "hbmfluid.none");
		WATER =				new FluidType(0x3333FF, 1, 1, 1, 0, 0, 0, EnumSymbol.NONE, "hbmfluid.water");
		STEAM =				new FluidType(0xe5e5e5, 9, 2, 1, 3, 0, 0, EnumSymbol.NONE, "hbmfluid.steam", 100);
		HOTSTEAM =			new FluidType(0xE7D6D6, 1, 1, 2, 4, 0, 0, EnumSymbol.NONE, "hbmfluid.hotsteam", 300);
		SUPERHOTSTEAM =		new FluidType(0xE7B7B7, 2, 1, 2, 4, 0, 0, EnumSymbol.NONE, "hbmfluid.superhotsteam", 450);
		ULTRAHOTSTEAM =		new FluidType(0xE39393, 13, 1, 2, 4, 0, 0, EnumSymbol.NONE, "hbmfluid.ultrahotsteam", 600);
		COOLANT =			new FluidType(0xd8fcff, 2, 1, 1, 1, 0, 0, EnumSymbol.NONE, "hbmfluid.coolant");
		LAVA =				new FluidType(0xFF3300, 3, 1, 1, 4, 0, 0, EnumSymbol.NOWATER, "hbmfluid.lava", 1200);
		DEUTERIUM =			new FluidType(0x0000FF, 4, 1, 1, 3, 4, 0, EnumSymbol.NONE, "hbmfluid.deuterium");
		TRITIUM =			new FluidType(0x000099, 5, 1, 1, 3, 4, 0, EnumSymbol.RADIATION, "hbmfluid.tritium");
		OIL =				new FluidType(0x020202, 6, 1, 1, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.oil");
		HOTOIL =			new FluidType(0x300900, 8, 2, 1, 2, 3, 0, EnumSymbol.NONE, "hbmfluid.hotoil", 350);
		HEAVYOIL =			new FluidType(0x141312, 2, 2, 1, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.heavyoil");
		BITUMEN =			new FluidType(0x1f2426, 3, 2, 1, 2, 0, 0, EnumSymbol.NONE, "hbmfluid.bitumen");
		SMEAR =				new FluidType(0x190f01, 7, 1, 1, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.smear");
		HEATINGOIL =		new FluidType(0x211806, 4, 2, 1, 2, 2, 0, EnumSymbol.NONE, "hbmfluid.heatingoil");
		RECLAIMED =			new FluidType(0x332b22, 8, 1, 1, 2, 2, 0, EnumSymbol.NONE, "hbmfluid.reclaimed");
		PETROIL =			new FluidType(0x44413d, 9, 1, 1, 1, 3, 0, EnumSymbol.NONE, "hbmfluid.petroil");
		LUBRICANT =			new FluidType(0x606060, 10, 1, 1, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.lubricant");
		NAPHTHA =			new FluidType(0x595744, 5, 2, 1, 2, 1, 0, EnumSymbol.NONE, "hbmfluid.naphtha");
		DIESEL =			new FluidType(0xf2eed5, 11, 1, 1, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.diesel");
		LIGHTOIL =			new FluidType(0x8c7451, 6, 2, 1, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.lightoil");
		KEROSENE =			new FluidType(0xffa5d2, 12, 1, 1, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.kerosene");
		GAS =				new FluidType(0xfffeed, 13, 1, 1, 1, 4, 1, EnumSymbol.NONE, "hbmfluid.gas");
		PETROLEUM = 		new FluidType(0x7cb7c9, 7, 2, 1, 1, 4, 1, EnumSymbol.NONE, "hbmfluid.petroleum");
		LPG =				new FluidType(0x4747EA, 5, 2, 2, 1, 3, 1, EnumSymbol.NONE, "hbmfluid.lpg");
		BIOGAS =			new FluidType(0xbfd37c, 12, 2, 1, 1, 4, 1, EnumSymbol.NONE, "hbmfluid.biogas");
		BIOFUEL =			new FluidType(0xeef274, 13, 2, 1, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.biofuel");
		NITAN =				new FluidType(0x8018ad, 15, 2, 1, 2, 4, 1, EnumSymbol.NONE, "hbmfluid.nitan");
		UF6 =				new FluidType(0xD1CEBE, 14, 1, 1, 4, 0, 2, EnumSymbol.RADIATION, "hbmfluid.uf6", FluidTrait.CORROSIVE);
		PUF6 =				new FluidType(0x4C4C4C, 15, 1, 1, 4, 0, 4, EnumSymbol.RADIATION, "hbmfluid.puf6", FluidTrait.CORROSIVE);
		SAS3 =				new FluidType(0x4ffffc, 14, 2, 1, 5, 0, 4, EnumSymbol.RADIATION, "hbmfluid.sas3", FluidTrait.CORROSIVE);
		SCHRABIDIC =		new FluidType(0x006B6B, 14, 1, 2, 5, 0, 5, EnumSymbol.ACID, "hbmfluid.schrabidic", FluidTrait.CORROSIVE_2);
		AMAT =				new FluidType(0x010101, 0, 2, 1, 5, 0, 5, EnumSymbol.ANTIMATTER, "hbmfluid.amat", FluidTrait.AMAT);
		ASCHRAB =			new FluidType(0xb50000, 1, 2, 1, 5, 0, 5, EnumSymbol.ANTIMATTER, "hbmfluid.aschrab", FluidTrait.AMAT);
		ACID =				new FluidType(0xfff7aa, 10, 2, 1, 3, 0, 3, EnumSymbol.OXIDIZER, "hbmfluid.acid", FluidTrait.CORROSIVE);
		WATZ =				new FluidType(0x86653E, 11, 2, 1, 4, 0, 3, EnumSymbol.ACID, "hbmfluid.watz", FluidTrait.CORROSIVE_2);
		CRYOGEL =			new FluidType(0x32ffff, 0, 1, 2, 2, 0, 0, EnumSymbol.CROYGENIC, "hbmfluid.cryogel", -170);
		HYDROGEN =			new FluidType(0x4286f4, 3, 1, 2, 3, 4, 0, EnumSymbol.CROYGENIC, "hbmfluid.hydrogen");
		OXYGEN =			new FluidType(0x98bdf9, 4, 1, 2, 3, 0, 0, EnumSymbol.CROYGENIC, "hbmfluid.oxygen");
		XENON =				new FluidType(0xba45e8, 5, 1, 2, 0, 0, 0, EnumSymbol.ASPHYXIANT, "hbmfluid.xenon");
		BALEFIRE =			new FluidType(0x28e02e, 6, 1, 2, 4, 4, 3, EnumSymbol.RADIATION, "hbmfluid.balefire", 1500, FluidTrait.CORROSIVE);
		MERCURY =			new FluidType(0x808080, 7, 1, 2, 2, 0, 0, EnumSymbol.NONE, "hbmfluid.mercury");
		PAIN =				new FluidType(0x938541, 15, 1, 2, 2, 0, 1, EnumSymbol.ACID, "hbmfluid.pain", 300, FluidTrait.CORROSIVE);
		WASTEFLUID =		new FluidType(0x544400, 0, 2, 2, 2, 0, 1, EnumSymbol.RADIATION, "hbmfluid.wastefluid", FluidTrait.NO_CONTAINER);
		WASTEGAS =			new FluidType(0xB8B8B8, 1, 2, 2, 2, 0, 1, EnumSymbol.RADIATION, "hbmfluid.wastegas", FluidTrait.NO_CONTAINER);
		GASOLINE =			new FluidType(0x445772, 2, 2, 2, 1, 2, 0, EnumSymbol.NONE, "hbmfluid.gasoline");
		SPENTSTEAM =		new FluidType(0x445772, 3, 2, 2, 2, 0, 0, EnumSymbol.NONE, "hbmfluid.spentsteam", FluidTrait.NO_CONTAINER);
		FRACKSOL =			new FluidType(0x798A6B, 4, 2, 2, 1, 3, 3, EnumSymbol.ACID, "hbmfluid.fracksol", FluidTrait.CORROSIVE);
		PLASMA_DT =			new FluidType(0xF7AFDE, 8, 1, 2, 0, 4, 0, EnumSymbol.RADIATION, "hbmfluid.plasma_dt", 3250, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_HD =			new FluidType(0xF0ADF4, 9, 1, 2, 0, 4, 0, EnumSymbol.RADIATION, "hbmfluid.plasma_hd", 2500, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_HT =			new FluidType(0xD1ABF2, 10, 1, 2, 0, 4, 0, EnumSymbol.RADIATION, "hbmfluid.plasma_ht", 3000, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_XM =			new FluidType(0xC6A5FF, 11, 1, 2, 0, 4, 1, EnumSymbol.RADIATION, "hbmfluid.plasma_xm", 4250, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		PLASMA_BF =			new FluidType(0xA7F1A3, 12, 1, 2, 4, 5, 4, EnumSymbol.ANTIMATTER, "hbmfluid.plasma_bf", 8500, FluidTrait.NO_CONTAINER, FluidTrait.NO_ID);
		CARBONDIOXIDE = new FluidType(0x404040, 6, 2, 2, 3, 0, 0, EnumSymbol.ASPHYXIANT, "hbmfluid.carbondioxide");
		// v v v v v v v v
		
		// ^ ^ ^ ^ ^ ^ ^ ^
		//ADD NEW FLUIDS HERE
		//AND DON'T FORGET THE META DOWN HERE
		// V V V V V V V V
		
		//null
		metaOrder.add(NONE);
		//vanilla
		metaOrder.add(WATER);
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
		metaOrder.add(OXYGEN);
		metaOrder.add(DEUTERIUM);
		metaOrder.add(TRITIUM);
		metaOrder.add(XENON);
		metaOrder.add(MERCURY);
		//oils, fuels
		metaOrder.add(OIL);
		metaOrder.add(HOTOIL);
		metaOrder.add(HEAVYOIL);
		metaOrder.add(NAPHTHA);
		metaOrder.add(LIGHTOIL);
		metaOrder.add(BITUMEN);
		metaOrder.add(SMEAR);
		metaOrder.add(HEATINGOIL);
		metaOrder.add(RECLAIMED);
		metaOrder.add(PETROIL);
		metaOrder.add(LUBRICANT);
		metaOrder.add(DIESEL);
		metaOrder.add(KEROSENE);
		metaOrder.add(GAS);
		metaOrder.add(PETROLEUM);
		metaOrder.add(LPG);
		metaOrder.add(BIOGAS);
		metaOrder.add(BIOFUEL);
		metaOrder.add(GASOLINE);
		metaOrder.add(NITAN);
		metaOrder.add(BALEFIRE);
		//processing fluids
		metaOrder.add(ACID);
		metaOrder.add(UF6);
		metaOrder.add(PUF6);
		metaOrder.add(SAS3);
		metaOrder.add(SCHRABIDIC);
		metaOrder.add(PAIN);
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
		metaOrder.add(PLASMA_XM);
		metaOrder.add(PLASMA_BF);
	}
	
	public static int registerSelf(FluidType fluid) {
		int id = idMapping.size();
		idMapping.put(id, fluid);
		return id;
	}
}