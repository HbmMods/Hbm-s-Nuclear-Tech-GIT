package com.hbm.hazard;

import static com.hbm.config.RadiationConfig.realisticRads;
import static com.hbm.main.MainRegistry.isPolaroid11;
import static com.hbm.blocks.ModBlocks.*;
import static com.hbm.items.ModItems.*;
import static com.hbm.inventory.OreDictManager.*;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.hazard.modifier.*;
import com.hbm.hazard.transformer.*;
import com.hbm.hazard.type.*;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ItemAmmoEnums.Ammo357Magnum;
import com.hbm.items.ItemAmmoEnums.AmmoHandGrenade;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemBreedingRod.BreedingRodType;
import com.hbm.items.machine.ItemRTGPelletDepleted.DepletedRTGMaterial;
import com.hbm.items.special.ItemHolotapeImage.EnumHoloImage;
import com.hbm.items.special.ItemWasteLong;
import com.hbm.items.special.ItemWasteShort;
import com.hbm.lib.HbmCollection;
import com.hbm.util.Compat;
import com.hbm.util.Compat.ReikaIsotope;
import com.hbm.util.I18nUtil;

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
	//SR90		            29a		β−	015.00Rad/s Spicy
	//TC99		       211,000a		β−	002.75Rad/s	Spicy
	//I181		           192h		β−	150.00Rad/s	2 much spice :(
	//XE135		             9h		β−	aaaaaaaaaaaaaaaa
	//CS137		            30a		β−	020.00Rad/s	Spicy
	//AU198		            64h		β−	500.00Rad/s	2 much spice :(
	//PB209		             3h		β−	10,000.00Rad/s mama mia my face is melting off
	//AT209		             5h		β+	like 7.5k or sth idk bruv
	//PO210		           138d		α	075.00Rad/s	Spicy
	//RA226		         1,600a		α	007.50Rad/s
	//AC227		            22a		β−	030.00Rad/s Spicy
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
//	private static final boolean realisticRads = RadiationConfig.realisticRads;
	// Half-life: >1 year
	private static final float mod1 = 10000f;
	// Half-life: <1 year
	private static final float mod2 = 1000000f;
	// Testing the values
	private static final float sa326HL = 100000;
	private static final float sa327HL = sa326HL * 0.75f;
	public static final float co60 = realisticRads ? (mod1 / 5.3F) * 2 : 60.0F;
	public static final float sr90 = realisticRads ? mod1 / 28F : 15F;
	public static final float tc99 = realisticRads ? mod1 / 211000 : 2.75F;
	public static final float i131 = realisticRads ? mod2 / 0.021917808219178F : 150.0F;
	public static final float xe135 = realisticRads ? mod2 / 0.0010388127853881F : 1250.0F;
	public static final float cs137 = realisticRads ? mod1 / 30F : 20.0F;
	public static final float ta182 = realisticRads ? mod2 / 0.31232876712329F : 85F;
	public static final float au198 = realisticRads ? mod2 / 0.0073059360730594F : isPolaroid11() ? 567.09F : 500.0F;// is le funi reference I swear
	public static final float pb209 = realisticRads ? mod2 / 0.00037134703196347f : 10000.0F;
	public static final float at209 = realisticRads ? mod2 / 0.00022831050228311F : 2000.0F;
	public static final float pb200 = realisticRads ? mod2 / 0.0024543378995434F: 1500.0F;
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
	public static final float ts294 = realisticRads ? mod2 / 1.617199391172E-9F : 10000000;
	public static final float sa326 = realisticRads ? (mod1 / sa326HL) * 10 : 15.0F;
	public static final float sa327 = realisticRads ? (mod1 / sa327HL) * 10 : 17.5F;
	public static final float mes = realisticRads ? ((np237 * 3) + (sa326 * 3)) * 0.1F : 5.85F;
	public static final float hes = ((np237 * 2) + (sa326 * 5)) * 0.1F;
	public static final float les = ((np237 * 4) + sa326) * 0.1F;
	//AM242		           141a		β−	009.50Rad/s

	//simplified groups for ReC compat
	public static final float gen_S = 10_000F;
	public static final float gen_H = 2_000F;
	public static final float gen_10D = 100F;
	public static final float gen_100D = 80F;
	public static final float gen_1Y = 50F;
	public static final float gen_10Y = 30F;
	public static final float gen_100Y = 10F;
	public static final float gen_1K = 7.5F;
	public static final float gen_10K = 6.25F;
	public static final float gen_100K = 5F;
	public static final float gen_1M = 2.5F;
	public static final float gen_10M = 1.5F;
	public static final float gen_100M = 1F;
	public static final float gen_1B = 0.5F;
	public static final float gen_10B = 0.1F;

	public static final float saf = 5.85F;
	public static final float sas3 = 5F;
	public static final float gh336 = realisticRads ? mod1 / 200000 : 5.0F;
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
	public static final HazardTypeBase RADIATION_TYPES = new HazardTypeRadiationTypes();
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
			// TODO
//			if(!ArmorRegistry.hasProtection(target, 3, HazardClass.PARTICLE_FINE))
//				HbmLivingProps.incStat(target, BERYLLIUM, level);
//			else
//				ArmorUtil.damageGasMaskFilter(target, (int) level);
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
			// TODO
//			if(!ArmorRegistry.hasProtection(target, 3, HazardClass.NERVE_AGENT))
//				HbmLivingProps.incStat(target, this, level);
//			else
//				ArmorUtil.damageGasMaskFilter(target, (int) level);
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
//			if(!ArmorRegistry.hasProtection(target, 3, HazardClass.GAS_INERT))
//				HbmLivingProps.incStat(target, this, level);
//			else
//				ArmorUtil.damageGasMaskFilter(target, (int) level);
		}
		
		@Override
		public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack,
				List<HazardModifier> modifiers)
		{
			list.add("[" + I18nUtil.resolveKey(HbmCollection.heavyMetal) + "]");
		}
	};
	public static final float pureAlpha = HazardSystem.compactRadTypes(1, 0, 0, 0),
							lepRad = HazardSystem.compactRadTypes(7, 0, 2, 1),
							thmeuRad = HazardSystem.compactRadTypes(9, 0, 1, 0),
							moxRad = HazardSystem.compactRadTypes(95, 0, 4, 1),
							leaRad = HazardSystem.compactRadTypes(8, 0, 2, 0),
							sfRad = HazardSystem.compactRadTypes(1, 6, 3, 0),
							bfRad = HazardSystem.compactRadTypes(0, 0, 6, 4);
	
	public static void registerItems() {
		
		HazardSystem.register(Items.gunpowder, makeData(EXPLOSIVE, 1F));
		HazardSystem.register(Blocks.tnt, makeData(EXPLOSIVE, 4F));
		HazardSystem.register(Items.pumpkin_pie, makeData(EXPLOSIVE, 1F));
		
		HazardSystem.register(ball_dynamite, makeData(EXPLOSIVE, 2F));
		HazardSystem.register(grenade.stackFromEnum(AmmoHandGrenade.DYNAMITE), makeData(EXPLOSIVE, 1F));
		HazardSystem.register(stick_tnt, makeData(EXPLOSIVE, 1.5F));
		HazardSystem.register(stick_semtex, makeData(EXPLOSIVE, 2.5F));
		HazardSystem.register(stick_c4, makeData(EXPLOSIVE, 2.5F));

		HazardSystem.register(cordite, makeData(EXPLOSIVE, 2F));
		HazardSystem.register(ballistite, makeData(EXPLOSIVE, 1F));

		HazardSystem.register("dustCoal", makeData(COAL, powder));
		HazardSystem.register("dustTinyCoal", makeData(COAL, powder_tiny));
		HazardSystem.register("dustLignite", makeData(COAL, powder));
		HazardSystem.register("dustTinyLignite", makeData(COAL, powder_tiny));
		
		HazardSystem.register(insert_polonium, makeData(RADIATION, 100F));
		HazardSystem.register(BE.dust(), makeData(BERYLLIUM, powder));
		HazardSystem.register(BE.dustTiny(), makeData(BERYLLIUM, powder_tiny));
		
		HazardSystem.register(ingot_semtex, makeData(EXPLOSIVE, 10F));
		HazardSystem.register(block_semtex, makeData(EXPLOSIVE, 40F));
		HazardSystem.register(cordite, makeData(EXPLOSIVE, 2F));
		HazardSystem.register(ballistite, makeData(EXPLOSIVE, 1F));
		HazardSystem.register(powder_thermite, makeData(EXPLOSIVE));
		
		HazardSystem.register(insert_polonium, makeData(RADIATION, 100F).alpha());
		
//		HazardSystem.register(part_einsteinium, makeData(RADIATION, es254 * powder).alpha(8).beta().gamma());

		HazardSystem.register(demon_core_open, makeData(RADIATION, 5F));
		HazardSystem.register(demon_core_closed, makeData(RADIATION, 100_000F));
		HazardSystem.register(lamp_demon, makeData(RADIATION, 100_000F));
		HazardSystem.register(demon_core_open, makeData(RADIATION, 5F).alpha());
		HazardSystem.register(demon_core_closed, makeData(RADIATION, 100_000F).gamma().neutron());
		HazardSystem.register(lamp_demon, makeData(RADIATION, 100_000F).gamma().neutron());

		HazardSystem.register(demon_core_open, makeData(RADIATION, 5F).alpha());
		HazardSystem.register(demon_core_closed, makeData(RADIATION, 100_000F).gamma(6).neutron(4).addEntry(HOT, 40));
		HazardSystem.register(lamp_demon, makeData(RADIATION, 100_000F).gamma(6).neutron(4).addEntry(HOT, 40));
		
		HazardSystem.register(pellet_rtg, makeData(RADIATION, pu238 * rtg).alpha().addEntry(HOT, 10));
		HazardSystem.register(pellet_rtg_weak, makeData(RADIATION, (u238 + u238 + pu238) * rtg).alpha());
		HazardSystem.register(pellet_rtg_polonium, makeData(RADIATION, po210 * rtg).alpha().addEntry(HOT, 25));
		HazardSystem.register(pellet_rtg_actinium, makeData(RADIATION, ac227 * rtg).alpha(25).beta(75).addEntry(HOT, 15));
		HazardSystem.register(pellet_rtg_gold, makeData(RADIATION, au198 * rtg).addEntry(HOT, 45));
		HazardSystem.register(pellet_rtg_strontium, makeData(RADIATION, sr90 * rtg).beta().addEntry(HOT, 10).addEntry(HYDROACTIVE));
		HazardSystem.register(pellet_rtg_americium, makeData(RADIATION, am241 * rtg).alpha());
		HazardSystem.register(pellet_rtg_berkelium, makeData(RADIATION, bk248 * rtg).alpha());
		
		if (!realisticRads)
			HazardSystem.register(cell_tritium, makeData(RADIATION, 0.001F));
		HazardSystem.register(cell_sas3, new HazardData().addEntry(RADIATION, sas3).addRadTypes(SA326.getRadTypes()).addEntry(BLINDING, 3F));
		HazardSystem.register(cell_balefire, makeData(RADIATION, 50F).gamma());
		HazardSystem.register(powder_balefire, makeData(RADIATION, 500F).gamma(8).neutron(2).addEntry(HOT, 2));
		HazardSystem.register(egg_balefire_shard, makeData(RADIATION, bf * nugget).addRadTypes(bfRad).addEntry(HOT, 5F));
		HazardSystem.register(egg_balefire, makeData(RADIATION, bf * ingot).addRadTypes(bfRad).addEntry(HOT, 50));

		HazardSystem.register(catalyst_ten, makeData(RADIATION, ts294 * powder * 3).alpha().addEntry(HOT, 600 * 3));
		
		HazardSystem.register(ModItems.crystal_trixite, makeData(RADIATION, trx * crystal));
		
		HazardSystem.register(nuclear_waste_long, makeData(RADIATION, 5F).alpha(3).beta(6).gamma());
		HazardSystem.register(nuclear_waste_long_tiny, makeData(RADIATION, 0.5F).alpha(3).beta(6).gamma());
		HazardSystem.register(nuclear_waste_short, new HazardData().addEntry(RADIATION, 30F).beta(8).gamma(2).addEntry(HOT, 5F));
		HazardSystem.register(nuclear_waste_short_tiny, new HazardData().addEntry(RADIATION, 3F).beta(8).gamma(2).addEntry(HOT, 5F));
		HazardSystem.register(nuclear_waste_long_depleted, makeData(RADIATION, 0.5F).alpha(9).beta());
		HazardSystem.register(nuclear_waste_long_depleted_tiny, makeData(RADIATION, 0.05F).alpha(9).beta());
		HazardSystem.register(nuclear_waste_short_depleted, makeData(RADIATION, 3F));
		HazardSystem.register(nuclear_waste_short_depleted_tiny, makeData(RADIATION, 0.3F));
		HazardSystem.register(new ComparableStack(nuclear_waste_long, 1, ItemWasteLong.WasteClass.SCHRABIDIUM326.ordinal()), makeData(BLINDING));
		HazardSystem.register(new ComparableStack(nuclear_waste_long_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM326.ordinal()), makeData(BLINDING));
		HazardSystem.register(new ComparableStack(nuclear_waste_long_depleted, 1, ItemWasteLong.WasteClass.SCHRABIDIUM326.ordinal()), makeData(BLINDING));
		HazardSystem.register(new ComparableStack(nuclear_waste_long_depleted_tiny, 1, ItemWasteLong.WasteClass.SCHRABIDIUM326.ordinal()), makeData(BLINDING));
		HazardSystem.register(new ComparableStack(nuclear_waste_short, 1, ItemWasteShort.WasteClass.SCHRABIDIUM326.ordinal()), makeData(BLINDING));
		HazardSystem.register(new ComparableStack(nuclear_waste_short_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM326.ordinal()), makeData(BLINDING));
		HazardSystem.register(new ComparableStack(nuclear_waste_short_depleted, 1, ItemWasteShort.WasteClass.SCHRABIDIUM326.ordinal()), makeData(BLINDING));
		HazardSystem.register(new ComparableStack(nuclear_waste_short_depleted_tiny, 1, ItemWasteShort.WasteClass.SCHRABIDIUM326.ordinal()), makeData(BLINDING));
		HazardSystem.register(ModBlocks.ancient_scrap, makeData(RADIATION, 150).gamma(75).beta(25));
		HazardSystem.register(ModBlocks.block_corium, makeData(RADIATION, 500).gamma(6).neutron(4));
		HazardSystem.register(ModBlocks.block_corium_cobble, makeData(RADIATION, 250).gamma(6).neutron(4));
		HazardSystem.register(block_waste, makeData(RADIATION, wst * block).beta(9).gamma());
		HazardSystem.register(block_waste_painted, makeData(RADIATION, wst * block).beta(9).gamma());
		HazardSystem.register(block_waste_vitrified, makeData(RADIATION, wstv * block));
		
		HazardSystem.register(ModBlocks.brick_asbestos, makeData(ASBESTOS));
		HazardSystem.register(ModBlocks.tile_lab, makeData(ASBESTOS));
		HazardSystem.register(ModBlocks.tile_lab_cracked, makeData(ASBESTOS, 2));
		HazardSystem.register(ModBlocks.tile_lab_broken, makeData(ASBESTOS, 3.5f));

		HazardSystem.register(debris_fuel, makeData(RADIATION, 15000).addEntry(HOT, 30).gamma(9).neutron());
		HazardSystem.register(debris_graphite, makeData(RADIATION, 50).addEntry(HOT, 5));
		HazardSystem.register(debris_metal, makeData(RADIATION, 10));
//		HazardSystem.register(trinitite, makeData(RADIATION, trn * ingot));
		HazardSystem.register(scrap_nuclear, makeData(RADIATION, 1F));
		HazardSystem.register(trinitite, makeData(RADIATION, trn * ingot));
		HazardSystem.register(block_trinitite, makeData(RADIATION, trn * block));
		HazardSystem.register(nuclear_waste, makeData(RADIATION, wst * ingot).beta(9).gamma());
		HazardSystem.register(billet_nuclear_waste, makeData(RADIATION, wst * billet).beta(9).gamma());
		HazardSystem.register(nuclear_waste_tiny, makeData(RADIATION, wst * nugget).beta(9).gamma());
		HazardSystem.register(nuclear_waste_vitrified, makeData(RADIATION, wstv * ingot));
		HazardSystem.register(nuclear_waste_vitrified_tiny, makeData(RADIATION, wstv * nugget));
		HazardSystem.register(sand_gold198, makeData(RADIATION, au198 * block * powder_mult));
		
		HazardSystem.register(sellafield_0, makeData(RADIATION, 0.5F));
		HazardSystem.register(sellafield_1, makeData(RADIATION, 1F));
		HazardSystem.register(sellafield_2, makeData(RADIATION, 2.5F));
		HazardSystem.register(sellafield_3, makeData(RADIATION, 4F));
		HazardSystem.register(sellafield_4, makeData(RADIATION, 5F));	
		HazardSystem.register(sellafield_core, makeData(RADIATION, 10F));
		HazardSystem.register(nugget_uranium_fuel, makeData(RADIATION, uf * nugget).alpha());
		HazardSystem.register(billet_uranium_fuel, makeData(RADIATION, uf * billet).alpha());
		HazardSystem.register(ingot_uranium_fuel, makeData(RADIATION, uf * ingot).alpha());
		HazardSystem.register(block_uranium_fuel, makeData(RADIATION, uf * block).alpha());
		
		registerOtherFuel(rod_zirnox_natural_uranium_fuel, u * rod_dual, wst * rod_dual * 11.5F, false);
		registerOtherFuel(rod_zirnox_uranium_fuel, uf * rod_dual, wst * rod_dual * 10F, false);
		registerOtherFuel(rod_zirnox_th232, th232 * rod_dual, thf * rod_dual, false);
		registerOtherFuel(rod_zirnox_thorium_fuel, thf * rod_dual, wst * rod_dual * 7.5F, false);
		registerOtherFuel(rod_zirnox_mox_fuel, mox * rod_dual, wst * rod_dual * 10F, false);
		registerOtherFuel(rod_zirnox_plutonium_fuel, puf * rod_dual, wst * rod_dual * 12.5F, false);
		registerOtherFuel(rod_zirnox_u233_fuel, u233 * rod_dual, wst * rod_dual * 10F, false);
		registerOtherFuel(rod_zirnox_u235_fuel, u235 * rod_dual, wst * rod_dual * 11F, false);
		registerOtherFuel(rod_zirnox_les_fuel, saf * rod_dual, wst * rod_dual * 15F, false);
		registerOtherFuel(rod_zirnox_lithium, 0, 0.001F * rod_dual, false);
		HazardSystem.register(nugget_plutonium_fuel, makeData(RADIATION, puf * nugget).addRadTypes(lepRad));
		HazardSystem.register(billet_plutonium_fuel, makeData(RADIATION, puf * billet).addRadTypes(lepRad));
		HazardSystem.register(ingot_plutonium_fuel, makeData(RADIATION, puf * ingot).addRadTypes(lepRad));
		HazardSystem.register(block_plutonium_fuel, makeData(RADIATION, puf * block).addRadTypes(lepRad));
		
		HazardSystem.register(rod_zirnox_natural_uranium_fuel_depleted, makeData(RADIATION, wst * rod_dual * 11.5F));
		HazardSystem.register(rod_zirnox_uranium_fuel_depleted, makeData(RADIATION, wst * rod_dual * 10F));
		HazardSystem.register(rod_zirnox_thorium_fuel_depleted, makeData(RADIATION, wst * rod_dual * 7.5F));
		HazardSystem.register(rod_zirnox_mox_fuel_depleted, makeData(RADIATION, wst * rod_dual * 10F));
		HazardSystem.register(rod_zirnox_plutonium_fuel_depleted, makeData(RADIATION, wst * rod_dual * 12.5F));
		HazardSystem.register(rod_zirnox_u233_fuel_depleted, makeData(RADIATION, wst * rod_dual * 10F));
		HazardSystem.register(rod_zirnox_u235_fuel_depleted, makeData(RADIATION, wst * rod_dual * 11F));
		HazardSystem.register(rod_zirnox_les_fuel_depleted, makeData().addEntry(RADIATION, wst * rod_dual * 15F).addEntry(BLINDING, 20F));
		HazardSystem.register(rod_zirnox_tritium, makeData(RADIATION, 0.001F * rod_dual));
		HazardSystem.register(nugget_thorium_fuel, makeData(RADIATION, thf * nugget).addRadTypes(thmeuRad));
		HazardSystem.register(billet_thorium_fuel, makeData(RADIATION, thf * billet).addRadTypes(thmeuRad));
		HazardSystem.register(ingot_thorium_fuel, makeData(RADIATION, thf * ingot).addRadTypes(thmeuRad));
		HazardSystem.register(block_thorium_fuel, makeData(RADIATION, thf * block).addRadTypes(thmeuRad));
		
		registerOtherWaste(waste_natural_uranium, wst * billet * 11.5F);
		registerOtherWaste(waste_uranium, wst * billet * 10F);
		registerOtherWaste(waste_thorium, wst * billet * 7.5F);
		registerOtherWaste(waste_mox, wst * billet * 10F);
		registerOtherWaste(waste_plutonium, wst * billet * 12.5F);
		registerOtherWaste(waste_u233, wst * billet * 10F);
		registerOtherWaste(waste_u235, wst * billet * 11F);
		registerOtherWaste(waste_schrabidium, wst * billet * 15F);
		HazardSystem.register(nugget_neptunium_fuel, makeData(RADIATION, npf * nugget).alpha());
		HazardSystem.register(billet_neptunium_fuel, makeData(RADIATION, npf * billet).alpha());
		HazardSystem.register(ingot_neptunium_fuel, makeData(RADIATION, npf * ingot).alpha());
		
		HazardSystem.register(nugget_mox_fuel, makeData(RADIATION, mox * nugget).addRadTypes(moxRad));
		HazardSystem.register(billet_mox_fuel, makeData(RADIATION, mox * billet).addRadTypes(moxRad));
		HazardSystem.register(ingot_mox_fuel, makeData(RADIATION, mox * ingot).addRadTypes(moxRad));
		HazardSystem.register(block_mox_fuel, makeData(RADIATION, mox * block).addRadTypes(moxRad));
		
		HazardSystem.register(nugget_americium_fuel, makeData(RADIATION, amf * nugget).addRadTypes(leaRad));
		HazardSystem.register(billet_americium_fuel, makeData(RADIATION, amf * billet).addRadTypes(leaRad));
		HazardSystem.register(ingot_americium_fuel, makeData(RADIATION, amf * ingot).addRadTypes(leaRad));
		
		HazardSystem.register(nugget_mes, new HazardData().addEntry(RADIATION, mes * nugget).addRadTypes(sfRad).addEntry(BLINDING, 5F * nugget));
		HazardSystem.register(billet_mes, new HazardData().addEntry(RADIATION, mes * billet).addRadTypes(sfRad).addEntry(BLINDING, 5F * billet));
		HazardSystem.register(ingot_mes, new HazardData().addEntry(RADIATION, mes * ingot).addRadTypes(sfRad).addEntry(BLINDING, 5F * ingot));
		HazardSystem.register(block_schrabidium_fuel, new HazardData().addEntry(RADIATION, mes * block).addRadTypes(sfRad).addEntry(BLINDING, 5F * block));
		
		HazardSystem.register(nugget_hes, makeData(RADIATION, hes * nugget).addRadTypes(sfRad).addEntry(BLINDING, 5F * nugget));
		HazardSystem.register(billet_hes, makeData(RADIATION, hes * billet).addRadTypes(sfRad).addEntry(BLINDING, 5F * billet));
		HazardSystem.register(ingot_hes, makeData(RADIATION, hes * ingot).addRadTypes(sfRad).addEntry(BLINDING, 5F * ingot));
		
		HazardSystem.register(nugget_les, makeData(RADIATION, les * nugget).addRadTypes(sfRad).addEntry(BLINDING, 5F * nugget));
		HazardSystem.register(billet_les, makeData(RADIATION, les * billet).addRadTypes(sfRad).addEntry(BLINDING, 5F * billet));
		HazardSystem.register(ingot_les, makeData(RADIATION, les * ingot).addRadTypes(sfRad).addEntry(BLINDING, 5F * ingot));
		
		registerFuelRod(rod_zirnox_natural_uranium_fuel, u * rod_dual, 70);
		registerFuelRod(rod_zirnox_uranium_fuel, uf * rod_dual, 80);
		registerFuelRod(rod_zirnox_th232, th232 * rod_dual, tha * rod_dual);
		registerFuelRod(rod_zirnox_thorium_fuel, thf * rod_dual, 60);
		registerFuelRod(rod_zirnox_mox_fuel, mox * rod_dual, 100);
		registerFuelRod(rod_zirnox_plutonium_fuel, puf * rod_dual, 110);
		registerFuelRod(rod_zirnox_u233_fuel, u233 * rod_dual, 120);
		registerFuelRod(rod_zirnox_u235_fuel, u235 * rod_dual, 110);
		
		registerFuelRod(rod_zirnox_les_fuel, les * rod_dual, 120);
		
		HazardSystem.register(rod_zirnox_natural_uranium_fuel_depleted, makeData(RADIATION, 70F).alpha(4).beta(5).gamma());
		HazardSystem.register(rod_zirnox_uranium_fuel_depleted, makeData(RADIATION, 80F).alpha(4).beta(5).gamma());
		HazardSystem.register(rod_zirnox_tha, makeData(RADIATION, tha * billet * 2).addRadTypes(THA.getRadTypes()));
		HazardSystem.register(rod_zirnox_thorium_fuel_depleted, makeData(RADIATION, 60F).beta(6).gamma(4));
		HazardSystem.register(rod_zirnox_mox_fuel_depleted, makeData(RADIATION, 100F).alpha(3).beta(6).gamma());
		HazardSystem.register(rod_zirnox_plutonium_fuel_depleted, makeData(RADIATION, 110F).alpha().beta(5).gamma(4));
		HazardSystem.register(rod_zirnox_u233_fuel_depleted, makeData(RADIATION, 120F).alpha().beta(7).gamma(2));
		HazardSystem.register(rod_zirnox_u235_fuel_depleted, makeData(RADIATION, 110F).alpha(2).beta(7).gamma());
		HazardSystem.register(rod_zirnox_les_fuel_depleted, new HazardData().addEntry(RADIATION, 120F).addEntry(BLINDING, 5F).gamma());
		
		HazardSystem.register(waste_natural_uranium, makeData(RADIATION, 35F));
		HazardSystem.register(waste_uranium, makeData(RADIATION, 34F));
		HazardSystem.register(waste_u233, makeData(RADIATION, 60F));
		HazardSystem.register(waste_u235, makeData(RADIATION, 55F));
		HazardSystem.register(waste_thorium, makeData(RADIATION, 30F));
		HazardSystem.register(waste_plutonium, makeData(RADIATION, 55F));
		HazardSystem.register(waste_mox, makeData(RADIATION, 50F));
		HazardSystem.register(waste_schrabidium, new HazardData().addEntry(RADIATION, 45F).addEntry(HOT, 5F));
		HazardSystem.register(sellafield_0, makeData(RADIATION, 0.5F));
		HazardSystem.register(sellafield_1, makeData(RADIATION, 1F));
		HazardSystem.register(sellafield_2, makeData(RADIATION, 2.5F));
		HazardSystem.register(sellafield_3, makeData(RADIATION, 4F));
		HazardSystem.register(sellafield_4, makeData(RADIATION, 5F));	
		HazardSystem.register(sellafield_core, makeData(RADIATION, 10F));
		
		registerOtherFuel(pellet_schrabidium, sa326 * ingot * 5, wst * ingot * 100, true);
		registerOtherFuel(pellet_hes, saf * ingot * 5, wst * ingot * 75, true);
		registerOtherFuel(pellet_mes, saf * ingot * 5, wst * ingot * 50, true);
		registerOtherFuel(pellet_les, saf * ingot * 5, wst * ingot * 20, false);
		registerOtherFuel(pellet_beryllium, 0F, 10F, false);
		registerOtherFuel(pellet_neptunium, np237 * ingot * 5, wst * ingot * 10, false);
		registerOtherFuel(pellet_lead, 0F, 15F, false);
		registerOtherFuel(pellet_advanced, 0F, 20F, false);
		
		registerOtherFuel(plate_fuel_u233, u233 * ingot, wst * ingot * 13F, false);
		registerOtherFuel(plate_fuel_u235, u235 * ingot, wst * ingot * 10F, false);
		registerOtherFuel(plate_fuel_mox, mox * ingot, wst * ingot * 16F, false);
		registerOtherFuel(plate_fuel_pu239, pu239 * ingot, wst * ingot * 13.5F, false);
		registerOtherFuel(plate_fuel_sa326, sa326 * ingot, wst * ingot * 10F, true);
		registerOtherFuel(plate_fuel_pu238be, pube * billet, pube * nugget * 1, false);
		
		registerOtherWaste(waste_plate_u233, wst * ingot * 13F);
		registerOtherWaste(waste_plate_u235, wst * ingot * 10F);
		registerOtherWaste(waste_plate_mox, wst * ingot * 16F);
		registerOtherWaste(waste_plate_pu239, wst * ingot * 13.5F);
		registerOtherWaste(waste_plate_sa326, wst * ingot * 10F);
		registerRadSourceWaste(waste_plate_ra226be, pobe * nugget * 3);
		registerRadSourceWaste(waste_plate_pu238be, pube * nugget * 1);
		
		HazardSystem.register(debris_graphite, makeData().addEntry(RADIATION, 70F).addEntry(HOT, 5F));
		registerOtherFuel(plate_fuel_ra226be, rabe * billet, pobe * nugget * 3, false);
		registerOtherFuel(rod_zirnox_natural_uranium_fuel, u * rod_dual, u * rod_dual * 100, false);
		registerOtherFuel(rod_zirnox_uranium_fuel, uf * rod_dual, uf * rod_dual * 100, false);
		registerOtherFuel(rod_zirnox_th232, th232 * rod_dual, thf * rod_dual, false);
		registerOtherFuel(rod_zirnox_thorium_fuel, thf * rod_dual, u233 * rod_dual * 10, false);
		registerOtherFuel(rod_zirnox_mox_fuel, mox * rod_dual, mox * rod_dual * 100, false);
		registerOtherFuel(rod_zirnox_plutonium_fuel, puf * rod_dual, puf * rod_dual * 100, false);
		registerOtherFuel(rod_zirnox_u233_fuel, u233 * rod_dual, u233 * rod_dual * 100, false);
		registerOtherFuel(rod_zirnox_u235_fuel, u235 * rod_dual, u235 * rod_dual * 100, false);
		registerOtherFuel(rod_zirnox_les_fuel, saf * rod_dual, saf * rod_dual * 100, false);
		if (!realisticRads)
			registerOtherFuel(rod_zirnox_lithium, 0, 0.001F * rod_dual, false);
		
		HazardSystem.register(rod_zirnox_natural_uranium_fuel_depleted, makeData(RADIATION, u * rod_dual * 100));
		HazardSystem.register(rod_zirnox_uranium_fuel_depleted, makeData(RADIATION, uf * rod_dual * 100));
		HazardSystem.register(rod_zirnox_thorium_fuel_depleted, makeData(RADIATION, u233 * rod_dual * 10));
		HazardSystem.register(rod_zirnox_mox_fuel_depleted, makeData(RADIATION, mox * rod_dual * 100));
		HazardSystem.register(rod_zirnox_plutonium_fuel_depleted, makeData(RADIATION, 100F + 30F));
		HazardSystem.register(rod_zirnox_u233_fuel_depleted, makeData(RADIATION, u233 * rod_dual * 100));
		HazardSystem.register(rod_zirnox_u235_fuel_depleted, makeData(RADIATION, u235 * rod_dual * 100));
		HazardSystem.register(rod_zirnox_les_fuel_depleted, makeData().addEntry(RADIATION, saf * rod_dual * 100).addEntry(BLINDING, 5F));
		if (!realisticRads)
			HazardSystem.register(rod_zirnox_tritium, makeData(RADIATION, 0.001F * rod_dual));
		
		registerOtherWaste(waste_natural_uranium, u * billet * 100);
		registerOtherWaste(waste_uranium, uf * billet * 100);
		registerOtherWaste(waste_u233, u233 * billet * 100);
		registerOtherWaste(waste_u235, u235 * billet * 100);
		registerOtherWaste(waste_thorium, u233 * billet * 10);
		registerOtherWaste(waste_plutonium, puf * billet * 100);
		registerOtherWaste(waste_mox, mox * billet * 100);
		registerOtherWaste(waste_schrabidium, mes * billet * 100);
		
		registerOtherFuel(pellet_schrabidium, sa326 * ingot * 5, sa326 * ingot * 100, true);
		registerOtherFuel(pellet_hes, hes * ingot * 5, hes * ingot * 75, true);
		registerOtherFuel(pellet_mes, mes * ingot * 5, mes * ingot * 50, true);
		registerOtherFuel(pellet_les, les * ingot * 5, les * ingot * 20, false);
		registerOtherFuel(pellet_beryllium, 0F, 10F, false);
		registerOtherFuel(pellet_neptunium, np237 * ingot * 5, np237 * ingot * 25, false);
		registerOtherFuel(pellet_lead, 0F, 15F, false);
		registerOtherFuel(pellet_advanced, 0F, 20F, false);
		
		registerOtherFuel(plate_fuel_u233, u233 * ingot, u233 * ingot * 100, false);
		registerOtherFuel(plate_fuel_u235, u235 * ingot, u235 * ingot * 100, false);
		registerOtherFuel(plate_fuel_mox, mox * ingot, mox * ingot * 100, false);
		registerOtherFuel(plate_fuel_pu239, pu239 * ingot, pu239 * ingot * 100, false);
		registerOtherFuel(plate_fuel_sa326, sa326 * ingot, sa326 * ingot * 100, true);
		registerOtherFuel(plate_fuel_ra226be, rabe * billet, po210 * nugget * 3, false);
		registerOtherFuel(plate_fuel_pu238be, pube * billet, pu238 * nugget, false);
		
		registerOtherWaste(waste_plate_u233, u233 * ingot * 100);
		registerOtherWaste(waste_plate_u235, u235 * ingot * 100);
		registerOtherWaste(waste_plate_mox, mox * ingot * 100);
		registerOtherWaste(waste_plate_pu239, pu239 * ingot * 100);
		registerOtherWaste(waste_plate_sa326, sa326 * ingot * 100);
		registerOtherWaste(waste_plate_ra226be, po210 * nugget * 3);
		
		HazardSystem.register(debris_graphite, makeData().addEntry(RADIATION, 70F).addEntry(HOT, 5F));
		HazardSystem.register(debris_metal, makeData(RADIATION, 5F));
		HazardSystem.register(debris_fuel, makeData().addEntry(RADIATION, 500F).addEntry(HOT, 5F));
		HazardSystem.register(debris_concrete, makeData(RADIATION, 30F));
		HazardSystem.register(debris_exchanger, makeData(RADIATION, 25F));
		HazardSystem.register(debris_shrapnel, makeData(RADIATION, 2.5F));
		HazardSystem.register(debris_element, makeData(RADIATION, 100F));
		
		HazardSystem.register(man_core, makeData(RADIATION, pu239 * nugget * 8).alpha());
		HazardSystem.register(gadget_core, makeData(RADIATION, (pu239 * nugget * 7) + (u238 * nugget * 3)).alpha());
		HazardSystem.register(mike_core, makeData(RADIATION, u238 * nugget * 24).alpha());
		HazardSystem.register(boy_bullet, makeData(RADIATION, u235 * nugget * 3).alpha());
		HazardSystem.register(boy_target, makeData(RADIATION, u235 * nugget * 7).alpha());
		HazardSystem.register(fleija_igniter, makeData(RADIATION, sa326 * nugget * 2).alpha().beta(6).gamma(3));
		HazardSystem.register(fleija_core, makeData(RADIATION, (u235 * 8 + np237 * 2) * nugget).alpha());
		HazardSystem.register(fleija_propellant, makeData(RADIATION, sa326 * ingot * 8).alpha().beta(6).gamma(3));
		HazardSystem.register(solinium_core, makeData(RADIATION, sa327).alpha().beta(5).gamma(4));
		
		HazardSystem.register(wire_schrabidium, makeData(RADIATION, sa326 * nugget).addEntry(BLINDING).alpha().beta(6).gamma(3));
		HazardSystem.register(ammo_357.stackFromEnum(Ammo357Magnum.SCHRABIDIUM), makeData(RADIATION, sa326 * nugget).addEntry(BLINDING).alpha().beta(6).gamma(3));
		
		HazardSystem.register(pellet_schrabidium, makeData(RADIATION, sa326 * 5).alpha().beta(6).gamma(3).addEntry(BLINDING, 5));
		HazardSystem.register(pellet_hes, makeData(RADIATION, hes * 5).alpha().beta(6).gamma(3).addEntry(BLINDING, 5));
		HazardSystem.register(pellet_mes, makeData(RADIATION, mes * 5).alpha().beta(6).gamma(3).addEntry(BLINDING, 5));
		HazardSystem.register(pellet_les, makeData(RADIATION, les * 5).alpha().beta(6).gamma(3).addEntry(BLINDING, 5));
//		HazardSystem.register(pellet_solinium, makeData(RADIATION, sa327 * 5).alpha().beta(6).gamma(3).addEntry(BLINDING, 5));
//		HazardSystem.register(pellet_solinium_laced, makeData(RADIATION, sa327 * nugget * 31).alpha().beta(5).gamma(4).addEntry(BLINDING, 5));
		
		HazardSystem.register(billet_balefire_gold, makeData(RADIATION, au198 * billet).beta(7).neutron(3));
		HazardSystem.register(billet_po210be, makeData(RADIATION, pobe * billet).neutron());
		HazardSystem.register(billet_ra226be, makeData(RADIATION, rabe * billet).neutron());
		HazardSystem.register(billet_pu238be, makeData(RADIATION, pube * billet).neutron());
		HazardSystem.register(billet_ac227be, makeData(RADIATION, ac227 * billet / 2).neutron());
		HazardSystem.register(billet_cf252be, makeData(RADIATION, cf252 * billet / 2).gamma(2).neutron(8));
		HazardSystem.register(billet_sa327be, makeData(RADIATION, sa327 * billet / 2).beta(2).gamma(2).neutron(6));
		
		registerRTGPellet(pellet_rtg, pu238 * rtg, 0, 3F);
		registerRTGPellet(pellet_rtg_radium, ra226 * rtg, 0);
		registerRTGPellet(pellet_rtg_weak, (pu238 + (u238 * 2)) * billet, 0);
		registerRTGPellet(pellet_rtg_strontium, sr90 * rtg, 0, 3);
		registerRTGPellet(pellet_rtg_actinium, ac227 * rtg, 0, 3);
		registerRTGPellet(pellet_rtg_polonium, po210 * rtg, 0, 3F);
		registerRTGPellet(pellet_rtg_cobalt, co60 * rtg, 5);
		registerRTGPellet(pellet_rtg_lead, pb209 * rtg, 0, 7F, 5F);
		registerRTGPellet(pellet_rtg_gold, au198 * rtg, 0, 5F);
		registerRTGPellet(pellet_rtg_americium, am241 * rtg, np237 * rtg);
		registerRTGPellet(pellet_rtg_berkelium, bk248 * rtg, cm244 * rtg);
		HazardSystem.register(new ItemStack(pellet_rtg_depleted, 1, DepletedRTGMaterial.NEPTUNIUM.ordinal()), makeData(RADIATION, np237 * rtg));
		HazardSystem.register(new ItemStack(pellet_rtg_depleted, 1, DepletedRTGMaterial.CURIUM.ordinal()), makeData(RADIATION, cm244 * rtg));
		
		if (!realisticRads)
			registerBreedingRodRadiation(BreedingRodType.TRITIUM, 0.001F);
		HazardSystem.register(pile_rod_uranium, makeData(RADIATION, u * billet * 3));
		HazardSystem.register(pile_rod_pu239, makeData(RADIATION, !GeneralConfig.enable528 ? purg * billet + pu239 * billet + u * billet : purg * billet + pu239 * billet + wst * billet));
		HazardSystem.register(pile_rod_plutonium, makeData(RADIATION, !GeneralConfig.enable528 ? purg * billet * 2 + u * billet : purg * billet * 2 + wst * billet));
		HazardSystem.register(pile_rod_source, makeData(RADIATION, rabe * billet * 3));
		
		registerBreedingRodRadiation(BreedingRodType.CO60, co60);
		registerBreedingRodRadiation(BreedingRodType.RA226, ra226);
		registerBreedingRodRadiation(BreedingRodType.AC227, ac227);
		registerBreedingRodRadiation(BreedingRodType.TH232, th232);
		registerBreedingRodRadiation(BreedingRodType.THF, thf);
		registerBreedingRodRadiation(BreedingRodType.U235, u235);
		registerBreedingRodRadiation(BreedingRodType.NP237, np237);
		registerBreedingRodRadiation(BreedingRodType.U238, u238);
		registerBreedingRodRadiation(BreedingRodType.PU238, pu238); //it's in a container :)
		registerBreedingRodRadiation(BreedingRodType.PU239, pu239);
		registerBreedingRodRadiation(BreedingRodType.RGP, purg);
		registerBreedingRodRadiation(BreedingRodType.WASTE, wst);
		registerBreedingRodRadiation(BreedingRodType.URANIUM, u);

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
		registerRBMK(rbmk_fuel_drx, bf * rod_rbmk, bf * rod_rbmk * 100F, true, true, 0, 1F/3F);

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
		registerRBMKPellet(rbmk_pellet_les, saf * billet, wst * billet * 24.5F);
		registerRBMKPellet(rbmk_pellet_mes, saf * billet, wst * billet * 30F);
		registerRBMKPellet(rbmk_pellet_hes, saf * billet, wst * billet * 50F);
		registerRBMKPellet(rbmk_pellet_balefire, bf * billet, bf * billet * 0.1F);
		registerRBMKPellet(rbmk_pellet_po210be, pobe * billet, pobe * billet / 4);
		registerRBMKPellet(rbmk_pellet_pu238be, pube * billet, pube * billet / 4);
		registerRBMKPellet(rbmk_pellet_ra226be, rabe * billet, rabe * billet * 2);
		registerRBMKPellet(rbmk_pellet_ac227be, ac227 * billet / 2, ac227  * billet / 4);
		registerRBMKPellet(rbmk_pellet_cf252be, cf252 * billet / 2, cf252  * billet / 4);
		registerRBMKPellet(rbmk_pellet_sa327be, sa327 * billet / 2, sa327  * billet / 4);
		registerRBMKPellet(rbmk_pellet_zfb_am_mix, zfb_am_mix * billet, zfb_am_mix * billet * 2);
		registerRBMKPellet(rbmk_pellet_zfb_bismuth, zfb_bi * billet, zfb_bi * billet / 2);
		registerRBMKPellet(rbmk_pellet_zfb_pu241, zfb_pu241 * billet, zfb_pu241 * billet * 2);
		registerRBMKPellet(rbmk_pellet_drx, bf * billet, bf * billet * 100F, true, 0F, 1F/24F);

		HazardSystem.register(powder_yellowcake, makeData(RADIATION, yc * powder));
		HazardSystem.register(block_yellowcake, makeData(RADIATION, yc * block * powder_mult));
		HazardSystem.register(ModItems.fallout, makeData(RADIATION, fo * powder));
		HazardSystem.register(ModBlocks.fallout, makeData(RADIATION, fo * powder * 2));
		HazardSystem.register(ModBlocks.block_fallout, makeData(RADIATION, yc * block * powder_mult));
		HazardSystem.register(powder_caesium, makeData().addEntry(HYDROACTIVE, 1F).addEntry(HOT, 3F));
		
		HazardSystem.register(wire_schrabidium, makeData(RADIATION, sa326 * nugget));
		
		HazardSystem.register(brick_asbestos, makeData(ASBESTOS, 1F));
		HazardSystem.register(tile_lab_broken, makeData(ASBESTOS, 1F));
		HazardSystem.register(powder_coltan_ore, makeData(ASBESTOS, 3F));
		
		//crystals
		HazardSystem.register(crystal_uranium, makeData(RADIATION, u * crystal));
		HazardSystem.register(crystal_thorium, makeData(RADIATION, th232 * crystal));
		HazardSystem.register(crystal_plutonium, makeData(RADIATION, pu * crystal));
		HazardSystem.register(crystal_schraranium, makeData(RADIATION, sr * crystal));
		HazardSystem.register(crystal_schrabidium, makeData(RADIATION, sa326 * crystal));
		HazardSystem.register(crystal_phosphorus, makeData(HOT, 2F * crystal));
		HazardSystem.register(crystal_lithium, makeData(HYDROACTIVE, 1F * crystal));
		HazardSystem.register(ModItems.crystal_trixite, makeData(RADIATION, trx * crystal));
		
		//nuke parts
		HazardSystem.register(boy_propellant, makeData(EXPLOSIVE, 2F));
		HazardSystem.register(boy_igniter, makeData(EXPLOSIVE, 1F));
		
		HazardSystem.register(gadget_core, makeData(RADIATION, pu239 * nugget * 10));
		HazardSystem.register(boy_target, makeData(RADIATION, u235 * ingot * 2));
		HazardSystem.register(boy_bullet, makeData(RADIATION, u235 * ingot));
		HazardSystem.register(man_core, makeData(RADIATION, pu239 * nugget * 10));
		HazardSystem.register(mike_core, makeData(RADIATION, u238 * nugget * 10));
		HazardSystem.register(tsar_core, makeData(RADIATION, pu239 * nugget * 15));
		
		HazardSystem.register(fleija_propellant, makeData().addEntry(RADIATION, 15F).addEntry(EXPLOSIVE, 8F).addEntry(BLINDING, 5F));
		HazardSystem.register(fleija_core, makeData(RADIATION, 10F));
		
		HazardSystem.register(solinium_propellant, makeData(EXPLOSIVE, 10F));
		HazardSystem.register(solinium_core, makeData().addEntry(RADIATION, sa327 * nugget * 8).addEntry(BLINDING, 5F));

		HazardSystem.register(nuke_fstbmb, makeData(DIGAMMA, 0.01F));
		HazardSystem.register(new ItemStack(ModItems.holotape_image, 1, EnumHoloImage.HOLO_RESTORED.ordinal()), makeData(DIGAMMA, 1F));
		
		/*
		 * Blacklist
		 */
		HazardSystem.blacklist(TH232.ore());
		HazardSystem.blacklist(U.ore());

		
		/*
		 * ReC compat
		 */
		Item recWaste = Compat.tryLoadItem(Compat.MOD_REC, "reactorcraft_item_waste");
		if(recWaste != null) {
			for(ReikaIsotope i : ReikaIsotope.values()) {
				if(i.getRad() > 0) {
					HazardSystem.register(new ItemStack(recWaste, 1, i.ordinal()), makeData(RADIATION, i.getRad()));
				}
			}
		}
	}
	
	public static void registerTrafos() {
		HazardSystem.trafos.add(new HazardTransformerRadiationNBT());
//		HazardSystem.trafos.add(new HazardTransformerDigammaNBT());
		HazardSystem.trafos.add(new HazardTransformerRadiationME());
	}
	
	private static HazardData makeData() { return new HazardData(); }
	private static HazardData makeData(HazardTypeBase hazard) { return new HazardData().addEntry(hazard); }
	private static HazardData makeData(HazardTypeBase hazard, float level) { return new HazardData().addEntry(hazard, level); }
	private static HazardData makeData(HazardTypeBase hazard, float level, boolean override) { return new HazardData().addEntry(hazard, level, override); }
	
	private static void registerRBMKPellet(Item pellet, float base, float dep) { registerRBMKPellet(pellet, base, dep, false, 0F, 0F); }
	private static void registerRBMKPellet(Item pellet, float base, float dep, boolean linear) { registerRBMKPellet(pellet, base, dep, linear, 0F, 0F); }
	private static void registerRBMKPellet(Item pellet, float base, float dep, boolean linear, float blinding, float digamma) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep, linear)));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		if(digamma > 0) data.addEntry(new HazardEntry(DIGAMMA, digamma));
		HazardSystem.register(pellet, data);
	}
	
	private static void registerRTGPellet(Item pellet, float base, float target) { registerRTGPellet(pellet, base, target, 0, 0); }
	private static void registerRTGPellet(Item pellet, float base, float target, float hot) { registerRTGPellet(pellet, base, target, hot, 0); }
	
	private static void registerRTGPellet(Item pellet, float base, float target, float hot, float blinding) {
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRTGRadiation(target)));
		if(hot > 0) data.addEntry(new HazardEntry(HOT, hot));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		HazardSystem.register(pellet, data);
	}
	
//	private static void registerRBMXPellet(Item pellet, float base, float dep)
//	{
//		HazardData data = new HazardData();
//		data.addEntry(new HazardEntry(DIGAMMA, base).addMod(new HazardModifierRBMKRadiation(dep)));
//		HazardSystem.register(pellet, data);
//	}
//	
//	private static void registerRBMXRod(Item rod, float base, float dep)
//	{
//		HazardData data = new HazardData();
//		data.addEntry(new HazardEntry(DIGAMMA, base).addMod(new HazardModifierRBMKRadiation(dep)));
//		HazardSystem.register(rod, data);
//	}
	private static void registerFuelRod(Item rod, float base, float dep)
	{
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierFuelRadiation(dep)));
		HazardSystem.register(rod, data);
	}
	private static void registerRBMKRod(Item rod, float base, float dep) { registerRBMK(rod, base, dep, true, false, 0F, 0F); }
	private static void registerRBMKRod(Item rod, float base, float dep, float blinding) { registerRBMK(rod, base, dep, true, false, blinding, 0F); }
	private static void registerRBMKRod(Item rod, float base, float dep, boolean linear) { registerRBMK(rod, base, dep, true, linear, 0F, 0F); }	
	private static void registerRBMK(Item rod, float base, float dep, boolean hot, boolean linear, float blinding, float digamma) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep, linear)));
		if(hot) data.addEntry(new HazardEntry(HOT, 0).addMod(new HazardModifierRBMKHot()));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		if(digamma > 0) data.addEntry(new HazardEntry(DIGAMMA, digamma));
		HazardSystem.register(rod, data);
	}
	
	private static void registerBreedingRodRadiation(BreedingRodType type, float base) {
		HazardSystem.register(new ItemStack(ModItems.rod, 1, type.ordinal()), makeData(RADIATION, base));
		HazardSystem.register(new ItemStack(ModItems.rod_dual, 1, type.ordinal()), makeData(RADIATION, base * rod_dual));
		HazardSystem.register(new ItemStack(ModItems.rod_quad, 1, type.ordinal()), makeData(RADIATION, base * rod_quad));
	}
	
	private static void registerOtherFuel(Item fuel, float base, float target, boolean blinding) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierFuelRadiation(target)));
		if(blinding)
			data.addEntry(BLINDING, 5F);
		HazardSystem.register(fuel, data);
	}
	
//	private static void registerRTGPellet(Item pellet, float base, float target) { registerRTGPellet(pellet, base, target, 0, 0); }
//	private static void registerRTGPellet(Item pellet, float base, float target, float hot) { registerRTGPellet(pellet, base, target, hot, 0); }
//	
//	private static void registerRTGPellet(Item pellet, float base, float target, float hot, float blinding) {
//		HazardData data = new HazardData();
//		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRTGRadiation(target)));
//		if(hot > 0) data.addEntry(new HazardEntry(HOT, hot));
//		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, hot));
//		HazardSystem.register(pellet, data);
//	}
//	
	private static void registerOtherWaste(Item waste, float base) {
		HazardSystem.register(new ItemStack(waste, 1, 0), makeData(RADIATION, base * 0.75F));
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base));
		data.addEntry(new HazardEntry(HOT, 5F));
		HazardSystem.register(new ItemStack(waste, 1, 1), data);
	}
	
	private static void registerRadSourceWaste(Item waste, float base) {
		HazardSystem.register(new ItemStack(waste, 1, 0), makeData(RADIATION, base));
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base));
		data.addEntry(new HazardEntry(HOT, 5F));
		HazardSystem.register(new ItemStack(waste, 1, 1), data);
	}
}
