package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerBombMulti;
import com.hbm.inventory.container.ContainerCentrifuge;
import com.hbm.inventory.container.ContainerConverterHeRf;
import com.hbm.inventory.container.ContainerConverterRfHe;
import com.hbm.inventory.container.ContainerCoreAdvanced;
import com.hbm.inventory.container.ContainerCoreTitanium;
import com.hbm.inventory.container.ContainerDiFurnace;
import com.hbm.inventory.container.ContainerElectricFurnace;
import com.hbm.inventory.container.ContainerFWatzCore;
import com.hbm.inventory.container.ContainerFusionMultiblock;
import com.hbm.inventory.container.ContainerGenerator;
import com.hbm.inventory.container.ContainerIGenerator;
import com.hbm.inventory.container.ContainerLaunchPadTier1;
import com.hbm.inventory.container.ContainerMachineBattery;
import com.hbm.inventory.container.ContainerMachineCMBFactory;
import com.hbm.inventory.container.ContainerMachineCoal;
import com.hbm.inventory.container.ContainerMachineCyclotron;
import com.hbm.inventory.container.ContainerMachineDeuterium;
import com.hbm.inventory.container.ContainerMachineDiesel;
import com.hbm.inventory.container.ContainerMachineRTG;
import com.hbm.inventory.container.ContainerMachineSchrabidiumTransmutator;
import com.hbm.inventory.container.ContainerMachineShredder;
import com.hbm.inventory.container.ContainerMachineTeleporter;
import com.hbm.inventory.container.ContainerNukeBoy;
import com.hbm.inventory.container.ContainerNukeCustom;
import com.hbm.inventory.container.ContainerNukeFleija;
import com.hbm.inventory.container.ContainerNukeFurnace;
import com.hbm.inventory.container.ContainerNukeGadget;
import com.hbm.inventory.container.ContainerNukeMan;
import com.hbm.inventory.container.ContainerNukeMike;
import com.hbm.inventory.container.ContainerNukePrototype;
import com.hbm.inventory.container.ContainerNukeTsar;
import com.hbm.inventory.container.ContainerPuF6Tank;
import com.hbm.inventory.container.ContainerReactor;
import com.hbm.inventory.container.ContainerReactorMultiblock;
import com.hbm.inventory.container.ContainerReiXMainframe;
import com.hbm.inventory.container.ContainerRtgFurnace;
import com.hbm.inventory.container.ContainerTestNuke;
import com.hbm.inventory.container.ContainerUF6Tank;
import com.hbm.inventory.container.ContainerWatzCore;
import com.hbm.inventory.gui.GUIBombMulti;
import com.hbm.inventory.gui.GUIConverterHeRf;
import com.hbm.inventory.gui.GUIConverterRfHe;
import com.hbm.inventory.gui.GUICoreAdvanced;
import com.hbm.inventory.gui.GUICoreTitanium;
import com.hbm.inventory.gui.GUIFWatzCore;
import com.hbm.inventory.gui.GUIFusionMultiblock;
import com.hbm.inventory.gui.GUIIGenerator;
import com.hbm.inventory.gui.GUILaunchPadTier1;
import com.hbm.inventory.gui.GUIMachineBattery;
import com.hbm.inventory.gui.GUIMachineCMBFactory;
import com.hbm.inventory.gui.GUIMachineCentrifuge;
import com.hbm.inventory.gui.GUIMachineCoal;
import com.hbm.inventory.gui.GUIMachineCyclotron;
import com.hbm.inventory.gui.GUIMachineDeuterium;
import com.hbm.inventory.gui.GUIMachineDiesel;
import com.hbm.inventory.gui.GUIMachineElectricFurnace;
import com.hbm.inventory.gui.GUIMachineGenerator;
import com.hbm.inventory.gui.GUIMachinePuF6Tank;
import com.hbm.inventory.gui.GUIMachineRTG;
import com.hbm.inventory.gui.GUIMachineReactor;
import com.hbm.inventory.gui.GUIMachineSchrabidiumTransmutator;
import com.hbm.inventory.gui.GUIMachineShredder;
import com.hbm.inventory.gui.GUIMachineTeleporter;
import com.hbm.inventory.gui.GUIMachineUF6Tank;
import com.hbm.inventory.gui.GUINukeBoy;
import com.hbm.inventory.gui.GUINukeCustom;
import com.hbm.inventory.gui.GUINukeFleija;
import com.hbm.inventory.gui.GUINukeFurnace;
import com.hbm.inventory.gui.GUINukeGadget;
import com.hbm.inventory.gui.GUINukeMan;
import com.hbm.inventory.gui.GUINukeMike;
import com.hbm.inventory.gui.GUINukePrototype;
import com.hbm.inventory.gui.GUINukeTsar;
import com.hbm.inventory.gui.GUIReactorMultiblock;
import com.hbm.inventory.gui.GUIReiXMainframe;
import com.hbm.inventory.gui.GUIRtgFurnace;
import com.hbm.inventory.gui.GUITestDiFurnace;
import com.hbm.inventory.gui.GUITestNuke;
import com.hbm.inventory.gui.GUIWatzCore;
import com.hbm.tileentity.TileEntityBombMulti;
import com.hbm.tileentity.TileEntityConverterHeRf;
import com.hbm.tileentity.TileEntityConverterRfHe;
import com.hbm.tileentity.TileEntityCoreAdvanced;
import com.hbm.tileentity.TileEntityCoreTitanium;
import com.hbm.tileentity.TileEntityDiFurnace;
import com.hbm.tileentity.TileEntityFWatzCore;
import com.hbm.tileentity.TileEntityFusionMultiblock;
import com.hbm.tileentity.TileEntityMachineIGenerator;
import com.hbm.tileentity.TileEntityLaunchPad;
import com.hbm.tileentity.TileEntityMachineBattery;
import com.hbm.tileentity.TileEntityMachineCMBFactory;
import com.hbm.tileentity.TileEntityMachineCentrifuge;
import com.hbm.tileentity.TileEntityMachineCoal;
import com.hbm.tileentity.TileEntityMachineCyclotron;
import com.hbm.tileentity.TileEntityMachineDeuterium;
import com.hbm.tileentity.TileEntityMachineDiesel;
import com.hbm.tileentity.TileEntityMachineElectricFurnace;
import com.hbm.tileentity.TileEntityMachineGenerator;
import com.hbm.tileentity.TileEntityMachinePuF6Tank;
import com.hbm.tileentity.TileEntityMachineRTG;
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
			}
		}
		return null;
	}

}
