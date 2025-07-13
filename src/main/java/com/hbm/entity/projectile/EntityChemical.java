package com.hbm.entity.projectile;

import java.awt.Color;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.glyphid.EntityGlyphid;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.*;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.particle.helper.FlameCreator;
import com.hbm.tileentity.IRepairable;
import com.hbm.tileentity.IRepairable.EnumExtinguishType;
import com.hbm.util.ArmorUtil;
import com.hbm.util.CompatExternal;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.EnchantmentUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.hbm.util.EntityDamageUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;


public class EntityChemical extends EntityThrowableNT {
	
	/*
	 * TYPE INFO:
	 * 
	 * if ANTIMATTER: ignore all other traits, become a gamma beam with no gravity
	 * if HOT: set fire and deal extra fire damage, scaling with the temperature
	 * if COLD: freeze, duration scaling with temperature, assuming COMBUSTIBLE does not apply
	 * if GAS: short range with the spread going up
	 * if EVAP: same as gas
	 * if LIQUID: if EVAP doesn't apply, create a narrow spray with long range affected by gravity
	 * if COMBUSTIBLE: auto-ignite
	 * if FLAMMABLE: if GAS or EVAP apply, do the same as COMBUSTIBLE, otherwise create a neutral spray that adds the "soaked" effect
	 * if CORROSIVE: apply extra acid damage, poison effect as well as armor degradation
	 */

	public EntityChemical(World world) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}

	public EntityChemical(World world, EntityLivingBase thrower, double sideOffset, double heightOffset, double frontOffset) {
		super(world, thrower);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(10, new Integer(0));
	}
	
	public EntityChemical setFluid(FluidType fluid) {
		this.dataWatcher.updateObject(10, fluid.getID());
		return this;
	}
	
	public FluidType getType() {
		return Fluids.fromID(this.dataWatcher.getWatchableObjectInt(10));
	}
	
	@Override
	public void onUpdate() {
		
		if(!worldObj.isRemote) {
			
			if(this.ticksExisted > this.getMaxAge()) {
				this.setDead();
			}
			
			FluidType type = this.getType();
			
			if(type.hasTrait(Fluids.GASEOUS.getClass()) || type.hasTrait(Fluids.EVAP.getClass())) {

				double intensity = 1D - (double) this.ticksExisted / (double) this.getMaxAge();
				List<Entity> affected = worldObj.getEntitiesWithinAABBExcludingEntity(this.thrower, this.boundingBox.expand(intensity * 2.5, intensity * 2.5, intensity * 2.5));
				
				for(Entity e : affected) {
					this.affect(e, intensity);
				}
			}
			
		} else {

			FluidType type = getType();
			ChemicalStyle style = getStyle();
			
			if(type == Fluids.BALEFIRE) {
				
				if(MainRegistry.proxy.me().getDistanceToEntity(this) < 100)
					FlameCreator.composeEffectClient(worldObj, posX, posY - 0.125, posZ, FlameCreator.META_BALEFIRE);
				
			} else if(style == ChemicalStyle.LIQUID) {
				
				Color color = new Color(type.getColor());
				
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "colordust");
				data.setDouble("posX", posX);
				data.setDouble("posY", posY);
				data.setDouble("posZ", posZ);
				data.setDouble("mX", motionX + worldObj.rand.nextGaussian() * 0.05);
				data.setDouble("mY", motionY - 0.2 + worldObj.rand.nextGaussian() * 0.05);
				data.setDouble("mZ", motionZ + worldObj.rand.nextGaussian() * 0.05);
				data.setFloat("r", color.getRed() / 255F);
				data.setFloat("g", color.getGreen() / 255F);
				data.setFloat("b", color.getBlue() / 255F);
				MainRegistry.proxy.effectNT(data);
				
			} else if(style == ChemicalStyle.BURNING) {
				
				if(MainRegistry.proxy.me().getDistanceToEntity(this) < 100)
					FlameCreator.composeEffectClient(worldObj, posX, posY - 0.125, posZ, FlameCreator.META_FIRE);
			}
		}
		super.onUpdate();
	}
	
	protected void affect(Entity e, double intensity) {
		
		ChemicalStyle style = getStyle();
		FluidType type = getType();
		EntityLivingBase living = e instanceof EntityLivingBase ? (EntityLivingBase) e : null;
		
		if(style == ChemicalStyle.LIQUID || style == ChemicalStyle.BURNING) //ignore range penalty for liquids
			intensity = 1D;
		
		if(style == ChemicalStyle.AMAT) {
			EntityDamageUtil.attackEntityFromIgnoreIFrame(e, ModDamageSource.radiation, 1F);
			if(living != null) {
				ContaminationUtil.contaminate(living, HazardType.RADIATION, ContaminationType.CREATIVE, 50F * (float) intensity);
				return;
			}
		}
		
		if(style == ChemicalStyle.LIGHTNING) {
			EntityDamageUtil.attackEntityFromIgnoreIFrame(e, ModDamageSource.electricity, 0.5F);
			if(living != null) {
				living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60, 9));
				living.addPotionEffect(new PotionEffect(Potion.weakness.id, 60, 9));
				return;
			}
		}
		
		if(type.temperature >= 100) {
			EntityDamageUtil.attackEntityFromIgnoreIFrame(e, getDamage(ModDamageSource.s_boil), Math.min(0.25F + (type.temperature - 100) * 0.001F, 15F)); //.25 damage at 100°C with one extra damage every 1000°C
			
			if(type.temperature >= 500) {
				e.setFire(10); //afterburn for 10 seconds
			}
		}
		
		if(style == ChemicalStyle.LIQUID || style == ChemicalStyle.GAS) {
			if(type.temperature < -20) {
				if(living != null) { //only living things are affected
					EntityDamageUtil.attackEntityFromIgnoreIFrame(e, getDamage(ModDamageSource.s_boil), Math.min(0.25F + (-type.temperature) * 0.01F, 2F));
				}
			}
			
			if(type.hasTrait(Fluids.DELICIOUS.getClass())) {
				if(living != null && living.isEntityAlive()) {
					living.heal(2F * (float) intensity);
				}
			}
		}
		
		if(style == ChemicalStyle.LIQUID) {
			
			if(type.hasTrait(FT_Flammable.class)) {
				if(living != null) {
					HbmLivingProps.setOil(living, 300); //doused in oil for 15 seconds
				}
			}
			if(type.hasTrait(Fluids.DELICIOUS.getClass())) {
				if(living != null && living.isEntityAlive()) {
					living.heal(2F * (float) intensity);
				}
			}

		}
		
		if(this.isExtinguishing()) {
			e.extinguish();
		}
		
		if(style == ChemicalStyle.BURNING) {
			FT_Combustible trait = type.getTrait(FT_Combustible.class);
			EntityDamageUtil.attackEntityFromIgnoreIFrame(e, getDamage(ModDamageSource.s_flamethrower), 0.2F + (trait != null ? (Math.min(trait.getCombustionEnergy() / 100_000F, 15F)) : 0));
			e.setFire(5);
		}
		
		if(style == ChemicalStyle.GASFLAME) {
			FT_Flammable flammable = type.getTrait(FT_Flammable.class);
			FT_Combustible combustible = type.getTrait(FT_Combustible.class);
			
			float heat = Math.max(flammable != null ? flammable.getHeatEnergy() / 50_000F : 0, combustible != null ? Math.min(combustible.getCombustionEnergy() / 100_000F, 15F) : 0);
			heat *= intensity;
			EntityDamageUtil.attackEntityFromIgnoreIFrame(e, getDamage(ModDamageSource.s_flamethrower), (0.2F + heat) * (float) intensity);
			e.setFire((int) Math.ceil(5 * intensity));
		}
		
		if(type.hasTrait(FT_Corrosive.class)) {
			FT_Corrosive trait = type.getTrait(FT_Corrosive.class);

			if(living != null) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(living, getDamage(ModDamageSource.s_acid), trait.getRating() / 50F);
				for(int i = 0; i < 4; i++) {
					ArmorUtil.damageSuit(living, i, trait.getRating() / 40);
				}
			}
		}
		
		if(type.hasTrait(FT_VentRadiation.class)) {
			FT_VentRadiation trait = type.getTrait(FT_VentRadiation.class);
			if(living != null) {
				ContaminationUtil.contaminate(living, HazardType.RADIATION, ContaminationType.CREATIVE, trait.getRadPerMB() * 5);
			}
			ChunkRadiationManager.proxy.incrementRad(worldObj, (int) Math.floor(e.posX), (int) Math.floor(e.posY), (int) Math.floor(e.posZ), trait.getRadPerMB() * 5);
		}
		
		if(type.hasTrait(FT_Poison.class)) {
			FT_Poison trait = type.getTrait(FT_Poison.class);
			
			if(living != null) {
				living.addPotionEffect(new PotionEffect(trait.isWithering() ? Potion.wither.id : Potion.poison.id, (int) (5 * 20 * intensity)));
			}
		}
		
		if(type.hasTrait(FT_Toxin.class)) {
			FT_Toxin trait = type.getTrait(FT_Toxin.class);
			
			if(living != null) {
				trait.affect(living, intensity);
			}
		}

		if(type.hasTrait(FT_Pheromone.class)){

			FT_Pheromone pheromone = type.getTrait(FT_Pheromone.class);

			if(living != null) {
				living.addPotionEffect(new PotionEffect(Potion.resistance.id, 2 * 60 * 20, 2));
				living.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5 * 60 * 20, 1));
				living.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 2 * 60 * 20, 4));

				if (living instanceof EntityGlyphid && pheromone.getType() == 1) {
					living.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 5 * 60 * 20, 4));
					living.addPotionEffect(new PotionEffect(Potion.fireResistance.id,  60 * 20, 0));
					living.addPotionEffect(new PotionEffect(Potion.field_76444_x.id,  60 * 20, 19));

				} else if (living instanceof EntityPlayer && pheromone.getType() == 2) {
					living.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 2 * 60 * 20, 2));
				}
			}
		}
		
		if(type == Fluids.XPJUICE) {
			
			if(e instanceof EntityPlayer) {
				EnchantmentUtil.addExperience((EntityPlayer) e, 1, false);
				this.setDead();
			}
		}
		
		if(type == Fluids.ENDERJUICE) {
			this.teleportRandomly(e);
		}
	}
	
	/* whether this type should extinguish entities */
	protected boolean isExtinguishing() {
		return this.getStyle() == ChemicalStyle.LIQUID && this.getType().temperature < 50 && !this.getType().hasTrait(FT_Flammable.class);
	}

	/* the extinguish type for burning multiblocks, roughly identical to the fire extinguisher */
	protected EnumExtinguishType getExtinguishingType(FluidType type) {
		return type == Fluids.CARBONDIOXIDE ? EnumExtinguishType.CO2 : type == Fluids.WATER || type == Fluids.HEAVYWATER || type == Fluids.COOLANT ? EnumExtinguishType.WATER : null;
	}
	
	protected DamageSource getDamage(String name) {
		
		if(thrower != null) {
			return new EntityDamageSourceIndirect(name, this, thrower);
		} else {
			return new DamageSource(name);
		}
	}
	
	//terribly copy-pasted from EntityEnderman.class
	public boolean teleportRandomly(Entity e) {
		double x = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
		double y = this.posY + (double) (this.rand.nextInt(64) - 32);
		double z = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
		return this.teleportTo(e, x, y, z);
	}
	
	public boolean teleportTo(Entity e, double x, double y, double z) {
		
		double targetX = e.posX;
		double targetY = e.posY;
		double targetZ = e.posZ;
		e.posX = x;
		e.posY = y;
		e.posZ = z;
		boolean flag = false;
		int i = MathHelper.floor_double(e.posX);
		int j = MathHelper.floor_double(e.posY);
		int k = MathHelper.floor_double(e.posZ);

		if(e.worldObj.blockExists(i, j, k)) {
			boolean flag1 = false;

			while(!flag1 && j > 0) {
				Block block = e.worldObj.getBlock(i, j - 1, k);

				if(block.getMaterial().blocksMovement()) {
					flag1 = true;
				} else {
					--e.posY;
					--j;
				}
			}

			if(flag1) {
				e.setPosition(e.posX, e.posY, e.posZ);

				if(e.worldObj.getCollidingBoundingBoxes(e, e.boundingBox).isEmpty() && !e.worldObj.isAnyLiquid(e.boundingBox)) {
					flag = true;
				}
			}
		}

		if(!flag) {
			e.setPosition(targetX, targetY, targetZ);
			return false;
		} else {
			short short1 = 128;

			for(int l = 0; l < short1; ++l) {
				double d6 = (double) l / ((double) short1 - 1.0D);
				float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
				float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
				float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
				double d7 = targetX + (e.posX - targetX) * d6 + (this.rand.nextDouble() - 0.5D) * (double) e.width * 2.0D;
				double d8 = targetY + (e.posY - targetY) * d6 + this.rand.nextDouble() * (double) e.height;
				double d9 = targetZ + (e.posZ - targetZ) * d6 + (this.rand.nextDouble() - 0.5D) * (double) e.width * 2.0D;
				e.worldObj.spawnParticle("portal", d7, d8, d9, (double) f, (double) f1, (double) f2);
			}

			e.worldObj.playSoundEffect(targetX, targetY, targetZ, "mob.endermen.portal", 1.0F, 1.0F);
			e.playSound("mob.endermen.portal", 1.0F, 1.0F);
			return true;
		}
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {
		
		if(!worldObj.isRemote) {
			
			if(mop.typeOfHit == mop.typeOfHit.ENTITY) {
				this.affect(mop.entityHit, 1D - (double) this.ticksExisted / (double) this.getMaxAge());
			}
			
			if(mop.typeOfHit == mop.typeOfHit.BLOCK) {
				
				FluidType type = getType();
				int x = mop.blockX;
				int y = mop.blockY;
				int z = mop.blockZ;
				
				if(type.hasTrait(FT_VentRadiation.class)) {
					FT_VentRadiation trait = type.getTrait(FT_VentRadiation.class);
					ChunkRadiationManager.proxy.incrementRad(worldObj, mop.blockX, mop.blockY, mop.blockZ, trait.getRadPerMB() * 5);
				}

				ChemicalStyle style = getStyle();
				
				if(style == ChemicalStyle.BURNING || style == ChemicalStyle.GASFLAME) {
					
					for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						
						Block fire = type == Fluids.BALEFIRE ? ModBlocks.balefire : Blocks.fire;
						
						if(worldObj.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).isAir(worldObj, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
							worldObj.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, fire);
						}
					}
				}

				if(style == ChemicalStyle.BURNING || style == ChemicalStyle.GASFLAME) {

					for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

						Block fire = type == Fluids.BALEFIRE ? ModBlocks.balefire : Blocks.fire;

						if(worldObj.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).isAir(worldObj, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ)) {
							worldObj.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, fire);
						}
					}
				}
				
				if(this.isExtinguishing()) {
					
					for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
						
						if(worldObj.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) == Blocks.fire) {
							worldObj.setBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, Blocks.air);
						}
					}
				}
				
				EnumExtinguishType fext = this.getExtinguishingType(type);
				if(fext != null) {
					TileEntity core = CompatExternal.getCoreFromPos(worldObj, x, y, z);
					if(core instanceof IRepairable) {
						((IRepairable) core).tryExtinguish(worldObj, x, y, z, fext);
					}
					
					if(fext == EnumExtinguishType.WATER && style == ChemicalStyle.LIQUID) {
						for(int i = -2; i <= 2; i++) {
							for(int j = 0; j <= 1; j++) {
								for(int k = -2; k <= 2; k++) {
									if(worldObj.getBlock(x + i, y + j, z + k) == ModBlocks.fallout) {
										worldObj.setBlock(x + i, y + j, z + k, Blocks.air);
									}
								}
							}
						}
					}
				}
				
				Block block = worldObj.getBlock(x, y, z);
				if(type == Fluids.SEEDSLURRY) {
					if(block == Blocks.dirt || block == ModBlocks.waste_earth || block == ModBlocks.dirt_dead || block == ModBlocks.dirt_oily) {
						
						if(worldObj.getBlockLightValue(x, y + 1, z) >= 9 && worldObj.getBlockLightOpacity(x, y + 1, z) <= 2) {
							worldObj.setBlock(x, y, z, Blocks.grass);
						}
					}
					int meta = worldObj.getBlockMetadata(x, y, z);
					if(block == Blocks.cobblestone) worldObj.setBlock(x, y, z, Blocks.mossy_cobblestone);
					if(block == Blocks.stonebrick && meta == 0) worldObj.setBlock(x, y, z, Blocks.stonebrick, 1, 3);
					if(block == ModBlocks.waste_earth) worldObj.setBlock(x, y, z, Blocks.grass);
					if(block == ModBlocks.brick_concrete) worldObj.setBlock(x, y, z, ModBlocks.brick_concrete_mossy);
					if(block == ModBlocks.concrete_brick_slab && meta % 8 == 0) worldObj.setBlock(x, y, z, ModBlocks.concrete_brick_slab, meta + 1, 3);
					if(block == ModBlocks.brick_concrete_stairs) worldObj.setBlock(x, y, z, ModBlocks.brick_concrete_mossy_stairs, meta, 3);
				}
				
				this.setDead();
			}
		}
	}

	@Override
	protected float getAirDrag() {
		
		ChemicalStyle type = getStyle();

		if(type == ChemicalStyle.AMAT) return 1F;
		if(type == ChemicalStyle.LIGHTNING) return 1F;
		if(type == ChemicalStyle.GAS) return 0.95F;
		
		return 0.99F;
	}

	@Override
	protected float getWaterDrag() {
		
		ChemicalStyle type = getStyle();

		if(type == ChemicalStyle.AMAT) return 1F;
		if(type == ChemicalStyle.LIGHTNING) return 1F;
		if(type == ChemicalStyle.GAS) return 1F;
		
		return 0.8F;
	}
	
	public int getMaxAge() {
		
		switch(this.getStyle()) {
		case AMAT: return 100;
		case LIGHTNING: return 5;
		case BURNING:return 600;
		case GAS: return 60;
		case GASFLAME: return 20;
		case LIQUID: return 600;
		default: return 100;
		}
	}

	@Override
	public double getGravityVelocity() {
		
		ChemicalStyle type = getStyle();

		if(type == ChemicalStyle.AMAT) return 0D;
		if(type == ChemicalStyle.LIGHTNING) return 0D;
		if(type == ChemicalStyle.GAS) return 0D;
		if(type == ChemicalStyle.GASFLAME) return -0.01D;
		
		return 0.03D;
	}
	
	public ChemicalStyle getStyle() {
		return getStyleFromType(this.getType());
	}
	
	public static ChemicalStyle getStyleFromType(FluidType type) {
		
		if(type == Fluids.IONGEL) {
			return ChemicalStyle.LIGHTNING;
		}
		
		if(type.isAntimatter()) {
			return ChemicalStyle.AMAT;
		}
		
		if(type.hasTrait(Fluids.GASEOUS.getClass()) || type.hasTrait(Fluids.EVAP.getClass())) {
			
			if(type.hasTrait(FT_Flammable.class) || type.hasTrait(FT_Combustible.class)) {
				return ChemicalStyle.GASFLAME;
			} else {
				return ChemicalStyle.GAS;
			}
		}
		
		if(type.hasTrait(Fluids.LIQUID.getClass())) {
			
			if(type.hasTrait(FT_Combustible.class)) {
				return ChemicalStyle.BURNING;
			} else {
				return ChemicalStyle.LIQUID;
			}
		}
		
		return ChemicalStyle.NULL;
	}

	/**
	 * The general type of the chemical, determines rendering and movement
	 */
	public static enum ChemicalStyle {
		AMAT,		//renders as beam
		LIGHTNING,	//renders as beam
		LIQUID,		//no renderer, fluid particles
		GAS,		//renders as particles
		GASFLAME,	//renders as fire particles
		BURNING,	//no renderer, fire particles
		NULL
	}
}
