package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileSchrabidium extends EntityMissileBaseAdvanced {

	public EntityMissileSchrabidium(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileSchrabidium(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
        if (!this.worldObj.isRemote)
        {
			EntityNukeExplosionMK3 entity = new EntityNukeExplosionMK3(this.worldObj);
			entity.posX = this.posX;
			entity.posY = this.posY;
			entity.posZ = this.posZ;
			entity.destructionRange = MainRegistry.aSchrabRadius;
			entity.speed = 25;
			entity.coefficient = 1.0F;
			entity.waste = false;

			this.worldObj.spawnEntityInWorld(entity);

			EntityCloudFleija cloud = new EntityCloudFleija(this.worldObj, MainRegistry.aSchrabRadius);
			cloud.posX = this.posX;
			cloud.posY = this.posY;
			cloud.posZ = this.posZ;
			this.worldObj.spawnEntityInWorld(cloud);
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
		return new ItemStack(ModItems.powder_schrabidium, 1);
	}

	@Override
	public int getMissileType() {
		return 0;
	}

}
