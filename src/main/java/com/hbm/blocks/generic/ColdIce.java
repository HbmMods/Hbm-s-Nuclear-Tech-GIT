package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.handler.RogueWorldHandler;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerRogue;
import com.hbm.potion.HbmPotion;
import com.hbm.saveddata.RogueWorldSaveData;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ColdIce extends BlockIce {
	
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconBottom;

	public ColdIce(boolean tick) {
		super();
	    this.setTickRandomly(tick);
	    //this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
	    //this.setLightOpacity(255);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		//this.iconTop = iconRegister.registerIcon("minecraft" + ":ice");
		this.blockIcon = Blocks.ice.getIcon(0, 0);
	}

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return AxisAlignedBB.getBoundingBox((double)(p_149668_2_ + 0), (double)(p_149668_3_ + 0), (double)(p_149668_4_ + 0), (double)(p_149668_2_ + 1), (double)(p_149668_3_ + 1), (double)(p_149668_4_ + 1));
    }
	
	/*@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : this.blockIcon;
	}*/

    //public boolean isOpaqueCube()
    //{
    //    return false;
    //}

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

	
	/*@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
		if(this == ModBlocks.frozen_grass)
		{
			return Items.snowball;
		}
		
		return Item.getItemFromBlock(this);
    }*/
    
/*    @Override
	public int quantityDropped(Random p_149745_1_)
    {
    	return 1;
    }
*/
    @Override
	public void onEntityWalking(World p_149724_1_, int p_149724_2_, int p_149724_3_, int p_149724_4_, Entity entity)
    {
    	if (entity instanceof EntityLivingBase) {
    	
    		((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 60 * 20, 2));
    	}
    }
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
    	super.updateTick(world, x, y, z, rand);
    	RogueWorldSaveData data = RogueWorldSaveData.forWorld(world);
		float temp = ModEventHandlerRogue.getTemperatureAtDepth(y, world);
		if(temp <0)
		{
			for(int i = -1; i < 2; i++) {
				for(int j = -1; j < 2; j++) {
					for(int k = -1; k < 2; k++) {
						RogueWorldHandler.freeze(world, x+i, y+j, z+k, temp);
					}
				}
			}
		}
    }
    
    /*@Override
    public int tickRate(World world) {
    	
    	if(this.radIn > 0)
    		return 20;
    	
    	return 100;
    }
    
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
    	
    	if(this.radIn > 0)
        	world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }*/

}
