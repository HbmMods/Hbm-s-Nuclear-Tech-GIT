package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.ExplosionThermo;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileEndo extends EntityMissileBaseAdvanced {

	public EntityMissileEndo(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileEndo(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10.0F, true);
		ExplosionThermo.freeze(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 30);
		ExplosionThermo.freezer(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 40);
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_titanium, 10));
		list.add(new ItemStack(ModItems.plate_steel, 14));
		list.add(new ItemStack(ModItems.plate_aluminium, 8));
		list.add(new ItemStack(ModItems.thruster_large, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.warhead_thermo_exo);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER3;
	}
}
