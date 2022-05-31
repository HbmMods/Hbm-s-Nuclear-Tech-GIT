package com.hbm.main;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMotherOfAllOres;
import com.hbm.config.*;
import com.hbm.creativetabs.*;
import com.hbm.entity.cart.*;
import com.hbm.entity.effect.*;
import com.hbm.entity.grenade.*;
import com.hbm.entity.item.*;
import com.hbm.entity.logic.*;
import com.hbm.entity.missile.*;
import com.hbm.entity.mob.*;
import com.hbm.entity.mob.botprime.*;
import com.hbm.entity.mob.siege.*;
import com.hbm.entity.particle.*;
import com.hbm.entity.projectile.*;
import com.hbm.entity.qic.EntitySPV;
import com.hbm.handler.*;
import com.hbm.handler.imc.*;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.hazard.HazardRegistry;
import com.hbm.inventory.*;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.*;
import com.hbm.inventory.recipes.anvil.AnvilRecipes;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmWorld;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.tileentity.TileMappings;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.util.ArmorUtil;
import com.hbm.world.feature.OreCave;
import com.hbm.world.feature.SchistStratum;
import com.hbm.world.generator.CellularDungeonFactory;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = RefStrings.MODID, name = RefStrings.NAME, version = RefStrings.VERSION)
public class MainRegistry {
	
	@Instance(RefStrings.MODID)
	public static MainRegistry instance;

	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;

	@Metadata
	public static ModMetadata meta;

	public static Logger logger = LogManager.getLogger("HBM");

	// Tool Materials
	public static ToolMaterial tMatSchrab = EnumHelper.addToolMaterial("SCHRABIDIUM", 3, 10000, 50.0F, 100.0F, 200);
	public static ToolMaterial tMatHammmer = EnumHelper.addToolMaterial("SCHRABIDIUMHAMMER", 3, 0, 50.0F, 999999996F, 200);
	public static ToolMaterial tMatChainsaw = EnumHelper.addToolMaterial("CHAINSAW", 3, 1500, 50.0F, 22.0F, 0);
	public static ToolMaterial tMatSteel = EnumHelper.addToolMaterial("HBM_STEEL", 2, 500, 7.5F, 2.0F, 10);
	public static ToolMaterial tMatTitan = EnumHelper.addToolMaterial("HBM_TITANIUM", 3, 750, 9.0F, 2.5F, 15);
	public static ToolMaterial tMatAlloy = EnumHelper.addToolMaterial("HBM_ALLOY", 3, 2000, 15.0F, 5.0F, 5);
	public static ToolMaterial tMatCMB = EnumHelper.addToolMaterial("HBM_CMB", 3, 8500, 40.0F, 55F, 100);
	public static ToolMaterial tMatElec = EnumHelper.addToolMaterial("HBM_ELEC", 3, 0, 30.0F, 12.0F, 2);
	public static ToolMaterial tMatDesh = EnumHelper.addToolMaterial("HBM_DESH", 2, 0, 7.5F, 2.0F, 10);
	public static ToolMaterial tMatCobalt = EnumHelper.addToolMaterial("HBM_COBALT", 3, 750, 9.0F, 2.5F, 15);

	public static ToolMaterial enumToolMaterialSaw = EnumHelper.addToolMaterial("SAW", 2, 750, 2.0F, 3.5F, 25);
	public static ToolMaterial enumToolMaterialBat = EnumHelper.addToolMaterial("BAT", 0, 500, 1.5F, 3F, 25);
	public static ToolMaterial enumToolMaterialBatNail = EnumHelper.addToolMaterial("BATNAIL", 0, 450, 1.0F, 4F, 25);
	public static ToolMaterial enumToolMaterialGolfClub = EnumHelper.addToolMaterial("GOLFCLUB", 1, 1000, 2.0F, 5F, 25);
	public static ToolMaterial enumToolMaterialPipeRusty = EnumHelper.addToolMaterial("PIPERUSTY", 1, 350, 1.5F, 4.5F, 25);
	public static ToolMaterial enumToolMaterialPipeLead = EnumHelper.addToolMaterial("PIPELEAD", 1, 250, 1.5F, 5.5F, 25);

	public static ToolMaterial enumToolMaterialBottleOpener = EnumHelper.addToolMaterial("OPENER", 1, 250, 1.5F, 0.5F, 200);
	public static ToolMaterial enumToolMaterialSledge = EnumHelper.addToolMaterial("SHIMMERSLEDGE", 1, 0, 25.0F, 26F, 200);

	public static ToolMaterial enumToolMaterialMultitool = EnumHelper.addToolMaterial("MULTITOOL", 3, 5000, 25F, 5.5F, 25);

	// Armor Materials
	public static ArmorMaterial enumArmorMaterialEmerald = EnumHelper.addArmorMaterial("HBM_TEST", 2500, new int[] { 3, 8, 6, 3 }, 30);
	public static ArmorMaterial aMatSchrab = EnumHelper.addArmorMaterial("HBM_SCHRABIDIUM", 100, new int[] { 3, 8, 6, 3 }, 50);
	public static ArmorMaterial aMatEuph = EnumHelper.addArmorMaterial("HBM_EUPHEMIUM", 15000000, new int[] { 3, 8, 6, 3 }, 100);
	public static ArmorMaterial aMatHaz = EnumHelper.addArmorMaterial("HBM_HAZMAT", 60, new int[] { 2, 5, 4, 1 }, 5);
	public static ArmorMaterial aMatHaz2 = EnumHelper.addArmorMaterial("HBM_HAZMAT2", 60, new int[] { 2, 5, 4, 1 }, 5);
	public static ArmorMaterial aMatHaz3 = EnumHelper.addArmorMaterial("HBM_HAZMAT3", 60, new int[] { 2, 5, 4, 1 }, 5);
	public static ArmorMaterial aMatSteel = EnumHelper.addArmorMaterial("HBM_STEEL", 20, new int[] { 2, 6, 5, 2 }, 5);
	public static ArmorMaterial aMatAsbestos = EnumHelper.addArmorMaterial("HBM_ASBESTOS", 20, new int[] { 1, 4, 3, 1 }, 5);
	public static ArmorMaterial aMatTitan = EnumHelper.addArmorMaterial("HBM_TITANIUM", 25, new int[] { 3, 8, 6, 3 }, 9);
	public static ArmorMaterial aMatAlloy = EnumHelper.addArmorMaterial("HBM_ALLOY", 40, new int[] { 3, 8, 6, 3 }, 12);
	public static ArmorMaterial aMatPaa = EnumHelper.addArmorMaterial("HBM_PAA", 75, new int[] { 3, 8, 6, 3 }, 25);
	public static ArmorMaterial aMatCMB = EnumHelper.addArmorMaterial("HBM_CMB", 60, new int[] { 3, 8, 6, 3 }, 50);
	public static ArmorMaterial aMatAus3 = EnumHelper.addArmorMaterial("HBM_AUSIII", 375, new int[] { 2, 6, 5, 2 }, 0);
	public static ArmorMaterial aMatSecurity = EnumHelper.addArmorMaterial("HBM_SECURITY", 100, new int[] { 3, 8, 6, 3 }, 15);
	public static ArmorMaterial aMatCobalt = EnumHelper.addArmorMaterial("HBM_COBALT", 70, new int[] { 3, 8, 6, 3 }, 25);
	public static ArmorMaterial aMatStarmetal = EnumHelper.addArmorMaterial("HBM_STARMETAL", 150, new int[] { 3, 8, 6, 3 }, 100);
	public static ArmorMaterial aMatBismuth = EnumHelper.addArmorMaterial("HBM_BISMUTH", 100, new int[] { 3, 8, 6, 3 }, 100);

	// Creative Tabs
	
	public static CreativeTabs partsTab = new PartsTab(CreativeTabs.getNextID(), "tabParts");					// ingots, nuggets, wires, machine parts
	public static CreativeTabs controlTab = new ControlTab(CreativeTabs.getNextID(), "tabControl");				// items that belong in machines, fuels, etc
	public static CreativeTabs templateTab = new TemplateTab(CreativeTabs.getNextID(), "tabTemplate");			// templates, siren tracks
	public static CreativeTabs blockTab = new BlockTab(CreativeTabs.getNextID(), "tabBlocks");					// ore and mineral blocks
	public static CreativeTabs machineTab = new MachineTab(CreativeTabs.getNextID(), "tabMachine");				// machines, structure parts
	public static CreativeTabs nukeTab = new NukeTab(CreativeTabs.getNextID(), "tabNuke");						// bombs
	public static CreativeTabs missileTab = new MissileTab(CreativeTabs.getNextID(), "tabMissile");				// missiles, satellites
	public static CreativeTabs weaponTab = new WeaponTab(CreativeTabs.getNextID(), "tabWeapon");				// turrets, weapons, ammo
	public static CreativeTabs consumableTab = new ConsumableTab(CreativeTabs.getNextID(), "tabConsumable");	// drinks, kits, tools

	// Achievements
	public static Achievement achSacrifice;
	public static Achievement achImpossible;
	public static Achievement achTOB;
	public static Achievement achFreytag;
	public static Achievement achPotato;
	public static Achievement achC44;
	public static Achievement achC20_5;
	public static Achievement achFiend;
	public static Achievement achFiend2;
	public static Achievement achRadPoison;
	public static Achievement achRadDeath;
	public static Achievement achStratum;
	public static Achievement achOmega12;
	public static Achievement achSomeWounds;
	public static Achievement achSlimeball;
	public static Achievement achSulfuric;
	public static Achievement achWitchtaunter;
	public static Achievement bobHidden;
	public static Achievement horizonsStart;
	public static Achievement horizonsEnd;
	public static Achievement horizonsBonus;
	public static Achievement bossCreeper;
	public static Achievement bossMeltdown;
	public static Achievement bossMaskman;
	public static Achievement bossWorm;
	public static Achievement bossUFO;
	public static Achievement digammaSee;
	public static Achievement digammaFeel;
	public static Achievement digammaKnow;
	public static Achievement digammaKauaiMoho;
	public static Achievement digammaUpOnTop;
	
	public static Achievement achBurnerPress;
	public static Achievement achBlastFurnace;
	public static Achievement achAssembly;
	public static Achievement achSelenium;
	public static Achievement achChemplant;
	public static Achievement achConcrete;
	public static Achievement achPolymer;
	public static Achievement achDesh;
	public static Achievement achTantalum;
	public static Achievement achRedBalloons;
	public static Achievement achManhattan;
	public static Achievement achGasCent;
	public static Achievement achCentrifuge;
	public static Achievement achFOEQ;
	public static Achievement achSoyuz;
	public static Achievement achSpace;
	public static Achievement achSchrab;
	public static Achievement achAcidizer;
	public static Achievement achRadium;
	public static Achievement achTechnetium;
	public static Achievement achZIRNOXBoom;
	public static Achievement achChicagoPile;
	public static Achievement achSILEX;
	public static Achievement achWatz;
	public static Achievement achWatzBoom;
	public static Achievement achRBMK;
	public static Achievement achRBMKBoom;
	public static Achievement achBismuth;
	public static Achievement achBreeding;
	public static Achievement achFusion;
	public static Achievement achMeltdown;
	
	public static int generalOverride = 0;
	public static int polaroidID = 1;
	
	public static long startupTime = 0;
	public static File configDir;

	Random rand = new Random();

	@EventHandler
	public void PreLoad(FMLPreInitializationEvent PreEvent) {
		
		startupTime = System.currentTimeMillis();
		configDir = PreEvent.getModConfigurationDirectory();
		
		logger.info("Let us celebrate the fact that the logger finally works again!");

		// Reroll Polaroid

		if(generalOverride > 0 && generalOverride < 19) {
			polaroidID = generalOverride;
		} else {
			polaroidID = rand.nextInt(18) + 1;
			while(polaroidID == 4 || polaroidID == 9)
				polaroidID = rand.nextInt(18) + 1;
		}

		loadConfig(PreEvent);
		HbmPotion.init();
		
		Fluids.init();
		ModBlocks.mainRegistry();
		ModItems.mainRegistry();
		proxy.registerRenderInfo();
		HbmWorld.mainRegistry();
		GameRegistry.registerFuelHandler(new FuelHandler());
		BulletConfigSyncingUtil.loadConfigsForSync();
		CellularDungeonFactory.init();
		Satellite.register();
		HTTPHandler.loadStats();
		CraftingManager.mainRegistry();
		AssemblerRecipes.preInit(PreEvent.getModConfigurationDirectory());
		SiegeTier.registerTiers();
		HazardRegistry.registerItems();
		HazardRegistry.registerTrafos();
		OreDictManager.registerGroups();

		Library.superuser.add("192af5d7-ed0f-48d8-bd89-9d41af8524f8");
		Library.superuser.add("5aee1e3d-3767-4987-a222-e7ce1fbdf88e");
		Library.superuser.add("937c9804-e11f-4ad2-a5b1-42e62ac73077");
		Library.superuser.add("3af1c262-61c0-4b12-a4cb-424cc3a9c8c0");
		Library.superuser.add("4729b498-a81c-42fd-8acd-20d6d9f759e0");
		Library.superuser.add("c3f5e449-6d8c-4fe3-acc9-47ef50e7e7ae");

		aMatSchrab.customCraftingMaterial = ModItems.ingot_schrabidium;
		aMatHaz.customCraftingMaterial = ModItems.hazmat_cloth;
		aMatHaz2.customCraftingMaterial = ModItems.hazmat_cloth_red;
		aMatHaz3.customCraftingMaterial = ModItems.hazmat_cloth_grey;
		aMatTitan.customCraftingMaterial = ModItems.ingot_titanium;
		aMatSteel.customCraftingMaterial = ModItems.ingot_steel;
		aMatAsbestos.customCraftingMaterial = ModItems.asbestos_cloth;
		aMatAlloy.customCraftingMaterial = ModItems.ingot_advanced_alloy;
		aMatPaa.customCraftingMaterial = ModItems.plate_paa;
		aMatCMB.customCraftingMaterial = ModItems.ingot_combine_steel;
		aMatAus3.customCraftingMaterial = ModItems.ingot_australium;
		aMatSecurity.customCraftingMaterial = ModItems.plate_kevlar;
		aMatCobalt.customCraftingMaterial = ModItems.ingot_cobalt;
		aMatStarmetal.customCraftingMaterial = ModItems.ingot_starmetal;
		tMatSchrab.setRepairItem(new ItemStack(ModItems.ingot_schrabidium));
		tMatHammmer.setRepairItem(new ItemStack(Item.getItemFromBlock(ModBlocks.block_schrabidium)));
		tMatChainsaw.setRepairItem(new ItemStack(ModItems.ingot_steel));
		tMatTitan.setRepairItem(new ItemStack(ModItems.ingot_titanium));
		tMatSteel.setRepairItem(new ItemStack(ModItems.ingot_steel));
		tMatAlloy.setRepairItem(new ItemStack(ModItems.ingot_advanced_alloy));
		tMatCMB.setRepairItem(new ItemStack(ModItems.ingot_combine_steel));
		enumToolMaterialBottleOpener.setRepairItem(new ItemStack(ModItems.plate_steel));
		tMatDesh.setRepairItem(new ItemStack(ModItems.ingot_desh));

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
		
		TileMappings.writeMappings();
		
		for(Entry<Class<? extends TileEntity>, String[]> e : TileMappings.map.entrySet()) {
			
			if(e.getValue().length == 1)
				GameRegistry.registerTileEntity(e.getKey(), e.getValue()[0]);
			else
				GameRegistry.registerTileEntityWithAlternatives(e.getKey(), e.getValue()[0], e.getValue());
		}

		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ModItems.armor_polish), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ModItems.bathwater), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.bathwater), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.serum), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.scrumpy), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.scrumpy), 1, 1, 1));

		EntityRegistry.registerModEntity(EntityRocket.class, "entity_rocket", 0, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityNukeExplosion.class, "entity_nuke_explosion", 1, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityNukeExplosionAdvanced.class, "entity_nuke_explosion_advanced", 2, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeGeneric.class, "entity_grenade_generic", 3, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeStrong.class, "entity_grenade_strong", 4, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeFrag.class, "entity_grenade_frag", 5, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeFire.class, "entity_grenade_fire", 6, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeCluster.class, "entity_grenade_cluster", 7, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityTestMissile.class, "entity_test_missile", 8, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityNukeCloudSmall.class, "entity_nuke_cloud_small", 9, this, 10000, 1, true);
		EntityRegistry.registerModEntity(EntityBullet.class, "entity_bullet", 10, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeFlare.class, "entity_grenade_flare", 11, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeElectric.class, "entity_grenade_electric", 12, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadePoison.class, "entity_grenade_poison", 13, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeGas.class, "entity_grenade_gas", 14, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeSchrabidium.class, "entity_grenade_schrab", 15, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeNuke.class, "entity_grenade_nuke", 16, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntitySchrab.class, "entity_schrabnel", 17, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityMissileGeneric.class, "entity_missile_generic", 18, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileStrong.class, "entity_missile_strong", 19, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileNuclear.class, "entity_missile_nuclear", 20, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileCluster.class, "entity_missile_cluster", 21, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileIncendiary.class, "entity_missile_incendiary", 22, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileAntiBallistic.class, "entity_missile_anti", 23, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileBunkerBuster.class, "entity_missile_buster", 24, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileIncendiaryStrong.class, "entity_missile_incendiary_strong", 25, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileClusterStrong.class, "entity_missile_cluster_strong", 26, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileBusterStrong.class, "entity_missile_buster_strong", 27, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileBurst.class, "entity_missile_burst", 28, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileInferno.class, "entity_missile_inferno", 29, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileRain.class, "entity_missile_rain", 30, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileDrill.class, "entity_missile_drill", 31, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileEndo.class, "entity_missile_endo", 32, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileExo.class, "entity_missile_exo", 33, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileMirv.class, "entity_missile_mirv", 34, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMIRV.class, "entity_mirvlet", 35, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntitySmokeFX.class, "entity_smoke_fx", 37, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityNukeCloudBig.class, "entity_nuke_cloud_big", 38, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeNuclear.class, "entity_grenade_nuclear", 39, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBSmokeFX.class, "entity_b_smoke_fx", 40, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadePlasma.class, "entity_grenade_plasma", 41, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeTau.class, "entity_grenade_tau", 42, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityChopperMine.class, "entity_chopper_mine", 43, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityCombineBall.class, "entity_combine_ball", 44, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityRainbow.class, "entity_rainbow", 45, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeLemon.class, "entity_grenade_lemon", 46, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityCloudFleija.class, "entity_cloud_fleija", 47, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeMk2.class, "entity_grenade_mk2", 48, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeZOMG.class, "entity_grenade_zomg", 49, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeASchrab.class, "entity_grenade_aschrab", 50, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityNukeCloudNoShroom.class, "entity_nuke_cloud_no", 51, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityFalloutRain.class, "entity_fallout", 52, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityDischarge.class, "entity_emp_discharge", 53, this, 500, 1, true);
		EntityRegistry.registerModEntity(EntityEMPBlast.class, "entity_emp_blast", 54, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityFire.class, "entity_fire", 57, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityPlasmaBeam.class, "entity_immolator_beam", 58, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityLN2.class, "entity_LN2", 59, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityNightmareBlast.class, "entity_ominous_bullet", 60, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadePulse.class, "entity_grenade_pulse", 61, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityNukeExplosionPlus.class, "entity_nuke_explosion_advanced", 62, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityLaserBeam.class, "entity_laser_beam", 63, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMinerBeam.class, "entity_miner_beam", 64, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityRubble.class, "entity_rubble", 65, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityDSmokeFX.class, "entity_d_smoke_fx", 66, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntitySSmokeFX.class, "entity_s_smoke_fx", 67, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityShrapnel.class, "entity_shrapnel", 68, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeShrapnel.class, "entity_grenade_shrapnel", 69, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityBlackHole.class, "entity_black_hole", 70, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeBlackHole.class, "entity_grenade_black_hole", 71, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityOilSpillFX.class, "entity_spill_fx", 72, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityOilSpill.class, "entity_oil_spill", 73, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGasFX.class, "entity_spill_fx", 74, this, 1000, 1, true);
		//EntityRegistry.registerModEntity(EntityGasFlameFX.class, "entity_gasflame_fx", 75, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMinecartTest.class, "entity_minecart_test", 76, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntitySparkBeam.class, "entity_spark_beam", 77, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileDoomsday.class, "entity_missile_doomsday", 78, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBombletTheta.class, "entity_theta", 79, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBombletSelena.class, "entity_selena", 80, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityTSmokeFX.class, "entity_t_smoke_fx", 81, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityNukeExplosionMK3.class, "entity_nuke_mk3", 82, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityVortex.class, "entity_vortex", 83, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityMeteor.class, "entity_meteor", 84, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityLaser.class, "entity_laser", 85, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBoxcar.class, "entity_boxcar", 86, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileTaint.class, "entity_missile_taint", 87, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeGascan.class, "entity_grenade_gascan", 88, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityNukeExplosionMK4.class, "entity_nuke_mk4", 89, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityCloudFleijaRainbow.class, "entity_cloud_rainbow", 90, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityExplosiveBeam.class, "entity_beam_bomb", 91, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityAAShell.class, "entity_aa_shell", 92, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityRocketHoming.class, "entity_stinger", 93, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileMicro.class, "entity_missile_micronuclear", 94, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityCloudSolinium.class, "entity_cloud_rainbow", 95, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityRagingVortex.class, "entity_raging_vortex", 96, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityCarrier.class, "entity_missile_carrier", 97, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBooster.class, "entity_missile_booster", 98, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityModBeam.class, "entity_beam_bang", 99, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileBHole.class, "entity_missile_blackhole", 100, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileSchrabidium.class, "entity_missile_schrabidium", 101, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileEMP.class, "entity_missile_emp", 102, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityChlorineFX.class, "entity_chlorine_fx", 103, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityPinkCloudFX.class, "entity_pink_cloud_fx", 104, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityCloudFX.class, "entity_cloud_fx", 105, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadePC.class, "entity_grenade_pink_cloud", 106, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeCloud.class, "entity_grenade_cloud", 107, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityBomber.class, "entity_bomber", 108, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBombletZeta.class, "entity_zeta", 109, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityOrangeFX.class, "entity_agent_orange", 110, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityDeathBlast.class, "entity_laser_blast", 111, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeSmart.class, "entity_grenade_smart", 112, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeMIRV.class, "entity_grenade_mirv", 113, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeBreach.class, "entity_grenade_breach", 114, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeBurst.class, "entity_grenade_burst", 115, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityBurningFOEQ.class, "entity_burning_foeq", 116, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFGeneric.class, "entity_grenade_ironshod", 117, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFHE.class, "entity_grenade_ironshod_he", 118, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFBouncy.class, "entity_grenade_ironshod_bouncy", 119, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFSticky.class, "entity_grenade_ironshod_sticky", 120, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFImpact.class, "entity_grenade_ironshod_impact", 121, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFIncendiary.class, "entity_grenade_ironshod_fire", 122, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFToxic.class, "entity_grenade_ironshod_toxic", 123, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFConcussion.class, "entity_grenade_ironshod_con", 124, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFBrimstone.class, "entity_grenade_ironshod_brim", 125, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFMystery.class, "entity_grenade_ironshod_m", 126, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFSpark.class, "entity_grenade_ironshod_s", 127, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFHopwire.class, "entity_grenade_ironshod_hopwire", 128, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFNull.class, "entity_grenade_ironshod_null", 129, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityFallingNuke.class, "entity_falling_bomb", 130, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBulletBase.class, "entity_bullet_mk2", 131, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityMinerRocket.class, "entity_miner_lander", 132, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityFogFX.class, "entity_nuclear_fog", 133, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityDuchessGambit.class, "entity_duchessgambit", 134, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileEMPStrong.class, "entity_missile_emp_strong", 135, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityEMP.class, "entity_emp_logic", 136, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityWaterSplash.class, "entity_water_splash", 137, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBobmazon.class, "entity_bobmazon_delivery", 138, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileCustom.class, "entity_custom_missile", 139, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBalefire.class, "entity_balefire", 140, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityTom.class, "entity_tom_the_moonstone", 141, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityTomBlast.class, "entity_tom_bust", 142, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBuilding.class, "entity_falling_building", 143, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntitySoyuz.class, "entity_soyuz", 144, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntitySoyuzCapsule.class, "entity_soyuz_capsule", 145, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMovingItem.class, "entity_c_item", 146, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityCloudTom.class, "entity_moonstone_blast", 147, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBeamVortex.class, "entity_vortex_beam", 148, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityFireworks.class, "entity_firework_ball", 149, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityWastePearl.class, "entity_waste_pearl", 150, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBOTPrimeHead.class, "entity_balls_o_tron", 151, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBOTPrimeBody.class, "entity_balls_o_tron_seg", 152, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBlockSpider.class, "entity_taintcrawler", 153, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityRBMKDebris.class, "entity_rbmk_debris", 154, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityUFO.class, "entity_ntm_ufo", 155, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityNukeExplosionNT.class, "entity_ntm_explosion_nt", 156, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityQuasar.class, "entity_digamma_quasar", 157, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntitySpear.class, "entity_digamma_spear", 158, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileVolcano.class, "entity_missile_volcano", 159, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityMissileShuttle.class, "entity_missile_shuttle", 160, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityZirnoxDebris.class, "entity_zirnox_debris", 161, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGhost.class, "entity_ntm_ghost", 162, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeDynamite.class, "entity_grenade_dynamite", 163, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntitySiegeLaser.class, "entity_ntm_siege_laser", 164, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntitySiegeDropship.class, "entity_ntm_siege_dropship", 165, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityTNTPrimedBase.class, "entity_ntm_tnt_primed", 166, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeBouncyGeneric.class, "entity_grenade_bouncy_generic", 168, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeImpactGeneric.class, "entity_grenade_impact_generic", 169, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityMinecartCrate.class, "entity_ntm_cart_crate", 170, this, 250, 1, false);
		EntityRegistry.registerModEntity(EntityMinecartDestroyer.class, "entity_ntm_cart_crate", 171, this, 250, 1, false);
		EntityRegistry.registerModEntity(EntityMinecartOre.class, "entity_ntm_cart_ore", 172, this, 250, 1, false);
		EntityRegistry.registerModEntity(EntityMinecartBogie.class, "entity_ntm_cart_bogie", 173, this, 250, 1, false);
		EntityRegistry.registerModEntity(EntityMagnusCartus.class, "entity_ntm_cart_chungoid", 174, this, 250, 1, false);
		EntityRegistry.registerModEntity(EntityMinecartPowder.class, "entity_ntm_cart_powder", 175, this, 250, 1, false);
		EntityRegistry.registerModEntity(EntityMinecartSemtex.class, "entity_ntm_cart_semtex", 176, this, 250, 1, false);

		EntityRegistry.registerGlobalEntityID(EntityNuclearCreeper.class, "entity_mob_nuclear_creeper", EntityRegistry.findGlobalUniqueEntityId(), 0x204131, 0x75CE00);
		EntityRegistry.registerGlobalEntityID(EntityTaintedCreeper.class, "entity_mob_tainted_creeper", EntityRegistry.findGlobalUniqueEntityId(), 0x813b9b, 0xd71fdd);
		EntityRegistry.registerGlobalEntityID(EntityHunterChopper.class, "entity_mob_hunter_chopper", EntityRegistry.findGlobalUniqueEntityId(), 0x000020, 0x2D2D72);
		EntityRegistry.registerGlobalEntityID(EntityCyberCrab.class, "entity_cyber_crab", EntityRegistry.findGlobalUniqueEntityId(), 0xAAAAAA, 0x444444);
		EntityRegistry.registerGlobalEntityID(EntityTeslaCrab.class, "entity_tesla_crab", EntityRegistry.findGlobalUniqueEntityId(), 0xAAAAAA, 0x440000);
		EntityRegistry.registerGlobalEntityID(EntityTaintCrab.class, "entity_taint_crab", EntityRegistry.findGlobalUniqueEntityId(), 0xAAAAAA, 0xFF00FF);
		EntityRegistry.registerGlobalEntityID(EntityMaskMan.class, "entity_mob_mask_man", EntityRegistry.findGlobalUniqueEntityId(), 0x818572, 0xC7C1B7);
		EntityRegistry.registerGlobalEntityID(EntityDuck.class, "entity_fucc_a_ducc", EntityRegistry.findGlobalUniqueEntityId(), 0xd0d0d0, 0xFFBF00);
		EntityRegistry.registerGlobalEntityID(EntityQuackos.class, "entity_elder_one", EntityRegistry.findGlobalUniqueEntityId(), 0xd0d0d0, 0xFFBF00);
		EntityRegistry.registerGlobalEntityID(EntityFBI.class, "entity_ntm_fbi", EntityRegistry.findGlobalUniqueEntityId(), 0x008000, 0x404040);
		EntityRegistry.registerGlobalEntityID(EntityRADBeast.class, "entity_ntm_radiation_blaze", EntityRegistry.findGlobalUniqueEntityId(), 0x303030, 0x008000);
		EntityRegistry.registerGlobalEntityID(EntitySiegeZombie.class, "entity_meme_zombie", EntityRegistry.findGlobalUniqueEntityId(), 0x303030, 0x008000);
		EntityRegistry.registerGlobalEntityID(EntitySiegeSkeleton.class, "entity_meme_skeleton", EntityRegistry.findGlobalUniqueEntityId(), 0x303030, 0x000080);
		EntityRegistry.registerGlobalEntityID(EntitySiegeUFO.class, "entity_meme_ufo", EntityRegistry.findGlobalUniqueEntityId(), 0x303030, 0x800000);
		EntityRegistry.registerGlobalEntityID(EntitySiegeCraft.class, "entity_meme_craft", EntityRegistry.findGlobalUniqueEntityId(), 0x303030, 0x808000);
		EntityRegistry.registerModEntity(EntitySiegeTunneler.class, "entity_meme_tunneler", 167, this, 1000, 1, true); //how about you have a taste of my fucking scrotum?
		//EntityRegistry.registerGlobalEntityID(EntitySiegeTunneler.class, "entity_meme_tunneler", EntityRegistry.findGlobalUniqueEntityId(), 0x303030, 0x008080);

		EntityRegistry.registerModEntity(EntitySPV.class, "entity_self_propelled_vehicle_mark_1", 1600, this, 1000, 1, true);

		ForgeChunkManager.setForcedChunkLoadingCallback(this, new LoadingCallback() {

			@Override
			public void ticketsLoaded(List<Ticket> tickets, World world) {
				for(Ticket ticket : tickets) {

					if(ticket.getEntity() instanceof IChunkLoader) {
						((IChunkLoader) ticket.getEntity()).init(ticket);
					}
				}
			}
		});

		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_generic, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeGeneric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_strong, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeStrong(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_frag, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeFrag(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_fire, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeFire(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_cluster, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeCluster(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_flare, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeFlare(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_electric, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeElectric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_poison, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadePoison(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_gas, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeGas(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_schrabidium, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeSchrabidium(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_nuke, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeNuke(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_nuclear, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeNuclear(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_pulse, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadePulse(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_plasma, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadePlasma(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_tau, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeTau(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_lemon, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeLemon(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_mk2, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeMk2(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_aschrab, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeASchrab(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_zomg, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeZOMG(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_shrapnel, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeShrapnel(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_black_hole, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeBlackHole(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_gascan, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeGascan(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_cloud, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeCloud(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_pink_cloud, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadePC(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_smart, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeSmart(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_mirv, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeMIRV(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_breach, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeBreach(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_burst, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeBurst(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_generic, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFGeneric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_he, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFHE(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_bouncy, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFBouncy(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_sticky, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFSticky(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_impact, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFImpact(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_incendiary, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFIncendiary(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_toxic, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFToxic(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_concussion, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFConcussion(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_brimstone, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFBrimstone(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_mystery, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFMystery(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_spark, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFSpark(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_hopwire, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFHopwire(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_null, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_) {
				return new EntityGrenadeIFNull(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.nuclear_waste_pearl, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityWastePearl(world, position.getX(), position.getY(), position.getZ());
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.stick_dynamite, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeDynamite(world, position.getX(), position.getY(), position.getZ());
			}
		});
	}

	@EventHandler
	public static void load(FMLInitializationEvent event) {

		achSacrifice = new Achievement("achievement.sacrifice", "sacrifice", -3, 1, ModItems.burnt_bark, null).initIndependentStat().setSpecial().registerStat();
		achImpossible = new Achievement("achievement.impossible", "impossible", 18, 10, ModItems.nothing, null).initIndependentStat().setSpecial().registerStat();
		achTOB = new Achievement("achievement.tasteofblood", "tasteofblood", 3, 10, new ItemStack(ModItems.fluid_icon, 1, Fluids.ASCHRAB.getID()), null).initIndependentStat().setSpecial().registerStat();
		achFreytag = new Achievement("achievement.freytag", "freytag", 0, -4, ModItems.gun_mp40, null).initIndependentStat().setSpecial().registerStat();
		achPotato = new Achievement("achievement.potato", "potato", -2, -2, ModItems.battery_potatos, null).initIndependentStat().setSpecial().registerStat();
		achC44 = new Achievement("achievement.c44", "c44", 2, -4, ModItems.gun_revolver_pip, null).initIndependentStat().setSpecial().registerStat();
		achC20_5 = new Achievement("achievement.c20_5", "c20_5", 3, 6, ModItems.gun_dampfmaschine, null).initIndependentStat().setSpecial().registerStat();
		achFiend = new Achievement("achievement.fiend", "fiend", -6, 8, ModItems.shimmer_sledge, null).initIndependentStat().setSpecial().registerStat();
		achFiend2 = new Achievement("achievement.fiend2", "fiend2", -4, 9, ModItems.shimmer_axe, null).initIndependentStat().setSpecial().registerStat();
		achStratum = new Achievement("achievement.stratum", "stratum", -4, -2, new ItemStack(ModBlocks.stone_gneiss), null).initIndependentStat().setSpecial().registerStat();
		achOmega12 = new Achievement("achievement.omega12", "omega12", 17, -1, ModItems.particle_digamma, null).initIndependentStat().setSpecial().registerStat();

		achWitchtaunter = new Achievement("achievement.witchtaunter", "witchtaunter", -8, 7, ModItems.ammo_4gauge_vampire, null).initIndependentStat().setSpecial().registerStat();
		achSlimeball = new Achievement("achievement.slimeball", "slimeball", -10, 6, Items.slime_ball, null).initIndependentStat().registerStat();
		achSulfuric = new Achievement("achievement.sulfuric", "sulfuric", -10, 8, ModItems.bucket_sulfuric_acid, achSlimeball).initIndependentStat().setSpecial().registerStat();

		bobHidden = new Achievement("achievement.hidden", "hidden", 15, -4, ModItems.gun_dampfmaschine, null).initIndependentStat().registerStat();

		horizonsStart = new Achievement("achievement.horizonsStart", "horizonsStart", -5, 4, ModItems.sat_gerald, null).initIndependentStat().registerStat();
		horizonsEnd = new Achievement("achievement.horizonsEnd", "horizonsEnd", -3, 4, ModItems.sat_gerald, horizonsStart).initIndependentStat().registerStat();
		horizonsBonus = new Achievement("achievement.horizonsBonus", "horizonsBonus", -1, 4, ModItems.sat_gerald, horizonsEnd).initIndependentStat().registerStat().setSpecial();

		bossCreeper = new Achievement("achievement.bossCreeper", "bossCreeper", -7, 1, ModItems.coin_creeper, null).initIndependentStat().registerStat();
		bossMeltdown = new Achievement("achievement.bossMeltdown", "bossMeltdown", -8, 3, ModItems.coin_radiation, bossCreeper).initIndependentStat().registerStat();
		bossMaskman = new Achievement("achievement.bossMaskman", "bossMaskman", -8, -1, ModItems.coin_maskman, bossCreeper).initIndependentStat().registerStat();
		bossWorm = new Achievement("achievement.bossWorm", "bossWorm", -8, -3, ModItems.coin_worm, bossMaskman).initIndependentStat().registerStat().setSpecial();
		bossUFO = new Achievement("achievement.bossUFO", "bossUFO", -6, -3, ModItems.coin_ufo, bossWorm).initIndependentStat().registerStat().setSpecial();

		achRadPoison = new Achievement("achievement.radPoison", "radPoison", -2, 6, ModItems.geiger_counter, null).initIndependentStat().registerStat();
		achRadDeath = new Achievement("achievement.radDeath", "radDeath", 0, 6, Items.skull, achRadPoison).initIndependentStat().registerStat().setSpecial();

		achSomeWounds = new Achievement("achievement.someWounds", "someWounds", -2, 10, ModItems.injector_knife, null).initIndependentStat().registerStat();
		
		digammaSee = new Achievement("achievement.digammaSee", "digammaSee", -1, 8, ModItems.digamma_see, null).initIndependentStat().registerStat();
		digammaFeel = new Achievement("achievement.digammaFeel", "digammaFeel", 1, 8, ModItems.digamma_feel, digammaSee).initIndependentStat().registerStat();
		digammaKnow = new Achievement("achievement.digammaKnow", "digammaKnow", 3, 8, ModItems.digamma_know, digammaFeel).initIndependentStat().registerStat().setSpecial();
		digammaKauaiMoho = new Achievement("achievement.digammaKauaiMoho", "digammaKauaiMoho", 5, 8, ModItems.digamma_kauai_moho, digammaKnow).initIndependentStat().registerStat().setSpecial();
		digammaUpOnTop = new Achievement("achievement.digammaUpOnTop", "digammaUpOnTop", 7, 8, ModItems.digamma_up_on_top, digammaKauaiMoho).initIndependentStat().registerStat().setSpecial();
		
		//progression achieves
		achBurnerPress = new Achievement("achievement.burnerPress", "burnerPress", 0, 0, new ItemStack(ModBlocks.machine_press), null).initIndependentStat().registerStat();
		achBlastFurnace = new Achievement("achievement.blastFurnace", "blastFurnace", 1, 3, new ItemStack(ModBlocks.machine_difurnace_off), achBurnerPress).initIndependentStat().registerStat();
		achAssembly = new Achievement("achievement.assembly", "assembly", 3, -1, new ItemStack(ModBlocks.machine_assembler), achBurnerPress).initIndependentStat().registerStat();
		achSelenium = new Achievement("achievement.selenium", "selenium", 3, 2, ModItems.ingot_starmetal, achBurnerPress).initIndependentStat().setSpecial().registerStat();
		achChemplant = new Achievement("achievement.chemplant", "chemplant", 6, -1, new ItemStack(ModBlocks.machine_chemplant), achAssembly).initIndependentStat().registerStat();
		achConcrete	= new Achievement("achievement.concrete", "concrete", 6, -4, new ItemStack(ModBlocks.concrete), achChemplant).initIndependentStat().registerStat();
		achPolymer = new Achievement("achievement.polymer", "polymer", 9, -1, ModItems.ingot_polymer, achChemplant).initIndependentStat().registerStat();
		achDesh = new Achievement("achievement.desh", "desh", 9, 2, ModItems.ingot_desh, achChemplant).initIndependentStat().registerStat();
		achTantalum = new Achievement("achievement.tantalum", "tantalum", 7, 3, ModItems.gem_tantalium, achChemplant).initIndependentStat().setSpecial().registerStat();
		achGasCent = new Achievement("achievement.gasCent", "gasCent", 13, 2, ModItems.ingot_uranium_fuel, achDesh).initIndependentStat().registerStat();
		achCentrifuge = new Achievement("achievement.centrifuge", "centrifuge", 12, -2, new ItemStack(ModBlocks.machine_centrifuge), achPolymer).initIndependentStat().registerStat();
		achFOEQ = new Achievement("achievement.FOEQ", "FOEQ", 5, 5, ModItems.sat_foeq, achDesh).initIndependentStat().setSpecial().registerStat();
		achSoyuz = new Achievement("achievement.soyuz", "soyuz", 7, 6, Items.baked_potato, achDesh).initIndependentStat().setSpecial().registerStat();
		achSpace = new Achievement("achievement.space", "space", 9, 7, ModItems.missile_carrier, achDesh).initIndependentStat().setSpecial().registerStat();
		achSchrab = new Achievement("achievement.schrab", "schrab", 11, 3, ModItems.ingot_schrabidium, achDesh).initIndependentStat().registerStat();
		achAcidizer = new Achievement("achievement.acidizer", "acidizer", 11, 5, new ItemStack(ModBlocks.machine_crystallizer), achDesh).initIndependentStat().registerStat();
		achRadium = new Achievement("achievement.radium", "radium", 13, -4, ModItems.coffee_radium, achCentrifuge).initIndependentStat().setSpecial().registerStat();
		achTechnetium = new Achievement("achievement.technetium", "technetium", 15, -2, ModItems.ingot_tcalloy, achCentrifuge).initIndependentStat().registerStat();
		achZIRNOXBoom = new Achievement("achievement.ZIRNOXBoom", "ZIRNOXBoom", 14, -1, ModItems.debris_element, achCentrifuge).initIndependentStat().setSpecial().registerStat();
		achChicagoPile = new Achievement("achievement.chicagoPile", "chicagoPile", 13, 0, ModItems.pile_rod_plutonium, achCentrifuge).initIndependentStat().registerStat();
		achSILEX = new Achievement("achievement.SILEX", "SILEX", 12, 7, new ItemStack(ModBlocks.machine_silex), achAcidizer).initIndependentStat().registerStat();
		achWatz = new Achievement("achievement.watz", "watz", 14, 3, ModItems.pellet_schrabidium, achSchrab).initIndependentStat().registerStat();
		achWatzBoom = new Achievement("achievement.watzBoom", "watzBoom", 14, 5, ModItems.bucket_mud, achWatz).initIndependentStat().setSpecial().registerStat();
		achRBMK = new Achievement("achievement.RBMK", "RBMK", 9, -5, ModItems.rbmk_fuel_ueu, achConcrete).initIndependentStat().registerStat();
		achRBMKBoom = new Achievement("achievement.RBMKBoom", "RBMKBoom", 9, -7, ModItems.debris_fuel, achRBMK).initIndependentStat().setSpecial().registerStat();
		achBismuth = new Achievement("achievement.bismuth", "bismuth", 11, -6, ModItems.ingot_bismuth, achRBMK).initIndependentStat().registerStat();
		achBreeding = new Achievement("achievement.breeding", "breeding", 7, -6, ModItems.ingot_am_mix, achRBMK).initIndependentStat().setSpecial().registerStat();
		achFusion = new Achievement("achievement.fusion", "fusion", 13, -7, new ItemStack(ModBlocks.iter), achBismuth).initIndependentStat().setSpecial().registerStat();
		achMeltdown = new Achievement("achievement.meltdown", "meltdown", 15, -7, ModItems.crystal_energy, achFusion).initIndependentStat().setSpecial().registerStat();
		achRedBalloons = new Achievement("achievement.redBalloons", "redBalloons", 11, 0, ModItems.missile_nuclear, achPolymer).initIndependentStat().setSpecial().registerStat();
		achManhattan = new Achievement("achievement.manhattan", "manhattan", 11, -4, new ItemStack(ModBlocks.nuke_boy), achPolymer).initIndependentStat().setSpecial().registerStat();
		
		AchievementPage.registerAchievementPage(new AchievementPage("Nuclear Tech", new Achievement[] {
				achSacrifice,
				achImpossible,
				achTOB,
				achFreytag,
				achPotato,
				achC44,
				achC20_5,
				achFiend,
				achFiend2,
				achStratum,
				achOmega12,
				bobHidden,
				horizonsStart,
				horizonsEnd,
				horizonsBonus,
				achRadPoison,
				achRadDeath,
				achWitchtaunter,
				achSlimeball,
				achSulfuric,
				bossCreeper,
				bossMeltdown,
				bossMaskman,
				bossWorm,
				bossUFO,
				achSomeWounds,
				digammaSee,
				digammaFeel,
				digammaKnow,
				digammaKauaiMoho,
				digammaUpOnTop,
				
				achBurnerPress,
				achBlastFurnace,
				achAssembly,
				achSelenium,
				achChemplant,
				achConcrete,
				achPolymer,
				achDesh,
				achTantalum,
				achGasCent,
				achCentrifuge,
				achFOEQ,
				achSoyuz,
				achSpace,
				achSchrab,
				achAcidizer,
				achRadium,
				achTechnetium,
				achZIRNOXBoom,
				achChicagoPile,
				achSILEX,
				achWatz,
				achWatzBoom,
				achRBMK,
				achRBMKBoom,
				achBismuth,
				achBreeding,
				achFusion,
				achMeltdown,
				achRedBalloons,
				achManhattan
		}));

		// MUST be initialized AFTER achievements!!
		BobmazonOfferFactory.init();
		OreDictManager.registerOres();

		IMCHandler.registerHandler("crystallizer", new IMCCrystallizer());
		IMCHandler.registerHandler("centrifuge", new IMCCentrifuge());
	}
	
	@EventHandler
	public static void initIMC(IMCEvent event) {
		
		ImmutableList<IMCMessage> inbox = event.getMessages(); //tee-hee
		
		for(IMCMessage message : inbox) {
			IMCHandler handler = IMCHandler.getHandler(message.key);
			
			if(handler != null) {
				MainRegistry.logger.info("Received IMC of type >" + message.key + "< from " + message.getSender() + "!");
				handler.process(message);
			} else {
				MainRegistry.logger.error("Could not process unknown IMC type \"" + message.key + "\"");
			}
		}
	}

	@EventHandler
	public static void PostLoad(FMLPostInitializationEvent PostEvent) {
		ShredderRecipes.registerShredder();
		ShredderRecipes.registerOverrides();
		CrystallizerRecipes.register();
		CentrifugeRecipes.register();
		TileEntityNukeFurnace.registerFuels();
		BreederRecipes.registerRecipes();
		AssemblerRecipes.loadRecipes();
		//ChemplantRecipes.register(); moved to SerializableRecipe
		CyclotronRecipes.register();
		//HadronRecipes.register(); moved to SerializableRecipe
		MagicRecipes.register();
		SILEXRecipes.register();
		AnvilRecipes.register();
		PressRecipes.register();
		RefineryRecipes.registerRefinery();
		RefineryRecipes.registerFractions();
		RefineryRecipes.registerCracking();
		RadiolysisRecipes.registerRadiolysis();
		GasCentrifugeRecipes.register();
		LiquefactionRecipes.register();
		SolidificationRecipes.register();

		//the good stuff
		SerializableRecipe.registerAllHandlers();
		SerializableRecipe.initialize();

		TileEntityNukeCustom.registerBombItems();
		ArmorUtil.register();
		HazmatRegistry.registerHazmats();
		FluidContainerRegistry.register();
		TileEntityMachineReactorLarge.registerAll();

		proxy.registerMissileItems();
		
		BlockMotherOfAllOres.init();
		
		//expand for the largest entity we have (currently Quackos who is 17.5m in diameter, that's one fat duck)
		World.MAX_ENTITY_RADIUS = Math.max(World.MAX_ENTITY_RADIUS, 8.75);

		new OreCave(ModBlocks.stone_resource, 0).setThreshold(1.5D).setRangeMult(20).setYLevel(30).setMaxRange(20).withFluid(ModBlocks.sulfuric_acid_block);	//sulfur
		new OreCave(ModBlocks.stone_resource, 1).setThreshold(1.75D).setRangeMult(20).setYLevel(25).setMaxRange(20);											//asbestos
		//new OreLayer(Blocks.coal_ore, 0.2F).setThreshold(4).setRangeMult(3).setYLevel(70);

		//imagine adding flower entries but they don't actually do shit with the world generator
		//"well but at least they work with bone meal, as advertised" except they fucking don't
		/*BiomeGenBase.plains.addFlower(ModBlocks.plant_flower, EnumFlowerType.FOXGLOVE.ordinal(), 10);
		BiomeGenBase.roofedForest.addFlower(ModBlocks.plant_flower, EnumFlowerType.NIGHTSHADE.ordinal(), 10);
		BiomeGenBase.jungle.addFlower(ModBlocks.plant_flower, EnumFlowerType.TOBACCO.ordinal(), 10);*/
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if(logger == null)
			logger = event.getModLog();

		ModEventHandler commonHandler = new ModEventHandler();
		FMLCommonHandler.instance().bus().register(commonHandler);
		MinecraftForge.EVENT_BUS.register(commonHandler);
		MinecraftForge.TERRAIN_GEN_BUS.register(commonHandler);
		MinecraftForge.ORE_GEN_BUS.register(commonHandler);
		
		SchistStratum schist = new SchistStratum();
		MinecraftForge.EVENT_BUS.register(schist); //DecorateBiomeEvent.Pre
		
		OreDictManager oreMan = new OreDictManager();
		MinecraftForge.EVENT_BUS.register(oreMan); //OreRegisterEvent
		
		PacketDispatcher.registerPackets();

		ChunkRadiationManager radiationSystem = new ChunkRadiationManager();
		MinecraftForge.EVENT_BUS.register(radiationSystem);
		FMLCommonHandler.instance().bus().register(radiationSystem);
		
		if(event.getSide() == Side.CLIENT) {
			HbmKeybinds.register();
			HbmKeybinds keyHandler = new HbmKeybinds();
			FMLCommonHandler.instance().bus().register(keyHandler);
		}
	}
	
	//yes kids, this is where we would usually register commands
	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		World world = event.getServer().getEntityWorld();
		RBMKDials.createDials(world);
		SiegeOrchestrator.createGameRules(world);
	}
	
	private void loadConfig(FMLPreInitializationEvent event) {

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		GeneralConfig.loadFromConfig(config);
		WorldConfig.loadFromConfig(config);
		MachineConfig.loadFromConfig(config);
		BombConfig.loadFromConfig(config);
		RadiationConfig.loadFromConfig(config);
		PotionConfig.loadFromConfig(config);
		ToolConfig.loadFromConfig(config);
		WeaponConfig.loadFromConfig(config);
		MobConfig.loadFromConfig(config);

		config.save();
	}
	
	private static HashSet<String> ignoreMappings = new HashSet();
	
	static {
		for(int i = 1; i <= 8; i++)
			ignoreMappings.add("hbm:item.gasflame" + i);
	}

	@EventHandler
	public void handleMissingMappings(FMLMissingMappingsEvent event) {
		
		for(MissingMapping mapping : event.get()) {
			if(mapping.type == GameRegistry.Type.ITEM) {
				if(ignoreMappings.contains(mapping.name)) {
					mapping.ignore();
				}
			}
		}
	}
}
