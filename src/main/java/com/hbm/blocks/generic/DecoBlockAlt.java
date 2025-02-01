package com.hbm.blocks.generic;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.deco.TileEntityDecoBlockAltF;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DecoBlockAlt extends BlockContainer {
	
	public DecoBlockAlt(Material p_i45386_1_) {
		super(p_i45386_1_);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":code");
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		if(this == ModBlocks.statue_elb_f) return new TileEntityDecoBlockAltF();
		return null;
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
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
		if(i == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}
		if(i == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}
		if(i == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			
			if(player.getCurrentEquippedItem() != null) {
				boolean cracked = player.getHeldItem().getItem() == ModItems.key_red_cracked;
				
				if((player.getHeldItem().getItem() == ModItems.key_red || cracked)) {
					if(cracked) player.getHeldItem().stackSize--;
					world.func_147480_a(x, y, z, true);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		float f = 0.0625F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 8 * f, 1.0F);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float f = 0.0625F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 8 * f, 1.0F);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
}
