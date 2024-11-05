package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerMachineAmmoPress;
import com.hbm.inventory.gui.GUIMachineAmmoPress;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityMachineAmmoPress extends TileEntityMachineBase implements IGUIProvider {

	public TileEntityMachineAmmoPress() {
		super(10);
	}

	@Override
	public String getName() {
		return "container.machineAmmoPress";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineAmmoPress(player.inventory, this); }
	@Override @SideOnly(Side.CLIENT) public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineAmmoPress(player.inventory, this); }
}
