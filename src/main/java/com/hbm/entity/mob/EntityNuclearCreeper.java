package com.hbm.entity.mob;

import java.util.HashSet;
import java.util.List;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.explosion.ExplosionParticle;
import com.hbm.explosion.ExplosionParticleB;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;

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
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityNuclearCreeper extends EntityMob {
    /**
     * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
     * weird)
     */
    private int lastActiveTime;
    /** The amount of time since the creeper was close enough to the player to ignite */
    private int timeSinceIgnited;
    private int fuseTime = 75;
    /** Explosion radius for this creeper. */
    private int explosionRadius = 20;
    private static final String __OBFID = "CL_00001684";

    public EntityNuclearCreeper(World p_i1733_1_)
    {
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
	protected void applyEntityAttributes()
    {
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

    /**
     * Returns true if the newer Entity AI code should be run
     */
    @Override
	public boolean isAIEnabled()
    {
        return true;
    }

    /**
     * The number of iterations PathFinder.getSafePoint will execute before giving up.
     */
    @Override
	public int getMaxSafePointTries()
    {
        return this.getAttackTarget() == null ? 3 : 3 + (int)(this.getHealth() - 1.0F);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    @Override
	protected void fall(float p_70069_1_)
    {
        super.fall(p_70069_1_);
        this.timeSinceIgnited = (int)(this.timeSinceIgnited + p_70069_1_ * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5)
        {
            this.timeSinceIgnited = this.fuseTime - 5;
        }
    }

    @Override
	protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) - 1));
        this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
        this.dataWatcher.addObject(18, Byte.valueOf((byte)0));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        super.writeEntityToNBT(p_70014_1_);

        if (this.dataWatcher.getWatchableObjectByte(17) == 1)
        {
            p_70014_1_.setBoolean("powered", true);
        }

        p_70014_1_.setShort("Fuse", (short)this.fuseTime);
        p_70014_1_.setByte("ExplosionRadius", (byte)this.explosionRadius);
        p_70014_1_.setBoolean("ignited", this.func_146078_ca());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        super.readEntityFromNBT(p_70037_1_);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte)(p_70037_1_.getBoolean("powered") ? 1 : 0)));

        if (p_70037_1_.hasKey("Fuse", 99))
        {
            this.fuseTime = p_70037_1_.getShort("Fuse");
        }

        if (p_70037_1_.hasKey("ExplosionRadius", 99))
        {
            this.explosionRadius = p_70037_1_.getByte("ExplosionRadius");
        }

        if (p_70037_1_.getBoolean("ignited"))
        {
            this.func_146079_cb();
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
	public void onUpdate()
    {
    	if(this.isDead)
    	{
    		this.isDead = false;
    		this.heal(10.0F);
    	}
    	
        if (this.isEntityAlive())
        {
            this.lastActiveTime = this.timeSinceIgnited;

            if (this.func_146078_ca())
            {
                this.setCreeperState(1);
            }

            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
            {
                this.playSound("creeper.primed", 1.0F * 30 / 75, 0.5F);
            }

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
            {
                this.timeSinceIgnited = 0;
            }

            if (this.timeSinceIgnited >= this.fuseTime)
            {
                this.timeSinceIgnited = this.fuseTime;
                this.func_146077_cc();
            }
        }
		int strength = 1;
		float f = strength;
        HashSet hashset = new HashSet();
        int i;
        int j;
        int k;
        double d5;
        double d6;
        double d7;
        double wat = f*2;
        boolean isOccupied = false;
        

        strength *= 2.0F;
        i = MathHelper.floor_double(this.posX - wat - 1.0D);
        j = MathHelper.floor_double(this.posX + wat + 1.0D);
        k = MathHelper.floor_double(this.posY - wat - 1.0D);
        int i2 = MathHelper.floor_double(this.posY + wat + 1.0D);
        int l = MathHelper.floor_double(this.posZ - wat - 1.0D);
        int j2 = MathHelper.floor_double(this.posZ + wat + 1.0D);
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);

        for (int i1 = 0; i1 < list.size(); ++i1)
        {
            Entity entity = (Entity)list.get(i1);
            double d4 = entity.getDistance(this.posX, this.posY, this.posZ) / 4;

            if (d4 <= 1.0D)
            {
                d5 = entity.posX - this.posX;
                d6 = entity.posY + entity.getEyeHeight() - this.posY;
                d7 = entity.posZ - this.posZ;
                double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);
                if (d9 < wat)
                {
                	if(entity instanceof EntityPlayer && Library.checkForHazmat((EntityPlayer)entity))
                	{
                		/*Library.damageSuit(((EntityPlayer)entity), 0);
                		Library.damageSuit(((EntityPlayer)entity), 1);
                		Library.damageSuit(((EntityPlayer)entity), 2);
                		Library.damageSuit(((EntityPlayer)entity), 3);*/
                		
                	} else if(entity instanceof EntityCreeper) {
                		EntityNuclearCreeper creep = new EntityNuclearCreeper(this.worldObj);
                		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                		//creep.setRotationYawHead(((EntityCreeper)entity).rotationYawHead);
                		if(!entity.isDead)
                			if(!worldObj.isRemote)
                				worldObj.spawnEntityInWorld(creep);
                		entity.setDead();
                	} else if(entity instanceof EntityVillager) {
                		EntityZombie creep = new EntityZombie(this.worldObj);
                		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                		entity.setDead();
                		if(!this.worldObj.isRemote)
                		this.worldObj.spawnEntityInWorld(creep);
                	} else if(entity instanceof EntityLivingBase && !(entity instanceof EntityNuclearCreeper) && !(entity instanceof EntityMooshroom) && !(entity instanceof EntityZombie))
                    {
                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.getId(), 5 * 20, 1));
                    	((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 15 * 20, 0));
                    }
                }
            }
        }

        strength = (int)f;

        super.onUpdate();
        
        if(this.getHealth() < this.getMaxHealth() && this.ticksExisted % 10 == 0)
        {
        	this.heal(1.0F);
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    @Override
	protected String getHurtSound()
    {
        return "mob.creeper.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    @Override
	protected String getDeathSound()
    {
        return "mob.creeper.death";
    }

    /**
     * Called when the mob's health reaches 0.
     */
    @Override
	public void onDeath(DamageSource p_70645_1_)
    {
        super.onDeath(p_70645_1_);

        if (p_70645_1_.getEntity() instanceof EntitySkeleton || (p_70645_1_.isProjectile() && p_70645_1_.getEntity() instanceof EntityArrow && ((EntityArrow)(p_70645_1_.getEntity())).shootingEntity == null))
        {
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
        	if(i == 5)
        	{
        		this.dropItem(ModItems.sulfur, j * 2);
        		this.dropItem(ModItems.niter, j * 2);
        	}
        	if(i == 6)
        		this.dropItem(ModItems.syringe_awesome, 1);
        	if(i == 7)
        		this.dropItem(ModItems.fusion_core, 1);
        	if(i == 8)
        		this.dropItem(ModItems.syringe_metal_stimpak, 1);
        	if(i == 9)
        	{
        		switch(rand.nextInt(4))
        		{
        		case 0: this.dropItem(ModItems.t45_helmet, 1); break;
        		case 1: this.dropItem(ModItems.t45_plate, 1); break;
        		case 2: this.dropItem(ModItems.t45_legs, 1); break;
        		case 3: this.dropItem(ModItems.t45_boots, 1); break;
        		}
        		this.dropItem(ModItems.fusion_core, 1);
        	}
        	if(i == 10)
        		this.dropItem(ModItems.gun_fatman_ammo, 1);
        }
    }

    @Override
	public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        return true;
    }

    /**
     * Returns true if the creeper is powered by a lightning bolt.
     */
    public boolean getPowered()
    {
        return this.dataWatcher.getWatchableObjectByte(17) == 1;
    }

    /**
     * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
     */
    @SideOnly(Side.CLIENT)
    public float getCreeperFlashIntensity(float p_70831_1_)
    {
        return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
    }

    @Override
	protected Item getDropItem()
    {
        return Item.getItemFromBlock(Blocks.tnt);
    }

    /**
     * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
     */
    public int getCreeperState()
    {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    /**
     * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
     */
    public void setCreeperState(int p_70829_1_)
    {
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)p_70829_1_));
    }

    /**
     * Called when a lightning bolt hits the entity.
     */
    @Override
	public void onStruckByLightning(EntityLightningBolt p_70077_1_)
    {
        super.onStruckByLightning(p_70077_1_);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    @Override
	protected boolean interact(EntityPlayer p_70085_1_)
    {
        ItemStack itemstack = p_70085_1_.inventory.getCurrentItem();

        if (itemstack != null && itemstack.getItem() == Items.flint_and_steel)
        {
            this.worldObj.playSoundEffect(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, "fire.ignite", 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
            p_70085_1_.swingItem();

            if (!this.worldObj.isRemote)
            {
                this.func_146079_cb();
                itemstack.damageItem(1, p_70085_1_);
                return true;
            }
        }

        return super.interact(p_70085_1_);
    }

    private void func_146077_cc()
    {
        if (!this.worldObj.isRemote)
        {
            boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

            if (this.getPowered())
            {
                //this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, (float)(this.explosionRadius * 2), flag);
            	this.explosionRadius *= 3;
            }
            
            EntityNukeExplosionMK3 explosion = new EntityNukeExplosionMK3(this.worldObj);
            explosion.speed = 25;
            explosion.coefficient = 5.0F;
            explosion.destructionRange = this.explosionRadius;
            explosion.posX = this.posX;
            explosion.posY = this.posY;
            explosion.posZ = this.posZ;
            this.worldObj.spawnEntityInWorld(explosion);
            
            if(this.getPowered())
            {
    			EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(this.worldObj, 1000);
    	    	entity2.posX = this.posX;
    	    	entity2.posY = this.posY - 11;
    	    	entity2.posZ = this.posZ;
    	    	this.worldObj.spawnEntityInWorld(entity2);
            } else {
            	if(rand.nextInt(100) == 0)
            	{
            		ExplosionParticleB.spawnMush(this.worldObj, (int)this.posX, (int)this.posY - 3, (int)this.posZ);
            	} else {
            		ExplosionParticle.spawnMush(this.worldObj, (int)this.posX, (int)this.posY - 3, (int)this.posZ);
            	}
            }

            this.setDead();
        }
    }

    public boolean func_146078_ca()
    {
        return this.dataWatcher.getWatchableObjectByte(18) != 0;
    }

    public void func_146079_cb()
    {
        this.dataWatcher.updateObject(18, Byte.valueOf((byte)1));
    }
    
    public void setPowered(int power) {
        this.dataWatcher.updateObject(17, power);
    }
}
