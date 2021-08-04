package com.hbm.tileentity.conductor;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.calc.UnionOfTileEntitiesAndBooleans;
import com.hbm.interfaces.IConductor;
import com.hbm.interfaces.Spaghetti;
import com.hbm.interfaces.Untested;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEPylonDestructorPacket;
import com.hbm.packet.TEPylonSenderPacket;
import com.hbm.tileentity.INBTPacketReceiver;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

@Untested
public class TileEntityPylonRedWire extends TileEntity implements IConductor, INBTPacketReceiver {
	
	public List<UnionOfTileEntitiesAndBooleans> uoteab = new ArrayList<UnionOfTileEntitiesAndBooleans>();
	public List<int[]> connected = new ArrayList<int[]>();
	public boolean scheduleConnectionCheck = false;
	public int[][] scheduleBuffer;
	
	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			for(int i = connected.size() - 1; i >= 0; i--) {
				
				int[] con = connected.get(i);
				
				if(con == null) {
					connected.remove(i);
					continue;
				}
				
				TileEntity pylon = worldObj.getTileEntity(con[0], con[1], con[2]);
				
				if(worldObj.blockExists(con[0], con[1], con[2]) && (pylon == null || pylon.isInvalid())) {
					connected.remove(i);
					continue;
				}
			}
			
			if(scheduleConnectionCheck && this.scheduleBuffer != null) {
				scheduleConnectionCheck = false;
				this.connected = convertArrayToList(this.scheduleBuffer, worldObj);
			}
			
			if(worldObj.getTotalWorldTime() % 10 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setInteger("count", this.connected.size());
	
				for(int i = 0; i < connected.size(); i++) {
	
					int[] pos = connected.get(i);
					
					TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
	
					if(te instanceof TileEntityPylonRedWire) {
						data.setIntArray("c" + i, new int[] { pos[0], pos[1], pos[2] });
					}
				}
	
				PacketDispatcher.wrapper.sendToAllAround(new NBTPacket(data, xCoord, yCoord, zCoord), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 100));
			}
		}
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		
		this.connected.clear();
		
		int count = nbt.getInteger("count");
		
		for(int i = 0; i < count; i++) {
			
			if(nbt.hasKey("c" + i)) {
				int[] pos = nbt.getIntArray("c" + i);
				this.connected.add(pos);
			}
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
	
	public static List<int[]> convertArrayToList(int[][] array, World worldObj) {
		
		List<int[]> list = new ArrayList<int[]>();
		
		for(int i = 0; i < array.length; i++) {
			list.add(new int[] {array[i][0], array[i][1], array[i][2]});
		}
		
		return list;
	}
	
	public static int[][] convertListToArray(List<int[]> list) {
		
		int[][] array = new int[list.size()][3];
		
		for(int i = 0; i < list.size(); i++) {
			int[] pos = list.get(i);
			array[i][0] = pos[0];
			array[i][1] = pos[1];
			array[i][2] = pos[2];
		}
		
		return array;
	}
	
	public void addTileEntityBasedOnCoords(int x, int y, int z) {

		TileEntity te = worldObj.getTileEntity(x, y, z);
		if(te != null && te instanceof TileEntityPylonRedWire)
			this.connected.add(new int[]{x, y, z});
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
