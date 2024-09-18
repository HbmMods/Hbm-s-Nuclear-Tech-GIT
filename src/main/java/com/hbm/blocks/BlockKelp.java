package com.hbm.blocks;

import java.util.Random;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockKelp extends Block {
    private IIcon[] icons = new IIcon[2]; 

    
    public BlockKelp() {
        super(Material.water);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (meta == 0 && side == 0) ? this.icons[0] : this.icons[1];
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icons[0] = iconRegister.registerIcon(RefStrings.MODID + ":laythe_kelp");
        this.icons[1] = iconRegister.registerIcon(RefStrings.MODID + ":laythe_kelp_top");
    }

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

    @Override
    public int getRenderType() {
        return 1; 
    }
    
    @Override
    public boolean canBlockStay(World world, int x, int y, int z) {
        Block below = world.getBlock(x, y - 1, z);
        return below == this || below == ModBlocks.laythe_silt;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        Block below = world.getBlock(x, y - 1, z);
        return below == this | below == ModBlocks.laythe_silt;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        this.updateBlockMeta(world, x, y, z);
    }

    private void updateBlockMeta(World world, int x, int y, int z) {
        Block above = world.getBlock(x, y + 1, z);
        if (above == this) {
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        } else {
            world.setBlockMetadataWithNotify(x, y, z, 1, 2); 

        }
    }
    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	//fixes the issue
    }

    @Override
    public Item getItemDropped(int parMetadata, Random parRand, int parFortune)  
    {
       return (ModItems.saltleaf);
    }
    @Override
    public int quantityDropped(int meta, int fortune, java.util.Random random) {
        return random.nextInt(4); 
    }


    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        if (!this.canBlockStay(world, x, y, z)) {
            world.setBlock(x, y, z, Blocks.water);
        } else {
            updateBlockMeta(world, x, y, z); 
        }
    }



}