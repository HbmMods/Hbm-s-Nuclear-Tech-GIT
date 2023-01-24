package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileBusterStrong extends EntityMissileBaseAdvanced {

	public EntityMissileBusterStrong(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileBusterStrong(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		for(int i = 0; i < 20; i++)
		{
			this.worldObj.createExplosion(this, this.posX, this.posY - i, this.posZ, 7.5F, true);
		}
		ExplosionLarge.spawnParticles(worldObj, this.posX, this.posY, this.posZ, 8);
		ExplosionLarge.spawnShrapnels(worldObj, this.posX, this.posY, this.posZ, 8);
		ExplosionLarge.spawnRubble(worldObj, this.posX, this.posY, this.posZ, 8);
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_steel, 10));
		list.add(new ItemStack(ModItems.plate_titanium, 6));
		list.add(new ItemStack(ModItems.thruster_medium, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.warhead_buster_medium);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER2;
	}
}
