package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerOreSlopper;
import com.hbm.inventory.gui.GUIOreSlopper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityMachineOreSlopper extends TileEntityMachineBase implements IGUIProvider {

	public TileEntityMachineOreSlopper() {
		super(11);
	}

	@Override
	public String getName() {
		return "container.machineOreSlopper";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerOreSlopper(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIOreSlopper(player.inventory, this);
	}
}
