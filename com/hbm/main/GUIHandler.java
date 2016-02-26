package com.hbm.main;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.TileEntityBombMulti;
import com.hbm.blocks.TileEntityCoreAdvanced;
import com.hbm.blocks.TileEntityCoreTitanium;
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
import com.hbm.blocks.TileEntityNukeMike;
import com.hbm.blocks.TileEntityNukePrototype;
import com.hbm.blocks.TileEntityNukeTsar;
import com.hbm.blocks.TileEntityReactorMultiblock;
import com.hbm.blocks.TileEntityRtgFurnace;
import com.hbm.blocks.TileEntityTestNuke;
import com.hbm.gui.ContainerBombMulti;
import com.hbm.gui.ContainerDiFurnace;
import com.hbm.gui.ContainerCentrifuge;
import com.hbm.gui.ContainerCoreAdvanced;
import com.hbm.gui.ContainerCoreTitanium;
import com.hbm.gui.ContainerElectricFurnace;
import com.hbm.gui.ContainerGenerator;
import com.hbm.gui.ContainerLaunchPadTier1;
import com.hbm.gui.ContainerMachineBattery;
import com.hbm.gui.ContainerMachineCoal;
import com.hbm.gui.ContainerMachineDeuterium;
import com.hbm.gui.ContainerNukeBoy;
import com.hbm.gui.ContainerNukeFleija;
import com.hbm.gui.ContainerNukeFurnace;
import com.hbm.gui.ContainerNukeGadget;
import com.hbm.gui.ContainerNukeMan;
import com.hbm.gui.ContainerNukeMike;
import com.hbm.gui.ContainerNukePrototype;
import com.hbm.gui.ContainerNukeTsar;
import com.hbm.gui.ContainerPuF6Tank;
import com.hbm.gui.ContainerReactor;
import com.hbm.gui.ContainerReactorMultiblock;
import com.hbm.gui.ContainerRtgFurnace;
import com.hbm.gui.ContainerTestNuke;
import com.hbm.gui.ContainerUF6Tank;
import com.hbm.gui.GUIBombMulti;
import com.hbm.gui.GUICoreAdvanced;
import com.hbm.gui.GUICoreTitanium;
import com.hbm.gui.GUILaunchPadTier1;
import com.hbm.gui.GUIMachineBattery;
import com.hbm.gui.GUIMachineCentrifuge;
import com.hbm.gui.GUIMachineCoal;
import com.hbm.gui.GUIMachineDeuterium;
import com.hbm.gui.GUIMachineElectricFurnace;
import com.hbm.gui.GUIMachineGenerator;
import com.hbm.gui.GUIMachinePuF6Tank;
import com.hbm.gui.GUIMachineReactor;
import com.hbm.gui.GUIMachineUF6Tank;
import com.hbm.gui.GUINukeBoy;
import com.hbm.gui.GUINukeFleija;
import com.hbm.gui.GUINukeFurnace;
import com.hbm.gui.GUINukeGadget;
import com.hbm.gui.GUINukeMan;
import com.hbm.gui.GUINukeMike;
import com.hbm.gui.GUINukePrototype;
import com.hbm.gui.GUINukeTsar;
import com.hbm.gui.GUIReactorMultiblock;
import com.hbm.gui.GUIRtgFurnace;
import com.hbm.gui.GUITestDiFurnace;
import com.hbm.gui.GUITestNuke;

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
			}
		}
		return null;
	}

}
