package com.hbm.entity.mob;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.world.feature.GlyphidHive;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGlyphidScout extends EntityGlyphid {
	
	public boolean hasHome = false;
	public double homeX;
	public double homeY;
	public double homeZ;

	public EntityGlyphidScout(World world) {
		super(world);
		this.setSize(1.25F, 0.75F);
	}
	
	@Override
	public float getDamageThreshold() {
		return 0.0F;
	}

	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_scout_tex;
	}

	@Override
	public double getScale() {
		return 0.75D;
	}

	@Override
	public int getArmorBreakChance(float amount) {
		return 1;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.5D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
	}

	@Override
	protected boolean canDespawn() {
		return true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!worldObj.isRemote) {
			
			if(!this.hasHome) {
				this.homeX = posX;
				this.homeY = posY;
				this.homeZ = posZ;
				this.hasHome = true;
			}

			if(this.ticksExisted > 0 && this.ticksExisted % 1200 == 0 && Vec3.createVectorHelper(posX - homeX, posY - homeY, posZ - homeZ).lengthVector() > 16) {
				
				Block b = worldObj.getBlock((int) Math.floor(posX), (int) Math.floor(posY - 1), (int) Math.floor(posZ));
				
				if(b.isNormalCube() && b != ModBlocks.glyphid_base) {
					this.setDead();
					worldObj.newExplosion(this, posX, posY, posZ, 5F, false, false);
					GlyphidHive.generate(worldObj, (int) Math.floor(posX), (int) Math.floor(posY), (int) Math.floor(posZ), rand);
				}
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("hasHome", hasHome);
		nbt.setDouble("homeX", homeX);
		nbt.setDouble("homeY", homeY);
		nbt.setDouble("homeZ", homeZ);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.hasHome = nbt.getBoolean("hasHome");
		this.homeX = nbt.getDouble("homeX");
		this.homeY = nbt.getDouble("homeY");
		this.homeZ = nbt.getDouble("homeZ");
	}
}
