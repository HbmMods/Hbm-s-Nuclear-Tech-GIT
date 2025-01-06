package com.hbm.entity.mob;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.MobConfig;
import com.hbm.entity.mob.ai.EntityAIBreaking;
import com.hbm.entity.pathfinder.PathFinderUtils;
import com.hbm.entity.projectile.EntityBullet;
import com.hbm.items.ModItems;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityFBI extends EntityMob implements IRangedAttackMob {

	public EntityFBI(World world) {
		super(world);
        this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreaking(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1D, 20, 25, 15.0F));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        //this.tasks.addTask(6, new EntityAI_MLPF(this, EntityPlayer.class, 100, 1D, 16));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, false));
        this.setSize(0.6F, 1.8F);
        
        this.isImmuneToFire = true;
	}

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }
    
    public boolean attackEntityFrom(DamageSource source, float amount) {
    	
    	if(source instanceof EntityDamageSourceIndirect && ((EntityDamageSourceIndirect)source).getEntity() instanceof EntityFBI) {
    		return false;
    	}

    	if(this.getEquipmentInSlot(4) != null && this.getEquipmentInSlot(4).getItem() == Item.getItemFromBlock(Blocks.glass)) {
	    	if("oxygenSuffocation".equals(source.damageType))
	    		return false;
	    	if("thermal".equals(source.damageType))
	    		return false;
    	}
    	
    	return super.attackEntityFrom(source, amount);
    }

    protected void entityInit() {
        super.entityInit();
    }
    
    protected boolean canDespawn() {
        return false;
    }
	
    protected void addRandomArmor() {
        //super.addRandomArmor();
        
        int equip = rand.nextInt(2);
        
        switch(equip) {
        case 0: this.setCurrentItemOrArmor(0, new ItemStack(ModItems.gun_heavy_revolver)); break;
        case 1: this.setCurrentItemOrArmor(0, new ItemStack(ModItems.gun_spas12)); break;
        }
        
        if(rand.nextInt(5) == 0) {
        	this.setCurrentItemOrArmor(4, new ItemStack(ModItems.security_helmet));
        	this.setCurrentItemOrArmor(3, new ItemStack(ModItems.security_plate));
        	this.setCurrentItemOrArmor(2, new ItemStack(ModItems.security_legs));
        	this.setCurrentItemOrArmor(1, new ItemStack(ModItems.security_boots));
        }
        
        if(this.worldObj != null && this.worldObj.provider.dimensionId != 0) {
        	this.setCurrentItemOrArmor(4, new ItemStack(Blocks.glass));
        	this.setCurrentItemOrArmor(3, new ItemStack(ModItems.paa_plate));
        	this.setCurrentItemOrArmor(2, new ItemStack(ModItems.paa_legs));
        	this.setCurrentItemOrArmor(1, new ItemStack(ModItems.paa_boots));
        }
    }
    
    protected boolean isAIEnabled() {
        return true;
    }

	@Override
	protected void updateAITasks() {
		super.updateAITasks();

		if(this.getAttackTarget() == null) {
			this.setAttackTarget(this.worldObj.getClosestVulnerablePlayerToEntity(this, 128.0D));
		}

		// hell yeah!!
		if(this.getAttackTarget() != null) {
			this.getNavigator().setPath(PathFinderUtils.getPathEntityToEntityPartial(worldObj, this, this.getAttackTarget(), 16F, true, false, false, true), 1);
		}
	}
    
    //combat vest = full diamond set
    public int getTotalArmorValue() {
    	return 20;
    }

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase entity, float f) {

		if(this.getEquipmentInSlot(0) != null) {
			if(this.getEquipmentInSlot(0).getItem() == ModItems.gun_heavy_revolver) {
				EntityBullet bullet = new EntityBullet(worldObj, this, entity, 3F, 2);
				bullet.damage = 10;
		        this.worldObj.spawnEntityInWorld(bullet);
		        this.playSound("hbm:weapon.revolverShootAlt", 1.0F, 1.0F);
			}

			if(this.getEquipmentInSlot(0).getItem() == ModItems.gun_spas12) {
				for(int i = 0; i < 7; i++) {
					EntityBullet bullet = new EntityBullet(worldObj, this, entity, 3F, 5);
					bullet.damage = 3;
			        this.worldObj.spawnEntityInWorld(bullet);
				}
		        this.playSound("hbm:weapon.shotgunShoot", 1.0F, 1.0F);
			}
		}
	}
	
	private static final Set<Block> canDestroy = new HashSet();
	
	static {
		canDestroy.add(Blocks.wooden_door);
		canDestroy.add(Blocks.iron_door);
		canDestroy.add(Blocks.trapdoor);
		canDestroy.add(ModBlocks.machine_press);
		canDestroy.add(ModBlocks.machine_epress);
		canDestroy.add(ModBlocks.machine_chemplant);
		canDestroy.add(ModBlocks.machine_crystallizer);
		canDestroy.add(ModBlocks.machine_turbine);
		canDestroy.add(ModBlocks.machine_large_turbine);
		canDestroy.add(ModBlocks.crate_iron);
		canDestroy.add(ModBlocks.crate_steel);
		canDestroy.add(ModBlocks.machine_diesel);
		canDestroy.add(ModBlocks.machine_rtg_grey);
		canDestroy.add(ModBlocks.machine_minirtg);
		canDestroy.add(ModBlocks.machine_powerrtg);
		canDestroy.add(ModBlocks.machine_cyclotron);
		canDestroy.add(Blocks.chest);
		canDestroy.add(Blocks.trapped_chest);
	}

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
    	this.addRandomArmor();
    	return super.onSpawnWithEgg(data);
    }

    public boolean isPotionApplicable(PotionEffect potion)
    {
    	if(this.getEquipmentInSlot(4) == null)
           	this.setCurrentItemOrArmor(4, new ItemStack(ModItems.gas_mask_m65));
    	
    	return false;
    }
	
    public void onLivingUpdate() {
    	super.onLivingUpdate();
    	
    	if(worldObj.isRemote || this.getHealth() <= 0)
    		return;
    	
    	if(this.ticksExisted % MobConfig.raidAttackDelay == 0) {
    		Vec3 vec = Vec3.createVectorHelper(MobConfig.raidAttackReach, 0, 0);
    		vec.rotateAroundY((float)(Math.PI * 2) * rand.nextFloat());
    		
            Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY + 0.5 + rand.nextFloat(), this.posZ);
            Vec3 vec31 = Vec3.createVectorHelper(vec3.xCoord + vec.xCoord, vec3.yCoord + vec.yCoord, vec3.zCoord + vec.zCoord);
            MovingObjectPosition mop = this.worldObj.func_147447_a(vec3, vec31, false, true, false);
            
            if(mop != null && mop.typeOfHit == MovingObjectType.BLOCK) {
            	
            	if(canDestroy.contains(worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ)))
            		worldObj.func_147480_a(mop.blockX, mop.blockY, mop.blockZ, false);
            }
    	}
    	
    	double range = 1.5;
    	
    	List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX, posY, posZ).expand(range, range, range));
    	
    	for(EntityItem item : items)
    		item.setFire(10);
    }
}
