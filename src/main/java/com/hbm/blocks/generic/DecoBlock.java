package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.deco.TileEntityDecoBlock;
import com.hbm.world.gen.nbt.INBTBlockTransformable;

import api.hbm.block.IToolable;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DecoBlock extends BlockContainer implements IToolable, INBTBlockTransformable {

	Random rand = new Random();

	public DecoBlock(Material p_i45386_1_) {
		super(p_i45386_1_);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, int side, float fX, float fY, float fZ, ToolType tool) {
		if(tool != ToolType.SCREWDRIVER) return false;
		if(this != ModBlocks.steel_wall && this != ModBlocks.steel_corner) return false;

		int meta = world.getBlockMetadata(x, y, z);

		if(!player.isSneaking()) {
			if(meta == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 3);
			else if(meta == 4) world.setBlockMetadataWithNotify(x, y, z, 2, 3);
			else if(meta == 2) world.setBlockMetadataWithNotify(x, y, z, 5, 3);
			else if(meta == 5) world.setBlockMetadataWithNotify(x, y, z, 3, 3);
		} else {
			if(meta == 3) world.setBlockMetadataWithNotify(x, y, z, 5, 3);
			else if(meta == 4) world.setBlockMetadataWithNotify(x, y, z, 3, 3);
			else if(meta == 2) world.setBlockMetadataWithNotify(x, y, z, 4, 3);
			else if(meta == 5) world.setBlockMetadataWithNotify(x, y, z, 2, 3);
		}

		return true;
	}

	@Override @SideOnly(Side.CLIENT) public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) { return true; }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		if(this == ModBlocks.steel_scaffold || this == ModBlocks.steel_beam || this == ModBlocks.steel_wall || this == ModBlocks.steel_corner) return null;
		return new TileEntityDecoBlock();
	}

	public static int renderIDBeam = RenderingRegistry.getNextAvailableRenderId();
	public static int renderIDWall = RenderingRegistry.getNextAvailableRenderId();
	public static int renderIDCorner = RenderingRegistry.getNextAvailableRenderId();

	@Override
	public int getRenderType(){
		if(this == ModBlocks.steel_wall) return renderIDWall;
		if(this == ModBlocks.steel_corner) return renderIDCorner;
		if(this == ModBlocks.steel_beam) return renderIDBeam;
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
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return Item.getItemFromBlock(this);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemStack) {
		int i = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if(i == 0) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		if(i == 1) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		if(i == 2) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		if(i == 3) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		int te = world.getBlockMetadata(x, y, z);
		float f = 0.0625F;

		if(this == ModBlocks.steel_wall) {
			switch(te) {
			case 4: this.setBlockBounds(14 * f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F); break;
			case 2: this.setBlockBounds(0.0F, 0.0F, 14 * f, 1.0F, 1.0F, 1.0F); break;
			case 5: this.setBlockBounds(0.0F, 0.0F, 0.0F, 2 * f, 1.0F, 1.0F); break;
			case 3: this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 2 * f); break;
			}
		}

		if(this == ModBlocks.steel_corner) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}

		if(this == ModBlocks.steel_roof) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1 * f, 1.0F);
		}

		if(this == ModBlocks.steel_beam) {
			this.setBlockBounds(7 * f, 0.0F, 7 * f, 9 * f, 1.0F, 9 * f);
		}

		if(this == ModBlocks.steel_scaffold) {
			this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f);
			switch(te) {
			case 4: this.setBlockBounds(2 * f, 0.0F, 0.0F, 14 * f, 1.0F, 1.0F); break;
			case 2: this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f); break;
			case 5: this.setBlockBounds(2 * f, 0.0F, 0.0F, 14 * f, 1.0F, 1.0F); break;
			case 3: this.setBlockBounds(0.0F, 0.0F, 2 * f, 1.0F, 1.0F, 14 * f); break;
			}
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		this.setBlockBoundsBasedOnState(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x + this.minX, y + this.minY, z + this.minZ, x + this.maxX, y + this.maxY, z + this.maxZ);
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity collider) {

		if(this == ModBlocks.steel_corner) {
			int meta = world.getBlockMetadata(x, y, z);
			List<AxisAlignedBB> bbs = new ArrayList();

			switch(meta) {
			case 2:
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.25D, y + 0D, z + 0.875D, x + 1D, y + 1D, z + 1D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0D, y + 0D, z + 0.75D, x + 0.25D, y + 1D, z + 1D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0D, y + 0D, z + 0D, x + 0.125D, y + 1D, z + 0.75D));
				break;
			case 3:
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0D, y + 0D, z + 0D, x + 0.75D, y + 1D, z + 0.125D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.75D, y + 0D, z + 0D, x + 1D, y + 1D, z + 0.25D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.875D, y + 0D, z + 0.25D, x + 1D, y + 1D, z + 1D));
				break;
			case 4:
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.875D, y + 0D, z + 0D, x + 1D, y + 1D, z + 0.75D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.75D, y + 0D, z + 0.75D, x + 1D, y + 1D, z + 1D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0D, y + 0D, z + 0.875D, x + 0.75D, y + 1D, z + 1D));
				break;
			case 5:
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0D, y + 0D, z + 0.25D, x + 0.125D, y + 1D, z + 1D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0D, y + 0D, z + 0D, x + 0.25D, y + 1D, z + 0.25D));
				bbs.add(AxisAlignedBB.getBoundingBox(x + 0.25D, y + 0D, z + 0D, x + 1D, y + 1D, z + 0.125D));
				break;
			}

			for(AxisAlignedBB bb : bbs) {
				if(aabb.intersectsWith(bb)) {
					list.add(bb);
				}
			}
		} else {
			super.addCollisionBoxesToList(world, x, y, z, aabb, list, collider);
		}
	}

	@Override
	public int transformMeta(int meta, int coordBaseMode) {
		return INBTBlockTransformable.transformMetaDeco(meta, coordBaseMode);
	}
}