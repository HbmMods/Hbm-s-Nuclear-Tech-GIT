package com.hbm.blocks.bomb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.potion.PotionEffectTaint;
import com.hbm.tileentity.deco.TileEntityTaint;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockTaint extends BlockContainer {
	
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

	public BlockTaint(Material p_i45386_1_) {
		super(p_i45386_1_);
        this.setTickRandomly(true);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityTaint();
	}
	
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int meta)
    {
        return this.icons[meta % this.icons.length];
    }
    
    public int damageDropped(int meta)
    {
        return 0;
    }

    public static int func_150032_b(int p_150032_0_)
    {
        return func_150031_c(p_150032_0_);
    }

    public static int func_150031_c(int p_150031_0_)
    {
        return p_150031_0_ & 15;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_)
    {
        for (int i = 0; i < 16; ++i)
        {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.icons = new IIcon[16];

        for (int i = 0; i < this.icons.length; ++i)
        {
            this.icons[i] = p_149651_1_.registerIcon("hbm:taint_" + i);
        }
    }

    public MapColor getMapColor(int p_149728_1_)
    {
        return MapColor.purpleColor;
    }
	
	@Override
	public int getRenderType(){
		return -1;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
    
    public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
    {
    	if(!hasPosNeightbour(world, x, y, z) && !world.isRemote)
			world.setBlockToAir(x, y, z);
    }

    public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	if(!world.isRemote && meta < 15) {
    		
	    	for(int i = 0; i < 15; i++) {
	    		int a = rand.nextInt(11) + x - 5;
	    		int b = rand.nextInt(11) + y - 5;
	    		int c = rand.nextInt(11) + z - 5;
	            if(world.getBlock(a, b, c).isReplaceable(world, a, b, c) && hasPosNeightbour(world, a, b, c))
	            	world.setBlock(a, b, c, ModBlocks.taint, meta + 1, 2);
	    	}
	            
		    	for(int i = 0; i < 85; i++) {
		    		int a = rand.nextInt(7) + x - 3;
		    		int b = rand.nextInt(7) + y - 3;
		    		int c = rand.nextInt(7) + z - 3;
		            if(world.getBlock(a, b, c).isReplaceable(world, a, b, c) && hasPosNeightbour(world, a, b, c))
		            	world.setBlock(a, b, c, ModBlocks.taint, meta + 1, 2);
		    	}
    	}
    }
    
    public static boolean hasPosNeightbour(World world, int x, int y, int z) {
    	Block b0 = world.getBlock(x + 1, y, z);
    	Block b1 = world.getBlock(x, y + 1, z);
    	Block b2 = world.getBlock(x, y, z + 1);
    	Block b3 = world.getBlock(x - 1, y, z);
    	Block b4 = world.getBlock(x, y - 1, z);
    	Block b5 = world.getBlock(x, y, z - 1);
    	boolean b = (b0.renderAsNormalBlock() && b0.getMaterial().isOpaque()) ||
    			(b1.renderAsNormalBlock() && b1.getMaterial().isOpaque()) ||
    			(b2.renderAsNormalBlock() && b2.getMaterial().isOpaque()) ||
    			(b3.renderAsNormalBlock() && b3.getMaterial().isOpaque()) ||
    			(b4.renderAsNormalBlock() && b4.getMaterial().isOpaque()) ||
    			(b5.renderAsNormalBlock() && b5.getMaterial().isOpaque());
    	return b;
    }
    
    @Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2, par3, par4, par2, par3, par4);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    	List<ItemStack> list = new ArrayList<ItemStack>();
    	PotionEffect effect = new PotionEffect(PotionEffectTaint.instance.id, 15 * 20, 0);
    	effect.setCurativeItems(list);
    	if(entity instanceof EntityLivingBase)
    	((EntityLivingBase)entity).addPotionEffect(effect);
	}

}
