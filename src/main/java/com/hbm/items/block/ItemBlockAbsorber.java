package com.hbm.items.block;

import com.hbm.blocks.generic.BlockAbsorber;
import com.hbm.blocks.generic.BlockAbsorber.EnumAbsorberTier;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockAbsorber extends ItemBlock {

    public ItemBlockAbsorber(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        BlockAbsorber block = (BlockAbsorber)this.field_150939_a;
        EnumAbsorberTier tier = block.getTier(stack.getItemDamage());
        return StatCollector.translateToLocal("tile.rad_absorber." + tier.name().toLowerCase() + ".name");
    }
}

