package com.hbm.tileentity.machine;

import java.util.List;

import api.hbm.redstoneoverradio.IRORInteractive;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityLoadedBase;
import com.hbm.util.Compat;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCargoElevator extends TileEntityLoadedBase implements IRORInteractive {

	public int height = 0;

	public int targetExtension;
	public double extension;
	public double prevExtension;
	public double syncExtension;
	private int sync;

	public static final double speed = 2D / 20D; // 2 blocks per second
	public static final int MAX_SIZE = 15;
	public boolean renderPlatform = false;

	public int minX = -1, maxX = 1, minZ = -1, maxZ = 1;

	private static final ForgeDirection[] HORIZONTALS = { ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.WEST, ForgeDirection.EAST };

	@Override
	public void updateEntity() {

		this.prevExtension = this.extension;

		if(!worldObj.isRemote) {

			// connect to lower elevator
			if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.cargo_elevator) {
				int[] pos = ((BlockDummyable) ModBlocks.cargo_elevator).findCore(worldObj, xCoord, yCoord - 1, zCoord);
				if(pos != null && pos[0] == xCoord && pos[2] == zCoord) {
					TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
					if (te instanceof TileEntityCargoElevator && ((TileEntityCargoElevator) te).hasSameFootprint(this)) {
						TileEntityCargoElevator lower = (TileEntityCargoElevator) te;
						lower.height += this.height + 1;
						lower.markDirty();
						BlockDummyable.safeRem = true;
						for (int x = xCoord + minX; x <= xCoord + maxX; x++)
							for (int z = zCoord + minZ; z <= zCoord + maxZ; z++) {
								for (int y = yCoord; y <= yCoord + this.height; y++) worldObj.setBlock(x, y, z, ModBlocks.cargo_elevator, 1, 3);
							}
						BlockDummyable.safeRem = false;
						return;
					}
				}
			}

			if (worldObj.getTotalWorldTime() % 10 == 0 && this.targetExtension == 0 && this.extension <= 0) {
				if (tryMergeHorizontal()) return;
			}

			if (this.extension < targetExtension) {  // go up
				this.extension += speed;
				this.extension = MathHelper.clamp_double(this.extension, 0, targetExtension);
			} else if (this.extension > targetExtension) {  // go down
				this.extension -= speed;
				this.extension = MathHelper.clamp_double(this.extension, targetExtension, this.height);
			}


			this.extension = MathHelper.clamp_double(this.extension, 0, this.height);
			
			// exist for at least one tick before the main portion gets rendered, fixes the short flickering platform that instantly despawns
			renderPlatform = true;
			
			this.networkPackNT(300);
		} else {

			if(this.sync > 0) {
				this.extension = this.extension + ((this.syncExtension - this.extension) / (float) this.sync);
				--this.sync;
			} else {
				this.extension = this.syncExtension;
			}
		}
		
		if(this.extension != this.prevExtension) {
			double liftUpper = this.yCoord + 1 + Math.max(this.extension, this.prevExtension);
			double liftLower = this.yCoord + 1 + Math.min(this.extension, this.prevExtension);
			List<Entity> toLift = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(
				xCoord + minX + 0.01, liftLower, zCoord + minZ + 0.01,
				xCoord + maxX + 0.99, liftUpper, zCoord + maxZ + 0.99
			));

			for(Entity e : toLift) {
				if(e instanceof EntityPlayer && !worldObj.isRemote) continue;
				if(e.boundingBox.minY >= liftLower && e.boundingBox.minY <= liftUpper) {
					double delta = e.boundingBox.minY - (this.yCoord + 1 + this.extension);
					e.moveEntity(0, -delta, 0);
					e.onGround = true;
					e.moveEntity(0, -0.125, 0);
				}
			}
		}
	}


	public int getSizeX() { return maxX - minX + 1; }
	public int getSizeZ() { return maxZ - minZ + 1; }
	public int getModules() { return (getSizeX() / 3) * (getSizeZ() / 3); }
	public boolean hasSameFootprint(TileEntityCargoElevator other) {
		return xCoord + minX == other.xCoord + other.minX && xCoord + maxX == other.xCoord + other.maxX
			&& zCoord + minZ == other.zCoord + other.minZ && zCoord + maxZ == other.zCoord + other.maxZ;
	}

	public void toggleElevator() {
		if (targetExtension == 0) {
			targetExtension = this.height;
		} else {
			targetExtension = 0;
		}
	}

	private boolean tryMergeHorizontal() {
		int x0 = xCoord + minX, x1 = xCoord + maxX;
		int z0 = zCoord + minZ, z1 = zCoord + maxZ;

		for (ForgeDirection dir : HORIZONTALS) {
			int cx = dir == ForgeDirection.EAST ? x1 + 1 : dir == ForgeDirection.WEST ? x0 - 1 : x0;
			int cz = dir == ForgeDirection.SOUTH ? z1 + 1 : dir == ForgeDirection.NORTH ? z0 - 1 : z0;

			if (worldObj.getBlock(cx, yCoord, cz) != ModBlocks.cargo_elevator) continue;
			int[] pos = ((BlockDummyable) ModBlocks.cargo_elevator).findCore(worldObj, cx, yCoord, cz);
			if (pos == null || pos[1] != yCoord) continue;

			TileEntity te = worldObj.getTileEntity(pos[0], pos[1], pos[2]);
			if (!(te instanceof  TileEntityCargoElevator) || te == this) continue;
			TileEntityCargoElevator other = (TileEntityCargoElevator) te;

			if (other.height != this.height || other.targetExtension != 0 || other.extension > 0) continue;

			int ox0 = other.xCoord + other.minX, ox1 = other.xCoord + other.maxX;
			int oz0 = other.zCoord + other.minZ, oz1 = other.zCoord + other.maxZ;

			boolean correctX = (dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) && oz0 == z0 && oz1 == z1;
			boolean correctZ = (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) && ox0 == x0 && ox1 == x1;
			if (!correctX && !correctZ) continue;

			int nx0 = Math.min(x0, ox0), nx1 = Math.max(x1, ox1);
			int nz0 = Math.min(z0, oz0), nz1 = Math.max(z1, oz1);
			if (nx1 - nx0 + 1 > MAX_SIZE || nz1 - nz0 + 1 > MAX_SIZE) continue;

			rewrite(nx0, nx1, nz0, nz1);
			return true;
		}
		return false;
	}

	private void rewrite(int x0, int x1, int z0, int z1) {
		BlockDummyable.safeRem = true;
		for (int x = x0; x <= x1; x++)
			for (int z = z0; z <= z1; z++) {
				if (x == xCoord && z == zCoord) continue;
				ForgeDirection d;
				if (x < xCoord) d = ForgeDirection.WEST;
				else if (x > xCoord) d = ForgeDirection.EAST;
				else if (z < zCoord) d = ForgeDirection.NORTH;
				else d = ForgeDirection.SOUTH;
				worldObj.setBlock(x, yCoord, z, ModBlocks.cargo_elevator, d.ordinal(), 3);
			}
		BlockDummyable.safeRem = false;

		minX = x0 - xCoord; maxX = x1 - xCoord;
		minZ = z0 - zCoord; maxZ = z1 - zCoord;
		markDirty();
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(renderPlatform);
		buf.writeShort((short) height);
		buf.writeDouble(extension);
		buf.writeByte(minX); buf.writeByte(maxX);
		buf.writeByte(minZ); buf.writeByte(maxZ);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.renderPlatform = buf.readBoolean();
		this.height = buf.readShort();
		this.syncExtension = buf.readDouble();

		int nMinX = buf.readByte(), nMaxX = buf.readByte(), nMinZ = buf.readByte(), nMaxZ = buf.readByte();
		if(nMinX != minX || nMaxX != maxX || nMinZ != minZ || nMaxZ != maxZ) bb = null;
		minX = nMinX; maxX = nMaxX; minZ = nMinZ; maxZ = nMaxZ;

		if(this.syncExtension > 0 && this.syncExtension < this.height) {
			this.sync = 3;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.extension = nbt.getDouble("extension");
		this.targetExtension = nbt.getInteger("targetExtension");
		this.height = nbt.getInteger("height");
		if(nbt.hasKey("minX")) { // Guard for old Elevators
			this.minX = nbt.getInteger("minX"); this.maxX = nbt.getInteger("maxX");
			this.minZ = nbt.getInteger("minZ"); this.maxZ = nbt.getInteger("maxZ");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("extension", extension);
		nbt.setInteger("targetExtension", this.targetExtension);
		nbt.setInteger("height", height);

		nbt.setInteger("minX", minX); nbt.setInteger("maxX", maxX);
		nbt.setInteger("minZ", minZ); nbt.setInteger("maxZ", maxZ);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		// workaround for angelica, extend AABB to build height by default instead of dynamically scaling
		int h = Compat.isModLoaded(Compat.MOD_ANG) ? 256 - yCoord : 1 + this.height;
		if(bb == null || bb.maxY - bb.minY < h) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord + minX,
					yCoord,
					zCoord + minZ,
					xCoord + maxX + 1,
					yCoord + h,
					zCoord + maxZ + 1
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
	public String runRORFunction(String name, String[] params) {
		if ((PREFIX_FUNCTION + "setextension").equals(name) && params.length > 0) {
			targetExtension = IRORInteractive.parseInt(params[0], 0, height);
			return null;
		}

		return null;
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[]{
			PREFIX_VALUE + "extension",
			PREFIX_FUNCTION + "setextension" + NAME_SEPARATOR + "amount",
		};
	}
}
