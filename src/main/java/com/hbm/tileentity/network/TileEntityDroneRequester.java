package com.hbm.tileentity.network;

import com.hbm.inventory.container.ContainerDroneRequester;
import com.hbm.inventory.gui.GUIDroneRequester;
import com.hbm.module.ModulePatternMatcher;
import com.hbm.tileentity.IGUIProvider;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityDroneRequester extends TileEntityRequestNetworkContainer implements IGUIProvider {
	
	public ModulePatternMatcher matcher;

	public TileEntityDroneRequester() {
		super(18);
		this.matcher = new ModulePatternMatcher(9);
	}

	@Override
	public String getName() {
		return "container.droneRequester";
	}
	
	public boolean matchesFilter(ItemStack stack) {
		
		for(int i = 0; i < 9; i++) {
			ItemStack filter = slots[i];
			
			if(filter != null && this.matcher.isValidForFilter(filter, i, stack)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void nextMode(int i) {
		this.matcher.nextMode(worldObj, slots[i], i);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return false;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return true;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerDroneRequester(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIDroneRequester(player.inventory, this);
	}
}
