package com.hbm.entity.missile;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.storage.TileEntitySoyuzCapsule;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySoyuzCapsule extends EntityThrowable {

	public int soyuz;
	public ItemStack[] payload = new ItemStack[18];

	public EntitySoyuzCapsule(World p_i1582_1_) {
		super(p_i1582_1_);
		this.ignoreFrustumCheck = true;
		this.isImmuneToFire = true;
	}
	
	@Override
	public void onUpdate() {

		this.lastTickPosX = this.prevPosX = posX;
		this.lastTickPosY = this.prevPosY = posY;
		this.lastTickPosZ = this.prevPosZ = posZ;
		this.setPosition(posX + this.motionX, posY + this.motionY, posZ + this.motionZ);
		
		if(this.motionY > -0.2)
			this.motionY -= 0.02;
		
		if(posY > 600)
			posY = 600;
        
        if(this.worldObj.getBlock((int)this.posX, (int)this.posY, (int)this.posZ) != Blocks.air) {
        	
    		this.setDead();
    		
    		if(!worldObj.isRemote) {
    			worldObj.setBlock((int)(this.posX), (int)(this.posY + 1), (int)(this.posZ), ModBlocks.soyuz_capsule);
    			
    			TileEntitySoyuzCapsule capsule = (TileEntitySoyuzCapsule)worldObj.getTileEntity((int)(this.posX), (int)(this.posY + 1), (int)(this.posZ));
    			if(capsule != null) {
    				
    				for(int i = 0; i < payload.length; i++) {
    					capsule.setInventorySlotContents(i, payload[i]);
    				}
    			}
    			
    			capsule.setInventorySlotContents(18, new ItemStack(ModItems.missile_soyuz, 1, soyuz));
    		}
        }
    }

	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		
	}
	
    @Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 500000;
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {

		NBTTagList list = nbt.getTagList("items", 10);
		
		soyuz = nbt.getInteger("soyuz");

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound nbt1 = list.getCompoundTagAt(i);
			byte b0 = nbt1.getByte("slot");
			if (b0 >= 0 && b0 < payload.length) {
				payload[b0] = ItemStack.loadItemStackFromNBT(nbt1);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {

		NBTTagList list = new NBTTagList();
		
		nbt.setInteger("soyuz", soyuz);

		for (int i = 0; i < payload.length; i++) {
			if (payload[i] != null) {
				NBTTagCompound nbt1 = new NBTTagCompound();
				nbt1.setByte("slot", (byte) i);
				payload[i].writeToNBT(nbt1);
				list.appendTag(nbt1);
			}
		}
		nbt.setTag("items", list);
	}
}
