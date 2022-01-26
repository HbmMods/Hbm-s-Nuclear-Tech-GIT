package com.hbm.entity.mob.siege;

import com.hbm.entity.mob.EntityUFOBase;
import com.hbm.entity.projectile.EntitySiegeLaser;
import com.hbm.items.ModItems;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntitySiegeCraft extends EntityUFOBase implements IBossDisplayData {
	
	private int attackCooldown;

	public EntitySiegeCraft(World world) {
		super(world);
		this.setSize(7F, 1F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataWatcher().addObject(12, (int) 0);
	}
	
	public void setTier(SiegeTier tier) {
		this.getDataWatcher().updateObject(12, tier.id);

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(tier.speedMod);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(tier.health * 25);
		this.setHealth(this.getMaxHealth());
	}
	
	public SiegeTier getTier() {
		SiegeTier tier = SiegeTier.tiers[this.getDataWatcher().getWatchableObjectInt(12)];
		return tier != null ? tier : SiegeTier.CLAY;
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();

		if(this.courseChangeCooldown > 0) {
			this.courseChangeCooldown--;
		}
		if(this.scanCooldown > 0) {
			this.scanCooldown--;
		}
		
		if(!worldObj.isRemote) {
			if(this.attackCooldown > 0) {
				this.attackCooldown--;
			}
			
			if(this.attackCooldown == 0 && this.target != null) {
				this.attackCooldown = 20 + rand.nextInt(5);
				
				double x = posX;
				double y = posY;
				double z = posZ;
				
				Vec3 vec = Vec3.createVectorHelper(target.posX - x, target.posY + target.height * 0.5 - y, target.posZ - z).normalize();
				SiegeTier tier = this.getTier();
				
				EntitySiegeLaser laser = new EntitySiegeLaser(worldObj, this);
				laser.setPosition(x, y, z);
				laser.setThrowableHeading(vec.xCoord, vec.yCoord, vec.zCoord, 1F, 0.15F);
				laser.setColor(0x808080);
				laser.setDamage(tier.damageMod);
				laser.setExplosive(tier.laserExplosive);
				laser.setBreakChance(tier.laserBreak);
				if(tier.laserIncendiary) laser.setIncendiary();
				worldObj.spawnEntityInWorld(laser);
				this.playSound("hbm:weapon.ballsLaser", 2.0F, 1.0F);
			}
		}
		
		if(this.courseChangeCooldown > 0) {
			approachPosition(this.target == null ? 0.25D : 0.5D + this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() * 1);
		}
	}

	@Override
	protected void setCourseWithoutTaget() {
		int x = (int) Math.floor(posX + rand.nextGaussian() * 15);
		int z = (int) Math.floor(posZ + rand.nextGaussian() * 15);
		this.setWaypoint(x, this.worldObj.getHeightValue(x, z) + 5 + rand.nextInt(6),  z);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("siegeTier", this.getTier().id);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setTier(SiegeTier.tiers[nbt.getInteger("siegeTier")]);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
		this.setTier(SiegeTier.tiers[rand.nextInt(SiegeTier.getLength())]);
		return super.onSpawnWithEgg(data);
	}

	@Override
	protected void dropFewItems(boolean byPlayer, int fortune) {
		
		if(byPlayer) {
			for(ItemStack drop : this.getTier().dropItem) {
				this.entityDropItem(drop.copy(), 0F);
			}
			
			this.entityDropItem(new ItemStack(ModItems.source, 50), 0F);
		}
	}
}
