package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerICF;
import com.hbm.inventory.gui.GUIICF;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityICF extends TileEntityMachineBase implements IGUIProvider {

	public TileEntityICF() {
		super(12);
	}

	@Override
	public String getName() {
		return "container.machineICF";
	}

	@Override
	public void updateEntity() {
		
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord + 0.5 - 8,
					yCoord,
					zCoord + 0.5 - 8,
					xCoord + 0.5 + 9,
					yCoord + 0.5 + 5,
					zCoord + 0.5 + 9
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerICF(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIICF(player.inventory, this);
	}
}
