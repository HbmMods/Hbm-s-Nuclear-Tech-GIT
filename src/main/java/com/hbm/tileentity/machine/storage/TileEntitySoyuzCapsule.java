package com.hbm.tileentity.machine.storage;

import com.hbm.inventory.container.ContainerSoyuzCapsule;
import com.hbm.inventory.gui.GUISoyuzCapsule;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityInventoryBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntitySoyuzCapsule extends TileEntityInventoryBase implements IGUIProvider {

	public TileEntitySoyuzCapsule() {
		super(19);
	}

	@Override
	public String getName() {
		return "container.soyuzCapsule";
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 2, yCoord + 3, zCoord + 2);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerSoyuzCapsule(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUISoyuzCapsule(player.inventory, this);
	}

}
