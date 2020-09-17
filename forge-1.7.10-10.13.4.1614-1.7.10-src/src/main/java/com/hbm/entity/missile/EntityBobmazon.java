package com.hbm.entity.missile;

import com.hbm.explosion.ExplosionLarge;
import com.hbm.main.MainRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
			
			if(worldObj.getBlock((int)(posX - 0.5), (int)(posY + 1), (int)(posZ - 0.5)).getMaterial() != Material.air && !worldObj.isRemote && dataWatcher.getWatchableObjectInt(16) != 1) {
				ExplosionLarge.spawnParticles(worldObj, posX, posY + 1, posZ, 50);

	            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "hbm:entity.oldExplosion", 10.0F, 0.5F + this.rand.nextFloat() * 0.1F);
				
				if(payload != null) {
					EntityItem pack = new EntityItem(worldObj, posX, posY + 2, posZ, payload);
					pack.motionX = 0;
					pack.motionZ = 0;
					worldObj.spawnEntityInWorld(pack);
				}
				
				this.setDead();
				
				break;
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
		}
		
		if(worldObj.isRemote) {


			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "exhaust");
			data.setString("mode", "meteor");
			data.setInteger("count", 1);
			data.setDouble("width", 0);
			data.setDouble("posX", posX);
			data.setDouble("posY", posY + 1);
			data.setDouble("posZ", posZ);
			
			MainRegistry.proxy.effectNT(data);
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {

		NBTTagCompound nbt1 = (NBTTagCompound)nbt.getTag("payload");
		this.payload = ItemStack.loadItemStackFromNBT(nbt1);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {

		NBTTagCompound nbt1 = new NBTTagCompound();
		payload.writeToNBT(nbt1);
		nbt.setTag("payload", nbt1);
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }

}
