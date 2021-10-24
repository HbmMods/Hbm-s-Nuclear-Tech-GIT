package com.hbm.hazard;

import static com.hbm.blocks.ModBlocks.block_mox_fuel;
import static com.hbm.blocks.ModBlocks.block_plutonium_fuel;
import static com.hbm.blocks.ModBlocks.block_schrabidium_fuel;
import static com.hbm.blocks.ModBlocks.block_semtex;
import static com.hbm.blocks.ModBlocks.block_thorium_fuel;
import static com.hbm.blocks.ModBlocks.block_uranium_fuel;
import static com.hbm.blocks.ModBlocks.lamp_demon;
import static com.hbm.items.ModItems.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.hazard.modifier.*;
import com.hbm.hazard.transformer.HazardTransformerRadiationNBT;
import com.hbm.hazard.type.*;
import com.hbm.items.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class HazardRegistry {

	//CO60		             5a		β−	030.00Rad/s	Spicy
	//TC99		       211,000a		β−	002.75Rad/s	Spicy
	//I181		           192h		β−	150.00Rad/s	2 much spice :(
	//XE135		             9h		β−	aaaaaaaaaaaaaaaa
	//CS137		            30a		β−	020.00Rad/s	Spicy
	//AU198		            64h		β−	500.00Rad/s	2 much spice :(
	//AT209		             5h		β+	like 2k or sth idk bruv
	//PO210		           138d		α	075.00Rad/s	Spicy
	//RA226		         1,600a		α	007.50Rad/s
	//TH232		14,000,000,000a		α	000.10Rad/s
	//U233		       160,000a		α	005.00Rad/s
	//U235		   700,000,000a		α	001.00Rad/s
	//U238		 4,500,000,000a		α	000.25Rad/s
	//NP237		     2,100,000a		α	002.50Rad/s
	//PU238		            88a		α	010.00Rad/s	Spicy
	//PU239		        24,000a		α	005.00Rad/s
	//PU240		         6,600a		α	007.50Rad/s
	//PU241		            14a		β−	025.00Rad/s	Spicy
	//AM241		           432a		α	008.50Rad/s
	//AM242m	           141a		β−	009.50Rad/s
	private static final boolean realisticRads = RadiationConfig.realisticRads;
	// Half-life: >1 year
	private static final float mod1 = 10000f;
	// Half-life: <1 year
	private static final float mod2 = 1000000f;
	// Testing the values
	private static final float sa326HL = 100000;
	private static final float sa327HL = sa326HL * 0.75f;
	public static final float co60 = realisticRads ? (mod1 / 5.3F) * 2 : 30.0F;
	public static final float sr90 = realisticRads ? mod1 / 28F : 15F;
	public static final float tc99 = realisticRads ? mod1 / 211000 : 2.75F;
	public static final float i131 = realisticRads ? mod2 / 0.021917808219178F : 150.0F;
	public static final float xe135 = realisticRads ? mod2 / 0.0010388127853881F : 1250.0F;
	public static final float cs137 = realisticRads ? mod1 / 30F : 20.0F;
	public static final float ta182 = realisticRads ? mod2 / 0.31232876712329F : 85F;
	public static final float au198 = realisticRads ? mod2 / 0.0073059360730594F : MainRegistry.isPolaroid11 ? 567.09F : 500.0F;// is le funi reference I swear
	public static final float at209 = realisticRads ? mod2 / 0.00022831050228311F : 2000.0F;
	public static final float po210 = realisticRads ? mod2 / 0.37808219178082F : 75.0F;
	public static final float ra226 = realisticRads ? mod1 / 16000 : 7.5F;
	public static final float ac227 = realisticRads ? mod1 / 22F : 25.0F;
	public static final float th232 = realisticRads ? mod1 / 14000000000L : 0.1F;
	public static final float pa233 = realisticRads ? mod2 / 0.073972602739726F : 100F;
	public static final float u233 = realisticRads ? mod1 / 160000F : 5.0F;
	public static final float u234 = realisticRads ? mod1 / 246000 : 2.5F;
	public static final float u235 = realisticRads ? mod1 / 700000000F : 1.0F;
	public static final float u238 = realisticRads ? mod1 / 4500000000F : 0.25F;
	public static final float u = realisticRads ? ((u238 * 8 + u235) * 0.1F) : 0.35F;
	public static final float tha = realisticRads ? (((th232 * 6) + (pa233 * 3)) * 0.1F) : 175F;
	public static final float thf = realisticRads ? (((th232 * 6) + (u233 * 3)) * 0.1F) : 1.75F;
	public static final float uf = realisticRads ? (u238 * 6F + u235 * 3F) / 10F : 0.5F;
	public static final float np237 = realisticRads ? mod1 / 2100000F : 2.5F;
	public static final float npf = realisticRads ? ((u238 * 2) + (np237)) * 0.1F : 1.5F;
	public static final float pu238 = realisticRads ? mod1 / 88F : 10.0F;
	public static final float pu239 = realisticRads ? mod1 / 24000F : 5.0F;
	public static final float pu240 = realisticRads ? mod1 / 6600F : 7.5F;
	public static final float pu241 = realisticRads ? mod1 / 14F : 25.0F;
	public static final float purg = realisticRads ?(((pu239 * 6F) + (pu240 * 3F)) * 0.1F) : 6.25F;
	public static final float pu = realisticRads ? (((pu238 * 3F) + (pu239 * 4F) + (pu240 * 2F)) * 0.1F) : 7.5F;
	public static final float puf = realisticRads ? (((purg * 2F) + u238) * 0.1F) : 4.25F;
	public static final float am241 = realisticRads ? mod1 / 432F : 8.5F;
	public static final float am242m = realisticRads ? mod1 / 141F : 9.5F;
	public static final float amrg = realisticRads ? ((am241 * 3) + (am242m * 6)) * 0.1F : 9.0F;
	public static final float amf = realisticRads ? ((u238 * 6) + (amrg * 3)) * 0.1F : 4.75F;
	public static final float mox = realisticRads ? ((u238 + purg + u235) * 0.1F) : 2.5F;
	public static final float cm242 = mod2 / 0.43835616438356F;// TODO No non-realistic RAD/s rates
	public static final float cm243 = mod1 / 29.1F;
	public static final float cm244 = mod1 / 18;
	public static final float cm245 = mod1 / 8500;
	public static final float cm246 = mod1 / 4730;
	public static final float cm247 = mod1 / 1.56e7F;
	public static final float cm248 = mod1 / 3.40e5F;
	public static final float cm250 = (mod1 / 9000) * 2;
	public static final float bk247 = mod1 / 1380;
	public static final float bk248 = mod1 / 300;
	public static final float bk249 = mod2 / 0.9F;
	public static final float cf249 = mod1 / 351;
	public static final float cf250 = mod1 / 13;
	public static final float cf251 = mod1 / 900;
	public static final float cf252 = mod1 / 2.6F;
	public static final float es254 = mod2 / 0.73972602739726F;
	public static final float md258 = mod2 / 0.13972602739726F;
	public static final float cn285 = mod2 / 9.1958396752917E-7F;
	public static final float cn286 = mod2 / 2.5367833587012E-7F;
	public static final float ts294 = realisticRads ? mod2 / 1.617199391172E-9F : 1000000;
	public static final float sa326 = realisticRads ? (mod1 / sa326HL) * 10 : 15.0F;
	public static final float sa327 = realisticRads ? (mod1 / sa327HL) * 10 : 17.5F;
	public static final float mes = realisticRads ? ((np237 * 3) + (sa326 * 3)) * 0.1F : 5.85F;
	public static final float hes = ((np237 * 2) + (sa326 * 5)) * 0.1F;
	public static final float les = ((np237 * 4) + sa326) * 0.1F;
	public static final float sas3 = 5F;
	public static final float radsource_mult = 0.5F;
	public static final float pobe = po210 * radsource_mult;
	public static final float rabe = ra226 * radsource_mult;
	public static final float pube = pu238 * radsource_mult;
	public static final float zfb_bi = u235 * 0.35F;
	public static final float zfb_pu241 = pu241 * 0.5F;
	public static final float zfb_am_mix = amrg * 0.5F;
	public static final float bf = 300_000.0F;
	public static final float bfb = 500_000.0F;

	public static final float sr = sa326 * 0.1F;
	public static final float sb = sa326 * 0.1F;
	public static final float trx = 25.0F;
	public static final float trn = 0.1F;
	public static final float wst = 15.0F;
	public static final float wstv = 7.5F;
	public static final float yc = u;
	public static final float fo = 10F;

	public static final float nugget = 0.1F;
	public static final float ingot = 1.0F;
	public static final float gem = 1.0F;
	public static final float plate = 1.0F;
	public static final float powder_mult = 3.0F;
	public static final float powder = ingot * powder_mult;
	public static final float powder_tiny = nugget * powder_mult;
	public static final float ore = ingot;
	public static final float block = 10.0F;
	public static final float crystal = block;
	public static final float billet = 0.5F;
	public static final float rtg = billet * 3;
	public static final float rod = 0.5F;
	public static final float rod_dual = rod * 2;
	public static final float rod_quad = rod * 4;
	public static final float rod_rbmk = rod * 8;

	public static final HazardTypeBase RADIATION = new HazardTypeRadiation();
	public static final HazardTypeBase DIGAMMA = new HazardTypeDigamma();
	public static final HazardTypeBase HOT = new HazardTypeHot();
	public static final HazardTypeBase BLINDING = new HazardTypeBlinding();
	public static final HazardTypeBase ASBESTOS = new HazardTypeAsbestos();
	public static final HazardTypeBase COAL = new HazardTypeCoal();
	public static final HazardTypeBase HYDROACTIVE = new HazardTypeHydroactive();
	public static final HazardTypeBase EXPLOSIVE = new HazardTypeExplosive();
	
	public static final HazardTypeBase BERYLLIUM = new HazardTypeBase()
	{
		@Override
		public void updateEntity(EntityItem item, float level){/* Not needed */}
		
		@Override
		public void onUpdate(EntityLivingBase target, float level, ItemStack stack)
		{
			if(!ArmorRegistry.hasProtection(target, 3, HazardClass.PARTICLE_FINE))
				HbmLivingProps.incStat(target, BERYLLIUM, level);
			else
				ArmorUtil.damageGasMaskFilter(target, (int) level);
		}
		
		@Override
		public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack,
				List<HazardModifier> modifiers)
		{
			list.add(EnumChatFormatting.DARK_GREEN + "[" + I18nUtil.resolveKey(HbmCollection.beryllium) + "]");
		}
		
		@Override
		public String toString()
		{
			return "BERYLLIUM";
		}
	};
	/** Aka nerve agent **/
	public static final HazardTypeBase CHEMICAL = new HazardTypeBase()
	{
		@Override
		public void updateEntity(EntityItem item, float level) {/* Not needed, yet? */}
		
		@Override
		public void onUpdate(EntityLivingBase target, float level, ItemStack stack)
		{
			if(!ArmorRegistry.hasProtection(target, 3, HazardClass.NERVE_AGENT))
				HbmLivingProps.incStat(target, this, level);
			else
				ArmorUtil.damageGasMaskFilter(target, (int) level);
		}
		
		@Override
		public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack,
				List<HazardModifier> modifiers)
		{
			list.add(EnumChatFormatting.YELLOW + "[" + I18nUtil.resolveKey(HbmCollection.chemical) + "]");
		}
	};
	
	public static final HazardTypeBase HEAVY_METAL = new HazardTypeBase()
	{
		@Override
		public void updateEntity(EntityItem item, float level)
		{
			// Nothing needed here
		}
		
		@Override
		public void onUpdate(EntityLivingBase target, float level, ItemStack stack)
		{
			// TODO
			if(!ArmorRegistry.hasProtection(target, 3, HazardClass.GAS_INERT))
				HbmLivingProps.incStat(target, this, level);
			else
				ArmorUtil.damageGasMaskFilter(target, (int) level);
		}
		
		@Override
		public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack,
				List<HazardModifier> modifiers)
		{
			list.add("[" + I18nUtil.resolveKey(HbmCollection.heavyMetal) + "]");
		}
	};
	
	public static void registerItems() {
		
		HazardSystem.register("ingotLanthanum", makeData(HYDROACTIVE));
		HazardSystem.register("dustLanthanum", makeData(HYDROACTIVE, powder));
		HazardSystem.register("dustTinyLanthanum", makeData(HYDROACTIVE, powder_tiny));
		HazardSystem.register("nuggetLanthanum", makeData(HYDROACTIVE, nugget));
		HazardSystem.register("blockLanthanum", makeData(HYDROACTIVE, block));
		
		HazardSystem.register(Items.gunpowder, makeData(EXPLOSIVE, 1F));
		HazardSystem.register(Blocks.tnt, makeData(EXPLOSIVE, 4F));
		HazardSystem.register(Items.pumpkin_pie, makeData(EXPLOSIVE, 4F));

		HazardSystem.register("dustCoal", makeData(COAL, powder));
		HazardSystem.register("dustTinyCoal", makeData(COAL, powder_tiny));
		HazardSystem.register("dustLignite", makeData(COAL, powder));
		HazardSystem.register("dustTinyLignite", makeData(COAL, powder_tiny));
		
		HazardSystem.register("dustBeryllium", makeData(BERYLLIUM, powder));
		HazardSystem.register("dustTinyBeryllium", makeData(BERYLLIUM, powder_tiny));
		
		HazardSystem.register(ingot_semtex, makeData(EXPLOSIVE, 10F));
		HazardSystem.register(block_semtex, makeData(EXPLOSIVE, 40F));
		HazardSystem.register(cordite, makeData(EXPLOSIVE, 2F));
		HazardSystem.register(ballistite, makeData(EXPLOSIVE, 1F));
		HazardSystem.register(powder_thermite, makeData(EXPLOSIVE));
		
		HazardSystem.register(insert_polonium, makeData(RADIATION, 100F));
		
		HazardSystem.register(part_einsteinium, makeData(RADIATION, es254 * powder));

		HazardSystem.register(demon_core_open, makeData(RADIATION, 5F));
		HazardSystem.register(demon_core_closed, makeData(RADIATION, 100_000F).addEntry(HOT, 40));
		HazardSystem.register(lamp_demon, makeData(RADIATION, 100_000F).addEntry(HOT, 40));
		
		HazardSystem.register(pellet_rtg, makeData(RADIATION, pu238 * rtg).addEntry(HOT, 10));
		HazardSystem.register(pellet_rtg_weak, makeData(RADIATION, (u238 + u238 + pu238) * rtg));
		HazardSystem.register(pellet_rtg_polonium, makeData(RADIATION, po210 * rtg).addEntry(HOT, 25));
		HazardSystem.register(pellet_rtg_actinium, makeData(RADIATION, ac227 * rtg).addEntry(HOT, 15));
		HazardSystem.register(pellet_rtg_gold, makeData(RADIATION, au198 * rtg).addEntry(HOT, 45));
		HazardSystem.register(pellet_rtg_strontium, makeData(RADIATION, sr90 * rtg).addEntry(HOT, 10));
		HazardSystem.register(pellet_rtg_americium, makeData(RADIATION, am241 * rtg));
		HazardSystem.register(pellet_rtg_berkelium, makeData(RADIATION, bk248 * rtg));
		
		if (!realisticRads)
			HazardSystem.register(cell_tritium, makeData(RADIATION, 0.001F));
		HazardSystem.register(cell_sas3, new HazardData().addEntry(RADIATION, sas3).addEntry(BLINDING, 3F));
		HazardSystem.register(cell_balefire, makeData(RADIATION, 50F));
		HazardSystem.register(powder_balefire, makeData(RADIATION, 500F).addEntry(HOT, 2));
		HazardSystem.register(egg_balefire_shard, makeData(RADIATION, bf * nugget).addEntry(HOT, 5F));
		HazardSystem.register(egg_balefire, makeData(RADIATION, bf * ingot).addEntry(HOT, 50));

		HazardSystem.register(catalyst_rare, makeData(RADIATION, (u238 + ac227) * powder));
		HazardSystem.register(catalyst_ten, makeData(RADIATION, ts294 * powder * 3).addEntry(HOT, 600 * 3));
		
		HazardSystem.register(crystal_trixite, makeData(RADIATION, trx * crystal));
		
		HazardSystem.register(nuclear_waste_long, makeData(RADIATION, 5F));
		HazardSystem.register(nuclear_waste_long_tiny, makeData(RADIATION, 0.5F));
		HazardSystem.register(nuclear_waste_short, new HazardData().addEntry(RADIATION, 30F).addEntry(HOT, 5F));
		HazardSystem.register(nuclear_waste_short_tiny, new HazardData().addEntry(RADIATION, 3F).addEntry(HOT, 5F));
		HazardSystem.register(nuclear_waste_long_depleted, makeData(RADIATION, 0.5F));
		HazardSystem.register(nuclear_waste_long_depleted_tiny, makeData(RADIATION, 0.05F));
		HazardSystem.register(nuclear_waste_short_depleted, makeData(RADIATION, 3F));
		HazardSystem.register(nuclear_waste_short_depleted_tiny, makeData(RADIATION, 0.3F));
		HazardSystem.register(ModBlocks.ancient_scrap, makeData(RADIATION, 150));
		HazardSystem.register(ModBlocks.block_corium, makeData(RADIATION, 500));
		HazardSystem.register(ModBlocks.block_corium_cobble, makeData(RADIATION, 250));
		HazardSystem.register(ModBlocks.block_waste, makeData(RADIATION, wst * block));
		HazardSystem.register(ModBlocks.block_waste_painted, makeData(RADIATION, wst * block));
		HazardSystem.register(ModBlocks.block_waste_vitrified, makeData(RADIATION, wstv * block));
		
		HazardSystem.register(ModBlocks.brick_asbestos, makeData(ASBESTOS));
		HazardSystem.register(ModBlocks.tile_lab, makeData(ASBESTOS));
		HazardSystem.register(ModBlocks.tile_lab_cracked, makeData(ASBESTOS, 2));
		HazardSystem.register(ModBlocks.tile_lab_broken, makeData(ASBESTOS, 3.5f));

		HazardSystem.register(debris_fuel, makeData(RADIATION, 15000).addEntry(HOT, 30));
		HazardSystem.register(debris_graphite, makeData(RADIATION, 50).addEntry(HOT, 5));
		HazardSystem.register(debris_metal, makeData(RADIATION, 10));
		HazardSystem.register(scrap_nuclear, makeData(RADIATION, 1F));
		HazardSystem.register(trinitite, makeData(RADIATION, trn * ingot));
		HazardSystem.register(nuclear_waste, makeData(RADIATION, wst * ingot));
		HazardSystem.register(billet_nuclear_waste, makeData(RADIATION, wst * billet));
		HazardSystem.register(nuclear_waste_tiny, makeData(RADIATION, wst * nugget));
		HazardSystem.register(nuclear_waste_vitrified, makeData(RADIATION, wstv * ingot));
		HazardSystem.register(nuclear_waste_vitrified_tiny, makeData(RADIATION, wstv * nugget));
		HazardSystem.register(block_waste, makeData(RADIATION, wst * block));
		HazardSystem.register(block_waste_painted, makeData(RADIATION, wst * block));
		HazardSystem.register(block_waste_vitrified, makeData(RADIATION, wstv * block));
		HazardSystem.register(ancient_scrap, makeData(RADIATION, 150F));
		HazardSystem.register(block_corium, makeData(RADIATION, 150F));
		HazardSystem.register(block_corium_cobble, makeData(RADIATION, 150F));
		
		HazardSystem.register(waste_uranium, makeData(RADIATION, 15F));
		HazardSystem.register(waste_thorium, makeData(RADIATION, 10F));
		HazardSystem.register(waste_plutonium, makeData(RADIATION, 15F));
		HazardSystem.register(waste_mox, makeData(RADIATION, 15F));
		HazardSystem.register(waste_schrabidium, new HazardData().addEntry(RADIATION, 15F).addEntry(HOT, 5F));
		HazardSystem.register(waste_uranium_hot, new HazardData().addEntry(RADIATION, 10F).addEntry(HOT, 5F));
		HazardSystem.register(waste_thorium_hot, new HazardData().addEntry(RADIATION, 15F).addEntry(HOT, 5F));
		HazardSystem.register(waste_plutonium_hot, new HazardData().addEntry(RADIATION, 15F).addEntry(HOT, 5F));
		HazardSystem.register(waste_mox_hot, new HazardData().addEntry(RADIATION, 15F).addEntry(HOT, 5F));
		HazardSystem.register(waste_schrabidium_hot, new HazardData().addEntry(RADIATION, 40F).addEntry(HOT, 5F).addEntry(BLINDING, 5F));
		
		HazardSystem.register(nugget_uranium_fuel, makeData(RADIATION, uf * nugget));
		HazardSystem.register(billet_uranium_fuel, makeData(RADIATION, uf * billet));
		HazardSystem.register(ingot_uranium_fuel, makeData(RADIATION, uf * ingot));
		HazardSystem.register(block_uranium_fuel, makeData(RADIATION, uf * block));
		
		HazardSystem.register(nugget_plutonium_fuel, makeData(RADIATION, puf * nugget));
		HazardSystem.register(billet_plutonium_fuel, makeData(RADIATION, puf * billet));
		HazardSystem.register(ingot_plutonium_fuel, makeData(RADIATION, puf * ingot));
		HazardSystem.register(block_plutonium_fuel, makeData(RADIATION, puf * block));
		
		HazardSystem.register(nugget_thorium_fuel, makeData(RADIATION, thf * nugget));
		HazardSystem.register(billet_thorium_fuel, makeData(RADIATION, thf * billet));
		HazardSystem.register(ingot_thorium_fuel, makeData(RADIATION, thf * ingot));
		HazardSystem.register(block_thorium_fuel, makeData(RADIATION, thf * block));
		
		HazardSystem.register(nugget_neptunium_fuel, makeData(RADIATION, npf * nugget));
		HazardSystem.register(billet_neptunium_fuel, makeData(RADIATION, npf * billet));
		HazardSystem.register(ingot_neptunium_fuel, makeData(RADIATION, npf * ingot));
		
		HazardSystem.register(nugget_mox_fuel, makeData(RADIATION, mox * nugget));
		HazardSystem.register(billet_mox_fuel, makeData(RADIATION, mox * billet));
		HazardSystem.register(ingot_mox_fuel, makeData(RADIATION, mox * ingot));
		HazardSystem.register(block_mox_fuel, makeData(RADIATION, mox * block));
		
		HazardSystem.register(nugget_americium_fuel, makeData(RADIATION, amf * nugget));
		HazardSystem.register(billet_americium_fuel, makeData(RADIATION, amf * billet));
		HazardSystem.register(ingot_americium_fuel, makeData(RADIATION, amf * ingot));
		
		HazardSystem.register(nugget_schrabidium_fuel, new HazardData().addEntry(RADIATION, mes * nugget).addEntry(BLINDING, 5F * nugget));
		HazardSystem.register(billet_schrabidium_fuel, new HazardData().addEntry(RADIATION, mes * billet).addEntry(BLINDING, 5F * billet));
		HazardSystem.register(ingot_schrabidium_fuel, new HazardData().addEntry(RADIATION, mes * ingot).addEntry(BLINDING, 5F * ingot));
		HazardSystem.register(block_schrabidium_fuel, new HazardData().addEntry(RADIATION, mes * block).addEntry(BLINDING, 5F * block));
		
		HazardSystem.register(nugget_hes, makeData(RADIATION, hes * nugget).addEntry(BLINDING, 5F * nugget));
		HazardSystem.register(billet_hes, makeData(RADIATION, hes * billet).addEntry(BLINDING, 5F * billet));
		HazardSystem.register(ingot_hes, makeData(RADIATION, hes * ingot).addEntry(BLINDING, 5F * ingot));
		
		HazardSystem.register(nugget_les, makeData(RADIATION, les * nugget).addEntry(BLINDING, 5F * nugget));
		HazardSystem.register(billet_les, makeData(RADIATION, les * billet).addEntry(BLINDING, 5F * billet));
		HazardSystem.register(ingot_les, makeData(RADIATION, les * ingot).addEntry(BLINDING, 5F * ingot));

		HazardSystem.register(man_core, makeData(RADIATION, pu239 * nugget * 8));
		HazardSystem.register(gadget_core, makeData(RADIATION, (pu239 * nugget * 7) + (u238 * nugget * 3)));
		HazardSystem.register(mike_core, makeData(RADIATION, u238 * nugget * 24));
		HazardSystem.register(boy_bullet, makeData(RADIATION, u235 * nugget * 3));
		HazardSystem.register(boy_target, makeData(RADIATION, u235 * nugget * 7));
		HazardSystem.register(fleija_igniter, makeData(RADIATION, sa326 * nugget * 2));
		HazardSystem.register(fleija_core, makeData(RADIATION, (u235 * 8 + np237 * 2) * nugget));
		HazardSystem.register(fleija_propellant, makeData(RADIATION, sa326 * ingot * 8));
		HazardSystem.register(solinium_core, makeData(RADIATION, sa327));
		
		HazardSystem.register(wire_schrabidium, makeData(RADIATION, sa326 * nugget).addEntry(BLINDING));
		HazardSystem.register(gun_revolver_schrabidium_ammo, makeData(RADIATION, sa326 * nugget).addEntry(BLINDING));
		
		HazardSystem.register(pellet_schrabidium, makeData(RADIATION, sa326 * 5).addEntry(BLINDING, 5));
		HazardSystem.register(pellet_hes, makeData(RADIATION, hes * 5).addEntry(BLINDING, 5));
		HazardSystem.register(pellet_mes, makeData(RADIATION, mes * 5).addEntry(BLINDING, 5));
		HazardSystem.register(pellet_les, makeData(RADIATION, les * 5).addEntry(BLINDING, 5));
		HazardSystem.register(pellet_solinium, makeData(RADIATION, sa327 * 5).addEntry(BLINDING, 5));
		HazardSystem.register(pellet_solinium_laced, makeData(RADIATION, sa327 * nugget * 31).addEntry(BLINDING, 5));
		
		HazardSystem.register(billet_balefire_gold, makeData(RADIATION, au198 * billet));
		HazardSystem.register(billet_po210be, makeData(RADIATION, pobe * billet));
		HazardSystem.register(billet_ra226be, makeData(RADIATION, rabe * billet));
		HazardSystem.register(billet_pu238be, makeData(RADIATION, pube * billet));
		HazardSystem.register(billet_ac227be, makeData(RADIATION, ac227 * billet / 2));
		HazardSystem.register(billet_cf252be, makeData(RADIATION, cf252 * billet / 2));
		HazardSystem.register(billet_sa327be, makeData(RADIATION, sa327 * billet / 2));

		registerRodRadiation(rod_th232, rod_dual_th232, rod_quad_th232, th232);
		registerRodRadiationExtra(rod_tha, rod_dual_tha, rod_quad_tha, tha, HOT, 5);
		registerRodRadiation(rod_uranium, rod_dual_uranium, rod_quad_uranium, u);
		registerRodRadiation(rod_u233, rod_dual_u233, rod_quad_u233, u233);
		registerRodRadiation(rod_u234, rod_dual_u234, rod_quad_u234, u234);
		registerRodRadiation(rod_u235, rod_dual_u235, rod_quad_u235, u235);
		registerRodRadiation(rod_u238, rod_dual_u238, rod_quad_u238, u238);
		registerRodRadiation(rod_plutonium, rod_dual_plutonium, rod_quad_plutonium, pu);
		registerRodRadiation(rod_pu238, rod_dual_pu238, rod_quad_pu238, pu238);
		registerRodRadiation(rod_pu239, rod_dual_pu239, rod_quad_pu239, pu239);
		registerRodRadiation(rod_pu240, rod_dual_pu240, rod_quad_pu240, pu240);
		registerRodRadiation(rod_neptunium, rod_dual_neptunium, rod_quad_neptunium, np237);
		registerRodRadiation(rod_polonium, rod_dual_polonium, rod_quad_polonium, po210);
		registerRodRadiationExtra(rod_schrabidium, rod_dual_schrabidium, rod_quad_schrabidium, sa326, BLINDING, 3F);
		registerRodRadiationExtra(rod_solinium, rod_dual_solinium, rod_quad_solinium, sa327, BLINDING, 3F);
		registerRodRadiation(rod_balefire, rod_dual_balefire, rod_quad_balefire, bf);
		registerRodRadiationExtra(rod_balefire_blazing, rod_dual_balefire_blazing, rod_quad_balefire_blazing, bfb, HOT, 5F);

		registerRBMKRod(rbmk_fuel_ueu, u * rod_rbmk, u * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_meu, uf * rod_rbmk, uf * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_heu233, u233 * rod_rbmk, u233 * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_heu235, u235 * rod_rbmk, u235 * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_thmeu, thf * rod_rbmk, u233 * rod_rbmk * 10);
		registerRBMKRod(rbmk_fuel_lep, puf * rod_rbmk, puf * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_mep, purg * rod_rbmk, purg * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_mox, mox * rod_rbmk, mox * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_hep239, pu239 * rod_rbmk, pu239 * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_hep241, pu241 * rod_rbmk, pu241 * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_lea, amf * rod_rbmk, amf * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_mea, amrg * rod_rbmk, amrg * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_hea241, am241 * rod_rbmk, am241 * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_hea242, am242m * rod_rbmk, am242m * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_men, npf * rod_rbmk, npf * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_hen, np237 * rod_rbmk, np237 * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_balefire_gold, au198 * rod_rbmk, au198 * rod_rbmk * 100);
		registerRBMKRod(rbmk_fuel_balefire, bf * rod_rbmk, bf * rod_rbmk * 0.1F);
		registerRBMKRod(rbmk_fuel_les, les * rod_rbmk, les * rod_rbmk * 100, 8);
		registerRBMKRod(rbmk_fuel_mes, mes * rod_rbmk, mes * rod_rbmk * 100, 24);
		registerRBMKRod(rbmk_fuel_hes, hes * rod_rbmk, hes * rod_rbmk * 100, 40);
		registerRBMKRod(rbmk_fuel_po210be, po210 * rod_rbmk / 2, po210 * rod_rbmk / 4);
		registerRBMKRod(rbmk_fuel_pu238be, pu238 * rod_rbmk / 2, pu238 * rod_rbmk / 4);
		registerRBMKRod(rbmk_fuel_ra226be, ra226 * rod_rbmk / 2, ra226 * rod_rbmk);
		registerRBMKRod(rbmk_fuel_ac227be, ac227 * rod_rbmk / 2, ac227 * rod_rbmk / 4);
		registerRBMKRod(rbmk_fuel_cf252be, cf252 * rod_rbmk / 2, cf252 * rod_rbmk / 4);
		registerRBMKRod(rbmk_fuel_sa327be, sa327 * rod_rbmk / 2, sa327 * rod_rbmk / 4, 8);
		registerRBMKRod(rbmk_fuel_zfb_am_mix, zfb_am_mix * rod_rbmk, zfb_am_mix * rod_rbmk * 2);
		registerRBMKRod(rbmk_fuel_zfb_bismuth, zfb_bi * rod_rbmk, zfb_bi * rod_rbmk / 2);
		registerRBMKRod(rbmk_fuel_zfb_pu241, zfb_pu241 * rod_rbmk, zfb_pu241 * rod_rbmk * 2);
		registerRBMXRod(rbmk_fuel_drx, 100, 100000000);

		registerRBMKPellet(rbmk_pellet_ueu, u * billet, u * billet * 100);
		registerRBMKPellet(rbmk_pellet_meu, uf * billet, uf * billet * 100);
		registerRBMKPellet(rbmk_pellet_heu233, u233 * billet, u233 * billet * 100);
		registerRBMKPellet(rbmk_pellet_heu235, u235 * billet, u235 * billet * 100);
		registerRBMKPellet(rbmk_pellet_thmeu, thf * billet, u233 * billet * 10);
		registerRBMKPellet(rbmk_pellet_lep, puf * billet, puf * billet * 100);
		registerRBMKPellet(rbmk_pellet_mep, purg * billet, purg * billet * 100);
		registerRBMKPellet(rbmk_pellet_mox, mox * billet, mox * billet * 100);
		registerRBMKPellet(rbmk_pellet_hep239, pu239 * billet, pu239 * billet * 100);
		registerRBMKPellet(rbmk_pellet_hep241, pu241 * billet, pu241 * billet * 100);
		registerRBMKPellet(rbmk_pellet_lea, amf * billet, amf * billet * 100);
		registerRBMKPellet(rbmk_pellet_mea, amrg * billet, amrg * billet * 100);
		registerRBMKPellet(rbmk_pellet_hea241, am241 * billet, am241 * billet* 100);
		registerRBMKPellet(rbmk_pellet_hea242, am242m * billet, am242m * billet * 100);
		registerRBMKPellet(rbmk_pellet_men, npf * billet, npf * billet * 100);
		registerRBMKPellet(rbmk_pellet_hen, np237 * billet, np237 * billet * 100);
		registerRBMKPellet(rbmk_pellet_les, les * billet, les * billet * 100, 8);
		registerRBMKPellet(rbmk_pellet_mes, mes * billet, mes * billet * 100, 24);
		registerRBMKPellet(rbmk_pellet_hes, hes * billet, hes * billet * 100, 40);
		registerRBMKPellet(rbmk_pellet_balefire, bf * billet, bf * billet * 0.1F);
		registerRBMKPellet(rbmk_pellet_po210be, pobe * billet, pobe * billet / 4);
		registerRBMKPellet(rbmk_pellet_pu238be, pube * billet, pube * billet / 4);
		registerRBMKPellet(rbmk_pellet_ra226be, rabe * billet, rabe * billet * 2);
		registerRBMKPellet(rbmk_pellet_ac227be, ac227 * billet / 2, ac227  * billet / 4);
		registerRBMKPellet(rbmk_pellet_cf252be, cf252 * billet / 2, cf252  * billet / 4);
		registerRBMKPellet(rbmk_pellet_sa327be, sa327 * billet / 2, sa327  * billet / 4, 8);
		registerRBMKPellet(rbmk_pellet_zfb_am_mix, zfb_am_mix * billet, zfb_am_mix * billet * 2);
		registerRBMKPellet(rbmk_pellet_zfb_bismuth, zfb_bi * billet, zfb_bi * billet / 2);
		registerRBMKPellet(rbmk_pellet_zfb_pu241, zfb_pu241 * billet, zfb_pu241 * billet * 2);
		registerRBMXPellet(rbmk_pellet_drx, 100 / 8, 100000000 / 8);

		HazardSystem.register(powder_yellowcake, makeData(RADIATION, yc * powder));
		HazardSystem.register(block_yellowcake, makeData(RADIATION, yc * block * powder_mult));
		HazardSystem.register(ModItems.fallout, makeData(RADIATION, fo * powder));
		HazardSystem.register(ModBlocks.fallout, makeData(RADIATION, fo * powder * 2));
		HazardSystem.register(ModBlocks.block_fallout, makeData(RADIATION, yc * block * powder_mult));
		
		//TODO
	}
	
	public static void registerTrafos() {
		HazardSystem.trafos.add(new HazardTransformerRadiationNBT());
		HazardSystem.trafos.add(new HazardTransformerDigammaNBT());
	}
	
	private static HazardData makeData() { return new HazardData(); }
	private static HazardData makeData(HazardTypeBase hazard) { return new HazardData().addEntry(hazard); }
	private static HazardData makeData(HazardTypeBase hazard, float level) { return new HazardData().addEntry(hazard, level); }
	private static HazardData makeData(HazardTypeBase hazard, float level, boolean override) { return new HazardData().addEntry(hazard, level, override); }
	
	private static void registerRodRadiation(Item single, Item dual, Item quad, float base) {
		HazardSystem.register(single, makeData(RADIATION, base * rod));
		HazardSystem.register(dual, makeData(RADIATION, base * rod_dual));
		HazardSystem.register(quad, makeData(RADIATION, base * rod_quad));
	}
	
	private static void registerRodRadiationExtra(Item single, Item dual, Item quad, float base, HazardTypeBase extra, float base2) {
		HazardSystem.register(single, new HazardData().addEntry(RADIATION, base * rod).addEntry(extra, base2 * rod));
		HazardSystem.register(dual, new HazardData().addEntry(RADIATION, base * rod_dual).addEntry(extra, base2 * rod_dual));
		HazardSystem.register(quad, new HazardData().addEntry(RADIATION, base * rod_quad).addEntry(extra, base2 * rod_quad));
	}
	
	private static void registerRBMKPellet(Item pellet, float base, float dep) { registerRBMKPellet(pellet, base, dep, 0F); }
	private static void registerRBMKPellet(Item pellet, float base, float dep, float blinding) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep)));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		HazardSystem.register(pellet, data);
	}
	
	private static void registerRBMXPellet(Item pellet, float base, float dep)
	{
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(DIGAMMA, base).addMod(new HazardModifierRBMKRadiation(dep)));
		HazardSystem.register(pellet, data);
	}
	
	private static void registerRBMXRod(Item rod, float base, float dep)
	{
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(DIGAMMA, base).addMod(new HazardModifierRBMKRadiation(dep)));
		HazardSystem.register(rod, data);
	}
	
	private static void registerRBMKRod(Item rod, float base, float dep) { registerRBMK(rod, base, dep, true, 0F); }
	private static void registerRBMKRod(Item rod, float base, float dep, float blinding) { registerRBMK(rod, base, dep, true, blinding); }
	
	private static void registerRBMK(Item rod, float base, float dep, boolean hot, float blinding) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep)));
		if(hot) data.addEntry(new HazardEntry(HOT, 0).addMod(new HazardModifierRBMKHot()));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		HazardSystem.register(rod, data);
	}
}
