package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerSafe;
import com.hbm.inventory.gui.GUISafe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.world.World;

public class TileEntitySafe extends TileEntityCrateBase implements ISidedInventory {

	public TileEntitySafe() {
		super(15);
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.safe";
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerSafe(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUISafe(player.inventory, this);
	}
}
