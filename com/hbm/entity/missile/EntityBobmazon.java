package com.hbm.entity.missile;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityBobmazon extends Entity {
	
	public ItemStack payload;

	public EntityBobmazon(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
        this.setSize(1F, 3F);
	}

	@Override
	protected void entityInit() {
		this.dataWatcher.addObject(16, Integer.valueOf(0));
	}
	
	@Override
	public void onUpdate() {
		
		motionY = -0.5;
		motionX = 0;
		motionZ = 0;

		this.lastTickPosX = this.prevPosX = this.posX;
		this.lastTickPosY = this.prevPosY = this.posY;
		this.lastTickPosZ = this.prevPosZ = this.posZ;
		
		for(int i = 0; i < 4; i++) {
			
			if(!this.worldObj.isRemote && i % 2 == 0)
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(posX, posY + 1, posZ, 2), new TargetPoint(worldObj.provider.dimensionId, posX, posY + 1, posZ, 300));
			
			if(worldObj.getBlock((int)(posX - 0.5), (int)(posY + 1), (int)(posZ - 0.5)).getMaterial() != Material.air && !worldObj.isRemote && dataWatcher.getWatchableObjectInt(16) != 1) {
				this.setDead();
				ExplosionLarge.spawnParticles(worldObj, posX, posY, posZ, 50);

	            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:entity.oldExplosion", 10.0F, 0.5F + this.rand.nextFloat() * 0.1F);
				
				if(payload != null)
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, payload));
				
				break;
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) { }

}
