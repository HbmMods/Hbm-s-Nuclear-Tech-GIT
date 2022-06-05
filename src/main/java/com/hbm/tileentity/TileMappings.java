package com.hbm.tileentity;

import java.util.HashMap;

import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.blocks.generic.BlockEmitter.TileEntityEmitter;
import com.hbm.blocks.generic.BlockLoot.TileEntityLoot;
import com.hbm.blocks.generic.BlockMotherOfAllOres.TileEntityRandomOre;
import com.hbm.blocks.network.CableDiode.TileEntityDiode;
import com.hbm.interfaces.IFluidAcceptor;
import com.hbm.interfaces.IFluidSource;
import com.hbm.tileentity.bomb.*;
import com.hbm.tileentity.conductor.*;
import com.hbm.tileentity.deco.*;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.oil.*;
import com.hbm.tileentity.machine.pile.*;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.tileentity.machine.storage.*;
import com.hbm.tileentity.network.*;
import com.hbm.tileentity.turret.*;
import com.hbm.util.LoggingUtil;

import api.hbm.fluid.IFluidConnector;
import net.minecraft.tileentity.TileEntity;

public class TileMappings {

	public static HashMap<Class<? extends TileEntity>, String[]> map = new HashMap();
	
	public static void writeMappings() {
		put(TileEntityTestBombAdvanced.class, "tilentity_testbombadvanced");
		put(TileEntityDiFurnace.class, "tilentity_diFurnace");
		put(TileEntityTestNuke.class, "tilentity_testnuke");
		put(TileEntityRotationTester.class, "tilentity_rotationtester");
		put(TileEntityTestRender.class, "tilentity_testrenderer");
		put(TileEntityTestContainer.class, "tilentity_testcontainer");
		put(TileEntityObjTester.class, "tilentity_objtester");
		put(TileEntityMachineCentrifuge.class, "tileentity_centrifuge");
		put(TileEntityNukeMan.class, "tileentity_nukeman");
		put(TileEntityMachineUF6Tank.class, "tileentity_uf6_tank");
		put(TileEntityMachinePuF6Tank.class, "tileentity_puf6_tank");
		put(TileEntityMachineReactorBreeding.class, "tileentity_reactor");
		put(TileEntityNukeFurnace.class, "tileentity_nukefurnace");
		put(TileEntityRtgFurnace.class, "tileentity_rtgfurnace");
		put(TileEntityMachineGenerator.class, "tileentity_generator");
		put(TileEntityMachineElectricFurnace.class, "tileentity_electric_furnace");
		put(TileEntityDecoTapeRecorder.class, "tileentity_taperecorder");
		put(TileEntityDecoSteelPoles.class, "tileentity_steelpoles");
		put(TileEntityDecoPoleTop.class, "tileentity_poletop");
		put(TileEntityDecoPoleSatelliteReceiver.class, "tileentity_satellitereceicer");
		put(TileEntityMachineDeuterium.class, "tileentity_deuterium");
		put(TileEntityMachineBattery.class, "tileentity_battery");
		put(TileEntityMachineCoal.class, "tileentity_coal");
		put(TileEntityRedBarrel.class, "tileentity_barrel");
		put(TileEntityYellowBarrel.class, "tileentity_nukebarrel");
		put(TileEntityLaunchPad.class, "tileentity_launch1");
		put(TileEntityDecoBlock.class, "tileentity_deco");
		put(TileEntityDecoBlockAltW.class, "tileentity_deco_w");
		put(TileEntityDecoBlockAltG.class, "tileentity_deco_g");
		put(TileEntityDecoBlockAltF.class, "tileentity_deco_f");
		put(TileEntityCoreTitanium.class, "tileentity_core_titanium");
		put(TileEntityCoreAdvanced.class, "tileentity_core_advanced");
		put(TileEntityFusionMultiblock.class, "tileentity_fusion_multiblock");
		put(TileEntityCrashedBomb.class, "tileentity_crashed_balefire");
		put(TileEntityConverterHeRf.class, "tileentity_converter_herf");
		put(TileEntityConverterRfHe.class, "tileentity_converter_rfhe");
		put(TileEntityMachineSchrabidiumTransmutator.class, "tileentity_schrabidium_transmutator");
		put(TileEntityMachineDiesel.class, "tileentity_diesel_generator");
		put(TileEntityWatzCore.class, "tileentity_watz_multiblock");
		put(TileEntityMachineShredder.class, "tileentity_machine_shredder");
		put(TileEntityMachineCMBFactory.class, "tileentity_machine_cmb");
		put(TileEntityFWatzCore.class, "tileentity_fwatz_multiblock");
		put(TileEntityMachineTeleporter.class, "tileentity_teleblock");
		put(TileEntityHatch.class, "tileentity_seal_lid");
		put(TileEntityMachineIGenerator.class, "tileentity_igenerator");
		put(TileEntityDummy.class, "tileentity_dummy");
		put(TileEntityMachineCyclotron.class, "tileentity_cyclotron");
		put(TileEntityOilDuct.class, "tileentity_oil_duct");
		put(TileEntityOilDuctSolid.class, "tileentity_oil_duct_solid");
		put(TileEntityGasDuct.class, "tileentity_gas_duct");
		put(TileEntityGasDuctSolid.class, "tileentity_gas_duct_solid");
		put(TileEntityMachineRTG.class, "tileentity_machine_rtg");
		put(TileEntityStructureMarker.class, "tileentity_structure_marker");
		put(TileEntityMachineMiningDrill.class, "tileentity_mining_drill");
		put(TileEntityFluidDuctSimple.class, "tileentity_universal_duct_simple");
		put(TileEntityFluidDuct.class, "tileentity_universal_duct");
		put(TileEntityMachineFluidTank.class, "tileentity_fluid_tank");
		put(TileEntityTurretHeavy.class, "tileentity_turret_heavy");
		put(TileEntityTurretRocket.class, "tileentity_turret_rocket");
		put(TileEntityTurretLight.class, "tileentity_turret_light");
		put(TileEntityTurretFlamer.class, "tileentity_turret_flamer");
		put(TileEntityTurretTau.class, "tileentity_turret_tau");
		put(TileEntityMachineTurbofan.class, "tileentity_machine_turbofan");
		put(TileEntityCrateIron.class, "tileentity_crate_iron");
		put(TileEntityCrateSteel.class, "tileentity_crate_steel");
		put(TileEntityCrateDesh.class, "tileentity_crate_desh");
		put(TileEntityMassStorage.class, "tileentity_mass_storage");
		put(TileEntityMachinePress.class, "tileentity_press");
		put(TileEntityAMSBase.class, "tileentity_ams_base");
		put(TileEntityAMSEmitter.class, "tileentity_ams_emitter");
		put(TileEntityAMSLimiter.class, "tileentity_ams_limiter");
		put(TileEntityMachineSiren.class, "tileentity_siren");
		put(TileEntityMachineSPP.class, "tileentity_spp");
		put(TileEntityTurretSpitfire.class, "tileentity_turret_spitfire");
		put(TileEntityMachineRadGen.class, "tileentity_radgen");
		put(TileEntityMachineTransformer.class, "tileentity_transformer");
		put(TileEntityTurretCIWS.class, "tileentity_turret_cwis");
		put(TileEntityMachineRadar.class, "tileentity_radar");
		put(TileEntityBroadcaster.class, "tileentity_pink_cloud_broadcaster");
		put(TileEntityTurretCheapo.class, "tileentity_turret_cheapo");
		put(TileEntityCelPrime.class, "tileentity_cel_prime");
		put(TileEntityCelPrimeTerminal.class, "tileentity_cel_prime_access");
		put(TileEntityCelPrimeBattery.class, "tileentity_cel_prime_energy");
		put(TileEntityCelPrimePort.class, "tileentity_cel_prime_connector");
		put(TileEntityCelPrimeTanks.class, "tileentity_cel_prime_storage");
		put(TileEntityMachineSeleniumEngine.class, "tileentity_selenium_engine");
		put(TileEntityMachineSatLinker.class, "tileentity_satlinker");
		put(TileEntityReactorResearch.class, "tileentity_small_reactor");
		put(TileEntityVaultDoor.class, "tileentity_vault_door");
		put(TileEntityRadiobox.class, "tileentity_radio_broadcaster");
		put(TileEntityRadioRec.class, "tileentity_radio_receiver");
		put(TileEntityVent.class, "tileentity_vent");
		put(TileEntityLandmine.class, "tileentity_landmine");
		put(TileEntityBomber.class, "tileentity_bomber");
		put(TileEntityMachineTeleLinker.class, "tileentity_telemetry_linker");
		put(TileEntityMachineKeyForge.class, "tileentity_key_forge");
		put(TileEntitySellafield.class, "tileentity_sellafield_core");
		put(TileEntityNukeN45.class, "tileentity_n45");
		put(TileEntityBlastDoor.class, "tileentity_blast_door");
		put(TileEntitySafe.class, "tileentity_safe");
		put(TileEntityMachineGasCent.class, "tileentity_gas_centrifuge");
		put(TileEntityMachineBoiler.class, "tileentity_boiler");
		put(TileEntityMachineBoilerElectric.class, "tileentity_electric_boiler");
		put(TileEntityMachineTurbine.class, "tileentity_turbine");
		put(TileEntityGeiger.class, "tileentity_geiger");
		put(TileEntityFF.class, "tileentity_forcefield");
		put(TileEntityForceField.class, "tileentity_machine_field");
		put(TileEntityMachineShredderLarge.class, "tileentity_machine_big_shredder");
		put(TileEntityRFDuct.class, "tileentity_hbm_rfduct");
		put(TileEntityReactorControl.class, "tileentity_reactor_remote_control");
		put(TileEntityMachineReactorLarge.class, "tileentity_large_reactor");
		put(TileEntityWasteDrum.class, "tileentity_waste_drum");
		put(TileEntityDecon.class, "tileentity_decon");
		put(TileEntityMachineSatDock.class, "tileentity_miner_dock");
		put(TileEntityMachineEPress.class, "tileentity_electric_press");
		put(TileEntityCoreEmitter.class, "tileentity_v0_emitter");
		put(TileEntityCoreReceiver.class, "tileentity_v0_receiver");
		put(TileEntityCoreInjector.class, "tileentity_v0_injector");
		put(TileEntityCoreStabilizer.class, "tileentity_v0_stabilizer");
		put(TileEntityCore.class, "tileentity_v0");
		put(TileEntityMachineArcFurnace.class, "tileentity_arc_furnace");
		put(TileEntityMachineAmgen.class, "tileentity_amgen");
		put(TileEntityGeysir.class, "tileentity_geysir");
		put(TileEntityMachineMissileAssembly.class, "tileentity_missile_assembly");
		put(TileEntityLaunchTable.class, "tileentity_large_launch_table");
		put(TileEntityCompactLauncher.class, "tileentity_small_launcher");
		put(TileEntityMultiblock.class, "tileentity_multi_core");
		put(TileEntityChlorineSeal.class, "tileentity_chlorine_seal");
		put(TileEntitySoyuzLauncher.class, "tileentity_soyuz_launcher");
		put(TileEntityTesla.class, "tileentity_tesla_coil");
		put(TileEntityBarrel.class, "tileentity_fluid_barrel");
		put(TileEntityCyberCrab.class, "tileentity_crabs");
		put(TileEntitySoyuzCapsule.class, "tileentity_soyuz_capsule");
		put(TileEntityMachineCrystallizer.class, "tileentity_acidomatic");
		put(TileEntitySoyuzStruct.class, "tileentity_soyuz_struct");
		put(TileEntityITERStruct.class, "tileentity_iter_struct");
		put(TileEntityMachineMiningLaser.class, "tileentity_mining_laser");
		put(TileEntityNukeBalefire.class, "tileentity_nuke_fstbmb");
		put(TileEntityMicrowave.class, "tileentity_microwave");
		put(TileEntityMachineMiniRTG.class, "tileentity_mini_rtg");
		put(TileEntityITER.class, "tileentity_iter");
		put(TileEntityMachinePlasmaHeater.class, "tileentity_plasma_heater");
		put(TileEntityMachineFENSU.class, "tileentity_fensu");
		put(TileEntityTrappedBrick.class, "tileentity_trapped_brick");
		put(TileEntityPlasmaStruct.class, "tileentity_plasma_struct");
		put(TileEntityMachineLargeTurbine.class, "tileentity_industrial_turbine");
		put(TileEntityHadronDiode.class, "tileentity_hadron_diode");
		put(TileEntityHadronPower.class, "tileentity_hadron_power");
		put(TileEntityHadron.class, "tileentity_hadron");
		put(TileEntitySolarBoiler.class, "tileentity_solarboiler");
		put(TileEntitySolarMirror.class, "tileentity_solarmirror");
		put(TileEntityMachineDetector.class, "tileentity_he_detector");
		put(TileEntityFireworks.class, "tileentity_firework_box");
		put(TileEntityCrateTungsten.class, "tileentity_crate_hot");
		put(TileEntitySILEX.class, "tileentity_silex");
		put(TileEntityFEL.class, "tileentity_fel");
		put(TileEntityDemonLamp.class, "tileentity_demonlamp");
		put(TileEntityStorageDrum.class, "tileentity_waste_storage_drum");
		put(TileEntityDeaerator.class, "tileentity_deaerator");
		put(TileEntityChungus.class, "tileentity_chungus");
		put(TileEntityCableBaseNT.class, "tileentity_ohgod");
		put(TileEntityPipeBaseNT.class, "tileentity_pipe_base");
		put(TileEntityWatz.class, "tileentity_watz");
		put(TileEntityMachineBAT9000.class, "tileentity_bat9000");
		put(TileEntityMachineOrbus.class, "tileentity_orbus");
		
		put(TileEntityLoot.class, "tileentity_ntm_loot");
		put(TileEntityBobble.class, "tileentity_ntm_bobblehead");
		put(TileEntityEmitter.class, "tileentity_ntm_emitter");

		put(TileEntityDoorGeneric.class, "tileentity_ntm_door");

		put(TileEntityCharger.class, "tileentity_ntm_charger");
		
		put(TileEntityProxyInventory.class, "tileentity_proxy_inventory");
		put(TileEntityProxyEnergy.class, "tileentity_proxy_power");
		put(TileEntityProxyCombo.class, "tileentity_proxy_combo");
		put(TileEntityProxyConductor.class, "tileentity_proxy_conductor");

		put(TileEntityRandomOre.class, "tileentity_mother_of_all_ores");
		
		putNetwork();
		putBombs();
		putTurrets();
		putMachines();
		putPile();
		putRBMK();
	}
	
	private static void putBombs() {
		put(TileEntityBombMulti.class, "tileentity_bombmulti");
		put(TileEntityNukeGadget.class, "tilentity_nukegadget");
		put(TileEntityNukeBoy.class, "tilentity_nukeboy");
		put(TileEntityNukeMike.class, "tileentity_nukemike");
		put(TileEntityNukeTsar.class, "tileentity_nuketsar");
		put(TileEntityNukeFleija.class, "tileentity_nukefleija");
		put(TileEntityNukePrototype.class, "tileentity_nukeproto");
		put(TileEntityNukeSolinium.class, "tileentity_nuke_solinium");
		put(TileEntityNukeN2.class, "tileentity_nuke_n2");
		put(TileEntityNukeCustom.class, "tileentity_nuke_custom");
		put(TileEntityCharge.class, "tileentity_explosive_charge");
	}
	
	private static void putTurrets() {
		put(TileEntityTurretChekhov.class, "tileentity_turret_chekhov");
		put(TileEntityTurretJeremy.class, "tileentity_turret_jeremy");
		put(TileEntityTurretTauon.class, "tileentity_turret_tauon");
		put(TileEntityTurretFriendly.class, "tileentity_turret_friendly");
		put(TileEntityTurretRichard.class, "tileentity_turret_richard");
		put(TileEntityTurretHoward.class, "tileentity_turret_howard");
		put(TileEntityTurretHowardDamaged.class, "tileentity_turret_howard_damaged");
		put(TileEntityTurretMaxwell.class, "tileentity_turret_maxwell");
		put(TileEntityTurretFritz.class, "tileentity_turret_fritz");
		put(TileEntityTurretBrandon.class, "tileentity_turret_brandon");
	}
	
	private static void putMachines() {
		put(TileEntityMachineAutocrafter.class, "tileentity_autocrafter");
		put(TileEntityDiFurnaceRTG.class, "tileentity_rtg_difurnace");
		put(TileEntityMachineRadiolysis.class, "tileentity_radiolysis");
		put(TileEntityUVLamp.class, "tileentity_uv_lamp");
		
		put(TileEntityCondenser.class, "tileentity_condenser");
		put(TileEntityTowerSmall.class, "tileentity_cooling_tower_small");
		put(TileEntityTowerLarge.class, "tileentity_cooling_tower_large");
		put(TileEntityDeuteriumExtractor.class, "tileentity_deuterium_extractor");
		put(TileEntityDeuteriumTower.class, "tileentity_deuterium_tower");
		put(TileEntityMachineLiquefactor.class, "tileentity_liquefactor");
		put(TileEntityMachineSolidifier.class, "tileentity_solidifier");
		put(TileEntityElectrolyser.class, "tileentity_electrolyser");

		put(TileEntityMachineAssembler.class, "tileentity_assembly_machine");
		put(TileEntityMachineAssemfac.class, "tileentity_assemfac");
		put(TileEntityMachineChemplant.class, "tileentity_chemical_plant");
		put(TileEntityMachineChemfac.class, "tileentity_chemfac");
		
		put(TileEntityMachineOilWell.class, "tileentity_derrick");
		put(TileEntityMachinePumpjack.class, "tileentity_machine_pumpjack");
		put(TileEntityMachineFrackingTower.class, "tileentity_fracking_tower");
		put(TileEntityMachineGasFlare.class, "tileentity_gasflare");
		put(TileEntityMachineRefinery.class, "tileentity_refinery");
		put(TileEntityMachineFractionTower.class, "tileentity_fraction_tower");
		put(TileEntitySpacer.class, "tileentity_fraction_spacer");
		put(TileEntityMachineCatalyticCracker.class, "tileentity_catalytic_cracker");
		
		put(TileEntityReactorZirnox.class, "tileentity_zirnox");
		put(TileEntityZirnoxDestroyed.class, "tileentity_zirnox_destroyed");
	}
	
	private static void putPile() {
		put(TileEntityPileFuel.class, "tileentity_pile_fuel");
		put(TileEntityPileSource.class, "tileentity_pile_source");
		put(TileEntityPileBreedingFuel.class, "tileentity_pile_breedingfuel");
		put(TileEntityPileNeutronDetector.class, "tileentity_pile_neutrondetector");
	}
	
	private static void putRBMK() {
		put(TileEntityRBMKRod.class, "tileentity_rbmk_rod");
		put(TileEntityRBMKRodReaSim.class, "tileentity_rbmk_rod_reasim");
		put(TileEntityRBMKControlManual.class, "tileentity_rbmk_control");
		put(TileEntityRBMKControlAuto.class, "tileentity_rbmk_control_auto");
		put(TileEntityRBMKBlank.class, "tileentity_rbmk_blank");
		put(TileEntityRBMKBoiler.class, "tileentity_rbmk_boiler");
		put(TileEntityRBMKReflector.class, "tileentity_rbmk_reflector");
		put(TileEntityRBMKAbsorber.class, "tileentity_rbmk_absorber");
		put(TileEntityRBMKModerator.class, "tileentity_rbmk_moderator");
		put(TileEntityRBMKOutgasser.class, "tileentity_rbmk_outgasser");
		put(TileEntityRBMKCooler.class, "tileentity_rbmk_cooler");
		put(TileEntityRBMKHeater.class, "tileentity_rbmk_heater");
		put(TileEntityRBMKStorage.class, "tileentity_rbmk_storage");
		put(TileEntityCraneConsole.class, "tileentity_rbmk_crane_console");
		put(TileEntityRBMKConsole.class, "tileentity_rbmk_console");
		put(TileEntityRBMKInlet.class, "tileentity_rbmk_inlet");
		put(TileEntityRBMKOutlet.class, "tileentity_rbmk_outlet");
		put(TileEntityHeatex.class, "tileentity_rbmk_heatex");
	}
	
	private static void putNetwork() {
		put(TileEntityCableBaseNT.class, "tileentity_cable", "tileentity_wirecoated");
		put(TileEntityCableSwitch.class, "tileentity_cable_switch");
		put(TileEntityDiode.class, "tileentity_cable_diode");
		
		put(TileEntityConnector.class, "tileentity_connector_redwire");
		put(TileEntityPylon.class, "tileentity_pylon_redwire");
		put(TileEntityPylonLarge.class, "tileentity_pylon_large");
		put(TileEntitySubstation.class, "tileentity_substation");

		put(TileEntityCraneInserter.class, "tileentity_inserter");
		put(TileEntityCraneExtractor.class, "tileentity_extractor");
	}
	
	private static void put(Class<? extends TileEntity> clazz, String... names) {
		map.put(clazz, names);

		if((IFluidSource.class.isAssignableFrom(clazz) || IFluidAcceptor.class.isAssignableFrom(clazz)) && !IFluidConnector.class.isAssignableFrom(clazz)) {
			LoggingUtil.errorWithHighlight(clazz.getCanonicalName() + " implements the old interfaces but not IFluidConnector!");
		}
	}
}
