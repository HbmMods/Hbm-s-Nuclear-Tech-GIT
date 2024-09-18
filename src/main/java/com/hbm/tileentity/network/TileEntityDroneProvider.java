package com.hbm.tileentity.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.inventory.container.ContainerDroneProvider;
import com.hbm.inventory.gui.GUIDroneProvider;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.network.RequestNetwork.OfferNode;
import com.hbm.tileentity.network.RequestNetwork.PathNode;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityDroneProvider extends TileEntityRequestNetworkContainer implements IGUIProvider {

	public TileEntityDroneProvider() {
		super(9);
	}

	@Override
	public String getName() {
		return "container.droneProvider";
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return true;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return false;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerDroneProvider(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIDroneProvider(player.inventory, this);
	}

	@Override
	public PathNode createNode(BlockPos pos) {
		List<ItemStack> offer = new ArrayList();
		for(ItemStack stack : slots) if(stack != null) offer.add(stack.copy());
		return new OfferNode(pos, this.reachableNodes, offer);
	}
}
