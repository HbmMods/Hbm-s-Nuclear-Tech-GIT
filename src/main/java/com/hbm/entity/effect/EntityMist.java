package com.hbm.entity.effect;

import java.util.List;

import com.hbm.extprop.HbmLivingProps;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FT_Corrosive;
import com.hbm.inventory.fluid.trait.FT_Flammable;
import com.hbm.inventory.fluid.trait.FT_Poison;
import com.hbm.inventory.fluid.trait.FT_Toxin;
import com.hbm.inventory.fluid.trait.FT_VentRadiation;
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMist extends Entity {

	public EntityMist(World world) {
		super(world);
		this.noClip = true;
	}
	
	public EntityMist setArea(float width, float height) {
		this.dataWatcher.updateObject(11, width);
		this.dataWatcher.updateObject(12, height);
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
			
			if(this.ticksExisted > this.getMaxAge()) {
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
			EntityDamageUtil.attackEntityFromIgnoreIFrame(e, new DamageSource(ModDamageSource.s_boil), 5F + (type.temperature - 100) * 0.02F);
			
			if(type.temperature >= 500) {
				e.setFire(10); //afterburn for 10 seconds
			}
		}
		if(type.temperature < -20) {
			if(living != null) { //only living things are affected
				EntityDamageUtil.attackEntityFromIgnoreIFrame(e, new DamageSource(ModDamageSource.s_cryolator), 5F + (type.temperature + 20) * -0.05F); //5 damage at -20°C with one extra damage every -20°C
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
			EntityDamageUtil.attackEntityFromIgnoreIFrame(e, new DamageSource(ModDamageSource.s_acid), trait.getRating() / 20F);
			
			if(living != null) {
				for(int i = 0; i < 4; i++) {
					ArmorUtil.damageSuit(living, i, trait.getRating() / 5);
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
	}
	
	protected boolean isExtinguishing(FluidType type) {
		return this.getStyleFromType(type) == SprayStyle.MIST && this.getType().temperature < 50 && !type.hasTrait(FT_Flammable.class);
	}

	public int getMaxAge() {
		return getStyleFromType(this.getType()) == SprayStyle.GAS ? 600 : 150;
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
}
