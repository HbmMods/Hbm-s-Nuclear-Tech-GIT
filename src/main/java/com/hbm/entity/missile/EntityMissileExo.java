package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.particle.EntitySmokeFX;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileExo extends EntityMissileBaseAdvanced {

	public EntityMissileExo(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileExo(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10.0F, true);
		ExplosionThermo.scorch(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 30);
		ExplosionThermo.setEntitiesOnFire(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 40);
	}

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_titanium, 10));
		list.add(new ItemStack(ModItems.plate_steel, 14));
		list.add(new ItemStack(ModItems.plate_aluminium, 8));
		list.add(new ItemStack(ModItems.thruster_large, 1));
		
		return list;
	}

	@Override
	public ItemStack getDebrisRareDrop() {
		return new ItemStack(ModItems.warhead_thermo_exo);
	}

	@Override
	public int getMissileType() {
		return 3;
	}
}
