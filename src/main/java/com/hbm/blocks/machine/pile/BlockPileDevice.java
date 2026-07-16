package com.hbm.blocks.machine.pile;

import java.util.List;

import com.hbm.blocks.IBlockMulti;
import com.hbm.tileentity.machine.pile.TileEntityPileControl;
import com.hbm.tileentity.machine.pile.TileEntityPileLoader;
import com.hbm.tileentity.machine.pile.TileEntityPileVent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPileDevice extends BlockContainer implements IBlockMulti {

	public static final int ITEM_META_LOADER = 0;
	public static final int ITEM_META_VENT = 1;
	public static final int ITEM_META_CONTROL = 2;

	public static final int BLOCK_META_LOADER = 0;
	public static final int BLOCK_META_VENT = 4;
	public static final int BLOCK_META_CONTROL = 8;

	public BlockPileDevice() {
		super(Material.iron);
	}

	@Override
	public int getSubCount() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < getSubCount(); ++i) {
			list.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		meta -= meta % 4;
		if(meta == BLOCK_META_LOADER) return new TileEntityPileLoader();
		if(meta == BLOCK_META_VENT) return new TileEntityPileVent();
		if(meta == BLOCK_META_CONTROL) return new TileEntityPileControl();
		return null;
	}
	
	@Override public int getRenderType() { return -1; }
	@Override public boolean isOpaqueCube() { return false; }
	@Override public boolean renderAsNormalBlock() { return false; }

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float fx, float fy, float fz, int meta) {
		int metaOffset = itemMetaToBlockMeta(meta);
		// side is reduced by 2 because UP and DOWN (0 and 1) aren't relevant
		// therefore, all item metas (device subtypes) are neatly packed into 4 metas each
		side = MathHelper.clamp_int(side - 2, 0, 3);
		return metaOffset + side;
	}
	
	public static int itemMetaToBlockMeta(int meta) {
		if(meta >= ITEM_META_CONTROL) return BLOCK_META_CONTROL;
		if(meta == ITEM_META_VENT) return BLOCK_META_VENT;
		return BLOCK_META_LOADER;
	}

	@Override
	public int damageDropped(int meta) {
		if(meta >= BLOCK_META_CONTROL) return ITEM_META_CONTROL;
		if(meta >= BLOCK_META_VENT) return ITEM_META_VENT;
		return ITEM_META_LOADER;
	}
}
