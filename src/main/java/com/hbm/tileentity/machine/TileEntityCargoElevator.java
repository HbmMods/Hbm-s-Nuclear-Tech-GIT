package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityLoadedBase;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class TileEntityCargoElevator extends TileEntityLoadedBase {

	public int height = 0;
	
	public double extension;
	public double prevExtension;
	public double syncExtension;
	private int sync;
	
	public boolean isExtending;
	public static final double speed = 2D / 20D; // 2 blocks per second
	public boolean renderPlatform = false;
	
	@Override
	public void updateEntity() {

		this.prevExtension = this.extension;
		
		if(!worldObj.isRemote) {
			
			// connect to lower elevator
			if(worldObj.getBlock(xCoord, yCoord - 1, zCoord) == ModBlocks.cargo_elevator) {
				int[] pos = ((BlockDummyable) ModBlocks.cargo_elevator).findCore(worldObj, xCoord, yCoord - 1, zCoord);
				if(pos != null && pos[0] == xCoord && pos[2] == zCoord) {
					TileEntityCargoElevator lower = (TileEntityCargoElevator) worldObj.getTileEntity(pos[0], pos[1], pos[2]);
					lower.height += this.height + 1;
					for(int x = xCoord -1; x < xCoord + 2; x++) for(int z = zCoord -1; z < zCoord + 2; z++) {
						for(int y = yCoord; y <= yCoord + this.height; y++) worldObj.setBlock(x, y, z, ModBlocks.cargo_elevator, 1, 3);
					}
					return;
				}
			}
			
			if(this.isExtending && this.extension < this.height) {
				this.extension += this.speed;
			}
			
			if(!this.isExtending && this.extension > 0) {
				this.extension -= this.speed;
			}
			
			this.extension = MathHelper.clamp_double(this.extension, 0, this.height);
			
			// exist for at least one tick before the main portion gets rendered, fixes the short flickering platform that instantly despawns
			renderPlatform = true;
			
			this.networkPackNT(100);
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
			List<Entity> toLift = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord - 0.99, liftLower, zCoord - 0.99, xCoord + 1.99, liftUpper, zCoord + 1.99));
			
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
	
	public void toggleElevator() {
		
		if(this.extension >= this.height) {
			this.isExtending = false;
		}
		
		if(this.extension <= 0) {
			this.isExtending = true;
		}
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		buf.writeBoolean(renderPlatform);
		buf.writeShort((short) height);
		buf.writeDouble(extension);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		this.renderPlatform = buf.readBoolean();
		this.height = buf.readShort();
		this.syncExtension = buf.readDouble();

		if(this.syncExtension > 0 && this.syncExtension < this.height) {
			this.sync = 3;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.extension = nbt.getDouble("extension");
		this.isExtending = nbt.getBoolean("isExtending");
		this.height = nbt.getInteger("height");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("extension", extension);
		nbt.setBoolean("isExtending", isExtending);
		nbt.setInteger("height", height);
	}
	
	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		int h = 1 + this.height;
		
		if(bb == null || bb.maxY - bb.minY < h) {
			bb = AxisAlignedBB.getBoundingBox(
					xCoord - 1,
					yCoord,
					zCoord - 1,
					xCoord + 2,
					yCoord + h,
					zCoord + 2
					);
		}

		return bb;
	}
}
