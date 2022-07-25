package com.hbm.entity.mob;

import java.util.List;

import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.mob.ai.EntityAINuclearCreeperSwell;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityNuclearCreeper extends EntityMob {

	private int lastActiveTime;
	private int timeSinceIgnited;
	private int fuseTime = 75;

	public EntityNuclearCreeper(World p_i1733_1_) {
		super(p_i1733_1_);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAINuclearCreeperSwell(this));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, false));
		this.tasks.addTask(4, new EntityAIWander(this, 0.8D));
		this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityOcelot.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

		if(source == ModDamageSource.radiation || source == ModDamageSource.mudPoisoning) {
			this.heal(amount);
			return false;
		}

		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean isAIEnabled() {
		return true;
	}

	@Override
	public int getMaxSafePointTries() {
		return this.getAttackTarget() == null ? 3 : 3 + (int) (this.getHealth() - 1.0F);
	}

	@Override
	protected void fall(float p_70069_1_) {
		super.fall(p_70069_1_);
		this.timeSinceIgnited = (int) (this.timeSinceIgnited + p_70069_1_ * 1.5F);

		if(this.timeSinceIgnited > this.fuseTime - 5) {
			this.timeSinceIgnited = this.fuseTime - 5;
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte) -1));
		this.dataWatcher.addObject(17, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(18, Byte.valueOf((byte) 0));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		super.writeEntityToNBT(p_70014_1_);

		if(this.dataWatcher.getWatchableObjectByte(17) == 1) {
			p_70014_1_.setBoolean("powered", true);
		}

		p_70014_1_.setShort("Fuse", (short) this.fuseTime);
		p_70014_1_.setBoolean("ignited", this.func_146078_ca());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		super.readEntityFromNBT(p_70037_1_);
		this.dataWatcher.updateObject(17, Byte.valueOf((byte) (p_70037_1_.getBoolean("powered") ? 1 : 0)));

		if(p_70037_1_.hasKey("Fuse", 99)) {
			this.fuseTime = p_70037_1_.getShort("Fuse");
		}

		if(p_70037_1_.getBoolean("ignited")) {
			this.func_146079_cb();
		}
	}

	@Override
	public void onUpdate() {
		if(this.isDead) {
			this.isDead = false;
			this.heal(10.0F);
		}

		if(this.isEntityAlive()) {
			this.lastActiveTime = this.timeSinceIgnited;

			if(this.func_146078_ca()) {
				this.setCreeperState(1);
			}

			int i = this.getCreeperState();

			if(i > 0 && this.timeSinceIgnited == 0) {
				this.playSound("creeper.primed", 1.0F * 30 / 75, 0.5F);
			}

			this.timeSinceIgnited += i;

			if(this.timeSinceIgnited < 0) {
				this.timeSinceIgnited = 0;
			}

			if(this.timeSinceIgnited >= this.fuseTime) {
				this.timeSinceIgnited = this.fuseTime;
				this.func_146077_cc();
			}
		}

		List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(posX - 5, posY - 5, posZ - 5, posX + 5, posY + 5, posZ + 5));

		for(Entity e : list)
			if(e instanceof EntityLivingBase) {
				ContaminationUtil.contaminate((EntityLivingBase) e, HazardType.RADIATION, ContaminationType.CREATIVE, 0.25F);
			}

		super.onUpdate();

		if(this.getHealth() < this.getMaxHealth() && this.ticksExisted % 10 == 0) {
			this.heal(1.0F);
		}
	}

	@Override
	protected String getHurtSound() {
		return "mob.creeper.say";
	}

	@Override
	protected String getDeathSound() {
		return "mob.creeper.death";
	}

	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);

		List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50, 50, 50));

		for(EntityPlayer player : players) {
			player.triggerAchievement(MainRegistry.bossCreeper);
		}

		if(p_70645_1_.getEntity() instanceof EntitySkeleton || (p_70645_1_.isProjectile() && p_70645_1_.getEntity() instanceof EntityArrow && ((EntityArrow) (p_70645_1_.getEntity())).shootingEntity == null)) {
			int i = rand.nextInt(11);
			int j = rand.nextInt(3);
			if(i == 0)
				this.dropItem(ModItems.nugget_u235, j);
			if(i == 1)
				this.dropItem(ModItems.nugget_pu238, j);
			if(i == 2)
				this.dropItem(ModItems.nugget_pu239, j);
			if(i == 3)
				this.dropItem(ModItems.nugget_neptunium, j);
			if(i == 4)
				this.dropItem(ModItems.man_core, 1);
			if(i == 5) {
				this.dropItem(ModItems.sulfur, j * 2);
				this.dropItem(ModItems.niter, j * 2);
			}
			if(i == 6)
				this.dropItem(ModItems.syringe_awesome, 1);
			if(i == 7)
				this.dropItem(ModItems.fusion_core, 1);
			if(i == 8)
				this.dropItem(ModItems.syringe_metal_stimpak, 1);
			if(i == 9) {
				switch(rand.nextInt(4)) {
				case 0:
					this.dropItem(ModItems.t45_helmet, 1);
					break;
				case 1:
					this.dropItem(ModItems.t45_plate, 1);
					break;
				case 2:
					this.dropItem(ModItems.t45_legs, 1);
					break;
				case 3:
					this.dropItem(ModItems.t45_boots, 1);
					break;
				}
				this.dropItem(ModItems.fusion_core, 1);
			}
			if(i == 10)
				this.dropItem(ModItems.ammo_nuke_high, 1);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity p_70652_1_) {
		return true;
	}

	public boolean getPowered() {
		return this.dataWatcher.getWatchableObjectByte(17) == 1;
	}

	@SideOnly(Side.CLIENT)
	public float getCreeperFlashIntensity(float p_70831_1_) {
		return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
	}

	@Override
	protected Item getDropItem() {
		return Item.getItemFromBlock(Blocks.tnt);
	}

	@Override
	protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {

		super.dropFewItems(p_70628_1_, p_70628_2_);

		if(rand.nextInt(3) == 0)
			this.dropItem(ModItems.coin_creeper, 1);
	}

	public int getCreeperState() {
		return this.dataWatcher.getWatchableObjectByte(16);
	}

	public void setCreeperState(int p_70829_1_) {
		this.dataWatcher.updateObject(16, Byte.valueOf((byte) p_70829_1_));
	}

	@Override
	public void onStruckByLightning(EntityLightningBolt p_70077_1_) {
		super.onStruckByLightning(p_70077_1_);
		this.dataWatcher.updateObject(17, Byte.valueOf((byte) 1));
	}

	@Override
	protected boolean interact(EntityPlayer p_70085_1_) {
		ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();

		if(itemstack != null && itemstack.getItem() == Items.flint_and_steel) {
			this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
			p_70085_1_.swingItem();

			if(!this.worldObj.isRemote) {
				this.func_146079_cb();
				itemstack.damageItem(1, p_70085_1_);
				return true;
			}
		}

		return super.interact(p_70085_1_);
	}

	private void func_146077_cc() {
		if(!this.worldObj.isRemote) {
			boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

			if(this.getPowered()) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "muke");
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, posX, posY + 0.5, posZ), new TargetPoint(dimension, posX, posY, posZ, 250));
				worldObj.playSoundEffect(posX, posY + 0.5, posZ, "hbm:weapon.mukeExplosion", 15.0F, 1.0F);

				if(flag) {
					worldObj.spawnEntityInWorld(EntityNukeExplosionMK4.statFac(worldObj, 50, posX, posY, posZ).mute());
				} else {
					ExplosionNukeGeneric.dealDamage(worldObj, posX, posY + 0.5, posZ, 100);
				}
			} else {

				if(flag) {
					ExplosionNukeSmall.explode(worldObj, posX, posY + 0.5, posZ, ExplosionNukeSmall.medium);
				} else {
					ExplosionNukeSmall.explode(worldObj, posX, posY + 0.5, posZ, ExplosionNukeSmall.safe);
				}
			}

			this.setDead();
		}
	}

	public boolean func_146078_ca() {
		return this.dataWatcher.getWatchableObjectByte(18) != 0;
	}

	public void func_146079_cb() {
		this.dataWatcher.updateObject(18, Byte.valueOf((byte) 1));
	}

	public void setPowered(int power) {
		this.dataWatcher.updateObject(17, power);
	}
}
