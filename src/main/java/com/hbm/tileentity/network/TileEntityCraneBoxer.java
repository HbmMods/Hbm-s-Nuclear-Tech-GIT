package com.hbm.tileentity.network;

import com.hbm.inventory.container.ContainerCraneBoxer;
import com.hbm.inventory.gui.GUICraneBoxer;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityCraneBoxer extends TileEntityMachineBase implements IGUIProvider {

	public TileEntityCraneBoxer() {
		super(7 * 3);
	}

	@Override
	public String getName() {
		return "container.craneBoxer";
	}

	@Override
	public void updateEntity() {
		
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerCraneBoxer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUICraneBoxer(player.inventory, this);
	}
}
