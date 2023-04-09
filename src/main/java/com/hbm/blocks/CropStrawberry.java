package com.hbm.blocks;

import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class CropStrawberry extends BlockCrop {
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(int parMetadata, int parFortune, Random parRand)
    {
    	if(parMetadata == 7) { //dividing is probably better, but thats the point?? plus i want players to fully grow their crops
    		return(4);
    	}
    	else {
            return (parMetadata/2);	
    	}
    }

    @Override
    public Item getItemDropped(int parMetadata, Random parRand, int parFortune)  
    {
       return (ModItems.strawberry);
    }
    
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister parIIconRegister)
    {
          iIcon = new IIcon[maxGrowthStage+1];
          // seems that crops like to have 8 growth icons, but okay to repeat actual texture if you want
          // to make generic should loop to maxGrowthStage
          iIcon[0] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_1");
          iIcon[1] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_1");
          iIcon[2] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_2");
          iIcon[3] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_2");
          iIcon[4] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_3");
          iIcon[5] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_3");
          iIcon[6] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_4");
          iIcon[7] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_5");
         // iIcon[6] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_5");
          //iIcon[7] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_5");
    }
}
