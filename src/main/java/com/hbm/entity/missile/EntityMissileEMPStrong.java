package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.EntityEMP;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileEMPStrong extends EntityMissileBaseAdvanced {

	public EntityMissileEMPStrong(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileEMPStrong(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
		
		EntityEMP emp = new EntityEMP(worldObj);
		emp.posX = posX;
		emp.posY = posY;
		emp.posZ = posZ;
		
		worldObj.spawnEntityInWorld(emp);
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
		return new ItemStack(ModItems.warhead_generic_medium);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER2;
	}
}
