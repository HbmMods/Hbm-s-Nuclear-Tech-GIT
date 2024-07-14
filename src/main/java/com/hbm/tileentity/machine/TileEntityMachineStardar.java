package com.hbm.tileentity.machine;

import com.hbm.dim.CelestialBody;
import com.hbm.inventory.container.ContainerStardar;
import com.hbm.inventory.gui.GUIMachineStardar;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energymk2.IEnergyProviderMK2;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineStardar extends TileEntityMachineBase implements IGUIProvider {

	public TileEntityMachineStardar() {
		super(2);
		// TODO Auto-generated constructor stub
	}

	private long power;
	private long maxpwr = 1_000;

	@Override
	public void updateEntity() {
		
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerStardar(player.inventory, this);
	}

	@Override
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineStardar(player.inventory, this);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "container.machineStardar";
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		} else {
			return player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 128;
		}
	}

}
