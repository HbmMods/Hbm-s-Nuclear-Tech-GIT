package com.hbm.tileentity.machine.pile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePWRController;
import com.hbm.blocks.machine.pile.BlockPile;
import com.hbm.interfaces.NotableComments;
import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.util.fauxpointtwelve.DirPos;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

@NotableComments
public class TileEntityPileCore extends TileEntityTickingBase {
	
	/*
	 *  ____,__,___
	 * |\   I, I,   \
	 * | \   I  I    \
	 * -- \___________\
	 * |--|           |
	 * -- |  U  U  U  |
	 * \--|     X     |
	 *  \ |  U  U  U  |
	 *   \|___________|
	 *   
	 *       BEHOLD
	 *      THE CUBE
	 */
	
	/**
	 * Orientation is kind of important now. The "direction" the pile is facing in is now decided
	 * by the side the core is on, i.e. the side it was assembled from. Any channel that goes along
	 * with the orientation is automatically considered a fuel channel. Any channel perpendicular
	 * to the orientation is for ventilation. Vertical channels are always for control rods.
	 */
	public PileOrientation orientation = PileOrientation.NEITHER;
	
	public int height;
	public int width;
	public int depth;

	public List<PileChannel> fuelChannels = new ArrayList();
	public List<PileChannel> ventilationChannels = new ArrayList();
	public List<PileChannel> controlChannels = new ArrayList();
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		height = nbt.getInteger("height");
		width = nbt.getInteger("width");
		depth = nbt.getInteger("depth");
		
		int fuelCount = nbt.getByte("fc");
		int ventCount = nbt.getByte("vc");
		int contCount = nbt.getByte("cc");

		fuelChannels.clear();
		ventilationChannels.clear();
		controlChannels.clear();

		for(int i = 0; i < fuelCount; i++) fuelChannels.add(readChannelFromNBT(nbt, "f" + i));
		for(int i = 0; i < ventCount; i++) ventilationChannels.add(readChannelFromNBT(nbt, "v" + i));
		for(int i = 0; i < contCount; i++) controlChannels.add(readChannelFromNBT(nbt, "c" + i));
	}

	public PileChannel getFuelChannel(int x, int y, int z) { return getChannel(x, y, z, fuelChannels); }
	public PileChannel getVentilationChannel(int x, int y, int z) { return getChannel(x, y, z, ventilationChannels); }
	public PileChannel getControlChannel(int x, int y, int z) { return getChannel(x, y, z, controlChannels); }
	
	public PileChannel getChannel(int x, int y, int z, List<PileChannel> list) {
		for(PileChannel channel : list) if(channel.entry.compare(x, y, z)) return channel;
		return null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("height", height);
		nbt.setInteger("width", width);
		nbt.setInteger("depth", depth);
		
		int fuelCount = fuelChannels.size();
		int ventCount = ventilationChannels.size();
		int contCount = controlChannels.size();

		nbt.setByte("fc", (byte) fuelCount);
		nbt.setByte("vc", (byte) ventCount);
		nbt.setByte("cc", (byte) contCount);

		for(int i = 0; i < fuelCount; i++) fuelChannels.get(i).writeChannelToNBT(nbt, "f" + i);
		for(int i = 0; i < ventCount; i++) ventilationChannels.get(i).writeChannelToNBT(nbt, "v" + i);
		for(int i = 0; i < contCount; i++) controlChannels.get(i).writeChannelToNBT(nbt, "c" + i);
	}
	
	public TileEntityPileCore setupSize(int h, int w, int d) {
		this.height = h;
		this.width = w;
		this.depth = d;
		return this;
	}
	
	public List<PileChannel> getChannelList(PileChannelType type) {
		if(type == PileChannelType.FUEL) return this.fuelChannels;
		if(type == PileChannelType.VENTILATION) return this.ventilationChannels;
		return this.controlChannels;
	}
	
	public boolean drillChannel(int x, int y, int z, ForgeDirection dir, EntityPlayer player) {
		PileChannelType type = PileChannelType.getChannelType(dir, orientation);
		int size =
				type == PileChannelType.CONTROL ? height :
				type == PileChannelType.FUEL ? depth : width;
		
		boolean error = false;
		for(int i = 0; i <= size; i++) {
			int iX = x + dir.offsetX * i;
			int iY = y + dir.offsetY * i;
			int iZ = z + dir.offsetZ * i;
			if(worldObj.getBlock(iX, iY, iZ) != ModBlocks.pile_block) { MachinePWRController.sendError(worldObj, iX, iY, iZ, "Foreign block in reactor", player); error = true; }
			int meta = worldObj.getBlockMetadata(iX, iY, iZ);
			if(meta == BlockPile.META_EDGE) { MachinePWRController.sendError(worldObj, iX, iY, iZ, "Cannot drill along edge", player); error = true; }
			if(meta == BlockPile.META_CORE) { MachinePWRController.sendError(worldObj, iX, iY, iZ, "Cannot intersect core", player); error = true; }
			if(meta == BlockPile.META_CHANNEL) { MachinePWRController.sendError(worldObj, iX, iY, iZ, "Cannot intersect channel", player); error = true; }
			if(meta != BlockPile.META_DUMMY) { MachinePWRController.sendError(worldObj, iX, iY, iZ, "Cannot intersect channel IO", player); error = true; }
		}
		
		if(error) return false;
		
		for(int i = 0; i <= size; i++) {
			int iX = x + dir.offsetX * i;
			int iY = y + dir.offsetY * i;
			int iZ = z + dir.offsetZ * i;
			if(i == 0) {
				if(type == PileChannelType.FUEL) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_FUEL_IN, 3);
				if(type == PileChannelType.VENTILATION) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_AIR_IN, 3);
				if(type == PileChannelType.CONTROL) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_CONTROL, 3);
			} else if(i == size) {
				if(type == PileChannelType.FUEL) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_FUEL_OUT, 3);
				if(type == PileChannelType.VENTILATION) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_AIR_OUT, 3);
				if(type == PileChannelType.CONTROL) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_CONTROL, 3);
			} else {
				worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_DUMMY, 3);
			}
		}
		
		List<PileChannel> list = getChannelList(type);
		list.add(new PileChannel(x, y, z, dir, size, type));
		
		this.markChanged();
		
		return true;
	}

	@Override
	public void updateEntity() {
		
	}
	
	public void destroy() {
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.pile_brick);
	}
	
	public static enum PileOrientation {
		NORTH_SOUTH,
		EAST_WEST,
		NEITHER;
		
		public static PileOrientation getOrientation(ForgeDirection dir) {
			if(dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) return NORTH_SOUTH;
			if(dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) return EAST_WEST;
			return NEITHER;
		}
	}
	
	public class PileChannel { // originally called "PileHole", however i didn't want it sounding too sexual
		
		public final DirPos entry; // first channel block plus direction it faces inward
		public final int length; // length of the channel, always equal to the h/w/d size of that axis
		public final PileChannelType type;
		
		public PileChannel(int x, int y, int z, ForgeDirection dir) {
			this.entry = new DirPos(x, y, z, dir);
			this.type = PileChannelType.getChannelType(dir, orientation);
			this.length =
					type == PileChannelType.CONTROL ? height :
					type == PileChannelType.FUEL ? depth : width;
		}
		
		public PileChannel(int x, int y, int z, ForgeDirection dir, int length, PileChannelType type) {
			this.entry = new DirPos(x, y, z, dir);
			this.type = type;
			this.length = length;
		}
		
		public void writeChannelToNBT(NBTTagCompound nbt, String name) {
			nbt.setInteger(name + "_x", entry.getX());
			nbt.setInteger(name + "_y", entry.getY());
			nbt.setInteger(name + "_z", entry.getZ());
			nbt.setByte(name + "_d", (byte) entry.getDir().ordinal());
		}
	}
	
	public PileChannel readChannelFromNBT(NBTTagCompound nbt, String name) {
		int x = nbt.getInteger(name + "_x");
		int y = nbt.getInteger(name + "_y");
		int z = nbt.getInteger(name + "_z");
		ForgeDirection dir = ForgeDirection.getOrientation(nbt.getByte(name + "_d"));
		return new PileChannel(x, y, z, dir);
	}
	
	public static enum PileChannelType {
		FUEL, VENTILATION, CONTROL;
		
		public static PileChannelType getChannelType(ForgeDirection channelDir, PileOrientation pileOrientation) {
			
			if(channelDir == ForgeDirection.UP || channelDir == ForgeDirection.DOWN) {
				return PileChannelType.CONTROL;
			} else if(PileOrientation.getOrientation(channelDir) == pileOrientation) {
				return PileChannelType.FUEL;
			} else {
				return PileChannelType.VENTILATION;
			}
		}
	}
}
