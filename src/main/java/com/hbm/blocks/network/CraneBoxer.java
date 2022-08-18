package com.hbm.blocks.network;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.network.TileEntityCraneBoxer;
import com.hbm.tileentity.network.TileEntityCraneInserter;

import api.hbm.conveyor.IConveyorItem;
import api.hbm.conveyor.IConveyorPackage;
import api.hbm.conveyor.IEnterableBlock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class CraneBoxer extends BlockCraneBase implements IEnterableBlock {

	public CraneBoxer() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCraneBoxer();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		this.iconIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_box");
		this.iconSideIn = iconRegister.registerIcon(RefStrings.MODID + ":crane_side_box");
		this.iconDirectional = iconRegister.registerIcon(RefStrings.MODID + ":crane_boxer_top");
		this.iconDirectionalUp = iconRegister.registerIcon(RefStrings.MODID + ":crane_boxer_side_up");
		this.iconDirectionalDown = iconRegister.registerIcon(RefStrings.MODID + ":crane_boxer_side_down");
	}

	@Override
	public int getRotationFromSide(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if(meta > 1 && side == 1) {
			if(meta == 2) return 3;
			if(meta == 3) return 0;
			if(meta == 4) return 1;
			if(meta == 5) return 2;
		}
		
		return 0;
	}

	@Override
	public boolean canItemEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity) {
		ForgeDirection orientation = ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z));
		return orientation == dir;
	}

	@Override
	public void onItemEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorItem entity) {
		TileEntityCraneBoxer boxer = (TileEntityCraneBoxer) world.getTileEntity(x, y, z);
		
		ItemStack remainder = CraneInserter.addToInventory(boxer, boxer.getAccessibleSlotsFromSide(dir.ordinal()), entity.getItemStack(), dir.ordinal());
		
		if(remainder != null && remainder.stackSize > 0) {
			EntityItem drop = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, remainder.copy());
			world.spawnEntityInWorld(drop);
		}
	}
	
	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}
	
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		return Container.calcRedstoneFromInventory((TileEntityCraneInserter)world.getTileEntity(x, y, z));
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		this.dropContents(world, x, y, z, block, meta, 0, 21);
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean canPackageEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorPackage entity) {
		return false;
	}

	@Override
	public void onPackageEnter(World world, int x, int y, int z, ForgeDirection dir, IConveyorPackage entity) { }
}
