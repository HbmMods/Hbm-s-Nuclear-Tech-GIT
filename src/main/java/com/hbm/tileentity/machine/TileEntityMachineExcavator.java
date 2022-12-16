package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerMachineExcavator;
import com.hbm.inventory.gui.GUIMachineExcavator;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityMachineExcavator extends TileEntityMachineBase implements IGUIProvider {

	public boolean enableDrill = false;
	public boolean enableCrusher = false;
	public boolean enableWalling = false;
	public boolean enableVeinMiner = false;
	public boolean enableSilkTouch = false;

	public TileEntityMachineExcavator() {
		super(14);
	}

	@Override
	public String getName() {
		return "container.machineExcavator";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineExcavator(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineExcavator(player.inventory, this);
	}
}
