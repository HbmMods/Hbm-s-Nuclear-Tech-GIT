package com.hbm.main;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.LoadingCallback;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.*;
import com.hbm.creativetabs.*;
import com.hbm.entity.effect.*;
import com.hbm.entity.grenade.*;
import com.hbm.entity.item.EntityMinecartTest;
import com.hbm.entity.logic.*;
import com.hbm.entity.missile.*;
import com.hbm.entity.mob.*;
import com.hbm.entity.particle.*;
import com.hbm.entity.projectile.*;
import com.hbm.handler.*;
import com.hbm.handler.FluidTypeHandler.FluidType;
import com.hbm.inventory.*;
import com.hbm.inventory.MachineRecipes.ShredderRecipe;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmWorld;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.packet.PacketDispatcher;
import com.hbm.potion.HbmPotion;
import com.hbm.render.util.HmfModelLoader;
import com.hbm.tileentity.bomb.*;
import com.hbm.tileentity.conductor.*;
import com.hbm.tileentity.deco.*;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge.ReactorFuelType;

import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = RefStrings.MODID, name = RefStrings.NAME, version = RefStrings.VERSION)
public class MainRegistry
{
	@Instance(RefStrings.MODID)
	public static MainRegistry instance;
	
	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;
	
	@Metadata
	public static ModMetadata meta;
	
	public static Logger logger;
	
	//Tool Materials
	public static ToolMaterial enumToolMaterialSchrabidium = EnumHelper.addToolMaterial("SCHRABIDIUM", 3, 10000, 50.0F, 100.0F, 200);
	public static ToolMaterial enumToolMaterialHammer = EnumHelper.addToolMaterial("SCHRABIDIUMHAMMER", 3, 0, 50.0F, 999999996F, 200);
	public static ToolMaterial enumToolMaterialChainsaw = EnumHelper.addToolMaterial("CHAINSAW", 3, 1500, 50.0F, 22.0F, 0);
	public static ToolMaterial enumToolMaterialSteel = EnumHelper.addToolMaterial("STEEL", 2, 500, 7.5F, 2.0F, 10);
	public static ToolMaterial enumToolMaterialTitanium = EnumHelper.addToolMaterial("TITANIUM", 3, 750, 9.0F, 2.5F, 15);
	public static ToolMaterial enumToolMaterialAlloy= EnumHelper.addToolMaterial("ALLOY", 3, 2000, 15.0F, 5.0F, 5);
	public static ToolMaterial enumToolMaterialCmb = EnumHelper.addToolMaterial("CMB", 3, 8500, 40.0F, 55F, 100);
	public static ToolMaterial enumToolMaterialElec = EnumHelper.addToolMaterial("ELEC", 3, 4700, 30.0F, 12.0F, 2);
	public static ToolMaterial enumToolMaterialDesh = EnumHelper.addToolMaterial("DESH", 2, 0, 7.5F, 2.0F, 10);

	public static ToolMaterial enumToolMaterialSaw = EnumHelper.addToolMaterial("SAW", 2, 750, 2.0F, 3.5F, 25);
	public static ToolMaterial enumToolMaterialBat = EnumHelper.addToolMaterial("BAT", 0, 500, 1.5F, 3F, 25);
	public static ToolMaterial enumToolMaterialBatNail = EnumHelper.addToolMaterial("BATNAIL", 0, 450, 1.0F, 4F, 25);
	public static ToolMaterial enumToolMaterialGolfClub = EnumHelper.addToolMaterial("GOLFCLUB", 1, 1000, 2.0F, 5F, 25);
	public static ToolMaterial enumToolMaterialPipeRusty = EnumHelper.addToolMaterial("PIPERUSTY", 1, 350, 1.5F, 4.5F, 25);
	public static ToolMaterial enumToolMaterialPipeLead = EnumHelper.addToolMaterial("PIPELEAD", 1, 250, 1.5F, 5.5F, 25);

	public static ToolMaterial enumToolMaterialBottleOpener = EnumHelper.addToolMaterial("OPENER", 1, 250, 1.5F, 0.5F, 200);
	public static ToolMaterial enumToolMaterialSledge = EnumHelper.addToolMaterial("SHIMMERSLEDGE", 1, 0, 25.0F, 26F, 200);

	public static ToolMaterial enumToolMaterialMultitool = EnumHelper.addToolMaterial("MULTITOOL", 3, 5000, 25F, 5.5F, 25);
	
	//Armor Materials
	public static ArmorMaterial enumArmorMaterialEmerald = EnumHelper.addArmorMaterial("TEST", 2500, new int[] {3, 8, 6, 3}, 30);
	public static ArmorMaterial enumArmorMaterialSchrabidium = EnumHelper.addArmorMaterial("SCHRABIDIUM", 100, new int[] {3, 8, 6, 3}, 50);
	public static ArmorMaterial enumArmorMaterialEuphemium = EnumHelper.addArmorMaterial("EUPHEMIUM", 150000000, new int[] {3, 8, 6, 3}, 100);
	public static ArmorMaterial enumArmorMaterialHazmat = EnumHelper.addArmorMaterial("HAZMAT", 60, new int[] {2, 5, 4, 1}, 5);
	public static ArmorMaterial enumArmorMaterialHazmat2 = EnumHelper.addArmorMaterial("HAZMAT2", 60, new int[] {2, 5, 4, 1}, 5);
	public static ArmorMaterial enumArmorMaterialHazmat3 = EnumHelper.addArmorMaterial("HAZMAT3", 60, new int[] {2, 5, 4, 1}, 5);
	public static ArmorMaterial enumArmorMaterialT45 = EnumHelper.addArmorMaterial("T45", 1000, new int[] {2, 5, 4, 1}, 0);
	public static ArmorMaterial enumArmorMaterialSteel = EnumHelper.addArmorMaterial("STEEL", 20, new int[] {2, 6, 5, 2}, 5);
	public static ArmorMaterial enumArmorMaterialTitanium = EnumHelper.addArmorMaterial("TITANIUM", 25, new int[] {3, 8, 6, 3}, 9);
	public static ArmorMaterial enumArmorMaterialAlloy = EnumHelper.addArmorMaterial("ALLOY", 40, new int[] {3, 8, 6, 3}, 12);
	public static ArmorMaterial enumArmorMaterialPaa = EnumHelper.addArmorMaterial("PAA", 75, new int[] {3, 8, 6, 3}, 25);
	public static ArmorMaterial enumArmorMaterialCmb = EnumHelper.addArmorMaterial("CMB", 60, new int[] {3, 8, 6, 3}, 50);
	public static ArmorMaterial enumArmorMaterialAusIII = EnumHelper.addArmorMaterial("AUSIII", 375, new int[] {2, 6, 5, 2}, 0);
	public static ArmorMaterial enumArmorMaterialAusIV = EnumHelper.addArmorMaterial("AUSIV", 375, new int[] {2, 6, 5, 2}, 0);
	public static ArmorMaterial enumArmorMaterialAusV = EnumHelper.addArmorMaterial("AUSV", 375, new int[] {2, 6, 5, 2}, 0);
	public static ArmorMaterial enumArmorMaterialSecurity = EnumHelper.addArmorMaterial("SECURITY", 100, new int[] {3, 8, 6, 3}, 15);
	
	//Creative Tabs
	//ingots, nuggets, wires, machine parts
	public static CreativeTabs partsTab = new PartsTab(CreativeTabs.getNextID(), "tabParts");
	//items that belong in machines, fuels, etc
	public static CreativeTabs controlTab = new ControlTab(CreativeTabs.getNextID(), "tabControl");
	//templates, siren tracks
	public static CreativeTabs templateTab = new TemplateTab(CreativeTabs.getNextID(), "tabTemplate");
	//ore and mineral blocks
	public static CreativeTabs blockTab = new BlockTab(CreativeTabs.getNextID(), "tabBlocks");
	//machines, structure parts
	public static CreativeTabs machineTab = new MachineTab(CreativeTabs.getNextID(), "tabMachine");
	//bombs
	public static CreativeTabs nukeTab = new NukeTab(CreativeTabs.getNextID(), "tabNuke");
	//missiles, satellites
	public static CreativeTabs missileTab = new MissileTab(CreativeTabs.getNextID(), "tabMissile");
	//turrets, weapons, ammo
	public static CreativeTabs weaponTab = new WeaponTab(CreativeTabs.getNextID(), "tabWeapon");
	//drinks, kits, tools
	public static CreativeTabs consumableTab = new ConsumableTab(CreativeTabs.getNextID(), "tabConsumable");
	
	//Achievements
	public static Achievement achCircuit0;
	public static Achievement achCircuit1;
	public static Achievement achCircuit2;
	public static Achievement achCircuit3;
	public static Achievement achCircuit4;
	public static Achievement achCircuit5;
	public static Achievement achJack;
	public static Achievement achDalekanium;
	public static Achievement achRefinery;
	public static Achievement achBattery;
	public static Achievement achOil;
	public static Achievement achCatapult1;
	public static Achievement achCatapult2;
	public static Achievement achCatapult3;
	public static Achievement achU235;
	public static Achievement achPu238;
	public static Achievement achPu239;
	public static Achievement achNeptunium;
	public static Achievement achDesh;
	public static Achievement achMeteor;
	public static Achievement achGeiger;
	public static Achievement achDesignator;
	public static Achievement achRemote;
	public static Achievement achOverpowered;
	public static Achievement achShimSham;
	public static Achievement achMatchstick;
	public static Achievement achRails;
	public static Achievement achFolder;
	public static Achievement achPress;
	public static Achievement achFWatz;
	public static Achievement achTurbofan;
	public static Achievement achGadget;
	public static Achievement achBoy;
	public static Achievement achMan;
	public static Achievement achMike;
	public static Achievement achTsar;
	public static Achievement achFLEIJA;
	public static Achievement achPrototype;
	public static Achievement achCustom;
	public static Achievement achTurret;
	public static Achievement achMeteorDeath;
	public static Achievement achXenium;
	public static Achievement achRadiation;
	public static Achievement achSchrabidium;
	public static Achievement achEuphemium;
	
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
	public static Achievement bobMetalworks;
	public static Achievement bobAssembly;
	public static Achievement bobChemistry;
	public static Achievement bobOil;
	public static Achievement bobNuclear;
	
	public static boolean enableDebugMode = true;
	public static boolean enableMycelium = false;
	public static boolean enablePlutoniumOre = false;
	public static boolean enableDungeons = true;
	public static boolean enableMDOres = true;
	public static boolean enableMines = true;
	public static boolean enableRad = true;
	public static boolean enableNITAN = true;
	public static boolean enableNukeClouds = true;
	public static boolean enableAutoCleanup = false;
	public static boolean enableMeteorStrikes = true;
	public static boolean enableMeteorShowers = true;
	public static boolean enableMeteorTails = true;
	public static boolean enableSpecialMeteors = true;
	public static boolean enableBomberShortMode = false;
	public static boolean enableVaults = true;
	public static boolean enableRads = true;
	public static boolean enableCataclysm = true;
	public static boolean enableExtendedLogging = false;
	public static boolean enableHardcoreTaint = false;
	public static boolean enableGuns = true;

	public static int uraniumSpawn = 6;
	public static int thoriumSpawn = 7;
	public static int titaniumSpawn = 8;
	public static int sulfurSpawn = 5;
	public static int aluminiumSpawn = 7;
	public static int copperSpawn = 12;
	public static int fluoriteSpawn = 6;
	public static int niterSpawn = 6;
	public static int tungstenSpawn = 10;
	public static int leadSpawn = 6;
	public static int berylliumSpawn = 6;
	public static int ligniteSpawn = 2;
	
	public static int gadgetRadius = 150;
	public static int boyRadius = 120;
	public static int manRadius = 175;
	public static int mikeRadius = 250;
	public static int tsarRadius = 500;
	public static int prototypeRadius = 150;
	public static int fleijaRadius = 50;
	public static int soliniumRadius = 75;
	public static int n2Radius = 100;
	public static int missileRadius = 100;
	public static int mirvRadius = 100;
	public static int fatmanRadius = 35;
	public static int nukaRadius = 25;
	public static int aSchrabRadius = 20;

	public static int blastSpeed = 1024;
	public static int falloutRange = 100;
	public static int fSpeed = 256;
	//public static int falloutDura = 100;
	
	public static int radioStructure = 500;
	public static int antennaStructure = 250;
	public static int atomStructure = 500;
	public static int vertibirdStructure = 500;
	public static int dungeonStructure = 64;
	public static int relayStructure = 500;
	public static int satelliteStructure = 500;
	public static int bunkerStructure = 1000;
	public static int siloStructure = 1000;
	public static int factoryStructure = 1000;
	public static int dudStructure = 500;
	public static int spaceshipStructure = 1000;
	public static int barrelStructure = 5000;
	public static int geyserWater = 3000;
	public static int geyserChlorine = 3000;
	public static int geyserVapor = 500;
	
	public static int broadcaster = 5000;
	public static int minefreq = 64;
	public static int radfreq = 5000;
	public static int vaultfreq = 2500;
	
	public static int meteorStrikeChance = 20 * 60 * 180;
	public static int meteorShowerChance = 20 * 60 * 5;
	public static int meteorShowerDuration = 6000;
	public static int limitExplosionLifespan = 0;
	public static int radarRange = 1000;
	public static int radarBuffer = 30;
	public static int radarAltitude = 55;
	public static int ciwsHitrate = 50;

	public static int mk4 = 1024;
	public static int rain = 0;
	public static int cont = 0;
	public static int fogRad = 100;
	public static int fogCh = 20;
	public static float hellRad = 0.1F;

	public static int generalOverride = 0;
	public static int polaroidID = 1;

	public static int taintID = 62;
	public static int radiationID = 63;
	public static int bangID = 64;
	public static int mutationID = 65;
	public static int radxID = 66;
	public static int leadID = 67;

	public static int x;
	public static int y;
	public static int z;
	public static long time;
	
	Random rand = new Random();
	
	@EventHandler
	public void PreLoad(FMLPreInitializationEvent PreEvent)
	{
		if(logger == null)
			logger = PreEvent.getModLog();
		
		//Reroll Polaroid
		
		if(generalOverride > 0 && generalOverride < 19) {
			polaroidID = generalOverride;
		} else {
			polaroidID = rand.nextInt(18) + 1;
			while(polaroidID == 4 || polaroidID == 9)
				polaroidID = rand.nextInt(18) + 1;
		}
		
		ModBlocks.mainRegistry();
		ModItems.mainRegistry();
		CraftingManager.mainRegistry();
		proxy.registerRenderInfo();
		HbmWorld.mainRegistry();
		GameRegistry.registerFuelHandler(new FuelHandler());
		HbmPotion.init();
		BulletConfigSyncingUtil.loadConfigsForSync();
		
		Library.superuser.add("192af5d7-ed0f-48d8-bd89-9d41af8524f8");
		Library.superuser.add("5aee1e3d-3767-4987-a222-e7ce1fbdf88e");
		Library.superuser.add("937c9804-e11f-4ad2-a5b1-42e62ac73077");
		Library.superuser.add("3af1c262-61c0-4b12-a4cb-424cc3a9c8c0");
		Library.superuser.add("4729b498-a81c-42fd-8acd-20d6d9f759e0");
		Library.superuser.add("c3f5e449-6d8c-4fe3-acc9-47ef50e7e7ae");
		//until he manages to do the most basic thing and INSTALL NEI
		//Library.superuser.add("122fe98f-be19-49ca-a96b-d4dee4f0b22e");
		
		Library.initBooks();

		enumArmorMaterialSchrabidium.customCraftingMaterial = ModItems.ingot_schrabidium;
		enumArmorMaterialHazmat.customCraftingMaterial = ModItems.hazmat_cloth;
		enumArmorMaterialHazmat2.customCraftingMaterial = ModItems.hazmat_cloth_red;
		enumArmorMaterialHazmat3.customCraftingMaterial = ModItems.hazmat_cloth_grey;
		enumArmorMaterialT45.customCraftingMaterial = ModItems.plate_titanium;
		enumArmorMaterialTitanium.customCraftingMaterial = ModItems.ingot_titanium;
		enumArmorMaterialSteel.customCraftingMaterial = ModItems.ingot_steel;
		enumArmorMaterialAlloy.customCraftingMaterial = ModItems.ingot_advanced_alloy;
		enumArmorMaterialPaa.customCraftingMaterial = ModItems.plate_paa;
		enumArmorMaterialCmb.customCraftingMaterial = ModItems.ingot_combine_steel;
		enumArmorMaterialAusIII.customCraftingMaterial = ModItems.ingot_australium;
		enumArmorMaterialAusIV.customCraftingMaterial = ModItems.rod_australium;
		enumArmorMaterialAusV.customCraftingMaterial = ModItems.nugget_australium;
		enumArmorMaterialSecurity.customCraftingMaterial = ModItems.plate_kevlar;
		enumToolMaterialSchrabidium.setRepairItem(new ItemStack(ModItems.ingot_schrabidium));
		enumToolMaterialHammer.setRepairItem(new ItemStack(Item.getItemFromBlock(ModBlocks.block_schrabidium)));
		enumToolMaterialChainsaw.setRepairItem(new ItemStack(ModItems.ingot_steel));
		enumToolMaterialTitanium.setRepairItem(new ItemStack(ModItems.ingot_titanium));
		enumToolMaterialSteel.setRepairItem(new ItemStack(ModItems.ingot_steel));
		enumToolMaterialAlloy.setRepairItem(new ItemStack(ModItems.ingot_advanced_alloy));
		enumToolMaterialCmb.setRepairItem(new ItemStack(ModItems.ingot_combine_steel));
		enumToolMaterialBottleOpener.setRepairItem(new ItemStack(ModItems.plate_steel));
		enumToolMaterialDesh.setRepairItem(new ItemStack(ModItems.ingot_desh));
		
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
		GameRegistry.registerTileEntity(TileEntityTaint.class, "tileentity_taint");
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
		GameRegistry.registerTileEntity(TileEntityMachineArcFurnace.class, "tileentity_arc_furnace");
		GameRegistry.registerTileEntity(TileEntityMachineAmgen.class, "tileentity_amgen");
		GameRegistry.registerTileEntity(TileEntityGeysir.class, "tileentity_geysir");

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
	    EntityRegistry.registerModEntity(EntityMiniNuke.class, "entity_mini_nuke", 36, this, 1000, 1, true);
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
	    EntityRegistry.registerModEntity(EntityMiniMIRV.class, "entity_mini_mirv", 55, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityBaleflare.class, "entity_bf_projectile", 56, this, 1000, 1, true);
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
	    
	    EntityRegistry.registerGlobalEntityID(EntityNuclearCreeper.class, "entity_mob_nuclear_creeper", EntityRegistry.findGlobalUniqueEntityId(), 0x204131, 0x75CE00);
	    EntityRegistry.registerGlobalEntityID(EntityTaintedCreeper.class, "entity_mob_tainted_creeper", EntityRegistry.findGlobalUniqueEntityId(), 0x813b9b, 0xd71fdd);
	    EntityRegistry.registerGlobalEntityID(EntityHunterChopper.class, "entity_mob_hunter_chopper", EntityRegistry.findGlobalUniqueEntityId(), 0x000020, 0x2D2D72);
	    EntityRegistry.registerGlobalEntityID(EntityCyberCrab.class, "entity_cyber_crab", EntityRegistry.findGlobalUniqueEntityId(), 0xAAAAAA, 0x444444);
	
		ForgeChunkManager.setForcedChunkLoadingCallback(this, new LoadingCallback() {
			
	        @Override
	        public void ticketsLoaded(List<Ticket> tickets, World world) {
	            for(Ticket ticket : tickets) {
	            	
	                if(ticket.getEntity() instanceof IChunkLoader) {
	                    ((IChunkLoader)ticket.getEntity()).init(ticket);
	                }
	            }
	        }
	    });

		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_generic, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeGeneric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_strong, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeStrong(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_frag, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeFrag(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_fire, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeFire(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_cluster, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeCluster(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_flare, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeFlare(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_electric, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeElectric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_poison, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadePoison(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_gas, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeGas(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_schrabidium, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeSchrabidium(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_nuke, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeNuke(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_nuclear, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeNuclear(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_pulse, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadePulse(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_plasma, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadePlasma(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_tau, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeTau(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_lemon, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeLemon(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_mk2, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeMk2(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_aschrab, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeASchrab(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_zomg, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeZOMG(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_shrapnel, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeShrapnel(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_black_hole, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeBlackHole(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_gascan, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeGascan(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_cloud, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeCloud(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_pink_cloud, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadePC(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_smart, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeSmart(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_mirv, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeMIRV(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_breach, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeBreach(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_burst, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeBurst(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_generic, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFGeneric(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_he, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFHE(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_bouncy, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFBouncy(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_sticky, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFSticky(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_impact, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFImpact(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_incendiary, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFIncendiary(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_toxic, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFToxic(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_concussion, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFConcussion(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_brimstone, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFBrimstone(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_mystery, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFMystery(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_spark, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFSpark(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_hopwire, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFHopwire(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
		BlockDispenser.dispenseBehaviorRegistry.putObject(ModItems.grenade_if_null, new BehaviorProjectileDispense() {

            protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
            {
                return new EntityGrenadeIFNull(p_82499_1_, p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());
            }
        });
	}

	@EventHandler
	public static void load(FMLInitializationEvent event)
	{
		/*achievementGetTitanium = new Achievement("achievement.getTitanium", "getTitanium", 0, -6, ModItems.ingot_titanium, (Achievement)null).initIndependentStat() .registerStat();
		achievementCraftAlloyFurnace = new Achievement("achievement.craftAlloyFurnace", "craftAlloyFurnace", -2, -4, Item.getItemFromBlock(ModBlocks.machine_difurnace_off), (Achievement)null).initIndependentStat() .registerStat();
		achievementCraftBreedingReactor = new Achievement("achievement.craftBreedingReactor", "craftBreedingReactor", 2, -4, Item.getItemFromBlock(ModBlocks.machine_reactor), (Achievement)null).initIndependentStat() .registerStat();
		achievementCraftCentrifuge = new Achievement("achievement.craftCentrifuge", "craftCentrifuge", 2, -2, Item.getItemFromBlock(ModBlocks.machine_centrifuge), achievementCraftBreedingReactor).initIndependentStat() .registerStat();
		AchievementPage.registerAchievementPage(new AchievementPage("NTM Achievements", new Achievement[]{ 
				achievementGetTitanium,
				achievementCraftAlloyFurnace,
				achievementCraftBreedingReactor,
				achievementCraftCentrifuge
		}));*/
		achSacrifice = new Achievement("achievement.sacrifice", "sacrifice", 0, 0, ModItems.burnt_bark, null).initIndependentStat().setSpecial().registerStat();
		achImpossible = new Achievement("achievement.impossible", "impossible", 2, 0, ModItems.nothing, null).initIndependentStat().setSpecial().registerStat();
		achTOB = new Achievement("achievement.tasteofblood", "tasteofblood", 0, 2, new ItemStack(ModItems.fluid_icon, 1, FluidType.ASCHRAB.getID()), null).initIndependentStat().setSpecial().registerStat();
		achFreytag = new Achievement("achievement.freytag", "freytag", 0, -2, ModItems.gun_mp40, null).initIndependentStat().setSpecial().registerStat();
		achSelenium = new Achievement("achievement.selenium", "selenium", -2, -2, ModItems.ingot_starmetal, null).initIndependentStat().setSpecial().registerStat();
		achPotato = new Achievement("achievement.potato", "potato", -2, 0, ModItems.battery_potatos, null).initIndependentStat().setSpecial().registerStat();
		achC44 = new Achievement("achievement.c44", "c44", 2, -2, ModItems.gun_revolver_pip, null).initIndependentStat().setSpecial().registerStat();
		achC20_5 = new Achievement("achievement.c20_5", "c20_5", 4, -2, ModItems.gun_dampfmaschine, null).initIndependentStat().setSpecial().registerStat();
		achSpace = new Achievement("achievement.space", "space", 4, 0, ModItems.missile_carrier, null).initIndependentStat().setSpecial().registerStat();
		achFOEQ = new Achievement("achievement.FOEQ", "FOEQ", 4, 2, ModItems.sat_foeq, null).initIndependentStat().setSpecial().registerStat();
		achFiend = new Achievement("achievement.fiend", "fiend", 6, 0, ModItems.shimmer_sledge, null).initIndependentStat().setSpecial().registerStat();
		achFiend2 = new Achievement("achievement.fiend2", "fiend2", 6, 2, ModItems.shimmer_axe, null).initIndependentStat().setSpecial().registerStat();

		bobMetalworks = new Achievement("achievement.metalworks", "metalworks", -2, 4, ModItems.bob_metalworks, null).initIndependentStat().registerStat();
		bobAssembly = new Achievement("achievement.assembly", "assembly", 0, 4, ModItems.bob_assembly, bobMetalworks).initIndependentStat().registerStat();
		bobChemistry = new Achievement("achievement.chemistry", "chemistry", 2, 4, ModItems.bob_chemistry, bobAssembly).initIndependentStat().registerStat();
		bobOil = new Achievement("achievement.oil", "oil", 4, 4, ModItems.bob_oil, bobChemistry).initIndependentStat().registerStat();
		bobNuclear = new Achievement("achievement.nuclear", "nuclear", 6, 4, ModItems.bob_nuclear, bobOil).initIndependentStat().registerStat();
		
		AchievementPage.registerAchievementPage(new AchievementPage("Nuclear Tech", new Achievement[]{ 
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
				bobMetalworks,
				bobAssembly,
				bobChemistry,
				bobOil,
				bobNuclear
		}));

		//MUST be initialized AFTER achievements!!
		BobmazonOfferFactory.init();
		
		OreDictionary.registerOre("ingotUranium", ModItems.ingot_uranium);
		OreDictionary.registerOre("ingotUranium233", ModItems.ingot_u233);
		OreDictionary.registerOre("ingotUranium235", ModItems.ingot_u235);
		OreDictionary.registerOre("ingotUranium238", ModItems.ingot_u238);
		OreDictionary.registerOre("ingotThorium", ModItems.ingot_th232);
		OreDictionary.registerOre("ingotThorium232", ModItems.ingot_th232);
		OreDictionary.registerOre("ingotPlutonium", ModItems.ingot_plutonium);
		OreDictionary.registerOre("ingotPlutonium238", ModItems.ingot_pu238);
		OreDictionary.registerOre("ingotPlutonium239", ModItems.ingot_pu239);
		OreDictionary.registerOre("ingotPlutonium240", ModItems.ingot_pu240);
		OreDictionary.registerOre("U233", ModItems.ingot_u233);
		OreDictionary.registerOre("U235", ModItems.ingot_u235);
		OreDictionary.registerOre("U238", ModItems.ingot_u238);
		OreDictionary.registerOre("Th232", ModItems.ingot_th232);
		OreDictionary.registerOre("Pu238", ModItems.ingot_pu238);
		OreDictionary.registerOre("Pu39", ModItems.ingot_pu239);
		OreDictionary.registerOre("Pu240", ModItems.ingot_pu240);
		OreDictionary.registerOre("ingotTitanium", ModItems.ingot_titanium);
		OreDictionary.registerOre("ingotSchrabidium", ModItems.ingot_schrabidium);
		OreDictionary.registerOre("dustSchrabidium", ModItems.powder_schrabidium);
		OreDictionary.registerOre("dustSulfur", ModItems.sulfur);
		OreDictionary.registerOre("dustNiter", ModItems.niter);
		OreDictionary.registerOre("dustSalpeter", ModItems.niter);
		OreDictionary.registerOre("dustLead", ModItems.powder_lead);
		OreDictionary.registerOre("dustNeptunium", ModItems.powder_neptunium);
		OreDictionary.registerOre("ingotCopper", ModItems.ingot_copper);
		OreDictionary.registerOre("ingotRedAlloy", ModItems.ingot_red_copper);
		OreDictionary.registerOre("ingotRedstoneAlloy", ModItems.ingot_red_copper);
		OreDictionary.registerOre("ingotAdvanced", ModItems.ingot_advanced_alloy);
		OreDictionary.registerOre("ingotAdvancedAlloy", ModItems.ingot_advanced_alloy);
		OreDictionary.registerOre("ingotTungsten", ModItems.ingot_tungsten);
		OreDictionary.registerOre("ingotAluminum", ModItems.ingot_aluminium);
		OreDictionary.registerOre("ingotBeryllium", ModItems.ingot_beryllium);
		OreDictionary.registerOre("ingotNeptunium", ModItems.ingot_neptunium);
		OreDictionary.registerOre("ingotLead", ModItems.ingot_lead);
		OreDictionary.registerOre("ingotLithium", ModItems.lithium);
		OreDictionary.registerOre("ingotMagnetizedTungsten", ModItems.ingot_magnetized_tungsten);
		OreDictionary.registerOre("ingotCMBSteel", ModItems.ingot_combine_steel);
		OreDictionary.registerOre("ingotAustralium", ModItems.ingot_australium);
		OreDictionary.registerOre("ingotWeidanium", ModItems.ingot_weidanium);
		OreDictionary.registerOre("ingotReiium", ModItems.ingot_reiium);
		OreDictionary.registerOre("ingotUnobtainium", ModItems.ingot_unobtainium);
		OreDictionary.registerOre("ingotDaffergon", ModItems.ingot_daffergon);
		OreDictionary.registerOre("ingotVerticium", ModItems.ingot_verticium);
		OreDictionary.registerOre("ingotDuraSteel", ModItems.ingot_dura_steel);
		OreDictionary.registerOre("ingotPolymer", ModItems.ingot_polymer);
		OreDictionary.registerOre("ingotLanthanium", ModItems.ingot_lanthanium);
		OreDictionary.registerOre("ingotActinium", ModItems.ingot_actinium);
		OreDictionary.registerOre("ingotDesh", ModItems.ingot_desh);
		OreDictionary.registerOre("ingotSaturnite", ModItems.ingot_saturnite);
		OreDictionary.registerOre("ingotEuphemium", ModItems.ingot_euphemium);
		OreDictionary.registerOre("ingotDineutronium", ModItems.ingot_dineutronium);
		OreDictionary.registerOre("dustFluorite", ModItems.fluorite);
		OreDictionary.registerOre("nuggetLead", ModItems.nugget_lead);
		OreDictionary.registerOre("nuggetUranium", ModItems.nugget_uranium);
		OreDictionary.registerOre("nuggetUranium233", ModItems.nugget_u233);
		OreDictionary.registerOre("nuggetUranium235", ModItems.nugget_u235);
		OreDictionary.registerOre("nuggetUranium238", ModItems.nugget_u238);
		OreDictionary.registerOre("nuggetThorium", ModItems.nugget_th232);
		OreDictionary.registerOre("nuggetThorium232", ModItems.nugget_th232);
		OreDictionary.registerOre("nuggetPlutonium", ModItems.nugget_plutonium);
		OreDictionary.registerOre("nuggetPlutonium238", ModItems.nugget_pu238);
		OreDictionary.registerOre("nuggetPlutonium239", ModItems.nugget_pu239);
		OreDictionary.registerOre("nuggetPlutonium240", ModItems.nugget_pu240);
		OreDictionary.registerOre("nuggetAustralium", ModItems.nugget_australium);
		OreDictionary.registerOre("nuggetWeidanium", ModItems.nugget_weidanium);
		OreDictionary.registerOre("nuggetReiium", ModItems.nugget_reiium);
		OreDictionary.registerOre("nuggetUnobtainium", ModItems.nugget_unobtainium);
		OreDictionary.registerOre("nuggetDaffergon", ModItems.nugget_daffergon);
		OreDictionary.registerOre("nuggetVerticium", ModItems.nugget_verticium);
		OreDictionary.registerOre("nuggetEuphemium", ModItems.nugget_euphemium);
		OreDictionary.registerOre("tinyU233", ModItems.nugget_u233);
		OreDictionary.registerOre("tinyU235", ModItems.nugget_u235);
		OreDictionary.registerOre("tinyU238", ModItems.nugget_u238);
		OreDictionary.registerOre("tinyTh232", ModItems.nugget_th232);
		OreDictionary.registerOre("tinyPu238", ModItems.nugget_pu238);
		OreDictionary.registerOre("tinyPu239", ModItems.nugget_pu239);
		OreDictionary.registerOre("tinyPu240", ModItems.nugget_pu240);
		OreDictionary.registerOre("nuggetNeptunium", ModItems.nugget_neptunium);
		OreDictionary.registerOre("nuggetSchrabidium", ModItems.nugget_schrabidium);
		OreDictionary.registerOre("plateTitanium", ModItems.plate_titanium);
		OreDictionary.registerOre("plateAluminum", ModItems.plate_aluminium);
		OreDictionary.registerOre("plateDenseLead", ModItems.neutron_reflector);
		OreDictionary.registerOre("ingotSteel", ModItems.ingot_steel);
		OreDictionary.registerOre("plateSteel", ModItems.plate_steel);
		OreDictionary.registerOre("plateLead", ModItems.plate_lead);
		OreDictionary.registerOre("plateCopper", ModItems.plate_copper);
		OreDictionary.registerOre("plateIron", ModItems.plate_iron);
		OreDictionary.registerOre("plateGold", ModItems.plate_gold);
		OreDictionary.registerOre("plateAdvanced", ModItems.plate_advanced_alloy);
		OreDictionary.registerOre("plateSchrabidium", ModItems.plate_schrabidium);
		OreDictionary.registerOre("plateCMBSteel", ModItems.plate_combine_steel);
		OreDictionary.registerOre("plateDesh", ModItems.plate_desh);
		OreDictionary.registerOre("plateSaturnite", ModItems.plate_saturnite);
		OreDictionary.registerOre("plateEuphemium", ModItems.plate_euphemium);
		OreDictionary.registerOre("plateDineutronium", ModItems.plate_dineutronium);
		OreDictionary.registerOre("dustIron", ModItems.powder_iron);
		OreDictionary.registerOre("dustGold", ModItems.powder_gold);
		OreDictionary.registerOre("dustUranium", ModItems.powder_uranium);
		OreDictionary.registerOre("dustThorium", ModItems.powder_thorium);
		OreDictionary.registerOre("dustPlutonium", ModItems.powder_plutonium);
		OreDictionary.registerOre("dustTitanium", ModItems.powder_titanium);
		OreDictionary.registerOre("dustTungsten", ModItems.powder_tungsten);
		OreDictionary.registerOre("dustCopper", ModItems.powder_copper);
		OreDictionary.registerOre("dustBeryllium", ModItems.powder_beryllium);
		OreDictionary.registerOre("dustAluminum", ModItems.powder_aluminium);
		OreDictionary.registerOre("dustDiamond", ModItems.powder_diamond);
		OreDictionary.registerOre("dustEmerald", ModItems.powder_emerald);
		OreDictionary.registerOre("dustLapis", ModItems.powder_lapis);
		OreDictionary.registerOre("dustCoal", ModItems.powder_coal);
		OreDictionary.registerOre("dustLignite", ModItems.powder_lignite);
		OreDictionary.registerOre("dustAdvanced", ModItems.powder_advanced_alloy);
		OreDictionary.registerOre("dustAdvancedAlloy", ModItems.powder_advanced_alloy);
		OreDictionary.registerOre("dustCMBSteel", ModItems.powder_combine_steel);
		OreDictionary.registerOre("dustMagnetizedTungsten", ModItems.powder_magnetized_tungsten);
		OreDictionary.registerOre("dustRedAlloy", ModItems.powder_red_copper);
		OreDictionary.registerOre("dustRedstoneAlloy", ModItems.powder_red_copper);
		OreDictionary.registerOre("dustSteel", ModItems.powder_steel);
		OreDictionary.registerOre("dustLithium", ModItems.powder_lithium);
		OreDictionary.registerOre("dustNetherQuartz", ModItems.powder_quartz);
		OreDictionary.registerOre("dustAustralium", ModItems.powder_australium);
		OreDictionary.registerOre("dustWeidanium", ModItems.powder_weidanium);
		OreDictionary.registerOre("dustReiium", ModItems.powder_reiium);
		OreDictionary.registerOre("dustUnobtainium", ModItems.powder_unobtainium);
		OreDictionary.registerOre("dustDaffergon", ModItems.powder_daffergon);
		OreDictionary.registerOre("dustVerticium", ModItems.powder_verticium);
		OreDictionary.registerOre("dustDuraSteel", ModItems.powder_dura_steel);
		OreDictionary.registerOre("dustPolymer", ModItems.powder_polymer);
		OreDictionary.registerOre("dustLanthanium", ModItems.powder_lanthanium);
		OreDictionary.registerOre("dustActinium", ModItems.powder_actinium);
		OreDictionary.registerOre("dustDesh", ModItems.powder_desh);
		OreDictionary.registerOre("dustEuphemium", ModItems.powder_euphemium);
		OreDictionary.registerOre("dustDineutronium", ModItems.powder_dineutronium);

		OreDictionary.registerOre("dustNeptunium", ModItems.powder_neptunium);
		OreDictionary.registerOre("dustIodine", ModItems.powder_iodine);
		OreDictionary.registerOre("dustThorium", ModItems.powder_thorium);
		OreDictionary.registerOre("dustAstatine", ModItems.powder_astatine);
		OreDictionary.registerOre("dustNeodymium", ModItems.powder_neodymium);
		OreDictionary.registerOre("dustCaesium", ModItems.powder_caesium);
		OreDictionary.registerOre("dustStrontium", ModItems.powder_strontium);
		OreDictionary.registerOre("dustCobalt", ModItems.powder_cobalt);
		OreDictionary.registerOre("dustBromine", ModItems.powder_bromine);
		OreDictionary.registerOre("dustNiobium", ModItems.powder_niobium);
		OreDictionary.registerOre("dustTennessine", ModItems.powder_tennessine);
		OreDictionary.registerOre("dustCerium", ModItems.powder_cerium);

		OreDictionary.registerOre("nuggetNeodymium", ModItems.fragment_neodymium);
		OreDictionary.registerOre("nuggetCobalt", ModItems.fragment_cobalt);
		OreDictionary.registerOre("nuggetNiobium", ModItems.fragment_niobium);
		OreDictionary.registerOre("nuggetCerium", ModItems.fragment_cerium);
		OreDictionary.registerOre("nuggetLanthanium", ModItems.fragment_lanthanium);
		OreDictionary.registerOre("nuggetActinium", ModItems.fragment_actinium);

		OreDictionary.registerOre("gemCoal", Items.coal);
		OreDictionary.registerOre("gemLignite", ModItems.lignite);

		OreDictionary.registerOre("oreUranium", ModBlocks.ore_uranium);
		OreDictionary.registerOre("oreThorium", ModBlocks.ore_thorium);
		OreDictionary.registerOre("oreTitanium", ModBlocks.ore_titanium);
		OreDictionary.registerOre("oreSchrabidium", ModBlocks.ore_schrabidium);
		OreDictionary.registerOre("oreSulfur", ModBlocks.ore_sulfur);
		OreDictionary.registerOre("oreNiter", ModBlocks.ore_niter);
		OreDictionary.registerOre("oreSapeter", ModBlocks.ore_niter);
		OreDictionary.registerOre("oreCopper", ModBlocks.ore_copper);
		OreDictionary.registerOre("oreTungsten", ModBlocks.ore_tungsten);
		OreDictionary.registerOre("oreAluminum", ModBlocks.ore_aluminium);
		OreDictionary.registerOre("oreFluorite", ModBlocks.ore_fluorite);
		OreDictionary.registerOre("oreLead", ModBlocks.ore_lead);
		OreDictionary.registerOre("oreBeryllium", ModBlocks.ore_beryllium);
		OreDictionary.registerOre("oreLignite", ModBlocks.ore_lignite);
		OreDictionary.registerOre("oreAustralium", ModBlocks.ore_australium);
		OreDictionary.registerOre("oreWeidanium", ModBlocks.ore_weidanium);
		OreDictionary.registerOre("oreReiium", ModBlocks.ore_reiium);
		OreDictionary.registerOre("oreUnobtainium", ModBlocks.ore_unobtainium);
		OreDictionary.registerOre("oreDaffergon", ModBlocks.ore_daffergon);
		OreDictionary.registerOre("oreVerticium", ModBlocks.ore_verticium);
		OreDictionary.registerOre("oreRareEarth", ModBlocks.ore_rare);

		OreDictionary.registerOre("oreUranium", ModBlocks.ore_nether_uranium);
		OreDictionary.registerOre("orePlutonium", ModBlocks.ore_nether_plutonium);
		OreDictionary.registerOre("oreTungsten", ModBlocks.ore_nether_tungsten);
		OreDictionary.registerOre("oreSulfur", ModBlocks.ore_nether_sulfur);
		OreDictionary.registerOre("oreSchrabidium", ModBlocks.ore_nether_schrabidium);

		OreDictionary.registerOre("blockThorium", ModBlocks.block_thorium);
		OreDictionary.registerOre("blockUranium", ModBlocks.block_uranium);
		OreDictionary.registerOre("blockTitanium", ModBlocks.block_titanium);
		OreDictionary.registerOre("blockSulfur", ModBlocks.block_sulfur);
		OreDictionary.registerOre("blockNiter", ModBlocks.block_niter);
		OreDictionary.registerOre("blockSalpeter", ModBlocks.block_niter);
		OreDictionary.registerOre("blockCopper", ModBlocks.block_copper);
		OreDictionary.registerOre("blockRedAlloy", ModBlocks.block_red_copper);
		OreDictionary.registerOre("blockRedstoneAlloy", ModBlocks.block_red_copper);
		OreDictionary.registerOre("blockAdvanced", ModBlocks.block_advanced_alloy);
		OreDictionary.registerOre("blockTungsten", ModBlocks.block_tungsten);
		OreDictionary.registerOre("blockAluminum", ModBlocks.block_aluminium);
		OreDictionary.registerOre("blockFluorite", ModBlocks.block_fluorite);
		OreDictionary.registerOre("blockSteel", ModBlocks.block_steel);
		OreDictionary.registerOre("blockLead", ModBlocks.block_lead);
		OreDictionary.registerOre("blockBeryllium", ModBlocks.block_beryllium);
		OreDictionary.registerOre("blockSchrabidium", ModBlocks.block_schrabidium);
		OreDictionary.registerOre("blockCMBSteel", ModBlocks.block_combine_steel);
		OreDictionary.registerOre("blockMagnetizedTungsten", ModBlocks.block_magnetized_tungsten);
		OreDictionary.registerOre("blockAustralium", ModBlocks.block_australium);
		OreDictionary.registerOre("blockWeidanium", ModBlocks.block_weidanium);
		OreDictionary.registerOre("blockReiium", ModBlocks.block_reiium);
		OreDictionary.registerOre("blockUnobtainium", ModBlocks.block_unobtainium);
		OreDictionary.registerOre("blockDaffergon", ModBlocks.block_daffergon);
		OreDictionary.registerOre("blockVerticium", ModBlocks.block_verticium);
		OreDictionary.registerOre("blockDesh", ModBlocks.block_desh);
	}
	
	@EventHandler
	public static void PostLoad(FMLPostInitializationEvent PostEvent)
	{
		ShredderRecipe recipes = new MachineRecipes().new ShredderRecipe();
		
		recipes.registerEverythingImSrs();
		
		recipes.addRecipes();
		
		recipes.removeDuplicates();

		recipes.overridePreSetRecipe(new ItemStack(ModItems.scrap), new ItemStack(ModItems.dust));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.dust), new ItemStack(ModItems.dust));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.glowstone), new ItemStack(Items.glowstone_dust, 4));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.quartz_block, 1, 0), new ItemStack(ModItems.powder_quartz, 4));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.quartz_block, 1, 1), new ItemStack(ModItems.powder_quartz, 4));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.quartz_block, 1, 2), new ItemStack(ModItems.powder_quartz, 4));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.quartz_stairs), new ItemStack(ModItems.powder_quartz, 3));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.stone_slab, 1, 7), new ItemStack(ModItems.powder_quartz, 2));
		recipes.overridePreSetRecipe(new ItemStack(Items.quartz), new ItemStack(ModItems.powder_quartz));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.quartz_ore), new ItemStack(ModItems.powder_quartz, 2));
		recipes.overridePreSetRecipe(new ItemStack(ModBlocks.ore_nether_fire), new ItemStack(ModItems.powder_fire, 6));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.packed_ice), new ItemStack(ModItems.powder_ice, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModBlocks.brick_light), new ItemStack(Items.clay_ball, 4));
		recipes.overridePreSetRecipe(new ItemStack(ModBlocks.brick_concrete), new ItemStack(Blocks.gravel, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModBlocks.brick_obsidian), new ItemStack(ModBlocks.gravel_obsidian, 1));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.obsidian), new ItemStack(ModBlocks.gravel_obsidian, 1));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.stone), new ItemStack(Blocks.gravel, 1));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.gravel, 1));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.gravel, 1));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.gravel), new ItemStack(Blocks.sand, 1));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.sand), new ItemStack(ModItems.dust, 2));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.brick_block), new ItemStack(Items.clay_ball, 4));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.brick_stairs), new ItemStack(Items.clay_ball, 3));
		recipes.overridePreSetRecipe(new ItemStack(Items.flower_pot), new ItemStack(Items.clay_ball, 3));
		recipes.overridePreSetRecipe(new ItemStack(Items.brick), new ItemStack(Items.clay_ball, 1));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.sandstone), new ItemStack(Blocks.sand, 4));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.sandstone_stairs), new ItemStack(Blocks.sand, 6));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.clay), new ItemStack(Items.clay_ball, 4));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.hardened_clay), new ItemStack(Items.clay_ball, 4));
		recipes.overridePreSetRecipe(new ItemStack(Blocks.tnt), new ItemStack(Items.gunpowder, 5));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.powder_quartz), new ItemStack(ModItems.powder_lithium_tiny, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.powder_lapis), new ItemStack(ModItems.powder_cobalt_tiny, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.fragment_neodymium), new ItemStack(ModItems.powder_neodymium_tiny, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.fragment_cobalt), new ItemStack(ModItems.powder_cobalt_tiny, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.fragment_niobium), new ItemStack(ModItems.powder_niobium_tiny, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.fragment_cerium), new ItemStack(ModItems.powder_cerium_tiny, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.fragment_lanthanium), new ItemStack(ModItems.powder_lanthanium_tiny, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.fragment_actinium), new ItemStack(ModItems.powder_actinium_tiny, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.fragment_meteorite), new ItemStack(ModItems.powder_meteorite_tiny, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModBlocks.block_meteor), new ItemStack(ModItems.powder_meteorite, 10));
		recipes.overridePreSetRecipe(new ItemStack(Items.enchanted_book), new ItemStack(ModItems.powder_magic, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.arc_electrode_burnt), new ItemStack(ModItems.powder_coal, 1));
		recipes.overridePreSetRecipe(new ItemStack(ModItems.arc_electrode_desh), new ItemStack(ModItems.powder_desh, 2));

		for(int i = 0; i < 16; i++) {
			recipes.overridePreSetRecipe(new ItemStack(Blocks.stained_hardened_clay, 1, i), new ItemStack(Items.clay_ball, 4));
			recipes.overridePreSetRecipe(new ItemStack(Blocks.wool, 1, i), new ItemStack(Items.string, 4));
		}
		
		recipes.PrintRecipes();

		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(Items.water_bucket), new ItemStack(Items.bucket), FluidType.WATER, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(Items.lava_bucket), new ItemStack(Items.bucket), FluidType.LAVA, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.bucket_mud), new ItemStack(Items.bucket), FluidType.WATZ, 1000));
		
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_water), new ItemStack(ModItems.rod_empty), FluidType.WATER, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_dual_water), new ItemStack(ModItems.rod_dual_empty), FluidType.WATER, 2000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_quad_water), new ItemStack(ModItems.rod_quad_empty), FluidType.WATER, 4000));
		
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_coolant), new ItemStack(ModItems.rod_empty), FluidType.COOLANT, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_dual_coolant), new ItemStack(ModItems.rod_dual_empty), FluidType.COOLANT, 2000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_quad_coolant), new ItemStack(ModItems.rod_quad_empty), FluidType.COOLANT, 4000));
		
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_oil), new ItemStack(ModItems.canister_empty), FluidType.OIL, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_smear), new ItemStack(ModItems.canister_empty), FluidType.SMEAR, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_heavyoil), new ItemStack(ModItems.canister_empty), FluidType.HEAVYOIL, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_bitumen), new ItemStack(ModItems.canister_empty), FluidType.BITUMEN, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_heatingoil), new ItemStack(ModItems.canister_empty), FluidType.HEATINGOIL, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_reoil), new ItemStack(ModItems.canister_empty), FluidType.RECLAIMED, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_petroil), new ItemStack(ModItems.canister_empty), FluidType.PETROIL, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_canola), new ItemStack(ModItems.canister_empty), FluidType.LUBRICANT, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_naphtha), new ItemStack(ModItems.canister_empty), FluidType.NAPHTHA, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_fuel), new ItemStack(ModItems.canister_empty), FluidType.DIESEL, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_lightoil), new ItemStack(ModItems.canister_empty), FluidType.LIGHTOIL, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_kerosene), new ItemStack(ModItems.canister_empty), FluidType.KEROSENE, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_biofuel), new ItemStack(ModItems.canister_empty), FluidType.BIOFUEL, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.canister_NITAN), new ItemStack(ModItems.canister_empty), FluidType.NITAN, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.gas_full), new ItemStack(ModItems.gas_empty), FluidType.GAS, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.gas_petroleum), new ItemStack(ModItems.gas_empty), FluidType.PETROLEUM, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.gas_biogas), new ItemStack(ModItems.gas_empty), FluidType.BIOGAS, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModBlocks.red_barrel), new ItemStack(ModItems.tank_steel), FluidType.DIESEL, 10000));
		
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_deuterium), new ItemStack(ModItems.cell_empty), FluidType.DEUTERIUM, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_tritium), new ItemStack(ModItems.cell_empty), FluidType.TRITIUM, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_tritium), new ItemStack(ModItems.rod_empty), FluidType.TRITIUM, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_dual_tritium), new ItemStack(ModItems.rod_dual_empty), FluidType.TRITIUM, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.rod_quad_tritium), new ItemStack(ModItems.rod_quad_empty), FluidType.TRITIUM, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_uf6), new ItemStack(ModItems.cell_empty), FluidType.UF6, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_puf6), new ItemStack(ModItems.cell_empty), FluidType.PUF6, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_antimatter), new ItemStack(ModItems.cell_empty), FluidType.AMAT, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_anti_schrabidium), new ItemStack(ModItems.cell_empty), FluidType.ASCHRAB, 1000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.cell_sas3), new ItemStack(ModItems.cell_empty), FluidType.SAS3, 1000));

		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModBlocks.ore_oil), new ItemStack(ModBlocks.ore_oil_empty), FluidType.OIL, 250));

		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 1), new ItemStack(ModItems.tank_waste, 1, 0), FluidType.WATZ, 8000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 2), new ItemStack(ModItems.tank_waste, 1, 1), FluidType.WATZ, 8000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 3), new ItemStack(ModItems.tank_waste, 1, 2), FluidType.WATZ, 8000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 4), new ItemStack(ModItems.tank_waste, 1, 3), FluidType.WATZ, 8000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 5), new ItemStack(ModItems.tank_waste, 1, 4), FluidType.WATZ, 8000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 6), new ItemStack(ModItems.tank_waste, 1, 5), FluidType.WATZ, 8000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 7), new ItemStack(ModItems.tank_waste, 1, 6), FluidType.WATZ, 8000));
		FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.tank_waste, 1, 8), new ItemStack(ModItems.tank_waste, 1, 7), FluidType.WATZ, 8000));
		
		for(int i = 1; i < FluidType.values().length; i++) {
			FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.fluid_tank_full, 1, i), new ItemStack(ModItems.fluid_tank_empty), FluidType.getEnum(i), 1000));
			FluidContainerRegistry.instance.registerContainer(new FluidContainer(new ItemStack(ModItems.fluid_barrel_full, 1, i), new ItemStack(ModItems.fluid_barrel_empty), FluidType.getEnum(i), 16000));
		}

		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_helmet, 0.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_plate, 0.4F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_legs, 0.3F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_boots, 0.1F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_helmet_red, 0.3F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_plate_red, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_legs_red, 0.45F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_boots_red, 0.15F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_helmet_grey, 0.4F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_plate_grey, 0.8F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_legs_grey, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_boots_grey, 0.2F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.t45_helmet, 0.4F);
		HazmatRegistry.instance.registerHazmat(ModItems.t45_plate, 0.8F);
		HazmatRegistry.instance.registerHazmat(ModItems.t45_legs, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.t45_boots, 0.2F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_helmet, 0.6F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_plate, 1.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_legs, 0.9F);
		HazmatRegistry.instance.registerHazmat(ModItems.hazmat_paa_boots, 0.3F);

		HazmatRegistry.instance.registerHazmat(ModItems.jackt, 0.3F);
		HazmatRegistry.instance.registerHazmat(ModItems.jackt2, 0.3F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.gas_mask, 0.15F);
		HazmatRegistry.instance.registerHazmat(ModItems.gas_mask_m65, 0.175F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.steel_helmet, 0.04F);
		HazmatRegistry.instance.registerHazmat(ModItems.steel_plate, 0.08F);
		HazmatRegistry.instance.registerHazmat(ModItems.steel_legs, 0.06F);
		HazmatRegistry.instance.registerHazmat(ModItems.steel_boots, 0.02F);
		
		HazmatRegistry.instance.registerHazmat(Items.iron_helmet, 0.04F);
		HazmatRegistry.instance.registerHazmat(Items.iron_chestplate, 0.08F);
		HazmatRegistry.instance.registerHazmat(Items.iron_leggings, 0.06F);
		HazmatRegistry.instance.registerHazmat(Items.iron_boots, 0.02F);
		
		HazmatRegistry.instance.registerHazmat(Items.golden_helmet, 0.04F);
		HazmatRegistry.instance.registerHazmat(Items.golden_chestplate, 0.08F);
		HazmatRegistry.instance.registerHazmat(Items.golden_leggings, 0.06F);
		HazmatRegistry.instance.registerHazmat(Items.golden_boots, 0.02F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.alloy_helmet, 0.08F);
		HazmatRegistry.instance.registerHazmat(ModItems.alloy_plate, 0.16F);
		HazmatRegistry.instance.registerHazmat(ModItems.alloy_legs, 0.12F);
		HazmatRegistry.instance.registerHazmat(ModItems.alloy_boots, 0.04F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_helmet, 1.2F);
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_plate, 2.4F);
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_legs, 1.8F);
		HazmatRegistry.instance.registerHazmat(ModItems.schrabidium_boots, 0.6F);
		
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_helmet, 6F);
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_plate, 12F);
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_legs, 9F);
		HazmatRegistry.instance.registerHazmat(ModItems.euphemium_boots, 3F);

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
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		if(logger == null)
			logger = event.getModLog();
		
		FMLCommonHandler.instance().bus().register(new ModEventHandler());
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new ModEventHandler());
		MinecraftForge.ORE_GEN_BUS.register(new ModEventHandler());
		PacketDispatcher.registerPackets();

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

        final String CATEGORY_GENERAL = "01_general";
        enableDebugMode = config.get(CATEGORY_GENERAL, "1.00_enableDebugMode", false).getBoolean(false);
        enableMycelium = config.get(CATEGORY_GENERAL, "1.01_enableMyceliumSpread", false).getBoolean(false);
        enablePlutoniumOre = config.get(CATEGORY_GENERAL, "1.02_enablePlutoniumNetherOre", false).getBoolean(false);
        enableDungeons = config.get(CATEGORY_GENERAL, "1.03_enableDungeonSpawn", true).getBoolean(true);
        enableMDOres = config.get(CATEGORY_GENERAL, "1.04_enableOresInModdedDimensions", true).getBoolean(true);
        enableMines = config.get(CATEGORY_GENERAL, "1.05_enableLandmineSpawn", true).getBoolean(true);
        enableRad = config.get(CATEGORY_GENERAL, "1.06_enableRadHotspotSpawn", true).getBoolean(true);
        enableNITAN = config.get(CATEGORY_GENERAL, "1.07_enableNITANChestSpawn", true).getBoolean(true);
        enableNukeClouds = config.get(CATEGORY_GENERAL, "1.08_enableMushroomClouds", true).getBoolean(true);
        enableAutoCleanup = config.get(CATEGORY_GENERAL, "1.09_enableAutomaticRadCleanup", false).getBoolean(false);
        enableMeteorStrikes = config.get(CATEGORY_GENERAL, "1.10_enableMeteorStrikes", true).getBoolean(true);
        enableMeteorShowers = config.get(CATEGORY_GENERAL, "1.11_enableMeteorShowers", true).getBoolean(true);
        enableMeteorTails = config.get(CATEGORY_GENERAL, "1.12_enableMeteorTails", true).getBoolean(true);
        enableSpecialMeteors = config.get(CATEGORY_GENERAL, "1.13_enableSpecialMeteors", false).getBoolean(false);
        enableBomberShortMode = config.get(CATEGORY_GENERAL, "1.14_enableBomberShortMode", false).getBoolean(false);
        enableVaults = config.get(CATEGORY_GENERAL, "1.15_enableVaultSpawn", true).getBoolean(true);
        enableRads = config.get(CATEGORY_GENERAL, "1.16_enableNewRadiation", true).getBoolean(true);
        enableCataclysm = config.get(CATEGORY_GENERAL, "1.17_enableCataclysm", false).getBoolean(false);
        enableExtendedLogging = config.get(CATEGORY_GENERAL, "1.18_enableExtendedLogging", false).getBoolean(false);
        enableHardcoreTaint = config.get(CATEGORY_GENERAL, "1.19_enableHardcoreTaint", false).getBoolean(false);
        enableGuns = config.get(CATEGORY_GENERAL, "1.20_enableGuns", true).getBoolean(true);

        final String CATEGORY_OREGEN = "02_ores";
        Property PuraniumSpawn = config.get(CATEGORY_OREGEN, "2.00_uraniumSpawnrate", 6);
        PuraniumSpawn.comment = "Ammount of uranium ore veins per chunk";
        uraniumSpawn = PuraniumSpawn.getInt();
        Property PtitaniumSpawn = config.get(CATEGORY_OREGEN, "2.01_titaniumSpawnrate", 8);
        PtitaniumSpawn.comment = "Ammount of titanium ore veins per chunk";
        titaniumSpawn = PtitaniumSpawn.getInt();
        Property PsulfurSpawn = config.get(CATEGORY_OREGEN, "2.02_sulfurSpawnrate", 5);
        PsulfurSpawn.comment = "Ammount of sulfur ore veins per chunk";
        sulfurSpawn = PsulfurSpawn.getInt();
        Property PaluminiumSpawn = config.get(CATEGORY_OREGEN, "2.03_aluminiumSpawnrate", 7);
        PaluminiumSpawn.comment = "Ammount of aluminium ore veins per chunk";
        aluminiumSpawn = PaluminiumSpawn.getInt();
        Property PcopperSpawn = config.get(CATEGORY_OREGEN, "2.04_copperSpawnrate", 12);
        PcopperSpawn.comment = "Ammount of copper ore veins per chunk";
        copperSpawn = PcopperSpawn.getInt();
        Property PFluoriteSpawn = config.get(CATEGORY_OREGEN, "2.05_fluoriteSpawnrate", 6);
        PFluoriteSpawn.comment = "Ammount of fluorite ore veins per chunk";
        fluoriteSpawn = PFluoriteSpawn.getInt();
        Property PNiterSpawn = config.get(CATEGORY_OREGEN, "2.06_niterSpawnrate", 6);
        PNiterSpawn.comment = "Ammount of niter ore veins per chunk";
        niterSpawn = PNiterSpawn.getInt();
        Property PtungstenSpawn = config.get(CATEGORY_OREGEN, "2.07_tungstenSpawnrate", 10);
        PtungstenSpawn.comment = "Ammount of tungsten ore veins per chunk";
        tungstenSpawn = PtungstenSpawn.getInt();
        Property PleadSpawn = config.get(CATEGORY_OREGEN, "2.08_leadSpawnrate", 6);
        PleadSpawn.comment = "Ammount of lead ore veins per chunk";
        leadSpawn = PleadSpawn.getInt();
        Property PberylliumSpawn = config.get(CATEGORY_OREGEN, "2.09_berylliumSpawnrate", 6);
        PberylliumSpawn.comment = "Ammount of beryllium ore veins per chunk";
        berylliumSpawn = PberylliumSpawn.getInt();
        Property PthoriumSpawn = config.get(CATEGORY_OREGEN, "2.10_thoriumSpawnrate", 7);
        PthoriumSpawn.comment = "Ammount of thorium ore veins per chunk";
        thoriumSpawn = PthoriumSpawn.getInt();
        Property ligniteSpawnP = config.get(CATEGORY_OREGEN, "2.11_ligniteSpawnrate", 2);
        ligniteSpawnP.comment = "Ammount of lignite ore veins per chunk";
        ligniteSpawn = ligniteSpawnP.getInt();
        
        final String CATEGORY_NUKES = "03_nukes";
        Property propGadget = config.get(CATEGORY_NUKES, "3.00_gadgetRadius", 150);
        propGadget.comment = "Radius of the Gadget";
        gadgetRadius = propGadget.getInt();
        Property propBoy = config.get(CATEGORY_NUKES, "3.01_boyRadius", 120);
        propBoy.comment = "Radius of Little Boy";
        boyRadius = propBoy.getInt();
        Property propMan = config.get(CATEGORY_NUKES, "3.02_manRadius", 175);
        propMan.comment = "Radius of Fat Man";
        manRadius = propMan.getInt();
        Property propMike = config.get(CATEGORY_NUKES, "3.03_mikeRadius", 250);
        propMike.comment = "Radius of Ivy Mike";
        mikeRadius = propMike.getInt();
        Property propTsar = config.get(CATEGORY_NUKES, "3.04_tsarRadius", 500);
        propTsar.comment = "Radius of the Tsar Bomba";
        tsarRadius = propTsar.getInt();
        Property propPrototype = config.get(CATEGORY_NUKES, "3.05_prototypeRadius", 150);
        propPrototype.comment = "Radius of the Prototype";
        prototypeRadius = propPrototype.getInt();
        Property propFleija = config.get(CATEGORY_NUKES, "3.06_fleijaRadius", 50);
        propFleija.comment = "Radius of F.L.E.I.J.A.";
        fleijaRadius = propFleija.getInt();
        Property propMissile = config.get(CATEGORY_NUKES, "3.07_missileRadius", 100);
        propMissile.comment = "Radius of the nuclear missile";
        missileRadius = propMissile.getInt();
        Property propMirv = config.get(CATEGORY_NUKES, "3.08_mirvRadius", 100);
        propMirv.comment = "Radius of a MIRV";
        mirvRadius = propMirv.getInt();
        Property propFatman = config.get(CATEGORY_NUKES, "3.09_fatmanRadius", 35);
        propFatman.comment = "Radius of the Fatman Launcher";
        fatmanRadius = propFatman.getInt();
        Property propNuka = config.get(CATEGORY_NUKES, "3.10_nukaRadius", 25);
        propNuka.comment = "Radius of the nuka grenade";
        nukaRadius = propNuka.getInt();
        Property propASchrab = config.get(CATEGORY_NUKES, "3.11_aSchrabRadius", 20);
        propASchrab.comment = "Radius of dropped anti schrabidium";
        aSchrabRadius = propASchrab.getInt();
        Property propSolinium = config.get(CATEGORY_NUKES, "3.12_soliniumRadius", 75);
        propSolinium.comment = "Radius of the blue rinse";
        soliniumRadius = propSolinium.getInt();
        Property propN2 = config.get(CATEGORY_NUKES, "3.13_n2Radius", 130);
        propN2.comment = "Radius of the N2 mine";
        n2Radius = propN2.getInt();

        final String CATEGORY_DUNGEON = "04_dungeons";
        Property propRadio = config.get(CATEGORY_DUNGEON, "4.00_radioSpawn", 500);
        propRadio.comment = "Spawn radio station on every nTH chunk";
        radioStructure = propRadio.getInt();
        Property propAntenna = config.get(CATEGORY_DUNGEON, "4.01_antennaSpawn", 250);
        propAntenna.comment = "Spawn antenna on every nTH chunk";
        antennaStructure = propAntenna.getInt();
        Property propAtom = config.get(CATEGORY_DUNGEON, "4.02_atomSpawn", 500);
        propAtom.comment = "Spawn power plant on every nTH chunk";
        atomStructure = propAtom.getInt();
        Property propVertibird = config.get(CATEGORY_DUNGEON, "4.03_vertibirdSpawn", 500);
        propVertibird.comment = "Spawn vertibird on every nTH chunk";
        vertibirdStructure = propVertibird.getInt();
        Property propDungeon = config.get(CATEGORY_DUNGEON, "4.04_dungeonSpawn", 64);
        propDungeon.comment = "Spawn library dungeon on every nTH chunk";
        dungeonStructure = propDungeon.getInt();
        Property propRelay = config.get(CATEGORY_DUNGEON, "4.05_relaySpawn", 500);
        propRelay.comment = "Spawn relay on every nTH chunk";
        relayStructure = propRelay.getInt();
        Property propSatellite = config.get(CATEGORY_DUNGEON, "4.06_satelliteSpawn", 500);
        propSatellite.comment = "Spawn satellite dish on every nTH chunk";
        satelliteStructure = propSatellite.getInt();
        Property propBunker = config.get(CATEGORY_DUNGEON, "4.07_bunkerSpawn", 1000);
        propBunker.comment = "Spawn bunker on every nTH chunk";
        bunkerStructure = propBunker.getInt();
        Property propSilo = config.get(CATEGORY_DUNGEON, "4.08_siloSpawn", 1000);
        propSilo.comment = "Spawn missile silo on every nTH chunk";
        siloStructure = propSilo.getInt();
        Property propFactory = config.get(CATEGORY_DUNGEON, "4.09_factorySpawn", 1000);
        propFactory.comment = "Spawn factory on every nTH chunk";
        factoryStructure = propFactory.getInt();
        Property propDud = config.get(CATEGORY_DUNGEON, "4.10_dudSpawn", 500);
        propDud.comment = "Spawn dud on every nTH chunk";
        dudStructure = propDud.getInt();
        Property propSpaceship = config.get(CATEGORY_DUNGEON, "4.11_spaceshipSpawn", 1000);
        propSpaceship.comment = "Spawn spaceship on every nTH chunk";
        spaceshipStructure = propSpaceship.getInt();
        Property propBarrel = config.get(CATEGORY_DUNGEON, "4.12_barrelSpawn", 5000);
        propBarrel.comment = "Spawn waste tank on every nTH chunk";
        barrelStructure = propBarrel.getInt();
        Property propBroadcaster = config.get(CATEGORY_DUNGEON, "4.13_broadcasterSpawn", 5000);
        propBroadcaster.comment = "Spawn corrupt broadcaster on every nTH chunk";
        broadcaster = propBroadcaster.getInt();
        Property propMines = config.get(CATEGORY_DUNGEON, "4.14_landmineSpawn", 64);
        propMines.comment = "Spawn AP landmine on every nTH chunk";
        minefreq = propMines.getInt();
        Property propRad = config.get(CATEGORY_DUNGEON, "4.15_radHotsoptSpawn", 5000);
        propRad.comment = "Spawn radiation hotspot on every nTH chunk";
        radfreq = propRad.getInt();
        Property propVault = config.get(CATEGORY_DUNGEON, "4.16_vaultSpawn", 2500);
        propVault.comment = "Spawn locked safe on every nTH chunk";
        vaultfreq = propVault.getInt();
        Property pGW = config.get(CATEGORY_DUNGEON, "4.17_geyserWaterSpawn", 3000);
        pGW.comment = "Spawn water geyser on every nTH chunk";
        geyserWater = pGW.getInt();
        Property pGC = config.get(CATEGORY_DUNGEON, "4.18_geyserChlorineSpawn", 3000);
        pGC.comment = "Spawn poison geyser on every nTH chunk";
        geyserChlorine = pGC.getInt();
        Property pGV = config.get(CATEGORY_DUNGEON, "4.19_geyserVaporSpawn", 500);
        pGV.comment = "Spawn vapor geyser on every nTH chunk";
        geyserVapor = pGV.getInt();

        final String CATEGORY_METEOR = "05_meteors";
        Property propMeteorStrikeChance = config.get(CATEGORY_METEOR, "5.00_meteorStrikeChance", 20 * 60 * 180);
        propMeteorStrikeChance.comment = "The probability of a meteor spawning (an average of once every nTH ticks)";
        meteorStrikeChance = propMeteorStrikeChance.getInt();
        Property propMeteorShowerChance = config.get(CATEGORY_METEOR, "5.01_meteorShowerChance", 20 * 60 * 5);
        propMeteorShowerChance.comment = "The probability of a meteor spawning during meteor shower (an average of once every nTH ticks)";
        meteorShowerChance = propMeteorShowerChance.getInt();
        Property propMeteorShowerDuration = config.get(CATEGORY_METEOR, "5.02_meteorShowerDuration", 6000);
        propMeteorShowerDuration.comment = "Max duration of meteor shower in ticks";
        meteorShowerDuration = propMeteorShowerDuration.getInt();

        final String CATEGORY_NUKE = "06_explosions";
        Property propLimitExplosionLifespan = config.get(CATEGORY_NUKE, "6.00_limitExplosionLifespan", 0);
        propLimitExplosionLifespan.comment = "How long an explosion can be unloaded until it dies in seconds. Based of system time. 0 disables the effect";
        limitExplosionLifespan = propLimitExplosionLifespan.getInt();
        //explosion speed
        Property propBlastSpeed = config.get(CATEGORY_NUKE, "6.01_blastSpeed", 1024);
        propBlastSpeed.comment = "Base speed of MK3 system (old and schrabidium) detonations (Blocks / tick)";
        blastSpeed = propBlastSpeed.getInt();
        //fallout range
        Property propFalloutRange = config.get(CATEGORY_NUKE, "6.02_blastSpeedNew", 1024);
        propFalloutRange.comment = "Base speed of MK4 system (new) detonations (Blocks / tick)";
        mk4 = propFalloutRange.getInt();
        //fallout speed
        Property falloutRangeProp = config.get(CATEGORY_NUKE, "6.03_falloutRange", 100);
        falloutRangeProp.comment = "Radius of fallout area (base radius * value in percent)";
        falloutRange = falloutRangeProp.getInt();
        //new explosion speed
        Property falloutSpeed = config.get(CATEGORY_NUKE, "6.04_falloutSpeed", 256);
        falloutSpeed.comment = "Blocks processed per tick by the fallout rain";
        fSpeed = falloutSpeed.getInt();
        //afterrain duration
        Property radRain = config.get(CATEGORY_NUKE, "6.05_falloutRainDuration", 0);
        radRain.comment = "Duration of the thunderstorm after fallout in ticks (only large explosions)";
        rain = radRain.getInt();
        //afterrain radiation
        Property rainCont = config.get(CATEGORY_NUKE, "6.06_falloutRainRadiation", 0);
        rainCont.comment = "Radiation in 100th RADs created by fallout rain";
        cont = rainCont.getInt();
        //fog threshold
        Property fogThresh = config.get(CATEGORY_NUKE, "6.07_fogThreshold", 100);
        fogThresh.comment = "Radiation in RADs required for fog to spawn";
        fogRad = fogThresh.getInt();
        //fog chance
        Property fogChance = config.get(CATEGORY_NUKE, "6.08_fogChance", 10);
        fogChance.comment = "1:n chance of fog spawning every second";
        fogCh = fogChance.getInt();
        //nether radiation
        Property netherRad = config.get(CATEGORY_NUKE, "6.09_netherRad", 10);
        netherRad.comment = "RAD/s in the nether in hundredths";
        hellRad = netherRad.getInt() * 0.01F;

        final String CATEGORY_MISSILE = "07_missile_machines";
        Property propRadarRange = config.get(CATEGORY_MISSILE, "7.00_radarRange", 1000);
        propRadarRange.comment = "Range of the radar, 50 will result in 100x100 block area covered";
        radarRange = propRadarRange.getInt();
        Property propRadarBuffer = config.get(CATEGORY_MISSILE, "7.01_radarBuffer", 30);
        propRadarBuffer.comment = "How high entities have to be above the radar to be detected";
        radarBuffer = propRadarBuffer.getInt();
        Property propRadarAltitude = config.get(CATEGORY_MISSILE, "7.02_radarAltitude", 55);
        propRadarAltitude.comment = "Y height required for the radar to work";
        radarAltitude = propRadarAltitude.getInt();
        Property propCiwsHitrate = config.get(CATEGORY_MISSILE, "7.03_ciwsAccuracy", 50);
        propCiwsHitrate.comment = "Additional modifier for CIWS accuracy";
        ciwsHitrate = propRadarAltitude.getInt();

        final String CATEGORY_POTION = "08_potion_effects";
        Property propTaintID = config.get(CATEGORY_POTION, "8.00_taintPotionID", 62);
        propTaintID.comment = "What potion ID the taint effect will have";
        taintID = propTaintID.getInt();
        Property propRadiationID = config.get(CATEGORY_POTION, "8.01_radiationPotionID", 63);
        propRadiationID.comment = "What potion ID the radiation effect will have";
        radiationID = propRadiationID.getInt();
        Property propBangID = config.get(CATEGORY_POTION, "8.02_bangPotionID", 64);
        propBangID.comment = "What potion ID the B93 timebomb effect will have";
        bangID = propBangID.getInt();
        Property propMutationID = config.get(CATEGORY_POTION, "8.03_mutationPotionID", 65);
        propMutationID.comment = "What potion ID the taint mutation effect will have";
        mutationID = propMutationID.getInt();
        Property propRadxID = config.get(CATEGORY_POTION, "8.04_radxPotionID", 66);
        propRadxID.comment = "What potion ID the Rad-X effect will have";
        radxID = propRadxID.getInt();
        Property propLeadID = config.get(CATEGORY_POTION, "8.05_leadPotionID", 67);
        propLeadID.comment = "What potion ID the lead poisoning effect will have";
        leadID = propLeadID.getInt();
        
        config.save();
        
        radioStructure = setDef(radioStructure, 1000);
        antennaStructure = setDef(antennaStructure, 1000);
        atomStructure = setDef(atomStructure, 1000);
        vertibirdStructure = setDef(vertibirdStructure, 1000);
        dungeonStructure = setDef(dungeonStructure, 1000);
        relayStructure = setDef(relayStructure, 1000);
        satelliteStructure = setDef(satelliteStructure, 1000);
        bunkerStructure = setDef(bunkerStructure, 1000);
        siloStructure = setDef(siloStructure, 1000);
        factoryStructure = setDef(factoryStructure, 1000);
        dudStructure = setDef(dudStructure, 1000);
        spaceshipStructure = setDef(spaceshipStructure, 1000);
        barrelStructure = setDef(barrelStructure, 1000);
        geyserWater = setDef(geyserWater, 1000);
        geyserChlorine = setDef(geyserChlorine, 1000);
        geyserVapor = setDef(geyserVapor, 1000);
        broadcaster = setDef(broadcaster, 1000);
        minefreq = setDef(minefreq, 1000);
        radfreq = setDef(radfreq, 1000);
        vaultfreq = setDef(vaultfreq, 1000);
        meteorStrikeChance = setDef(meteorStrikeChance, 1000);
        meteorShowerChance = setDef(meteorShowerChance, 1000);
        fogCh = setDef(fogCh, 20);
	}
	
	private static int setDef(int value, int def) {
		
		if(value <= 0) {
			logger.error("Fatal error config: Randomizer value has been set to zero, despite bound having to be positive integer!");
			logger.error(String.format("Errored value will default back to %d, PLEASE REVIEW CONFIGURATION DESCRIPTION BEFORE MEDDLING WITH VALUES!", def));
			return def;
		}
		
		return value;
	}
}
