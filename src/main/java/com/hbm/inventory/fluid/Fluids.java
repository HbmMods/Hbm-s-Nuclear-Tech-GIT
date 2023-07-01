package com.hbm.inventory.fluid;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.inventory.fluid.FluidType.ExtContainer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.hbm.inventory.fluid.trait.*;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.*;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.potion.HbmPotion;
import com.hbm.inventory.fluid.trait.FT_Combustible.FuelGrade;
import com.hbm.inventory.fluid.trait.FT_Coolable.CoolingType;
import com.hbm.inventory.fluid.trait.FT_Heatable.HeatingType;
import com.hbm.inventory.fluid.trait.FT_Toxin.*;
import com.hbm.render.util.EnumSymbol;
import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Fluids {

	public static final Gson gson = new Gson();

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
	public static FluidType NITROGEN;
	public static FluidType NITRIC_ACID;
	public static FluidType BLOOD;				//WEEE
	public static FluidType XENON;
	public static FluidType AIR;				//cant believe im adding this
	public static FluidType MINSOL;
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
	public static FluidType AMMONIA;
	public static FluidType HYDRAZINE;
	public static FluidType BLOODGAS;
	private static FluidType BLOOD_HOT;
	public static FluidType SOLVENT; //oranic solvent in fact
	public static FluidType HCL;
	public static FluidType SYNGAS;
	public static FluidType OXYHYDROGEN;
	public static FluidType RADIOSOLVENT;		//DCM-ish made by wacky radio cracking
	public static FluidType CHLORINE;			//everone's favorite!
	public static FluidType HEAVYOIL_VACUUM;
	public static FluidType REFORMATE;
	public static FluidType LIGHTOIL_VACUUM;
	public static FluidType SOURGAS;
	public static FluidType NEON;
	public static FluidType ARGON;
	public static FluidType KRYPTON;
	public static FluidType COFFEE;
	public static FluidType TEA;
	public static FluidType HONEY;
	public static FluidType OLIVEOIL;
	public static FluidType MILK;
	public static FluidType SMILK;
	public static FluidType XYLENE;				//BTX: benzene, terephthalate and xylene
	public static FluidType HEATINGOIL_VACUUM;
	public static FluidType DIESEL_REFORM;
	public static FluidType DIESEL_CRACK_REFORM;
	public static FluidType KEROSENE_REFORM;
	public static FluidType REFORMGAS;			//MAPD: propyne, propadiene
	public static FluidType COLLOID;
	public static FluidType PHOSGENE;
	public static FluidType MUSTARDGAS;
	public static FluidType IONGEL;
	public static FluidType ELBOWGREASE;
	public static FluidType NMASSTETRANOL; //stronger, not suitable for FTL due to its Carbon-Chain content
	public static FluidType NMASS; //weaker, much more suitable for FTL
	public static FluidType RIZZ;
	public static FluidType HTcO4;//we
	public static FluidType OIL_COKER;			//heavy fractions from coking, mostly bitumen
	public static FluidType NAPHTHA_COKER;		//medium fractions from coking, aromatics and fuel oil
	public static FluidType GAS_COKER;			//light fractions from coking, natgas and co2
	public static FluidType EGG;
	public static FluidType CHOLESTEROL;
	public static FluidType ESTRADIOL;
	public static FluidType FISHOIL;
	public static FluidType SUNFLOWEROIL;
	public static FluidType NITROGLYCERIN;
	public static FluidType REDMUD;
	public static FluidType CHLOROCALCITE_SOLUTION;
	public static FluidType CHLOROCALCITE_MIX;
	public static FluidType CHLOROCALCITE_CLEANED;
	public static FluidType POTASSIUM_CHLORIDE;
	public static FluidType CALCIUM_CHLORIDE;
	public static FluidType CALCIUM_SOLUTION;

	private static final HashMap<Integer, FluidType> idMapping = new HashMap();
	private static final HashMap<String, FluidType> nameMapping = new HashMap();
	protected static final List<FluidType> metaOrder = new ArrayList();

	public static final FT_Liquid LIQUID = new FT_Liquid();
	public static final FT_Viscous VISCOUS = new FT_Viscous();
	public static final FT_Gaseous_ART EVAP = new FT_Gaseous_ART();
	public static final FT_Gaseous GASEOUS = new FT_Gaseous();
	public static final FT_Plasma PLASMA = new FT_Plasma();
	public static final FT_Amat ANTI = new FT_Amat();
	public static final FT_LeadContainer LEADCON = new FT_LeadContainer();
	public static final FT_NoContainer NOCON = new FT_NoContainer();
	public static final FT_NoID NOID = new FT_NoID();
	public static final FT_Delicious DELICIOUS = new FT_Delicious();
	public static final FT_ULTRAKILL ULTRAKILL = new FT_ULTRAKILL();
	public static final FT_EXPLOSIVE EXPLOSIVE = new FT_EXPLOSIVE();
	public static final FT_Leaded LEADED = new FT_Leaded();
	
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
		
		NONE =					new FluidType("NONE",				0x888888, 0, 0, 0, EnumSymbol.NONE);
		WATER =					new FluidType("WATER",				0x3333FF, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		STEAM =					new FluidType("STEAM",				0xe5e5e5, 3, 0, 0, EnumSymbol.NONE).setTemp(100).setCompression(0.01D).addTraits(GASEOUS);
		HOTSTEAM =				new FluidType("HOTSTEAM",			0xE7D6D6, 4, 0, 0, EnumSymbol.NONE).setTemp(300).setCompression(0.1D).addTraits(GASEOUS);
		SUPERHOTSTEAM =			new FluidType("SUPERHOTSTEAM",		0xE7B7B7, 4, 0, 0, EnumSymbol.NONE).setTemp(450).setCompression(1D).addTraits(GASEOUS);
		ULTRAHOTSTEAM =			new FluidType("ULTRAHOTSTEAM",		0xE39393, 4, 0, 0, EnumSymbol.NONE).setTemp(600).setCompression(10D).addTraits(GASEOUS);
		COOLANT =				new FluidType("COOLANT",			0xd8fcff, 1, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		LAVA =					new FluidType("LAVA",				0xFF3300, 4, 0, 0, EnumSymbol.NOWATER).setTemp(1200).addTraits(LIQUID, VISCOUS);
		DEUTERIUM =				new FluidType("DEUTERIUM",			0x0000FF, 3, 4, 0, EnumSymbol.NONE).addTraits(new FT_Flammable(5_000), new FT_Combustible(FuelGrade.HIGH, 10_000), GASEOUS);
		TRITIUM =				new FluidType("TRITIUM",			0x000099, 3, 4, 0, EnumSymbol.RADIATION).addTraits(new FT_Flammable(5_000), new FT_Combustible(FuelGrade.HIGH, 10_000), GASEOUS, new FT_VentRadiation(0.001F));
		OIL =					new FluidType("OIL",				0x020202, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x424242)).addTraits(new FT_Flammable(10_000), LIQUID, VISCOUS);
		HOTOIL =				new FluidType("HOTOIL",				0x300900, 2, 3, 0, EnumSymbol.NONE).setTemp(350).addTraits(new FT_Flammable(10_000), LIQUID, VISCOUS);
		HEAVYOIL =				new FluidType("HEAVYOIL",			0x141312, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x513F39)).addTraits(new FT_Flammable(50_000), new FT_Combustible(FuelGrade.LOW, 25_000), LIQUID, VISCOUS);
		BITUMEN =				new FluidType("BITUMEN",			0x1f2426, 2, 0, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x5A5877)).addTraits(LIQUID, VISCOUS);
		SMEAR =					new FluidType("SMEAR",				0x190f01, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x624F3B)).addTraits(new FT_Flammable(50_000), LIQUID, VISCOUS);
		HEATINGOIL =			new FluidType("HEATINGOIL",			0x211806, 2, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x694235)).addTraits(new FT_Flammable(150_000), new FT_Combustible(FuelGrade.LOW, 100_000), LIQUID, VISCOUS);
		RECLAIMED =				new FluidType("RECLAIMED",			0x332b22, 2, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xF65723)).addTraits(new FT_Flammable(100_000), new FT_Combustible(FuelGrade.LOW, 200_000), LIQUID, VISCOUS);
		PETROIL =				new FluidType("PETROIL",			0x44413d, 1, 3, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x2369F6)).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 300_000), LIQUID);
		LUBRICANT =				new FluidType("LUBRICANT",			0x606060, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xF1CC05)).addTraits(LIQUID);
		NAPHTHA =				new FluidType("NAPHTHA",			0x595744, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x5F6D44)).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 200_000), LIQUID, VISCOUS);
		DIESEL =				new FluidType("DIESEL",				0xf2eed5, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xFF2C2C)).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.HIGH, 500_000), LIQUID);
		LIGHTOIL =				new FluidType("LIGHTOIL",			0x8c7451, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xB46B52)).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.MEDIUM, 500_000), LIQUID);
		KEROSENE =				new FluidType("KEROSENE",			0xffa5d2, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xFF377D)).addTraits(new FT_Flammable(300_000), new FT_Combustible(FuelGrade.AERO, 1_250_000), LIQUID);
		GAS =					new FluidType("GAS",				0xfffeed, 1, 4, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0xFF4545, 0xFFE97F)).addTraits(new FT_Flammable(10_000), GASEOUS);
		PETROLEUM = 			new FluidType("PETROLEUM",			0x7cb7c9, 1, 4, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0x5E7CFF, 0xFFE97F)).addTraits(new FT_Flammable(25_000), GASEOUS);
		LPG =					new FluidType("LPG",				0x4747EA, 1, 3, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.HIGH, 400_000), LIQUID);
		BIOGAS =				new FluidType("BIOGAS",				0xbfd37c, 1, 4, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0xC8FF1F, 0x303030)).addTraits(new FT_Flammable(25_000), GASEOUS);
		BIOFUEL =				new FluidType("BIOFUEL",			0xeef274, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x9EB623)).addTraits(new FT_Flammable(150_000), new FT_Combustible(FuelGrade.HIGH, 400_000), LIQUID);
		NITAN =					new FluidType("NITAN",				0x8018ad, 2, 4, 1, EnumSymbol.NONE).addContainers(new CD_Canister(0x6B238C)).addTraits(new FT_Flammable(2_000_000), new FT_Combustible(FuelGrade.HIGH, 5_000_000), LIQUID);
		UF6 =					new FluidType("UF6",				0xD1CEBE, 4, 0, 2, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.2F), new FT_Corrosive(15), GASEOUS);
		PUF6 =					new FluidType("PUF6",				0x4C4C4C, 4, 0, 4, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.1F), new FT_Corrosive(15), GASEOUS);
		SAS3 =					new FluidType("SAS3",				0x4ffffc, 5, 0, 4, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(1F), new FT_Corrosive(30), LIQUID);
		SCHRABIDIC =			new FluidType("SCHRABIDIC",			0x006B6B, 5, 0, 5, EnumSymbol.ACID).addTraits(new FT_VentRadiation(1F), new FT_Corrosive(75), new FT_Poison(true, 2), LIQUID);
		AMAT =					new FluidType("AMAT",				0x010101, 5, 0, 5, EnumSymbol.ANTIMATTER).addTraits(ANTI, GASEOUS);
		ASCHRAB =				new FluidType("ASCHRAB",			0xb50000, 5, 0, 5, EnumSymbol.ANTIMATTER).addTraits(ANTI, GASEOUS);
		ACID =					new FluidType("ACID",				0xfff7aa, 3, 0, 3, EnumSymbol.OXIDIZER).addTraits(new FT_Corrosive(40), LIQUID);
		WATZ =					new FluidType("WATZ",				0x86653E, 4, 0, 3, EnumSymbol.ACID).addTraits(new FT_Corrosive(60), new FT_VentRadiation(0.1F), LIQUID, VISCOUS);
		CRYOGEL =				new FluidType("CRYOGEL",			0x32ffff, 2, 0, 0, EnumSymbol.CROYGENIC).setTemp(-170).addTraits(LIQUID, VISCOUS);
		HYDROGEN =				new FluidType("HYDROGEN",			0x4286f4, 3, 4, 0, EnumSymbol.CROYGENIC).setTemp(-260).addContainers(new CD_Gastank(0x4286f4, 0xffffff)).addTraits(new FT_Flammable(5_000), new FT_Combustible(FuelGrade.HIGH, 10_000), LIQUID, EVAP);
		OXYGEN =				new FluidType("OXYGEN",				0x98bdf9, 3, 0, 0, EnumSymbol.CROYGENIC).setTemp(-100).addContainers(new CD_Gastank(0x98bdf9, 0xffffff)).addTraits(LIQUID, EVAP);
		XENON =					new FluidType("XENON",				0xba45e8, 0, 0, 0, EnumSymbol.ASPHYXIANT).addContainers(new CD_Gastank(0x8C21FF, 0x303030)).addTraits(GASEOUS);
		BALEFIRE =				new FluidType("BALEFIRE",			0x28e02e, 4, 4, 3, EnumSymbol.RADIATION).setTemp(1500).addTraits(new FT_Corrosive(50), new FT_Flammable(1_000_000), new FT_Combustible(FuelGrade.HIGH, 2_500_000), LIQUID, VISCOUS);
		MERCURY =				new FluidType("MERCURY",			0x808080, 2, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, new FT_Poison(false, 2));
		PAIN =					new FluidType("PAIN",				0x938541, 2, 0, 1, EnumSymbol.ACID).setTemp(300).addTraits(new FT_Corrosive(30), new FT_Poison(true, 2), LIQUID, VISCOUS);
		WASTEFLUID =			new FluidType("WASTEFLUID",			0x544400, 2, 0, 1, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.5F), NOCON, LIQUID, VISCOUS);
		WASTEGAS =				new FluidType("WASTEGAS",			0xB8B8B8, 2, 0, 1, EnumSymbol.RADIATION).addTraits(new FT_VentRadiation(0.5F), NOCON, GASEOUS);
		GASOLINE =				new FluidType("GASOLINE",			0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x2F7747)).addTraits(new FT_Flammable(400_000), new FT_Combustible(FuelGrade.HIGH, 1_000_000), LIQUID);
		COALGAS =				new FluidType("COALGAS",			0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x2E155F)).addTraits(new FT_Flammable(75_000), new FT_Combustible(FuelGrade.MEDIUM, 150_000), LIQUID);
		SPENTSTEAM =			new FluidType("SPENTSTEAM",			0x445772, 2, 0, 0, EnumSymbol.NONE).setCompression(1D).addTraits(NOCON, GASEOUS);
		FRACKSOL =				new FluidType("FRACKSOL",			0x798A6B, 1, 3, 3, EnumSymbol.ACID).addContainers(new CD_Canister(0x4F887F)).addTraits(new FT_Corrosive(15), new FT_Poison(false, 0), LIQUID, VISCOUS);
		PLASMA_DT =				new FluidType("PLASMA_DT",			0xF7AFDE, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3250).addTraits(NOCON, NOID, PLASMA);
		PLASMA_HD =				new FluidType("PLASMA_HD",			0xF0ADF4, 0, 4, 0, EnumSymbol.RADIATION).setTemp(2500).addTraits(NOCON, NOID, PLASMA);
		PLASMA_HT =				new FluidType("PLASMA_HT",			0xD1ABF2, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3000).addTraits(NOCON, NOID, PLASMA);
		PLASMA_XM =				new FluidType("PLASMA_XM",			0xC6A5FF, 0, 4, 1, EnumSymbol.RADIATION).setTemp(4250).addTraits(NOCON, NOID, PLASMA);
		PLASMA_BF =				new FluidType("PLASMA_BF",			0xA7F1A3, 4, 5, 4, EnumSymbol.ANTIMATTER).setTemp(8500).addTraits(NOCON, NOID, PLASMA);
		CARBONDIOXIDE =			new FluidType("CARBONDIOXIDE",		0x404040, 3, 0, 0, EnumSymbol.ASPHYXIANT).addTraits(GASEOUS);
		PLASMA_DH3 =			new FluidType("PLASMA_DH3",			0xFF83AA, 0, 4, 0, EnumSymbol.RADIATION).setTemp(3480).addTraits(NOCON, NOID, PLASMA);
		HELIUM3 =				new FluidType("HELIUM3",			0xFCF0C4, 3, 4, 0, EnumSymbol.ASPHYXIANT).addTraits(GASEOUS);
		DEATH =					new FluidType("DEATH",				0x717A88, 2, 0, 1, EnumSymbol.ACID).setTemp(300).addTraits(new FT_Corrosive(80), new FT_Poison(true, 4), LEADCON, LIQUID, VISCOUS);
		ETHANOL =				new FluidType("ETHANOL",			0xe0ffff, 2, 3, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xEAFFF3)).addTraits(new FT_Flammable(75_000), new FT_Combustible(FuelGrade.HIGH, 200_000), LIQUID);
		HEAVYWATER =			new FluidType("HEAVYWATER",			0x00a0b0, 1, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		CRACKOIL =				new FluidType("CRACKOIL",			0x020202, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x424242)).addTraits(new FT_Flammable(10_000), LIQUID, VISCOUS);
		COALOIL =				new FluidType("COALOIL",			0x020202, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x424242)).addTraits(new FT_Flammable(10_000), LIQUID, VISCOUS);
		HOTCRACKOIL =			new FluidType("HOTCRACKOIL",		0x300900, 2, 3, 0, EnumSymbol.NONE).setTemp(350).addContainers(new CD_Canister(0x424242)).addTraits(new FT_Flammable(10_000), LIQUID, VISCOUS);
		NAPHTHA_CRACK =			new FluidType("NAPHTHA_CRACK",		0x595744, 2, 1, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x5F6D44)).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 200_000), LIQUID, VISCOUS);
		LIGHTOIL_CRACK =		new FluidType("LIGHTOIL_CRACK",		0x8c7451, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xB46B52)).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.MEDIUM, 500_000), LIQUID);
		DIESEL_CRACK =			new FluidType("DIESEL_CRACK",		0xf2eed5, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xFF2C2C)).addTraits(new FT_Flammable(200_000), new FT_Combustible(FuelGrade.HIGH, 450_000), LIQUID);
		AROMATICS =				new FluidType("AROMATICS",			0x68A09A, 1, 4, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(25_000), LIQUID, VISCOUS);
		UNSATURATEDS =			new FluidType("UNSATURATEDS",		0x628FAE, 1, 4, 1, EnumSymbol.NONE).addTraits(new FT_Flammable(1_000_000), GASEOUS); //acetylene burns as hot as satan's asshole
		SALIENT =				new FluidType("SALIENT",			0x457F2D, 0, 0, 0, EnumSymbol.NONE).addTraits(DELICIOUS, LIQUID, VISCOUS);
		XPJUICE =				new FluidType("XPJUICE",			0xBBFF09, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS);
		ENDERJUICE =			new FluidType("ENDERJUICE",			0x127766, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS);
		PETROIL_LEADED =		new FluidType("PETROIL_LEADED",		0x44413d, 1, 3, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x2331F6)).addTraits(new FT_Flammable(125_000), new FT_Combustible(FuelGrade.MEDIUM, 450_000), LIQUID, LEADED);
		GASOLINE_LEADED =		new FluidType("GASOLINE_LEADED",	0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x2F775A)).addTraits(new FT_Flammable(400_000), new FT_Combustible(FuelGrade.HIGH, 1_500_000), LIQUID, LEADED);
		COALGAS_LEADED =		new FluidType("COALGAS_LEADED",		0x445772, 1, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x1E155F)).addTraits(new FT_Flammable(75_000), new FT_Combustible(FuelGrade.MEDIUM, 250_000), LIQUID, LEADED);
		SULFURIC_ACID =			new FluidType("SULFURIC_ACID",		0xB0AA64, 3, 0, 2, EnumSymbol.ACID).addTraits(new FT_Corrosive(50), LIQUID);
		COOLANT_HOT =			new FluidType("COOLANT_HOT",		0x99525E, 1, 0, 0, EnumSymbol.NONE).setTemp(600).addTraits(LIQUID);
		MUG =					new FluidType("MUG",				0x4B2D28, 0, 0, 0, EnumSymbol.NONE).addTraits(DELICIOUS, LIQUID);
		MUG_HOT =				new FluidType("MUG_HOT",			0x6B2A20, 0, 0, 0, EnumSymbol.NONE).setTemp(500).addTraits(DELICIOUS, LIQUID);
		WOODOIL =				new FluidType("WOODOIL",			0x847D54, 2, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xBF7E4F)).addTraits(LIQUID);
		COALCREOSOTE =			new FluidType("COALCREOSOTE",		0x51694F, 3, 2, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x285A3F)).addTraits(LIQUID);
		SEEDSLURRY =			new FluidType("SEEDSLURRY",			0x7CC35E, 0, 0, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0x7CC35E)).addTraits(LIQUID);
		NITROGEN =				new FluidType("NITROGEN",			0xB3C6D2, 1, 0, 0, EnumSymbol.CROYGENIC).setTemp(-90).addTraits(LIQUID, EVAP);
		BLOOD =			    	new FluidType("BLOOD",				0x4D0000, 2, 0, 0, EnumSymbol.NONE).addContainers(0x4D0000, ExtContainer.CANISTER).addTraits(DELICIOUS, ULTRAKILL, LIQUID); 
		NITRIC_ACID =			new FluidType("NITRIC_ACID",		0xFFCC80, 3, 0, 3, EnumSymbol.ACID).addTraits(new FT_Poison(true, 4)).addTraits(new FT_Flammable(20_000), new FT_Combustible(FuelGrade.MEDIUM, 50_000), new FT_Corrosive(20), LIQUID);
		AMMONIA =				new FluidType("AMMONIA",			0x00A0F7, 2, 0, 1, EnumSymbol.ASPHYXIANT).addTraits(new FT_Poison(true, 4), GASEOUS);
		HYDRAZINE =				new FluidType("HYDRAZINE",			0x31517D, 2, 3, 2, EnumSymbol.NONE).addContainers(0x31517D, ExtContainer.CANISTER).addTraits((new FT_Flammable(500_000)), new FT_Combustible(FuelGrade.HIGH, 1_250_000), new FT_Corrosive(30), LIQUID);	
		BLOODGAS =				new FluidType("BLOODGAS",		    0x591000, 3, 1, 1, EnumSymbol.NONE).addContainers(new CD_Canister(0x591000)).addTraits(new FT_Flammable(1_000_000), new FT_Combustible(FuelGrade.AERO, 2_500_000)).addTraits(LIQUID);
		AIR =					new FluidType("AIR",				0xD1CEBE, 0, 0, 0, EnumSymbol.NONE).addTraits(GASEOUS);
		BLOOD_HOT =				new FluidType(89, "BLOOD_HOT",		0x890404, 3, 0, 0, EnumSymbol.NONE).addTraits(LIQUID).setTemp(666);//0xE4E3EF
		SOLVENT =				new FluidType(90, "SOLVENT",		0xE4E3EF, 2, 3, 0, EnumSymbol.NONE).addContainers(0xE4E3EF, ExtContainer.CANISTER).addTraits(LIQUID);
		HCL =					new FluidType("HCL",				0x00D452, 3, 0, 3, EnumSymbol.ACID).addTraits(new FT_Corrosive(30), LIQUID);
		MINSOL =				new FluidType("MINSOL",				0xFADF6A, 3, 0, 3, EnumSymbol.ACID).addTraits(new FT_Corrosive(10), LIQUID);
		SYNGAS =				new FluidType("SYNGAS",				0x131313, 1, 4, 2, EnumSymbol.NONE).addTraits(GASEOUS);
		OXYHYDROGEN =			new FluidType(94, "OXYHYDROGEN",	0x483FC1, 0, 4, 2, EnumSymbol.NONE).addTraits(GASEOUS);
		RADIOSOLVENT =			new FluidType("RADIOSOLVENT",		0xA4D7DD, 3, 3, 0, EnumSymbol.NONE).addTraits(LIQUID, LEADCON, new FT_Corrosive(50), new FT_VentRadiation(0.01F));
		CHLORINE =				new FluidType("CHLORINE",			0xBAB572, 3, 0, 0, EnumSymbol.OXIDIZER).addContainers(new CD_Gastank(0xBAB572, 0x887B34)).addTraits(GASEOUS, new FT_Corrosive(25));
		HEAVYOIL_VACUUM =		new FluidType("HEAVYOIL_VACUUM",	0x131214, 2, 1, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS).addContainers(new CD_Canister(0x513F39));
		REFORMATE =				new FluidType("REFORMATE",			0x835472, 2, 2, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS).addContainers(new CD_Canister(0xD180D6));
		LIGHTOIL_VACUUM =		new FluidType("LIGHTOIL_VACUUM",	0x8C8851, 1, 2, 0, EnumSymbol.NONE).addTraits(LIQUID).addContainers(new CD_Canister(0xB46B52));
		SOURGAS =				new FluidType("SOURGAS",			0xC9BE0D, 4, 4, 0, EnumSymbol.ACID).addContainers(new CD_Gastank(0xC9BE0D, 0x303030)).addTraits(GASEOUS, new FT_Corrosive(10), new FT_Poison(false, 1));
		XYLENE =				new FluidType("XYLENE",				0x5C4E76, 2, 3, 0, EnumSymbol.NONE).addTraits(LIQUID).addContainers(new CD_Canister(0xA380D6));
		NEON =  				new FluidType("NEON", 0xF1F600,		0, 0,0,EnumSymbol.CROYGENIC).addTraits(GASEOUS);
		ARGON = 				new FluidType("ARGON", 0xFD70D0,	0, 0,0,EnumSymbol.CROYGENIC).addTraits(GASEOUS);
		KRYPTON = 				new FluidType("KRYPTON", 0x9AC6E6,	0, 0,0,EnumSymbol.CROYGENIC).addTraits(GASEOUS);
		COFFEE =				new FluidType("COFFEE",				0x57493D, 0, 0, 0, EnumSymbol.NONE).addTraits(DELICIOUS, LIQUID);
		TEA =					new FluidType("TEA",				0x76523C, 0, 0, 0, EnumSymbol.NONE).addTraits(DELICIOUS, LIQUID);
		HONEY =					new FluidType("HONEY",				0xD99A02, 0, 0, 0, EnumSymbol.NONE).addTraits(DELICIOUS, LIQUID);
		HEATINGOIL_VACUUM =		new FluidType("HEATINGOIL_VACUUM",	0x211D06, 2, 2, 0, EnumSymbol.NONE).addTraits(LIQUID).addContainers(new CD_Canister(0x694235));
		DIESEL_REFORM =			new FluidType("DIESEL_REFORM",		0xCDC3C6, 1, 2, 0, EnumSymbol.NONE).addTraits(LIQUID).addContainers(new CD_Canister(0xFFC500));
		DIESEL_CRACK_REFORM =	new FluidType("DIESEL_CRACK_REFORM",0xCDC3CC, 1, 2, 0, EnumSymbol.NONE).addTraits(LIQUID).addContainers(new CD_Canister(0xFFC500));
		KEROSENE_REFORM =		new FluidType("KEROSENE_REFORM",	0xFFA5F3, 1, 2, 0, EnumSymbol.NONE).addTraits(LIQUID).addContainers(new CD_Canister(0xFF377D));
		REFORMGAS =				new FluidType("REFORMGAS",			0x6362AE, 1, 4, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0x9392FF, 0xFFB992)).addTraits(GASEOUS);
		MILK =					new FluidType("MILK",				0xCFCFCF, 0, 0, 0, EnumSymbol.NONE).addTraits(DELICIOUS, LIQUID);//F5DEE4
		SMILK =					new FluidType("SMILK",				0xF5DEE4, 0, 0, 0, EnumSymbol.NONE).addTraits(DELICIOUS, LIQUID);
		OLIVEOIL =				new FluidType("OLIVEOIL",			0xA9B98E, 0, 0, 0, EnumSymbol.NONE).addTraits(DELICIOUS, LIQUID);
		COLLOID =				new FluidType("COLLOID",			0x787878, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS);
		PHOSGENE =				new FluidType("PHOSGENE",			0xCFC4A4, 4, 0, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0xCFC4A4, 0x361414)).addTraits(GASEOUS);
		MUSTARDGAS =			new FluidType("MUSTARDGAS",			0xBAB572, 4, 1, 1, EnumSymbol.NONE).addContainers(new CD_Gastank(0xBAB572, 0x361414)).addTraits(GASEOUS);
		IONGEL =				new FluidType("IONGEL",				0xB8FFFF, 1, 0, 4, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS);
		ELBOWGREASE =			new FluidType("ELBOWGREASE",		0xCBC433, 1, 3, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xCBC433)).addTraits(new FT_Flammable(600_000), LIQUID);
		NMASSTETRANOL =			new FluidType("NMASSTETRANOL",		0xF1DB0F, 1, 3, 0, EnumSymbol.NONE).addContainers(new CD_Canister(0xF1DB0F)).addTraits(new FT_Flammable(1_000_000), LIQUID, new FT_Corrosive(70), new FT_Poison(true, 0), new FT_VentRadiation(0.01F));
		NMASS =					new FluidType("NMASS",				0x53A9F4, 1, 2, 0, EnumSymbol.NONE).addTraits(LIQUID, new FT_Corrosive(10), new FT_Poison(true, 0), new FT_VentRadiation(0.04F));
		RIZZ =					new FluidType("RIZZ",				0x53A9F4, 1, 2, 0, EnumSymbol.NONE).addTraits(LIQUID, new FT_Corrosive(10), new FT_Poison(true, 0));
		HTcO4 =					new FluidType("HTcO4",				0x675454, 1, 3, 0, EnumSymbol.RADIATION).addTraits(LIQUID, new FT_Corrosive(10), new FT_VentRadiation(0.5F));
		OIL_COKER =				new FluidType("OIL_COKER",			0x001802, 2, 1, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS);
		NAPHTHA_COKER =			new FluidType("NAPHTHA_COKER",		0x495944, 2, 1, 0, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS);
		GAS_COKER =				new FluidType("GAS_COKER",			0xDEF4CA, 1, 4, 0, EnumSymbol.NONE).addTraits(GASEOUS);
		EGG =					new FluidType("EGG",				0xD2C273, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		CHOLESTEROL =			new FluidType("CHOLESTEROL",		0xD6D2BD, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		ESTRADIOL =				new FluidType("ESTRADIOL",			0xCDD5D8, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID);
		FISHOIL =				new FluidType("FISHOIL",			0x4B4A45, 0, 1, 0, EnumSymbol.NONE).addTraits(LIQUID);
		SUNFLOWEROIL =			new FluidType("SUNFLOWEROIL",		0xCBAD45, 0, 1, 0, EnumSymbol.NONE).addTraits(LIQUID);
		NITROGLYCERIN =			new FluidType("NITROGLYCERIN",		0x92ACA6, 0, 4, 0, EnumSymbol.NONE).addTraits(LIQUID, EXPLOSIVE);
		REDMUD =				new FluidType("REDMUD",				0xD85638, 3, 0, 4, EnumSymbol.NONE).addTraits(LIQUID, VISCOUS, LEADCON, new FT_Corrosive(60), new FT_Flammable(1_000));
		CHLOROCALCITE_SOLUTION = new FluidType("CHLOROCALCITE_SOLUTION", 0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, NOCON, new FT_Corrosive(60));
		CHLOROCALCITE_MIX =		new FluidType("CHLOROCALCITE_MIX",	0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, NOCON, new FT_Corrosive(60));
		CHLOROCALCITE_CLEANED =	new FluidType("CHLOROCALCITE_CLEANED", 0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, NOCON, new FT_Corrosive(60));
		POTASSIUM_CHLORIDE =	new FluidType("POTASSIUM_CHLORIDE",	0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, NOCON, new FT_Corrosive(60));
		CALCIUM_CHLORIDE =		new FluidType("CALCIUM_CHLORIDE", 0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, NOCON, new FT_Corrosive(60));
		CALCIUM_SOLUTION =		new FluidType(140, "CALCIUM_SOLUTION", 0x808080, 0, 0, 0, EnumSymbol.NONE).addTraits(LIQUID, NOCON, new FT_Corrosive(60));
		
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
		//blood
		metaOrder.add(BLOOD);
		metaOrder.add(BLOODGAS);
		metaOrder.add(BLOOD_HOT);
		//pure elements, cyogenic gasses
		metaOrder.add(HYDROGEN);
		metaOrder.add(DEUTERIUM);
		metaOrder.add(TRITIUM);
		metaOrder.add(HELIUM3);
		metaOrder.add(OXYGEN);
		metaOrder.add(XENON);
		metaOrder.add(CHLORINE);
		metaOrder.add(MERCURY);
		metaOrder.add(NITROGEN);
		metaOrder.add(RIZZ);
		//oils, fuels
		metaOrder.add(OIL);
		metaOrder.add(CRACKOIL);
		metaOrder.add(COALOIL);
		metaOrder.add(OIL_COKER);
		metaOrder.add(HOTOIL);
		metaOrder.add(HOTCRACKOIL);
		metaOrder.add(HEAVYOIL);
		metaOrder.add(HEAVYOIL_VACUUM);
		metaOrder.add(NAPHTHA);
		metaOrder.add(NAPHTHA_CRACK);
		metaOrder.add(NAPHTHA_COKER);
		metaOrder.add(REFORMATE);
		metaOrder.add(LIGHTOIL);
		metaOrder.add(LIGHTOIL_CRACK);
		metaOrder.add(LIGHTOIL_VACUUM);
		metaOrder.add(BITUMEN);
		metaOrder.add(SMEAR);
		metaOrder.add(HEATINGOIL);
		metaOrder.add(HEATINGOIL_VACUUM);
		metaOrder.add(RECLAIMED);
		metaOrder.add(LUBRICANT);
		metaOrder.add(GAS);
		metaOrder.add(GAS_COKER);
		metaOrder.add(PETROLEUM);
		metaOrder.add(SOURGAS);
		metaOrder.add(LPG);
		metaOrder.add(SYNGAS);
		metaOrder.add(OXYHYDROGEN);
		metaOrder.add(AROMATICS);
		metaOrder.add(UNSATURATEDS);
		metaOrder.add(XYLENE);
		metaOrder.add(REFORMGAS);
		metaOrder.add(DIESEL);
		metaOrder.add(DIESEL_REFORM);
		metaOrder.add(DIESEL_CRACK);
		metaOrder.add(DIESEL_CRACK_REFORM);
		metaOrder.add(KEROSENE);
		metaOrder.add(KEROSENE_REFORM);
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
		metaOrder.add(NMASSTETRANOL);
		metaOrder.add(FISHOIL);
		metaOrder.add(SUNFLOWEROIL);
		metaOrder.add(NITAN);
		metaOrder.add(HYDRAZINE);
		metaOrder.add(BALEFIRE);
		//processing fluids
		metaOrder.add(SALIENT);
		metaOrder.add(SEEDSLURRY);
		metaOrder.add(COLLOID);
		metaOrder.add(IONGEL);
		metaOrder.add(ACID);
		metaOrder.add(SULFURIC_ACID);
		//NITRIC_ACID
		metaOrder.add(AIR); //do it for love, do it for life, for mankiiiiiind!!
		metaOrder.add(NITRIC_ACID);
		metaOrder.add(HCL);
		metaOrder.add(AMMONIA);
		metaOrder.add(SOLVENT);
		metaOrder.add(RADIOSOLVENT);
		metaOrder.add(HTcO4);
		metaOrder.add(SCHRABIDIC);
		metaOrder.add(UF6);
		metaOrder.add(PUF6);
		metaOrder.add(SAS3);
		metaOrder.add(PAIN);
		metaOrder.add(DEATH);
		metaOrder.add(WATZ);
		metaOrder.add(REDMUD);
		metaOrder.add(EGG);
		metaOrder.add(CHOLESTEROL);
		metaOrder.add(CHLOROCALCITE_SOLUTION);
		metaOrder.add(CHLOROCALCITE_MIX);
		metaOrder.add(CHLOROCALCITE_CLEANED);
		metaOrder.add(POTASSIUM_CHLORIDE);
		metaOrder.add(CALCIUM_CHLORIDE);
		metaOrder.add(CALCIUM_SOLUTION);
		//solutions and working fluids
		metaOrder.add(FRACKSOL);
		//the fun guys
		metaOrder.add(PHOSGENE);
		metaOrder.add(MUSTARDGAS);
		metaOrder.add(ESTRADIOL);
		metaOrder.add(NITROGLYCERIN);
		//antimatter
		metaOrder.add(AMAT);
		metaOrder.add(ASCHRAB);
		//nuclear waste
		metaOrder.add(WASTEFLUID);
		metaOrder.add(WASTEGAS);
		//garbage
		metaOrder.add(XPJUICE);
		metaOrder.add(ENDERJUICE);
		//slurry
		metaOrder.add(MINSOL);
		metaOrder.add(NMASS);
		//plasma
		metaOrder.add(PLASMA_DT);
		metaOrder.add(PLASMA_HD);
		metaOrder.add(PLASMA_HT);
		metaOrder.add(PLASMA_DH3);
		metaOrder.add(PLASMA_XM);
		metaOrder.add(PLASMA_BF);
		//noble gasses
		metaOrder.add(KRYPTON);
		metaOrder.add(ARGON);
		metaOrder.add(NEON);
		//misc for cute dates
		metaOrder.add(TEA);
		metaOrder.add(HONEY);
		metaOrder.add(COFFEE);
		metaOrder.add(MILK);
		metaOrder.add(SMILK);
		metaOrder.add(OLIVEOIL);
		metaOrder.add(ELBOWGREASE);




		CHLORINE.addTraits(new FT_Toxin().addEntry(new ToxinDirectDamage(ModDamageSource.cloud, 2F, 20, HazardClass.GAS_CHLORINE, false)));
		PHOSGENE.addTraits(new FT_Toxin().addEntry(new ToxinDirectDamage(ModDamageSource.cloud, 4F, 20, HazardClass.GAS_CHLORINE, false)));
		MUSTARDGAS.addTraits(new FT_Toxin().addEntry(new ToxinDirectDamage(ModDamageSource.cloud, 4F, 10, HazardClass.GAS_CORROSIVE, false))
				.addEntry(new ToxinEffects(HazardClass.GAS_CORROSIVE, true).add(new PotionEffect(Potion.wither.id, 100, 1), new PotionEffect(Potion.confusion.id, 100, 0))));
		ESTRADIOL.addTraits(new FT_Toxin().addEntry(new ToxinEffects(HazardClass.PARTICLE_FINE, false).add(new PotionEffect(HbmPotion.death.id, 60 * 60 * 20, 0))));
		REDMUD.addTraits(new FT_Toxin().addEntry(new ToxinEffects(HazardClass.GAS_CORROSIVE, false).add(new PotionEffect(Potion.wither.id, 30 * 20, 2))));

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
			
		BLOOD.addTraits(new FT_Heatable().setEff(HeatingType.HEATEXCHANGER, 1.0D).addStep(300, 1, BLOOD_HOT, 1));
		BLOOD_HOT.addTraits(new FT_Coolable(BLOOD, 1, 1, 500).setEff(CoolingType.HEATEXCHANGER, 1.0D));
	
		

		
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
		double complexityCoker = 1.25D;
		double complexityChemplant = 1.1D;
		double complexityLubed = 1.15D;
		double complexityLeaded = 1.5D;
		double complexityVacuum = 3.0D;
		double complexityReform = 2.5D;
		double flammabilityLow = 0.25D;
		double flammabilityNormal = 1.0D;
		double flammabilityHigh = 2.0D;

		/// the allmighty excel spreadsheet has spoken! ///
		registerCalculatedFuel(OIL, (baseline / 1D * flammabilityLow * demandLow), 0, null);
		registerCalculatedFuel(CRACKOIL, (baseline / 1D * flammabilityLow * demandLow * complexityCracking), 0, null);
		registerCalculatedFuel(OIL_COKER, (baseline / 1D * flammabilityLow * demandLow * complexityCoker), 0, null);
		registerCalculatedFuel(GAS, (baseline / 1D * flammabilityNormal * demandVeryLow), 1.25, FuelGrade.GAS);
		registerCalculatedFuel(GAS_COKER, (baseline / 1D * flammabilityNormal * demandVeryLow * complexityCoker), 1.25, FuelGrade.GAS);
		registerCalculatedFuel(HEAVYOIL, (baseline / 0.5 * flammabilityLow * demandLow * complexityRefinery), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(SMEAR, (baseline / 0.35 * flammabilityLow * demandLow * complexityRefinery * complexityFraction), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(RECLAIMED, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(PETROIL, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant * complexityLubed), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(PETROIL_LEADED, (baseline / 0.28 * flammabilityLow * demandLow * complexityRefinery * complexityFraction * complexityChemplant * complexityLubed * complexityLeaded), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(HEATINGOIL, (baseline / 0.31 * flammabilityNormal * demandLow * complexityRefinery * complexityFraction * complexityFraction), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(NAPHTHA, (baseline / 0.25 * flammabilityLow * demandLow * complexityRefinery), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(NAPHTHA_CRACK, (baseline / 0.40 * flammabilityLow * demandLow * complexityRefinery * complexityCracking), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(NAPHTHA_COKER, (baseline / 0.25 * flammabilityLow * demandLow * complexityCoker), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(GASOLINE, (baseline / 0.20 * flammabilityNormal * demandLow * complexityRefinery * complexityChemplant), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(GASOLINE_LEADED, (baseline / 0.20 * flammabilityNormal * demandLow * complexityRefinery * complexityChemplant * complexityLeaded), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(DIESEL, (baseline / 0.21 * flammabilityNormal * demandLow * complexityRefinery * complexityFraction), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(DIESEL_CRACK, (baseline / 0.28 * flammabilityNormal * demandLow * complexityRefinery * complexityCracking * complexityFraction), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(LIGHTOIL, (baseline / 0.15 * flammabilityNormal * demandHigh * complexityRefinery), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(LIGHTOIL_CRACK, (baseline / 0.30 * flammabilityNormal * demandHigh * complexityRefinery * complexityCracking), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(KEROSENE, (baseline / 0.09 * flammabilityNormal * demandHigh * complexityRefinery * complexityFraction), 1.5D, FuelGrade.AERO);
		registerCalculatedFuel(PETROLEUM, (baseline / 0.10 * flammabilityNormal * demandMedium * complexityRefinery), 1.25, FuelGrade.GAS);
		registerCalculatedFuel(AROMATICS, (baseline / 0.15 * flammabilityLow * demandHigh * complexityRefinery * complexityCracking), 0, null);
		registerCalculatedFuel(UNSATURATEDS, (baseline / 0.15 * flammabilityHigh * demandHigh * complexityRefinery * complexityCracking), 0, null);
		registerCalculatedFuel(LPG, (baseline / 0.05 * flammabilityNormal * demandMedium * complexityRefinery * complexityChemplant), 2.5, FuelGrade.HIGH);
		registerCalculatedFuel(NITAN, KEROSENE.getTrait(FT_Flammable.class).getHeatEnergy() * 25L, 2.5, FuelGrade.HIGH);
		registerCalculatedFuel(BALEFIRE, KEROSENE.getTrait(FT_Flammable.class).getHeatEnergy() * 100L, 2.5, FuelGrade.HIGH);
		registerCalculatedFuel(BLOODGAS, KEROSENE.getTrait(FT_Flammable.class).getHeatEnergy() * 0.8, 2.5, FuelGrade.AERO); //0.8
		registerCalculatedFuel(HEAVYOIL_VACUUM, (baseline / 0.4 * flammabilityLow * demandLow * complexityVacuum), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(REFORMATE, (baseline / 0.25 * flammabilityNormal * demandHigh * complexityVacuum), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(LIGHTOIL_VACUUM, (baseline / 0.20 * flammabilityNormal * demandHigh * complexityVacuum), 1.5D, FuelGrade.MEDIUM);
		registerCalculatedFuel(SOURGAS, (baseline / 0.15 * flammabilityLow * demandVeryLow * complexityVacuum), 0, null);
		registerCalculatedFuel(XYLENE, (baseline / 0.15 * flammabilityNormal * demandMedium * complexityVacuum * complexityFraction), 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(HEATINGOIL_VACUUM, (baseline / 0.24 * flammabilityNormal * demandLow * complexityVacuum * complexityFraction), 1.25D, FuelGrade.LOW);
		registerCalculatedFuel(DIESEL_REFORM, DIESEL.getTrait(FT_Flammable.class).getHeatEnergy() * complexityReform, 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(DIESEL_CRACK_REFORM, DIESEL_CRACK.getTrait(FT_Flammable.class).getHeatEnergy() * complexityReform, 2.5D, FuelGrade.HIGH);
		registerCalculatedFuel(KEROSENE_REFORM, KEROSENE.getTrait(FT_Flammable.class).getHeatEnergy() * complexityReform, 1.5D, FuelGrade.AERO);
		registerCalculatedFuel(REFORMGAS, (baseline / 0.06 * flammabilityHigh * demandLow * complexityVacuum * complexityFraction), 1.25D, FuelGrade.GAS);
		registerCalculatedFuel(NMASSTETRANOL, BALEFIRE.getTrait(FT_Flammable.class).getHeatEnergy() * 1000, 10.5, FuelGrade.HIGH); //0.8

		//all hail the spreadsheet
		//the spreadsheet must not be questioned
		//none may enter the orb- i mean the spreadsheet
		
		int coalHeat = 400_000; // 200TU/t for 2000 ticks
		registerCalculatedFuel(COALOIL, (coalHeat * (1000 /* bucket */ / 100 /* mB per coal */) * flammabilityLow * demandLow * complexityChemplant), 0, null);
		long coaloil = COALOIL.getTrait(FT_Flammable.class).getHeatEnergy();
		registerCalculatedFuel(COALGAS, (coaloil / 0.3 * flammabilityNormal * demandMedium * complexityChemplant * complexityFraction), 1.5, FuelGrade.MEDIUM);
		registerCalculatedFuel(COALGAS_LEADED, (coaloil / 0.3 * flammabilityNormal * demandMedium * complexityChemplant * complexityFraction * complexityLeaded), 1.5, FuelGrade.MEDIUM);

		registerCalculatedFuel(ETHANOL, 275_000D /* diesel / 2 */, 2.5D, FuelGrade.HIGH);

		registerCalculatedFuel(BIOGAS, 250_000D * flammabilityLow /* biofuel with half compression, terrible flammability */, 1.25, FuelGrade.GAS);
		registerCalculatedFuel(BIOFUEL, 500_000D /* slightly below diesel */, 2.5D, FuelGrade.HIGH);

		registerCalculatedFuel(WOODOIL, 110_000 /* 20_000 TU per 250mB + a bonus */, 0, null);
		registerCalculatedFuel(COALCREOSOTE, 250_000 /* 20_000 TU per 100mB + a bonus */, 0, null);
		registerCalculatedFuel(FISHOIL, 75_000, 0, null);
		registerCalculatedFuel(SUNFLOWEROIL, 50_000, 0, null);

		registerCalculatedFuel(SOLVENT, 100_000, 0, null); // flammable, sure, but not combustable
		registerCalculatedFuel(RADIOSOLVENT, 150_000, 0, null);

		registerCalculatedFuel(SYNGAS, (coalHeat * (1000 /* bucket */ / 100 /* mB per coal */) * flammabilityLow * demandLow * complexityChemplant) * 2.5, 2.25, FuelGrade.GAS); //same as coal oil, +50% bonus
		registerCalculatedFuel(OXYHYDROGEN, 5_000, 3, FuelGrade.GAS); // whatever
		
		File folder = MainRegistry.configHbmDir;

		File config = new File(folder.getAbsolutePath() + File.separatorChar + "hbmFluids.json");
		File template = new File(folder.getAbsolutePath() + File.separatorChar + "_hbmFluids.json");
		
		if(!config.exists()) {
			writeDefault(template);
		} else {
			readConfig(config);
		}
	}
	
	private static void writeDefault(File file) {

		try {
			JsonWriter writer = new JsonWriter(new FileWriter(file));
			writer.setIndent("  ");
			writer.beginObject();
			
			for(FluidType type : metaOrder) {
				writer.name(type.getUnlocalizedName()).beginObject();
				
				for(Entry<Class<? extends FluidTrait>, FluidTrait> entry : type.traits.entrySet()) {
					writer.name(FluidTrait.traitNameMap.inverse().get(entry.getKey())).beginObject();
					entry.getValue().serializeJSON(writer);
					writer.endObject();
				}
				
				writer.endObject();
			}
			
			writer.endObject();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void readConfig(File config) {
		
		try {
			JsonObject json = gson.fromJson(new FileReader(config), JsonObject.class);
			
			for(FluidType type : metaOrder) {
				
				JsonElement element = json.get(type.getUnlocalizedName());
				if(element != null) {
					type.traits.clear();
					JsonObject obj = element.getAsJsonObject();
					
					for(Entry<String, JsonElement> entry : obj.entrySet()) {
						Class<? extends FluidTrait> traitClass = FluidTrait.traitNameMap.get(entry.getKey());
						try {
							FluidTrait trait = traitClass.newInstance();
							trait.deserializeJSON(entry.getValue().getAsJsonObject());
							type.addTraits(trait);
						} catch(Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
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
	
	public static class CD_Canister {
		public int color;
		public CD_Canister(int color) { this.color = color; }
	}
	
	public static class CD_Gastank {
		public int bottleColor, labelColor;
		public CD_Gastank(int color1, int color2) { this.bottleColor = color1; this.labelColor = color2; }
	}
}