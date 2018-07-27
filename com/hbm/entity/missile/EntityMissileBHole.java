package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileBHole extends EntityMissileBaseAdvanced {

	public EntityMissileBHole(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileBHole(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
        if (!this.worldObj.isRemote)
        {
            this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1.5F, true);

        	EntityBlackHole bl = new EntityBlackHole(this.worldObj, 1.5F);
        	bl.posX = this.posX;
        	bl.posY = this.posY;
        	bl.posZ = this.posZ;
        	this.worldObj.spawnEntityInWorld(bl);
        }
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.wire_aluminium, 4));
		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.hull_small_aluminium, 2));
		list.add(new ItemStack(ModItems.ducttape, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.grenade_black_hole, 1);
	}

	@Override
	public int getMissileType() {
		return 0;
	}

}
