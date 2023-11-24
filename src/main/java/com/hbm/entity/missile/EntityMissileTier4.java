package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;

import api.hbm.entity.IRadarDetectableNT;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class EntityMissileTier4 extends EntityMissileBaseNT {

	public EntityMissileTier4(World world) { super(world); }
	public EntityMissileTier4(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ModItems.plate_titanium, 16));
		list.add(new ItemStack(ModItems.plate_steel, 20));
		list.add(new ItemStack(ModItems.plate_aluminium, 12));
		list.add(new ItemStack(ModItems.thruster_large, 1));
		list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
		return list;
	}

	@Override
	public RadarTargetType getTargetType() {
		return RadarTargetType.MISSILE_TIER4;
	}

	@Override
	public String getUnlocalizedName() {
		return "radar.target.tier4";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER4;
	}
	
	public static class EntityMissileNuclear extends EntityMissileTier4 {
		public EntityMissileNuclear(World world) { super(world); }
		public EntityMissileNuclear(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(worldObj, BombConfig.missileRadius, posX, posY, posZ));
			EntityNukeTorex.statFac(worldObj, posX, posY, posZ, BombConfig.missileRadius);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_nuclear); }
	}
	
	public static class EntityMissileMirv extends EntityMissileTier4 {
		public EntityMissileMirv(World world) { super(world); }
		public EntityMissileMirv(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			worldObj.spawnEntityInWorld(EntityNukeExplosionMK5.statFac(worldObj, BombConfig.missileRadius * 2, posX, posY, posZ));
			EntityNukeTorex.statFac(worldObj, posX, posY, posZ, BombConfig.missileRadius * 2);
		}
		@Override public List<ItemStack> getDebris() {
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(new ItemStack(ModItems.plate_titanium, 16));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 1));
			return list;
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_mirv); }
	}
	
	public static class EntityMissileVolcano extends EntityMissileTier4 {
		public EntityMissileVolcano(World world) { super(world); }
		public EntityMissileVolcano(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			ExplosionLarge.explode(worldObj, posX, posY, posZ, 10.0F, true, true, true);
			for(int x = -1; x <= 1; x++) for(int y = -1; y <= 1; y++) for(int z = -1; z <= 1; z++) worldObj.setBlock((int)Math.floor(posX + x), (int)Math.floor(posY + y), (int)Math.floor(posZ + z), ModBlocks.volcanic_lava_block);
			worldObj.setBlock((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ), ModBlocks.volcano_core);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_volcano); }
	}
}
