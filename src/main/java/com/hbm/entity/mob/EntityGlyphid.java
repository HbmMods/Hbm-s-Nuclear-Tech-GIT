package com.hbm.entity.mob;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.hbm.entity.pathfinder.PathFinderUtils;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.ResourceManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphid extends EntityMob {

	public EntityGlyphid(World world) {
		super(world);
		/*this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));*/
		this.setSize(1.75F, 1F);
	}
	
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_tex;
	}
	
	public double getScale() {
		return 1.0D;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(16, new Byte((byte) 0)); //wall climbing
		this.dataWatcher.addObject(17, new Byte((byte) 0b11111)); //armor
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5D);
	}
	
	@Override
	protected void dropFewItems(boolean byPlayer, int looting) {
		if(rand.nextInt(3) == 0) this.entityDropItem(new ItemStack(ModItems.glyphid_meat, 1 + rand.nextInt(2) + looting), 0F);
	}

	@Override
	protected Entity findPlayerToAttack() {
		EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 128.0D);
		return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
	}

	@Override
	protected void updateEntityActionState() {
		super.updateEntityActionState();

		// hell yeah!!
		if(this.entityToAttack != null && !this.hasPath()) {
			this.setPathToEntity(PathFinderUtils.getPathEntityToEntityPartial(worldObj, this, this.entityToAttack, 16F, true, false, false, true));
		}
	}

	@Override
	protected boolean canDespawn() {
		return entityToAttack == null;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(!source.isDamageAbsolute() && !source.isUnblockable() && !worldObj.isRemote && !source.isFireDamage() && !source.getDamageType().equals(ModDamageSource.s_cryolator)) {
			byte armor = this.dataWatcher.getWatchableObjectByte(17);
			
			if(armor != 0) { //if at least one bit of armor is present
				
				if(amount < getDamageThreshold()) return false;
				
				int chance = getArmorBreakChance(amount); //chances of armor being broken off
				if(this.rand.nextInt(chance) == 0 && amount > 1) {
					breakOffArmor();
					amount *= 0.25F;
				}
				
				amount -= getDamageThreshold();
				if(amount < 0) return true;
			}
			
			amount = this.calculateDamage(amount);
		}
		
		if(source.isFireDamage()) amount *= 4F;
		
		return super.attackEntityFrom(source, amount);
	}
	
	public int getArmorBreakChance(float amount) {
		return amount < 10 ? 5 : amount < 20 ? 3 : 2;
	}
	
	public float calculateDamage(float amount) {

		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		int divisor = 1;
		
		for(int i = 0; i < 5; i++) {
			if((armor & (1 << i)) > 0) {
				divisor++;
			}
		}
		
		amount /= divisor;
		
		return amount;
	}
	
	public float getDamageThreshold() {
		return 0.5F;
	}
	
	public void breakOffArmor() {
		byte armor = this.dataWatcher.getWatchableObjectByte(17);
		List<Integer> indices = Arrays.asList(0, 1, 2, 3, 4);
		Collections.shuffle(indices);
		
		for(Integer i : indices) {
			byte bit = (byte) (1 << i);
			if((armor & bit) > 0) {
				armor &= ~bit;
				armor = (byte) (armor & 0b11111);
				this.dataWatcher.updateObject(17, armor);
				worldObj.playSoundAtEntity(this, "mob.zombie.woodbreak", 1.0F, 1.25F);
				break;
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity victum) {
		if(this.isSwingInProgress) return false;
		this.swingItem();
		return super.attackEntityAsMob(victum);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!this.worldObj.isRemote) {
			this.setBesideClimbableBlock(this.isCollidedHorizontally);
			
			if(worldObj.getTotalWorldTime() % 200 == 0) {
				this.swingItem();
			}
		}
	}

	@Override
	protected void updateArmSwingProgress() {
		int i = this.swingDuration();

		if(this.isSwingInProgress) {
			++this.swingProgressInt;

			if(this.swingProgressInt >= i) {
				this.swingProgressInt = 0;
				this.isSwingInProgress = false;
			}
		} else {
			this.swingProgressInt = 0;
		}

		this.swingProgress = (float) this.swingProgressInt / (float) i;
	}
	
	public int swingDuration() {
		return 15;
	}

	@Override
	public void setInWeb() { }
	
	@Override
	public boolean isOnLadder() {
		return this.isBesideClimbableBlock();
	}
	
	public boolean isBesideClimbableBlock() {
		return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setBesideClimbableBlock(boolean climbable) {
		byte watchable = this.dataWatcher.getWatchableObjectByte(16);

		if(climbable) {
			watchable = (byte) (watchable | 1);
		} else {
			watchable &= -2;
		}

		this.dataWatcher.updateObject(16, Byte.valueOf(watchable));
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setByte("armor", this.dataWatcher.getWatchableObjectByte(17));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(17, nbt.getByte("armor"));
	}
}
