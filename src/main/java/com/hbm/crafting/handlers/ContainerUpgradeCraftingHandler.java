package com.hbm.crafting.handlers;

import java.util.ArrayList;

import com.hbm.blocks.generic.BlockStorageCrate;
import com.hbm.blocks.machine.BlockMassStorage;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Handles upgrading storage crates and mass storage blocks, preserving their contents.
 * 
 * Note: this assumes the input and the output items store their inventory in the same format
 * in the NBT
 */
public class ContainerUpgradeCraftingHandler extends ShapedOreRecipe {

    public ContainerUpgradeCraftingHandler(ItemStack result, Object... items){
        super(result, items);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {

        ItemStack source = getFirstContainer(inventoryCrafting);
        ItemStack result = super.getCraftingResult(inventoryCrafting);

        result.setTagCompound(source.getTagCompound());

        return result;
    }

    private static ItemStack getFirstContainer(InventoryCrafting inventoryCrafting) {
        for (int i = 0; i < 9; ++i) {
            ItemStack itemstack = inventoryCrafting.getStackInRowAndColumn(i % 3, i / 3);
            if (itemstack == null)
                continue;
            
            Block block = Block.getBlockFromItem(itemstack.getItem());
            if (block == null)
                continue;
            
            if (block instanceof BlockStorageCrate || block instanceof BlockMassStorage)
                return itemstack;
        }
        return null;
    }

}
