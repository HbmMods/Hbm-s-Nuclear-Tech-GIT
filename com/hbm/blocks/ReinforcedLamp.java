package com.hbm.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ReinforcedLamp extends Block {
	
    private final boolean field_150171_a;
    private static final String __OBFID = "CL_00000297";

    public ReinforcedLamp(Material mat, boolean p_i45421_1_)
    {
        super(mat);
        this.field_150171_a = p_i45421_1_;

        if (p_i45421_1_)
        {
            this.setLightLevel(1.0F);
        }
    }

    @Override
	public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
    {
        if (!p_149726_1_.isRemote)
        {
            if (this.field_150171_a && !p_149726_1_.isBlockIndirectlyGettingPowered(p_149726_2_, p_149726_3_, p_149726_4_))
            {
                p_149726_1_.scheduleBlockUpdate(p_149726_2_, p_149726_3_, p_149726_4_, this, 4);
            }
            else if (!this.field_150171_a && p_149726_1_.isBlockIndirectlyGettingPowered(p_149726_2_, p_149726_3_, p_149726_4_))
            {
                p_149726_1_.setBlock(p_149726_2_, p_149726_3_, p_149726_4_, ModBlocks.reinforced_lamp_on, 0, 2);
            }
        }
    }

    @Override
	public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        if (!p_149695_1_.isRemote)
        {
            if (this.field_150171_a && !p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_))
            {
                p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, 4);
            }
            else if (!this.field_150171_a && p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_))
            {
                p_149695_1_.setBlock(p_149695_2_, p_149695_3_, p_149695_4_, ModBlocks.reinforced_lamp_on, 0, 2);
            }
        }
    }

    @Override
	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        if (!p_149674_1_.isRemote && this.field_150171_a && !p_149674_1_.isBlockIndirectlyGettingPowered(p_149674_2_, p_149674_3_, p_149674_4_))
        {
            p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, ModBlocks.reinforced_lamp_off, 0, 2);
        }
    }

    @Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return Item.getItemFromBlock(ModBlocks.reinforced_lamp_off);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(ModBlocks.reinforced_lamp_off);
    }

    @Override
	protected ItemStack createStackedBlock(int p_149644_1_)
    {
        return new ItemStack(ModBlocks.reinforced_lamp_off);
    }
}
