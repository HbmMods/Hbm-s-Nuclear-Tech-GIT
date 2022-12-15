package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityMissileEMP extends EntityMissileBaseAdvanced {

	public EntityMissileEMP(World p_i1582_1_) {
		super(p_i1582_1_);
	}

	public EntityMissileEMP(World world, float x, float y, float z, int a, int b) {
		super(world, x, y, z, a, b);
	}

	@Override
	public void onImpact() {
        if (!this.worldObj.isRemote)
        {
			ExplosionNukeGeneric.empBlast(worldObj, (int)posX, (int)posY, (int)posZ, 50);
			EntityEMPBlast wave = new EntityEMPBlast(worldObj, 50);
			wave.posX = posX;
			wave.posY = posY;
			wave.posZ = posZ;
			worldObj.spawnEntityInWorld(wave);
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
		return new ItemStack(ModBlocks.emp_bomb, 1);
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER0;
	}
}
