package com.hbm.blocks;

import java.util.Random;

import com.hbm.entity.EntityNuclearCreeper;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class BlockOre extends Block {

	protected BlockOre(Material p_i45394_1_) {
		super(p_i45394_1_);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		if(this == ModBlocks.ore_fluorite)
		{
			return ModItems.fluorite;
		}
		if(this == ModBlocks.ore_niter)
		{
			return ModItems.niter;
		}
		if(this == ModBlocks.ore_sulfur || this == ModBlocks.ore_nether_sulfur)
		{
			return ModItems.sulfur;
		}
		if(this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red)
		{
			return ModItems.trinitite;
		}
		if(this == ModBlocks.waste_planks)
		{
			return Items.coal;
		}
		if(this == ModBlocks.frozen_dirt)
		{
			return Items.snowball;
		}
		if(this == ModBlocks.frozen_planks)
		{
			return Items.snowball;
		}
		if(this == ModBlocks.ore_nether_fire)
		{
			return p_149650_2_.nextInt(2) == 0 ? ModItems.powder_fire : Items.blaze_powder;
		}
		
		return Item.getItemFromBlock(this);
    }
    
    @Override
	public int quantityDropped(Random p_149745_1_)
    {
    	if(this == ModBlocks.ore_fluorite)
		{
    		return 2 + p_149745_1_.nextInt(3);
		}
		if(this == ModBlocks.ore_niter)
		{
			return 2 + p_149745_1_.nextInt(3);
		}
		if(this == ModBlocks.ore_sulfur || this == ModBlocks.ore_nether_sulfur)
		{
			return 2 + p_149745_1_.nextInt(3);
		}
		if(this == ModBlocks.ore_nether_fire)
		{
			return 2 + p_149745_1_.nextInt(3);
		}
    	
    	return 1;
    }

    @Override
	public int damageDropped(int p_149692_1_)
    {
        return this == ModBlocks.waste_planks ? 1 : 0;
    }

    @Override
	public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity entity)
    {
    	if (entity instanceof EntityLivingBase && this == ModBlocks.frozen_dirt)
    	{
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 60 * 20, 2));
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.block_trinitite)
    	{
    		if(entity instanceof EntityPlayer && Library.checkForHazmat((EntityPlayer)entity))
        	{
        		/*Library.damageSuit(((EntityPlayer)entity), 0);
        		Library.damageSuit(((EntityPlayer)entity), 1);
        		Library.damageSuit(((EntityPlayer)entity), 2);
        		Library.damageSuit(((EntityPlayer)entity), 3);*/
        		
        	} else if(entity instanceof EntityCreeper) {
        		EntityNuclearCreeper creep = new EntityNuclearCreeper(p_149724_1_);
        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        		//creep.setRotationYawHead(((EntityCreeper)entity).rotationYawHead);
        		entity.setDead();
        		if(!p_149724_1_.isRemote)
        		p_149724_1_.spawnEntityInWorld(creep);
        	} else if(entity instanceof EntityVillager) {
        		EntityZombie creep = new EntityZombie(p_149724_1_);
        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        		entity.setDead();
        		if(!p_149724_1_.isRemote)
        		p_149724_1_.spawnEntityInWorld(creep);
        	} else if(!(entity instanceof EntityNuclearCreeper) && !(entity instanceof EntityMooshroom) && !(entity instanceof EntityZombie)) {
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 3 * 60 * 20, 2));
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.id, 30 * 20, 0));
    		entity.attackEntityFrom(ModDamageSource.radiation, 0.5F);
        	}
    	}
    	if (entity instanceof EntityLivingBase && this == ModBlocks.block_waste)
    	{
    		if(entity instanceof EntityPlayer && Library.checkForHazmat((EntityPlayer)entity))
        	{
        		/*Library.damageSuit(((EntityPlayer)entity), 0);
        		Library.damageSuit(((EntityPlayer)entity), 1);
        		Library.damageSuit(((EntityPlayer)entity), 2);
        		Library.damageSuit(((EntityPlayer)entity), 3);*/
        		
        	} else if(entity instanceof EntityCreeper) {
        		EntityNuclearCreeper creep = new EntityNuclearCreeper(p_149724_1_);
        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        		//creep.setRotationYawHead(((EntityCreeper)entity).rotationYawHead);
        		if(!entity.isDead)
        			if(!p_149724_1_.isRemote)
        					p_149724_1_.spawnEntityInWorld(creep);
        		entity.setDead();
        	} else if(entity instanceof EntityCow) {
        		EntityMooshroom creep = new EntityMooshroom(p_149724_1_);
        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        		//creep.setRotationYawHead(((EntityCow)entity).rotationYawHead);
        		if(!entity.isDead)
        			if(!p_149724_1_.isRemote)
        					p_149724_1_.spawnEntityInWorld(creep);
        		entity.setDead();
        	} else if(entity instanceof EntityVillager) {
        		EntityZombie creep = new EntityZombie(p_149724_1_);
        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        		entity.setDead();
        		if(!p_149724_1_.isRemote)
        		p_149724_1_.spawnEntityInWorld(creep);
        	} else if(!(entity instanceof EntityNuclearCreeper) && !(entity instanceof EntityMooshroom) && !(entity instanceof EntityZombie)) {
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 10 * 60 * 20, 4));
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 4 * 60 * 20, 2));
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.id, 3 * 60 * 20, 2));
    		entity.attackEntityFrom(ModDamageSource.radiation, 2.5F);
        	}
    	}
    	if (entity instanceof EntityLivingBase && (this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red))
    	{
    		if(entity instanceof EntityPlayer && Library.checkForHazmat((EntityPlayer)entity))
        	{
        		/*Library.damageSuit(((EntityPlayer)entity), 0);
        		Library.damageSuit(((EntityPlayer)entity), 1);
        		Library.damageSuit(((EntityPlayer)entity), 2);
        		Library.damageSuit(((EntityPlayer)entity), 3);*/
        		
        	} else if(entity instanceof EntityCreeper) {
        		EntityNuclearCreeper creep = new EntityNuclearCreeper(p_149724_1_);
        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        		//creep.setRotationYawHead(((EntityCreeper)entity).rotationYawHead);
        		if(!entity.isDead)
        			if(!p_149724_1_.isRemote)
        					p_149724_1_.spawnEntityInWorld(creep);
        		entity.setDead();
        	} else if(entity instanceof EntityVillager) {
        		EntityZombie creep = new EntityZombie(p_149724_1_);
        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        		entity.setDead();
        		if(!p_149724_1_.isRemote)
        		p_149724_1_.spawnEntityInWorld(creep);
        	} else if(!(entity instanceof EntityNuclearCreeper) && !(entity instanceof EntityMooshroom) && !(entity instanceof EntityZombie)) {
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 30 * 20, 1));
        	}
    	}
    }
    
    @Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
        super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);

        if (this == ModBlocks.waste_trinitite || this == ModBlocks.waste_trinitite_red || this == ModBlocks.block_trinitite || this == ModBlocks.block_waste)
        {
            p_149734_1_.spawnParticle("townaura", p_149734_2_ + p_149734_5_.nextFloat(), p_149734_3_ + 1.1F, p_149734_4_ + p_149734_5_.nextFloat(), 0.0D, 0.0D, 0.0D);
        }
    }

}
