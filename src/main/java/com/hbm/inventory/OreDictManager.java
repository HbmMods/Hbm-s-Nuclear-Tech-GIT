package com.hbm.inventory;

import java.util.HashMap;

import javax.annotation.CheckForNull;
import javax.annotation.CheckReturnValue;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictManager
{
	public static final HashMap<String, Integer> sizeMap = new HashMap<String, Integer>();
	static
	{
		sizeMap.put("nugget", 1);
		sizeMap.put("tiny", 1);
		sizeMap.put("dustSmall", 1);
		sizeMap.put("dustTiny", 1);
		sizeMap.put("ingot", 9);
		sizeMap.put("plate", 9);
		sizeMap.put("gem", 9);
		sizeMap.put("dust", 9);
		sizeMap.put("material", 9);
		sizeMap.put("block", 81);
	}
	public static void registerOres()
	{
		new DictFrame("Uranium").billet(ModItems.billet_uranium).block(ModBlocks.block_uranium).crystal(ModItems.crystal_uranium).dust(ModItems.powder_uranium).ore(ModBlocks.ore_gneiss_uranium, ModBlocks.ore_gneiss_uranium_scorched, ModBlocks.ore_meteor_uranium, ModBlocks.ore_nether_uranium, ModBlocks.ore_nether_uranium_scorched, ModBlocks.ore_uranium, ModBlocks.ore_uranium_scorched).oreNether(ModBlocks.ore_nether_uranium, ModBlocks.ore_nether_uranium_scorched);
		new DictFrame("Uranium233", "U233").billet(ModItems.billet_u233).block(ModBlocks.block_u233).nugget(ModItems.nugget_u233).ingot(ModItems.ingot_u233);
		new DictFrame("Uranium234", "U234").billet(ModItems.billet_u234).nugget(ModItems.nugget_u234).ingot(ModItems.ingot_u234);
		new DictFrame("Uranium235", "U235").billet(ModItems.billet_u235).block(ModBlocks.block_u235).nugget(ModItems.nugget_u235).ingot(ModItems.ingot_u235).dust(ModItems.powder_u235);
		new DictFrame("Uranium238", "U238").billet(ModItems.billet_u238).block(ModBlocks.block_u238).nugget(ModItems.nugget_u238).ingot(ModItems.ingot_u238).dust(ModItems.powder_u238);
		new DictFrame("UraniumDioxide", "Uranium238Dioxide").dust(ModItems.powder_du_dioxide).ingot(ModItems.ingot_du_dioxide);
		new DictFrame("Thorium", "Thorium232", "Th232").billet(ModItems.billet_th232).block(ModBlocks.block_thorium).crystal(ModItems.crystal_thorium).dust(ModItems.powder_thorium).ore(ModBlocks.ore_meteor_thorium, ModBlocks.ore_thorium).ingot(ModItems.ingot_th232);
		new DictFrame("Plutonium").billet(ModItems.billet_plutonium).block(ModBlocks.block_plutonium).crystal(ModItems.crystal_plutonium).dust(ModItems.powder_plutonium).ingot(ModItems.ingot_plutonium).nugget(ModItems.nugget_plutonium).ore(ModBlocks.ore_nether_plutonium).oreNether(ModBlocks.ore_nether_plutonium);
		new DictFrame("Plutonium238", "Pu238").billet(ModItems.billet_pu238).block(ModBlocks.block_pu238).nugget(ModItems.nugget_pu238).ingot(ModItems.ingot_pu238);
		new DictFrame("Plutonium239", "Pu239").billet(ModItems.billet_pu239).block(ModBlocks.block_pu239).nugget(ModItems.nugget_pu239).ingot(ModItems.ingot_pu239).dust(ModItems.powder_pu239);
		new DictFrame("Plutonium240", "Pu240").billet(ModItems.billet_pu240).block(ModBlocks.block_pu240).nugget(ModItems.nugget_pu240).ingot(ModItems.ingot_pu240);
		new DictFrame("Neptunium237", "Np237").billet(ModItems.billet_neptunium).block(ModBlocks.block_neptunium).nugget(ModItems.nugget_neptunium).ingot(ModItems.ingot_neptunium);
		new DictFrame("Schrabidium", "Schrabidium326", "Sa326").billet(ModItems.billet_schrabidium).block(ModBlocks.block_schrabidium).crystal(ModItems.crystal_schrabidium).nugget(ModItems.nugget_schrabidium).ingot(ModItems.ingot_schrabidium).ore(ModBlocks.ore_gneiss_schrabidium, ModBlocks.ore_nether_schrabidium, ModBlocks.ore_schrabidium).oreNether(ModBlocks.ore_nether_schrabidium);
		new DictFrame("Polonium210", "Po210").billet(ModItems.billet_polonium).block(ModBlocks.block_polonium).dust(ModItems.powder_polonium).nugget(ModItems.nugget_polonium).ingot(ModItems.ingot_polonium);
		new DictFrame("Strontium90", "Sr90").ingot(ModItems.ingot_sr90).dust(ModItems.powder_sr90);
		new DictFrame("Actinium", "Actinium227", "Ac227").ingot(ModItems.ingot_actinium).block(ModBlocks.block_actinium).billet(ModItems.billet_actinium).dust(ModItems.powder_actinium).dustSmall(ModItems.powder_actinium_tiny).nugget(ModItems.nugget_actinium, ModItems.fragment_actinium);
		new DictFrame("Curium242", "Cm242").ingot(ModItems.ingot_cm242);
		new DictFrame("Curium243", "Cm243").ingot(ModItems.ingot_cm243);
		new DictFrame("Curium244", "Cm244").ingot(ModItems.ingot_cm244);
		new DictFrame("Curium245", "Cm245").ingot(ModItems.ingot_cm245);
		new DictFrame("Curium246", "Cm246").ingot(ModItems.ingot_cm246);
		new DictFrame("Curium247", "Cm247").ingot(ModItems.ingot_cm247);
		new DictFrame("Curium248", "Cm248").ingot(ModItems.ingot_cm248);
		new DictFrame("Curium250", "Cm250").ingot(ModItems.ingot_cm250);
		new DictFrame("Berkelium247", "Bk247").ingot(ModItems.ingot_bk247);
		new DictFrame("Berkelium249", "Bk249").ingot(ModItems.ingot_bk249);
		new DictFrame("Californium249", "Cf249").ingot(ModItems.ingot_cf249);
		new DictFrame("Californium251", "Cf251").ingot(ModItems.ingot_cf251);
		new DictFrame("Californium252", "Cf252").ingot(ModItems.ingot_cf252).billet(ModItems.billet_cf252).nugget(ModItems.nugget_cf252);
		new DictFrame("Einsteinium254", "Es254").ingot(ModItems.ingot_es254).dust(ModItems.powder_es254);
		new DictFrame("Mendelevium258", "Md258").ingot(ModItems.ingot_md258);
		new DictFrame("Copernicium285", "Cn285").ingot(ModItems.ingot_cn285);
		new DictFrame("Copernicium286", "Cn286").ingot(ModItems.ingot_cn286);
		new DictFrame("Titanium").block(ModBlocks.block_titanium).crystal(ModItems.crystal_titanium).dust(ModItems.powder_titanium).ore(ModBlocks.ore_titanium, ModBlocks.ore_meteor_titanium).ingot(ModItems.ingot_titanium).plate(ModItems.plate_titanium);
		new DictFrame("Steel").block(ModBlocks.block_steel).dust(ModItems.powder_steel).dustSmall(ModItems.powder_steel_tiny).ingot(ModItems.ingot_steel).plate(ModItems.plate_titanium);
		new DictFrame("Copper").block(ModBlocks.block_copper).crystal(ModItems.crystal_copper).dust(ModItems.powder_copper).ingot(ModItems.ingot_copper).ore(ModBlocks.ore_copper, ModBlocks.ore_gneiss_copper, ModBlocks.ore_meteor_copper).plate(ModItems.plate_copper);
		new DictFrame("Zirconium").billet(ModItems.billet_zirconium).dust(ModItems.powder_zirconium).ingot(ModItems.ingot_zirconium).nugget(ModItems.nugget_zirconium);
		OreDictionary.registerOre("ingotRedCopperAlloy", ModItems.ingot_red_copper);
		OreDictionary.registerOre("ingotAdvanced", ModItems.ingot_advanced_alloy);
		OreDictionary.registerOre("ingotAdvancedAlloy", ModItems.ingot_advanced_alloy);
		OreDictionary.registerOre("ingotTungsten", ModItems.ingot_tungsten);
		OreDictionary.registerOre("ingotAluminum", ModItems.ingot_aluminium);
		OreDictionary.registerOre("ingotBeryllium", ModItems.ingot_beryllium);
		OreDictionary.registerOre("ingotCobalt", ModItems.ingot_cobalt);
		OreDictionary.registerOre("ingotLead", ModItems.ingot_lead);
		OreDictionary.registerOre("ingotLithium", ModItems.lithium);
		OreDictionary.registerOre("ingotMagnetizedTungsten", ModItems.ingot_magnetized_tungsten);
		OreDictionary.registerOre("ingotCMBSteel", ModItems.ingot_combine_steel);
		OreDictionary.registerOre("ingotAustralium", ModItems.ingot_australium);
		OreDictionary.registerOre("ingotWeidanium", ModItems.ingot_weidanium);
		OreDictionary.registerOre("ingotReiium", ModItems.ingot_reiium);
		OreDictionary.registerOre("ingotUnobtainium", ModItems.ingot_unobtainium);
		OreDictionary.registerOre("ingotDaffergon", ModItems.ingot_daffergon);
		OreDictionary.registerOre("ingotVerticium", ModItems.ingot_verticium);
		OreDictionary.registerOre("ingotDuraSteel", ModItems.ingot_dura_steel);
		OreDictionary.registerOre("ingotPolymer", ModItems.ingot_polymer);
		OreDictionary.registerOre("ingotAcrylic", ModItems.acrylic);
		OreDictionary.registerOre("ingotLanthanium", ModItems.ingot_lanthanium);
		OreDictionary.registerOre("ingotLanthanum", ModItems.ingot_lanthanium);
		OreDictionary.registerOre("ingotActinium", ModItems.ingot_actinium);
		OreDictionary.registerOre("ingotActinium227", ModItems.ingot_actinium);
		OreDictionary.registerOre("ingotDeshAlloy", ModItems.ingot_desh);
		OreDictionary.registerOre("ingotSaturnite", ModItems.ingot_saturnite);
		OreDictionary.registerOre("ingotEuphemium", ModItems.ingot_euphemium);
		OreDictionary.registerOre("ingotDineutronium", ModItems.ingot_dineutronium);
		OreDictionary.registerOre("ingotStarmetal", ModItems.ingot_starmetal);
		OreDictionary.registerOre("ingotAsbestos", ModItems.ingot_asbestos);
		OreDictionary.registerOre("ingotZirconium", ModItems.ingot_zirconium);
		OreDictionary.registerOre("ingotSilicon", ModItems.ingot_silicon);
		OreDictionary.registerOre("ingotNiobium", ModItems.ingot_niobium);
		OreDictionary.registerOre("ingotNiobiumBeryllium", ModItems.ingot_nbbe);
		OreDictionary.registerOre("ingotNiobiumSuperalloy", ModItems.ingot_niobium_alloy);
		OreDictionary.registerOre("ingotTungstenCobalt", new ItemStack(ModItems.component_ftl, 1, 4));
		OreDictionary.registerOre("ingotNeodymium", ModItems.ingot_neodymium);

		OreDictionary.registerOre("nuggetLead", ModItems.nugget_lead);
		OreDictionary.registerOre("nuggetBeryllium", ModItems.nugget_beryllium);
		OreDictionary.registerOre("nuggetUranium", ModItems.nugget_uranium);
		OreDictionary.registerOre("nuggetUranium233", ModItems.nugget_u233);
		OreDictionary.registerOre("nuggetUranium235", ModItems.nugget_u235);
		OreDictionary.registerOre("nuggetUranium238", ModItems.nugget_u238);
		OreDictionary.registerOre("nuggetThorium", ModItems.nugget_th232);
		OreDictionary.registerOre("nuggetThorium232", ModItems.nugget_th232);
		OreDictionary.registerOre("nuggetPlutonium", ModItems.nugget_plutonium);
		OreDictionary.registerOre("nuggetPlutonium238", ModItems.nugget_pu238);
		OreDictionary.registerOre("nuggetPlutonium239", ModItems.nugget_pu239);
		OreDictionary.registerOre("nuggetPlutonium240", ModItems.nugget_pu240);
		OreDictionary.registerOre("nuggetAustralium", ModItems.nugget_australium);
		OreDictionary.registerOre("nuggetWeidanium", ModItems.nugget_weidanium);
		OreDictionary.registerOre("nuggetReiium", ModItems.nugget_reiium);
		OreDictionary.registerOre("nuggetUnobtainium", ModItems.nugget_unobtainium);
		OreDictionary.registerOre("nuggetDaffergon", ModItems.nugget_daffergon);
		OreDictionary.registerOre("nuggetVerticium", ModItems.nugget_verticium);
		OreDictionary.registerOre("nuggetEuphemium", ModItems.nugget_euphemium);
		OreDictionary.registerOre("nuggetNeptunium", ModItems.nugget_neptunium);
		OreDictionary.registerOre("nuggetNeptunium237", ModItems.nugget_neptunium);
		OreDictionary.registerOre("nuggetPolonium", ModItems.nugget_polonium);
		OreDictionary.registerOre("nuggetPolonium210", ModItems.nugget_polonium);
		OreDictionary.registerOre("nuggetSchrabidium", ModItems.nugget_schrabidium);
		OreDictionary.registerOre("nuggetZirconium", ModItems.nugget_zirconium);
		OreDictionary.registerOre("nuggetSilicon", ModItems.nugget_silicon);
		OreDictionary.registerOre("tinyU233", ModItems.nugget_u233);
		OreDictionary.registerOre("tinyU235", ModItems.nugget_u235);
		OreDictionary.registerOre("tinyU238", ModItems.nugget_u238);
		OreDictionary.registerOre("tinyTh232", ModItems.nugget_th232);
		OreDictionary.registerOre("tinyPu238", ModItems.nugget_pu238);
		OreDictionary.registerOre("tinyPu239", ModItems.nugget_pu239);
		OreDictionary.registerOre("tinyPu240", ModItems.nugget_pu240);

		OreDictionary.registerOre("nuggetNeodymium", ModItems.fragment_neodymium);
		OreDictionary.registerOre("nuggetCobalt", ModItems.fragment_cobalt);
		OreDictionary.registerOre("nuggetNiobium", ModItems.fragment_niobium);
		OreDictionary.registerOre("nuggetCerium", ModItems.fragment_cerium);
		OreDictionary.registerOre("nuggetLanthanium", ModItems.fragment_lanthanium);
		OreDictionary.registerOre("nuggetActinium", ModItems.fragment_actinium);
		OreDictionary.registerOre("nuggetActinium227", ModItems.fragment_actinium);
		
		OreDictionary.registerOre("dustIron", ModItems.powder_iron);
		OreDictionary.registerOre("dustGold", ModItems.powder_gold);
		OreDictionary.registerOre("dustUranium", ModItems.powder_uranium);
		OreDictionary.registerOre("dustThorium", ModItems.powder_thorium);
		OreDictionary.registerOre("dustPlutonium", ModItems.powder_plutonium);
		OreDictionary.registerOre("dustTitanium", ModItems.powder_titanium);
		OreDictionary.registerOre("dustTungsten", ModItems.powder_tungsten);
		OreDictionary.registerOre("dustCopper", ModItems.powder_copper);
		OreDictionary.registerOre("dustBeryllium", ModItems.powder_beryllium);
		OreDictionary.registerOre("dustAluminum", ModItems.powder_aluminium);
		OreDictionary.registerOre("dustDiamond", ModItems.powder_diamond);
		OreDictionary.registerOre("dustEmerald", ModItems.powder_emerald);
		OreDictionary.registerOre("dustLapis", ModItems.powder_lapis);
		OreDictionary.registerOre("dustCoal", ModItems.powder_coal);
		OreDictionary.registerOre("dustLignite", ModItems.powder_lignite);
		OreDictionary.registerOre("dustAdvanced", ModItems.powder_advanced_alloy);
		OreDictionary.registerOre("dustAdvancedAlloy", ModItems.powder_advanced_alloy);
		OreDictionary.registerOre("dustCMBSteel", ModItems.powder_combine_steel);
		OreDictionary.registerOre("dustMagnetizedTungsten", ModItems.powder_magnetized_tungsten);
		OreDictionary.registerOre("dustRedCopperAlloy", ModItems.powder_red_copper);
		OreDictionary.registerOre("dustSteel", ModItems.powder_steel);
		OreDictionary.registerOre("dustLithium", ModItems.powder_lithium);
		OreDictionary.registerOre("dustNetherQuartz", ModItems.powder_quartz);
		OreDictionary.registerOre("dustQuartz", ModItems.powder_quartz);
		OreDictionary.registerOre("dustAustralium", ModItems.powder_australium);
		OreDictionary.registerOre("dustWeidanium", ModItems.powder_weidanium);
		OreDictionary.registerOre("dustReiium", ModItems.powder_reiium);
		OreDictionary.registerOre("dustUnobtainium", ModItems.powder_unobtainium);
		OreDictionary.registerOre("dustDaffergon", ModItems.powder_daffergon);
		OreDictionary.registerOre("dustVerticium", ModItems.powder_verticium);
		OreDictionary.registerOre("dustDuraSteel", ModItems.powder_dura_steel);
		OreDictionary.registerOre("dustPolymer", ModItems.powder_polymer);
		OreDictionary.registerOre("dustAcrylic", ModItems.powder_acrylic);
		OreDictionary.registerOre("dustLanthanium", ModItems.powder_lanthanium);
		OreDictionary.registerOre("dustLanthanum", ModItems.powder_lanthanium);
		OreDictionary.registerOre("dustActinium", ModItems.powder_actinium);
		OreDictionary.registerOre("dustActinium227", ModItems.powder_actinium);
		OreDictionary.registerOre("dustDeshAlloy", ModItems.powder_desh);
		OreDictionary.registerOre("dustEuphemium", ModItems.powder_euphemium);
		OreDictionary.registerOre("dustDineutronium", ModItems.powder_dineutronium);
		OreDictionary.registerOre("dustSchrabidium", ModItems.powder_schrabidium);
		OreDictionary.registerOre("dustSulfur", ModItems.sulfur);
		OreDictionary.registerOre("dustNiter", ModItems.niter);
		OreDictionary.registerOre("dustSaltpeter", ModItems.niter);
		OreDictionary.registerOre("dustLead", ModItems.powder_lead);
		OreDictionary.registerOre("dustNeptunium", ModItems.powder_neptunium);
		OreDictionary.registerOre("dustNeptunium237", ModItems.powder_neptunium);
		OreDictionary.registerOre("dustPolonium", ModItems.powder_polonium);
		OreDictionary.registerOre("dustPolonium210", ModItems.powder_polonium);
		OreDictionary.registerOre("dustAsbestos", ModItems.powder_asbestos);
		OreDictionary.registerOre("dustPhosphorus", ModItems.powder_fire);
		OreDictionary.registerOre("dustZirconium", ModItems.powder_zirconium);
		OreDictionary.registerOre("dustSilicon", ModItems.powder_silicon);
		OreDictionary.registerOre("dustUranium238", ModItems.powder_u238);
		OreDictionary.registerOre("dustUraniumDioxide", ModItems.powder_du_dioxide);
		OreDictionary.registerOre("dustNiobiumBeryllium", ModItems.powder_nbbe);
		OreDictionary.registerOre("dustNiobiumSuperalloy", ModItems.powder_niobium_alloy);

		OreDictionary.registerOre("dustNeptunium", ModItems.powder_neptunium);
		OreDictionary.registerOre("dustIodine", ModItems.powder_iodine);
		OreDictionary.registerOre("dustThorium", ModItems.powder_thorium);
		OreDictionary.registerOre("dustAstatine", ModItems.powder_astatine);
		OreDictionary.registerOre("dustNeodymium", ModItems.powder_neodymium);
		OreDictionary.registerOre("dustCaesium", ModItems.powder_caesium);
		OreDictionary.registerOre("dustStrontium", ModItems.powder_strontium);
		OreDictionary.registerOre("dustCobalt", ModItems.powder_cobalt);
		OreDictionary.registerOre("dustBromine", ModItems.powder_bromine);
		OreDictionary.registerOre("dustNiobium", ModItems.powder_niobium);
		OreDictionary.registerOre("dustTennessine", ModItems.powder_tennessine);
		OreDictionary.registerOre("dustCerium", ModItems.powder_cerium);

		OreDictionary.registerOre("gemCoal", Items.coal);
		OreDictionary.registerOre("gemLignite", ModItems.lignite);
		OreDictionary.registerOre("dustFluorite", ModItems.fluorite);
		
		OreDictionary.registerOre("plateTitanium", ModItems.plate_titanium);
		OreDictionary.registerOre("plateAluminum", ModItems.plate_aluminium);
		OreDictionary.registerOre("plateDenseLead", ModItems.neutron_reflector);
		OreDictionary.registerOre("plateSteel", ModItems.plate_steel);
		OreDictionary.registerOre("plateLead", ModItems.plate_lead);
		OreDictionary.registerOre("plateCopper", ModItems.plate_copper);
		OreDictionary.registerOre("plateIron", ModItems.plate_iron);
		OreDictionary.registerOre("plateGold", ModItems.plate_gold);
		OreDictionary.registerOre("plateAdvanced", ModItems.plate_advanced_alloy);
		OreDictionary.registerOre("plateSchrabidium", ModItems.plate_schrabidium);
		OreDictionary.registerOre("plateCMBSteel", ModItems.plate_combine_steel);
		OreDictionary.registerOre("plateSaturnite", ModItems.plate_saturnite);

		OreDictionary.registerOre("oreUranium", ModBlocks.ore_uranium);
		OreDictionary.registerOre("oreUranium", ModBlocks.ore_uranium_scorched);
		OreDictionary.registerOre("oreThorium", ModBlocks.ore_thorium);
		OreDictionary.registerOre("oreTitanium", ModBlocks.ore_titanium);
		OreDictionary.registerOre("oreSchrabidium", ModBlocks.ore_schrabidium);
		OreDictionary.registerOre("oreSulfur", ModBlocks.ore_sulfur);
		OreDictionary.registerOre("oreNiter", ModBlocks.ore_niter);
		OreDictionary.registerOre("oreSaltpeter", ModBlocks.ore_niter);
		OreDictionary.registerOre("oreCopper", ModBlocks.ore_copper);
		OreDictionary.registerOre("oreTungsten", ModBlocks.ore_tungsten);
		OreDictionary.registerOre("oreAluminum", ModBlocks.ore_aluminium);
		OreDictionary.registerOre("oreFluorite", ModBlocks.ore_fluorite);
		OreDictionary.registerOre("oreLead", ModBlocks.ore_lead);
		OreDictionary.registerOre("oreBeryllium", ModBlocks.ore_beryllium);
		OreDictionary.registerOre("oreLignite", ModBlocks.ore_lignite);
		OreDictionary.registerOre("oreAustralium", ModBlocks.ore_australium);
		OreDictionary.registerOre("oreWeidanium", ModBlocks.ore_weidanium);
		OreDictionary.registerOre("oreReiium", ModBlocks.ore_reiium);
		OreDictionary.registerOre("oreUnobtainium", ModBlocks.ore_unobtainium);
		OreDictionary.registerOre("oreDaffergon", ModBlocks.ore_daffergon);
		OreDictionary.registerOre("oreVerticium", ModBlocks.ore_verticium);
		OreDictionary.registerOre("oreRareEarth", ModBlocks.ore_rare);
		OreDictionary.registerOre("oreCobalt", ModBlocks.ore_cobalt);

		OreDictionary.registerOre("oreIron", ModBlocks.ore_gneiss_iron);
		OreDictionary.registerOre("oreGold", ModBlocks.ore_gneiss_gold);
		OreDictionary.registerOre("oreUranium", ModBlocks.ore_gneiss_uranium);
		OreDictionary.registerOre("oreUranium", ModBlocks.ore_gneiss_uranium_scorched);
		OreDictionary.registerOre("oreCopper", ModBlocks.ore_gneiss_copper);
		OreDictionary.registerOre("oreAsbestos", ModBlocks.ore_gneiss_asbestos);
		OreDictionary.registerOre("oreLithium", ModBlocks.ore_gneiss_lithium);
		OreDictionary.registerOre("oreRareEarth", ModBlocks.ore_gneiss_rare);
		OreDictionary.registerOre("oreSchrabidium", ModBlocks.ore_gneiss_schrabidium);

		OreDictionary.registerOre("oreUranium", ModBlocks.ore_nether_uranium);
		OreDictionary.registerOre("oreUranium", ModBlocks.ore_nether_uranium_scorched);
		OreDictionary.registerOre("orePlutonium", ModBlocks.ore_nether_plutonium);
		OreDictionary.registerOre("oreTungsten", ModBlocks.ore_nether_tungsten);
		OreDictionary.registerOre("oreSulfur", ModBlocks.ore_nether_sulfur);
		OreDictionary.registerOre("oreSchrabidium", ModBlocks.ore_nether_schrabidium);
		OreDictionary.registerOre("oreCobalt", ModBlocks.ore_nether_cobalt);
		
		OreDictionary.registerOre("oreUranium", ModBlocks.ore_meteor_uranium);
		OreDictionary.registerOre("oreThorium", ModBlocks.ore_meteor_thorium);
		OreDictionary.registerOre("oreTitanium", ModBlocks.ore_meteor_titanium);
		OreDictionary.registerOre("oreSulfur", ModBlocks.ore_meteor_sulfur);
		OreDictionary.registerOre("oreCopper", ModBlocks.ore_meteor_copper);
		OreDictionary.registerOre("oreTungsten", ModBlocks.ore_meteor_tungsten);
		OreDictionary.registerOre("oreAluminum", ModBlocks.ore_meteor_aluminium);
		OreDictionary.registerOre("oreLead", ModBlocks.ore_meteor_lead);
		OreDictionary.registerOre("oreLithium", ModBlocks.ore_meteor_lithium);
		OreDictionary.registerOre("oreStarmetal", ModBlocks.ore_meteor_starmetal);
		OreDictionary.registerOre("oreAsbestos", ModBlocks.ore_asbestos);

		OreDictionary.registerOre("blockActinium", ModBlocks.block_actinium);
		OreDictionary.registerOre("blockActinium227", ModBlocks.block_actinium);
		OreDictionary.registerOre("blockThorium", ModBlocks.block_thorium);
		OreDictionary.registerOre("blockUranium", ModBlocks.block_uranium);
		OreDictionary.registerOre("blockTitanium", ModBlocks.block_titanium);
		OreDictionary.registerOre("blockSulfur", ModBlocks.block_sulfur);
		OreDictionary.registerOre("blockNiter", ModBlocks.block_niter);
		OreDictionary.registerOre("blockSaltpeter", ModBlocks.block_niter);
		OreDictionary.registerOre("blockCopper", ModBlocks.block_copper);
		OreDictionary.registerOre("blockRedCopperAlloy", ModBlocks.block_red_copper);
		OreDictionary.registerOre("blockAdvanced", ModBlocks.block_advanced_alloy);
		OreDictionary.registerOre("blockTungsten", ModBlocks.block_tungsten);
		OreDictionary.registerOre("blockAluminum", ModBlocks.block_aluminium);
		OreDictionary.registerOre("blockFluorite", ModBlocks.block_fluorite);
		OreDictionary.registerOre("blockSteel", ModBlocks.block_steel);
		OreDictionary.registerOre("blockLead", ModBlocks.block_lead);
		OreDictionary.registerOre("blockBeryllium", ModBlocks.block_beryllium);
		OreDictionary.registerOre("blockSchrabidium", ModBlocks.block_schrabidium);
		OreDictionary.registerOre("blockCMBSteel", ModBlocks.block_combine_steel);
		OreDictionary.registerOre("blockMagnetizedTungsten", ModBlocks.block_magnetized_tungsten);
		OreDictionary.registerOre("blockAustralium", ModBlocks.block_australium);
		OreDictionary.registerOre("blockWeidanium", ModBlocks.block_weidanium);
		OreDictionary.registerOre("blockReiium", ModBlocks.block_reiium);
		OreDictionary.registerOre("blockUnobtainium", ModBlocks.block_unobtainium);
		OreDictionary.registerOre("blockDaffergon", ModBlocks.block_daffergon);
		OreDictionary.registerOre("blockVerticium", ModBlocks.block_verticium);
		OreDictionary.registerOre("blockDeshAlloy", ModBlocks.block_desh);
		OreDictionary.registerOre("blockAsbestos", ModBlocks.block_asbestos);
		OreDictionary.registerOre("blockCobalt", ModBlocks.block_cobalt);
		OreDictionary.registerOre("blockEuphemium", ModBlocks.block_euphemium);
		OreDictionary.registerOre("blockLanthanium", ModBlocks.block_lanthanum);
		OreDictionary.registerOre("blockLanthanum", ModBlocks.block_lanthanum);
		OreDictionary.registerOre("blockLithium", ModBlocks.block_lithium);
		OreDictionary.registerOre("blockDineutronium", ModBlocks.block_dineutronium);
		OreDictionary.registerOre("blockSilicon", ModBlocks.block_silicon);
		OreDictionary.registerOre("blockPolymer", ModBlocks.block_polymer);
		OreDictionary.registerOre("blockFerrouranium", ModBlocks.block_ferrouranium);
		OreDictionary.registerOre("blockStaballoy", ModBlocks.block_staballoy);
		OreDictionary.registerOre("blockDuraSteel", ModBlocks.block_dura_steel);
		
		OreDictionary.registerOre("blockThorium", ModBlocks.block_thorium);
		OreDictionary.registerOre("blockThorium232", ModBlocks.block_thorium);
		OreDictionary.registerOre("blockUranium233", ModBlocks.block_u233);
		OreDictionary.registerOre("blockUranium235", ModBlocks.block_u235);
		OreDictionary.registerOre("blockUranium238", ModBlocks.block_u238);
		OreDictionary.registerOre("blockNeptunium", ModBlocks.block_neptunium);
		OreDictionary.registerOre("blockNeptunium237", ModBlocks.block_neptunium);
		OreDictionary.registerOre("blockPolonium", ModBlocks.block_polonium);
		OreDictionary.registerOre("blockPolonium210", ModBlocks.block_polonium);
		OreDictionary.registerOre("blockPlutonium", ModBlocks.block_plutonium);
		OreDictionary.registerOre("blockPlutonium238", ModBlocks.block_pu238);
		OreDictionary.registerOre("blockPlutonium239", ModBlocks.block_pu239);
		OreDictionary.registerOre("blockPlutonium240", ModBlocks.block_pu240);

		OreDictionary.registerOre("logWood", ModBlocks.pink_log);
		OreDictionary.registerOre("logWoodPink", ModBlocks.pink_log);
		OreDictionary.registerOre("plankWood", ModBlocks.pink_planks);
		OreDictionary.registerOre("plankWoodPink", ModBlocks.pink_planks);
		OreDictionary.registerOre("slabWood", ModBlocks.pink_slab);
		OreDictionary.registerOre("slabWoodPink", ModBlocks.pink_slab);
		OreDictionary.registerOre("stairWood", ModBlocks.pink_stairs);
		OreDictionary.registerOre("stairWoodPink", ModBlocks.pink_stairs);

		OreDictionary.registerOre("blockGlass", ModBlocks.glass_uranium);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_trinitite);
		OreDictionary.registerOre("blockGlass", ModBlocks.glass_polonium);
		OreDictionary.registerOre("blockGlassYellow", ModBlocks.glass_uranium);
		OreDictionary.registerOre("blockGlassLime", ModBlocks.glass_trinitite);
		OreDictionary.registerOre("blockGlassRed", ModBlocks.glass_polonium);
		
		OreDictionary.registerOre("blockConcrete", ModBlocks.concrete);
		OreDictionary.registerOre("blockConcrete", ModBlocks.concrete_smooth);
		OreDictionary.registerOre("concrete", ModBlocks.concrete);
		OreDictionary.registerOre("concrete", ModBlocks.concrete_smooth);
	}
	@CheckReturnValue
	public static String getMatFromKey(String key)
	{
		for (String prefix : sizeMap.keySet())
			if (key.startsWith(prefix) && !key.substring(prefix.length()).isEmpty())
				return key.substring(prefix.length());
		
		return new String();
	}
	@CheckReturnValue
	public static String getPrefixFromKey(String key)
	{
		for (String prefix : sizeMap.keySet())
			if (key.startsWith(prefix) && !key.substring(prefix.length()).isEmpty())
				return prefix;
		
		return new String();
	}
	
	public static class MaterialStack
	{
		public static final MaterialStack NULL_STACK = nullStack();
		private String material = new String();
		private int size = 0;
		/** Unspecified stack **/
		protected MaterialStack()
		{}
		/** Assumes stack is an ingot or equivalent **/
		public MaterialStack(String matIn)
		{
			material = matIn;
			size = 9;
		}
		
		public MaterialStack(String matIn, int size)
		{
			this(matIn);
			this.size = size;
		}
		
		public MaterialStack(OreDictStack stack)
		{
			this(getMatFromKey(stack.name), sizeMap.get(getPrefixFromKey(stack.name)) * stack.stacksize);
		}
		public MaterialStack(ComparableStack stack, int multi)
		{
			this(stack.getStack().getUnlocalizedName(), stack.stacksize * multi);
		}
		public MaterialStack(ComparableStack stack)
		{
			this(stack, sizeMap.get(getPrefixFromKey(stack.getDictKeys()[0])) * stack.stacksize);
		}
		@CheckReturnValue
		@CheckForNull
		public static MaterialStack materialStackHelper(AStack stack)
		{
			if (stack instanceof ComparableStack)
				return new MaterialStack((ComparableStack) stack);
			else if (stack instanceof OreDictStack)
				return new MaterialStack((OreDictStack) stack);
			else
				return NULL_STACK;
		}
		
		public boolean isStackApplicable(AStack stack)
		{
			if (stack instanceof ComparableStack)
			{
				ComparableStack cStack = (ComparableStack) stack;
				for (String key : cStack.getDictKeys())
					if (getMatFromKey(key).equals(material))
						return true;
			}
			else if (stack instanceof OreDictStack)
			{
				OreDictStack oStack = (OreDictStack) stack;
				return getMatFromKey(oStack.name).equals(material);
			}
			return false;
		}
		
		public int getSize()
		{
			return size;
		}
		/** Returns the total size of the stack formatted in standard blocks, ingots, and nuggets **/
		public int[] getAllSizes()
		{
			int[] sizes = new int[3];
			sizes[0] = Math.floorDiv(getSize(), 81);
			sizes[1] = Math.floorDiv(getSize() - (sizes[0] * 81), 9);
			sizes[2] = getSize() - ((sizes[0] * 81) + (sizes[1] * 9));
			return sizes;
		}
		
		public MaterialStack setSize(int i)
		{
			size = i;
			return this;
		}
		public void incrementSize(int i)
		{
			size += i;
		}
		public void decrementSize(int i)
		{
			size -= i;
			if (size < 0)
				size = 0;
		}
		
		public static MaterialStack nullStack()
		{
			return new MaterialStack();
		}
		
		@Override
		public String toString()
		{
			return String.format("Material name: %s; Size (in nuggets): %s", material, size);
		}
		@Override
		public MaterialStack clone()
		{
			return new MaterialStack(material, size);
		}
	}
	
	public static class DictFrame
	{
		String[] mats;
		@SafeVarargs
		public DictFrame(String...mats)
		{
			this.mats = mats;
		}
		public DictFrame nugget(Item...items)
		{
			return makeItems("nugget", items).makeItems("tiny", items);
		}
		public DictFrame ingot(Item...items)
		{
			return makeItems("ingot", items);
		}
		public DictFrame dustSmall(Item...items)
		{
			return makeItems("dustSmall", items).makeItems("dustTiny", items);
		}
		public DictFrame dust(Item...items)
		{
			return makeItems("dust", items);
		}
		public DictFrame gem(Item...items)
		{
			return makeItems("gem", items);
		}
		public DictFrame crystal(Item...items)
		{
			return makeItems("crystal", items);
		}
		public DictFrame plate(Item...items)
		{
			return makeItems("plate", items);
		}
		public DictFrame billet(Item...items)
		{
			return makeItems("billet", items);
		}
		public DictFrame block(Block...blocks)
		{
			return makeBlocks("block", blocks);
		}
		public DictFrame ore(Block...blocks)
		{
			return makeBlocks("ore", blocks);
		}
		public DictFrame oreNether(Block...blocks)
		{
			return makeBlocks("oreNether", blocks);
		}
		
		public DictFrame makeItems(String tag, Item...items)
		{
			for (String mat : mats)
				for (Item i : items)
					OreDictionary.registerOre(tag + mat, i);
			return this;
		}
		public DictFrame makeBlocks(String tag, Block...blocks)
		{
			for (String mat : mats)
				for (Block b : blocks)
					OreDictionary.registerOre(tag + mat, b);
			return this;
		}
	}
}
