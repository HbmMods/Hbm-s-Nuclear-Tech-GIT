package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerFurnaceIron;
import com.hbm.inventory.gui.GUIFurnaceIron;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityFurnaceIron extends TileEntityMachineBase implements IGUIProvider {

	public TileEntityFurnaceIron() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.furnaceIron";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceIron(player.inventory, this);
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceIron(player.inventory, this);
	}
}
