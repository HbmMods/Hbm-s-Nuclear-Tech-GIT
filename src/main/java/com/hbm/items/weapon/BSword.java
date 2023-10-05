package com.hbm.items.weapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class BSword extends ItemSword {

	public World worldObj;

	public double posX;
	public double posY;
	public double posZ;
	
	public BSword(ToolMaterial p_i45356_1_) {
		super(p_i45356_1_);
		// TODO Auto-generated constructor stub
	}


    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (target instanceof EntitySquid) {
            target.worldObj.createExplosion(attacker, target.posX, target.posY, target.posZ, 2.0F, true);
        }
        return super.hitEntity(stack, target, attacker);
    }
}
