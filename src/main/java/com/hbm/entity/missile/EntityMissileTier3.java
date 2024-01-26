package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionThermo;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.items.ModItems;

import api.hbm.entity.IRadarDetectableNT;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class EntityMissileTier3 extends EntityMissileBaseNT {

	public EntityMissileTier3(World world) { super(world); }
	public EntityMissileTier3(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }

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
	public String getUnlocalizedName() {
		return "radar.target.tier3";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER3;
	}
	
	public static class EntityMissileBurst extends EntityMissileTier3 {
		public EntityMissileBurst(World world) { super(world); }
		public EntityMissileBurst(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.explodeStandard(50F, 48, false, true);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_generic_large); }
	}
	
	public static class EntityMissileInferno extends EntityMissileTier3 {
		public EntityMissileInferno(World world) { super(world); }
		public EntityMissileInferno(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.explodeStandard(50F, 48, true, true);
			ExplosionChaos.burn(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 10);
			ExplosionChaos.flameDeath(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 25);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_incendiary_large); }
	}

	public static class EntityMissileRain extends EntityMissileTier3 {
		public EntityMissileRain(World world) { super(world); }
		public EntityMissileRain(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); this.isCluster = true; }
		@Override public void onImpact() {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 25F, true);
			ExplosionChaos.cluster(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 100, 100);
		}
		@Override public void cluster() { this.onImpact(); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_cluster_large); }
	}
	
	public static class EntityMissileDrill extends EntityMissileTier3 {
		public EntityMissileDrill(World world) { super(world); }
		public EntityMissileDrill(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			for(int i = 0; i < 30; i++) {
				ExplosionNT explosion = new ExplosionNT(worldObj, this, this.posX, this.posY - i, this.posZ, 10F);
				explosion.addAllAttrib(ExAttrib.ERRODE);
				explosion.explode(); //an explosion exploded!
			}
			ExplosionLarge.spawnParticles(worldObj, this.posX, this.posY, this.posZ, 25);
			ExplosionLarge.spawnShrapnels(worldObj, this.posX, this.posY, this.posZ, 12);
			ExplosionLarge.jolt(worldObj, this.posX, this.posY, this.posZ, 10, 50, 1);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_buster_large); }
	}
	
	public static class EntityMissileEndo extends EntityMissileTier3 {
		public EntityMissileEndo(World world) { super(world); }
		public EntityMissileEndo(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10.0F, true);
			ExplosionThermo.freeze(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 30);
			ExplosionThermo.freezer(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 40);
		}
		@Override public List<ItemStack> getDebris() {
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			list.add(new ItemStack(ModItems.plate_aluminium, 8));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
			return list;
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_thermo_exo); }
	}
	
	public static class EntityMissileExo extends EntityMissileTier3 {
		public EntityMissileExo(World world) { super(world); }
		public EntityMissileExo(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10.0F, true);
			ExplosionThermo.scorch(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 30);
			ExplosionThermo.setEntitiesOnFire(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 40);
		}
		@Override public List<ItemStack> getDebris() {
			List<ItemStack> list = new ArrayList<ItemStack>();
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			list.add(new ItemStack(ModItems.plate_aluminium, 8));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
			return list;
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_thermo_exo); }
	}
}
