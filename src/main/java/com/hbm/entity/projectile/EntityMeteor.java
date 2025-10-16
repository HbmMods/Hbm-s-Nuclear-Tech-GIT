package com.hbm.entity.projectile;

import com.hbm.config.WorldConfig;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;
import com.hbm.world.feature.Meteorite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import com.hbm.sound.AudioWrapper;

public class EntityMeteor extends Entity {

	public boolean safe = false;
	private AudioWrapper audioFly;

	public EntityMeteor(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.setSize(4F, 4F);

		if(worldObj.isRemote) this.audioFly = MainRegistry.proxy.getLoopedSound("hbm:entity.meteoriteFallingLoop", 0, 0, 0, 1F, 100F, 0.9F + this.rand.nextFloat() * 0.2F, 0);
	}

	public boolean destroyWeakBlocks(World world, int x, int y, int z, int radius) {
		int rSq = radius * radius;
		boolean foundSolidBlock = false;

		for (int dx = -radius; dx <= radius; dx++) {
			for (int dy = -radius; dy <= radius; dy++) {
				for (int dz = -radius; dz <= radius; dz++) {
					// Check if point (dx, dy, dz) lies inside the sphere
					if (dx * dx + dy * dy + dz * dz <= rSq) {
						int blockX = x + dx;
						int blockY = y + dy;
						int blockZ = z + dz;

						Block block = world.getBlock(blockX, blockY, blockZ);
						if (block == null) continue;

						float hardness = block.getBlockHardness(world, blockX, blockY, blockZ);

						if (block == Blocks.leaves || block == Blocks.log || hardness <= 0.3F || block == Blocks.water) {
							if(!safe) world.setBlockToAir(blockX, blockY, blockZ);
						} else {
							foundSolidBlock = true;
						}
					}
				}
			}
		}

		return foundSolidBlock;
	}

	@Override
	public void onUpdate() {
		if(!worldObj.isRemote && !WorldConfig.enableMeteorStrikes) {
			this.setDead();
			return;
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.motionY -= 0.03;
		if(motionY < -2.5)
			motionY = -2.5;

		this.moveEntity(motionX, motionY, motionZ);

		if(!this.worldObj.isRemote && this.posY < 260) {
			if(destroyWeakBlocks(worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 6) && this.onGround) {

				//worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 5 + rand.nextFloat(), !safe);
				if(WorldConfig.enableMeteorTails) {
					ExplosionLarge.spawnParticles(worldObj, posX, posY + 5, posZ, 75);
					ExplosionLarge.spawnParticles(worldObj, posX + 5, posY, posZ, 75);
					ExplosionLarge.spawnParticles(worldObj, posX - 5, posY, posZ, 75);
					ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ + 5, 75);
					ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ - 5, 75);
				}

				(new Meteorite()).generate(worldObj, rand, (int) Math.round(this.posX - 0.5D), (int) Math.round(this.posY - 0.5D), (int) Math.round(this.posZ - 0.5D), safe, true, true);

				// Sound
				this.audioFly.stopSound();
				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:entity.oldExplosion", 10000.0F, 0.5F + this.rand.nextFloat() * 0.1F);

				this.setDead();
			}
		}

		// Sound
		if(worldObj.isRemote){
			if(this.audioFly.isPlaying()) {
				// Update sound
				this.audioFly.keepAlive();
				this.audioFly.updateVolume(1F);
				this.audioFly.updatePosition((int)this.posX, (int)this.posY, (int)this.posZ);
			}
			else
			{
				// Start playing the sound
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				double distance = player.getDistanceSq(this.posX, this.posY, this.posZ);
				if (distance < 110 * 110) {
					this.audioFly.startSound();
				}
			}
		}

		if(WorldConfig.enableMeteorTails && worldObj.isRemote) {

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "exhaust");
			data.setString("mode", "meteor");
			data.setInteger("count", 10);
			data.setDouble("width", 1);
			data.setDouble("posX", posX - motionX);
			data.setDouble("posY", posY - motionY);
			data.setDouble("posZ", posZ - motionZ);

			MainRegistry.proxy.effectNT(data);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.safe = nbt.getBoolean("safe");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("safe", safe);
	}
}
