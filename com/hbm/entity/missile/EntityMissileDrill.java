package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileDrill extends EntityMissileBaseAdvanced {

	public EntityMissileDrill(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileDrill(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		for(int i = 0; i < 30; i++)
		{
			this.worldObj.createExplosion(this, this.posX, this.posY - i, this.posZ, 10F, true);
		}
		ExplosionLarge.spawnParticles(worldObj, this.posX, this.posY, this.posZ, 25);
		ExplosionLarge.spawnShrapnels(worldObj, this.posX, this.posY, this.posZ, 12);
		ExplosionLarge.spawnRubble(worldObj, this.posX, this.posY, this.posZ, 12);
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_steel, 16));
		list.add(new ItemStack(ModItems.plate_titanium, 10));
		list.add(new ItemStack(ModItems.thruster_large, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.warhead_buster_large);
	}

	@Override
	public int getMissileType() {
		return 2;
	}
}
