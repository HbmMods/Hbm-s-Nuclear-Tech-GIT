package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockBobble.TileEntityBobble;
import com.hbm.blocks.machine.NTMAnvil;
import com.hbm.entity.cart.EntityMinecartCrate;
import com.hbm.entity.cart.EntityMinecartDestroyer;
import com.hbm.interfaces.Spaghetti;
import com.hbm.inventory.container.*;
import com.hbm.inventory.gui.*;
import com.hbm.inventory.inv.InventoryLeadBox;
import com.hbm.items.ModItems;
import com.hbm.tileentity.bomb.*;
import com.hbm.tileentity.machine.*;
import com.hbm.tileentity.machine.oil.*;
import com.hbm.tileentity.machine.rbmk.*;
import com.hbm.tileentity.machine.storage.*;
import com.hbm.tileentity.turret.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

@Spaghetti("ew")
public class GUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(entity instanceof TileEntityCrateIron) {		return new ContainerCrateIron(player.inventory, (TileEntityCrateIron) entity); }
		if(entity instanceof TileEntityCrateSteel) {	return new ContainerCrateSteel(player.inventory, (TileEntityCrateSteel) entity); }
		if(entity instanceof TileEntityCrateDesh) {		return new ContainerCrateDesh(player.inventory, (TileEntityCrateDesh) entity); }
		if(entity instanceof TileEntityCrateTungsten) {	return new ContainerCrateTungsten(player.inventory, (TileEntityCrateTungsten) entity); }
		if(entity instanceof TileEntitySafe) {			return new ContainerSafe(player.inventory, (TileEntitySafe) entity); }

		if(entity instanceof TileEntityMachineLiquefactor) {	return new ContainerLiquefactor(player.inventory, (TileEntityMachineLiquefactor) entity); }
		if(entity instanceof TileEntityMachineSolidifier) {		return new ContainerSolidifier(player.inventory, (TileEntityMachineSolidifier) entity); }
		if(entity instanceof TileEntityMachineRadiolysis) {		return new ContainerRadiolysis(player.inventory, (TileEntityMachineRadiolysis) entity); }
		if(entity instanceof TileEntityMachineAssemfac) {		return new ContainerAssemfac(player.inventory, (TileEntityMachineAssemfac) entity); }
		if(entity instanceof TileEntityMachineChemfac) {		return new ContainerChemfac(player.inventory, (TileEntityMachineChemfac) entity); }
		if(entity instanceof TileEntityElectrolyser) {			return new ContainerElectrolyser(player.inventory, (TileEntityElectrolyser) entity); }

		if(entity instanceof TileEntityRBMKHeater) {			return new ContainerRBMKHeater(player.inventory, (TileEntityRBMKHeater) entity); }
		
		switch(ID) {
		case ModBlocks.guiID_test_difurnace: {
			if(entity instanceof TileEntityDiFurnace) {
				return new ContainerDiFurnace(player.inventory, (TileEntityDiFurnace) entity);
			}
			return null;
		}

		case ModBlocks.guiID_test_nuke: {
			if(entity instanceof TileEntityTestNuke) {
				return new ContainerTestNuke(player.inventory, (TileEntityTestNuke) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_gadget: {
			if(entity instanceof TileEntityNukeGadget) {
				return new ContainerNukeGadget(player.inventory, (TileEntityNukeGadget) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_boy: {
			if(entity instanceof TileEntityNukeBoy) {
				return new ContainerNukeBoy(player.inventory, (TileEntityNukeBoy) entity);
			}
			return null;
		}

		case ModBlocks.guiID_centrifuge: {
			if(entity instanceof TileEntityMachineCentrifuge) {
				return new ContainerCentrifuge(player.inventory, (TileEntityMachineCentrifuge) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_man: {
			if(entity instanceof TileEntityNukeMan) {
				return new ContainerNukeMan(player.inventory, (TileEntityNukeMan) entity);
			}
			return null;
		}

		case ModBlocks.guiID_uf6_tank: {
			if(entity instanceof TileEntityMachineUF6Tank) {
				return new ContainerUF6Tank(player.inventory, (TileEntityMachineUF6Tank) entity);
			}
			return null;
		}

		case ModBlocks.guiID_puf6_tank: {
			if(entity instanceof TileEntityMachinePuF6Tank) {
				return new ContainerPuF6Tank(player.inventory, (TileEntityMachinePuF6Tank) entity);
			}
			return null;
		}

		case ModBlocks.guiID_reactor_breeding: {
			if(entity instanceof TileEntityMachineReactorBreeding) {
				return new ContainerMachineReactorBreeding(player.inventory, (TileEntityMachineReactorBreeding) entity);
			}
			return null;
		}

		case ModBlocks.guiID_bomb_multi: {
			if(entity instanceof TileEntityBombMulti) {
				return new ContainerBombMulti(player.inventory, (TileEntityBombMulti) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_mike: {
			if(entity instanceof TileEntityNukeMike) {
				return new ContainerNukeMike(player.inventory, (TileEntityNukeMike) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_tsar: {
			if(entity instanceof TileEntityNukeTsar) {
				return new ContainerNukeTsar(player.inventory, (TileEntityNukeTsar) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_furnace: {
			if(entity instanceof TileEntityNukeFurnace) {
				return new ContainerNukeFurnace(player.inventory, (TileEntityNukeFurnace) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rtg_furnace: {
			if(entity instanceof TileEntityRtgFurnace) {
				return new ContainerRtgFurnace(player.inventory, (TileEntityRtgFurnace) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_generator: {
			if(entity instanceof TileEntityMachineGenerator) {
				return new ContainerGenerator(player.inventory, (TileEntityMachineGenerator) entity);
			}
			return null;
		}

		case ModBlocks.guiID_electric_furnace: {
			if(entity instanceof TileEntityMachineElectricFurnace) {
				return new ContainerElectricFurnace(player.inventory, (TileEntityMachineElectricFurnace) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_fleija: {
			if(entity instanceof TileEntityNukeFleija) {
				return new ContainerNukeFleija(player.inventory, (TileEntityNukeFleija) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_deuterium: {
			if(entity instanceof TileEntityMachineDeuterium) {
				return new ContainerMachineDeuterium(player.inventory, (TileEntityMachineDeuterium) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_battery: {
			if(entity instanceof TileEntityMachineBattery) {
				return new ContainerMachineBattery(player.inventory, (TileEntityMachineBattery) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_coal: {
			if(entity instanceof TileEntityMachineCoal) {
				return new ContainerMachineCoal(player.inventory, (TileEntityMachineCoal) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_prototype: {
			if(entity instanceof TileEntityNukePrototype) {
				return new ContainerNukePrototype(player.inventory, (TileEntityNukePrototype) entity);
			}
			return null;
		}

		case ModBlocks.guiID_launch_pad: {
			if(entity instanceof TileEntityLaunchPad) {
				return new ContainerLaunchPadTier1(player.inventory, (TileEntityLaunchPad) entity);
			}
			return null;
		}

		case ModBlocks.guiID_factory_titanium: {
			if(entity instanceof TileEntityCoreTitanium) {
				return new ContainerCoreTitanium(player.inventory, (TileEntityCoreTitanium) entity);
			}
			return null;
		}

		case ModBlocks.guiID_factory_advanced: {
			if(entity instanceof TileEntityCoreAdvanced) {
				return new ContainerCoreAdvanced(player.inventory, (TileEntityCoreAdvanced) entity);
			}
			return null;
		}

		case ModBlocks.guiID_reactor_multiblock: {
			if(entity instanceof TileEntityMachineReactorLarge) {
				return new ContainerReactorMultiblock(player.inventory, (TileEntityMachineReactorLarge) entity);
			}
			return null;
		}

		case ModBlocks.guiID_fusion_multiblock: {
			if(entity instanceof TileEntityFusionMultiblock) {
				return new ContainerFusionMultiblock(player.inventory, (TileEntityFusionMultiblock) entity);
			}
			return null;
		}

		case ModBlocks.guiID_schrabidium_transmutator: {
			if(entity instanceof TileEntityMachineSchrabidiumTransmutator) {
				return new ContainerMachineSchrabidiumTransmutator(player.inventory, (TileEntityMachineSchrabidiumTransmutator) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_diesel: {
			if(entity instanceof TileEntityMachineDiesel) {
				return new ContainerMachineDiesel(player.inventory, (TileEntityMachineDiesel) entity);
			}
			return null;
		}

		case ModBlocks.guiID_watz_multiblock: {
			if(entity instanceof TileEntityWatzCore) {
				return new ContainerWatzCore(player.inventory, (TileEntityWatzCore) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_shredder: {
			if(entity instanceof TileEntityMachineShredder) {
				return new ContainerMachineShredder(player.inventory, (TileEntityMachineShredder) entity);
			}
			return null;
		}

		case ModBlocks.guiID_combine_factory: {
			if(entity instanceof TileEntityMachineCMBFactory) {
				return new ContainerMachineCMBFactory(player.inventory, (TileEntityMachineCMBFactory) entity);
			}
			return null;
		}

		case ModBlocks.guiID_fwatz_multiblock: {
			if(entity instanceof TileEntityFWatzCore) {
				return new ContainerFWatzCore(player.inventory, (TileEntityFWatzCore) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_teleporter: {
			if(entity instanceof TileEntityMachineTeleporter) {
			//	return new ContainerMachineTeleporter(player.inventory, (TileEntityMachineTeleporter) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_custom: {
			if(entity instanceof TileEntityNukeCustom) {
				return new ContainerNukeCustom(player.inventory, (TileEntityNukeCustom) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_industrial_generator: {
			if(entity instanceof TileEntityMachineIGenerator) {
				return new ContainerIGenerator(player.inventory, (TileEntityMachineIGenerator) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_rtg: {
			if(entity instanceof TileEntityMachineRTG) {
				return new ContainerMachineRTG(player.inventory, (TileEntityMachineRTG) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_cyclotron: {
			if(entity instanceof TileEntityMachineCyclotron) {
				return new ContainerMachineCyclotron(player.inventory, (TileEntityMachineCyclotron) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_well: {
			if(entity instanceof TileEntityOilDrillBase) {
				return new ContainerMachineOilWell(player.inventory, (TileEntityOilDrillBase) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_refinery: {
			if(entity instanceof TileEntityMachineRefinery) {
				return new ContainerMachineRefinery(player.inventory, (TileEntityMachineRefinery) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_flare: {
			if(entity instanceof TileEntityMachineGasFlare) {
				return new ContainerMachineGasFlare(player.inventory, (TileEntityMachineGasFlare) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_drill: {
			if(entity instanceof TileEntityMachineMiningDrill) {
				return new ContainerMachineMiningDrill(player.inventory, (TileEntityMachineMiningDrill) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_assembler: {
			if(entity instanceof TileEntityMachineAssembler) {
				return new ContainerMachineAssembler(player.inventory, (TileEntityMachineAssembler) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_chemplant: {
			if(entity instanceof TileEntityMachineChemplant) {
				return new ContainerMachineChemplant(player.inventory, (TileEntityMachineChemplant) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_fluidtank: {
			if(entity instanceof TileEntityMachineFluidTank) {
				return new ContainerMachineFluidTank(player.inventory, (TileEntityMachineFluidTank) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_turbofan: {
			if(entity instanceof TileEntityMachineTurbofan) {
				return new ContainerMachineTurbofan(player.inventory, (TileEntityMachineTurbofan) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_press: {
			if(entity instanceof TileEntityMachinePress) {
				return new ContainerMachinePress(player.inventory, (TileEntityMachinePress) entity);
			}
			return null;
		}

		case ModBlocks.guiID_ams_limiter: {
			if(entity instanceof TileEntityAMSLimiter) {
				return new ContainerAMSLimiter(player.inventory, (TileEntityAMSLimiter) entity);
			}
			return null;
		}

		case ModBlocks.guiID_ams_emitter: {
			if(entity instanceof TileEntityAMSEmitter) {
				return new ContainerAMSEmitter(player.inventory, (TileEntityAMSEmitter) entity);
			}
			return null;
		}

		case ModBlocks.guiID_ams_base: {
			if(entity instanceof TileEntityAMSBase) {
				return new ContainerAMSBase(player.inventory, (TileEntityAMSBase) entity);
			}
			return null;
		}

		case ModBlocks.guiID_siren: {
			if(entity instanceof TileEntityMachineSiren) {
				return new ContainerMachineSiren(player.inventory, (TileEntityMachineSiren) entity);
			}
			return null;
		}

		case ModBlocks.guiID_radgen: {
			if(entity instanceof TileEntityMachineRadGen) {
				return new ContainerMachineRadGen(player.inventory, (TileEntityMachineRadGen) entity);
			}
			return null;
		}

		case ModBlocks.guiID_radar: {
			if(entity instanceof TileEntityMachineRadar) {
				return new ContainerMachineRadar(player.inventory, (TileEntityMachineRadar) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_solinium: {
			if(entity instanceof TileEntityNukeSolinium) {
				return new ContainerNukeSolinium(player.inventory, (TileEntityNukeSolinium) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_n2: {
			if(entity instanceof TileEntityNukeN2) {
				return new ContainerNukeN2(player.inventory, (TileEntityNukeN2) entity);
			}
			return null;
		}

		case ModBlocks.guiID_cel_prime: {
			if(entity instanceof TileEntityCelPrime) {
				return new ContainerCelPrime(player.inventory, (TileEntityCelPrime) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_selenium: {
			if(entity instanceof TileEntityMachineSeleniumEngine) {
				return new ContainerMachineSelenium(player.inventory, (TileEntityMachineSeleniumEngine) entity);
			}
			return null;
		}

		case ModBlocks.guiID_satlinker: {
			if(entity instanceof TileEntityMachineSatLinker) {
				return new ContainerMachineSatLinker(player.inventory, (TileEntityMachineSatLinker) entity);
			}
			return null;
		}

		case ModBlocks.guiID_reactor_research: {
			if(entity instanceof TileEntityReactorResearch) {
				return new ContainerReactorResearch(player.inventory, (TileEntityReactorResearch) entity);
			}
			return null;
		}

		case ModBlocks.guiID_radiobox: {
			if(entity instanceof TileEntityRadiobox) {
				return new ContainerRadiobox(player.inventory, (TileEntityRadiobox) entity);
			}
			return null;
		}

		case ModBlocks.guiID_telelinker: {
			if(entity instanceof TileEntityMachineTeleLinker) {
				return new ContainerMachineTeleLinker(player.inventory, (TileEntityMachineTeleLinker) entity);
			}
			return null;
		}

		case ModBlocks.guiID_keyforge: {
			if(entity instanceof TileEntityMachineKeyForge) {
				return new ContainerMachineKeyForge(player.inventory, (TileEntityMachineKeyForge) entity);
			}
			return null;
		}

		case ModBlocks.guiID_radiorec: {
			if(entity instanceof TileEntityRadioRec) {
				return new ContainerRadioRec(player.inventory, (TileEntityRadioRec) entity);
			}
			return null;
		}

		case ModBlocks.guiID_gascent: {
			if(entity instanceof TileEntityMachineGasCent) {
				return new ContainerMachineGasCent(player.inventory, (TileEntityMachineGasCent) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_boiler: {
			if(entity instanceof TileEntityMachineBoiler) {
				return new ContainerMachineBoiler(player.inventory, (TileEntityMachineBoiler) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_boiler_electric: {
			if(entity instanceof TileEntityMachineBoilerElectric) {
				return new ContainerMachineBoilerElectric(player.inventory, (TileEntityMachineBoilerElectric) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_turbine: {
			if(entity instanceof TileEntityMachineTurbine) {
				return new ContainerMachineTurbine(player.inventory, (TileEntityMachineTurbine) entity);
			}
			return null;
		}

		case ModBlocks.guiID_forcefield: {
			if(entity instanceof TileEntityForceField) {
				return new ContainerForceField(player.inventory, (TileEntityForceField) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_n45: {
			if(entity instanceof TileEntityNukeN45) {
				return new ContainerNukeN45(player.inventory, (TileEntityNukeN45) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_controller: {
			if(entity instanceof TileEntityReactorControl) {
				return new ContainerReactorControl(player.inventory, (TileEntityReactorControl) entity);
			}
			return null;
		}

		case ModBlocks.guiID_waste_drum: {
			if(entity instanceof TileEntityWasteDrum) {
				return new ContainerWasteDrum(player.inventory, (TileEntityWasteDrum) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dock: {
			if(entity instanceof TileEntityMachineSatDock) {
				return new ContainerSatDock(player.inventory, (TileEntityMachineSatDock) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_epress: {
			if(entity instanceof TileEntityMachineEPress) {
				return new ContainerMachineEPress(player.inventory, (TileEntityMachineEPress) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_arc: {
			if(entity instanceof TileEntityMachineArcFurnace) {
				return new ContainerMachineArcFurnace(player.inventory, (TileEntityMachineArcFurnace) entity);
			}
			return null;
		}

		case ModBlocks.guiID_missile_assembly: {
			if(entity instanceof TileEntityMachineMissileAssembly) {
				return new ContainerMachineMissileAssembly(player.inventory, (TileEntityMachineMissileAssembly) entity);
			}
			return null;
		}

		case ModBlocks.guiID_compact_launcher: {
			if(entity instanceof TileEntityCompactLauncher) {
				return new ContainerCompactLauncher(player.inventory, (TileEntityCompactLauncher) entity);
			}
			return null;
		}

		case ModBlocks.guiID_launch_table: {
			if(entity instanceof TileEntityLaunchTable) {
				return new ContainerLaunchTable(player.inventory, (TileEntityLaunchTable) entity);
			}
			return null;
		}

		case ModBlocks.guiID_soyuz_launcher: {
			if(entity instanceof TileEntitySoyuzLauncher) {
				return new ContainerSoyuzLauncher(player.inventory, (TileEntitySoyuzLauncher) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dfc_emitter: {
			if(entity instanceof TileEntityCoreEmitter) {
				return new ContainerCoreEmitter(player.inventory, (TileEntityCoreEmitter) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dfc_receiver: {
			if(entity instanceof TileEntityCoreReceiver) {
				return new ContainerCoreReceiver(player.inventory, (TileEntityCoreReceiver) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dfc_injector: {
			if(entity instanceof TileEntityCoreInjector) {
				return new ContainerCoreInjector(player.inventory, (TileEntityCoreInjector) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dfc_core: {
			if(entity instanceof TileEntityCore) {
				return new ContainerCore(player.inventory, (TileEntityCore) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dfc_stabilizer: {
			if(entity instanceof TileEntityCoreStabilizer) {
				return new ContainerCoreStabilizer(player.inventory, (TileEntityCoreStabilizer) entity);
			}
			return null;
		}

		case ModBlocks.guiID_barrel: {
			if(entity instanceof TileEntityBarrel) {
				return new ContainerBarrel(player.inventory, (TileEntityBarrel) entity);
			}
			return null;
		}

		case ModBlocks.guiID_capsule: {
			if(entity instanceof TileEntitySoyuzCapsule) {
				return new ContainerSoyuzCapsule(player.inventory, (TileEntitySoyuzCapsule) entity);
			}
			return null;
		}

		case ModBlocks.guiID_crystallizer: {
			if(entity instanceof TileEntityMachineCrystallizer) {
				return new ContainerCrystallizer(player.inventory, (TileEntityMachineCrystallizer) entity);
			}
			return null;
		}

		case ModBlocks.guiID_mining_laser: {
			if(entity instanceof TileEntityMachineMiningLaser) {
				return new ContainerMiningLaser(player.inventory, (TileEntityMachineMiningLaser) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_fstbmb: {
			if(entity instanceof TileEntityNukeBalefire) {
				return new ContainerNukeFstbmb(player.inventory, (TileEntityNukeBalefire) entity);
			}
			return null;
		}

		case ModBlocks.guiID_microwave: {
			if(entity instanceof TileEntityMicrowave) {
				return new ContainerMicrowave(player.inventory, (TileEntityMicrowave) entity);
			}
			return null;
		}

		case ModBlocks.guiID_iter: {
			if(entity instanceof TileEntityITER) {
				return new ContainerITER(player.inventory, (TileEntityITER) entity);
			}
			return null;
		}

		case ModBlocks.guiID_plasma_heater: {
			if(entity instanceof TileEntityMachinePlasmaHeater) {
				return new ContainerPlasmaHeater(player.inventory, (TileEntityMachinePlasmaHeater) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_large_turbine: {
			if(entity instanceof TileEntityMachineLargeTurbine) {
				return new ContainerMachineLargeTurbine(player.inventory, (TileEntityMachineLargeTurbine) entity);
			}
			return null;
		}

		case ModBlocks.guiID_hadron: {
			if(entity instanceof TileEntityHadron) {
				return new ContainerHadron(player.inventory, (TileEntityHadron) entity);
			}
			return null;
		}

		case ModBlocks.guiID_armor_table: {
			if(world.getBlock(x, y, z) == ModBlocks.machine_armor_table) {
				return new ContainerArmorTable(player.inventory);
			}
			return null;
		}

		case ModBlocks.guiID_chekhov: {
			if(entity instanceof TileEntityTurretChekhov) {
				return new ContainerTurretBase(player.inventory, (TileEntityTurretChekhov) entity);
			}
			return null;
		}

		case ModBlocks.guiID_friendly: {
			if(entity instanceof TileEntityTurretFriendly) {
				return new ContainerTurretBase(player.inventory, (TileEntityTurretFriendly) entity);
			}
			return null;
		}

		case ModBlocks.guiID_jeremy: {
			if(entity instanceof TileEntityTurretJeremy) {
				return new ContainerTurretBase(player.inventory, (TileEntityTurretJeremy) entity);
			}
			return null;
		}

		case ModBlocks.guiID_tauon: {
			if(entity instanceof TileEntityTurretTauon) {
				return new ContainerTurretBase(player.inventory, (TileEntityTurretTauon) entity);
			}
			return null;
		}

		case ModBlocks.guiID_richard: {
			if(entity instanceof TileEntityTurretRichard) {
				return new ContainerTurretBase(player.inventory, (TileEntityTurretRichard) entity);
			}
			return null;
		}

		case ModBlocks.guiID_howard: {
			if(entity instanceof TileEntityTurretHoward) {
				return new ContainerTurretBase(player.inventory, (TileEntityTurretHoward) entity);
			}
			return null;
		}

		case ModBlocks.guiID_silex: {
			if(entity instanceof TileEntitySILEX) {
				return new ContainerSILEX(player.inventory, (TileEntitySILEX) entity);
			}
			return null;
		}

		case ModBlocks.guiID_fel: {
			if(entity instanceof TileEntityFEL) {
				return new ContainerFEL(player.inventory, (TileEntityFEL) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_rod: {
			if(entity instanceof TileEntityRBMKRod) {
				return new ContainerRBMKRod(player.inventory, (TileEntityRBMKRod) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_boiler: {
			if(entity instanceof TileEntityRBMKBoiler) {
				return new ContainerRBMKGeneric(player.inventory);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_control: {
			if(entity instanceof TileEntityRBMKControlManual) {
				return new ContainerRBMKControl(player.inventory, (TileEntityRBMKControlManual) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_control_auto: {
			if(entity instanceof TileEntityRBMKControlAuto) {
				return new ContainerRBMKControlAuto(player.inventory, (TileEntityRBMKControlAuto) entity);
			}
			return null;
		}

		case ModBlocks.guiID_storage_drum: {
			if(entity instanceof TileEntityStorageDrum) {
				return new ContainerStorageDrum(player.inventory, (TileEntityStorageDrum) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_outgasser: {
			if(entity instanceof TileEntityRBMKOutgasser) {
				return new ContainerRBMKOutgasser(player.inventory, (TileEntityRBMKOutgasser) entity);
			}
			return null;
		}

		case ModBlocks.guiID_maxwell: {
			if(entity instanceof TileEntityTurretMaxwell) {
				return new ContainerTurretBase(player.inventory, (TileEntityTurretMaxwell) entity);
			}
			return null;
		}

		case ModBlocks.guiID_anvil: {
			if(world.getBlock(x, y, z) instanceof NTMAnvil) {
				return new ContainerAnvil(player.inventory, ((NTMAnvil)world.getBlock(x, y, z)).tier);
			}
			return null;
		}

		case ModBlocks.guiID_fritz: {
			if(entity instanceof TileEntityTurretFritz) {
				return new ContainerTurretBase(player.inventory, (TileEntityTurretFritz) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_storage: {
			if(entity instanceof TileEntityRBMKStorage) {
				return new ContainerRBMKStorage(player.inventory, (TileEntityRBMKStorage) entity);
			}
			return null;
		}
		
		case ModBlocks.guiID_rtg_difurnace: {
			if (entity instanceof TileEntityDiFurnaceRTG) {
				return new ContainerMachineDiFurnaceRTG(player.inventory, (TileEntityDiFurnaceRTG) entity);
			}
			return null;
		}
		
		case ModBlocks.guiID_reactor_zirnox: {
			if(entity instanceof TileEntityReactorZirnox) {
				return new ContainerReactorZirnox(player.inventory, (TileEntityReactorZirnox) entity);
			}
			return null;
		}
		}
		// NON-TE CONTAINERS

		switch(ID) {
		case ModItems.guiID_item_box: return new ContainerLeadBox(player, player.inventory, new InventoryLeadBox(player.getHeldItem()));
		case ModItems.guiID_item_book: return new ContainerBook(player.inventory);
		case ModItems.guiID_cart_crate: return new ContainerCrateSteel(player.inventory, (EntityMinecartCrate)player.worldObj.getEntityByID(x));
		case ModItems.guiID_cart_destroyer: return new ContainerCartDestroyer(player.inventory, (EntityMinecartDestroyer)player.worldObj.getEntityByID(x));
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);

		if(entity instanceof TileEntityCrateIron) {		return new GUICrateIron(player.inventory, (TileEntityCrateIron) entity); }
		if(entity instanceof TileEntityCrateSteel) {	return new GUICrateSteel(player.inventory, (TileEntityCrateSteel) entity); }
		if(entity instanceof TileEntityCrateDesh) {		return new GUICrateDesh(player.inventory, (TileEntityCrateDesh) entity); }
		if(entity instanceof TileEntityCrateTungsten) {	return new GUICrateTungsten(player.inventory, (TileEntityCrateTungsten) entity); }
		if(entity instanceof TileEntitySafe) {			return new GUISafe(player.inventory, (TileEntitySafe) entity); }
		
		if(entity instanceof TileEntityMachineLiquefactor) {	return new GUILiquefactor(player.inventory, (TileEntityMachineLiquefactor) entity); }
		if(entity instanceof TileEntityMachineSolidifier) {		return new GUISolidifier(player.inventory, (TileEntityMachineSolidifier) entity); }
		if(entity instanceof TileEntityMachineRadiolysis) {		return new GUIRadiolysis(player.inventory, (TileEntityMachineRadiolysis) entity); }
		if(entity instanceof TileEntityMachineAssemfac) {		return new GUIAssemfac(player.inventory, (TileEntityMachineAssemfac) entity); }
		if(entity instanceof TileEntityMachineChemfac) {		return new GUIChemfac(player.inventory, (TileEntityMachineChemfac) entity); }
		if(entity instanceof TileEntityElectrolyser) { 			return new GUIElectrolyser(player.inventory, (TileEntityElectrolyser) entity); }

		if(entity instanceof TileEntityRBMKHeater) { 			return new GUIRBMKHeater(player.inventory, (TileEntityRBMKHeater) entity); }
		
		switch(ID) {
		case ModBlocks.guiID_test_difurnace: {
			if(entity instanceof TileEntityDiFurnace) {
				return new GUITestDiFurnace(player.inventory, (TileEntityDiFurnace) entity);
			}
			return null;
		}

		case ModBlocks.guiID_test_nuke: {
			if(entity instanceof TileEntityTestNuke) {
				return new GUITestNuke(player.inventory, (TileEntityTestNuke) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_gadget: {
			if(entity instanceof TileEntityNukeGadget) {
				return new GUINukeGadget(player.inventory, (TileEntityNukeGadget) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_boy: {
			if(entity instanceof TileEntityNukeBoy) {
				return new GUINukeBoy(player.inventory, (TileEntityNukeBoy) entity);
			}
			return null;
		}

		case ModBlocks.guiID_centrifuge: {
			if(entity instanceof TileEntityMachineCentrifuge) {
				return new GUIMachineCentrifuge(player.inventory, (TileEntityMachineCentrifuge) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_man: {
			if(entity instanceof TileEntityNukeMan) {
				return new GUINukeMan(player.inventory, (TileEntityNukeMan) entity);
			}
			return null;
		}

		case ModBlocks.guiID_uf6_tank: {
			if(entity instanceof TileEntityMachineUF6Tank) {
				return new GUIMachineUF6Tank(player.inventory, (TileEntityMachineUF6Tank) entity);
			}
			return null;
		}

		case ModBlocks.guiID_puf6_tank: {
			if(entity instanceof TileEntityMachinePuF6Tank) {
				return new GUIMachinePuF6Tank(player.inventory, (TileEntityMachinePuF6Tank) entity);
			}
			return null;
		}

		case ModBlocks.guiID_reactor_breeding: {
			if(entity instanceof TileEntityMachineReactorBreeding) {
				return new GUIMachineReactorBreeding(player.inventory, (TileEntityMachineReactorBreeding) entity);
			}
			return null;
		}

		case ModBlocks.guiID_bomb_multi: {
			if(entity instanceof TileEntityBombMulti) {
				return new GUIBombMulti(player.inventory, (TileEntityBombMulti) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_mike: {
			if(entity instanceof TileEntityNukeMike) {
				return new GUINukeMike(player.inventory, (TileEntityNukeMike) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_tsar: {
			if(entity instanceof TileEntityNukeTsar) {
				return new GUINukeTsar(player.inventory, (TileEntityNukeTsar) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_furnace: {
			if(entity instanceof TileEntityNukeFurnace) {
				return new GUINukeFurnace(player.inventory, (TileEntityNukeFurnace) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rtg_furnace: {
			if(entity instanceof TileEntityRtgFurnace) {
				return new GUIRtgFurnace(player.inventory, (TileEntityRtgFurnace) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_generator: {
			if(entity instanceof TileEntityMachineGenerator) {
				return new GUIMachineGenerator(player.inventory, (TileEntityMachineGenerator) entity);
			}
			return null;
		}

		case ModBlocks.guiID_electric_furnace: {
			if(entity instanceof TileEntityMachineElectricFurnace) {
				return new GUIMachineElectricFurnace(player.inventory, (TileEntityMachineElectricFurnace) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_fleija: {
			if(entity instanceof TileEntityNukeFleija) {
				return new GUINukeFleija(player.inventory, (TileEntityNukeFleija) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_deuterium: {
			if(entity instanceof TileEntityMachineDeuterium) {
				return new GUIMachineDeuterium(player.inventory, (TileEntityMachineDeuterium) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_battery: {
			if(entity instanceof TileEntityMachineBattery) {
				return new GUIMachineBattery(player.inventory, (TileEntityMachineBattery) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_coal: {
			if(entity instanceof TileEntityMachineCoal) {
				return new GUIMachineCoal(player.inventory, (TileEntityMachineCoal) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_prototype: {
			if(entity instanceof TileEntityNukePrototype) {
				return new GUINukePrototype(player.inventory, (TileEntityNukePrototype) entity);
			}
			return null;
		}

		case ModBlocks.guiID_launch_pad: {
			if(entity instanceof TileEntityLaunchPad) {
				return new GUILaunchPadTier1(player.inventory, (TileEntityLaunchPad) entity);
			}
			return null;
		}

		case ModBlocks.guiID_factory_titanium: {
			if(entity instanceof TileEntityCoreTitanium) {
				return new GUICoreTitanium(player.inventory, (TileEntityCoreTitanium) entity);
			}
			return null;
		}

		case ModBlocks.guiID_factory_advanced: {
			if(entity instanceof TileEntityCoreAdvanced) {
				return new GUICoreAdvanced(player.inventory, (TileEntityCoreAdvanced) entity);
			}
			return null;
		}

		case ModBlocks.guiID_reactor_multiblock: {
			if(entity instanceof TileEntityMachineReactorLarge) {
				return new GUIReactorMultiblock(player.inventory, (TileEntityMachineReactorLarge) entity);
			}
			return null;
		}

		case ModBlocks.guiID_fusion_multiblock: {
			if(entity instanceof TileEntityFusionMultiblock) {
				return new GUIFusionMultiblock(player.inventory, (TileEntityFusionMultiblock) entity);
			}
			return null;
		}

		case ModBlocks.guiID_schrabidium_transmutator: {
			if(entity instanceof TileEntityMachineSchrabidiumTransmutator) {
				return new GUIMachineSchrabidiumTransmutator(player.inventory, (TileEntityMachineSchrabidiumTransmutator) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_diesel: {
			if(entity instanceof TileEntityMachineDiesel) {
				return new GUIMachineDiesel(player.inventory, (TileEntityMachineDiesel) entity);
			}
			return null;
		}

		case ModBlocks.guiID_watz_multiblock: {
			if(entity instanceof TileEntityWatzCore) {
				return new GUIWatzCore(player.inventory, (TileEntityWatzCore) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_shredder: {
			if(entity instanceof TileEntityMachineShredder) {
				return new GUIMachineShredder(player.inventory, (TileEntityMachineShredder) entity);
			}
			return null;
		}

		case ModBlocks.guiID_combine_factory: {
			if(entity instanceof TileEntityMachineCMBFactory) {
				return new GUIMachineCMBFactory(player.inventory, (TileEntityMachineCMBFactory) entity);
			}
			return null;
		}

		case ModBlocks.guiID_fwatz_multiblock: {
			if(entity instanceof TileEntityFWatzCore) {
				return new GUIFWatzCore(player.inventory, (TileEntityFWatzCore) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_teleporter: {
			if(entity instanceof TileEntityMachineTeleporter) {
				return new GUIMachineTeleporter(player.inventory, (TileEntityMachineTeleporter) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_custom: {
			if(entity instanceof TileEntityNukeCustom) {
				return new GUINukeCustom(player.inventory, (TileEntityNukeCustom) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_industrial_generator: {
			if(entity instanceof TileEntityMachineIGenerator) {
				return new GUIIGenerator(player.inventory, (TileEntityMachineIGenerator) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_rtg: {
			if(entity instanceof TileEntityMachineRTG) {
				return new GUIMachineRTG(player.inventory, (TileEntityMachineRTG) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_cyclotron: {
			if(entity instanceof TileEntityMachineCyclotron) {
				return new GUIMachineCyclotron(player.inventory, (TileEntityMachineCyclotron) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_well: {
			if(entity instanceof TileEntityOilDrillBase) {
				return new GUIMachineOilWell(player.inventory, (TileEntityOilDrillBase) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_refinery: {
			if(entity instanceof TileEntityMachineRefinery) {
				return new GUIMachineRefinery(player.inventory, (TileEntityMachineRefinery) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_flare: {
			if(entity instanceof TileEntityMachineGasFlare) {
				return new GUIMachineGasFlare(player.inventory, (TileEntityMachineGasFlare) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_drill: {
			if(entity instanceof TileEntityMachineMiningDrill) {
				return new GUIMachineMiningDrill(player.inventory, (TileEntityMachineMiningDrill) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_assembler: {
			if(entity instanceof TileEntityMachineAssembler) {
				return new GUIMachineAssembler(player.inventory, (TileEntityMachineAssembler) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_chemplant: {
			if(entity instanceof TileEntityMachineChemplant) {
				return new GUIMachineChemplant(player.inventory, (TileEntityMachineChemplant) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_fluidtank: {
			if(entity instanceof TileEntityMachineFluidTank) {
				return new GUIMachineFluidTank(player.inventory, (TileEntityMachineFluidTank) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_turbofan: {
			if(entity instanceof TileEntityMachineTurbofan) {
				return new GUIMachineTurbofan(player.inventory, (TileEntityMachineTurbofan) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_press: {
			if(entity instanceof TileEntityMachinePress) {
				return new GUIMachinePress(player.inventory, (TileEntityMachinePress) entity);
			}
			return null;
		}

		case ModBlocks.guiID_ams_limiter: {
			if(entity instanceof TileEntityAMSLimiter) {
				return new GUIAMSLimiter(player.inventory, (TileEntityAMSLimiter) entity);
			}
			return null;
		}

		case ModBlocks.guiID_ams_emitter: {
			if(entity instanceof TileEntityAMSEmitter) {
				return new GUIAMSEmitter(player.inventory, (TileEntityAMSEmitter) entity);
			}
			return null;
		}

		case ModBlocks.guiID_ams_base: {
			if(entity instanceof TileEntityAMSBase) {
				return new GUIAMSBase(player.inventory, (TileEntityAMSBase) entity);
			}
			return null;
		}

		case ModBlocks.guiID_siren: {
			if(entity instanceof TileEntityMachineSiren) {
				return new GUIMachineSiren(player.inventory, (TileEntityMachineSiren) entity);
			}
			return null;
		}

		case ModBlocks.guiID_radgen: {
			if(entity instanceof TileEntityMachineRadGen) {
				return new GUIMachineRadGen(player.inventory, (TileEntityMachineRadGen) entity);
			}
			return null;
		}

		case ModBlocks.guiID_radar: {
			if(entity instanceof TileEntityMachineRadar) {
				return new GUIMachineRadar(player.inventory, (TileEntityMachineRadar) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_solinium: {
			if(entity instanceof TileEntityNukeSolinium) {
				return new GUINukeSolinium(player.inventory, (TileEntityNukeSolinium) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_n2: {
			if(entity instanceof TileEntityNukeN2) {
				return new GUINukeN2(player.inventory, (TileEntityNukeN2) entity);
			}
			return null;
		}

		case ModBlocks.guiID_cel_prime: {
			if(entity instanceof TileEntityCelPrime) {
				return new GUICelPrime(player.inventory, (TileEntityCelPrime) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_selenium: {
			if(entity instanceof TileEntityMachineSeleniumEngine) {
				return new GUIMachineSelenium(player.inventory, (TileEntityMachineSeleniumEngine) entity);
			}
			return null;
		}

		case ModBlocks.guiID_satlinker: {
			if(entity instanceof TileEntityMachineSatLinker) {
				return new GUIMachineSatLinker(player.inventory, (TileEntityMachineSatLinker) entity);
			}
			return null;
		}

		case ModBlocks.guiID_reactor_research: {
			if(entity instanceof TileEntityReactorResearch) {
				return new GUIReactorResearch(player.inventory, (TileEntityReactorResearch) entity);
			}
			return null;
		}

		case ModBlocks.guiID_telelinker: {
			if(entity instanceof TileEntityMachineTeleLinker) {
				return new GUIMachineTeleLinker(player.inventory, (TileEntityMachineTeleLinker) entity);
			}
			return null;
		}

		case ModBlocks.guiID_keyforge: {
			if(entity instanceof TileEntityMachineKeyForge) {
				return new GUIMachineKeyForge(player.inventory, (TileEntityMachineKeyForge) entity);
			}
			return null;
		}

		case ModBlocks.guiID_radiorec: {
			if(entity instanceof TileEntityRadioRec) {
				return new GUIRadioRec(player.inventory, (TileEntityRadioRec) entity);
			}
			return null;
		}

		case ModBlocks.guiID_gascent: {
			if(entity instanceof TileEntityMachineGasCent) {
				return new GUIMachineGasCent(player.inventory, (TileEntityMachineGasCent) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_boiler: {
			if(entity instanceof TileEntityMachineBoiler) {
				return new GUIMachineBoiler(player.inventory, (TileEntityMachineBoiler) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_boiler_electric: {
			if(entity instanceof TileEntityMachineBoilerElectric) {
				return new GUIMachineBoilerElectric(player.inventory, (TileEntityMachineBoilerElectric) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_turbine: {
			if(entity instanceof TileEntityMachineTurbine) {
				return new GUIMachineTurbine(player.inventory, (TileEntityMachineTurbine) entity);
			}
			return null;
		}

		case ModBlocks.guiID_forcefield: {
			if(entity instanceof TileEntityForceField) {
				return new GUIForceField(player.inventory, (TileEntityForceField) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_n45: {
			if(entity instanceof TileEntityNukeN45) {
				return new GUINukeN45(player.inventory, (TileEntityNukeN45) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_controller: {
			if(entity instanceof TileEntityReactorControl) {
				return new GUIReactorControl(player.inventory, (TileEntityReactorControl) entity);
			}
			return null;
		}

		case ModBlocks.guiID_waste_drum: {
			if(entity instanceof TileEntityWasteDrum) {
				return new GUIWasteDrum(player.inventory, (TileEntityWasteDrum) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dock: {
			if(entity instanceof TileEntityMachineSatDock) {
				return new GUISatDock(player.inventory, (TileEntityMachineSatDock) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_epress: {
			if(entity instanceof TileEntityMachineEPress) {
				return new GUIMachineEPress(player.inventory, (TileEntityMachineEPress) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_arc: {
			if(entity instanceof TileEntityMachineArcFurnace) {
				return new GUIMachineArcFurnace(player.inventory, (TileEntityMachineArcFurnace) entity);
			}
			return null;
		}

		case ModBlocks.guiID_missile_assembly: {
			if(entity instanceof TileEntityMachineMissileAssembly) {
				return new GUIMachineMissileAssembly(player.inventory, (TileEntityMachineMissileAssembly) entity);
			}
			return null;
		}

		case ModBlocks.guiID_compact_launcher: {
			if(entity instanceof TileEntityCompactLauncher) {
				return new GUIMachineCompactLauncher(player.inventory, (TileEntityCompactLauncher) entity);
			}
			return null;
		}

		case ModBlocks.guiID_launch_table: {
			if(entity instanceof TileEntityLaunchTable) {
				return new GUIMachineLaunchTable(player.inventory, (TileEntityLaunchTable) entity);
			}
			return null;
		}

		case ModBlocks.guiID_soyuz_launcher: {
			if(entity instanceof TileEntitySoyuzLauncher) {
				return new GUISoyuzLauncher(player.inventory, (TileEntitySoyuzLauncher) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dfc_emitter: {
			if(entity instanceof TileEntityCoreEmitter) {
				return new GUICoreEmitter(player.inventory, (TileEntityCoreEmitter) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dfc_receiver: {
			if(entity instanceof TileEntityCoreReceiver) {
				return new GUICoreReceiver(player.inventory, (TileEntityCoreReceiver) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dfc_injector: {
			if(entity instanceof TileEntityCoreInjector) {
				return new GUICoreInjector(player.inventory, (TileEntityCoreInjector) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dfc_core: {
			if(entity instanceof TileEntityCore) {
				return new GUICore(player.inventory, (TileEntityCore) entity);
			}
			return null;
		}

		case ModBlocks.guiID_dfc_stabilizer: {
			if(entity instanceof TileEntityCoreStabilizer) {
				return new GUICoreStabilizer(player.inventory, (TileEntityCoreStabilizer) entity);
			}
			return null;
		}

		case ModBlocks.guiID_barrel: {
			if(entity instanceof TileEntityBarrel) {
				return new GUIBarrel(player.inventory, (TileEntityBarrel) entity);
			}
			return null;
		}

		case ModBlocks.guiID_capsule: {
			if(entity instanceof TileEntitySoyuzCapsule) {
				return new GUISoyuzCapsule(player.inventory, (TileEntitySoyuzCapsule) entity);
			}
			return null;
		}

		case ModBlocks.guiID_crystallizer: {
			if(entity instanceof TileEntityMachineCrystallizer) {
				return new GUICrystallizer(player.inventory, (TileEntityMachineCrystallizer) entity);
			}
			return null;
		}

		case ModBlocks.guiID_mining_laser: {
			if(entity instanceof TileEntityMachineMiningLaser) {
				return new GUIMiningLaser(player.inventory, (TileEntityMachineMiningLaser) entity);
			}
			return null;
		}

		case ModBlocks.guiID_nuke_fstbmb: {
			if(entity instanceof TileEntityNukeBalefire) {
				return new GUINukeFstbmb(player.inventory, (TileEntityNukeBalefire) entity);
			}
			return null;
		}

		case ModBlocks.guiID_microwave: {
			if(entity instanceof TileEntityMicrowave) {
				return new GUIMicrowave(player.inventory, (TileEntityMicrowave) entity);
			}
			return null;
		}

		case ModBlocks.guiID_iter: {
			if(entity instanceof TileEntityITER) {
				return new GUIITER(player.inventory, (TileEntityITER) entity);
			}
			return null;
		}

		case ModBlocks.guiID_plasma_heater: {
			if(entity instanceof TileEntityMachinePlasmaHeater) {
				return new GUIPlasmaHeater(player.inventory, (TileEntityMachinePlasmaHeater) entity);
			}
			return null;
		}

		case ModBlocks.guiID_machine_large_turbine: {
			if(entity instanceof TileEntityMachineLargeTurbine) {
				return new GUIMachineLargeTurbine(player.inventory, (TileEntityMachineLargeTurbine) entity);
			}
			return null;
		}

		case ModBlocks.guiID_hadron: {
			if(entity instanceof TileEntityHadron) {
				return new GUIHadron(player.inventory, (TileEntityHadron) entity);
			}
			return null;
		}

		case ModBlocks.guiID_armor_table: {
			if(world.getBlock(x, y, z) == ModBlocks.machine_armor_table) {
				return new GUIArmorTable(player.inventory);
			}
			return null;
		}

		case ModBlocks.guiID_chekhov: {
			if(entity instanceof TileEntityTurretChekhov) {
				return new GUITurretChekhov(player.inventory, (TileEntityTurretChekhov) entity);
			}
			return null;
		}

		case ModBlocks.guiID_friendly: {
			if(entity instanceof TileEntityTurretFriendly) {
				return new GUITurretFriendly(player.inventory, (TileEntityTurretFriendly) entity);
			}
			return null;
		}

		case ModBlocks.guiID_jeremy: {
			if(entity instanceof TileEntityTurretJeremy) {
				return new GUITurretJeremy(player.inventory, (TileEntityTurretJeremy) entity);
			}
			return null;
		}

		case ModBlocks.guiID_tauon: {
			if(entity instanceof TileEntityTurretTauon) {
				return new GUITurretTauon(player.inventory, (TileEntityTurretTauon) entity);
			}
			return null;
		}

		case ModBlocks.guiID_richard: {
			if(entity instanceof TileEntityTurretRichard) {
				return new GUITurretRichard(player.inventory, (TileEntityTurretRichard) entity);
			}
			return null;
		}

		case ModBlocks.guiID_howard: {
			if(entity instanceof TileEntityTurretHoward) {
				return new GUITurretHoward(player.inventory, (TileEntityTurretHoward) entity);
			}
			return null;
		}

		case ModBlocks.guiID_silex: {
			if(entity instanceof TileEntitySILEX) {
				return new GUISILEX(player.inventory, (TileEntitySILEX) entity);
			}
			return null;
		}

		case ModBlocks.guiID_fel: {
			if(entity instanceof TileEntityFEL) {
				return new GUIFEL(player.inventory, (TileEntityFEL) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_rod: {
			if(entity instanceof TileEntityRBMKRod) {
				return new GUIRBMKRod(player.inventory, (TileEntityRBMKRod) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_boiler: {
			if(entity instanceof TileEntityRBMKBoiler) {
				return new GUIRBMKBoiler(player.inventory, (TileEntityRBMKBoiler) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_control: {
			if(entity instanceof TileEntityRBMKControlManual) {
				return new GUIRBMKControl(player.inventory, (TileEntityRBMKControlManual) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_control_auto: {
			if(entity instanceof TileEntityRBMKControlAuto) {
				return new GUIRBMKControlAuto(player.inventory, (TileEntityRBMKControlAuto) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_console: {
			if(entity instanceof TileEntityRBMKConsole) {
				return new GUIRBMKConsole(player.inventory, (TileEntityRBMKConsole) entity);
			}
			return null;
		}

		case ModBlocks.guiID_storage_drum: {
			if(entity instanceof TileEntityStorageDrum) {
				return new GUIStorageDrum(player.inventory, (TileEntityStorageDrum) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_outgasser: {
			if(entity instanceof TileEntityRBMKOutgasser) {
				return new GUIRBMKOutgasser(player.inventory, (TileEntityRBMKOutgasser) entity);
			}
			return null;
		}

		case ModBlocks.guiID_maxwell: {
			if(entity instanceof TileEntityTurretMaxwell) {
				return new GUITurretMaxwell(player.inventory, (TileEntityTurretMaxwell) entity);
			}
			return null;
		}

		case ModBlocks.guiID_anvil: {
			if(world.getBlock(x, y, z) instanceof NTMAnvil) {
				return new GUIAnvil(player.inventory, ((NTMAnvil)world.getBlock(x, y, z)).tier);
			}
			return null;
		}

		case ModBlocks.guiID_fritz: {
			if(entity instanceof TileEntityTurretFritz) {
				return new GUITurretFritz(player.inventory, (TileEntityTurretFritz) entity);
			}
			return null;
		}

		case ModBlocks.guiID_rbmk_storage: {
			if(entity instanceof TileEntityRBMKStorage) {
				return new GUIRBMKStorage(player.inventory, (TileEntityRBMKStorage) entity);
			}
			return null;
		}
		
		case ModBlocks.guiID_rtg_difurnace: {
			if (entity instanceof TileEntityDiFurnaceRTG) {
				return new GUIMachineDiFurnaceRTG(player.inventory, (TileEntityDiFurnaceRTG) entity);
			}
			return null;
		}
		
		case ModBlocks.guiID_reactor_zirnox: {
			if(entity instanceof TileEntityReactorZirnox) {
				return new GUIReactorZirnox(player.inventory, (TileEntityReactorZirnox) entity);
			}
			return null;
		}
		}
		// ITEM GUIS

		switch(ID) {
		case ModItems.guiID_item_folder:
			return new GUIScreenTemplateFolder(player);
		case ModItems.guiID_item_designator:
			return new GUIScreenDesignator(player);
		case ModItems.guiID_item_sat_interface:
			return new GUIScreenSatInterface(player);
		case ModItems.guiID_item_sat_coord:
			return new GUIScreenSatCoord(player);
		case ModItems.guiID_item_box:
			return new GUILeadBox(new ContainerLeadBox(player, player.inventory, new InventoryLeadBox(player.getHeldItem())));
		case ModItems.guiID_item_bobmazon:
			if(BobmazonOfferFactory.getOffers(player.getHeldItem()) != null)
				return new GUIScreenBobmazon(player, BobmazonOfferFactory.getOffers(player.getHeldItem()));
		case ModItems.guiID_item_book:
			return new GUIBook(player.inventory);
		case ModItems.guiID_item_guide:
			return new GUIScreenGuide(player);
		case ModItems.guiID_item_bobble:
			return new GUIScreenBobble((TileEntityBobble) world.getTileEntity(x, y, z));
		case ModItems.guiID_item_holo_image:
			return new GUIScreenHolotape();
		case ModItems.guiID_item_fluid:
			return new GUIScreenFluid(player);
			
		case ModItems.guiID_cart_crate: return new GUICrateSteel(player.inventory, (EntityMinecartCrate) player.worldObj.getEntityByID(x));
		case ModItems.guiID_cart_destroyer: return new GUICartDestroyer(player.inventory, (EntityMinecartDestroyer) player.worldObj.getEntityByID(x));
		}
		return null;
	}

}
