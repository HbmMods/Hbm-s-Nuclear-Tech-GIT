package com.hbm.blocks.generic;

import com.hbm.blocks.BlockMulti;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockScaffold extends BlockMulti {
	
	protected String[] variants = new String[] {"scaffold_steel", "scaffold_red", "scaffold_white", "scaffold_yellow"};
	@SideOnly(Side.CLIENT) protected IIcon[] icons;

	public BlockScaffold() {
		super(Material.iron);
	}

	public static int renderIDScaffold = RenderingRegistry.getNextAvailableRenderId();
	
	@Override
	public int getRenderType(){
		return renderIDScaffold;
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
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		this.blockIcon = ModBlocks.deco_steel.getIcon(0, 0);
		this.icons = new IIcon[variants.length];
		
		for(int i = 0; i < variants.length; i++) {
			this.icons[i] = reg.registerIcon(RefStrings.MODID + ":" + variants[i]);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return this.icons[this.damageDropped(meta)];
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fx, float fy, float fz, int meta) {
		return side;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		ForgeDirection placed = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
		int meta = stack.getItemDamage();
		
		if(placed == ForgeDirection.UP || placed == ForgeDirection.DOWN) {
			int rot = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
			if(rot % 2 == 0) {
				world.setBlockMetadataWithNotify(x, y, z, meta, 2);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, meta + 8, 2);
			}
		} else if(placed == ForgeDirection.NORTH || placed == ForgeDirection.SOUTH) {
			world.setBlockMetadataWithNotify(x, y, z, meta + 4, 2);
		} else {
			world.setBlockMetadataWithNotify(x, y, z, meta + 12, 2);
		}
	}

	@Override
	public int damageDropped(int meta) {
		return rectify(meta);
	}

	@Override
	public int getSubCount() {
		return variants.length;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		float f = 0.0625F;
		
		if(meta >= 12) {
			this.setBlockBounds(0.0F, 2 * f, 0.0F, 1.0F, 14 * f, 1.0F);
		} else if(meta >= 8) {
			this.setBlockBounds(2 * f, 0.0F, 0.0F, 14 * f, 1.0F, 1.0F);
		} else if(meta >= 4) {
			this.setBlockBounds(0.0F, 2 * f, 0.0F, 1.0F, 14 * f, 1.0F);
		} else {
			this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
}
