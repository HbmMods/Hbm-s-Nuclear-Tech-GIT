package com.hbm.blocks.siege;

import com.hbm.blocks.BlockBase;
import com.hbm.entity.mob.EntityFBI;
import com.hbm.tileentity.machine.TileEntityBaseMent;
import com.hbm.tileentity.machine.TileEntityCyberCrab;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;
import java.util.Random;


public class Basement extends BlockContainer {
    public Basement() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityBaseMent();
    }
}
    //@Override
    /*public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
        spawnFBI(world,10, x, y, z);
        return true;
    }*/

