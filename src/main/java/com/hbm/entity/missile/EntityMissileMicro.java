package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.items.ItemAmmoEnums.AmmoFatman;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileMicro extends EntityMissileBaseAdvanced {

	public EntityMissileMicro(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileMicro(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		if(!this.worldObj.isRemote) {

			ExplosionNukeSmall.explode(worldObj, posX, posY + 0.5, posZ, ExplosionNukeSmall.high);
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
		return ModItems.ammo_nuke.stackFromEnum(AmmoFatman.HIGH);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER0;
	}
}
