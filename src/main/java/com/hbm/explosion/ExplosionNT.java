package com.hbm.explosion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

@Deprecated
public class ExplosionNT extends Explosion {
	
	public Set<ExAttrib> atttributes = new HashSet();

	private Random explosionRNG = new Random();
	private World worldObj;
	protected int resolution = 16;
	protected Map affectedEntities = new HashMap();

	@Deprecated public static final List<ExAttrib> nukeAttribs = Arrays.asList(new ExAttrib[] { ExAttrib.FIRE, ExAttrib.NOPARTICLE, ExAttrib.NOSOUND, ExAttrib.NODROP, ExAttrib.NOHURT });

	public ExplosionNT(World world, Entity exploder, double x, double y, double z, float strength) {
		super(world, exploder, x, y, z, strength);
		this.worldObj = world;
	}

	public ExplosionNT addAttrib(ExAttrib attrib) {
		atttributes.add(attrib);
		return this;
	}

	public ExplosionNT addAllAttrib(List<ExAttrib> attrib) {
		atttributes.addAll(attrib);
		return this;
	}

	public ExplosionNT addAllAttrib(ExAttrib... attrib) {
		for(ExAttrib a : attrib) atttributes.add(a);
		return this;
	}

	public ExplosionNT overrideResolution(int res) {
		resolution = res;
		return this;
	}

	public void explode() {
		doExplosionA();
		doExplosionB(false);
	}

	public void doExplosionA() {
		float f = this.explosionSize;
		HashSet hashset = new HashSet();
		int i;
		int j;
		int k;
		double currentX;
		double currentY;
		double currentZ;

		for(i = 0; i < this.resolution; ++i) {
			for(j = 0; j < this.resolution; ++j) {
				for(k = 0; k < this.resolution; ++k) {
					
					if(i == 0 || i == this.resolution - 1 || j == 0 || j == this.resolution - 1 || k == 0 || k == this.resolution - 1) {
						
						double d0 = (double) ((float) i / ((float) this.resolution - 1.0F) * 2.0F - 1.0F);
						double d1 = (double) ((float) j / ((float) this.resolution - 1.0F) * 2.0F - 1.0F);
						double d2 = (double) ((float) k / ((float) this.resolution - 1.0F) * 2.0F - 1.0F);
						
						double dist = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 /= dist;
						d1 /= dist;
						d2 /= dist;
						
						float remainingPower = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
						currentX = this.explosionX;
						currentY = this.explosionY;
						currentZ = this.explosionZ;

						for(float step = 0.3F; remainingPower > 0.0F; remainingPower -= step * 0.75F) {
							int xPos = MathHelper.floor_double(currentX);
							int yPos = MathHelper.floor_double(currentY);
							int zPos = MathHelper.floor_double(currentZ);
							Block block = this.worldObj.getBlock(xPos, yPos, zPos);

							if(block.getMaterial() != Material.air) {
								float resistance = this.exploder != null ? this.exploder.func_145772_a(this, this.worldObj, xPos, yPos, zPos, block) : block.getExplosionResistance(this.exploder, worldObj, xPos, yPos, zPos, explosionX, explosionY, explosionZ);
								remainingPower -= (resistance + 0.3F) * step;
							}

							if(block != Blocks.air && remainingPower > 0.0F && (this.exploder == null || this.exploder.func_145774_a(this, this.worldObj, xPos, yPos, zPos, block, remainingPower))) {
								hashset.add(new ChunkPosition(xPos, yPos, zPos));
								
							} else if(this.has(ExAttrib.ERRODE) && errosion.containsKey(block)) {
								hashset.add(new ChunkPosition(xPos, yPos, zPos));
							}

							currentX += d0 * (double) step;
							currentY += d1 * (double) step;
							currentZ += d2 * (double) step;
						}
					}
				}
			}
		}

		this.affectedBlockPositions.addAll(hashset);

		if(!has(ExAttrib.NOHURT)) {

			this.explosionSize *= 2.0F;
			i = MathHelper.floor_double(this.explosionX - (double) this.explosionSize - 1.0D);
			j = MathHelper.floor_double(this.explosionX + (double) this.explosionSize + 1.0D);
			k = MathHelper.floor_double(this.explosionY - (double) this.explosionSize - 1.0D);
			int i2 = MathHelper.floor_double(this.explosionY + (double) this.explosionSize + 1.0D);
			int l = MathHelper.floor_double(this.explosionZ - (double) this.explosionSize - 1.0D);
			int j2 = MathHelper.floor_double(this.explosionZ + (double) this.explosionSize + 1.0D);
			List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getBoundingBox((double) i, (double) k, (double) l, (double) j, (double) i2, (double) j2));
			net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.worldObj, this, list, this.explosionSize);
			Vec3 vec3 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);

			for(int i1 = 0; i1 < list.size(); ++i1) {
				Entity entity = (Entity) list.get(i1);
				double d4 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double) this.explosionSize;

				if(d4 <= 1.0D) {
					currentX = entity.posX - this.explosionX;
					currentY = entity.posY + (double) entity.getEyeHeight() - this.explosionY;
					currentZ = entity.posZ - this.explosionZ;
					double d9 = (double) MathHelper.sqrt_double(currentX * currentX + currentY * currentY + currentZ * currentZ);

					if(d9 != 0.0D) {
						currentX /= d9;
						currentY /= d9;
						currentZ /= d9;
						double d10 = (double) this.worldObj.getBlockDensity(vec3, entity.boundingBox);
						double d11 = (1.0D - d4) * d10;
						entity.attackEntityFrom(setExplosionSource(this), (float) ((int) ((d11 * d11 + d11) / 2.0D * 8.0D * (double) this.explosionSize + 1.0D)));
						double d8 = EnchantmentProtection.func_92092_a(entity, d11);
						entity.motionX += currentX * d8;
						entity.motionY += currentY * d8;
						entity.motionZ += currentZ * d8;

						if(entity instanceof EntityPlayer) {
							this.affectedEntities.put((EntityPlayer) entity, Vec3.createVectorHelper(currentX * d11, currentY * d11, currentZ * d11));
						}
					}
				}
			}

			this.explosionSize = f;
		}
	}

	public static DamageSource setExplosionSource(Explosion explosion) {
		return explosion != null && explosion.getExplosivePlacedBy() != null ?
				(new EntityDamageSource("explosion.player", explosion.getExplosivePlacedBy())).setExplosion() :
					(new DamageSource("explosion")).setExplosion();
	}

	public void doExplosionB(boolean p_77279_1_) {

		if(!has(ExAttrib.NOSOUND))
			this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

		if(!has(ExAttrib.NOPARTICLE)) {
			if(this.explosionSize >= 2.0F && this.isSmoking) {
				this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
			} else {
				this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
			}
		}

		Iterator iterator;
		ChunkPosition chunkposition;
		int i;
		int j;
		int k;
		Block block;

		if(this.isSmoking) {
			iterator = this.affectedBlockPositions.iterator();

			while(iterator.hasNext()) {
				chunkposition = (ChunkPosition) iterator.next();
				i = chunkposition.chunkPosX;
				j = chunkposition.chunkPosY;
				k = chunkposition.chunkPosZ;
				block = this.worldObj.getBlock(i, j, k);

				if(!has(ExAttrib.NOPARTICLE)) {
					double d0 = (double) ((float) i + this.worldObj.rand.nextFloat());
					double d1 = (double) ((float) j + this.worldObj.rand.nextFloat());
					double d2 = (double) ((float) k + this.worldObj.rand.nextFloat());
					double d3 = d0 - this.explosionX;
					double d4 = d1 - this.explosionY;
					double d5 = d2 - this.explosionZ;
					double d6 = (double) MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
					d3 /= d6;
					d4 /= d6;
					d5 /= d6;
					double d7 = 0.5D / (d6 / (double) this.explosionSize + 0.1D);
					d7 *= (double) (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F);
					d3 *= d7;
					d4 *= d7;
					d5 *= d7;
					this.worldObj.spawnParticle("explode", (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5);
					this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
				}

				if(block.getMaterial() != Material.air) {
					
					boolean doesErrode = false;
					Block errodesInto = Blocks.air;
					
					if(this.has(ExAttrib.ERRODE) && this.explosionRNG.nextFloat() < 0.6F) { //errosion has a 60% chance to occour
						
						if(errosion.containsKey(block)) {
							doesErrode = true;
							errodesInto = errosion.get(block);
						}
					}
					
					if(block.canDropFromExplosion(this) && !has(ExAttrib.NODROP) && !doesErrode) {
						float chance = 1.0F;

						if(!has(ExAttrib.ALLDROP))
							chance = 1.0F / this.explosionSize;

						block.dropBlockAsItemWithChance(this.worldObj, i, j, k, this.worldObj.getBlockMetadata(i, j, k), chance, 0);
					}

					block.onBlockExploded(this.worldObj, i, j, k, this);
					
					if(block.isNormalCube()) {
						
						if(doesErrode) {
							this.worldObj.setBlock(i, j, k, errodesInto);
						}
						
						if(has(ExAttrib.DIGAMMA)) {
							this.worldObj.setBlock(i, j, k, ModBlocks.ash_digamma);
							
							if(this.explosionRNG.nextInt(5) == 0 && this.worldObj.getBlock(i, j + 1, k) == Blocks.air)
								this.worldObj.setBlock(i, j + 1, k, ModBlocks.fire_digamma);
							
						} else if(has(ExAttrib.DIGAMMA_CIRCUIT)) {
							
							if(i % 3 == 0 && k % 3 == 0) {
								this.worldObj.setBlock(i, j, k, ModBlocks.pribris_digamma);
							} else if((i % 3 == 0 || k % 3 == 0) && this.explosionRNG.nextBoolean()) {
								this.worldObj.setBlock(i, j, k, ModBlocks.pribris_digamma);
							} else {
								this.worldObj.setBlock(i, j, k, ModBlocks.ash_digamma);
								
								if(this.explosionRNG.nextInt(5) == 0 && this.worldObj.getBlock(i, j + 1, k) == Blocks.air)
									this.worldObj.setBlock(i, j + 1, k, ModBlocks.fire_digamma);
							}
						} else if(has(ExAttrib.LAVA_V)) {
							this.worldObj.setBlock(i, j, k, ModBlocks.volcanic_lava_block);
						} else if(has(ExAttrib.LAVA_R)) {
							this.worldObj.setBlock(i, j, k, ModBlocks.rad_lava_block);
						}
					}
				}
			}
		}

		if(has(ExAttrib.FIRE) || has(ExAttrib.BALEFIRE) || has(ExAttrib.LAVA)) {
			iterator = this.affectedBlockPositions.iterator();

			while(iterator.hasNext()) {
				chunkposition = (ChunkPosition) iterator.next();
				i = chunkposition.chunkPosX;
				j = chunkposition.chunkPosY;
				k = chunkposition.chunkPosZ;
				block = this.worldObj.getBlock(i, j, k);
				Block block1 = this.worldObj.getBlock(i, j - 1, k);

				boolean shouldReplace = true;

				if(!has(ExAttrib.ALLMOD) && !has(ExAttrib.DIGAMMA))
					shouldReplace = this.explosionRNG.nextInt(3) == 0;

				if(block.getMaterial() == Material.air && block1.func_149730_j() && shouldReplace) {
					if(has(ExAttrib.FIRE))
						this.worldObj.setBlock(i, j, k, Blocks.fire);
					else if(has(ExAttrib.BALEFIRE))
						this.worldObj.setBlock(i, j, k, ModBlocks.balefire);
					else if(has(ExAttrib.LAVA))
						this.worldObj.setBlock(i, j, k, Blocks.flowing_lava);
				}
			}
		}
	}

	public Map func_77277_b() {
		return this.affectedEntities;
	}

	public EntityLivingBase getExplosivePlacedBy() {
		return this.exploder == null ? null : (this.exploder instanceof EntityTNTPrimed ? ((EntityTNTPrimed) this.exploder).getTntPlacedBy() : (this.exploder instanceof EntityLivingBase ? (EntityLivingBase) this.exploder : null));
	}

	// unconventional name, sure, but it's short
	public boolean has(ExAttrib attrib) {
		return this.atttributes.contains(attrib);
	}
	
	//this solution is a bit hacky but in the end easier to work with
	public static enum ExAttrib {
		FIRE,		//classic vanilla fire explosion
		BALEFIRE,	//same with but with balefire
		DIGAMMA,
		DIGAMMA_CIRCUIT,
		LAVA,		//again the same thing but lava
		LAVA_V,		//again the same thing but volcanic lava
		LAVA_R,		//again the same thing but radioactive lava
		ERRODE,		//will turn select blocks into gravel or sand
		ALLMOD,		//block placer attributes like fire are applied for all destroyed blocks
		ALLDROP,	//miner TNT!
		NODROP,		//the opposite
		NOPARTICLE,
		NOSOUND,
		NOHURT
	}
	
	public static final HashMap<Block, Block> errosion = new HashMap();
	
	static {
		errosion.put(ModBlocks.concrete, Blocks.gravel);
		errosion.put(ModBlocks.concrete_smooth, Blocks.gravel);
		errosion.put(ModBlocks.brick_concrete, ModBlocks.brick_concrete_broken);
		errosion.put(ModBlocks.brick_concrete_broken, Blocks.gravel);
	}

}
