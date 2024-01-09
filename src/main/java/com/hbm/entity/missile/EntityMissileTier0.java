package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityBlackHole;
import com.hbm.entity.effect.EntityCloudFleija;
import com.hbm.entity.effect.EntityEMPBlast;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.items.ModItems;

import api.hbm.entity.IRadarDetectableNT;

import com.hbm.items.ItemAmmoEnums.AmmoFatman;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class EntityMissileTier0 extends EntityMissileBaseNT {

	public EntityMissileTier0(World world) { super(world); }
	public EntityMissileTier0(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }

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
	public String getUnlocalizedName() {
		return "radar.target.tier0";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER0;
	}
	
	public static class EntityMissileMicro extends EntityMissileTier0 {
		public EntityMissileMicro(World world) { super(world); }
		public EntityMissileMicro(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() { ExplosionNukeSmall.explode(worldObj, posX, posY + 0.5, posZ, ExplosionNukeSmall.PARAMS_HIGH); }
		@Override public ItemStack getDebrisRareDrop() { return ModItems.ammo_nuke.stackFromEnum(AmmoFatman.HIGH); }
	}
	
	public static class EntityMissileSchrabidium extends EntityMissileTier0 {
		public EntityMissileSchrabidium(World world) { super(world); }
		public EntityMissileSchrabidium(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			EntityNukeExplosionMK3 ex = EntityNukeExplosionMK3.statFacFleija(worldObj, posX, posY, posZ, BombConfig.aSchrabRadius);
			if(!ex.isDead) {
				worldObj.spawnEntityInWorld(ex);
				EntityCloudFleija cloud = new EntityCloudFleija(this.worldObj, BombConfig.aSchrabRadius);
				cloud.posX = this.posX;
				cloud.posY = this.posY;
				cloud.posZ = this.posZ;
				this.worldObj.spawnEntityInWorld(cloud);
			}
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.powder_schrabidium, 1); }
	}
	
	public static class EntityMissileBHole extends EntityMissileTier0 {
		public EntityMissileBHole(World world) { super(world); }
		public EntityMissileBHole(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 1.5F, true);
			EntityBlackHole bl = new EntityBlackHole(this.worldObj, 1.5F);
			bl.posX = this.posX;
			bl.posY = this.posY;
			bl.posZ = this.posZ;
			this.worldObj.spawnEntityInWorld(bl);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.grenade_black_hole, 1); }
	}
	
	public static class EntityMissileTaint extends EntityMissileTier0 {
		public EntityMissileTaint(World world) { super(world); }
		public EntityMissileTaint(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 10.0F, true);
			for(int i = 0; i < 100; i++) {
				int a = rand.nextInt(11) + (int) this.posX - 5;
				int b = rand.nextInt(11) + (int) this.posY - 5;
				int c = rand.nextInt(11) + (int) this.posZ - 5;
				if(worldObj.getBlock(a, b, c).isReplaceable(worldObj, a, b, c) && BlockTaint.hasPosNeightbour(worldObj, a, b, c)) worldObj.setBlock(a, b, c, ModBlocks.taint);
			}
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.powder_spark_mix, 1); }
	}
	
	public static class EntityMissileEMP extends EntityMissileTier0 {
		public EntityMissileEMP(World world) { super(world); }
		public EntityMissileEMP(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onImpact() {
			ExplosionNukeGeneric.empBlast(worldObj, (int)posX, (int)posY, (int)posZ, 50);
			EntityEMPBlast wave = new EntityEMPBlast(worldObj, 50);
			wave.posX = posX;
			wave.posY = posY;
			wave.posZ = posZ;
			worldObj.spawnEntityInWorld(wave);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModBlocks.emp_bomb, 1); }
	}
}
