package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.projectile.EntityFluidMirv;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.interfaces.IFluidMissile;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.particle.helper.ExplosionCreator;

import api.hbm.entity.IRadarDetectableNT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
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

	@Override
	protected void spawnContrail() {
		
		Vec3 thrust = Vec3.createVectorHelper(0, 0, 0.5);
		thrust.rotateAroundY((this.rotationYaw + 90) * (float) Math.PI / 180F);
		thrust.rotateAroundX(this.rotationPitch * (float) Math.PI / 180F);
		thrust.rotateAroundY(-(this.rotationYaw + 90) * (float) Math.PI / 180F);

		this.spawnContrailWithOffset(thrust.xCoord, thrust.yCoord, thrust.zCoord);
		this.spawnContrailWithOffset(-thrust.zCoord, thrust.yCoord, thrust.xCoord);
		this.spawnContrailWithOffset(-thrust.xCoord, -thrust.zCoord, -thrust.zCoord);
		this.spawnContrailWithOffset(thrust.zCoord, -thrust.zCoord, -thrust.xCoord);
	}
	
	public static class EntityMissileBurst extends EntityMissileTier3 {
		public EntityMissileBurst(World world) { super(world); }
		public EntityMissileBurst(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onMissileImpact(MovingObjectPosition mop) {
			this.explodeStandard(50F, 48, false);
			ExplosionCreator.composeEffectLarge(worldObj, posX, posY, posZ);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_generic_large); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_burst); }
	}
	
	public static class EntityMissileInferno extends EntityMissileTier3 {
		public EntityMissileInferno(World world) { super(world); }
		public EntityMissileInferno(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onMissileImpact(MovingObjectPosition mop) {
			this.explodeStandard(50F, 48, true);
			ExplosionCreator.composeEffectLarge(worldObj, posX, posY, posZ);
			ExplosionChaos.igniteAllBlocks(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 10);
			ExplosionChaos.igniteFlammableBlocks(this.worldObj, (int)this.posX, (int)this.posY, (int)this.posZ, 25);
		}
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_incendiary_large); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_inferno); }
	}

	public static class EntityMissileRain extends EntityMissileTier3 {
		public EntityMissileRain(World world) { super(world); }
		public EntityMissileRain(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); this.isCluster = true; }
		@Override public void onMissileImpact(MovingObjectPosition mop) {
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 25F, true);
			ExplosionChaos.cluster(this.worldObj, this.posX, this.posY, this.posZ, 100, this.rotationYaw, this.rotationPitch, (float) Math.PI * 0.25F, (float) Math.PI * 0.25F, 1F);
		}
		@Override public void cluster() { this.onMissileImpact(null); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_cluster_large); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_rain); }
	}
	
	public static class EntityMissileDrill extends EntityMissileTier3 {
		public EntityMissileDrill(World world) { super(world); }
		public EntityMissileDrill(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); }
		@Override public void onMissileImpact(MovingObjectPosition mop) {
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
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_drill); }
	}
	
	public static class EntityMissileFluidCluster extends EntityMissileTier3 implements IFluidMissile {
		public int fluidFill = 0;
		public static final int MIRV_COUNT = 32;
		
		public EntityMissileFluidCluster(World world) { super(world); this.isCluster = true; }
		public EntityMissileFluidCluster(World world, float x, float y, float z, int a, int b) { super(world, x, y, z, a, b); this.isCluster = true; }
		@Override public void onMissileImpact(MovingObjectPosition mop) {
			worldObj.createExplosion(this, posX, posY, posZ, 5f, false);
			if(getFluidType() == Fluids.NONE || fluidFill <= 0) return;
			
			for(int i = 0; i < MIRV_COUNT; i++) {
				EntityFluidMirv mirv = new EntityFluidMirv(worldObj);
				mirv.setPosition(posX, posY, posZ);
				mirv.setFluid(getFluidType(), fluidFill / MIRV_COUNT);
				mirv.motionX = motionX * 0.75 + rand.nextGaussian() * 0.25;
				mirv.motionY = motionY * 0.75 + rand.nextGaussian() * 0.2;
				mirv.motionZ = motionZ * 0.75 + rand.nextGaussian() * 0.25;
				mirv.rotation();
				worldObj.spawnEntityInWorld(mirv);
			}
		}
		@Override public void cluster() { onMissileImpact(null); }
		@Override public ItemStack getDebrisRareDrop() { return new ItemStack(ModItems.warhead_fluid_cluster); }
		@Override public ItemStack getMissileItemForInfo() { return new ItemStack(ModItems.missile_fluid_cluster); }

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
		@Override
		public void setFluid(FluidType type, int fill) {
			this.dataWatcher.updateObject(12, type.getID());
			this.fluidFill = fill;
		}
		@Override public FluidType getFluidType() { return Fluids.fromID(this.dataWatcher.getWatchableObjectInt(12)); }
		@Override public int getFluidFill() { return fluidFill; }
	}
}
