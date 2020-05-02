package com.hbm.items.block;

import java.util.List;

import com.hbm.blocks.bomb.BlockTaint;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemTaintBlock extends ItemBlock
{
    public ItemTaintBlock(Block p_i45358_1_)
    {
        super(p_i45358_1_);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_)
    {
        return this.field_150939_a.func_149735_b(2, BlockTaint.func_150032_b(p_77617_1_));
    }

    public int getMetadata(int p_77647_1_)
    {
        return p_77647_1_;
    }
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean bool)
	{
		list.add("DO NOT TOUCH, BREATHE OR STARE AT.");
	}
}
