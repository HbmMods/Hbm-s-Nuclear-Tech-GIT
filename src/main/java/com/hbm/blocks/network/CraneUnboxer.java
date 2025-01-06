package com.hbm.blocks.network;

import api.hbm.conveyor.IConveyorItem;
import api.hbm.conveyor.IConveyorPackage;
import api.hbm.conveyor.IEnterableBlock;
import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityCraneBase;
import com.hbm.tileentity.network.TileEntityCraneUnboxer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CraneUnboxer extends BlockCraneBase implements IEnterableBlock {

	public CraneUnboxer() {
		super(Material.iron);
	}

	@Override
	public TileEntityCraneBase createNewTileEntity(World world, int meta) {
		return new TileEntityCraneUnboxer();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_box");
		this.iconSideOut = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_box");
		this.iconDirectional = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_top");
		this.iconDirectionalUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_side_down");
		this.iconDirectionalDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_side_up");
		this.iconDirectionalTurnLeft = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_top_right");
		this.iconDirectionalTurnRight = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_top_left");
		this.iconDirectionalSideLeftTurnUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_side_up_turn_left");
		this.iconDirectionalSideRightTurnUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_side_up_turn_right");
		this.iconDirectionalSideLeftTurnDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_side_down_turn_left");
		this.iconDirectionalSideRightTurnDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_side_down_turn_right");
		this.iconDirectionalSideUpTurnLeft = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_side_left_turn_up");
		this.iconDirectionalSideUpTurnRight = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_side_right_turn_up");
		this.iconDirectionalSideDownTurnLeft = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_side_left_turn_down");
		this.iconDirectionalSideDownTurnRight = iconRegister.registerIcon(RefStrings.MODID + ":crane_unboxer_side_right_turn_down");
	}

	@Override
	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);

		if(meta > 1 && side == 1) {
			// ok so i've been sitting around for 4-5 hours trying to come up with a
			// more elegant way to implement this and i have seriously no clue what
			// the guys at mojang did, but the uv rotation makes absolutely no sense
			// it's 2:30 am, please just accept this
			// - martin
			ForgeDirection leftHandDirection = getOutputSide(world, x, y, z).getRotation(getInputSide(world, x, y, z));
			if (leftHandDirection == ForgeDirection.UP) {
				if (meta == 2) return 2;
				if (meta == 3) return 1;
				if (meta == 4) return 3;
				if (meta == 5) return 0;
			}
			if (leftHandDirection == ForgeDirection.DOWN) {
				if (meta == 2) return 1;
				if (meta == 3) return 2;
				if (meta == 4) return 0;
				if (meta == 5) return 3;
			}

			if(meta == 2) return 0;
			if(meta == 3) return 3;
			if(meta == 4) return 2;
			if(meta == 5) return 1;
		}

		return 0;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		this.dropContents(world, x, y, z, block, meta, 0, 23);
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean canItemEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity) {
		return false;
	}

	@Override
	public void onItemEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity) { }

	@Override
	public boolean canPackageEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorPackage entity) {
		return getOutputSide(world, x, y, z) == dir;
	}

	@Override
	public void onPackageEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorPackage entity) {
		TileEntityCraneUnboxer unboxer = (TileEntityCraneUnboxer) world.getTileEntity(x, y, z);
		ForgeDirection accessedSide = getOutputSide(world, x, y, z).getOpposite();

		for(ItemStack stack : entity.getItemStacks()) {
			ItemStack remainder = CraneInserter.addToInventory(unboxer, unboxer.getAccessibleSlotsFromSide(accessedSide.ordinal()), stack, accessedSide.ordinal());
			
			if(remainder != null && remainder.stackSize > 0) {
				EntityItem drop = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, remainder.copy());
				world.spawnEntityInWorld(drop);
			}
		}
	}
	
	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		return Container.calcRedstoneFromInventory((TileEntityCraneUnboxer)world.getTileEntity(x, y, z));
	}
}
