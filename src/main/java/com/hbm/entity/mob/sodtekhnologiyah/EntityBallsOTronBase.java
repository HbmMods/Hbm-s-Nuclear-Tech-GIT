package com.hbm.entity.mob.sodtekhnologiyah;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class EntityBallsOTronBase extends EntityWormBase {
	
	public int attackCounter = 0;
	
	protected final IEntitySelector selector = new IEntitySelector() {

		@Override
		public boolean isEntityApplicable(Entity ent) {
			
			if(ent instanceof EntityWormBase && ((EntityWormBase)ent).getUniqueWormID() == EntityBallsOTronBase.this.getUniqueWormID())
				return false;
			
			return true;
		}
		
	};

	public EntityBallsOTronBase(World world) {
		super(world);
		this.setSize(2.0F, 2.0F);
		this.isImmuneToFire = true;
		this.isAirBorne = true;
		this.noClip = true;
		this.renderDistanceWeight = 15.0D;
		this.dragInAir = 0.995F;
		this.dragInGround = 0.98F;
		this.knockbackDivider = 1.0D;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5000.0D);
	}

	@Override
    protected boolean isAIEnabled() {
        return true;
    }

	@Override
    protected boolean canDespawn() {
        return false;
    }
}
