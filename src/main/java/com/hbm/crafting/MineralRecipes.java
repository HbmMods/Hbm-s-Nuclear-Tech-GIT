package com.hbm.crafting;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ItemGenericPart.EnumPartType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRTGPelletDepleted.DepletedRTGMaterial;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.items.special.ItemByproduct.EnumByproduct;
import com.hbm.main.CraftingManager;

import static com.hbm.inventory.OreDictManager.*;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Anything that deals exclusively with nuggets, ingots, blocks or compression in general
 * @author hbm
 */
public class MineralRecipes {
	
	public static void register() {
		
		add1To9Pair(ModItems.dust, ModItems.dust_tiny);
		add1To9Pair(ModItems.powder_coal, ModItems.powder_coal_tiny);
		add1To9Pair(ModItems.ingot_mercury, ModItems.nugget_mercury);
		
		add1To9Pair(ModBlocks.block_aluminium, ModItems.ingot_aluminium);
		add1To9Pair(ModBlocks.block_graphite, ModItems.ingot_graphite);
		add1To9Pair(ModBlocks.block_boron, ModItems.ingot_boron);
		add1To9Pair(ModBlocks.block_schraranium, ModItems.ingot_schraranium);
		add1To9Pair(ModBlocks.block_lanthanium, ModItems.ingot_lanthanium);
		add1To9Pair(ModBlocks.block_ra226, ModItems.ingot_ra226);
		add1To9Pair(ModBlocks.block_actinium, ModItems.ingot_actinium);
		add1To9Pair(ModBlocks.block_schrabidate, ModItems.ingot_schrabidate);
		add1To9Pair(ModBlocks.block_coltan, ModItems.fragment_coltan);
		add1To9Pair(ModBlocks.block_smore, ModItems.ingot_smore);
		add1To9Pair(ModBlocks.block_semtex, ModItems.ingot_semtex);
		add1To9Pair(ModBlocks.block_c4, ModItems.ingot_c4);
		add1To9Pair(ModBlocks.block_polymer, ModItems.ingot_polymer);
		add1To9Pair(ModBlocks.block_bakelite, ModItems.ingot_bakelite);
		add1To9Pair(ModBlocks.block_rubber, ModItems.ingot_rubber);
		add1To9Pair(ModBlocks.block_cadmium, ModItems.ingot_cadmium);
		add1To9Pair(ModBlocks.block_tcalloy, ModItems.ingot_tcalloy);
		add1To9Pair(ModBlocks.block_cdalloy, ModItems.ingot_cdalloy);
		
		for(int i = 0; i < EnumCokeType.values().length; i++) {
			add1To9PairSameMeta(Item.getItemFromBlock(ModBlocks.block_coke), ModItems.coke, i);
		}

		addMineralSet(ModItems.nugget_niobium, ModItems.ingot_niobium, ModBlocks.block_niobium);
		addMineralSet(ModItems.nugget_bismuth, ModItems.ingot_bismuth, ModBlocks.block_bismuth);
		addMineralSet(ModItems.nugget_tantalium, ModItems.ingot_tantalium, ModBlocks.block_tantalium);
		addMineralSet(ModItems.nugget_zirconium, ModItems.ingot_zirconium, ModBlocks.block_zirconium);
		addMineralSet(ModItems.nugget_dineutronium, ModItems.ingot_dineutronium, ModBlocks.block_dineutronium);
		addMineralSet(ModItems.nuclear_waste_vitrified_tiny, ModItems.nuclear_waste_vitrified, ModBlocks.block_waste_vitrified);
		
		add1To9Pair(ModItems.ingot_silicon, ModItems.nugget_silicon);

		add1To9Pair(ModItems.powder_boron, ModItems.powder_boron_tiny);
		add1To9Pair(ModItems.powder_sr90, ModItems.powder_sr90_tiny);
		add1To9Pair(ModItems.powder_xe135, ModItems.powder_xe135_tiny);
		add1To9Pair(ModItems.powder_cs137, ModItems.powder_cs137_tiny);
		add1To9Pair(ModItems.powder_i131, ModItems.powder_i131_tiny);

		add1To9Pair(ModItems.ingot_technetium, ModItems.nugget_technetium);
		add1To9Pair(ModItems.ingot_co60, ModItems.nugget_co60);
		add1To9Pair(ModItems.ingot_sr90, ModItems.nugget_sr90);
		add1To9Pair(ModItems.ingot_au198, ModItems.nugget_au198);
		add1To9Pair(ModItems.ingot_pb209, ModItems.nugget_pb209);
		add1To9Pair(ModItems.ingot_ra226, ModItems.nugget_ra226);
		add1To9Pair(ModItems.ingot_actinium, ModItems.nugget_actinium);
		add1To9Pair(ModItems.ingot_arsenic, ModItems.nugget_arsenic);

		add1To9Pair(ModItems.ingot_pu241, ModItems.nugget_pu241);
		add1To9Pair(ModItems.ingot_am241, ModItems.nugget_am241);
		add1To9Pair(ModItems.ingot_am242, ModItems.nugget_am242);
		add1To9Pair(ModItems.ingot_am_mix, ModItems.nugget_am_mix);
		add1To9Pair(ModItems.ingot_americium_fuel, ModItems.nugget_americium_fuel);
		
		add1To9Pair(ModItems.ingot_gh336, ModItems.nugget_gh336);

		for(int i = 0; i < ItemWasteLong.WasteClass.values().length; i++) {
			add1To9PairSameMeta(ModItems.nuclear_waste_long, ModItems.nuclear_waste_long_tiny, i);
			add1To9PairSameMeta(ModItems.nuclear_waste_long_depleted, ModItems.nuclear_waste_long_depleted_tiny, i);
		}

		for(int i = 0; i < ItemWasteShort.WasteClass.values().length; i++) {
			add1To9PairSameMeta(ModItems.nuclear_waste_short, ModItems.nuclear_waste_short_tiny, i);
			add1To9PairSameMeta(ModItems.nuclear_waste_short_depleted, ModItems.nuclear_waste_short_depleted_tiny, i);
		}
		
		add1To9Pair(ModBlocks.block_fallout, ModItems.fallout);
		GameRegistry.addRecipe(new ItemStack(ModBlocks.fallout, 2), new Object[] { "##", '#', ModItems.fallout });

		addMineralSet(ModItems.nugget_pu_mix, ModItems.ingot_pu_mix, ModBlocks.block_pu_mix);
		add1To9Pair(ModItems.ingot_neptunium_fuel, ModItems.nugget_neptunium_fuel);
		
		addBillet(ModItems.billet_cobalt,				ModItems.ingot_cobalt,				ModItems.nugget_cobalt);
		addBillet(ModItems.billet_co60,					ModItems.ingot_co60,				ModItems.nugget_co60);
		addBillet(ModItems.billet_sr90,					ModItems.ingot_sr90,				ModItems.nugget_sr90, SR90.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_uranium,				ModItems.ingot_uranium,				ModItems.nugget_uranium, U.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_u233,					ModItems.ingot_u233,				ModItems.nugget_u233, U233.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_u235,					ModItems.ingot_u235,				ModItems.nugget_u235, U235.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_u238,					ModItems.ingot_u238,				ModItems.nugget_u238, U238.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_th232,				ModItems.ingot_th232,				ModItems.nugget_th232, TH232.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_plutonium,			ModItems.ingot_plutonium,			ModItems.nugget_plutonium, PU.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_pu238,				ModItems.ingot_pu238,				ModItems.nugget_pu238, PU238.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_pu239,				ModItems.ingot_pu239,				ModItems.nugget_pu239, PU239.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_pu240,				ModItems.ingot_pu240,				ModItems.nugget_pu240, PU240.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_pu241,				ModItems.ingot_pu241,				ModItems.nugget_pu241, PU241.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_pu_mix,				ModItems.ingot_pu_mix,				ModItems.nugget_pu_mix);
		addBillet(ModItems.billet_am241,				ModItems.ingot_am241,				ModItems.nugget_am241, AM241.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_am242,				ModItems.ingot_am242,				ModItems.nugget_am242, AM242.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_am_mix,				ModItems.ingot_am_mix,				ModItems.nugget_am_mix);
		addBillet(ModItems.billet_neptunium,			ModItems.ingot_neptunium,			ModItems.nugget_neptunium, NP237.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_polonium,				ModItems.ingot_polonium,			ModItems.nugget_polonium, PO210.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_technetium,			ModItems.ingot_technetium,			ModItems.nugget_technetium, TC99.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_au198,				ModItems.ingot_au198,				ModItems.nugget_au198, AU198.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_pb209,				ModItems.ingot_pb209,				ModItems.nugget_pb209, PB209.all(MaterialShapes.NUGGET)); //and so forth
		addBillet(ModItems.billet_ra226,				ModItems.ingot_ra226,				ModItems.nugget_ra226, RA226.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_actinium,				ModItems.ingot_actinium,			ModItems.nugget_actinium, AC227.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_schrabidium,			ModItems.ingot_schrabidium,			ModItems.nugget_schrabidium, SA326.nugget());
		addBillet(ModItems.billet_solinium,				ModItems.ingot_solinium,			ModItems.nugget_solinium, SA327.nugget());
		addBillet(ModItems.billet_gh336,				ModItems.ingot_gh336,				ModItems.nugget_gh336, GH336.all(MaterialShapes.NUGGET));
		addBillet(ModItems.billet_uranium_fuel,			ModItems.ingot_uranium_fuel,		ModItems.nugget_uranium_fuel);
		addBillet(ModItems.billet_thorium_fuel,			ModItems.ingot_thorium_fuel,		ModItems.nugget_thorium_fuel);
		addBillet(ModItems.billet_plutonium_fuel,		ModItems.ingot_plutonium_fuel,		ModItems.nugget_plutonium_fuel);
		addBillet(ModItems.billet_neptunium_fuel,		ModItems.ingot_neptunium_fuel,		ModItems.nugget_neptunium_fuel);
		addBillet(ModItems.billet_mox_fuel,				ModItems.ingot_mox_fuel,			ModItems.nugget_mox_fuel);
		addBillet(ModItems.billet_les,					ModItems.ingot_les,					ModItems.nugget_les);
		addBillet(ModItems.billet_schrabidium_fuel,		ModItems.ingot_schrabidium_fuel,	ModItems.nugget_schrabidium_fuel);
		addBillet(ModItems.billet_hes,					ModItems.ingot_hes,					ModItems.nugget_hes);
		addBillet(ModItems.billet_australium,			ModItems.ingot_australium,			ModItems.nugget_australium, "nuggetAustralium");
		addBillet(ModItems.billet_australium_greater,										ModItems.nugget_australium_greater);
		addBillet(ModItems.billet_australium_lesser,										ModItems.nugget_australium_lesser);
		addBillet(ModItems.billet_nuclear_waste,		ModItems.nuclear_waste,				ModItems.nuclear_waste_tiny);
		addBillet(ModItems.billet_beryllium,			ModItems.ingot_beryllium,			ModItems.nugget_beryllium, BE.nugget());
		addBillet(ModItems.billet_zirconium,			ModItems.ingot_zirconium,			ModItems.nugget_zirconium, ZR.nugget());
		addBillet(ModItems.billet_bismuth,				ModItems.ingot_bismuth,				ModItems.nugget_bismuth);
		addBillet(ModItems.billet_silicon,				ModItems.ingot_silicon,				ModItems.nugget_silicon, SI.nugget());

		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_thorium_fuel, 6), new Object[] { ModItems.billet_th232, ModItems.billet_th232, ModItems.billet_th232, ModItems.billet_th232, ModItems.billet_th232, ModItems.billet_u233 });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_thorium_fuel, 1), new Object[] { "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetUranium233" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_thorium_fuel, 1), new Object[] { "tinyTh232", "tinyTh232", "tinyTh232", "tinyTh232", "tinyTh232", "tinyU233" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_uranium_fuel, 6), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_u235 });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_uranium_fuel, 1), new Object[] { "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium235" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_uranium_fuel, 1), new Object[] { "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU235" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_plutonium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_pu_mix });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_plutonium_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_plutonium_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, "tinyU238", "tinyU238", "tinyU238", "tinyU238" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_pu_mix, 3), new Object[] { ModItems.billet_pu239, ModItems.billet_pu239, ModItems.billet_pu240 });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_pu_mix, 1), new Object[] { "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium240", "nuggetPlutonium240" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_pu_mix, 1), new Object[] { "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu240", "tinyPu240" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_americium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_am_mix });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_americium_fuel, 1), new Object[] { ModItems.nugget_am_mix, ModItems.nugget_am_mix, "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_americium_fuel, 1), new Object[] { ModItems.nugget_am_mix, ModItems.nugget_am_mix, "tinyU238", "tinyU238", "tinyU238", "tinyU238" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_am_mix, 3), new Object[] { ModItems.billet_am241, ModItems.billet_am242, ModItems.billet_am242 });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_am_mix, 1), new Object[] { "nuggetAmericium241", "nuggetAmericium241", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_am_mix, 1), new Object[] { "tinyAm241", "tinyAm241", "tinyAm242", "tinyAm242", "tinyAm242", "tinyAm242" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_neptunium_fuel, 3), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_neptunium });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_neptunium_fuel, 1), new Object[] { "nuggetNeptunium237", "nuggetNeptunium237", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_neptunium_fuel, 1), new Object[] { "tinyNp237", "tinyNp237", "tinyU238", "tinyU238", "tinyU238", "tinyU238" }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_mox_fuel, 3), new Object[] { ModItems.billet_uranium_fuel, ModItems.billet_uranium_fuel, ModItems.billet_pu_mix });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_mox_fuel, 1), new Object[] { ModItems.nugget_pu_mix, ModItems.nugget_pu_mix, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel, ModItems.nugget_uranium_fuel }));
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_schrabidium_fuel, 3), new Object[] { ModItems.billet_schrabidium, ModItems.billet_neptunium, ModItems.billet_beryllium });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_schrabidium_fuel, 1), new Object[] { ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, "nuggetNeptunium237", "nuggetNeptunium237", ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_schrabidium_fuel, 1), new Object[] { ModItems.nugget_schrabidium, ModItems.nugget_schrabidium, "tinyNp237", "tinyNp237", ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_po210be, 1), new Object[] { "nuggetPolonium210", "nuggetPolonium210", "nuggetPolonium210", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_pu238be, 1), new Object[] { "nuggetPlutonium238", "nuggetPlutonium238", "nuggetPlutonium238", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_ra226be, 1), new Object[] { "nuggetRadium226", "nuggetRadium226", "nuggetRadium226", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_po210be, 2), new Object[] { ModItems.billet_polonium, ModItems.billet_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_pu238be, 2), new Object[] { ModItems.billet_pu238, ModItems.billet_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_ra226be, 2), new Object[] { ModItems.billet_ra226, ModItems.billet_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_po210be, 6), new Object[] { ModItems.billet_polonium, ModItems.billet_polonium, ModItems.billet_polonium,  ModItems.billet_beryllium, ModItems.billet_beryllium, ModItems.billet_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_pu238be, 6), new Object[] { ModItems.billet_pu238, ModItems.billet_pu238, ModItems.billet_pu238,  ModItems.billet_beryllium, ModItems.billet_beryllium, ModItems.billet_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_ra226be, 6), new Object[] { ModItems.billet_ra226, ModItems.billet_ra226, ModItems.billet_ra226,  ModItems.billet_beryllium, ModItems.billet_beryllium, ModItems.billet_beryllium }));
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_zfb_bismuth, 1), new Object[] { ZR.nugget(), ZR.nugget(), ZR.nugget(), U.nugget(), PU241.nugget(), BI.nugget() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_zfb_pu241, 1), new Object[] {  ZR.nugget(), ZR.nugget(), ZR.nugget(), U235.nugget(), PU240.nugget(), PU241.nugget() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_zfb_am_mix, 1), new Object[] {  ZR.nugget(), ZR.nugget(), ZR.nugget(), PU241.nugget(), PU241.nugget(), AMRG.nugget() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_zfb_bismuth, 6), new Object[] { ZR.billet(), ZR.billet(), ZR.billet(), U.billet(), PU241.billet(), BI.billet() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_zfb_pu241, 6), new Object[] { ZR.billet(), ZR.billet(), ZR.billet(), U235.billet(), PU240.billet(), PU241.billet() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_zfb_am_mix, 6), new Object[] { ZR.billet(), ZR.billet(), ZR.billet(), PU241.billet(), PU241.billet(), AMRG.billet() }));


		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_uranium, 2), new Object[] { ModItems.billet_uranium_fuel, ModItems.billet_u238 });
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_uranium, 2), new Object[] { ModItems.billet_u238, "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium235" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.billet_uranium, 2), new Object[] { ModItems.billet_u238, "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU235" }));
		
		/*addBilletToIngot(ModItems.ingot_uranium, ModItems.billet_uranium);
		addBilletToIngot(ModItems.ingot_u233, ModItems.billet_u233);
		addBilletToIngot(ModItems.ingot_u235, ModItems.billet_u235);
		addBilletToIngot(ModItems.ingot_u238, ModItems.billet_u238);
		addBilletToIngot(ModItems.ingot_plutonium, ModItems.billet_plutonium);
		addBilletToIngot(ModItems.ingot_pu238, ModItems.billet_pu238);
		addBilletToIngot(ModItems.ingot_pu239, ModItems.billet_pu239);
		addBilletToIngot(ModItems.ingot_pu240, ModItems.billet_pu240);
		addBilletToIngot(ModItems.ingot_pu241, ModItems.billet_pu241);
		addBilletToIngot(ModItems.ingot_pu_mix, ModItems.billet_pu_mix);
		addBilletToIngot(ModItems.ingot_am241, ModItems.billet_am241);
		addBilletToIngot(ModItems.ingot_am242, ModItems.billet_am242);
		addBilletToIngot(ModItems.ingot_am_mix, ModItems.billet_am_mix);
		addBilletToIngot(ModItems.ingot_uranium_fuel, ModItems.billet_uranium_fuel);
		addBilletToIngot(ModItems.ingot_plutonium_fuel, ModItems.billet_plutonium_fuel);
		addBilletToIngot(ModItems.ingot_americium_fuel, ModItems.billet_americium_fuel);
		addBilletToIngot(ModItems.ingot_mox_fuel, ModItems.billet_mox_fuel);
		addBilletToIngot(ModItems.ingot_neptunium, ModItems.billet_neptunium);
		addBilletToIngot(ModItems.ingot_neptunium_fuel, ModItems.billet_neptunium_fuel);
		addBilletToIngot(ModItems.ingot_polonium, ModItems.billet_polonium);
		addBilletToIngot(ModItems.ingot_technetium, ModItems.billet_technetium);
		addBilletToIngot(ModItems.ingot_schrabidium, ModItems.billet_schrabidium);
		addBilletToIngot(ModItems.ingot_solinium, ModItems.billet_solinium);
		addBilletToIngot(ModItems.ingot_les, ModItems.billet_les);
		addBilletToIngot(ModItems.ingot_schrabidium_fuel, ModItems.billet_schrabidium_fuel);
		addBilletToIngot(ModItems.ingot_hes, ModItems.billet_hes);
		addBilletToIngot(ModItems.ingot_australium, ModItems.billet_australium);*/
		
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_balefire_gold, 1), new Object[] { ModItems.billet_au198, ModItems.cell_antimatter, ModItems.pellet_charged });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_flashlead, 2), new Object[] { ModItems.billet_balefire_gold, ModItems.billet_pb209, ModItems.cell_antimatter });
		
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg), new Object[] { ModItems.billet_pu238, ModItems.billet_pu238, ModItems.billet_pu238, IRON.plate() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_radium), new Object[] { ModItems.billet_ra226, ModItems.billet_ra226, ModItems.billet_ra226, IRON.plate() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_weak), new Object[] { ModItems.billet_u238, ModItems.billet_u238, ModItems.billet_pu238, IRON.plate() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_strontium), new Object[] { ModItems.billet_sr90, ModItems.billet_sr90, ModItems.billet_sr90, IRON.plate() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_cobalt), new Object[] { ModItems.billet_co60, ModItems.billet_co60, ModItems.billet_co60, IRON.plate() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_actinium), new Object[] { ModItems.billet_actinium, ModItems.billet_actinium, ModItems.billet_actinium, IRON.plate() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_polonium), new Object[] { ModItems.billet_polonium, ModItems.billet_polonium, ModItems.billet_polonium, IRON.plate() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_lead), new Object[] { ModItems.billet_pb209, ModItems.billet_pb209, ModItems.billet_pb209, IRON.plate() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_gold), new Object[] { ModItems.billet_au198, ModItems.billet_au198, ModItems.billet_au198, IRON.plate() }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.pellet_rtg_americium), new Object[] { ModItems.billet_am241, ModItems.billet_am241, ModItems.billet_am241, IRON.plate() }));
		
		//There's no need for anvil recycling recipes if you simply set the container item
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_bismuth, 3), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted, 1, DepletedRTGMaterial.BISMUTH.ordinal()) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_lead, 2), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted, 1, DepletedRTGMaterial.LEAD.ordinal()) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.ingot_mercury, 2), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted, 1, DepletedRTGMaterial.MERCURY.ordinal()) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_neptunium, 3), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted, 1, DepletedRTGMaterial.NEPTUNIUM.ordinal()) });
		GameRegistry.addShapelessRecipe(new ItemStack(ModItems.billet_zirconium, 3), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted, 1, DepletedRTGMaterial.ZIRCONIUM.ordinal()) });
		if(OreDictionary.doesOreNameExist("ingotNickel"))
			GameRegistry.addShapelessRecipe(new ItemStack(OreDictionary.getOres("ingotNickel").get(0).getItem(), 2), new Object[] { new ItemStack(ModItems.pellet_rtg_depleted, 1, DepletedRTGMaterial.NICKEL.ordinal()) });
		
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_copper), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_fluorite), 1), new Object[] { "###", "###", "###", '#', ModItems.fluorite });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_niter), 1), new Object[] { "###", "###", "###", '#', ModItems.niter });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_red_copper), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_red_copper });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_sulfur), 1), new Object[] { "###", "###", "###", '#', ModItems.sulfur });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_titanium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_titanium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_tungsten), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_tungsten });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_uranium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_uranium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_thorium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_th232 });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_lead), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_lead });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_trinitite), 1), new Object[] { "###", "###", "###", '#', ModItems.trinitite });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_waste), 1), new Object[] { "###", "###", "###", '#', ModItems.nuclear_waste });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_scrap), 1), new Object[] { "##", "##", '#', ModItems.scrap });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_scrap), 1), new Object[] { "###", "###", "###", '#', ModItems.dust });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_beryllium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_beryllium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_schrabidium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_schrabidium_cluster, 1), new Object[] { "#S#", "SXS", "#S#", '#', ModItems.ingot_schrabidium, 'S', ModItems.ingot_starmetal, 'X', ModItems.ingot_schrabidate });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_euphemium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_euphemium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_advanced_alloy), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_advanced_alloy });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_magnetized_tungsten), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_magnetized_tungsten });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_combine_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_combine_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_australium), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_australium });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_desh), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_desh });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_dura_steel), 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_dura_steel });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_meteor_cobble), 1), new Object[] { "##", "##", '#', ModItems.fragment_meteorite });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_meteor_broken), 1), new Object[] { "###", "###", "###", '#', ModItems.fragment_meteorite });
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.block_yellowcake), 1), new Object[] { "###", "###", "###", '#', ModItems.powder_yellowcake });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_starmetal, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_u233, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_u233 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_u235, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_u235 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_u238, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_uranium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_uranium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_neptunium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_polonium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_polonium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_plutonium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_plutonium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_pu238, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_pu239, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_pu240, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_pu240 });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_mox_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_mox_fuel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_plutonium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_plutonium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_thorium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_thorium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_solinium, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_solinium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_schrabidium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_schrabidium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_lithium, 1), new Object[] { "###", "###", "###", '#', ModItems.lithium });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_white_phosphorus, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_red_phosphorus, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_fire });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_insulator, 1), new Object[] { "###", "###", "###", '#', ModItems.plate_polymer });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_asbestos, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_asbestos });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_fiberglass, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_fiberglass });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.block_cobalt, 1), new Object[] { "###", "###", "###", '#', ModItems.ingot_cobalt });

		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_copper, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_copper) });
		GameRegistry.addRecipe(new ItemStack(ModItems.fluorite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_fluorite) });
		GameRegistry.addRecipe(new ItemStack(ModItems.niter, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_niter) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_red_copper, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_red_copper) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_steel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.sulfur, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_sulfur) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_titanium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_titanium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_tungsten, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_tungsten) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_uranium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_th232, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_thorium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_lead, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_lead) });
		GameRegistry.addRecipe(new ItemStack(ModItems.trinitite, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_trinitite) });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_waste) });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_waste_painted) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_beryllium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_beryllium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_schrabidium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_euphemium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_euphemium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_advanced_alloy, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_advanced_alloy) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_magnetized_tungsten, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_magnetized_tungsten) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_combine_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_combine_steel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_australium, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_australium) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_desh, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_desh) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_dura_steel, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_dura_steel) });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_yellowcake, 9), new Object[] { "#", '#', Item.getItemFromBlock(ModBlocks.block_yellowcake) });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_starmetal, 9), new Object[] { "#", '#', ModBlocks.block_starmetal });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u233, 9), new Object[] { "#", '#', ModBlocks.block_u233 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u235, 9), new Object[] { "#", '#', ModBlocks.block_u235 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u238, 9), new Object[] { "#", '#', ModBlocks.block_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_uranium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_neptunium, 9), new Object[] { "#", '#', ModBlocks.block_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_polonium, 9), new Object[] { "#", '#', ModBlocks.block_polonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_plutonium, 9), new Object[] { "#", '#', ModBlocks.block_plutonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu238, 9), new Object[] { "#", '#', ModBlocks.block_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu239, 9), new Object[] { "#", '#', ModBlocks.block_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu240, 9), new Object[] { "#", '#', ModBlocks.block_pu240 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_mox_fuel, 9), new Object[] { "#", '#', ModBlocks.block_mox_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_plutonium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_thorium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_thorium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_solinium, 9), new Object[] { "#", '#', ModBlocks.block_solinium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 9), new Object[] { "#", '#', ModBlocks.block_schrabidium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.lithium, 9), new Object[] { "#", '#', ModBlocks.block_lithium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_phosphorus, 9), new Object[] { "#", '#', ModBlocks.block_white_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_fire, 9), new Object[] { "#", '#', ModBlocks.block_red_phosphorus });
		GameRegistry.addRecipe(new ItemStack(ModItems.plate_polymer, 9), new Object[] { "#", '#', ModBlocks.block_insulator });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_asbestos, 9), new Object[] { "#", '#', ModBlocks.block_asbestos });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_fiberglass, 9), new Object[] { "#", '#', ModBlocks.block_fiberglass });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_cobalt, 9), new Object[] { "#", '#', ModBlocks.block_cobalt });

		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_plutonium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_plutonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_plutonium, 9), new Object[] { "#", '#', ModItems.ingot_plutonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu238, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_pu238, 9), new Object[] { "#", '#', ModItems.ingot_pu238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu239, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_pu239, 9), new Object[] { "#", '#', ModItems.ingot_pu239 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_pu240, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_pu240 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_pu240, 9), new Object[] { "#", '#', ModItems.ingot_pu240 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_th232, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_th232 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_th232, 9), new Object[] { "#", '#', ModItems.ingot_th232 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_uranium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_uranium, 9), new Object[] { "#", '#', ModItems.ingot_uranium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u233, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u233 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_u233, 9), new Object[] { "#", '#', ModItems.ingot_u233 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u235, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u235 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_u235, 9), new Object[] { "#", '#', ModItems.ingot_u235 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_u238, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_u238, 9), new Object[] { "#", '#', ModItems.ingot_u238 });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_neptunium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_neptunium, 9), new Object[] { "#", '#', ModItems.ingot_neptunium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_polonium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_polonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_polonium, 9), new Object[] { "#", '#', ModItems.ingot_polonium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_lead, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_lead });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_lead, 9), new Object[] { "#", '#', ModItems.ingot_lead });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_beryllium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_beryllium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_beryllium, 9), new Object[] { "#", '#', ModItems.ingot_beryllium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_schrabidium, 9), new Object[] { "#", '#', ModItems.ingot_schrabidium });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_uranium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_uranium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_uranium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_thorium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_thorium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_thorium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_thorium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_plutonium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_plutonium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_plutonium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_mox_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_mox_fuel, 9), new Object[] { "#", '#', ModItems.ingot_mox_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_schrabidium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_schrabidium_fuel, 9), new Object[] { "#", '#', ModItems.ingot_schrabidium_fuel });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_hes, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_hes });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_hes, 9), new Object[] { "#", '#', ModItems.ingot_hes });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_les, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_les });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_les, 9), new Object[] { "#", '#', ModItems.ingot_les });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_australium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_australium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_australium, 9), new Object[] { "#", '#', ModItems.ingot_australium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_steel, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_steel_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_steel_tiny, 9), new Object[] { "#", '#', ModItems.powder_steel });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_lithium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_lithium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_lithium_tiny, 9), new Object[] { "#", '#', ModItems.powder_lithium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_cobalt, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_cobalt_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_cobalt_tiny, 9), new Object[] { "#", '#', ModItems.powder_cobalt });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_neodymium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_neodymium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_neodymium_tiny, 9), new Object[] { "#", '#', ModItems.powder_neodymium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_niobium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_niobium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_niobium_tiny, 9), new Object[] { "#", '#', ModItems.powder_niobium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_cerium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_cerium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_cerium_tiny, 9), new Object[] { "#", '#', ModItems.powder_cerium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_lanthanium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_lanthanium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_lanthanium_tiny, 9), new Object[] { "#", '#', ModItems.powder_lanthanium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_actinium, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_actinium_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_actinium_tiny, 9), new Object[] { "#", '#', ModItems.powder_actinium });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_meteorite, 1), new Object[] { "###", "###", "###", '#', ModItems.powder_meteorite_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.powder_meteorite_tiny, 9), new Object[] { "#", '#', ModItems.powder_meteorite });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_solinium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_solinium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_solinium, 9), new Object[] { "#", '#', ModItems.ingot_solinium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste, 1), new Object[] { "###", "###", "###", '#', ModItems.nuclear_waste_tiny });
		GameRegistry.addRecipe(new ItemStack(ModItems.nuclear_waste_tiny, 9), new Object[] { "#", '#', ModItems.nuclear_waste });
		GameRegistry.addRecipe(new ItemStack(ModItems.bottle_mercury, 1), new Object[] { "###", "#B#", "###", '#', ModItems.ingot_mercury, 'B', Items.glass_bottle });
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_mercury, 8), new Object[] { "#", '#', ModItems.bottle_mercury });
		GameRegistry.addRecipe(new ItemStack(ModItems.egg_balefire, 1), new Object[] { "###", "###", "###", '#', ModItems.egg_balefire_shard });
		GameRegistry.addRecipe(new ItemStack(ModItems.egg_balefire_shard, 9), new Object[] { "#", '#', ModItems.egg_balefire });
		GameRegistry.addRecipe(new ItemStack(ModItems.nitra, 1), new Object[] { "##", "##", '#', ModItems.nitra_small });
		GameRegistry.addRecipe(new ItemStack(ModItems.nitra_small, 4), new Object[] { "#", '#', ModItems.nitra });
		GameRegistry.addRecipe(new ItemStack(ModBlocks.glass_polarized, 4), new Object[] { "##", "##", '#', DictFrame.fromOne(ModItems.part_generic, EnumPartType.GLASS_POLARIZED) });
		add1To9Pair(ModItems.powder_paleogenite, ModItems.powder_paleogenite_tiny);
		add1To9Pair(ModItems.ingot_osmiridium, ModItems.nugget_osmiridium);

		GameRegistry.addRecipe(new ItemStack(ModItems.egg_balefire_shard, 1), new Object[] { "##", "##", '#', ModItems.powder_balefire });
		add9To1(ModItems.cell_balefire, ModItems.egg_balefire_shard);
		
		GameRegistry.addRecipe(new ItemStack(ModItems.ingot_euphemium, 1), new Object[] { "###", "###", "###", '#', ModItems.nugget_euphemium });
		GameRegistry.addRecipe(new ItemStack(ModItems.nugget_euphemium, 9), new Object[] { "#", '#', ModItems.ingot_euphemium });

		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "nuggetUranium235", "nuggetUranium235", "nuggetUranium235", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "nuggetUranium233", "nuggetUranium233", "nuggetUranium233", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_thorium_fuel, 1), new Object[] { "nuggetUranium233", "nuggetUranium233", "nuggetUranium233", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232", "nuggetThorium232" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "nuggetPlutonium238", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium240", "nuggetPlutonium240", "nuggetPlutonium240" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetUranium238", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_uranium_fuel, 1), new Object[] { "tinyU235", "tinyU235", "tinyU235", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238", "tinyU238" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_plutonium_fuel, 1), new Object[] { "tinyPu238", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu240", "tinyPu240", "tinyPu240" }));
		//GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_mox_fuel, 1), new Object[] { "tinyU235", "tinyU235", "tinyU235", "tinyU238", "tinyU238", "tinyPu238", "tinyPu239", "tinyPu239", "tinyPu239" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_schrabidium_fuel, 1), new Object[] { "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetNeptunium237", "nuggetNeptunium237", "nuggetNeptunium237", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_hes, 1), new Object[] { "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetSchrabidium", "nuggetNeptunium237", "nuggetNeptunium237", ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_les, 1), new Object[] { "nuggetSchrabidium", "nuggetNeptunium237", "nuggetNeptunium237", "nuggetNeptunium237", "nuggetNeptunium237", ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium, ModItems.nugget_beryllium }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_pu_mix, 1), new Object[] { "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPlutonium239", "nuggetPluonium240", "nuggetPluonium240", "nuggetPluonium240" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_pu_mix, 1), new Object[] { "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu239", "tinyPu240", "tinyPu240", "tinyPu240" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_am_mix, 1), new Object[] { "nuggetAmericium241", "nuggetAmericium241", "nuggetAmericium241", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242", "nuggetAmericium242" }));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.ingot_am_mix, 1), new Object[] { "tinyAm241", "tinyAm241", "tinyAm241", "tinyAm242", "tinyAm242", "tinyAm242", "tinyAm242", "tinyAm242", "tinyAm242" }));
		
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ball_fireclay, 4), new Object[] { Items.clay_ball, Items.clay_ball, Items.clay_ball, AL.dust() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ball_fireclay, 4), new Object[] { Items.clay_ball, Items.clay_ball, Items.clay_ball, AL.ore() });
		CraftingManager.addShapelessAuto(new ItemStack(ModItems.ball_fireclay, 4), new Object[] { Items.clay_ball, Items.clay_ball, DictFrame.fromOne(ModBlocks.stone_resource, EnumStoneType.LIMESTONE), KEY_SAND });

		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_IRON), new ItemStack(ModItems.powder_iron));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_COPPER), new ItemStack(ModItems.powder_copper));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_LITHIUM), new ItemStack(ModItems.powder_lithium));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_SILICON), new ItemStack(ModItems.nugget_silicon, 3));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_LEAD), new ItemStack(ModItems.powder_lead));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_TITANIUM), new ItemStack(ModItems.powder_titanium));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_ALUMINIUM), new ItemStack(ModItems.powder_aluminium));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_SULFUR), new ItemStack(ModItems.sulfur));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_CALCIUM), new ItemStack(ModItems.powder_calcium));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_BISMUTH), new ItemStack(ModItems.powder_bismuth));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_RADIUM), new ItemStack(ModItems.powder_ra226));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_TECHNETIUM), new ItemStack(ModItems.billet_technetium));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_POLONIUM), new ItemStack(ModItems.billet_polonium));
		add9To1(DictFrame.fromOne(ModItems.ore_byproduct, EnumByproduct.B_URANIUM), new ItemStack(ModItems.powder_uranium));
	}
	
	//Bundled 1/9 recipes
	public static void add1To9Pair(Item one, Item nine) {
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add1To9Pair(Block one, Item nine) {
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
		add9To1(new ItemStack(nine), new ItemStack(one));
	}
	
	public static void add1To9PairSameMeta(Item one, Item nine, int meta) {
		add1To9SameMeta(one, nine, meta);
		add9To1SameMeta(nine, one, meta);
	}
	
	//Full set of nugget, ingot and block
	public static void addMineralSet(Item nugget, Item ingot, Block block) {
		add1To9(new ItemStack(ingot), new ItemStack(nugget, 9));
		add9To1(new ItemStack(nugget), new ItemStack(ingot));
		add1To9(new ItemStack(block), new ItemStack(ingot, 9));
		add9To1(new ItemStack(ingot), new ItemStack(block));
	}

	//Decompress one item into nine
	public static void add1To9(Block one, Item nine) {
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
	}

	public static void add1To9(Item one, Item nine) {
		add1To9(new ItemStack(one), new ItemStack(nine, 9));
	}

	public static void add1To9SameMeta(Item one, Item nine, int meta) {
		add1To9(new ItemStack(one, 1, meta), new ItemStack(nine, 9, meta));
	}

	public static void add1To9(ItemStack one, ItemStack nine) {
		GameRegistry.addRecipe(nine, new Object[] { "#", '#', one });
	}

	//Compress nine items into one
	public static void add9To1(Item nine, Block one) {
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add9To1(Item nine, Item one) {
		add9To1(new ItemStack(nine), new ItemStack(one));
	}

	public static void add9To1SameMeta(Item nine, Item one, int meta) {
		add9To1(new ItemStack(nine, 1, meta), new ItemStack(one, 1, meta));
	}

	public static void add9To1(ItemStack nine, ItemStack one) {
		GameRegistry.addRecipe(one, new Object[] { "###", "###", "###", '#', nine });
	}

	public static void addBillet(Item billet, Item nugget, String... ore) {
		for(String o : ore) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(billet), new Object[] { "###", "###", '#', o }));
		addBillet(billet, nugget);
	}

	public static void addBillet(Item billet, Item ingot, Item nugget, String... ore) {
		for(String o : ore) GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(billet), new Object[] { "###", "###", '#', o }));
		addBillet(billet, ingot, nugget);
	}

	public static void addBilletFragment(ItemStack billet, ItemStack nugget) {
		GameRegistry.addRecipe(billet.copy(), new Object[] { "###", "###", '#', nugget });
	}

	public static void addBillet(Item billet, Item nugget) {
		GameRegistry.addRecipe(new ItemStack(billet), new Object[] { "###", "###", '#', nugget });
		GameRegistry.addShapelessRecipe(new ItemStack(nugget, 6), new Object[] { billet });
	}

	public static void addBillet(Item billet, Item ingot, Item nugget) {
		GameRegistry.addRecipe(new ItemStack(billet), new Object[] { "###", "###", '#', nugget });
		GameRegistry.addShapelessRecipe(new ItemStack(nugget, 6), new Object[] { billet });
		addBilletToIngot(billet, ingot);
	}

	public static void addBilletToIngot(Item billet, Item ingot) {
		GameRegistry.addShapelessRecipe(new ItemStack(ingot, 2), new Object[] { billet, billet, billet });
		GameRegistry.addRecipe(new ItemStack(billet, 3), new Object[] { "##", '#', ingot });
	}
}
