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
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		int meta = stack.getItemDamage();

		if(i % 2 == 0) {
			world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		} else {
			world.setBlockMetadataWithNotify(x, y, z, meta + 8, 2);
		}
	}

	@Override
	public int damageDropped(int meta) {
		return rectify(meta) & 7;
	}

	@Override
	public int getSubCount() {
		return variants.length;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		int te = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
		float f = 0.0625F;
		
		if((te & 8) != 0)
			this.setBlockBounds(2 * f, 0.0F, 0.0F, 14 * f, 1.0F, 1.0F);
		else
			this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {

		int te = world.getBlockMetadata(x, y, z);
		float f = 0.0625F;
		
		if((te & 8) != 0)
			this.setBlockBounds(2 * f, 0.0F, 0.0F, 14 * f, 1.0F, 1.0F);
		else
			this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);

		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}
}
