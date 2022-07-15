package com.hbm.inventory.recipes;

import static com.hbm.blocks.ModBlocks.*;
import static com.hbm.inventory.OreDictManager.*;
import static com.hbm.inventory.fluid.Fluids.*;
import static com.hbm.inventory.recipes.RefineryRecipes.*;
import static com.hbm.items.ModItems.*;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.google.common.annotations.Beta;
import com.hbm.config.GeneralConfig;
import com.hbm.handler.imc.IMCChemPlant;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.util.ItemStackUtil;

import api.hbm.computer.Researchable;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
@Untested
@Beta
public class ChemPlantRecipesNT// extends SerializableRecipe
{
//	public static final HashMap<String, ChemPlantRecipe> recipes = new HashMap<>();
	public static final ArrayList<ChemPlantRecipe> recipes = new ArrayList<ChemPlantRecipesNT.ChemPlantRecipe>();
	
	@Untested
	@Beta
	public static class ChemPlantRecipe extends Researchable
	{
		public static final ChemPlantRecipe NULL = new ChemPlantRecipe("NONE").setItemInput(Library.filledArray(AStack.class, new ComparableStack(nothing), 4)).setItemOutput(Library.filledArray(ItemStack.class, new ItemStack(nothing), 4));
		public int time = 100;
		public String name = new String();
		public ResourceLocation icon;
		
		public FluidStack[] fIn = new FluidStack[2];
		public AStack[] inputs = new AStack[4];
		
		public FluidStack[] fOut = new FluidStack[2];
		public ItemStack[] outputs = new ItemStack[4];
		public ChemPlantRecipe(String name)
		{
			super(name);
			this.name = name;
			icon = new ResourceLocation("hbm", "chem_icon_".concat(name));
		}
		public ChemPlantRecipe(String name, int dura)
		{
			this(name);
			time = dura;
		}
		public ChemPlantRecipe setFluidInput(FluidStack f)
		{
			return setFluidInput(f, null);
		}
		public ChemPlantRecipe setFluidInput(FluidStack f1, @Nullable FluidStack f2)
		{
			fIn[0] = f1;
			fIn[1] = f2;
			return this;
		}
		public ChemPlantRecipe setItemInput(AStack...stacks)
		{
			assert stacks.length <= 4 : "Stack array length must be equal or less than 4!";
			
			for (byte i = 0; i < stacks.length; i++)
				inputs[i] = stacks[i];
			
			return this;
		}
		public ChemPlantRecipe setFluidOutput(FluidStack f)
		{
			return setFluidOutput(f, null);
		}
		public ChemPlantRecipe setFluidOutput(FluidStack f1, @Nullable FluidStack f2)
		{
			fOut[0] = f1;
			fOut[1] = f2;
			return this;
		}
		public ChemPlantRecipe setItemOutput(ItemStack...stacks)
		{
			assert stacks.length <= 4 : "Stack array length must be equal or less than 4!";
			
			for (int i = 0; i < stacks.length && i < 4; i++)
				outputs[i] = ItemStackUtil.carefulCopy(stacks[i]);
			
			return this;
		}
		public ChemPlantRecipe setIcon(String icon)
		{
			this.icon = new ResourceLocation("hbm", "chem_icon_".concat(icon));
			return this;
		}
		@Override
		public String getKey()
		{
			return name;
		}
		@Override
		public ChemPlantRecipe setPrevious(Researchable prev)
		{
			return (ChemPlantRecipe) super.setPrevious(prev);
		}
		@Override
		public ChemPlantRecipe setWork(long ram, long cpu)
		{
			return (ChemPlantRecipe) super.setWork(ram, cpu);
		}
	}

//	@Override
//	public String getFileName()
//	{
//		return "hbmChemicalPlant.json";
//	}
//
//	@Override
//	public Object getRecipeObject()
//	{
//		return recipes;
//	}
//
//	@Override
//	public void readRecipe(JsonElement recipe)
//	{
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void writeRecipe(Object recipe, JsonWriter writer) throws IOException
//	{
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
	public static void registerDefaults()
	{
		recipes.add(new ChemPlantRecipe("FP_HEAVYOIL", 50)
				.setFluidInput(new FluidStack(HEAVYOIL, 1000))
				.setFluidOutput(new FluidStack(BITUMEN, heavy_frac_bitu * 10), new FluidStack(SMEAR, heavy_frac_smear * 10)));
		recipes.add(new ChemPlantRecipe("FP_SMEAR", 50)
				.setFluidInput(new FluidStack(SMEAR, 1000))
				.setFluidOutput(new FluidStack(HEATINGOIL, smear_frac_heat * 10), new FluidStack(LUBRICANT, smear_frac_lube * 10)));
		recipes.add(new ChemPlantRecipe("FP_NAPHTHA", 50)
				.setFluidInput(new FluidStack(NAPHTHA, 1000))
				.setFluidOutput(new FluidStack(HEATINGOIL, napht_frac_heat * 10), new FluidStack(DIESEL, napht_frac_diesel * 10)));
		recipes.add(new ChemPlantRecipe("FP_LIGHTOIL", 50)
				.setFluidInput(new FluidStack(LIGHTOIL, 1000))
				.setFluidOutput(new FluidStack(DIESEL, light_frac_diesel * 10), new FluidStack(KEROSENE, light_frac_kero * 10)));
		recipes.add(new ChemPlantRecipe("FR_REOIL", 30)
				.setFluidInput(new FluidStack(SMEAR, 1000))
				.setFluidOutput(new FluidStack(RECLAIMED, 800)));
		recipes.add(new ChemPlantRecipe("FR_PETROIL", 30)
				.setFluidInput(new FluidStack(RECLAIMED, 800), new FluidStack(LUBRICANT, 200))
				.setFluidOutput(new FluidStack(PETROIL, 1000)));
		recipes.add(new ChemPlantRecipe("BP_BIOGAS", 200)
				.setItemInput(new ComparableStack(biomass, 16))
				.setFluidOutput(new FluidStack(BIOGAS, 4000)));
		recipes.add(new ChemPlantRecipe("BP_BIOFUEL")
				.setFluidInput(new FluidStack(BIOGAS, 2000))
				.setFluidOutput(new FluidStack(BIOFUEL, 1000)));
		recipes.add(new ChemPlantRecipe("LPG")
				.setFluidInput(new FluidStack(PETROLEUM, 2000))
				.setFluidOutput(new FluidStack(LPG, 1000)));
		recipes.add(new ChemPlantRecipe("OIL_SAND", 200)
				.setItemInput(new ComparableStack(ore_oil_sand, 16))
				.setFluidInput(new FluidStack(BITUMEN, 400))
				.setFluidOutput(new FluidStack(BITUMEN, 1000)));
		recipes.add(new ChemPlantRecipe("ASPHALT")
				.setItemInput(new OreDictStack("sand", 6), new ComparableStack(Blocks.gravel, 2))
				.setFluidInput(new FluidStack(BITUMEN, 1000))
				.setItemOutput(new ItemStack(asphalt, 4), new ItemStack(asphalt, 4), new ItemStack(asphalt, 4), new ItemStack(asphalt, 4)));
		recipes.add(new ChemPlantRecipe("COOLANT", 50)
				.setItemInput(new OreDictStack(KNO.dust()))
				.setFluidInput(new FluidStack(WATER, 1800))
				.setFluidOutput(new FluidStack(COOLANT, 2000)));
		recipes.add(new ChemPlantRecipe("CRYOGEL", 50)
				.setItemInput(new ComparableStack(powder_ice))
				.setFluidInput(new FluidStack(COOLANT, 1800))
				.setFluidOutput(new FluidStack(CRYOGEL, 2000)));
		recipes.add(new ChemPlantRecipe("DESH", 300)
				.setItemInput(new ComparableStack(powder_desh_mix))
				.setFluidInput(new FluidStack(MERCURY, 200), GeneralConfig.enableLBSMSimpleChemsitry ? null : new FluidStack(LIGHTOIL, 200))
				.setItemOutput(new ItemStack(ingot_desh)));
		recipes.add(new ChemPlantRecipe("NITAN", 50)
				.setItemInput(new ComparableStack(powder_nitan_mix, 2))
				.setFluidInput(new FluidStack(KEROSENE, 600), new FluidStack(MERCURY, 200))
				.setFluidOutput(new FluidStack(NITAN, 1000)));
		recipes.add(new ChemPlantRecipe("PEROXIDE", 50)
				.setFluidInput(new FluidStack(WATER, 1000))
				.setFluidOutput(new FluidStack(ACID, 800)));
		recipes.add(new ChemPlantRecipe("POLYMER")
				.setItemInput(new OreDictStack(COAL.dust(), 2), new OreDictStack(F.dust()))
				.setFluidInput(new FluidStack(PETROLEUM, 600))
				.setItemOutput(new ItemStack(ingot_polymer)));
		recipes.add(new ChemPlantRecipe("STEAM", 20)
				.setFluidInput(new FluidStack(WATER, 1000))
				.setFluidOutput(new FluidStack(STEAM, 1000)));
		recipes.add(new ChemPlantRecipe("YELLOWCAKE", 250)
				.setItemInput(new OreDictStack(U.billet(), 2), new OreDictStack(S.dust(), 2))
				.setFluidInput(new FluidStack(ACID, 500))
				.setItemOutput(new ItemStack(powder_yellowcake)));
		recipes.add(new ChemPlantRecipe("UF6")
				.setItemInput(new ComparableStack(powder_yellowcake), new OreDictStack(F.dust(), 4))
				.setFluidInput(new FluidStack(WATER, 1000))
				.setItemOutput(new ItemStack(sulfur, 2))
				.setFluidOutput(new FluidStack(UF6, 1200)));
		recipes.add(new ChemPlantRecipe("PUF6", 150)
				.setItemInput(new OreDictStack(PU.dust()), new OreDictStack(F.dust(), 3))
				.setFluidInput(new FluidStack(WATER, 1000))
				.setFluidOutput(new FluidStack(PUF6, 900)));
		recipes.add(new ChemPlantRecipe("SAS3", 200)
				.setItemInput(new OreDictStack(SA326.dust()), new OreDictStack(S.dust(), 2))
				.setFluidInput(new FluidStack(ACID, 2000))
				.setFluidOutput(new FluidStack(SAS3, 1000)));
		recipes.add(new ChemPlantRecipe("CORDITE", 40)
				.setItemInput(new OreDictStack(KNO.dust(), 2), new OreDictStack(KEY_PLANKS), new ComparableStack(Items.sugar))
				.setFluidInput(new FluidStack(HEATINGOIL, 200))
				.setItemOutput(new ItemStack(cordite, 4)));
		recipes.add(new ChemPlantRecipe("KEVLAR", 40)
				.setItemInput(new OreDictStack(KNO.dust(), 2), new ComparableStack(Items.brick), new OreDictStack(COAL.dust()))
				.setFluidInput(new FluidStack(PETROLEUM, 100))
				.setItemOutput(new ItemStack(plate_kevlar, 4)));
		recipes.add(new ChemPlantRecipe("CONCRETE")
				.setFluidInput(new FluidStack(2000, WATER), null)
				.setItemInput(new OreDictStack("sand", 8), new ComparableStack(Blocks.gravel, 8))
				.setItemOutput(new ItemStack(concrete_smooth, 4), new ItemStack(concrete_smooth, 4), new ItemStack(concrete_smooth, 4), new ItemStack(concrete_smooth, 4)));
		recipes.add(new ChemPlantRecipe("CONCRETE_ASBESTOS")
				.setItemInput(new OreDictStack("sand", 2), new ComparableStack(Blocks.gravel, 2), new OreDictStack(ASBESTOS.dust(), 4)));
		recipes.add(new ChemPlantRecipe("SOLID_FUEL", 200)
				.setItemInput(new ComparableStack(solid_fuel, 2), new OreDictStack(KNO.dust()), new OreDictStack(REDSTONE.dust()))
				.setFluidInput(new FluidStack(PETROLEUM, 200))
				.setItemOutput(new ItemStack(rocket_fuel, 4)));
		recipes.add(new ChemPlantRecipe("ELECTROLYSIS", 150)
				.setFluidInput(new FluidStack(WATER, 8000))
				.setFluidOutput(new FluidStack(HYDROGEN, 400), new FluidStack(OXYGEN, 400)));
		recipes.add(new ChemPlantRecipe("XENON", 300)
				.setFluidOutput(new FluidStack(XENON, 50)));
		recipes.add(new ChemPlantRecipe("XENON_OXY", 20)
				.setFluidInput(new FluidStack(OXYGEN, 250))
				.setFluidOutput(new FluidStack(XENON, 50)));
		recipes.add(new ChemPlantRecipe("SATURN", 60)
				.setItemInput(new OreDictStack(DURA.dust()), new OreDictStack(P_RED.dust()))
				.setFluidInput(new FluidStack(ACID, 100), new FluidStack(MERCURY, 50))
				.setItemOutput(new ItemStack(ingot_saturnite, 2)));
		recipes.add(new ChemPlantRecipe("BALEFIRE")
				.setItemInput(new ComparableStack(egg_balefire_shard))
				.setFluidInput(new FluidStack(KEROSENE, 6000))
				.setItemOutput(new ItemStack(powder_balefire))
				.setFluidOutput(new FluidStack(BALEFIRE, 8000)));
		recipes.add(new ChemPlantRecipe("SCHRABIDIC")
				.setItemInput(new ComparableStack(pellet_charged))
				.setFluidInput(new FluidStack(SAS3, 8000), new FluidStack(ACID, 6000))
				.setFluidOutput(new FluidStack(SCHRABIDIC, 16000)));
		recipes.add(new ChemPlantRecipe("SCHRABIDATE", 150)
				.setItemInput(new OreDictStack(FE.dust()))
				.setFluidInput(new FluidStack(SCHRABIDIC, 250))
				.setItemOutput(new ItemStack(powder_schrabidate)));
		recipes.add(new ChemPlantRecipe("QUARTZ")
				.setItemInput(new OreDictStack(SI.dust(), 4))
				.setFluidInput(new FluidStack(OXYGEN, 400))
				.setItemOutput(new ItemStack(Items.quartz, 3)));
		recipes.add(new ChemPlantRecipe("ACRYLIC", 150)
				.setItemInput(new OreDictStack(POLYMER.dust()))
				.setFluidInput(new FluidStack(BITUMEN, 900), new FluidStack(WATER, 3000))
				.setItemOutput(new ItemStack(powder_acrylic, 2), new ItemStack(powder_acrylic, 2), new ItemStack(powder_acrylic, 2), new ItemStack(powder_acrylic, 2)));
		recipes.add(new ChemPlantRecipe("OD_CD"));
		recipes.add(new ChemPlantRecipe("OD_DVD", 200));
		recipes.add(new ChemPlantRecipe("OD_BD", 250));
		recipes.add(new ChemPlantRecipe("OD_5D", 300));
		recipes.add(new ChemPlantRecipe("COLTAN_CLEANING", 60)
				.setItemInput(new OreDictStack(COLTAN.dust(), 2), new OreDictStack(COAL.dust()))
				.setFluidInput(new FluidStack(ACID, 250), new FluidStack(HYDROGEN, 500))
				.setItemOutput(new ItemStack(powder_coltan), new ItemStack(powder_niobium), new ItemStack(dust))
				.setFluidOutput(new FluidStack(WATER, 500)));
		recipes.add(new ChemPlantRecipe("COLTAN_PAIN", 120)
				.setItemInput(new ComparableStack(powder_coltan), new OreDictStack(F.dust()))
				.setFluidInput(new FluidStack(GAS, 1000), new FluidStack(OXYGEN, 500))
				.setFluidOutput(new FluidStack(PAIN, 1000)));
		recipes.add(new ChemPlantRecipe("COLTAN_CRYSTAL", 80)
				.setFluidInput(new FluidStack(PAIN, 1000), new FluidStack(ACID, 500))
				.setItemOutput(new ItemStack(gem_tantalium), new ItemStack(dust, 3))
				.setFluidOutput(new FluidStack(WATER, 250)));
		recipes.add(new ChemPlantRecipe("VIT_LIQUID")
				.setItemInput(new ComparableStack(sand_lead))
				.setFluidInput(new FluidStack(WASTEFLUID, 1000))
				.setItemOutput(new ItemStack(nuclear_waste_vitrified)));
		recipes.add(new ChemPlantRecipe("VIT_GAS")
				.setItemInput(new ComparableStack(sand_lead))
				.setFluidInput(new FluidStack(WASTEGAS, 1000))
				.setItemOutput(new ItemStack(nuclear_waste_vitrified)));
		recipes.add(new ChemPlantRecipe("VIT_SOLID", 250)
				.setItemInput(new ComparableStack(sand_lead), new ComparableStack(nuclear_waste), new OreDictStack(BR.dust()), new OreDictStack(B.dust()))
				.setItemOutput(new ItemStack(nuclear_waste_vitrified_tiny, 3)));
		recipes.add(new ChemPlantRecipe("TEL", 40)
				.setItemInput(new OreDictStack(ANY_TAR.any()), new OreDictStack(PB.dust()))
				.setFluidInput(new FluidStack(PETROLEUM, 100), new FluidStack(STEAM, 1000))
				.setItemOutput(new ItemStack(antiknock)));
		recipes.add(new ChemPlantRecipe("GASOLINE", 40)
				.setItemInput(new ComparableStack(antiknock))
				.setFluidInput(new FluidStack(PETROIL, 10000))
				.setFluidOutput(new FluidStack(GASOLINE, 12000)));
		recipes.add(new ChemPlantRecipe("FRACKSOL", 20)
				.setItemInput(new OreDictStack(S.dust()))
				.setFluidInput(new FluidStack(PETROLEUM, 100), new FluidStack(WATER, 1000))
				.setFluidOutput(new FluidStack(FRACKSOL, 1000)));
		recipes.add(new ChemPlantRecipe("HELIUM3", 200)
				.setItemInput(new ComparableStack(moon_turf, 8))
				.setFluidOutput(new FluidStack(HELIUM3, 1000)));
		recipes.add(new ChemPlantRecipe("OSMIRIDIUM_DEATH", 240)
				.setItemInput(new ComparableStack(powder_paleogenite), new OreDictStack(F.dust(), 8), new ComparableStack(nugget_bismuth, 4))
				.setFluidInput(new FluidStack(ACID, 1000)));
		recipes.add(new ChemPlantRecipe("ETHANOL", 50)
				.setItemInput(new ComparableStack(Items.sugar, 6)));
		recipes.add(new ChemPlantRecipe("METH", 30)
				.setItemInput(new ComparableStack(Items.wheat), new ComparableStack(Items.dye, 2, 3))
				.setFluidInput(new FluidStack(LUBRICANT, 400), new FluidStack(ACID, 400))
				.setItemOutput(new ItemStack(chocolate, 2), new ItemStack(chocolate, 2)));
		recipes.add(new ChemPlantRecipe("CO2", 60)
				.setFluidInput(new FluidStack(GAS, 1000))
				.setFluidOutput(new FluidStack(CARBONDIOXIDE, 1000)));
		recipes.add(new ChemPlantRecipe("HEAVY_ELECTROLYSIS", 150)
				.setFluidInput(new FluidStack(HEAVYWATER, 8000))
				.setFluidOutput(new FluidStack(DEUTERIUM, 400), new FluidStack(OXYGEN, 400)));
		recipes.add(new ChemPlantRecipe("DUCRETE", 150)
				.setItemInput(new OreDictStack("sand", 8), new OreDictStack(U238.billet(), 2), new ComparableStack(Items.clay_ball, 4))
				.setFluidInput(new FluidStack(WATER, 2000))
				.setItemOutput(new ItemStack(ducrete_smooth, 2), new ItemStack(ducrete_smooth, 2), new ItemStack(ducrete_smooth, 2), new ItemStack(ducrete_smooth, 2)));
		recipes.add(new ChemPlantRecipe("LF_BASE", 400)
				.setFluidInput(new FluidStack(8000, WATER), new FluidStack(1200, ACID))
				.setItemInput(new OreDictStack(AL.dust(), 2), new OreDictStack(BR.dust()), new OreDictStack(LI.dust(), 3), new OreDictStack(F.dust(), 3))
				.setFluidOutput(new FluidStack(1000, SALT)));
		recipes.add(new ChemPlantRecipe("ALCOHOL", 200)
				.setFluidInput(new FluidStack(500, PETROLEUM), new FluidStack(2000, WATER))
				.setItemInput(new OreDictStack(S.dust(), 2))
				.setFluidOutput(new FluidStack(1000, ALCOHOL)));
		recipes.add(new ChemPlantRecipe("SARIN", 1200)
				.setFluidInput(new FluidStack(2500, ALCOHOL), new FluidStack(4000, VITRIOL))
				.setItemInput(new OreDictStack(P_RED.dust(), 4), new OreDictStack(F.dust(), 4))
				.setFluidOutput(new FluidStack(1000, SARIN)));
		recipes.add(new ChemPlantRecipe("CF3", 2500)
				.setFluidInput(new FluidStack(24000, WATER))
				.setItemInput(new OreDictStack(F.dust(), 3))
				.setFluidOutput(new FluidStack(1000, WRATH)));
		recipes.add(new ChemPlantRecipe("H_BR", 1000)
				.setFluidInput(new FluidStack(2000, WATER))
				.setItemInput(new OreDictStack(BR.dust(), 2), new OreDictStack(S.dust()))
				.setFluidOutput(new FluidStack(500, H_BR), new FluidStack(250, VITRIOL)));
		recipes.add(new ChemPlantRecipe("NITER", 300)
				.setFluidInput(new FluidStack(WATER, 2000), new FluidStack(ACID, 1000))
				.setItemInput(new OreDictStack(KNO.dust(), 3))
				.setFluidOutput(new FluidStack(WATER, 1000)));
		recipes.add(new ChemPlantRecipe("NITRO", 250)
				.setFluidInput(new FluidStack(VITRIOL, 500), new FluidStack(NITER, 500))
				.setItemInput(new OreDictStack(KNO.dust()), new ComparableStack(Items.sugar))
				.setFluidOutput(new FluidStack(NITROGLYCERIN, 1000)));
		if (GeneralConfig.enable528)
			recipes.add(new ChemPlantRecipe("A_ALLOY", 800)
					.setFluidInput(new FluidStack(4000, ACID))
					.setItemInput(new OreDictStack(PB.dust(), 2), new OreDictStack(ST.dust(), 2), new OreDictStack(ND.dust()), new OreDictStack(CU.dust(), 3))
					.setItemOutput(new ItemStack(powder_advanced_alloy, 8)));
		
		if (!IMCChemPlant.buffer.isEmpty())
		{
			recipes.addAll(IMCChemPlant.buffer);
			MainRegistry.logger.info("Fetched " + IMCChemPlant.buffer.size() + " IMC chemical plant recipes!");
			IMCChemPlant.buffer.clear();
		}
		
		Researchable.addAll(recipes);
	}

//	@Override
	public static void deleteRecipes()
	{
		recipes.clear();
	}
}
