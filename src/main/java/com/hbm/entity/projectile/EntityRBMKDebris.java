package com.hbm.entity.projectile;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import com.hbm.tileentity.machine.rbmk.RBMKDials;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRBMKDebris extends Entity {
	
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
	protected void entityInit() {
		this.dataWatcher.addObject(20, 0);
		this.rot = this.lastRot = this.rand.nextFloat() * 360;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
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
			
			if(this.getType() == DebrisType.FUEL) {
				List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(10, 10, 10));
				
				for(EntityLivingBase e : entities) {
					e.addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 60 * 20, 9));
				}
			}
			
			if(!RBMKDials.getPermaScrap(worldObj) && this.ticksExisted > getLifetime() + this.getEntityId() % 50)
				this.setDead();
		}
	}
	
	private int getLifetime() {
		
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

	@Override
	public void moveEntity(double moX, double moY, double moZ) {

		this.worldObj.theProfiler.startSection("move");
		this.ySize *= 0.4F;

		if(this.isInWeb) {
			this.isInWeb = false;
		}

		double initMoX = moX;
		double initMoY = moY;
		double initMoZ = moZ;
		AxisAlignedBB axisalignedbb = this.boundingBox.copy();

		List list = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(moX, moY, moZ));

		for(int i = 0; i < list.size(); ++i) {
			moY = ((AxisAlignedBB) list.get(i)).calculateYOffset(this.boundingBox, moY);
		}

		this.boundingBox.offset(0.0D, moY, 0.0D);

		if(!this.field_70135_K && initMoY != moY) {
			moZ = 0.0D;
			moY = 0.0D;
			moX = 0.0D;
		}

		boolean isGoingDown = this.onGround || initMoY != moY && initMoY < 0.0D;
		int j;

		for(j = 0; j < list.size(); ++j) {
			moX = ((AxisAlignedBB) list.get(j)).calculateXOffset(this.boundingBox, moX);
		}

		this.boundingBox.offset(moX, 0.0D, 0.0D);

		if(!this.field_70135_K && initMoX != moX) {
			moZ = 0.0D;
			moY = 0.0D;
			moX = 0.0D;
		}

		for(j = 0; j < list.size(); ++j) {
			moZ = ((AxisAlignedBB) list.get(j)).calculateZOffset(this.boundingBox, moZ);
		}

		this.boundingBox.offset(0.0D, 0.0D, moZ);

		if(!this.field_70135_K && initMoZ != moZ) {
			moZ = 0.0D;
			moY = 0.0D;
			moX = 0.0D;
		}

		double d10;
		double d11;
		int k;
		double d12;

		if(this.stepHeight > 0.0F && isGoingDown && this.ySize < 0.05F && (initMoX != moX || initMoZ != moZ)) {
			d12 = moX;
			d10 = moY;
			d11 = moZ;
			moX = initMoX;
			moY = (double) this.stepHeight;
			moZ = initMoZ;
			AxisAlignedBB axisalignedbb1 = this.boundingBox.copy();
			this.boundingBox.setBB(axisalignedbb);
			list = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(initMoX, moY, initMoZ));

			for(k = 0; k < list.size(); ++k) {
				moY = ((AxisAlignedBB) list.get(k)).calculateYOffset(this.boundingBox, moY);
			}

			this.boundingBox.offset(0.0D, moY, 0.0D);

			if(!this.field_70135_K && initMoY != moY) {
				moZ = 0.0D;
				moY = 0.0D;
				moX = 0.0D;
			}

			for(k = 0; k < list.size(); ++k) {
				moX = ((AxisAlignedBB) list.get(k)).calculateXOffset(this.boundingBox, moX);
			}

			this.boundingBox.offset(moX, 0.0D, 0.0D);

			if(!this.field_70135_K && initMoX != moX) {
				moZ = 0.0D;
				moY = 0.0D;
				moX = 0.0D;
			}

			for(k = 0; k < list.size(); ++k) {
				moZ = ((AxisAlignedBB) list.get(k)).calculateZOffset(this.boundingBox, moZ);
			}

			this.boundingBox.offset(0.0D, 0.0D, moZ);

			if(!this.field_70135_K && initMoZ != moZ) {
				moZ = 0.0D;
				moY = 0.0D;
				moX = 0.0D;
			}

			if(!this.field_70135_K && initMoY != moY) {
				moZ = 0.0D;
				moY = 0.0D;
				moX = 0.0D;
			} else {
				moY = (double) (-this.stepHeight);

				for(k = 0; k < list.size(); ++k) {
					moY = ((AxisAlignedBB) list.get(k)).calculateYOffset(this.boundingBox, moY);
				}

				this.boundingBox.offset(0.0D, moY, 0.0D);
			}

			if(d12 * d12 + d11 * d11 >= moX * moX + moZ * moZ) {
				moX = d12;
				moY = d10;
				moZ = d11;
				this.boundingBox.setBB(axisalignedbb1);
			}
		}

		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("rest");
		this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
		this.posY = this.boundingBox.minY + (double) this.yOffset - (double) this.ySize;
		this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
		this.isCollidedHorizontally = initMoX != moX || initMoZ != moZ;
		this.isCollidedVertically = initMoY != moY;
		this.onGround = initMoY != moY && initMoY < 0.0D;
		this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
		this.updateFallState(moY, this.onGround);

		if(initMoX != moX) {
			//this.motionX = 0.0D;
			this.motionX *= -0.75D;
			
		}

		if(initMoY != moY) {
			this.motionY = 0.0D;
		}

		if(initMoZ != moZ) {
			//this.motionZ = 0.0D;
			this.motionZ *= -0.75D;
		}

		try {
			this.func_145775_I();
		} catch(Throwable throwable) {
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
			this.addEntityCrashInfo(crashreportcategory);
			throw new ReportedException(crashreport);
		}

		this.worldObj.theProfiler.endSection();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double dist) {
		
		int range = 128;
		return dist < range * range;
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
