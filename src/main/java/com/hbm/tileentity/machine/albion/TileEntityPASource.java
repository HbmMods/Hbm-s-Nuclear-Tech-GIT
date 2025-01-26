package com.hbm.tileentity.machine.albion;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.container.ContainerPASource;
import com.hbm.inventory.gui.GUIPASource;
import com.hbm.lib.Library;
import com.hbm.particle.helper.ExplosionSmallCreator;
import com.hbm.tileentity.IConditionalInvAccess;
import com.hbm.tileentity.IGUIProvider;
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
	
	public static final long usage = 1_000_000;
	public Particle particle;
	
	public int debugSpeed;
	
	public TileEntityPASource() {
		super(5);
	}

	@Override
	public String getName() {
		return "container.paSource";
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void updateEntity() {
		
		if(!worldObj.isRemote) {
			this.power = Library.chargeTEFromItems(slots, 0, power, this.getMaxPower());
			
			if(particle != null) {
				steppy();
				this.debugSpeed = particle.momentum;
				if(particle.invalid) this.particle = null;
			} else if(this.power >= this.usage && slots[1] != null && slots[2] != null) {
				tryRun();
			}
		}
		
		super.updateEntity();
	}
	
	public void steppy() {
		if(!worldObj.getChunkProvider().chunkExists(particle.x >> 4, particle.z >> 4)) return; //halt if we reach unloaded areas
		System.out.println("ticking");
		ExplosionSmallCreator.composeEffect(worldObj, particle.x + 0.5, particle.y + 0.5, particle.z + 0.5, 10, 1, 1);
		
		Block b = worldObj.getBlock(particle.x, particle.y, particle.z);
		if(b instanceof BlockDummyable) {
			int[] pos = ((BlockDummyable) b).findCore(worldObj, particle.x, particle.y, particle.z);
			if(pos == null) { particle.crash(); return; }
			TileEntity tile = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
			if(!(tile instanceof IParticleUser)) { particle.crash(); return; }
			IParticleUser pa = (IParticleUser) tile;
			System.out.println(pa + "");
			if(pa.canParticleEnter(particle, particle.dir, particle.x, particle.y, particle.z)) {
				pa.onEnter(particle, particle.dir);
				BlockPos exit = pa.getExitPos(particle);
				particle.move(exit);
			} else { particle.crash(); worldObj.createExplosion(null, particle.x + 0.5, particle.y + 0.5, particle.z + 0.5, 5, false); return; }
		} else {
			System.out.println("derailed!");
			particle.crash();
		}
	}
	
	public void tryRun() {
		if(slots[1].getItem().hasContainerItem(slots[1]) && slots[3] != null) return;
		if(slots[2].getItem().hasContainerItem(slots[2]) && slots[4] != null) return;
		
		this.power -= usage;
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
		this.particle = new Particle(xCoord + rot.offsetX * 5, yCoord, zCoord + rot.offsetZ * 5, rot, slots[1], slots[2]);
		this.slots[1] = null;
		this.slots[2] = null;
		this.markDirty();
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeInt(debugSpeed);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		debugSpeed = buf.readInt();
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
		if(data.hasKey("cancel")) this.particle = null;
	}
	
	public static class Particle {

		public int x;
		public int y;
		public int z;
		public ForgeDirection dir;
		public int momentum;
		public int defocus;
		public static final int maxDefocus = 100;
		public boolean invalid = false;
		
		public ItemStack input1;
		public ItemStack input2;
		
		public Particle(int x, int y, int z, ForgeDirection dir, ItemStack input1, ItemStack input2) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.dir = dir;
			this.input1 = input1;
			this.input2 = input2;
		}
		
		public void crash() {
			this.invalid = true;
		}
		
		public void move(BlockPos pos) {
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
		}
		
		public void defocus(int amount) {
			this.defocus += amount;
			if(this.defocus > this.maxDefocus) this.crash();
		}
		
		public void focus(int amount) {
			this.defocus -= amount;
			if(this.defocus < 0) this.defocus = 0;
		}
	}
}
