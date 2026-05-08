package com.hbm.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTileEntity extends BlockContainer {
    
    public BlockTileEntity(Material material) {
        super(material);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return null;
    }
}