package com.hbm.items.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockBaseColored extends ItemBlockBase {
	
	private Block block;
	
	public ItemBlockBaseColored(Block block) { 
		super(block);
		this.block = block;
		}
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int p_82790_2_)
    {
        return this.block.getRenderColor(stack.getItemDamage());
    }

}
