package com.hbm.blocks.generic;

import com.hbm.lib.ModDamageSource;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class Spikes extends Block {

    public Spikes(Material mat) {
        super(mat);
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
    
    public static int renderID = RenderingRegistry.getNextAvailableRenderId();

    public int getRenderType()
    {
        return renderID;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
	}
    
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity ent) {
    	
    	if(ent instanceof EntityLivingBase && ent.motionY < -0.1) {
    		if(ent.attackEntityFrom(ModDamageSource.spikes, 100))
    			world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, "hbm:entity.slicer", 1.0F, 1.0F);
    	}
    }

}
