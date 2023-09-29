package com.hbm.tileentity.network;

import com.hbm.inventory.container.ContainerDroneDock;
import com.hbm.inventory.gui.GUIDroneDock;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.network.RequestNetwork.PathNode;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

public class TileEntityDroneDock extends TileEntityRequestNetworkContainer implements IGUIProvider {

	public TileEntityDroneDock() {
		super(9);
	}

	@Override
	public String getName() {
		return "container.droneDock";
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerDroneDock(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIDroneDock(player.inventory, this);
	}

	@Override
	public PathNode createNode(BlockPos pos) {
		return new PathNode(pos);
	}
}
