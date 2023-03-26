package com.hbm.entity.item;

import com.hbm.lib.Library;
import com.hbm.util.fauxpointtwelve.BlockPos;

import api.hbm.conveyor.IConveyorBelt;
import api.hbm.conveyor.IEnterableBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class EntityMovingConveyorObject extends Entity {
	
	protected int turnProgress;
	protected double syncPosX;
	protected double syncPosY;
	protected double syncPosZ;
	@SideOnly(Side.CLIENT) protected double velocityX;
	@SideOnly(Side.CLIENT) protected double velocityY;
	@SideOnly(Side.CLIENT) protected double velocityZ;

	public EntityMovingConveyorObject(World world) {
		super(world);
		this.noClip = true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canAttackWithItem() {
		return true;
	}

	@Override
	public boolean hitByEntity(Entity attacker) {

		if(attacker instanceof EntityPlayer) {
			this.setDead();
		}
		
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {
		return true;
	}

	@Override
	public void onUpdate() {
		
		if(worldObj.isRemote) {
			if(this.turnProgress > 0) {
				double interpX = this.posX + (this.syncPosX - this.posX) / (double) this.turnProgress;
				double interpY = this.posY + (this.syncPosY - this.posY) / (double) this.turnProgress;
				double interpZ = this.posZ + (this.syncPosZ - this.posZ) / (double) this.turnProgress;
				--this.turnProgress;
				this.setPosition(interpX, interpY, interpZ);
			} else {
				this.setPosition(this.posX, this.posY, this.posZ);
			}
		}

		if(!worldObj.isRemote) {
			
			ticksExisted++;
			
			if(this.ticksExisted <= 5) {
				return;
			}

			int blockX = (int) Math.floor(posX);
			int blockY = (int) Math.floor(posY);
			int blockZ = (int) Math.floor(posZ);
			
			Block b = worldObj.getBlock(blockX, blockY, blockZ);
			boolean isOnConveyor = b instanceof IConveyorBelt && ((IConveyorBelt) b).canItemStay(worldObj, blockX, blockY, blockZ, Vec3.createVectorHelper(posX, posY, posZ));
			
			if(!isOnConveyor) {
				
				if(onLeaveConveyor()) {
					return;
				}
			} else {
				
				Vec3 target = ((IConveyorBelt) b).getTravelLocation(worldObj, blockX, blockY, blockZ, Vec3.createVectorHelper(posX, posY, posZ), getMoveSpeed());
				this.motionX = target.xCoord - posX;
				this.motionY = target.yCoord - posY;
				this.motionZ = target.zCoord - posZ;
			}
			
			BlockPos lastPos = new BlockPos(posX, posY, posZ);
			this.moveEntity(motionX, motionY, motionZ);
			BlockPos newPos = new BlockPos(posX, posY, posZ);
			
			if(!lastPos.equals(newPos)) {
				
				Block newBlock = worldObj.getBlock(newPos.getX(), newPos.getY(), newPos.getZ());
				
				if(newBlock instanceof IEnterableBlock) {
					
					ForgeDirection dir = ForgeDirection.UNKNOWN;

					if(lastPos.getX() > newPos.getX() && lastPos.getY() == newPos.getY() && lastPos.getZ() == newPos.getZ()) dir = Library.POS_X;
					else if(lastPos.getX() < newPos.getX() && lastPos.getY() == newPos.getY() && lastPos.getZ() == newPos.getZ()) dir = Library.NEG_X;
					else if(lastPos.getX() == newPos.getX() && lastPos.getY() > newPos.getY() && lastPos.getZ() == newPos.getZ()) dir = Library.POS_Y;
					else if(lastPos.getX() == newPos.getX() && lastPos.getY() < newPos.getY() && lastPos.getZ() == newPos.getZ()) dir = Library.NEG_Y;
					else if(lastPos.getX() == newPos.getX() && lastPos.getY() == newPos.getY() && lastPos.getZ() > newPos.getZ()) dir = Library.POS_Z;
					else if(lastPos.getX() == newPos.getX() && lastPos.getY() == newPos.getY() && lastPos.getZ() < newPos.getZ()) dir = Library.NEG_Z;
					
					IEnterableBlock enterable = (IEnterableBlock) newBlock;
					enterBlock(enterable, newPos, dir);
					
				} else {
					
					if(!newBlock.getMaterial().isSolid()) {
						
						newBlock = worldObj.getBlock(newPos.getX(), newPos.getY() - 1, newPos.getZ());
						
						if(newBlock instanceof IEnterableBlock) {
							
							IEnterableBlock enterable = (IEnterableBlock) newBlock;
							enterBlockFalling(enterable, newPos);
						}
					}
				}
			}
		}
	}

	public abstract void enterBlock(IEnterableBlock enterable, BlockPos pos, ForgeDirection dir);
	
	public void enterBlockFalling(IEnterableBlock enterable, BlockPos pos) {
		this.enterBlock(enterable, pos.add(0, -1, 0), ForgeDirection.UP);
	}
	
	/**
	 * @return true if the update loop should end
	 */
	public abstract boolean onLeaveConveyor();
	
	public double getMoveSpeed() {
		return 0.0625D;
	}
	
	@SideOnly(Side.CLIENT)
	public void setVelocity(double motionX, double motionY, double motionZ) {
		this.velocityX = this.motionX = motionX;
		this.velocityY = this.motionY = motionY;
		this.velocityZ = this.motionZ = motionZ;
	}
	
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int theNumberThree) {
		this.syncPosX = x;
		this.syncPosY = y;
		this.syncPosZ = z;
		this.turnProgress = theNumberThree + 2; //use 4-ply for extra smoothness
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}
}
