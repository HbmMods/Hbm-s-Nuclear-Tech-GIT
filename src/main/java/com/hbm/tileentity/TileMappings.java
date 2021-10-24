package com.hbm.tileentity;

import java.util.HashMap;

import com.hbm.blocks.generic.BlockLoot.TileEntityLoot;
import com.hbm.tileentity.bomb.*;
import com.hbm.tileentity.conductor.*;
import com.hbm.tileentity.deco.*;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.oil.*;
import com.hbm.tileentity.machine.pile.*;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.tileentity.network.*;
import com.hbm.tileentity.turret.*;

import net.minecraft.tileentity.TileEntity;

public class TileMappings {

	public static HashMap<Class<? extends TileEntity>, String> map = new HashMap();
	
	public static void writeMappings() {
		map.put(TileEntityTestBombAdvanced.class, "tilentity_testbombadvanced");
		map.put(TileEntityDiFurnace.class, "tilentity_diFurnace");
		map.put(TileEntityTestNuke.class, "tilentity_testnuke");
		map.put(TileEntityRotationTester.class, "tilentity_rotationtester");
		map.put(TileEntityTestRender.class, "tilentity_testrenderer");
		map.put(TileEntityTestContainer.class, "tilentity_testcontainer");
		map.put(TileEntityObjTester.class, "tilentity_objtester");
		map.put(TileEntityMachineCentrifuge.class, "tileentity_centrifuge");
		map.put(TileEntityNukeMan.class, "tileentity_nukeman");
		map.put(TileEntityMachineUF6Tank.class, "tileentity_uf6_tank");
		map.put(TileEntityMachinePuF6Tank.class, "tileentity_puf6_tank");
		map.put(TileEntityMachineReactor.class, "tileentity_reactor");
		map.put(TileEntityNukeFurnace.class, "tileentity_nukefurnace");
		map.put(TileEntityRtgFurnace.class, "tileentity_rtgfurnace");
		map.put(TileEntityMachineGenerator.class, "tileentity_generator");
		map.put(TileEntityMachineElectricFurnace.class, "tileentity_electric_furnace");
		map.put(TileEntityDecoTapeRecorder.class, "tileentity_taperecorder");
		map.put(TileEntityDecoSteelPoles.class, "tileentity_steelpoles");
		map.put(TileEntityDecoPoleTop.class, "tileentity_poletop");
		map.put(TileEntityDecoPoleSatelliteReceiver.class, "tileentity_satellitereceicer");
		map.put(TileEntityMachineDeuterium.class, "tileentity_deuterium");
		map.put(TileEntityWireCoated.class, "tileentity_wirecoated");
		map.put(TileEntityMachineBattery.class, "tileentity_battery");
		map.put(TileEntityMachineCoal.class, "tileentity_coal");
		map.put(TileEntityRedBarrel.class, "tileentity_barrel");
		map.put(TileEntityYellowBarrel.class, "tileentity_nukebarrel");
		map.put(TileEntityLaunchPad.class, "tileentity_launch1");
		map.put(TileEntityDecoBlock.class, "tileentity_deco");
		map.put(TileEntityDecoBlockAltW.class, "tileentity_deco_w");
		map.put(TileEntityDecoBlockAltG.class, "tileentity_deco_g");
		map.put(TileEntityDecoBlockAltF.class, "tileentity_deco_f");
		map.put(TileEntityCoreTitanium.class, "tileentity_core_titanium");
		map.put(TileEntityCoreAdvanced.class, "tileentity_core_advanced");
		map.put(TileEntityFusionMultiblock.class, "tileentity_fusion_multiblock");
		map.put(TileEntityCrashedBomb.class, "tileentity_crashed_balefire");
		map.put(TileEntityCable.class, "tileentity_cable");
		map.put(TileEntityConverterHeRf.class, "tileentity_converter_herf");
		map.put(TileEntityConverterRfHe.class, "tileentity_converter_rfhe");
		map.put(TileEntityMachineSchrabidiumTransmutator.class, "tileentity_schrabidium_transmutator");
		map.put(TileEntityMachineDiesel.class, "tileentity_diesel_generator");
		map.put(TileEntityWatzCore.class, "tileentity_watz_multiblock");
		map.put(TileEntityMachineShredder.class, "tileentity_machine_shredder");
		map.put(TileEntityMachineCMBFactory.class, "tileentity_machine_cmb");
		map.put(TileEntityFWatzCore.class, "tileentity_fwatz_multiblock");
		map.put(TileEntityMachineTeleporter.class, "tileentity_teleblock");
		map.put(TileEntityHatch.class, "tileentity_seal_lid");
		map.put(TileEntityMachineIGenerator.class, "tileentity_igenerator");
		map.put(TileEntityDummy.class, "tileentity_dummy");
		map.put(TileEntityMachineCyclotron.class, "tileentity_cyclotron");
		map.put(TileEntityOilDuct.class, "tileentity_oil_duct");
		map.put(TileEntityOilDuctSolid.class, "tileentity_oil_duct_solid");
		map.put(TileEntityGasDuct.class, "tileentity_gas_duct");
		map.put(TileEntityGasDuctSolid.class, "tileentity_gas_duct_solid");
		map.put(TileEntityMachineRTG.class, "tileentity_machine_rtg");
		map.put(TileEntityPylonRedWire.class, "tileentity_pylon_redwire");
		map.put(TileEntityStructureMarker.class, "tileentity_structure_marker");
		map.put(TileEntityMachineMiningDrill.class, "tileentity_mining_drill");
		map.put(TileEntityMachineAssembler.class, "tileentity_assembly_machine");
		map.put(TileEntityFluidDuct.class, "tileentity_universal_duct");
		map.put(TileEntityMachineChemplant.class, "tileentity_chemical_plant");
		map.put(TileEntityMachineFluidTank.class, "tileentity_fluid_tank");
		map.put(TileEntityTurretHeavy.class, "tileentity_turret_heavy");
		map.put(TileEntityTurretRocket.class, "tileentity_turret_rocket");
		map.put(TileEntityTurretLight.class, "tileentity_turret_light");
		map.put(TileEntityTurretFlamer.class, "tileentity_turret_flamer");
		map.put(TileEntityTurretTau.class, "tileentity_turret_tau");
		map.put(TileEntityMachineTurbofan.class, "tileentity_machine_turbofan");
		map.put(TileEntityCrateIron.class, "tileentity_crate_iron");
		map.put(TileEntityCrateSteel.class, "tileentity_crate_steel");
		map.put(TileEntityMachinePress.class, "tileentity_press");
		map.put(TileEntityAMSBase.class, "tileentity_ams_base");
		map.put(TileEntityAMSEmitter.class, "tileentity_ams_emitter");
		map.put(TileEntityAMSLimiter.class, "tileentity_ams_limiter");
		map.put(TileEntityMachineSiren.class, "tileentity_siren");
		map.put(TileEntityMachineSPP.class, "tileentity_spp");
		map.put(TileEntityTurretSpitfire.class, "tileentity_turret_spitfire");
		map.put(TileEntityMachineRadGen.class, "tileentity_radgen");
		map.put(TileEntityMachineTransformer.class, "tileentity_transformer");
		map.put(TileEntityTurretCIWS.class, "tileentity_turret_cwis");
		map.put(TileEntityMachineRadar.class, "tileentity_radar");
		map.put(TileEntityBroadcaster.class, "tileentity_pink_cloud_broadcaster");
		map.put(TileEntityTurretCheapo.class, "tileentity_turret_cheapo");
		map.put(TileEntityCelPrime.class, "tileentity_cel_prime");
		map.put(TileEntityCelPrimeTerminal.class, "tileentity_cel_prime_access");
		map.put(TileEntityCelPrimeBattery.class, "tileentity_cel_prime_energy");
		map.put(TileEntityCelPrimePort.class, "tileentity_cel_prime_connector");
		map.put(TileEntityCelPrimeTanks.class, "tileentity_cel_prime_storage");
		map.put(TileEntityMachineSeleniumEngine.class, "tileentity_selenium_engine");
		map.put(TileEntityMachineSatLinker.class, "tileentity_satlinker");
		map.put(TileEntityMachineReactorSmall.class, "tileentity_small_reactor");
		map.put(TileEntityVaultDoor.class, "tileentity_vault_door");
		map.put(TileEntityRadiobox.class, "tileentity_radio_broadcaster");
		map.put(TileEntityRadioRec.class, "tileentity_radio_receiver");
		map.put(TileEntityVent.class, "tileentity_vent");
		map.put(TileEntityLandmine.class, "tileentity_landmine");
		map.put(TileEntityBomber.class, "tileentity_bomber");
		map.put(TileEntityMachineTeleLinker.class, "tileentity_telemetry_linker");
		map.put(TileEntityMachineKeyForge.class, "tileentity_key_forge");
		map.put(TileEntitySellafield.class, "tileentity_sellafield_core");
		map.put(TileEntityNukeN45.class, "tileentity_n45");
		map.put(TileEntityBlastDoor.class, "tileentity_blast_door");
		map.put(TileEntitySafe.class, "tileentity_safe");
		map.put(TileEntityMachineGasCent.class, "tileentity_gas_centrifuge");
		map.put(TileEntityMachineBoiler.class, "tileentity_boiler");
		map.put(TileEntityMachineBoilerElectric.class, "tileentity_electric_boiler");
		map.put(TileEntityMachineTurbine.class, "tileentity_turbine");
		map.put(TileEntityGeiger.class, "tileentity_geiger");
		map.put(TileEntityFF.class, "tileentity_forcefield");
		map.put(TileEntityForceField.class, "tileentity_machine_field");
		map.put(TileEntityMachineShredderLarge.class, "tileentity_machine_big_shredder");
		map.put(TileEntityRFDuct.class, "tileentity_hbm_rfduct");
		map.put(TileEntityReactorControl.class, "tileentity_reactor_remote_control");
		map.put(TileEntityMachineReactorLarge.class, "tileentity_large_reactor");
		map.put(TileEntityWasteDrum.class, "tileentity_waste_drum");
		map.put(TileEntityDecon.class, "tileentity_decon");
		map.put(TileEntityMachineSatDock.class, "tileentity_miner_dock");
		map.put(TileEntityMachineEPress.class, "tileentity_electric_press");
		map.put(TileEntityCoreEmitter.class, "tileentity_v0_emitter");
		map.put(TileEntityCoreReceiver.class, "tileentity_v0_receiver");
		map.put(TileEntityCoreInjector.class, "tileentity_v0_injector");
		map.put(TileEntityCoreStabilizer.class, "tileentity_v0_stabilizer");
		map.put(TileEntityCore.class, "tileentity_v0");
		map.put(TileEntityMachineArcFurnace.class, "tileentity_arc_furnace");
		map.put(TileEntityMachineAmgen.class, "tileentity_amgen");
		map.put(TileEntityGeysir.class, "tileentity_geysir");
		map.put(TileEntityMachineMissileAssembly.class, "tileentity_missile_assembly");
		map.put(TileEntityLaunchTable.class, "tileentity_large_launch_table");
		map.put(TileEntityCompactLauncher.class, "tileentity_small_launcher");
		map.put(TileEntityMultiblock.class, "tileentity_multi_core");
		map.put(TileEntityChlorineSeal.class, "tileentity_chlorine_seal");
		map.put(TileEntityCableSwitch.class, "tileentity_he_switch");
		map.put(TileEntitySoyuzLauncher.class, "tileentity_soyuz_launcher");
		map.put(TileEntityTesla.class, "tileentity_tesla_coil");
		map.put(TileEntityBarrel.class, "tileentity_fluid_barrel");
		map.put(TileEntityCyberCrab.class, "tileentity_crabs");
		map.put(TileEntitySoyuzCapsule.class, "tileentity_soyuz_capsule");
		map.put(TileEntityMachineCrystallizer.class, "tileentity_acidomatic");
		map.put(TileEntitySoyuzStruct.class, "tileentity_soyuz_struct");
		map.put(TileEntityITERStruct.class, "tileentity_iter_struct");
		map.put(TileEntityMachineMiningLaser.class, "tileentity_mining_laser");
		map.put(TileEntityProxyInventory.class, "tileentity_proxy_inventory");
		map.put(TileEntityProxyEnergy.class, "tileentity_proxy_power");
		map.put(TileEntityNukeBalefire.class, "tileentity_nuke_fstbmb");
		map.put(TileEntityProxyCombo.class, "tileentity_proxy_combo");
		map.put(TileEntityMicrowave.class, "tileentity_microwave");
		map.put(TileEntityMachineMiniRTG.class, "tileentity_mini_rtg");
		map.put(TileEntityITER.class, "tileentity_iter");
		map.put(TileEntityMachinePlasmaHeater.class, "tileentity_plasma_heater");
		map.put(TileEntityMachineFENSU.class, "tileentity_fensu");
		map.put(TileEntityTrappedBrick.class, "tileentity_trapped_brick");
		map.put(TileEntityPlasmaStruct.class, "tileentity_plasma_struct");
		map.put(TileEntityMachineLargeTurbine.class, "tileentity_industrial_turbine");
		map.put(TileEntityHadronDiode.class, "tileentity_hadron_diode");
		map.put(TileEntityHadronPower.class, "tileentity_hadron_power");
		map.put(TileEntityHadron.class, "tileentity_hadron");
		map.put(TileEntitySolarBoiler.class, "tileentity_solarboiler");
		map.put(TileEntitySolarMirror.class, "tileentity_solarmirror");
		map.put(TileEntityMachineDetector.class, "tileentity_he_detector");
		map.put(TileEntityFireworks.class, "tileentity_firework_box");
		map.put(TileEntityCrateTungsten.class, "tileentity_crate_hot");
		map.put(TileEntitySILEX.class, "tileentity_silex");
		map.put(TileEntityFEL.class, "tileentity_fel");
		map.put(TileEntityDemonLamp.class, "tileentity_demonlamp");
		map.put(TileEntityStorageDrum.class, "tileentity_waste_storage_drum");
		map.put(TileEntityDeaerator.class, "tileentity_deaerator");
		map.put(TileEntityChungus.class, "tileentity_chungus");
		map.put(TileEntityCableBaseNT.class, "tileentity_ohgod");
		map.put(TileEntityWatz.class, "tileentity_watz");
		map.put(TileEntityMachineBAT9000.class, "tileentity_bat9000");
		map.put(TileEntityMachineOrbus.class, "tileentity_orbus");
		map.put(TileEntityMachineFractionTower.class, "tileentity_fraction_tower");
		map.put(TileEntitySpacer.class, "tileentity_fraction_spacer");

		map.put(TileEntityLoot.class, "tileentity_ntm_loot");
		
		putBombs();
		putTurrets();
		putMachines();
		putPile();
		putRBMK();
	}
	
	private static void putBombs() {
		map.put(TileEntityBombMulti.class, "tileentity_bombmulti");
		map.put(TileEntityNukeGadget.class, "tilentity_nukegadget");
		map.put(TileEntityNukeBoy.class, "tilentity_nukeboy");
		map.put(TileEntityNukeMike.class, "tileentity_nukemike");
		map.put(TileEntityNukeTsar.class, "tileentity_nuketsar");
		map.put(TileEntityNukeFleija.class, "tileentity_nukefleija");
		map.put(TileEntityNukePrototype.class, "tileentity_nukeproto");
		map.put(TileEntityNukeSolinium.class, "tileentity_nuke_solinium");
		map.put(TileEntityNukeN2.class, "tileentity_nuke_n2");
		map.put(TileEntityNukeCustom.class, "tileentity_nuke_custom");
	}
	
	private static void putTurrets() {
		map.put(TileEntityTurretChekhov.class, "tileentity_turret_chekhov");
		map.put(TileEntityTurretJeremy.class, "tileentity_turret_jeremy");
		map.put(TileEntityTurretTauon.class, "tileentity_turret_tauon");
		map.put(TileEntityTurretFriendly.class, "tileentity_turret_friendly");
		map.put(TileEntityTurretRichard.class, "tileentity_turret_richard");
		map.put(TileEntityTurretHoward.class, "tileentity_turret_howard");
		map.put(TileEntityTurretHowardDamaged.class, "tileentity_turret_howard_damaged");
		map.put(TileEntityTurretMaxwell.class, "tileentity_turret_maxwell");
		map.put(TileEntityTurretFritz.class, "tileentity_turret_fritz");
		map.put(TileEntityTurretBrandon.class, "tileentity_turret_brandon");
	}
	
	private static void putMachines() {
		map.put(TileEntityUVLamp.class, "tileentity_uv_lamp");
		
		map.put(TileEntityCondenser.class, "tileentity_condenser");
		map.put(TileEntityTowerSmall.class, "tileentity_cooling_tower_small");
		map.put(TileEntityTowerLarge.class, "tileentity_cooling_tower_large");
		
		map.put(TileEntityMachineOilWell.class, "tileentity_derrick");
		map.put(TileEntityMachinePumpjack.class, "tileentity_machine_pumpjack");
		map.put(TileEntityMachineFrackingTower.class, "tileentity_fracking_tower");
		map.put(TileEntityMachineGasFlare.class, "tileentity_gasflare");
		map.put(TileEntityMachineRefinery.class, "tileentity_refinery");
	}
	
	private static void putPile() {
		map.put(TileEntityPileFuel.class, "tileentity_pile_fuel");
		map.put(TileEntityPileSource.class, "tileentity_pile_source");
	}
	
	private static void putRBMK() {
		map.put(TileEntityRBMKRod.class, "tileentity_rbmk_rod");
		map.put(TileEntityRBMKRodReaSim.class, "tileentity_rbmk_rod_reasim");
		map.put(TileEntityRBMKControlManual.class, "tileentity_rbmk_control");
		map.put(TileEntityRBMKControlAuto.class, "tileentity_rbmk_control_auto");
		map.put(TileEntityRBMKBlank.class, "tileentity_rbmk_blank");
		map.put(TileEntityRBMKBoiler.class, "tileentity_rbmk_boiler");
		map.put(TileEntityRBMKReflector.class, "tileentity_rbmk_reflector");
		map.put(TileEntityRBMKAbsorber.class, "tileentity_rbmk_absorber");
		map.put(TileEntityRBMKModerator.class, "tileentity_rbmk_moderator");
		map.put(TileEntityRBMKOutgasser.class, "tileentity_rbmk_outgasser");
		map.put(TileEntityRBMKCooler.class, "tileentity_rbmk_cooler");
		map.put(TileEntityRBMKStorage.class, "tileentity_rbmk_storage");
		map.put(TileEntityCraneConsole.class, "tileentity_rbmk_crane_console");
		map.put(TileEntityRBMKConsole.class, "tileentity_rbmk_console");
		map.put(TileEntityRBMKInlet.class, "tileentity_rbmk_inlet");
		map.put(TileEntityRBMKOutlet.class, "tileentity_rbmk_outlet");
	}
}
