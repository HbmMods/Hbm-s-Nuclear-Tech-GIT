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

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.config.GeneralConfig;
import com.hbm.config.MachineConfig;
import com.hbm.config.MobConfig;
import com.hbm.config.PotionConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.config.ToolConfig;
import com.hbm.config.WeaponConfig;
import com.hbm.config.WorldConfig;
import com.hbm.creativetabs.*;
import com.hbm.entity.effect.*;
import com.hbm.entity.grenade.*;
import com.hbm.entity.item.*;
import com.hbm.entity.logic.*;
import com.hbm.entity.missile.*;
import com.hbm.entity.mob.*;
import com.hbm.entity.mob.botprime.EntityBOTPrimeBody;
import com.hbm.entity.mob.botprime.EntityBOTPrimeHead;
import com.hbm.entity.particle.*;
import com.hbm.entity.projectile.*;
import com.hbm.handler.*;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.*;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmWorld;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.TileEntityProxyEnergy;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.bomb.*;
import com.hbm.tileentity.conductor.*;
import com.hbm.tileentity.deco.*;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge.ReactorFuelType;
import com.hbm.tileentity.turret.*;
import com.hbm.world.generator.CellularDungeonFactory;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = RefStrings.MODID, name = RefStrings.NAME, version = RefStrings.VERSION)
public class MainRegistry {
	@Instance(RefStrings.MODID)
	public static MainRegistry instance;

	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;

	@Metadata
	public static ModMetadata meta;

	public static Logger logger;

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

	// Creative Tabs
	// ingots, nuggets, wires, machine parts
	public static CreativeTabs partsTab = new PartsTab(CreativeTabs.getNextID(), "tabParts");
	// items that belong in machines, fuels, etc
	public static CreativeTabs controlTab = new ControlTab(CreativeTabs.getNextID(), "tabControl");
	// templates, siren tracks
	public static CreativeTabs templateTab = new TemplateTab(CreativeTabs.getNextID(), "tabTemplate");
	// ore and mineral blocks
	public static CreativeTabs blockTab = new BlockTab(CreativeTabs.getNextID(), "tabBlocks");
	// machines, structure parts
	public static CreativeTabs machineTab = new MachineTab(CreativeTabs.getNextID(), "tabMachine");
	// bombs
	public static CreativeTabs nukeTab = new NukeTab(CreativeTabs.getNextID(), "tabNuke");
	// missiles, satellites
	public static CreativeTabs missileTab = new MissileTab(CreativeTabs.getNextID(), "tabMissile");
	// turrets, weapons, ammo
	public static CreativeTabs weaponTab = new WeaponTab(CreativeTabs.getNextID(), "tabWeapon");
	// drinks, kits, tools
	public static CreativeTabs consumableTab = new ConsumableTab(CreativeTabs.getNextID(), "tabConsumable");

	// Achievements
	public static Achievement achSacrifice;
	public static Achievement achImpossible;
	public static Achievement achTOB;
	public static Achievement achFreytag;
	public static Achievement achSelenium;
	public static Achievement achPotato;
	public static Achievement achC44;
	public static Achievement achC20_5;
	public static Achievement achSpace;
	public static Achievement achFOEQ;
	public static Achievement achFiend;
	public static Achievement achFiend2;
	public static Achievement achSoyuz;
	public static Achievement achRadPoison;
	public static Achievement achRadDeath;
	public static Achievement achStratum;
	public static Achievement achMeltdown;
	public static Achievement achOmega12;
	public static Achievement bobMetalworks;
	public static Achievement bobAssembly;
	public static Achievement bobChemistry;
	public static Achievement bobOil;
	public static Achievement bobNuclear;
	public static Achievement bobHidden;
	public static Achievement horizonsStart;
	public static Achievement horizonsEnd;
	public static Achievement horizonsBonus;
	public static Achievement bossCreeper;
	public static Achievement bossMeltdown;
	public static Achievement bossMaskman;
	public static Achievement bossWorm;
	public static Achievement digammaSee;
	public static Achievement digammaFeel;
	public static Achievement digammaKnow;
	public static Achievement digammaKauaiMoho;

	public static int generalOverride = 0;
	public static int polaroidID = 1;

	public static int x;
	public static int y;
	public static int z;
	public static long time;

	Random rand = new Random();

	@EventHandler
	public void PreLoad(FMLPreInitializationEvent PreEvent) {
		if(logger == null)
			logger = PreEvent.getModLog();

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

		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ModItems.armor_polish), 1, 1, 3));
		ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, new WeightedRandomChestContent(new ItemStack(ModItems.bathwater), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.bathwater), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, new WeightedRandomChestContent(new ItemStack(ModItems.serum), 1, 1, 5));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.heart_piece), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.scrumpy), 1, 1, 1));
		ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, new WeightedRandomChestContent(new ItemStack(ModItems.scrumpy), 1, 1, 1));

		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
		GameRegistry.registerTileEntity(TileEntityTestBombAdvanced.class, "tilentity_testbombadvanced");
		GameRegistry.registerTileEntity(TileEntityDiFurnace.class, "tilentity_diFurnace");
		GameRegistry.registerTileEntity(TileEntityTestNuke.class, "tilentity_testnuke");
		GameRegistry.registerTileEntity(TileEntityRotationTester.class, "tilentity_rotationtester");
		GameRegistry.registerTileEntity(TileEntityTestRender.class, "tilentity_testrenderer");
		GameRegistry.registerTileEntity(TileEntityTestContainer.class, "tilentity_testcontainer");
		GameRegistry.registerTileEntity(TileEntityObjTester.class, "tilentity_objtester");
		GameRegistry.registerTileEntity(TileEntityNukeGadget.class, "tilentity_nukegadget");
		GameRegistry.registerTileEntity(TileEntityNukeBoy.class, "tilentity_nukeboy");
		GameRegistry.registerTileEntity(TileEntityMachineCentrifuge.class, "tileentity_centrifuge");
		GameRegistry.registerTileEntity(TileEntityNukeMan.class, "tileentity_nukeman");
		GameRegistry.registerTileEntity(TileEntityMachineUF6Tank.class, "tileentity_uf6_tank");
		GameRegistry.registerTileEntity(TileEntityMachinePuF6Tank.class, "tileentity_puf6_tank");
		GameRegistry.registerTileEntity(TileEntityMachineReactor.class, "tileentity_reactor");
		GameRegistry.registerTileEntity(TileEntityBombMulti.class, "tileentity_bombmulti");
		GameRegistry.registerTileEntity(TileEntityNukeMike.class, "tileentity_nukemike");
		GameRegistry.registerTileEntity(TileEntityNukeTsar.class, "tileentity_nuketsar");
		GameRegistry.registerTileEntity(TileEntityNukeFurnace.class, "tileentity_nukefurnace");
		GameRegistry.registerTileEntity(TileEntityRtgFurnace.class, "tileentity_rtgfurnace");
		GameRegistry.registerTileEntity(TileEntityMachineGenerator.class, "tileentity_generator");
		GameRegistry.registerTileEntity(TileEntityMachineElectricFurnace.class, "tileentity_electric_furnace");
		GameRegistry.registerTileEntity(TileEntityNukeFleija.class, "tileentity_nukefleija");
		GameRegistry.registerTileEntity(TileEntityDecoTapeRecorder.class, "tileentity_taperecorder");
		GameRegistry.registerTileEntity(TileEntityDecoSteelPoles.class, "tileentity_steelpoles");
		GameRegistry.registerTileEntity(TileEntityDecoPoleTop.class, "tileentity_poletop");
		GameRegistry.registerTileEntity(TileEntityDecoPoleSatelliteReceiver.class, "tileentity_satellitereceicer");
		GameRegistry.registerTileEntity(TileEntityMachineDeuterium.class, "tileentity_deuterium");
		GameRegistry.registerTileEntity(TileEntityWireCoated.class, "tileentity_wirecoated");
		GameRegistry.registerTileEntity(TileEntityMachineBattery.class, "tileentity_battery");
		GameRegistry.registerTileEntity(TileEntityMachineCoal.class, "tileentity_coal");
		GameRegistry.registerTileEntity(TileEntityNukePrototype.class, "tileentity_nukeproto");
		GameRegistry.registerTileEntity(TileEntityRedBarrel.class, "tileentity_barrel");
		GameRegistry.registerTileEntity(TileEntityYellowBarrel.class, "tileentity_nukebarrel");
		GameRegistry.registerTileEntity(TileEntityLaunchPad.class, "tileentity_launch1");
		GameRegistry.registerTileEntity(TileEntityDecoBlock.class, "tileentity_deco");
		GameRegistry.registerTileEntity(TileEntityDecoBlockAltW.class, "tileentity_deco_w");
		GameRegistry.registerTileEntity(TileEntityDecoBlockAltG.class, "tileentity_deco_g");
		GameRegistry.registerTileEntity(TileEntityDecoBlockAltF.class, "tileentity_deco_f");
		GameRegistry.registerTileEntity(TileEntityCoreTitanium.class, "tileentity_core_titanium");
		GameRegistry.registerTileEntity(TileEntityCoreAdvanced.class, "tileentity_core_advanced");
		GameRegistry.registerTileEntity(TileEntityFusionMultiblock.class, "tileentity_fusion_multiblock");
		GameRegistry.registerTileEntity(TileEntityCrashedBomb.class, "tileentity_crashed_balefire");
		GameRegistry.registerTileEntity(TileEntityCable.class, "tileentity_cable");
		GameRegistry.registerTileEntity(TileEntityConverterHeRf.class, "tileentity_converter_herf");
		GameRegistry.registerTileEntity(TileEntityConverterRfHe.class, "tileentity_converter_rfhe");
		GameRegistry.registerTileEntity(TileEntityMachineSchrabidiumTransmutator.class, "tileentity_schrabidium_transmutator");
		GameRegistry.registerTileEntity(TileEntityMachineDiesel.class, "tileentity_diesel_generator");
		GameRegistry.registerTileEntity(TileEntityWatzCore.class, "tileentity_watz_multiblock");
		GameRegistry.registerTileEntity(TileEntityMachineShredder.class, "tileentity_machine_shredder");
		GameRegistry.registerTileEntity(TileEntityMachineCMBFactory.class, "tileentity_machine_cmb");
		GameRegistry.registerTileEntity(TileEntityFWatzCore.class, "tileentity_fwatz_multiblock");
		GameRegistry.registerTileEntity(TileEntityMachineTeleporter.class, "tileentity_teleblock");
		GameRegistry.registerTileEntity(TileEntityNukeCustom.class, "tileentity_nuke_custom");
		GameRegistry.registerTileEntity(TileEntityHatch.class, "tileentity_seal_lid");
		GameRegistry.registerTileEntity(TileEntityMachineIGenerator.class, "tileentity_igenerator");
		GameRegistry.registerTileEntity(TileEntityDummy.class, "tileentity_dummy");
		GameRegistry.registerTileEntity(TileEntityMachineCyclotron.class, "tileentity_cyclotron");
		GameRegistry.registerTileEntity(TileEntityMachineOilWell.class, "tileentity_derrick");
		GameRegistry.registerTileEntity(TileEntityMachineGasFlare.class, "tileentity_gasflare");
		GameRegistry.registerTileEntity(TileEntityMachineRefinery.class, "tileentity_refinery");
		GameRegistry.registerTileEntity(TileEntityOilDuct.class, "tileentity_oil_duct");
		GameRegistry.registerTileEntity(TileEntityOilDuctSolid.class, "tileentity_oil_duct_solid");
		GameRegistry.registerTileEntity(TileEntityGasDuct.class, "tileentity_gas_duct");
		GameRegistry.registerTileEntity(TileEntityGasDuctSolid.class, "tileentity_gas_duct_solid");
		GameRegistry.registerTileEntity(TileEntityMachineRTG.class, "tileentity_machine_rtg");
		GameRegistry.registerTileEntity(TileEntityPylonRedWire.class, "tileentity_pylon_redwire");
		GameRegistry.registerTileEntity(TileEntityStructureMarker.class, "tileentity_structure_marker");
		GameRegistry.registerTileEntity(TileEntityMachineMiningDrill.class, "tileentity_mining_drill");
		GameRegistry.registerTileEntity(TileEntityMachineAssembler.class, "tileentity_assembly_machine");
		GameRegistry.registerTileEntity(TileEntityFluidDuct.class, "tileentity_universal_duct");
		GameRegistry.registerTileEntity(TileEntityMachineChemplant.class, "tileentity_chemical_plant");
		GameRegistry.registerTileEntity(TileEntityMachineFluidTank.class, "tileentity_fluid_tank");
		GameRegistry.registerTileEntity(TileEntityTurretHeavy.class, "tileentity_turret_heavy");
		GameRegistry.registerTileEntity(TileEntityTurretRocket.class, "tileentity_turret_rocket");
		GameRegistry.registerTileEntity(TileEntityTurretLight.class, "tileentity_turret_light");
		GameRegistry.registerTileEntity(TileEntityTurretFlamer.class, "tileentity_turret_flamer");
		GameRegistry.registerTileEntity(TileEntityTurretTau.class, "tileentity_turret_tau");
		GameRegistry.registerTileEntity(TileEntityMachinePumpjack.class, "tileentity_machine_pumpjack");
		GameRegistry.registerTileEntity(TileEntityMachineTurbofan.class, "tileentity_machine_turbofan");
		GameRegistry.registerTileEntity(TileEntityCrateIron.class, "tileentity_crate_iron");
		GameRegistry.registerTileEntity(TileEntityCrateSteel.class, "tileentity_crate_steel");
		GameRegistry.registerTileEntity(TileEntityMachinePress.class, "tileentity_press");
		GameRegistry.registerTileEntity(TileEntityAMSBase.class, "tileentity_ams_base");
		GameRegistry.registerTileEntity(TileEntityAMSEmitter.class, "tileentity_ams_emitter");
		GameRegistry.registerTileEntity(TileEntityAMSLimiter.class, "tileentity_ams_limiter");
		GameRegistry.registerTileEntity(TileEntityMachineSiren.class, "tileentity_siren");
		GameRegistry.registerTileEntity(TileEntityMachineSPP.class, "tileentity_spp");
		GameRegistry.registerTileEntity(TileEntityTurretSpitfire.class, "tileentity_turret_spitfire");
		GameRegistry.registerTileEntity(TileEntityMachineRadGen.class, "tileentity_radgen");
		GameRegistry.registerTileEntity(TileEntityMachineTransformer.class, "tileentity_transformer");
		GameRegistry.registerTileEntity(TileEntityTurretCIWS.class, "tileentity_turret_cwis");
		GameRegistry.registerTileEntity(TileEntityMachineRadar.class, "tileentity_radar");
		GameRegistry.registerTileEntity(TileEntityBroadcaster.class, "tileentity_pink_cloud_broadcaster");
		GameRegistry.registerTileEntity(TileEntityTurretCheapo.class, "tileentity_turret_cheapo");
		GameRegistry.registerTileEntity(TileEntityNukeSolinium.class, "tileentity_nuke_solinium");
		GameRegistry.registerTileEntity(TileEntityNukeN2.class, "tileentity_nuke_n2");
		GameRegistry.registerTileEntity(TileEntityCelPrime.class, "tileentity_cel_prime");
		GameRegistry.registerTileEntity(TileEntityCelPrimeTerminal.class, "tileentity_cel_prime_access");
		GameRegistry.registerTileEntity(TileEntityCelPrimeBattery.class, "tileentity_cel_prime_energy");
		GameRegistry.registerTileEntity(TileEntityCelPrimePort.class, "tileentity_cel_prime_connector");
		GameRegistry.registerTileEntity(TileEntityCelPrimeTanks.class, "tileentity_cel_prime_storage");
		GameRegistry.registerTileEntity(TileEntityMachineSeleniumEngine.class, "tileentity_selenium_engine");
		GameRegistry.registerTileEntity(TileEntityMachineSatLinker.class, "tileentity_satlinker");
		GameRegistry.registerTileEntity(TileEntityMachineReactorSmall.class, "tileentity_small_reactor");
		GameRegistry.registerTileEntity(TileEntityVaultDoor.class, "tileentity_vault_door");
		GameRegistry.registerTileEntity(TileEntityRadiobox.class, "tileentity_radio_broadcaster");
		GameRegistry.registerTileEntity(TileEntityRadioRec.class, "tileentity_radio_receiver");
		GameRegistry.registerTileEntity(TileEntityVent.class, "tileentity_vent");
		GameRegistry.registerTileEntity(TileEntityLandmine.class, "tileentity_landmine");
		GameRegistry.registerTileEntity(TileEntityBomber.class, "tileentity_bomber");
		GameRegistry.registerTileEntity(TileEntityMachineTeleLinker.class, "tileentity_telemetry_linker");
		GameRegistry.registerTileEntity(TileEntityMachineKeyForge.class, "tileentity_key_forge");
		GameRegistry.registerTileEntity(TileEntitySellafield.class, "tileentity_sellafield_core");
		GameRegistry.registerTileEntity(TileEntityNukeN45.class, "tileentity_n45");
		GameRegistry.registerTileEntity(TileEntityBlastDoor.class, "tileentity_blast_door");
		GameRegistry.registerTileEntity(TileEntitySafe.class, "tileentity_safe");
		GameRegistry.registerTileEntity(TileEntityMachineGasCent.class, "tileentity_gas_centrifuge");
		GameRegistry.registerTileEntity(TileEntityMachineBoiler.class, "tileentity_boiler");
		GameRegistry.registerTileEntity(TileEntityMachineBoilerElectric.class, "tileentity_electric_boiler");
		GameRegistry.registerTileEntity(TileEntityMachineTurbine.class, "tileentity_turbine");
		GameRegistry.registerTileEntity(TileEntityGeiger.class, "tileentity_geiger");
		GameRegistry.registerTileEntity(TileEntityFF.class, "tileentity_forcefield");
		GameRegistry.registerTileEntity(TileEntityForceField.class, "tileentity_machine_field");
		GameRegistry.registerTileEntity(TileEntityMachineShredderLarge.class, "tileentity_machine_big_shredder");
		GameRegistry.registerTileEntity(TileEntityRFDuct.class, "tileentity_hbm_rfduct");
		GameRegistry.registerTileEntity(TileEntityReactorControl.class, "tileentity_reactor_remote_control");
		GameRegistry.registerTileEntity(TileEntityMachineReactorLarge.class, "tileentity_large_reactor");
		GameRegistry.registerTileEntity(TileEntityWasteDrum.class, "tileentity_waste_drum");
		GameRegistry.registerTileEntity(TileEntityDecon.class, "tileentity_decon");
		GameRegistry.registerTileEntity(TileEntityMachineSatDock.class, "tileentity_miner_dock");
		GameRegistry.registerTileEntity(TileEntityMachineEPress.class, "tileentity_electric_press");
		GameRegistry.registerTileEntity(TileEntityCoreEmitter.class, "tileentity_v0_emitter");
		GameRegistry.registerTileEntity(TileEntityCoreReceiver.class, "tileentity_v0_receiver");
		GameRegistry.registerTileEntity(TileEntityCoreInjector.class, "tileentity_v0_injector");
		GameRegistry.registerTileEntity(TileEntityCoreStabilizer.class, "tileentity_v0_stabilizer");
		GameRegistry.registerTileEntity(TileEntityCore.class, "tileentity_v0");
		GameRegistry.registerTileEntity(TileEntityMachineArcFurnace.class, "tileentity_arc_furnace");
		GameRegistry.registerTileEntity(TileEntityMachineAmgen.class, "tileentity_amgen");
		GameRegistry.registerTileEntity(TileEntityGeysir.class, "tileentity_geysir");
		GameRegistry.registerTileEntity(TileEntityMachineMissileAssembly.class, "tileentity_missile_assembly");
		GameRegistry.registerTileEntity(TileEntityLaunchTable.class, "tileentity_large_launch_table");
		GameRegistry.registerTileEntity(TileEntityCompactLauncher.class, "tileentity_small_launcher");
		GameRegistry.registerTileEntity(TileEntityMultiblock.class, "tileentity_multi_core");
		GameRegistry.registerTileEntity(TileEntityChlorineSeal.class, "tileentity_chlorine_seal");
		GameRegistry.registerTileEntity(TileEntityCableSwitch.class, "tileentity_he_switch");
		GameRegistry.registerTileEntity(TileEntitySoyuzLauncher.class, "tileentity_soyuz_launcher");
		GameRegistry.registerTileEntity(TileEntityTesla.class, "tileentity_tesla_coil");
		GameRegistry.registerTileEntity(TileEntityBarrel.class, "tileentity_fluid_barrel");
		GameRegistry.registerTileEntity(TileEntityCyberCrab.class, "tileentity_crabs");
		GameRegistry.registerTileEntity(TileEntitySoyuzCapsule.class, "tileentity_soyuz_capsule");
		GameRegistry.registerTileEntity(TileEntityMachineCrystallizer.class, "tileentity_acidomatic");
		GameRegistry.registerTileEntity(TileEntitySoyuzStruct.class, "tileentity_soyuz_struct");
		GameRegistry.registerTileEntity(TileEntityITERStruct.class, "tileentity_iter_struct");
		GameRegistry.registerTileEntity(TileEntityMachineMiningLaser.class, "tileentity_mining_laser");
		GameRegistry.registerTileEntity(TileEntityProxyInventory.class, "tileentity_proxy_inventory");
		GameRegistry.registerTileEntity(TileEntityProxyEnergy.class, "tileentity_proxy_power");
		GameRegistry.registerTileEntity(TileEntityNukeBalefire.class, "tileentity_nuke_fstbmb");
		GameRegistry.registerTileEntity(TileEntityProxyCombo.class, "tileentity_proxy_combo");
		GameRegistry.registerTileEntity(TileEntityMicrowave.class, "tileentity_microwave");
		GameRegistry.registerTileEntity(TileEntityMachineMiniRTG.class, "tileentity_mini_rtg");
		GameRegistry.registerTileEntity(TileEntityITER.class, "tileentity_iter");
		GameRegistry.registerTileEntity(TileEntityMachinePlasmaHeater.class, "tileentity_plasma_heater");
		GameRegistry.registerTileEntity(TileEntityMachineFENSU.class, "tileentity_fensu");
		GameRegistry.registerTileEntity(TileEntityTrappedBrick.class, "tileentity_trapped_brick");
		GameRegistry.registerTileEntity(TileEntityPlasmaStruct.class, "tileentity_plasma_struct");
		GameRegistry.registerTileEntity(TileEntityMachineLargeTurbine.class, "tileentity_industrial_turbine");
		GameRegistry.registerTileEntity(TileEntityHadronDiode.class, "tileentity_hadron_diode");
		GameRegistry.registerTileEntity(TileEntityHadronPower.class, "tileentity_hadron_power");
		GameRegistry.registerTileEntity(TileEntityHadron.class, "tileentity_hadron");
		GameRegistry.registerTileEntity(TileEntitySolarBoiler.class, "tileentity_solarboiler");
		GameRegistry.registerTileEntity(TileEntitySolarMirror.class, "tileentity_solarmirror");
		GameRegistry.registerTileEntity(TileEntityMachineDetector.class, "tileentity_he_detector");
		GameRegistry.registerTileEntity(TileEntityFireworks.class, "tileentity_firework_box");
		GameRegistry.registerTileEntity(TileEntityCrateTungsten.class, "tileentity_crate_hot");
		GameRegistry.registerTileEntity(TileEntityTurretChekhov.class, "tileentity_turret_chekhov");
		GameRegistry.registerTileEntity(TileEntityTurretJeremy.class, "tileentity_turret_jeremy");
		GameRegistry.registerTileEntity(TileEntityTurretTauon.class, "tileentity_turret_tauon");
		GameRegistry.registerTileEntity(TileEntityTurretFriendly.class, "tileentity_turret_friendly");
		GameRegistry.registerTileEntity(TileEntityTurretRichard.class, "tileentity_turret_richard");
		GameRegistry.registerTileEntity(TileEntitySILEX.class, "tileentity_silex");
		GameRegistry.registerTileEntity(TileEntityFEL.class, "tileentity_fel");

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
		EntityRegistry.registerModEntity(EntityGasFlameFX.class, "entity_gasflame_fx", 75, this, 1000, 1, true);
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
		EntityRegistry.registerModEntity(EntityGrenadeIFHE.class, "entity_grenade_ironshod", 118, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFBouncy.class, "entity_grenade_ironshod", 119, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFSticky.class, "entity_grenade_ironshod", 120, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFImpact.class, "entity_grenade_ironshod", 121, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFIncendiary.class, "entity_grenade_ironshod", 122, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFToxic.class, "entity_grenade_ironshod", 123, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFConcussion.class, "entity_grenade_ironshod", 124, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFBrimstone.class, "entity_grenade_ironshod", 125, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFMystery.class, "entity_grenade_ironshod", 126, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFSpark.class, "entity_grenade_ironshod", 127, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFHopwire.class, "entity_grenade_ironshod", 128, this, 250, 1, true);
		EntityRegistry.registerModEntity(EntityGrenadeIFNull.class, "entity_grenade_ironshod", 129, this, 250, 1, true);
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

		//EntityRegistry.registerGlobalEntityID(EntityBOTPrimeHead.class, "entity_balls_o_tron", EntityRegistry.findGlobalUniqueEntityId(), 0xAAAAAA, 0xAAAAAA);
		EntityRegistry.registerModEntity(EntityBOTPrimeHead.class, "entity_balls_o_tron", 150, this, 1000, 1, true);
		EntityRegistry.registerModEntity(EntityBOTPrimeBody.class, "entity_balls_o_tron_seg", 151, this, 1000, 1, true);

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
	}

	@EventHandler
	public static void load(FMLInitializationEvent event) {

		achSacrifice = new Achievement("achievement.sacrifice", "sacrifice", 0, -2, ModItems.burnt_bark, null).initIndependentStat().setSpecial().registerStat();
		achImpossible = new Achievement("achievement.impossible", "impossible", 2, -2, ModItems.nothing, null).initIndependentStat().setSpecial().registerStat();
		achTOB = new Achievement("achievement.tasteofblood", "tasteofblood", 0, 0, new ItemStack(ModItems.fluid_icon, 1, FluidType.ASCHRAB.getID()), null).initIndependentStat().setSpecial().registerStat();
		achFreytag = new Achievement("achievement.freytag", "freytag", 0, -4, ModItems.gun_mp40, null).initIndependentStat().setSpecial().registerStat();
		achSelenium = new Achievement("achievement.selenium", "selenium", -2, -4, ModItems.ingot_starmetal, null).initIndependentStat().setSpecial().registerStat();
		achPotato = new Achievement("achievement.potato", "potato", -2, -2, ModItems.battery_potatos, null).initIndependentStat().setSpecial().registerStat();
		achC44 = new Achievement("achievement.c44", "c44", 2, -4, ModItems.gun_revolver_pip, null).initIndependentStat().setSpecial().registerStat();
		achC20_5 = new Achievement("achievement.c20_5", "c20_5", 4, -4, ModItems.gun_dampfmaschine, null).initIndependentStat().setSpecial().registerStat();
		achSpace = new Achievement("achievement.space", "space", 4, -2, ModItems.missile_carrier, null).initIndependentStat().setSpecial().registerStat();
		achFOEQ = new Achievement("achievement.FOEQ", "FOEQ", 4, 0, ModItems.sat_foeq, null).initIndependentStat().setSpecial().registerStat();
		achFiend = new Achievement("achievement.fiend", "fiend", 6, -2, ModItems.shimmer_sledge, null).initIndependentStat().setSpecial().registerStat();
		achFiend2 = new Achievement("achievement.fiend2", "fiend2", 6, 0, ModItems.shimmer_axe, null).initIndependentStat().setSpecial().registerStat();
		achSoyuz = new Achievement("achievement.soyuz", "soyuz", -2, 0, Items.baked_potato, null).initIndependentStat().setSpecial().registerStat();
		achStratum = new Achievement("achievement.stratum", "stratum", -4, -2, new ItemStack(ModBlocks.stone_gneiss), null).initIndependentStat().setSpecial().registerStat();
		achMeltdown = new Achievement("achievement.meltdown", "meltdown", -4, 0, new ItemStack(ModBlocks.iter), null).initIndependentStat().setSpecial().registerStat();
		achOmega12 = new Achievement("achievement.omega12", "omega12", -4, 2, ModItems.particle_digamma, null).initIndependentStat().setSpecial().registerStat();

		bobMetalworks = new Achievement("achievement.metalworks", "metalworks", -2, 2, ModItems.bob_metalworks, null).initIndependentStat().registerStat();
		bobAssembly = new Achievement("achievement.assembly", "assembly", 0, 2, ModItems.bob_assembly, bobMetalworks).initIndependentStat().registerStat();
		bobChemistry = new Achievement("achievement.chemistry", "chemistry", 2, 2, ModItems.bob_chemistry, bobAssembly).initIndependentStat().registerStat();
		bobOil = new Achievement("achievement.oil", "oil", 4, 2, ModItems.bob_oil, bobChemistry).initIndependentStat().registerStat();
		bobNuclear = new Achievement("achievement.nuclear", "nuclear", 6, 2, ModItems.bob_nuclear, bobOil).initIndependentStat().registerStat();
		bobHidden = new Achievement("achievement.hidden", "hidden", 8, 2, ModItems.gun_dampfmaschine, bobNuclear).initIndependentStat().registerStat();

		horizonsStart = new Achievement("achievement.horizonsStart", "horizonsStart", -2, 4, ModItems.sat_gerald, null).initIndependentStat().registerStat();
		horizonsEnd = new Achievement("achievement.horizonsEnd", "horizonsEnd", 0, 4, ModItems.sat_gerald, horizonsStart).initIndependentStat().registerStat();
		horizonsBonus = new Achievement("achievement.horizonsBonus", "horizonsBonus", 2, 4, ModItems.sat_gerald, horizonsEnd).initIndependentStat().registerStat().setSpecial();

		bossCreeper = new Achievement("achievement.bossCreeper", "bossCreeper", 8, 0, ModItems.coin_creeper, null).initIndependentStat().registerStat();
		bossMeltdown = new Achievement("achievement.bossMeltdown", "bossMeltdown", 9, -1, ModItems.coin_radiation, bossCreeper).initIndependentStat().registerStat();
		bossMaskman = new Achievement("achievement.bossMaskman", "bossMaskman", 9, 1, ModItems.coin_maskman, bossCreeper).initIndependentStat().registerStat();
		bossWorm = new Achievement("achievement.bossWorm", "bossWorm", 11, 1, ModItems.coin_worm, bossMaskman).initIndependentStat().registerStat().setSpecial();

		achRadPoison = new Achievement("achievement.radPoison", "radPoison", -2, 6, ModItems.geiger_counter, null).initIndependentStat().registerStat();
		achRadDeath = new Achievement("achievement.radDeath", "radDeath", 0, 6, Items.skull, achRadPoison).initIndependentStat().registerStat().setSpecial();

		digammaSee = new Achievement("achievement.digammaSee", "digammaSee", -2, 8, ModItems.digamma_see, null).initIndependentStat().registerStat();
		digammaFeel = new Achievement("achievement.digammaFeel", "digammaFeel", 0, 8, ModItems.digamma_feel, digammaSee).initIndependentStat().registerStat();
		digammaKnow = new Achievement("achievement.digammaKnow", "digammaKnow", 2, 8, ModItems.digamma_know, digammaFeel).initIndependentStat().registerStat().setSpecial();
		digammaKauaiMoho = new Achievement("achievement.digammaKauaiMoho", "digammaKauaiMoho", 4, 8, ModItems.digamma_kauai_moho, digammaKnow).initIndependentStat().registerStat().setSpecial();

		AchievementPage.registerAchievementPage(new AchievementPage("Nuclear Tech", new Achievement[] {
				achSacrifice,
				achImpossible,
				achTOB,
				achFreytag,
				achSelenium,
				achPotato,
				achC44,
				achC20_5,
				achSpace,
				achFOEQ,
				achFiend,
				achFiend2,
				achSoyuz,
				achStratum,
				achMeltdown,
				achOmega12,
				bobMetalworks,
				bobAssembly,
				bobChemistry,
				bobOil,
				bobNuclear,
				bobHidden,
				horizonsStart,
				horizonsEnd,
				horizonsBonus,
				achRadPoison,
				achRadDeath,
				bossCreeper,
				bossMeltdown,
				bossMaskman,
				bossWorm,
				digammaSee,
				digammaFeel,
				digammaKnow,
				digammaKauaiMoho
		}));

		// MUST be initialized AFTER achievements!!
		BobmazonOfferFactory.init();
		OreDictManager.registerOres();
	}

	@EventHandler
	public static void PostLoad(FMLPostInitializationEvent PostEvent) {
		ShredderRecipes.registerShredder();
		ShredderRecipes.registerOverrides();
		CrystallizerRecipes.register();
		CentrifugeRecipes.register();
		BreederRecipes.registerFuels();
		BreederRecipes.registerRecipes();
		AssemblerRecipes.loadRecipes();
		CyclotronRecipes.register();
		HadronRecipes.register();
		MagicRecipes.register();

		TileEntityNukeCustom.registerBombItems();

		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(Items.water_bucket), new ItemStack(Items.bucket), FluidType.WATER, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(Items.lava_bucket), new ItemStack(Items.bucket), FluidType.LAVA, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.bucket_mud), new ItemStack(Items.bucket), FluidType.WATZ, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.bucket_schrabidic_acid), new ItemStack(Items.bucket), FluidType.SCHRABIDIC, 1000));

		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_water), new ItemStack(ModItems.rod_empty), FluidType.WATER, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_dual_water), new ItemStack(ModItems.rod_dual_empty), FluidType.WATER, 2000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_quad_water), new ItemStack(ModItems.rod_quad_empty), FluidType.WATER, 4000));

		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_coolant), new ItemStack(ModItems.rod_empty), FluidType.COOLANT, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_dual_coolant), new ItemStack(ModItems.rod_dual_empty), FluidType.COOLANT, 2000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_quad_coolant), new ItemStack(ModItems.rod_quad_empty), FluidType.COOLANT, 4000));

		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_oil), new ItemStack(ModItems.canister_empty), FluidType.OIL, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_smear), new ItemStack(ModItems.canister_empty), FluidType.SMEAR, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_heavyoil), new ItemStack(ModItems.canister_empty), FluidType.HEAVYOIL, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_bitumen), new ItemStack(ModItems.canister_empty), FluidType.BITUMEN, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_heatingoil), new ItemStack(ModItems.canister_empty), FluidType.HEATINGOIL, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_reoil), new ItemStack(ModItems.canister_empty), FluidType.RECLAIMED, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_petroil), new ItemStack(ModItems.canister_empty), FluidType.PETROIL, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_canola), new ItemStack(ModItems.canister_empty), FluidType.LUBRICANT, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_naphtha), new ItemStack(ModItems.canister_empty), FluidType.NAPHTHA, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_fuel), new ItemStack(ModItems.canister_empty), FluidType.DIESEL, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_lightoil), new ItemStack(ModItems.canister_empty), FluidType.LIGHTOIL, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_kerosene), new ItemStack(ModItems.canister_empty), FluidType.KEROSENE, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_biofuel), new ItemStack(ModItems.canister_empty), FluidType.BIOFUEL, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_NITAN), new ItemStack(ModItems.canister_empty), FluidType.NITAN, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.gas_full), new ItemStack(ModItems.gas_empty), FluidType.GAS, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.gas_petroleum), new ItemStack(ModItems.gas_empty), FluidType.PETROLEUM, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.gas_biogas), new ItemStack(ModItems.gas_empty), FluidType.BIOGAS, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModBlocks.red_barrel), new ItemStack(ModItems.tank_steel), FluidType.DIESEL, 10000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModBlocks.pink_barrel), new ItemStack(ModItems.tank_steel), FluidType.KEROSENE, 10000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModBlocks.lox_barrel), new ItemStack(ModItems.tank_steel), FluidType.OXYGEN, 10000));
		
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModBlocks.ore_gneiss_gas), new ItemStack(ModBlocks.stone_gneiss), FluidType.PETROLEUM, 250));

		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_deuterium), new ItemStack(ModItems.cell_empty), FluidType.DEUTERIUM, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_tritium), new ItemStack(ModItems.cell_empty), FluidType.TRITIUM, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_tritium), new ItemStack(ModItems.rod_empty), FluidType.TRITIUM, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_dual_tritium), new ItemStack(ModItems.rod_dual_empty), FluidType.TRITIUM, 2000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_quad_tritium), new ItemStack(ModItems.rod_quad_empty), FluidType.TRITIUM, 4000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_uf6), new ItemStack(ModItems.cell_empty), FluidType.UF6, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_puf6), new ItemStack(ModItems.cell_empty), FluidType.PUF6, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_antimatter), new ItemStack(ModItems.cell_empty), FluidType.AMAT, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_anti_schrabidium), new ItemStack(ModItems.cell_empty), FluidType.ASCHRAB, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_sas3), new ItemStack(ModItems.cell_empty), FluidType.SAS3, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.bottle_mercury), new ItemStack(Items.glass_bottle), FluidType.MERCURY, 1000));

		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 1), new ItemStack(ModItems.tank_waste, 1, 0), FluidType.WATZ, 8000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 2), new ItemStack(ModItems.tank_waste, 1, 1), FluidType.WATZ, 8000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 3), new ItemStack(ModItems.tank_waste, 1, 2), FluidType.WATZ, 8000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 4), new ItemStack(ModItems.tank_waste, 1, 3), FluidType.WATZ, 8000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 5), new ItemStack(ModItems.tank_waste, 1, 4), FluidType.WATZ, 8000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 6), new ItemStack(ModItems.tank_waste, 1, 5), FluidType.WATZ, 8000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 7), new ItemStack(ModItems.tank_waste, 1, 6), FluidType.WATZ, 8000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 8), new ItemStack(ModItems.tank_waste, 1, 7), FluidType.WATZ, 8000));

		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.particle_hydrogen), new ItemStack(ModItems.particle_empty), FluidType.HYDROGEN, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.particle_amat), new ItemStack(ModItems.particle_empty), FluidType.AMAT, 1000));
		FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.particle_aschrab), new ItemStack(ModItems.particle_empty), FluidType.ASCHRAB, 1000));

		for(int i = 1; i < FluidType.values().length; i++) {
			FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.fluid_tank_full, 1, i), new ItemStack(ModItems.fluid_tank_empty), FluidType.getEnum(i), 1000));
			FluidContainerRegistry.registerContainer(new FluidContainer(new ItemStack(ModItems.fluid_barrel_full, 1, i), new ItemStack(ModItems.fluid_barrel_empty), FluidType.getEnum(i), 16000));
		}

		HazmatRegistry.registerHazmat(ModItems.hazmat_helmet, 0.2F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_plate, 0.4F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_legs, 0.3F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_boots, 0.1F);

		HazmatRegistry.registerHazmat(ModItems.hazmat_helmet_red, 0.3F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_plate_red, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_legs_red, 0.45F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_boots_red, 0.15F);

		HazmatRegistry.registerHazmat(ModItems.hazmat_helmet_grey, 0.4F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_plate_grey, 0.8F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_legs_grey, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_boots_grey, 0.2F);

		HazmatRegistry.registerHazmat(ModItems.t45_helmet, 0.4F);
		HazmatRegistry.registerHazmat(ModItems.t45_plate, 0.8F);
		HazmatRegistry.registerHazmat(ModItems.t45_legs, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.t45_boots, 0.2F);

		HazmatRegistry.registerHazmat(ModItems.ajr_helmet, 0.4F);
		HazmatRegistry.registerHazmat(ModItems.ajr_plate, 0.8F);
		HazmatRegistry.registerHazmat(ModItems.ajr_legs, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.ajr_boots, 0.2F);
		HazmatRegistry.registerHazmat(ModItems.ajro_helmet, 0.4F);
		HazmatRegistry.registerHazmat(ModItems.ajro_plate, 0.8F);
		HazmatRegistry.registerHazmat(ModItems.ajro_legs, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.ajro_boots, 0.2F);

		HazmatRegistry.registerHazmat(ModItems.bj_helmet, 0.4F);
		HazmatRegistry.registerHazmat(ModItems.bj_plate, 0.8F);
		HazmatRegistry.registerHazmat(ModItems.bj_plate_jetpack, 0.8F);
		HazmatRegistry.registerHazmat(ModItems.bj_legs, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.bj_boots, 0.2F);

		HazmatRegistry.registerHazmat(ModItems.hev_helmet, 0.5F);
		HazmatRegistry.registerHazmat(ModItems.hev_plate, 1.0F);
		HazmatRegistry.registerHazmat(ModItems.hev_legs, 0.7F);
		HazmatRegistry.registerHazmat(ModItems.hev_boots, 0.3F);

		HazmatRegistry.registerHazmat(ModItems.fau_helmet, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.fau_plate, 1.2F);
		HazmatRegistry.registerHazmat(ModItems.fau_legs, 0.9F);
		HazmatRegistry.registerHazmat(ModItems.fau_boots, 0.3F);

		HazmatRegistry.registerHazmat(ModItems.paa_plate, 0.8F);
		HazmatRegistry.registerHazmat(ModItems.paa_legs, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.paa_boots, 0.2F);

		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_helmet, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_plate, 1.2F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_legs, 0.9F);
		HazmatRegistry.registerHazmat(ModItems.hazmat_paa_boots, 0.3F);

		HazmatRegistry.registerHazmat(ModItems.security_helmet, 0.2F);
		HazmatRegistry.registerHazmat(ModItems.security_plate, 0.4F);
		HazmatRegistry.registerHazmat(ModItems.security_legs, 0.3F);
		HazmatRegistry.registerHazmat(ModItems.security_boots, 0.1F);

		HazmatRegistry.registerHazmat(ModItems.starmetal_helmet, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.starmetal_plate, 1.2F);
		HazmatRegistry.registerHazmat(ModItems.starmetal_legs, 0.9F);
		HazmatRegistry.registerHazmat(ModItems.starmetal_boots, 0.3F);

		HazmatRegistry.registerHazmat(ModItems.jackt, 0.3F);
		HazmatRegistry.registerHazmat(ModItems.jackt2, 0.3F);

		HazmatRegistry.registerHazmat(ModItems.gas_mask, 0.15F);
		HazmatRegistry.registerHazmat(ModItems.gas_mask_m65, 0.175F);

		HazmatRegistry.registerHazmat(ModItems.steel_helmet, 0.04F);
		HazmatRegistry.registerHazmat(ModItems.steel_plate, 0.08F);
		HazmatRegistry.registerHazmat(ModItems.steel_legs, 0.06F);
		HazmatRegistry.registerHazmat(ModItems.steel_boots, 0.02F);

		HazmatRegistry.registerHazmat(ModItems.titanium_helmet, 0.06F);
		HazmatRegistry.registerHazmat(ModItems.titanium_plate, 0.12F);
		HazmatRegistry.registerHazmat(ModItems.titanium_legs, 0.1F);
		HazmatRegistry.registerHazmat(ModItems.titanium_boots, 0.03F);

		HazmatRegistry.registerHazmat(ModItems.cobalt_helmet, 0.1F);
		HazmatRegistry.registerHazmat(ModItems.cobalt_plate, 0.2F);
		HazmatRegistry.registerHazmat(ModItems.cobalt_legs, 0.15F);
		HazmatRegistry.registerHazmat(ModItems.cobalt_boots, 0.05F);

		HazmatRegistry.registerHazmat(Items.iron_helmet, 0.04F);
		HazmatRegistry.registerHazmat(Items.iron_chestplate, 0.08F);
		HazmatRegistry.registerHazmat(Items.iron_leggings, 0.06F);
		HazmatRegistry.registerHazmat(Items.iron_boots, 0.02F);

		HazmatRegistry.registerHazmat(Items.golden_helmet, 0.04F);
		HazmatRegistry.registerHazmat(Items.golden_chestplate, 0.08F);
		HazmatRegistry.registerHazmat(Items.golden_leggings, 0.06F);
		HazmatRegistry.registerHazmat(Items.golden_boots, 0.02F);

		HazmatRegistry.registerHazmat(Items.diamond_helmet, 0.05F);
		HazmatRegistry.registerHazmat(Items.diamond_chestplate, 0.09F);
		HazmatRegistry.registerHazmat(Items.diamond_leggings, 0.07F);
		HazmatRegistry.registerHazmat(Items.diamond_boots, 0.03F);

		HazmatRegistry.registerHazmat(ModItems.alloy_helmet, 0.08F);
		HazmatRegistry.registerHazmat(ModItems.alloy_plate, 0.16F);
		HazmatRegistry.registerHazmat(ModItems.alloy_legs, 0.12F);
		HazmatRegistry.registerHazmat(ModItems.alloy_boots, 0.04F);

		HazmatRegistry.registerHazmat(ModItems.cmb_helmet, 0.5F);
		HazmatRegistry.registerHazmat(ModItems.cmb_plate, 1.1F);
		HazmatRegistry.registerHazmat(ModItems.cmb_legs, 0.8F);
		HazmatRegistry.registerHazmat(ModItems.cmb_boots, 0.2F);

		HazmatRegistry.registerHazmat(ModItems.schrabidium_helmet, 0.6F);
		HazmatRegistry.registerHazmat(ModItems.schrabidium_plate, 1.2F);
		HazmatRegistry.registerHazmat(ModItems.schrabidium_legs, 0.9F);
		HazmatRegistry.registerHazmat(ModItems.schrabidium_boots, 0.3F);

		HazmatRegistry.registerHazmat(ModItems.euphemium_helmet, 6F);
		HazmatRegistry.registerHazmat(ModItems.euphemium_plate, 12F);
		HazmatRegistry.registerHazmat(ModItems.euphemium_legs, 9F);
		HazmatRegistry.registerHazmat(ModItems.euphemium_boots, 3F);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.URANIUM, ModItems.nugget_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.URANIUM, ModItems.ingot_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.URANIUM, ModItems.rod_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.URANIUM, ModItems.rod_dual_uranium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.URANIUM, ModItems.rod_quad_uranium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.URANIUM, ModItems.rod_empty, ModItems.rod_uranium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.URANIUM, ModItems.rod_dual_empty, ModItems.rod_dual_uranium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.URANIUM, ModItems.rod_quad_empty, ModItems.rod_quad_uranium_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.PLUTONIUM, ModItems.nugget_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.PLUTONIUM, ModItems.ingot_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.PLUTONIUM, ModItems.rod_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.PLUTONIUM, ModItems.rod_dual_plutonium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.PLUTONIUM, ModItems.rod_quad_plutonium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.PLUTONIUM, ModItems.rod_empty, ModItems.rod_plutonium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.PLUTONIUM, ModItems.rod_dual_empty, ModItems.rod_dual_plutonium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.PLUTONIUM, ModItems.rod_quad_empty, ModItems.rod_quad_plutonium_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.MOX, ModItems.nugget_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.MOX, ModItems.ingot_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.MOX, ModItems.rod_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.MOX, ModItems.rod_dual_mox_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.MOX, ModItems.rod_quad_mox_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.MOX, ModItems.rod_empty, ModItems.rod_mox_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.MOX, ModItems.rod_dual_empty, ModItems.rod_dual_mox_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.MOX, ModItems.rod_quad_empty, ModItems.rod_quad_mox_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(10, ReactorFuelType.SCHRABIDIUM, ModItems.nugget_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(90, ReactorFuelType.SCHRABIDIUM, ModItems.ingot_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(60, ReactorFuelType.SCHRABIDIUM, ModItems.rod_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(120, ReactorFuelType.SCHRABIDIUM, ModItems.rod_dual_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(240, ReactorFuelType.SCHRABIDIUM, ModItems.rod_quad_schrabidium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(60, ReactorFuelType.SCHRABIDIUM, ModItems.rod_empty, ModItems.rod_schrabidium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(120, ReactorFuelType.SCHRABIDIUM, ModItems.rod_dual_empty, ModItems.rod_dual_schrabidium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(240, ReactorFuelType.SCHRABIDIUM, ModItems.rod_quad_empty, ModItems.rod_quad_schrabidium_fuel_depleted);

		TileEntityMachineReactorLarge.registerFuelEntry(1, ReactorFuelType.THORIUM, ModItems.nugget_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(9, ReactorFuelType.THORIUM, ModItems.ingot_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(6, ReactorFuelType.THORIUM, ModItems.rod_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(12, ReactorFuelType.THORIUM, ModItems.rod_dual_thorium_fuel);
		TileEntityMachineReactorLarge.registerFuelEntry(24, ReactorFuelType.THORIUM, ModItems.rod_quad_thorium_fuel);
		TileEntityMachineReactorLarge.registerWasteEntry(6, ReactorFuelType.THORIUM, ModItems.rod_empty, ModItems.rod_thorium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(12, ReactorFuelType.THORIUM, ModItems.rod_dual_empty, ModItems.rod_dual_thorium_fuel_depleted);
		TileEntityMachineReactorLarge.registerWasteEntry(24, ReactorFuelType.THORIUM, ModItems.rod_quad_empty, ModItems.rod_quad_thorium_fuel_depleted);

		proxy.registerMissileItems();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if(logger == null)
			logger = event.getModLog();

		FMLCommonHandler.instance().bus().register(new ModEventHandler());
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new ModEventHandler());
		MinecraftForge.ORE_GEN_BUS.register(new ModEventHandler());
		PacketDispatcher.registerPackets();
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
}
