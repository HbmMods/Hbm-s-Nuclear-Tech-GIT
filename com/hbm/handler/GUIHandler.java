package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.gui.container.ContainerBombMulti;
import com.hbm.gui.container.ContainerCentrifuge;
import com.hbm.gui.container.ContainerConverterHeRf;
import com.hbm.gui.container.ContainerConverterRfHe;
import com.hbm.gui.container.ContainerCoreAdvanced;
import com.hbm.gui.container.ContainerCoreTitanium;
import com.hbm.gui.container.ContainerDiFurnace;
import com.hbm.gui.container.ContainerElectricFurnace;
import com.hbm.gui.container.ContainerFWatzCore;
import com.hbm.gui.container.ContainerFusionMultiblock;
import com.hbm.gui.container.ContainerGenerator;
import com.hbm.gui.container.ContainerIGenerator;
import com.hbm.gui.container.ContainerLaunchPadTier1;
import com.hbm.gui.container.ContainerMachineBattery;
import com.hbm.gui.container.ContainerMachineCMBFactory;
import com.hbm.gui.container.ContainerMachineCoal;
import com.hbm.gui.container.ContainerMachineDeuterium;
import com.hbm.gui.container.ContainerMachineDiesel;
import com.hbm.gui.container.ContainerMachineSchrabidiumTransmutator;
import com.hbm.gui.container.ContainerMachineShredder;
import com.hbm.gui.container.ContainerMachineTeleporter;
import com.hbm.gui.container.ContainerNukeBoy;
import com.hbm.gui.container.ContainerNukeCustom;
import com.hbm.gui.container.ContainerNukeFleija;
import com.hbm.gui.container.ContainerNukeFurnace;
import com.hbm.gui.container.ContainerNukeGadget;
import com.hbm.gui.container.ContainerNukeMan;
import com.hbm.gui.container.ContainerNukeMike;
import com.hbm.gui.container.ContainerNukePrototype;
import com.hbm.gui.container.ContainerNukeTsar;
import com.hbm.gui.container.ContainerPuF6Tank;
import com.hbm.gui.container.ContainerReactor;
import com.hbm.gui.container.ContainerReactorMultiblock;
import com.hbm.gui.container.ContainerReiXMainframe;
import com.hbm.gui.container.ContainerRtgFurnace;
import com.hbm.gui.container.ContainerTestNuke;
import com.hbm.gui.container.ContainerUF6Tank;
import com.hbm.gui.container.ContainerWatzCore;
import com.hbm.gui.gui.GUIBombMulti;
import com.hbm.gui.gui.GUIConverterHeRf;
import com.hbm.gui.gui.GUIConverterRfHe;
import com.hbm.gui.gui.GUICoreAdvanced;
import com.hbm.gui.gui.GUICoreTitanium;
import com.hbm.gui.gui.GUIFWatzCore;
import com.hbm.gui.gui.GUIFusionMultiblock;
import com.hbm.gui.gui.GUIIGenerator;
import com.hbm.gui.gui.GUILaunchPadTier1;
import com.hbm.gui.gui.GUIMachineBattery;
import com.hbm.gui.gui.GUIMachineCMBFactory;
import com.hbm.gui.gui.GUIMachineCentrifuge;
import com.hbm.gui.gui.GUIMachineCoal;
import com.hbm.gui.gui.GUIMachineDeuterium;
import com.hbm.gui.gui.GUIMachineDiesel;
import com.hbm.gui.gui.GUIMachineElectricFurnace;
import com.hbm.gui.gui.GUIMachineGenerator;
import com.hbm.gui.gui.GUIMachinePuF6Tank;
import com.hbm.gui.gui.GUIMachineReactor;
import com.hbm.gui.gui.GUIMachineSchrabidiumTransmutator;
import com.hbm.gui.gui.GUIMachineShredder;
import com.hbm.gui.gui.GUIMachineTeleporter;
import com.hbm.gui.gui.GUIMachineUF6Tank;
import com.hbm.gui.gui.GUINukeBoy;
import com.hbm.gui.gui.GUINukeCustom;
import com.hbm.gui.gui.GUINukeFleija;
import com.hbm.gui.gui.GUINukeFurnace;
import com.hbm.gui.gui.GUINukeGadget;
import com.hbm.gui.gui.GUINukeMan;
import com.hbm.gui.gui.GUINukeMike;
import com.hbm.gui.gui.GUINukePrototype;
import com.hbm.gui.gui.GUINukeTsar;
import com.hbm.gui.gui.GUIReactorMultiblock;
import com.hbm.gui.gui.GUIReiXMainframe;
import com.hbm.gui.gui.GUIRtgFurnace;
import com.hbm.gui.gui.GUITestDiFurnace;
import com.hbm.gui.gui.GUITestNuke;
import com.hbm.gui.gui.GUIWatzCore;
import com.hbm.tileentity.TileEntityBombMulti;
import com.hbm.tileentity.TileEntityConverterHeRf;
import com.hbm.tileentity.TileEntityConverterRfHe;
import com.hbm.tileentity.TileEntityCoreAdvanced;
import com.hbm.tileentity.TileEntityCoreTitanium;
import com.hbm.tileentity.TileEntityDiFurnace;
import com.hbm.tileentity.TileEntityFWatzCore;
import com.hbm.tileentity.TileEntityFusionMultiblock;
import com.hbm.tileentity.TileEntityIGenerator;
import com.hbm.tileentity.TileEntityLaunchPad;
import com.hbm.tileentity.TileEntityMachineBattery;
import com.hbm.tileentity.TileEntityMachineCMBFactory;
import com.hbm.tileentity.TileEntityMachineCentrifuge;
import com.hbm.tileentity.TileEntityMachineCoal;
import com.hbm.tileentity.TileEntityMachineDeuterium;
import com.hbm.tileentity.TileEntityMachineDiesel;
import com.hbm.tileentity.TileEntityMachineElectricFurnace;
import com.hbm.tileentity.TileEntityMachineGenerator;
import com.hbm.tileentity.TileEntityMachinePuF6Tank;
import com.hbm.tileentity.TileEntityMachineReactor;
import com.hbm.tileentity.TileEntityMachineSchrabidiumTransmutator;
import com.hbm.tileentity.TileEntityMachineShredder;
import com.hbm.tileentity.TileEntityMachineTeleporter;
import com.hbm.tileentity.TileEntityMachineUF6Tank;
import com.hbm.tileentity.TileEntityNukeBoy;
import com.hbm.tileentity.TileEntityNukeCustom;
import com.hbm.tileentity.TileEntityNukeFleija;
import com.hbm.tileentity.TileEntityNukeFurnace;
import com.hbm.tileentity.TileEntityNukeGadget;
import com.hbm.tileentity.TileEntityNukeMan;
import com.hbm.tileentity.TileEntityNukeMike;
import com.hbm.tileentity.TileEntityNukePrototype;
import com.hbm.tileentity.TileEntityNukeTsar;
import com.hbm.tileentity.TileEntityReactorMultiblock;
import com.hbm.tileentity.TileEntityReiXMainframe;
import com.hbm.tileentity.TileEntityRtgFurnace;
import com.hbm.tileentity.TileEntityTestNuke;
import com.hbm.tileentity.TileEntityWatzCore;

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
				if(entity instanceof TileEntityIGenerator)
				{
					return new ContainerIGenerator(player.inventory, (TileEntityIGenerator) entity);
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
					if(entity instanceof TileEntityIGenerator)
					{
						return new GUIIGenerator(player.inventory, (TileEntityIGenerator) entity);
					}
				}
			}
		}
		return null;
	}

}
