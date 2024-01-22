package com.hbm.main;

import com.google.common.collect.ImmutableList;
import com.hbm.blocks.BlockEnums.EnumStoneType;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockMotherOfAllOres;
import com.hbm.blocks.generic.BlockToolConversion;
import com.hbm.commands.*;
import com.hbm.config.*;
import com.hbm.crafting.RodRecipes;
import com.hbm.creativetabs.*;
import com.hbm.entity.EntityMappings;
import com.hbm.entity.grenade.*;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.mob.siege.SiegeTier;
import com.hbm.handler.*;
import com.hbm.handler.imc.IMCBlastFurnace;
import com.hbm.handler.imc.IMCCentrifuge;
import com.hbm.handler.imc.IMCCrystallizer;
import com.hbm.handler.imc.IMCHandler;
import com.hbm.handler.pollution.PollutionHandler;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.hazard.HazardRegistry;
import com.hbm.inventory.FluidContainerRegistry;
import com.hbm.inventory.OreDictManager;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.recipes.*;
import com.hbm.inventory.recipes.anvil.AnvilRecipes;
import com.hbm.inventory.recipes.loader.SerializableRecipe;
import com.hbm.items.ItemAmmoEnums.Ammo4Gauge;
import com.hbm.items.ItemEnums.EnumAchievementType;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemFertilizer;
import com.hbm.items.weapon.ItemGenericGrenade;
import com.hbm.lib.HbmWorld;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.tileentity.TileMappings;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.machine.TileEntityNukeFurnace;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.util.*;
import com.hbm.world.biome.BiomeGenCraterBase;
import com.hbm.world.feature.BedrockOre;
import com.hbm.world.feature.OreCave;
import com.hbm.world.feature.OreLayer3D;
import com.hbm.world.feature.SchistStratum;
import com.hbm.world.generator.CellularDungeonFactory;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

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
	public static ToolMaterial tMatCobalt = EnumHelper.addToolMaterial("HBM_COBALT", 3, 750, 9.0F, 2.5F, 60);

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
	public static ArmorMaterial aMatCobalt = EnumHelper.addArmorMaterial("HBM_COBALT", 70, new int[] { 3, 8, 6, 3 }, 60);
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
	public static Achievement achGoFish;
	public static Achievement achNo9;
	public static Achievement achInferno;
	public static Achievement achRedRoom;
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
	public static File configHbmDir;

	public Random rand = new Random();

	@EventHandler
	public void PreLoad(FMLPreInitializationEvent PreEvent) {
		
		startupTime = System.currentTimeMillis();
		configDir = PreEvent.getModConfigurationDirectory();
		configHbmDir = new File(configDir.getAbsolutePath() + File.separatorChar + "hbmConfig");

		if(!configHbmDir.exists()) configHbmDir.mkdir();
		
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
		
		/* For whichever fucking reason, replacing the bolt items with a bolt autogen broke all autogen items, most likely due to the load order.
		 * This "fix" just makes sure that the material system is loaded first no matter what. */
		Mats.MAT_STONE.getUnlocalizedName();
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
		
		OreDictManager oreMan = new OreDictManager();
		MinecraftForge.EVENT_BUS.register(oreMan); //OreRegisterEvent
		OreDictManager.registerGroups(); //important to run first
		OreDictManager.registerOres();
		
		if(WorldConfig.enableCraterBiomes) BiomeGenCraterBase.initDictionary();

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
		MachineDynConfig.initialize();
		TileEntityLaunchPad.registerLaunchables();
		
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
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.no9), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.scrumpy), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.scrumpy), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.no9), 1, 1, 7));

		EntityMappings.writeMappings();
		
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
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_kyiv, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeImpactGeneric(world, position.getX(), position.getY(), position.getZ()).setType((ItemGenericGrenade) ModItems.grenade_kyiv);
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.stick_dynamite_fishing, new BehaviorProjectileDispense() {

			protected IProjectile getProjectileEntity(World world, IPosition position) {
				return new EntityGrenadeImpactGeneric(world, position.getX(), position.getY(), position.getZ()).setType((ItemGenericGrenade) ModItems.stick_dynamite_fishing);
			}
		});
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.powder_fertilizer, new BehaviorDefaultDispenseItem() {

			private boolean dispenseSound = true;
			@Override protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
				
				EnumFacing facing = BlockDispenser.func_149937_b(source.getBlockMetadata());
				World world = source.getWorld();
				int x = source.getXInt() + facing.getFrontOffsetX();
				int y = source.getYInt() + facing.getFrontOffsetY();
				int z = source.getZInt() + facing.getFrontOffsetZ();
				this.dispenseSound = ItemFertilizer.useFertillizer(stack, world, x, y, z);
				return stack;
			}
			@Override protected void playDispenseSound(IBlockSource source) {
				if(this.dispenseSound) {
					source.getWorld().playAuxSFX(1000, source.getXInt(), source.getYInt(), source.getZInt(), 0);
				} else {
					source.getWorld().playAuxSFX(1001, source.getXInt(), source.getYInt(), source.getZInt(), 0);
				}
			}
		});
	}

	@EventHandler
	public static void load(FMLInitializationEvent event) {
		
		RodRecipes.registerInit();

		achSacrifice = new Achievement("achievement.sacrifice", "sacrifice", -3, 1, ModItems.burnt_bark, null).initIndependentStat().setSpecial().registerStat();
		achImpossible = new Achievement("achievement.impossible", "impossible", 18, 10, ModItems.nothing, null).initIndependentStat().setSpecial().registerStat();
		achTOB = new Achievement("achievement.tasteofblood", "tasteofblood", 3, 10, new ItemStack(ModItems.fluid_icon, 1, Fluids.ASCHRAB.getID()), null).initIndependentStat().setSpecial().registerStat();
		achGoFish = new Achievement("achievement.goFish", "goFish", 5, 10, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.GOFISH), null).initIndependentStat().setSpecial().registerStat();
		achFreytag = new Achievement("achievement.freytag", "freytag", 0, -4, ModItems.gun_mp40, null).initIndependentStat().setSpecial().registerStat();
		achPotato = new Achievement("achievement.potato", "potato", -2, -2, ModItems.battery_potatos, null).initIndependentStat().setSpecial().registerStat();
		achC44 = new Achievement("achievement.c44", "c44", 2, -4, ModItems.gun_revolver_pip, null).initIndependentStat().setSpecial().registerStat();
		achC20_5 = new Achievement("achievement.c20_5", "c20_5", 3, 6, ModItems.gun_dampfmaschine, null).initIndependentStat().setSpecial().registerStat();
		achFiend = new Achievement("achievement.fiend", "fiend", -6, 8, ModItems.shimmer_sledge, null).initIndependentStat().setSpecial().registerStat();
		achFiend2 = new Achievement("achievement.fiend2", "fiend2", -4, 9, ModItems.shimmer_axe, null).initIndependentStat().setSpecial().registerStat();
		achStratum = new Achievement("achievement.stratum", "stratum", -4, -2, new ItemStack(ModBlocks.stone_gneiss), null).initIndependentStat().setSpecial().registerStat();
		achOmega12 = new Achievement("achievement.omega12", "omega12", 17, -1, ModItems.particle_digamma, null).initIndependentStat().setSpecial().registerStat();

		achWitchtaunter = new Achievement("achievement.witchtaunter", "witchtaunter", -8, 7, ModItems.ammo_4gauge.stackFromEnum(Ammo4Gauge.VAMPIRE), null).initIndependentStat().setSpecial().registerStat();
		achNo9 = new Achievement("achievement.no9", "no9", -8, 12, ModItems.no9, null).initIndependentStat().registerStat();
		achSlimeball = new Achievement("achievement.slimeball", "slimeball", -10, 6, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.ACID), null).initIndependentStat().registerStat();
		achSulfuric = new Achievement("achievement.sulfuric", "sulfuric", -10, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.BALLS), achSlimeball).initIndependentStat().setSpecial().registerStat();
		achInferno = new Achievement("achievement.inferno", "inferno", -8, 10, ModItems.canister_napalm, null).initIndependentStat().setSpecial().registerStat();
		achRedRoom = new Achievement("achievement.redRoom", "redRoom", -10, 10, ModItems.key_red, null).initIndependentStat().setSpecial().registerStat();
		
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
		
		digammaSee = new Achievement("achievement.digammaSee", "digammaSee", -1, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.DIGAMMASEE), null).initIndependentStat().registerStat();
		digammaFeel = new Achievement("achievement.digammaFeel", "digammaFeel", 1, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.DIGAMMAFEEL), digammaSee).initIndependentStat().registerStat();
		digammaKnow = new Achievement("achievement.digammaKnow", "digammaKnow", 3, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.DIGAMMAKNOW), digammaFeel).initIndependentStat().registerStat().setSpecial();
		digammaKauaiMoho = new Achievement("achievement.digammaKauaiMoho", "digammaKauaiMoho", 5, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.DIGAMMAKAUAIMOHO), digammaKnow).initIndependentStat().registerStat().setSpecial();
		digammaUpOnTop = new Achievement("achievement.digammaUpOnTop", "digammaUpOnTop", 7, 8, DictFrame.fromOne(ModItems.achievement_icon, EnumAchievementType.DIGAMMAUPONTOP), digammaKauaiMoho).initIndependentStat().registerStat().setSpecial();
		
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
		achMeltdown = new Achievement("achievement.meltdown", "meltdown", 15, -7, ModItems.powder_balefire, achFusion).initIndependentStat().setSpecial().registerStat();
		achRedBalloons = new Achievement("achievement.redBalloons", "redBalloons", 11, 0, ModItems.missile_nuclear, achPolymer).initIndependentStat().setSpecial().registerStat();
		achManhattan = new Achievement("achievement.manhattan", "manhattan", 11, -4, new ItemStack(ModBlocks.nuke_boy), achPolymer).initIndependentStat().setSpecial().registerStat();
		
		AchievementPage.registerAchievementPage(new AchievementPage("Nuclear Tech", new Achievement[] {
				achSacrifice,
				achImpossible,
				achTOB,
				achGoFish,
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
				achNo9,
				achInferno,
				achRedRoom,
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

		IMCHandler.registerHandler("blastfurnace", new IMCBlastFurnace());
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
		TileEntityNukeFurnace.registerFuels();
		AssemblerRecipes.loadRecipes();
		MagicRecipes.register();
		SILEXRecipes.register();
		AnvilRecipes.register();
		RefineryRecipes.registerRefinery();
		GasCentrifugeRecipes.register();
		
		CustomMachineConfigJSON.initialize();

		//the good stuff
		SerializableRecipe.registerAllHandlers();
		SerializableRecipe.initialize();

		//has to register after cracking, and therefore after all serializable recipes
		RadiolysisRecipes.registerRadiolysis();
		
		FalloutConfigJSON.initialize();

		TileEntityNukeCustom.registerBombItems();
		ArmorUtil.register();
		HazmatRegistry.registerHazmats();
		FluidContainerRegistry.register();
		BlockToolConversion.registerRecipes();
		AchievementHandler.register();

		proxy.registerMissileItems();
		
		BlockMotherOfAllOres.init();
		
		//expand for the largest entity we have (currently Quackos who is 17.5m in diameter, that's one fat duck)
		World.MAX_ENTITY_RADIUS = Math.max(World.MAX_ENTITY_RADIUS, 8.75);

		MinecraftForge.EVENT_BUS.register(new SchistStratum()); //DecorateBiomeEvent.Pre
		//MinecraftForge.EVENT_BUS.register(new DeepLayer()); //DecorateBiomeEvent.Pre

		new OreCave(ModBlocks.stone_resource, 0).setThreshold(1.5D).setRangeMult(20).setYLevel(30).setMaxRange(20).withFluid(ModBlocks.sulfuric_acid_block);	//sulfur
		new OreCave(ModBlocks.stone_resource, 1).setThreshold(1.75D).setRangeMult(20).setYLevel(25).setMaxRange(20);											//asbestos
		new OreLayer3D(ModBlocks.stone_resource, EnumStoneType.HEMATITE.ordinal()).setScaleH(0.04D).setScaleV(0.25D).setThreshold(230);
		new OreLayer3D(ModBlocks.stone_resource, EnumStoneType.BAUXITE.ordinal()).setScaleH(0.03D).setScaleV(0.15D).setThreshold(300);
		//new BiomeCave().setThreshold(1.5D).setRangeMult(20).setYLevel(40).setMaxRange(20);
		//new OreLayer(Blocks.coal_ore, 0.2F).setThreshold(4).setRangeMult(3).setYLevel(70);
		BedrockOre.init();
		
		Compat.handleRailcraftNonsense();
		SuicideThreadDump.register();
		
		//ExplosionTests.runTest();
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

		ModEventHandlerImpact impactHandler = new ModEventHandlerImpact();
		FMLCommonHandler.instance().bus().register(impactHandler);
		MinecraftForge.EVENT_BUS.register(impactHandler);
		MinecraftForge.TERRAIN_GEN_BUS.register(impactHandler);
		
		PacketDispatcher.registerPackets();

		ChunkRadiationManager radiationSystem = new ChunkRadiationManager();
		MinecraftForge.EVENT_BUS.register(radiationSystem);
		FMLCommonHandler.instance().bus().register(radiationSystem);

		PollutionHandler pollution = new PollutionHandler();
		MinecraftForge.EVENT_BUS.register(pollution);
		FMLCommonHandler.instance().bus().register(pollution);
		
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
		event.registerServerCommand(new CommandReloadRecipes());
		event.registerServerCommand(new CommandDebugChunkLoad());
		event.registerServerCommand(new CommandSatellites());
		event.registerServerCommand(new CommandRadiation());
	}
	
	@EventHandler
	public void serverStart(FMLServerStartedEvent event) {
		
		if(GeneralConfig.enableStatReRegistering) {
			logger.info("Attempting to re-register item stats...");
			StatHelper.resetStatShitFuck(); //shit yourself
			logger.info("Item stats re-registered");
		}
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
		StructureConfig.loadFromConfig(config);

		config.save();
		
		try {
			if(GeneralConfig.enableThermosPreventer && Class.forName("thermos.ThermosClassTransformer") != null) {
				throw new IllegalStateException("The mod tried to start on a Thermos or it's fork server and therefore stopped. To allow the server to start on Thermos, change the appropriate "
						+ "config entry (0.00 in hbm.cfg). This was done because, by default, Thermos "
						+ "uses a so-called \"optimization\" feature that reduces tile ticking a lot, which will inevitably break a lot of machines. Most people aren't even aware "
						+ "of this, and start blaming random mods for all their stuff breaking. In order to adjust or even disable this feature, edit \"tileentities.yml\" in your "
						+ "Thermos install folder. If you believe that crashing the server until a config option is changed is annoying, then I would agree, but it's still preferable "
						+ "over wasting hours trying to fix an issue that is really just an \"intended feature\" added by Thermos itself, and not a bug in the mod. You'll have to "
						+ "change Thermos' config anyway so that extra change in NTM's config can't be that big of a burden.");
			}
		} catch(ClassNotFoundException e) { }
	}
	
	private static HashSet<String> ignoreMappings = new HashSet();
	private static HashMap<String, Item> remapItems = new HashMap();
	

	@EventHandler
	public void handleMissingMappings(FMLMissingMappingsEvent event) {
		
		ignoreMappings.clear();
		remapItems.clear();
		
		/// IGNORE ///
		for(int i = 1; i <= 8; i++) ignoreMappings.add("hbm:item.gasflame" + i);
		ignoreMappings.add("hbm:item.cyclotron_tower");
		ignoreMappings.add("hbm:item.magnet_dee");
		ignoreMappings.add("hbm:item.centrifuge_tower");
		ignoreMappings.add("hbm:item.gun_revolver_nopip_ammo");
		ignoreMappings.add("hbm:item.gun_revolver_pip_ammo");
		ignoreMappings.add("hbm:item.gun_calamity_ammo");
		ignoreMappings.add("hbm:item.gun_lacunae_ammo");
		ignoreMappings.add("hbm:item.gun_rpg_ammo");
		ignoreMappings.add("hbm:item.gun_mp40_ammo");
		ignoreMappings.add("hbm:item.gun_uzi_ammo");
		ignoreMappings.add("hbm:item.gun_uboinik_ammo");
		ignoreMappings.add("hbm:item.gun_lever_action_ammo");
		ignoreMappings.add("hbm:item.gun_bolt_action_ammo");
		ignoreMappings.add("hbm:item.gun_fatman_ammo");
		ignoreMappings.add("hbm:item.gun_mirv_ammo");
		ignoreMappings.add("hbm:item.gun_stinger_ammo");
		ignoreMappings.add("hbm:item.limiter");
		ignoreMappings.add("hbm:item.turret_biometry");
		ignoreMappings.add("hbm:item.thermo_unit_empty");
		ignoreMappings.add("hbm:item.thermo_unit_endo");
		ignoreMappings.add("hbm:item.thermo_unit_exo");
		ignoreMappings.add("hbm:item.gadget_explosive");
		ignoreMappings.add("hbm:item.man_explosive");
		ignoreMappings.add("hbm:item.crystal_energy");
		ignoreMappings.add("hbm:item.pellet_coolant");
		ignoreMappings.add("hbm:item.turret_control");
		ignoreMappings.add("hbm:tile.sellafield_0");
		ignoreMappings.add("hbm:tile.sellafield_1");
		ignoreMappings.add("hbm:tile.sellafield_2");
		ignoreMappings.add("hbm:tile.sellafield_3");
		ignoreMappings.add("hbm:tile.sellafield_4");
		ignoreMappings.add("hbm:tile.sellafield_core");
		ignoreMappings.add("hbm:tile.fusion_core");
		ignoreMappings.add("hbm:tile.machine_telelinker");
		ignoreMappings.add("hbm:item.dynosphere_base");
		ignoreMappings.add("hbm:item.dynosphere_desh");
		ignoreMappings.add("hbm:item.dynosphere_desh_charged");
		ignoreMappings.add("hbm:item.dynosphere_schrabidium");
		ignoreMappings.add("hbm:item.dynosphere_schrabidium_charged");
		ignoreMappings.add("hbm:item.dynosphere_euphemium");
		ignoreMappings.add("hbm:item.dynosphere_euphemium_charged");
		ignoreMappings.add("hbm:item.dynosphere_dineutronium");
		ignoreMappings.add("hbm:item.dynosphere_dineutronium_charged");
		ignoreMappings.add("hbm:item.factory_core_titanium");
		ignoreMappings.add("hbm:item.factory_core_advanced");
		ignoreMappings.add("hbm:tile.factory_titanium_core");
		ignoreMappings.add("hbm:tile.factory_advanced_core");
		ignoreMappings.add("hbm:tile.factory_titanium_conductor");
		ignoreMappings.add("hbm:tile.factory_advanced_conductor");
		ignoreMappings.add("hbm:tile.factory_titanium_furnace");
		ignoreMappings.add("hbm:tile.factory_advanced_furnace");
		ignoreMappings.add("hbm:tile.turret_light");
		ignoreMappings.add("hbm:tile.turret_heavy");
		ignoreMappings.add("hbm:tile.turret_rocket");
		ignoreMappings.add("hbm:tile.turret_flamer");
		ignoreMappings.add("hbm:tile.turret_tau");
		ignoreMappings.add("hbm:tile.turret_cwis");
		ignoreMappings.add("hbm:tile.turret_spitfire");
		ignoreMappings.add("hbm:tile.turret_cheapo");
		ignoreMappings.add("hbm:item.turret_light_ammo");
		ignoreMappings.add("hbm:item.turret_heavy_ammo");
		ignoreMappings.add("hbm:item.turret_rocket_ammo");
		ignoreMappings.add("hbm:item.turret_flamer_ammo");
		ignoreMappings.add("hbm:item.turret_tau_ammo");
		ignoreMappings.add("hbm:item.turret_cwis_ammo");
		ignoreMappings.add("hbm:item.turret_spitfire_ammo");
		ignoreMappings.add("hbm:item.turret_cheapo_ammo");
		ignoreMappings.add("hbm:tile.cel_prime");
		ignoreMappings.add("hbm:tile.cel_prime_terminal");
		ignoreMappings.add("hbm:tile.cel_prime_battery");
		ignoreMappings.add("hbm:tile.cel_prime_port");
		ignoreMappings.add("hbm:tile.cel_prime_tanks");
		ignoreMappings.add("hbm:tile.rf_cable");
		ignoreMappings.add("hbm:tile.test_container");
		ignoreMappings.add("hbm:tile.test_bb_bork");
		ignoreMappings.add("hbm:tile.test_bb_inf");
		ignoreMappings.add("hbm:tile.test_missile");
		ignoreMappings.add("hbm:tile.rotation_tester");
		ignoreMappings.add("hbm:tile.test_ticker");
		ignoreMappings.add("hbm:tile.dummy_block_flare");
		ignoreMappings.add("hbm:tile.dummy_port_flare");
		ignoreMappings.add("hbm:tile.dummy_block_chemplant");
		ignoreMappings.add("hbm:tile.dummy_port_chemplant");
		ignoreMappings.add("hbm:tile.dummy_block_pumpjack");
		ignoreMappings.add("hbm:tile.dummy_port_pumpjack");
		ignoreMappings.add("hbm:tile.dummy_block_radgen");
		ignoreMappings.add("hbm:tile.dummy_port_radgen");
		ignoreMappings.add("hbm:tile.test_conductor");
		ignoreMappings.add("hbm:tile.dummy_block_fluidtank");
		ignoreMappings.add("hbm:tile.dummy_port_fluidtank");
		ignoreMappings.add("hbm:item.telepad");
		ignoreMappings.add("hbm:item.rubber_gloves");
		ignoreMappings.add("hbm:item.pirfenidone");
		ignoreMappings.add("hbm:item.coin_siege");
		ignoreMappings.add("hbm:item.source");
		ignoreMappings.add("hbm:item.gun_brimstone");
		ignoreMappings.add("hbm:item.stamp_schrabidium_flat");
		ignoreMappings.add("hbm:item.stamp_schrabidium_plate");
		ignoreMappings.add("hbm:item.stamp_schrabidium_wire");
		ignoreMappings.add("hbm:item.stamp_schrabidium_circuit");
		ignoreMappings.add("hbm:item.blades_combine_steel");
		ignoreMappings.add("hbm:item.blades_schrabidium");
		ignoreMappings.add("hbm:item.blades_aluminium");
		ignoreMappings.add("hbm:item.blades_gold");
		ignoreMappings.add("hbm:item.blades_iron");
		ignoreMappings.add("hbm:item.cap_aluminium");
		ignoreMappings.add("hbm:tile.dummy_block_refinery");
		ignoreMappings.add("hbm:tile.dummy_port_refinery");
		ignoreMappings.add("hbm:item.gun_revolver_iron");
		ignoreMappings.add("hbm:item.gun_calamity_dual");
		ignoreMappings.add("hbm:item.gun_revolver_lead");
		ignoreMappings.add("hbm:tile.dummy_block_turbofan");
		ignoreMappings.add("hbm:tile.dummy_port_turbofan");
		ignoreMappings.add("hbm:item.canister_smear");
		ignoreMappings.add("hbm:item.canister_canola");
		ignoreMappings.add("hbm:item.canister_oil");
		ignoreMappings.add("hbm:item.canister_fuel");
		ignoreMappings.add("hbm:item.canister_kerosene");
		ignoreMappings.add("hbm:item.canister_reoil");
		ignoreMappings.add("hbm:item.canister_petroil");
		ignoreMappings.add("hbm:item.canister_gasoline");
		ignoreMappings.add("hbm:item.canister_fracksol");
		ignoreMappings.add("hbm:item.canister_NITAN");
		ignoreMappings.add("hbm:item.canister_heavyoil");
		ignoreMappings.add("hbm:item.canister_bitumen");
		ignoreMappings.add("hbm:item.canister_heatingoil");
		ignoreMappings.add("hbm:item.canister_naphtha");
		ignoreMappings.add("hbm:item.canister_lightoil");
		ignoreMappings.add("hbm:item.canister_biofuel");
		ignoreMappings.add("hbm:item.canister_ethanol");
		ignoreMappings.add("hbm:item.gun_revolver_nightmare2_ammo");
		ignoreMappings.add("hbm:item.gun_revolver_iron_ammo");
		ignoreMappings.add("hbm:item.gun_revolver_gold_ammo");
		ignoreMappings.add("hbm:item.gun_revolver_cursed_ammo");
		ignoreMappings.add("hbm:item.gun_revolver_ammo");
		ignoreMappings.add("hbm:item.gun_revolver_nightmare_ammo");
		ignoreMappings.add("hbm:item.gun_mp_ammo");
		ignoreMappings.add("hbm:item.gun_revolver_lead_ammo");
		ignoreMappings.add("hbm:item.gun_revolver_schrabidium_ammo");
		ignoreMappings.add("hbm:item.tank_waste");
		ignoreMappings.add("hbm:item.digamma_see");
		ignoreMappings.add("hbm:item.digamma_feel");
		ignoreMappings.add("hbm:item.digamma_know");
		ignoreMappings.add("hbm:item.digamma_kauai_moho");
		ignoreMappings.add("hbm:item.digamma_up_on_top");
		ignoreMappings.add("hbm:tile.oil_duct_solid");
		ignoreMappings.add("hbm:tile.oil_duct");
		ignoreMappings.add("hbm:tile.gas_duct_solid");
		ignoreMappings.add("hbm:tile.gas_duct");
		ignoreMappings.add("hbm:tile.dummy_block_assembler");
		ignoreMappings.add("hbm:tile.dummy_port_assembler");
		ignoreMappings.add("hbm:item.canned_beef");
		ignoreMappings.add("hbm:item.canned_tuna");
		ignoreMappings.add("hbm:item.canned_mystery");
		ignoreMappings.add("hbm:item.canned_pashtet");
		ignoreMappings.add("hbm:item.canned_cheese");
		ignoreMappings.add("hbm:item.canned_jizz");
		ignoreMappings.add("hbm:item.canned_milk");
		ignoreMappings.add("hbm:item.canned_ass");
		ignoreMappings.add("hbm:item.canned_pizza");
		ignoreMappings.add("hbm:item.canned_tube");
		ignoreMappings.add("hbm:item.canned_tomato");
		ignoreMappings.add("hbm:item.canned_asbestos");
		ignoreMappings.add("hbm:item.canned_bhole");
		ignoreMappings.add("hbm:item.canned_hotdogs");
		ignoreMappings.add("hbm:item.canned_leftovers");
		ignoreMappings.add("hbm:item.canned_yogurt");
		ignoreMappings.add("hbm:item.canned_stew");
		ignoreMappings.add("hbm:item.canned_chinese");
		ignoreMappings.add("hbm:item.canned_oil");
		ignoreMappings.add("hbm:item.canned_fist");
		ignoreMappings.add("hbm:item.canned_spam");
		ignoreMappings.add("hbm:item.canned_fried");
		ignoreMappings.add("hbm:item.canned_napalm");
		ignoreMappings.add("hbm:item.canned_diesel");
		ignoreMappings.add("hbm:item.canned_kerosene");
		ignoreMappings.add("hbm:item.canned_recursion");
		ignoreMappings.add("hbm:item.canned_bark");
		ignoreMappings.add("hbm:item.primer_357");
		ignoreMappings.add("hbm:item.primer_44");
		ignoreMappings.add("hbm:item.primer_9");
		ignoreMappings.add("hbm:item.primer_50");
		ignoreMappings.add("hbm:item.primer_buckshot");
		ignoreMappings.add("hbm:tile.ore_bedrock_coltan");
		ignoreMappings.add("hbm:item.recycled_ground");
		ignoreMappings.add("hbm:item.recycled_rock");
		ignoreMappings.add("hbm:item.recycled_metal");
		ignoreMappings.add("hbm:item.recycled_refined");
		ignoreMappings.add("hbm:item.recycled_organic");
		ignoreMappings.add("hbm:item.recycled_crystal");
		ignoreMappings.add("hbm:item.recycled_explosive");
		ignoreMappings.add("hbm:item.recycled_electronic");
		ignoreMappings.add("hbm:item.recycled_nuclear");
		ignoreMappings.add("hbm:item.recycled_misc");
		ignoreMappings.add("hbm:item.gun_bf_ammo");
		ignoreMappings.add("hbm:tile.brick_dungeon");
		ignoreMappings.add("hbm:tile.brick_dungeon_flat");
		ignoreMappings.add("hbm:tile.brick_dungeon_tile");
		ignoreMappings.add("hbm:tile.brick_dungeon_circle");
		ignoreMappings.add("hbm:tile.bomber");
		ignoreMappings.add("hbm:item.bolt_tungsten");
		ignoreMappings.add("hbm:item.bolt_dura_steel");
		ignoreMappings.add("hbm:tile.rail_large_curve_wide");
		ignoreMappings.add("hbm:tile.nuke_n45");
		ignoreMappings.add("hbm:tile.machine_coal_off");
		ignoreMappings.add("hbm:tile.machine_coal_on");
		ignoreMappings.add("hbm:tile.machine_drill");
		ignoreMappings.add("hbm:tile.drill_pipe");
		ignoreMappings.add("hbm:tile.dummy_block_drill");
		ignoreMappings.add("hbm:tile.dummy_port_drill");
		ignoreMappings.add("hbm:tile.machine_combine_factory");
		ignoreMappings.add("hbm:tile.watz_core");
		ignoreMappings.add("hbm:tile.watz_hatch");
		ignoreMappings.add("hbm:tile.marker_structure");
		ignoreMappings.add("hbm:tile.reactor_element");
		ignoreMappings.add("hbm:tile.reactor_control");
		ignoreMappings.add("hbm:tile.reactor_hatch");
		ignoreMappings.add("hbm:tile.reactor_ejector");
		ignoreMappings.add("hbm:tile.reactor_inserter");
		ignoreMappings.add("hbm:tile.reactor_conductor");
		ignoreMappings.add("hbm:tile.reactor_computer");
		ignoreMappings.add("hbm:tile.ff");
		ignoreMappings.add("hbm:tile.muffler");
		
		/// REMAP ///
		remapItems.put("hbm:item.gadget_explosive8", ModItems.early_explosive_lenses);
		remapItems.put("hbm:item.man_explosive8", ModItems.explosive_lenses);
		remapItems.put("hbm:item.briquette_lignite", ModItems.briquette);
		
		for(MissingMapping mapping : event.get()) {
			
			// ignore all ammo prefixes because those are from the time we threw out all the ammo items
			if(mapping.name.startsWith("hbm:item.ammo_")) {
				mapping.ignore();
				continue;
			}

			if(ignoreMappings.contains(mapping.name)) {
				mapping.ignore();
				continue;
			}
			
			if(mapping.type == GameRegistry.Type.ITEM) {
				
				if(remapItems.get(mapping.name) != null) {
					mapping.remap(remapItems.get(mapping.name));
					continue;
				}
			}
		}
	}
}
