package com.hbm.entity.projectile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeSmall;
import com.hbm.handler.radiation.ChunkRadiationManager;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRocketHoming extends Entity implements IProjectile
{
    private int field_145791_d = -1;
    private int field_145792_e = -1;
    private int field_145789_f = -1;
    public double gravity = 0.0D;
    private Block field_145790_g;
    private int inData;
    private boolean inGround;
    public int canBePickedUp;
    public int arrowShake;
    public Entity shootingEntity;
    private int ticksInGround;
    private int ticksInAir;
    private double damage = 2.0D;
    private int knockbackStrength;
    private float explosionStrength;

    // specifies the type of stinger rocket that was fired
    /*  0  =  Normal
     *  1  =  HE
     *  2  =  Incendiary
     *  4  =  Nuclear
     *  42 =  bone-seeking
     */ 
    public int type;
    

    public EntityRocketHoming(World p_i1753_1_)
    {
        super(p_i1753_1_);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
    }

    public EntityRocketHoming(World p_i1754_1_, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_)
    {
        super(p_i1754_1_);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
        this.setPosition(p_i1754_2_, p_i1754_4_, p_i1754_6_);
        this.yOffset = 0.0F;
    }

    public EntityRocketHoming(World p_i1755_1_, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_, int rocketType)
    {
        super(p_i1755_1_);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = p_i1755_2_;
        this.type = rocketType;

        if (p_i1755_2_ instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }

        this.posY = p_i1755_2_.posY + p_i1755_2_.getEyeHeight() - 0.10000000149011612D;
        double d0 = p_i1755_3_.posX - p_i1755_2_.posX;
        double d1 = p_i1755_3_.boundingBox.minY + p_i1755_3_.height / 3.0F - this.posY;
        double d2 = p_i1755_3_.posZ - p_i1755_2_.posZ;
        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D)
        {
            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            this.setLocationAndAngles(p_i1755_2_.posX + d4, this.posY, p_i1755_2_.posZ + d5, f2, f3);
            this.yOffset = 0.0F;
            float f4 = (float)d3 * 0.2F;
            this.setThrowableHeading(d0, d1 + f4, d2, p_i1755_4_, p_i1755_5_);
        }
    }

    public EntityRocketHoming(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_, float strength, int type)
    {
        super(p_i1756_1_);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = p_i1756_2_;
        this.type = type;

        if (p_i1756_2_ instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;
        }

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(p_i1756_2_.posX, p_i1756_2_.posY + p_i1756_2_.getEyeHeight(), p_i1756_2_.posZ, p_i1756_2_.rotationYaw, p_i1756_2_.rotationPitch);
        this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        this.posY -= 0.10000000149011612D;
        this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI);
        this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, p_i1756_3_ * 1.5F, 1.0F);
        this.explosionStrength = strength;
    }

    public EntityRocketHoming(World world, int x, int y, int z, double mx, double my, double mz, double grav) {
        super(world);
    	this.posX = x + 0.5F;
    	this.posY = y + 0.5F;
    	this.posZ = z + 0.5F;
    	
    	this.motionX = mx;
    	this.motionY = my;
    	this.motionZ = mz;
    	
    	this.gravity = grav;
    }

    @Override
	protected void entityInit()
    {
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    @Override
	public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
    {
        float f2 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= f2;
        p_70186_3_ /= f2;
        p_70186_5_ /= f2;
        p_70186_1_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
        p_70186_3_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
        p_70186_5_ += this.rand.nextGaussian() * (this.rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
        p_70186_1_ *= p_70186_7_;
        p_70186_3_ *= p_70186_7_;
        p_70186_5_ *= p_70186_7_;
        this.motionX = p_70186_1_;
        this.motionY = p_70186_3_;
        this.motionZ = p_70186_5_;
        float f3 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70186_3_, f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    /**
     * Sets the position and rotation. Only difference from the other one is no bounding on the rotation. Args: posX,
     * posY, posZ, yaw, pitch
     */
    @Override
	@SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_)
    {
        this.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        this.setRotation(p_70056_7_, p_70056_8_);
    }

    /**
     * Sets the velocity to the args. Args: x, y, z
     */
    @Override
	@SideOnly(Side.CLIENT)
    public void setVelocity(double p_70016_1_, double p_70016_3_, double p_70016_5_)
    {
        this.motionX = p_70016_1_;
        this.motionY = p_70016_3_;
        this.motionZ = p_70016_5_;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(p_70016_1_ * p_70016_1_ + p_70016_5_ * p_70016_5_);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70016_1_, p_70016_5_) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70016_3_, f) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch;
            this.prevRotationYaw = this.rotationYaw;
            this.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.ticksInGround = 0;
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    //@Override
    @Override
	public void onUpdate()
    {
        super.onUpdate();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            //this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }

        Block block = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);

        if (block.getMaterial() != Material.air)
        {
            block.setBlockBoundsBasedOnState(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);
            AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f);

            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        if (this.inGround)
        {
            /*int j = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
            if (block == this.field_145790_g && j == this.inData)
            {
                ++this.ticksInGround;
                if (this.ticksInGround == 1200)
                {
                    this.setDead();
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }*/


            if (!this.worldObj.isRemote)
            {
            	//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.5F, true);
            	Explode(this.type, this.explosionStrength);
                /*EntityNukeExplosionAdvanced explosion = new EntityNukeExplosionAdvanced(this.worldObj);
                explosion.speed = 25;
                explosion.coefficient = 5.0F;
                explosion.destructionRange = 20;
                explosion.posX = this.posX;
                explosion.posY = this.posY;
                explosion.posZ = this.posZ;
                this.worldObj.spawnEntityInWorld(explosion);*/
            }
        	this.setDead();
        }
        else
        {
            ++this.ticksInAir;
            Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
            vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
            vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null)
            {
                vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }

            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            int i;
            float f1;

            for (i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);

                if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5))
                {
                    f1 = 0.3F;
                    AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }
            

            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null && movingobjectposition.entityHit != null && movingobjectposition.entityHit instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)movingobjectposition.entityHit;

                if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
                {
                    movingobjectposition = null;
                }
            }

            float f2;
            float f4;

            if (movingobjectposition != null)
            {
                if (movingobjectposition.entityHit != null)
                {
                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    int k = MathHelper.ceiling_double_int(f2 * this.damage);

                    if (this.getIsCritical())
                    {
                        k += this.rand.nextInt(k / 2 + 2);
                    }

                    DamageSource damagesource = null;

                    if (this.shootingEntity == null)
                    {
                        damagesource = DamageSource.causeIndirectMagicDamage(this, this);
                    }
                    else
                    {
                        damagesource = DamageSource.causeIndirectMagicDamage(this, this);
                    }

                    if (this.isBurning() && !(movingobjectposition.entityHit instanceof EntityEnderman))
                    {
                        movingobjectposition.entityHit.setFire(5);
                    }

                    if (movingobjectposition.entityHit.attackEntityFrom(damagesource, k))
                    {
                        if (movingobjectposition.entityHit instanceof EntityLivingBase)
                        {
                            EntityLivingBase entitylivingbase = (EntityLivingBase)movingobjectposition.entityHit;

                            if (this.knockbackStrength > 0)
                            {
                                f4 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

                                if (f4 > 0.0F)
                                {
                                    movingobjectposition.entityHit.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / f4, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / f4);
                                }
                            }

                            if (this.shootingEntity != null && this.shootingEntity instanceof EntityLivingBase)
                            {
                                EnchantmentHelper.func_151384_a(entitylivingbase, this.shootingEntity);
                                EnchantmentHelper.func_151385_b((EntityLivingBase)this.shootingEntity, entitylivingbase);
                            }

                            if (this.shootingEntity != null && movingobjectposition.entityHit != this.shootingEntity && movingobjectposition.entityHit instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
                            {
                                ((EntityPlayerMP)this.shootingEntity).playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                            }
                        }

                        if (!(movingobjectposition.entityHit instanceof EntityEnderman))
                        {
                            if (!this.worldObj.isRemote)
                            {
                            	//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.5F, true);
                            	Explode(this.type, this.explosionStrength);
                            }
                        	this.setDead();
                        }
                    }
                    else
                    {
                        if (!this.worldObj.isRemote)
                        {
                        	//this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2.5F, true);
                        	Explode(this.type, this.explosionStrength);
                        }
                    	this.setDead();
                    }
                }
                else
                {
                    this.field_145791_d = movingobjectposition.blockX;
                    this.field_145792_e = movingobjectposition.blockY;
                    this.field_145789_f = movingobjectposition.blockZ;
                    this.field_145790_g = this.worldObj.getBlock(this.field_145791_d, this.field_145792_e, this.field_145789_f);
                    this.inData = this.worldObj.getBlockMetadata(this.field_145791_d, this.field_145792_e, this.field_145789_f);
                    this.motionX = ((float)(movingobjectposition.hitVec.xCoord - this.posX));
                    this.motionY = ((float)(movingobjectposition.hitVec.yCoord - this.posY));
                    this.motionZ = ((float)(movingobjectposition.hitVec.zCoord - this.posZ));
                    f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                    this.posX -= this.motionX / f2 * 0.05000000074505806D;
                    this.posY -= this.motionY / f2 * 0.05000000074505806D;
                    this.posZ -= this.motionZ / f2 * 0.05000000074505806D;
                    this.inGround = true;
                    this.arrowShake = 7;
                    this.setIsCritical(false);

                    if (this.field_145790_g.getMaterial() != Material.air)
                    {
                        this.field_145790_g.onEntityCollidedWithBlock(this.worldObj, this.field_145791_d, this.field_145792_e, this.field_145789_f, this);
                    }
                }
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

            //for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            /*while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }
            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }
            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }*/

            f1 = 0.05F;

            if (this.isInWater())
            {
                for (int l = 0; l < 4; ++l)
                {
                    f4 = 0.25F;
                    this.worldObj.spawnParticle("bubble", this.posX - this.motionX * f4, this.posY - this.motionY * f4, this.posZ - this.motionZ * f4, this.motionX, this.motionY, this.motionZ);
                }
            }

            if (this.isWet())
            {
                this.extinguish();
            }

            /*this.motionX *= f3;
            this.motionY *= f3;
            this.motionZ *= f3;*/
            //this.motionY -= gravity;
            this.setPosition(this.posX, this.posY, this.posZ);
            this.func_145775_I();
        }

        if(!steer())
        	lockonTicks = 0;

		if (this.ticksExisted > 250)
			this.setDead();
    }

    public int homingRadius = 35;
    public int homingMod = 15;
    public float acceptance = 120;
    int lockonTicks = 0;
    boolean hasBeeped = false;
    
    private boolean steer() {
    	List<Entity> all = null;
    	if(this.type == 42) {
    		all = worldObj.getEntitiesWithinAABB(EntitySkeleton.class, AxisAlignedBB.getBoundingBox(posX - homingRadius, posY - homingRadius, posZ - homingRadius, posX + homingRadius, posY + homingRadius, posZ + homingRadius));
    	} else {
    		all = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(posX - homingRadius, posY - homingRadius, posZ - homingRadius, posX + homingRadius, posY + homingRadius, posZ + homingRadius));
    	}
    	
    	HashMap<Entity, Double> targetable = new HashMap();
    	Vec3 path = Vec3.createVectorHelper(motionX, motionY, motionZ);
    	double startSpeed = path.lengthVector();
    	path.normalize();
    	
    	if(all.isEmpty())
    		return false;
    	
    	//Iterate through all entities and only allocate ones that can be targeted
    	for(Entity e : all) {
    		
    		if(e == this.shootingEntity)
    			continue;
    		
    		Vec3 rel = Vec3.createVectorHelper(e.posX - posX, e.posY + e.getEyeHeight() - posY, e.posZ - posZ);
    		double vecProd = rel.xCoord * path.xCoord + rel.yCoord * path.yCoord + rel.zCoord * path.zCoord;
    		double bot = rel.lengthVector() * path.lengthVector();
    		double angle = Math.acos(vecProd / bot) * 180 / Math.PI;
    		
    		if(angle <= acceptance);
    			if(e.height * e.width * e.width >= 0.5D)
    				if(!Library.isObstructed(worldObj, e.posX, e.posY, e.posZ, posX, posY, posZ))
    					targetable.put(e, angle);
    	}
    	
    	if(targetable.isEmpty())
    		return false;
    	
    	double smallest = Double.POSITIVE_INFINITY;
    	Entity nearestE = null;
    	
    	//Iterate through all entities and choose the one that has the smallest angle
    	for(Map.Entry<Entity, Double> entry : targetable.entrySet()) {
    		if(entry.getValue() < smallest) {
    			smallest = entry.getValue();
    			nearestE = entry.getKey();
    		}
    	}
    	
    	if(nearestE == null)
    		return false;
    	
    	Vec3 winVec = Vec3.createVectorHelper(nearestE.posX - posX, nearestE.posY - posY, nearestE.posZ - posZ);
    	
    	winVec.normalize();
    	
    	double newX = ((path.xCoord * (smallest * homingMod - 1)) + winVec.xCoord) / (smallest * homingMod);
    	double newY = ((path.yCoord * (smallest * homingMod - 1)) + winVec.yCoord) / (smallest * homingMod);
    	double newZ = ((path.zCoord * (smallest * homingMod - 1)) + winVec.zCoord) / (smallest * homingMod);

    	Vec3 newPath = Vec3.createVectorHelper(newX, newY, newZ);
    	newPath.normalize();
    	newPath.xCoord *= startSpeed;
    	newPath.yCoord *= startSpeed;
    	newPath.zCoord *= startSpeed;

    	motionX = newPath.xCoord;
    	motionY = newPath.yCoord;
    	motionZ = newPath.zCoord;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, (float)startSpeed, 0.0F);
        
        lockonTicks++;
        if(lockonTicks == 5 && !hasBeeped) {
        	if(this.getIsCritical())
        		worldObj.playSoundAtEntity(this, "hbm:weapon.stingerLockOn", 10F, 0.75F);
        	else
        		worldObj.playSoundAtEntity(this, "hbm:weapon.stingerLockOn", 10F, 1F);
        	hasBeeped = true;
        }
        
        return true;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
	public void writeEntityToNBT(NBTTagCompound p_70014_1_)
    {
        p_70014_1_.setShort("xTile", (short)this.field_145791_d);
        p_70014_1_.setShort("yTile", (short)this.field_145792_e);
        p_70014_1_.setShort("zTile", (short)this.field_145789_f);
        p_70014_1_.setShort("life", (short)this.ticksInGround);
        p_70014_1_.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145790_g));
        p_70014_1_.setByte("inData", (byte)this.inData);
        p_70014_1_.setByte("shake", (byte)this.arrowShake);
        p_70014_1_.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        p_70014_1_.setByte("pickup", (byte)this.canBePickedUp);
        p_70014_1_.setDouble("damage", this.damage);
        p_70014_1_.setFloat("strength", (byte)this.explosionStrength);
        p_70014_1_.setByte("type", (byte)this.type);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
	public void readEntityFromNBT(NBTTagCompound p_70037_1_)
    {
        this.field_145791_d = p_70037_1_.getShort("xTile");
        this.field_145792_e = p_70037_1_.getShort("yTile");
        this.field_145789_f = p_70037_1_.getShort("zTile");
        this.ticksInGround = p_70037_1_.getShort("life");
        this.field_145790_g = Block.getBlockById(p_70037_1_.getByte("inTile") & 255);
        this.inData = p_70037_1_.getByte("inData") & 255;
        this.arrowShake = p_70037_1_.getByte("shake") & 255;
        this.inGround = p_70037_1_.getByte("inGround") == 1;
        this.explosionStrength = p_70037_1_.getFloat("strength");
        this.type = p_70037_1_.getByte("type");

        if (p_70037_1_.hasKey("damage", 99))
        {
            this.damage = p_70037_1_.getDouble("damage");
        }

        if (p_70037_1_.hasKey("pickup", 99))
        {
            this.canBePickedUp = p_70037_1_.getByte("pickup");
        }
        else if (p_70037_1_.hasKey("player", 99))
        {
            this.canBePickedUp = p_70037_1_.getBoolean("player") ? 1 : 0;
        }
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    @Override
	public void onCollideWithPlayer(EntityPlayer p_70100_1_)
    {
        if (!this.worldObj.isRemote && this.inGround && this.arrowShake <= 0)
        {
            boolean flag = this.canBePickedUp == 1 || this.canBePickedUp == 2 && p_70100_1_.capabilities.isCreativeMode;

            if (this.canBePickedUp == 1 && !p_70100_1_.inventory.addItemStackToInventory(new ItemStack(ModItems.ammo_stinger_rocket, 1)))
            {
                flag = false;
            }

            if (flag)
            {
                p_70100_1_.onItemPickup(this, 1);
                this.setDead();
            }
        }
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    @Override
	protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
	@SideOnly(Side.CLIENT)
    public float getShadowSize()
    {
        return 0.0F;
    }

    public void setDamage(double p_70239_1_)
    {
        this.damage = p_70239_1_;
    }

    public double getDamage()
    {
        return this.damage;
    }

    /**
     * Sets the amount of knockback the arrow applies when it hits a mob.
     */
    public void setKnockbackStrength(int p_70240_1_)
    {
        this.knockbackStrength = p_70240_1_;
    }

    /**
     * If returns false, the item will not inflict any damage against entities.
     */
    @Override
	public boolean canAttackWithItem()
    {
        return false;
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public void setIsCritical(boolean p_70243_1_)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (p_70243_1_)
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -2)));
        }
    }

    /**
     * Whether the arrow has a stream of critical hit particles flying behind it.
     */
    public boolean getIsCritical()
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        return (b0 & 1) != 0;
    }
    
    public void Explode(int type, float strength) {
    	switch(type) {
    		case 42: ChunkRadiationManager.proxy.incrementRad(worldObj, (int)posX, (int)posY, (int)posZ, 2000);
    		case 0: ExplosionLarge.explode(worldObj, posX, posY, posZ, strength, true, false, true); break;
    		case 1: ExplosionLarge.explode(worldObj, posX, posY, posZ, strength * 2, true, false, true); break;
    		case 2: ExplosionLarge.explodeFire(worldObj, posX, posY, posZ, strength, true, false, false); break;
    		case 4:
    			//ExplosionLarge.explode(worldObj, posX, posY, posZ, strength * 3, false, false, true);
    			ExplosionNukeSmall.explode(worldObj, posX, posY, posZ, ExplosionNukeSmall.PARAMS_MEDIUM);
    			break;
    		default: break;
    	}
    }
}