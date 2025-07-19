package com.hbm.blocks.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        // Make the hitbox in-line with ladders, if relevant
        if (isLadder(world, x, y, z, null)) {
            int meta = world.getBlockMetadata(x, y, z);
            float thickness = 0.125F;

            if ((meta & 3) == 0)
                return AxisAlignedBB.getBoundingBox(x, y, z + 1F - thickness, x + 1F, y + 1F, z + 1F);
            
            if ((meta & 3) == 1)
                return AxisAlignedBB.getBoundingBox(x, y, z, x + 1F, y + 1F, z + thickness);
            
            if ((meta & 3) == 2)
                return AxisAlignedBB.getBoundingBox(x + 1F - thickness, y, z, x + 1F, y + 1F, z + 1F);
            
            if ((meta & 3) == 3)
                return AxisAlignedBB.getBoundingBox(x, y, z, x + thickness, y + 1F, z + 1F);
        }

        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        // The original code prevented manual operation of Material.iron trapdoors. This bypasses that behavior
        int meta = world.getBlockMetadata(x, y, z);
        world.setBlockMetadataWithNotify(x, y, z, meta ^ 4, 2);
        world.playAuxSFXAtEntity(player, 1003, x, y, z, 0);
        return true;
    }
}
