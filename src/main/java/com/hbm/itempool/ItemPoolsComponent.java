package com.hbm.itempool;

import static com.hbm.lib.HbmChestContents.weighted;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.machine.ItemCircuit.EnumCircuitType;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemRTGPelletDepleted;
import com.hbm.items.tool.ItemBlowtorch;

import net.minecraft.init.Items;
import net.minecraft.util.WeightedRandomChestContent;

public class ItemPoolsComponent {

	public static final String POOL_MACHINE_PARTS = "POOL_MACHINE_PARTS";
	public static final String POOL_NUKE_FUEL = "POOL_NUKE_FUEL";
	public static final String POOL_SILO = "POOL_SILO";
	public static final String POOL_OFFICE_TRASH = "POOL_OFFICE_TRASH";
	public static final String POOL_FILING_CABINET = "POOL_FILING_CABINET";
	public static final String POOL_SOLID_FUEL = "POOL_SOLID_FUEL";
	public static final String POOL_VAULT_LAB = "POOL_VAULT_LAB";
	public static final String POOL_VAULT_LOCKERS = "POOL_VAULT_LOCKERS";
	public static final String POOL_METEOR_SAFE = "POOL_METEOR_SAFE";
	public static final String POOL_OIL_RIG = "POOL_OIL_RIG";
	public static final String POOL_RTG = "POOL_RTG";
	public static final String POOL_REPAIR_MATERIALS = "POOL_REPAIR_MATERIALS";

	public static void init() {

		//machine parts
		new ItemPool(POOL_MACHINE_PARTS) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.plate_steel, 0, 1, 5, 5),
					weighted(ModItems.shell, Mats.MAT_STEEL.id, 1, 3, 3),
					weighted(ModItems.plate_polymer, 0, 1, 6, 5),
					weighted(ModItems.bolt, Mats.MAT_STEEL.id, 4, 16, 3),
					weighted(ModItems.bolt, Mats.MAT_TUNGSTEN.id, 4, 16, 3),
					weighted(ModItems.coil_tungsten, 0, 1, 2, 5),
					weighted(ModItems.motor, 0, 1, 2, 4),
					weighted(ModItems.tank_steel, 0, 1, 2, 3),
					weighted(ModItems.coil_copper, 0, 1, 3, 4),
					weighted(ModItems.coil_copper_torus, 0, 1, 2, 3),
					weighted(ModItems.wire_fine, Mats.MAT_MINGRADE.id, 1, 8, 5),
					weighted(ModItems.piston_selenium, 0, 1, 1, 3),
					weighted(ModItems.battery_advanced_cell, 0, 1, 1, 3),
					weighted(ModItems.circuit, EnumCircuitType.VACUUM_TUBE.ordinal(), 1, 2, 4),
					weighted(ModItems.circuit, EnumCircuitType.PCB.ordinal(), 1, 3, 5),
					weighted(ModItems.circuit, EnumCircuitType.CAPACITOR.ordinal(), 1, 1, 3),
					weighted(ModItems.blade_titanium, 0, 1, 8, 1)
			};
		}};

		//fuel isotopes found in bunkers and labs
		new ItemPool(POOL_NUKE_FUEL) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.billet_uranium, 0, 1, 4, 4),
					weighted(ModItems.billet_th232, 0, 1, 3, 3),
					weighted(ModItems.billet_uranium_fuel, 0, 1, 3, 5),
					weighted(ModItems.billet_mox_fuel, 0, 1, 3, 5),
					weighted(ModItems.billet_thorium_fuel, 0, 1, 3, 3),
					weighted(ModItems.billet_ra226be, 0, 1, 2, 2),
					weighted(ModItems.billet_beryllium, 0, 1, 1, 1),
					weighted(ModItems.nugget_u233, 0, 1, 1, 1),
					weighted(ModItems.nugget_uranium_fuel, 0, 1, 1, 1),
					weighted(ModItems.rod_zirnox_empty, 0, 1, 3, 3),
					weighted(ModItems.ingot_graphite, 0, 1, 4, 3),
					weighted(ModItems.pile_rod_uranium, 0, 2, 5, 3),
					weighted(ModItems.pile_rod_source, 0, 1, 2, 2),
					weighted(ModItems.reacher, 0, 1, 1, 3),
					weighted(ModItems.screwdriver, 0, 1, 1, 2)
			};
		}};

		//missile parts found in silos
		new ItemPool(POOL_SILO) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.missile_generic, 0, 1, 1, 4),
					weighted(ModItems.missile_incendiary, 0, 1, 1, 4),
					weighted(ModItems.gas_mask_m65, 0, 1, 1, 5),
					weighted(ModItems.battery_advanced, 0, 1, 1, 5),
					weighted(ModItems.designator, 0, 1, 1, 5),
					weighted(ModItems.thruster_small, 0, 1, 1, 5),
					weighted(ModItems.thruster_medium, 0, 1, 1, 4),
					weighted(ModItems.fuel_tank_small, 0, 1, 1, 5),
					weighted(ModItems.fuel_tank_medium, 0, 1, 1, 4),
					weighted(ModItems.bomb_caller, 0, 1, 1, 1),
					weighted(ModItems.bomb_caller, 3, 1, 1, 1),
					weighted(ModItems.bottle_nuka, 0, 1, 3, 10)
			};
		}};

		//low quality items from offices in chests
		new ItemPool(POOL_OFFICE_TRASH) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(Items.paper, 0, 1, 12, 10),
					weighted(Items.book, 0, 1, 3, 4),
					weighted(ModItems.twinkie, 0, 1, 2, 6),
					weighted(ModItems.coffee, 0, 1, 1, 4),
					weighted(ModItems.flame_politics, 0, 1, 1, 2),
					weighted(ModItems.ring_pull, 0, 1, 1, 4),
					weighted(ModItems.can_empty, 0, 1, 1, 2),
					weighted(ModItems.can_creature, 0, 1, 2, 2),
					weighted(ModItems.can_smart, 0, 1, 3, 2),
					weighted(ModItems.can_mrsugar, 0, 1, 2, 2),
					weighted(ModItems.cap_nuka, 0, 1, 16, 2),
					weighted(ModItems.book_guide, 3, 1, 1, 1),
					weighted(ModBlocks.deco_computer, 0, 1, 1, 1)
			};
		}};

		//things found in various filing cabinets, paper, books, etc
		new ItemPool(POOL_FILING_CABINET) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(Items.paper, 0, 1, 12, 240),
					weighted(Items.book, 0, 1, 3, 90),
					weighted(Items.map, 0, 1, 1, 50),
					weighted(Items.writable_book, 0, 1, 1, 30),
					weighted(ModItems.cigarette, 0, 1, 16, 20),
					weighted(ModItems.toothpicks, 0, 1, 16, 10),
					weighted(ModItems.dust, 0, 1, 1, 40),
					weighted(ModItems.dust_tiny, 0, 1, 3, 75),
					weighted(ModItems.ink, 0, 1, 1, 1)
			};
		}};

		//solid fuels from bunker power rooms
		new ItemPool(POOL_SOLID_FUEL) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.solid_fuel, 0, 1, 5, 1),
					weighted(ModItems.solid_fuel_presto, 0, 1, 2, 2),
					weighted(ModItems.ball_dynamite, 0, 1, 4, 2),
					weighted(ModItems.coke, EnumCokeType.PETROLEUM.ordinal(), 1, 3, 1),
					weighted(Items.redstone, 0, 1, 3, 1),
					weighted(ModItems.niter, 0, 1, 3, 1)
			};
		}};

		//various lab related items from bunkers
		new ItemPool(POOL_VAULT_LAB) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ItemBlowtorch.getEmptyTool(ModItems.blowtorch), 1, 1, 4),
					weighted(ModItems.chemistry_set, 0, 1, 1, 15),
					weighted(ModItems.screwdriver, 0, 1, 1, 10),
					weighted(ModItems.nugget_mercury, 0, 1, 1, 3),
					weighted(ModItems.morning_glory, 0, 1, 1, 1),
					weighted(ModItems.filter_coal, 0, 1, 1, 5),
					weighted(ModItems.dust, 0, 1, 3, 25),
					weighted(Items.paper, 0, 1, 2, 15),
					weighted(ModItems.cell_empty, 0, 1, 1, 5),
					weighted(Items.glass_bottle, 0, 1, 1, 5),
					weighted(ModItems.powder_iodine, 0, 1, 1, 1),
					weighted(ModItems.powder_bromine, 0, 1, 1, 1),
					weighted(ModItems.powder_cobalt, 0, 1, 1, 1),
					weighted(ModItems.powder_neodymium, 0, 1, 1, 1),
					weighted(ModItems.powder_boron, 0, 1, 1, 1)
			};
		}};

		//personal items and gear from vaults
		new ItemPool(POOL_VAULT_LOCKERS) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.robes_helmet, 0, 1, 1, 1),
					weighted(ModItems.robes_plate, 0, 1, 1, 1),
					weighted(ModItems.robes_legs, 0, 1, 1, 1),
					weighted(ModItems.robes_boots, 0, 1, 1, 1),
					weighted(ModItems.jackt, 0, 1, 1, 1),
					weighted(ModItems.jackt2, 0, 1, 1, 1),
					weighted(ModItems.gas_mask_m65, 0, 1, 1, 2),
					weighted(ModItems.gas_mask_mono, 0, 1, 1, 2),
					weighted(ModItems.goggles, 0, 1, 1, 2),
					weighted(ModItems.gas_mask_filter, 0, 1, 1, 4),
					weighted(ModItems.flame_opinion, 0, 1, 3, 5),
					weighted(ModItems.flame_conspiracy, 0, 1, 3, 5),
					weighted(ModItems.flame_politics, 0, 1, 3, 5),
					weighted(ModItems.definitelyfood, 0, 2, 7, 5),
					weighted(ModItems.cigarette, 0, 1, 8, 5),
					weighted(ModItems.armor_polish, 0, 1, 1, 3),
					weighted(ModItems.gun_kit_1, 0, 1, 1, 3),
					weighted(ModItems.rag, 0, 1, 3, 5),
					weighted(Items.paper, 0, 1, 6, 7),
					weighted(Items.clock, 0, 1, 1, 3),
					weighted(Items.book, 0, 1, 5, 10),
					weighted(Items.experience_bottle, 0, 1, 3, 1)
			};
		}};

		// Black Book safe in meteor dungeons
		new ItemPool(POOL_METEOR_SAFE) {{
			this.pool = new WeightedRandomChestContent[] {
					weighted(ModItems.book_of_, 0, 1, 1, 1),
					weighted(ModItems.stamp_book, 0, 1, 1, 1),
					weighted(ModItems.stamp_book, 1, 1, 1, 1),
					weighted(ModItems.stamp_book, 2, 1, 1, 1),
					weighted(ModItems.stamp_book, 3, 1, 1, 1),
					weighted(ModItems.stamp_book, 4, 1, 1, 1),
					weighted(ModItems.stamp_book, 5, 1, 1, 1),
					weighted(ModItems.stamp_book, 6, 1, 1, 1),
					weighted(ModItems.stamp_book, 7, 1, 1, 1),
			};
		}};

		new ItemPool(POOL_OIL_RIG) {{
			this.pool = new WeightedRandomChestContent[] {
				weighted(ModItems.oil_detector, 0, 1, 1, 1),
				weighted(ModItems.canister_full, Fluids.OIL.getID(), 1, 4, 5),
				weighted(ModBlocks.machine_fraction_tower,0, 0, 1, 1),
				weighted(ModBlocks.fraction_spacer,0, 0, 1, 1),
				weighted(ModItems.circuit,EnumCircuitType.ANALOG.ordinal(), 1, 4, 1),
				weighted(ModItems.circuit, EnumCircuitType.CAPACITOR.ordinal(), 1, 1, 3),
			};
		}};
    
		new ItemPool(POOL_RTG) {{
			this.pool = new WeightedRandomChestContent[] {
				weighted(ModItems.pellet_rtg_depleted, ItemRTGPelletDepleted.DepletedRTGMaterial.LEAD.ordinal(), 1, 1, 40),
				weighted(ModItems.pellet_rtg_weak,0, 0, 1, 1),
			};
		}};
        
		new ItemPool(POOL_REPAIR_MATERIALS) {{
			this.pool = new WeightedRandomChestContent[] {
				weighted(ModItems.ingot_aluminium, 0, 2, 8, 3),
				weighted(ModItems.ingot_steel, 0, 0, 12, 4),
				weighted(ModItems.plate_aluminium, 0, 5, 12, 3),
				weighted(ModItems.plate_iron, 0, 6, 16, 3),
				weighted(ModItems.plate_steel, 0, 2, 12, 2),
				weighted(ModItems.ingot_tungsten, 0, 0, 2, 1),
				weighted(ModBlocks.deco_aluminium, 0, 12, 24, 4),
				weighted(ModBlocks.deco_steel, 0, 5, 12, 2),
				weighted(ModBlocks.block_aluminium, 0, 0, 2, 1),
				weighted(ModBlocks.block_steel, 0, 0, 1, 1),
				weighted(ModItems.bolt, Mats.MAT_STEEL.id, 4, 16, 3),
				weighted(ModItems.circuit, EnumCircuitType.VACUUM_TUBE.ordinal(), 1, 2, 4),
				weighted(ModItems.circuit, EnumCircuitType.ANALOG.ordinal(), 1, 3, 5),
				weighted(ModItems.circuit, EnumCircuitType.CAPACITOR.ordinal(), 1, 1, 3),
			};
		}};
	}
}
