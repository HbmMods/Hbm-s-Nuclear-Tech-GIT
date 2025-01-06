package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.items.ModItems;
import com.hbm.particle.helper.ExplosionCreator;

import api.hbm.entity.IRadarDetectableNT;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class EntityMissileTier1 extends EntityMissileBaseNT {

	public EntityMissileTier1(World world) { super(world); }
	public EntityMissileTier1(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();
		list.add(new ItemStack(ModItems.plate_titanium, 4));
		list.add(new ItemStack(ModItems.thruster_small, 1));
		return list;
	}

	@Override
	protected float getContrailScale() {
		return 0.5F;
	}

	public static class EntityMissileGeneric extends EntityMissileTier1 {
		public EntityMissileGeneric(World world) { super(world); }
		public EntityMissileGeneric(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() { this.explodeStandard(15F, 24, false); ExplosionCreator.composeEffectSmall(worldObj, posX, posY, posZ); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_generic_small); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_generic); }
	}

	public static class EntityMissileDecoy extends EntityMissileTier1 {
		public EntityMissileDecoy(World world) { super(world); }
		public EntityMissileDecoy(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() { worldObj.newExplosion(this, posX, posY, posZ, 4F, false, false); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.ingot_steel); }
		@Override public String getUnlocalizedName() { return "radar.target.tier4"; }
		@Override public int getBlipLevel() { return IRadarDetectableNT.TIER4; }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_decoy); }
	}

	public static class EntityMissileIncendiary extends EntityMissileTier1 {
		public EntityMissileIncendiary(World world) { super(world); }
		public EntityMissileIncendiary(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() { this.explodeStandard(15F, 24, true); ExplosionCreator.composeEffectSmall(worldObj, posX, posY, posZ); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_incendiary_small); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_incendiary); }
	}

	public static class EntityMissileCluster extends EntityMissileTier1 {
		public EntityMissileCluster(World world) { super(world); }
		public EntityMissileCluster(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); this.isCluster = true; }
		@Override public void onImpact() {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 5F, true);
			ExplosionChaos.cluster(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 25, 100);
		}
		@Override public void cluster() { this.onImpact(); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_cluster_small); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_cluster); }
	}

	public static class EntityMissileBunkerBuster extends EntityMissileTier1 {
		public EntityMissileBunkerBuster(World world) { super(world); }
		public EntityMissileBunkerBuster(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			for(int i = 0; i < 15; i++) this.worldObj.createExplosion(this, this.posX, this.posY - i, this.posZ, 5F, true);
			ExplosionLarge.spawnParticles(worldObj, this.posX, this.posY, this.posZ, 5);
			ExplosionLarge.spawnShrapnels(worldObj, this.posX, this.posY, this.posZ, 5);
			ExplosionLarge.spawnRubble(worldObj, this.posX, this.posY, this.posZ, 5);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_buster_small); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_buster); }
	}
}
