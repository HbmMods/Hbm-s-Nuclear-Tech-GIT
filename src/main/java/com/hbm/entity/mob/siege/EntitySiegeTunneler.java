package com.hbm.entity.mob.siege;

import com.hbm.entity.mob.EntityBurrowingSwingingBase;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

//cursed code ahead
//no, this time it's vanilla that is cursed
//for NO FUCKING REASON IN PARTICULAR, EntityCreatue mobs aren't synchronized to clients AT ALL
//yet EntityMob that inherits EntityCreature and adds ABSOLUTELY FUCKING NOTHING other than base functionality works
//why?
//is that some sort of elaborate prank i'm not smart enough to understand?
//well it ain't fucking funny
//this stupid fucking random ass bullshit is the P R E C I S E reason i loathe working with entities
//honest to fucking god was the entire mojang dev team on crack when they wrote this?
public class EntitySiegeTunneler extends EntityBurrowingSwingingBase {

	public EntitySiegeTunneler(World world) {
		super(world);
		this.tasks.addTask(0, new EntityAISwimming(this));
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataWatcher().addObject(12, (int) 0);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23D);
	}
	
	public void setTier(SiegeTier tier) {
		this.getDataWatcher().updateObject(12, tier.id);

		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(new AttributeModifier("Tier Speed Mod", tier.speedMod, 1));
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(tier.health);
		this.setHealth(this.getMaxHealth());
	}
	
	public SiegeTier getTier() {
		SiegeTier tier = SiegeTier.tiers[this.getDataWatcher().getWatchableObjectInt(12)];
		return tier != null ? tier : SiegeTier.CLAY;
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
}
