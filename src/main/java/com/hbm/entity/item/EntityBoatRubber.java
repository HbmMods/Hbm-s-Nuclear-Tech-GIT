package com.hbm.entity.item;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.TrackerUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityBoatRubber extends Entity {

	private boolean isBoatEmpty;
	private double speedMultiplier;
	private int boatPosRotationIncrements;
	private double boatX;
	private double boatY;
	private double boatZ;
	private double boatYaw;
	private double boatPitch;
	@SideOnly(Side.CLIENT) private double velocityX;
	@SideOnly(Side.CLIENT) private double velocityY;
	@SideOnly(Side.CLIENT) private double velocityZ;
	public float prevRenderYaw;

	public EntityBoatRubber(World world) {
		super(world);
		this.isBoatEmpty = true;
		this.speedMultiplier = 0.07D;
		this.preventEntitySpawning = true;
		this.setSize(1.5F, 0.6F);
		this.yOffset = this.height / 2.0F;
	}

	public EntityBoatRubber(World world, double x, double y, double z) {
		this(world);
		this.setPosition(x, y + (double) this.yOffset, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	protected void entityInit() {
		this.dataWatcher.addObject(17, new Integer(0));
		this.dataWatcher.addObject(18, new Integer(1));
		this.dataWatcher.addObject(19, new Float(0.0F));
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBox(Entity entity) {
		return entity.boundingBox;
	}
	@Override
	public AxisAlignedBB getBoundingBox() {
		return this.boundingBox;
	}
	@Override
	public boolean canBePushed() {
		return true;
	}
	@Override
	public double getMountedYOffset() {
		return (double) this.height * 0.0D - 0.3D;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(this.isEntityInvulnerable()) {
			return false;
		} else if(!this.worldObj.isRemote && !this.isDead) {
			this.setForwardDirection(-this.getForwardDirection());
			this.setTimeSinceHit(10);
			this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
			this.setBeenAttacked();
			boolean hitByCreative = source.getEntity() instanceof EntityPlayer && ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

			if(hitByCreative || this.getDamageTaken() > 40.0F) {
				if(this.riddenByEntity != null) {
					this.riddenByEntity.mountEntity(this);
				}

				if(!hitByCreative) {
					this.dropBoat();
				}

				this.setDead();
			}

			return true;
		} else {
			return true;
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int interp) {
		if(this.isBoatEmpty) {
			this.boatPosRotationIncrements = interp;
		} else {
			double d3 = x - this.posX;
			double d4 = y - this.posY;
			double d5 = z - this.posZ;
			double d6 = d3 * d3 + d4 * d4 + d5 * d5;

			if(d6 <= 1.0D) {
				return;
			}

			this.boatPosRotationIncrements = 3;
		}

		this.boatX = x;
		this.boatY = y;
		this.boatZ = z;
		this.boatYaw = (double) yaw;
		this.boatPitch = (double) pitch;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z) {
		this.velocityX = this.motionX = x;
		this.velocityY = this.motionY = y;
		this.velocityZ = this.motionZ = z;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();

		if(this.getTimeSinceHit() > 0) {
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if(this.getDamageTaken() > 0.0F) {
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		byte b0 = 5;
		double d0 = 0.0D;

		for(int i = 0; i < b0; ++i) {
			double d1 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double) (i + 0) / (double) b0 - 0.125D;
			double d3 = this.boundingBox.minY + (this.boundingBox.maxY - this.boundingBox.minY) * (double) (i + 1) / (double) b0 - 0.125D;
			AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.boundingBox.minX, d1, this.boundingBox.minZ, this.boundingBox.maxX, d3, this.boundingBox.maxZ);

			if(this.worldObj.isAABBInMaterial(axisalignedbb, Material.water)) {
				d0 += 1.0D / (double) b0;
			}
		}

		if(this.worldObj.isRemote && this.isBoatEmpty) {
			if(this.boatPosRotationIncrements > 0) {
				double x = this.posX + (this.boatX - this.posX) / (double) this.boatPosRotationIncrements;
				double y = this.posY + (this.boatY - this.posY) / (double) this.boatPosRotationIncrements;
				double z = this.posZ + (this.boatZ - this.posZ) / (double) this.boatPosRotationIncrements;
				double yaw = MathHelper.wrapAngleTo180_double(this.boatYaw - (double) this.rotationYaw);
				this.rotationYaw = (float) ((double) this.rotationYaw + yaw / (double) this.boatPosRotationIncrements);
				this.rotationPitch = (float) ((double) this.rotationPitch + (this.boatPitch - (double) this.rotationPitch) / (double) this.boatPosRotationIncrements);
				--this.boatPosRotationIncrements;
				this.setPosition(x, y, z);
				
			} else {
				double x = this.posX + this.motionX;
				double y = this.posY + this.motionY;
				double z = this.posZ + this.motionZ;
				this.setPosition(x, y, z);

				if(this.onGround) {
					this.motionX *= 0.5D;
					this.motionY *= 0.5D;
					this.motionZ *= 0.5D;
				}
				
				this.passiveDeccelerate();
			}
		} else {
			if(d0 < 1.0D) {
				double d2 = d0 * 2.0D - 1.0D;
				this.motionY += 0.04D * d2;
			} else {
				if(this.motionY < 0.0D) {
					this.motionY /= 2.0D;
				}

				this.motionY += 0.007000000216066837D;
			}
			
			double prevSpeedSq = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			
			this.isAirBorne = false;

			if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
				EntityLivingBase entitylivingbase = (EntityLivingBase) this.riddenByEntity;
				
				if(entitylivingbase.moveForward != 0 || entitylivingbase.moveStrafing != 0) {
					
					Vec3 dir = Vec3.createVectorHelper(0, 0, 1);
					dir.rotateAroundY((float) -((this.rotationYaw + 90) * Math.PI / 180D));
					this.motionX += dir.xCoord * this.speedMultiplier * entitylivingbase.moveForward * 0.05D;
					this.motionZ += dir.zCoord * this.speedMultiplier * entitylivingbase.moveForward * 0.05D;
					
					float prevYaw = this.rotationYaw;
					this.rotationYaw -= entitylivingbase.moveStrafing * 3;
					
					Vec3 newMotion = Vec3.createVectorHelper(motionX, 0, motionZ);
					newMotion.rotateAroundY((float) (-(this.rotationYaw - prevYaw) * Math.PI / 180D));
					this.motionX = newMotion.xCoord;
					this.motionZ = newMotion.zCoord;
					
					//HOLY HELL! if we don't shit ourselves over packets and send them at proper intervals, entities are suddenly smooth! who would have thought! mojang certainly didn't!
					EntityTrackerEntry entry = TrackerUtil.getTrackerEntry((WorldServer) worldObj, this.getEntityId());
					entry.lastYaw = MathHelper.floor_float(this.rotationYaw * 256.0F / 360.0F) + 10; //force-trigger rotation update
				}
			} else {
				this.motionX *= 0.95D;
				this.motionY *= 0.95D;
				this.motionZ *= 0.95D;
			}

			double speedSq = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

			if(speedSq > 0.5D) {
				double d4 = 0.5D / speedSq;
				this.motionX *= d4;
				this.motionZ *= d4;
				speedSq = 0.5D;
			}

			if(speedSq > prevSpeedSq && this.speedMultiplier < 0.5D) {
				this.speedMultiplier += (0.5D - this.speedMultiplier) / 50.0D;

				if(this.speedMultiplier > 0.5D) {
					this.speedMultiplier = 0.5D;
				}
			} else {
				this.speedMultiplier -= (this.speedMultiplier - 0.07D) / 35.0D;

				if(this.speedMultiplier < 0.07D) {
					this.speedMultiplier = 0.07D;
				}
			}

			for(int index = 0; index < 4; ++index) {
				int x = MathHelper.floor_double(this.posX + ((double) (index % 2) - 0.5D) * 0.8D);
				int z = MathHelper.floor_double(this.posZ + ((double) (index / 2) - 0.5D) * 0.8D);

				for(int yOff = 0; yOff < 2; ++yOff) {
					int y = MathHelper.floor_double(this.posY) + yOff;
					Block block = this.worldObj.getBlock(x, y, z);

					if(block == Blocks.snow_layer) {
						this.worldObj.setBlockToAir(x, y, z);
						this.isCollidedHorizontally = false;
					} else if(block == Blocks.waterlily) {
						this.worldObj.func_147480_a(x, y, z, true);
						this.isCollidedHorizontally = false;
					}
				}
			}

			if(this.onGround) {
				this.motionX *= 0.5D;
				this.motionY *= 0.5D;
				this.motionZ *= 0.5D;
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);

			if(this.isCollidedHorizontally && prevSpeedSq > 0.2D) {
				this.motionX *= 0.25D;
				this.motionY *= 0.25D;
				this.motionZ *= 0.25D;
				
			} else {
				this.passiveDeccelerate();
			}

			this.rotationPitch = 0.0F;
			
			if(!(this.riddenByEntity instanceof EntityLivingBase)) {
				double yaw = (double) this.rotationYaw;
				double deltaX = this.prevPosX - this.posX;
				double deltaZ = this.prevPosZ - this.posZ;
	
				if(deltaX * deltaX + deltaZ * deltaZ > 0.001D) {
					yaw = (double) ((float) (Math.atan2(deltaZ, deltaX) * 180.0D / Math.PI));
				}
	
				double rotationSpeed = MathHelper.wrapAngleTo180_double(yaw - (double) this.rotationYaw);
	
				if(rotationSpeed > 20.0D) {
					rotationSpeed = 20.0D;
				}
	
				if(rotationSpeed < -20.0D) {
					rotationSpeed = -20.0D;
				}
	
				this.rotationYaw = (float) ((double) this.rotationYaw + rotationSpeed);
			}
			
			this.setRotation(this.rotationYaw, this.rotationPitch);

			if(!this.worldObj.isRemote) {
				List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.2D, 0.0D, 0.2D));

				if(list != null && !list.isEmpty()) {
					for(int k1 = 0; k1 < list.size(); ++k1) {
						Entity entity = (Entity) list.get(k1);

						if(entity != this.riddenByEntity && entity.canBePushed() && (entity instanceof EntityBoatRubber || entity instanceof EntityBoat)) {
							entity.applyEntityCollision(this);
						}
					}
				}

				if(this.riddenByEntity != null && this.riddenByEntity.isDead) {
					this.riddenByEntity = null;
				}
			}
		}

		double moX = this.prevPosX - this.posX;
		double moZ = this.prevPosZ - this.posZ;
		double prevSpeedSq = Math.sqrt(moX * moX + moZ * moZ);

		if(prevSpeedSq > 0.2625D) {
			double cosYaw = Math.cos(this.rotationYaw * Math.PI / 180.0D);
			double sinYaw = Math.sin(this.rotationYaw * Math.PI / 180.0D);

			for(double j = 0; j < 1.0D + prevSpeedSq * 60.0D; ++j) {
				double offset = (double) (this.rand.nextFloat() * 2.0F - 1.0F);
				double side = (double) (this.rand.nextInt(2) * 2 - 1) * 0.7D;
				double magX;
				double magZ;

				if(this.rand.nextBoolean()) {
					magX = this.posX - cosYaw * offset * 0.8D + sinYaw * side;
					magZ = this.posZ - sinYaw * offset * 0.8D - cosYaw * side;
					this.worldObj.spawnParticle("splash", magX, this.posY - 0.125D, magZ, moX, 0.1, moZ);
				} else {
					magX = this.posX + cosYaw + sinYaw * offset * 0.7D;
					magZ = this.posZ + sinYaw - cosYaw * offset * 0.7D;
					this.worldObj.spawnParticle("splash", magX, this.posY - 0.125D, magZ, moX, 0.1, moZ);
				}
			}
		}
	}
	
	protected void passiveDeccelerate() {
		this.motionX *= 0.99D;
		this.motionY *= 0.95D;
		this.motionZ *= 0.99D;
	}
	
	@Override
	public void updateRiderPosition() {
		if(this.riddenByEntity != null) {
			double offX = Math.cos((double) this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			double offZ = Math.sin((double) this.rotationYaw * Math.PI / 180.0D) * 0.4D;
			this.riddenByEntity.setPosition(this.posX + offX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ + offZ);
			
			if(this.riddenByEntity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) this.riddenByEntity;
				player.renderYawOffset = MathHelper.wrapAngleTo180_float(this.rotationYaw + 90F);
			}
		}
	}
	
	@Override protected void writeEntityToNBT(NBTTagCompound p_70014_1_) { }
	@Override protected void readEntityFromNBT(NBTTagCompound p_70037_1_) { }
	
	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}
	
	@Override
	public boolean interactFirst(EntityPlayer player) {
		if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && this.riddenByEntity != player) {
			return true;
		} else {
			if(!this.worldObj.isRemote) {
				player.mountEntity(this);
			}

			return true;
		}
	}
	
	@Override
	protected void updateFallState(double fall, boolean onGround) {
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY);
		int z = MathHelper.floor_double(this.posZ);

		if(onGround) {
			if(this.fallDistance > 5.0F) {
				this.fall(this.fallDistance);

				if(!this.worldObj.isRemote && !this.isDead) {
					this.setDead();
					this.dropBoat();
				}

				this.fallDistance = 0.0F;
			}
		} else if(this.worldObj.getBlock(x, y - 1, z).getMaterial() != Material.water && fall < 0.0D) {
			this.fallDistance = (float) ((double) this.fallDistance - fall);
		}
	}
	
	public void dropBoat() {
		this.func_145778_a(ModItems.boat_rubber, 1, 0.0F);
	}
	
	public void setDamageTaken(float amount) {
		this.dataWatcher.updateObject(19, Float.valueOf(amount));
	}

	public float getDamageTaken() {
		return this.dataWatcher.getWatchableObjectFloat(19);
	}

	public void setTimeSinceHit(int time) {
		this.dataWatcher.updateObject(17, Integer.valueOf(time));
	}

	public int getTimeSinceHit() {
		return this.dataWatcher.getWatchableObjectInt(17);
	}

	public void setForwardDirection(int dir) {
		this.dataWatcher.updateObject(18, Integer.valueOf(dir));
	}

	public int getForwardDirection() {
		return this.dataWatcher.getWatchableObjectInt(18);
	}

	@SideOnly(Side.CLIENT)
	public void setIsBoatEmpty(boolean empty) {
		this.isBoatEmpty = empty;
	}
}
