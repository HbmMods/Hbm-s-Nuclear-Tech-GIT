package com.hbm.tileentity.machine.pile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachinePWRController;
import com.hbm.blocks.machine.pile.BlockPile;
import com.hbm.interfaces.NotableComments;
import com.hbm.items.machine.ItemPileRodMK2;
import com.hbm.items.machine.ItemPileRodMK2.EnumPileRod;
import com.hbm.main.NTMSounds;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;
import com.hbm.packet.toclient.PlayerInformPacket;
import com.hbm.tileentity.TileEntityTickingBase;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
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
	
	public int left;
	public int right;
	/**
	 * Segments are fuel and control channels grouped by vertical slices viewed from the front, this makes the simulation part easier.
	 */
	public PileSegment[] segments = new PileSegment[0];
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		height = nbt.getInteger("height");
		width = nbt.getInteger("width");
		depth = nbt.getInteger("depth");
		left = nbt.getInteger("left");
		right = nbt.getInteger("right");
		
		segments = new PileSegment[width];
		
		int fuelCount = nbt.getByte("fc");
		int ventCount = nbt.getByte("vc");
		int contCount = nbt.getByte("cc");

		fuelChannels.clear();
		ventilationChannels.clear();
		controlChannels.clear();

		for(int i = 0; i < fuelCount; i++) fuelChannels.add(readChannelFromNBT(nbt, "f" + i));
		for(int i = 0; i < ventCount; i++) ventilationChannels.add(readChannelFromNBT(nbt, "v" + i));
		for(int i = 0; i < contCount; i++) controlChannels.add(readChannelFromNBT(nbt, "c" + i));
		
		this.recalculateSegments();
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("height", height);
		nbt.setInteger("width", width);
		nbt.setInteger("depth", depth);
		nbt.setInteger("left", left);
		nbt.setInteger("right", right);
		
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

	public PileChannel getFuelChannel(int x, int y, int z) { return getChannel(x, y, z, fuelChannels); }
	public PileChannel getVentilationChannel(int x, int y, int z) { return getChannel(x, y, z, ventilationChannels); }
	public PileChannel getControlChannel(int x, int y, int z) { return getChannel(x, y, z, controlChannels); }
	
	public PileChannel getChannel(int x, int y, int z, List<PileChannel> list) {
		for(PileChannel channel : list) if(channel.entry.compare(x, y, z)) return channel;
		return null;
	}

	public int getFuelChannelNum(PileChannel chan) { return getChannelNum(chan, fuelChannels); }
	public int getVentilationChannelNum(PileChannel chan) { return getChannelNum(chan, ventilationChannels); }
	public int getControlChannelNum(PileChannel chan) { return getChannelNum(chan, controlChannels); }
	
	public int getChannelNum(PileChannel chan, List<PileChannel> list) {
		return list.indexOf(chan); // more reliable when channels change and not that big of a deal because we have lists in single digit length
	}
	
	public TileEntityPileCore setupSize(int h, int l, int r, int d) {
		this.height = h;
		this.width = l + 1 + r;
		this.depth = d;
		this.left = l;
		this.right = r;
		this.segments = new PileSegment[width];
		return this;
	}
	
	public List<PileChannel> getChannelList(PileChannelType type) {
		if(type == PileChannelType.FUEL) return this.fuelChannels;
		if(type == PileChannelType.VENTILATION) return this.ventilationChannels;
		return this.controlChannels;
	}
	
	public boolean drillChannel(int x, int y, int z, ForgeDirection dir, EntityPlayer player) {
		int startMeta = worldObj.getBlockMetadata(x, y, z);
		PileChannelType type = PileChannelType.getChannelType(dir, orientation);
		
		int size =
				type == PileChannelType.CONTROL ? height :
				type == PileChannelType.FUEL ? depth : width;
		
		List<PileChannel> list = getChannelList(type);
		
		if(startMeta == BlockPile.META_FUEL_IN || startMeta == BlockPile.META_AIR_IN || startMeta == BlockPile.META_CONTROL) {
			for(int i = 0; i < list.size(); i++) {
				PileChannel chan = list.get(i);
				if(chan.entry.compare(x, y, z) && chan.entry.getDir() == dir) {
					list.remove(i);
					for(int j = 0; j < size; j++) {
						int iX = x + dir.offsetX * j;
						int iY = y + dir.offsetY * j;
						int iZ = z + dir.offsetZ * j;
						worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_DUMMY, 3);
					}
					worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, NTMSounds.VANILLA_PLINK, 1F, 0.75F);
					recalculateSegments();
					return true;
				}
			}
		}
		
		boolean error = false;
		for(int i = 0; i < size; i++) {
			int iX = x + dir.offsetX * i;
			int iY = y + dir.offsetY * i;
			int iZ = z + dir.offsetZ * i;
			if(worldObj.getBlock(iX, iY, iZ) != ModBlocks.pile_block) { MachinePWRController.sendError(worldObj, iX, iY, iZ, "Foreign block in reactor", player); error = true; }
			int meta = worldObj.getBlockMetadata(iX, iY, iZ);
			if(meta == BlockPile.META_EDGE) { MachinePWRController.sendError(worldObj, iX, iY, iZ, "Cannot drill along edge", player); error = true; }
			else if(meta == BlockPile.META_CORE) { MachinePWRController.sendError(worldObj, iX, iY, iZ, "Cannot intersect core", player); error = true; }
			else if(meta == BlockPile.META_CHANNEL) { MachinePWRController.sendError(worldObj, iX, iY, iZ, "Cannot intersect channel", player); error = true; }
			else if(meta != BlockPile.META_DUMMY) { MachinePWRController.sendError(worldObj, iX, iY, iZ, "Cannot intersect channel IO", player); error = true; }
		}
		
		if(error) return false;
		
		for(int i = 0; i < size; i++) {
			int iX = x + dir.offsetX * i;
			int iY = y + dir.offsetY * i;
			int iZ = z + dir.offsetZ * i;
			if(i == 0) {
				if(type == PileChannelType.FUEL) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_FUEL_IN, 3);
				if(type == PileChannelType.VENTILATION) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_AIR_IN, 3);
				if(type == PileChannelType.CONTROL) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_CONTROL, 3);
			} else if(i == size - 1) {
				if(type == PileChannelType.FUEL) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_FUEL_OUT, 3);
				if(type == PileChannelType.VENTILATION) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_AIR_OUT, 3);
				if(type == PileChannelType.CONTROL) worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_CONTROL, 3);
			} else {
				worldObj.setBlockMetadataWithNotify(iX, iY, iZ, BlockPile.META_CHANNEL, 3);
			}
		}
		
		list.add(new PileChannel(x, y, z, dir, size, type));

		worldObj.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, NTMSounds.VANILLA_PLINK, 1F, 1.25F);
		this.markChanged();
		
		recalculateSegments();
		
		return true;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			
			this.runSimulation();
			this.handleVentilation();
		}
	}
	
	protected void runSimulation() {
		
		// reaction
		for(PileChannel chan : this.fuelChannels) {
			if(chan.length <= 0) continue;
			double producedNeutrons = 0;
			double latestDepletion = 0D;
			
			for(int i = 0; i < chan.rods.length; i++) {
				ItemStack stack = chan.rods[i];
				if(stack != null && stack.getItem() instanceof ItemPileRodMK2) {
					double neut = ItemPileRodMK2.getReactivity(stack, chan.incomingNeutrons / chan.length);
					producedNeutrons += neut;
					chan.heat += neut * ItemPileRodMK2.getHeatPerNeutron(stack);
					chan.rods[i] = ItemPileRodMK2.react(stack, neut);
					EnumPileRod rod = EnumUtil.grabEnumSafely(EnumPileRod.class, stack.getItemDamage());
					double life = rod.life < 1 ? 1 : rod.life;
					latestDepletion = ItemPileRodMK2.getDepletion(chan.rods[i]) / life;
				}
			}
			chan.outgoingNeutrons = producedNeutrons;
			chan.incomingNeutrons = 0;
			
			/// DEBUG ///
			int hash = BlockPos.getIdentity(xCoord, yCoord, zCoord);
			
			for(Object o : worldObj.playerEntities) {
				if(o instanceof EntityPlayerMP) {
					EntityPlayerMP player = (EntityPlayerMP) o;
					int index = this.getFuelChannelNum(chan);
					PacketDispatcher.wrapper.sendTo(new PlayerInformPacket("#" + index + " " + Math.round(chan.heat) + "TU " + Math.round(chan.outgoingNeutrons) + "n " + Math.round(latestDepletion * 100) + "%", hash + index), player);
				}
			}
			/// DEBUG ///
		}
		
		// intra-segment propagation
		for(PileSegment seg : this.segments) {
			if(seg == null || seg.segType != seg.segType.FUEL) continue;
			double outgoing = 0D;
			for(PileChannel chan : seg.channels) outgoing += chan.outgoingNeutrons;
			for(PileChannel chan : seg.channels) chan.incomingNeutrons += outgoing;
		}
		
		// inter-segment propagation
		for(int i = 1; i < this.segments.length - 1; i++) { // bound chosen because edges can't have channels
			PileSegment seg = this.segments[i];
			if(seg == null || seg.segType != seg.segType.FUEL) continue;
			double outgoing = 0D;
			for(PileChannel chan : seg.channels) outgoing += chan.outgoingNeutrons;
			
			double mult = 1D;
			for(int j = i - 1; j >= 1; j--) { // center to left
				PileSegment neighbor = this.segments[j];
				if(neighbor == null) continue;
				mult *= neighbor.getNeutronMult(this);
				if(neighbor.segType == neighbor.segType.FUEL) {
					for(PileChannel chan : neighbor.channels) chan.incomingNeutrons += outgoing * mult;
				}
			}
			
			mult = 1D;
			for(int j = i + 1; j < this.segments.length - 1; j++) { // center to right
				PileSegment neighbor = this.segments[j];
				if(neighbor == null) continue;
				mult *= neighbor.getNeutronMult(this);
				if(neighbor.segType == neighbor.segType.FUEL) {
					for(PileChannel chan : neighbor.channels) chan.incomingNeutrons += outgoing * mult;
				}
			}
		}
	}
	
	protected void handleVentilation() {
		
		for(PileChannel chan : this.ventilationChannels) {
			if(chan.air <= 0) continue;
			
			double airCap = (double) chan.air / (double) chan.MAX_AIR;
			
			for(PileChannel fuel : this.fuelChannels) {
				if(Math.abs(fuel.entry.getY() - chan.entry.getY()) <= 1) {
					fuel.heat *= (1D - airCap * 0.5D); // channel can hold up to 1000mB, turning into a 0.5 heat mult for connected fuel channels
				}
			}
			
			int toUse = (int) Math.ceil(airCap * 5D);
			chan.air -= toUse;
			
			if(worldObj.getTotalWorldTime() % 3 != 0) continue;

			double x = chan.entry.getX() + 0.5 + chan.entry.getDir().offsetX * (this.width - 0.375);
			double y = chan.entry.getY() + 0.5;
			double z = chan.entry.getZ() + 0.5 + chan.entry.getDir().offsetZ * (this.width - 0.375);
			Random rand = worldObj.rand;
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "tower");
			data.setFloat("lift", 1F);
			data.setFloat("base", (0.125F + rand.nextFloat() * 0.125F) * (float) airCap);
			data.setFloat("max", 1F * (float) airCap);
			data.setFloat("strafe", 0.0025F);
			data.setBoolean("noWind", true);
			data.setInteger("life", 20 + worldObj.rand.nextInt(30));
			data.setInteger("color", 0xa0a0a0);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 150));
		}
	}
	
	protected void recalculateSegments() {
		this.segments = new PileSegment[width];
		
		for(PileChannel chan : fuelChannels) {
			int index = getChannelVerticalIndex(chan);
			if(index < 0 || index >= this.segments.length) continue;
			
			if(this.segments[index] == null) {
				this.segments[index] = new PileSegment(PileChannelType.FUEL).addChan(chan);
			} else {
				if(this.segments[index].segType == PileChannelType.FUEL) this.segments[index].addChan(chan);
			}
		}
		
		for(PileChannel chan : controlChannels) {
			int index = getChannelVerticalIndex(chan);
			if(index < 0 || index >= this.segments.length) continue;
			
			if(this.segments[index] == null) {
				this.segments[index] = new PileSegment(PileChannelType.CONTROL).addChan(chan);
			} else {
				if(this.segments[index].segType == PileChannelType.CONTROL) this.segments[index].addChan(chan);
			}
		}
	}
	
	/**
	 * Splits the entire pile into vertical slices, left to right, and returns the index of which slice a channel belongs to.
	 * Since this left-to-right approach assumes the front of the pile, this only is used for fuel channels and control rods.
	 */
	protected int getChannelVerticalIndex(PileChannel chan) {
		DirPos pos = chan.entry;
		ForgeDirection right = pos.getDir().getRotation(ForgeDirection.UP);
		int deltaX = (pos.getX() - xCoord) * right.offsetX;
		int deltaZ = (pos.getZ() - zCoord) * right.offsetZ;
		int abs = deltaX == 0 ? deltaZ : deltaX;
		int index = abs + this.left;
		return index;
	}
	
	public void destroy() {
		worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.pile_brick);
	}
	
	/**
	 * The orientation of the pile, based on the direction the core is pointing in.
	 * Channels that follow the orientation are fuel, perpendicular ones are for ventilation.
	 */
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
	
	/**
	 * A channel, represents any type, therefore has the data of all three types.
	 * Could have been an abstract class + three child classes but then we'd just be overcomplicating it.
	 */
	public class PileChannel { // originally called "PileHole", however i didn't want it sounding too sexual
		
		public final DirPos entry; // first channel block plus direction it faces inward
		public final int length; // length of the channel, always equal to the h/w/d size of that axis
		public final PileChannelType type;
		
		public final ItemStack[] rods;
		public double heat = 0D;
		public double outgoingNeutrons = 0D;
		public double incomingNeutrons = 0D;
		public static final int MAX_AIR = 1_000;
		public int air;
		public double control = 1D; // pulled out by default, unlike me who never pulls out
		
		public PileChannel(int x, int y, int z, ForgeDirection dir) {
			this.entry = new DirPos(x, y, z, dir);
			this.type = PileChannelType.getChannelType(dir, orientation);
			this.length =
					type == PileChannelType.CONTROL ? height :
					type == PileChannelType.FUEL ? depth : width;

			this.rods = new ItemStack[length];
		}
		
		public PileChannel(int x, int y, int z, ForgeDirection dir, int length, PileChannelType type) {
			this.entry = new DirPos(x, y, z, dir);
			this.type = type;
			this.length = length;
			this.rods = new ItemStack[length];
		}
		
		public void writeChannelToNBT(NBTTagCompound nbt, String name) {
			nbt.setInteger(name + "_x", entry.getX());
			nbt.setInteger(name + "_y", entry.getY());
			nbt.setInteger(name + "_z", entry.getZ());
			nbt.setByte(name + "_d", (byte) entry.getDir().ordinal());

			if(type == type.FUEL) {
				NBTTagList list = new NBTTagList();
				for(int i = 0; i < rods.length; i++) {
					if(rods[i] != null) {
						NBTTagCompound nbt1 = new NBTTagCompound();
						nbt1.setByte("slot", (byte)i);
						rods[i].writeToNBT(nbt1);
						list.appendTag(nbt1);
					}
				}
				nbt.setTag("items", list);

				nbt.setDouble("heat", heat);
				nbt.setDouble("neutrons", incomingNeutrons);
			}
			
			if(type == type.VENTILATION) {
				nbt.setInteger("air", air);
			}
			
			if(type == type.CONTROL) {
				nbt.setDouble("control", control);
			}
		}
		
		public void loadItem(ItemStack stack) {
			if(stack == null) return;
			if(rods.length <= 0) { dropItem(stack, -1); return; }
			
			for(int i = 0; i < rods.length; i++) {
				if(rods[i] == null) {
					rods[i] = stack;
					return;
					
				} else {
					ItemStack prev = rods[i];
					rods[i] = stack;
					stack = prev;
				}
			}
			
			if(stack != null) dropItem(stack, length);
		}
		
		public void dropItem(ItemStack stack, int depth) {
			int x = entry.getX() + entry.getDir().offsetX * depth;
			int y = entry.getY();
			int z = entry.getZ() + entry.getDir().offsetZ * depth;
			
			EntityItem item = new EntityItem(worldObj, x + 0.5, y + 0.5, z + 0.5, stack);
			worldObj.spawnEntityInWorld(item);
		}
	}
	
	public PileChannel readChannelFromNBT(NBTTagCompound nbt, String name) {
		int x = nbt.getInteger(name + "_x");
		int y = nbt.getInteger(name + "_y");
		int z = nbt.getInteger(name + "_z");
		ForgeDirection dir = ForgeDirection.getOrientation(nbt.getByte(name + "_d"));
		
		PileChannel chan = new PileChannel(x, y, z, dir);

		if(chan.type == chan.type.FUEL) {
			NBTTagList list = nbt.getTagList("fuel", 10);
			for(int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound nbt1 = list.getCompoundTagAt(i);
				byte b0 = nbt1.getByte("slot");
				if(b0 >= 0 && b0 < chan.rods.length) {
					chan.rods[b0] = ItemStack.loadItemStackFromNBT(nbt1);
				}
			}

			chan.heat = nbt.getDouble("heat");
			chan.incomingNeutrons = nbt.getDouble("neutrons");
		}
		
		if(chan.type == chan.type.VENTILATION) {
			chan.air = nbt.getInteger("air");
		}
		
		if(chan.type == chan.type.CONTROL) {
			chan.control = nbt.getDouble("control");
		}
		
		return chan;
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
	
	/** A vertical "slice" of the front of the reactor, can include multiple channels per slice,
	 * due to reactor geometry constraints, all channels are of the same type. */
	public static class PileSegment {
		
		public List<PileChannel> channels = new ArrayList();
		public final PileChannelType segType;
		
		public PileSegment(PileChannelType segType) {
			this.segType = segType;
		}
		
		public PileSegment addChan(PileChannel chan) {
			this.channels.add(chan);
			return this;
		}
		
		public double getNeutronMult(TileEntityPileCore core) {
			if(this.segType != this.segType.CONTROL) return 1D;
			int size = core.depth - 1; // minus one to make the calculation easier, a xixix pattern becomes xixi which means the rods cover 50%
			if(size < 3) return 0D; // reactors this small can't exist, nor can they have control rods
			double total = 0D;
			
			for(PileChannel chan : channels) total += chan.control;
			return MathHelper.clamp_double(total / size, 0D, 0.5D);
		}
	}
}
