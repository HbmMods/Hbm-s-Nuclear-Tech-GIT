package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.rbmk.RBMKDials;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRBMKDebris extends EntityDebrisBase {
	
	public float rot;
	public float lastRot;
	private boolean hasSizeSet = false;

	public EntityRBMKDebris(World world) {
		super(world);
	}

	public EntityRBMKDebris(World world, double x, double y, double z, DebrisType type) {
		super(world);
		this.setPosition(x, y, z);
		this.setType(type);
	}

	@Override
	public boolean interactFirst(EntityPlayer player) {

		if(!worldObj.isRemote) {
			
			switch(this.getType()) {
			case BLANK: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_metal))) this.setDead(); break;
			case ELEMENT: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_metal))) this.setDead(); break;
			case FUEL: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_fuel))) this.setDead(); break;
			case GRAPHITE: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_graphite))) this.setDead(); break;
			case LID: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.rbmk_lid))) this.setDead(); break;
			case ROD: if(player.inventory.addItemStackToInventory(new ItemStack(ModItems.debris_metal))) this.setDead(); break;
			}
			
			player.inventoryContainer.detectAndSendChanges();
		}

		return false;
	}

	@Override
	public void onUpdate() {
		
		if(!hasSizeSet) {
			
			switch(this.getType()) {
			case BLANK: this.setSize(0.5F, 0.5F); break;
			case ELEMENT: this.setSize(1F, 1F); break;
			case FUEL: this.setSize(0.25F, 0.25F); break;
			case GRAPHITE: this.setSize(0.25F, 0.25F); break;
			case LID: this.setSize(1F, 0.5F); break;
			case ROD: this.setSize(0.75F, 0.5F); break;
			}
			
			hasSizeSet = true;
		}
		
		if(!worldObj.isRemote) {
			if(this.getType() == DebrisType.LID && motionY > 0) {
	
				Vec3 pos = Vec3.createVectorHelper(posX, posY, posZ);
				Vec3 next = Vec3.createVectorHelper(posX + motionX * 2, posY + motionY * 2, posZ + motionZ * 2);
				MovingObjectPosition mop = worldObj.func_147447_a(pos, next, false, false, false);
				
				if(mop != null && mop.typeOfHit == mop.typeOfHit.BLOCK) {
	
					int x = mop.blockX;
					int y = mop.blockY;
					int z = mop.blockZ;
					
					for(int i = -1; i <= 1; i++) {
						for(int j = -1; j <= 1; j++) {
							for(int k = -1; k <= 1; k++) {
								
								int rn = Math.abs(i) + Math.abs(j) + Math.abs(k);
								
								if(rn <= 1 || rand.nextInt(rn) == 0)
									worldObj.setBlockToAir(x + i, y + j, z + k);
							}
						}
					}
					
					this.setDead();
				}
			}
			
			if(this.getType() == DebrisType.FUEL || this.getType() == DebrisType.GRAPHITE) {
				List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(2.5, 2.5, 2.5));
				
				int level = this.getType() == DebrisType.FUEL ? 9 : 4;
				for(EntityLivingBase e : entities) {
					e.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 60 * 20, level));
				}
			}
			
			if(!RBMKDials.getPermaScrap(worldObj) && this.ticksExisted > getLifetime() + this.getEntityId() % 50)
				this.setDead();
		}
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.motionY -= 0.04D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		
		this.lastRot = this.rot;

		if(this.onGround) {
			this.motionX *= 0.85D;
			this.motionZ *= 0.85D;
			this.motionY *= -0.5D;
			
		} else {
			
			this.rot += 10F;
			
			if(rot >= 360F) {
				this.rot -= 360F;
				this.lastRot -= 360F;
			}
		}
	}
	
	@Override
	protected int getLifetime() {
		
		switch(this.getType()) {
		case BLANK: return 3 * 60 * 20;
		case ELEMENT: return 3 * 60 * 20;
		case FUEL: return 10 * 60 * 20;
		case GRAPHITE: return 15 * 60 * 20;
		case LID: return 30 * 20;
		case ROD: return 60 * 20;
		default: return 0;
		}
	}
	
	public void setType(DebrisType type) {
		this.dataWatcher.updateObject(20, type.ordinal());
	}
	
	public DebrisType getType() {
		return DebrisType.values()[Math.abs(this.dataWatcher.getWatchableObjectInt(20)) % DebrisType.values().length];
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.dataWatcher.updateObject(20, nbt.getInteger("debtype"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("debtype", this.dataWatcher.getWatchableObjectInt(20));
	}
	
	public static enum DebrisType {
		BLANK,		//just a metal beam
		ELEMENT,	//the entire casing of a fuel assembly because fuck you
		FUEL,		//spicy
		ROD,		//solid boron rod
		GRAPHITE,	//spicy rock
		LID;		//the all destroying harbinger of annihilation
	}
}
