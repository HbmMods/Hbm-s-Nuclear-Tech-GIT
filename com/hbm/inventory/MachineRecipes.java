package com.hbm.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemBattery;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.machine.ItemAssemblyTemplate.EnumAssemblyTemplate;
import com.hbm.main.MainRegistry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

//TODO: clean this shit up
@Spaghetti("everything")
public class MachineRecipes {

	public MachineRecipes() {

	}

	public static MachineRecipes instance() {
		return new MachineRecipes();
	}

	public static ItemStack getFurnaceProcessingResult(ItemStack item, ItemStack item2) {
		return getFurnaceOutput(item, item2);
	}

	public static ItemStack getFurnaceOutput(ItemStack item, ItemStack item2) {
		
		if(item == null || item2 == null)
			return null;
		
		if (MainRegistry.enableDebugMode) {
			if (item.getItem() == Items.iron_ingot && item2.getItem() == Items.quartz
					|| item.getItem() == Items.quartz && item2.getItem() == Items.iron_ingot) {
				return new ItemStack(ModBlocks.test_render, 1);
			}
		}

		if (mODE(item, new String[] {"ingotTungsten", "dustTungsten"}) && mODE(item2, "gemCoal")
				|| mODE(item, "gemCoal") && mODE(item2, new String[] {"ingotTungsten", "dustTungsten"})) {
			return new ItemStack(ModItems.neutron_reflector, 2);
		}

		if (mODE(item, new String[] {"ingotLead", "dustLead"}) && mODE(item2, new String[] {"ingotCopper", "dustCopper"})
				|| mODE(item, new String[] {"ingotCopper", "dustCopper"}) && mODE(item2, new String[] {"ingotLead", "dustLead"})) {
			return new ItemStack(ModItems.neutron_reflector, 4);
		}

		if (mODE(item, "plateLead") && mODE(item2, "plateCopper")
				|| mODE(item, "plateCopper") && mODE(item2, "plateLead")) {
			return new ItemStack(ModItems.neutron_reflector, 1);
		}

		if (mODE(item, new String[] {"ingotIron", "dustIron"}) && mODE(item2, new String[] {"gemCoal", "dustCoal"})
				|| mODE(item, new String[] {"gemCoal", "dustCoal"}) && mODE(item2, new String[] {"ingotIron", "dustIron"})) {
			return new ItemStack(ModItems.ingot_steel, 2);
		}

		if (mODE(item, new String[] {"ingotCopper", "dustCopper"}) && item2.getItem() == Items.redstone
				|| item.getItem() == Items.redstone && mODE(item2, new String[] {"ingotCopper", "dustCopper"})) {
			return new ItemStack(ModItems.ingot_red_copper, 2);
		}

		if (item.getItem() == ModItems.canister_fuel && item2.getItem() == Items.slime_ball
				|| item.getItem() == Items.slime_ball && item2.getItem() == ModItems.canister_fuel) {
			return new ItemStack(ModItems.canister_napalm, 1);
		}

		if (mODE(item, new String[] {"ingotRedstoneAlloy", "dustRedstoneAlloy"}) && mODE(item2, new String[] {"ingotSteel", "dustSteel"})
				|| mODE(item, new String[] {"ingotSteel", "dustSteel"}) && mODE(item2, new String[] {"ingotRedstoneAlloy", "dustRedstoneAlloy"})) {
			return new ItemStack(ModItems.ingot_advanced_alloy, 2);
		}

		if (mODE(item, new String[] {"ingotTungsten", "dustTungsten"}) && mODE(item2, "nuggetSchrabidium")
				|| mODE(item, "nuggetSchrabidium") && mODE(item2, new String[] {"ingotTungsten", "dustTungsten"})) {
			return new ItemStack(ModItems.ingot_magnetized_tungsten, 1);
		}

		if (item.getItem() == ModItems.plate_mixed && mODE(item2, "plateGold")
				|| mODE(item, "plateGold") && item2.getItem() == ModItems.plate_mixed) {
			return new ItemStack(ModItems.plate_paa, 2);
		}

		if (mODE(item, new String[] {"ingotSteel", "dustSteel"}) && mODE(item2, new String[] {"ingotTungsten", "dustTungsten"})
				|| mODE(item, new String[] {"ingotTungsten", "dustTungsten"}) && mODE(item2, new String[] {"ingotSteel", "dustSteel"})) {
			return new ItemStack(ModItems.ingot_dura_steel, 2);
		}

		if (mODE(item, new String[] {"ingotSteel", "dustSteel"}) && mODE(item2, new String[] {"ingotCobalt", "dustCobalt"})
				|| mODE(item, new String[] {"ingotCobalt", "dustCobalt"}) && mODE(item2, new String[] {"ingotSteel", "dustSteel"})) {
			return new ItemStack(ModItems.ingot_dura_steel, 2);
		}

		if (mODE(item, new String[] {"ingotSaturnite", "dustSaturnite"}) && item2.getItem() == ModItems.powder_meteorite
				|| item.getItem() == ModItems.powder_meteorite && mODE(item2, new String[] {"ingotSaturnite", "dustSaturnite"})) {
			return new ItemStack(ModItems.ingot_starmetal, 2);
		}

		return null;
	}

	// Arrays!

	public static ItemStack[] getCentrifugeProcessingResult(ItemStack item) {
		return getCentrifugeOutput(item);
	}

	public static ItemStack[] getCentrifugeOutput(ItemStack item) {
		
		if(item == null || item.getItem() == null)
			return null;

		ItemStack[] test = new ItemStack[] { new ItemStack(Items.apple, 3), new ItemStack(Items.leather, 1),
				new ItemStack(Items.sugar, 3), new ItemStack(Items.blaze_powder, 2) };

		ItemStack[] uranF = new ItemStack[] { new ItemStack(ModItems.nugget_u235, 1),
				new ItemStack(ModItems.nugget_u238, 2), new ItemStack(ModItems.nugget_pu239, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 2) };
		ItemStack[] plutoniumF = new ItemStack[] { new ItemStack(ModItems.nugget_pu239, 1),
				new ItemStack(ModItems.nugget_pu240, 1), new ItemStack(ModItems.nugget_lead, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 3) };
		ItemStack[] moxF = new ItemStack[] { new ItemStack(ModItems.nugget_pu239, 1),
				new ItemStack(ModItems.nugget_neptunium, 1), new ItemStack(ModItems.nugget_u238, 2),
				new ItemStack(ModItems.nuclear_waste_tiny, 2) };
		ItemStack[] schrabidiumF = new ItemStack[] { new ItemStack(ModItems.nugget_beryllium, 1),
				new ItemStack(ModItems.nugget_lead, 1), new ItemStack(ModItems.nugget_solinium, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 3) };
		ItemStack[] thoriumF = new ItemStack[] { new ItemStack(ModItems.nugget_u238, 1),
				new ItemStack(ModItems.nugget_th232, 1), new ItemStack(ModItems.nugget_u233, 3),
				new ItemStack(ModItems.nuclear_waste_tiny, 1) };
		
		ItemStack[] cloud = new ItemStack[] { new ItemStack(ModItems.powder_copper, 1),
				new ItemStack(ModItems.sulfur, 1), new ItemStack(ModItems.dust, 1),
				new ItemStack(ModItems.dust, 1) };

		ItemStack[] coal = new ItemStack[] { new ItemStack(ModItems.powder_coal, 2),
				new ItemStack(ModItems.powder_coal, 2), new ItemStack(ModItems.powder_coal, 2),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] lignite = new ItemStack[] { new ItemStack(ModItems.powder_lignite, 2),
				new ItemStack(ModItems.powder_lignite, 2), new ItemStack(ModItems.powder_lignite, 2),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] iron = new ItemStack[] { new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(ModItems.powder_iron, 1), new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] gold = new ItemStack[] { new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(ModItems.powder_gold, 1), new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] diamond = new ItemStack[] { new ItemStack(ModItems.powder_diamond, 1),
				new ItemStack(ModItems.powder_diamond, 1), new ItemStack(ModItems.powder_diamond, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] emerald = new ItemStack[] { new ItemStack(ModItems.powder_emerald, 1),
				new ItemStack(ModItems.powder_emerald, 1), new ItemStack(ModItems.powder_emerald, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] titanium = new ItemStack[] { new ItemStack(ModItems.powder_titanium, 1),
				new ItemStack(ModItems.powder_titanium, 1), new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] quartz = new ItemStack[] { new ItemStack(ModItems.powder_quartz, 1),
				new ItemStack(ModItems.powder_quartz, 1), new ItemStack(ModItems.powder_lithium_tiny, 1),
				new ItemStack(Blocks.netherrack, 1) };
		ItemStack[] tungsten = new ItemStack[] { new ItemStack(ModItems.powder_tungsten, 1),
				new ItemStack(ModItems.powder_tungsten, 1), new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] copper = new ItemStack[] { new ItemStack(ModItems.powder_copper, 1),
				new ItemStack(ModItems.powder_copper, 1), new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] aluminium = new ItemStack[] { new ItemStack(ModItems.powder_aluminium, 1),
				new ItemStack(ModItems.powder_aluminium, 1), new ItemStack(ModItems.powder_iron, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] lead = new ItemStack[] { new ItemStack(ModItems.powder_lead, 1),
				new ItemStack(ModItems.powder_lead, 1), new ItemStack(ModItems.powder_gold, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] schrabidium = new ItemStack[] { new ItemStack(ModItems.powder_schrabidium, 1),
				new ItemStack(ModItems.powder_schrabidium, 1), new ItemStack(ModItems.nugget_solinium, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] rare = new ItemStack[] { new ItemStack(ModItems.powder_desh_mix, 1),
				new ItemStack(ModItems.powder_actinium_tiny, 1), new ItemStack(ModItems.powder_lanthanium_tiny, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] plutonium = new ItemStack[] { new ItemStack(ModItems.powder_plutonium, 1),
				new ItemStack(ModItems.powder_plutonium, 1), new ItemStack(ModItems.powder_uranium, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] uranium = new ItemStack[] { new ItemStack(ModItems.powder_uranium, 1),
				new ItemStack(ModItems.powder_uranium, 1), new ItemStack(ModItems.powder_thorium, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] thorium = new ItemStack[] { new ItemStack(ModItems.powder_thorium, 1),
				new ItemStack(ModItems.powder_thorium, 1), new ItemStack(ModItems.powder_uranium, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] beryllium = new ItemStack[] { new ItemStack(ModItems.powder_beryllium, 1),
				new ItemStack(ModItems.powder_beryllium, 1), new ItemStack(ModItems.powder_emerald, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] redstone = new ItemStack[] { new ItemStack(Items.redstone, 3),
				new ItemStack(Items.redstone, 3), new ItemStack(ModItems.nugget_mercury, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] tikite = new ItemStack[] { new ItemStack(ModItems.powder_plutonium, 2),
				new ItemStack(ModItems.powder_cobalt, 2), new ItemStack(ModItems.powder_nitan_mix, 1),
				new ItemStack(Blocks.end_stone, 1) };
		ItemStack[] lapis = new ItemStack[] { new ItemStack(ModItems.powder_lapis, 3),
				new ItemStack(ModItems.powder_lapis, 3), new ItemStack(ModItems.powder_cobalt_tiny, 1),
				new ItemStack(Blocks.gravel, 1) };
		ItemStack[] starmetal = new ItemStack[] { new ItemStack(ModItems.powder_dura_steel, 3),
				new ItemStack(ModItems.powder_astatine, 1), new ItemStack(ModItems.powder_cobalt, 2),
				new ItemStack(Blocks.gravel, 1) };
		
		ItemStack[] euphCluster = new ItemStack[] { new ItemStack(ModItems.nugget_euphemium, 7),
				new ItemStack(ModItems.powder_schrabidium, 4), new ItemStack(ModItems.ingot_starmetal, 2),
				new ItemStack(ModItems.nugget_solinium, 2) };

		if (MainRegistry.enableDebugMode) {
			if (item.getItem() == Item.getItemFromBlock(ModBlocks.test_render)) {
				return test;
			}
		}

		if (item.getItem() == ModItems.waste_uranium) {
			return uranF;
		}

		if (item.getItem() == ModItems.waste_thorium) {
			return thoriumF;
		}

		/*if (item.getItem() == ModItems.rod_dual_uranium_fuel_depleted) {
			return uran2;
		}

		if (item.getItem() == ModItems.rod_quad_uranium_fuel_depleted) {
			return uran3;
		}*/

		if (item.getItem() == ModItems.waste_plutonium) {
			return plutoniumF;
		}

		if (item.getItem() == ModItems.waste_mox) {
			return moxF;
		}

		if (item.getItem() == ModItems.waste_schrabidium) {
			return schrabidiumF;
		}

		if (item.getItem() == ModItems.powder_cloud) {
			return cloud;
		}

		if (mODE(item, "oreCoal")) {
			return coal;
		}

		if (mODE(item, "oreLignite")) {
			return lignite;
		}

		if (mODE(item, "oreIron")) {
			return iron;
		}

		if (mODE(item, "oreGold")) {
			return gold;
		}

		if (mODE(item, "oreDiamond")) {
			return diamond;
		}

		if (mODE(item, "oreEmerald")) {
			return emerald;
		}

		if (mODE(item, "oreUranium")) {
			return uranium;
		}

		if (mODE(item, "oreThorium")) {
			return thorium;
		}

		if (mODE(item, "orePlutonium")) {
			return plutonium;
		}

		if (mODE(item, "oreTitanium")) {
			return titanium;
		}

		if (mODE(item, "oreTungsten")) {
			return tungsten;
		}

		if (mODE(item, "oreLead")) {
			return lead;
		}

		if (mODE(item, "oreBeryllium")) {
			return beryllium;
		}

		if (mODE(item, "oreAluminum")) {
			return aluminium;
		}

		if (mODE(item, "oreSchrabidium")) {
			return schrabidium;
		}

		if (mODE(item, "oreQuartz") || mODE(item, "oreNetherQuartz")) {
			return quartz;
		}

		if (item.getItem() == Item.getItemFromBlock(ModBlocks.ore_rare)) {
			return rare;
		}

		if (mODE(item, "oreCopper")) {
			return copper;
		}

		if (mODE(item, "oreRedstone") || item.getItem() == Item.getItemFromBlock(Blocks.lit_redstone_ore)) {
			return redstone;
		}

		if (item.getItem() == Item.getItemFromBlock(ModBlocks.ore_tikite)) {
			return tikite;
		}

		if (mODE(item, "oreLapis")) {
			return lapis;
		}

		if (mODE(item, "oreStarmetal")) {
			return starmetal;
		}

		if (item.getItem() == Item.getItemFromBlock(ModBlocks.block_euphemium_cluster)) {
			return euphCluster;
		}

		if (item.getItem() == Item.getItemFromBlock(ModBlocks.ore_nether_fire)) {
			return new ItemStack[] {
					new ItemStack(Items.blaze_powder, 2),
					new ItemStack(ModItems.powder_fire, 2),
					new ItemStack(ModItems.ingot_phosphorus),
					new ItemStack(Blocks.netherrack)
			};
		}

		if (item.getItem() == Items.blaze_rod) {
			return new ItemStack[] {
					new ItemStack(Items.blaze_powder, 1),
					new ItemStack(Items.blaze_powder, 1),
					new ItemStack(ModItems.powder_fire, 1),
					new ItemStack(ModItems.powder_fire, 1)
			};
		}

		return null;
	}

	//bro, i don't care
	@SuppressWarnings("incomplete-switch")
	public static List<GasCentOutput> getGasCentOutput(FluidType fluid) {
		
		List<GasCentOutput> list = new ArrayList();
		
		switch(fluid) {
		case LAVA:
			list.add(new GasCentOutput(1, new ItemStack(ModBlocks.gravel_obsidian), 1));
			list.add(new GasCentOutput(2, new ItemStack(Blocks.gravel), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_lithium), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_iron, 2), 4));
			return list;
		case UF6:
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_u238), 1));
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_u238), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.nugget_u235), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.fluorite), 4));
			return list;
		case PUF6:
			list.add(new GasCentOutput(3, new ItemStack(ModItems.nugget_pu238), 1));
			list.add(new GasCentOutput(2, new ItemStack(ModItems.nugget_pu239), 2));
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_pu240), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.fluorite), 4));
			return list;
		case WATZ:
			list.add(new GasCentOutput(1, new ItemStack(ModItems.nugget_schrabidium), 1));
			list.add(new GasCentOutput(3, new ItemStack(ModItems.nugget_uranium), 2));
			list.add(new GasCentOutput(3, new ItemStack(ModItems.powder_iron), 3));
			list.add(new GasCentOutput(3, new ItemStack(ModItems.powder_copper), 4));
			return list;
		case SAS3:
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_schrabidium), 1));
			list.add(new GasCentOutput(4, new ItemStack(ModItems.nugget_schrabidium), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.sulfur), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.sulfur), 4));
			return list;
		case COOLANT:
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 1));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 4));
			return list;
		case CRYOGEL:
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_ice), 1));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_ice), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.niter), 4));
			return list;
		case NITAN:
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_nitan_mix), 1));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_nitan_mix), 2));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_nitan_mix), 3));
			list.add(new GasCentOutput(1, new ItemStack(ModItems.powder_nitan_mix), 4));
			return list;
		}
		
		return null;
	}
	
	public static class GasCentOutput {
		public int weight;
		public ItemStack output;
		public int slot;
		
		public GasCentOutput(int w, ItemStack s, int i) {
			weight = w;
			output = s;
			slot = i;
		}
	}

	@SuppressWarnings("incomplete-switch")
	public static int getFluidConsumedGasCent(FluidType fluid) {
		
		new ArrayList();
		
		switch(fluid) {
		case LAVA:
			return 1000;
		case UF6:
			return 100;
		case PUF6:
			return 100;
		case WATZ:
			return 100;
		case SAS3:
			return 100;
		case COOLANT:
			return 2000;
		case CRYOGEL:
			return 1000;
		case NITAN:
			return 500;
		}
		
		return 100;
	}
	
	private static List<ItemStack> addToListWithWeight(List<ItemStack> list, ItemStack stack, int weight) {
		
		for(int i = 0; i < weight; i++)
			list.add(stack);
		
		return list;
	}
	
	//return: FluidType, amount produced, amount required, heat required (°C * 100)
	@SuppressWarnings("incomplete-switch")
	public static Object[] getBoilerOutput(FluidType type) {
		
		switch(type) {
		case WATER: return new Object[] { FluidType.STEAM, 500, 5, 10000 };
		case STEAM: return new Object[] { FluidType.HOTSTEAM, 5, 50, 30000 };
		case HOTSTEAM: return new Object[] { FluidType.SUPERHOTSTEAM, 5, 50, 45000 };
		case OIL: return new Object[] { FluidType.HOTOIL, 5, 5, 35000 };
		}
		
		return null;
	}
	
	//return: FluidType, amount produced, amount required, HE produced
	@SuppressWarnings("incomplete-switch")
	public static Object[] getTurbineOutput(FluidType type) {
		
		switch(type) {
		case STEAM: return new Object[] { FluidType.WATER, 5, 500, 50 };
		case HOTSTEAM: return new Object[] { FluidType.STEAM, 50, 5, 100 };
		case SUPERHOTSTEAM: return new Object[] { FluidType.HOTSTEAM, 50, 5, 150 };
		}
		
		return null;
	}
	
	public static List<Item> stamps_flat = new ArrayList<Item>() {{
		add(ModItems.stamp_stone_flat);
		add(ModItems.stamp_iron_flat);
		add(ModItems.stamp_steel_flat);
		add(ModItems.stamp_titanium_flat);
		add(ModItems.stamp_obsidian_flat);
		add(ModItems.stamp_schrabidium_flat);
	}};
	
	public static List<Item> stamps_plate = new ArrayList<Item>() {{
		add(ModItems.stamp_stone_plate);
		add(ModItems.stamp_iron_plate);
		add(ModItems.stamp_steel_plate);
		add(ModItems.stamp_titanium_plate);
		add(ModItems.stamp_obsidian_plate);
		add(ModItems.stamp_schrabidium_plate);
	}};
	
	public static List<Item> stamps_wire = new ArrayList<Item>() {{
		add(ModItems.stamp_stone_wire);
		add(ModItems.stamp_iron_wire);
		add(ModItems.stamp_steel_wire);
		add(ModItems.stamp_titanium_wire);
		add(ModItems.stamp_obsidian_wire);
		add(ModItems.stamp_schrabidium_wire);
	}};
	
	public static List<Item> stamps_circuit = new ArrayList<Item>() {{
		add(ModItems.stamp_stone_circuit);
		add(ModItems.stamp_iron_circuit);
		add(ModItems.stamp_steel_circuit);
		add(ModItems.stamp_titanium_circuit);
		add(ModItems.stamp_obsidian_circuit);
		add(ModItems.stamp_schrabidium_circuit);
	}};


	public static ItemStack getPressResultNN(ItemStack stamp, ItemStack input) {
		return getPressResult(input, stamp) == null ? new ItemStack(ModItems.nothing) : getPressResult(input, stamp);
	}

	public static ItemStack getPressResultNN(Item stamp, Item input) {
		return getPressResult(new ItemStack(input), new ItemStack(stamp)) == null ? new ItemStack(ModItems.nothing) : getPressResult(new ItemStack(input), new ItemStack(stamp));
	}
	
	public static ItemStack getPressResult(ItemStack input, ItemStack stamp) {
		
		if(input == null || stamp == null)
			return null;
		
		if(stamps_flat.contains(stamp.getItem())) {

			if(mODE(input, "dustCoal"))
				return new ItemStack(Items.coal);
			if(mODE(input, "dustQuartz"))
				return new ItemStack(Items.quartz);
			if(mODE(input, "dustNetherQuartz"))
				return new ItemStack(Items.quartz);
			if(mODE(input, "dustLapis"))
				return new ItemStack(Items.dye, 1, 4);
			if(mODE(input, "dustDiamond"))
				return new ItemStack(Items.diamond);
			if(mODE(input, "dustEmerald"))
				return new ItemStack(Items.emerald);
			if(input.getItem() == ModItems.pellet_coal)
				return new ItemStack(Items.diamond);
			if(input.getItem() == ModItems.biomass)
				return new ItemStack(ModItems.biomass_compressed);
			if(input.getItem() == ModItems.powder_lignite)
				return new ItemStack(ModItems.briquette_lignite);
		}
		
		if(stamps_plate.contains(stamp.getItem())) {

			if(mODE(input, "ingotIron"))
				return new ItemStack(ModItems.plate_iron);
			if(mODE(input, "ingotGold"))
				return new ItemStack(ModItems.plate_gold);
			if(mODE(input, "ingotTitanium"))
				return new ItemStack(ModItems.plate_titanium);
			if(mODE(input, "ingotAluminum"))
				return new ItemStack(ModItems.plate_aluminium);
			if(mODE(input, "ingotSteel"))
				return new ItemStack(ModItems.plate_steel);
			if(mODE(input, "ingotLead"))
				return new ItemStack(ModItems.plate_lead);
			if(mODE(input, "ingotCopper"))
				return new ItemStack(ModItems.plate_copper);
			if(mODE(input, "ingotAdvanced"))
				return new ItemStack(ModItems.plate_advanced_alloy);
			if(mODE(input, "ingotAdvancedAlloy"))
				return new ItemStack(ModItems.plate_advanced_alloy);
			if(mODE(input, "ingotSchrabidium"))
				return new ItemStack(ModItems.plate_schrabidium);
			if(mODE(input, "ingotCMBSteel"))
				return new ItemStack(ModItems.plate_combine_steel);
			if(mODE(input, "ingotSaturnite"))
				return new ItemStack(ModItems.plate_saturnite);
			
		}
		
		if(stamps_wire.contains(stamp.getItem())) {

			if(mODE(input, "ingotAluminum"))
				return new ItemStack(ModItems.wire_aluminium, 8);
			if(mODE(input, "ingotCopper"))
				return new ItemStack(ModItems.wire_copper, 8);
			if(mODE(input, "ingotTungsten"))
				return new ItemStack(ModItems.wire_tungsten, 8);
			if(mODE(input, "ingotRedAlloy"))
				return new ItemStack(ModItems.wire_red_copper, 8);
			if(mODE(input, "ingotRedstoneAlloy"))
				return new ItemStack(ModItems.wire_red_copper, 8);
			if(mODE(input, "ingotGold"))
				return new ItemStack(ModItems.wire_gold, 8);
			if(mODE(input, "ingotSchrabidium"))
				return new ItemStack(ModItems.wire_schrabidium, 8);
			if(mODE(input, "ingotAdvanced"))
				return new ItemStack(ModItems.wire_advanced_alloy, 8);
			if(mODE(input, "ingotAdvancedAlloy"))
				return new ItemStack(ModItems.wire_advanced_alloy, 8);
			if(mODE(input, "ingotMagnetizedTungsten"))
				return new ItemStack(ModItems.wire_magnetized_tungsten, 8);
		}
		
		if(stamps_circuit.contains(stamp.getItem())) {

			if(input.getItem() == ModItems.circuit_raw)
				return new ItemStack(ModItems.circuit_aluminium);
		}
		
		if(stamp.getItem() == ModItems.stamp_357) {

			if(input.getItem() == ModItems.assembly_iron)
				return new ItemStack(ModItems.gun_revolver_iron_ammo);
			if(input.getItem() == ModItems.assembly_steel)
				return new ItemStack(ModItems.gun_revolver_ammo);
			if(input.getItem() == ModItems.assembly_lead)
				return new ItemStack(ModItems.gun_revolver_lead_ammo);
			if(input.getItem() == ModItems.assembly_gold)
				return new ItemStack(ModItems.gun_revolver_gold_ammo);
			if(input.getItem() == ModItems.assembly_schrabidium)
				return new ItemStack(ModItems.gun_revolver_schrabidium_ammo);
			if(input.getItem() == ModItems.assembly_nightmare)
				return new ItemStack(ModItems.gun_revolver_nightmare_ammo);
			if(input.getItem() == ModItems.assembly_desh)
				return new ItemStack(ModItems.ammo_357_desh);

			if(mODE(input, "ingotSteel"))
				return new ItemStack(ModItems.gun_revolver_cursed_ammo);
		}
		
		if(stamp.getItem() == ModItems.stamp_44) {

			if(input.getItem() == ModItems.assembly_nopip)
				return new ItemStack(ModItems.ammo_44);
		}
		
		if(stamp.getItem() == ModItems.stamp_9) {

			if(input.getItem() == ModItems.assembly_smg)
				return new ItemStack(ModItems.ammo_9mm);
			if(input.getItem() == ModItems.assembly_uzi)
				return new ItemStack(ModItems.ammo_22lr);
			if(mODE(input, "ingotGold"))
				return new ItemStack(ModItems.ammo_566_gold);
			if(input.getItem() == ModItems.assembly_lacunae)
				return new ItemStack(ModItems.ammo_5mm);
			if(input.getItem() == ModItems.assembly_556)
				return new ItemStack(ModItems.ammo_556);
		}
		
		if(stamp.getItem() == ModItems.stamp_50) {

			if(input.getItem() == ModItems.assembly_calamity)
				return new ItemStack(ModItems.ammo_50bmg);
			if(input.getItem() == ModItems.assembly_actionexpress)
				return new ItemStack(ModItems.ammo_50ae);
		}
		
		return null;
	}

	public static Map<Object[], Object> getPressRecipes() {
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();

		List<ItemStack> i_stamps_flat = new ArrayList<ItemStack>();
		for(Item i : stamps_flat)
			i_stamps_flat.add(new ItemStack(i));
		List<ItemStack> i_stamps_plate = new ArrayList<ItemStack>();
		for(Item i : stamps_plate)
			i_stamps_plate.add(new ItemStack(i));
		List<ItemStack> i_stamps_wire = new ArrayList<ItemStack>();
		for(Item i : stamps_wire)
			i_stamps_wire.add(new ItemStack(i));
		List<ItemStack> i_stamps_circuit = new ArrayList<ItemStack>();
		for(Item i : stamps_circuit)
			i_stamps_circuit.add(new ItemStack(i));

		List<ItemStack> i_stamps_357 = new ArrayList<ItemStack>();
		i_stamps_357.add(new ItemStack(ModItems.stamp_357));
		List<ItemStack> i_stamps_44 = new ArrayList<ItemStack>();
		i_stamps_44.add(new ItemStack(ModItems.stamp_44));
		List<ItemStack> i_stamps_9 = new ArrayList<ItemStack>();
		i_stamps_9.add(new ItemStack(ModItems.stamp_9));
		List<ItemStack> i_stamps_50 = new ArrayList<ItemStack>();
		i_stamps_50.add(new ItemStack(ModItems.stamp_50));
		
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_coal) }, getPressResultNN(stamps_flat.get(0), ModItems.powder_coal));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_quartz) }, getPressResultNN(stamps_flat.get(0), ModItems.powder_quartz));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_lapis) }, getPressResultNN(stamps_flat.get(0), ModItems.powder_lapis));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_diamond) }, getPressResultNN(stamps_flat.get(0), ModItems.powder_diamond));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_emerald) }, getPressResultNN(stamps_flat.get(0), ModItems.powder_emerald));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.pellet_coal) }, getPressResultNN(stamps_flat.get(0), ModItems.pellet_coal));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.biomass) }, getPressResultNN(stamps_flat.get(0), ModItems.biomass));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_lignite) }, getPressResultNN(stamps_flat.get(0), ModItems.powder_lignite));

		recipes.put(new Object[] { i_stamps_plate, new ItemStack(Items.iron_ingot) }, getPressResultNN(stamps_plate.get(0), Items.iron_ingot));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(Items.gold_ingot) }, getPressResultNN(stamps_plate.get(0), Items.gold_ingot));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_titanium) }, getPressResultNN(stamps_plate.get(0), ModItems.ingot_titanium));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_aluminium) }, getPressResultNN(stamps_plate.get(0), ModItems.ingot_aluminium));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_steel) }, getPressResultNN(stamps_plate.get(0), ModItems.ingot_steel));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_lead) }, getPressResultNN(stamps_plate.get(0), ModItems.ingot_lead));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_copper) }, getPressResultNN(stamps_plate.get(0), ModItems.ingot_copper));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_advanced_alloy) }, getPressResultNN(stamps_plate.get(0), ModItems.ingot_advanced_alloy));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_schrabidium) }, getPressResultNN(stamps_plate.get(0), ModItems.ingot_schrabidium));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_combine_steel) }, getPressResultNN(stamps_plate.get(0), ModItems.ingot_combine_steel));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_saturnite) }, getPressResultNN(stamps_plate.get(0), ModItems.ingot_saturnite));

		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_aluminium) }, getPressResultNN(stamps_wire.get(0), ModItems.ingot_aluminium));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_copper) }, getPressResultNN(stamps_wire.get(0), ModItems.ingot_copper));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_tungsten) }, getPressResultNN(stamps_wire.get(0), ModItems.ingot_tungsten));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_red_copper) }, getPressResultNN(stamps_wire.get(0), ModItems.ingot_red_copper));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(Items.gold_ingot) }, getPressResultNN(stamps_wire.get(0), Items.gold_ingot));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_schrabidium) }, getPressResultNN(stamps_wire.get(0), ModItems.ingot_schrabidium));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_advanced_alloy) }, getPressResultNN(stamps_wire.get(0), ModItems.ingot_advanced_alloy));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_magnetized_tungsten) }, getPressResultNN(stamps_wire.get(0), ModItems.ingot_magnetized_tungsten));

		recipes.put(new Object[] { i_stamps_circuit, new ItemStack(ModItems.circuit_raw) }, getPressResultNN(stamps_circuit.get(0), ModItems.circuit_raw));

		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_iron) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_iron));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_steel) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_steel));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_lead) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_lead));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_gold) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_gold));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_schrabidium) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_schrabidium));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.ingot_steel) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.ingot_steel));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_nightmare) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_nightmare));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_desh) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_desh));

		recipes.put(new Object[] { i_stamps_44, new ItemStack(ModItems.assembly_nopip) }, getPressResultNN(i_stamps_44.get(0).getItem(), ModItems.assembly_nopip));

		recipes.put(new Object[] { i_stamps_9, new ItemStack(ModItems.assembly_smg) }, getPressResultNN(i_stamps_9.get(0).getItem(), ModItems.assembly_smg));
		recipes.put(new Object[] { i_stamps_9, new ItemStack(ModItems.assembly_uzi) }, getPressResultNN(i_stamps_9.get(0).getItem(), ModItems.assembly_uzi));
		recipes.put(new Object[] { i_stamps_9, new ItemStack(ModItems.assembly_lacunae) }, getPressResultNN(i_stamps_9.get(0).getItem(), ModItems.assembly_lacunae));
		recipes.put(new Object[] { i_stamps_9, new ItemStack(Items.gold_ingot) }, getPressResultNN(i_stamps_9.get(0).getItem(), Items.gold_ingot));
		recipes.put(new Object[] { i_stamps_9, new ItemStack(ModItems.assembly_556) }, getPressResultNN(i_stamps_9.get(0).getItem(), ModItems.assembly_556));
		
		recipes.put(new Object[] { i_stamps_50, new ItemStack(ModItems.assembly_actionexpress) }, getPressResultNN(i_stamps_50.get(0).getItem(), ModItems.assembly_actionexpress));
		recipes.put(new Object[] { i_stamps_50, new ItemStack(ModItems.assembly_calamity) }, getPressResultNN(i_stamps_50.get(0).getItem(), ModItems.assembly_calamity));
		
		return recipes;
	}
	
	public static ItemStack getReactorProcessingResult(Item item) {
		return getReactorOutput(item);
	}

	public static ItemStack getReactorOutput(Item item) {

		if (item == ModItems.rod_th232) {
			return new ItemStack(ModItems.rod_u233, 1);
		}

		if (item == ModItems.rod_uranium) {
			return new ItemStack(ModItems.rod_plutonium, 1);
		}

		if (item == ModItems.rod_u233) {
			return new ItemStack(ModItems.rod_u235, 1);
		}

		if (item == ModItems.rod_u235) {
			return new ItemStack(ModItems.rod_neptunium, 1);
		}

		if (item == ModItems.rod_u238) {
			return new ItemStack(ModItems.rod_pu239, 1);
		}

		if (item == ModItems.rod_neptunium) {
			return new ItemStack(ModItems.rod_pu238, 1);
		}

		if (item == ModItems.rod_plutonium) {
			return new ItemStack(ModItems.rod_waste, 1);
		}

		if (item == ModItems.rod_pu238) {
			return new ItemStack(ModItems.rod_pu239, 1);
		}

		if (item == ModItems.rod_pu239) {
			return new ItemStack(ModItems.rod_pu240, 1);
		}

		if (item == ModItems.rod_pu240) {
			return new ItemStack(ModItems.rod_waste, 1);
		}

		if (item == ModItems.rod_schrabidium) {
			return new ItemStack(ModItems.rod_solinium, 1);
		}

		if (item == ModItems.rod_dual_th232) {
			return new ItemStack(ModItems.rod_dual_u233, 1);
		}

		if (item == ModItems.rod_dual_uranium) {
			return new ItemStack(ModItems.rod_dual_plutonium, 1);
		}

		if (item == ModItems.rod_dual_u233) {
			return new ItemStack(ModItems.rod_dual_u235, 1);
		}

		if (item == ModItems.rod_dual_u235) {
			return new ItemStack(ModItems.rod_dual_neptunium, 1);
		}

		if (item == ModItems.rod_dual_u238) {
			return new ItemStack(ModItems.rod_dual_pu239, 1);
		}

		if (item == ModItems.rod_dual_neptunium) {
			return new ItemStack(ModItems.rod_dual_pu238, 1);
		}

		if (item == ModItems.rod_dual_plutonium) {
			return new ItemStack(ModItems.rod_dual_waste, 1);
		}

		if (item == ModItems.rod_dual_pu238) {
			return new ItemStack(ModItems.rod_dual_pu239, 1);
		}

		if (item == ModItems.rod_dual_pu239) {
			return new ItemStack(ModItems.rod_dual_pu240, 1);
		}

		if (item == ModItems.rod_dual_pu240) {
			return new ItemStack(ModItems.rod_dual_waste, 1);
		}

		if (item == ModItems.rod_dual_schrabidium) {
			return new ItemStack(ModItems.rod_dual_solinium, 1);
		}

		if (item == ModItems.rod_quad_th232) {
			return new ItemStack(ModItems.rod_quad_u233, 1);
		}

		if (item == ModItems.rod_quad_uranium) {
			return new ItemStack(ModItems.rod_quad_plutonium, 1);
		}

		if (item == ModItems.rod_quad_u233) {
			return new ItemStack(ModItems.rod_quad_u235, 1);
		}

		if (item == ModItems.rod_quad_u235) {
			return new ItemStack(ModItems.rod_quad_neptunium, 1);
		}

		if (item == ModItems.rod_quad_u238) {
			return new ItemStack(ModItems.rod_quad_pu239, 1);
		}

		if (item == ModItems.rod_quad_neptunium) {
			return new ItemStack(ModItems.rod_quad_pu238, 1);
		}

		if (item == ModItems.rod_quad_plutonium) {
			return new ItemStack(ModItems.rod_quad_waste, 1);
		}

		if (item == ModItems.rod_quad_pu238) {
			return new ItemStack(ModItems.rod_quad_pu239, 1);
		}

		if (item == ModItems.rod_quad_pu239) {
			return new ItemStack(ModItems.rod_quad_pu240, 1);
		}

		if (item == ModItems.rod_quad_pu240) {
			return new ItemStack(ModItems.rod_quad_waste, 1);
		}

		if (item == ModItems.rod_quad_schrabidium) {
			return new ItemStack(ModItems.rod_quad_solinium, 1);
		}

		if (item == ModItems.rod_lithium) {
			return new ItemStack(ModItems.rod_tritium, 1);
		}

		if (item == ModItems.rod_dual_lithium) {
			return new ItemStack(ModItems.rod_dual_tritium, 1);
		}

		if (item == ModItems.rod_quad_lithium) {
			return new ItemStack(ModItems.rod_quad_tritium, 1);
		}

		if (item == ModItems.rod_quad_solinium) {
			return new ItemStack(ModItems.rod_quad_euphemium, 1);
		}

		if (item == Item.getItemFromBlock(Blocks.stone)) {
			return new ItemStack(ModBlocks.sellafield_slaked, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_slaked)) {
			return new ItemStack(ModBlocks.sellafield_0, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_0)) {
			return new ItemStack(ModBlocks.sellafield_1, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_1)) {
			return new ItemStack(ModBlocks.sellafield_2, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_2)) {
			return new ItemStack(ModBlocks.sellafield_3, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_3)) {
			return new ItemStack(ModBlocks.sellafield_4, 1);
		}

		if (item == Item.getItemFromBlock(ModBlocks.sellafield_4)) {
			return new ItemStack(ModBlocks.sellafield_core, 1);
		}

		return null;
	}

	public static ItemStack getCyclotronOutput(ItemStack part, ItemStack item) {

		if(part == null || item == null)
			return null;
		
		//LITHIUM
		if (part.getItem() == ModItems.part_lithium) {
			if(item.getItem() == ModItems.niter)
				return new ItemStack(ModItems.fluorite, 1);
			if(item.getItem() == ModItems.powder_coal)
				return new ItemStack(ModItems.fluorite, 1);
			if(mODE(item, "dustIron"))
				return new ItemStack(ModItems.powder_cobalt, 1);
			if(mODE(item, "dustGold"))
				return new ItemStack(ModItems.powder_lead, 1);
			if(mODE(item, "dustNetherQuartz"))
				return new ItemStack(ModItems.sulfur, 1);
			if(mODE(item, "dustUranium"))
				return new ItemStack(ModItems.powder_plutonium, 1);
			if(mODE(item, "dustAluminum"))
				return new ItemStack(ModItems.powder_quartz, 1);
			if(mODE(item, "dustBeryllium"))
				return new ItemStack(ModItems.powder_coal, 1);
			if(item.getItem() == ModItems.powder_schrabidium)
				return new ItemStack(ModItems.powder_reiium, 1);
			if(item.getItem() == ModItems.powder_lithium)
				return new ItemStack(ModItems.powder_coal, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_caesium, 1);
			if(item.getItem() == ModItems.powder_thorium)
				return new ItemStack(ModItems.powder_uranium, 1);
			if(item.getItem() == ModItems.powder_caesium)
				return new ItemStack(ModItems.powder_lanthanium, 1);
			if(item.getItem() == ModItems.powder_reiium)
				return new ItemStack(ModItems.powder_weidanium, 1);
			if(mODE(item, "dustCobalt"))
				return new ItemStack(ModItems.powder_copper, 1);
			if(item.getItem() == ModItems.powder_cerium)
				return new ItemStack(ModItems.powder_neodymium, 1);
			if(item.getItem() == ModItems.powder_actinium)
				return new ItemStack(ModItems.powder_thorium, 1);
			if(item.getItem() == ModItems.powder_lanthanium)
				return new ItemStack(ModItems.powder_cerium, 1);
		}
		
		//BERYLLIUM
		if (part.getItem() == ModItems.part_beryllium) {
			if(mODE(item, "dustSulfur"))
				return new ItemStack(ModItems.powder_titanium, 1);
			if(item.getItem() == ModItems.fluorite)
				return new ItemStack(ModItems.powder_aluminium, 1);
			if(mODE(item, "dustIron"))
				return new ItemStack(ModItems.powder_copper, 1);
			if(mODE(item, "dustNetherQuartz"))
				return new ItemStack(ModItems.powder_titanium, 1);
			if(mODE(item, "dustTitanium"))
				return new ItemStack(ModItems.powder_iron, 1);
			if(mODE(item, "dustCopper"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustTungsten"))
				return new ItemStack(ModItems.powder_gold, 1);
			if(mODE(item, "dustAluminum"))
				return new ItemStack(ModItems.sulfur, 1);
			if(mODE(item, "dustLead"))
				return new ItemStack(ModItems.powder_astatine, 1);
			if(mODE(item, "dustBeryllium"))
				return new ItemStack(ModItems.niter, 1);
			if(mODE(item, "dustLithium"))
				return new ItemStack(ModItems.niter, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_cerium, 1);
			if(item.getItem() == ModItems.powder_thorium)
				return new ItemStack(ModItems.powder_neptunium, 1);
			if(item.getItem() == ModItems.powder_astatine)
				return new ItemStack(ModItems.powder_actinium, 1);
			if(item.getItem() == ModItems.powder_caesium)
				return new ItemStack(ModItems.powder_neodymium, 1);
			if(item.getItem() == ModItems.powder_weidanium)
				return new ItemStack(ModItems.powder_australium, 1);
			if(item.getItem() == ModItems.powder_strontium)
				return new ItemStack(ModItems.powder_niobium, 1);
			if(item.getItem() == ModItems.powder_bromine)
				return new ItemStack(ModItems.powder_strontium, 1);
			if(item.getItem() == ModItems.powder_actinium)
				return new ItemStack(ModItems.powder_uranium, 1);
			if(item.getItem() == ModItems.powder_lanthanium)
				return new ItemStack(ModItems.powder_neodymium, 1);
		}
		
		//CARBON
		if (part.getItem() == ModItems.part_carbon) {
			if(mODE(item, "dustSulfur"))
				return new ItemStack(ModItems.powder_iron, 1);
			if(item.getItem() == ModItems.niter)
				return new ItemStack(ModItems.powder_aluminium, 1);
			if(item.getItem() == ModItems.fluorite)
				return new ItemStack(ModItems.sulfur, 1);
			if(mODE(item, "dustCoal"))
				return new ItemStack(ModItems.powder_aluminium, 1);
			if(mODE(item, "dustIron"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustGold"))
				return new ItemStack(ModItems.powder_astatine, 1);
			if(mODE(item, "dustNetherQuartz"))
				return new ItemStack(ModItems.powder_iron, 1);
			if(item.getItem() == ModItems.powder_plutonium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_neptunium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(mODE(item, "dustTitanium"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustCopper"))
				return new ItemStack(ModItems.powder_strontium, 1);
			if(mODE(item, "dustTungsten"))
				return new ItemStack(ModItems.powder_lead, 1);
			if(mODE(item, "dustAluminum"))
				return new ItemStack(ModItems.powder_titanium, 1);
			if(mODE(item, "dustLead"))
				return new ItemStack(ModItems.powder_thorium, 1);
			if(mODE(item, "dustBeryllium"))
				return new ItemStack(ModItems.fluorite, 1);
			if(mODE(item, "dustLithium"))
				return new ItemStack(ModItems.fluorite, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_tungsten, 1);
			if(item.getItem() == ModItems.powder_neodymium)
				return new ItemStack(ModItems.powder_tungsten, 1);
			if(item.getItem() == ModItems.powder_australium)
				return new ItemStack(ModItems.powder_verticium, 1);
			if(item.getItem() == ModItems.powder_strontium)
				return new ItemStack(ModItems.powder_iodine, 1);
			if(mODE(item, "dustCobalt"))
				return new ItemStack(ModItems.powder_strontium, 1);
			if(item.getItem() == ModItems.powder_bromine)
				return new ItemStack(ModItems.powder_niobium, 1);
			if(item.getItem() == ModItems.powder_niobium)
				return new ItemStack(ModItems.powder_iodine, 1);
			if(item.getItem() == ModItems.powder_tennessine)
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_cerium)
				return new ItemStack(ModItems.powder_tungsten, 1);
		}
		
		//COPPER
		if (part.getItem() == ModItems.part_copper) {
			if(mODE(item, "dustSulfur"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.niter)
				return new ItemStack(ModItems.powder_cobalt, 1);
			if(item.getItem() == ModItems.fluorite)
				return new ItemStack(ModItems.powder_iron, 1);
			if(mODE(item, "dustCoal"))
				return new ItemStack(ModItems.powder_iron, 1);
			if(mODE(item, "dustIron"))
				return new ItemStack(ModItems.powder_niobium, 1);
			if(mODE(item, "dustGold"))
				return new ItemStack(ModItems.powder_lanthanium, 1);
			if(mODE(item, "dustNetherQuartz"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustUranium"))
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(mODE(item, "dustTitanium"))
				return new ItemStack(ModItems.powder_strontium, 1);
			if(mODE(item, "dustCopper"))
				return new ItemStack(ModItems.powder_niobium, 1);
			if(mODE(item, "dustTungsten"))
				return new ItemStack(ModItems.powder_actinium, 1);
			if(mODE(item, "dustAluminum"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustLead"))
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(mODE(item, "dustBeryllium"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(mODE(item, "dustLithium"))
				return new ItemStack(ModItems.powder_bromine, 1);
			if(item.getItem() == ModItems.powder_iodine)
				return new ItemStack(ModItems.powder_astatine, 1);
			if(item.getItem() == ModItems.powder_thorium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_neodymium)
				return new ItemStack(ModItems.powder_lead, 1);
			if(item.getItem() == ModItems.powder_astatine)
				return new ItemStack(ModItems.powder_plutonium, 1);
			if(item.getItem() == ModItems.powder_caesium)
				return new ItemStack(ModItems.powder_tungsten, 1);
			if(item.getItem() == ModItems.powder_verticium)
				return new ItemStack(ModItems.powder_unobtainium, 1);
			if(mODE(item, "dustCobalt"))
				return new ItemStack(ModItems.powder_iodine, 1);
			if(item.getItem() == ModItems.powder_bromine)
				return new ItemStack(ModItems.powder_caesium, 1);
			if(item.getItem() == ModItems.powder_niobium)
				return new ItemStack(ModItems.powder_cerium, 1);
			if(item.getItem() == ModItems.powder_tennessine)
				return new ItemStack(ModItems.powder_reiium, 1);
			if(item.getItem() == ModItems.powder_cerium)
				return new ItemStack(ModItems.powder_lead, 1);
			if(item.getItem() == ModItems.powder_actinium)
				return new ItemStack(ModItems.powder_tennessine, 1);
			if(item.getItem() == ModItems.powder_lanthanium)
				return new ItemStack(ModItems.powder_astatine, 1);
		}
		
		//PLUTONIUM
		if (part.getItem() == ModItems.part_plutonium) {
			if(mODE(item, "dustUranium"))
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_plutonium)
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_neptunium)
				return new ItemStack(ModItems.powder_schrabidium, 1);
			if(item.getItem() == ModItems.powder_unobtainium)
				return new ItemStack(ModItems.powder_daffergon, 1);
			if(item.getItem() == ModItems.cell_antimatter)
				return new ItemStack(ModItems.cell_anti_schrabidium, 1);
		}

		return null;
	}

	public Map<Object[], Object> getAlloyRecipes() {
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		
		if (MainRegistry.enableDebugMode) {
			recipes.put(new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Items.quartz) },
					new ItemStack(Item.getItemFromBlock(ModBlocks.test_render)));
		}
		try {
			recipes.put(new ItemStack[] { new ItemStack(Items.iron_ingot), new ItemStack(Items.coal) },
				getFurnaceOutput(new ItemStack(Items.iron_ingot), new ItemStack(Items.coal)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_lead), new ItemStack(ModItems.ingot_copper) },
					getFurnaceOutput(new ItemStack(ModItems.ingot_lead), new ItemStack(ModItems.ingot_copper)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.plate_lead), new ItemStack(ModItems.plate_copper) },
					getFurnaceOutput(new ItemStack(ModItems.plate_lead), new ItemStack(ModItems.plate_copper)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_tungsten), new ItemStack(Items.coal) },
					getFurnaceOutput(new ItemStack(ModItems.ingot_tungsten), new ItemStack(Items.coal)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_copper), new ItemStack(Items.redstone) },
					getFurnaceOutput(new ItemStack(ModItems.ingot_copper), new ItemStack(Items.redstone)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_red_copper), new ItemStack(ModItems.ingot_steel) },
					getFurnaceOutput(new ItemStack(ModItems.ingot_red_copper), new ItemStack(ModItems.ingot_steel)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.canister_fuel), new ItemStack(Items.slime_ball) },
					getFurnaceOutput(new ItemStack(ModItems.canister_fuel), new ItemStack(Items.slime_ball)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_tungsten), new ItemStack(ModItems.nugget_schrabidium) },
					getFurnaceOutput(new ItemStack(ModItems.ingot_tungsten), new ItemStack(ModItems.nugget_schrabidium)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.plate_mixed), new ItemStack(ModItems.plate_gold) },
					getFurnaceOutput(new ItemStack(ModItems.plate_mixed), new ItemStack(ModItems.plate_gold)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_steel), new ItemStack(ModItems.ingot_tungsten) },
					getFurnaceOutput(new ItemStack(ModItems.ingot_steel), new ItemStack(ModItems.ingot_tungsten)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_steel), new ItemStack(ModItems.ingot_cobalt) },
					getFurnaceOutput(new ItemStack(ModItems.ingot_steel), new ItemStack(ModItems.ingot_cobalt)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_saturnite), new ItemStack(ModItems.powder_meteorite) },
					getFurnaceOutput(new ItemStack(ModItems.ingot_saturnite), new ItemStack(ModItems.powder_meteorite)).copy());
		} catch (Exception x) {
			MainRegistry.logger.error("Unable to register alloy recipes for NEI!");
		}
		return recipes;
	}

	public ArrayList<ItemStack> getAlloyFuels() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(Items.coal));
		fuels.add(new ItemStack(Blocks.coal_block));
		fuels.add(new ItemStack(Items.lava_bucket));
		fuels.add(new ItemStack(Items.blaze_rod));
		fuels.add(new ItemStack(Items.blaze_powder));
		fuels.add(new ItemStack(ModItems.lignite));
		fuels.add(new ItemStack(ModItems.powder_lignite));
		fuels.add(new ItemStack(ModItems.briquette_lignite));
		fuels.add(new ItemStack(ModItems.coke));
		fuels.add(new ItemStack(ModItems.solid_fuel));
		fuels.add(new ItemStack(ModItems.powder_coal));
		return fuels;
	}

	public Map<Object, Object[]> getCentrifugeRecipes() {
		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();
		if (MainRegistry.enableDebugMode) {
			recipes.put(new ItemStack(Item.getItemFromBlock(ModBlocks.test_render)),
					getCentrifugeOutput(new ItemStack(ModBlocks.test_render)));
		}
		recipes.put(new ItemStack(ModItems.powder_cloud),
				getCentrifugeOutput(new ItemStack(ModItems.powder_cloud)));
		recipes.put(new ItemStack(Blocks.coal_ore),
				getCentrifugeOutput(new ItemStack(Blocks.coal_ore)));
		recipes.put(new ItemStack(Blocks.iron_ore),
				getCentrifugeOutput(new ItemStack(Blocks.iron_ore)));
		recipes.put(new ItemStack(Blocks.gold_ore),
				getCentrifugeOutput(new ItemStack(Blocks.gold_ore)));
		recipes.put(new ItemStack(Blocks.diamond_ore),
				getCentrifugeOutput(new ItemStack(Blocks.diamond_ore)));
		recipes.put(new ItemStack(Blocks.emerald_ore),
				getCentrifugeOutput(new ItemStack(Blocks.emerald_ore)));
		recipes.put(new ItemStack(ModBlocks.ore_uranium),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_uranium)));
		recipes.put(new ItemStack(ModBlocks.ore_thorium),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_thorium)));
		recipes.put(new ItemStack(ModBlocks.ore_titanium),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_titanium)));
		recipes.put(new ItemStack(ModBlocks.ore_tungsten),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_tungsten)));
		recipes.put(new ItemStack(ModBlocks.ore_aluminium),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_aluminium)));
		recipes.put(new ItemStack(ModBlocks.ore_beryllium),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_beryllium)));
		recipes.put(new ItemStack(ModBlocks.ore_copper),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_copper)));
		recipes.put(new ItemStack(ModBlocks.ore_lead),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_lead)));
		recipes.put(new ItemStack(ModBlocks.ore_rare),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_rare)));
		recipes.put(new ItemStack(ModBlocks.ore_schrabidium),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_schrabidium)));
		recipes.put(new ItemStack(ModBlocks.ore_tikite),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_tikite)));
		recipes.put(new ItemStack(Blocks.redstone_ore),
				getCentrifugeOutput(new ItemStack(Blocks.redstone_ore)));
		recipes.put(new ItemStack(Blocks.lapis_ore),
				getCentrifugeOutput(new ItemStack(Blocks.lapis_ore)));
		recipes.put(new ItemStack(ModBlocks.ore_nether_plutonium),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_nether_plutonium)));
		recipes.put(new ItemStack(ModItems.waste_uranium),
				getCentrifugeOutput(new ItemStack(ModItems.waste_uranium)));
		recipes.put(new ItemStack(ModItems.waste_thorium),
				getCentrifugeOutput(new ItemStack(ModItems.waste_thorium)));
		recipes.put(new ItemStack(ModItems.waste_plutonium),
				getCentrifugeOutput(new ItemStack(ModItems.waste_plutonium)));
		recipes.put(new ItemStack(ModItems.waste_mox),
				getCentrifugeOutput(new ItemStack(ModItems.waste_mox)));
		recipes.put(new ItemStack(ModItems.waste_schrabidium),
				getCentrifugeOutput(new ItemStack(ModItems.waste_schrabidium)));
		recipes.put(new ItemStack(ModBlocks.ore_lignite),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_lignite)));
		recipes.put(new ItemStack(ModBlocks.ore_meteor_starmetal),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_meteor_starmetal)));
		recipes.put(new ItemStack(ModBlocks.block_euphemium_cluster),
				getCentrifugeOutput(new ItemStack(ModBlocks.block_euphemium_cluster)));
		recipes.put(new ItemStack(ModBlocks.ore_nether_fire),
				getCentrifugeOutput(new ItemStack(ModBlocks.ore_nether_fire)));
		recipes.put(new ItemStack(Items.blaze_rod),
				getCentrifugeOutput(new ItemStack(Items.blaze_rod)));
		return recipes;
	}

	public Map<Object, Object[]> getGasCentrifugeRecipes() {
		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();

		for(int i = 0; i < FluidType.values().length; i++) {
			
			if(getGasCentOutput(FluidType.getEnum(i)) != null) {
				
				List<GasCentOutput> outputs = getGasCentOutput(FluidType.getEnum(i));
				
				int totalWeight = 0;
				
				for(GasCentOutput o : outputs) {
					totalWeight += o.weight;
				}
				
				ItemStack input = new ItemStack(ModItems.fluid_icon, 1, i);
				ItemFluidIcon.addQuantity(input, getFluidConsumedGasCent(FluidType.getEnum(i)) * totalWeight);

				ItemStack[] out = new ItemStack[4];
				
				for(int j = 0; j < outputs.size(); j++) {
					
					out[j] = outputs.get(j).output.copy();
					out[j].stackSize *= outputs.get(j).weight;
				}
				
				for(int j = 0; j < 4; j++)
					if(out[j] == null)
						out[j] = new ItemStack(ModItems.nothing);
				
				recipes.put(input, out);
			}
		}
		
		return recipes;
	}

	public ArrayList<ItemStack> getCentrifugeFuels() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(Items.coal));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.coal_block)));
		fuels.add(new ItemStack(Items.lava_bucket));
		fuels.add(new ItemStack(Items.redstone));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.redstone_block)));
		fuels.add(new ItemStack(Item.getItemFromBlock(Blocks.netherrack)));
		fuels.add(new ItemStack(Items.blaze_rod));
		fuels.add(new ItemStack(Items.blaze_powder));
		return fuels;
	}

	public Map<Object, Object> getReactorRecipes() {
		Map<Object, Object> recipes = new HashMap<Object, Object>();
		recipes.put(new ItemStack(ModItems.rod_uranium), getReactorOutput(ModItems.rod_uranium));
		recipes.put(new ItemStack(ModItems.rod_dual_uranium), getReactorOutput(ModItems.rod_dual_uranium));
		recipes.put(new ItemStack(ModItems.rod_quad_uranium), getReactorOutput(ModItems.rod_quad_uranium));
		recipes.put(new ItemStack(ModItems.rod_u235), getReactorOutput(ModItems.rod_u235));
		recipes.put(new ItemStack(ModItems.rod_dual_u235), getReactorOutput(ModItems.rod_dual_u235));
		recipes.put(new ItemStack(ModItems.rod_quad_u235), getReactorOutput(ModItems.rod_quad_u235));
		recipes.put(new ItemStack(ModItems.rod_u238), getReactorOutput(ModItems.rod_u238));
		recipes.put(new ItemStack(ModItems.rod_dual_u238), getReactorOutput(ModItems.rod_dual_u238));
		recipes.put(new ItemStack(ModItems.rod_quad_u238), getReactorOutput(ModItems.rod_quad_u238));
		recipes.put(new ItemStack(ModItems.rod_plutonium), getReactorOutput(ModItems.rod_plutonium));
		recipes.put(new ItemStack(ModItems.rod_dual_plutonium), getReactorOutput(ModItems.rod_dual_plutonium));
		recipes.put(new ItemStack(ModItems.rod_quad_plutonium), getReactorOutput(ModItems.rod_quad_plutonium));
		recipes.put(new ItemStack(ModItems.rod_pu238), getReactorOutput(ModItems.rod_pu238));
		recipes.put(new ItemStack(ModItems.rod_dual_pu238), getReactorOutput(ModItems.rod_dual_pu238));
		recipes.put(new ItemStack(ModItems.rod_quad_pu238), getReactorOutput(ModItems.rod_quad_pu238));
		recipes.put(new ItemStack(ModItems.rod_pu239), getReactorOutput(ModItems.rod_pu239));
		recipes.put(new ItemStack(ModItems.rod_dual_pu239), getReactorOutput(ModItems.rod_dual_pu239));
		recipes.put(new ItemStack(ModItems.rod_quad_pu239), getReactorOutput(ModItems.rod_quad_pu239));
		recipes.put(new ItemStack(ModItems.rod_pu240), getReactorOutput(ModItems.rod_pu240));
		recipes.put(new ItemStack(ModItems.rod_dual_pu240), getReactorOutput(ModItems.rod_dual_pu240));
		recipes.put(new ItemStack(ModItems.rod_quad_pu240), getReactorOutput(ModItems.rod_quad_pu240));
		recipes.put(new ItemStack(ModItems.rod_neptunium), getReactorOutput(ModItems.rod_neptunium));
		recipes.put(new ItemStack(ModItems.rod_dual_neptunium), getReactorOutput(ModItems.rod_dual_neptunium));
		recipes.put(new ItemStack(ModItems.rod_quad_neptunium), getReactorOutput(ModItems.rod_quad_neptunium));
		recipes.put(new ItemStack(ModItems.rod_schrabidium), getReactorOutput(ModItems.rod_schrabidium));
		recipes.put(new ItemStack(ModItems.rod_dual_schrabidium), getReactorOutput(ModItems.rod_dual_schrabidium));
		recipes.put(new ItemStack(ModItems.rod_quad_schrabidium), getReactorOutput(ModItems.rod_quad_schrabidium));
		recipes.put(new ItemStack(ModItems.rod_quad_solinium), getReactorOutput(ModItems.rod_quad_solinium));
		recipes.put(new ItemStack(ModItems.rod_lithium), getReactorOutput(ModItems.rod_lithium));
		recipes.put(new ItemStack(ModItems.rod_dual_lithium), getReactorOutput(ModItems.rod_dual_lithium));
		recipes.put(new ItemStack(ModItems.rod_quad_lithium), getReactorOutput(ModItems.rod_quad_lithium));
		recipes.put(new ItemStack(Blocks.stone), getReactorOutput(Item.getItemFromBlock(Blocks.stone)));
		recipes.put(new ItemStack(ModBlocks.sellafield_slaked), getReactorOutput(Item.getItemFromBlock(ModBlocks.sellafield_slaked)));
		recipes.put(new ItemStack(ModBlocks.sellafield_0), getReactorOutput(Item.getItemFromBlock(ModBlocks.sellafield_0)));
		recipes.put(new ItemStack(ModBlocks.sellafield_1), getReactorOutput(Item.getItemFromBlock(ModBlocks.sellafield_1)));
		recipes.put(new ItemStack(ModBlocks.sellafield_2), getReactorOutput(Item.getItemFromBlock(ModBlocks.sellafield_2)));
		recipes.put(new ItemStack(ModBlocks.sellafield_3), getReactorOutput(Item.getItemFromBlock(ModBlocks.sellafield_3)));
		recipes.put(new ItemStack(ModBlocks.sellafield_4), getReactorOutput(Item.getItemFromBlock(ModBlocks.sellafield_4)));
		recipes.put(new ItemStack(ModItems.rod_th232), getReactorOutput(ModItems.rod_th232));
		recipes.put(new ItemStack(ModItems.rod_dual_th232), getReactorOutput(ModItems.rod_dual_th232));
		recipes.put(new ItemStack(ModItems.rod_quad_th232), getReactorOutput(ModItems.rod_quad_th232));
		recipes.put(new ItemStack(ModItems.rod_u233), getReactorOutput(ModItems.rod_u233));
		recipes.put(new ItemStack(ModItems.rod_dual_u233), getReactorOutput(ModItems.rod_dual_u233));
		recipes.put(new ItemStack(ModItems.rod_quad_u233), getReactorOutput(ModItems.rod_quad_u233));
		return recipes;
	}
	
	public Map<Object[], Object> getCyclotronRecipes() {
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		Item part = ModItems.part_lithium;
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.niter) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.niter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_coal) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_coal)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_gold) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_gold)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_uranium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_uranium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_schrabidium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_schrabidium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_thorium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_thorium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_caesium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_caesium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_reiium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_reiium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cobalt) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cobalt)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cerium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cerium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_actinium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_actinium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lanthanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lanthanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				new ItemStack(ModItems.cell_antimatter));

		part = ModItems.part_beryllium;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.sulfur) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.sulfur)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.fluorite) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.fluorite)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_titanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_titanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_copper) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_copper)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tungsten) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tungsten)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lead) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lead)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_thorium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_thorium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_astatine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_astatine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_caesium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_caesium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_weidanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_weidanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_strontium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_strontium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_bromine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_bromine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_actinium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_actinium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lanthanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lanthanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				new ItemStack(ModItems.cell_antimatter));
		
		part = ModItems.part_carbon;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.sulfur) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.sulfur)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.niter) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.niter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.fluorite) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.fluorite)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_coal) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_coal)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_gold) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_gold)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_plutonium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_plutonium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neptunium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neptunium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_titanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_titanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_copper) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_copper)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tungsten) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tungsten)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lead) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lead)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neodymium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neodymium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_australium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_australium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_strontium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_strontium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cobalt) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cobalt)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_bromine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_bromine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_niobium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_niobium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tennessine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tennessine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cerium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cerium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				new ItemStack(ModItems.cell_antimatter));
		
		part = ModItems.part_copper;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.sulfur) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.sulfur)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.niter) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.niter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.fluorite) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.fluorite)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_coal) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_coal)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_gold) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_gold)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_uranium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_uranium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_titanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_titanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_copper) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_copper)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tungsten) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tungsten)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lead) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lead)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_thorium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_thorium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neodymium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neodymium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_astatine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_astatine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_caesium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_caesium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_verticium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_verticium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cobalt) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cobalt)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_bromine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_bromine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_niobium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_niobium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tennessine) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tennessine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cerium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cerium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_actinium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_actinium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lanthanium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lanthanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				new ItemStack(ModItems.cell_antimatter));
		
		part = ModItems.part_plutonium;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_uranium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_uranium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_plutonium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_plutonium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neptunium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neptunium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_unobtainium) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_unobtainium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.cell_antimatter) },
				getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.cell_antimatter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				new ItemStack(ModItems.cell_antimatter));
		
		return recipes;
	}

	public ArrayList<ItemStack> getReactorFuels() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(ModItems.rod_u238));
		fuels.add(new ItemStack(ModItems.rod_dual_u238));
		fuels.add(new ItemStack(ModItems.rod_quad_u238));
		fuels.add(new ItemStack(ModItems.rod_u235));
		fuels.add(new ItemStack(ModItems.rod_dual_u235));
		fuels.add(new ItemStack(ModItems.rod_quad_u235));
		fuels.add(new ItemStack(ModItems.rod_pu238));
		fuels.add(new ItemStack(ModItems.rod_dual_pu238));
		fuels.add(new ItemStack(ModItems.rod_quad_pu238));
		fuels.add(new ItemStack(ModItems.rod_pu239));
		fuels.add(new ItemStack(ModItems.rod_dual_pu239));
		fuels.add(new ItemStack(ModItems.rod_quad_pu239));
		fuels.add(new ItemStack(ModItems.rod_pu240));
		fuels.add(new ItemStack(ModItems.rod_dual_pu240));
		fuels.add(new ItemStack(ModItems.rod_quad_pu240));
		fuels.add(new ItemStack(ModItems.rod_neptunium));
		fuels.add(new ItemStack(ModItems.rod_dual_neptunium));
		fuels.add(new ItemStack(ModItems.rod_quad_neptunium));
		fuels.add(new ItemStack(ModItems.rod_schrabidium));
		fuels.add(new ItemStack(ModItems.rod_dual_schrabidium));
		fuels.add(new ItemStack(ModItems.rod_quad_schrabidium));
		fuels.add(new ItemStack(ModItems.rod_solinium));
		fuels.add(new ItemStack(ModItems.rod_dual_solinium));
		fuels.add(new ItemStack(ModItems.rod_quad_solinium));
		fuels.add(new ItemStack(ModItems.pellet_rtg));
		return fuels;
	}

	//keep this
	//like in a museum or something
	//this is a testament of my incompetence
	//look at it
	//look at how horrifying it is
	//children, never do this
	/*public class ShredderRecipe {

		public ItemStack input;
		public ItemStack output;

		public void registerEverythingImSrs() {
			
			String[] names = OreDictionary.getOreNames();
			List<ItemStack> stacks = new ArrayList<ItemStack>();
			
			for(int i = 0; i < names.length; i++) {
				stacks.addAll(OreDictionary.getOres(names[i]));
			}
			
			for(int i = 0; i < stacks.size(); i++) {
				
				int[] ids = OreDictionary.getOreIDs(stacks.get(i));

				List<String> oreNames = new ArrayList<String>();
				
				for(int j = 0; j < ids.length; j++) {
					oreNames.add(OreDictionary.getOreName(ids[j]));
				}
				
				theWholeThing.add(new DictCouple(stacks.get(i), oreNames));
			}
			
			MainRegistry.logger.info("Added " + theWholeThing.size() + " elements from the Ore Dict!");
		}
		
		public boolean doesExist(ItemStack stack) {
			
			for(DictCouple dic : theWholeThing) {
				if(dic.item.getItem() == stack.getItem() && dic.item.getItemDamage() == stack.getItemDamage())
					return true;
			}
			
			return false;
		}

		public void addRecipes() {

			// Not very efficient, I know, but at least it works AND it's
			// somewhat smart!
			
			for(int i = 0; i < theWholeThing.size(); i++)
			{
				for(int j = 0; j < theWholeThing.get(i).list.size(); j++)
				{
					String s = theWholeThing.get(i).list.get(j);
					
					if (s.length() > 5 && s.substring(0, 5).equals("ingot")) {
						ItemStack stack = canFindDustByName(s.substring(5));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, stack);
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("ore")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 2, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 5 && s.substring(0, 5).equals("block")) {
						ItemStack stack = canFindDustByName(s.substring(5));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 9, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 3 && s.substring(0, 3).equals("gem")) {
						ItemStack stack = canFindDustByName(s.substring(3));
						if (stack != null) {
							setRecipe(theWholeThing.get(i).item, new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
						} else {
							setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
						}
					} else if (s.length() > 4 && s.substring(0, 4).equals("dust")) {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.dust));
					} else if (s.length() > 6 && s.substring(0, 6).equals("powder")) {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.dust));
					} else {
						setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
					}
				}

				if(theWholeThing.get(i).list.isEmpty())
					setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
				if(!theWholeThing.get(i).list.isEmpty() && theWholeThing.get(i).list.get(0).equals("Unknown"))
					setRecipe(theWholeThing.get(i).item, new ItemStack(ModItems.scrap));
			}

			MainRegistry.logger.info("Added " + recipesShredder.size() + " in total.");
			MainRegistry.logger.info("Added " + dustCount + " ore dust recipes.");
		}
		
		public ItemStack canFindDustByName(String s) {
			
			for(DictCouple d : theWholeThing)
			{
				for(String s1 : d.list)
				{
					if(s1.length() > 4 && s1.substring(0, 4).equals("dust") && s1.substring(4).equals(s))
					{
						dustCount++;
						return d.item;
					}
				}
			}
			
			return null;
		}
		
		public void setRecipe(ItemStack inp, ItemStack outp) {
			ShredderRecipe recipe = new ShredderRecipe();
			
			recipe.input = inp;
			recipe.output = outp;
			
			recipesShredder.add(recipe);
		}
		
		public void overridePreSetRecipe(ItemStack inp, ItemStack outp) {
			
			boolean flag = false;
			
			for(int i = 0; i < recipesShredder.size(); i++)
			{
				if(recipesShredder.get(i) != null && 
						recipesShredder.get(i).input != null && 
						recipesShredder.get(i).output != null && 
						inp != null && 
						outp != null && 
						recipesShredder.get(i).input.getItem() == inp.getItem() && 
						recipesShredder.get(i).input.getItemDamage() == inp.getItemDamage()) {
					recipesShredder.get(i).output = outp;
					flag = true;
				}
			}
			
			if(!flag) {
				ShredderRecipe rec = new ShredderRecipe();
				rec.input = inp;
				rec.output = outp;
				recipesShredder.add(rec);
			}
		}
		
		public void removeDuplicates() {
			List<ShredderRecipe> newList = new ArrayList<ShredderRecipe>();
			
			for(ShredderRecipe piv : recipesShredder)
			{
				boolean flag = false;
				
				if(newList.size() == 0)
				{
					newList.add(piv);
				} else {
					for(ShredderRecipe rec : newList) {
						if(piv != null && rec != null && piv.input != null && rec.input != null && rec.input.getItem() != null && piv.input.getItem() != null && rec.input.getItemDamage() == piv.input.getItemDamage() && rec.input.getItem() == piv.input.getItem())
							flag = true;
						if(piv == null || rec == null || piv.input == null || rec.input == null)
							flag = true;
					}
				}
				
				if(!flag)
				{
					newList.add(piv);
				}
			}
		}
		
		public void PrintRecipes() {

			MainRegistry.logger.debug("TWT: " + theWholeThing.size() + ", REC: " + recipesShredder.size());
		}
	}

	public static class DictCouple {
		
		public ItemStack item;
		public List<String> list;

		public DictCouple(ItemStack item, List<String> list) {
			this.item = item;
			this.list = list;
		}
		
		public static List<String> findWithStack(ItemStack stack) {
			for(DictCouple couple : theWholeThing) {
				if(couple.item == stack);
					return couple.list;
			}
			
			return null;
		}
	}

	public static List<ShredderRecipe> recipesShredder = new ArrayList<ShredderRecipe>();
	public static List<DictCouple> theWholeThing = new ArrayList<DictCouple>();
	public static int dustCount = 0;
	
	public static ItemStack getShredderResult(ItemStack stack) {
		for(ShredderRecipe rec : recipesShredder)
		{
			if(stack != null && 
					rec.input.getItem() == stack.getItem() && 
					rec.input.getItemDamage() == stack.getItemDamage())
				return rec.output.copy();
		}
		
		return new ItemStack(ModItems.scrap);
	}
	
	public Map<Object, Object> getShredderRecipes() {
		Map<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(int i = 0; i < MachineRecipes.recipesShredder.size(); i++) {
			if(MachineRecipes.recipesShredder.get(i) != null && MachineRecipes.recipesShredder.get(i).output.getItem() != ModItems.scrap)
				recipes.put(MachineRecipes.recipesShredder.get(i).input, getShredderResult(MachineRecipes.recipesShredder.get(i).input));
		}
		
		return recipes;
	}*/
	
	//new and improved
	public static HashMap<StackWrapper, ItemStack> shredderRecipes = new HashMap();
	public static HashMap<Object, Object> neiShredderRecipes;
	
	public static void registerShredder() {
		
		String[] names = OreDictionary.getOreNames();
		
		for(int i = 0; i < names.length; i++) {
			
			String name = names[i];
			
			//if the dict contains invalid names, skip
			if(name == null || name.isEmpty())
				continue;
			
			List<ItemStack> matches = OreDictionary.getOres(name);
			
			//if the name isn't assigned to an ore, also skip
			if(matches == null || matches.isEmpty())
				continue;

			if(name.length() > 5 && name.substring(0, 5).equals("ingot")) {
				ItemStack dust = getDustByName(name.substring(5));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {

					for(ItemStack stack : matches) {
						shredderRecipes.put(new StackWrapper(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 3).equals("ore")) {
				ItemStack dust = getDustByName(name.substring(3));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {
					
					dust.stackSize = 2;

					for(ItemStack stack : matches) {
						shredderRecipes.put(new StackWrapper(stack), dust);
					}
				}
			} else if(name.length() > 5 && name.substring(0, 5).equals("block")) {
				ItemStack dust = getDustByName(name.substring(5));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {
					
					dust.stackSize = 9;

					for(ItemStack stack : matches) {
						shredderRecipes.put(new StackWrapper(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 3).equals("gem")) {
				ItemStack dust = getDustByName(name.substring(3));
				
				if(dust != null && dust.getItem() != ModItems.scrap) {

					for(ItemStack stack : matches) {
						shredderRecipes.put(new StackWrapper(stack), dust);
					}
				}
			} else if(name.length() > 3 && name.substring(0, 4).equals("dust")) {

				for(ItemStack stack : matches) {
					shredderRecipes.put(new StackWrapper(stack), new ItemStack(ModItems.dust));
				}
			}
		}
	}
	
	public static ItemStack getDustByName(String name) {
		
		List<ItemStack> matches = OreDictionary.getOres("dust" + name);
		
		if(matches != null && !matches.isEmpty())
			return matches.get(0).copy();
		
		return new ItemStack(ModItems.scrap);
	}
	
	public static void overridePreSetRecipe(ItemStack in, ItemStack out) {
		
		shredderRecipes.put(new StackWrapper(in), out);
	}
	
	public Map<Object, Object> getShredderRecipes() {
		
		//convert the map only once to save on processing power (might be more ram intensive but that can't be THAT bad, right?)
		if(neiShredderRecipes == null)
			neiShredderRecipes = new HashMap(shredderRecipes);
		
		return neiShredderRecipes;
	}
	
	public static ItemStack getShredderResult(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return new ItemStack(ModItems.scrap);
		
		ItemStack sta = shredderRecipes.get(new StackWrapper(stack));
		
		/*if(sta != null)
			System.out.println(stack.getDisplayName() + " resulted " + sta.getDisplayName());
		else
			System.out.println(stack.getDisplayName() + " resulted null");*/
		
		return sta == null ? new ItemStack(ModItems.scrap) : sta;
	}

	public Map<Object[], Object> getCMBRecipes() {
		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_advanced_alloy), new ItemStack(ModItems.ingot_magnetized_tungsten) },
				new ItemStack(ModItems.ingot_combine_steel, 4));
		recipes.put(new ItemStack[] { new ItemStack(ModItems.powder_advanced_alloy), new ItemStack(ModItems.powder_magnetized_tungsten) },
				new ItemStack(ModItems.ingot_combine_steel, 4));
		return recipes;
	}

	public ArrayList<ItemStack> getBatteries() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(ModItems.battery_potato));
		fuels.add(new ItemStack(ModItems.battery_potatos));
		fuels.add(new ItemStack(ModItems.battery_su));
		fuels.add(new ItemStack(ModItems.battery_su_l));
		fuels.add(new ItemStack(ModItems.battery_generic));
		fuels.add(new ItemStack(ModItems.battery_red_cell));
		fuels.add(new ItemStack(ModItems.battery_red_cell_6));
		fuels.add(new ItemStack(ModItems.battery_red_cell_24));
		fuels.add(new ItemStack(ModItems.battery_advanced));
		fuels.add(new ItemStack(ModItems.battery_advanced_cell));
		fuels.add(new ItemStack(ModItems.battery_advanced_cell_4));
		fuels.add(new ItemStack(ModItems.battery_advanced_cell_12));
		fuels.add(new ItemStack(ModItems.battery_lithium));
		fuels.add(new ItemStack(ModItems.battery_lithium_cell));
		fuels.add(new ItemStack(ModItems.battery_lithium_cell_3));
		fuels.add(new ItemStack(ModItems.battery_lithium_cell_6));
		fuels.add(new ItemStack(ModItems.battery_schrabidium));
		fuels.add(new ItemStack(ModItems.battery_schrabidium_cell));
		fuels.add(new ItemStack(ModItems.battery_schrabidium_cell_2));
		fuels.add(new ItemStack(ModItems.battery_schrabidium_cell_4));
		fuels.add(new ItemStack(ModItems.battery_spark));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_6));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_25));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_100));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_1000));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_10000));
		fuels.add(new ItemStack(ModItems.battery_spark_cell_power));
		fuels.add(new ItemStack(ModItems.fusion_core));
		fuels.add(new ItemStack(ModItems.energy_core));
		return fuels;
	}

	public ArrayList<ItemStack> getBlades() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(ModItems.blades_advanced_alloy));
		fuels.add(new ItemStack(ModItems.blades_aluminium));
		fuels.add(new ItemStack(ModItems.blades_combine_steel));
		fuels.add(new ItemStack(ModItems.blades_gold));
		fuels.add(new ItemStack(ModItems.blades_iron));
		fuels.add(new ItemStack(ModItems.blades_steel));
		fuels.add(new ItemStack(ModItems.blades_titanium));
		fuels.add(new ItemStack(ModItems.blades_schrabidium));
		return fuels;
	}
	
	public static boolean mODE(Item item, String[] names) {
		return mODE(new ItemStack(item), names);
	}
	
	public static boolean mODE(ItemStack item, String[] names) {
		boolean flag = false;
		if(names.length > 0) {
			for(int i = 0; i < names.length; i++) {
				if(mODE(item, names[i]))
					flag = true;
			}
		}
		
		return flag;
	}
	
	public static boolean mODE(Item item, String name) {
		return mODE(new ItemStack(item), name);
	}
	
	//Matches Ore Dict Entry
	public static boolean mODE(ItemStack stack, String name) {
		
		int[] ids = OreDictionary.getOreIDs(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 0 && s.equals(name))
				return true;
		}
		
		return false;
	}
	
	@Spaghetti("jesus christ")
	public static List<ItemStack> getRecipeFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemAssemblyTemplate))
			return null;
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		EnumAssemblyTemplate template = ItemAssemblyTemplate.EnumAssemblyTemplate.getEnum(stack.getItemDamage());
		
		if(template.ingredients != null)
			return template.ingredients;
		
		switch(template) {
        case IRON_PLATE:
			list.add(new ItemStack(Items.iron_ingot, 3));
			break;
		case GOLD_PLATE:
			list.add(new ItemStack(Items.gold_ingot, 3));
			break;
		case TITANIUM_PLATE:
			list.add(new ItemStack(ModItems.ingot_titanium, 3));
			break;
		case ALUMINIUM_PLATE:
			list.add(new ItemStack(ModItems.ingot_aluminium, 3));
			break;
		case STEEL_PLATE:
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			break;
		case LEAD_PLATE:
			list.add(new ItemStack(ModItems.ingot_lead, 3));
			break;
		case COPPER_PLATE:
			list.add(new ItemStack(ModItems.ingot_copper, 3));
			break;
		case ADVANCED_PLATE:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 3));
			break;
		case SCHRABIDIUM_PLATE:
			list.add(new ItemStack(ModItems.ingot_schrabidium, 3));
			break;
		case CMB_PLATE:
			list.add(new ItemStack(ModItems.ingot_combine_steel, 3));
			break;
		case SATURN_PLATE:
			list.add(new ItemStack(ModItems.ingot_saturnite, 3));
			break;
		case ALUMINIUM_WIRE:
			list.add(new ItemStack(ModItems.ingot_aluminium, 1));
			break;
		case COPPER_WIRE:
			list.add(new ItemStack(ModItems.ingot_copper, 1));
			break;
		case TUNGSTEN_WIRE:
			list.add(new ItemStack(ModItems.ingot_tungsten, 1));
			break;
		case REDCOPPER_WIRE:
			list.add(new ItemStack(ModItems.ingot_red_copper, 1));
			break;
		case ADVANCED_WIRE:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 1));
			break;
		case GOLD_WIRE:
			list.add(new ItemStack(Items.gold_ingot, 1));
			break;
		case SCHRABIDIUM_WIRE:
			list.add(new ItemStack(ModItems.ingot_schrabidium, 1));
			break;
		case MAGNETIZED_WIRE:
			list.add(new ItemStack(ModItems.ingot_magnetized_tungsten, 1));
			break;
		case CIRCUIT_1:
			list.add(new ItemStack(ModItems.circuit_raw, 1));
			break;
		case SCHRABIDIUM_HAMMER:
			list.add(new ItemStack(ModBlocks.block_schrabidium, 15));
			list.add(new ItemStack(ModItems.ingot_polymer, 64*2));
			list.add(new ItemStack(Items.nether_star, 3));
			list.add(new ItemStack(ModItems.fragment_meteorite, 64*8));
			break;
		case MIXED_PLATE:
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.neutron_reflector, 2));
			list.add(new ItemStack(ModItems.plate_combine_steel, 1));
			list.add(new ItemStack(ModItems.plate_lead, 4));
			break;
		case HAZMAT_CLOTH:
			list.add(new ItemStack(ModItems.powder_lead, 4));
			list.add(new ItemStack(Items.string, 8));
			break;
		case ASBESTOS_CLOTH:
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			list.add(new ItemStack(Items.string, 6));
			list.add(new ItemStack(Blocks.wool, 1));
			break;
		case COAL_FILTER:
			list.add(new ItemStack(ModItems.powder_coal, 4));
			list.add(new ItemStack(Items.string, 6));
			list.add(new ItemStack(Items.paper, 1));
			break;
		case CENTRIFUGE_ELEMENT:
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.coil_tungsten, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case CENTRIFUGE_TOWER:
			list.add(new ItemStack(ModItems.centrifuge_element, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.powder_lapis, 2));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case DEE_MAGNET:
			list.add(new ItemStack(ModBlocks.fusion_conductor, 6));
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 1));
			break;
		case FLAT_MAGNET:
			list.add(new ItemStack(ModBlocks.fusion_conductor, 5));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			break;
		case CYCLOTRON_TOWER:
			list.add(new ItemStack(ModItems.magnet_circular, 6));
			list.add(new ItemStack(ModItems.magnet_dee, 3));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.plate_polymer, 24));
			break;
		case REACTOR_CORE:
			list.add(new ItemStack(ModItems.ingot_lead, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			break;
		case RTG_UNIT:
			list.add(new ItemStack(ModItems.thermo_element, 6));
			list.add(new ItemStack(ModItems.board_copper, 2));
			list.add(new ItemStack(ModItems.ingot_lead, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.circuit_copper, 2));
			break;
		case HEAT_UNIT:
			list.add(new ItemStack(ModItems.coil_copper_torus, 3));
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.plate_polymer, 12));
			break;
		case GRAVITY_UNIT:
			list.add(new ItemStack(ModItems.coil_copper, 4));
			list.add(new ItemStack(ModItems.coil_tungsten, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.nugget_schrabidium, 2));
			break;
		case TITANIUM_DRILL:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.ingot_dura_steel, 2));
			list.add(new ItemStack(ModItems.bolt_dura_steel, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case TELEPAD:
			list.add(new ItemStack(ModItems.ingot_polymer, 12));
			list.add(new ItemStack(ModItems.plate_schrabidium, 2));
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 1));
			break;
		case TELEKIT:
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 6));
			list.add(new ItemStack(ModItems.plate_lead, 16));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			list.add(new ItemStack(ModItems.singularity_counter_resonant, 1));
			list.add(new ItemStack(ModItems.singularity_super_heated, 1));
			list.add(new ItemStack(ModItems.powder_power, 4));
			break;
		case GEASS_REACTOR:
			list.add(new ItemStack(ModItems.plate_steel, 15));
			list.add(new ItemStack(ModItems.ingot_lead, 5));
			list.add(new ItemStack(ModItems.rod_quad_empty, 10));
			list.add(new ItemStack(Items.dye, 4, 3));
			break;
		case GENERATOR_FRONT:
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.turbine_titanium, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.wire_gold, 4));
			break;
		case WT1_GENERIC:
			list.add(new ItemStack(ModItems.plate_titanium, 5));
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(Blocks.tnt, 2));
			break;
		case WT2_GENERIC:
			list.add(new ItemStack(ModItems.plate_titanium, 8));
			list.add(new ItemStack(ModItems.plate_steel, 5));
			list.add(new ItemStack(Blocks.tnt, 4));
			break;
		case WT3_GENERIC:
			list.add(new ItemStack(ModItems.plate_titanium, 15));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(Blocks.tnt, 8));
			break;
		case WT1_FIRE:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModItems.powder_fire, 4));
			break;
		case WT2_FIRE:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModItems.powder_fire, 8));
			break;
		case WT3_FIRE:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModItems.powder_fire, 16));
			break;
		case MISSILE_ASSEMBLY:
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.hull_small_aluminium, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.wire_aluminium, 6));
			list.add(new ItemStack(ModItems.canister_kerosene, 3));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
			break;
		case CARRIER:
			list.add(new ItemStack(ModItems.fluid_barrel_full, 16, FluidType.KEROSENE.getID()));
			list.add(new ItemStack(ModItems.thruster_medium, 4));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.hull_big_titanium, 6));
			list.add(new ItemStack(ModItems.hull_big_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_aluminium, 12));
			list.add(new ItemStack(ModItems.plate_titanium, 24));
			list.add(new ItemStack(ModItems.plate_polymer, 128));
			list.add(new ItemStack(ModBlocks.det_cord, 8));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 3));
			break;
		case WT1_CLUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 4));
			break;
		case WT2_CLUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 8));
			break;
		case WT3_CLUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 16));
			break;
		case WT1_BUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModBlocks.det_cord, 8));
			break;
		case WT2_BUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModBlocks.det_cord, 4));
			list.add(new ItemStack(ModBlocks.det_charge, 4));
			break;
		case WT3_BUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModBlocks.det_charge, 8));
			break;
		case W_NUCLEAR:
			list.add(new ItemStack(ModItems.boy_shielding, 1));
			list.add(new ItemStack(ModItems.boy_target, 1));
			list.add(new ItemStack(ModItems.boy_bullet, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			break;
		case W_MIRVLET:
			list.add(new ItemStack(ModItems.ingot_steel, 5));
			list.add(new ItemStack(ModItems.plate_steel, 18));
			list.add(new ItemStack(ModItems.ingot_pu239, 1));
			list.add(new ItemStack(Blocks.tnt, 2));
			break;
		case W_MIRV:
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModItems.ingot_pu239, 1));
			list.add(new ItemStack(Blocks.tnt, 8));
			list.add(new ItemStack(ModItems.neutron_reflector, 6));
			list.add(new ItemStack(ModItems.lithium, 4));
			list.add(new ItemStack(ModItems.cell_deuterium, 6));
			break;
		case W_ENDOTHERMIC:
			list.add(new ItemStack(ModBlocks.therm_endo, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			break;
		case W_EXOTHERMIC:
			list.add(new ItemStack(ModBlocks.therm_exo, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			break;
		case T1_TANK:
			list.add(new ItemStack(ModItems.canister_kerosene, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case T2_TANK:
			list.add(new ItemStack(ModItems.fuel_tank_small, 3));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case T3_TANK:
			list.add(new ItemStack(ModItems.fuel_tank_medium, 3));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case T1_THRUSTER:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.wire_aluminium, 4));
			break;
		case T2_THRUSTER:
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.wire_copper, 4));
			break;
		case T3_THRUSTER:
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.hull_big_steel, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case NUCLEAR_THRUSTER:
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 3));
			list.add(new ItemStack(ModItems.board_copper, 6));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 2));
			list.add(new ItemStack(ModBlocks.machine_reactor_small, 1));
			break;
		case SAT_BASE:
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.plate_desh, 4));
			list.add(new ItemStack(ModItems.hull_big_titanium, 3));
			list.add(new ItemStack(ModItems.fluid_barrel_full, 1, FluidType.KEROSENE.getID()));
			list.add(new ItemStack(ModItems.photo_panel, 24));
			list.add(new ItemStack(ModItems.board_copper, 12));
			list.add(new ItemStack(ModItems.circuit_gold, 6));
			list.add(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6));
			break;
		case SAT_MAPPER:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.hull_small_steel, 3));
			list.add(new ItemStack(ModItems.plate_desh, 2));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			list.add(new ItemStack(ModItems.plate_polymer, 12));
			list.add(new ItemStack(Items.redstone, 6));
			list.add(new ItemStack(Items.diamond, 1));
			list.add(new ItemStack(Blocks.glass_pane, 6));
			break;
		case SAT_SCANNER:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.plate_titanium, 32));
			list.add(new ItemStack(ModItems.plate_desh, 6));
			list.add(new ItemStack(ModItems.magnetron, 6));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 2));
			list.add(new ItemStack(ModItems.circuit_gold, 6));
			list.add(new ItemStack(ModItems.plate_polymer, 6));
			list.add(new ItemStack(Items.diamond, 1));
			break;
		case SAT_RADAR:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 32));
			list.add(new ItemStack(ModItems.magnetron, 12));
			list.add(new ItemStack(ModItems.plate_polymer, 16));
			list.add(new ItemStack(ModItems.wire_red_copper, 16));
			list.add(new ItemStack(ModItems.coil_gold, 3));
			list.add(new ItemStack(ModItems.circuit_gold, 5));
			list.add(new ItemStack(Items.diamond, 1));
			break;
		case SAT_LASER:
			list.add(new ItemStack(ModItems.ingot_steel, 12));
			list.add(new ItemStack(ModItems.ingot_tungsten, 16));
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
			list.add(new ItemStack(ModItems.plate_polymer, 16));
			list.add(new ItemStack(ModItems.board_copper, 24));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 2));
			list.add(new ItemStack(Items.redstone, 16));
			list.add(new ItemStack(Items.diamond, 5));
			list.add(new ItemStack(Blocks.glass_pane, 16));
			break;
		case SAT_RESONATOR:
			list.add(new ItemStack(ModItems.ingot_steel, 32));
			list.add(new ItemStack(ModItems.ingot_polymer, 48));
			list.add(new ItemStack(ModItems.plate_polymer, 8));
			list.add(new ItemStack(ModItems.crystal_xen, 1));
			list.add(new ItemStack(ModItems.ingot_starmetal, 7));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier6, 2));
			break;
		case SAT_FOEQ:
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.plate_desh, 8));
			list.add(new ItemStack(ModItems.hull_big_titanium, 3));
			list.add(new ItemStack(ModItems.fluid_barrel_full, 1, FluidType.WATER.getID()));
			list.add(new ItemStack(ModItems.photo_panel, 16));
			list.add(new ItemStack(ModItems.thruster_nuclear, 1));
			list.add(new ItemStack(ModItems.rod_quad_uranium_fuel, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 6));
			list.add(new ItemStack(ModItems.magnetron, 3));
			list.add(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6));
			break;
		case SAT_MINER:
			list.add(new ItemStack(ModItems.plate_saturnite, 24));
			list.add(new ItemStack(ModItems.plate_desh, 8));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.drill_titanium, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 2));
			list.add(new ItemStack(ModItems.fluid_barrel_full, 1, FluidType.KEROSENE.getID()));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.photo_panel, 12));
			list.add(new ItemStack(ModItems.centrifuge_element, 4));
			list.add(new ItemStack(ModItems.magnetron, 3));
			list.add(new ItemStack(ModItems.plate_polymer, 12));
			list.add(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6));
			break;
		case CHOPPER_HEAD:
			list.add(new ItemStack(ModBlocks.reinforced_glass, 2));
			list.add(new ItemStack(ModBlocks.fwatz_computer, 1));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 22));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			break;
		case CHOPPER_GUN:
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 2));
			list.add(new ItemStack(ModItems.wire_tungsten, 6));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 1));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case CHOPPER_BODY:
			list.add(new ItemStack(ModItems.ingot_combine_steel, 26));
			list.add(new ItemStack(ModBlocks.fwatz_computer, 1));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.chopper_blades, 2));
			break;
		case CHOPPER_TAIL:
			list.add(new ItemStack(ModItems.plate_combine_steel, 8));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 5));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.chopper_blades, 2));
			break;
		case CHOPPER_WING:
			list.add(new ItemStack(ModItems.plate_combine_steel, 6));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 3));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 2));
			break;
		case CHOPPER_BLADES:
			list.add(new ItemStack(ModItems.plate_combine_steel, 8));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 2));
			break;
		case CIRCUIT_2:
			list.add(new ItemStack(ModItems.circuit_aluminium, 1));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			list.add(new ItemStack(ModItems.plate_copper, 1));
			break;
		case CIRCUIT_3:
			list.add(new ItemStack(ModItems.circuit_copper, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.powder_gold, 4));
			list.add(new ItemStack(ModItems.plate_polymer, 1));
			break;
		case RTG_PELLET:
			list.add(new ItemStack(ModItems.nugget_pu238, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case WEAK_PELLET:
			list.add(new ItemStack(ModItems.nugget_u238, 4));
			list.add(new ItemStack(ModItems.nugget_pu238, 1));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case FUSION_PELLET:
			list.add(new ItemStack(ModItems.cell_deuterium, 6));
			list.add(new ItemStack(ModItems.cell_tritium, 2));
			list.add(new ItemStack(ModItems.lithium, 4));
			break;
		case CLUSTER_PELLETS:
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(Blocks.tnt, 1));
			break;
		case GUN_PELLETS:
			list.add(new ItemStack(ModItems.nugget_lead, 6));
			break;
		case AUSTRALIUM_MACHINE:
			list.add(new ItemStack(ModItems.rod_australium, 1));
			list.add(new ItemStack(ModItems.ingot_steel, 1));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			break;
		case MAGNETRON:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 1));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.wire_tungsten, 1));
			list.add(new ItemStack(ModItems.coil_tungsten, 1));
			break;
		case W_SP:
			list.add(new ItemStack(ModItems.ingot_schrabidium, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_SHE:
			list.add(new ItemStack(ModItems.ingot_hes, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_SME:
			list.add(new ItemStack(ModItems.ingot_schrabidium_fuel, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_SLE:
			list.add(new ItemStack(ModItems.ingot_les, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_B:
			list.add(new ItemStack(ModItems.ingot_beryllium, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_N:
			list.add(new ItemStack(ModItems.ingot_neptunium, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_L:
			list.add(new ItemStack(ModItems.ingot_lead, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_A:
			list.add(new ItemStack(ModItems.ingot_desh, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case UPGRADE_TEMPLATE:
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.plate_iron, 4));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			break;
		case UPGRADE_RED_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_red_copper, 4));
			list.add(new ItemStack(Items.redstone, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_RED_II:
			list.add(new ItemStack(ModItems.upgrade_speed_1, 1));
			list.add(new ItemStack(ModItems.powder_red_copper, 2));
			list.add(new ItemStack(Items.redstone, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_RED_III:
			list.add(new ItemStack(ModItems.upgrade_speed_2, 1));
			list.add(new ItemStack(ModItems.powder_red_copper, 2));
			list.add(new ItemStack(Items.redstone, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_GREEN_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_dura_steel, 4));
			list.add(new ItemStack(ModItems.powder_steel, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_GREEN_II:
			list.add(new ItemStack(ModItems.upgrade_effect_1, 1));
			list.add(new ItemStack(ModItems.powder_dura_steel, 2));
			list.add(new ItemStack(ModItems.powder_steel, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_GREEN_III:
			list.add(new ItemStack(ModItems.upgrade_effect_2, 1));
			list.add(new ItemStack(ModItems.powder_dura_steel, 2));
			list.add(new ItemStack(ModItems.powder_steel, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_BLUE_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_lapis, 4));
			list.add(new ItemStack(Items.glowstone_dust, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_BLUE_II:
			list.add(new ItemStack(ModItems.upgrade_power_1, 1));
			list.add(new ItemStack(ModItems.powder_lapis, 2));
			list.add(new ItemStack(Items.glowstone_dust, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_BLUE_III:
			list.add(new ItemStack(ModItems.upgrade_power_2, 1));
			list.add(new ItemStack(ModItems.powder_lapis, 2));
			list.add(new ItemStack(Items.glowstone_dust, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_PURPLE_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_diamond, 4));
			list.add(new ItemStack(ModItems.powder_iron, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_PURPLE_II:
			list.add(new ItemStack(ModItems.upgrade_fortune_1, 1));
			list.add(new ItemStack(ModItems.powder_diamond, 2));
			list.add(new ItemStack(ModItems.powder_iron, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_PURPLE_III:
			list.add(new ItemStack(ModItems.upgrade_fortune_2, 1));
			list.add(new ItemStack(ModItems.powder_diamond, 2));
			list.add(new ItemStack(ModItems.powder_iron, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_PINK_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_polymer, 4));
			list.add(new ItemStack(ModItems.powder_tungsten, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_PINK_II:
			list.add(new ItemStack(ModItems.upgrade_afterburn_1, 1));
			list.add(new ItemStack(ModItems.powder_polymer, 2));
			list.add(new ItemStack(ModItems.powder_tungsten, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_PINK_III:
			list.add(new ItemStack(ModItems.upgrade_afterburn_2, 1));
			list.add(new ItemStack(ModItems.powder_polymer, 2));
			list.add(new ItemStack(ModItems.powder_tungsten, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_RANGE:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(Items.glowstone_dust, 6));
			list.add(new ItemStack(ModItems.powder_diamond, 4));
			break;
		case UPGRADE_HEALTH:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(Items.glowstone_dust, 6));
			list.add(new ItemStack(ModItems.powder_titanium, 4));
			break;
		case FUSE:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(Blocks.glass_pane, 1));
			list.add(new ItemStack(ModItems.wire_aluminium, 1));
			break;
		case REDCOIL_CAPACITOR:
			list.add(new ItemStack(ModItems.plate_gold, 3));
			list.add(new ItemStack(ModItems.fuse, 1));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 6));
			list.add(new ItemStack(Blocks.redstone_block, 2));
			break;
		case TITANIUM_FILTER:
			list.add(new ItemStack(ModItems.plate_lead, 3));
			list.add(new ItemStack(ModItems.fuse, 1));
			list.add(new ItemStack(ModItems.wire_tungsten, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.ingot_u238, 2));
			break;
		case LITHIUM_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_lithium, 2));
			break;
		case BERYLLIUM_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_beryllium, 2));
			break;
		case COAL_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_coal, 2));
			break;
		case COPPER_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_copper, 2));
			break;
		case PLUTONIUM_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_plutonium, 2));
			break;
		case THERMO_ELEMENT:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 2));
			list.add(new ItemStack(ModItems.wire_aluminium, 2));
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			break;
		case LIMITER:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.circuit_copper, 2));
			list.add(new ItemStack(ModItems.wire_copper, 4));
			break;
		case ANGRY_METAL:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			break;
		case METEOR_BLOCK:
			list.add(new ItemStack(ModItems.fragment_meteorite, 100));
			break;
		case CMB_TILE:
			list.add(new ItemStack(ModItems.ingot_combine_steel, 1));
			list.add(new ItemStack(ModItems.plate_combine_steel, 8));
			break;
		case CMB_BRICKS:
			list.add(new ItemStack(ModBlocks.block_magnetized_tungsten, 4));
			list.add(new ItemStack(ModBlocks.brick_concrete, 4));
			list.add(new ItemStack(ModBlocks.cmb_brick, 1));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			break;
		case HATCH_FRAME:
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.wire_aluminium, 4));
			list.add(new ItemStack(Items.redstone, 2));
			list.add(new ItemStack(ModBlocks.steel_roof, 5));
			break;
		case HATCH_CONTROLLER:
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.ingot_red_copper, 1));
			list.add(new ItemStack(Items.redstone, 4));
			list.add(new ItemStack(ModBlocks.steel_roof, 5));
			break;
		case BLAST_DOOR:
			list.add(new ItemStack(ModItems.ingot_steel, 128));
			list.add(new ItemStack(ModItems.ingot_tungsten, 32));
			list.add(new ItemStack(ModItems.plate_lead, 48));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.plate_polymer, 16));
			list.add(new ItemStack(ModItems.bolt_tungsten, 18));
			list.add(new ItemStack(ModItems.bolt_dura_steel, 27));
			list.add(new ItemStack(ModItems.motor, 5));
			break;
		case SLIDING_DOOR:
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.ingot_tungsten, 8));
			list.add(new ItemStack(ModItems.plate_lead, 12));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 3));
			list.add(new ItemStack(ModItems.plate_polymer, 3));
			list.add(new ItemStack(ModItems.bolt_tungsten, 3));
			list.add(new ItemStack(ModItems.bolt_dura_steel, 3));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case CENTRIFUGE:
			list.add(new ItemStack(ModItems.centrifuge_tower, 1));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(Items.iron_ingot, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			break;
		case CENTRIFUGE_GAS:
			list.add(new ItemStack(ModItems.centrifuge_tower, 1));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.ingot_desh, 2));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			list.add(new ItemStack(ModItems.wire_gold, 4));
			break;
		case BREEDING_REACTOR:
			list.add(new ItemStack(ModItems.reactor_core, 1));
			list.add(new ItemStack(ModItems.ingot_lead, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			break;
		case RTG_FURNACE:
			list.add(new ItemStack(Blocks.furnace, 1));
			list.add(new ItemStack(ModItems.rtg_unit, 3));
			list.add(new ItemStack(ModItems.plate_lead, 6));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			break;
		case RAD_GEN:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.plate_steel, 32));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 6));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 24));
			list.add(new ItemStack(ModItems.circuit_gold, 4));
			list.add(new ItemStack(ModItems.reactor_core, 3));
			list.add(new ItemStack(ModItems.ingot_starmetal, 1));
			list.add(new ItemStack(Items.dye, 1, 1));
			break;
		case DIESEL_GENERATOR:
			list.add(new ItemStack(ModItems.hull_small_steel, 4));
			list.add(new ItemStack(Blocks.piston, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.ingot_red_copper, 2));
			list.add(new ItemStack(ModItems.plate_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			break;
		case SELENIUM_GENERATOR:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.plate_copper, 8));
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.hull_small_steel, 9));
			list.add(new ItemStack(ModItems.pedestal_steel, 1));
			list.add(new ItemStack(ModItems.coil_copper, 4));
			break;
		case NUCLEAR_GENERATOR:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.plate_lead, 8));
			list.add(new ItemStack(ModItems.plate_copper, 4));
			list.add(new ItemStack(ModItems.ingot_lead, 12));
			list.add(new ItemStack(ModItems.ingot_red_copper, 6));
			list.add(new ItemStack(ModItems.circuit_copper, 8));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			break;
		case INDUSTRIAL_GENERATOR:
			list.add(new ItemStack(ModItems.generator_front, 1));
			list.add(new ItemStack(ModItems.generator_steel, 3));
			list.add(new ItemStack(ModItems.rotor_steel, 3));
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.board_copper, 4));
			list.add(new ItemStack(ModItems.wire_gold, 8));
			list.add(new ItemStack(ModBlocks.red_wire_coated, 2));
			list.add(new ItemStack(ModItems.pedestal_steel, 2));
			list.add(new ItemStack(ModItems.circuit_copper, 4));
			break;
		case CYCLOTRON:
			list.add(new ItemStack(ModItems.cyclotron_tower, 1));
			list.add(new ItemStack(ModItems.board_copper, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.ingot_polymer, 24));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModBlocks.machine_battery, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 20));
			list.add(new ItemStack(ModItems.circuit_red_copper, 12));
			list.add(new ItemStack(ModItems.circuit_gold, 3));
			break;
		case RT_GENERATOR:
			list.add(new ItemStack(ModItems.rtg_unit, 5));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
			break;
		case BATTERY:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.sulfur, 12));
			list.add(new ItemStack(ModItems.powder_lead, 12));
			list.add(new ItemStack(ModItems.ingot_red_copper, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case BATTERY_L:
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.powder_cobalt, 12));
			list.add(new ItemStack(ModItems.powder_lithium, 12));
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case BATTERY_S:
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			list.add(new ItemStack(ModItems.powder_neptunium, 12));
			list.add(new ItemStack(ModItems.powder_schrabidium, 12));
			list.add(new ItemStack(ModItems.ingot_schrabidium, 2));
			list.add(new ItemStack(ModItems.wire_schrabidium, 4));
			break;
		case BATTERY_D:
			list.add(new ItemStack(ModItems.ingot_dineutronium, 24));
			list.add(new ItemStack(ModItems.powder_spark_mix, 12));
			list.add(new ItemStack(ModItems.battery_spark_cell_1000, 1));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 32));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 8));
			break;
		/*case HE_TO_RF:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			list.add(new ItemStack(ModItems.coil_copper_torus, 1));
			break;
		case RF_TO_HE:
			list.add(new ItemStack(ModItems.ingot_beryllium, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			list.add(new ItemStack(ModItems.coil_copper_torus, 1));
			break;*/
		case SHREDDER:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 2));
			list.add(new ItemStack(ModBlocks.steel_beam, 2));
			list.add(new ItemStack(Blocks.iron_bars, 2));
			list.add(new ItemStack(ModBlocks.red_wire_coated, 1));
			break;
		/*case DEUTERIUM_EXTRACTOR:
			list.add(new ItemStack(ModItems.ingot_titanium, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.coil_tungsten, 4));
			break;*/
		case DERRICK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 20));
			list.add(new ItemStack(ModBlocks.steel_beam, 8));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.pipes_steel, 3));
			list.add(new ItemStack(ModItems.drill_titanium, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			break;
		case PUMPJACK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 8));
			list.add(new ItemStack(ModBlocks.block_steel, 8));
			list.add(new ItemStack(ModItems.pipes_steel, 4));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 24));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(new ItemStack(ModItems.plate_aluminium, 6));
			list.add(new ItemStack(ModItems.drill_titanium, 1));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			break;
		case FLARE_STACK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 28));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.thermo_element, 3));
			break;
		case REFINERY:
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.plate_steel, 24));
			list.add(new ItemStack(ModItems.plate_copper, 16));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.hull_big_steel, 6));
			list.add(new ItemStack(ModItems.pipes_steel, 4));
			list.add(new ItemStack(ModItems.coil_tungsten, 10));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.plate_polymer, 8));
			break;
		case EPRESS:
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.plate_polymer, 4));
			list.add(new ItemStack(ModItems.pipes_steel, 1));
			list.add(new ItemStack(ModItems.bolt_tungsten, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.circuit_copper, 1));
			list.add(new ItemStack(ModItems.canister_canola, 1));
			break;
		case CHEMPLANT:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.plate_copper, 6));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 16));
			list.add(new ItemStack(ModItems.wire_tungsten, 3));
			list.add(new ItemStack(ModItems.circuit_copper, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 2));
			list.add(new ItemStack(ModItems.plate_polymer, 8));
			break;
		case TANK:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.hull_big_steel, 4));
			break;
		case MINER:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 6));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.circuit_copper, 1));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.ingot_dura_steel, 2));
			list.add(new ItemStack(ModItems.bolt_dura_steel, 2));
			list.add(new ItemStack(ModItems.drill_titanium, 1));
			break;
		case TURBOFAN:
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_titanium, 3));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.turbine_tungsten, 1));
			list.add(new ItemStack(ModItems.turbine_titanium, 7));
			list.add(new ItemStack(ModItems.bolt_compound, 8));
			list.add(new ItemStack(ModItems.ingot_red_copper, 12));
			list.add(new ItemStack(ModItems.wire_red_copper, 24));
			break;
		case TELEPORTER:
			list.add(new ItemStack(ModItems.ingot_titanium, 6));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 12));
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(ModItems.telepad, 1));
			list.add(new ItemStack(ModItems.entanglement_kit, 1));
			list.add(new ItemStack(ModBlocks.machine_battery, 2));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 4));
			break;
		case SCHRABTRANS:
			list.add(new ItemStack(ModItems.ingot_magnetized_tungsten, 1));
			list.add(new ItemStack(ModItems.ingot_titanium, 24));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 18));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModItems.plate_desh, 6));
			list.add(new ItemStack(ModItems.plate_polymer, 8));
			list.add(new ItemStack(ModBlocks.machine_battery, 5));
			list.add(new ItemStack(ModItems.circuit_gold, 5));
			break;
		case CMB_FURNACE:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.plate_copper, 6));
			list.add(new ItemStack(ModItems.circuit_gold, 6));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.coil_tungsten, 4));
			list.add(new ItemStack(ModItems.ingot_magnetized_tungsten, 12));
			break;
		case FA_HULL:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 6));
			break;
		case FA_HATCH:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 2));
			break;
		case FA_CORE:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.motor, 16));
			list.add(new ItemStack(Blocks.piston, 6));
			break;
		case FA_PORT:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.fuse, 6));
			break;
		case LR_ELEMENT:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			list.add(new ItemStack(ModItems.plate_lead, 2));
			list.add(new ItemStack(ModItems.rod_empty, 8));
			break;
		case LR_CONTROL:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.ingot_lead, 6));
			list.add(new ItemStack(ModItems.bolt_tungsten, 6));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case LR_HATCH:
			list.add(new ItemStack(ModBlocks.brick_concrete, 1));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			break;
		case LR_PORT:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_copper, 12));
			list.add(new ItemStack(ModItems.wire_tungsten, 4));
			break;
		case LR_CORE:
			list.add(new ItemStack(ModBlocks.reactor_conductor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 4));
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			break;
		case LF_MAGNET:
			list.add(new ItemStack(ModItems.plate_steel, 10));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 5));
			break;
		case LF_CENTER:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 24));
			break;
		case LF_MOTOR:
			list.add(new ItemStack(ModItems.ingot_titanium, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.motor, 4));
			break;
		case LF_HEATER:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.neutron_reflector, 6));
			list.add(new ItemStack(ModItems.magnetron, 4));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 4));
			break;
		case LF_HATCH:
			list.add(new ItemStack(ModBlocks.fusion_heater, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case LF_CORE:
			list.add(new ItemStack(ModBlocks.fusion_center, 3));
			list.add(new ItemStack(ModItems.circuit_red_copper, 48));
			list.add(new ItemStack(ModItems.circuit_gold, 12));
			break;
		case LW_ELEMENT:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.rod_empty, 4));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 2));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			break;
		case LW_CONTROL:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.ingot_lead, 2));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			list.add(new ItemStack(ModItems.circuit_copper, 2));
			break;
		case LW_COOLER:
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.niter, 6));
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			break;
		case LW_STRUTURE:
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.ingot_lead, 2));
			list.add(new ItemStack(ModItems.ingot_steel, 5));
			break;
		case LW_HATCH:
			list.add(new ItemStack(ModBlocks.reinforced_brick, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case LW_PORT:
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.ingot_lead, 2));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 2));
			list.add(new ItemStack(ModItems.fuse, 4));
			break;
		case LW_CORE:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 8));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 2));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 12));
			break;
		case FW_PORT:
			list.add(new ItemStack(ModItems.ingot_tungsten, 6));
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			break;
		case FW_MAGNET:
			list.add(new ItemStack(ModItems.plate_combine_steel, 10));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 5));
			break;
		case FW_COMPUTER:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 16));
			list.add(new ItemStack(ModItems.powder_diamond, 6));
			list.add(new ItemStack(ModItems.powder_magnetized_tungsten, 6));
			list.add(new ItemStack(ModItems.powder_desh, 4));
			break;
		case FW_CORE:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 24));
			list.add(new ItemStack(ModItems.powder_diamond, 8));
			list.add(new ItemStack(ModItems.powder_magnetized_tungsten, 12));
			list.add(new ItemStack(ModItems.powder_desh, 8));
			list.add(new ItemStack(ModItems.upgrade_power_3, 1));
			list.add(new ItemStack(ModItems.upgrade_speed_3, 1));
			break;
		case GADGET:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.fins_flat, 2));
			list.add(new ItemStack(ModItems.pedestal_steel, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			list.add(new ItemStack(Items.dye, 6, 8));
			break;
		case LITTLE_BOY:
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_small_steel, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
			list.add(new ItemStack(ModItems.wire_aluminium, 6));
			list.add(new ItemStack(Items.dye, 4, 4));
			break;
		case FAT_MAN:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_steel, 2));
			list.add(new ItemStack(ModItems.fins_big_steel, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 2));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			list.add(new ItemStack(Items.dye, 6, 11));
			break;
		case IVY_MIKE:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_aluminium, 4));
			list.add(new ItemStack(ModItems.cap_aluminium, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 3));
			list.add(new ItemStack(ModItems.wire_gold, 18));
			list.add(new ItemStack(Items.dye, 12, 7));
			break;
		case TSAR_BOMB:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_titanium, 6));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_tri_steel, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 5));
			list.add(new ItemStack(ModItems.wire_gold, 24));
			list.add(new ItemStack(ModItems.wire_tungsten, 12));
			list.add(new ItemStack(Items.dye, 6, 0));
			break;
		case PROTOTYPE:
			list.add(new ItemStack(ModItems.dysfunctional_reactor, 1));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.ingot_euphemium, 3));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 1));
			list.add(new ItemStack(ModItems.wire_gold, 16));
			break;
		case FLEIJA:
			list.add(new ItemStack(ModItems.hull_small_aluminium, 1));
			list.add(new ItemStack(ModItems.fins_quad_titanium, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 2));
			list.add(new ItemStack(ModItems.wire_gold, 8));
			list.add(new ItemStack(Items.dye, 4, 15));
			break;
		case SOLINIUM:
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_quad_titanium, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 3));
			list.add(new ItemStack(ModItems.wire_gold, 10));
			list.add(new ItemStack(ModItems.pipes_steel, 4));
			list.add(new ItemStack(Items.dye, 4, 8));
			break;
		case N2:
			list.add(new ItemStack(ModItems.hull_big_steel, 3));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 12));
			list.add(new ItemStack(ModItems.pipes_steel, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 3));
			list.add(new ItemStack(Items.dye, 12, 0));
			break;
		case CUSTOM_NUKE:
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_small_steel, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			list.add(new ItemStack(ModItems.wire_gold, 12));
			list.add(new ItemStack(Items.dye, 4, 8));
			break;
		case BOMB_LEV:
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.levitation_unit, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 4));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			break;
		case BOMB_ENDO:
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.thermo_unit_endo, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			break;
		case BOMB_EXO:
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.thermo_unit_exo, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			break;
		case LAUNCH_PAD:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModBlocks.machine_battery, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			break;
		case TURRET_LIGHT:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.ingot_red_copper, 2));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 2));
			break;
		case TURRET_HEAVY:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.ingot_aluminium, 4));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.ingot_red_copper, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 3));
			break;
		case TURRET_ROCKET:
			list.add(new ItemStack(ModItems.ingot_steel, 12));
			list.add(new ItemStack(ModItems.ingot_titanium, 4));
			list.add(new ItemStack(ModItems.hull_small_steel, 8));
			list.add(new ItemStack(ModItems.ingot_red_copper, 6));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 2));
			break;
		case TURRET_FLAMER:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 1));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.ingot_red_copper, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 2));
			break;
		case TURRET_TAU:
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.ingot_titanium, 8));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.redcoil_capacitor, 3));
			list.add(new ItemStack(ModItems.ingot_red_copper, 12));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 2));
			break;
		case TURRET_SPITFIRE:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.ingot_red_copper, 6));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(new ItemStack(ModItems.plate_iron, 8));
			list.add(new ItemStack(ModItems.hull_small_steel, 4));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.motor, 3));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			break;
		case TURRET_CIWS:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.ingot_red_copper, 8));
			list.add(new ItemStack(ModItems.plate_steel, 10));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.hull_small_aluminium, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 6));
			list.add(new ItemStack(ModItems.motor, 4));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 2));
			list.add(new ItemStack(ModItems.magnetron, 3));
			break;
		case TURRET_CHEAPO:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_iron, 4));
			list.add(new ItemStack(ModItems.pipes_steel, 3));
			list.add(new ItemStack(ModItems.motor, 3));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 4));
			break;
		case HUNTER_CHOPPER:
			list.add(new ItemStack(ModItems.chopper_blades, 5));
			list.add(new ItemStack(ModItems.chopper_gun, 1));
			list.add(new ItemStack(ModItems.chopper_head, 1));
			list.add(new ItemStack(ModItems.chopper_tail, 1));
			list.add(new ItemStack(ModItems.chopper_torso, 1));
			list.add(new ItemStack(ModItems.chopper_wing, 2));
			break;
		case MISSILE_HE_1:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
			break;
		case MISSILE_FIRE_1:
			list.add(new ItemStack(ModItems.warhead_incendiary_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
			break;
		case MISSILE_CLUSTER_1:
			list.add(new ItemStack(ModItems.warhead_cluster_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
			break;
		case MISSILE_BUSTER_1:
			list.add(new ItemStack(ModItems.warhead_buster_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
			break;
		case MISSILE_HE_2:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
			break;
		case MISSILE_FIRE_2:
			list.add(new ItemStack(ModItems.warhead_incendiary_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
			break;
		case MISSILE_CLUSTER_2:
			list.add(new ItemStack(ModItems.warhead_cluster_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
			break;
		case MISSILE_BUSTER_2:
			list.add(new ItemStack(ModItems.warhead_buster_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
			break;
		case MISSILE_HE_3:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			break;
		case MISSILE_FIRE_3:
			list.add(new ItemStack(ModItems.warhead_incendiary_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			break;
		case MISSILE_CLUSTER_3:
			list.add(new ItemStack(ModItems.warhead_cluster_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			break;
		case MISSILE_BUSTER_3:
			list.add(new ItemStack(ModItems.warhead_buster_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			break;
		case MISSILE_NUCLEAR:
			list.add(new ItemStack(ModItems.warhead_nuclear, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 24));
			list.add(new ItemStack(ModItems.plate_aluminium, 16));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
			break;
		case MISSILE_MIRV:
			list.add(new ItemStack(ModItems.warhead_mirv, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 24));
			list.add(new ItemStack(ModItems.plate_aluminium, 16));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 1));
			break;
		case MISSILE_ENDO:
			list.add(new ItemStack(ModItems.warhead_thermo_endo, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
			break;
		case MISSILE_EXO:
			list.add(new ItemStack(ModItems.warhead_thermo_exo, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
			break;
		case DEFAB:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.ingot_polymer, 8));
			list.add(new ItemStack(ModItems.plate_iron, 5));
			list.add(new ItemStack(ModItems.mechanism_special, 3));
			list.add(new ItemStack(Items.diamond, 1));
			list.add(new ItemStack(ModItems.plate_dalekanium, 3));
			break;
		case MINI_NUKE:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.nugget_pu239, 3));
			break;
		case MINI_MIRV:
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_iron, 10));
			list.add(new ItemStack(ModItems.nugget_pu239, 24));
			break;
		case DARK_PLUG:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(Items.redstone, 1));
			list.add(new ItemStack(Items.glowstone_dust, 1));
			break;
		case COMBINE_BALL:
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(Items.redstone, 7));
			list.add(new ItemStack(ModItems.powder_power, 3));
			break;
		case GRENADE_FLAME:
			list.add(new ItemStack(ModItems.grenade_frag, 1));
			list.add(new ItemStack(ModItems.powder_fire, 1));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			break;
		case GRENADE_SHRAPNEL:
			list.add(new ItemStack(ModItems.grenade_frag, 1));
			list.add(new ItemStack(ModItems.pellet_buckshot, 1));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case GRENAGE_CLUSTER:
			list.add(new ItemStack(ModItems.grenade_frag, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 1));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case GREANADE_FLARE:
			list.add(new ItemStack(ModItems.grenade_generic, 1));
			list.add(new ItemStack(Items.glowstone_dust, 1));
			list.add(new ItemStack(ModItems.plate_aluminium, 2));
			break;
		case GRENADE_LIGHTNING:
			list.add(new ItemStack(ModItems.grenade_generic, 1));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.plate_gold, 2));
			break;
		case GRENADE_IMPULSE:
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.plate_iron, 3));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(Items.diamond, 1));
			break;
		case GRENADE_PLASMA:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 1));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 1));
			list.add(new ItemStack(ModItems.cell_deuterium, 1));
			list.add(new ItemStack(ModItems.cell_tritium, 1));
			break;
		case GRENADE_TAU:
			list.add(new ItemStack(ModItems.plate_lead, 3));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 1));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 1));
			list.add(new ItemStack(ModItems.gun_xvl1456_ammo, 1));
			break;
		case GRENADE_SCHRABIDIUM:
			list.add(new ItemStack(ModItems.grenade_flare, 1));
			list.add(new ItemStack(ModItems.powder_schrabidium, 1));
			list.add(new ItemStack(ModItems.neutron_reflector, 2));
			break;
		case GRENADE_NUKE:
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.nugget_pu239, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 2));
			break;
		case GRENADE_ZOMG:
			list.add(new ItemStack(ModItems.plate_paa, 3));
			list.add(new ItemStack(ModItems.neutron_reflector, 1));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 3));
			list.add(new ItemStack(ModItems.powder_power, 3));
			break;
		case GRENADE_BLACK_HOLE:
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
			list.add(new ItemStack(ModItems.neutron_reflector, 3));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 2));
			list.add(new ItemStack(ModItems.black_hole, 1));
			break;
		case POWER_FIST:
			list.add(new ItemStack(ModItems.rod_reiium, 1));
			list.add(new ItemStack(ModItems.rod_weidanium, 1));
			list.add(new ItemStack(ModItems.rod_australium, 1));
			list.add(new ItemStack(ModItems.rod_verticium, 1));
			list.add(new ItemStack(ModItems.rod_unobtainium, 1));
			list.add(new ItemStack(ModItems.rod_daffergon, 1));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			list.add(new ItemStack(ModItems.ducttape, 1));
			break;
		case GADGET_PROPELLANT:
			list.add(new ItemStack(Blocks.tnt, 3));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.plate_aluminium, 4));
			list.add(new ItemStack(ModItems.wire_gold, 3));
			break;
		case GADGET_WIRING:
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.wire_gold, 12));
			break;
		case GADGET_CORE:
			list.add(new ItemStack(ModItems.nugget_pu239, 7));
			list.add(new ItemStack(ModItems.nugget_u238, 3));
			break;
		case BOY_SHIELDING:
			list.add(new ItemStack(ModItems.neutron_reflector, 12));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			break;
		case BOY_TARGET:
			list.add(new ItemStack(ModItems.nugget_u235, 7));
			break;
		case BOY_BULLET:
			list.add(new ItemStack(ModItems.nugget_u235, 3));
			break;
		case BOY_PRPELLANT:
			list.add(new ItemStack(Blocks.tnt, 3));
			list.add(new ItemStack(ModItems.plate_iron, 8));
			list.add(new ItemStack(ModItems.plate_aluminium, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case BOY_IGNITER:
			list.add(new ItemStack(ModItems.plate_aluminium, 6));
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 3));
			break;
		case MAN_PROPELLANT:
			list.add(new ItemStack(Blocks.tnt, 3));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 3));
			break;
		case MAN_IGNITER:
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 9));
			break;
		case MAN_CORE:
			list.add(new ItemStack(ModItems.nugget_pu239, 8));
			list.add(new ItemStack(ModItems.nugget_beryllium, 2));
			break;
		case MIKE_TANK:
			list.add(new ItemStack(ModItems.nugget_u238, 24));
			list.add(new ItemStack(ModItems.ingot_lead, 6));
			break;
		case MIKE_DEUT:
			list.add(new ItemStack(ModItems.plate_iron, 12));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(new ItemStack(ModItems.cell_deuterium, 10));
			break;
		case MIKE_COOLER:
			list.add(new ItemStack(ModItems.plate_iron, 8));
			list.add(new ItemStack(ModItems.coil_copper, 5));
			list.add(new ItemStack(ModItems.coil_tungsten, 5));
			list.add(new ItemStack(ModItems.motor, 2));
			break;
		case FLEIJA_IGNITER:
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.wire_schrabidium, 2));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 1));
			break;
		case FLEIJA_CORE:
			list.add(new ItemStack(ModItems.nugget_u235, 8));
			list.add(new ItemStack(ModItems.nugget_neptunium, 2));
			list.add(new ItemStack(ModItems.nugget_beryllium, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			break;
		case FLEIJA_PROPELLANT:
			list.add(new ItemStack(Blocks.tnt, 3));
			list.add(new ItemStack(ModItems.plate_schrabidium, 8));
			break;
		case SOLINIUM_IGNITER:
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 1));
			list.add(new ItemStack(ModItems.coil_gold, 1));
			break;
		case SOLINIUM_CORE:
			list.add(new ItemStack(ModItems.nugget_solinium, 9));
			list.add(new ItemStack(ModItems.nugget_euphemium, 1));
			break;
		case SOLINIUM_PROPELLANT:
			list.add(new ItemStack(Blocks.tnt, 3));
			list.add(new ItemStack(ModItems.neutron_reflector, 2));
			list.add(new ItemStack(ModItems.plate_polymer, 6));
			list.add(new ItemStack(ModItems.wire_tungsten, 6));
			list.add(new ItemStack(ModItems.biomass_compressed, 4));
			break;
		case COMPONENT_LIMITER:
			list.add(new ItemStack(ModItems.hull_big_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 32));
			list.add(new ItemStack(ModItems.plate_titanium, 18));
			list.add(new ItemStack(ModItems.plate_desh, 12));
			list.add(new ItemStack(ModItems.pipes_steel, 4));
			list.add(new ItemStack(ModItems.circuit_gold, 8));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 4));
			list.add(new ItemStack(ModItems.ingot_starmetal, 14));
			list.add(new ItemStack(ModItems.plate_dalekanium, 5));
			list.add(new ItemStack(ModItems.powder_magic, 16));
			list.add(new ItemStack(ModBlocks.fwatz_computer, 3));
			break;
		case COMPONENT_EMITTER:
			list.add(new ItemStack(ModItems.hull_big_steel, 3));
			list.add(new ItemStack(ModItems.hull_big_titanium, 2));
			list.add(new ItemStack(ModItems.plate_steel, 32));
			list.add(new ItemStack(ModItems.plate_lead, 24));
			list.add(new ItemStack(ModItems.plate_desh, 24));
			list.add(new ItemStack(ModItems.pipes_steel, 8));
			list.add(new ItemStack(ModItems.circuit_gold, 12));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 8));
			list.add(new ItemStack(ModItems.ingot_starmetal, 26));
			list.add(new ItemStack(ModItems.powder_magic, 48));
			list.add(new ItemStack(ModBlocks.fwatz_computer, 2));
			list.add(new ItemStack(ModItems.crystal_xen, 1));
			break;
		case AMS_LIMITER:
			list.add(new ItemStack(ModItems.component_limiter, 5));
			list.add(new ItemStack(ModItems.plate_steel, 64));
			list.add(new ItemStack(ModItems.plate_titanium, 128));
			list.add(new ItemStack(ModItems.plate_dineutronium, 16));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 6));
			list.add(new ItemStack(ModItems.pipes_steel, 16));
			list.add(new ItemStack(ModItems.motor, 12));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 12));
			list.add(new ItemStack(ModItems.entanglement_kit, 1));
			break;
		case AMS_EMITTER:
			list.add(new ItemStack(ModItems.component_emitter, 16));
			list.add(new ItemStack(ModItems.plate_steel, 128));
			list.add(new ItemStack(ModItems.plate_titanium, 192));
			list.add(new ItemStack(ModItems.plate_dineutronium, 32));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 12));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 24));
			list.add(new ItemStack(ModItems.entanglement_kit, 3));
			list.add(new ItemStack(ModItems.crystal_horn, 1));
			list.add(new ItemStack(ModBlocks.fwatz_core, 1));
			break;
		case RADAR:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.plate_polymer, 24));
			list.add(new ItemStack(ModItems.magnetron, 10));
			list.add(new ItemStack(ModItems.motor, 3));
			list.add(new ItemStack(ModItems.circuit_gold, 4));
			list.add(new ItemStack(ModItems.coil_copper, 12));
			break;
		case FORCEFIELD:
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.plate_desh, 4));
			list.add(new ItemStack(ModItems.coil_gold_torus, 6));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 12));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.upgrade_radius, 1));
			list.add(new ItemStack(ModItems.upgrade_health, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 1));
			list.add(new ItemStack(ModBlocks.machine_transformer, 1));
			break;
		default:
			list.add(new ItemStack(Items.stick));
			break;
		}
		
		if(list.isEmpty())
			return null;
		else
			return list;
	}
	
	public static ItemStack getOutputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemAssemblyTemplate))
			return null;
		
		EnumAssemblyTemplate template = ItemAssemblyTemplate.EnumAssemblyTemplate.getEnum(stack.getItemDamage());
		
		if(template.output != null)
			return template.output;
		
		ItemStack output = null;
		
		switch(template) {
        case IRON_PLATE:
			output = new ItemStack(ModItems.plate_iron, 2);
			break;
		case GOLD_PLATE:
			output = new ItemStack(ModItems.plate_gold, 2);
			break;
		case TITANIUM_PLATE:
			output = new ItemStack(ModItems.plate_titanium, 2);
			break;
		case ALUMINIUM_PLATE:
			output = new ItemStack(ModItems.plate_aluminium, 2);
			break;
		case STEEL_PLATE:
			output = new ItemStack(ModItems.plate_steel, 2);
			break;
		case LEAD_PLATE:
			output = new ItemStack(ModItems.plate_lead, 2);
			break;
		case COPPER_PLATE:
			output = new ItemStack(ModItems.plate_copper, 2);
			break;
		case ADVANCED_PLATE:
			output = new ItemStack(ModItems.plate_advanced_alloy, 2);
			break;
		case SCHRABIDIUM_PLATE:
			output = new ItemStack(ModItems.plate_schrabidium, 2);
			break;
		case CMB_PLATE:
			output = new ItemStack(ModItems.plate_combine_steel, 2);
			break;
		case SATURN_PLATE:
			output = new ItemStack(ModItems.plate_saturnite, 2);
			break;
		case ALUMINIUM_WIRE:
			output = new ItemStack(ModItems.wire_aluminium, 6);
			break;
		case COPPER_WIRE:
			output = new ItemStack(ModItems.wire_copper, 6);
			break;
		case TUNGSTEN_WIRE:
			output = new ItemStack(ModItems.wire_tungsten, 6);
			break;
		case REDCOPPER_WIRE:
			output = new ItemStack(ModItems.wire_red_copper, 6);
			break;
		case ADVANCED_WIRE:
			output = new ItemStack(ModItems.wire_advanced_alloy, 6);
			break;
		case GOLD_WIRE:
			output = new ItemStack(ModItems.wire_gold, 6);
			break;
		case SCHRABIDIUM_WIRE:
			output = new ItemStack(ModItems.wire_schrabidium, 6);
			break;
		case MAGNETIZED_WIRE:
			output = new ItemStack(ModItems.wire_magnetized_tungsten, 6);
			break;
		case CIRCUIT_1:
			output = new ItemStack(ModItems.circuit_aluminium, 1);
			break;
		case SCHRABIDIUM_HAMMER:
			output = new ItemStack(ModItems.schrabidium_hammer, 1);
			break;
		case MIXED_PLATE:
			output = new ItemStack(ModItems.plate_mixed, 6);
			break;
		case HAZMAT_CLOTH:
			output = new ItemStack(ModItems.hazmat_cloth, 4);
			break;
		case ASBESTOS_CLOTH:
			output = new ItemStack(ModItems.asbestos_cloth, 4);
			break;
		case COAL_FILTER:
			output = new ItemStack(ModItems.filter_coal, 1);
			break;
		case CENTRIFUGE_ELEMENT:
			output = new ItemStack(ModItems.centrifuge_element, 1);
			break;
		case CENTRIFUGE_TOWER:
			output = new ItemStack(ModItems.centrifuge_tower, 1);
			break;
		case DEE_MAGNET:
			output = new ItemStack(ModItems.magnet_dee, 1);
			break;
		case FLAT_MAGNET:
			output = new ItemStack(ModItems.magnet_circular, 1);
			break;
		case CYCLOTRON_TOWER:
			output = new ItemStack(ModItems.cyclotron_tower, 1);
			break;
		case REACTOR_CORE:
			output = new ItemStack(ModItems.reactor_core, 1);
			break;
		case RTG_UNIT:
			output = new ItemStack(ModItems.rtg_unit, 2);
			break;
		case HEAT_UNIT:
			output = new ItemStack(ModItems.thermo_unit_empty, 1);
			break;
		case GRAVITY_UNIT:
			output = new ItemStack(ModItems.levitation_unit, 1);
			break;
		case TITANIUM_DRILL:
			output = new ItemStack(ModItems.drill_titanium, 1);
			break;
		case TELEPAD:
			output = new ItemStack(ModItems.telepad, 1);
			break;
		case TELEKIT:
			output = new ItemStack(ModItems.entanglement_kit, 1);
			break;
		case GEASS_REACTOR:
			output = new ItemStack(ModItems.dysfunctional_reactor, 1);
			break;
		case GENERATOR_FRONT:
			output = new ItemStack(ModItems.generator_front, 1);
			break;
		case WT1_GENERIC:
			output = new ItemStack(ModItems.warhead_generic_small, 1);
			break;
		case WT2_GENERIC:
			output = new ItemStack(ModItems.warhead_generic_medium, 1);
			break;
		case WT3_GENERIC:
			output = new ItemStack(ModItems.warhead_generic_large, 1);
			break;
		case WT1_FIRE:
			output = new ItemStack(ModItems.warhead_incendiary_small, 1);
			break;
		case WT2_FIRE:
			output = new ItemStack(ModItems.warhead_incendiary_medium, 1);
			break;
		case WT3_FIRE:
			output = new ItemStack(ModItems.warhead_incendiary_large, 1);
			break;
		case MISSILE_ASSEMBLY:
			output = new ItemStack(ModItems.missile_assembly, 1);
			break;
		case CARRIER:
			output = new ItemStack(ModItems.missile_carrier, 1);
			break;
		case WT1_CLUSTER:
			output = new ItemStack(ModItems.warhead_cluster_small, 1);
			break;
		case WT2_CLUSTER:
			output = new ItemStack(ModItems.warhead_cluster_medium, 1);
			break;
		case WT3_CLUSTER:
			output = new ItemStack(ModItems.warhead_cluster_large, 1);
			break;
		case WT1_BUSTER:
			output = new ItemStack(ModItems.warhead_buster_small, 1);
			break;
		case WT2_BUSTER:
			output = new ItemStack(ModItems.warhead_buster_medium, 1);
			break;
		case WT3_BUSTER:
			output = new ItemStack(ModItems.warhead_buster_large, 1);
			break;
		case W_NUCLEAR:
			output = new ItemStack(ModItems.warhead_nuclear, 1);
			break;
		case W_MIRVLET:
			output = new ItemStack(ModItems.warhead_mirvlet, 1);
			break;
		case W_MIRV:
			output = new ItemStack(ModItems.warhead_mirv, 1);
			break;
		case W_ENDOTHERMIC:
			output = new ItemStack(ModItems.warhead_thermo_endo, 1);
			break;
		case W_EXOTHERMIC:
			output = new ItemStack(ModItems.warhead_thermo_exo, 1);
			break;
		case T1_TANK:
			output = new ItemStack(ModItems.fuel_tank_small, 1);
			break;
		case T2_TANK:
			output = new ItemStack(ModItems.fuel_tank_medium, 1);
			break;
		case T3_TANK:
			output = new ItemStack(ModItems.fuel_tank_large, 1);
			break;
		case T1_THRUSTER:
			output = new ItemStack(ModItems.thruster_small, 1);
			break;
		case T2_THRUSTER:
			output = new ItemStack(ModItems.thruster_medium, 1);
			break;
		case T3_THRUSTER:
			output = new ItemStack(ModItems.thruster_large, 1);
			break;
		case NUCLEAR_THRUSTER:
			output = new ItemStack(ModItems.thruster_nuclear, 1);
			break;
		case SAT_BASE:
			output = new ItemStack(ModItems.sat_base, 1);
			break;
		case SAT_MAPPER:
			output = new ItemStack(ModItems.sat_head_mapper, 1);
			break;
		case SAT_SCANNER:
			output = new ItemStack(ModItems.sat_head_scanner, 1);
			break;
		case SAT_RADAR:
			output = new ItemStack(ModItems.sat_head_radar, 1);
			break;
		case SAT_LASER:
			output = new ItemStack(ModItems.sat_head_laser, 1);
			break;
		case SAT_RESONATOR:
			output = new ItemStack(ModItems.sat_head_resonator, 1);
			break;
		case SAT_FOEQ:
			output = new ItemStack(ModItems.sat_foeq, 1);
			break;
		case SAT_MINER:
			output = new ItemStack(ModItems.sat_miner, 1);
			break;
		case CHOPPER_HEAD:
			output = new ItemStack(ModItems.chopper_head, 1);
			break;
		case CHOPPER_GUN:
			output = new ItemStack(ModItems.chopper_gun, 1);
			break;
		case CHOPPER_BODY:
			output = new ItemStack(ModItems.chopper_torso, 1);
			break;
		case CHOPPER_TAIL:
			output = new ItemStack(ModItems.chopper_tail, 1);
			break;
		case CHOPPER_WING:
			output = new ItemStack(ModItems.chopper_wing, 1);
			break;
		case CHOPPER_BLADES:
			output = new ItemStack(ModItems.chopper_blades, 1);
			break;
		case CIRCUIT_2:
			output = new ItemStack(ModItems.circuit_copper, 1);
			break;
		case CIRCUIT_3:
			output = new ItemStack(ModItems.circuit_red_copper, 1);
			break;
		case RTG_PELLET:
			output = new ItemStack(ModItems.pellet_rtg, 1);
			break;
		case WEAK_PELLET:
			output = new ItemStack(ModItems.pellet_rtg_weak, 1);
			break;
		case FUSION_PELLET:
			output = new ItemStack(ModItems.tritium_deuterium_cake, 1);
			break;
		case CLUSTER_PELLETS:
			output = new ItemStack(ModItems.pellet_cluster, 1);
			break;
		case GUN_PELLETS:
			output = new ItemStack(ModItems.pellet_buckshot, 1);
			break;
		case AUSTRALIUM_MACHINE:
			output = new ItemStack(ModItems.australium_iii, 1);
			break;
		case MAGNETRON:
			output = new ItemStack(ModItems.magnetron, 1);
			break;
		case W_SP:
			output = new ItemStack(ModItems.pellet_schrabidium, 1);
			break;
		case W_SHE:
			output = new ItemStack(ModItems.pellet_hes, 1);
			break;
		case W_SME:
			output = new ItemStack(ModItems.pellet_mes, 1);
			break;
		case W_SLE:
			output = new ItemStack(ModItems.pellet_les, 1);
			break;
		case W_B:
			output = new ItemStack(ModItems.pellet_beryllium, 1);
			break;
		case W_N:
			output = new ItemStack(ModItems.pellet_neptunium, 1);
			break;
		case W_L:
			output = new ItemStack(ModItems.pellet_lead, 1);
			break;
		case W_A:
			output = new ItemStack(ModItems.pellet_advanced, 1);
			break;
		case UPGRADE_TEMPLATE:
			output = new ItemStack(ModItems.upgrade_template, 1);
			break;
		case UPGRADE_RED_I:
			output = new ItemStack(ModItems.upgrade_speed_1, 1);
			break;
		case UPGRADE_RED_II:
			output = new ItemStack(ModItems.upgrade_speed_2, 1);
			break;
		case UPGRADE_RED_III:
			output = new ItemStack(ModItems.upgrade_speed_3, 1);
			break;
		case UPGRADE_GREEN_I:
			output = new ItemStack(ModItems.upgrade_effect_1, 1);
			break;
		case UPGRADE_GREEN_II:
			output = new ItemStack(ModItems.upgrade_effect_2, 1);
			break;
		case UPGRADE_GREEN_III:
			output = new ItemStack(ModItems.upgrade_effect_3, 1);
			break;
		case UPGRADE_BLUE_I:
			output = new ItemStack(ModItems.upgrade_power_1, 1);
			break;
		case UPGRADE_BLUE_II:
			output = new ItemStack(ModItems.upgrade_power_2, 1);
			break;
		case UPGRADE_BLUE_III:
			output = new ItemStack(ModItems.upgrade_power_3, 1);
			break;
		case UPGRADE_PURPLE_I:
			output = new ItemStack(ModItems.upgrade_fortune_1, 1);
			break;
		case UPGRADE_PURPLE_II:
			output = new ItemStack(ModItems.upgrade_fortune_2, 1);
			break;
		case UPGRADE_PURPLE_III:
			output = new ItemStack(ModItems.upgrade_fortune_3, 1);
			break;
		case UPGRADE_PINK_I:
			output = new ItemStack(ModItems.upgrade_afterburn_1, 1);
			break;
		case UPGRADE_PINK_II:
			output = new ItemStack(ModItems.upgrade_afterburn_2, 1);
			break;
		case UPGRADE_PINK_III:
			output = new ItemStack(ModItems.upgrade_afterburn_3, 1);
			break;
		case UPGRADE_RANGE:
			output = new ItemStack(ModItems.upgrade_radius, 1);
			break;
		case UPGRADE_HEALTH:
			output = new ItemStack(ModItems.upgrade_health, 1);
			break;
		case FUSE:
			output = new ItemStack(ModItems.fuse, 1);
			break;
		case REDCOIL_CAPACITOR:
			output = new ItemStack(ModItems.redcoil_capacitor, 1);
			break;
		case TITANIUM_FILTER:
			output = new ItemStack(ModItems.titanium_filter, 1);
			break;
		case LITHIUM_BOX:
			output = new ItemStack(ModItems.part_lithium, 1);
			break;
		case BERYLLIUM_BOX:
			output = new ItemStack(ModItems.part_beryllium, 1);
			break;
		case COAL_BOX:
			output = new ItemStack(ModItems.part_carbon, 1);
			break;
		case COPPER_BOX:
			output = new ItemStack(ModItems.part_copper, 1);
			break;
		case PLUTONIUM_BOX:
			output = new ItemStack(ModItems.part_plutonium, 1);
			break;
		case THERMO_ELEMENT:
			output = new ItemStack(ModItems.thermo_element, 1);
			break;
		case LIMITER:
			output = new ItemStack(ModItems.limiter, 1);
			break;
		case ANGRY_METAL:
			output = new ItemStack(ModItems.plate_dalekanium, 1);
			break;
		case METEOR_BLOCK:
			output = new ItemStack(ModBlocks.block_meteor, 1);
			break;
		case CMB_TILE:
			output = new ItemStack(ModBlocks.cmb_brick, 8);
			break;
		case CMB_BRICKS:
			output = new ItemStack(ModBlocks.cmb_brick_reinforced, 8);
			break;
		case HATCH_FRAME:
			output = new ItemStack(ModBlocks.seal_frame, 1);
			break;
		case HATCH_CONTROLLER:
			output = new ItemStack(ModBlocks.seal_controller, 1);
			break;
		case BLAST_DOOR:
			output = new ItemStack(ModBlocks.vault_door, 1);
			break;
		case SLIDING_DOOR:
			output = new ItemStack(ModBlocks.blast_door, 1);
			break;
		case CENTRIFUGE:
			output = new ItemStack(ModBlocks.machine_centrifuge, 1);
			break;
		case CENTRIFUGE_GAS:
			output = new ItemStack(ModBlocks.machine_gascent, 1);
			break;
		case BREEDING_REACTOR:
			output = new ItemStack(ModBlocks.machine_reactor, 1);
			break;
		case RTG_FURNACE:
			output = new ItemStack(ModBlocks.machine_rtg_furnace_off, 1);
			break;
		case RAD_GEN:
			output = new ItemStack(ModBlocks.machine_radgen, 1);
			break;
		case DIESEL_GENERATOR:
			output = new ItemStack(ModBlocks.machine_diesel, 1);
			break;
		case SELENIUM_GENERATOR:
			output = new ItemStack(ModBlocks.machine_selenium, 1);
			break;
		case NUCLEAR_GENERATOR:
			output = new ItemStack(ModBlocks.machine_reactor_small, 1);
			break;
		case INDUSTRIAL_GENERATOR:
			output = new ItemStack(ModBlocks.machine_industrial_generator, 1);
			break;
		case CYCLOTRON:
			output = new ItemStack(ModBlocks.machine_cyclotron, 1);
			break;
		case RT_GENERATOR:
			output = new ItemStack(ModBlocks.machine_rtg_grey, 1);
			break;
		case BATTERY:
			output = new ItemStack(ModBlocks.machine_battery, 1);
			break;
		case BATTERY_L:
			output = new ItemStack(ModBlocks.machine_lithium_battery, 1);
			break;
		case BATTERY_S:
			output = new ItemStack(ModBlocks.machine_schrabidium_battery, 1);
			break;
		case BATTERY_D:
			output = new ItemStack(ModBlocks.machine_dineutronium_battery, 1);
			break;
		/*case HE_TO_RF:
			output = new ItemStack(ModBlocks.machine_converter_he_rf, 1);
			break;
		case RF_TO_HE:
			output = new ItemStack(ModBlocks.machine_converter_rf_he, 1);
			break;*/
		case SHREDDER:
			output = new ItemStack(ModBlocks.machine_shredder, 1);
			break;
		//case DEUTERIUM_EXTRACTOR:
		//	output = new ItemStack(ModBlocks.machine_deuterium, 1);
		//	break;
		case DERRICK:
			output = new ItemStack(ModBlocks.machine_well, 1);
			break;
		case PUMPJACK:
			output = new ItemStack(ModBlocks.machine_pumpjack, 1);
			break;
		case FLARE_STACK:
			output = new ItemStack(ModBlocks.machine_flare, 1);
			break;
		case REFINERY:
			output = new ItemStack(ModBlocks.machine_refinery, 1);
			break;
		case EPRESS:
			output = new ItemStack(ModBlocks.machine_epress, 1);
			break;
		case CHEMPLANT:
			output = new ItemStack(ModBlocks.machine_chemplant, 1);
			break;
		case TANK:
			output = new ItemStack(ModBlocks.machine_fluidtank, 1);
			break;
		case MINER:
			output = new ItemStack(ModBlocks.machine_drill, 1);
			break;
		case TURBOFAN:
			output = new ItemStack(ModBlocks.machine_turbofan, 1);
			break;
		case TELEPORTER:
			output = new ItemStack(ModBlocks.machine_teleporter, 1);
			break;
		case SCHRABTRANS:
			output = new ItemStack(ModBlocks.machine_schrabidium_transmutator, 1);
			break;
		case CMB_FURNACE:
			output = new ItemStack(ModBlocks.machine_combine_factory, 1);
			break;
		case FA_HULL:
			output = new ItemStack(ModBlocks.factory_advanced_hull, 1);
			break;
		case FA_HATCH:
			output = new ItemStack(ModBlocks.factory_advanced_furnace, 1);
			break;
		case FA_CORE:
			output = new ItemStack(ModBlocks.factory_advanced_core, 1);
			break;
		case FA_PORT:
			output = new ItemStack(ModBlocks.factory_advanced_conductor, 1);
			break;
		case LR_ELEMENT:
			output = new ItemStack(ModBlocks.reactor_element, 1);
			break;
		case LR_CONTROL:
			output = new ItemStack(ModBlocks.reactor_control, 1);
			break;
		case LR_HATCH:
			output = new ItemStack(ModBlocks.reactor_hatch, 1);
			break;
		case LR_PORT:
			output = new ItemStack(ModBlocks.reactor_conductor, 1);
			break;
		case LR_CORE:
			output = new ItemStack(ModBlocks.reactor_computer, 1);
			break;
		case LF_MAGNET:
			output = new ItemStack(ModBlocks.fusion_conductor, 1);
			break;
		case LF_CENTER:
			output = new ItemStack(ModBlocks.fusion_center, 1);
			break;
		case LF_MOTOR:
			output = new ItemStack(ModBlocks.fusion_motor, 1);
			break;
		case LF_HEATER:
			output = new ItemStack(ModBlocks.fusion_heater, 1);
			break;
		case LF_HATCH:
			output = new ItemStack(ModBlocks.fusion_hatch, 1);
			break;
		case LF_CORE:
			output = new ItemStack(ModBlocks.fusion_core, 1);
			break;
		case LW_ELEMENT:
			output = new ItemStack(ModBlocks.watz_element, 1);
			break;
		case LW_CONTROL:
			output = new ItemStack(ModBlocks.watz_control, 1);
			break;
		case LW_COOLER:
			output = new ItemStack(ModBlocks.watz_cooler, 1);
			break;
		case LW_STRUTURE:
			output = new ItemStack(ModBlocks.watz_end, 1);
			break;
		case LW_HATCH:
			output = new ItemStack(ModBlocks.watz_hatch, 1);
			break;
		case LW_PORT:
			output = new ItemStack(ModBlocks.watz_conductor, 1);
			break;
		case LW_CORE:
			output = new ItemStack(ModBlocks.watz_core, 1);
			break;
		case FW_PORT:
			output = new ItemStack(ModBlocks.fwatz_hatch, 1);
			break;
		case FW_MAGNET:
			output = new ItemStack(ModBlocks.fwatz_conductor, 1);
			break;
		case FW_COMPUTER:
			output = new ItemStack(ModBlocks.fwatz_computer, 1);
			break;
		case FW_CORE:
			output = new ItemStack(ModBlocks.fwatz_core, 1);
			break;
		case GADGET:
			output = new ItemStack(ModBlocks.nuke_gadget, 1);
			break;
		case LITTLE_BOY:
			output = new ItemStack(ModBlocks.nuke_boy, 1);
			break;
		case FAT_MAN:
			output = new ItemStack(ModBlocks.nuke_man, 1);
			break;
		case IVY_MIKE:
			output = new ItemStack(ModBlocks.nuke_mike, 1);
			break;
		case TSAR_BOMB:
			output = new ItemStack(ModBlocks.nuke_tsar, 1);
			break;
		case PROTOTYPE:
			output = new ItemStack(ModBlocks.nuke_prototype, 1);
			break;
		case FLEIJA:
			output = new ItemStack(ModBlocks.nuke_fleija, 1);
			break;
		case SOLINIUM:
			output = new ItemStack(ModBlocks.nuke_solinium, 1);
			break;
		case N2:
			output = new ItemStack(ModBlocks.nuke_n2, 1);
			break;
		case CUSTOM_NUKE:
			output = new ItemStack(ModBlocks.nuke_custom, 1);
			break;
		case BOMB_LEV:
			output = new ItemStack(ModBlocks.float_bomb, 1);
			break;
		case BOMB_ENDO:
			output = new ItemStack(ModBlocks.therm_endo, 1);
			break;
		case BOMB_EXO:
			output = new ItemStack(ModBlocks.therm_exo, 1);
			break;
		case LAUNCH_PAD:
			output = new ItemStack(ModBlocks.launch_pad, 1);
			break;
		case TURRET_LIGHT:
			output = new ItemStack(ModBlocks.turret_light, 1);
			break;
		case TURRET_HEAVY:
			output = new ItemStack(ModBlocks.turret_heavy, 1);
			break;
		case TURRET_ROCKET:
			output = new ItemStack(ModBlocks.turret_rocket, 1);
			break;
		case TURRET_FLAMER:
			output = new ItemStack(ModBlocks.turret_flamer, 1);
			break;
		case TURRET_TAU:
			output = new ItemStack(ModBlocks.turret_tau, 1);
			break;
		case TURRET_SPITFIRE:
			output = new ItemStack(ModBlocks.turret_spitfire, 1);
			break;
		case TURRET_CIWS:
			output = new ItemStack(ModBlocks.turret_cwis, 1);
			break;
		case TURRET_CHEAPO:
			output = new ItemStack(ModBlocks.turret_cheapo, 1);
			break;
		case HUNTER_CHOPPER:
			output = new ItemStack(ModItems.chopper, 1);
			break;
		case MISSILE_HE_1:
			output = new ItemStack(ModItems.missile_generic, 1);
			break;
		case MISSILE_FIRE_1:
			output = new ItemStack(ModItems.missile_incendiary, 1);
			break;
		case MISSILE_CLUSTER_1:
			output = new ItemStack(ModItems.missile_cluster, 1);
			break;
		case MISSILE_BUSTER_1:
			output = new ItemStack(ModItems.missile_buster, 1);
			break;
		case MISSILE_HE_2:
			output = new ItemStack(ModItems.missile_strong, 1);
			break;
		case MISSILE_FIRE_2:
			output = new ItemStack(ModItems.missile_incendiary_strong, 1);
			break;
		case MISSILE_CLUSTER_2:
			output = new ItemStack(ModItems.missile_cluster_strong, 1);
			break;
		case MISSILE_BUSTER_2:
			output = new ItemStack(ModItems.missile_buster_strong, 1);
			break;
		case MISSILE_HE_3:
			output = new ItemStack(ModItems.missile_burst, 1);
			break;
		case MISSILE_FIRE_3:
			output = new ItemStack(ModItems.missile_inferno, 1);
			break;
		case MISSILE_CLUSTER_3:
			output = new ItemStack(ModItems.missile_rain, 1);
			break;
		case MISSILE_BUSTER_3:
			output = new ItemStack(ModItems.missile_drill, 1);
			break;
		case MISSILE_NUCLEAR:
			output = new ItemStack(ModItems.missile_nuclear, 1);
			break;
		case MISSILE_MIRV:
			output = new ItemStack(ModItems.missile_nuclear_cluster, 1);
			break;
		case MISSILE_ENDO:
			output = new ItemStack(ModItems.missile_endo, 1);
			break;
		case MISSILE_EXO:
			output = new ItemStack(ModItems.missile_exo, 1);
			break;
		case DEFAB:
			output = new ItemStack(ModItems.gun_defabricator, 1);
			break;
		case MINI_NUKE:
			output = new ItemStack(ModItems.gun_fatman_ammo, 1);
			break;
		case MINI_MIRV:
			output = new ItemStack(ModItems.gun_mirv_ammo, 1);
			break;
		case DARK_PLUG:
			output = new ItemStack(ModItems.gun_osipr_ammo, 24);
			break;
		case COMBINE_BALL:
			output = new ItemStack(ModItems.gun_osipr_ammo2, 1);
			break;
		case GRENADE_FLAME:
			output = new ItemStack(ModItems.grenade_fire, 1);
			break;
		case GRENADE_SHRAPNEL:
			output = new ItemStack(ModItems.grenade_shrapnel, 1);
			break;
		case GRENAGE_CLUSTER:
			output = new ItemStack(ModItems.grenade_cluster, 1);
			break;
		case GREANADE_FLARE:
			output = new ItemStack(ModItems.grenade_flare, 1);
			break;
		case GRENADE_LIGHTNING:
			output = new ItemStack(ModItems.grenade_electric, 1);
			break;
		case GRENADE_IMPULSE:
			output = new ItemStack(ModItems.grenade_pulse, 4);
			break;
		case GRENADE_PLASMA:
			output = new ItemStack(ModItems.grenade_plasma, 2);
			break;
		case GRENADE_TAU:
			output = new ItemStack(ModItems.grenade_tau, 2);
			break;
		case GRENADE_SCHRABIDIUM:
			output = new ItemStack(ModItems.grenade_schrabidium, 1);
			break;
		case GRENADE_NUKE:
			output = new ItemStack(ModItems.grenade_nuclear, 1);
			break;
		case GRENADE_ZOMG:
			output = new ItemStack(ModItems.grenade_zomg, 1);
			break;
		case GRENADE_BLACK_HOLE:
			output = new ItemStack(ModItems.grenade_black_hole, 1);
			break;
		case POWER_FIST:
			ItemStack multitool = new ItemStack(ModItems.multitool_dig, 1);
			multitool.addEnchantment(Enchantment.looting, 3);
			multitool.addEnchantment(Enchantment.fortune, 3);
			output = multitool.copy();
			break;
		case GADGET_PROPELLANT:
			output = new ItemStack(ModItems.gadget_explosive, 1);
			break;
		case GADGET_WIRING:
			output = new ItemStack(ModItems.gadget_wireing, 1);
			break;
		case GADGET_CORE:
			output = new ItemStack(ModItems.gadget_core, 1);
			break;
		case BOY_SHIELDING:
			output = new ItemStack(ModItems.boy_shielding, 1);
			break;
		case BOY_TARGET:
			output = new ItemStack(ModItems.boy_target, 1);
			break;
		case BOY_BULLET:
			output = new ItemStack(ModItems.boy_bullet, 1);
			break;
		case BOY_PRPELLANT:
			output = new ItemStack(ModItems.boy_propellant, 1);
			break;
		case BOY_IGNITER:
			output = new ItemStack(ModItems.boy_igniter, 1);
			break;
		case MAN_PROPELLANT:
			output = new ItemStack(ModItems.man_explosive, 1);
			break;
		case MAN_IGNITER:
			output = new ItemStack(ModItems.man_igniter, 1);
			break;
		case MAN_CORE:
			output = new ItemStack(ModItems.man_core, 1);
			break;
		case MIKE_TANK:
			output = new ItemStack(ModItems.mike_core, 1);
			break;
		case MIKE_DEUT:
			output = new ItemStack(ModItems.mike_deut, 1);
			break;
		case MIKE_COOLER:
			output = new ItemStack(ModItems.mike_cooling_unit, 1);
			break;
		case FLEIJA_IGNITER:
			output = new ItemStack(ModItems.fleija_igniter, 1);
			break;
		case FLEIJA_CORE:
			output = new ItemStack(ModItems.fleija_core, 1);
			break;
		case FLEIJA_PROPELLANT:
			output = new ItemStack(ModItems.fleija_propellant, 1);
			break;
		case SOLINIUM_IGNITER:
			output = new ItemStack(ModItems.solinium_igniter, 1);
			break;
		case SOLINIUM_CORE:
			output = new ItemStack(ModItems.solinium_core, 1);
			break;
		case SOLINIUM_PROPELLANT:
			output = new ItemStack(ModItems.solinium_propellant, 1);
			break;
		case COMPONENT_LIMITER:
			output = new ItemStack(ModItems.component_limiter, 1);
			break;
		case COMPONENT_EMITTER:
			output = new ItemStack(ModItems.component_emitter, 1);
			break;
		case AMS_LIMITER:
			output = new ItemStack(ModBlocks.ams_limiter, 1);
			break;
		case AMS_EMITTER:
			output = new ItemStack(ModBlocks.ams_emitter, 1);
			break;
		case RADAR:
			output = new ItemStack(ModBlocks.machine_radar, 1);
			break;
		case FORCEFIELD:
			output = new ItemStack(ModBlocks.machine_forcefield, 1);
			break;
		default:
			output = new ItemStack(Items.stick, 1);
			break;
		}
		
		return output;
	}
	
	public Map<Object[], Object> getAssemblyRecipes() {

		Map<Object[], Object> recipes = new HashMap<Object[], Object>();
		
        for (int i = 0; i < EnumAssemblyTemplate.values().length; ++i)
        {
        	ItemStack[] array = new ItemStack[13];
        	array[12] = new ItemStack(ModItems.assembly_template, 1, i);
        	List<ItemStack> list = MachineRecipes.getRecipeFromTempate(array[12]);
        	
        	for(int j = 0; j < list.size(); j++)
        		array[j] = list.get(j).copy();
        	
        	for(int j = 0; j < 12; j++)
        		if(array[j] == null)
        			array[j] = new ItemStack(ModItems.nothing);
        	
        	recipes.put(array, MachineRecipes.getOutputFromTempate(array[12]));
        }
		
		return recipes;
	}
	
	public Map<Object[], Object[]> getChemistryRecipes() {

		Map<Object[], Object[]> recipes = new HashMap<Object[], Object[]>();
		
        for (int i = 0; i < ItemChemistryTemplate.EnumChemistryTemplate.values().length; ++i)
        {
        	ItemStack[] inputs = new ItemStack[7];
        	ItemStack[] outputs = new ItemStack[6];
        	inputs[6] = new ItemStack(ModItems.chemistry_template, 1, i);
        	
        	List<ItemStack> listIn = MachineRecipes.getChemInputFromTempate(inputs[6]);
        	if(listIn != null)
        		for(int j = 0; j < listIn.size(); j++)
        			if(listIn.get(j) != null)
        				inputs[j + 2] = listIn.get(j).copy();
        	
        	FluidStack[] fluidIn = MachineRecipes.getFluidInputFromTempate(inputs[6]);
        	for(int j = 0; j < fluidIn.length; j++)
        		if(fluidIn[j] != null)
        			inputs[j] = ItemFluidIcon.addQuantity(new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(fluidIn[j].type)), fluidIn[j].fill);
        	
        	ItemStack[] listOut = MachineRecipes.getChemOutputFromTempate(inputs[6]);
        	for(int j = 0; j < listOut.length; j++)
        		if(listOut[j] != null)
        			outputs[j + 2] = listOut[j].copy();
        	
        	FluidStack[] fluidOut = MachineRecipes.getFluidOutputFromTempate(inputs[6]);
        	for(int j = 0; j < fluidOut.length; j++)
        		if(fluidOut[j] != null)
        			outputs[j] = ItemFluidIcon.addQuantity(new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(fluidOut[j].type)), fluidOut[j].fill);
        	
        	for(int j = 0; j < inputs.length; j++)
        		if(inputs[j] == null)
        			inputs[j] = new ItemStack(ModItems.nothing);
        	
        	for(int j = 0; j < outputs.length; j++)
        		if(outputs[j] == null)
        			outputs[j] = new ItemStack(ModItems.nothing);
        	
        	recipes.put(inputs, outputs);
        }
		
		return recipes;
	}
	
	public Map<Object, Object[]> getRefineryRecipe() {

		Map<Object, Object[]> recipes = new HashMap<Object, Object[]>();

		ItemStack oil = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(FluidType.HOTOIL));
		oil.stackTagCompound = new NBTTagCompound();
		oil.stackTagCompound.setInteger("fill", 1000);
		
		ItemStack heavy = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(FluidType.HEAVYOIL));
		heavy.stackTagCompound = new NBTTagCompound();
		heavy.stackTagCompound.setInteger("fill", 500);
		
		ItemStack naphtha = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(FluidType.NAPHTHA));
		naphtha.stackTagCompound = new NBTTagCompound();
		naphtha.stackTagCompound.setInteger("fill", 250);
		
		ItemStack light = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(FluidType.LIGHTOIL));
		light.stackTagCompound = new NBTTagCompound();
		light.stackTagCompound.setInteger("fill", 150);
		
		ItemStack petroleum = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(FluidType.PETROLEUM));
		petroleum.stackTagCompound = new NBTTagCompound();
		petroleum.stackTagCompound.setInteger("fill", 100);
		
        recipes.put(oil , new ItemStack[] { 
        		heavy, 
        		naphtha, 
        		light, 
        		petroleum, 
        		new ItemStack(ModItems.sulfur, 1) });
		
		return recipes;
	}
	
	public Map<Object, Object> getBoilerRecipes() {

		Map<Object, Object> recipes = new HashMap<Object, Object>();
		
		for(int i = 0; i < FluidType.values().length; i++) {
			Object[] outs = getBoilerOutput(FluidType.getEnum(i));
			
			if(outs != null) {

				ItemStack in = new ItemStack(ModItems.fluid_icon, 1, i);
				in.stackTagCompound = new NBTTagCompound();
				in.stackTagCompound.setInteger("fill", (Integer) outs[2]);
				
				ItemStack out = new ItemStack(ModItems.fluid_icon, 1, ((FluidType)outs[0]).getID());
				out.stackTagCompound = new NBTTagCompound();
				out.stackTagCompound.setInteger("fill", (Integer) outs[1]);
				
				recipes.put(in, out);
			}
		}
		
		return recipes;
	}
	
	public static List<ItemStack> getChemInputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		switch(ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
        case CC_OIL:
			list.add(new ItemStack(Items.coal, 10));
			break;
        case CC_I:
			list.add(new ItemStack(Items.coal, 8));
			break;
        case CC_HEATING:
			list.add(new ItemStack(Items.coal, 8));
			break;
        case CC_HEAVY:
			list.add(new ItemStack(Items.coal, 10));
			break;
        case CC_NAPHTHA:
			list.add(new ItemStack(Items.coal, 10));
			break;
        case ASPHALT:
			list.add(new ItemStack(Blocks.gravel, 2));
			list.add(new ItemStack(Blocks.sand, 6));
			break;
        case CONCRETE:
			list.add(new ItemStack(Blocks.gravel, 8));
			list.add(new ItemStack(Blocks.sand, 8));
			break;
        case COOLANT:
			list.add(new ItemStack(ModItems.niter, 1));
			break;
        case CRYOGEL:
			list.add(new ItemStack(ModItems.powder_ice, 1));
			break;
        case DESH:
			list.add(new ItemStack(ModItems.powder_desh_mix, 1));
			break;
        case CIRCUIT_4:
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			list.add(new ItemStack(ModItems.powder_lapis, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 1));
			break;
        case CIRCUIT_5:
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			list.add(new ItemStack(ModItems.wire_schrabidium, 6));
			list.add(new ItemStack(ModItems.powder_diamond, 4));
			list.add(new ItemStack(ModItems.ingot_desh, 1));
			break;
        case POLYMER:
			list.add(new ItemStack(Items.coal, 2));
			list.add(new ItemStack(ModItems.fluorite, 1));
			break;
        case DEUTERIUM:
			list.add(new ItemStack(ModItems.sulfur, 1));
			break;
        case BP_BIOGAS:
			list.add(new ItemStack(ModItems.biomass, 16));
			break;
        case YELLOWCAKE:
			list.add(new ItemStack(ModItems.powder_uranium, 1));
			list.add(new ItemStack(ModItems.sulfur, 2));
			break;
        case UF6:
			list.add(new ItemStack(ModItems.powder_yellowcake, 1));
			list.add(new ItemStack(ModItems.fluorite, 3));
			break;
        case PUF6:
			list.add(new ItemStack(ModItems.powder_plutonium, 1));
			list.add(new ItemStack(ModItems.fluorite, 3));
			break;
        case SAS3:
			list.add(new ItemStack(ModItems.powder_schrabidium, 1));
			list.add(new ItemStack(ModItems.sulfur, 2));
			break;
        case NITAN:
			list.add(new ItemStack(ModItems.powder_nitan_mix, 2));
			break;
        case OIL_SAND:
			list.add(new ItemStack(ModBlocks.ore_oil_sand, 16));
			break;
        case DYN_SCHRAB:
			list.add(new ItemStack(ModItems.dynosphere_desh_charged, 3));
			list.add(new ItemStack(ModItems.ingot_uranium, 1));
			list.add(new ItemStack(ModItems.catalyst_clay, 8));
			break;
        case DYN_EUPH:
			list.add(new ItemStack(ModItems.dynosphere_schrabidium_charged, 1));
			list.add(new ItemStack(ModItems.ingot_plutonium, 1));
			list.add(new ItemStack(ModItems.catalyst_clay, 16));
			list.add(new ItemStack(ModItems.ingot_euphemium, 1));
			break;
        case DYN_DNT:
			list.add(new ItemStack(ModItems.dynosphere_euphemium_charged, 2));
			list.add(new ItemStack(ModItems.powder_spark_mix, 1));
			list.add(new ItemStack(ModItems.ingot_starmetal, 1));
			list.add(new ItemStack(ModItems.catalyst_clay, 32));
			break;
        case CORDITE:
			list.add(new ItemStack(ModItems.niter, 2));
			list.add(new ItemStack(Blocks.planks, 1));
			list.add(new ItemStack(Items.sugar, 1));
			break;
        case KEVLAR:
			list.add(new ItemStack(ModItems.niter, 2));
			list.add(new ItemStack(Items.brick, 1));
			list.add(new ItemStack(Items.coal, 1));
			break;
        case SOLID_FUEL:
			list.add(new ItemStack(ModItems.solid_fuel, 2));
			list.add(new ItemStack(ModItems.niter, 1));
			list.add(new ItemStack(Items.redstone, 1));
			break;
        case SATURN:
			list.add(new ItemStack(ModItems.powder_dura_steel, 1));
			list.add(new ItemStack(ModItems.powder_fire, 1));
			break;
		default:
			break;
		}
		
		if(list.isEmpty())
			return null;
		else
			return list;
	}
	
	public static FluidStack[] getFluidInputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		
		FluidStack[] input = new FluidStack[2];
		
		switch(ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
        case FP_HEAVYOIL:
			input[0] = new FluidStack(1000, FluidType.HEAVYOIL);
			break;
        case FP_SMEAR:
			input[0] = new FluidStack(1000, FluidType.SMEAR);
			break;
        case FP_NAPHTHA:
			input[0] = new FluidStack(1000, FluidType.NAPHTHA);
			break;
        case FP_LIGHTOIL:
			input[0] = new FluidStack(1000, FluidType.LIGHTOIL);
			break;
        case FR_REOIL:
			input[0] = new FluidStack(1000, FluidType.SMEAR);
			break;
        case FR_PETROIL:
			input[0] = new FluidStack(800, FluidType.RECLAIMED);
			input[1] = new FluidStack(200, FluidType.LUBRICANT);
			break;
        case FC_BITUMEN:
			input[0] = new FluidStack(1200, FluidType.BITUMEN);
			input[1] = new FluidStack(2400, FluidType.STEAM);
			break;
        case FC_I_NAPHTHA:
			input[0] = new FluidStack(1400, FluidType.SMEAR);
			input[1] = new FluidStack(800, FluidType.WATER);
			break;
        case FC_GAS_PETROLEUM:
			input[0] = new FluidStack(1800, FluidType.GAS);
			input[1] = new FluidStack(1200, FluidType.WATER);
			break;
        case FC_DIESEL_KEROSENE:
			input[0] = new FluidStack(1200, FluidType.DIESEL);
			input[1] = new FluidStack(2000, FluidType.STEAM);
			break;
        case FC_KEROSENE_PETROLEUM:
			input[0] = new FluidStack(1400, FluidType.KEROSENE);
			input[1] = new FluidStack(2000, FluidType.STEAM);
			break;
        case CC_I:
			input[0] = new FluidStack(800, FluidType.SMEAR);
			input[1] = new FluidStack(1800, FluidType.WATER);
			break;
        case CC_OIL:
			input[0] = new FluidStack(600, FluidType.OIL);
			input[1] = new FluidStack(1400, FluidType.STEAM);
			break;
        case CC_HEATING:
			input[0] = new FluidStack(800, FluidType.HEATINGOIL);
			input[1] = new FluidStack(2000, FluidType.STEAM);
			break;
        case CC_HEAVY:
			input[0] = new FluidStack(600, FluidType.HEAVYOIL);
			input[1] = new FluidStack(1400, FluidType.WATER);
			break;
        case CC_NAPHTHA:
			input[0] = new FluidStack(1200, FluidType.NAPHTHA);
			input[1] = new FluidStack(2400, FluidType.STEAM);
			break;
        case ASPHALT:
			input[0] = new FluidStack(8000, FluidType.BITUMEN);
			break;
        case CONCRETE:
			input[0] = new FluidStack(2000, FluidType.WATER);
			break;
        case COOLANT:
			input[0] = new FluidStack(1800, FluidType.WATER);
			break;
        case CRYOGEL:
			input[0] = new FluidStack(1800, FluidType.COOLANT);
			break;
        case DESH:
			input[0] = new FluidStack(800, FluidType.ACID);
			input[1] = new FluidStack(200, FluidType.LIGHTOIL);
			break;
        case PEROXIDE:
			input[0] = new FluidStack(1000, FluidType.WATER);
        	break;
        case CIRCUIT_4:
			input[0] = new FluidStack(400, FluidType.ACID);
        	break;
        case CIRCUIT_5:
			input[0] = new FluidStack(800, FluidType.ACID);
			input[1] = new FluidStack(400, FluidType.PETROLEUM);
        	break;
        case SF_OIL:
			input[0] = new FluidStack(350, FluidType.OIL);
        	break;
        case SF_HEAVYOIL:
			input[0] = new FluidStack(250, FluidType.HEAVYOIL);
        	break;
        case SF_SMEAR:
			input[0] = new FluidStack(200, FluidType.SMEAR);
        	break;
        case SF_HEATINGOIL:
			input[0] = new FluidStack(100, FluidType.HEATINGOIL);
        	break;
        case SF_RECLAIMED:
			input[0] = new FluidStack(200, FluidType.RECLAIMED);
        	break;
        case SF_PETROIL:
			input[0] = new FluidStack(250, FluidType.PETROIL);
        	break;
    	case SF_LUBRICANT:
			input[0] = new FluidStack(250, FluidType.LUBRICANT);
        	break;
    	case SF_NAPHTHA:
			input[0] = new FluidStack(300, FluidType.NAPHTHA);
        	break;
    	case SF_DIESEL:
			input[0] = new FluidStack(400, FluidType.DIESEL);
        	break;
    	case SF_LIGHTOIL:
			input[0] = new FluidStack(450, FluidType.LIGHTOIL);
        	break;
    	case SF_KEROSENE:
			input[0] = new FluidStack(550, FluidType.KEROSENE);
        	break;
    	case SF_GAS:
			input[0] = new FluidStack(750, FluidType.GAS);
        	break;
    	case SF_PETROLEUM:
			input[0] = new FluidStack(600, FluidType.PETROLEUM);
        	break;
    	case SF_BIOGAS:
			input[0] = new FluidStack(3500, FluidType.BIOGAS);
        	break;
    	case SF_BIOFUEL:
			input[0] = new FluidStack(1500, FluidType.BIOFUEL);
        	break;
        case POLYMER:
			input[0] = new FluidStack(600, FluidType.PETROLEUM);
        	break;
        case DEUTERIUM:
			input[0] = new FluidStack(4000, FluidType.WATER);
        	break;
        case STEAM:
			input[0] = new FluidStack(1000, FluidType.WATER);
        	break;
        case BP_BIOFUEL:
			input[0] = new FluidStack(2000, FluidType.BIOGAS);
        	break;
        case YELLOWCAKE:
			input[0] = new FluidStack(500, FluidType.ACID);
        	break;
        case UF6:
			input[0] = new FluidStack(1000, FluidType.WATER);
        	break;
        case PUF6:
			input[0] = new FluidStack(1000, FluidType.WATER);
        	break;
        case SAS3:
			input[0] = new FluidStack(2000, FluidType.ACID);
        	break;
        case NITAN:
			input[0] = new FluidStack(600, FluidType.KEROSENE);
        	break;
        case OIL_SAND:
			input[0] = new FluidStack(400, FluidType.BITUMEN);
        	break;
        case CORDITE:
			input[0] = new FluidStack(200, FluidType.HEATINGOIL);
        	break;
        case KEVLAR:
			input[0] = new FluidStack(100, FluidType.PETROLEUM);
        	break;
        case SOLID_FUEL:
			input[0] = new FluidStack(200, FluidType.PETROLEUM);
        	break;
    	case ELECTROLYSIS:
			input[0] = new FluidStack(8000, FluidType.WATER);
        	break;
    	case XENON:
			input[0] = new FluidStack(0, FluidType.NONE);
        	break;
    	case SATURN:
			input[0] = new FluidStack(100, FluidType.ACID);
			input[1] = new FluidStack(200, FluidType.MERCURY);
        	break;
		default:
			break;
		}
		
		return input;
	}
	
	public static ItemStack[] getChemOutputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		
		ItemStack[] output = new ItemStack[4];
		
		switch(ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
		case ASPHALT:
			output[0] = new ItemStack(ModBlocks.asphalt, 4);
			output[1] = new ItemStack(ModBlocks.asphalt, 4);
			output[2] = new ItemStack(ModBlocks.asphalt, 4);
			output[3] = new ItemStack(ModBlocks.asphalt, 4);
			break;
		case CONCRETE:
			output[0] = new ItemStack(ModBlocks.concrete_smooth, 4);
			output[1] = new ItemStack(ModBlocks.concrete_smooth, 4);
			output[2] = new ItemStack(ModBlocks.concrete_smooth, 4);
			output[3] = new ItemStack(ModBlocks.concrete_smooth, 4);
			break;
		case DESH:
			output[0] = new ItemStack(ModItems.ingot_desh, 1);
			break;
		case CIRCUIT_4:
			output[0] = new ItemStack(ModItems.circuit_gold, 1);
			break;
		case CIRCUIT_5:
			output[0] = new ItemStack(ModItems.circuit_schrabidium, 1);
			break;
        case SF_OIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case SF_HEAVYOIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case SF_SMEAR:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case SF_HEATINGOIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case SF_RECLAIMED:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case SF_PETROIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_LUBRICANT:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_NAPHTHA:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_DIESEL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_LIGHTOIL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_KEROSENE:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_GAS:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_PETROLEUM:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_BIOGAS:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
    	case SF_BIOFUEL:
			output[0] = new ItemStack(ModItems.solid_fuel, 1);
			output[1] = new ItemStack(ModItems.solid_fuel, 1);
			break;
        case POLYMER:
			output[0] = new ItemStack(ModItems.ingot_polymer, 1);
        	break;
        case YELLOWCAKE:
			output[0] = new ItemStack(ModItems.powder_yellowcake, 1);
        	break;
        case DYN_SCHRAB:
			output[0] = new ItemStack(ModItems.ingot_schrabidium, 1);
			output[1] = new ItemStack(ModItems.powder_desh, 12);
			output[2] = new ItemStack(ModItems.powder_desh_mix, 12);
        	break;
        case DYN_EUPH:
			output[0] = new ItemStack(ModItems.nugget_euphemium, 12);
			output[1] = new ItemStack(ModItems.powder_schrabidium, 4);
			output[2] = new ItemStack(ModItems.powder_power, 4);
        	break;
        case DYN_DNT:
			output[0] = new ItemStack(ModItems.ingot_dineutronium, 1);
			output[1] = new ItemStack(ModItems.powder_euphemium, 8);
			output[2] = new ItemStack(ModItems.powder_nitan_mix, 8);
        	break;
        case CORDITE:
			output[0] = new ItemStack(ModItems.cordite, 4);
        	break;
        case KEVLAR:
			output[0] = new ItemStack(ModItems.plate_kevlar, 4);
        	break;
        case SOLID_FUEL:
			output[0] = new ItemStack(ModItems.rocket_fuel, 1);
        	break;
        case SATURN:
			output[0] = new ItemStack(ModItems.ingot_saturnite, 1);
        	break;
		default:
			break;
		}
		
		return output;
	}
	
	public static FluidStack[] getFluidOutputFromTempate(ItemStack stack) {
		
		if(stack == null || !(stack.getItem() instanceof ItemChemistryTemplate))
			return null;
		
		FluidStack[] output = new FluidStack[2];
		
		switch(ItemChemistryTemplate.EnumChemistryTemplate.getEnum(stack.getItemDamage())) {
        case FP_HEAVYOIL:
			output[0] = new FluidStack(300, FluidType.BITUMEN);
			output[1] = new FluidStack(700, FluidType.SMEAR);
			break;
        case FP_SMEAR:
			output[0] = new FluidStack(600, FluidType.HEATINGOIL);
			output[1] = new FluidStack(400, FluidType.LUBRICANT);
			break;
        case FP_NAPHTHA:
			output[0] = new FluidStack(400, FluidType.HEATINGOIL);
			output[1] = new FluidStack(600, FluidType.DIESEL);
			break;
        case FP_LIGHTOIL:
			output[0] = new FluidStack(400, FluidType.DIESEL);
			output[1] = new FluidStack(600, FluidType.KEROSENE);
			break;
        case FR_REOIL:
			output[0] = new FluidStack(800, FluidType.RECLAIMED);
			break;
        case FR_PETROIL:
			output[0] = new FluidStack(1000, FluidType.PETROIL);
			break;
        case FC_BITUMEN:
			output[0] = new FluidStack(1000, FluidType.OIL);
			output[1] = new FluidStack(200, FluidType.PETROLEUM);
			break;
        case FC_I_NAPHTHA:
			output[0] = new FluidStack(800, FluidType.NAPHTHA);
			break;
        case FC_GAS_PETROLEUM:
			output[0] = new FluidStack(800, FluidType.PETROLEUM);
			break;
        case FC_DIESEL_KEROSENE:
			output[0] = new FluidStack(400, FluidType.KEROSENE);
			break;
        case FC_KEROSENE_PETROLEUM:
			output[0] = new FluidStack(800, FluidType.PETROLEUM);
			break;
        case CC_OIL:
			output[0] = new FluidStack(2000, FluidType.OIL);
			break;
        case CC_I:
			output[0] = new FluidStack(1600, FluidType.SMEAR);
			break;
        case CC_HEATING:
			output[0] = new FluidStack(1800, FluidType.HEATINGOIL);
			break;
        case CC_HEAVY:
			output[0] = new FluidStack(1800, FluidType.HEAVYOIL);
			break;
        case CC_NAPHTHA:
			output[0] = new FluidStack(2000, FluidType.NAPHTHA);
			break;
        case COOLANT:
			output[0] = new FluidStack(2000, FluidType.COOLANT);
			break;
        case CRYOGEL:
			output[0] = new FluidStack(2000, FluidType.CRYOGEL);
			break;
        case PEROXIDE:
			output[0] = new FluidStack(800, FluidType.ACID);
			break;
        case DEUTERIUM:
			output[0] = new FluidStack(500, FluidType.DEUTERIUM);
        	break;
        case STEAM:
			output[0] = new FluidStack(1000, FluidType.STEAM);
        	break;
        case BP_BIOGAS:
			output[0] = new FluidStack(4000, FluidType.BIOGAS);
        	break;
        case BP_BIOFUEL:
			output[0] = new FluidStack(1000, FluidType.BIOFUEL);
        	break;
        case UF6:
			output[0] = new FluidStack(1000, FluidType.UF6);
        	break;
        case PUF6:
			output[0] = new FluidStack(1000, FluidType.PUF6);
        	break;
        case SAS3:
			output[0] = new FluidStack(1000, FluidType.SAS3);
        	break;
        case NITAN:
			output[0] = new FluidStack(1000, FluidType.NITAN);
        	break;
        case OIL_SAND:
			output[0] = new FluidStack(1000, FluidType.BITUMEN);
        	break;
        case DYN_SCHRAB:
			output[0] = new FluidStack(50, FluidType.WATZ);
        	break;
        case DYN_EUPH:
			output[0] = new FluidStack(100, FluidType.WATZ);
        	break;
        case DYN_DNT:
			output[0] = new FluidStack(150, FluidType.WATZ);
        	break;
        case ELECTROLYSIS:
			output[0] = new FluidStack(400, FluidType.HYDROGEN);
			output[1] = new FluidStack(400, FluidType.OXYGEN);
        	break;
        case XENON:
			output[0] = new FluidStack(50, FluidType.XENON);
        	break;
		default:
			break;
		}
		
		return output;
	}
	
	public Map<Object, Object> getFluidContainers() {
		Map<Object, Object> map = new HashMap<Object, Object>();
		
		for(FluidContainer con : FluidContainerRegistry.instance.allContainers) {
			if(con != null) {
				ItemStack fluid = new ItemStack(ModItems.fluid_icon, 1, Arrays.asList(FluidType.values()).indexOf(con.type));
				fluid.stackTagCompound = new NBTTagCompound();
				fluid.stackTagCompound.setInteger("fill", con.content);
				map.put(fluid, con.fullContainer);
			}
		}
		
		return map;
	}
	
	public static class StackWrapper {

		public Item item;
		public int damage;
		
		public StackWrapper(ItemStack item) {
			this.item = item.getItem();
			this.damage = item.getItemDamage();
		}
		
		public StackWrapper(Item item) {
			this.item = item;
			this.damage = 0;
		}
		
		public StackWrapper(Item item, int meta) {
			this.item = item;
			this.damage = meta;
		}
		
		public ItemStack getStack() {
			return new ItemStack(item, 1, damage);
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + damage;
			result = prime * result + ((item == null) ? 0 : item.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			StackWrapper other = (StackWrapper) obj;
			if (damage != other.damage)
				return false;
			if (item == null) {
				if (other.item != null)
					return false;
			} else if (!item.equals(other.item))
				return false;
			return true;
		}
	}
}
