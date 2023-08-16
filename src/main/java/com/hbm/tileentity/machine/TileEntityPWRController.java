package com.hbm.tileentity.machine;

import com.hbm.inventory.container.ContainerPWR;
import com.hbm.inventory.gui.GUIPWR;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityPWRController extends TileEntityMachineBase implements IGUIProvider {
	
	public boolean assembled;

	public TileEntityPWRController() {
		super(3);
	}

	@Override
	public String getName() {
		return "container.pwrController";
	}

	@Override
	public void updateEntity() {
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.assembled = nbt.getBoolean("assembled");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("assembled", assembled);
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerPWR(player.inventory, this);
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIPWR(player.inventory, this);
	}
}
