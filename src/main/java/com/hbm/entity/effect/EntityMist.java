package com.hbm.entity.effect;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Gaseous_ART;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Liquid;
import com.hbm.inventory.fluid.trait.FluidTraitSimple.FT_Viscous;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityMist extends Entity {

	public EntityMist(World world) {
		super(world);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(10, new Integer(0));
	}
	
	public EntityMist setFluid(FluidType fluid) {
		this.dataWatcher.updateObject(10, fluid.getID());
		return this;
	}
	
	public FluidType getType() {
		return Fluids.fromID(this.dataWatcher.getWatchableObjectInt(10));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		
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
