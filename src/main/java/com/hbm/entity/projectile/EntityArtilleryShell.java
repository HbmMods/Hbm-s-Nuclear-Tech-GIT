package com.hbm.entity.projectile;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableSet;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.items.weapon.ItemAmmoArty;
import com.hbm.items.weapon.ItemAmmoArty.ArtilleryShell;
import com.hbm.main.MainRegistry;

import api.hbm.entity.IRadarDetectable;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityArtilleryShell extends EntityThrowableNT implements IChunkLoader, IRadarDetectable {

	private Ticket loaderTicket;
	
	private int turnProgress;
	private double syncPosX;
	private double syncPosY;
	private double syncPosZ;
	private double syncYaw;
	private double syncPitch;
	@SideOnly(Side.CLIENT)
	private double velocityX;
	@SideOnly(Side.CLIENT)
	private double velocityY;
	@SideOnly(Side.CLIENT)
	private double velocityZ;

	private double targetX;
	private double targetY;
	private double targetZ;
	private boolean shouldWhistle = false;
	private boolean didWhistle = false;
	
	private ItemStack cargo = null;
	
	public EntityArtilleryShell(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.setSize(0.5F, 0.5F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
		this.dataWatcher.addObject(10, new Integer(0));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}
	
	public EntityArtilleryShell setType(int type) {
		this.dataWatcher.updateObject(10, type);
		return this;
	}
	
	public ArtilleryShell getType() {
		try {
			return ItemAmmoArty.itemTypes[this.dataWatcher.getWatchableObjectInt(10)];
		} catch(Exception ex) {
			return ItemAmmoArty.itemTypes[0];
		}
	}
	
	public double[] getTarget() {
		return new double[] { this.targetX, this.targetY, this.targetZ };
	}
	
	public void setTarget(double x, double y, double z) {
		this.targetX = x;
		this.targetY = y;
		this.targetZ = z;
	}
	
	public double getTargetHeight() {
		return this.targetY;
	}
	
	public void setWhistle(boolean whistle) {
		this.shouldWhistle = whistle;
	}
	
	public boolean getWhistle() {
		return this.shouldWhistle;
	}
	
	public boolean didWhistle() {
		return this.didWhistle;
	}
	
	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote) {
			super.onUpdate();
			
			if(!didWhistle && this.shouldWhistle) {
				double speed = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
				double deltaX = this.posX - this.targetX;
				double deltaZ = this.posZ - this.targetZ;
				double dist = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
				
				if(speed * 18 > dist) {
					worldObj.playSoundEffect(this.targetX, this.targetY, this.targetZ, "hbm:turret.mortarWhistle", 15.0F, 0.9F + rand.nextFloat() * 0.2F);
					this.didWhistle = true;
				}
			}

			loadNeighboringChunks((int)Math.floor(posX / 16D), (int)Math.floor(posZ / 16D));
			this.getType().onUpdate(this);
			
		} else {
			if(this.turnProgress > 0) {
				double interpX = this.posX + (this.syncPosX - this.posX) / (double) this.turnProgress;
				double interpY = this.posY + (this.syncPosY - this.posY) / (double) this.turnProgress;
				double interpZ = this.posZ + (this.syncPosZ - this.posZ) / (double) this.turnProgress;
				double d = MathHelper.wrapAngleTo180_double(this.syncYaw - (double) this.rotationYaw);
				this.rotationYaw = (float) ((double) this.rotationYaw + d / (double) this.turnProgress);
				this.rotationPitch = (float)((double)this.rotationPitch + (this.syncPitch - (double)this.rotationPitch) / (double)this.turnProgress);
				--this.turnProgress;
				this.setPosition(interpX, interpY, interpZ);
			} else {
				this.setPosition(this.posX, this.posY, this.posZ);
			}
			
			if(Vec3.createVectorHelper(this.syncPosX - this.posX, this.syncPosY - this.posY, this.syncPosZ - this.posZ).lengthVector() < 0.2) {
				worldObj.spawnParticle("smoke", posX, posY + 0.5, posZ, 0.0, 0.1, 0.0);
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_) {
		this.velocityX = this.motionX = p_70016_1_;
		this.velocityY = this.motionY = p_70016_3_;
		this.velocityZ = this.motionZ = p_70016_5_;
	}
	
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int theNumberThree) {
		this.syncPosX = x;
		this.syncPosY = y;
		this.syncPosZ = z;
		this.syncYaw = yaw;
		this.syncPitch = pitch;
		this.turnProgress = theNumberThree;
		this.motionX = this.velocityX;
		this.motionY = this.velocityY;
		this.motionZ = this.velocityZ;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		
		if(!worldObj.isRemote) {
			
			if(mop.typeOfHit == mop.typeOfHit.ENTITY && mop.entityHit instanceof EntityArtilleryShell) return;
			this.getType().onImpact(this, mop);
		}
	}

	@Override
	public void init(Ticket ticket) {
		if(!worldObj.isRemote && ticket != null) {
			if(loaderTicket == null) {
				loaderTicket = ticket;
				loaderTicket.bindEntity(this);
				loaderTicket.getModData();
			}
			ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
		}
	}

	List<ChunkCoordIntPair> loadedChunks = new ArrayList<ChunkCoordIntPair>();

	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!worldObj.isRemote && loaderTicket != null) {

			for(ChunkCoordIntPair chunk : ImmutableSet.copyOf(loaderTicket.getChunkList())) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}

			loadedChunks.clear();
			loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
			//loadedChunks.add(new ChunkCoordIntPair(newChunkX + (int) Math.floor((this.posX + this.motionX) / 16D), newChunkZ + (int) Math.floor((this.posZ + this.motionZ) / 16D)));

			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.forceChunk(loaderTicket, chunk);
			}
		}
	}
	
	public void killAndClear() {
		this.setDead();
		this.clearChunkLoader();
	}
	
	public void clearChunkLoader() {
		if(!worldObj.isRemote && loaderTicket != null) {
			ForgeChunkManager.releaseTicket(loaderTicket);
			this.loaderTicket = null;
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		
		nbt.setInteger("type", this.dataWatcher.getWatchableObjectInt(10));
		nbt.setBoolean("shouldWhistle", this.shouldWhistle);
		nbt.setBoolean("didWhistle", this.didWhistle);
		nbt.setDouble("targetX", this.targetX);
		nbt.setDouble("targetY", this.targetY);
		nbt.setDouble("targetZ", this.targetZ);

		if(this.cargo != null)
			nbt.setTag("cargo", this.cargo.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);

		this.dataWatcher.updateObject(10, nbt.getInteger("type"));
		this.shouldWhistle = nbt.getBoolean("shouldWhistle");
		this.didWhistle = nbt.getBoolean("didWhistle");
		this.targetX = nbt.getDouble("targetX");
		this.targetY = nbt.getDouble("targetY");
		this.targetZ = nbt.getDouble("targetZ");

		NBTTagCompound compound = nbt.getCompoundTag("cargo");
		this.setCargo(ItemStack.loadItemStackFromNBT(compound));
	}

	@Override
	protected float getAirDrag() {
		return 1.0F;
	}

	@Override
	public double getGravityVelocity() {
		return 9.81 * 0.05;
	}

	@Override
	protected int groundDespawn() {
		return cargo != null ? 0 : 1200;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean canAttackWithItem() {
		return true;
	}
	
	public void setCargo(ItemStack stack) {
		this.cargo = stack;
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {

		if(!worldObj.isRemote) {
			if(this.cargo != null) {
				player.inventory.addItemStackToInventory(this.cargo.copy());
				player.inventoryContainer.detectAndSendChanges();
			}
			this.setDead();
		}

		return false;
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.ARTILLERY;
	}
}
