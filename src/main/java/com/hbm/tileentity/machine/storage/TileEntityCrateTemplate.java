package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerCrateTemplate;
import com.hbm.inventory.gui.GUICrateTemplate;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityCrateTemplate extends TileEntityCrateBase {

	public TileEntityCrateTemplate() {
		super(27);
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.crateTemplate";
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCrateTemplate(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICrateTemplate(player.inventory, this);
	}
}
