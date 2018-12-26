package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.potion.HbmPotion;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BarbedWire extends Block {

    public BarbedWire(Material mat)
    {
        super(mat);
    }
    
    public void onEntityCollidedWithBlock(World p_149670_1_, int x, int y, int z, Entity ent)
    {
    	ent.setInWeb();

        if(this == ModBlocks.barbed_wire) {
        	ent.attackEntityFrom(DamageSource.cactus, 2.0F);
        }

        if(this == ModBlocks.barbed_wire_fire) {
        	ent.attackEntityFrom(DamageSource.cactus, 2.0F);
        	ent.setFire(1);
        }

        if(this == ModBlocks.barbed_wire_poison) {
        	ent.attackEntityFrom(DamageSource.cactus, 2.0F);
        	
        	if(ent instanceof EntityLivingBase)
        		((EntityLivingBase)ent).addPotionEffect(new PotionEffect(Potion.poison.id, 5 * 20, 2));
        		
        }

        if(this == ModBlocks.barbed_wire_acid) {
        	ent.attackEntityFrom(DamageSource.cactus, 2.0F);

        	if(ent instanceof EntityPlayer) {
	    		Library.damageSuit((EntityPlayer)ent, 0, 1);
	    		Library.damageSuit((EntityPlayer)ent, 1, 1);
	    		Library.damageSuit((EntityPlayer)ent, 2, 1);
	    		Library.damageSuit((EntityPlayer)ent, 3, 1);
        	}
        }

        if(this == ModBlocks.barbed_wire_wither) {
        	ent.attackEntityFrom(DamageSource.cactus, 2.0F);
        	
        	if(ent instanceof EntityLivingBase)
        		((EntityLivingBase)ent).addPotionEffect(new PotionEffect(Potion.wither.id, 5 * 20, 4));
        }

        if(this == ModBlocks.barbed_wire_ultradeath) {
			ent.attackEntityFrom(ModDamageSource.pc, 5.0F);
        	
        	if(ent instanceof EntityLivingBase)
        		((EntityLivingBase)ent).addPotionEffect(new PotionEffect(HbmPotion.radiation.id, 5 * 20, 9));
        }
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    public int getRenderType()
    {
        return 1;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

}
