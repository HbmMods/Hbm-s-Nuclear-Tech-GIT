package com.hbm.blocks.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.IBlockAccess;

public class BlockNTMTrapdoor extends BlockTrapDoor {
    public BlockNTMTrapdoor(Material material) {
        super(material);
    }

    @Override
    public boolean isLadder(IBlockAccess world, int x, int y, int z, EntityLivingBase entity) {
        int meta = world.getBlockMetadata(x, y, z);

        // isOpen
        if (!func_150118_d(meta))
            return false;
        
        Block blockBelow = world.getBlock(x, y - 1, z);
        return blockBelow != null && blockBelow.isLadder(world, x, y - 1, z, entity);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        super.setBlockBoundsBasedOnState(world, x, y, z);

        // Make the hitbox in-line with ladders
        if (isLadder(world, x, y, z, null)) {
            int meta = world.getBlockMetadata(x, y, z);
            float thickness = 0.125F;

            if ((meta & 3) == 0) {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - thickness, 1.0F, 1.0F, 1.0F);
            } else if ((meta & 3) == 1) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thickness);
            } else if ((meta & 3) == 2) {
                this.setBlockBounds(1.0F - thickness, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else if ((meta & 3) == 3) {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, thickness, 1.0F, 1.0F);
            }
        }
    }
}
