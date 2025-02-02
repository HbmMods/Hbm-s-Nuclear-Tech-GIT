package com.hbm.tileentity.machine.albion;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPASource;
import com.hbm.inventory.gui.GUIPASource;
import com.hbm.lib.Library;
import com.hbm.tileentity.IConditionalInvAccess;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.util.EnumUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;
import com.hbm.util.fauxpointtwelve.DirPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPASource extends TileEntityCooledBase implements IGUIProvider, IConditionalInvAccess, IControlReceiver {
	
	public static final long usage = 100_000;
	public Particle particle;
	public PAState state = PAState.IDLE;
	
	public int lastSpeed;
	
	public int debugSpeed;
	
	public static enum PAState {
		IDLE(0x8080ff),					//no particle active
		RUNNING(0xffff00),				//running without further issue
		SUCCESS(0x00ff00),				//completed recipe
		PAUSE_UNLOADED(0x808080),		//particle suspended because it entered unloaded chunks
		CRASH_DEFOCUS(0xff0000),		//crash from excessive defocus
		CRASH_DERAIL(0xff0000),			//crash due to leaving the beamline
		CRASH_CANNOT_ENTER(0xff0000),	//crash due to hitting PA component from invalid side
		CRASH_NOCOOL(0xff0000),			//crash due to lack of cooling
		CRASH_NOPOWER(0xff0000),		//crash due to power outage
		CRASH_NOCOIL(0xff0000),			//crash due to no coil installed (QP, DP)
		CRASH_OVERSPEED(0xff0000);		//crash due to coil max speed exceeded (QP, DP)
		
		public int color;
		
		private PAState(int color) {
			this.color = color;
		}
	}
	
	public void updateState(PAState state) { this.state = state; }
	
	public TileEntityPASource() {
		super(5);
	}

	@Override public String getName() { return "container.paSource"; }
	@Override public int getInventoryStackLimit() { return 1; }

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
			
			for(int i = 0; i < 10; i++) {
				if(particle != null) {
					this.state = PAState.RUNNING;
					steppy();
					this.debugSpeed = particle.momentum;
					if(particle.invalid) this.particle = null;
				} else if(this.power >= this.usage && slots[1] != null && slots[2] != null) {
					tryRun();
					break;
				}
			}
		}
		
		super.updateEntity();
	}
	
	public void steppy() {
		if(!worldObj.getChunkProvider().chunkExists(particle.x >> 4, particle.z >> 4)) { this.state = PAState.PAUSE_UNLOADED; return; } //halt if we reach unloaded areas
		//ExplosionSmallCreator.composeEffect(worldObj, particle.x + 0.5, particle.y + 0.5, particle.z + 0.5, 10, 1, 1);
		
		Block b = worldObj.getBlock(particle.x, particle.y, particle.z);
		if(b instanceof BlockDummyable) {
			int[] pos = ((BlockDummyable) b).findCore(worldObj, particle.x, particle.y, particle.z);
			if(pos == null) { particle.crash(PAState.CRASH_DERAIL); return; }
			TileEntity tile = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
			if(!(tile instanceof IParticleUser)) { particle.crash(PAState.CRASH_DERAIL); return; }
			IParticleUser pa = (IParticleUser) tile;
			if(pa.canParticleEnter(particle, particle.dir, particle.x, particle.y, particle.z)) {
				pa.onEnter(particle, particle.dir);
				BlockPos exit = pa.getExitPos(particle);
				if(exit != null) particle.move(exit);
			} else { particle.crash(PAState.CRASH_CANNOT_ENTER); return; }
		} else {
			particle.crash(PAState.CRASH_DERAIL);
		}
	}
	
	public void tryRun() {
		if(slots[1].getItem().hasContainerItem(slots[1]) && slots[3] != null) return;
		if(slots[2].getItem().hasContainerItem(slots[2]) && slots[4] != null) return;

		if(slots[1].getItem().hasContainerItem(slots[1])) slots[3] = slots[1].getItem().getContainerItem(slots[1]).copy();
		if(slots[2].getItem().hasContainerItem(slots[2])) slots[4] = slots[2].getItem().getContainerItem(slots[2]).copy();
		
		this.power -= usage;
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);
		this.particle = new Particle(this, xCoord + rot.offsetX * 5, yCoord, zCoord + rot.offsetZ * 5, rot, slots[1], slots[2]);
		this.slots[1] = null;
		this.slots[2] = null;
		this.markDirty();
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(debugSpeed);
		buf.writeByte((byte) this.state.ordinal());
		buf.writeInt(this.lastSpeed);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		debugSpeed = buf.readInt();
		state = EnumUtil.grabEnumSafely(PAState.class, buf.readByte());
		this.lastSpeed = buf.readInt();
	}
	
	@Override
	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		return new DirPos[] {
				new DirPos(xCoord + dir.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2, dir),
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 2, dir),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 2 - rot.offsetZ * 2, dir),
				new DirPos(xCoord - dir.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2 + rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 2 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 2 - rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord + rot.offsetX * 5, yCoord, zCoord + rot.offsetZ * 5, rot),
		};
	}

	//ISidedInventory
	@Override public boolean isItemValidForSlot(int slot, ItemStack stack) { return slot == 1 || slot == 2; }
	@Override public boolean canExtractItem(int slot, ItemStack stack, int side) { return slot == 3 || slot == 4; }
	@Override public int[] getAccessibleSlotsFromSide(int side) { return new int[] { 3, 4 }; }

	//IConditionalInvAccess
	@Override public boolean isItemValidForSlot(int x, int y, int z, int slot, ItemStack stack) { return isItemValidForSlot(slot, stack); }
	@Override public boolean canExtractItem(int x, int y, int z, int slot, ItemStack stack, int side) { return canExtractItem(slot, stack, side); }

	//reusing the same fucking instance because doing anything else would be retarded
	public static final BlockPos cheapAss = new BlockPos(0, 0, 0);
	public static final int[] slotsRed = new int[] {1};
	public static final int[] slotsYellow = new int[] {2};
	@Override
	public int[] getAccessibleSlotsFromSide(int x, int y, int z, int side) {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		cheapAss.mutate(x, y, z);
		
		if(cheapAss.compare(xCoord + dir.offsetX - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ - rot.offsetZ * 2) ||
				cheapAss.compare(xCoord - dir.offsetX + rot.offsetX * 2, yCoord, zCoord - dir.offsetZ + rot.offsetZ * 2)) {
			return slotsYellow;
		}
		
		if(cheapAss.compare(xCoord - dir.offsetX - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ - rot.offsetZ * 2) ||
				cheapAss.compare(xCoord + dir.offsetX + rot.offsetX * 2, yCoord, zCoord + dir.offsetZ + rot.offsetZ * 2)) {
			return slotsRed;
		}
		
		return getAccessibleSlotsFromSide(side);
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}

	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 4,
					yCoord - 1,
					zCoord - 4,
					xCoord + 5,
					yCoord + 2,
					zCoord + 6
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) { return new ContainerPASource(player.inventory, this); }
	@Override public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) { return new GUIPASource(player.inventory, this); }

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if(data.hasKey("cancel")) {
			this.particle = null;
			this.state = PAState.IDLE;
		}
	}
	
	public static class Particle {

		private TileEntityPASource source;
		public int x;
		public int y;
		public int z;
		public ForgeDirection dir;
		public int momentum;
		public int defocus;
		public int distanceTraveled;
		public static final int maxDefocus = 1000;
		public boolean invalid = false;
		
		public ItemStack input1;
		public ItemStack input2;
		
		public Particle(TileEntityPASource source, int x, int y, int z, ForgeDirection dir, ItemStack input1, ItemStack input2) {
			this.source = source;
			this.x = x;
			this.y = y;
			this.z = z;
			this.dir = dir;
			this.input1 = input1;
			this.input2 = input2;
		}
		
		public void crash(PAState state) {
			this.invalid = true;
			this.source.updateState(state);
		}
		
		public void move(BlockPos pos) {
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
			this.source.lastSpeed = this.momentum;
		}
		
		public void addDistance(int dist) { this.distanceTraveled += dist; }
		public void resetDistance() { this.distanceTraveled = 0; }
		
		public void defocus(int amount) {
			this.defocus += amount;
			if(this.defocus > this.maxDefocus) this.crash(PAState.CRASH_DEFOCUS);
		}
		
		public void focus(int amount) {
			this.defocus -= amount;
			if(this.defocus < 0) this.defocus = 0;
		}
	}
}
