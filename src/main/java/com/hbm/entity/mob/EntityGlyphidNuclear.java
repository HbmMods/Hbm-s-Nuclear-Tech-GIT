package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.effect.EntityMist;
import com.hbm.entity.logic.EntityWaypoint;
import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockMutatorDebris;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;

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
	public void onUpdate() {
		super.onUpdate();
		if (ticksExisted % 20 == 0) {
			if (isAtDestination() && getCurrentTask() == 4) {
				setCurrentTask(0, null);
			}

			if(getCurrentTask() == 2 && getAITarget() == null){
				this.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 10 * 20, 3));
			}

			if (getCurrentTask() == 5) {
				this.setHealth(0);
			}

		}
	}

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
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.8D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50D);
	}

	@Override
	public boolean isArmorBroken(float amount) {
		// amount < 5 ? 5 : amount < 10 ? 3 : 2;
		return this.rand.nextInt(100) <= Math.min(Math.pow(amount * 0.12, 2), 100);
	}

	@Override
	public float calculateDamage(float amount) {

		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		int divisor = 1;
		
		for(int i = 0; i < 5; i++) {
			if((armor & (1 << i)) > 0) {
				divisor += 5;
			}
		}
		
		amount /= divisor;
		
		return amount;
	}

	@Override
	public float getDamageThreshold() {
		return 10F;
	}

	public boolean hasWaypoint = false;
	@Override
	protected void onDeathUpdate() {
		++this.deathTicks;

		if(!hasWaypoint) {
			communicate(3, null);
			hasWaypoint = true;
		}
		if(deathTicks == 90){
			int radius = 8;
			AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(
					this.posX - radius,
					this.posY - radius,
					this.posZ - radius,
					this.posX + radius,
					this.posY + radius,
					this.posZ + radius);

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
				vnt.setBlockAllocator(new BlockAllocatorStandard(24));
				vnt.setBlockProcessor(new BlockProcessorStandard().withBlockEffect(new BlockMutatorDebris(ModBlocks.volcanic_lava_block, 0)).setNoDrop());
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
