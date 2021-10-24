package com.hbm.entity.projectile;

import com.hbm.lib.ModDamageSource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.ParticleBurstPacket;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRubble extends EntityThrowable {

    public EntityRubble(World p_i1773_1_)
    {
        super(p_i1773_1_);
    }

    public EntityRubble(World p_i1774_1_, EntityLivingBase p_i1774_2_)
    {
        super(p_i1774_1_, p_i1774_2_);
    }

    @Override
	public void entityInit() {
        this.dataWatcher.addObject(16, (int)Integer.valueOf(0));
        this.dataWatcher.addObject(17, (int)Integer.valueOf(0));
    }

    public EntityRubble(World p_i1775_1_, double p_i1775_2_, double p_i1775_4_, double p_i1775_6_)
    {
        super(p_i1775_1_, p_i1775_2_, p_i1775_4_, p_i1775_6_);
    }

    @Override
	protected void onImpact(MovingObjectPosition p_70184_1_)
    {
        if (p_70184_1_.entityHit != null)
        {
            byte b0 = 15;

            p_70184_1_.entityHit.attackEntityFrom(ModDamageSource.rubble, b0);
        }

        if(this.ticksExisted > 2) {
        	this.setDead();
        	
    		worldObj.playSoundAtEntity(this, "hbm:block.debris", 1.5F, 1.0F);
            //worldObj.playAuxSFX(2001, (int)posX, (int)posY, (int)posZ, this.dataWatcher.getWatchableObjectInt(16) + (this.dataWatcher.getWatchableObjectInt(17) << 12));
    		
    		if(!worldObj.isRemote)
    			PacketDispatcher.wrapper.sendToAllAround(new ParticleBurstPacket((int)Math.floor(posX), (int)posY, (int)Math.floor(posZ), this.dataWatcher.getWatchableObjectInt(16), this.dataWatcher.getWatchableObjectInt(17)), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 50));
        }
    }
    
    public void setMetaBasedOnBlock(Block b, int i) {

    	this.dataWatcher.updateObject(16, Block.getIdFromBlock(b));
    	this.dataWatcher.updateObject(17, i);
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.dataWatcher.updateObject(16, nbt.getInteger("block"));
		this.dataWatcher.updateObject(17, nbt.getInteger("meta"));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setInteger("block", this.dataWatcher.getWatchableObjectInt(16));
		nbt.setInteger("meta", this.dataWatcher.getWatchableObjectInt(17));
	}
}
