package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.entity.projectile.EntityThrowableInterp;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineRadarNT;

import api.hbm.entity.IRadarDetectable;
import api.hbm.entity.IRadarDetectableNT;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public class EntityMissileAntiBallistic extends EntityThrowableInterp implements IChunkLoader, IRadarDetectable, IRadarDetectableNT {

	private Ticket loaderTicket;
	public Entity tracking;
	public double velocity;
	protected int activationTimer;

	public static double baseSpeed = 1.5D;

	public EntityMissileAntiBallistic(World world) {
		super(world);
		this.setSize(1.5F, 1.5F);
		this.motionY = baseSpeed;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
	}

	@Override
	protected double motionMult() {
		return velocity;
	}

	@Override
	public boolean doesImpactEntities() {
		return false;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!worldObj.isRemote) {

			if(velocity < 6) velocity += 0.1;

			if(activationTimer < 40) {
				activationTimer++;
				motionY = baseSpeed;
			} else {
				Entity prevTracking = this.tracking;

				if(this.tracking == null || this.tracking.isDead) this.targetMissile();

				if(prevTracking == null && this.tracking != null) {
					ExplosionLarge.spawnShock(worldObj, posX, posY, posZ, 24, 3F);
				}
        
				if(this.tracking != null && !this.tracking.isDead) {
					this.aimAtTarget();
				} else {
					if(this.ticksExisted > 600) this.setDead();
				}
			}

			loadNeighboringChunks((int) Math.floor(posX / 16), (int) Math.floor(posZ / 16));

			if(this.posY > 2000 && (this.tracking == null || this.tracking.isDead)) this.setDead();

		} else {

			Vec3 vec = Vec3.createVectorHelper(motionX, motionY, motionZ).normalize();
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "ABMContrail");
			data.setDouble("posX", posX - vec.xCoord);
			data.setDouble("posY", posY - vec.yCoord);
			data.setDouble("posZ", posZ - vec.zCoord);
			MainRegistry.proxy.effectNT(data);
		}

		float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
		for(this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) this.prevRotationPitch += 360.0F;
		while(this.rotationYaw - this.prevRotationYaw < -180.0F) this.prevRotationYaw -= 360.0F;
		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) this.prevRotationYaw += 360.0F;
	}

	/** Detects and caches nearby EntityMissileBaseNT */
	protected void targetMissile() {

		Entity closest = null;
		double dist = 1_000;

		for(Entity e : TileEntityMachineRadarNT.matchingEntities) {
			if(e.dimension != this.dimension) continue;
			if(!(e instanceof EntityMissileBaseNT)) continue; //can only lock onto missiles
			if(e instanceof EntityMissileStealth) continue; //cannot lack onto missiles with stealth coating

			Vec3 vec = Vec3.createVectorHelper(e.posX - posX, e.posY - posY, e.posZ - posZ);

			if(vec.lengthVector() < dist) {
				closest = e;
			}
		}

		this.tracking = closest;
	}

	/** Predictive targeting system */
	protected void aimAtTarget() {

		Vec3 delta = Vec3.createVectorHelper(tracking.posX - posX, tracking.posY - posY, tracking.posZ - posZ);
		double intercept = delta.lengthVector() / (this.baseSpeed * this.velocity);
		Vec3 predicted = Vec3.createVectorHelper(tracking.posX + (tracking.posX - tracking.lastTickPosX) * intercept, tracking.posY + (tracking.posY - tracking.lastTickPosY) * intercept, tracking.posZ + (tracking.posZ - tracking.lastTickPosZ) * intercept);
		Vec3 motion = Vec3.createVectorHelper(predicted.xCoord - posX, predicted.yCoord - posY, predicted.zCoord - posZ).normalize();

		if(delta.lengthVector() < 10 && activationTimer >= 40) {
			this.setDead();
			ExplosionLarge.explode(worldObj, posX, posY, posZ, 15F, true, false, false);

		}

		this.motionX = motion.xCoord * baseSpeed;
		this.motionY = motion.yCoord * baseSpeed;
		this.motionZ = motion.zCoord * baseSpeed;
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		if(this.activationTimer >= 40) {
			this.setDead();
			ExplosionLarge.explode(worldObj, posX, posY, posZ, 20F, true, false, false);
		}
	}

	@Override
	public double getGravityVelocity() {
		return 0.0D;
	}

	@Override
	protected float getAirDrag() {
		return 1F;
	}

	@Override
	protected float getWaterDrag() {
		return 1F;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.velocity = nbt.getDouble("veloc");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setDouble("veloc", this.velocity);
	}

	@Override
	public void init(Ticket ticket) {
		if(!worldObj.isRemote) {

			if(ticket != null) {

				if(loaderTicket == null) {

					loaderTicket = ticket;
					loaderTicket.bindEntity(this);
					loaderTicket.getModData();
				}

				ForgeChunkManager.forceChunk(loaderTicket, new ChunkCoordIntPair(chunkCoordX, chunkCoordZ));
			}
		}
	}

	List<ChunkCoordIntPair> loadedChunks = new ArrayList<ChunkCoordIntPair>();

	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!worldObj.isRemote && loaderTicket != null) {

			clearChunkLoader();

			loadedChunks.clear();
			for(int i = -1; i <= 1; i++) for(int j = -1; j <= 1; j++) loadedChunks.add(new ChunkCoordIntPair(newChunkX + i, newChunkZ + j));

			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.forceChunk(loaderTicket, chunk);
			}
		}
	}

	@Override
	public void setDead() {
		super.setDead();
		this.clearChunkLoader();
	}

	public void clearChunkLoader() {
		if(!worldObj.isRemote && loaderTicket != null) {
			for(ChunkCoordIntPair chunk : loadedChunks) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_AB;
	}

	@Override
	public String getUnlocalizedName() {
		return "radar.target.abm";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER_AB;
	}

	@Override
	public boolean canBeSeenBy(Object radar) {
		return true;
	}

	@Override
	public boolean paramsApplicable(RadarScanParams params) {
		return params.scanMissiles;
	}

	@Override
	public boolean suppliesRedstone(RadarScanParams params) {
		return false;
	}
}
