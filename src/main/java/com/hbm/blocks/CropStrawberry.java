package com.hbm.blocks;

import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;

public class CropStrawberry extends BlockCrop {
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(int parMetadata, int parFortune, Random parRand)
    {
        return (parMetadata/2);
    }

    @Override
    public Item getItemDropped(int parMetadata, Random parRand, int parFortune)  
    {
     // DEBUG
     System.out.println("am2");
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
          iIcon[3] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_3");
          iIcon[4] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_4");
          iIcon[5] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_5");
         // iIcon[6] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_5");
          //iIcon[7] = parIIconRegister.registerIcon(RefStrings.MODID + ":strawberry_5");
    }
}
