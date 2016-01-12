package com.hbm.items;

import com.hbm.entity.EntityGrenadeCluster;
import com.hbm.entity.EntityGrenadeElectric;
import com.hbm.entity.EntityGrenadeFire;
import com.hbm.entity.EntityGrenadeFlare;
import com.hbm.entity.EntityGrenadeFrag;
import com.hbm.entity.EntityGrenadeGas;
import com.hbm.entity.EntityGrenadeGeneric;
import com.hbm.entity.EntityGrenadeNuke;
import com.hbm.entity.EntityGrenadePoison;
import com.hbm.entity.EntityGrenadeSchrabidium;
import com.hbm.entity.EntityGrenadeStrong;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGrenade extends Item {

    public ItemGrenade()
    {
        this.maxStackSize = 16;
    }
    
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        if (!p_77659_3_.capabilities.isCreativeMode)
        {
            --p_77659_1_.stackSize;
        }

        p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!p_77659_2_.isRemote)
        {
        	if(this == ModItems.grenade_generic)
        	{
        		p_77659_2_.spawnEntityInWorld(new EntityGrenadeGeneric(p_77659_2_, p_77659_3_));
        	}
        	if(this == ModItems.grenade_strong)
        	{
        		p_77659_2_.spawnEntityInWorld(new EntityGrenadeStrong(p_77659_2_, p_77659_3_));
        	}
        	if(this == ModItems.grenade_frag)
        	{
        		EntityGrenadeFrag frag = new EntityGrenadeFrag(p_77659_2_, p_77659_3_);
        		frag.shooter = p_77659_3_;
        		p_77659_2_.spawnEntityInWorld(frag);
        	}
        	if(this == ModItems.grenade_fire)
        	{
        		EntityGrenadeFire fire = new EntityGrenadeFire(p_77659_2_, p_77659_3_);
        		fire.shooter = p_77659_3_;
        		p_77659_2_.spawnEntityInWorld(fire);
        	}
        	if(this == ModItems.grenade_cluster)
        	{
        		p_77659_2_.spawnEntityInWorld(new EntityGrenadeCluster(p_77659_2_, p_77659_3_));
        	}
        	if(this == ModItems.grenade_flare)
        	{
        		p_77659_2_.spawnEntityInWorld(new EntityGrenadeFlare(p_77659_2_, p_77659_3_));
        	}
        	if(this == ModItems.grenade_electric)
        	{
        		p_77659_2_.spawnEntityInWorld(new EntityGrenadeElectric(p_77659_2_, p_77659_3_));
        	}
        	if(this == ModItems.grenade_poison)
        	{
        		p_77659_2_.spawnEntityInWorld(new EntityGrenadePoison(p_77659_2_, p_77659_3_));
        	}
        	if(this == ModItems.grenade_gas)
        	{
        		p_77659_2_.spawnEntityInWorld(new EntityGrenadeGas(p_77659_2_, p_77659_3_));
        	}
        	if(this == ModItems.grenade_schrabidium)
        	{
        		p_77659_2_.spawnEntityInWorld(new EntityGrenadeSchrabidium(p_77659_2_, p_77659_3_));
        	}
        	if(this == ModItems.grenade_nuke)
        	{
        		p_77659_2_.spawnEntityInWorld(new EntityGrenadeNuke(p_77659_2_, p_77659_3_));
        	}
        }

        return p_77659_1_;
    }

    public EnumRarity getRarity(ItemStack p_77613_1_) {
    	
    	if(this == ModItems.grenade_schrabidium)
    	{
        	return EnumRarity.rare;
    	}
    	
    	return EnumRarity.common;
    }

}
