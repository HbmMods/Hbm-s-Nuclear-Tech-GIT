package com.hbm.entity.item;

import api.hbm.block.IFuckingExplode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTNTPrimedBase extends Entity {

	public boolean detonateOnCollision;
	public int fuse;
	private EntityLivingBase tntPlacedBy;

	public EntityTNTPrimedBase(World world) {
		super(world);
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
		this.fuse = 80;
		this.detonateOnCollision = false;
	}

	public EntityTNTPrimedBase(World world, double x, double y, double z, EntityLivingBase entity, Block bomb) {
		this(world);
		this.setPosition(x, y, z);
		float f = (float) (Math.random() * Math.PI * 2.0D);
		this.motionX = (double) (-((float) Math.sin((double) f)) * 0.02F);
		this.motionY = 0.2D;
		this.motionZ = (double) (-((float) Math.cos((double) f)) * 0.02F);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.tntPlacedBy = entity;
		this.dataWatcher.updateObject(12, Block.getIdFromBlock(bomb));
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(12, 0);
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public void onUpdate() {
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.04D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.98D;
		this.motionY *= 0.98D;
		this.motionZ *= 0.98D;

		if(this.onGround) {
			this.motionX *= 0.7D;
			this.motionZ *= 0.7D;
			this.motionY *= -0.5D;
		}
		
		if(this.fuse-- <= 0 || (this.detonateOnCollision && this.isCollided)) {
			this.setDead();

			if(!this.worldObj.isRemote) {
				this.explode();
			}
		} else {
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	private void explode() {
		this.getBomb().explodeEntity(worldObj, posX, posY, posZ, this);
	}
	
	public IFuckingExplode getBomb() {
		return (IFuckingExplode) getBlock();
	}

	public Block getBlock() {
		return Block.getBlockById(this.dataWatcher.getWatchableObjectInt(12));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setByte("Fuse", (byte) this.fuse);
		nbt.setInteger("Tile", this.dataWatcher.getWatchableObjectInt(12));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.fuse = nbt.getByte("Fuse");
		this.dataWatcher.updateObject(12, nbt.getInteger("Tile"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize() {
		return 0.0F;
	}
	
	public EntityLivingBase getTntPlacedBy() {
		return this.tntPlacedBy;
	}
}
