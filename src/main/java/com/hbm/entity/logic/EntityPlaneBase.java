package com.hbm.entity.logic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.particle.helper.ExplosionSmallCreator;
import com.hbm.util.ParticleUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

public abstract class EntityPlaneBase extends Entity implements IChunkLoader {

	private Ticket loaderTicket;
	private List<ChunkCoordIntPair> loadedChunks = new ArrayList<ChunkCoordIntPair>();
	
	public float health = getMaxHealth();
	public int timer = getLifetime();

	public EntityPlaneBase(World world) { super(world); }

	public float getMaxHealth() { return 50F; }
	public int getLifetime() { return 200; }
	
	@Override public boolean canBeCollidedWith() { return this.health > 0; }

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(source == ModDamageSource.nuclearBlast) return false;
		if(this.isEntityInvulnerable()) return false;
		if(!this.isDead && !this.worldObj.isRemote && this.health > 0) {
			health -= amount;
			if(this.health <= 0) this.killPlane();
		}
		return true;
	}

	protected void killPlane() {
		ExplosionSmallCreator.composeEffect(worldObj, posX, posY, posZ, 25, 3.5F, 2F);
		worldObj.playSoundEffect(posX, posY, posZ, "hbm:entity.planeShotDown", 25.0F, 1.0F);
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, worldObj, Type.ENTITY));
		this.dataWatcher.addObject(17, new Float(50F));
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
	
	@Override
	public void onUpdate() {

		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		this.setPosition(posX + motionX, posY + motionY, posZ + motionZ);
		
		if(!worldObj.isRemote) {
			this.dataWatcher.updateObject(17, health);
		} else {
			health = this.dataWatcher.getWatchableObjectFloat(17);
		}
		
		this.rotation();
		
		if(this.health <= 0) {
			motionY -= 0.025;
			
			for(int i = 0; i < 10; i++) ParticleUtil.spawnGasFlame(this.worldObj, this.posX + rand.nextGaussian() * 0.5 - motionX * 2, this.posY + rand.nextGaussian() * 0.5 - motionY * 2, this.posZ + rand.nextGaussian() * 0.5 - motionZ * 2, 0.0, 0.1, 0.0);
			
			if((!worldObj.getBlock((int) posX, (int) posY, (int) posZ).isAir(worldObj, (int) posX, (int) posY, (int) posZ) || posY < 0) && !worldObj.isRemote) {
				this.setDead();
				new ExplosionVNT(worldObj, posX, posY, posZ, 15F).makeStandard().explode();
				worldObj.playSoundEffect(posX, posY, posZ, "hbm:entity.planeCrash", 25.0F, 1.0F);
				return;
			}
		} else {
			this.motionY = 0F;
		}
		
		if(this.ticksExisted > timer) this.setDead();
		if(!worldObj.isRemote) loadNeighboringChunks((int)Math.floor(posX / 16D), (int)Math.floor(posZ / 16D));
	}
	
	protected void rotation() {
		float motionHorizontal = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
		for(this.rotationPitch = (float) (Math.atan2(this.motionY, motionHorizontal) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
		while(this.rotationPitch - this.prevRotationPitch >= 180.0F) this.prevRotationPitch += 360.0F;
		while(this.rotationYaw - this.prevRotationYaw < -180.0F) this.prevRotationYaw -= 360.0F;
		while(this.rotationYaw - this.prevRotationYaw >= 180.0F) this.prevRotationYaw += 360.0F;
	}

	@Override
	public void setDead() {
		super.setDead();
		this.clearChunkLoader();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		ticksExisted = nbt.getInteger("ticksExisted");
		this.getDataWatcher().updateObject(17, nbt.getFloat("health"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("ticksExisted", ticksExisted);
		nbt.setFloat("health", this.getDataWatcher().getWatchableObjectFloat(17));
	}
	
	public void clearChunkLoader() {
		if(!worldObj.isRemote && loaderTicket != null) {
			for(ChunkCoordIntPair chunk : loadedChunks) ForgeChunkManager.unforceChunk(loaderTicket, chunk);
		}
	}

	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!worldObj.isRemote && loaderTicket != null) {
			clearChunkLoader();
			loadedChunks.clear();
			loadedChunks.add(new ChunkCoordIntPair(newChunkX, newChunkZ));
			for(ChunkCoordIntPair chunk : loadedChunks) ForgeChunkManager.forceChunk(loaderTicket, chunk);
		}
	}
	
	@Override @SideOnly(Side.CLIENT) public boolean isInRangeToRenderDist(double distance) { return true; }
}
