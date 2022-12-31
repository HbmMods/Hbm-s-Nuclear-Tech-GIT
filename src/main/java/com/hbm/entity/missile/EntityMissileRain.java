package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileRain extends EntityMissileBaseAdvanced {

	public EntityMissileRain(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileRain(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
		this.isCluster = true;
	}

	@Override
	public void onImpact() {
        this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 25F, true);
        ExplosionChaos.cluster(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 100, 100);
	}
	
	@Override
	public void cluster() {
		this.onImpact();
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
		return new ItemStack(ModItems.warhead_cluster_large);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER3;
	}
}
