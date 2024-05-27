package com.hbm.entity.mob;

import java.util.ArrayList;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityTesla;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

public class EntityTeslaCrab extends EntityCyberCrab {
	
	public List<double[]> targets = new ArrayList();

	public EntityTeslaCrab(World p_i1733_1_) {
		super(p_i1733_1_);
        this.setSize(0.75F, 1.25F);
        this.ignoreFrustumCheck = true;
	}

    @Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5F);
    }
    
    public void onLivingUpdate() {
    	
    	targets = TileEntityTesla.zap(worldObj, posX, posY + 1, posZ, 3, this);
    	
        super.onLivingUpdate();
    }

    protected void dropRareDrop(int p_70600_1_) {
    	this.dropItem(ModItems.coil_copper, 1);
    }

}
