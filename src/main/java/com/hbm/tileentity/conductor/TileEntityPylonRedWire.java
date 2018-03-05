package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEPylonDestructorPacket;
import com.hbm.packet.TEPylonSenderPacket;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileEntityPylonRedWire extends TileEntity implements IConductor {
	
	public List<UnionOfTileEntitiesAndBooleans> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleans>();
	public List<TileEntityPylonRedWire> connected = new ArrayList<TileEntityPylonRedWire>();
	public boolean scheduleConnectionCheck = false;
	public int[][] scheduleBuffer;
	
	@Override
	public void updateEntity() {
		
		for(int i = connected.size() - 1; i >= 0; i--) {
			if(connected.size() >= i + 1 && connected.get(i) == null)
				connected.remove(i);
		}
		
		for(int i = connected.size() - 1; i >= 0; i--) {
			if(connected.size() >= i + 1 && connected.get(i) != null && 
					this.worldObj.getBlock(connected.get(i).xCoord, connected.get(i).yCoord, connected.get(i).zCoord) != ModBlocks.red_pylon)
				connected.remove(i);
		}
		
		if(scheduleConnectionCheck && this.scheduleBuffer != null) {
			scheduleConnectionCheck = false;
			this.connected = TileEntityPylonRedWire.convertArrayToList(this.scheduleBuffer, worldObj);
		}
		
		if(!worldObj.isRemote)
			if(!connected.isEmpty()) {
				PacketDispatcher.wrapper.sendToAll(new TEPylonDestructorPacket(xCoord, yCoord, zCoord));
				
				for(TileEntityPylonRedWire wire : connected)
					PacketDispatcher.wrapper.sendToAll(new TEPylonSenderPacket(xCoord, yCoord, zCoord,
						wire.xCoord, wire.yCoord, wire.zCoord));
			}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		int[] conX = nbt.getIntArray("conX");
		int[] conY = nbt.getIntArray("conY");
		int[] conZ = nbt.getIntArray("conZ");

		int[][] con = new int[conX.length][3];
		
		for(int i = 0; i < conX.length; i++) {
			con[i][0] = conX[i];
			con[i][1] = conY[i];
			con[i][2] = conZ[i];
		}
			
		scheduleConnectionCheck = true;
		scheduleBuffer = con;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		int[][] con = TileEntityPylonRedWire.convertListToArray(connected);

		int[] conX = new int[con.length];
		int[] conY = new int[con.length];
		int[] conZ = new int[con.length];
		
		for(int i = 0; i < conX.length; i++) {
			conX[i] = con[i][0];
			conY[i] = con[i][1];
			conZ[i] = con[i][2];
		}

		nbt.setIntArray("conX", conX);
		nbt.setIntArray("conY", conY);
		nbt.setIntArray("conZ", conZ);
	}
	
	public static List<TileEntityPylonRedWire> convertArrayToList(int[][] array, World worldObj) {
		
		List<TileEntityPylonRedWire> list = new ArrayList<TileEntityPylonRedWire>();
		
		for(int i = 0; i < array.length; i++) {
			TileEntity te = worldObj.getTileEntity(array[i][0], array[i][1], array[i][2]);
			if(te != null && te instanceof TileEntityPylonRedWire)
				list.add((TileEntityPylonRedWire)te);
		}
		
		return list;
	}
	
	public static int[][] convertListToArray(List<TileEntityPylonRedWire> list) {
		
		int[][] array = new int[list.size()][3];
		
		for(int i = 0; i < list.size(); i++) {
			TileEntity te = list.get(i);
			array[i][0] = te.xCoord;
			array[i][1] = te.yCoord;
			array[i][2] = te.zCoord;
		}
		
		return array;
	}
	
	public void addTileEntityBasedOnCoords(int x, int y, int z) {

		TileEntity te = worldObj.getTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityPylonRedWire)
			this.connected.add((TileEntityPylonRedWire)te);
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
}
