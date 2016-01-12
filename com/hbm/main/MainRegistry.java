package com.hbm.main;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.TileEntityBombMulti;
import com.hbm.blocks.TileEntityDecoBlock;
import com.hbm.blocks.TileEntityDecoPoleSatelliteReceiver;
import com.hbm.blocks.TileEntityDecoPoleTop;
import com.hbm.blocks.TileEntityDecoSteelPoles;
import com.hbm.blocks.TileEntityDecoTapeRecorder;
import com.hbm.blocks.TileEntityDiFurnace;
import com.hbm.blocks.TileEntityLaunchPad;
import com.hbm.blocks.TileEntityMachineBattery;
import com.hbm.blocks.TileEntityMachineCoal;
import com.hbm.blocks.TileEntityMachineDeuterium;
import com.hbm.blocks.TileEntityMachineElectricFurnace;
import com.hbm.blocks.TileEntityMachineGenerator;
import com.hbm.blocks.TileEntityMachineCentrifuge;
import com.hbm.blocks.TileEntityMachinePuF6Tank;
import com.hbm.blocks.TileEntityMachineReactor;
import com.hbm.blocks.TileEntityMachineUF6Tank;
import com.hbm.blocks.TileEntityNukeBoy;
import com.hbm.blocks.TileEntityNukeFleija;
import com.hbm.blocks.TileEntityNukeFurnace;
import com.hbm.blocks.TileEntityNukeGadget;
import com.hbm.blocks.TileEntityNukeMan;
import com.hbm.blocks.TileEntityNukePrototype;
import com.hbm.blocks.TileEntityNukeTsar;
import com.hbm.blocks.TileEntityObjTester;
import com.hbm.blocks.TileEntityRedBarrel;
import com.hbm.blocks.TileEntityRotationTester;
import com.hbm.blocks.TileEntityRtgFurnace;
import com.hbm.blocks.TileEntityTestBombAdvanced;
import com.hbm.blocks.TileEntityTestContainer;
import com.hbm.blocks.TileEntityTestNuke;
import com.hbm.blocks.TileEntityTestRender;
import com.hbm.blocks.TileEntityWireCoated;
import com.hbm.blocks.TileEntityYellowBarrel;
import com.hbm.creativetabs.BlockTab;
import com.hbm.creativetabs.NukeTab;
import com.hbm.creativetabs.PartsTab;
import com.hbm.creativetabs.TestTab;
import com.hbm.entity.EntityBullet;
import com.hbm.entity.EntityGrenadeCluster;
import com.hbm.entity.EntityGrenadeElectric;
import com.hbm.entity.EntityGrenadeFire;
import com.hbm.entity.EntityGrenadeFlare;
import com.hbm.entity.EntityGrenadeFrag;
import com.hbm.entity.EntityGrenadeGas;
import com.hbm.entity.EntityGrenadeGeneric;
import com.hbm.entity.EntityGrenadeNuke;
import com.hbm.entity.EntityGrenadePoison;
import com.hbm.entity.EntityGrenadeSchrabidium;
import com.hbm.entity.EntityGrenadeStrong;
import com.hbm.entity.EntityMirv;
import com.hbm.entity.EntityMissileAntiBallistic;
import com.hbm.entity.EntityMissileBunkerBuster;
import com.hbm.entity.EntityMissileBurst;
import com.hbm.entity.EntityMissileBusterStrong;
import com.hbm.entity.EntityMissileCluster;
import com.hbm.entity.EntityMissileClusterStrong;
import com.hbm.entity.EntityMissileDrill;
import com.hbm.entity.EntityMissileEndo;
import com.hbm.entity.EntityMissileExo;
import com.hbm.entity.EntityMissileGeneric;
import com.hbm.entity.EntityMissileIncendiary;
import com.hbm.entity.EntityMissileIncendiaryStrong;
import com.hbm.entity.EntityMissileInferno;
import com.hbm.entity.EntityMissileMirv;
import com.hbm.entity.EntityMissileNuclear;
import com.hbm.entity.EntityMissileRain;
import com.hbm.entity.EntityMissileStrong;
import com.hbm.entity.EntityNuclearCreeper;
import com.hbm.entity.EntityNukeCloudSmall;
import com.hbm.entity.EntityNukeExplosion;
import com.hbm.entity.EntityNukeExplosionAdvanced;
import com.hbm.entity.EntityRocket;
import com.hbm.entity.EntitySchrab;
import com.hbm.entity.EntityTestMissile;
import com.hbm.items.ModItems;
import com.hbm.lib.HbmWorld;
import com.hbm.lib.RefStrings;
import com.hbm.particles.NukeSmokeFX;

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

	/*public static Achievement achievementGetTitanium;
	public static Achievement achievementCraftDiFurnace;
	public static Achievement achievementGetSteel;
	public static Achievement achievementCraftCentrifuge;
	public static Achievement achievementGetUranium;
	public static Achievement achievementCraftReactor;
	public static Achievement achievementGetPlutonium;
	public static Achievement achievementGetSchrabidium;
	public static Achievement achievementGetEuphemium;

	public static Achievement achievementGetRedCopper;
	public static Achievement achievementCraftWireCoated;
	public static Achievement achievementCraftCoal;
	public static Achievement achievementCraftGenerator;
	public static Achievement achievementCraftElectricFurnace;
	public static Achievement achievementCraftDeuterium;

	public static Achievement achievementCraftCircuit;
	public static Achievement achievementCraftBattery;
	public static Achievement achievementCraftEnergyStorage;

	public static Achievement achievementCraftNukeFurnace;
	public static Achievement achievementCraftUraniumRod;
	public static Achievement achievementCraftUraniumDualRod;
	public static Achievement achievementCraftUraniumQuadRod;
	public static Achievement achievementCraftUraniumFuel;

	public static Achievement achievementCraftPlutoniumRod;
	public static Achievement achievementCraftRtg;
	public static Achievement achievementCraftRtgFurnace;
	public static Achievement achievementCraftPlutoniumFuel;
	public static Achievement achievementCraftMoxFuel;

	public static Achievement achievementCraftSchrabidiumRod;
	public static Achievement achievementCraftSchrabidiumFuel;
	public static Achievement achievementCraftSchrabidiumApple;
	public static Achievement achievementCraftSchrabidiumCircuit;

	public static Achievement achievementCraftWatch;

	public static Achievement achievementGetNeutronReflector;
	
	public static Achievement achievementCraftRevolver;
	public static Achievement achievementCraftGrenade;
	public static Achievement achievementCraftGrenadeStrong;
	public static Achievement achievementCraftGrenadeCluster;
	public static Achievement achievementCraftGrenadeFlare;
	public static Achievement achievementCraftGrenadeSchrbidium;

	public static Achievement achievementCraftLittleBoy;

	public static Achievement achievementCraftTheGadget;
	public static Achievement achievementCraftFatMan;
	public static Achievement achievementCraftIvyMike;
	public static Achievement achievementCraftTsarBomba;

	public static Achievement achievementCraftFleija;

	public static Achievement achievementCraftIgniter;
	public static Achievement achievementCraftSas3;
	public static Achievement achievementCraftPrototype;*/
	
	@SidedProxy(clientSide = RefStrings.CLIENTSIDE, serverSide = RefStrings.SERVERSIDE)
	public static ServerProxy proxy;
	
	@Metadata
	public static ModMetadata meta;
	
	//Tool Materials
	public static ToolMaterial enumToolMaterialSchrabidium = EnumHelper.addToolMaterial("SCHRABIDIUM", 3, 10000, 50.0F, 20.0F, 200);
	
	//Armor Materials
	public static ArmorMaterial enumArmorMaterialEmerald = EnumHelper.addArmorMaterial("TEST", 2500, new int[] {3, 8, 6, 3}, 30);
	public static ArmorMaterial enumArmorMaterialSchrabidium = EnumHelper.addArmorMaterial("SCHRABIDIUM", 100, new int[] {3, 8, 6, 3}, 50);
	public static ArmorMaterial enumArmorMaterialEuphemium = EnumHelper.addArmorMaterial("EUPHEMIUM", 2147483647, new int[] {3, 8, 6, 3}, 100);
	public static ArmorMaterial enumArmorMaterialHazmat = EnumHelper.addArmorMaterial("HAZMAT", 60, new int[] {2, 5, 4, 1}, 5);
	public static ArmorMaterial enumArmorMaterialT45 = EnumHelper.addArmorMaterial("T45", 1000, new int[] {2, 5, 4, 1}, 0);
	
	//Creative Tabs
	public static CreativeTabs tabTest = new TestTab(CreativeTabs.getNextID(), "tabTest");
	public static CreativeTabs tabParts = new PartsTab(CreativeTabs.getNextID(), "tabParts");
	public static CreativeTabs tabBlock = new BlockTab(CreativeTabs.getNextID(), "tabBlocks");
	public static CreativeTabs tabNuke = new NukeTab(CreativeTabs.getNextID(), "tabNuke");
	
	public static boolean enableDebugMode = true;
	
	@EventHandler
	public void PreLoad(FMLPreInitializationEvent PreEvent)
	{		
		ModBlocks.mainRegistry();
		ModItems.mainRegistry();
		CraftingManager.mainRegistry();
		proxy.registerRenderInfo();
		HbmWorld.mainRegistry();

		enumArmorMaterialSchrabidium.customCraftingMaterial = ModItems.ingot_schrabidium;
		enumArmorMaterialT45.customCraftingMaterial = ModItems.plate_titanium;
		
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

	    EntityRegistry.registerModEntity(EntityRocket.class, "entity_rocket", 0, this, 250, 1, true);
	    EntityRegistry.registerModEntity(EntityNukeExplosion.class, "entity_nuke_explosion", 1, this, 250, 1, true);
	    EntityRegistry.registerModEntity(EntityNukeExplosionAdvanced.class, "entity_nuke_explosion_advanced", 2, this, 250, 1, true);
	    EntityRegistry.registerModEntity(EntityGrenadeGeneric.class, "entity_grenade_generic", 3, this, 250, 1, true);
	    EntityRegistry.registerModEntity(EntityGrenadeStrong.class, "entity_grenade_strong", 4, this, 250, 1, true);
	    EntityRegistry.registerModEntity(EntityGrenadeFrag.class, "entity_grenade_frag", 5, this, 250, 1, true);
	    EntityRegistry.registerModEntity(EntityGrenadeFire.class, "entity_grenade_fire", 6, this, 250, 1, true);
	    EntityRegistry.registerModEntity(EntityGrenadeCluster.class, "entity_grenade_cluster", 7, this, 250, 1, true);
	    EntityRegistry.registerModEntity(EntityTestMissile.class, "entity_test_missile", 8, this, 1000, 1, true);
	    EntityRegistry.registerModEntity(EntityNukeCloudSmall.class, "entity_nuke_cloud_small", 9, this, 1000, 1, true);
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
	    EntityRegistry.registerModEntity(EntityMirv.class, "entity_mirvlet", 35, this, 1000, 1, true);
	    
	    EntityRegistry.registerGlobalEntityID(EntityNuclearCreeper.class, "entity_mob_nuclear_creeper", EntityRegistry.findGlobalUniqueEntityId(), 0x204131, 0x75CE00);
	}

	@EventHandler
	public static void load(FMLInitializationEvent event)
	{
		OreDictionary.registerOre("ingotUranium", ModItems.ingot_uranium);
		OreDictionary.registerOre("ingotPlutonium", ModItems.ingot_pu239);
		OreDictionary.registerOre("ingotTitanium", ModItems.ingot_titanium);
		OreDictionary.registerOre("dustSulfur", ModItems.sulfur);
		OreDictionary.registerOre("dustNiter", ModItems.niter);
		OreDictionary.registerOre("dustLead", ModItems.powder_lead);
		OreDictionary.registerOre("dustNeptunium", ModItems.powder_neptunium);
		OreDictionary.registerOre("ingotCopper", ModItems.ingot_copper);
		OreDictionary.registerOre("ingotRedAlloy", ModItems.ingot_red_copper);
		OreDictionary.registerOre("ingotTungsten", ModItems.ingot_tungsten);
		OreDictionary.registerOre("ingotAluminum", ModItems.ingot_aluminium);
		OreDictionary.registerOre("ingotNeptunium", ModItems.ingot_neptunium);
		OreDictionary.registerOre("ingotLead", ModItems.ingot_lead);
		OreDictionary.registerOre("dustFluorite", ModItems.fluorite);
		OreDictionary.registerOre("nuggetUranium", ModItems.nugget_uranium);
		OreDictionary.registerOre("nuggetUranium235", ModItems.nugget_u235);
		OreDictionary.registerOre("nuggetUranium238", ModItems.nugget_u238);
		OreDictionary.registerOre("nuggetPlutonium", ModItems.nugget_plutonium);
		OreDictionary.registerOre("nuggetPlutonium238", ModItems.nugget_pu238);
		OreDictionary.registerOre("nuggetPlutonium239", ModItems.nugget_pu239);
		OreDictionary.registerOre("nuggetPlutonium240", ModItems.nugget_pu240);
		OreDictionary.registerOre("nuggetNeptunium", ModItems.nugget_neptunium);
		OreDictionary.registerOre("plateTitanium", ModItems.plate_titanium);
		OreDictionary.registerOre("plateAluminum", ModItems.plate_aluminium);
		OreDictionary.registerOre("plateDenseLead", ModItems.neutron_reflector);
		OreDictionary.registerOre("ingotSteel", ModItems.ingot_steel);
		OreDictionary.registerOre("plateSteel", ModItems.plate_steel);
		OreDictionary.registerOre("plateLead", ModItems.plate_lead);
		OreDictionary.registerOre("plateCopper", ModItems.plate_copper);

		OreDictionary.registerOre("oreUranium", ModBlocks.ore_uranium);
		OreDictionary.registerOre("oreTitanium", ModBlocks.ore_titanium);
		OreDictionary.registerOre("oreSulfur", ModBlocks.ore_sulfur);
		OreDictionary.registerOre("oreNiter", ModBlocks.ore_niter);
		OreDictionary.registerOre("oreCopper", ModBlocks.ore_copper);
		OreDictionary.registerOre("oreTungsten", ModBlocks.ore_tungsten);
		OreDictionary.registerOre("oreAluminum", ModBlocks.ore_aluminium);
		OreDictionary.registerOre("oreFluorite", ModBlocks.ore_fluorite);
		OreDictionary.registerOre("oreLead", ModBlocks.ore_lead);
		
		/*achievementGetTitanium = new Achievement("achievement.getTitanium", "getTitanium", 0, -8, ModItems.ingot_titanium, (Achievement)null).initIndependentStat().registerStat();
		
		AchievementPage.registerAchievementPage(new AchievementPage("Nuclear Tech", new Achievement[] {
				achievementGetTitanium
		}));*/
	}
	
	@EventHandler
	public static void PostLoad(FMLPostInitializationEvent PostEvent)
	{
		
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(new ModEventHandler());
		MinecraftForge.EVENT_BUS.register(new ModEventHandler());
		MinecraftForge.TERRAIN_GEN_BUS.register(new ModEventHandler());
		MinecraftForge.ORE_GEN_BUS.register(new ModEventHandler());
	}
}
