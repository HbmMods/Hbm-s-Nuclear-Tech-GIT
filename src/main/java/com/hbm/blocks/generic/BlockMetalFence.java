package com.hbm.blocks.generic;

import java.util.List;

import com.hbm.blocks.IBlockMulti;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockMetalFence extends BlockFence implements IBlockMulti {

	public IIcon postIcon;

	public BlockMetalFence(Material mat) {
		super("", mat);
	}

	public static int renderID = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType() {
		return renderID;
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
	public int damageDropped(int meta) {
		return rectify(meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return meta == 0 ? this.blockIcon : this.postIcon;
	}

	public String getUnlocalizedName(ItemStack stack) {
		return stack.getItemDamage() == 1 ? getUnlocalizedName() + "_post" : getUnlocalizedName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(this.getTextureName());
		this.postIcon = iconRegister.registerIcon(this.getTextureName() + "_post");
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		boolean flag = this.canConnectFenceTo(world, x, y, z - 1);
		boolean flag1 = this.canConnectFenceTo(world, x, y, z + 1);
		boolean flag2 = this.canConnectFenceTo(world, x - 1, y, z);
		boolean flag3 = this.canConnectFenceTo(world, x + 1, y, z);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;

		if(flag) {
			f2 = 0.0F;
		}

		if(flag1) {
			f3 = 1.0F;
		}

		if(flag || flag1) {
			this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
			addCol(world, x, y, z, aabb, list, entity);
		}

		f2 = 0.375F;
		f3 = 0.625F;

		if(flag2) {
			f = 0.0F;
		}

		if(flag3) {
			f1 = 1.0F;
		}

		if(flag2 || flag3 || !flag && !flag1) {
			this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
			addCol(world, x, y, z, aabb, list, entity);
		}

		if(flag) {
			f2 = 0.0F;
		}

		if(flag1) {
			f3 = 1.0F;
		}

		this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	private void addCol(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity) {
		AxisAlignedBB axisalignedbb1 = this.getCollisionBoundingBoxFromPool(world, x, y, z);

		if(axisalignedbb1 != null && aabb.intersectsWith(axisalignedbb1)) {
			list.add(axisalignedbb1);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < getSubCount(); ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public int getSubCount() {
		return 2;
	}

}
