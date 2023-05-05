package com.hbm.entity.train;

import com.hbm.blocks.rail.IRailNTM;
import com.hbm.blocks.rail.IRailNTM.RailLeaveInfo;
import com.hbm.blocks.rail.IRailNTM.TrackGauge;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PlayerInformPacket;
import com.hbm.util.ChatBuilder;
import com.hbm.util.ParticleUtil;
import com.hbm.util.fauxpointtwelve.BlockPos;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityRailCarBase extends Entity {
	
	public boolean isOnRail = true;
	private int turnProgress;
	private double trainX;
	private double trainY;
	private double trainZ;
	private double trainYaw;
	private double trainPitch;
	@SideOnly(Side.CLIENT) private double velocityX;
	@SideOnly(Side.CLIENT) private double velocityY;
	@SideOnly(Side.CLIENT) private double velocityZ;

	public EntityRailCarBase(World world) {
		super(world);
	}

	@Override protected void entityInit() { }
	@Override protected void readEntityFromNBT(NBTTagCompound nbt) { }
	@Override protected void writeEntityToNBT(NBTTagCompound nbt) { }

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}
	
	@Override
	public void onUpdate() {

		if(this.worldObj.isRemote) {
			if(this.turnProgress > 0) {
				double x = this.posX + (this.trainX - this.posX) / (double) this.turnProgress;
				double y = this.posY + (this.trainY - this.posY) / (double) this.turnProgress;
				double z = this.posZ + (this.trainZ - this.posZ) / (double) this.turnProgress;
				double yaw = MathHelper.wrapAngleTo180_double(this.trainYaw - (double) this.rotationYaw);
				this.rotationYaw = (float) ((double) this.rotationYaw + yaw / (double) this.turnProgress);
				this.rotationPitch = (float) ((double) this.rotationPitch + (this.trainPitch - (double) this.rotationPitch) / (double) this.turnProgress);
				--this.turnProgress;
				this.setPosition(x, y, z);
				this.setRotation(this.rotationYaw, this.rotationPitch);
				this.setRotation((float)this.trainYaw, this.rotationPitch);
			} else {
				this.setPosition(this.posX, this.posY, this.posZ);
				this.setRotation(this.rotationYaw, this.rotationPitch);
				this.setRotation((float)this.trainYaw, this.rotationPitch);
			}
		} else {
			
			BlockPos anchor = this.getCurentAnchorPos();
			Vec3 corePos = getRelPosAlongRail(anchor, this.getCurrentSpeed());
			
			if(corePos == null) {
				this.derail();
			} else {
				this.prevPosX = this.posX;
				this.prevPosY = this.posY;
				this.prevPosZ = this.posZ;
				this.setPosition(corePos.xCoord, corePos.yCoord, corePos.zCoord);
				
				anchor = this.getCurentAnchorPos(); //reset origin to new position
				Vec3 frontPos = getRelPosAlongRail(anchor, this.getLengthSpan());
				Vec3 backPos = getRelPosAlongRail(anchor, -this.getLengthSpan());

				if(frontPos == null || backPos == null) {
					this.derail();
				} else {
					this.rotationYaw = generateYaw(frontPos, backPos);
				}
			}
			
			PacketDispatcher.wrapper.sendTo(new PlayerInformPacket(new ChatComponentText("Yaw: " + this.rotationYaw), 665, 3000), (EntityPlayerMP) worldObj.playerEntities.get(0));
		}
	}
	
	public Vec3 getRelPosAlongRail(BlockPos anchor, double distanceToCover) {
		
		float yaw = this.rotationYaw;
		
		Vec3 next = Vec3.createVectorHelper(posX, posY, posZ);
		int it = 0;
		
		do {
			
			it++;
			
			if(it > 30) {
				worldObj.createExplosion(this, posX, posY, posZ, 5F, false);
				this.derail();
				return null;
			}
			
			int x = anchor.getX();
			int y = anchor.getY();
			int z = anchor.getZ();
			Block block = worldObj.getBlock(x, y, z);
			
			Vec3 rot = Vec3.createVectorHelper(0, 0, 1);
			rot.rotateAroundY((float) (-yaw * Math.PI / 180D));
			
			if(block instanceof IRailNTM) {
				IRailNTM rail = (IRailNTM) block;
				
				if(rail.getGauge(worldObj, x, y, z) == this.getGauge()) {
					RailLeaveInfo info = new RailLeaveInfo();
					Vec3 prev = next;
					next = rail.getTravelLocation(worldObj, x, y, z, posX, posY, posZ, rot.xCoord, rot.yCoord, rot.zCoord, distanceToCover, info);
					distanceToCover = info.overshoot;
					anchor = info.pos;
					yaw = generateYaw(next, prev);

					//if(info.overshoot > 0) System.out.println("[" + (worldObj.getTotalWorldTime() % 100) + "] Left track " + ((Block) rail).getUnlocalizedName() + " with " + ((int)(info.overshoot * 100) / 100D) + "m more to go!");
					
				} else {
					return null;
				}
			} else {
				return null;
			}
			
		} while(distanceToCover != 0); //if there's still length to cover, keep going
		
		return next;
	}
	
	public float generateYaw(Vec3 front, Vec3 back) {
		double deltaX = front.xCoord - back.xCoord;
		double deltaZ = front.zCoord - back.zCoord;
		double radians = Math.atan(deltaZ / deltaX);
		return (float) MathHelper.wrapAngleTo180_double(radians * 180D / Math.PI - 90);
	}

	/** Returns the amount of blocks that the train should move per tick */
	public abstract double getCurrentSpeed();
	/** Returns the gauge of this train */
	public abstract TrackGauge getGauge();
	/** Returns the length between the core and one of the bogies */
	public abstract double getLengthSpan();
	
	/** Returns the "true" position of the train, i.e. the block it wants to snap to */
	public BlockPos getCurentAnchorPos() {
		return new BlockPos(posX, posY, posZ);
	}
	
	public void derail() {
		isOnRail = false;
		this.setDead();
	}
	
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double posX, double posY, double posZ, float yaw, float pitch, int turnProg) {
		this.trainX = posX;
		this.trainY = posY;
		this.trainZ = posZ;
		this.trainYaw = (double) yaw;
		this.trainPitch = (double) pitch;
		this.turnProgress = turnProg + 2;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	@SideOnly(Side.CLIENT)
	public void setVelocity(double mX, double mY, double mZ) {
		this.velocityX = this.motionX = mX;
		this.velocityY = this.motionY = mY;
		this.velocityZ = this.motionZ = mZ;
	}
}
