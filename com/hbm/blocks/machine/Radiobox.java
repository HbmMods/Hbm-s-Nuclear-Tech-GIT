package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityDiFurnace;
import com.hbm.tileentity.machine.TileEntityMachineBattery;
import com.hbm.tileentity.machine.TileEntityRadiobox;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Radiobox extends BlockContainer {

	public Radiobox(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityRadiobox();
	}

	@Override
	public int getRenderType() {
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
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		
		if(i == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			int meta = world.getBlockMetadata(x, y, z);
			if(meta <= 5) {
				world.setBlockMetadataWithNotify(x, y, z, meta + 4, 2);
				world.playSoundEffect(x, y, z, "hbm:block.reactorStart", 1.0F, 1.0F);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, meta - 4, 2);
				world.playSoundEffect(x, y, z, "hbm:block.reactorStart", 1.0F, 0.85F);
			}
			
			return true;
		} else {
			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_radiobox, world, x, y, z);
			return true;
		}
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
		int te = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        float f = 0.0625F;
        
        this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
        switch(te)
		{
		case 4:
		case 8:
	        this.setBlockBounds(11*f, 1*f, 4*f, 16*f, 15*f, 12*f);
            break;
		case 2:
		case 6:
	        this.setBlockBounds(4*f, 1*f, 11*f, 12*f, 15*f, 16*f);
            break;
		case 5:
		case 9:
	        this.setBlockBounds(0*f, 1*f, 4*f, 5*f, 15*f, 12*f);
            break;
		case 3:
		case 7:
	        this.setBlockBounds(4*f, 1*f, 0*f, 12*f, 15*f, 5*f);
            break;
		}
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		int te = world.getBlockMetadata(x, y, z);
        float f = 0.0625F;
        
        this.setBlockBounds(0.0F, 0.0F, 2*f, 1.0F, 1.0F, 14*f);
        switch(te)
		{
		case 4:
		case 8:
	        this.setBlockBounds(11*f, 1*f, 4*f, 16*f, 15*f, 12*f);
            break;
		case 2:
		case 6:
	        this.setBlockBounds(4*f, 1*f, 11*f, 12*f, 15*f, 16*f);
            break;
		case 5:
		case 9:
	        this.setBlockBounds(0*f, 1*f, 4*f, 5*f, 15*f, 12*f);
            break;
		case 3:
		case 7:
	        this.setBlockBounds(4*f, 1*f, 0*f, 12*f, 15*f, 5*f);
            break;
		}
        
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

}
