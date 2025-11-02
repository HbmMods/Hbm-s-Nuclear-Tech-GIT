package com.hbm.entity.projectile;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.WorldConfig;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;
import com.hbm.world.feature.Meteorite;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import com.hbm.sound.AudioWrapper;
import com.hbm.util.fauxpointtwelve.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class EntityMeteor extends Entity {

	public boolean safe = false;
	private AudioWrapper audioFly;

	public EntityMeteor(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.setSize(4F, 4F);
		if(worldObj.isRemote)
			this.audioFly = MainRegistry.proxy.getLoopedSound("hbm:entity.meteoriteFallingLoop", 0, 0, 0, 1F, 200F, 0.9F + this.rand.nextFloat() * 0.2F, 0);
	}

	public List<BlockPos> getBlocksInRadius(World world, int x, int y, int z, int radius) {
		List<BlockPos> foundBlocks = new ArrayList();

		int rSq = radius * radius;
		for(int dx = -radius; dx <= radius; dx++) {
			for(int dy = -radius; dy <= radius; dy++) {
				for(int dz = -radius; dz <= radius; dz++) {
					// Check if point (dx, dy, dz) lies inside the sphere
					if(dx * dx + dy * dy + dz * dz <= rSq) {
						foundBlocks.add(new BlockPos(x + dx, y + dy, z + dz));
					}
				}
			}
		}
		return foundBlocks;
	}

	public void damageOrDestroyBlock(World world, int blockX, int blockY, int blockZ) {
		if(safe) return;

		// Get current block info
		Block block = world.getBlock(blockX, blockY, blockZ);
		if(block == null) return;
		float hardness = block.getBlockHardness(world, blockX, blockY, blockZ);

		// Check if the block is weak and can be destroyed
		if(block == Blocks.leaves || block == Blocks.log || (hardness >= 0 && hardness <= 0.3F)) {
			// Destroy the block
			world.setBlockToAir(blockX, blockY, blockZ);
		} else {
			// Found solid block
			if(hardness < 0 || hardness > 5F) return;
			
			if(rand.nextInt(6) == 1) {
				// Turn blocks into damaged variants
				if(block == Blocks.dirt) {
					world.setBlock(blockX, blockY, blockZ, ModBlocks.dirt_dead);
				} else if(block == Blocks.sand) {
					if(rand.nextInt(2) == 1) {
						world.setBlock(blockX, blockY, blockZ, Blocks.sandstone);
					} else {
						world.setBlock(blockX, blockY, blockZ, Blocks.glass);
					}
				} else if(block == Blocks.stone) {
					world.setBlock(blockX, blockY, blockZ, Blocks.cobblestone);
				} else if(block == Blocks.grass) {
					world.setBlock(blockX, blockY, blockZ, ModBlocks.waste_earth);
				}
			}
		}
	}

	public void clearMeteorPath(World world, int x, int y, int z) {
		for(BlockPos blockPos : getBlocksInRadius(world, x, y, z, 5)) {
			damageOrDestroyBlock(worldObj, blockPos.getX(), blockPos.getY(), blockPos.getZ());
		}
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
		if(motionY < -2.5) motionY = -2.5;

		this.moveEntity(motionX, motionY, motionZ);

		if(!this.worldObj.isRemote && this.posY < 260) {
			clearMeteorPath(worldObj, (int) this.posX, (int) this.posY, (int) this.posZ);
			if(this.onGround) {
				worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 5 + rand.nextFloat(), !safe);

				if(WorldConfig.enableMeteorTails) {
					ExplosionLarge.spawnRubble(worldObj, this.posX, this.posY, this.posZ, 15);

					ExplosionLarge.spawnParticles(worldObj, posX, posY + 5, posZ, 75);
					ExplosionLarge.spawnParticles(worldObj, posX + 5, posY, posZ, 75);
					ExplosionLarge.spawnParticles(worldObj, posX - 5, posY, posZ, 75);
					ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ + 5, 75);
					ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ - 5, 75);
				}

				// Bury the meteor into the ground
				int spawnPosX = (int) (Math.round(this.posX - 0.5D) + (safe ? 0 : (this.motionZ * 4)));
				int spawnPosY = (int) Math.round(this.posY - (safe ? 0 : 4));
				int spawnPosZ = (int) (Math.round(this.posZ - 0.5D) + (safe ? 0 : (this.motionZ * 4)));

				(new Meteorite()).generate(worldObj, rand, spawnPosX, spawnPosY, spawnPosZ, safe, true, true);
				clearMeteorPath(worldObj, spawnPosX, spawnPosY, spawnPosZ);

				this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:entity.oldExplosion", 10000.0F, 0.5F + this.rand.nextFloat() * 0.1F);

				this.setDead();
			}
		}

		// Sound
		if(worldObj.isRemote) {
			
			if(this.isDead) {
				if(this.audioFly != null) this.audioFly.stopSound();
				
			} else {
	
				if(this.audioFly.isPlaying()) {
					// Update sound
					this.audioFly.keepAlive();
					this.audioFly.updateVolume(1F);
					this.audioFly.updatePosition((int) this.posX, (int) this.posY, (int) this.posZ);
				} else {
					// Start playing the sound
					EntityPlayer player = MainRegistry.proxy.me();
					double distance = player.getDistanceSq(this.posX, this.posY, this.posZ);
					if(distance < 210 * 210) {
						this.audioFly.startSound();
					}
				}
			}

			if(WorldConfig.enableMeteorTails) {

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
	}

	@Override @SideOnly(Side.CLIENT) public boolean isInRangeToRenderDist(double distance) { return true; }
	@Override @SideOnly(Side.CLIENT) public int getBrightnessForRender(float f) { return 15728880; }
	@Override public float getBrightness(float f) { return 1.0F; }
	
	@Override protected void entityInit() { }
	@Override protected void readEntityFromNBT(NBTTagCompound nbt) { this.safe = nbt.getBoolean("safe"); }
	@Override protected void writeEntityToNBT(NBTTagCompound nbt) { nbt.setBoolean("safe", safe); }
}
