package com.hbm.tileentity.network;

import java.util.List;

import com.hbm.entity.item.EntityDeliveryDrone;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.INBTPacketReceiver;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TileEntityDroneCrate extends TileEntityMachineBase implements IGUIProvider, INBTPacketReceiver, IDroneLinkable {
	
	public int nextX = -1;
	public int nextY = -1;
	public int nextZ = -1;

	public TileEntityDroneCrate() {
		super(19);
	}

	@Override
	public String getName() {
		return "container.droneCrate";
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			if(nextY != -1) {
				
				List<EntityDeliveryDrone> drones = worldObj.getEntitiesWithinAABB(EntityDeliveryDrone.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 1, zCoord, xCoord + 1, yCoord + 2, zCoord + 1));
				for(EntityDeliveryDrone drone : drones) {
					if(Vec3.createVectorHelper(drone.motionX, drone.motionY, drone.motionZ).lengthVector() < 0.05) {
						drone.setTarget(nextX + 0.5, nextY, nextZ + 0.5);
					}
				}
			}
			
			NBTTagCompound data = new NBTTagCompound();
			data.setIntArray("pos", new int[] {nextX, nextY, nextZ});
			INBTPacketReceiver.networkPack(this, data, 25);
		}
	}

	@Override
	public BlockPos getPoint() {
		return new BlockPos(xCoord, yCoord + 1, zCoord);
	}

	@Override
	public void setNextTarget(int x, int y, int z) {
		this.nextX = x;
		this.nextY = y;
		this.nextZ = z;
		this.markDirty();
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		int[] pos = nbt.getIntArray("pos");
		this.nextX = pos[0];
		this.nextY = pos[1];
		this.nextZ = pos[2];
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}
}
