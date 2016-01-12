package com.hbm.blocks;

import java.util.Random;

import com.hbm.entity.EntityNuclearCreeper;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class WasteEarth extends Block {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	private IIcon iconBottom;

	protected WasteEarth(Material p_i45394_1_) {
		super(p_i45394_1_);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_earth ? ":waste_earth_top" : ":frozen_grass_top"));
		this.iconBottom = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_earth ? ":waste_earth_bottom" : ":frozen_dirt"));
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + (this == ModBlocks.waste_earth ? ":waste_earth_side" : ":frozen_grass_side"));
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : (side == 0 ? this.iconBottom : this.blockIcon);
	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		if(this == ModBlocks.waste_earth)
		{
			return Item.getItemFromBlock(Blocks.dirt);
		}
		
		if(this == ModBlocks.frozen_grass)
		{
			return Items.snowball;
		}
		
		return null;
    }
    
    public int quantityDropped(Random p_149745_1_)
    {
    	return 1;
    }

    public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity entity)
    {
    	if (entity instanceof EntityLivingBase && this == ModBlocks.waste_earth)
    	{
    		if(entity instanceof EntityPlayer && Library.checkForHazmat((EntityPlayer)entity))
        	{
        		Library.damageSuit(((EntityPlayer)entity), 0);
        		Library.damageSuit(((EntityPlayer)entity), 1);
        		Library.damageSuit(((EntityPlayer)entity), 2);
        		Library.damageSuit(((EntityPlayer)entity), 3);
        		
        	} else if(entity instanceof EntityCreeper) {
        		EntityNuclearCreeper creep = new EntityNuclearCreeper(p_149724_1_);
        		creep.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        		creep.setRotationYawHead(((EntityCreeper)entity).rotationYawHead);
        		if(!entity.isDead)
        			if(!p_149724_1_.isRemote)
        					p_149724_1_.spawnEntityInWorld(creep);
        		entity.setDead();
        	} else if(!(entity instanceof EntityNuclearCreeper)) {
        		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 2 * 60 * 20, 2));
        	}
    	}
    	
    	if (entity instanceof EntityLivingBase && this == ModBlocks.frozen_grass)
    	{
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 60 * 20, 2));
    	}
    }
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
        super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);

        if (this == ModBlocks.waste_earth)
        {
            p_149734_1_.spawnParticle("townaura", (double)((float)p_149734_2_ + p_149734_5_.nextFloat()), (double)((float)p_149734_3_ + 1.1F), (double)((float)p_149734_4_ + p_149734_5_.nextFloat()), 0.0D, 0.0D, 0.0D);
        }
    }

}
