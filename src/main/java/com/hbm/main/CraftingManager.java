package com.hbm.main;

import com.hbm.blocks.BlockEnums.DecoCabinetEnum;
import com.hbm.blocks.BlockEnums.LightstoneType;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockConcreteColoredExt.EnumConcreteType;
import com.hbm.blocks.generic.BlockGenericStairs;
import com.hbm.blocks.generic.BlockMultiSlab;
import com.hbm.blocks.generic.BlockNTMFlower.EnumFlowerType;
import com.hbm.blocks.generic.BlockPlushie.PlushieType;
import com.hbm.config.GeneralConfig;
import com.hbm.crafting.*;
import com.hbm.crafting.handlers.*;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumLegendaryType;
import com.hbm.items.ItemEnums.EnumPages;
import com.hbm.items.ItemEnums.EnumPlantType;
import com.hbm.items.ItemGenericPart.EnumPartType;
import com.hbm.items.food.ItemConserve.EnumFoodType;
import com.hbm.items.machine.ItemArcElectrode.EnumElectrodeType;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.special.ItemCircuitStarComponent.CircuitComponentType;
import com.hbm.items.special.ItemHolotapeImage.EnumHoloImage;
import com.hbm.items.special.ItemPlasticScrap.ScrapType;
import com.hbm.items.tool.ItemConveyorWand.ConveyorType;
import com.hbm.items.tool.ItemDrone.EnumDroneType;
import com.hbm.items.tool.ItemGuideBook.BookType;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class CraftingManager {

	public static void mainRegistry() {

		AddCraftingRec();
		SmeltingRecipes.AddSmeltingRec();

		MineralRecipes.register();
		RodRecipes.register();
		ToolRecipes.register();
		ArmorRecipes.register();
		WeaponRecipes.register();
		ConsumableRecipes.register();
		PowderRecipes.register();

		GameRegistry.addRecipe(new RBMKFuelCraftingHandler());
		GameRegistry.addRecipe(new MKUCraftingHandler());
		GameRegistry.addRecipe(new CargoShellCraftingHandler());
		GameRegistry.addRecipe(new ScrapsCraftingHandler());

		RecipeSorter.register("hbm:rbmk", RBMKFuelCraftingHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("hbm:cargo", CargoShellCraftingHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("hbm:scraps", ScrapsCraftingHandler.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
		RecipeSorter.register("hbm:mku", MKUCraftingHandler.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
		RecipeSorter.register("hbm:containerupgrade", ContainerUpgradeCraftingHandler.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
	}

	public static void AddCraftingRec() {

		for(Object[] array : BlockMultiSlab.recipeGen) {
			addRecipeAuto(new ItemStack((Block) array[2], 6, (int) array[3]), new Object[] { "###", '#', new ItemStack((Block) array[0], 1, (int) array[1]) });
		}
		for(Object[] array : BlockGenericStairs.recipeGen) {
			addRecipeAuto(new ItemStack((Block) array[2], 4), new Object[] { "#  ",  "## ",  "###", '#', new ItemStack((Block) array[0], 1, (int) array[1]) });
		}

		addRecipeAuto(new ItemStack(ModItems.redstone_sword, 1), new Object[] { "R", "R", "S", 'R', REDSTONE.block(), 'S', KEY_STICK });
		addRecipeAuto(new ItemStack(ModItems.big_sword, 1), new Object[] { "QIQ", "QIQ", "GSG", 'G', Items.gold_ingot, 'S', KEY_STICK, 'I', Items.iron_ingot, 'Q', Items.quartz});

		addRecipeAuto(Mats.MAT_IRON.make(ModItems.plate_cast), new Object[] { "BPB", "BPB", "BPB", 'B', STEEL.bolt(), 'P', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.hazmat_cloth_red, 1), new Object[] { "C", "R", "C", 'C', ModItems.hazmat_cloth, 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(ModItems.hazmat_cloth_grey, 1), new Object[] { " P ", "ICI", " L ", 'C', ModItems.hazmat_cloth_red, 'P', IRON.plate(), 'L', PB.plate(), 'I', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(ModItems.asbestos_cloth, 8), new Object[] { "SCS", "CPC", "SCS", 'S', Items.string, 'P', BR.dust(), 'C', Blocks.wool });
		addRecipeAuto(new ItemStack(ModItems.bolt_spike, 2), new Object[] { "BB", "B ", "B ", 'B', STEEL.bolt()});
		addRecipeAuto(new ItemStack(ModItems.pipes_steel, 1), new Object[] { "B", "B", "B", 'B', STEEL.block() });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 8), new Object[] { "DD", 'D', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 8), new Object[] { "DD", 'D', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', FIBER.ingot()});
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 16), new Object[] { "DD", 'D', ASBESTOS.ingot()});
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "SWS", 'S', Items.string, 'W', Blocks.wool });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "BB", 'B', "ingotBrick" });
		addRecipeAuto(new ItemStack(ModItems.plate_polymer, 4), new Object[] { "BB", 'B', "ingotNetherBrick" });

		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), new Object[] { "G", "W", "I", 'G', KEY_ANYPANE, 'W', W.wireFine(), 'I', ModItems.plate_polymer });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), new Object[] { "G", "W", "I", 'G', KEY_ANYPANE, 'W', CARBON.wireFine(), 'I', ModItems.plate_polymer });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR), new Object[] { "I", "N", "W", 'I', ModItems.plate_polymer, 'N', NB.nugget(), 'W', AL.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR), new Object[] { "I", "N", "W", 'I', ModItems.plate_polymer, 'N', NB.nugget(), 'W', CU.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR, 2), new Object[] { "IAI", "W W", 'I', ModItems.plate_polymer, 'A', AL.dust(), 'W', AL.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR, 2), new Object[] { "IAI", "W W", 'I', ModItems.plate_polymer, 'A', AL.dust(), 'W', CU.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR_TANTALIUM), new Object[] { "I", "N", "W", 'I', ModItems.plate_polymer, 'N', TA.nugget(), 'W', AL.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR_TANTALIUM), new Object[] { "I", "N", "W", 'I', ModItems.plate_polymer, 'N', TA.nugget(), 'W', CU.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.PCB), new Object[] { "I", "P", 'I', ModItems.plate_polymer, 'P', CU.plate() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.PCB, 4), new Object[] { "I", "P", 'I', ModItems.plate_polymer, 'P', GOLD.plate() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), new Object[] { "I", "S", "W", 'I', ModItems.plate_polymer, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.SILICON), 'W', CU.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), new Object[] { "I", "S", "W", 'I', ModItems.plate_polymer, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.SILICON), 'W', GOLD.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_BISMOID), new Object[] { "III", "SNS", "WWW", 'I', ModItems.plate_polymer, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.SILICON), 'N', ANY_BISMOID.nugget(), 'W', CU.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_BISMOID), new Object[] { "III", "SNS", "WWW", 'I', ModItems.plate_polymer, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.SILICON), 'N', ANY_BISMOID.nugget(), 'W', GOLD.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_QUANTUM), new Object[] { "HHH", "SIS", "WWW", 'H', ANY_HARDPLASTIC.ingot(), 'S', BSCCO.wireDense(), 'I', ModItems.pellet_charged, 'W', CU.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP_QUANTUM), new Object[] { "HHH", "SIS", "WWW", 'H', ANY_HARDPLASTIC.ingot(), 'S', BSCCO.wireDense(), 'I', ModItems.pellet_charged, 'W', GOLD.wireFine() });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER_CHASSIS), new Object[] { "PPP", "CBB", "PPP", 'P', ANY_PLASTIC.ingot(), 'C', ModItems.crt_display, 'B', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.PCB) });
		addRecipeAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ATOMIC_CLOCK), new Object[] { "ICI", "CSC", "ICI", 'I', ModItems.plate_polymer, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), 'S', SR.dust() });

		addRecipeAuto(new ItemStack(ModItems.crt_display, 4), new Object[] { " A ", "SGS", " T ", 'A', AL.dust(), 'S', STEEL.plate(), 'G', KEY_ANYPANE, 'T', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE) });

		addRecipeAuto(new ItemStack(ModItems.cell_empty, 6), new Object[] { " S ", "G G", " S ", 'S', STEEL.plate(), 'G', KEY_ANYPANE });
		addRecipeAuto(new ItemStack(ModItems.cell_deuterium, 8), new Object[] { "DDD", "DTD", "DDD", 'D', ModItems.cell_empty, 'T', ModItems.mike_deut });
		addRecipeAuto(new ItemStack(ModItems.particle_empty, 2), new Object[] { "STS", "G G", "STS", 'S', PB.plateCast(), 'T', ModItems.coil_gold, 'G', KEY_ANYPANE });
		addShapelessAuto(new ItemStack(ModItems.particle_copper, 1), new Object[] { ModItems.particle_empty, CU.dust(), ModItems.pellet_charged });
		addShapelessAuto(new ItemStack(ModItems.particle_lead, 1), new Object[] { ModItems.particle_empty, PB.dust(), ModItems.pellet_charged });
		addShapelessAuto(new ItemStack(ModItems.cell_antimatter, 1), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.cell_empty });
		addShapelessAuto(new ItemStack(ModItems.particle_amat, 1), new Object[] { ModItems.particle_aproton, ModItems.particle_aelectron, ModItems.particle_empty });

		addRecipeAuto(new ItemStack(ModItems.canister_empty, 2), new Object[] { "S ", "AA", "AA", 'S', STEEL.plate(), 'A', AL.plate() });
		addRecipeAuto(new ItemStack(ModItems.gas_empty, 2), new Object[] { "S ", "AA", "AA", 'A', STEEL.plate(), 'S', CU.plate() });
		addShapelessAuto(new ItemStack(ModBlocks.block_waste_painted, 1), new Object[] { KEY_YELLOW, ModBlocks.block_waste });


		addRecipeAuto(new ItemStack(ModItems.ingot_aluminium, 1), new Object[] { "###", "###", "###", '#', AL.wireFine() });
		addRecipeAuto(new ItemStack(ModItems.ingot_copper, 1), new Object[] { "###", "###", "###", '#', CU.wireFine() });
		addRecipeAuto(new ItemStack(ModItems.ingot_tungsten, 1), new Object[] { "###", "###", "###", '#', W.wireFine() });
		addRecipeAuto(new ItemStack(ModItems.ingot_red_copper, 1), new Object[] { "###", "###", "###", '#', MINGRADE.wireFine() });
		addRecipeAuto(new ItemStack(ModItems.ingot_advanced_alloy, 1), new Object[] { "###", "###", "###", '#', ALLOY.wireFine() });
		addRecipeAuto(new ItemStack(Items.gold_ingot, 1), new Object[] { "###", "###", "###", '#', GOLD.wireFine() });
		addRecipeAuto(new ItemStack(ModItems.ingot_schrabidium, 1), new Object[] { "###", "###", "###", '#', SA326.wireFine() });
		addRecipeAuto(new ItemStack(ModItems.ingot_magnetized_tungsten, 1), new Object[] { "###", "###", "###", '#', MAGTUNG.wireFine() });

		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, ModItems.powder_sawdust, ModItems.powder_sawdust, ModItems.powder_sawdust });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.apple, Items.apple, Items.apple });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.reeds, Items.reeds, Items.reeds });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.rotten_flesh, Items.rotten_flesh, Items.rotten_flesh });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot, Items.carrot });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato, Items.potato });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, KEY_SAPLING, KEY_SAPLING, KEY_SAPLING, KEY_SAPLING, KEY_SAPLING, KEY_SAPLING });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, KEY_LEAVES, KEY_LEAVES, KEY_LEAVES, KEY_LEAVES, KEY_LEAVES, KEY_LEAVES });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Blocks.pumpkin });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Blocks.melon_block });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Blocks.cactus, Blocks.cactus, Blocks.cactus });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { Items.sugar, ModItems.powder_sawdust, ModItems.powder_sawdust, Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat, Items.wheat });
		addShapelessAuto(new ItemStack(ModItems.biomass, 4), new Object[] { DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED), DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED), DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED), DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED), DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED), DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED) });

		addRecipeAuto(new ItemStack(ModItems.coil_copper, 1), new Object[] { "WWW", "WIW", "WWW", 'W', MINGRADE.wireFine(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_alloy, 1), new Object[] { "WWW", "WIW", "WWW", 'W', ALLOY.wireFine(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_gold, 1), new Object[] { "WWW", "WIW", "WWW", 'W', GOLD.wireFine(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_advanced_alloy });
		addRecipeAuto(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', IRON.plate(), 'C', ModItems.coil_gold });
		addRecipeAuto(new ItemStack(ModItems.coil_copper_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.coil_advanced_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_advanced_alloy });
		addRecipeAuto(new ItemStack(ModItems.coil_gold_torus, 2), new Object[] { " C ", "CPC", " C ", 'P', STEEL.plate(), 'C', ModItems.coil_gold });
		addRecipeAuto(new ItemStack(ModItems.coil_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', W.wireFine(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.coil_magnetized_tungsten, 1), new Object[] { "WWW", "WIW", "WWW", 'W', MAGTUNG.wireFine(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.tank_steel, 2), new Object[] { "STS", "S S", "STS", 'S', STEEL.plate(), 'T', TI.plate() });
		addRecipeAuto(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", "ITI", 'R', MINGRADE.wireFine(), 'T', ModItems.coil_copper_torus, 'I', IRON.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.motor, 2), new Object[] { " R ", "ICI", " T ", 'R', MINGRADE.wireFine(), 'T', ModItems.coil_copper_torus, 'I', STEEL.plate(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModItems.motor_desh, 1), new Object[] { "PCP", "DMD", "PCP", 'P', ANY_PLASTIC.ingot(), 'C', ModItems.coil_gold_torus, 'D', DESH.ingot(), 'M', ModItems.motor });
		addRecipeAuto(new ItemStack(ModItems.motor_bismuth, 1), new Object[] { "BCB", "SDS", "BCB", 'B', BI.nugget(), 'C', ModBlocks.hadron_coil_alloy, 'S', STEEL.plateCast(), 'D', DURA.ingot() });
		addRecipeAuto(new ItemStack(ModItems.deuterium_filter, 1), new Object[] { "TST", "SCS", "TST", 'T', ANY_RESISTANTALLOY.ingot(), 'S', S.dust(), 'C', ModItems.catalyst_clay });

		addRecipeAuto(new ItemStack(ModItems.fins_flat, 1), new Object[] { "IP", "PP", "IP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fins_small_steel, 1), new Object[] { " PP", "PII", " PP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fins_big_steel, 1), new Object[] { " PI", "III", " PI", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fins_tri_steel, 1), new Object[] { " PI", "IIB", " PI", 'P', STEEL.plate(), 'I', STEEL.ingot(), 'B', STEEL.block() });
		addRecipeAuto(new ItemStack(ModItems.fins_quad_titanium, 1), new Object[] { " PP", "III", " PP", 'P', TI.plate(), 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.sphere_steel, 1), new Object[] { "PIP", "I I", "PIP", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.pedestal_steel, 1), new Object[] { "P P", "P P", "III", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.lemon, 1), new Object[] { " D ", "DSD", " D ", 'D', KEY_YELLOW, 'S', "stone" });
		addRecipeAuto(new ItemStack(ModItems.blade_titanium, 2), new Object[] { "TP", "TP", "TT", 'P', TI.plate(), 'T', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.turbine_titanium, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_titanium, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.shimmer_head, 1), new Object[] { "SSS", "DTD", "SSS", 'S', STEEL.ingot(), 'D', DESH.block(), 'T', W.block() });
		addRecipeAuto(new ItemStack(ModItems.shimmer_axe_head, 1), new Object[] { "PII", "PBB", "PII", 'P', STEEL.plate(), 'B', DESH.block(), 'I', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.shimmer_handle, 1), new Object[] { "GP", "GP", "GP", 'G', GOLD.plate(), 'P', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModItems.shimmer_sledge, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_head });
		addRecipeAuto(new ItemStack(ModItems.shimmer_axe, 1), new Object[] { "H", "G", "G", 'G', ModItems.shimmer_handle, 'H', ModItems.shimmer_axe_head });
		addShapelessAuto(new ItemStack(ModItems.definitelyfood, 4), new Object[] { ANY_RUBBER.ingot(), Items.wheat, Items.rotten_flesh, "treeSapling" });
		addShapelessAuto(new ItemStack(ModItems.definitelyfood, 4), new Object[] { ANY_RUBBER.ingot(), Items.wheat, Items.rotten_flesh, Items.wheat_seeds, Items.wheat_seeds, Items.wheat_seeds });
		addRecipeAuto(new ItemStack(ModItems.turbine_tungsten, 1), new Object[] { "BBB", "BSB", "BBB", 'B', ModItems.blade_tungsten, 'S', DURA.ingot() });
		addRecipeAuto(new ItemStack(ModItems.ring_starmetal, 1), new Object[] { " S ", "S S", " S ", 'S', STAR.ingot() });
		addRecipeAuto(new ItemStack(ModItems.flywheel_beryllium, 1), new Object[] { "IBI", "BTB", "IBI", 'B', BE.block(), 'I', IRON.plateCast(), 'T', DURA.pipe() });

		addShapelessAuto(new ItemStack(ModItems.powder_poison), new Object[] { DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.NIGHTSHADE) });
		addShapelessAuto(new ItemStack(ModItems.syringe_metal_stimpak), new Object[] { ModItems.syringe_metal_empty, Items.carrot, DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.FOXGLOVE) }); //xander root and broc flower
		addShapelessAuto(new ItemStack(ModItems.pill_herbal), new Object[] { COAL.dust(), Items.poisonous_potato, Items.nether_wart, DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.FOXGLOVE) });
		addShapelessAuto(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE, 1), new Object[] { Items.string, Items.string, Items.string });
		addRecipeAuto(DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE, 4), new Object[] { "W", "W", "W", 'W', DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED) });
		addShapelessAuto(new ItemStack(Items.string, 3), new Object[] { DictFrame.fromOne(ModBlocks.plant_flower, EnumFlowerType.WEED) });
		addRecipeAuto(new ItemStack(Items.paper, 3), new Object[] { "SSS", 'S', ModItems.powder_sawdust });

		addRecipeAuto(new ItemStack(ModItems.wrench, 1), new Object[] { " S ", " IS", "I  ", 'S', STEEL.ingot(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.wrench_flipped, 1), new Object[] { "S", "D", "W", 'S', Items.iron_sword, 'D', ModItems.ducttape, 'W', ModItems.wrench });
		addRecipeAuto(new ItemStack(ModItems.memespoon, 1), new Object[] { "CGC", "PSP", "IAI", 'C', ModItems.powder_yellowcake, 'G', TH232.block(), 'P', ModItems.photo_panel, 'S', ModItems.steel_shovel, 'I', ModItems.plate_polymer, 'A', "ingotAustralium" });
		addShapelessAuto(new ItemStack(ModItems.cbt_device, 1), new Object[] { STEEL.bolt(), ModItems.wrench });

		addShapelessAuto(new ItemStack(ModItems.toothpicks, 3), new Object[] { KEY_STICK, KEY_STICK, KEY_STICK });
		addRecipeAuto(new ItemStack(ModItems.ducttape, 4), new Object[] { "F", "P", "S", 'F', Items.string, 'S', KEY_SLIME, 'P', Items.paper });

		addRecipeAuto(new ItemStack(ModBlocks.radio_torch_sender, 4), new Object[] { "G", "R", "I", 'G', "dustGlowstone", 'R', Blocks.redstone_torch, 'I', NETHERQUARTZ.gem() });
		addRecipeAuto(new ItemStack(ModBlocks.radio_torch_receiver, 4), new Object[] { "G", "R", "I", 'G', "dustGlowstone", 'R', Blocks.redstone_torch, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.radio_torch_logic, 4), new Object[] { "G", "R", "I", 'G', "dustGlowstone", 'R', Blocks.redstone_torch, 'I', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP) });
		addRecipeAuto(new ItemStack(ModBlocks.radio_torch_counter, 4), new Object[] { "G", "R", "I", 'G', "dustGlowstone", 'R', Blocks.redstone_torch, 'I', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE) });
		addRecipeAuto(new ItemStack(ModBlocks.radio_telex, 2), new Object[] { "SCR", "W#W", "WWW", 'S', ModBlocks.radio_torch_sender, 'C', ModItems.crt_display, 'R', ModBlocks.radio_torch_receiver, 'W', KEY_PLANKS, '#', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG) });

		addRecipeAuto(DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR, 16), new Object[] { "LLL", "I I", "LLL", 'L', Items.leather, 'I', IRON.ingot() });
		addRecipeAuto(DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR, 16), new Object[] { "RSR", "I I", "RSR", 'I', IRON.ingot(), 'R', DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE), 'S', IRON.plate() });
		addRecipeAuto(DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR, 64), new Object[] { "LLL", "I I", "LLL", 'L', ANY_RUBBER.ingot(), 'I', IRON.ingot() });
		addRecipeAuto(DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.EXPRESS, 8), new Object[] { "CCC", "CLC", "CCC", 'C', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR), 'L', Fluids.LUBRICANT.getDict(1_000) });
		addRecipeAuto(DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.DOUBLE), new Object[] { "CPC", 'C', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR), 'P', IRON.plate() });
		addRecipeAuto(DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.TRIPLE), new Object[] { "DPC", 'C', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR), 'D', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.DOUBLE), 'P', STEEL.plate() });

		//addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_difurnace_off), 1), new Object[] { "T T", "PHP", "TFT", 'T', W.ingot(), 'P', ModItems.board_copper, 'H', Blocks.hopper, 'F', Blocks.furnace });
		addRecipeAuto(new ItemStack(ModBlocks.machine_difurnace_extension, 1), new Object[] { " C ", "BGB", "BGB", 'C', CU.plate(), 'B', ModItems.ingot_firebrick, 'G', ModBlocks.steel_grate });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.machine_electric_furnace_off), 1), new Object[] { "BBB", "WFW", "RRR", 'B', BE.ingot(), 'R', ModItems.coil_tungsten, 'W', CU.plateCast(), 'F', Item.getItemFromBlock(Blocks.furnace) });
		addRecipeAuto(new ItemStack(ModBlocks.red_wire_coated, 16), new Object[] { "WRW", "RIR", "WRW", 'W', ModItems.plate_polymer, 'I', MINGRADE.ingot(), 'R', MINGRADE.wireFine() });
		addRecipeAuto(new ItemStack(ModBlocks.red_cable_paintable, 16), new Object[] { "WRW", "RIR", "WRW", 'W', STEEL.plate(), 'I', MINGRADE.ingot(), 'R', MINGRADE.wireFine() });
		addRecipeAuto(new ItemStack(ModBlocks.cable_switch, 1), new Object[] { "S", "W", 'S', Blocks.lever, 'W', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(ModBlocks.cable_detector, 1), new Object[] { "S", "W", 'S', REDSTONE.dust(), 'W', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(ModBlocks.cable_diode, 1), new Object[] { " Q ", "CAC", " Q ", 'Q', SI.nugget(), 'C', ModBlocks.red_cable, 'A', AL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_detector, 1), new Object[] { "IRI", "CTC", "IRI", 'I', ModItems.plate_polymer, 'R', REDSTONE.dust(), 'C', MINGRADE.wireFine(), 'T', ModItems.coil_tungsten });
		addRecipeAuto(new ItemStack(ModBlocks.red_cable, 16), new Object[] { " W ", "RRR", " W ", 'W', ModItems.plate_polymer, 'R', MINGRADE.wireFine() });
		addShapelessAuto(new ItemStack(ModBlocks.red_cable_classic, 1), new Object[] { ModBlocks.red_cable });
		addShapelessAuto(new ItemStack(ModBlocks.red_cable, 1), new Object[] { ModBlocks.red_cable_classic });
		addShapelessAuto(new ItemStack(ModBlocks.red_cable_gauge), new Object[] { ModBlocks.red_wire_coated, STEEL.ingot(), DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC) });
		addRecipeAuto(new ItemStack(ModBlocks.red_connector, 4), new Object[] { "C", "I", "S", 'C', ModItems.coil_copper, 'I', ModItems.plate_polymer, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.red_pylon, 4), new Object[] { "CWC", "PWP", " T ", 'C', ModItems.coil_copper, 'W', KEY_PLANKS, 'P', ModItems.plate_polymer, 'T', ModBlocks.red_wire_coated });
		addRecipeAuto(new ItemStack(ModBlocks.red_pylon_medium_wood, 2), new Object[] { "CCW", "IIW", "  S", 'C', ModItems.coil_copper, 'W', KEY_PLANKS, 'I', ModItems.plate_polymer, 'S', KEY_COBBLESTONE });
		addShapelessAuto(new ItemStack(ModBlocks.red_pylon_medium_wood_transformer, 1), new Object[] { ModBlocks.red_pylon_medium_wood, ModItems.plate_polymer, ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModBlocks.red_pylon_medium_steel, 2), new Object[] { "CCW", "IIW", "  S", 'C', ModItems.coil_copper, 'W', STEEL.pipe(), 'I', ModItems.plate_polymer, 'S', KEY_COBBLESTONE });
		addShapelessAuto(new ItemStack(ModBlocks.red_pylon_medium_steel_transformer, 1), new Object[] { ModBlocks.red_pylon_medium_steel, ModItems.plate_polymer, ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_battery_potato, 1), new Object[] { "PCP", "WRW", "PCP", 'P', ItemBattery.getEmptyBattery(ModItems.battery_potato), 'C', CU.ingot(), 'R', REDSTONE.block(), 'W', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModBlocks.capacitor_bus, 1), new Object[] { "PIP", "PIP", "PIP", 'P', ModItems.plate_polymer, 'I', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.capacitor_copper, 1), new Object[] { "PPP", "PCP", "WWW", 'P', STEEL.plate(), 'C', CU.block(), 'W', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModBlocks.capacitor_gold, 1), new Object[] { "PPP", "ICI", "WWW", 'P', STEEL.plate(), 'I', ANY_PLASTIC.ingot(), 'C', GOLD.block(), 'W', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.capacitor_niobium, 1), new Object[] { "PPP", "ICI", "WWW", 'P', STEEL.plate(), 'I', RUBBER.ingot(), 'C', NB.block(), 'W', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.capacitor_tantalium, 1), new Object[] { "PPP", "ICI", "WWW", 'P', STEEL.plate(), 'I', ANY_RESISTANTALLOY.ingot(), 'C', TA.block(), 'W', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.capacitor_schrabidate, 1), new Object[] { "PPP", "ICI", "WWW", 'P', STEEL.plate(), 'I', ANY_RESISTANTALLOY.ingot(), 'C', SBD.block(), 'W', STEEL.ingot() });
		//addRecipeAuto(new ItemStack(ModBlocks.machine_coal_off, 1), new Object[] { "STS", "SCS", "SFS", 'S', STEEL.ingot(), 'T', ModItems.tank_steel, 'C', MINGRADE.ingot(), 'F', Blocks.furnace });
		addRecipeAuto(new ItemStack(ModBlocks.machine_wood_burner, 1), new Object[] { "PPP", "CFC", "I I" , 'P', STEEL.plate528(), 'C', ModItems.coil_copper, 'I', IRON.ingot(), 'F', Blocks.furnace});
		addRecipeAuto(new ItemStack(ModBlocks.machine_turbine, 1), new Object[] { "SMS", "PTP", "SMS", 'S', STEEL.ingot(), 'T', ModItems.turbine_titanium, 'M', ModItems.coil_copper, 'P', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_converter_he_rf, 1), new Object[] { "RRR", "WWW", "III", 'R', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR), 'W', REDSTONE.dust(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_converter_rf_he, 1), new Object[] { "RRR", "WWW", "III", 'R', REDSTONE.dust(), 'W', MINGRADE.wireFine(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.crate_template, 1), new Object[] { "IPI", "P P", "IPI", 'I', IRON.ingot(), 'P', Items.paper });
		addRecipeAuto(new ItemStack(ModBlocks.crate_iron, 1), new Object[] { "PPP", "I I", "III", 'P', IRON.plate(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.crate_steel, 1), new Object[] { "PPP", "I I", "III", 'P', STEEL.plate(), 'I', STEEL.ingot() });

		GameRegistry.addRecipe(new ContainerUpgradeCraftingHandler(new ItemStack(ModBlocks.crate_desh, 1), new Object[] { " D ", "DSD", " D ", 'D', ModItems.plate_desh, 'S', ModBlocks.crate_steel }));
		GameRegistry.addRecipe(new ContainerUpgradeCraftingHandler(new ItemStack(ModBlocks.crate_tungsten, 1), new Object[] { "BPB", "PCP", "BPB", 'B', W.block(), 'P', CU.plateCast(), 'C', ModBlocks.crate_steel }));
		// Note: voids the last few slots when placed, because a safe's inventory is smaller than a crate's one
		GameRegistry.addRecipe(new ContainerUpgradeCraftingHandler(new ItemStack(ModBlocks.safe, 1), new Object[] { "LAL", "ACA", "LAL", 'L', PB.plate(), 'A', ALLOY.plate(), 'C', ModBlocks.crate_steel }));
		// Note: doesn't preserve storage because a crate's contents are different items, but a mass storage's is just one
		addRecipeAuto(new ItemStack(ModBlocks.mass_storage, 1, 0), new Object[] { " L ", "ICI", " I ", 'I', TI.ingot(), 'C', ModBlocks.crate_steel, 'L', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE) });
		GameRegistry.addRecipe(new ContainerUpgradeCraftingHandler(new ItemStack(ModBlocks.mass_storage, 1, 1), new Object[] { " C ", "PMP", " P ", 'P', DESH.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), 'M', new ItemStack(ModBlocks.mass_storage, 1, 0) }));
		GameRegistry.addRecipe(new ContainerUpgradeCraftingHandler(new ItemStack(ModBlocks.mass_storage, 1, 2), new Object[] { " C ", "PMP", " P ", 'P', ANY_RESISTANTALLOY.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'M', new ItemStack(ModBlocks.mass_storage, 1, 1) }));
		addRecipeAuto(new ItemStack(ModBlocks.mass_storage, 1, 3), new Object[] { "PPP", "PIP", "PPP", 'P', KEY_PLANKS, 'I', IRON.plate() });

		addRecipeAuto(new ItemStack(ModBlocks.machine_autocrafter, 1), new Object[] { "SCS", "MWM", "SCS", 'S', STEEL.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'M', ModItems.motor, 'W', Blocks.crafting_table });
		addRecipeAuto(new ItemStack(ModBlocks.machine_funnel, 1), new Object[] { "S S", "SRS", " S ", 'S', STEEL.ingot(), 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_waste_drum, 1), new Object[] { "LRL", "BRB", "LRL", 'L', PB.ingot(), 'B', Blocks.iron_bars, 'R', ModItems.rod_quad_empty });
		addRecipeAuto(new ItemStack(ModBlocks.machine_press, 1), new Object[] { "IRI", "IPI", "IBI", 'I', IRON.ingot(), 'R', Blocks.furnace, 'B', IRON.block(), 'P', Blocks.piston });
		addRecipeAuto(new ItemStack(ModBlocks.machine_ammo_press, 1), new Object[] { "IPI", "C C", "SSS", 'I', IRON.ingot(), 'P', Blocks.piston, 'C', CU.ingot(), 'S', Blocks.stone });
		addRecipeAuto(new ItemStack(ModBlocks.machine_siren, 1), new Object[] { "SIS", "ICI", "SRS", 'S', STEEL.plate(), 'I', ANY_RUBBER.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.machine_microwave, 1), new Object[] { "III", "SGM", "IDI", 'I', ModItems.plate_polymer, 'S', STEEL.plate(), 'G', KEY_ANYPANE, 'M', ModItems.magnetron, 'D', ModItems.motor });
		addRecipeAuto(new ItemStack(ModBlocks.machine_solar_boiler), new Object[] { "SHS", "DHD", "SHS", 'S', STEEL.ingot(), 'H', STEEL.shell(), 'D', KEY_BLACK });
		addRecipeAuto(new ItemStack(ModBlocks.solar_mirror, 3), new Object[] { "AAA", " B ", "SSS", 'A', AL.plate(), 'B', ModBlocks.steel_beam, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.anvil_iron, 1), new Object[] { "III", " B ", "III", 'I', IRON.ingot(), 'B', IRON.block() });
		addRecipeAuto(new ItemStack(ModBlocks.anvil_lead, 1), new Object[] { "III", " B ", "III", 'I', PB.ingot(), 'B', PB.block() });
		addRecipeAuto(new ItemStack(ModBlocks.anvil_murky, 1), new Object[] { "UUU", "UAU", "UUU", 'U', ModItems.undefined, 'A', ModBlocks.anvil_steel });
		addRecipeAuto(new ItemStack(ModBlocks.machine_fraction_tower), new Object[] { "H", "G", "H", 'H', STEEL.plateWelded(), 'G', ModBlocks.steel_grate });
		addRecipeAuto(new ItemStack(ModBlocks.fraction_spacer), new Object[] { "BHB", 'H', STEEL.shell(), 'B', Blocks.iron_bars });
		addRecipeAuto(new ItemStack(ModBlocks.machine_furnace_brick_off), new Object[] { "III", "I I", "BBB", 'I', Items.brick, 'B', Blocks.stone });
		addRecipeAuto(new ItemStack(ModBlocks.furnace_iron), new Object[] { "III", "IFI", "BBB", 'I', IRON.ingot(), 'F', Blocks.furnace, 'B', Blocks.stonebrick });
		addRecipeAuto(new ItemStack(ModBlocks.machine_mixer), new Object[] { "PIP", "GCG", "PMP", 'P', STEEL.plate(), 'I', DURA.ingot(), 'G', KEY_ANYPANE, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'M', ModItems.motor });
		addRecipeAuto(new ItemStack(ModBlocks.fan), new Object[] { "BPB", "PRP", "BPB", 'B', STEEL.bolt(), 'P', IRON.plate(), 'R', REDSTONE.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.piston_inserter), new Object[] { "ITI", "TPT", "ITI", 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'I', IRON.plate(), 'T', STEEL.bolt() });

		addRecipeAuto(new ItemStack(ModItems.upgrade_muffler, 16), new Object[] { "III", "IWI", "III", 'I', ANY_RUBBER.ingot(), 'W', Blocks.wool });
		addRecipeAuto(new ItemStack(ModItems.upgrade_template, 1), new Object[] { "WIW", "PCP", "WIW", 'W', CU.wireFine(), 'I', IRON.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG), 'P', ModItems.plate_polymer });
		addRecipeAuto(new ItemStack(ModItems.upgrade_template, 1), new Object[] { "WIW", "PCP", "WIW", 'W', CU.wireFine(), 'I', ANY_PLASTIC.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'P', ModItems.plate_polymer });

		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_titanium_hull), 8), new Object[] { "PIP", "I I", "PIP", 'P', TI.plate(), 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.factory_advanced_hull), 8), new Object[] { "PIP", "I I", "PIP", 'P', ALLOY.plate(), 'I', ALLOY.ingot() });

		addRecipeAuto(DictFrame.fromOne(ModItems.arc_electrode, EnumElectrodeType.GRAPHITE), new Object[] { "C", "T", "C", 'C', GRAPHITE.ingot(), 'T', STEEL.bolt() });
		addRecipeAuto(DictFrame.fromOne(ModItems.arc_electrode, EnumElectrodeType.GRAPHITE), new Object[] { "C", "T", "C", 'C', PETCOKE.gem(), 'T', ANY_TAR.any() });
		addRecipeAuto(DictFrame.fromOne(ModItems.arc_electrode, EnumElectrodeType.LANTHANIUM), new Object[] { "C", "T", "C", 'C', LA.ingot(), 'T', KEY_BRICK });
		addRecipeAuto(DictFrame.fromOne(ModItems.arc_electrode, EnumElectrodeType.DESH), new Object[] { "C", "T", "C", 'C', DESH.ingot(), 'T', TI.ingot() });
		addRecipeAuto(DictFrame.fromOne(ModItems.arc_electrode, EnumElectrodeType.DESH), new Object[] { "C", "T", "C", 'C', DESH.ingot(), 'T', W.ingot() });
		addRecipeAuto(DictFrame.fromOne(ModItems.arc_electrode, EnumElectrodeType.SATURNITE), new Object[] { "C", "T", "C", 'C', BIGMT.ingot(), 'T', NB.ingot() });

		addRecipeAuto(new ItemStack(ModItems.detonator, 1), new Object[] { "C", "S", 'S', STEEL.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), });
		addShapelessAuto(new ItemStack(ModItems.detonator_multi, 1), new Object[] { ModItems.detonator, DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED) });
		addRecipeAuto(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', STEEL.plate(), 'R', REDSTONE.dust(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'D', DIAMOND.gem(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.detonator_laser, 1), new Object[] { "RRD", "PIC", "  P", 'P', STEEL.plate(), 'R', REDSTONE.dust(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'D', EMERALD.gem(), 'I', STEEL.ingot() });
		addShapelessAuto(new ItemStack(ModItems.detonator_deadman, 1), new Object[] { ModItems.detonator, ModItems.defuser, ModItems.ducttape });
		addRecipeAuto(new ItemStack(ModItems.detonator_de, 1), new Object[] { "T", "D", "T", 'T', Blocks.tnt, 'D', ModItems.detonator_deadman });

		addRecipeAuto(new ItemStack(ModItems.singularity, 1), new Object[] { "ESE", "SBS", "ESE", 'E', EUPH.nugget(), 'S', ModItems.cell_anti_schrabidium, 'B', SA326.block() });
		addRecipeAuto(new ItemStack(ModItems.singularity_counter_resonant, 1), new Object[] { "CTC", "TST", "CTC", 'C', CMB.plate(), 'T', MAGTUNG.ingot(), 'S', ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.singularity_super_heated, 1), new Object[] { "CTC", "TST", "CTC", 'C', ALLOY.plate(), 'T', ModItems.powder_power, 'S', ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.black_hole, 1), new Object[] { "SSS", "SCS", "SSS", 'C', ModItems.singularity, 'S', ModItems.crystal_xen });
		addRecipeAuto(new ItemStack(ModItems.crystal_xen, 1), new Object[] { "EEE", "EIE", "EEE", 'E', ModItems.powder_power, 'I', EUPH.ingot() });

		addShapelessAuto(new ItemStack(ModItems.fuse, 1), new Object[] { STEEL.plate(), ModItems.plate_polymer, W.wireFine() });
		addShapelessAuto(new ItemStack(ModItems.overfuse, 1), new Object[] { STEEL.bolt(), NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), CU.plateCast(), ModItems.black_hole, CS.dust() });
		addShapelessAuto(new ItemStack(ModItems.overfuse, 1), new Object[] { STEEL.bolt(), ST.dust(), BR.dust(), CO.dust(), TS.dust(), NB.dust(), CU.plateCast(), ModItems.black_hole, CE.dust() });

		addRecipeAuto(new ItemStack(ModItems.blades_steel, 1), new Object[] { " P ", "PIP", " P ", 'P', STEEL.plate(), 'I', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_titanium, 1), new Object[] { " P ", "PIP", " P ", 'P', TI.plate(), 'I', TI.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { " P ", "PIP", " P ", 'P', ALLOY.plate(), 'I', ALLOY.ingot() });
		addRecipeAuto(new ItemStack(ModItems.blades_desh, 1), new Object[] { " P ", "PBP", " P ", 'P', ModItems.plate_desh, 'B', ModItems.blades_advanced_alloy }); //4 desh ingots still needed to do anything

		addRecipeAuto(new ItemStack(ModItems.blades_steel, 1), new Object[] { "PIP", 'P', STEEL.plate(), 'I', new ItemStack(ModItems.blades_steel, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_titanium, 1), new Object[] { "PIP", 'P', TI.plate(), 'I', new ItemStack(ModItems.blades_titanium, 1, OreDictionary.WILDCARD_VALUE) });
		addRecipeAuto(new ItemStack(ModItems.blades_advanced_alloy, 1), new Object[] { "PIP", 'P', ALLOY.plate(), 'I', new ItemStack(ModItems.blades_advanced_alloy, 1, OreDictionary.WILDCARD_VALUE) });

		addRecipeAuto(new ItemStack(ModItems.laser_crystal_co2, 1), new Object[] { "QDQ", "NCN", "QDQ", 'Q', ModBlocks.glass_quartz, 'D', DESH.ingot(), 'N', NB.ingot(), 'C', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.CARBONDIOXIDE.getID()) });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_bismuth, 1), new Object[] {"QUQ", "BCB", "QTQ", 'Q', ModBlocks.glass_quartz, 'U', U.ingot(), 'T', TH232.ingot(), 'B', ModItems.nugget_bismuth, 'C', ModItems.crystal_rare });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_cmb, 1), new Object[] {"QBQ", "CSC", "QBQ", 'Q', ModBlocks.glass_quartz, 'B', CMB.ingot(), 'C', SBD.ingot(), 'S', ModItems.cell_anti_schrabidium });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_dnt, 1), new Object[] {"QDQ", "SBS", "QDQ", 'Q', ModBlocks.glass_quartz, 'D', DNT.ingot(), 'B', ModItems.egg_balefire, 'S', ModItems.powder_spark_mix });
		addRecipeAuto(new ItemStack(ModItems.laser_crystal_digamma, 1), new Object[] {"QUQ", "UEU", "QUQ", 'Q', ModBlocks.glass_quartz, 'U', ModItems.undefined, 'E', ModItems.ingot_electronium } );

		Item[] bricks = new Item[] {Items.brick, Items.netherbrick};

		for(Item brick : bricks) {
			addRecipeAuto(new ItemStack(ModItems.stamp_stone_flat, 1), new Object[] { "III", "SSS", 'I', brick, 'S', "stone" });
			addRecipeAuto(new ItemStack(ModItems.stamp_iron_flat, 1), new Object[] { "III", "SSS", 'I', brick, 'S', IRON.ingot() });
			addRecipeAuto(new ItemStack(ModItems.stamp_steel_flat, 1), new Object[] { "III", "SSS", 'I', brick, 'S', STEEL.ingot() });
			addRecipeAuto(new ItemStack(ModItems.stamp_titanium_flat, 1), new Object[] { "III", "SSS", 'I', brick, 'S', TI.ingot() });
			addRecipeAuto(new ItemStack(ModItems.stamp_obsidian_flat, 1), new Object[] { "III", "SSS", 'I', brick, 'S', Blocks.obsidian });
			addRecipeAuto(new ItemStack(ModItems.stamp_desh_flat, 1), new Object[] { "BDB", "DSD", "BDB", 'B', brick, 'D', DESH.ingot(), 'S', FERRO.ingot() });
		}

		addRecipeAuto(new ItemStack(ModBlocks.watz_pump, 1), new Object[] { "MPM", "PCP", "PSP", 'M', ModItems.motor_desh, 'P', ANY_RESISTANTALLOY.plateCast(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID), 'S', ModItems.pipes_steel });

		addRecipeAuto(new ItemStack(ModBlocks.reinforced_stone, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.cobblestone, 'B', Blocks.stone });
		addRecipeAuto(new ItemStack(ModBlocks.brick_light, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.fence, 'B', Blocks.brick_block });
		addRecipeAuto(new ItemStack(ModBlocks.brick_asbestos, 2), new Object[] { " A ", "ABA", " A ", 'B', ModBlocks.brick_light, 'A', ASBESTOS.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.concrete, 4), new Object[] { "CC", "CC", 'C', ModBlocks.concrete_smooth });
		addRecipeAuto(new ItemStack(ModBlocks.concrete_pillar, 6), new Object[] { "CBC", "CBC", "CBC", 'C', ModBlocks.concrete_smooth, 'B', Blocks.iron_bars });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete_smooth, 'B', Items.clay_ball });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete, 4), new Object[] { " C ", "CBC", " C ", 'C', ModBlocks.concrete, 'B', Items.clay_ball });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.brick_concrete, 'V', Blocks.vine });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.brick_concrete });
		addRecipeAuto(new ItemStack(ModBlocks.brick_concrete_broken, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.brick_concrete_cracked });
		addRecipeAuto(new ItemStack(ModBlocks.ducrete, 4), new Object[] { "DD", "DD", 'D', ModBlocks.ducrete_smooth });
		addRecipeAuto(new ItemStack(ModBlocks.brick_ducrete, 4), new Object[] {"CDC", "DLD", "CDC", 'D', ModBlocks.ducrete_smooth, 'C', Items.clay_ball, 'L', ModItems.plate_lead });
		addRecipeAuto(new ItemStack(ModBlocks.brick_ducrete, 4), new Object[] {"CDC", "DLD", "CDC", 'D', ModBlocks.ducrete, 'C', Items.clay_ball, 'L', ModItems.plate_lead });
		addRecipeAuto(new ItemStack(ModBlocks.reinforced_ducrete, 4), new Object[] {"DSD", "SUS", "DSD", 'D', ModBlocks.brick_ducrete, 'S', ModItems.plate_steel, 'U', U238.billet() });
		addRecipeAuto(new ItemStack(ModBlocks.brick_obsidian, 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.obsidian });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.block_meteor_broken });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_pillar, 2), new Object[] { "C", "C", 'C', ModBlocks.meteor_polished });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.meteor_polished });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick_mossy, 8), new Object[] { "CCC", "CVC", "CCC", 'C', ModBlocks.meteor_brick, 'V', Blocks.vine });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_brick_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.meteor_brick });
		addRecipeAuto(new ItemStack(ModBlocks.meteor_battery, 1), new Object[] { "MSM", "MWM", "MSM", 'M', ModBlocks.meteor_polished, 'S', STAR.block(), 'W', SA326.wireFine() });
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab, 4), new Object[] { "CBC", "CBC", "CBC", 'C', Items.brick, 'B', ASBESTOS.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab_cracked, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.tile_lab });
		addRecipeAuto(new ItemStack(ModBlocks.tile_lab_broken, 6), new Object[] { " C " , "C C", " C ", 'C', ModBlocks.tile_lab_cracked });
		addShapelessAuto(new ItemStack(ModBlocks.asphalt_light, 1), new Object[] { ModBlocks.asphalt, Items.glowstone_dust });
		addShapelessAuto(new ItemStack(ModBlocks.asphalt, 1), new Object[] { ModBlocks.asphalt_light });

		String[] dyes = { "Black", "Red", "Green", "Brown", "Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime", "Yellow", "LightBlue", "Magenta", "Orange", "White" };

		for(int i = 0; i < 16; i++) {
			String dyeName = "dye" + dyes[15 - i];
			addRecipeAuto(new ItemStack(ModBlocks.concrete_colored, 8, i), new Object[] { "CCC", "CDC", "CCC", 'C', ModBlocks.concrete_smooth, 'D', dyeName });
		}
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth, 1), new Object[] { new ItemStack(ModBlocks.concrete_colored, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(ModBlocks.concrete_smooth, 1), new Object[] { new ItemStack(ModBlocks.concrete_colored_ext, 1, OreDictionary.WILDCARD_VALUE) });

		addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.MACHINE.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', KEY_BROWN, '2', KEY_GRAY });
		addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.MACHINE_STRIPE.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', KEY_BROWN, '2', KEY_BLACK });
		addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.INDIGO.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', KEY_BLUE, '2', KEY_PURPLE });
		addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.PURPLE.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', KEY_PURPLE, '2', KEY_PURPLE });
		addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.PINK.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', KEY_PINK, '2', KEY_RED });
		addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.HAZARD.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', KEY_YELLOW, '2', KEY_BLACK });
		addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.SAND.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', KEY_YELLOW, '2', KEY_GRAY });
		addRecipeAuto(new ItemStack(ModBlocks.concrete_colored_ext, 6, EnumConcreteType.BRONZE.ordinal()), new Object[] { "CCC", "1 2", "CCC", 'C', ModBlocks.concrete_smooth, '1', KEY_ORANGE, '2', KEY_BROWN });

		addRecipeAuto(new ItemStack(ModBlocks.gneiss_tile, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_gneiss });
		addRecipeAuto(new ItemStack(ModBlocks.gneiss_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.gneiss_tile });
		addShapelessAuto(new ItemStack(ModBlocks.gneiss_chiseled, 1), new Object[] { ModBlocks.gneiss_tile });
		addRecipeAuto(new ItemStack(ModBlocks.depth_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_depth });
		addRecipeAuto(new ItemStack(ModBlocks.depth_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.depth_brick });
		addRecipeAuto(new ItemStack(ModBlocks.depth_nether_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.stone_depth_nether });
		addRecipeAuto(new ItemStack(ModBlocks.depth_nether_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.depth_nether_brick });
		addRecipeAuto(new ItemStack(ModBlocks.basalt_polished, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_smooth });
		addRecipeAuto(new ItemStack(ModBlocks.basalt_brick, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_polished });
		addRecipeAuto(new ItemStack(ModBlocks.basalt_tiles, 4), new Object[] { "CC", "CC", 'C', ModBlocks.basalt_brick });
		
		addShapelessAuto(new ItemStack(ModBlocks.lightstone, 4), new Object[] { Blocks.stone, Blocks.stone, Blocks.stone, ModItems.powder_limestone });
		addRecipeAuto(new ItemStack(ModBlocks.lightstone, 4, LightstoneType.TILE.ordinal()), new Object[] { "CC", "CC", 'C', new ItemStack(ModBlocks.lightstone, 1, 0) });
		addRecipeAuto(new ItemStack(ModBlocks.lightstone, 4, LightstoneType.BRICKS.ordinal()), new Object[] { "CC", "CC", 'C', new ItemStack(ModBlocks.lightstone, 1, LightstoneType.TILE.ordinal()) });
		addShapelessAuto(new ItemStack(ModBlocks.lightstone, 1, LightstoneType.BRICKS_CHISELED.ordinal()), new Object[] { new ItemStack(ModBlocks.lightstone, 1, LightstoneType.BRICKS.ordinal()) });
		addShapelessAuto(new ItemStack(ModBlocks.lightstone, 1, LightstoneType.CHISELED.ordinal()), new Object[] { ModBlocks.lightstone });

		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_brick), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', ModBlocks.brick_concrete });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.brick_compound), 4), new Object[] { "FBF", "BTB", "FBF", 'F', STEEL.bolt(), 'B', ModBlocks.reinforced_brick, 'T', ANY_TAR.any() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_glass), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.glass });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_glass_pane), 16), new Object[] { "   ", "GGG", "GGG", 'G', ModBlocks.reinforced_glass});
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_laminate_pane), 16), new Object[] { "   ", "LLL", "LLL", 'L', ModBlocks.reinforced_laminate});
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_light), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.glowstone });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_lamp_off), 1), new Object[] { "FFF", "FBF", "FFF", 'F', Blocks.iron_bars, 'B', Blocks.redstone_lamp });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.reinforced_sand), 4), new Object[] { "FBF", "BFB", "FBF", 'F', Blocks.iron_bars, 'B', Blocks.sandstone });

		addShapelessAuto(new ItemStack(ModBlocks.lamp_tritium_green_off, 1), new Object[] { KEY_ANYGLASS, P_RED.dust(), Fluids.TRITIUM.getDict(1_000), S.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.lamp_tritium_blue_off, 1), new Object[] {  KEY_ANYGLASS, P_RED.dust(), Fluids.TRITIUM.getDict(1_000), AL.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.lantern, 1), new Object[] { "PGP", " S ", " S ", 'P', KEY_ANYPANE, 'G', Items.glowstone_dust, 'S', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(ModBlocks.spotlight_incandescent, 8), new Object[] { "G", "T", "I", 'G', KEY_ANYPANE, 'T', W.wireFine(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.spotlight_fluoro, 8), new Object[] { "G", "M", "A", 'G', KEY_ANYPANE, 'M', ModItems.ingot_mercury, 'A', ModItems.plate_aluminium });
		addRecipeAuto(new ItemStack(ModBlocks.spotlight_halogen, 8), new Object[] { "G", "B", "S", 'G', KEY_ANYPANE, 'B', ModItems.powder_bromine, 'S', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.floodlight, 2), new Object[] { "CSC", "TST", "G G", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR), 'S', STEEL.plate(), 'T', ModItems.coil_tungsten, 'G', KEY_ANYPANE });

		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire, 16), new Object[] { "AIA", "I I", "AIA", 'A', STEEL.wireFine(), 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_fire, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', P_RED.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_poison, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', ModItems.powder_poison });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_acid, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(ModItems.fluid_tank_full, 1, Fluids.PEROXIDE.getID()) });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_wither, 8), new Object[] { "BBB", "BIB", "BBB", 'B', ModBlocks.barbed_wire, 'I', new ItemStack(Items.skull, 1, 1) });
		addRecipeAuto(new ItemStack(ModBlocks.barbed_wire_ultradeath, 4), new Object[] { "BCB", "CIC", "BCB", 'B', ModBlocks.barbed_wire, 'C', ModItems.powder_yellowcake, 'I', ModItems.nuclear_waste });

		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.tape_recorder), 4), new Object[] { "TST", "SSS", 'T', W.ingot(), 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_poles), 16), new Object[] { "S S", "SSS", "S S", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_top), 1), new Object[] { "T T", "TRT", "BBB", 'T', W.ingot(), 'B', BE.ingot(), 'R', MINGRADE.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.pole_satellite_receiver), 1), new Object[] { "SS ", "SCR", "SS ", 'S', STEEL.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'R', MINGRADE.wireFine() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_wall), 4), new Object[] { "SSS", "SSS", 'S', STEEL.ingot() });
		addShapelessAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_corner)), new Object[] { ModBlocks.steel_wall, ModBlocks.steel_wall });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_roof), 2), new Object[] { "SSS", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_scaffold), 8), new Object[] { "SSS", " S ", "SSS", 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_beam), 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.chain), 8), new Object[] { "S", "S", "S", 'S', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_grate), 4), new Object[] { "SS", "SS", 'S', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_grate_wide), 4), new Object[] { "SS", 'S', ModBlocks.steel_grate });
		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.steel_grate), 1), new Object[] { "SS", 'S', ModBlocks.steel_grate_wide });


		addRecipeAuto(new ItemStack(ModBlocks.steel_scaffold, 8, 0), new Object[] { "SSS", "SDS", "SSS", 'S', ModBlocks.steel_scaffold, 'D', "dyeGray" });
		addRecipeAuto(new ItemStack(ModBlocks.steel_scaffold, 8, 1), new Object[] { "SSS", "SDS", "SSS", 'S', ModBlocks.steel_scaffold, 'D', "dyeRed" });
		addRecipeAuto(new ItemStack(ModBlocks.steel_scaffold, 8, 2), new Object[] { "SSS", "SDS", "SSS", 'S', ModBlocks.steel_scaffold, 'D', "dyeWhite" });
		addRecipeAuto(new ItemStack(ModBlocks.steel_scaffold, 8, 3), new Object[] { "SSS", "SDS", "SSS", 'S', ModBlocks.steel_scaffold, 'D', "dyeYellow" });

		reg2();
	}

	public static void reg2() {

		addRecipeAuto(new ItemStack(ModBlocks.sat_dock, 1), new Object[] { "SSS", "PCP", 'S', STEEL.ingot(), 'P', ANY_PLASTIC.ingot(), 'C', ModBlocks.crate_iron });
		addRecipeAuto(new ItemStack(ModBlocks.book_guide, 1), new Object[] { "IBI", "LBL", "IBI", 'B', Items.book, 'I', KEY_BLACK, 'L', KEY_BLUE });

		addRecipeAuto(new ItemStack(ModBlocks.rail_wood, 16), new Object[] { "S S", "SRS", "S S", 'S', Items.stick, 'R', DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE) });
		addRecipeAuto(new ItemStack(ModBlocks.rail_narrow, 64), new Object[] { "S S", "S S", "S S", 'S', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(ModBlocks.rail_highspeed, 16), new Object[] { "S S", "SIS", "S S", 'S', STEEL.ingot(), 'I', IRON.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.rail_booster, 6), new Object[] { "S S", "CIC", "SRS", 'S', STEEL.ingot(), 'I', IRON.plate(), 'R', MINGRADE.ingot(), 'C', ModItems.coil_copper });

		addRecipeAuto(new ItemStack(ModBlocks.rail_large_straight, 4), new Object[] { "B B", "SSS", "W W", 'B', ModItems.bolt_spike, 'S', ModBlocks.steel_beam, 'W', KEY_SLAB });
		addShapelessAuto(new ItemStack(ModBlocks.rail_large_straight, 1), new Object[] { ModBlocks.rail_large_straight_short, ModBlocks.rail_large_straight_short, ModBlocks.rail_large_straight_short, ModBlocks.rail_large_straight_short, ModBlocks.rail_large_straight_short });
		addShapelessAuto(new ItemStack(ModBlocks.rail_large_straight_short, 5), new Object[] { ModBlocks.rail_large_straight });
		addRecipeAuto(new ItemStack(ModBlocks.rail_large_buffer, 1), new Object[] { " S", "RS", 'R', ModBlocks.rail_large_straight, 'S', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.rail_large_curve, 1), new Object[] { "R ", " R", 'R', ModBlocks.rail_large_straight });
		addRecipeAuto(new ItemStack(ModBlocks.rail_large_curve_7, 1), new Object[] { "RR", " R", 'R', ModBlocks.rail_large_straight });
		addRecipeAuto(new ItemStack(ModBlocks.rail_large_curve_9, 1), new Object[] { "RR ", "  R", "  R", 'R', ModBlocks.rail_large_straight });
		addRecipeAuto(new ItemStack(ModBlocks.rail_large_ramp, 1), new Object[] { " R ", "SSS", 'R', ModBlocks.rail_large_straight, 'S', KEY_SLAB });
		addRecipeAuto(new ItemStack(ModBlocks.rail_large_switch, 1), new Object[] { "R R", " RR", "  R", 'R', ModBlocks.rail_large_straight });
		addShapelessAuto(new ItemStack(ModBlocks.rail_large_switch), new Object[] { ModBlocks.rail_large_switch_flipped });
		addShapelessAuto(new ItemStack(ModBlocks.rail_large_switch_flipped), new Object[] { ModBlocks.rail_large_switch });

		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.bomb_multi), 1), new Object[] { "AAD", "CHF", "AAD", 'A', AL.wireFine(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'H', AL.shell(), 'F', ModItems.fins_quad_titanium, 'D', KEY_WHITE });
		addShapelessAuto(new ItemStack(ModItems.powder_ice, 4), new Object[] { Items.snowball, KNO.dust(), REDSTONE.dust() });
		addShapelessAuto(new ItemStack(ModItems.powder_poison, 4), new Object[] { Items.spider_eye, REDSTONE.dust(), NETHERQUARTZ.gem() });
		addShapelessAuto(new ItemStack(ModItems.pellet_gas, 2), new Object[] { Items.water_bucket, "dustGlowstone", STEEL.plate() });

		addRecipeAuto(new ItemStack(ModItems.flame_pony, 1), new Object[] { " O ", "DPD", " O ", 'D', "dyePink", 'O', KEY_YELLOW, 'P', Items.paper });
		addRecipeAuto(new ItemStack(ModItems.flame_conspiracy, 1), new Object[] { " S ", "STS", " S ", 'S', Fluids.KEROSENE.getDict(1000), 'T', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.flame_politics, 1), new Object[] { " I ", "IPI", " I ", 'P', Items.paper, 'I', KEY_BLACK });
		addRecipeAuto(new ItemStack(ModItems.flame_opinion, 1), new Object[] { " R ", "RPR", " R ", 'P', Items.paper, 'R', KEY_RED });

		addRecipeAuto(new ItemStack(ModItems.solid_fuel_presto, 1), new Object[] { " P ", "SRS", " P ", 'P', Items.paper, 'S', ModItems.solid_fuel, 'R', REDSTONE.dust() });
		addShapelessAuto(new ItemStack(ModItems.solid_fuel_presto_triplet, 1), new Object[] { ModItems.solid_fuel_presto, ModItems.solid_fuel_presto, ModItems.solid_fuel_presto, ModItems.ball_dynamite });
		addRecipeAuto(new ItemStack(ModItems.solid_fuel_presto_bf, 1), new Object[] { " P ", "SRS", " P ", 'P', Items.paper, 'S', ModItems.solid_fuel_bf, 'R', REDSTONE.dust() });
		addShapelessAuto(new ItemStack(ModItems.solid_fuel_presto_triplet_bf, 1), new Object[] { ModItems.solid_fuel_presto_bf, ModItems.solid_fuel_presto_bf, ModItems.solid_fuel_presto_bf, ModItems.ingot_c4 });

		addRecipeAuto(new ItemStack(Item.getItemFromBlock(ModBlocks.flame_war), 1), new Object[] { "WHW", "CTP", "WOW", 'W', Item.getItemFromBlock(Blocks.planks), 'T', Item.getItemFromBlock(Blocks.tnt), 'H', ModItems.flame_pony, 'C', ModItems.flame_conspiracy, 'P', ModItems.flame_politics, 'O', ModItems.flame_opinion });
		addRecipeAuto(new ItemStack(ModBlocks.det_cord, 4), new Object[] { " P ", "PGP", " P ", 'P', Items.paper, 'G', Items.gunpowder });
		addRecipeAuto(new ItemStack(ModBlocks.det_charge, 1), new Object[] { "PDP", "DTD", "PDP", 'P', STEEL.plate(), 'D', ModBlocks.det_cord, 'T', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.det_nuke, 1), new Object[] { "PFP", "DCD", "PFP", 'P', DESH.plateCast(), 'D', ModBlocks.det_charge, 'C', ModItems.man_core, 'F', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CONTROLLER) });
		addRecipeAuto(new ItemStack(ModBlocks.det_miner, 4), new Object[] { "FFF", "ITI", "ITI", 'F', Items.flint, 'I', IRON.plate(), 'T', ModItems.ball_dynamite });
		addRecipeAuto(new ItemStack(ModBlocks.det_miner, 12), new Object[] { "FFF", "ITI", "ITI", 'F', Items.flint, 'I', STEEL.plate(), 'T', ANY_PLASTICEXPLOSIVE.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.emp_bomb, 1), new Object[] { "LML", "LCL", "LML", 'L', PB.plate(), 'M', ModItems.magnetron, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED) });
		addShapelessAuto(new ItemStack(ModBlocks.charge_dynamite, 1), new Object[] { ModItems.stick_dynamite, ModItems.stick_dynamite, ModItems.stick_dynamite, ModItems.ducttape });
		addRecipeAuto(new ItemStack(ModBlocks.charge_miner, 1), new Object[] { " F ", "FCF", " F ", 'F', Items.flint, 'C', ModBlocks.charge_dynamite });
		addShapelessAuto(new ItemStack(ModBlocks.charge_semtex, 1), new Object[] { ModItems.stick_semtex, ModItems.stick_semtex, ModItems.stick_semtex, ModItems.ducttape });
		addShapelessAuto(new ItemStack(ModBlocks.charge_c4, 1), new Object[] { ModItems.stick_c4, ModItems.stick_c4, ModItems.stick_c4, ModItems.ducttape });

		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_generic), new Object[] { " A ", "PRP", "PRP", 'A', AL.wireFine(), 'P', AL.plate(), 'R', REDSTONE.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', MINGRADE.wireFine(), 'P', CU.plate(), 'S', "sulfur", 'L', PB.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', MINGRADE.wireFine(), 'P', CU.plate(), 'S', "sulfur", 'L', PB.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PSP", "PLP", 'A', MINGRADE.wireFine(), 'P', CU.plate(), 'S', "dustSulfur", 'L', PB.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced), new Object[] { " A ", "PLP", "PSP", 'A', MINGRADE.wireFine(), 'P', CU.plate(), 'S', "dustSulfur", 'L', PB.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PSP", "PLP", 'A', GOLD.wireFine(), 'P', TI.plate(), 'S', LI.dust(), 'L', CO.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium), new Object[] { "A A", "PLP", "PSP", 'A', GOLD.wireFine(), 'P', TI.plate(), 'S', LI.dust(), 'L', CO.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PNP", "PSP", 'A', SA326.wireFine(), 'P', SA326.plate(), 'S', SA326.dust(), 'N', NP237.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium), new Object[] { " A ", "PSP", "PNP", 'A', SA326.wireFine(), 'P', SA326.plate(), 'S', SA326.dust(), 'N', NP237.dust() });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark), new Object[] { "P", "S", "S", 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_trixite), new Object[] { " A ", "PSP", "PTP", 'A', AL.wireFine(), 'P', AL.plate(), 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_trixite), new Object[] { " A ", "PTP", "PSP", 'A', AL.wireFine(), 'P', AL.plate(), 'S', ModItems.powder_power, 'T', ModItems.crystal_trixite });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TRD", "PCW", 'P', ALLOY.plate(), 'C', ModItems.coil_advanced_alloy, 'W', ALLOY.wireFine(), 'R', ModItems.cell_tritium, 'D', ModItems.cell_deuterium, 'T', W.ingot() });
		addRecipeAuto(ItemBattery.getFullBattery(ModItems.energy_core), new Object[] { "PCW", "TDR", "PCW", 'P', ALLOY.plate(), 'C', ModItems.coil_advanced_alloy, 'W', ALLOY.wireFine(), 'R', ModItems.cell_tritium, 'D', ModItems.cell_deuterium, 'T', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "IEI", "ICI", 'W', GOLD.wireFine(), 'I', ModItems.plate_polymer, 'E', REDSTONE.dust(), 'C', CO.dust() });
		addRecipeAuto(new ItemStack(ModItems.hev_battery, 4), new Object[] { " W ", "ICI", "IEI", 'W', GOLD.wireFine(), 'I', ModItems.plate_polymer, 'E', REDSTONE.dust(), 'C', CO.dust() });
		addShapelessAuto(new ItemStack(ModItems.hev_battery, 1), new Object[] { ModBlocks.hev_battery });
		addShapelessAuto(new ItemStack(ModBlocks.hev_battery, 1), new Object[] { ModItems.hev_battery });

		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell), new Object[] { "WBW", "PBP", "WBW", 'W', AL.wireFine(), 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_generic) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell), new Object[] { "WBW", "PBP", "WBW", 'W', MINGRADE.wireFine(), 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', GOLD.wireFine(), 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell), new Object[] { "WBW", "PBP", "WBW", 'W', SA326.wireFine(), 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6), new Object[] { "BBB", "WPW", "BBB", 'W', AL.wireFine(), 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4), new Object[] { "BWB", "WPW", "BWB", 'W', MINGRADE.wireFine(), 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3), new Object[] { "WPW", "BBB", "WPW", 'W', GOLD.wireFine(), 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2), new Object[] { "WPW", "BWB", "WPW", 'W', SA326.wireFine(), 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_red_cell_24), new Object[] { "BWB", "WPW", "BWB", 'W', AL.wireFine(), 'P', AL.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_red_cell_6) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_12), new Object[] { "WPW", "BBB", "WPW", 'W', MINGRADE.wireFine(), 'P', CU.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_advanced_cell_4) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6), new Object[] { "WPW", "BWB", "WPW", 'W', GOLD.wireFine(), 'P', TI.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_3) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_4), new Object[] { "WPW", "BWB", "WPW", 'W', SA326.wireFine(), 'P', SA326.plate(), 'B', ItemBattery.getEmptyBattery(ModItems.battery_schrabidium_cell_2) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6), new Object[] { "BW", "PW", "BW", 'W', MAGTUNG.wireFine(), 'P', ModItems.powder_spark_mix, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25), new Object[] { "W W", "SCS", "PSP", 'W', MAGTUNG.wireFine(), 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_6) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), new Object[] { "W W", "BPB", "SSS", 'W', MAGTUNG.wireFine(), 'P', ModItems.plate_dineutronium, 'S', ModItems.powder_spark_mix, 'B', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_25) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), new Object[] { "PCP", "CSC", "PCP", 'S', ModItems.singularity_spark, 'P', ModItems.powder_spark_mix, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100) });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), new Object[] { "SCS", "CVC", "SCS", 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_100), 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_1000), 'S', ModItems.powder_spark_mix });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), new Object[] { "OSO", "SVS", "OSO", 'S', ModItems.singularity_spark, 'V', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_2500), 'O', ModItems.ingot_osmiridium });
		addRecipeAuto(ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_power), new Object[] { "YSY", "SCS", "YSY", 'S', ModItems.singularity_spark, 'C', ItemBattery.getEmptyBattery(ModItems.battery_spark_cell_10000), 'Y', ModItems.billet_yharonite });

		addShapelessAuto(ItemBattery.getFullBattery(ModItems.battery_potato), new Object[] { Items.potato, AL.wireFine(), CU.wireFine() });
		addShapelessAuto(ItemBattery.getFullBattery(ModItems.battery_potatos), new Object[] { ItemBattery.getFullBattery(ModItems.battery_potato), ModItems.turret_chip, REDSTONE.dust() });

		addRecipeAuto(new ItemStack(ModItems.battery_sc_uranium), new Object[] { "NBN", "PCP", "NBN", 'N', GOLD.nugget(), 'B', U238.billet(), 'P', PB.plate(), 'C', ModItems.thermo_element });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_technetium), new Object[] { "NBN", "PCP", "NBN", 'N', GOLD.nugget(), 'B', TC99.billet(), 'P', PB.plate(), 'C', ModItems.battery_sc_uranium });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_plutonium), new Object[] { "NBN", "PCP", "NBN", 'N', TC99.nugget(), 'B', PU238.billet(), 'P', PB.plate(), 'C', ModItems.battery_sc_technetium });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_polonium), new Object[] { "NBN", "PCP", "NBN", 'N', TC99.nugget(), 'B', PO210.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_plutonium });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_gold), new Object[] { "NBN", "PCP", "NBN", 'N', TA.nugget(), 'B', AU198.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_polonium });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_lead), new Object[] { "NBN", "PCP", "NBN", 'N', TA.nugget(), 'B', PB209.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_gold });
		addRecipeAuto(new ItemStack(ModItems.battery_sc_americium), new Object[] { "NBN", "PCP", "NBN", 'N', TA.nugget(), 'B', AM241.billet(), 'P', ANY_PLASTIC.ingot(), 'C', ModItems.battery_sc_lead });

		addRecipeAuto(new ItemStack(ModItems.wiring_red_copper, 1), new Object[] { "PPP", "PIP", "PPP", 'P', STEEL.plate(), 'I', STEEL.ingot() });

		addRecipeAuto(new ItemStack(ModItems.jetpack_tank, 1), new Object[] { " S ", "BKB", " S ", 'S', STEEL.plate(), 'B', STEEL.bolt(), 'K', Fluids.KEROSENE.getDict(1000) });
		addShapelessAuto(new ItemStack(ModItems.gun_kit_1, 1), new Object[] { ANY_RUBBER.ingot(), Fluids.WOODOIL.getDict(1_000), IRON.ingot() });
		addShapelessAuto(new ItemStack(ModItems.gun_kit_2, 1), new Object[] { ModItems.gun_kit_1, ModItems.wrench, ModItems.ducttape, Fluids.LUBRICANT.getDict(1_000) });

		addRecipeAuto(new ItemStack(ModItems.igniter, 1), new Object[] { " W", "SC", "CE", 'S', STEEL.plate(), 'W', SA326.wireFine(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'E', EUPH.ingot() });
		addRecipeAuto(new ItemStack(ModItems.watch, 1), new Object[] { "LYL", "EWE", "LYL", 'E', EUPH.ingot(), 'L', KEY_BLUE, 'W', Items.clock, 'Y', ModItems.billet_yharonite });

		addRecipeAuto(new ItemStack(ModItems.key, 1), new Object[] { "  B", " B ", "P  ", 'P', STEEL.plate(), 'B', STEEL.bolt() });
		addRecipeAuto(new ItemStack(ModItems.key_kit, 1), new Object[] { "PKP", "DTD", "PKP", 'P', GOLD.plate(), 'K', ModItems.key, 'D', DESH.dust(), 'T', KEY_TOOL_SCREWDRIVER });
		addRecipeAuto(new ItemStack(ModItems.key_red, 1), new Object[] { "RCA", "CIC", "KCR", 'R', KEY_RED, 'C', STAR.wireDense(), 'A', ModItems.gem_alexandrite, 'I', ModItems.ingot_chainsteel, 'K', ModItems.key });
		addRecipeAuto(new ItemStack(ModItems.pin, 1), new Object[] { "W ", " W", " W", 'W', CU.wireFine() });
		addRecipeAuto(new ItemStack(ModItems.padlock_rusty, 1), new Object[] { "I", "B", "I", 'I', IRON.ingot(), 'B', STEEL.bolt() });
		addRecipeAuto(new ItemStack(ModItems.padlock, 1), new Object[] { " P ", "PBP", "PPP", 'P', STEEL.plate(), 'B', STEEL.bolt() });
		addRecipeAuto(new ItemStack(ModItems.padlock_reinforced, 1), new Object[] { " P ", "PBP", "PDP", 'P', ALLOY.plate(), 'D', ModItems.plate_desh, 'B', DURA.bolt() });
		addRecipeAuto(new ItemStack(ModItems.padlock_unbreakable, 1), new Object[] { " P ", "PBP", "PDP", 'P', BIGMT.plate(), 'D', DIAMOND.gem(), 'B', DURA.bolt() });

		addRecipeAuto(new ItemStack(ModItems.record_lc, 1), new Object[] { " S ", "SDS", " S ", 'S', ANY_PLASTIC.ingot(), 'D', LAPIS.dust() });
		addRecipeAuto(new ItemStack(ModItems.record_ss, 1), new Object[] { " S ", "SDS", " S ", 'S', ANY_PLASTIC.ingot(), 'D', ALLOY.dust() });
		addRecipeAuto(new ItemStack(ModItems.record_vc, 1), new Object[] { " S ", "SDS", " S ", 'S', ANY_PLASTIC.ingot(), 'D', CMB.dust() });

		addRecipeAuto(new ItemStack(ModItems.polaroid, 1), new Object[] { " C ", "RPY", " B ", 'B', LAPIS.dust(), 'C', COAL.dust(), 'R', ALLOY.dust(), 'Y', GOLD.dust(), 'P', Items.paper });

		addShapelessAuto(new ItemStack(ModItems.crystal_horn, 1), new Object[] { NP237.dust(), I.dust(), TH232.dust(), AT.dust(), ND.dust(), CS.dust(), ModBlocks.block_meteor, ModBlocks.gravel_obsidian, Items.water_bucket });
		addShapelessAuto(new ItemStack(ModItems.crystal_charred, 1), new Object[] { ST.dust(), CO.dust(), BR.dust(), NB.dust(), TS.dust(), CE.dust(), ModBlocks.block_meteor, AL.block(), Items.water_bucket });
		addRecipeAuto(new ItemStack(ModBlocks.crystal_virus, 1), new Object[] { "STS", "THT", "STS", 'S', ModItems.particle_strange, 'T', W.dust(), 'H', ModItems.crystal_horn });
		addRecipeAuto(new ItemStack(ModBlocks.crystal_pulsar, 32), new Object[] { "STS", "THT", "STS", 'S', ModItems.cell_uf6, 'T', AL.dust(), 'H', ModItems.crystal_charred });

		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_neo, 8, 0), new Object[] { "SAS", "   ", "SAS", 'S', STEEL.plate(), 'A', AL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_neo, 8, 1), new Object[] { "IAI", "   ", "IAI", 'I', IRON.plate(), 'A', AL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_neo, 8, 2), new Object[] { "ASA", "   ", "ASA", 'S', STEEL.plate(), 'A', AL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.fluid_duct_paintable, 8), new Object[] { "SAS", "A A", "SAS", 'S', STEEL.ingot(), 'A', AL.plate() });
		addShapelessAuto(new ItemStack(ModBlocks.fluid_duct_gauge), new Object[] { ModBlocks.fluid_duct_paintable, STEEL.ingot(), DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC) });
		addRecipeAuto(new ItemStack(ModBlocks.fluid_valve, 1), new Object[] { "S", "W", 'S', Blocks.lever, 'W', ModBlocks.fluid_duct_paintable });
		addRecipeAuto(new ItemStack(ModBlocks.fluid_switch, 1), new Object[] { "S", "W", 'S', REDSTONE.dust(), 'W', ModBlocks.fluid_duct_paintable });
		addRecipeAuto(new ItemStack(ModItems.template_folder, 1), new Object[] { "LPL", "BPB", "LPL", 'P', Items.paper, 'L', "dye", 'B', "dye" });
		addRecipeAuto(new ItemStack(ModItems.pellet_antimatter, 1), new Object[] { "###", "###", "###", '#', ModItems.cell_antimatter });
		addRecipeAuto(new ItemStack(ModItems.fluid_tank_empty, 8), new Object[] { "121", "1G1", "121", '1', AL.plate(), '2', IRON.plate(), 'G', KEY_ANYPANE });
		addRecipeAuto(new ItemStack(ModItems.fluid_tank_lead_empty, 4), new Object[] { "LUL", "LTL", "LUL", 'L', PB.plate(), 'U', U238.billet(), 'T', ModItems.fluid_tank_empty });
		addRecipeAuto(new ItemStack(ModItems.fluid_barrel_empty, 2), new Object[] { "121", "1G1", "121", '1', STEEL.plate(), '2', AL.plate(), 'G', KEY_ANYPANE });

		if(!GeneralConfig.enable528) {
			addRecipeAuto(new ItemStack(ModItems.inf_water, 1), new Object[] { "222", "131", "222", '1', Items.water_bucket, '2', AL.plate(), '3', DIAMOND.gem() });
			addRecipeAuto(new ItemStack(ModItems.inf_water_mk2, 1), new Object[] { "BPB", "PTP", "BPB", 'B', ModItems.inf_water, 'P', ModBlocks.fluid_duct_neo, 'T', ModItems.tank_steel });
		}

		//not so Temporary Crappy Recipes
		addRecipeAuto(new ItemStack(ModItems.piston_selenium, 1), new Object[] { "SSS", "STS", " D ", 'S', STEEL.plate(), 'T', W.ingot(), 'D', DURA.bolt() });
		addShapelessAuto(new ItemStack(ModItems.catalyst_clay), new Object[] { IRON.dust(), Items.clay_ball });
		addRecipeAuto(new ItemStack(ModItems.singularity_spark, 1), new Object[] { "XAX", "BCB", "XAX", 'X', ModItems.plate_dineutronium, 'A', ModItems.singularity_counter_resonant, 'B', ModItems.singularity_super_heated, 'C', ModItems.black_hole });
		addRecipeAuto(new ItemStack(ModItems.singularity_spark, 1), new Object[] { "XBX", "ACA", "XBX", 'X', ModItems.plate_dineutronium, 'A', ModItems.singularity_counter_resonant, 'B', ModItems.singularity_super_heated, 'C', ModItems.black_hole });
		addRecipeAuto(new ItemStack(ModItems.ams_core_sing, 1), new Object[] { "EAE", "ASA", "EAE", 'E', ModItems.plate_euphemium, 'A', ModItems.cell_anti_schrabidium, 'S', ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.ams_core_wormhole, 1), new Object[] { "DPD", "PSP", "DPD", 'D', ModItems.plate_dineutronium, 'P', ModItems.powder_spark_mix, 'S', ModItems.singularity });
		addRecipeAuto(new ItemStack(ModItems.ams_core_eyeofharmony, 1), new Object[] { "ALA", "LSL", "ALA", 'A', ModItems.plate_dalekanium, 'L', new ItemStack(ModItems.fluid_barrel_full, 1, Fluids.LAVA.getID()), 'S', ModItems.black_hole });
		addRecipeAuto(new ItemStack(ModItems.ams_core_thingy), new Object[] { "NSN", "NGN", "G G", 'N', GOLD.nugget(), 'G', GOLD.ingot(), 'S', ModItems.battery_spark_cell_10000 });
		addRecipeAuto(new ItemStack(ModItems.photo_panel), new Object[] { " G ", "IPI", " C ", 'G', KEY_ANYPANE, 'I', ModItems.plate_polymer, 'P', NETHERQUARTZ.dust(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.PCB) });
		addRecipeAuto(new ItemStack(ModBlocks.machine_satlinker), new Object[] { "PSP", "SCS", "PSP", 'P', STEEL.plate(), 'S', STAR.ingot(), 'C', ModItems.sat_chip });
		addRecipeAuto(new ItemStack(ModBlocks.machine_keyforge), new Object[] { "PCP", "WSW", "WSW", 'P', STEEL.plate(), 'S', W.ingot(), 'C', ModItems.padlock, 'W', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModItems.sat_chip), new Object[] { "WWW", "CIC", "WWW", 'W', MINGRADE.wireFine(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'I', ANY_PLASTIC.ingot() });
		addShapelessAuto(new ItemStack(ModItems.sat_mapper), new Object[] { ModBlocks.sat_mapper });
		addShapelessAuto(new ItemStack(ModItems.sat_scanner), new Object[] { ModBlocks.sat_scanner });
		addShapelessAuto(new ItemStack(ModItems.sat_radar), new Object[] { ModBlocks.sat_radar });
		addShapelessAuto(new ItemStack(ModItems.sat_laser), new Object[] { ModBlocks.sat_laser });
		addShapelessAuto(new ItemStack(ModItems.sat_resonator), new Object[] { ModBlocks.sat_resonator });
		addShapelessAuto(new ItemStack(ModItems.sat_foeq), new Object[] { ModBlocks.sat_foeq });
		addShapelessAuto(new ItemStack(ModItems.geiger_counter), new Object[] { ModBlocks.geiger });
		addRecipeAuto(new ItemStack(ModItems.sat_interface), new Object[] { "ISI", "PCP", "PAP", 'I', STEEL.ingot(), 'S', STAR.ingot(), 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED) });
		addRecipeAuto(new ItemStack(ModItems.sat_coord), new Object[] { "SII", "SCA", "SPP", 'I', STEEL.ingot(), 'S', STAR.ingot(), 'P', ModItems.plate_polymer, 'C', ModItems.sat_chip, 'A', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED) });
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer), new Object[] { "SCS", "MDM", "SCS", 'S', IRON.ingot(), 'D', MINGRADE.ingot(), 'M',ModItems.coil_advanced_alloy, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR) });
		addRecipeAuto(new ItemStack(ModBlocks.machine_transformer_dnt), new Object[] { "SDS", "MCM", "MCM", 'S', STAR.ingot(), 'D', DESH.ingot(), 'M', MAGTUNG.wireDense(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID) });
		addRecipeAuto(new ItemStack(ModBlocks.radiobox), new Object[] { "PLP", "PSP", "PLP", 'P', STEEL.plate(), 'S', ModItems.ring_starmetal, 'C', ModItems.fusion_core, 'L', getReflector() });
		addRecipeAuto(new ItemStack(ModBlocks.radiorec), new Object[] { "  W", "PCP", "PIP", 'W', CU.wireFine(), 'P', STEEL.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'I', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModItems.jackt), new Object[] { "S S", "LIL", "LIL", 'S', STEEL.plate(), 'L', Items.leather, 'I', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(ModItems.jackt2), new Object[] { "S S", "LIL", "III", 'S', STEEL.plate(), 'L', Items.leather, 'I', ANY_RUBBER.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.vent_chlorine), new Object[] { "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.iron_bars, 'C', ModItems.pellet_gas, 'D', Blocks.dispenser });
		addRecipeAuto(new ItemStack(ModBlocks.vent_chlorine_seal), new Object[] { "ISI", "SCS", "ISI", 'I', BIGMT.ingot(), 'S', STAR.ingot(), 'C', ModItems.chlorine_pinwheel });
		addRecipeAuto(new ItemStack(ModBlocks.vent_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.iron_bars, 'C', ModItems.grenade_cloud, 'D', Blocks.dispenser });
		addRecipeAuto(new ItemStack(ModBlocks.vent_pink_cloud), new Object[] { "IGI", "ICI", "IDI", 'I', IRON.plate(), 'G', Blocks.iron_bars, 'C', ModItems.grenade_pink_cloud, 'D', Blocks.dispenser });
		addRecipeAuto(new ItemStack(ModBlocks.spikes, 4), new Object[] { "BBB", "BBB", "TTT", 'B', STEEL.bolt(), 'T', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModItems.custom_fall, 1), new Object[] { "IIP", "CHW", "IIP", 'I', ANY_RUBBER.ingot(), 'P', BIGMT.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'H', STEEL.shell(), 'W', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModBlocks.machine_controller, 1), new Object[] { "TDT", "DCD", "TDT", 'T', ANY_RESISTANTALLOY.ingot(), 'D', ModItems.crt_display, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED) });
		addRecipeAuto(new ItemStack(ModItems.containment_box, 1), new Object[] { "LUL", "UCU", "LUL", 'L', PB.plate(), 'U', U238.billet(), 'C', ModBlocks.crate_steel });
		addRecipeAuto(new ItemStack(ModItems.casing_bag, 1), new Object[] { " L ", "LGL", " L ", 'L', Items.leather, 'G', GUNMETAL.plate() });
		addRecipeAuto(new ItemStack(ModItems.casing_bag, 1), new Object[] { " L ", "LGL", " L ", 'L', ANY_RUBBER.ingot(), 'G', GUNMETAL.plate() });

		addRecipeAuto(new ItemStack(ModBlocks.absorber, 1), new Object[] { "ICI", "CPC", "ICI", 'I', CU.ingot(), 'C', COAL.dust(), 'P', PB.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.absorber_red, 1), new Object[] { "ICI", "CPC", "ICI", 'I', TI.ingot(), 'C', COAL.dust(), 'P', ModBlocks.absorber });
		addRecipeAuto(new ItemStack(ModBlocks.absorber_green, 1), new Object[] { "ICI", "CPC", "ICI", 'I', ANY_PLASTIC.ingot(), 'C', ModItems.powder_desh_mix, 'P', ModBlocks.absorber_red });
		addRecipeAuto(new ItemStack(ModBlocks.absorber_pink, 1), new Object[] { "ICI", "CPC", "ICI", 'I', BIGMT.ingot(), 'C', ModItems.powder_nitan_mix, 'P', ModBlocks.absorber_green });
		addRecipeAuto(new ItemStack(ModBlocks.decon, 1), new Object[] { "BGB", "SAS", "BSB", 'B', BE.ingot(), 'G', Blocks.iron_bars, 'S', STEEL.ingot(), 'A', ModBlocks.absorber });
		addRecipeAuto(new ItemStack(ModBlocks.machine_minirtg, 1), new Object[] { "LLL", "PPP", "TRT", 'L', PB.plate(), 'P', PU238.billet(), 'T', ModItems.thermo_element, 'R', ModItems.rtg_unit });
		addRecipeAuto(new ItemStack(ModBlocks.machine_powerrtg, 1), new Object[] { "SRS", "PTP", "SRS", 'S', STAR.ingot(), 'R', ModItems.rtg_unit, 'P', PO210.billet(), 'T', TS.dust() });

		addRecipeAuto(new ItemStack(ModBlocks.pink_planks, 4), new Object[] { "W", 'W', ModBlocks.pink_log });
		addRecipeAuto(new ItemStack(ModBlocks.pink_slab, 6), new Object[] { "WWW", 'W', ModBlocks.pink_planks });
		addRecipeAuto(new ItemStack(ModBlocks.pink_stairs, 6), new Object[] { "W  ", "WW ", "WWW", 'W', ModBlocks.pink_planks });

		addRecipeAuto(new ItemStack(ModItems.door_metal, 1), new Object[] { "II", "SS", "II", 'I', IRON.plate(), 'S', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModItems.door_office, 1), new Object[] { "II", "SS", "II", 'I', KEY_PLANKS, 'S', IRON.plate() });
		addRecipeAuto(new ItemStack(ModItems.door_bunker, 1), new Object[] { "II", "SS", "II", 'I', STEEL.plate(), 'S', PB.plate() });

		addShapelessAuto(new ItemStack(Items.paper, 1), new Object[] { new ItemStack(ModItems.assembly_template, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(Items.paper, 1), new Object[] { new ItemStack(ModItems.chemistry_template, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(Items.paper, 1), new Object[] { new ItemStack(ModItems.crucible_template, 1, OreDictionary.WILDCARD_VALUE) });
		addShapelessAuto(new ItemStack(Items.slime_ball, 16), new Object[] { new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15), new ItemStack(Items.dye, 1, 15), Fluids.SULFURIC_ACID.getDict(1000) });

		for(int i = 1; i < Fluids.getAll().length; ++i) {
    		addShapelessAuto(new ItemStack(ModItems.fluid_duct, 1, i), new Object[] { new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModItems.fluid_identifier, 1, i) });

    		addShapelessAuto(new ItemStack(ModItems.fluid_duct, 8, i), new Object[] { new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModBlocks.fluid_duct_neo, 1),
    				new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModBlocks.fluid_duct_neo, 1),
    				new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModBlocks.fluid_duct_neo, 1), new ItemStack(ModItems.fluid_identifier, 1, i) });

    		addShapelessAuto(new ItemStack(ModItems.fluid_duct, 1, i), new Object[] { new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_identifier, 1, i) });

    		addShapelessAuto(new ItemStack(ModItems.fluid_duct, 8, i), new Object[] { new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE),
    				new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE),
    				new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ModItems.fluid_identifier, 1, i) });
		}

		addShapelessAuto(new ItemStack(ModBlocks.fluid_duct_neo, 1), new Object[] { new ItemStack(ModItems.fluid_duct, 1, OreDictionary.WILDCARD_VALUE) });

		addRecipeAuto(new ItemStack(Blocks.torch, 3), new Object[] { "L", "S", 'L', LIGNITE.gem(), 'S', KEY_STICK });
		addRecipeAuto(new ItemStack(Blocks.torch, 8), new Object[] { "L", "S", 'L', ANY_COKE.gem(), 'S', KEY_STICK });

		addRecipeAuto(new ItemStack(ModBlocks.machine_missile_assembly, 1), new Object[] { "PWP", "SSS", "CCC", 'P', ModItems.pedestal_steel, 'W', ModItems.wrench, 'S', STEEL.plate(), 'C', ModBlocks.steel_scaffold });
		addRecipeAuto(new ItemStack(ModBlocks.struct_launcher, 8), new Object[] { "PPP", "SDS", "CCC", 'P', STEEL.plate(), 'S', ModBlocks.steel_scaffold, 'D', STEEL.pipe(), 'C', ANY_CONCRETE.any() });
		addRecipeAuto(new ItemStack(ModBlocks.struct_scaffold, 8), new Object[] { "SSS", "DCD", "SSS", 'S', ModBlocks.steel_scaffold, 'D', new ItemStack(ModBlocks.fluid_duct_neo, 1, OreDictionary.WILDCARD_VALUE), 'C', ModBlocks.red_cable });

		addRecipeAuto(new ItemStack(ModItems.seg_10, 1), new Object[] { "P", "S", "B", 'P', AL.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(ModItems.seg_15, 1), new Object[] { "PP", "SS", "BB", 'P', TI.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });
		addRecipeAuto(new ItemStack(ModItems.seg_20, 1), new Object[] { "PGP", "SSS", "BBB", 'P', STEEL.plate(), 'G', GOLD.plate(), 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.steel_beam });

		addRecipeAuto(new ItemStack(ModBlocks.obj_tester, 1), new Object[] { "P", "I", "S", 'P', ModItems.polaroid, 'I', ModItems.flame_pony, 'S', STEEL.plate() });

		addRecipeAuto(new ItemStack(ModBlocks.fence_metal, 6), new Object[] { "BIB", "BIB", 'B', Blocks.iron_bars, 'I', Items.iron_ingot });
		addShapelessAuto(new ItemStack(ModBlocks.fence_metal, 1, 1), new Object[] { new ItemStack(ModBlocks.fence_metal, 1, 0) });
		addShapelessAuto(new ItemStack(ModBlocks.fence_metal, 1, 0), new Object[] { new ItemStack(ModBlocks.fence_metal, 1, 1) });

		addShapelessAuto(new ItemStack(ModBlocks.waste_trinitite), new Object[] { new ItemStack(Blocks.sand, 1, 0), ModItems.trinitite });
		addShapelessAuto(new ItemStack(ModBlocks.waste_trinitite_red), new Object[] { new ItemStack(Blocks.sand, 1, 1), ModItems.trinitite });
		addShapelessAuto(new ItemStack(ModBlocks.sand_uranium, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", U.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_polonium, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", PO210.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_boron, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", B.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_lead, 8), new Object[] { "sand", "sand", "sand", "sand", "sand", "sand", "sand", "sand", PB.dust() });
		addShapelessAuto(new ItemStack(ModBlocks.sand_quartz, 1), new Object[] { "sand", "sand", NETHERQUARTZ.dust(), NETHERQUARTZ.dust() });

		addRecipeAuto(new ItemStack(ModItems.rune_blank, 1), new Object[] { "PSP", "SDS", "PSP", 'P', ModItems.powder_magic, 'S', STAR.ingot(), 'D', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID) });
		addShapelessAuto(new ItemStack(ModItems.rune_isa, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_counter_resonant });
		addShapelessAuto(new ItemStack(ModItems.rune_dagaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity });
		addShapelessAuto(new ItemStack(ModItems.rune_hagalaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_super_heated });
		addShapelessAuto(new ItemStack(ModItems.rune_jera, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.singularity_spark });
		addShapelessAuto(new ItemStack(ModItems.rune_thurisaz, 1), new Object[] { ModItems.rune_blank, ModItems.powder_spark_mix, ModItems.black_hole });
		addRecipeAuto(new ItemStack(ModItems.ams_lens, 1), new Object[] { "PDP", "GDG", "PDP", 'P', ModItems.plate_dineutronium, 'G', ModBlocks.reinforced_glass, 'D', Blocks.diamond_block });
		addRecipeAuto(new ItemStack(ModItems.ams_catalyst_blank, 1), new Object[] { "TET", "ETE", "TET", 'T', TS.dust(), 'E', EUPH.ingot()});
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_lithium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_jera, ModItems.rune_jera, LI.dust(), LI.dust(), LI.dust(), LI.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_beryllium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_jera, BE.dust(), BE.dust(), BE.dust(), BE.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_copper, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_jera, CU.dust(), CU.dust(), CU.dust(), CU.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_cobalt, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, CO.dust(), CO.dust(), CO.dust(), CO.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_tungsten, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_jera, W.dust(), W.dust(), W.dust(), W.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_aluminium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_jera, ModItems.rune_thurisaz, AL.dust(), AL.dust(), AL.dust(), AL.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_iron, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_thurisaz, IRON.dust(), IRON.dust(), IRON.dust(), IRON.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_strontium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_jera, ModItems.rune_thurisaz, ST.dust(), ST.dust(), ST.dust(), ST.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_niobium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_thurisaz, NB.dust(), NB.dust(), NB.dust(), NB.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_cerium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_jera, ModItems.rune_thurisaz, CE.dust(), CE.dust(), CE.dust(), CE.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_caesium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_isa, ModItems.rune_thurisaz, ModItems.rune_thurisaz, CS.dust(), CS.dust(), CS.dust(), CS.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_thorium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_isa, ModItems.rune_dagaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, TH232.dust(), TH232.dust(), TH232.dust(), TH232.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_euphemium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_dagaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, EUPH.dust(), EUPH.dust(), EUPH.dust(), EUPH.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_schrabidium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_dagaz, ModItems.rune_hagalaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, SA326.dust(), SA326.dust(), SA326.dust(), SA326.dust() });
		addShapelessAuto(new ItemStack(ModItems.ams_catalyst_dineutronium, 1), new Object[] { ModItems.ams_catalyst_blank, ModItems.rune_hagalaz, ModItems.rune_hagalaz, ModItems.rune_thurisaz, ModItems.rune_thurisaz, DNT.dust(), DNT.dust(), DNT.dust(), DNT.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_core, 1), new Object[] { "DLD", "LML", "DLD", 'D', ModItems.ingot_bismuth, 'L', DNT.block(), 'M', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID) });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_emitter, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.plateWelded(), 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModItems.crystal_xen, 'L', ModItems.sat_head_laser });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_receiver, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.plateWelded(), 'D', ModItems.plate_desh, 'T', ModBlocks.machine_transformer_dnt, 'X', ModBlocks.block_dineutronium, 'L', STEEL.shell() });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_injector, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.plateWelded(), 'D', CMB.plate(), 'T', ModBlocks.machine_fluidtank, 'X', ModItems.motor, 'L', ModItems.pipes_steel });
		addRecipeAuto(new ItemStack(ModBlocks.dfc_stabilizer, 1), new Object[] { "SDS", "TXL", "SDS", 'S', OSMIRIDIUM.plateWelded(), 'D', ModItems.plate_desh, 'T', ModItems.singularity_spark, 'X', ModBlocks.fusion_conductor, 'L', ModItems.crystal_xen });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_plastic, 1), new Object[] { "IPI", "I I", "IPI", 'I', ModItems.plate_polymer, 'P', AL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_iron, 1), new Object[] { "IPI", "I I", "IPI", 'I', IRON.plate(), 'P', IRON.ingot() });
		addShapelessAuto(new ItemStack(ModBlocks.barrel_iron, 1), new Object[] { ModBlocks.barrel_corroded, ANY_TAR.any() });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_steel, 1), new Object[] { "IPI", "ITI", "IPI", 'I', STEEL.plate(), 'P', STEEL.ingot(), 'T', ANY_TAR.any() });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_tcalloy, 1), new Object[] { "IPI", "I I", "IPI", 'I', "ingotTcAlloy", 'P', TI.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.barrel_antimatter, 1), new Object[] { "IPI", "IBI", "IPI", 'I', BIGMT.plate(), 'P', ModItems.coil_advanced_torus, 'B', ModItems.battery_sc_technetium });
		addRecipeAuto(new ItemStack(ModBlocks.tesla, 1), new Object[] { "CCC", "PIP", "WTW", 'C', ModItems.coil_copper, 'I', IRON.ingot(), 'P', ANY_PLASTIC.ingot(), 'T', ModBlocks.machine_transformer, 'W', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModBlocks.struct_plasma_core, 1), new Object[] { "CBC", "BHB", "CBC", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'B', ModBlocks.machine_lithium_battery, 'H', ModBlocks.fusion_heater });
		addRecipeAuto(new ItemStack(ModBlocks.struct_watz_core, 1), new Object[] { "CBC", "BHB", "CBC", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'B', ANY_RESISTANTALLOY.plateCast(), 'H', ModBlocks.watz_cooler });
		addShapelessAuto(new ItemStack(ModBlocks.fusion_heater), new Object[] { ModBlocks.fusion_hatch });
		addShapelessAuto(new ItemStack(ModItems.energy_core), new Object[] { ModItems.fusion_core, ModItems.fuse });
		addRecipeAuto(new ItemStack(ModItems.catalytic_converter, 1), new Object[] { "PCP", "PBP", "PCP", 'P', ANY_HARDPLASTIC.ingot(), 'C', CO.dust(), 'B', BI.ingot() });

		addRecipeAuto(new ItemStack(ModItems.upgrade_nullifier, 1), new Object[] { "SPS", "PUP", "SPS", 'S', STEEL.plate(), 'P', ModItems.powder_fire, 'U', ModItems.upgrade_template });
		addRecipeAuto(new ItemStack(ModItems.upgrade_smelter, 1), new Object[] { "PHP", "CUC", "DTD", 'P', CU.plate(), 'H', Blocks.hopper, 'C', ModItems.coil_tungsten, 'U', ModItems.upgrade_template, 'D', ModItems.coil_copper, 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(ModItems.upgrade_shredder, 1), new Object[] { "PHP", "CUC", "DTD", 'P', ModItems.motor, 'H', Blocks.hopper, 'C', ModItems.blades_advanced_alloy, 'U', ModItems.upgrade_smelter, 'D', TI.plate(), 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(ModItems.upgrade_centrifuge, 1), new Object[] { "PHP", "PUP", "DTD", 'P', ModItems.centrifuge_element, 'H', Blocks.hopper, 'U', ModItems.upgrade_shredder, 'D', ANY_PLASTIC.ingot(), 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(ModItems.upgrade_crystallizer, 1), new Object[] { "PHP", "CUC", "DTD", 'P', new ItemStack(ModItems.fluid_barrel_full, 1, Fluids.PEROXIDE.getID()), 'H', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'C', ModBlocks.barrel_steel, 'U', ModItems.upgrade_centrifuge, 'D', ModItems.motor, 'T', ModBlocks.machine_transformer });
		addRecipeAuto(new ItemStack(ModItems.upgrade_screm, 1), new Object[] { "SUS", "SCS", "SUS", 'S', STEEL.plate(), 'U', ModItems.upgrade_template, 'C', ModItems.crystal_xen });
		addRecipeAuto(new ItemStack(ModItems.upgrade_gc_speed, 1), new Object[] {"GNG", "RUR", "GMG", 'R', RUBBER.ingot(), 'M', ModItems.motor, 'G', ModItems.coil_gold, 'N', NB.ingot(), 'U', ModItems.upgrade_template});

		addRecipeAuto(new ItemStack(ModItems.upgrade_stack, 1, 0), new Object[] { " C ", "PUP", " C ", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'U', ModItems.upgrade_template });
		addRecipeAuto(new ItemStack(ModItems.upgrade_stack, 1, 1), new Object[] { " C ", "PUP", " C ", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CAPACITOR), 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_HYDRAULIC), 'U', new ItemStack(ModItems.upgrade_stack, 1, 0) });
		addRecipeAuto(new ItemStack(ModItems.upgrade_stack, 1, 2), new Object[] { " C ", "PUP", " C ", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_ELECTRIC), 'U', new ItemStack(ModItems.upgrade_stack, 1, 1) });
		addRecipeAuto(new ItemStack(ModItems.upgrade_ejector, 1, 0), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_copper, 'P', ModItems.motor, 'U', ModItems.upgrade_template });
		addRecipeAuto(new ItemStack(ModItems.upgrade_ejector, 1, 1), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_gold, 'P', ModItems.motor, 'U', new ItemStack(ModItems.upgrade_ejector, 1, 0) });
		addRecipeAuto(new ItemStack(ModItems.upgrade_ejector, 1, 2), new Object[] { " C ", "PUP", " C ", 'C', ModItems.plate_saturnite, 'P', ModItems.motor, 'U', new ItemStack(ModItems.upgrade_ejector, 1, 1) });

		addRecipeAuto(new ItemStack(ModItems.mech_key, 1), new Object[] { "MCM", "MKM", "MMM", 'M', ModItems.ingot_meteorite_forged, 'C', ModItems.coin_maskman, 'K', ModItems.key });
		addRecipeAuto(new ItemStack(ModItems.spawn_ufo, 1), new Object[] { "MMM", "DCD", "MMM", 'M', ModItems.ingot_meteorite, 'D', DNT.ingot(), 'C', ModItems.coin_worm });

		addShapelessAuto(new ItemStack(ModItems.wire_dense, 4, Mats.MAT_GOLD.id), new Object[] { ModBlocks.hadron_coil_gold });
		addShapelessAuto(new ItemStack(ModItems.wire_dense, 4, Mats.MAT_NEODYMIUM.id), new Object[] { ModBlocks.hadron_coil_neodymium });
		addShapelessAuto(new ItemStack(ModItems.wire_dense, 4, Mats.MAT_MAGTUNG.id), new Object[] { ModBlocks.hadron_coil_magtung });
		addShapelessAuto(new ItemStack(ModItems.wire_dense, 2, Mats.MAT_SCHRABIDIUM.id), new Object[] { ModBlocks.hadron_coil_schrabidium });
		addShapelessAuto(new ItemStack(ModItems.wire_dense, 2, Mats.MAT_SCHRABIDATE.id), new Object[] { ModBlocks.hadron_coil_schrabidate });
		addShapelessAuto(new ItemStack(ModItems.wire_dense, 2, Mats.MAT_STAR.id), new Object[] { ModBlocks.hadron_coil_starmetal });
		addShapelessAuto(new ItemStack(ModItems.powder_chlorophyte, 2), new Object[] { ModBlocks.hadron_coil_chlorophyte });
		addShapelessAuto(new ItemStack(ModItems.wire_dense, 1, Mats.MAT_DNT.id), new Object[] { ModBlocks.hadron_coil_mese });
		addShapelessAuto(new ItemStack(ModItems.plate_cast, 1, Mats.MAT_STEEL.id), new Object[] { ModBlocks.hadron_plating });
		addShapelessAuto(new ItemStack(ModItems.plate_cast, 1, Mats.MAT_STEEL.id), new Object[] { ModBlocks.hadron_plating_blue });
		addShapelessAuto(new ItemStack(ModItems.plate_cast, 1, Mats.MAT_STEEL.id), new Object[] { ModBlocks.hadron_plating_black });
		addShapelessAuto(new ItemStack(ModItems.plate_cast, 1, Mats.MAT_STEEL.id), new Object[] { ModBlocks.hadron_plating_yellow });
		addShapelessAuto(new ItemStack(ModItems.plate_cast, 1, Mats.MAT_STEEL.id), new Object[] { ModBlocks.hadron_plating_striped });
		addShapelessAuto(new ItemStack(ModItems.plate_cast, 1, Mats.MAT_STEEL.id), new Object[] { ModBlocks.hadron_plating_glass });
		addShapelessAuto(new ItemStack(ModItems.plate_cast, 1, Mats.MAT_STEEL.id), new Object[] { ModBlocks.hadron_plating_voltz });
		addShapelessAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), new Object[] { ModBlocks.hadron_analysis });
		addShapelessAuto(DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), new Object[] { ModBlocks.hadron_analysis_glass });

		addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_alloy, 1), new Object[] { "WW", "WW", 'W', ALLOY.wireDense() });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_gold, 1), new Object[] { "WG", "GW", 'W', ALLOY.wireDense(), 'G', GOLD.wireDense() });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_neodymium, 1), new Object[] { "WG", "GW", 'W', ND.wireDense(), 'G', GOLD.wireDense() });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_magtung, 1), new Object[] { "WW", "WW", 'W', MAGTUNG.wireDense() });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidium, 1), new Object[] { "WS", "SW", 'W', MAGTUNG.wireDense(), 'S', SA326.wireDense() });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_schrabidate, 1), new Object[] { "WS", "SW", 'W', SBD.wireDense(), 'S', SA326.wireDense() });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_starmetal, 1), new Object[] { "SW", "WS", 'W', SBD.wireDense(), 'S', STAR.wireDense() });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_coil_chlorophyte, 1), new Object[] { "TC", "CT", 'T', CU.wireDense(), 'C', ModItems.powder_chlorophyte });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_diode, 1), new Object[] { "CIC", "ISI", "CIC", 'C', ModBlocks.hadron_coil_alloy, 'I', STEEL.ingot(), 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED) });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_plating, 16), new Object[] { "CC", "CC", 'C', STEEL.plateCast()});
		//addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_blue, 1), new Object[] { ModBlocks.hadron_plating, KEY_BLUE });
		//addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_black, 1), new Object[] { ModBlocks.hadron_plating, KEY_BLACK });
		//addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_yellow, 1), new Object[] { ModBlocks.hadron_plating, KEY_YELLOW });
		//addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_striped, 1), new Object[] { ModBlocks.hadron_plating, KEY_BLACK, KEY_YELLOW });
		//addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_glass, 1), new Object[] { ModBlocks.hadron_plating, KEY_ANYGLASS });
		//addShapelessAuto(new ItemStack(ModBlocks.hadron_plating_voltz, 1), new Object[] { ModBlocks.hadron_plating, KEY_RED });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_power, 1), new Object[] { "SFS", "FTF", "SFS", 'S', BIGMT.ingot(), 'T', ModBlocks.machine_transformer, 'F', ModItems.fuse });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_power_10m, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power, 'F', ModItems.fuse });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_power_100m, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_10m, 'F', ModItems.fuse });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_power_1g, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_100m, 'F', ModItems.fuse });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_power_10g, 1), new Object[] { "HF", 'H', ModBlocks.hadron_power_1g, 'F', ModItems.fuse });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_analysis, 1), new Object[] { "IPI", "PCP", "IPI", 'I', TI.ingot(), 'P', getReflector(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED) });
		//addShapelessAuto(new ItemStack(ModBlocks.hadron_analysis_glass, 1), new Object[] { ModBlocks.hadron_analysis, KEY_ANYGLASS });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_access, 1), new Object[] { "IGI", "CRC", "IPI", 'I', ModItems.plate_polymer, 'G', KEY_ANYPANE, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'R', REDSTONE.block(), 'P', ModBlocks.hadron_plating_blue });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_cooler, 1, 0), new Object[] { "PCP", "CHC", "PCP", 'P', ANY_RESISTANTALLOY.plateCast(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'H', Fluids.HELIUM4.getDict(16_000) });
		//addRecipeAuto(new ItemStack(ModBlocks.hadron_cooler, 1, 1), new Object[] { "PCP", "CHC", "PCP", 'P', GOLD.plateCast(), 'C', ModItems.motor_bismuth, 'H', new ItemStack(ModBlocks.hadron_cooler, 1, 0) });

		addRecipeAuto(new ItemStack(ModBlocks.fireworks, 1), new Object[] { "PPP", "PPP", "WIW", 'P', Items.paper, 'W', KEY_PLANKS, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModItems.safety_fuse, 8), new Object[] { "SSS", "SGS", "SSS", 'S', Items.string, 'G', Items.gunpowder });

		addRecipeAuto(new ItemStack(ModItems.rbmk_lid, 4), new Object[] { "PPP", "CCC", "PPP", 'P', STEEL.plate(), 'C', ModBlocks.concrete_asbestos });
		addRecipeAuto(new ItemStack(ModItems.rbmk_lid_glass, 4), new Object[] { "LLL", "BBB", "P P", 'P', STEEL.plate(), 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron });
		addRecipeAuto(new ItemStack(ModItems.rbmk_lid_glass, 4), new Object[] { "BBB", "LLL", "P P", 'P', STEEL.plate(), 'L', ModBlocks.glass_lead, 'B', ModBlocks.glass_boron });

		addRecipeAuto(new ItemStack(ModBlocks.rbmk_moderator, 1), new Object[] { " G ", "GRG", " G ", 'G', GRAPHITE.block(), 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_absorber, 1), new Object[] { "GGG", "GRG", "GGG", 'G', B.ingot(), 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_reflector, 1), new Object[] { "GGG", "GRG", "GGG", 'G', OreDictManager.getReflector(), 'R', ModBlocks.rbmk_blank });
		if(!GeneralConfig.enable528) {
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_control, 1), new Object[] { " B ", "GRG", " B ", 'G', GRAPHITE.ingot(), 'B', ModItems.motor, 'R', ModBlocks.rbmk_absorber });
		} else {
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_control, 1), new Object[] { "CBC", "GRG", "CBC", 'G', GRAPHITE.ingot(), 'B', ModItems.motor, 'R', ModBlocks.rbmk_absorber, 'C', CD.ingot() });
		}
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', GRAPHITE.block(), 'R', ModBlocks.rbmk_control, 'B', ModItems.nugget_bismuth });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_control_auto, 1), new Object[] { "C", "R", "D", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'R', ModBlocks.rbmk_control, 'D', ModItems.crt_display });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_reasim, 1), new Object[] { "ZCZ", "ZRZ", "ZCZ", 'C', STEEL.shell(), 'R', ModBlocks.rbmk_blank, 'Z', ZR.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_reasim_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', GRAPHITE.block(), 'R', ModBlocks.rbmk_rod_reasim, 'B', ANY_RESISTANTALLOY.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_outgasser, 1), new Object[] { "GHG", "GRG", "GTG", 'G', ModBlocks.steel_grate, 'H', Blocks.hopper, 'T', ModItems.tank_steel, 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_storage, 1), new Object[] { "C", "R", "C", 'C', ModBlocks.crate_steel, 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_loader, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.plate(), 'C', CU.ingot(), 'B', ModItems.tank_steel });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_steam_inlet, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', IRON.plate(), 'B', ModItems.tank_steel });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_steam_outlet, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', CU.plate(), 'B', ModItems.tank_steel });
		//addRecipeAuto(new ItemStack(ModBlocks.rbmk_heatex, 1), new Object[] { "SCS", "CBC", "SCS", 'S', STEEL.ingot(), 'C', CU.plate(), 'B', ModItems.pipes_steel });

		addRecipeAuto(new ItemStack(ModBlocks.pwr_fuel, 4), new Object[] { "LZL", "L L", "LZL", 'L', PB.plate528(), 'Z', ZR.plateWelded() });
		addRecipeAuto(new ItemStack(ModBlocks.pwr_control, 4), new Object[] { "SBS", "MBM", "SBS", 'S', STEEL.plate528(), 'B', B.ingot(), 'M', ModItems.motor });
		addRecipeAuto(new ItemStack(ModBlocks.pwr_channel, 4), new Object[] { "CPC", "BPB", "CPC", 'C', CU.ingot(), 'P', STEEL.pipe(), 'B', ANY_PLASTIC.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.pwr_heatex, 4), new Object[] { "CSC", "SMS", "CSC", 'C', CU.plateCast(), 'S', STEEL.plate528(), 'M', ModItems.motor });
		addRecipeAuto(new ItemStack(ModBlocks.pwr_heatsink, 4), new Object[] { "SCS", "CRC", "SCS", 'S', BIGMT.plateCast(), 'C', CU.plate(), 'R', RUBBER.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.pwr_reflector, 4), new Object[] { "RLR", "LSL", "RLR", 'R', OreDictManager.getReflector(), 'L', PB.plate528(), 'S', STEEL.plateCast() });
		addRecipeAuto(new ItemStack(ModBlocks.pwr_casing, 4), new Object[] { "LCL", "CSC", "LCL", 'L', PB.plate528(), 'C', ANY_CONCRETE.any(), 'S', STEEL.plateCast() });
		addRecipeAuto(new ItemStack(ModBlocks.pwr_controller, 1), new Object[] { "CPC", "PSP", "CPC", 'C', ModBlocks.pwr_casing, 'P', ANY_PLASTIC.ingot(), 'S', !GeneralConfig.enableExpensiveMode ? DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC) : STEEL.heavyComp() });
		addRecipeAuto(new ItemStack(ModBlocks.pwr_port, 1), new Object[] { "S", "C", "S", 'S', STEEL.plate(), 'C', ModBlocks.pwr_casing });
		addRecipeAuto(new ItemStack(ModBlocks.pwr_neutron_source, 1), new Object[] { "LRL", "ZRZ", "LRL", 'L', PB.plate528(), 'R', ModItems.billet_ra226be, 'Z', ZR.plateCast() });

		addRecipeAuto(new ItemStack(ModBlocks.deco_rbmk, 8), new Object[] { "R", 'R', ModBlocks.rbmk_blank });
		addRecipeAuto(new ItemStack(ModBlocks.deco_rbmk_smooth, 1), new Object[] { "R", 'R', ModBlocks.deco_rbmk });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_blank, 1), new Object[] { "RRR", "R R", "RRR", 'R', ModBlocks.deco_rbmk });
		addRecipeAuto(new ItemStack(ModBlocks.rbmk_blank, 1), new Object[] { "RRR", "R R", "RRR", 'R', ModBlocks.deco_rbmk_smooth });

		addRecipeAuto(new ItemStack(ModBlocks.ladder_sturdy, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', KEY_PLANKS });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_iron, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_gold, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', GOLD.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_aluminium, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', AL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_copper, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', CU.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_titanium, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', TI.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_lead, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', PB.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_cobalt, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', CO.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_steel, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', STEEL.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.ladder_tungsten, 8), new Object[] { "LLL", "L#L", "LLL", 'L', Blocks.ladder, '#', W.ingot() });

		addRecipeAuto(new ItemStack(ModBlocks.machine_storage_drum), new Object[] { "LLL", "L#L", "LLL", 'L', PB.plate(), '#', ModItems.tank_steel });

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe, 6), new Object[] { "PP", 'P', STEEL.pipe() });
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_rim });
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_framed });
		addShapelessAuto(new ItemStack(ModBlocks.deco_pipe, 1), new Object[] { ModBlocks.deco_pipe_quad });

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', STEEL.plate() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad, 4), new Object[] { "PP", "PP", 'P', ModBlocks.deco_pipe });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', Blocks.iron_bars });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', Blocks.iron_bars });

		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_green, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_green_rusted, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', IRON.dust() });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe, 'C', KEY_RED });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim, 'C', KEY_RED });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad, 'C', KEY_RED });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_red, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed, 'C', KEY_RED });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_green, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_rim_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_rim_green, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_quad_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_quad_green, 'C', KEY_GREEN });
		addRecipeAuto(new ItemStack(ModBlocks.deco_pipe_framed_marked, 8), new Object[] { "PPP", "PCP", "PPP", 'P', ModBlocks.deco_pipe_framed_green, 'C', KEY_GREEN });

		addRecipeAuto(new ItemStack(ModBlocks.deco_emitter), new Object[] { "IDI", "DRD", "IDI", 'I', IRON.ingot(), 'D', DIAMOND.gem(), 'R', REDSTONE.block() });

		addRecipeAuto(new ItemStack(Items.name_tag), new Object[] { "SB ", "BPB", " BP", 'S', Items.string, 'B', KEY_SLIME, 'P', Items.paper });
		addRecipeAuto(new ItemStack(Items.name_tag), new Object[] { "SB ", "BPB", " BP", 'S', Items.string, 'B', ANY_TAR.any(), 'P', Items.paper });
		addRecipeAuto(new ItemStack(Items.lead, 4), new Object[] { "RSR", 'R', DictFrame.fromOne(ModItems.plant_item, EnumPlantType.ROPE), 'S', KEY_SLIME });
		addRecipeAuto(new ItemStack(ModItems.rag, 4), new Object[] { "SW", "WS", 'S', Items.string, 'W', Blocks.wool });

		addShapelessAuto(new ItemStack(ModItems.solid_fuel, 3), new Object[] { Fluids.HEATINGOIL.getDict(16000), KEY_TOOL_CHEMISTRYSET });
		addShapelessAuto(new ItemStack(ModItems.canister_full, 2, Fluids.LUBRICANT.getID()), new Object[] { Fluids.HEATINGOIL.getDict(1000), Fluids.UNSATURATEDS.getDict(1000), ModItems.canister_empty, ModItems.canister_empty, KEY_TOOL_CHEMISTRYSET });

		addRecipeAuto(new ItemStack(ModBlocks.machine_condenser), new Object[] { "SIS", "ICI", "SIS", 'S', STEEL.ingot(), 'I', IRON.plate(), 'C', CU.plateCast() });

		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.TEST.ordinal()), new Object[] { Items.book, ModItems.canned_conserve.stackFromEnum(EnumFoodType.JIZZ) });
		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.RBMK.ordinal()), new Object[] { Items.book, Items.potato });
		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.HADRON.ordinal()), new Object[] { Items.book, ModItems.fuse });
		addShapelessAuto(new ItemStack(ModItems.book_guide, 1, BookType.STARTER.ordinal()), new Object[] { Items.book, Items.iron_ingot });

		addRecipeAuto(new ItemStack(ModBlocks.charger), new Object[] { "G", "S", "C", 'G', Items.glowstone_dust, 'S', STEEL.ingot(), 'C', ModItems.coil_copper });
		addRecipeAuto(new ItemStack(ModBlocks.charger, 16), new Object[] { "G", "S", "C", 'G', Blocks.glowstone, 'S', STEEL.block(), 'C', ModItems.coil_copper_torus });
		addRecipeAuto(new ItemStack(ModBlocks.refueler), new Object[] { "SS", "HC", "SS", 'S', TI.plate(), 'H', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_HYDRAULIC), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC) });
		addRecipeAuto(new ItemStack(ModBlocks.press_preheater), new Object[] { "CCC", "SLS", "TST", 'C', CU.plate(), 'S', Blocks.stone, 'L', Fluids.LAVA.getDict(1000), 'T', W.ingot() });
		addRecipeAuto(new ItemStack(ModItems.fluid_identifier_multi), new Object[] { "D", "C", "P", 'D', "dye", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG), 'P', IRON.plate() });

		addShapelessAuto(ItemBattery.getEmptyBattery(ModItems.anchor_remote), new Object[] { DIAMOND.gem(), ModItems.ducttape, DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC) });
		addRecipeAuto(new ItemStack(ModBlocks.teleanchor), new Object[] { "ODO", "EAE", "ODO", 'O', Blocks.obsidian, 'D', DIAMOND.gem(), 'E', ModItems.powder_magic, 'A', ModItems.gem_alexandrite });
		addRecipeAuto(new ItemStack(ModBlocks.field_disturber), new Object[] { "ICI", "CAC", "ICI", 'I', STAR.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID), 'A', ModItems.gem_alexandrite });

		addShapelessAuto(new ItemStack(ModItems.holotape_image, 1, EnumHoloImage.HOLO_RESTORED.ordinal()), new Object[] { new ItemStack(ModItems.holotape_image, 1, EnumHoloImage.HOLO_DIGAMMA.ordinal()), KEY_TOOL_SCREWDRIVER, ModItems.ducttape, ModItems.armor_polish });
		addShapelessAuto(new ItemStack(ModItems.holotape_damaged), new Object[] { DictFrame.fromOne(ModItems.holotape_image, EnumHoloImage.HOLO_RESTORED), ModItems.upgrade_muffler, ModItems.crt_display, ModItems.gem_alexandrite /* placeholder for amplifier */ });

		addRecipeAuto(DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC, 4), new Object[] { " I ", "CPC", " I ", 'I', IRON.ingot(), 'C', CU.ingot(), 'P', IRON.plate() });
		addRecipeAuto(DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_HYDRAULIC, 4), new Object[] { " I ", "CPC", " I ", 'I', STEEL.ingot(), 'C', TI.ingot(), 'P', Fluids.LUBRICANT.getDict(1000) });
		addRecipeAuto(DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_ELECTRIC, 4), new Object[] { " I ", "CPC", " I ", 'I', ANY_RESISTANTALLOY.ingot(), 'C', ANY_PLASTIC.ingot(), 'P', ModItems.motor });

		Object[] craneCasing = new Object[] {
				Blocks.stonebrick, 1,
				IRON.ingot(), 2,
				STEEL.ingot(), 4
		};

		for(int i = 0; i < craneCasing.length / 2; i++) {
			Object casing = craneCasing[i * 2];
			int amount = (int) craneCasing[i * 2 + 1];
			addRecipeAuto(new ItemStack(ModBlocks.crane_inserter, amount), new Object[] { "CCC", "C C", "CBC", 'C', casing, 'B', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR) });
			addRecipeAuto(new ItemStack(ModBlocks.crane_extractor, amount), new Object[] { "CCC", "CPC", "CBC", 'C', casing, 'B', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR), 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC) });
			addRecipeAuto(new ItemStack(ModBlocks.crane_grabber, amount), new Object[] { "C C", "P P", "CBC", 'C', casing, 'B', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR), 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC) });
		}

		addRecipeAuto(new ItemStack(ModBlocks.crane_boxer), new Object[] { "WWW", "WPW", "CCC", 'W', KEY_PLANKS, 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'C', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR) });
		addRecipeAuto(new ItemStack(ModBlocks.crane_unboxer), new Object[] { "WWW", "WPW", "CCC", 'W', KEY_STICK, 'P', Items.shears, 'C', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR) });
		addRecipeAuto(new ItemStack(ModBlocks.crane_router), new Object[] { "PIP", "ICI", "PIP", 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'I', ModItems.plate_polymer, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC) });
		addRecipeAuto(new ItemStack(ModBlocks.crane_splitter), new Object[] { "III", "PCP", "III", 'P', DictFrame.fromOne(ModItems.part_generic, EnumPartType.PISTON_PNEUMATIC), 'I', STEEL.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE) });
		addRecipeAuto(new ItemStack(ModBlocks.crane_partitioner), new Object[] { " M ", "BCB", 'M', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), 'B', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR), 'C', ModBlocks.crate_steel });

		addRecipeAuto(new ItemStack(ModBlocks.machine_conveyor_press), new Object[] { "CPC", "CBC", "CCC", 'C', CU.plate(), 'P', ModBlocks.machine_epress, 'B', DictFrame.fromOne(ModItems.conveyor_wand, ConveyorType.REGULAR) });
		addRecipeAuto(new ItemStack(ModBlocks.radar_screen), new Object[] { "PCP", "SRS", "PCP", 'P', ANY_PLASTIC.ingot(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'S', STEEL.plate(), 'R', ModItems.crt_display });
		addRecipeAuto(new ItemStack(ModItems.radar_linker), new Object[] { "S", "C", "P", 'S', ModItems.crt_display, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'P', STEEL.plate() });

		addRecipeAuto(new ItemStack(ModItems.drone, 2, EnumDroneType.PATROL.ordinal()), new Object[] { " P ", "HCH", " B ", 'P', ANY_PLASTIC.ingot(), 'H', STEEL.pipe(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE), 'B', STEEL.shell() });
		addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_CHUNKLOADING.ordinal()), new Object[] { "E", "D", 'E', Items.ender_pearl, 'D', new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL.ordinal()) });
		addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS.ordinal()), new Object[] { " P ", "KDK", " P ", 'P', TI.plateWelded(), 'K', Fluids.KEROSENE.getDict(1_000), 'D', new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL.ordinal()) });
		addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS_CHUNKLOADING.ordinal()), new Object[] { "E", "D", 'E', Items.ender_pearl, 'D', new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS.ordinal()) });
		addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS_CHUNKLOADING.ordinal()), new Object[] { " P ", "KDK", " P ", 'P', TI.plateWelded(), 'K', Fluids.KEROSENE.getDict(1_000), 'D', new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_CHUNKLOADING.ordinal()) });
		addShapelessAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL.ordinal()), new Object[] { new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_CHUNKLOADING.ordinal()) });
		addShapelessAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS.ordinal()), new Object[] { new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL_EXPRESS_CHUNKLOADING.ordinal()) });
		addRecipeAuto(new ItemStack(ModItems.drone, 1, EnumDroneType.REQUEST.ordinal()), new Object[] { "E", "D", 'E', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.CHIP), 'D', new ItemStack(ModItems.drone, 1, EnumDroneType.PATROL.ordinal()) });

		addRecipeAuto(new ItemStack(ModItems.drone_linker), new Object[] { "T", "C", 'T', ModBlocks.drone_waypoint, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC) });
		addRecipeAuto(new ItemStack(ModBlocks.drone_waypoint, 4), new Object[] { "G", "T", "C", 'G', KEY_GREEN, 'T', Blocks.redstone_torch, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC) });
		addRecipeAuto(new ItemStack(ModBlocks.drone_crate), new Object[] { "T", "C", 'T', ModBlocks.drone_waypoint, 'C', ModBlocks.crate_steel });
		addRecipeAuto(new ItemStack(ModBlocks.drone_waypoint_request, 4), new Object[] { "G", "T", "C", 'G', KEY_BLUE, 'T', Blocks.redstone_torch, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC) });
		addRecipeAuto(new ItemStack(ModBlocks.drone_crate_requester), new Object[] { "T", "C", "B", 'T', ModBlocks.drone_waypoint_request, 'C', ModBlocks.crate_steel, 'B', KEY_YELLOW });
		addRecipeAuto(new ItemStack(ModBlocks.drone_crate_provider), new Object[] { "T", "C", "B", 'T', ModBlocks.drone_waypoint_request, 'C', ModBlocks.crate_steel, 'B', KEY_ORANGE });
		addRecipeAuto(new ItemStack(ModBlocks.drone_dock), new Object[] { "T", "C", "B", 'T', ModBlocks.drone_waypoint_request, 'C', ModBlocks.crate_steel, 'B', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED) });

		addRecipeAuto(new ItemStack(ModItems.ball_resin), new Object[] { "DD", "DD", 'D', Blocks.yellow_flower });

		addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER1), new Object[] { ModItems.ingot_chainsteel, ASBESTOS.ingot(), ModItems.gem_alexandrite });
		addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER1, 3), new Object[] { DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2) });
		addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2), new Object[] { ModItems.ingot_chainsteel, ModItems.ingot_bismuth, ModItems.gem_alexandrite, ModItems.gem_alexandrite });
		addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER2, 3), new Object[] { DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER3) });
		addShapelessAuto(DictFrame.fromOne(ModItems.parts_legendary, EnumLegendaryType.TIER3), new Object[] { ModItems.ingot_chainsteel, ModItems.ingot_smore, ModItems.gem_alexandrite, ModItems.gem_alexandrite, ModItems.gem_alexandrite });

		addRecipeAuto(new ItemStack(ModItems.gear_large, 1, 0), new Object[] { "III", "ICI", "III", 'I', IRON.plate(), 'C', CU.ingot()});
		addRecipeAuto(new ItemStack(ModItems.gear_large, 1, 1), new Object[] { "III", "ICI", "III", 'I', STEEL.plate(), 'C', TI.ingot()});
		addRecipeAuto(new ItemStack(ModItems.sawblade), new Object[] { "III", "ICI", "III", 'I', STEEL.plate(), 'C', IRON.ingot()});

		addRecipeAuto(new ItemStack(ModBlocks.foundry_basin), new Object[] { "B B", "B B", "BSB", 'B', ModItems.ingot_firebrick, 'S', Blocks.stone_slab });
		addRecipeAuto(new ItemStack(ModBlocks.foundry_mold), new Object[] { "B B", "BSB", 'B', ModItems.ingot_firebrick, 'S', Blocks.stone_slab });
		addRecipeAuto(new ItemStack(ModBlocks.foundry_channel, 4), new Object[] { "B B", " S ", 'B', ModItems.ingot_firebrick, 'S', Blocks.stone_slab });
		addRecipeAuto(new ItemStack(ModBlocks.foundry_tank), new Object[] { "B B", "I I", "BSB", 'B', ModItems.ingot_firebrick, 'I', STEEL.ingot(), 'S', Blocks.stone_slab });
		addShapelessAuto(new ItemStack(ModBlocks.foundry_outlet), new Object[] { ModBlocks.foundry_channel, STEEL.plate() });
		addShapelessAuto(new ItemStack(ModBlocks.foundry_slagtap), new Object[] { ModBlocks.foundry_channel, Blocks.stonebrick });
		addRecipeAuto(new ItemStack(ModItems.mold_base), new Object[] { " B ", "BIB", " B ", 'B', ModItems.ingot_firebrick, 'I', IRON.ingot() });
		addRecipeAuto(new ItemStack(ModBlocks.brick_fire), new Object[] { "BB", "BB", 'B', ModItems.ingot_firebrick });
		addShapelessAuto(new ItemStack(ModItems.ingot_firebrick, 4), new Object[] { ModBlocks.brick_fire });

		addRecipeAuto(new ItemStack(ModBlocks.machine_drain), new Object[] { "PPP", "T  ", "PPP", 'P', STEEL.plateCast(), 'T', ModItems.tank_steel });

		addRecipeAuto(new ItemStack(ModBlocks.filing_cabinet, 1, DecoCabinetEnum.STEEL.ordinal()), new Object[] { " P ", "PIP", " P ", 'P', STEEL.plate(), 'I', ModItems.plate_polymer });

		addRecipeAuto(new ItemStack(ModBlocks.vinyl_tile, 4), new Object[] { " I ", "IBI", " I ", 'I', ModItems.plate_polymer, 'B', ModBlocks.brick_light });
		addRecipeAuto(new ItemStack(ModBlocks.vinyl_tile, 4, 1), new Object[] { "BB", "BB", 'B', new ItemStack(ModBlocks.vinyl_tile, 1, 0) });
		addShapelessAuto(new ItemStack(ModBlocks.vinyl_tile), new Object[] { new ItemStack(ModBlocks.vinyl_tile, 1, 1) });

		addShapelessAuto(new ItemStack(ModItems.upgrade_5g), new Object[] { ModItems.upgrade_template, ModItems.gem_alexandrite });

		addShapelessAuto(new ItemStack(ModItems.bdcl), new Object[] { ANY_TAR.any(), Fluids.WATER.getDict(1_000), KEY_WHITE });

		addShapelessAuto(new ItemStack(ModItems.book_of_), new Object[] { DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE1), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE2), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE3), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE4), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE5), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE6), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE7), DictFrame.fromOne(ModItems.page_of_, EnumPages.PAGE8), ModItems.egg_balefire });

		if(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSimpleCrafting) {
			addShapelessAuto(new ItemStack(ModItems.cordite, 3), new Object[] { ModItems.ballistite, Items.gunpowder, new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE) });
			addShapelessAuto(new ItemStack(ModItems.ingot_semtex, 3), new Object[] { Items.slime_ball, Blocks.tnt, KNO.dust() });
			addShapelessAuto(new ItemStack(ModItems.canister_full, 1, Fluids.DIESEL.getID()), new Object[] { new ItemStack(ModItems.canister_full, 1, Fluids.OIL.getID()), REDSTONE.dust(), ModItems.canister_empty });

			addShapelessAuto(new ItemStack(ModBlocks.ore_uranium, 1), new Object[] { ModBlocks.ore_uranium_scorched, Items.water_bucket });
			addRecipeAuto(new ItemStack(ModBlocks.ore_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_uranium_scorched, 'B', Items.water_bucket });
			addShapelessAuto(new ItemStack(ModBlocks.ore_nether_uranium, 1), new Object[] { ModBlocks.ore_nether_uranium_scorched, Items.water_bucket });
			addRecipeAuto(new ItemStack(ModBlocks.ore_nether_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_nether_uranium_scorched, 'B', Items.water_bucket });
			addShapelessAuto(new ItemStack(ModBlocks.ore_gneiss_uranium, 1), new Object[] { ModBlocks.ore_gneiss_uranium_scorched, Items.water_bucket });
			addRecipeAuto(new ItemStack(ModBlocks.ore_gneiss_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_gneiss_uranium_scorched, 'B', Items.water_bucket });
			addShapelessAuto(new ItemStack(ModBlocks.ore_uranium, 1), new Object[] { ModBlocks.ore_sellafield_uranium_scorched, Items.water_bucket });
			addRecipeAuto(new ItemStack(ModBlocks.ore_uranium, 8), new Object[] { "OOO", "OBO", "OOO", 'O', ModBlocks.ore_sellafield_uranium_scorched, 'B', Items.water_bucket });

			addRecipeAuto(new ItemStack(ModItems.plate_iron, 4), new Object[] { "##", "##", '#', IRON.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_gold, 4), new Object[] { "##", "##", '#', GOLD.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_aluminium, 4), new Object[] { "##", "##", '#', AL.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_titanium, 4), new Object[] { "##", "##", '#', TI.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_copper, 4), new Object[] { "##", "##", '#', CU.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_lead, 4), new Object[] { "##", "##", '#', PB.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_steel, 4), new Object[] { "##", "##", '#', STEEL.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_schrabidium, 4), new Object[] { "##", "##", '#', SA326.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_advanced_alloy, 4), new Object[] { "##", "##", '#', ALLOY.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_saturnite, 4), new Object[] { "##", "##", '#', BIGMT.ingot() });
			addRecipeAuto(new ItemStack(ModItems.plate_combine_steel, 4), new Object[] { "##", "##", '#', CMB.ingot() });
			addRecipeAuto(new ItemStack(ModItems.neutron_reflector, 4), new Object[] { "##", "##", '#', W.ingot() });

			for(NTMMaterial mat : Mats.orderedList) {
				if(mat.autogen.contains(MaterialShapes.WIRE)) for(String name : mat.names) addRecipeAuto(new ItemStack(ModItems.wire_fine, 24, mat.id), new Object[] { "###", '#', MaterialShapes.INGOT.prefixes[0] + name });
			}

			addRecipeAuto(new ItemStack(ModItems.book_of_), new Object[] { "BGB", "GAG", "BGB", 'B', ModItems.egg_balefire_shard, 'G', GOLD.ingot(), 'A', Items.book });
		}

		for(NTMMaterial mat : Mats.orderedList) {
			if(mat.autogen.contains(MaterialShapes.BOLT)) for(String name : mat.names) addRecipeAuto(new ItemStack(ModItems.bolt, 16, mat.id), new Object[] { "#", "#", '#', MaterialShapes.INGOT.prefixes[0] + name });
		}

		if(!GeneralConfig.enable528) {
			addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core, 1), new Object[] { "SCS", "SIS", "BEB", 'S', ModBlocks.steel_scaffold, 'I', Blocks.iron_bars, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
			addRecipeAuto(new ItemStack(ModBlocks.struct_launcher_core_large, 1), new Object[] { "SIS", "ICI", "BEB", 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'I', Blocks.iron_bars, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'B', ModBlocks.struct_launcher, 'E', ModBlocks.machine_battery });
			addRecipeAuto(new ItemStack(ModBlocks.struct_soyuz_core, 1), new Object[] { "CUC", "TST", "TBT", 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED), 'U', ModItems.upgrade_power_3, 'T', ModBlocks.barrel_steel, 'S', ModBlocks.steel_scaffold, 'B', ModBlocks.machine_lithium_battery });
			addRecipeAuto(new ItemStack(ModItems.reactor_sensor, 1), new Object[] { "WPW", "CMC", "PPP", 'W', W.wireFine(), 'P', PB.plate(), 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC), 'M', ModItems.magnetron });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_console, 1), new Object[] { "BBB", "DGD", "DCD", 'B', B.ingot(), 'D', ModBlocks.deco_rbmk, 'G', KEY_ANYPANE, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG) });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_crane_console, 1), new Object[] { "BCD", "DDD", 'B', B.ingot(), 'D', ModBlocks.deco_rbmk, 'C', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG) });
			addRecipeAuto(new ItemStack(ModBlocks.hadron_core, 1), new Object[] { "CCC", "DSD", "CCC", 'C', ModBlocks.hadron_coil_alloy, 'D', ModBlocks.hadron_diode, 'S', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED) });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod, 1), new Object[] { "C", "R", "C", 'C', STEEL.shell(), 'R', ModBlocks.rbmk_blank });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_rod_mod, 1), new Object[] { "BGB", "GRG", "BGB", 'G', GRAPHITE.block(), 'R', ModBlocks.rbmk_rod, 'B', ModItems.nugget_bismuth });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_boiler, 1), new Object[] { "CPC", "CRC", "CPC", 'C', CU.pipe(), 'P', CU.shell(), 'R', ModBlocks.rbmk_blank });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_heater, 1), new Object[] { "CIC", "PRP", "CIC", 'C', CU.pipe(), 'P', STEEL.shell(), 'R', ModBlocks.rbmk_blank, 'I', ANY_PLASTIC.ingot() });
			addRecipeAuto(new ItemStack(ModBlocks.rbmk_cooler, 1), new Object[] { "IGI", "GCG", "IGI", 'C', ModBlocks.rbmk_blank, 'I', ModItems.plate_polymer, 'G', ModBlocks.steel_grate });
		}

		addShapelessAuto(new ItemStack(ModItems.launch_code), new Object[] {
				new ItemStack(ModItems.launch_code_piece), new ItemStack(ModItems.launch_code_piece),
				new ItemStack(ModItems.launch_code_piece), new ItemStack(ModItems.launch_code_piece),
				new ItemStack(ModItems.launch_code_piece), new ItemStack(ModItems.launch_code_piece),
				new ItemStack(ModItems.launch_code_piece), new ItemStack(ModItems.launch_code_piece),
				DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED)
		});

		addShapelessAuto(ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CHIPSET), new Object[] {
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_BIOS),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_BUS),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_CHIPSET),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_CMOS),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_IO),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_NORTH),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BRIDGE_SOUTH)
		});

		addShapelessAuto(ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CPU), new Object[] {
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_CACHE),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_CLOCK),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_EXT),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_LOGIC),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_REGISTER),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CPU_SOCKET)
		});

		addShapelessAuto(ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.RAM), new Object[] {
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_SOCKET),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_A),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_B),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_C),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.MEM_16K_D)
		});

		addShapelessAuto(ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CARD), new Object[] {
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CARD_BOARD),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.CARD_PROCESSOR)
		});

		addShapelessAuto(new ItemStack(ModItems.circuit_star), new Object[] {
				ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CHIPSET),
				ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CPU),
				ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.RAM),
				ModItems.circuit_star_component.stackFromEnum(CircuitComponentType.CARD),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BOARD_TRANSISTOR),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BOARD_CONVERTER),
				ModItems.circuit_star_piece.stackFromEnum(ScrapType.BOARD_BLANK)
		});

		addRecipeAuto(new ItemStack(ModItems.sliding_blast_door_skin), "SPS", "DPD", "SPS", 'P', Items.paper, 'D', "dye", 'S', STEEL.plate());
		addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin, 1, 1), new ItemStack(ModItems.sliding_blast_door_skin, 1, 0));
		addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin, 1, 2), new ItemStack(ModItems.sliding_blast_door_skin, 1, 1));
		addShapelessAuto(new ItemStack(ModItems.sliding_blast_door_skin), new ItemStack(ModItems.sliding_blast_door_skin, 1, 2));

		addRecipeAuto(new ItemStack(ModBlocks.cm_block, 4, 0), " I ", "IPI", " I ", 'I', STEEL.ingot(), 'P', STEEL.plateCast());
		addRecipeAuto(new ItemStack(ModBlocks.cm_block, 4, 1), " I ", "IPI", " I ", 'I', ALLOY.ingot(), 'P', ALLOY.plateCast());
		addRecipeAuto(new ItemStack(ModBlocks.cm_block, 4, 2), " I ", "IPI", " I ", 'I', DESH.ingot(), 'P', DESH.plateCast());
		addRecipeAuto(new ItemStack(ModBlocks.cm_block, 4, 3), " I ", "IPI", " I ", 'I', ANY_RESISTANTALLOY.ingot(), 'P', ANY_RESISTANTALLOY.plateCast());

		for(int i = 0; i < 4; i++) {
			addRecipeAuto(new ItemStack(ModBlocks.cm_sheet, 16, i), "BB", "BB", 'B', new ItemStack(ModBlocks.cm_block, 1, i));
			addRecipeAuto(new ItemStack(ModBlocks.cm_tank, 4, i), " B ", "BGB", " B ", 'B', new ItemStack(ModBlocks.cm_block, 1, i), 'G', KEY_ANYGLASS);
			addRecipeAuto(new ItemStack(ModBlocks.cm_port, 1, i), "P", "B", "P", 'B', new ItemStack(ModBlocks.cm_block, 1, i), 'P', IRON.plate());
		}

		addRecipeAuto(new ItemStack(ModBlocks.cm_engine, 1, 0), " I ", "IMI", " I ", 'I', STEEL.ingot(), 'M', ModItems.motor);
		addRecipeAuto(new ItemStack(ModBlocks.cm_engine, 1, 1), " I ", "IMI", " I ", 'I', STEEL.ingot(), 'M', ModItems.motor_desh);
		addRecipeAuto(new ItemStack(ModBlocks.cm_engine, 1, 2), " I ", "IMI", " I ", 'I', STEEL.ingot(), 'M', ModItems.motor_bismuth);
		addRecipeAuto(new ItemStack(ModBlocks.cm_circuit, 1, 0), " I ", "IMI", " I ", 'I', STEEL.ingot(), 'M', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE));
		addRecipeAuto(new ItemStack(ModBlocks.cm_circuit, 1, 1), " I ", "IMI", " I ", 'I', STEEL.ingot(), 'M', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ANALOG));
		addRecipeAuto(new ItemStack(ModBlocks.cm_circuit, 1, 2), " I ", "IMI", " I ", 'I', STEEL.ingot(), 'M', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BASIC));
		addRecipeAuto(new ItemStack(ModBlocks.cm_circuit, 1, 3), " I ", "IMI", " I ", 'I', STEEL.ingot(), 'M', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.ADVANCED));
		addRecipeAuto(new ItemStack(ModBlocks.cm_circuit, 1, 4), " I ", "IMI", " I ", 'I', STEEL.ingot(), 'M', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.BISMOID));
		addRecipeAuto(new ItemStack(ModBlocks.cm_flux, 1, 0), "NNN", "ZCZ", "NNN", 'Z', ZR.plateCast(), 'N', ModItems.neutron_reflector, 'C', ModItems.reactor_core);
		addRecipeAuto(new ItemStack(ModBlocks.cm_heat, 1, 0), "PCP", "PCP", "PCP", 'P', ModItems.plate_polymer, 'C', CU.ingot());

		addRecipeAuto(new ItemStack(ModBlocks.plushie, 1, PlushieType.YOMI.ordinal()), "LCR", 'L', "cropCarrot", 'C', ModItems.rag, 'R', DictFrame.fromOne(ModItems.circuit, EnumCircuitType.VACUUM_TUBE));
		addRecipeAuto(new ItemStack(ModBlocks.plushie, 1, PlushieType.NUMBERNINE.ordinal()), " C ", "LCR", " C ", 'L', ModItems.cigarette, 'C', ModItems.rag, 'R', COAL.gem());
	}

	public static void crumple() {

		List<ItemStack> targets = new ArrayList();

		if(GeneralConfig.enableMekanismChanges) {

			if(Loader.isModLoaded("Mekanism")) {
				Block mb = (Block) Block.blockRegistry.getObject("Mekanism:MachineBlock");
				Item disassembler = (Item) Item.itemRegistry.getObject("Mekanism:AtomicDisassembler");
				targets.add(new ItemStack(mb, 1, 4)); // digiminer
				targets.add(new ItemStack(disassembler)); // atomic disassembler
			}

			if(Loader.isModLoaded("MekanismGenerators")) {
				Block mb = (Block) Block.blockRegistry.getObject("MekanismGenerators:Generator");
				targets.add(new ItemStack(mb, 1, 6)); // wind turbine
			}

			List<IRecipe> toDestroy = new ArrayList();

			List recipeList = net.minecraft.item.crafting.CraftingManager.getInstance().getRecipeList();

			synchronized(recipeList) { //this is how threading works. i think.
				for(Object o : recipeList) {

					if(o instanceof IRecipe) {
						IRecipe rec = (IRecipe)o;
						ItemStack stack = rec.getRecipeOutput();

						for(ItemStack target : targets) {
							if(stack != null && stack.getItem() == target.getItem() && stack.getItemDamage() == target.getItemDamage()) toDestroy.add(rec);
						}
					}
				}

				if(toDestroy.size() > 0) {
					recipeList.removeAll(toDestroy);
				}

				if(Loader.isModLoaded("Mekanism")) {
					Item disassembler = (Item) Item.itemRegistry.getObject("Mekanism:AtomicDisassembler");
					if(disassembler != null) addRecipeAuto(new ItemStack(disassembler, 1), "GAG", "EIE", " I ", 'G', GOLD.plateCast(), 'A', "alloyUltimate", 'E', "battery", 'I', "ingotRefinedObsidian");
				}

				if(Loader.isModLoaded("MekanismGenerators")) {
					Block generator = (Block) Block.blockRegistry.getObject("MekanismGenerators:Generator");
					if(generator != null) addRecipeAuto(new ItemStack(generator, 1, 6), " T ", "TAT", "BCB", 'T', TI.plateCast(), 'A', "alloyAdvanced", 'B', "battery", 'C', ANY_PLASTIC.ingot());
				}
			}
		}
	}

	//option 1: find every entry that needs to be ore dicted and change the recipe method by hand and commit to doing it right in the future
	//option 2: just make the computer do all the stupid work for us
	public static void addRecipeAuto(ItemStack result, Object... ins) {

		boolean shouldUseOD = false;
		boolean engage = false;

		for(int i = 0; i < ins.length; i++) {
			Object ingredient = ins[i];

			if(ingredient instanceof String) {

				if(engage) {
					shouldUseOD = true;
					break;
				}
			} else {
				engage = true;
			}
		}

		if(shouldUseOD)
			GameRegistry.addRecipe(new ShapedOreRecipe(result, ins));
		else
			GameRegistry.addRecipe(result, ins);
	}

	public static void addShapelessAuto(ItemStack result, Object... ins) {

		boolean shouldUseOD = false;

		for(int i = 0; i < ins.length; i ++) {
			Object ingredient = ins[i];

			if(ingredient instanceof String) {
				shouldUseOD = true;
				break;
			}
		}

		if(shouldUseOD)
			GameRegistry.addRecipe(new ShapelessOreRecipe(result, ins));
		else
			GameRegistry.addShapelessRecipe(result, ins);
	}
}
