package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

//i love you
import com.hbm.items.ModItems2;
import com.hbm.blocks.ModBlocks2;
import static com.hbm.inventory.OreDictManager.DictFrame.*;
import static com.hbm.inventory.OreNames.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.config.GeneralConfig;
import com.hbm.hazard.HazardData;
import com.hbm.hazard.HazardEntry;
import com.hbm.hazard.HazardRegistry;
import com.hbm.hazard.HazardSystem;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.NTMMaterial.SmeltingBehavior;
import com.hbm.items.ModItems;
import com.hbm.items.ItemEnums.EnumAshType;
import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ItemEnums.EnumTarType;

import com.hbm.main.MainRegistry;
import com.hbm.util.Compat;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

//the more i optimize this, the more it starts looking like gregtech
public class OreDictManager2 {
	private static final HashMap<String, HashSet<String>> reRegistration = new HashMap();

	public static final DictFrame MANGANESE = new DictFrame("Manganese");

	public static final DictFrame COAL = new DictFrame("Coal");
	public static final DictFrame IRON = new DictFrame("Iron");
	public static final DictFrame GOLD = new DictFrame("Gold");
	public static final DictFrame LAPIS = new DictFrame("Lapis");
	public static final DictFrame REDSTONE = new DictFrame("Redstone");
	public static final DictFrame GLOWSTONE = new DictFrame("Glowstone");
	public static final DictFrame NETHERQUARTZ = new DictFrame("NetherQuartz");
	public static final DictFrame DIAMOND = new DictFrame("Diamond");
	public static final DictFrame EMERALD = new DictFrame("Emerald");


	public static final DictFrame TI = new DictFrame("Titanium");
	public static final DictFrame CU = new DictFrame("Copper");

	public static final DictFrame AL = new DictFrame("Aluminum");
	public static final DictFrame BI = new DictFrame("Bismuth");
	public static final DictFrame AS = new DictFrame("Arsenic");
	public static final DictFrame CA = new DictFrame("Calcium");
	public static final DictFrame B = new DictFrame("Boron");
	public static final DictFrame DESH = new DictFrame("WorkersAlloy");
	public static final DictFrame EUPH = new DictFrame("Euphemium");
	public static final DictFrame ASBESTOS = new DictFrame("Asbestos");
	public static final DictFrame OSMIRIDIUM = new DictFrame("Osmiridium");

	public static final DictFrame CINNABAR = new DictFrame("Cinnabar");
	public static final DictFrame S = new DictFrame("Sulfur");
	public static final DictFrame KNO = new DictFrame("Saltpeter");
	public static final DictFrame F = new DictFrame("Fluorite");
	public static final DictFrame BAUXITE = new DictFrame("Bauxite");
	public static final DictFrame ALEXANDRITE = new DictFrame("Alexandrite");
	public static final DictFrame VOLCANIC = new DictFrame("Volcanic");

	public static final DictFrame TIKITE = new DictFrame("Tikite");
	public static final DictFrame ND = new DictFrame("Neodymium");
	public static final DictFrame P_WHITE = new DictFrame("WhitePhosphorus");
	public static final DictFrame NA = new DictFrame("Sodium");

	
	public static void registerOres() {

		MANGANESE	.ore(ModBlocks2.ore_manganese)		.ingot(ModItems2.ingot_manganese);
		COAL	.ingot(Items.coal);
		IRON	.ore(ModBlocks.cluster_iron, ModBlocks.cluster_depth_iron);
		LAPIS	.ingot(ModItems.powder_lapis);
		NETHERQUARTZ	.ingot(Items.quartz);
		DIAMOND	.ingot(ModItems.powder_diamond);
		EMERALD	.ingot(ModItems.powder_emerald);	
		GLOWSTONE	.ingot(Items.glowstone_dust)	.ore(Blocks.glowstone);

		TI	.ore(ModBlocks.cluster_titanium,ModBlocks.cluster_depth_titanium);
		CU	.ore(ModBlocks.cluster_copper);
		AL	.ore(ModBlocks.cluster_aluminium);
		BI	.ore(ModBlocks.ancient_scrap);
		AS	.ore(ModBlocks.stone_porous,ModBlocks.dirt_oily, ModBlocks.dirt_dead, ModBlocks.stone_cracked, ModBlocks.sand_dirty, ModBlocks.sand_dirty_red );
		CA	.ore(fromOne(ModBlocks.stone_resource, EnumStoneType.LIMESTONE) );
		B	.ore(ModBlocks.ore_depth_borax );
		EUPH	.ore(ModBlocks.block_euphemium_cluster);
		ASBESTOS	.asbestos(1F)	.ore(fromOne(ModBlocks.stone_resource, EnumStoneType.ASBESTOS));
		OSMIRIDIUM	.ore(ModBlocks.ore_tektite_osmiridium);
        		REDSTONE	.ingot(ModItems.redstone_depleted);
		DESH	.ore(ModBlocks.ore_rare, ModBlocks.ore_gneiss_rare );

		S	.ingot(ModItems.sulfur)	.ore(fromOne(ModBlocks.stone_resource, EnumStoneType.SULFUR));
		KNO	.ingot(ModItems.niter);
		F	.ingot(ModItems.fluorite);
		CINNABAR	.ingot(ModItems.cinnebar);
		BAUXITE	.ore(fromOne(ModBlocks.stone_resource, EnumStoneType.BAUXITE));
		ALEXANDRITE	.ingot(ModItems.gem_alexandrite )	.gem(ModItems.gem_alexandrite )	.ore(ModBlocks.ore_alexandrite );
		VOLCANIC						.ingot(ModItems.gem_volcanic);													


		NA	.hydro(1F)	.ingot(ModItems2.ingot_sodium)	.ore(ModBlocks2.ore_sodium);											
		P_WHITE	.hot(5)	.ore(ModBlocks.ore_nether_fire);
		ND	.ingot(ModItems.powder_neodymium);
		TIKITE	.ingot(ModItems.powder_spark_mix )	.dust(ModItems.powder_nitan_mix )	.ore(ModBlocks.ore_tikite );

	}
	

	

	
	private static boolean recursionBrake = false;
	
	@SubscribeEvent
	public void onRegisterOre(OreRegisterEvent event) {
		
		if(recursionBrake)
			return;
		
		recursionBrake = true;
		
		HashSet<String> strings = reRegistration.get(event.Name);
		
		if(strings != null) {
			for(String name : strings) {
				OreDictionary.registerOre(name, event.Ore);
				MainRegistry.logger.info("Re-registration for " + event.Name + " to " + name);
			}
		}
		
		recursionBrake = false;
	}
	
	public static class DictFrame {
		public String[] mats;
		float hazMult = 1.0F;
		List<HazardEntry> hazards = new ArrayList();
		
		public DictFrame(String... mats) {
			this.mats = mats;
		}

		/*
		 * Quick access methods to grab ore names for recipes.
		 */
		public String any() {			return ANY			+ mats[0]; }
		public String nugget() {		return NUGGET		+ mats[0]; }
		public String tiny() {			return TINY			+ mats[0]; }
		public String ingot() {			return INGOT		+ mats[0]; }
		public String dustTiny() {		return DUSTTINY		+ mats[0]; }
		public String dust() {			return DUST			+ mats[0]; }
		public String gem() {			return GEM			+ mats[0]; }
		public String crystal() {		return CRYSTAL		+ mats[0]; }
		public String plate() {			return PLATE		+ mats[0]; }
		public String plateCast() {		return PLATECAST	+ mats[0]; }
		//public String plateWelded() {	return PLATEWELDED		+ mats[0]; }
		//public String heavyComp() {		return HEAVY_COMPONENT	+ mats[0]; }
		public String billet() {		return BILLET			+ mats[0]; }
		public String block() {			return BLOCK			+ mats[0]; }
		public String ore() {			return ORE				+ mats[0]; }
		public String[] anys() {		return appendToAll(ANY); }
		public String[] nuggets() {		return appendToAll(NUGGET); }
		public String[] tinys() {		return appendToAll(TINY); }
		public String[] allNuggets() {	return appendToAll(NUGGET, TINY); }
		public String[] ingots() {		return appendToAll(INGOT); }
		public String[] dustTinys() {	return appendToAll(DUSTTINY); }
		public String[] dusts() {		return appendToAll(DUST); }
		public String[] gems() {		return appendToAll(GEM); }
		public String[] crystals() {	return appendToAll(CRYSTAL); }
		public String[] plates() {		return appendToAll(PLATE); }
		public String[] plateCasts() {	return appendToAll(PLATECAST); }
		public String[] billets() {		return appendToAll(BILLET); }
		public String[] blocks() {		return appendToAll(BLOCK); }
		public String[] ores() {		return appendToAll(ORE); }
		
		/** Returns cast (triple) plates if 528 mode is enabled or normal plates if not */
		public String plate528() { return GeneralConfig.enable528 ? plateCast() : plate(); }
		
		private String[] appendToAll(String... prefix) {
			
			String[] names = new String[mats.length * prefix.length];
			
			for(int i = 0; i < mats.length; i++) {
				for(int j = 0; j < prefix.length; j++) {
					names[i * prefix.length + j] = prefix[j] + mats[i];
				}
			}
			return names;
		}

		public DictFrame rad(float rad) {		return this.haz(new HazardEntry(HazardRegistry.RADIATION, rad)); }
		public DictFrame hot(float time) {		return this.haz(new HazardEntry(HazardRegistry.HOT, time)); }
		public DictFrame blinding(float time) {	return this.haz(new HazardEntry(HazardRegistry.BLINDING, time)); }
		public DictFrame asbestos(float asb) {	return this.haz(new HazardEntry(HazardRegistry.ASBESTOS, asb)); }
		public DictFrame hydro(float h) {		return this.haz(new HazardEntry(HazardRegistry.HYDROACTIVE, h)); }
		
		public DictFrame haz(HazardEntry hazard) {
			hazards.add(hazard);
			return this;
		}
		
		/** Returns an ItemStack composed of the supplied item with the meta being the enum's ordinal. Purely syntactic candy */
		public static ItemStack fromOne(Item item, Enum en) {
			return new ItemStack(item, 1, en.ordinal());
		}
		public static ItemStack fromOne(Block block, Enum en) {
			return new ItemStack(block, 1, en.ordinal());
		}
		public static ItemStack fromOne(Item item, Enum en, int stacksize) {
			return new ItemStack(item, stacksize, en.ordinal());
		}
		public static ItemStack fromOne(Block block, Enum en, int stacksize) {
			return new ItemStack(block, stacksize, en.ordinal());
		}
		/** Same as fromOne but with an array of ItemStacks. The array type is Object[] so that the ODM methods work with it. 
		Generates ItemStacks for the entire enum class. */
		public static Object[] fromAll(Item item, Class<? extends Enum> en) {
			Enum[] vals = en.getEnumConstants();
			Object[] stacks = new Object[vals.length];
			
			for(int i = 0; i < vals.length; i++) {
				stacks[i] = new ItemStack(item, 1, vals[i].ordinal());
			}
			return stacks;
		}
		public static Object[] fromAll(Block block, Class<? extends Enum> en) {
			Enum[] vals = en.getEnumConstants();
			Object[] stacks = new Object[vals.length];
			
			for(int i = 0; i < vals.length; i++) {
				stacks[i] = new ItemStack(block, 1, vals[i].ordinal());
			}
			return stacks;
		}
		
		public DictFrame any(Object... thing) {
			return makeObject(ANY, thing);
		}
		public DictFrame nugget(Object... nugget) {
			hazMult = HazardRegistry.nugget;
			return makeObject(NUGGET, nugget).makeObject(TINY, nugget);
		}
		public DictFrame ingot(Object... ingot) {
			hazMult = HazardRegistry.ingot;
			return makeObject(INGOT, ingot);
		}
		public DictFrame dustSmall(Object... dustSmall) {
			hazMult = HazardRegistry.powder_tiny;
			return makeObject(DUSTTINY, dustSmall);
		}
		public DictFrame dust(Object... dust) {
			hazMult = HazardRegistry.powder;
			return makeObject(DUST, dust);
		}
		public DictFrame gem(Object... gem) {
			hazMult = HazardRegistry.gem;
			return makeObject(GEM, gem);
		}
		public DictFrame crystal(Object... crystal) {
			hazMult = HazardRegistry.gem;
			return makeObject(CRYSTAL, crystal);
		}
		public DictFrame plate(Object... plate) {
			hazMult = HazardRegistry.plate;
			return makeObject(PLATE, plate);
		}
		public DictFrame plateCast(Object... plate) {
			hazMult = HazardRegistry.plateCast;
			return makeObject(PLATECAST, plate);
		}
		public DictFrame billet(Object... billet) {
			hazMult = HazardRegistry.billet;
			return makeObject(BILLET, billet);
		}
		
		public DictFrame block(Object... block) {
			hazMult = HazardRegistry.block;
			return makeObject(BLOCK, block);
		}
		public DictFrame ore(Object... ore) {
			hazMult = HazardRegistry.ore;
			return makeObject(ORE, ore);
		}
		public DictFrame oreNether(Object... oreNether) {
			hazMult = HazardRegistry.ore;
			return makeObject(ORENETHER, oreNether);
		}

		public DictFrame makeObject(String tag, Object... objects) {
			
			for(Object o : objects) {
				if(o instanceof Item)		registerStack(tag, new ItemStack((Item) o));
				if(o instanceof Block)		registerStack(tag, new ItemStack((Block) o));
				if(o instanceof ItemStack)	registerStack(tag, (ItemStack) o);
			}
			
			return this;
		}
		
		public DictFrame makeItem(String tag, Item... items) {
			for(Item i : items) registerStack(tag, new ItemStack(i));
			return this;
		}
		public DictFrame makeStack(String tag, ItemStack... stacks) {
			for(ItemStack s : stacks) registerStack(tag, s);
			return this;
		}
		public DictFrame makeBlocks(String tag, Block... blocks) {
			for(Block b : blocks) registerStack(tag, new ItemStack(b));
			return this;
		}
		
		public static void registerHazards(List<HazardEntry> hazards, float hazMult, String dictKey) {
			
			if(!hazards.isEmpty() && hazMult > 0F) {
				HazardData data = new HazardData().setMutex(0b1);
				
				for(HazardEntry hazard : hazards) {
					data.addEntry(hazard.clone(hazMult));
				}
				
				HazardSystem.register(dictKey, data);
			}
		}
		
		public void registerStack(String tag, ItemStack stack) {
			for(String mat : mats) {
				OreDictionary.registerOre(tag + mat, stack);
				registerHazards(hazards, hazMult, tag + mat);
			}
			
			/*
			 * Fix for a small oddity in nuclearcraft: many radioactive elements do not have an ore prefix and the sizes
			 * seem generally inconsistent (TH and U are 20 "tiny"s per ingot while boron is 12), so we assume those to be ingots.
			 * Therefore we register all ingots a second time but without prefix. TODO: add a config option to disable this compat.
			 * I'd imagine greg's OD system might not like things without prefixes.
			 */
			if("ingot".equals(tag)) {
				registerStack("", stack);
			}
		}
	}
	
	public static class DictGroup {
		
		private String groupName;
		private HashSet<String> names = new HashSet();
		
		public DictGroup(String groupName) {
			this.groupName = groupName;
		}
		public DictGroup(String groupName, String... names) {
			this(groupName);
			this.addNames(names);
		}
		public DictGroup(String groupName, DictFrame... frames) {
			this(groupName);
			this.addFrames(frames);
		}
		
		public DictGroup addNames(String... names) {
			for(String mat : names) this.names.add(mat);
			return this;
		}
		public DictGroup addFrames(DictFrame... frames) {
			for(DictFrame frame : frames) this.addNames(frame.mats);
			return this;
		}
		
		/**
		 * Will add a reregistration entry for every mat name of every added DictFrame for the given prefix
		 * @param prefix The prefix of both the input and result of the reregistration
		 * @return
		 */
		public DictGroup addPrefix(String prefix, boolean inputPrefix) {
			
			String group = prefix + groupName;
			
			for(String name : names) {
				String original = (inputPrefix ? prefix : "") + name;
				addReRegistration(original, group);
			}
			
			return this;
		}
		/**
		 * Same thing as addPrefix, but the input for the reregistration is not bound by the prefix or any mat names
		 * @param prefix The prefix for the resulting reregistration entry (in full: prefix + group name)
		 * @param original The full original ore dict key, not bound by any naming conventions
		 * @return
		 */
		public DictGroup addFixed(String prefix, String original) {
			
			String group = prefix + groupName;
			addReRegistration(original, group);
			return this;
		}
		
		public String any() {			return ANY			+ groupName; }
		public String nugget() {		return NUGGET		+ groupName; }
		public String tiny() {			return TINY			+ groupName; }
		public String ingot() {			return INGOT		+ groupName; }
		public String dustTiny() {		return DUSTTINY		+ groupName; }
		public String dust() {			return DUST			+ groupName; }
		public String gem() {			return GEM			+ groupName; }
		public String crystal() {		return CRYSTAL		+ groupName; }
		public String plate() {			return PLATE		+ groupName; }
		public String plateCast() {		return PLATECAST	+ groupName; }
		//public String plateWelded() {	return PLATEWELDED		+ groupName; }
		//public String heavyComp() {		return HEAVY_COMPONENT	+ groupName; }
		public String billet() {		return BILLET			+ groupName; }
		public String block() {			return BLOCK			+ groupName; }
		public String ore() {			return ORE				+ groupName; }
	}
	
	private static void addReRegistration(String original, String additional) {
		
		HashSet<String> strings = reRegistration.get(original);
		
		if(strings == null)
			strings = new HashSet();
		
		strings.add(additional);
		
		reRegistration.put(original, strings);
	}
}
