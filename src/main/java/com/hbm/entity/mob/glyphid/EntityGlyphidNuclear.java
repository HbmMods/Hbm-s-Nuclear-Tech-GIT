package com.hbm.entity.mob.glyphid;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.logic.EntityWaypoint;
import com.hbm.entity.mob.EntityParasiteMaggot;
import com.hbm.entity.mob.glyphid.GlyphidStats.StatBundle;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockMutatorDebris;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EntityGlyphidNuclear extends EntityGlyphid {
	
	public int deathTicks;
	public EntityGlyphidNuclear(World world) {
		super(world);
		this.setSize(2.5F, 1.75F);
		this.isImmuneToFire = true;
	}
	
	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_nuclear_tex;
	}

	@Override
	public double getScale() {
		return 2D;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(GlyphidStats.getStats().getNuclear().health);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(GlyphidStats.getStats().getNuclear().speed);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(GlyphidStats.getStats().getNuclear().damage);
	}
	
	public StatBundle getStats() {
		return GlyphidStats.getStats().statsNuclear;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if(ticksExisted % 20 == 0) {
			if(isAtDestination() && getCurrentTask() == TASK_FOLLOW) {
				setCurrentTask(TASK_IDLE, null);
			}

			if(getCurrentTask() == TASK_BUILD_HIVE && getAITarget() == null) {
				this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10 * 20, 3));
			}

			if(getCurrentTask() == TASK_TERRAFORM) {
				this.setHealth(0);
			}
		}
	}

	/** Communicates only with glyphid scouts, unlike the super implementation which does the opposite */
	@Override
	public void communicate(int task, @Nullable EntityWaypoint waypoint) {
		int radius = waypoint != null ? waypoint.radius : 4;

		AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(
				this.posX - radius,
				this.posY - radius,
				this.posZ - radius,
				this.posX + radius,
				this.posY + radius,
				this.posZ + radius);

		List<Entity> bugs = worldObj.getEntitiesWithinAABBExcludingEntity(this, bb);
		for (Entity e: bugs){
			if(e instanceof EntityGlyphidScout){
				if(((EntityGlyphid) e).getCurrentTask() != task){
					((EntityGlyphid) e).setCurrentTask(task, waypoint);
				}
			}
		}
	}

	@Override
	public boolean isArmorBroken(float amount) {
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount * 0.12, 2), 100);
	}

	@Override
	public boolean doesInfectedSpawnMaggots() {
		return false;
	}

	public boolean hasWaypoint = false;
	@Override
	protected void onDeathUpdate() {
		++this.deathTicks;

		if(!hasWaypoint) {
			// effectively causes neighboring EntityGlyphidScout to retreat
			communicate(TASK_INITIATE_RETREAT, null);
			hasWaypoint = true;
		}
		
		if(deathTicks == 90){
			int radius = 8;
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(this.posX, this.posY, this.posZ, this.posX, this.posY, this.posZ).expand(radius, radius, radius);

			List<Entity> bugs = worldObj.getEntitiesWithinAABBExcludingEntity(this, bb);
			for (Entity e: bugs){
				if(e instanceof EntityGlyphid){
					addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 20, 6));
					addPotionEffect(new PotionEffect(Potion.fireResistance.id, 15 * 20, 1));
				}
			}
		}
		if(this.deathTicks == 100) {
			
			if(!worldObj.isRemote) {
				ExplosionVNT vnt = new ExplosionVNT(worldObj, posX, posY, posZ, 25, this);

				if(this.dataWatcher.getWatchableObjectByte(DW_SUBTYPE) == TYPE_INFECTED) {
					int j = 15 + this.rand.nextInt(6);
					for(int k = 0; k < j; ++k) {
						float f = ((float) (k % 2) - 0.5F) * 0.5F;
						float f1 = ((float) (k / 2) - 0.5F) * 0.5F;
						EntityParasiteMaggot maggot = new EntityParasiteMaggot(worldObj);
						maggot.setLocationAndAngles(this.posX + (double) f, this.posY + 0.5D, this.posZ + (double) f1, this.rand.nextFloat() * 360.0F, 0.0F);
						maggot.motionX = f;
						maggot.motionZ = f1;
						maggot.velocityChanged = true;
						this.worldObj.spawnEntityInWorld(maggot);
					}
				} else {
					vnt.setBlockAllocator(new BlockAllocatorStandard(24));
					vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(new BlockMutatorDebris(ModBlocks.volcanic_lava_block, 0)).setNoDrop());
				}
				
				vnt.setEntityProcessor(new EntityProcessorStandard());
				vnt.setPlayerProcessor(new PlayerProcessorStandard());
				vnt.explode();

				worldObj.playSoundEffect(posX, posY, posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);
	
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "muke");
				// if the FX type is "muke", apply random BF effect
				if(MainRegistry.polaroidID == 11 || rand.nextInt(100) == 0) {
					data.setBoolean("balefire", true);
				}
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(dimension, posX, posY, posZ, 250));
			}
			
			this.setDead();
		} else {
			if(!worldObj.isRemote && this.deathTicks % 10 == 0) {
				worldObj.playSoundEffect(posX, posY, posZ, "hbm:weapon.fstbmbPing", 5.0F, 1.0F);
			}
		}
	}
}
