package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerMachineSatLinker;
import com.hbm.inventory.gui.GUIMachineSatLinker;
import com.hbm.items.ISatChip;
import com.hbm.saveddata.SatelliteSavedData;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class TileEntityMachineSatLinker extends TileEntityMachineBase implements IGUIProvider {
	
	public TileEntityMachineSatLinker() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.satLinker";
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return stack.getItem() instanceof ISatChip;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return ISatChip.getFreqS(slots[0]) == ISatChip.getFreqS(slots[1]);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 1 };
	}

	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			if(slots[0] != null && slots[1] != null && slots[0].getItem() instanceof ISatChip && slots[1].getItem() instanceof ISatChip) {
				ISatChip.setFreqS(slots[1], ISatChip.getFreqS(slots[0]));
			}
			
			if(slots[2] != null && slots[2].getItem() instanceof ISatChip) {
				SatelliteSavedData satelliteData = SatelliteSavedData.getData(worldObj);
				int newId = worldObj.rand.nextInt(100000);
				if(!satelliteData.isFreqTaken(newId)) {
					ISatChip.setFreqS(slots[2], newId);
				}
			}
		}
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerMachineSatLinker(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIMachineSatLinker(player.inventory, this); }
}
