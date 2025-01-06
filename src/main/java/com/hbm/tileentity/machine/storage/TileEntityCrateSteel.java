package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerCrateSteel;
import com.hbm.inventory.gui.GUICrateSteel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityCrateSteel extends TileEntityCrateBase {

	public TileEntityCrateSteel() {
		super(54);
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.crateSteel";
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrateSteel(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrateSteel(player.inventory, this);
	}
}
