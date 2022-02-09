package com.hbm.entity.item;

import com.hbm.blocks.bomb.BlockTNTBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityTNTPrimedBase extends Entity {

	public int fuse;
	private EntityLivingBase tntPlacedBy;
	public BlockTNTBase bomb;

	public EntityTNTPrimedBase(World world) {
		super(world);
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
		this.yOffset = this.height / 2.0F;
	}

	public EntityTNTPrimedBase(World world, double x, double y, double z, EntityLivingBase entity, BlockTNTBase bomb) {
		this(world);
		this.setPosition(x, y, z);
		float f = (float) (Math.random() * Math.PI * 2.0D);
		this.motionX = (double) (-((float) Math.sin((double) f)) * 0.02F);
		this.motionY = 0.2D;
		this.motionZ = (double) (-((float) Math.cos((double) f)) * 0.02F);
		this.fuse = 80;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.tntPlacedBy = entity;
		this.bomb = bomb;
	}

	@Override
	protected void entityInit() { }

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
		
		if(bomb == null) {
			this.setDead();
			return;
		}
		
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

		if(this.fuse-- <= 0) {
			this.setDead();

			if(!this.worldObj.isRemote) {
				this.explode();
			}
		} else {
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	private void explode() {
		this.bomb.explodeEntity(worldObj, posX, posZ, posZ, this);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setByte("Fuse", (byte) this.fuse);
		nbt.setInteger("Tile", Block.getIdFromBlock(bomb));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.fuse = nbt.getByte("Fuse");
		this.bomb = (BlockTNTBase) Block.getBlockById(nbt.getInteger("Tile"));
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
