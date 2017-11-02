package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityNuclearCreeper;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockOre extends Block {

	public BlockOre(Material p_i45394_1_) {
		super(p_i45394_1_);
		//if(this == ModBlocks.block_meteor_molten)
	    this.setTickRandomly(true);
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j)
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
			return rand.nextInt(2) == 0 ? ModItems.powder_fire : Items.blaze_powder;
		}
		if(this == ModBlocks.block_meteor)
		{
			return rand.nextInt(10) == 0 ? ModItems.plate_dalekanium : Item.getItemFromBlock(ModBlocks.block_meteor);
		}
		if(this == ModBlocks.block_meteor_cobble)
		{
			return ModItems.fragment_meteorite;
		}
		if(this == ModBlocks.block_meteor_broken)
		{
			return ModItems.fragment_meteorite;
		}
		if(this == ModBlocks.block_meteor_treasure)
		{
			switch(rand.nextInt(35)) {
			case 0: return ModItems.coil_advanced_alloy;
			case 1: return ModItems.plate_advanced_alloy;
			case 2: return ModItems.powder_desh_mix;
			case 3: return ModItems.ingot_desh;
			case 4: return ModItems.fusion_core;
			case 5: return ModItems.battery_lithium_cell;
			case 6: return ModItems.battery_schrabidium;
			case 7: return ModItems.nugget_schrabidium;
			case 8: return ModItems.ingot_pu238;
			case 9: return ModItems.ingot_pu239;
			case 10: return ModItems.ingot_u235;
			case 11: return ModItems.turbine_tungsten;
			case 12: return ModItems.ingot_dura_steel;
			case 13: return ModItems.ingot_polymer;
			case 14: return ModItems.ingot_tungsten;
			case 15: return ModItems.ingot_combine_steel;
			case 16: return ModItems.ingot_lanthanium;
			case 17: return ModItems.ingot_actinium;
			case 18: return Item.getItemFromBlock(ModBlocks.block_meteor);
			case 19: return Item.getItemFromBlock(ModBlocks.fusion_heater);
			case 20: return Item.getItemFromBlock(ModBlocks.fusion_core);
			case 21: return Item.getItemFromBlock(ModBlocks.watz_element);
			case 22: return Item.getItemFromBlock(ModBlocks.ore_rare);
			case 23: return Item.getItemFromBlock(ModBlocks.fusion_conductor);
			case 24: return Item.getItemFromBlock(ModBlocks.reactor_computer);
			case 25: return Item.getItemFromBlock(ModBlocks.machine_diesel);
			case 26: return Item.getItemFromBlock(ModBlocks.machine_rtg_grey);
			case 27: return ModItems.pellet_rtg;
			case 28: return ModItems.pellet_rtg_weak;
			case 29: return ModItems.rtg_unit;
			case 30: return ModItems.gun_spark_ammo;
			case 31: return ModItems.gun_fatman_ammo;
			case 32: return ModItems.gun_mirv_ammo;
			case 33: return ModItems.gun_defabricator_ammo;
			case 34: return ModItems.gun_osipr_ammo2;
			}
		}
		if(this == ModBlocks.ore_rare)
		{
			switch(rand.nextInt(6)) {
			case 0: return ModItems.fragment_actinium;
			case 1: return ModItems.fragment_cerium;
			case 2: return ModItems.fragment_cobalt;
			case 3: return ModItems.fragment_lanthanium;
			case 4: return ModItems.fragment_neodymium;
			case 5: return ModItems.fragment_niobium;
			}
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
		if(this == ModBlocks.ore_rare)
		{
			return 2 + p_149745_1_.nextInt(4);
		}
		if(this == ModBlocks.block_meteor_broken)
		{
			return 1 + p_149745_1_.nextInt(3);
		}
		if(this == ModBlocks.block_meteor_treasure)
		{
			return 1 + p_149745_1_.nextInt(3);
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
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 30 * 20, 2));
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.id, 5 * 20, 0));
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 15 * 20, 0));
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
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 60 * 20, 2));
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 30 * 20, 1));
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.id, 10 * 20, 0));
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 20 * 20, 0));
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
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 5 * 20, 1));
        	}
    	}
    	
        if(this == ModBlocks.block_meteor_molten)
        	entity.setFire(5);
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

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (world.getBlock(x, y - 1, z) == ModBlocks.ore_oil_empty)
        {
        	world.setBlock(x, y, z, ModBlocks.ore_oil_empty);
        	world.setBlock(x, y - 1, z, ModBlocks.ore_oil);
        }
    }
	
    @Override
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if(this == ModBlocks.block_meteor_molten && rand.nextInt(3) == 0) {
        	if(!world.isRemote)
        		world.setBlock(x, y, z, ModBlocks.block_meteor_cobble);
        	world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
        }
    }

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int i) {

        if(this == ModBlocks.block_meteor_molten) {
        	if(!world.isRemote)
        		world.setBlock(x, y, z, Blocks.lava);
        }
	}
}
