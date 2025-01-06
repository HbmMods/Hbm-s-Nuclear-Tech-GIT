package com.hbm.entity.effect;

import java.util.List;

import com.hbm.entity.mob.glyphid.EntityGlyphid;
import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.*;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Liquid;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Viscous;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.util.ArmorUtil;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.EntityDamageUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityMist extends Entity {

	public EntityMist(World world) {
		super(world);
		this.noClip = true;
	}
	public int maxAge = 150;
	public EntityMist setArea(float width, float height) {
		this.dataWatcher.updateObject(11, width);
		this.dataWatcher.updateObject(12, height);
		return this;
	}
	public EntityMist setDuration(int duration){
		this.maxAge = duration;
		return this;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(10, new Integer(0));
		this.dataWatcher.addObject(11, new Float(0));
		this.dataWatcher.addObject(12, new Float(0));
	}
	
	public EntityMist setType(FluidType fluid) {
		this.dataWatcher.updateObject(10, fluid.getID());
		return this;
	}
	
	public FluidType getType() {
		return Fluids.fromID(this.dataWatcher.getWatchableObjectInt(10));
	}

	@Override
	public void onEntityUpdate() {
		
		float height = this.dataWatcher.getWatchableObjectFloat(12);
		this.yOffset = 0;
		this.setSize(this.dataWatcher.getWatchableObjectFloat(11), height);
		this.setPosition(this.posX, this.posY, this.posZ);
		
		if(!worldObj.isRemote) {
			
			if(this.ticksExisted >= this.getMaxAge()) {
				this.setDead();
			}

			FluidType type = this.getType();
			
			if(type.hasTrait(FT_VentRadiation.class)) {
				FT_VentRadiation trait = type.getTrait(FT_VentRadiation.class);
				ChunkRadiationManager.proxy.incrementRad(worldObj, (int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ), trait.getRadPerMB() * 2);
			}
			
			double intensity = 1D - (double) this.ticksExisted / (double) this.getMaxAge();
			
			if(type.hasTrait(FT_Flammable.class) && this.isBurning()) {
				worldObj.createExplosion(this, posX, posY + height / 2, posZ, (float) intensity * 15F, true);
				this.setDead();
				return;
			}
			
			AxisAlignedBB aabb = this.boundingBox.copy();
			List<Entity> affected = worldObj.getEntitiesWithinAABBExcludingEntity(this, aabb.offset(-this.width / 2, 0, -this.width / 2));
			
			for(Entity e : affected) {
				this.affect(e, intensity);
			}
		} else {
			
			for(int i = 0; i < 2; i++) {
				double x = this.boundingBox.minX + (rand.nextDouble() - 0.5) * (this.boundingBox.maxX - this.boundingBox.minX);
				double y = this.boundingBox.minY + rand.nextDouble() * (this.boundingBox.maxY - this.boundingBox.minY);
				double z = this.boundingBox.minZ + (rand.nextDouble() - 0.5) * (this.boundingBox.maxZ - this.boundingBox.minZ);
				
				NBTTagCompound fx = new NBTTagCompound();
				fx.setString("type", "tower");
				fx.setFloat("lift", 0.5F);
				fx.setFloat("base", 0.75F);
				fx.setFloat("max", 2F);
				fx.setInteger("life", 50 + worldObj.rand.nextInt(10));
				fx.setInteger("color",this.getType().getColor());
				fx.setDouble("posX", x);
				fx.setDouble("posY", y);
				fx.setDouble("posZ", z);
				MainRegistry.proxy.effectNT(fx);
			}
		}
	}
	
	/* can't reuse EntityChemical here, while similar or identical in some places, the actual effects are often different */
	protected void affect(Entity e, double intensity) {

		FluidType type = this.getType();
		EntityLivingBase living = e instanceof EntityLivingBase ? (EntityLivingBase) e : null;
		
		if(type.temperature >= 100) {
			EntityDamageUtil.attackEntityFromIgnoreIFrame(e, new DamageSource(ModDamageSource.s_boil), 0.2F + (type.temperature - 100) * 0.02F);
			
			if(type.temperature >= 500) {
				e.setFire(10); //afterburn for 10 seconds
			}
		}
		if(type.temperature < -20) {
			if(living != null) { //only living things are affected
				EntityDamageUtil.attackEntityFromIgnoreIFrame(e, new DamageSource(ModDamageSource.s_cryolator), 0.2F + (type.temperature + 20) * -0.05F); //5 damage at -20°C with one extra damage every -20°C
				living.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 2));
				living.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 100, 4));
			}
		}
		
		if(type.hasTrait(Fluids.DELICIOUS.getClass())) {
			if(living != null && living.isEntityAlive()) {
				living.heal(2F * (float) intensity);
			}
		}
		
		if(type.hasTrait(FT_Flammable.class) && type.hasTrait(FT_Liquid.class)) {
			if(living != null) {
				HbmLivingProps.setOil(living, 200); //doused in oil for 10 seconds
			}
		}
		
		if(this.isExtinguishing(type)) {
			e.extinguish();
		}
		
		if(type.hasTrait(FT_Corrosive.class)) {
			FT_Corrosive trait = type.getTrait(FT_Corrosive.class);
			
			if(living != null) {
				EntityDamageUtil.attackEntityFromIgnoreIFrame(living, ModDamageSource.acid, trait.getRating() / 60F);
				for(int i = 0; i < 4; i++) {
					ArmorUtil.damageSuit(living, i, trait.getRating() / 50);
				}
			}
		}
		
		if(type.hasTrait(FT_VentRadiation.class)) {
			FT_VentRadiation trait = type.getTrait(FT_VentRadiation.class);
			if(living != null) {
				ContaminationUtil.contaminate(living, HazardType.RADIATION, ContaminationType.CREATIVE, trait.getRadPerMB() * 5);
			}
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

		if(type == Fluids.ENDERJUICE && living != null){
			teleportRandomly(living);
		}

		if(type.hasTrait(FT_Pheromone.class)){

			FT_Pheromone pheromone = type.getTrait(FT_Pheromone.class);

			if(living != null) {
				if ((living instanceof EntityGlyphid && pheromone.getType() == 1) || (living instanceof EntityPlayer && pheromone.getType() == 2)) {
					int mult = pheromone.getType();

					living.addPotionEffect(new PotionEffect(Potion.moveSpeed.id,  mult * 60 * 20, 1));
					living.addPotionEffect(new PotionEffect(Potion.digSpeed.id, mult * 60 * 20, 1));
					living.addPotionEffect(new PotionEffect(Potion.regeneration.id,  mult * 2 * 20, 0));
					living.addPotionEffect(new PotionEffect(Potion.resistance.id,  mult * 60 * 20, 0));
					living.addPotionEffect(new PotionEffect(Potion.damageBoost.id,  mult * 60 * 20, 1));
					living.addPotionEffect(new PotionEffect(Potion.fireResistance.id,  mult * 60 * 20, 0));

				}
			}
		}
	}
	
	protected boolean isExtinguishing(FluidType type) {
		return this.getType().temperature < 50 && !type.hasTrait(FT_Flammable.class);
	}

	public int getMaxAge() {
		return maxAge;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.setType(Fluids.fromID(nbt.getInteger("type")));
		this.setArea(nbt.getFloat("width"), nbt.getFloat("height"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("type", this.getType().getID());
		nbt.setFloat("width", this.dataWatcher.getWatchableObjectFloat(11));
		nbt.setFloat("height", this.dataWatcher.getWatchableObjectFloat(12));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canRenderOnFire() {
		return false;
	}

	@Override public void moveEntity(double x, double y, double z) { }
	@Override public void addVelocity(double x, double y, double z) { }
	@Override public void setPosition(double x, double y, double z) {
		if(this.ticksExisted == 0) super.setPosition(x, y, z); //honest to fucking god mojang suck my fucking nuts
	}
	
	public static SprayStyle getStyleFromType(FluidType type) {
		
		if(type.hasTrait(FT_Viscous.class)) {
			return SprayStyle.NULL;
		}
		
		if(type.hasTrait(FT_Gaseous.class) || type.hasTrait(FT_Gaseous_ART.class)) {
			return SprayStyle.GAS;
		}
		
		if(type.hasTrait(FT_Liquid.class)) {
			return SprayStyle.MIST;
		}
		
		return SprayStyle.NULL;
	}

	public static enum SprayStyle {
		MIST,	//liquids that have been sprayed into a mist
		GAS,	//things that were already gaseous
		NULL
	}

	//terribly copy-pasted from EntityChemical.class, whose method was terribly copy-pasted from EntityEnderman.class
	//the fun never ends
	public void teleportRandomly(Entity e) {
		double x = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
		double y = this.posY + (double) (this.rand.nextInt(64) - 32);
		double z = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
		this.teleportTo(e, x, y, z);
	}

	public void teleportTo(Entity e, double x, double y, double z) {

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
		}
	}
}
