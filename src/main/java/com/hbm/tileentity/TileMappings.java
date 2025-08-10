package com.hbm.tileentity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.blocks.bomb.BlockVolcano.TileEntityVolcanoCore;
import com.hbm.blocks.generic.BlockBedrockOreTE.TileEntityBedrockOre;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.blocks.generic.BlockDynamicSlag.TileEntitySlag;
import com.hbm.blocks.generic.BlockEmitter.TileEntityEmitter;
import com.hbm.blocks.generic.BlockFissure.TileEntityFissure;
import com.hbm.blocks.generic.BlockGlyphidSpawner.TileEntityGlpyhidSpawner;
import com.hbm.blocks.generic.BlockLoot.TileEntityLoot;
import com.hbm.blocks.generic.BlockPedestal.TileEntityPedestal;
import com.hbm.blocks.generic.BlockPlushie.TileEntityPlushie;
import com.hbm.blocks.generic.BlockRebar.TileEntityRebar;
import com.hbm.blocks.generic.BlockSkeletonHolder.TileEntitySkeletonHolder;
import com.hbm.blocks.generic.BlockSnowglobe.TileEntitySnowglobe;
import com.hbm.blocks.generic.BlockSupplyCrate.TileEntitySupplyCrate;
import com.hbm.blocks.generic.BlockWandJigsaw.TileEntityWandJigsaw;
import com.hbm.blocks.generic.BlockWandLoot.TileEntityWandLoot;
import com.hbm.blocks.generic.BlockWandTandem.TileEntityWandTandem;
import com.hbm.blocks.generic.BlockWandLogic.TileEntityWandLogic;
import com.hbm.blocks.generic.DungeonSpawner.TileEntityDungeonSpawner;
import com.hbm.blocks.generic.LogicBlock;
import com.hbm.blocks.generic.PartEmitter.TileEntityPartEmitter;
import com.hbm.blocks.machine.BlockICF.TileEntityBlockICF;
import com.hbm.blocks.machine.BlockPWR.TileEntityBlockPWR;
import com.hbm.blocks.machine.Floodlight.TileEntityFloodlight;
import com.hbm.blocks.machine.FloodlightBeam.TileEntityFloodlightBeam;
import com.hbm.blocks.machine.MachineCapacitor.TileEntityCapacitor;
import com.hbm.blocks.machine.MachineFan.TileEntityFan;
import com.hbm.blocks.machine.PistonInserter.TileEntityPistonInserter;
import com.hbm.blocks.machine.WatzPump.TileEntityWatzPump;
import com.hbm.blocks.network.BlockCableGauge.TileEntityCableGauge;
import com.hbm.blocks.network.BlockCablePaintable.TileEntityCablePaintable;
import com.hbm.blocks.network.CableDiode.TileEntityDiode;
import com.hbm.blocks.network.CranePartitioner.TileEntityCranePartitioner;
import com.hbm.blocks.network.FluidDuctGauge.TileEntityPipeGauge;
import com.hbm.blocks.network.FluidDuctPaintable.TileEntityPipePaintable;
import com.hbm.blocks.network.FluidDuctPaintableBlockExhaust.TileEntityPipeExhaustPaintable;
import com.hbm.blocks.network.FluidPump.TileEntityFluidPump;
import com.hbm.blocks.network.PneumoTubePaintableBlock.TileEntityPneumoTubePaintable;
import com.hbm.blocks.rail.RailStandardSwitch.TileEntityRailSwitch;
import com.hbm.blocks.network.BlockOpenComputersCablePaintable.TileEntityOpenComputersCablePaintable;
import com.hbm.tileentity.bomb.*;
import com.hbm.tileentity.deco.*;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.albion.*;
import com.hbm.tileentity.machine.oil.*;
import com.hbm.tileentity.machine.pile.*;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.tileentity.machine.storage.*;
import com.hbm.tileentity.network.*;
import com.hbm.tileentity.turret.*;
import cpw.mods.fml.common.Loader;

import net.minecraft.tileentity.TileEntity;

public class TileMappings {

	public static HashMap<Class<? extends TileEntity>, String[]> map = new HashMap<Class<? extends TileEntity>, String[]>();
	public static List<Class<? extends IConfigurableMachine>> configurables = new ArrayList<Class<? extends IConfigurableMachine>>();

	public static void writeMappings() {
		put(TileEntityDiFurnace.class, "tilentity_diFurnace");
		put(TileEntityObjTester.class, "tilentity_objtester");
		put(TileEntityMachineCentrifuge.class, "tileentity_centrifuge");
		put(TileEntityNukeMan.class, "tileentity_nukeman");
		put(TileEntityMachineUF6Tank.class, "tileentity_uf6_tank");
		put(TileEntityMachinePuF6Tank.class, "tileentity_puf6_tank");
		put(TileEntityMachineReactorBreeding.class, "tileentity_reactor");
		put(TileEntityRtgFurnace.class, "tileentity_rtgfurnace");
		put(TileEntityMachineElectricFurnace.class, "tileentity_electric_furnace");
		put(TileEntityDecoTapeRecorder.class, "tileentity_taperecorder");
		put(TileEntityDecoSteelPoles.class, "tileentity_steelpoles");
		put(TileEntityDecoPoleTop.class, "tileentity_poletop");
		put(TileEntityDecoPoleSatelliteReceiver.class, "tileentity_satellitereceicer");
		put(TileEntityMachineBattery.class, "tileentity_battery");
		put(TileEntityCapacitor.class, "tileentity_capacitor");
		put(TileEntityMachineWoodBurner.class, "tileentity_wood_burner");
		put(TileEntityRedBarrel.class, "tileentity_barrel");
		put(TileEntityYellowBarrel.class, "tileentity_nukebarrel");
		put(TileEntityLaunchPad.class, "tileentity_launch1");
		put(TileEntityLaunchPadRusted.class, "tileentity_launchpad_rusted");
		put(TileEntityLaunchPadLarge.class, "tileentity_launchpad_large");
		put(TileEntityDecoBlock.class, "tileentity_deco");
		put(TileEntityDecoBlockAltW.class, "tileentity_deco_w");
		put(TileEntityDecoBlockAltG.class, "tileentity_deco_g");
		put(TileEntityDecoBlockAltF.class, "tileentity_deco_f");
		put(TileEntityCrashedBomb.class, "tileentity_crashed_balefire");
		put(TileEntityConverterHeRf.class, "tileentity_converter_herf");
		put(TileEntityConverterRfHe.class, "tileentity_converter_rfhe");
		put(TileEntityMachineSchrabidiumTransmutator.class, "tileentity_schrabidium_transmutator");
		put(TileEntityMachineDiesel.class, "tileentity_diesel_generator");
		put(TileEntityMachineShredder.class, "tileentity_machine_shredder");
		put(TileEntityMachineTeleporter.class, "tileentity_teleblock");
		put(TileEntityHatch.class, "tileentity_seal_lid");
		put(TileEntityMachineIGenerator.class, "tileentity_igenerator");
		put(TileEntityPartEmitter.class, "tileentity_partemitter");
		put(TileEntityDummy.class, "tileentity_dummy");
		put(TileEntityMachineCyclotron.class, "tileentity_cyclotron");
		put(TileEntityMachineExposureChamber.class, "tileentity_exposure_chamber");
		put(TileEntityMachineRTG.class, "tileentity_machine_rtg");
		put(TileEntityMachineExcavator.class, "tileentity_ntm_excavator");
		put(TileEntityMachineOreSlopper.class, "tileentity_ore_slopper");
		put(TileEntityMachineDrain.class, "tileentity_fluid_drain");
		put(TileEntityMachineFluidTank.class, "tileentity_fluid_tank");
		put(TileEntityMachineTurbofan.class, "tileentity_machine_turbofan");
		put(TileEntityMachineTurbineGas.class, "tileentity_machine_gasturbine");
		put(TileEntityMachineLPW2.class, "tileentity_machine_lpw2");
		put(TileEntityCrateTemplate.class, "tileentity_crate_template");
		put(TileEntityCrateIron.class, "tileentity_crate_iron");
		put(TileEntityCrateSteel.class, "tileentity_crate_steel");
		put(TileEntityCrateDesh.class, "tileentity_crate_desh");
		put(TileEntityMassStorage.class, "tileentity_mass_storage");
		put(TileEntityMachinePress.class, "tileentity_press");
		put(TileEntityMachineAmmoPress.class, "tileentity_ammo_press");
		put(TileEntityMachineSiren.class, "tileentity_siren");
		put(TileEntityMachineSPP.class, "tileentity_spp");
		put(TileEntityMachineRadGen.class, "tileentity_radgen");
		put(TileEntityMachineRadarNT.class, "tileentity_radar");
		put(TileEntityMachineRadarLarge.class, "tileentity_radar_large");
		put(TileEntityMachineRadarScreen.class, "tileentity_radar_screen");
		put(TileEntityBroadcaster.class, "tileentity_pink_cloud_broadcaster");
		put(TileEntityMachineSatLinker.class, "tileentity_satlinker");
		put(TileEntityReactorResearch.class, "tileentity_small_reactor");
		put(TileEntityVaultDoor.class, "tileentity_vault_door");
		put(TileEntityRadiobox.class, "tileentity_radio_broadcaster");
		put(TileEntityRadioRec.class, "tileentity_radio_receiver");
		put(TileEntityVent.class, "tileentity_vent");
		put(TileEntityLandmine.class, "tileentity_landmine");
		put(TileEntityMachineKeyForge.class, "tileentity_key_forge");
		put(TileEntitySellafield.class, "tileentity_sellafield_core");
		put(TileEntityBlastDoor.class, "tileentity_blast_door");
		put(TileEntitySafe.class, "tileentity_safe");
		put(TileEntityMachineGasCent.class, "tileentity_gas_centrifuge");
		put(TileEntityGeiger.class, "tileentity_geiger");
		put(TileEntityFF.class, "tileentity_forcefield");
		put(TileEntityForceField.class, "tileentity_machine_field");
		put(TileEntityReactorControl.class, "tileentity_reactor_remote_control");
		put(TileEntityWasteDrum.class, "tileentity_waste_drum");
		put(TileEntityDecon.class, "tileentity_decon");
		put(TileEntityMachineSatDock.class, "tileentity_miner_dock");
		put(TileEntityMachineEPress.class, "tileentity_electric_press");
		put(TileEntityConveyorPress.class, "tileentity_conveyor_press");
		put(TileEntityCoreEmitter.class, "tileentity_v0_emitter");
		put(TileEntityCoreReceiver.class, "tileentity_v0_receiver");
		put(TileEntityCoreInjector.class, "tileentity_v0_injector");
		put(TileEntityCoreStabilizer.class, "tileentity_v0_stabilizer");
		put(TileEntityCore.class, "tileentity_v0");
		put(TileEntityMachineAmgen.class, "tileentity_amgen");
		put(TileEntityMachineHephaestus.class, "tileentity_hephaestus");
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
		put(TileEntitySupplyCrate.class, "tileentity_supply_crate");
		put(TileEntityMachineRotaryFurnace.class, "tileentity_rotary_furnace");
		put(TileEntityMachineCrystallizer.class, "tileentity_acidomatic");
		put(TileEntitySoyuzStruct.class, "tileentity_soyuz_struct");
		put(TileEntityITERStruct.class, "tileentity_iter_struct");
		put(TileEntityMachineMiningLaser.class, "tileentity_mining_laser");
		put(TileEntityNukeBalefire.class, "tileentity_nuke_fstbmb");
		put(TileEntityMicrowave.class, "tileentity_microwave");
		put(TileEntityMachineMiniRTG.class, "tileentity_mini_rtg");
		put(TileEntityITER.class, "tileentity_iter");
		put(TileEntityBlockICF.class, "tileentity_block_icf");
		put(TileEntityICFPress.class, "tileentity_icf_press");
		put(TileEntityICFController.class, "tileentity_icf_controller");
		put(TileEntityICF.class, "tileentity_icf");
		put(TileEntityMachinePlasmaHeater.class, "tileentity_plasma_heater");
		put(TileEntityMachineFENSU.class, "tileentity_fensu");
		put(TileEntityTrappedBrick.class, "tileentity_trapped_brick");
		put(TileEntityPlasmaStruct.class, "tileentity_plasma_struct");
		put(TileEntityWatzStruct.class, "tileentity_watz_struct");
		put(TileEntityICFStruct.class, "tileentity_icf_struct");
		put(TileEntityHadronDiode.class, "tileentity_hadron_diode");
		put(TileEntityHadronPower.class, "tileentity_hadron_power");
		put(TileEntityHadron.class, "tileentity_hadron");
		put(TileEntityPASource.class, "tileentity_pa_source");
		put(TileEntityPABeamline.class, "tileentity_pa_beamline");
		put(TileEntityPARFC.class, "tileentity_pa_rfc");
		put(TileEntityPAQuadrupole.class, "tileentity_pa_quadrupole");
		put(TileEntityPADipole.class, "tileentity_pa_dipole");
		put(TileEntityPADetector.class, "tileentity_pa_detector");
		put(TileEntitySolarBoiler.class, "tileentity_solarboiler");
		put(TileEntitySolarMirror.class, "tileentity_solarmirror");
		put(TileEntityMachineDetector.class, "tileentity_he_detector");
		put(TileEntityFireworks.class, "tileentity_firework_box");
		put(TileEntityCrateTungsten.class, "tileentity_crate_hot");
		put(TileEntitySILEX.class, "tileentity_silex");
		put(TileEntityFEL.class, "tileentity_fel");
		put(TileEntityDemonLamp.class, "tileentity_demonlamp");
		put(TileEntityFloodlight.class, "tileentity_floodlight");
		put(TileEntityFloodlightBeam.class, "tileentity_floodlight_beam");
		put(TileEntityLantern.class, "tileentity_lantern_ordinary");
		put(TileEntityLanternBehemoth.class, "tileentity_lantern_behemoth");
		put(TileEntityStorageDrum.class, "tileentity_waste_storage_drum");
		put(TileEntityMachineBAT9000.class, "tileentity_bat9000");
		put(TileEntityMachineOrbus.class, "tileentity_orbus");
		put(TileEntityGlpyhidSpawner.class, "tileentity_glyphid_spawner");
		put(TileEntityCustomMachine.class, "tileentity_custom_machine");

		put(TileEntityLoot.class, "tileentity_ntm_loot");
		put(TileEntityPedestal.class, "tileentity_ntm_pedestal");
		put(TileEntitySkeletonHolder.class, "tileentity_ntm_skeleton");
		put(TileEntityDungeonSpawner.class, "tileentity_ntm_dungeon_spawner");
		put(LogicBlock.TileEntityLogicBlock.class, "tileentity_ntm_logic_block");
		put(TileEntityBobble.class, "tileentity_ntm_bobblehead");
		put(TileEntitySnowglobe.class, "tileentity_ntm_snowglobe");
		put(TileEntityPlushie.class, "tileentity_ntm_plushie");
		put(TileEntityEmitter.class, "tileentity_ntm_emitter");

		put(TileEntityDoorGeneric.class, "tileentity_ntm_door");

		put(TileEntityCharger.class, "tileentity_ntm_charger");
		put(TileEntityRefueler.class, "tileentity_ntm_refueler");

		put(TileEntityFileCabinet.class, "tileentity_file_cabinet");

		put(TileEntityProxyInventory.class, "tileentity_proxy_inventory");
		put(TileEntityProxyEnergy.class, "tileentity_proxy_power");
		put(TileEntityProxyCombo.class, "tileentity_proxy_combo");
		put(TileEntityProxyDyn.class, "tileentity_proxy_dyn");
		put(TileEntityProxyConductor.class, "tileentity_proxy_conductor");

		put(TileEntityBedrockOre.class, "tileentity_bedrock_ore");
		put(TileEntityFissure.class, "tileentity_fissure");

		put(TileEntityBlockPWR.class, "tileentity_block_pwr");
		put(TileEntityPWRController.class, "tileentity_pwr_controller");

		put(TileEntityData.class, "tileentity_data");

		put(TileEntityWandLoot.class, "tileentity_wand_loot");
		put(TileEntityWandJigsaw.class, "tileentity_wand_jigsaw");
		put(TileEntityWandLogic.class, "tileentity_wand_spawner");
		put(TileEntityWandTandem.class, "tileentity_wand_tandem");

		putNetwork();
		putBombs();
		putTurrets();
		putMachines();
		putPile();
		putRBMK();

		TileEntityMachineRadarNT.registerEntityClasses();
		TileEntityMachineRadarNT.registerConverters();
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
		put(TileEntityVolcanoCore.class, "tileentity_volcano_core");
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
		put(TileEntityTurretArty.class, "tileentity_turret_arty");
		put(TileEntityTurretHIMARS.class, "tileentity_turret_himars");
		put(TileEntityTurretSentry.class, "tileentity_turret_sentry");
		put(TileEntityTurretSentryDamaged.class, "tileentity_turret_sentry_damaged");
	}

	private static void putMachines() {
		put(TileEntityHeaterFirebox.class, "tileentity_firebox");
		put(TileEntityHeaterOven.class, "tileentity_heating_oven");
		put(TileEntityAshpit.class, "tileentity_ashpit");
		put(TileEntityHeaterOilburner.class, "tileentity_oilburner");
		put(TileEntityHeaterElectric.class, "tileentity_electric_heater");
		put(TileEntityHeaterHeatex.class, "tileentity_heater_heatex");
		put(TileEntityFurnaceIron.class, "tileentity_furnace_iron");
		put(TileEntityFurnaceBrick.class, "tileentity_furnace_brick");
		put(TileEntityFurnaceSteel.class, "tileentity_furnace_steel");
		put(TileEntityFurnaceCombination.class, "tileentity_combination_oven");
		put(TileEntityStirling.class, "tileentity_stirling");
		put(TileEntitySawmill.class, "tileentity_sawmill");
		put(TileEntityCrucible.class, "tileentity_crucible");
		put(TileEntityHeatBoiler.class, "tileentity_heat_boiler");
		put(TileEntityHeatBoilerIndustrial.class, "tileentity_heat_boiler_industrial");

		put(TileEntityMachinePumpSteam.class, "tileentity_steam_pump");
		put(TileEntityMachinePumpElectric.class, "tileentity_electric_pump");

		put(TileEntityFoundryMold.class, "tileentity_foundry_mold");
		put(TileEntityFoundryBasin.class, "tileentity_foundry_basin");
		put(TileEntityFoundryChannel.class, "tileentity_foundry_channel");
		put(TileEntityFoundryTank.class, "tileentity_foundry_tank");
		put(TileEntityFoundryOutlet.class, "tileentity_foundry_outlet");
		put(TileEntityFoundrySlagtap.class, "tileentity_foundry_slagtap");
		put(TileEntitySlag.class, "tileentity_foundry_slag");
		put(TileEntityMachineStrandCaster.class, "tileentity_strand_caster");

		put(TileEntityMachineAutocrafter.class, "tileentity_autocrafter");
		put(TileEntityMachineFunnel.class, "tileentity_funnel");
		put(TileEntityDiFurnaceRTG.class, "tileentity_rtg_difurnace");
		put(TileEntityMachineRadiolysis.class, "tileentity_radiolysis");
		put(TileEntityMachineAutosaw.class, "tileentity_autosaw");

		put(TileEntityCondenser.class, "tileentity_condenser");
		put(TileEntityTowerSmall.class, "tileentity_cooling_tower_small");
		put(TileEntityTowerLarge.class, "tileentity_cooling_tower_large");
		put(TileEntityCondenserPowered.class, "tileentity_condenser_powered");
		put(TileEntityDeuteriumExtractor.class, "tileentity_deuterium_extractor");
		put(TileEntityDeuteriumTower.class, "tileentity_deuterium_tower");
		put(TileEntityMachineLiquefactor.class, "tileentity_liquefactor");
		put(TileEntityMachineSolidifier.class, "tileentity_solidifier");
		put(TileEntityMachineIntake.class, "tileentity_intake");
		put(TileEntityMachineCompressor.class, "tileentity_compressor");
		put(TileEntityMachineCompressorCompact.class, "tileentity_compressor_compact");
		put(TileEntityElectrolyser.class, "tileentity_electrolyser");
		put(TileEntityMachineMixer.class, "tileentity_mixer");
		put(TileEntityMachineArcWelder.class, "tileentity_arc_welder");
		put(TileEntityMachineSolderingStation.class, "tileentity_soldering_station");
		put(TileEntityMachineArcFurnaceLarge.class, "tileentity_arc_furnace_large");

		put(TileEntitySteamEngine.class, "tileentity_steam_engine");
		put(TileEntityMachineTurbine.class, "tileentity_turbine");
		put(TileEntityMachineLargeTurbine.class, "tileentity_industrial_turbine");
		put(TileEntityChungus.class, "tileentity_chungus");

		put(TileEntityMachineCombustionEngine.class, "tileentity_combustion_engine");

		put(TileEntityMachineAssembler.class, "tileentity_assembly_machine");
		put(TileEntityMachineAssemblyMachine.class, "tileentity_assemblymachine");
		put(TileEntityMachineAssemfac.class, "tileentity_assemfac");
		put(TileEntityMachineChemplant.class, "tileentity_chemical_plant");
		put(TileEntityMachineChemicalPlant.class, "tileentity_chemicalplant");
		put(TileEntityMachineChemfac.class, "tileentity_chemfac");
		put(TileEntityMachineChemicalFactory.class, "tileentity_chemicalfactory");
		put(TileEntityMachinePUREX.class, "tileentity_purex");

		put(TileEntityMachineOilWell.class, "tileentity_derrick");
		put(TileEntityMachinePumpjack.class, "tileentity_machine_pumpjack");
		put(TileEntityMachineFrackingTower.class, "tileentity_fracking_tower");
		put(TileEntityMachineGasFlare.class, "tileentity_gasflare");
		put(TileEntityMachineRefinery.class, "tileentity_refinery");
		put(TileEntityMachineVacuumDistill.class, "tileentity_vacuuum_distill");
		put(TileEntityMachineFractionTower.class, "tileentity_fraction_tower");
		put(TileEntitySpacer.class, "tileentity_fraction_spacer");
		put(TileEntityMachineCatalyticCracker.class, "tileentity_catalytic_cracker");
		put(TileEntityMachineCatalyticReformer.class, "tileentity_catalytic_reformer");
		put(TileEntityMachineHydrotreater.class, "tileentity_hydrotreater");
		put(TileEntityMachineCoker.class, "tileentity_coker");
		put(TileEntityMachinePyroOven.class, "tileentity_pyrooven");
		put(TileEntityChimneyBrick.class, "tileentity_chimney_brick");
		put(TileEntityChimneyIndustrial.class, "tileentity_chimney_industrial");

		put(TileEntityReactorZirnox.class, "tileentity_zirnox");
		put(TileEntityZirnoxDestroyed.class, "tileentity_zirnox_destroyed");

		put(TileEntityWatz.class, "tileentity_watz");
		put(TileEntityWatzPump.class, "tileentity_watz_pump");
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
		put(TileEntityRBMKAutoloader.class, "tileentity_rbmk_autoloader");
	}

	private static void putNetwork() {
		put(TileEntityCableBaseNT.class, "tileentity_cable", "tileentity_wirecoated");
		put(TileEntityCablePaintable.class, "tileentity_cable_paintable");
		put(TileEntityCableGauge.class, "tileentity_cable_gauge");
		put(TileEntityCableSwitch.class, "tileentity_cable_switch");
		put(TileEntityDiode.class, "tileentity_cable_diode");

		put(TileEntityConnector.class, "tileentity_connector_redwire");
		put(TileEntityPylon.class, "tileentity_pylon_redwire");
		put(TileEntityPylonMedium.class, "tileentity_pylon_medium");
		put(TileEntityPylonLarge.class, "tileentity_pylon_large");
		put(TileEntitySubstation.class, "tileentity_substation");

		put(TileEntityPipeBaseNT.class, "tileentity_pipe_base");
		put(TileEntityPipePaintable.class, "tileentity_pipe_paintable");
		put(TileEntityPipeGauge.class, "tileentity_pipe_gauge");
		put(TileEntityPipeExhaust.class, "tileentity_pipe_exhaust");
		put(TileEntityPipeExhaustPaintable.class, "tileentity_pipe_exhaust_paintable");
		put(TileEntityFluidValve.class, "tileentity_pipe_valve");
		put(TileEntityFluidPump.class, "tileentity_pipe_pump");

		put(TileEntityCraneInserter.class, "tileentity_inserter");
		put(TileEntityCraneExtractor.class, "tileentity_extractor");
		put(TileEntityCraneGrabber.class, "tileentity_grabber");
		put(TileEntityCraneBoxer.class, "tileentity_boxer");
		put(TileEntityCraneUnboxer.class, "tileentity_unboxer");
		put(TileEntityCraneRouter.class, "tileentity_router");
		put(TileEntityCraneSplitter.class, "tileentity_splitter");
		put(TileEntityCranePartitioner.class, "tileentity_partitioner");
		put(TileEntityFan.class, "tileentity_fan");
		put(TileEntityPistonInserter.class, "tileentity_piston_inserter");

		put(TileEntityPneumoTube.class, "tileentity_pneumatic_tube");
		put(TileEntityPneumoTubePaintable.class, "tileentity_pneumatic_tube_paintable");

		put(TileEntityRadioTorchSender.class, "tileentity_rtty_sender");
		put(TileEntityRadioTorchReceiver.class, "tileentity_rtty_rec");
		put(TileEntityRadioTorchCounter.class, "tileentity_rtty_counter");
		put(TileEntityRadioTorchLogic.class, "tileentity_rtty_logic");
		put(TileEntityRadioTorchReader.class, "tileentity_rtty_reader");
		put(TileEntityRadioTorchController.class, "tileentity_rtty_controller");
		put(TileEntityRadioTelex.class, "tileentity_rtty_telex");

		put(TileEntityDroneWaypoint.class, "tileentity_drone_waypoint");
		put(TileEntityDroneCrate.class, "tileentity_drone_crate");
		put(TileEntityDroneWaypointRequest.class, "tileentity_drone_waypoint_request");
		put(TileEntityDroneDock.class, "tileentity_drone_dock");
		put(TileEntityDroneProvider.class, "tileentity_drone_provider");
		put(TileEntityDroneRequester.class, "tileentity_drone_requester");

		put(TileEntityRailSwitch.class, "tileentity_rail_switch");

		put(TileEntityRebar.class, "tileentity_rebar");

		// OC Compat items
		boolean ocPresent = Loader.isModLoaded("OpenComputers");

		if (ocPresent) {
			put(TileEntityOpenComputersCablePaintable.class, "tileentity_oc_cable_paintable");
		}
	}

	private static void put(Class<? extends TileEntity> clazz, String... names) {
		map.put(clazz, names);

		/*if((IFluidSource.class.isAssignableFrom(clazz) || IFluidAcceptor.class.isAssignableFrom(clazz)) && !IFluidConnector.class.isAssignableFrom(clazz)) {
			LoggingUtil.errorWithHighlight(clazz.getCanonicalName() + " implements the old interfaces but not IFluidConnector!");
		}*/

		if(IConfigurableMachine.class.isAssignableFrom(clazz)) {
			configurables.add((Class<? extends IConfigurableMachine>) clazz);
		}
	}
}
