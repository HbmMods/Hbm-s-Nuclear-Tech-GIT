package com.hbm.inventory.fluid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.inventory.fluid.FluidType.ExtContainer;
import com.hbm.inventory.fluid.trait.*;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.*;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
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
	public static FluidType WOODOIL;
	public static FluidType COALCREOSOTE;
	public static FluidType SEEDSLURRY;

	private static final HashMap<Integer, FluidType> idMapping = new HashMap();
	private static final HashMap<String, FluidType> nameMapping = new HashMap();
	protected static final List<FluidType> metaOrder = new ArrayList();

	public static final FT_Liquid LIQUID = new FT_Liquid();
	public static final FT_Gaseous_ART EVAP = new FT_Gaseous_ART();
	public static final FT_Gaseous GASEOUS = new FT_Gaseous();
	public static final FT_Plasma PLASMA = new FT_Plasma();
	public static final FT_Amat ANTI = new FT_Amat();
	public static final FT_LeadContainer LEADCON = new FT_LeadContainer();
	public static final FT_NoContainer NOCON = new FT_NoContainer();
	public static final FT_NoID NOID = new FT_NoID();
	public static final FT_Delicious DELICIOUS = new FT_Delicious();
	
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
		
		NONE =				new FluidType("NONE",				0x888888, 0, 0, 0, EnumSymbol.NONE);
		WATER =				new FluidType("WATER",				0x3333FF, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		STEAM =				new FluidType("STEAM",				0xe5e5e5, 3, 0, 0, EnumSymbol.NONE).setTemp(100).setCompression(0.01D).addTraits(GASEOUS);
		HOTSTEAM =			new FluidType("HOTSTEAM",			0xE7D6D6, 4, 0, 0, EnumSymbol.NONE).setTemp(300).setCompression(0.1D).addTraits(GASEOUS);
		SUPERHOTSTEAM =		new FluidType("SUPERHOTSTEAM",		0xE7B7B7, 4, 0, 0, EnumSymbol.NONE).setTemp(450).setCompression(1D).addTraits(GASEOUS);
		ULTRAHOTSTEAM =		new FluidType("ULTRAHOTSTEAM",		0xE39393, 4, 0, 0, EnumSymbol.NONE).setTemp(600).setCompression(10D).addTraits(GASEOUS);
		COOLANT =			new FluidType("COOLANT",			0xd8fcff, 1, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		LAVA =				new FluidType("LAVA",				0xFF3300, 4, 0, 0, EnumSymbol.NOWATER).setTemp(1200).addTraits(LIQUID);
		DEUTERIUM =			new FluidType("DEUTERIUM",			0x0000FF, 3, 4, 0, EnumSymbol.NONE).addTraits(new FT_Flammable(5_000), new FT_Combustible(FuelGrade.HIGH, 10_000), GASEOUS);
		TRITIUM =			new FluidType("TRITIUM",			0x000099, 3, 4, 0, EnumSymbol.RADIATION).addTraits(new FT_Flammable(5_000), new FT_Combustible(FuelGrade.HIGH, 10_000), GASEOUS, new FT_VentRadiation(0.001F));
		OIL =				new FluidType("OIL",				0x020202, 2, 1, 0, EnumSymbol.NONE).addContainers(0x424242, ExtContainer.CANISTER).addTraits(new FT_Flammable(10_000), LIQUID);
		HOTOIL =			new FluidType("HOTOIL",				0x300900, 2, 3, 0, EnumSymbol.NONE).setTemp(350).addTraits(new FT_Flammable(10_000), LIQUID);
		HEAVYOIL =			new FluidType("HEAVYOIL",			0x141312, 2, 1, 0, EnumSymbol.NONE).addContainers(0x513F39, ExtContainer.CANISTER).addTraits(new FT_Flammable(50_000), new FT_Combustible(FuelGrade.LOW, 25_000), LIQUID);
		BITUMEN =			new FluidType("BITUMEN",			0x1f2426, 2, 0, 0, EnumSymbol.NONE).addContainers(0x5A5877, ExtContainer.CANISTER).addTraits(LIQUID);
		SMEAR =				new FluidType("SMEAR",				0x190f01, 2, 1, 0, EnumSymbol.NONE).addContainers(0x624F3B, ExtContainer.CANISTER).addTraits(new FT_Flammable(50_000), LIQUID);
		HEATINGOIL =		new FluidType("HEATINGOIL",			0x211806, 2, 2, 0, EnumSymbol.NONE).addContainers(0x694235, ExtContainer.CANISTER).addTraits(new FT_Flammable(150_000), new FT_Combustible(FuelGrade.LOW, 100_000), LIQUID);
		RECLAIMED =			new FluidType("RECLAIMED",			0x332b22, 2, 2, 0, EnumSymbol.NONE).addContainers(0xF65723, ExtContainer.CANISTER).addTraits(new FT_Flammable(100_000), new FT_Combustible(FuelGrade.LOW, 200_000), LIQUID);
		PETROIL =			new FluidType("PETROIL",			0x44413d, 1, 3, 0, EnumSymbol.NONE).addContainers(0x2369F6, ExtContainer.CANISTER).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 300_000), LIQUID);
		LUBRICANT =			new FluidType("LUBRICANT",			0x606060, 2, 1, 0, EnumSymbol.NONE).addContainers(0xF1CC05, ExtContainer.CANISTER).addTraits(LIQUID);
		NAPHTHA =			new FluidType("NAPHTHA",			0x595744, 2, 1, 0, EnumSymbol.NONE).addContainers(0x5F6D44, ExtContainer.CANISTER).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 200_000), LIQUID);
		DIESEL =			new FluidType("DIESEL",				0xf2eed5, 1, 2, 0, EnumSymbol.NONE).addContainers(0xFF2C2C, ExtContainer.CANISTER).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.HIGH, 500_000), LIQUID);
		LIGHTOIL =			new FluidType("LIGHTOIL",			0x8c7451, 1, 2, 0, EnumSymbol.NONE).addContainers(0xB46B52, ExtContainer.CANISTER).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.MEDIUM, 500_000), LIQUID);
		KEROSENE =			new FluidType("KEROSENE",			0xffa5d2, 1, 2, 0, EnumSymbol.NONE).addContainers(0xFF377D, ExtContainer.CANISTER).addTraits(new FT_Flammable(300_000), new FT_Combustible(FuelGrade.AERO, 1_250_000), LIQUID);
		GAS =				new FluidType("GAS",				0xfffeed, 1, 4, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(10_000), GASEOUS);
		PETROLEUM = 		new FluidType("PETROLEUM",			0x7cb7c9, 1, 4, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(25_000), GASEOUS);
		LPG =				new FluidType("LPG",				0x4747EA, 1, 3, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.HIGH, 400_000), LIQUID);
		BIOGAS =			new FluidType("BIOGAS",				0xbfd37c, 1, 4, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(25_000), GASEOUS);
		BIOFUEL =			new FluidType("BIOFUEL",			0xeef274, 1, 2, 0, EnumSymbol.NONE).addContainers(0x9EB623, ExtContainer.CANISTER).addTraits(new FT_Flammable(150_000), new FT_Combustible(FuelGrade.HIGH, 400_000), LIQUID);
		NITAN =				new FluidType("NITAN",				0x8018ad, 2, 4, 1, EnumSymbol.NONE).addContainers(0x6B238C, ExtContainer.CANISTER).addTraits(new FT_Flammable(2_000_000), new FT_Combustible(FuelGrade.HIGH, 5_000_000), LIQUID);
		UF6 =				new FluidType("UF6",				0xD1CEBE, 4, 0, 2, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.2F), new FT_Corrosive(15), GASEOUS);
		PUF6 =				new FluidType("PUF6",				0x4C4C4C, 4, 0, 4, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.1F), new FT_Corrosive(15), GASEOUS);
		SAS3 =				new FluidType("SAS3",				0x4ffffc, 5, 0, 4, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(1F), new FT_Corrosive(30), LIQUID);
		SCHRABIDIC =		new FluidType("SCHRABIDIC",			0x006B6B, 5, 0, 5, EnumSymbol.ACID).addTraits(new FT_VentRadiation(1F), new FT_Corrosive(75), new FT_Poison(true, 2), LIQUID);
		AMAT =				new FluidType("AMAT",				0x010101, 5, 0, 5, EnumSymbol.ANTIMATTER).addTraits(ANTI, GASEOUS);
		ASCHRAB =			new FluidType("ASCHRAB",			0xb50000, 5, 0, 5, EnumSymbol.ANTIMATTER).addTraits(ANTI, GASEOUS);
		ACID =				new FluidType("ACID",				0xfff7aa, 3, 0, 3, EnumSymbol.OXIDIZER).addTraits(new FT_Corrosive(40), LIQUID);
		WATZ =				new FluidType("WATZ",				0x86653E, 4, 0, 3, EnumSymbol.ACID).addTraits(new FT_Corrosive(60), new FT_VentRadiation(0.1F), LIQUID);
		CRYOGEL =			new FluidType("CRYOGEL",			0x32ffff, 2, 0, 0, EnumSymbol.CROYGENIC).setTemp(-170).addTraits(LIQUID);
		HYDROGEN =			new FluidType("HYDROGEN",			0x4286f4, 3, 4, 0, EnumSymbol.CROYGENIC).setTemp(-260).addTraits(new FT_Flammable(5_000), new FT_Combustible(FuelGrade.HIGH, 10_000), LIQUID, EVAP);
		OXYGEN =			new FluidType("OXYGEN",				0x98bdf9, 3, 0, 0, EnumSymbol.CROYGENIC).setTemp(-100).addTraits(LIQUID, EVAP);
		XENON =				new FluidType("XENON",				0xba45e8, 0, 0, 0, EnumSymbol.ASPHYXIANT).addTraits(GASEOUS);
		BALEFIRE =			new FluidType("BALEFIRE",			0x28e02e, 4, 4, 3, EnumSymbol.RADIATION).setTemp(1500).addTraits(new FT_Corrosive(50), new FT_Flammable(1_000_000), new FT_Combustible(FuelGrade.HIGH, 2_500_000), LIQUID);
		MERCURY =			new FluidType("MERCURY",			0x808080, 2, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, new FT_Poison(false, 2));
		PAIN =				new FluidType("PAIN",				0x938541, 2, 0, 1, EnumSymbol.ACID).setTemp(300).addTraits(new FT_Corrosive(30), new FT_Poison(true, 2), LIQUID);
		WASTEFLUID =		new FluidType("WASTEFLUID",			0x544400, 2, 0, 1, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.5F), NOCON, LIQUID);
		WASTEGAS =			new FluidType("WASTEGAS",			0xB8B8B8, 2, 0, 1, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.5F), NOCON, GASEOUS);
		GASOLINE =			new FluidType("GASOLINE",			0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(0x2F7747, ExtContainer.CANISTER).addTraits(new FT_Flammable(400_000), new FT_Combustible(FuelGrade.HIGH, 1_000_000), LIQUID);
		COALGAS =			new FluidType("COALGAS",			0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(0x2E155F, ExtContainer.CANISTER).addTraits(new FT_Flammable(75_000), new FT_Combustible(FuelGrade.MEDIUM, 150_000), LIQUID);
		SPENTSTEAM =		new FluidType("SPENTSTEAM",			0x445772, 2, 0, 0, EnumSymbol.NONE).setCompression(1D).addTraits(NOCON, GASEOUS);
		FRACKSOL =			new FluidType("FRACKSOL",			0x798A6B, 1, 3, 3, EnumSymbol.ACID).addContainers(0x4F887F, ExtContainer.CANISTER).addTraits(new FT_Corrosive(15), new FT_Poison(false, 0), LIQUID);
		PLASMA_DT =			new FluidType("PLASMA_DT",			0xF7AFDE, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3250).addTraits(NOCON, NOID, PLASMA);
		PLASMA_HD =			new FluidType("PLASMA_HD",			0xF0ADF4, 0, 4, 0, EnumSymbol.RADIATION).setTemp(2500).addTraits(NOCON, NOID, PLASMA);
		PLASMA_HT =			new FluidType("PLASMA_HT",			0xD1ABF2, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3000).addTraits(NOCON, NOID, PLASMA);
		PLASMA_XM =			new FluidType("PLASMA_XM",			0xC6A5FF, 0, 4, 1, EnumSymbol.RADIATION).setTemp(4250).addTraits(NOCON, NOID, PLASMA);
		PLASMA_BF =			new FluidType("PLASMA_BF",			0xA7F1A3, 4, 5, 4, EnumSymbol.ANTIMATTER).setTemp(8500).addTraits(NOCON, NOID, PLASMA);
		CARBONDIOXIDE =		new FluidType("CARBONDIOXIDE",		0x404040, 3, 0, 0, EnumSymbol.ASPHYXIANT).addTraits(GASEOUS);
		PLASMA_DH3 =		new FluidType("PLASMA_DH3",			0xFF83AA, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3480).addTraits(NOCON, NOID, PLASMA);
		HELIUM3 =			new FluidType("HELIUM3",			0xFCF0C4, 3, 4, 0, EnumSymbol.ASPHYXIANT).addTraits(GASEOUS);
		DEATH =				new FluidType("DEATH",				0x717A88, 2, 0, 1, EnumSymbol.ACID).setTemp(300).addTraits(new FT_Corrosive(80), new FT_Poison(true, 4), LEADCON, LIQUID);
		ETHANOL =			new FluidType("ETHANOL",			0xe0ffff, 2, 3, 0, EnumSymbol.NONE).addContainers(0xEAFFF3, ExtContainer.CANISTER).addTraits(new FT_Flammable(75_000), new FT_Combustible(FuelGrade.HIGH, 200_000), LIQUID);
		HEAVYWATER =		new FluidType("HEAVYWATER",			0x00a0b0, 1, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		CRACKOIL =			new FluidType("CRACKOIL",			0x020202, 2, 1, 0, EnumSymbol.NONE).addContainers(0x424242, ExtContainer.CANISTER).addTraits(new FT_Flammable(10_000), LIQUID);
		COALOIL =			new FluidType("COALOIL",			0x020202, 2, 1, 0, EnumSymbol.NONE).addContainers(0x424242, ExtContainer.CANISTER).addTraits(new FT_Flammable(10_000), LIQUID);
		HOTCRACKOIL =		new FluidType("HOTCRACKOIL",		0x300900, 2, 3, 0, EnumSymbol.NONE).setTemp(350).addContainers(0x424242, ExtContainer.CANISTER).addTraits(new FT_Flammable(10_000), LIQUID);
		NAPHTHA_CRACK =		new FluidType("NAPHTHA_CRACK",		0x595744, 2, 1, 0, EnumSymbol.NONE).addContainers(0x5F6D44, ExtContainer.CANISTER).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 200_000), LIQUID);
		LIGHTOIL_CRACK =	new FluidType("LIGHTOIL_CRACK",		0x8c7451, 1, 2, 0, EnumSymbol.NONE).addContainers(0xB46B52, ExtContainer.CANISTER).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.MEDIUM, 500_000), LIQUID);
		DIESEL_CRACK =		new FluidType("DIESEL_CRACK",		0xf2eed5, 1, 2, 0, EnumSymbol.NONE).addContainers(0xFF2C2C, ExtContainer.CANISTER).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.HIGH, 450_000), LIQUID);
		AROMATICS =			new FluidType("AROMATICS",			0x68A09A, 1, 4, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(25_000), LIQUID);
		UNSATURATEDS =		new FluidType("UNSATURATEDS",		0x628FAE, 1, 4, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(1_000_000), GASEOUS); //acetylene burns as hot as satan's asshole
		SALIENT =			new FluidType("SALIENT",			0x457F2D, 0, 0, 0, EnumSymbol.NONE).addTraits(DELICIOUS, LIQUID);
		XPJUICE =			new FluidType("XPJUICE",			0xBBFF09, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		ENDERJUICE =		new FluidType("ENDERJUICE",			0x127766, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		PETROIL_LEADED =	new FluidType("PETROIL_LEADED",		0x44413d, 1, 3, 0, EnumSymbol.NONE).addContainers(0x2331F6, ExtContainer.CANISTER).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 450_000), LIQUID);
		GASOLINE_LEADED =	new FluidType("GASOLINE_LEADED",	0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(0x2F775A, ExtContainer.CANISTER).addTraits(new FT_Flammable(400_000), new FT_Combustible(FuelGrade.HIGH, 1_500_000), LIQUID);
		COALGAS_LEADED =	new FluidType("COALGAS_LEADED",		0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(0x1E155F, ExtContainer.CANISTER).addTraits(new FT_Flammable(75_000), new FT_Combustible(FuelGrade.MEDIUM, 250_000), LIQUID);
		SULFURIC_ACID =		new FluidType("SULFURIC_ACID",		0xB0AA64, 3, 0, 2, EnumSymbol.ACID).addTraits(new FT_Corrosive(50), LIQUID);
		COOLANT_HOT =		new FluidType("COOLANT_HOT",		0x99525E, 1, 0, 0, EnumSymbol.NONE).setTemp(600).addTraits(LIQUID);
		MUG =				new FluidType("MUG",				0x4B2D28, 0, 0, 0, EnumSymbol.NONE).addTraits(DELICIOUS, LIQUID);
		MUG_HOT =			new FluidType("MUG_HOT",			0x6B2A20, 0, 0, 0, EnumSymbol.NONE).setTemp(500).addTraits(DELICIOUS, LIQUID);
		WOODOIL =			new FluidType("WOODOIL",			0x847D54, 2, 2, 0, EnumSymbol.NONE).addContainers(0xBF7E4F, ExtContainer.CANISTER).addTraits(LIQUID);
		COALCREOSOTE =		new FluidType("COALCREOSOTE",		0x51694F, 3, 2, 0, EnumSymbol.NONE).addContainers(0x285A3F, ExtContainer.CANISTER).addTraits(LIQUID);
		SEEDSLURRY =		new FluidType(81, "SEEDSLURRY",		0x7CC35E, 0, 0, 0, EnumSymbol.NONE).addContainers(0x7CC35E, ExtContainer.CANISTER).addTraits(LIQUID);
		
		
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
		metaOrder.add(COALCREOSOTE);
		metaOrder.add(WOODOIL);
		metaOrder.add(BIOGAS);
		metaOrder.add(BIOFUEL);
		metaOrder.add(ETHANOL);
		metaOrder.add(NITAN);
		metaOrder.add(BALEFIRE);
		//processing fluids
		metaOrder.add(SALIENT);
		metaOrder.add(SEEDSLURRY);
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

		double eff_steam_boil = 1.0D;
		double eff_steam_heatex = 0.25D;
		
		WATER.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, eff_steam_boil).setEff(HeatingType.HEATEXCHANGER, eff_steam_heatex)
				.addStep(200, 1, STEAM, 100)
				.addStep(220, 1, HOTSTEAM, 10)
				.addStep(238, 1, SUPERHOTSTEAM, 1)
				.addStep(2500, 10, ULTRAHOTSTEAM, 1));

		STEAM.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, eff_steam_boil).setEff(HeatingType.HEATEXCHANGER, eff_steam_heatex).addStep(2, 10, HOTSTEAM, 1));
		HOTSTEAM.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, eff_steam_boil).setEff(HeatingType.HEATEXCHANGER, eff_steam_heatex).addStep(18, 10, SUPERHOTSTEAM, 1));
		SUPERHOTSTEAM.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, eff_steam_boil).setEff(HeatingType.HEATEXCHANGER, eff_steam_heatex).addStep(120, 10, ULTRAHOTSTEAM, 1));

		double eff_steam_turbine = 1.0D;
		double eff_steam_cool = 0.5D;
		STEAM.addTraits(new FT_Coolable(SPENTSTEAM, 100, 1, 200).setEff(CoolingType.TURBINE, eff_steam_turbine).setEff(CoolingType.HEATEXCHANGER, eff_steam_cool));
		HOTSTEAM.addTraits(new FT_Coolable(STEAM, 1, 10, 2).setEff(CoolingType.TURBINE, eff_steam_turbine).setEff(CoolingType.HEATEXCHANGER, eff_steam_cool));
		SUPERHOTSTEAM.addTraits(new FT_Coolable(HOTSTEAM, 1, 10, 18).setEff(CoolingType.TURBINE, eff_steam_turbine).setEff(CoolingType.HEATEXCHANGER, eff_steam_cool));
		ULTRAHOTSTEAM.addTraits(new FT_Coolable(SUPERHOTSTEAM, 1, 10, 120).setEff(CoolingType.TURBINE, eff_steam_turbine).setEff(CoolingType.HEATEXCHANGER, eff_steam_cool));
		
		OIL.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, 1.0D).setEff(HeatingType.HEATEXCHANGER, 1.0D).addStep(10, 1, HOTOIL, 1));
		CRACKOIL.addTraits(new FT_Heatable().setEff(HeatingType.BOILER, 1.0D).setEff(HeatingType.HEATEXCHANGER, 1.0D).addStep(10, 1, HOTCRACKOIL, 1));

		HOTOIL.addTraits(new FT_Coolable(OIL, 1, 1, 10).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		HOTCRACKOIL.addTraits(new FT_Coolable(CRACKOIL, 1, 1, 10).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		
		COOLANT.addTraits(new FT_Heatable().setEff(HeatingType.HEATEXCHANGER, 1.0D).addStep(300, 1, COOLANT_HOT, 1));
		COOLANT_HOT.addTraits(new FT_Coolable(COOLANT, 1, 1, 300).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		
		MUG.addTraits(new FT_Heatable().setEff(HeatingType.HEATEXCHANGER, 1.0D).addStep(400, 1, MUG_HOT, 1));
		MUG_HOT.addTraits(new FT_Coolable(MUG, 1, 1, 400).setEff(CoolingType.HEATEXCHANGER, 1.0D));
		
		if(idMapping.size() != metaOrder.size()) {
			throw new IllegalStateException("A severe error has occoured during NTM's fluid registering process! The MetaOrder and Mappings are inconsistent! Mapping size: " + idMapping.size()+ " / MetaOrder size: " + metaOrder.size());
		}
		
		
		/// EXPERIMENTAL ///
		
		long baseline = 100_000L; //we do not know
		double demandVeryLow = 0.5D;
		double demandLow = 1.0D;
		double demandMedium = 1.5D;
		double demandHigh = 2.0D;
		double complexityRefinery = 1.1D;
		double complexityFraction = 1.05D;
		double complexityCracking = 1.25D;
		double complexityChemplant = 1.1D;
		double complexityLubed = 1.15D;
		double complexityLeaded = 1.5D;
		double flammabilityLow = 0.25D;
		double flammabilityNormal = 1.0D;
		double flammabilityHigh = 2.0D;

		/// the allmighty excel spreadsheet has spoken! ///
		registerCalculatedFuel(OIL, (baseline / 1D * flammabilityLow * demandLow), 0, null);
		registerCalculatedFuel(CRACKOIL, (baseline / 1D * flammabilityLow * demandLow * complexityCracking), 0, null);
		registerCalculatedFuel(GAS, (baseline / 1D * flammabilityNormal * demandVeryLow), 0, null);
		registerCalculatedFuel(HEAVYOIL, (baseline / 0.5 * flammabilityLow * demandLow * complexityRefinery), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(SMEAR, (baseline / 0.35 * flammabilityLow * demandLow * complexityRefinery * complexityFraction), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(RECLAIMED, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(PETROIL, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant * complexityLubed), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(PETROIL_LEADED, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant * complexityLubed * complexityLeaded), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(HEATINGOIL, (baseline / 0.31 * flammabilityNormal * demandLow * complexityRefinery * complexityFraction * complexityFraction), 1.0D, FuelGrade.LOW);
		registerCalculatedFuel(NAPHTHA, (baseline / 0.25 * flammabilityLow * demandLow * complexityRefinery), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(NAPHTHA_CRACK, (baseline / 0.40 * flammabilityLow * demandLow * complexityRefinery * complexityCracking), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(GASOLINE, (baseline / 0.20 * flammabilityNormal * demandLow * complexityRefinery * complexityChemplant), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(GASOLINE_LEADED, (baseline / 0.20 * flammabilityNormal * demandLow * complexityRefinery * complexityChemplant * complexityLeaded), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(DIESEL, (baseline / 0.21 * flammabilityNormal * demandLow * complexityRefinery * complexityFraction), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(DIESEL_CRACK, (baseline / 0.28 * flammabilityNormal * demandLow * complexityRefinery * complexityCracking * complexityFraction), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(LIGHTOIL, (baseline / 0.15 * flammabilityNormal * demandHigh * complexityRefinery), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(LIGHTOIL_CRACK, (baseline / 0.30 * flammabilityNormal * demandHigh * complexityRefinery * complexityCracking), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(KEROSENE, (baseline / 0.09 * flammabilityNormal * demandHigh * complexityRefinery * complexityFraction), 1.5D, FuelGrade.AERO);
		registerCalculatedFuel(PETROLEUM, (baseline / 0.10 * flammabilityNormal * demandMedium * complexityRefinery), 0, null);
		registerCalculatedFuel(AROMATICS, (baseline / 0.15 * flammabilityLow * demandHigh * complexityRefinery * complexityCracking), 0, null);
		registerCalculatedFuel(UNSATURATEDS, (baseline / 0.15 * flammabilityHigh * demandHigh * complexityRefinery * complexityCracking), 0, null);
		registerCalculatedFuel(LPG, (baseline / 0.05 * flammabilityNormal * demandMedium * complexityRefinery * complexityChemplant), 2.5, FuelGrade.HIGH);
		registerCalculatedFuel(NITAN, KEROSENE.getTrait(FT_Flammable.class).getHeatEnergy() * 25L, 2.5, FuelGrade.HIGH);
		registerCalculatedFuel(BALEFIRE, KEROSENE.getTrait(FT_Flammable.class).getHeatEnergy() * 100L, 2.5, FuelGrade.HIGH);
		
		int coalHeat = 400_000; // 200TU/t for 2000 ticks
		registerCalculatedFuel(COALOIL, (coalHeat * (1000 /* bucket */ / 100 /* mB per coal */) * flammabilityLow * demandLow * complexityChemplant), 0, null);
		long coaloil = COALOIL.getTrait(FT_Flammable.class).getHeatEnergy();
		registerCalculatedFuel(COALGAS, (coaloil / 0.3 * flammabilityNormal * demandMedium * complexityChemplant * complexityFraction), 1.5, FuelGrade.MEDIUM);
		registerCalculatedFuel(COALGAS_LEADED, (coaloil / 0.3 * flammabilityNormal * demandMedium * complexityChemplant * complexityFraction * complexityLeaded), 1.5, FuelGrade.MEDIUM);

		registerCalculatedFuel(ETHANOL, 275_000D /* diesel / 2 */, 2.5D, FuelGrade.HIGH);

		registerCalculatedFuel(BIOGAS, 250_000D * flammabilityLow /* biofuel with half compression, terrible flammability */, 0, null);
		registerCalculatedFuel(BIOFUEL, 500_000D /* slightly below diesel */, 2.5D, FuelGrade.HIGH);

		registerCalculatedFuel(WOODOIL, 110_000 /* 20_000 TU per 250mB + a bonus */, 0, null);
		registerCalculatedFuel(COALCREOSOTE, 250_000 /* 20_000 TU per 100mB + a bonus */, 0, null);
	}
	
	private static void registerCalculatedFuel(FluidType type, double base, double combustMult, FuelGrade grade) {
		
		long flammable = (long) base;
		long combustible = (long) (base * combustMult);

		flammable = round(flammable);
		combustible = round(combustible);

		type.addTraits(new FT_Flammable(flammable));
		
		if(combustible > 0 && grade != null)
			type.addTraits(new FT_Combustible(grade, combustible));
	}
	
	/** ugly but it does the thing well enough */
	private static long round(long l) {
		if(l > 10_000_000L) return l - (l % 100_000L);
		if(l > 1_000_000L) return l - (l % 10_000L);
		if(l > 100_000L) return l - (l % 1_000L);
		if(l > 10_000L) return l - (l % 100L);
		if(l > 1_000L) return l - (l % 10L);
		
		return l;
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