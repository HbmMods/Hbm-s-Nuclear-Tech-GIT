package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.*;
import com.hbm.inventory.gui.*;
import com.hbm.items.ModItems;
import com.hbm.tileentity.*;
import com.hbm.tileentity.bomb.TileEntityBombMulti;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityNukeBoy;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeGadget;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.tileentity.bomb.TileEntityNukeMike;
import com.hbm.tileentity.bomb.TileEntityNukePrototype;
import com.hbm.tileentity.bomb.TileEntityNukeTsar;
import com.hbm.tileentity.bomb.TileEntityTestNuke;
import com.hbm.tileentity.machine.TileEntityConverterHeRf;
import com.hbm.tileentity.machine.TileEntityConverterRfHe;
import com.hbm.tileentity.machine.TileEntityCoreAdvanced;
import com.hbm.tileentity.machine.TileEntityCoreTitanium;
import com.hbm.tileentity.machine.TileEntityCrateIron;
import com.hbm.tileentity.machine.TileEntityCrateSteel;
import com.hbm.tileentity.machine.TileEntityDiFurnace;
import com.hbm.tileentity.machine.TileEntityFWatzCore;
import com.hbm.tileentity.machine.TileEntityFusionMultiblock;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineBattery;
import com.hbm.tileentity.machine.TileEntityMachineCMBFactory;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachineCoal;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;
import com.hbm.tileentity.machine.TileEntityMachineDeuterium;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;
import com.hbm.tileentity.machine.TileEntityMachineElectricFurnace;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;
import com.hbm.tileentity.machine.TileEntityMachineGasFlare;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;
import com.hbm.tileentity.machine.TileEntityMachineIGenerator;
import com.hbm.tileentity.machine.TileEntityMachineInserter;
import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;
import com.hbm.tileentity.machine.TileEntityMachineOilWell;
import com.hbm.tileentity.machine.TileEntityMachinePuF6Tank;
import com.hbm.tileentity.machine.TileEntityMachinePumpjack;
import com.hbm.tileentity.machine.TileEntityMachineRTG;
import com.hbm.tileentity.machine.TileEntityMachineReactor;
import com.hbm.tileentity.machine.TileEntityMachineRefinery;
import com.hbm.tileentity.machine.TileEntityMachineSchrabidiumTransmutator;
import com.hbm.tileentity.machine.TileEntityMachineShredder;
import com.hbm.tileentity.machine.TileEntityMachineTeleporter;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;
import com.hbm.tileentity.machine.TileEntityMachineUF6Tank;
import com.hbm.tileentity.machine.TileEntityNukeFurnace;
import com.hbm.tileentity.machine.TileEntityReactorMultiblock;
import com.hbm.tileentity.machine.TileEntityReiXMainframe;
import com.hbm.tileentity.machine.TileEntityRtgFurnace;
import com.hbm.tileentity.machine.TileEntityWatzCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		switch(ID)
		{
			case ModBlocks.guiID_test_difurnace:
			{
				if(entity instanceof TileEntityDiFurnace)
				{
					return new ContainerDiFurnace(player.inventory, (TileEntityDiFurnace) entity);
				}
				return null;
			}
			
			case ModBlocks.guiID_test_nuke:
			{
				if(entity instanceof TileEntityTestNuke)
				{
					return new ContainerTestNuke(player.inventory, (TileEntityTestNuke) entity);
				}
				return null;
			}
			
			case ModBlocks.guiID_nuke_gadget:
			{
				if(entity instanceof TileEntityNukeGadget)
				{
					return new ContainerNukeGadget(player.inventory, (TileEntityNukeGadget) entity);
				}
			}
			
			case ModBlocks.guiID_nuke_boy:
			{
				if(entity instanceof TileEntityNukeBoy)
				{
					return new ContainerNukeBoy(player.inventory, (TileEntityNukeBoy) entity);
				}
			}
			
			case ModBlocks.guiID_centrifuge:
			{
				if(entity instanceof TileEntityMachineCentrifuge)
				{
					return new ContainerCentrifuge(player.inventory, (TileEntityMachineCentrifuge) entity);
				}
			}
			
			case ModBlocks.guiID_nuke_man:
			{
				if(entity instanceof TileEntityNukeMan)
				{
					return new ContainerNukeMan(player.inventory, (TileEntityNukeMan) entity);
				}
			}
			
			case ModBlocks.guiID_uf6_tank:
			{
				if(entity instanceof TileEntityMachineUF6Tank)
				{
					return new ContainerUF6Tank(player.inventory, (TileEntityMachineUF6Tank) entity);
				}
			}
			
			case ModBlocks.guiID_puf6_tank:
			{
				if(entity instanceof TileEntityMachinePuF6Tank)
				{
					return new ContainerPuF6Tank(player.inventory, (TileEntityMachinePuF6Tank) entity);
				}
			}
			
			case ModBlocks.guiID_reactor:
			{
				if(entity instanceof TileEntityMachineReactor)
				{
					return new ContainerReactor(player.inventory, (TileEntityMachineReactor) entity);
				}
			}
			
			case ModBlocks.guiID_bomb_multi:
			{
				if(entity instanceof TileEntityBombMulti)
				{
					return new ContainerBombMulti(player.inventory, (TileEntityBombMulti) entity);
				}
			}
			
			case ModBlocks.guiID_nuke_mike:
			{
				if(entity instanceof TileEntityNukeMike)
				{
					return new ContainerNukeMike(player.inventory, (TileEntityNukeMike) entity);
				}
			}
			
			case ModBlocks.guiID_nuke_tsar:
			{
				if(entity instanceof TileEntityNukeTsar)
				{
					return new ContainerNukeTsar(player.inventory, (TileEntityNukeTsar) entity);
				}
			}
			
			case ModBlocks.guiID_nuke_furnace:
			{
				if(entity instanceof TileEntityNukeFurnace)
				{
					return new ContainerNukeFurnace(player.inventory, (TileEntityNukeFurnace) entity);
				}
			}
			
			case ModBlocks.guiID_rtg_furnace:
			{
				if(entity instanceof TileEntityRtgFurnace)
				{
					return new ContainerRtgFurnace(player.inventory, (TileEntityRtgFurnace) entity);
				}
			}
			
			case ModBlocks.guiID_machine_generator:
			{
				if(entity instanceof TileEntityMachineGenerator)
				{
					return new ContainerGenerator(player.inventory, (TileEntityMachineGenerator) entity);
				}
			}
			
			case ModBlocks.guiID_electric_furnace:
			{
				if(entity instanceof TileEntityMachineElectricFurnace)
				{
					return new ContainerElectricFurnace(player.inventory, (TileEntityMachineElectricFurnace) entity);
				}
			}
			
			case ModBlocks.guiID_nuke_fleija:
			{
				if(entity instanceof TileEntityNukeFleija)
				{
					return new ContainerNukeFleija(player.inventory, (TileEntityNukeFleija) entity);
				}
			}
			
			case ModBlocks.guiID_machine_deuterium:
			{
				if(entity instanceof TileEntityMachineDeuterium)
				{
					return new ContainerMachineDeuterium(player.inventory, (TileEntityMachineDeuterium) entity);
				}
			}
			
			case ModBlocks.guiID_machine_battery:
			{
				if(entity instanceof TileEntityMachineBattery)
				{
					return new ContainerMachineBattery(player.inventory, (TileEntityMachineBattery) entity);
				}
			}
			
			case ModBlocks.guiID_machine_coal:
			{
				if(entity instanceof TileEntityMachineCoal)
				{
					return new ContainerMachineCoal(player.inventory, (TileEntityMachineCoal) entity);
				}
			}
			
			case ModBlocks.guiID_nuke_prototype:
			{
				if(entity instanceof TileEntityNukePrototype)
				{
					return new ContainerNukePrototype(player.inventory, (TileEntityNukePrototype) entity);
				}
			}
			
			case ModBlocks.guiID_launch_pad:
			{
				if(entity instanceof TileEntityLaunchPad)
				{
					return new ContainerLaunchPadTier1(player.inventory, (TileEntityLaunchPad) entity);
				}
			}
			
			case ModBlocks.guiID_factory_titanium:
			{
				if(entity instanceof TileEntityCoreTitanium)
				{
					return new ContainerCoreTitanium(player.inventory, (TileEntityCoreTitanium) entity);
				}
			}
			
			case ModBlocks.guiID_factory_advanced:
			{
				if(entity instanceof TileEntityCoreAdvanced)
				{
					return new ContainerCoreAdvanced(player.inventory, (TileEntityCoreAdvanced) entity);
				}
			}
			
			case ModBlocks.guiID_reactor_multiblock:
			{
				if(entity instanceof TileEntityReactorMultiblock)
				{
					return new ContainerReactorMultiblock(player.inventory, (TileEntityReactorMultiblock) entity);
				}
			}
			
			case ModBlocks.guiID_fusion_multiblock:
			{
				if(entity instanceof TileEntityFusionMultiblock)
				{
					return new ContainerFusionMultiblock(player.inventory, (TileEntityFusionMultiblock) entity);
				}
			}
			
			case ModBlocks.guiID_converter_he_rf:
			{
				if(entity instanceof TileEntityConverterHeRf)
				{
					return new ContainerConverterHeRf(player.inventory, (TileEntityConverterHeRf) entity);
				}
			}
			
			case ModBlocks.guiID_converter_rf_he:
			{
				if(entity instanceof TileEntityConverterRfHe)
				{
					return new ContainerConverterRfHe(player.inventory, (TileEntityConverterRfHe) entity);
				}
			}

			case ModBlocks.guiID_schrabidium_transmutator:
			{
				if(entity instanceof TileEntityMachineSchrabidiumTransmutator)
				{
					return new ContainerMachineSchrabidiumTransmutator(player.inventory, (TileEntityMachineSchrabidiumTransmutator) entity);
				}
			}

			case ModBlocks.guiID_machine_diesel:
			{
				if(entity instanceof TileEntityMachineDiesel)
				{
					return new ContainerMachineDiesel(player.inventory, (TileEntityMachineDiesel) entity);
				}
			}

			case ModBlocks.guiID_watz_multiblock:
			{
				if(entity instanceof TileEntityWatzCore)
				{
					return new ContainerWatzCore(player.inventory, (TileEntityWatzCore) entity);
				}
			}

			case ModBlocks.guiID_machine_shredder:
			{
				if(entity instanceof TileEntityMachineShredder)
				{
					return new ContainerMachineShredder(player.inventory, (TileEntityMachineShredder) entity);
				}
			}

			case ModBlocks.guiID_combine_factory:
			{
				if(entity instanceof TileEntityMachineCMBFactory)
				{
					return new ContainerMachineCMBFactory(player.inventory, (TileEntityMachineCMBFactory) entity);
				}
			}

			case ModBlocks.guiID_fwatz_multiblock:
			{
				if(entity instanceof TileEntityFWatzCore)
				{
					return new ContainerFWatzCore(player.inventory, (TileEntityFWatzCore) entity);
				}
			}

			case ModBlocks.guiID_machine_teleporter:
			{
				if(entity instanceof TileEntityMachineTeleporter)
				{
					return new ContainerMachineTeleporter(player.inventory, (TileEntityMachineTeleporter) entity);
				}
			}

			case ModBlocks.guiID_nuke_custom:
			{
				if(entity instanceof TileEntityNukeCustom)
				{
					return new ContainerNukeCustom(player.inventory, (TileEntityNukeCustom) entity);
				}
			}

			case ModBlocks.guiID_machine_reix_mainframe:
			{
				if(entity instanceof TileEntityReiXMainframe)
				{
					return new ContainerReiXMainframe(player.inventory, (TileEntityReiXMainframe) entity);
				}
			}

			case ModBlocks.guiID_machine_industrial_generator:
			{
				if(entity instanceof TileEntityMachineIGenerator)
				{
					return new ContainerIGenerator(player.inventory, (TileEntityMachineIGenerator) entity);
				}
			}

			case ModBlocks.guiID_machine_rtg:
			{
				if(entity instanceof TileEntityMachineRTG)
				{
					return new ContainerMachineRTG(player.inventory, (TileEntityMachineRTG) entity);
				}
			}

			case ModBlocks.guiID_machine_cyclotron:
			{
				if(entity instanceof TileEntityMachineCyclotron)
				{
					return new ContainerMachineCyclotron(player.inventory, (TileEntityMachineCyclotron) entity);
				}
			}

			case ModBlocks.guiID_machine_well:
			{
				if(entity instanceof TileEntityMachineOilWell)
				{
					return new ContainerMachineOilWell(player.inventory, (TileEntityMachineOilWell) entity);
				}
			}

			case ModBlocks.guiID_machine_refinery:
			{
				if(entity instanceof TileEntityMachineRefinery)
				{
					return new ContainerMachineRefinery(player.inventory, (TileEntityMachineRefinery) entity);
				}
			}

			case ModBlocks.guiID_machine_flare:
			{
				if(entity instanceof TileEntityMachineGasFlare)
				{
					return new ContainerMachineGasFlare(player.inventory, (TileEntityMachineGasFlare) entity);
				}
			}

			case ModBlocks.guiID_machine_drill:
			{
				if(entity instanceof TileEntityMachineMiningDrill)
				{
					return new ContainerMachineMiningDrill(player.inventory, (TileEntityMachineMiningDrill) entity);
				}
			}

			case ModBlocks.guiID_machine_assembler:
			{
				if(entity instanceof TileEntityMachineAssembler)
				{
					return new ContainerMachineAssembler(player.inventory, (TileEntityMachineAssembler) entity);
				}
			}

			case ModBlocks.guiID_machine_chemplant:
			{
				if(entity instanceof TileEntityMachineChemplant)
				{
					return new ContainerMachineChemplant(player.inventory, (TileEntityMachineChemplant) entity);
				}
			}

			case ModBlocks.guiID_machine_fluidtank:
			{
				if(entity instanceof TileEntityMachineFluidTank)
				{
					return new ContainerMachineFluidTank(player.inventory, (TileEntityMachineFluidTank) entity);
				}
			}

			case ModBlocks.guiID_machine_pumpjack:
			{
				if(entity instanceof TileEntityMachinePumpjack)
				{
					return new ContainerMachinePumpjack(player.inventory, (TileEntityMachinePumpjack) entity);
				}
			}

			case ModBlocks.guiID_machine_turbofan:
			{
				if(entity instanceof TileEntityMachineTurbofan)
				{
					return new ContainerMachineTurbofan(player.inventory, (TileEntityMachineTurbofan) entity);
				}
			}

			case ModBlocks.guiID_crate_iron:
			{
				if(entity instanceof TileEntityCrateIron)
				{
					return new ContainerCrateIron(player.inventory, (TileEntityCrateIron) entity);
				}
			}

			case ModBlocks.guiID_crate_steel:
			{
				if(entity instanceof TileEntityCrateSteel)
				{
					return new ContainerCrateSteel(player.inventory, (TileEntityCrateSteel) entity);
				}
			}

			case ModBlocks.guiID_machine_inserter:
			{
				if(entity instanceof TileEntityMachineInserter)
				{
					return new ContainerMachineInserter(player.inventory, (TileEntityMachineInserter) entity);
				}
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if(entity != null)
		{
			switch(ID)
			{
				case ModBlocks.guiID_test_difurnace:
				{
					if(entity instanceof TileEntityDiFurnace)
					{
						return new GUITestDiFurnace(player.inventory, (TileEntityDiFurnace) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_test_nuke:
				{
					if(entity instanceof TileEntityTestNuke)
					{
						return new GUITestNuke(player.inventory, (TileEntityTestNuke) entity);
					}
					return null;
				}
				
				case ModBlocks.guiID_nuke_gadget:
				{
					if(entity instanceof TileEntityNukeGadget)
					{
						return new GUINukeGadget(player.inventory, (TileEntityNukeGadget) entity);
					}
				}
				
				case ModBlocks.guiID_nuke_boy:
				{
					if(entity instanceof TileEntityNukeBoy)
					{
						return new GUINukeBoy(player.inventory, (TileEntityNukeBoy) entity);
					}
				}
				
				case ModBlocks.guiID_centrifuge:
				{
					if(entity instanceof TileEntityMachineCentrifuge)
					{
						return new GUIMachineCentrifuge(player.inventory, (TileEntityMachineCentrifuge) entity);
					}
				}
				
				case ModBlocks.guiID_nuke_man:
				{
					if(entity instanceof TileEntityNukeMan)
					{
						return new GUINukeMan(player.inventory, (TileEntityNukeMan) entity);
					}
				}
				
				case ModBlocks.guiID_uf6_tank:
				{
					if(entity instanceof TileEntityMachineUF6Tank)
					{
						return new GUIMachineUF6Tank(player.inventory, (TileEntityMachineUF6Tank) entity);
					}
				}
				
				case ModBlocks.guiID_puf6_tank:
				{
					if(entity instanceof TileEntityMachinePuF6Tank)
					{
						return new GUIMachinePuF6Tank(player.inventory, (TileEntityMachinePuF6Tank) entity);
					}
				}
				
				case ModBlocks.guiID_reactor:
				{
					if(entity instanceof TileEntityMachineReactor)
					{
						return new GUIMachineReactor(player.inventory, (TileEntityMachineReactor) entity);
					}
				}
				
				case ModBlocks.guiID_bomb_multi:
				{
					if(entity instanceof TileEntityBombMulti)
					{
						return new GUIBombMulti(player.inventory, (TileEntityBombMulti) entity);
					}
				}
				
				case ModBlocks.guiID_nuke_mike:
				{
					if(entity instanceof TileEntityNukeMike)
					{
						return new GUINukeMike(player.inventory, (TileEntityNukeMike) entity);
					}
				}
				
				case ModBlocks.guiID_nuke_tsar:
				{
					if(entity instanceof TileEntityNukeTsar)
					{
						return new GUINukeTsar(player.inventory, (TileEntityNukeTsar) entity);
					}
				}
				
				case ModBlocks.guiID_nuke_furnace:
				{
					if(entity instanceof TileEntityNukeFurnace)
					{
						return new GUINukeFurnace(player.inventory, (TileEntityNukeFurnace) entity);
					}
				}
				
				case ModBlocks.guiID_rtg_furnace:
				{
					if(entity instanceof TileEntityRtgFurnace)
					{
						return new GUIRtgFurnace(player.inventory, (TileEntityRtgFurnace) entity);
					}
				}
				
				case ModBlocks.guiID_machine_generator:
				{
					if(entity instanceof TileEntityMachineGenerator)
					{
						return new GUIMachineGenerator(player.inventory, (TileEntityMachineGenerator) entity);
					}
				}
				
				case ModBlocks.guiID_electric_furnace:
				{
					if(entity instanceof TileEntityMachineElectricFurnace)
					{
						return new GUIMachineElectricFurnace(player.inventory, (TileEntityMachineElectricFurnace) entity);
					}
				}
				
				case ModBlocks.guiID_nuke_fleija:
				{
					if(entity instanceof TileEntityNukeFleija)
					{
						return new GUINukeFleija(player.inventory, (TileEntityNukeFleija) entity);
					}
				}
				
				case ModBlocks.guiID_machine_deuterium:
				{
					if(entity instanceof TileEntityMachineDeuterium)
					{
						return new GUIMachineDeuterium(player.inventory, (TileEntityMachineDeuterium) entity);
					}
				}
				
				case ModBlocks.guiID_machine_battery:
				{
					if(entity instanceof TileEntityMachineBattery)
					{
						return new GUIMachineBattery(player.inventory, (TileEntityMachineBattery) entity);
					}
				}
				
				case ModBlocks.guiID_machine_coal:
				{
					if(entity instanceof TileEntityMachineCoal)
					{
						return new GUIMachineCoal(player.inventory, (TileEntityMachineCoal) entity);
					}
				}
				
				case ModBlocks.guiID_nuke_prototype:
				{
					if(entity instanceof TileEntityNukePrototype)
					{
						return new GUINukePrototype(player.inventory, (TileEntityNukePrototype) entity);
					}
				}
				
				case ModBlocks.guiID_launch_pad:
				{
					if(entity instanceof TileEntityLaunchPad)
					{
						return new GUILaunchPadTier1(player.inventory, (TileEntityLaunchPad) entity);
					}
				}
				
				case ModBlocks.guiID_factory_titanium:
				{
					if(entity instanceof TileEntityCoreTitanium)
					{
						return new GUICoreTitanium(player.inventory, (TileEntityCoreTitanium) entity);
					}
				}
				
				case ModBlocks.guiID_factory_advanced:
				{
					if(entity instanceof TileEntityCoreAdvanced)
					{
						return new GUICoreAdvanced(player.inventory, (TileEntityCoreAdvanced) entity);
					}
				}
				
				case ModBlocks.guiID_reactor_multiblock:
				{
					if(entity instanceof TileEntityReactorMultiblock)
					{
						return new GUIReactorMultiblock(player.inventory, (TileEntityReactorMultiblock) entity);
					}
				}
				
				case ModBlocks.guiID_fusion_multiblock:
				{
					if(entity instanceof TileEntityFusionMultiblock)
					{
						return new GUIFusionMultiblock(player.inventory, (TileEntityFusionMultiblock) entity);
					}
				}
				
				case ModBlocks.guiID_converter_he_rf:
				{
					if(entity instanceof TileEntityConverterHeRf)
					{
						return new GUIConverterHeRf(player.inventory, (TileEntityConverterHeRf) entity);
					}
				}
				
				case ModBlocks.guiID_converter_rf_he:
				{
					if(entity instanceof TileEntityConverterRfHe)
					{
						return new GUIConverterRfHe(player.inventory, (TileEntityConverterRfHe) entity);
					}
				}
				
				case ModBlocks.guiID_schrabidium_transmutator:
				{
					if(entity instanceof TileEntityMachineSchrabidiumTransmutator)
					{
						return new GUIMachineSchrabidiumTransmutator(player.inventory, (TileEntityMachineSchrabidiumTransmutator) entity);
					}
				}
				
				case ModBlocks.guiID_machine_diesel:
				{
					if(entity instanceof TileEntityMachineDiesel)
					{
						return new GUIMachineDiesel(player.inventory, (TileEntityMachineDiesel) entity);
					}
				}
				
				case ModBlocks.guiID_watz_multiblock:
				{
					if(entity instanceof TileEntityWatzCore)
					{
						return new GUIWatzCore(player.inventory, (TileEntityWatzCore) entity);
					}
				}
				
				case ModBlocks.guiID_machine_shredder:
				{
					if(entity instanceof TileEntityMachineShredder)
					{
						return new GUIMachineShredder(player.inventory, (TileEntityMachineShredder) entity);
					}
				}
				
				case ModBlocks.guiID_combine_factory:
				{
					if(entity instanceof TileEntityMachineCMBFactory)
					{
						return new GUIMachineCMBFactory(player.inventory, (TileEntityMachineCMBFactory) entity);
					}
				}
				
				case ModBlocks.guiID_fwatz_multiblock:
				{
					if(entity instanceof TileEntityFWatzCore)
					{
						return new GUIFWatzCore(player.inventory, (TileEntityFWatzCore) entity);
					}
				}
				
				case ModBlocks.guiID_machine_teleporter:
				{
					if(entity instanceof TileEntityMachineTeleporter)
					{
						return new GUIMachineTeleporter(player.inventory, (TileEntityMachineTeleporter) entity);
					}
				}
				
				case ModBlocks.guiID_nuke_custom:
				{
					if(entity instanceof TileEntityNukeCustom)
					{
						return new GUINukeCustom(player.inventory, (TileEntityNukeCustom) entity);
					}
				}
				
				case ModBlocks.guiID_machine_reix_mainframe:
				{
					if(entity instanceof TileEntityReiXMainframe)
					{
						return new GUIReiXMainframe(player.inventory, (TileEntityReiXMainframe) entity);
					}
				}
				
				case ModBlocks.guiID_machine_industrial_generator:
				{
					if(entity instanceof TileEntityMachineIGenerator)
					{
						return new GUIIGenerator(player.inventory, (TileEntityMachineIGenerator) entity);
					}
				}
				
				case ModBlocks.guiID_machine_rtg:
				{
					if(entity instanceof TileEntityMachineRTG)
					{
						return new GUIMachineRTG(player.inventory, (TileEntityMachineRTG) entity);
					}
				}
				
				case ModBlocks.guiID_machine_cyclotron:
				{
					if(entity instanceof TileEntityMachineCyclotron)
					{
						return new GUIMachineCyclotron(player.inventory, (TileEntityMachineCyclotron) entity);
					}
				}
				
				case ModBlocks.guiID_machine_well:
				{
					if(entity instanceof TileEntityMachineOilWell)
					{
						return new GUIMachineOilWell(player.inventory, (TileEntityMachineOilWell) entity);
					}
				}
				
				case ModBlocks.guiID_machine_refinery:
				{
					if(entity instanceof TileEntityMachineRefinery)
					{
						return new GUIMachineRefinery(player.inventory, (TileEntityMachineRefinery) entity);
					}
				}
				
				case ModBlocks.guiID_machine_flare:
				{
					if(entity instanceof TileEntityMachineGasFlare)
					{
						return new GUIMachineGasFlare(player.inventory, (TileEntityMachineGasFlare) entity);
					}
				}
				
				case ModBlocks.guiID_machine_drill:
				{
					if(entity instanceof TileEntityMachineMiningDrill)
					{
						return new GUIMachineMiningDrill(player.inventory, (TileEntityMachineMiningDrill) entity);
					}
				}
				
				case ModBlocks.guiID_machine_assembler:
				{
					if(entity instanceof TileEntityMachineAssembler)
					{
						return new GUIMachineAssembler(player.inventory, (TileEntityMachineAssembler) entity);
					}
				}
				
				case ModBlocks.guiID_machine_chemplant:
				{
					if(entity instanceof TileEntityMachineChemplant)
					{
						return new GUIMachineChemplant(player.inventory, (TileEntityMachineChemplant) entity);
					}
				}
				
				case ModBlocks.guiID_machine_fluidtank:
				{
					if(entity instanceof TileEntityMachineFluidTank)
					{
						return new GUIMachineFluidTank(player.inventory, (TileEntityMachineFluidTank) entity);
					}
				}
				
				case ModBlocks.guiID_machine_pumpjack:
				{
					if(entity instanceof TileEntityMachinePumpjack)
					{
						return new GUIMachinePumpjack(player.inventory, (TileEntityMachinePumpjack) entity);
					}
				}
				
				case ModBlocks.guiID_machine_turbofan:
				{
					if(entity instanceof TileEntityMachineTurbofan)
					{
						return new GUIMachineTurbofan(player.inventory, (TileEntityMachineTurbofan) entity);
					}
				}
				
				case ModBlocks.guiID_crate_iron:
				{
					if(entity instanceof TileEntityCrateIron)
					{
						return new GUICrateIron(player.inventory, (TileEntityCrateIron) entity);
					}
				}
				
				case ModBlocks.guiID_crate_steel:
				{
					if(entity instanceof TileEntityCrateSteel)
					{
						return new GUICrateSteel(player.inventory, (TileEntityCrateSteel) entity);
					}
				}
				
				case ModBlocks.guiID_machine_inserter:
				{
					if(entity instanceof TileEntityMachineInserter)
					{
						return new GUIMachineInserter(player.inventory, (TileEntityMachineInserter) entity);
					}
				}
			}
		} else {
			//CLIENTONLY GUIS
			
			switch(ID)
			{
			case ModItems.guiID_item_folder:
				return new GUIScreenTemplateFolder(player);
			}
		}
		return null;
	}

}
