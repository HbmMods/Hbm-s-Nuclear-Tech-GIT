package com.hbm.entity.effect;

import java.util.List;
import java.util.Random;

import com.hbm.entity.projectile.EntityRubble;
import com.hbm.items.ModItems;
import com.hbm.lib.ModDamageSource;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBlackHole extends Entity {
	
	Random rand = new Random();

	public EntityBlackHole(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
		this.noClip = true;
	}

	public EntityBlackHole(World world, float size) {
		this(world);
		this.dataWatcher.updateObject(16, size);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		float size = this.dataWatcher.getWatchableObjectFloat(16);
		
		if(!worldObj.isRemote) {
			for(int k = 0; k < size * 2; k++) {
				double phi = rand.nextDouble() * (Math.PI * 2);
				double costheta = rand.nextDouble() * 2 - 1;
				double theta = Math.acos(costheta);
				double x = Math.sin( theta) * Math.cos( phi );
				double y = Math.sin( theta) * Math.sin( phi );
				double z = Math.cos( theta );
				
				Vec3 vec = Vec3.createVectorHelper(x, y, z);
				int length = (int)Math.ceil(size * 15);
				
				for(int i = 0; i < length; i ++) {
					int x0 = (int)(this.posX + (vec.xCoord * i));
					int y0 = (int)(this.posY + (vec.yCoord * i));
					int z0 = (int)(this.posZ + (vec.zCoord * i));
					
					if(worldObj.getBlock(x0, y0, z0).getMaterial().isLiquid()) {
						worldObj.setBlock(x0, y0, z0, Blocks.air);
					}
					
					if(worldObj.getBlock(x0, y0, z0) != Blocks.air) {
						EntityRubble rubble = new EntityRubble(worldObj);
						rubble.posX = x0 + 0.5F;
						rubble.posY = y0;
						rubble.posZ = z0 + 0.5F;
						rubble.setMetaBasedOnBlock(worldObj.getBlock(x0, y0, z0), worldObj.getBlockMetadata(x0, y0, z0));
						
						worldObj.spawnEntityInWorld(rubble);
					
						worldObj.setBlock(x0, y0, z0, Blocks.air);
						break;
					}
				}
			}
		}
		
		double range = size * 15;
		
		List<Entity> entities = worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(
				posX - range, posY - range, posZ - range, posX + range, posY + range, posZ + range));
		
		for(Entity e : entities) {
			
			if(e instanceof EntityPlayer && ((EntityPlayer)e).capabilities.isCreativeMode)
				continue;
			
			if(e instanceof EntityFallingBlock && !worldObj.isRemote && e.ticksExisted > 1) {
				
				double x = e.posX;
				double y = e.posY;
				double z = e.posZ;
				Block b = ((EntityFallingBlock)e).func_145805_f();
				int meta = ((EntityFallingBlock)e).field_145814_a;
				
				e.setDead();
				
				EntityRubble rubble = new EntityRubble(worldObj);
				rubble.setMetaBasedOnBlock(b, meta);
				rubble.setPositionAndRotation(x, y, z, 0, 0);
				rubble.motionX = e.motionX;
				rubble.motionY = e.motionY;
				rubble.motionZ = e.motionZ;
				worldObj.spawnEntityInWorld(rubble);
			}
			
			Vec3 vec = Vec3.createVectorHelper(posX - e.posX, posY - e.posY, posZ - e.posZ);
			
			double dist = vec.lengthVector();
			
			if(dist > range)
				continue;
			
			vec = vec.normalize();
			
			if(!(e instanceof EntityItem))
				vec.rotateAroundY((float)Math.toRadians(15));
			
			double speed = 0.1D;
			e.motionX += vec.xCoord * speed;
			e.motionY += vec.yCoord * speed * 2;
			e.motionZ += vec.zCoord * speed;
			
			if(e instanceof EntityBlackHole)
				continue;
			
			if(dist < size * 1.5) {
				e.attackEntityFrom(ModDamageSource.blackhole, 1000);
				
				if(!(e instanceof EntityLivingBase))
					e.setDead();
				
				if(!worldObj.isRemote && e instanceof EntityItem) {
					EntityItem item = (EntityItem) e;
					ItemStack stack = item.getEntityItem();
					
					if(stack.getItem() == ModItems.pellet_antimatter || stack.getItem() == ModItems.flame_pony) {
						this.setDead();
						worldObj.createExplosion(null, this.posX, this.posY, this.posZ, 5.0F, true);
						return;
					}
				}
			}
		}
		
		this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		
		this.motionX *= 0.99D;
		this.motionY *= 0.99D;
		this.motionZ *= 0.99D;
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, 0.5F);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.dataWatcher.updateObject(16, nbt.getFloat("size"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat("size", this.dataWatcher.getWatchableObjectFloat(16));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 25000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}

	@Override
	public float getBrightness(float p_70013_1_) {
		return 1.0F;
	}
}
