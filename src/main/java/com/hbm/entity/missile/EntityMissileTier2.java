package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.effect.EntityMist;
import com.hbm.entity.logic.EntityEMP;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IFluidMissile;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.trait.FluidTrait;
import com.hbm.items.ModItems;
import com.hbm.particle.helper.ExplosionCreator;

import api.hbm.entity.IRadarDetectableNT;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class EntityMissileTier2 extends EntityMissileBaseNT {

	public EntityMissileTier2(World world) { super(world); }
	public EntityMissileTier2(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }

	@Override
	public List<ItemStack> getDebris() {
		List<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(ModItems.plate_steel, 10));
		list.add(new ItemStack(ModItems.plate_titanium, 6));
		list.add(new ItemStack(ModItems.thruster_medium, 1));
		
		return list;
	}

	@Override
	public String getUnlocalizedName() {
		return "radar.target.tier2";
	}

	@Override
	public int getBlipLevel() {
		return IRadarDetectableNT.TIER2;
	}

	public static class EntityMissileStrong extends EntityMissileTier2 {
		public EntityMissileStrong(World world) { super(world); }
		public EntityMissileStrong(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onMissileImpact(MovingObjectPosition mop) { this.explodeStandard(30F, 32, false); ExplosionCreator.composeEffectStandard(worldObj, posX, posY, posZ); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_generic_medium); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_strong); }
	}

	public static class EntityMissileIncendiaryStrong extends EntityMissileTier2 {
		public EntityMissileIncendiaryStrong(World world) { super(world); }
		public EntityMissileIncendiaryStrong(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onMissileImpact(MovingObjectPosition mop) {
			this.explodeStandard(30F, 32, true);
			ExplosionCreator.composeEffectStandard(worldObj, posX, posY, posZ);
			ExplosionChaos.igniteFlammableBlocks(this.worldObj, (int)((float)this.posX + 0.5F), (int)((float)this.posY + 0.5F), (int)((float)this.posZ + 0.5F), 25);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_incendiary_medium); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_incendiary_strong); }
	}

	public static class EntityMissileClusterStrong extends EntityMissileTier2 {
		public EntityMissileClusterStrong(World world) { super(world); }
		public EntityMissileClusterStrong(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); this.isCluster = true; }
		@Override public void onMissileImpact(MovingObjectPosition mop) {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 15F, true);
			ExplosionChaos.cluster(this.worldObj, this.posX, this.posY, this.posZ, 50, this.rotationYaw, this.rotationPitch, (float) Math.PI * 0.25F, (float) Math.PI * 0.25F, 1F);
		}
		@Override public void cluster() { this.onMissileImpact(null); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_cluster_medium); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_cluster_strong); }
	}

	public static class EntityMissileBusterStrong extends EntityMissileTier2 {
		public EntityMissileBusterStrong(World world) { super(world); }
		public EntityMissileBusterStrong(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onMissileImpact(MovingObjectPosition mop) {
			for(int i = 0; i < 20; i++) this.worldObj.createExplosion(this, this.posX, this.posY - i, this.posZ, 7.5F, true);
			ExplosionLarge.spawnParticles(worldObj, this.posX, this.posY, this.posZ, 8);
			ExplosionLarge.spawnShrapnels(worldObj, this.posX, this.posY, this.posZ, 8);
			ExplosionLarge.spawnRubble(worldObj, this.posX, this.posY, this.posZ, 8);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_buster_medium); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_buster_strong); }
	}

	public static class EntityMissileEMPStrong extends EntityMissileTier2 {
		public EntityMissileEMPStrong(World world) { super(world); }
		public EntityMissileEMPStrong(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onMissileImpact(MovingObjectPosition mop) {
			EntityEMP emp = new EntityEMP(worldObj);
			emp.posX = posX;
			emp.posY = posY;
			emp.posZ = posZ;
			worldObj.spawnEntityInWorld(emp);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_generic_medium); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_emp_strong); }
	}
	
	public static class EntityMissileFluid extends EntityMissileTier2 implements IFluidMissile {
		public int fluidFill = 0;
		public static final int CAPACITY = 32_000;
		
		public EntityMissileFluid(World world) { super(world); }
		public EntityMissileFluid(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onMissileImpact(MovingObjectPosition mop) {
			double x = posX, y = posY, z = posZ;
			if(mop != null && mop.hitVec != null) {
				x = mop.hitVec.xCoord;
				y = mop.hitVec.yCoord;
				z = mop.hitVec.zCoord;
			}
			
			worldObj.createExplosion(this, x, y, z, 5f, false);
			if(getFluidType() != Fluids.NONE && fluidFill > 0) {
				EntityMist mist = new EntityMist(worldObj);
				mist.setType(getFluidType());
				mist.setPosition(x, y, z);
				float size = 5f + 35f * Math.min(fluidFill, CAPACITY) / CAPACITY;
				mist.setArea(size, size / 2);
				mist.setDuration(800);
				mist.setDensity(((float) fluidFill / CAPACITY * 7f) + 1f);
				mist.setParticleLife(100);
				FluidTrait.onRelease(worldObj,(int) posX,(int) posY,(int) posZ, getFluidType(), null, FluidTrait.FluidReleaseType.SPILL, fluidFill);
				worldObj.spawnEntityInWorld(mist);
			}
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_fluid); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_fluid); }
		@Override protected void entityInit() {
			super.entityInit();
			this.dataWatcher.addObject(12, new Integer(0));
		}
		@Override public void writeEntityToNBT(NBTTagCompound nbt) {
			super.writeEntityToNBT(nbt);
			nbt.setInteger("fluid", this.dataWatcher.getWatchableObjectInt(12));
			nbt.setInteger("fill", fluidFill);
		}
		@Override public void readEntityFromNBT(NBTTagCompound nbt) {
			super.readEntityFromNBT(nbt);
			this.dataWatcher.updateObject(12, nbt.getInteger("fluid"));
		fluidFill = nbt.getInteger("fill");
		}
		@Override public void setFluid(FluidType type, int fill) {
			this.dataWatcher.updateObject(12, type.getID());
			this.fluidFill = fill;
		}
		@Override public FluidType getFluidType() { return Fluids.fromID(this.dataWatcher.getWatchableObjectInt(12)); }
		@Override public int getFluidFill() { return fluidFill; }
	}
}
