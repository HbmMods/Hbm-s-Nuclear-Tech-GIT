package com.hbm.blocks.network;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.IBlockMulti;
import com.hbm.lib.Library;
import com.hbm.lib.RefStrings;
import com.hbm.render.block.RenderBoxDuct;
import com.hbm.tileentity.network.TileEntityCableBaseNT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class PowerCableBox extends BlockContainer implements IBlockMulti {

	@SideOnly(Side.CLIENT) public IIcon iconStraight;
	@SideOnly(Side.CLIENT) public IIcon[] iconEnd;
	@SideOnly(Side.CLIENT) public IIcon iconCurveTL;
	@SideOnly(Side.CLIENT) public IIcon iconCurveTR;
	@SideOnly(Side.CLIENT) public IIcon iconCurveBL;
	@SideOnly(Side.CLIENT) public IIcon iconCurveBR;
	@SideOnly(Side.CLIENT) public IIcon iconJunction;

	public PowerCableBox(Material mat) {
		super(mat);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCableBaseNT();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);

		iconEnd = new IIcon[5];

		iconStraight = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + "cable" + "_straight");
		for(int i = 0; i < 5; i++) iconEnd[i] = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + "cable" + "_end_" + i);
		iconCurveTL = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + "cable" + "_curve_tl");
		iconCurveTR = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + "cable" + "_curve_tr");
		iconCurveBL = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + "cable" + "_curve_bl");
		iconCurveBR = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + "cable" + "_curve_br");
		iconJunction = iconRegister.registerIcon(RefStrings.MODID + ":boxduct_" + "cable" + "_junction");
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {

		TileEntity te = world.getTileEntity(x, y, z);

		boolean nX = canConnectTo(world, x, y, z, Library.NEG_X, te);
		boolean pX = canConnectTo(world, x, y, z, Library.POS_X, te);
		boolean nY = canConnectTo(world, x, y, z, Library.NEG_Y, te);
		boolean pY = canConnectTo(world, x, y, z, Library.POS_Y, te);
		boolean nZ = canConnectTo(world, x, y, z, Library.NEG_Z, te);
		boolean pZ = canConnectTo(world, x, y, z, Library.POS_Z, te);

		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);
		int count = 0 + (pX ? 1 : 0) + (nX ? 1 : 0) + (pY ? 1 : 0) + (nY ? 1 : 0) + (pZ ? 1 : 0) + (nZ ? 1 : 0);

		int meta = world.getBlockMetadata(x, y, z);

		if((mask & 0b001111) == 0 && mask > 0) {
			return (side == 4 || side == 5) ? iconEnd[meta] : iconStraight;
		} else if((mask & 0b111100) == 0 && mask > 0) {
			return (side == 2 || side == 3) ? iconEnd[meta] : iconStraight;
		} else if((mask & 0b110011) == 0 && mask > 0) {
			return (side == 0 || side == 1) ? iconEnd[meta] : iconStraight;
		} else {

			if(side == 0 && nY || side == 1 && pY || side == 2 && nZ || side == 3 && pZ || side == 4 && nX || side == 5 && pX)
				return iconEnd[meta];

			if(count == 2) {
				if(side == 1 && nY || side == 0 && pY || side == 3 && nZ || side == 2 && pZ || side == 5 && nX || side == 4 && pX)
					return iconStraight;

				if(nY && pZ) return side == 4 ? iconCurveBR : iconCurveBL;
				if(nY && nZ) return side == 5 ? iconCurveBR : iconCurveBL;
				if(nY && pX) return side == 3 ? iconCurveBR : iconCurveBL;
				if(nY && nX) return side == 2 ? iconCurveBR : iconCurveBL;
				if(pY && pZ) return side == 4 ? iconCurveTR : iconCurveTL;
				if(pY && nZ) return side == 5 ? iconCurveTR : iconCurveTL;
				if(pY && pX) return side == 3 ? iconCurveTR : iconCurveTL;
				if(pY && nX) return side == 2 ? iconCurveTR : iconCurveTL;

				if(pX && nZ) return side == 0 ? iconCurveTR : iconCurveTR;
				if(pX && pZ) return side == 0 ? iconCurveBR : iconCurveBR;
				if(nX && nZ) return side == 0 ? iconCurveTL : iconCurveTL;
				if(nX && pZ) return side == 0 ? iconCurveBL : iconCurveBL;
			}
		}

		return iconJunction;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 5; ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	public int damageDropped(int meta) {
		return meta % 5;
	}

	@Override
	public int getRenderType() {
		return RenderBoxDuct.renderID;
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
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB entityBounding, List list, Entity entity) {

		List<AxisAlignedBB> bbs = new ArrayList<>();

		TileEntity te = world.getTileEntity(x, y, z);

		double lower = 0.125D;
		double upper = 0.875D;
		int meta = world.getBlockMetadata(x, y, z);

		for(int i = 0; i < 5; i++) {

			if(meta > i) {
				lower += 0.0625D;
				upper -= 0.0625D;
			}
		}

		boolean nX = canConnectTo(world, x, y, z, Library.NEG_X, te);
		boolean pX = canConnectTo(world, x, y, z, Library.POS_X, te);
		boolean nY = canConnectTo(world, x, y, z, Library.NEG_Y, te);
		boolean pY = canConnectTo(world, x, y, z, Library.POS_Y, te);
		boolean nZ = canConnectTo(world, x, y, z, Library.NEG_Z, te);
		boolean pZ = canConnectTo(world, x, y, z, Library.POS_Z, te);
		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);

		if(mask == 0) {
			bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + lower, x + upper, y + upper, z + upper));
		} else if(mask == 0b100000 || mask == 0b010000 || mask == 0b110000) {
			bbs.add(AxisAlignedBB.getBoundingBox(x + 0.0D, y + lower, z + lower, x + 1.0D, y + upper, z + upper));
		} else if(mask == 0b001000 || mask == 0b000100 || mask == 0b001100) {
			bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + 0.0D, z + lower, x + upper, y + 1.0D, z + upper));
		} else if(mask == 0b000010 || mask == 0b000001 || mask == 0b000011) {
			bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + 0.0D, x + upper, y + upper, z + 1.0D));
		} else {
			bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + lower, x + upper, y + upper, z + upper));

			if(pX) bbs.add(AxisAlignedBB.getBoundingBox(x + upper, y + lower, z + lower, x + 1.0D, y + upper, z + upper));
			if(nX) bbs.add(AxisAlignedBB.getBoundingBox(x + 0.0D, y + lower, z + lower, x + lower, y + upper, z + upper));
			if(pY) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + upper, z + lower, x + upper, y + 1.0D, z + upper));
			if(nY) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + 0.0D, z + lower, x + upper, y + lower, z + upper));
			if(pZ) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + upper, x + upper, y + upper, z + 1.0D));
			if(nZ) bbs.add(AxisAlignedBB.getBoundingBox(x + lower, y + lower, z + 0.0D, x + upper, y + upper, z + lower));
		}

		for(AxisAlignedBB bb : bbs) {
			if(entityBounding.intersectsWith(bb)) {
				list.add(bb);
			}
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

		TileEntity te = world.getTileEntity(x, y, z);

		float lower = 0.125F;
		float upper = 0.875F;
		int meta = world.getBlockMetadata(x, y, z);

		for(int i = 0; i < 5; i++) {

			if(meta > i) {
				lower += 0.0625F;
				upper -= 0.0625F;
			}
		}

		boolean nX = canConnectTo(world, x, y, z, Library.NEG_X, te);
		boolean pX = canConnectTo(world, x, y, z, Library.POS_X, te);
		boolean nY = canConnectTo(world, x, y, z, Library.NEG_Y, te);
		boolean pY = canConnectTo(world, x, y, z, Library.POS_Y, te);
		boolean nZ = canConnectTo(world, x, y, z, Library.NEG_Z, te);
		boolean pZ = canConnectTo(world, x, y, z, Library.POS_Z, te);
		int mask = 0 + (pX ? 32 : 0) + (nX ? 16 : 0) + (pY ? 8 : 0) + (nY ? 4 : 0) + (pZ ? 2 : 0) + (nZ ? 1 : 0);

		if(mask == 0) {
			this.setBlockBounds(lower, lower, lower, upper, upper, upper);
		} else if(mask == 0b100000 || mask == 0b010000 || mask == 0b110000) {
			this.setBlockBounds(0F, lower, lower, 1F, upper, upper);
		} else if(mask == 0b001000 || mask == 0b000100 || mask == 0b001100) {
			this.setBlockBounds(lower, 0F, lower, upper, 1F, upper);
		} else if(mask == 0b000010 || mask == 0b000001 || mask == 0b000011) {
			this.setBlockBounds(lower, lower, 0F, upper, upper, 1F);
		} else {

			this.setBlockBounds(
					nX ? 0F : lower,
					nY ? 0F : lower,
					nZ ? 0F : lower,
					pX ? 1F : upper,
					pY ? 1F : upper,
					pZ ? 1F : upper);
		}
	}

	public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection dir, TileEntity tile) {
		if(tile instanceof TileEntityCableBaseNT) {
			return Library.canConnect(world, x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ, dir);
		}
		return false;
	}

	@Override
	public int getSubCount() {
		return 1;
	}

}
