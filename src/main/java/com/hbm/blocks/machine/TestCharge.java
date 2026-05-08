package com.hbm.blocks.machine;

import com.hbm.blocks.BlockTileEntity;
import com.hbm.tileentity.machine.TestObjTester;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TestCharge extends BlockTileEntity {

    public TestCharge(Material material) {
        super(material);
        this.setHardness(2.5F);
        this.setResistance(10.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TestObjTester();
    }
}