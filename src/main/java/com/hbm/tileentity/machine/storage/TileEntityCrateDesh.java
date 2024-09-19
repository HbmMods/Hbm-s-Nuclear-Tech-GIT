package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerCrateDesh;
import com.hbm.inventory.gui.GUICrateDesh;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityCrateDesh extends TileEntityCrateBase {
	
	public TileEntityCrateDesh() {
		super(104); //8 rows with 13 slots
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.crateDesh";
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrateDesh(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrateDesh(player.inventory, this);
	}
}
